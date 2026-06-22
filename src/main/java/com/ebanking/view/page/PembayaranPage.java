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

public class PembayaranPage extends javax.swing.JPanel implements Page {

    private final User user;
    private final TransactionDAO dao = new TransactionDAO();

    public PembayaranPage(User user) {
        this.user = user;
        initComponents();
        btnBayar.addActionListener(e -> prosesBayar());
    }

    @Override public String getRoute() { return "/pembayaran"; }
    @Override public JPanel getRoot()  { return this; }

    @Override
    public void onShow() {
        loadFeatures();
        txtNomor.setText("");
        txtNominal.setText("");
    }

    private void loadFeatures() {
        cmbJenis.removeAllItems();
        for (FeatureModel f : dao.getPaymentFeatures()) cmbJenis.addItem(f);
        updateFeeLabel();
    }

    private void updateFeeLabel() {
        FeatureModel f = (FeatureModel) cmbJenis.getSelectedItem();
        lblFeeValue.setText(f != null ? String.format("Rp %,.0f", f.getFee()) : "-");
    }

    private void prosesBayar() {
        FeatureModel fitur = (FeatureModel) cmbJenis.getSelectedItem();
        if (fitur == null || txtNomor.getText().trim().isEmpty() || txtNominal.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi."); return;
        }
        try {
            double nominal = Double.parseDouble(txtNominal.getText().trim());
            String norek = dao.getAccountNumberByCif(user.getCifNumber());
            Account acc = new Account(); acc.setAccountNumber(norek); acc.setCifNumber(user.getCifNumber());
            new TransferService(acc, norek, txtNomor.getText().trim(), nominal, "Pembayaran", fitur.getFeatureCode()).execute();
            onShow();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nominal harus berupa angka.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblSub = new javax.swing.JLabel();
        pnlForm = new javax.swing.JPanel();
        lblJenis = new javax.swing.JLabel();
        lblFee = new javax.swing.JLabel();
        lblNomor = new javax.swing.JLabel();
        lblNominal = new javax.swing.JLabel();
        cmbJenis = new javax.swing.JComboBox();
        lblFeeValue = new javax.swing.JLabel();
        txtNomor = new javax.swing.JTextField();
        txtNominal = new javax.swing.JTextField();
        btnBayar = new javax.swing.JButton();

        pnlHeader.setOpaque(false);

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(0, 102, 102));
        lblTitle.setText("Pembayaran Tagihan");

        lblSub.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSub.setText("Bayar tagihan listrik, air, dan lainnya");

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

        lblJenis.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblJenis.setText("Jenis Pembayaran");

        lblFee.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblFee.setText("Biaya Admin");

        lblNomor.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblNomor.setText("Nomor Tagihan");

        lblNominal.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblNominal.setText("Nominal (Rp)");

        cmbJenis.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblFeeValue.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblFeeValue.setForeground(new java.awt.Color(0, 102, 102));
        lblFeeValue.setText("-");

        txtNomor.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtNominal.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        btnBayar.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnBayar.setText("Bayar Sekarang");
        btnBayar.setFocusPainted(false);
        btnBayar.setOpaque(true);

        javax.swing.GroupLayout pnlFormLayout = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pnlFormLayout);
        pnlFormLayout.setHorizontalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFee, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNomor, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNominal, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbJenis, 0, 406, Short.MAX_VALUE)
                            .addComponent(lblFeeValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNomor)
                            .addComponent(txtNominal)))
                    .addComponent(btnBayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );
        pnlFormLayout.setVerticalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJenis)
                    .addComponent(cmbJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFee)
                    .addComponent(lblFeeValue))
                .addGap(8, 8, 8)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomor)
                    .addComponent(txtNomor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNominal)
                    .addComponent(txtNominal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    private javax.swing.JButton btnBayar;
    private javax.swing.JComboBox cmbJenis;
    private javax.swing.JLabel lblFee;
    private javax.swing.JLabel lblFeeValue;
    private javax.swing.JLabel lblJenis;
    private javax.swing.JLabel lblNominal;
    private javax.swing.JLabel lblNomor;
    private javax.swing.JLabel lblSub;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JTextField txtNominal;
    private javax.swing.JTextField txtNomor;
    // End of variables declaration//GEN-END:variables
}
