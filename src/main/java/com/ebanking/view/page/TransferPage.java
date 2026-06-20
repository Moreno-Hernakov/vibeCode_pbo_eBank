package com.ebanking.view.page;

import com.ebanking.model.User;
import com.ebanking.model.Account; // Pastikan model Account sudah diimport
import com.ebanking.dao.TransactionDAO;
import com.ebanking.model.FeatureModel; // Sesuaikan package tempat FeatureModel berada
import com.ebanking.model.FeatureModel;
import com.ebanking.service.impl.TransferService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TransferPage extends javax.swing.JPanel implements Page {

    private final User user;
    private final TransactionDAO transactionDAO;

    // Komponen Swing GUI
    private JComboBox<FeatureModel> comboFasilitas;
    private JTextField txtRekeningTujuan;
    private JTextField txtNominal;
    private JTextField txtKeterangan;
    private JButton btnTransfer;

    public TransferPage(User user) {
        this.user = user;
        this.transactionDAO = new TransactionDAO();
        initComponents();
        modelTransfer();
        loadFeatures();
    }

    @Override 
    public String getRoute() { return "/transfer"; }
    
    @Override 
    public JPanel getRoot()  { return this; }
    
    @Override 
    public void onShow()     {
        // Opsional: Reset form atau refresh saldo user saat halaman dibuka
        txtRekeningTujuan.setText("");
        txtNominal.setText("");
        txtKeterangan.setText("");
    }

    private void modelTransfer() {
        // Menggunakan GridBagLayout agar form presisi di tengah
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("MENU TRANSFER DANA");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(lblTitle, gbc);

        gbc.gridwidth = 1; // reset gridwidth
        gbc.anchor = GridBagConstraints.WEST;

        // 1. Combo Box Fitur/Fasilitas Transfer
        gbc.gridx = 0; gbc.gridy = 1;
        this.add(new JLabel("Pilih Jenis Transfer:"), gbc);
        
        comboFasilitas = new JComboBox<>();
        gbc.gridx = 1; gbc.gridy = 1;
        this.add(comboFasilitas, gbc);

        // 2. Input Rekening Tujuan
        gbc.gridx = 0; gbc.gridy = 2;
        this.add(new JLabel("Nomor Rekening Tujuan:"), gbc);
        
        txtRekeningTujuan = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2;
        this.add(txtRekeningTujuan, gbc);

        // 3. Input Nominal
        gbc.gridx = 0; gbc.gridy = 3;
        this.add(new JLabel("Nominal Transfer (Rp):"), gbc);
        
        txtNominal = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 3;
        this.add(txtNominal, gbc);

        // 4. Input Keterangan
        gbc.gridx = 0; gbc.gridy = 4;
        this.add(new JLabel("Keterangan:"), gbc);
        
        txtKeterangan = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 4;
        this.add(txtKeterangan, gbc);

        // 5. Tombol Eksekusi
        btnTransfer = new JButton("Kirim Transfer");
        btnTransfer.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnTransfer.setBackground(new Color(0, 123, 255));
        btnTransfer.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(btnTransfer, gbc);

        // Action Listener Tombol
        btnTransfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesTransfer();
            }
        });
    }

    /**
     * Mengambil data fitur transfer dari database dan memasukkannya ke Combo Box
     */
    private void loadFeatures() {
        List<FeatureModel> fiturList = transactionDAO.getTransferFeatures();
        comboFasilitas.removeAllItems();
        for (FeatureModel fitur : fiturList) {
            comboFasilitas.addItem(fitur);
        }
    }

    /**
     * Membaca input GUI dan melemparnya ke TransferService
     */
    private void prosesTransfer() {
        try {
            FeatureModel fiturTerpilih = (FeatureModel) comboFasilitas.getSelectedItem();
            if (fiturTerpilih == null) {
                JOptionPane.showMessageDialog(this, "Silakan pilih jenis transfer terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String rekTujuan = txtRekeningTujuan.getText().trim();
            String nominalRaw = txtNominal.getText().trim();
            String keterangan = txtKeterangan.getText().trim();

            if (rekTujuan.isEmpty() || nominalRaw.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua baris input wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double nominal = Double.parseDouble(nominalRaw);

            // --- AMBIL ACCOUNT NUMBER LANGSUNG DARI DAO ---
            String norekPengirim = transactionDAO.getAccountNumberByCif(user.getCifNumber());

            if (norekPengirim == null || norekPengirim.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Akun rekening untuk user ini tidak ditemukan di database!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // ----------------------------------------------

            // Membuat objek Account pengirim temporer untuk melengkapi BaseTransaction
            Account accountPengirim = new Account();
            accountPengirim.setAccountNumber(norekPengirim);
            accountPengirim.setCifNumber(user.getCifNumber());

            // Inisialisasi TransferService dengan nomor rekening yang didapat dari DAO
            TransferService service = new TransferService(
                    accountPengirim,
                    norekPengirim, // Source Account hasil query DAO
                    rekTujuan, // Destination Account dari input text field
                    nominal,
                    keterangan
            );

            // Eksekusi transaksi ke SP database
            service.execute();

            JOptionPane.showMessageDialog(this, "Permintaan transfer diproses! Periksa log konsol untuk status respon.", "Informasi", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nominal transfer harus berupa angka valid!", "Error Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Terjadi Kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}


