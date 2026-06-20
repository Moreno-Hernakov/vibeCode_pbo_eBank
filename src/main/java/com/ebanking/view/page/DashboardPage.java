package com.ebanking.view.page;

import com.ebanking.config.DBConnection;
import com.ebanking.model.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class DashboardPage extends javax.swing.JPanel implements Page {

    private final User user;
    private JLabel lblJumlahTransaksi;
    private JLabel lblJumlahRekening;
    private JLabel lblTotalSaldo;
    private JTable table;
    private DefaultTableModel model;

    public DashboardPage(User user) {
        this.user = user;
        initComponents();
    }

    @Override public String getRoute() { return "/dashboard"; }
    @Override public JPanel getRoot()  { return this; }

    @Override
    public void onShow() {
        loadJumlahTransaksi();
        loadJumlahRekening();
        loadTotalSaldo();
        loadTable();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 245, 245));

        // === KARTU STATISTIK ===
        lblJumlahTransaksi = new JLabel("0", SwingConstants.CENTER);
        lblJumlahRekening  = new JLabel("0", SwingConstants.CENTER);
        lblTotalSaldo      = new JLabel("Rp 0", SwingConstants.CENTER);

        JPanel topPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        topPanel.setOpaque(false);
        topPanel.add(createCard("Jumlah Transaksi", lblJumlahTransaksi));
        topPanel.add(createCard("Jumlah Rekening",  lblJumlahRekening));
        topPanel.add(createCard("Total Saldo",       lblTotalSaldo));
        add(topPanel, BorderLayout.NORTH);

        // === TABEL TRANSAKSI ===
        model = new DefaultTableModel(new Object[]{
            "ID", "Tanggal", "Reference", "Jumlah", "Status", "No. Rekening"
        }, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);
        table.getTableHeader().setBackground(new Color(0, 102, 102));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel titleLabel = new JLabel("Riwayat Transaksi Terbaru");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 102, 102));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.add(titleLabel, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);
    }
    // </editor-fold>

    private JPanel createCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 102, 102), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(new Color(117, 117, 117));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(new Color(0, 102, 102));

        card.add(lblTitle,   BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private void loadJumlahTransaksi() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM t_transaction");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) lblJumlahTransaksi.setText(rs.getString("total"));
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadJumlahRekening() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM m_account");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) lblJumlahRekening.setText(rs.getString("total"));
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadTotalSaldo() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT SUM(balance) AS total FROM m_account");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) lblTotalSaldo.setText(String.format("Rp %,.0f", rs.getDouble("total")));
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadTable() {
        model.setRowCount(0);
        String sql = "SELECT id_transaction, reference_number, transaction_date, " +
                     "transaction_amount, transaction_status, from_account_number " +
                     "FROM t_transaction WHERE cif_number = ? ORDER BY transaction_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getCifNumber());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getLong("id_transaction"),
                        rs.getTimestamp("transaction_date"),
                        rs.getString("reference_number"),
                        String.format("Rp %,.0f", rs.getDouble("transaction_amount")),
                        rs.getString("transaction_status"),
                        rs.getString("from_account_number")
                    });
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
