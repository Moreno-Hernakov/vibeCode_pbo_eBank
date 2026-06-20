package com.ebanking.dao;

import com.ebanking.config.DBConnection;
import com.ebanking.model.Feature;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeatureDAO implements BaseDAO<Feature> {

    @Override
    public Feature getById(Long id) { return null; } // PK String, tidak dipakai

    @Override
    public List<Feature> getAll() {
        List<Feature> list = new ArrayList<>();
        String sql = "SELECT feature_code, feature_name, fee FROM m_feature ORDER BY feature_code";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next())
                list.add(new Feature(rs.getString("feature_code"), rs.getString("feature_name"), rs.getDouble("fee")));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public boolean save(Feature f) {
        String sql = "INSERT INTO m_feature (feature_code, feature_name, fee) VALUES (?, ?, ?)";
        try {
            return DBConnection.executeUpdate(sql, Arrays.asList(f.getFeatureCode(), f.getFeatureName(), f.getFee())) > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public boolean update(Feature f) {
        String sql = "UPDATE m_feature SET feature_name=?, fee=? WHERE feature_code=?";
        try {
            return DBConnection.executeUpdate(sql, Arrays.asList(f.getFeatureName(), f.getFee(), f.getFeatureCode())) > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public boolean delete(Long id) { return false; } // gunakan deleteByCode

    public boolean deleteByCode(String code) {
        String sql = "DELETE FROM m_feature WHERE feature_code=?";
        try {
            return DBConnection.executeUpdate(sql, Arrays.asList(code)) > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public Feature getByCode(String code) {
        String sql = "SELECT feature_code, feature_name, fee FROM m_feature WHERE feature_code=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Feature(rs.getString("feature_code"), rs.getString("feature_name"), rs.getDouble("fee"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}
