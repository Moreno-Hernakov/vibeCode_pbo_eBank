package com.ebanking.dao;

import com.ebanking.config.DBConnection;
import com.ebanking.model.Menu;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuDAO implements BaseDAO<Menu> {

    @Override public Menu getById(Long id) { return null; }
    @Override public boolean delete(Long id) { return false; }

    @Override
    public List<Menu> getAll() {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT menu_title, route_path, is_active FROM m_menu ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next())
                list.add(new Menu(rs.getString("menu_title"), rs.getString("route_path"), rs.getBoolean("is_active")));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public boolean save(Menu m) {
        String sql = "INSERT INTO m_menu (menu_title, route_path, is_active) VALUES (?, ?, ?)";
        try {
            return DBConnection.executeUpdate(sql, Arrays.asList(m.getMenuTitle(), m.getRoutePath(), m.isActive())) > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public boolean update(Menu m) {
        String sql = "UPDATE m_menu SET menu_title=?, is_active=? WHERE route_path=?";
        try {
            return DBConnection.executeUpdate(sql, Arrays.asList(m.getMenuTitle(), m.isActive(), m.getRoutePath())) > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteByRoute(String route) {
        String sql = "DELETE FROM m_menu WHERE route_path=?";
        try {
            return DBConnection.executeUpdate(sql, Arrays.asList(route)) > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
