package com.ebanking.dao;

import com.ebanking.model.User;
import java.sql.*;

public class UserDAO {
    // Logic koneksi pindah kesini biar gak ribet katanya
    private static final String URL = "jdbc:mysql://localhost:3306/db_ebanking"; 
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver tidak ditemukan: " + e.getMessage());
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public User login(String username, String password) {
        String sql = "{CALL sp_login_user(?, ?, ?)}";
        
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.registerOutParameter(3, Types.VARCHAR);
            
            stmt.execute();
            
            String responseCode = stmt.getString(3);
            
            if ("00".equals(responseCode)) {
                return getUserByUsername(username);
            } else {
                System.out.println("Login Gagal: Response Code " + responseCode);
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat login: " + e.getMessage());
        }
        return null;
    }

    private User getUserByUsername(String username) {
        String sql = "SELECT * FROM m_user WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("cif_number"),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error ambil data user: " + e.getMessage());
        }
        return null;
    }
}
