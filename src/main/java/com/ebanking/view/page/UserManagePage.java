package com.ebanking.view.page;

import com.ebanking.config.DBConnection;
import com.ebanking.model.User;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class UserManagePage extends javax.swing.JPanel implements Page {

    private final User user;

    public UserManagePage(User user) {
        this.user = user;
        initComponents();
        styleComponents();
        wireListeners();
        setRowSelected(false);
    }

    @Override public String getRoute() { return "/admin/user"; }
    @Override public JPanel getRoot()  { return this; }

    @Override
    public void onShow() {
        loadTable();
    }

    /** Terapkan warna & font yang tidak bisa di-set via designer */
    private void styleComponents() {
        // Header tabel
        jTable1.getTableHeader().setBackground(new java.awt.Color(0, 102, 102));
        jTable1.getTableHeader().setForeground(java.awt.Color.WHITE);
        jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));

        // Padding tombol
        btnLock.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btnUnlock.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btnDelete.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btnLock.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        btnUnlock.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void setRowSelected(boolean selected) {
        btnLock.setEnabled(selected);
        btnUnlock.setEnabled(selected);
        btnDelete.setEnabled(selected);
    }

    /** Pasang event listener ke tombol */
    private void wireListeners() {
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) setRowSelected(jTable1.getSelectedRow() != -1);
        });
        btnLock.addActionListener(e -> setStatus("LOCKED"));
        btnUnlock.addActionListener(e -> setStatus("ACTIVE"));
        btnDelete.addActionListener(e -> deleteUser());
    }

    /** Load data dari DB ke tabel */
    private void loadTable() {
        String sql =
            "SELECT mc.cif_number, mc.customer_name, mu.username, " +
            "       ma.account_number, mu.status " +
            "FROM m_customer mc " +
            "JOIN m_user    mu ON mu.cif_number = mc.cif_number " +
            "JOIN m_account ma ON ma.cif_number = mc.cif_number";
        try {
            DefaultTableModel newModel = DBConnection.selectToTable(sql, null);
            DefaultTableModel tm = (DefaultTableModel) jTable1.getModel();
            tm.setRowCount(0);
            for (int i = 0; i < newModel.getRowCount(); i++) {
                Object[] row = new Object[newModel.getColumnCount()];
                for (int j = 0; j < row.length; j++) row[j] = newModel.getValueAt(i, j);
                tm.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        setRowSelected(false);
    }

    /** Lock atau Unlock akun user yang dipilih */
    private void setStatus(String status) {
        int row = jTable1.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih pengguna terlebih dahulu."); return; }

        String username = jTable1.getModel().getValueAt(row, 2).toString();
        String label = "LOCKED".equals(status) ? "mengunci" : "mengaktifkan";

        if (JOptionPane.showConfirmDialog(this,
                "Yakin " + label + " akun \"" + username + "\"?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;

        try {
            DBConnection.executeUpdate("UPDATE m_user SET status=? WHERE username=?",
                Arrays.asList(status, username));
            JOptionPane.showMessageDialog(this, "Berhasil " + label + " akun.");
            loadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Hapus akun user yang dipilih */
    private void deleteUser() {
        int row = jTable1.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih pengguna terlebih dahulu."); return; }

        String username = jTable1.getModel().getValueAt(row, 2).toString();
        if (JOptionPane.showConfirmDialog(this,
                "Yakin menghapus akun \"" + username + "\"? Tidak bisa dibatalkan.",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) return;

        try {
            DBConnection.executeUpdate("DELETE FROM m_user WHERE username=?", Arrays.asList(username));
            JOptionPane.showMessageDialog(this, "Akun berhasil dihapus.");
            loadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblSub = new javax.swing.JLabel();
        pnlTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pnlAction = new javax.swing.JPanel();
        btnUnlock = new javax.swing.JButton();
        btnLock = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 245, 245));

        pnlHeader.setOpaque(false);

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 22));
        lblTitle.setForeground(new java.awt.Color(0, 102, 102));
        lblTitle.setText("Manajemen Pengguna");

        lblSub.setFont(new java.awt.Font("Segoe UI", 0, 13));
        lblSub.setForeground(new java.awt.Color(117, 117, 117));
        lblSub.setText("Kelola status akun pengguna terdaftar");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblSub,   javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSub,   javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlTable.setBackground(new java.awt.Color(255, 255, 255));
        pnlTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224)));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] {},
            new String[] { "CIF", "Nama Nasabah", "Username", "No. Rekening", "Status" }
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        jTable1.setFillsViewportHeight(true);
        jTable1.setRowHeight(30);
        jTable1.setShowGrid(true);
        jTable1.setGridColor(new java.awt.Color(224, 224, 224));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlAction.setOpaque(false);
        pnlAction.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 5));

        btnUnlock.setBackground(new java.awt.Color(224, 224, 224));
        btnUnlock.setFont(new java.awt.Font("Segoe UI", 1, 13));
        btnUnlock.setForeground(new java.awt.Color(33, 33, 33));
        btnUnlock.setText("Unlock");
        btnUnlock.setFocusPainted(false);
        btnUnlock.setOpaque(true);
        pnlAction.add(btnUnlock);

        btnLock.setBackground(new java.awt.Color(0, 102, 102));
        btnLock.setFont(new java.awt.Font("Segoe UI", 1, 13));
        btnLock.setForeground(new java.awt.Color(255, 255, 255));
        btnLock.setText("Lock");
        btnLock.setFocusPainted(false);
        btnLock.setOpaque(true);
        pnlAction.add(btnLock);

        btnDelete.setBackground(new java.awt.Color(192, 57, 43));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 13));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Hapus");
        btnDelete.setFocusPainted(false);
        btnDelete.setOpaque(true);
        pnlAction.add(btnDelete);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlHeader,  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTable,   javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAction,  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, 20)
                .addComponent(pnlHeader,  javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, 12)
                .addComponent(pnlTable,   javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, 12)
                .addComponent(pnlAction,  javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnLock;
    private javax.swing.JButton btnUnlock;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblSub;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlAction;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlTable;
    // End of variables declaration//GEN-END:variables
}






