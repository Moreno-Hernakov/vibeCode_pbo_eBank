package com.ebanking.dao;

import com.ebanking.config.DBConnection;
import com.ebanking.config.ResponseHelper;
import com.ebanking.model.Transaction;
import com.ebanking.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionDAO implements BaseDAO<Transaction> {

    // =====================================================
    // CONNECTION
    // =====================================================
    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    // =====================================================
    // CRUD
    // =====================================================
    @Override
    public Transaction getById(Long id) {

        String sql = "SELECT * FROM t_transaction WHERE id_transaction = ?";

        try (
                Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {

            System.err.println("Error getById transaction: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Transaction> getAll() {

        List<Transaction> transactions = new ArrayList<>();

        String sql = """
                SELECT *
                FROM t_transaction
                ORDER BY transaction_date DESC
                """;

        try (
                Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                transactions.add(mapRow(rs));
            }

        } catch (SQLException e) {

            System.err.println("Error getAll transaction: " + e.getMessage());
        }

        return transactions;
    }

    @Override
    public boolean save(Transaction entity) {

        String sql = """
                INSERT INTO t_transaction
                (
                    reference_number,
                    cif_number,
                    from_account_number,
                    customer_reference,
                    transaction_amount,
                    fee,
                    transaction_status,
                    transaction_date,
                    feature_code,
                    response_code,
                    ipaddress,
                    biller_name,
                    location
                )
                VALUES
                (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try {

            List<Object> params = Arrays.asList(
                    entity.getReferenceNumber(),
                    entity.getCifNumber(),
                    entity.getFromAccountNumber(),
                    entity.getCustomerReference(),
                    entity.getTransactionAmount(),
                    entity.getFee(),
                    entity.getTransactionStatus(),
                    entity.getTransactionDate(),
                    entity.getFeatureCode(),
                    entity.getResponseCode(),
                    entity.getIpaddress(),
                    entity.getBillerName(),
                    entity.getLocation()
            );

            return DBConnection.executeUpdate(sql, params) > 0;

        } catch (SQLException e) {

            System.err.println("Error save transaction: " + e.getMessage());

            return false;
        }
    }

    @Override
    public boolean update(Transaction entity) {

        String sql = """
                UPDATE t_transaction
                SET
                    transaction_status = ?,
                    response_code = ?
                WHERE id_transaction = ?
                """;

        try {

            List<Object> params = Arrays.asList(
                    entity.getTransactionStatus(),
                    entity.getResponseCode(),
                    entity.getIdTransaction()
            );

            return DBConnection.executeUpdate(sql, params) > 0;

        } catch (SQLException e) {

            System.err.println("Error update transaction: " + e.getMessage());

            return false;
        }
    }

    @Override
    public boolean delete(Long id) {

        String sql = "DELETE FROM t_transaction WHERE id_transaction = ?";

        try {

            List<Object> params = Arrays.asList(id);

            return DBConnection.executeUpdate(sql, params) > 0;

        } catch (SQLException e) {

            System.err.println("Error delete transaction: " + e.getMessage());

            return false;
        }
    }

    // =====================================================
    // FUND TRANSFER
    // =====================================================
    public boolean fundTransfer(
            String fromAccount,
            String toAccount,
            double amount,
            String featureCode,
            String cifNumber,
            String ipAddress
    ) {

        // =========================
        // VALIDATION
        // =========================
        if (fromAccount == null || fromAccount.trim().isEmpty()
                || toAccount == null || toAccount.trim().isEmpty()) {

            throw new RuntimeException("Nomor rekening wajib diisi");
        }

        if (amount <= 0) {

            throw new RuntimeException("Nominal transfer tidak valid");
        }

        // =========================
        // STORED PROCEDURE
        // =========================
        String sql = "{CALL sp_fund_transfer(?,?,?,?,?,?,?,?)}";

        try (
                Connection conn = getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            // =========================
            // INPUT PARAMETER
            // =========================
            stmt.setString(1, fromAccount);

            stmt.setString(2, toAccount);

            stmt.setDouble(3, amount);

            stmt.setString(4, featureCode);

            stmt.setString(5, cifNumber);

            stmt.setString(6, ipAddress);

            // =========================
            // OUTPUT PARAMETER
            // =========================
            stmt.registerOutParameter(7, Types.VARCHAR);

            stmt.registerOutParameter(8, Types.VARCHAR);

            // =========================
            // EXECUTE
            // =========================
            stmt.execute();

            // =========================
            // GET OUTPUT
            // =========================
            String responseCode = stmt.getString(7);

            String referenceNumber = stmt.getString(8);

            // =========================
            // RESPONSE HANDLING
            // =========================
            if (ResponseHelper.isSuccess(responseCode)) {

                System.out.println(
                        "Transfer Success. Ref No: "
                        + referenceNumber
                );

                return true;

            } else {

                switch (responseCode) {

                    case "14":
                        throw new RuntimeException(
                                "Rekening tidak ditemukan / tidak aktif"
                        );

                    case "51":
                        throw new RuntimeException(
                                "Saldo tidak mencukupi"
                        );

                    case "61":
                        throw new RuntimeException(
                                "Limit transfer harian terlampaui"
                        );

                    case "99":
                        throw new RuntimeException(
                                "Database transaction error"
                        );

                    default:
                        throw new RuntimeException(
                                "Transfer gagal. Code: "
                                + responseCode
                        );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "SQLException transfer: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =====================================================
    // MAPPING RESULTSET -> OBJECT
    // =====================================================
    private Transaction mapRow(ResultSet rs) throws SQLException {

        Transaction trx = new Transaction();

        trx.setIdTransaction(rs.getLong("id_transaction"));

        trx.setReferenceNumber(rs.getString("reference_number"));

        trx.setCifNumber(rs.getString("cif_number"));

        trx.setFromAccountNumber(rs.getString("from_account_number"));

        trx.setCustomerReference(rs.getString("customer_reference"));

        trx.setTransactionAmount(rs.getBigDecimal("transaction_amount"));

        trx.setFee(rs.getBigDecimal("fee"));

        trx.setTransactionStatus(rs.getString("transaction_status"));

        trx.setTransactionDate(rs.getTimestamp("transaction_date"));

        trx.setFeatureCode(rs.getString("feature_code"));

        trx.setResponseCode(rs.getString("response_code"));

        trx.setIpaddress(rs.getString("ipaddress"));

        trx.setBillerName(rs.getString("biller_name"));

        trx.setLocation(rs.getString("location"));

        return trx;
    }
}
