package com.ebanking.view.page;

import com.ebanking.dao.TransactionDAO;
import com.ebanking.model.Account;
import com.ebanking.model.FeatureModel;
import com.ebanking.model.User;
import com.ebanking.service.impl.TransferService;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import java.util.List;

public class TransferPage extends javax.swing.JPanel implements Page {

    private final User user;
    private final TransactionDAO transactionDAO = new TransactionDAO();

    public TransferPage(User user) {
        this.user = user;
        initComponents();
        loadFeatures();
    }

    @Override public String getRoute() { return "/transfer"; }
    @Override public JPanel getRoot()  { return this; }

    @Override
    public void onShow() {
        txtRekeningTujuan.setText("");
        txtNominal.setText("");
        txtKeterangan.setText("");
    }

    private void loadFeatures() {
        List<FeatureModel> list = transactionDAO.getTransferFeatures();
        comboFasilitas.removeAllItems();
        for (FeatureModel f : list) comboFasilitas.addItem(f);
    }

    private void prosesTransfer() {
        try {
            FeatureModel fiturTerpilih = (FeatureModel) comboFasilitas.getSelectedItem();
            if (fiturTerpilih == null) { JOptionPane.showMessageDialog(this, "Silakan pilih jenis transfer terlebih dahulu!"); return; }
            String rekTujuan  = txtRekeningTujuan.getText().trim();
            String nominalRaw = txtNominal.getText().trim();
            String keterangan = txtKeterangan.getText().trim();
            if (rekTujuan.isEmpty() || nominalRaw.isEmpty()) { JOptionPane.showMessageDialog(this, "Semua baris input wajib diisi!"); return; }
            double nominal = Double.parseDouble(nominalRaw);
            String norekPengirim = transactionDAO.getAccountNumberByCif(user.getCifNumber());
            if (norekPengirim == null || norekPengirim.isEmpty()) { JOptionPane.showMessageDialog(this, "Akun rekening tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE); return; }
            Account acc = new Account(); acc.setAccountNumber(norekPengirim); acc.setCifNumber(user.getCifNumber());
            new TransferService(acc, norekPengirim, rekTujuan, nominal, keterangan, fiturTerpilih.getFeatureCode()).execute();
            onShow();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nominal transfer harus berupa angka valid!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Terjadi Kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblSub = new javax.swing.JLabel();
        pnlForm = new javax.swing.JPanel();
        lblFasilitas = new javax.swing.JLabel();
        lblRekening = new javax.swing.JLabel();
        lblNominal = new javax.swing.JLabel();
        lblKeterangan = new javax.swing.JLabel();
        comboFasilitas = new javax.swing.JComboBox();
        txtRekeningTujuan = new javax.swing.JTextField();
        txtNominal = new javax.swing.JTextField();
        txtKeterangan = new javax.swing.JTextField();
        btnTransfer = new javax.swing.JButton();

        pnlHeader.setOpaque(false);

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(0, 102, 102));
        lblTitle.setText("Transfer Dana");

        lblSub.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSub.setText("Transfer ke sesama bank atau antar bank");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblSub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lblSub, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlForm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblFasilitas.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblFasilitas.setText("Jenis Transfer");

        lblRekening.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblRekening.setText("Rekening Tujuan");

        lblNominal.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblNominal.setText("Nominal (Rp)");

        lblKeterangan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblKeterangan.setText("Keterangan");

        comboFasilitas.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtRekeningTujuan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtNominal.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtKeterangan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        btnTransfer.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnTransfer.setText("Kirim Transfer");
        btnTransfer.setFocusPainted(false);
        btnTransfer.setOpaque(true);

        javax.swing.GroupLayout pnlFormLayout = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pnlFormLayout);
        pnlFormLayout.setHorizontalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFasilitas, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRekening, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNominal, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboFasilitas, 0, 338, Short.MAX_VALUE)
                            .addComponent(txtRekeningTujuan)
                            .addComponent(txtNominal)
                            .addComponent(txtKeterangan)))
                    .addComponent(btnTransfer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );
        pnlFormLayout.setVerticalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFasilitas)
                    .addComponent(comboFasilitas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRekening)
                    .addComponent(txtRekeningTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNominal)
                    .addComponent(txtNominal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKeterangan)
                    .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(btnTransfer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(pnlForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTransfer;
    private javax.swing.JComboBox comboFasilitas;
    private javax.swing.JLabel lblFasilitas;
    private javax.swing.JLabel lblKeterangan;
    private javax.swing.JLabel lblNominal;
    private javax.swing.JLabel lblRekening;
    private javax.swing.JLabel lblSub;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JTextField txtKeterangan;
    private javax.swing.JTextField txtNominal;
    private javax.swing.JTextField txtRekeningTujuan;
    // End of variables declaration//GEN-END:variables
}
