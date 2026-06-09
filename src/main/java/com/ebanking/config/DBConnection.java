package com.ebanking.config;

import java.sql.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 * Utility database helper buat manage koneksi dan query
 * Biar ga nulis boilerplate JDBC berkali-kali di DAO/UI.
 */
public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_ebanking";
    private static final String USER = "root";
    private static final String PASS = "";

    /**
     * Dapatkan koneksi database.
     * Dipakai DAO untuk query custom atau transaction handling.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver tidak ditemukan: " + e.getMessage());
        }
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    /**
     * Binding parameter PreparedStatement secara dinamis pake Object.
     * Lebih type-safe daripada dipaksa String semua.
     */
    public static void fillParameters(PreparedStatement stmt, List<Object> params) throws SQLException {
        if (params != null) {
            int i = 1;
            for (Object p : params) {
                stmt.setObject(i++, p);
            }
        }
    }

    /**
     * Untuk query INSERT, UPDATE, DELETE (DML)
     * @return jumlah row yang terpengaruh
     */
    public static int executeUpdate(String query, List<Object> params) throws SQLException {
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            fillParameters(stmt, params);
            return stmt.executeUpdate();
        }
    }

    /**
     * Untuk load query SELECT langsung jadi DefaultTableModel (siap tempel ke JTable)
     */
    public static DefaultTableModel selectToTable(String query, List<Object> params) throws SQLException {
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            fillParameters(stmt, params);
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData rsMeta = rs.getMetaData();
                int columnCount = rsMeta.getColumnCount(); 

                String[] headers = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    headers[i - 1] = rsMeta.getColumnLabel(i);
                }

                DefaultTableModel model = new DefaultTableModel(headers, 0);

                while (rs.next()) {
                    Object[] rowData = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        rowData[i - 1] = rs.getObject(i);
                    }
                    model.addRow(rowData);
                }
                return model;
            }
        }
    }
}
