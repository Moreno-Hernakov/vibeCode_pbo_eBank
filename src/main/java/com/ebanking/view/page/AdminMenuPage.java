package com.ebanking.view.page;

import com.ebanking.model.User;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class AdminMenuPage extends javax.swing.JPanel implements Page {

    private final User user;

    public AdminMenuPage(User user) {
        this.user = user;
        initComponents();
        styleComponents();
        wireListeners();
    }

    @Override public String getRoute() { return "/admin/menu"; }
    @Override public JPanel getRoot()  { return this; }

    @Override
    public void onShow() {
        loadTable(); // TODO: isi implementasi
    }

    private void styleComponents() {
        jTable1.getTableHeader().setBackground(new java.awt.Color(0, 102, 102));
        jTable1.getTableHeader().setForeground(java.awt.Color.WHITE);
        jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        for (javax.swing.JButton btn : new javax.swing.JButton[]{btnSave, btnUpdate, btnDelete, btnClear}) {
            btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 14, 6, 14));
            btn.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        }
    }

    private void wireListeners() {
        // Klik baris tabel ? isi form
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromTable();
        });
        btnSave.addActionListener(e   -> saveMenu());
        btnUpdate.addActionListener(e -> updateMenu());
        btnDelete.addActionListener(e -> deleteMenu());
        btnClear.addActionListener(e  -> clearForm());
    }

    // ===================== TODO: diisi teman =====================

    /** Load semua data m_menu ke tabel */
    private void loadTable() {
        // TODO: query SELECT id, menu_title, route_path, is_active FROM m_menu
        // lalu isi DefaultTableModel jTable1
    }

    /** Isi form dari baris terpilih */
    private void fillFormFromTable() {
        int row = jTable1.getSelectedRow();
        if (row == -1) return;
        txtJudul.setText(jTable1.getValueAt(row, 1).toString());
        txtRoute.setText(jTable1.getValueAt(row, 2).toString());
        chkAktif.setSelected(Boolean.TRUE.equals(jTable1.getValueAt(row, 3)));
    }

    /** INSERT baris baru ke m_menu */
    private void saveMenu() {
        // TODO: validasi input, lalu INSERT INTO m_menu (menu_title, route_path, is_active)
    }

    /** UPDATE baris terpilih di m_menu */
    private void updateMenu() {
        // TODO: validasi, lalu UPDATE m_menu SET ... WHERE id = ?
    }

    /** DELETE baris terpilih dari m_menu */
    private void deleteMenu() {
        // TODO: konfirmasi JOptionPane, lalu DELETE FROM m_menu WHERE id = ?
    }

    private void clearForm() {
        txtJudul.setText("");
        txtRoute.setText("");
        chkAktif.setSelected(false);
        jTable1.clearSelection();
    }

    // ==============================================================

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader  = new javax.swing.JPanel();
        lblTitle   = new javax.swing.JLabel();
        lblSub     = new javax.swing.JLabel();
        pnlTable   = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1    = new javax.swing.JTable();
        pnlForm    = new javax.swing.JPanel();
        lblJudul   = new javax.swing.JLabel();
        lblRoute   = new javax.swing.JLabel();
        lblAktif   = new javax.swing.JLabel();
        txtJudul   = new javax.swing.JTextField();
        txtRoute   = new javax.swing.JTextField();
        chkAktif   = new javax.swing.JCheckBox();
        btnSave    = new javax.swing.JButton();
        btnUpdate  = new javax.swing.JButton();
        btnDelete  = new javax.swing.JButton();
        btnClear   = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 245, 245));

        // --- HEADER ---
        pnlHeader.setOpaque(false);
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 22));
        lblTitle.setForeground(new java.awt.Color(0, 102, 102));
        lblTitle.setText("Manajemen Menu");
        lblSub.setFont(new java.awt.Font("Segoe UI", 0, 13));
        lblSub.setForeground(new java.awt.Color(117, 117, 117));
        lblSub.setText("Kelola menu yang tampil di sidebar");

        javax.swing.GroupLayout phL = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(phL);
        phL.setHorizontalGroup(phL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblSub,   javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        phL.setVerticalGroup(phL.createSequentialGroup()
            .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(lblSub,   javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE));

        // --- TABLE ---
        pnlTable.setBackground(java.awt.Color.WHITE);
        pnlTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224)));
        jTable1.setModel(new DefaultTableModel(
            new Object[][]{}, new String[]{"ID", "Judul Menu", "Route", "Aktif"}
        ) {
            final Class<?>[] types = {Integer.class, String.class, String.class, Boolean.class};
            @Override public Class<?> getColumnClass(int c) { return types[c]; }
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        jTable1.setFillsViewportHeight(true);
        jTable1.setRowHeight(30);
        jTable1.setShowGrid(true);
        jTable1.setGridColor(new java.awt.Color(224, 224, 224));
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout ptL = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(ptL);
        ptL.setHorizontalGroup(ptL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        ptL.setVerticalGroup(ptL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        // --- FORM ---
        pnlForm.setBackground(java.awt.Color.WHITE);
        pnlForm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224)));

        lblJudul.setFont(new java.awt.Font("Segoe UI", 0, 13));  lblJudul.setText("Judul Menu");
        lblRoute.setFont(new java.awt.Font("Segoe UI", 0, 13));  lblRoute.setText("Route");
        lblAktif.setFont(new java.awt.Font("Segoe UI", 0, 13));  lblAktif.setText("Aktif");
        txtJudul.setFont(new java.awt.Font("Segoe UI", 0, 13));
        txtRoute.setFont(new java.awt.Font("Segoe UI", 0, 13));
        chkAktif.setFont(new java.awt.Font("Segoe UI", 0, 13));
        chkAktif.setText("Tampilkan di sidebar");
        chkAktif.setOpaque(false);

        btnSave.setBackground(new java.awt.Color(0, 102, 102));   btnSave.setForeground(java.awt.Color.WHITE);
        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 13));    btnSave.setText("Simpan");     btnSave.setFocusPainted(false); btnSave.setOpaque(true);
        btnUpdate.setBackground(new java.awt.Color(0, 102, 153)); btnUpdate.setForeground(java.awt.Color.WHITE);
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 13));  btnUpdate.setText("Update");   btnUpdate.setFocusPainted(false); btnUpdate.setOpaque(true);
        btnDelete.setBackground(new java.awt.Color(192, 57, 43)); btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 13));  btnDelete.setText("Hapus");    btnDelete.setFocusPainted(false); btnDelete.setOpaque(true);
        btnClear.setBackground(new java.awt.Color(224, 224, 224)); btnClear.setForeground(new java.awt.Color(33, 33, 33));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 13));   btnClear.setText("Bersihkan"); btnClear.setFocusPainted(false); btnClear.setOpaque(true);

        javax.swing.GroupLayout pfL = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pfL);
        pfL.setHorizontalGroup(pfL.createSequentialGroup()
            .addContainerGap(12, 12)
            .addGroup(pfL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblRoute, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblAktif, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, 8)
            .addGroup(pfL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(txtJudul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtRoute, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chkAktif, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, 12)
            .addGroup(pfL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnSave,   javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnClear,  javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(12, 12));
        pfL.setVerticalGroup(pfL.createSequentialGroup()
            .addContainerGap(10, 10)
            .addGroup(pfL.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblJudul).addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, 8)
            .addGroup(pfL.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblRoute).addComponent(txtRoute, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, 8)
            .addGroup(pfL.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblAktif).addComponent(chkAktif, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 0, Short.MAX_VALUE)
            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(10, 10));

        // --- ROOT ---
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addContainerGap(20, 20)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlTable,  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlForm,   javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap(20, 20));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addContainerGap(20, 20)
            .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, 12)
            .addComponent(pnlTable,  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, 12)
            .addComponent(pnlForm,   javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(20, 20));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JCheckBox chkAktif;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblAktif;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblRoute;
    private javax.swing.JLabel lblSub;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JTextField txtJudul;
    private javax.swing.JTextField txtRoute;
    // End of variables declaration//GEN-END:variables
}

