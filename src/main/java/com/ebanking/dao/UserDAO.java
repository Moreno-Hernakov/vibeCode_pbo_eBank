package com.ebanking.dao;

import com.ebanking.model.User;
import com.ebanking.model.Menu;
import com.ebanking.config.ResponseHelper;
import com.ebanking.config.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UserDAO implements BaseDAO<User> {

    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }
    
    public boolean isUsernameExists(String username) {
        String sql = "SELECT 1 FROM m_user WHERE username = ?";

        try (
                Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Error cek username: " + e.getMessage());
            return true;
        }
    }
    
    public boolean changePass(String username, String old_pass, String new_pass, String new_pass_confirm) {
        if (!(new_pass.equals(new_pass_confirm))) {
            throw new RuntimeException("Password yang diisikan harus sama!");
        } else {
            String sql = "{CALL sp_change_password(?,?,?,?)}";
            try (Connection conn = getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, old_pass);
                stmt.setString(3, new_pass_confirm);
                stmt.registerOutParameter(4, Types.VARCHAR);
                
                stmt.execute();
                
                String response_code = stmt.getString(4);

                if ("01".equals(response_code)) {
                    throw new RuntimeException("Salah password, silahkan dicoba kembali");

                } else if ("02".equals(response_code)) {
                    throw new RuntimeException("Password tidak boleh sama dengan sebelumnya!");

                } else {
                    return true;
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Change Password Gagal!");
                return false;
            }
        }
    }
    
    public User register(String name, String phone, String email, String username, String password) {
        if (name == null || name.trim().isEmpty()
                || phone == null || phone.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            throw new RuntimeException("Semua field wajib diisi");
        }

        if (isUsernameExists(username)) {
            throw new RuntimeException("Username sudah digunakan");
        }
        String sql = "{CALL sp_register_customer(?,?,?,?,?,?,?,?)}";
        try (Connection conn = getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setString(4, "123456");
            stmt.setString(5, username);
            stmt.setString(6, password);
            stmt.registerOutParameter(7, Types.VARCHAR);
            stmt.registerOutParameter(8, Types.VARCHAR);

            stmt.execute();

            String responseCode = stmt.getString(7);


            if (ResponseHelper.isSuccess(responseCode)) {
                User user = new User();
                user.setUsername(username);
                user.setCifNumber(stmt.getString(8));
                user.setStatus("ACTIVE");

                return user;
            } 

        } catch (SQLException e) {
            System.err.println("SQLException register: " + e.getMessage());

        }
        return null;
    }

    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {

            throw new RuntimeException("Semua field wajib diisi");
        }
        
        String cleanUsername = (username != null) ? username.trim() : "";
        String cleanPassword = (password != null) ? password.trim() : "";

        String sql = "{CALL sp_login_user(?, ?, ?)}";
        try (Connection conn = getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, cleanUsername);
            stmt.setString(2, cleanPassword);
            stmt.registerOutParameter(3, Types.VARCHAR);

            boolean hasResultSet = stmt.execute();

            List<Menu> menuList = new ArrayList<>();
            if (hasResultSet) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        menuList.add(new Menu(
                                rs.getString("menu_title"),
                                rs.getString("route_path")
                        ));
                    }
                }
            }

            String responseCode = stmt.getString(3);
            if (ResponseHelper.isSuccess(responseCode)) {
                User user = getByUsername(cleanUsername);
                if (user != null) {
                    user.setMenus(menuList);
                }
                return user;
            }

        } catch (SQLException e) {
            System.err.println("SQLException login: " + e.getMessage());
        }
        return null;
    }

    public User getByUsername(String username) {
        String sql = "SELECT * FROM m_user WHERE username = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password").trim());
                    user.setCifNumber(rs.getString("cif_number"));
                    user.setStatus(rs.getString("status"));
                    return user;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error ambil data user: " + e.getMessage());
        }
        return null;
    }

    @Override
    public User getById(Long id) {
        String sql = "SELECT * FROM m_user WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setCifNumber(rs.getString("cif_number"));
                    user.setStatus(rs.getString("status"));
                    return user;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getById: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM m_user";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setCifNumber(rs.getString("cif_number"));
                user.setStatus(rs.getString("status"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error getAll: " + e.getMessage());
        }
        return users;
    }

    @Override
    public boolean save(User entity) {
        String sql = "INSERT INTO m_user (username, password, cif_number, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, entity.getUsername());
            stmt.setString(2, entity.getPassword());
            stmt.setString(3, entity.getCifNumber());
            stmt.setString(4, entity.getStatus());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error save user: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(User entity) {
        String sql = "UPDATE m_user SET password = ?, status = ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, entity.getPassword());
            stmt.setString(2, entity.getStatus());
            stmt.setString(3, entity.getUsername());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update user: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM m_user WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error delete user: " + e.getMessage());
            return false;
        }
    }
}