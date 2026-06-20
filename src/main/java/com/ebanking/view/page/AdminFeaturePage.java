package com.ebanking.view.page;

import com.ebanking.dao.FeatureDAO;
import com.ebanking.model.Feature;
import com.ebanking.model.User;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class AdminFeaturePage extends javax.swing.JPanel implements Page {

    private final User user;
    private final FeatureDAO dao = new FeatureDAO();

    public AdminFeaturePage(User user) {
        this.user = user;
        initComponents();
        styleComponents();
        wireListeners();
    }

    @Override public String getRoute() { return "/admin/feature"; }
    @Override public JPanel getRoot()  { return this; }

    @Override
    public void onShow() { loadTable(); }

    private void styleComponents() {
        // Header tabel
        jTable1.getTableHeader().setBackground(new java.awt.Color(0, 102, 102));
        jTable1.getTableHeader().setForeground(java.awt.Color.WHITE);
        jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));

        // Padding tombol
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

        btnSave.addActionListener(e   -> saveFeature());
        btnUpdate.addActionListener(e -> updateFeature());
        btnDelete.addActionListener(e -> deleteFeature());
        btnClear.addActionListener(e  -> clearForm());
    }

    private void loadTable() {
        DefaultTableModel tm = (DefaultTableModel) jTable1.getModel();
        tm.setRowCount(0);
        for (Feature f : dao.getAll()) {
            tm.addRow(new Object[]{
                f.getFeatureCode(),
                f.getFeatureName(),
                String.format("Rp %,.0f", f.getFee())
            });
        }
    }

    /** Isi textfield dari baris terpilih di tabel */
    private void fillFormFromTable() {
        int row = jTable1.getSelectedRow();
        if (row == -1) return;
        txtKode.setText(jTable1.getValueAt(row, 0).toString());
        txtNama.setText(jTable1.getValueAt(row, 1).toString());
        // Hapus "Rp " dan titik sebelum parse
        String feeStr = jTable1.getValueAt(row, 2).toString()
            .replace("Rp ", "").replace(".", "").replace(",", ".");
        txtBiaya.setText(feeStr);
        txtKode.setEditable(false); // kode tidak boleh diubah saat edit
    }

    private void saveFeature() {
        if (!validateForm()) return;
        Feature f = new Feature(txtKode.getText().trim(), txtNama.getText().trim(), parseFee());
        if (dao.save(f)) {
            JOptionPane.showMessageDialog(this, "Fitur berhasil ditambahkan.");
            loadTable(); clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateFeature() {
        if (jTable1.getSelectedRow() == -1) { JOptionPane.showMessageDialog(this, "Pilih fitur terlebih dahulu."); return; }
        if (!validateForm()) return;
        Feature f = new Feature(txtKode.getText().trim(), txtNama.getText().trim(), parseFee());
        if (dao.update(f)) {
            JOptionPane.showMessageDialog(this, "Fitur berhasil diperbarui.");
            loadTable(); clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteFeature() {
        int row = jTable1.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih fitur terlebih dahulu."); return; }
        String code = jTable1.getValueAt(row, 0).toString();
        if (JOptionPane.showConfirmDialog(this,
                "Yakin menghapus fitur \"" + code + "\"?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        if (dao.deleteByCode(code)) {
            JOptionPane.showMessageDialog(this, "Fitur berhasil dihapus.");
            loadTable(); clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menghapus.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtKode.setText(""); txtNama.setText(""); txtBiaya.setText("");
        txtKode.setEditable(true);
        jTable1.clearSelection();
    }

    private boolean validateForm() {
        if (txtKode.getText().trim().isEmpty() || txtNama.getText().trim().isEmpty() || txtBiaya.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi.");
            return false;
        }
        try { Double.parseDouble(txtBiaya.getText().trim().replace(",", ".")); }
        catch (NumberFormatException e) { JOptionPane.showMessageDialog(this, "Biaya admin harus berupa angka."); return false; }
        return true;
    }

    private double parseFee() {
        return Double.parseDouble(txtBiaya.getText().trim().replace(",", "."));
    }

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
        lblKode    = new javax.swing.JLabel();
        lblNama    = new javax.swing.JLabel();
        lblBiaya   = new javax.swing.JLabel();
        txtKode    = new javax.swing.JTextField();
        txtNama    = new javax.swing.JTextField();
        txtBiaya   = new javax.swing.JTextField();
        btnSave    = new javax.swing.JButton();
        btnUpdate  = new javax.swing.JButton();
        btnDelete  = new javax.swing.JButton();
        btnClear   = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 245, 245));

        // --- HEADER ---
        pnlHeader.setOpaque(false);
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 22));
        lblTitle.setForeground(new java.awt.Color(0, 102, 102));
        lblTitle.setText("Manajemen Fitur");
        lblSub.setFont(new java.awt.Font("Segoe UI", 0, 13));
        lblSub.setForeground(new java.awt.Color(117, 117, 117));
        lblSub.setText("Kelola katalog fitur dan biaya admin");

        javax.swing.GroupLayout phLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(phLayout);
        phLayout.setHorizontalGroup(phLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblSub,   javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        phLayout.setVerticalGroup(phLayout.createSequentialGroup()
            .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(lblSub,   javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE));

        // --- TABLE ---
        pnlTable.setBackground(java.awt.Color.WHITE);
        pnlTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224)));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{}, new String[]{"Kode", "Nama Fitur", "Biaya Admin"}
        ) { @Override public boolean isCellEditable(int r, int c) { return false; } });
        jTable1.setFillsViewportHeight(true);
        jTable1.setRowHeight(30);
        jTable1.setShowGrid(true);
        jTable1.setGridColor(new java.awt.Color(224, 224, 224));
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout ptLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(ptLayout);
        ptLayout.setHorizontalGroup(ptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        ptLayout.setVerticalGroup(ptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        // --- FORM ---
        pnlForm.setBackground(java.awt.Color.WHITE);
        pnlForm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224)));

        lblKode.setFont(new java.awt.Font("Segoe UI", 0, 13));  lblKode.setText("Kode");
        lblNama.setFont(new java.awt.Font("Segoe UI", 0, 13));  lblNama.setText("Nama Fitur");
        lblBiaya.setFont(new java.awt.Font("Segoe UI", 0, 13)); lblBiaya.setText("Biaya Admin");
        txtKode.setFont(new java.awt.Font("Segoe UI", 0, 13));
        txtNama.setFont(new java.awt.Font("Segoe UI", 0, 13));
        txtBiaya.setFont(new java.awt.Font("Segoe UI", 0, 13));

        btnSave.setBackground(new java.awt.Color(0, 102, 102));   btnSave.setForeground(java.awt.Color.WHITE);
        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 13));    btnSave.setText("Simpan");    btnSave.setFocusPainted(false); btnSave.setOpaque(true);
        btnUpdate.setBackground(new java.awt.Color(0, 102, 153)); btnUpdate.setForeground(java.awt.Color.WHITE);
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 13));  btnUpdate.setText("Update");  btnUpdate.setFocusPainted(false); btnUpdate.setOpaque(true);
        btnDelete.setBackground(new java.awt.Color(192, 57, 43)); btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 13));  btnDelete.setText("Hapus");   btnDelete.setFocusPainted(false); btnDelete.setOpaque(true);
        btnClear.setBackground(new java.awt.Color(224, 224, 224)); btnClear.setForeground(new java.awt.Color(33, 33, 33));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 13));   btnClear.setText("Bersihkan"); btnClear.setFocusPainted(false); btnClear.setOpaque(true);

        javax.swing.GroupLayout pfLayout = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pfLayout);
        pfLayout.setHorizontalGroup(pfLayout.createSequentialGroup()
            .addContainerGap(12, 12)
            .addGroup(pfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblKode, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblNama, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblBiaya, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, 8)
            .addGroup(pfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(txtKode,  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtNama,  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtBiaya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, 12)
            .addGroup(pfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnSave,   javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnClear,  javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(12, 12));
        pfLayout.setVerticalGroup(pfLayout.createSequentialGroup()
            .addContainerGap(10, 10)
            .addGroup(pfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblKode).addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, 8)
            .addGroup(pfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblNama).addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, 8)
            .addGroup(pfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblBiaya).addComponent(txtBiaya, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 0, Short.MAX_VALUE)
            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(10, 10));

        // --- ROOT LAYOUT ---
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
            .addComponent(pnlForm,   javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(20, 20));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblBiaya;
    private javax.swing.JLabel lblKode;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblSub;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JTextField txtBiaya;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtNama;
    // End of variables declaration//GEN-END:variables
}


