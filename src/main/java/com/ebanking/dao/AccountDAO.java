package com.ebanking.dao;

import com.ebanking.model.Account;
import com.ebanking.config.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountDAO implements BaseDAO<Account> {

    @Override
    public Account getById(Long id) {
        // Not applicable - PK m_account adalah VARCHAR, bukan BIGINT
        return null;
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM m_account";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                accounts.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getAll account: " + e.getMessage());
        }
        return accounts;
    }

    @Override
    public boolean save(Account entity) {
        String sql = "INSERT INTO m_account (account_number, cif_number, product_type_id, balance, status) VALUES (?, ?, ?, ?, ?)";
        try {
            List<Object> params = Arrays.asList(
                entity.getAccountNumber(),
                entity.getCifNumber(),
                entity.getProductTypeId(),
                entity.getBalance(),
                entity.getStatus()
            );
            return DBConnection.executeUpdate(sql, params) > 0;
        } catch (SQLException e) {
            System.err.println("Error save account: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Account entity) {
        String sql = "UPDATE m_account SET balance = ?, status = ? WHERE account_number = ?";
        try {
            List<Object> params = Arrays.asList(
                entity.getBalance(),
                entity.getStatus(),
                entity.getAccountNumber()
            );
            return DBConnection.executeUpdate(sql, params) > 0;
        } catch (SQLException e) {
            System.err.println("Error update account: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        // Not applicable - PK m_account adalah VARCHAR, bukan BIGINT
        return false;
    }

    // ==================== Custom Methods ====================

    public Account getByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM m_account WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getByAccountNumber: " + e.getMessage());
        }
        return null;
    }

    public List<Account> getByCifNumber(String cifNumber) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM m_account WHERE cif_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cifNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    accounts.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getByCifNumber: " + e.getMessage());
        }
        return accounts;
    }

    public boolean deleteByAccountNumber(String accountNumber) {
        String sql = "DELETE FROM m_account WHERE account_number = ?";
        try {
            List<Object> params = Arrays.asList((Object) accountNumber);
            return DBConnection.executeUpdate(sql, params) > 0;
        } catch (SQLException e) {
            System.err.println("Error deleteByAccountNumber: " + e.getMessage());
            return false;
        }
    }

    // Helper: mapping ResultSet row ke Account object
    private Account mapRow(ResultSet rs) throws SQLException {
        Account acc = new Account();
        acc.setAccountNumber(rs.getString("account_number"));
        acc.setCifNumber(rs.getString("cif_number"));
        acc.setProductTypeId(rs.getInt("product_type_id"));
        acc.setBalance(rs.getDouble("balance"));
        acc.setStatus(rs.getString("status"));
        return acc;
    }
}
