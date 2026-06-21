package com.ebanking.view.page;

import com.ebanking.dao.TransactionDAO;
import com.ebanking.model.TransactionHistory; 
import com.ebanking.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MutasiPage extends javax.swing.JPanel implements Page {

    private final User user;
    private final TransactionDAO transactionDAO = new TransactionDAO();
    
    // Deklarasi komponen GUI secara manual
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private JButton btnSearch;
    private JTable tableMutation;
    private DefaultTableModel tableModel;

    public MutasiPage(User user) {
        this.user = user;
        initComponents(); // Mengabaikan layout builder NetBeans
        initManualComponents();
    }

    private void initManualComponents() {
        // 1. Atur layout panel utama menjadi BorderLayout
        this.setLayout(new BorderLayout(15, 15));
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 2. --- PANEL ATAS: JUDUL ---
        JPanel panelHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelHeader.setBackground(Color.WHITE);
        JLabel lblTitle = new JLabel("MUTASI REKENING");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 102)); 
        panelHeader.add(lblTitle);

        // 3. --- PANEL ATAS: FILTER INPUT & TOMBOL (FULL VERTIKAL) ---
        JPanel panelFilter = new JPanel(new GridBagLayout());
        panelFilter.setBackground(Color.WHITE);
        panelFilter.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(204, 204, 204)), 
                "Periode Transaksi", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new Font("Segoe UI", Font.PLAIN, 12), 
                new Color(102, 102, 102)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 12, 5, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Komponen Input (Ukurannya dipanjangkan menjadi 20)
        JLabel lblStartDate = new JLabel("Tanggal Awal (YYYY-MM-DD):");
        lblStartDate.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtStartDate = new JTextField("2026-01-01", 20); // Nilai 20 membuat text field lebih panjang
        txtStartDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lblEndDate = new JLabel("Tanggal Akhir (YYYY-MM-DD):");
        lblEndDate.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtEndDate = new JTextField("2026-12-31", 20); // Nilai 20 membuat text field lebih panjang
        txtEndDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        btnSearch = new JButton("Cari Mutasi");
        btnSearch.setBackground(new Color(0, 102, 102));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.setPreferredSize(new Dimension(150, 38));

        // Menyusun secara vertikal berurutan ke bawah (gridx = 0, gridy bertambah)
        gbc.gridx = 0; gbc.gridy = 0;
        panelFilter.add(lblStartDate, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelFilter.add(txtStartDate, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelFilter.add(lblEndDate, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelFilter.add(txtEndDate, gbc);

        // Tombol Search dipindahkan ke BARIS PALING BAWAH dari form input
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.insets = new Insets(15, 12, 8, 12); // Memberikan jarak atas lebih renggang khusus untuk tombol
        panelFilter.add(btnSearch, gbc);

        // Satukan Judul dan Form Filter ke Container NORTH (Atas)
        JPanel panelNorthContainer = new JPanel(new BorderLayout(10, 10));
        panelNorthContainer.setBackground(Color.WHITE);
        panelNorthContainer.add(panelHeader, BorderLayout.NORTH);
        panelNorthContainer.add(panelFilter, BorderLayout.WEST); // Mengunci form di sebelah kiri atas agar tidak melar melebar
        
        this.add(panelNorthContainer, BorderLayout.NORTH);

        // 4. --- PANEL BAWAH: TABEL MUTASI (HANYA 3 KOLOM) ---
        String[] columnNames = { "Tanggal", "Nominal", "Lokasi" };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Mengunci tabel agar tidak bisa diedit mengetik manual
            }
        };

        tableMutation = new JTable(tableModel);
        tableMutation.setRowHeight(28);
        tableMutation.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        // Style Header Tabel Tema Teal E-Banking
        tableMutation.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableMutation.getTableHeader().setBackground(new Color(0, 102, 102));
        tableMutation.getTableHeader().setForeground(Color.WHITE);
        tableMutation.setGridColor(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(tableMutation);
        this.add(scrollPane, BorderLayout.CENTER); // Menempatkan tabel memenuhi area bawah layar

        // Menghubungkan aksi klik tombol
        btnSearch.addActionListener(e -> loadMutationData());
    }

    private void loadMutationData() {
        String startDate = txtStartDate.getText().trim();
        String endDate = txtEndDate.getText().trim();

        if (startDate.isEmpty() || endDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tanggal awal dan akhir wajib diisi!", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Sesi user tidak ditemukan. Silakan login kembali.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Memanggil method DAO yang sudah diperbaiki
        List<TransactionHistory> listHistory = transactionDAO.getMutationHistory(user.getUsername(), startDate, endDate);
        tableModel.setRowCount(0);

        if (listHistory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada transaksi ditemukan pada periode tersebut.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Memasukkan data ke dalam 3 kolom JTable
        for (TransactionHistory h : listHistory) {
            Object[] rowData = {
                h.getTransactionDate() != null ? dateFormat.format(h.getTransactionDate()) : "-",
                rupiahFormat.format(h.getTransactionAmount()),
                h.getLocation()
            };
            tableModel.addRow(rowData);
        }
    }

    @Override public String getRoute() { return "/mutasi"; }
    @Override public JPanel getRoot()  { return this; }
    @Override public void onShow()     {
        if (tableModel != null) {
            tableModel.setRowCount(0);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(378, 378, 378)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
