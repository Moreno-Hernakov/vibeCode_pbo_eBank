package com.ebanking.view.page;

import com.ebanking.dao.TransactionDAO;
import com.ebanking.model.TransactionHistory;
import com.ebanking.model.User;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;

public class MutasiPage extends javax.swing.JPanel implements Page {

    private final User user;
    private final TransactionDAO transactionDAO = new TransactionDAO();

    public MutasiPage(User user) {
        this.user = user;
        initComponents();
        styleComponents();
        btnSearch.addActionListener(e -> loadMutationData());
    }

    @Override public String getRoute() { return "/mutasi"; }
    @Override public JPanel getRoot()  { return this; }
    @Override public void onShow()     { ((DefaultTableModel) tableMutation.getModel()).setRowCount(0); }

    private void styleComponents() {
        tableMutation.getTableHeader().setBackground(new java.awt.Color(0, 102, 102));
        tableMutation.getTableHeader().setForeground(java.awt.Color.WHITE);
        tableMutation.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        tableMutation.getTableHeader().setReorderingAllowed(false);
        tableMutation.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
    }

    private void loadMutationData() {
        String startDate = txtStartDate.getText().trim();
        String endDate   = txtEndDate.getText().trim();
        if (startDate.isEmpty() || endDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tanggal awal dan akhir wajib diisi!"); return;
        }
        List<TransactionHistory> list = transactionDAO.getMutationHistory(user.getUsername(), startDate, endDate);
        DefaultTableModel tm = (DefaultTableModel) tableMutation.getModel();
        tm.setRowCount(0);
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada transaksi pada periode tersebut."); return;
        }
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (TransactionHistory h : list) {
            tm.addRow(new Object[]{
                h.getTransactionDate() != null ? sdf.format(h.getTransactionDate()) : "-",
                rupiah.format(h.getTransactionAmount()),
                h.getLocation()
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader    = new javax.swing.JPanel();
        lblTitle     = new javax.swing.JLabel();
        lblSub       = new javax.swing.JLabel();
        pnlFilter    = new javax.swing.JPanel();
        lblStartDate = new javax.swing.JLabel();
        lblEndDate   = new javax.swing.JLabel();
        txtStartDate = new javax.swing.JTextField();
        txtEndDate   = new javax.swing.JTextField();
        btnSearch    = new javax.swing.JButton();
        pnlTable     = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMutation= new javax.swing.JTable();

        setBackground(new java.awt.Color(245, 245, 245));

        // --- HEADER ---
        pnlHeader.setOpaque(false);
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 22));
        lblTitle.setForeground(new java.awt.Color(0, 102, 102));
        lblTitle.setText("Mutasi Rekening");
        lblSub.setFont(new java.awt.Font("Segoe UI", 0, 13));
        lblSub.setForeground(new java.awt.Color(117, 117, 117));
        lblSub.setText("Riwayat transaksi rekening Anda");

        GroupLayout phL = new GroupLayout(pnlHeader); pnlHeader.setLayout(phL);
        phL.setHorizontalGroup(phL.createParallelGroup()
            .addComponent(lblTitle, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblSub,   0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        phL.setVerticalGroup(phL.createSequentialGroup()
            .addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(lblSub,   GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE));

        // --- FILTER ---
        pnlFilter.setBackground(java.awt.Color.WHITE);
        pnlFilter.setBorder(BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224)));

        lblStartDate.setFont(new java.awt.Font("Segoe UI", 0, 13)); lblStartDate.setText("Tanggal Awal");
        lblEndDate.setFont(new java.awt.Font("Segoe UI", 0, 13));   lblEndDate.setText("Tanggal Akhir");
        txtStartDate.setFont(new java.awt.Font("Segoe UI", 0, 13)); txtStartDate.setText("2026-01-01");
        txtEndDate.setFont(new java.awt.Font("Segoe UI", 0, 13));   txtEndDate.setText("2026-12-31");

        btnSearch.setBackground(new java.awt.Color(0, 102, 102)); btnSearch.setForeground(java.awt.Color.WHITE);
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 13));  btnSearch.setText("Cari Mutasi");
        btnSearch.setFocusPainted(false); btnSearch.setOpaque(true);
        btnSearch.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));

        GroupLayout pfL = new GroupLayout(pnlFilter); pnlFilter.setLayout(pfL);
        pfL.setHorizontalGroup(pfL.createSequentialGroup()
            .addContainerGap(12, 12)
            .addComponent(lblStartDate, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
            .addGap(6)
            .addComponent(txtStartDate, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
            .addGap(16)
            .addComponent(lblEndDate, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
            .addGap(6)
            .addComponent(txtEndDate, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
            .addContainerGap(12, 12));
        pfL.setVerticalGroup(pfL.createSequentialGroup()
            .addContainerGap(10, 10)
            .addGroup(pfL.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblStartDate)
                .addComponent(txtStartDate, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblEndDate)
                .addComponent(txtEndDate, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
            .addContainerGap(10, 10));

        // --- TABLE ---
        pnlTable.setBackground(java.awt.Color.WHITE);
        pnlTable.setBorder(BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224)));

        tableMutation.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{}, new String[]{"Tanggal", "Nominal", "Lokasi"}
        ) { @Override public boolean isCellEditable(int r, int c) { return false; } });
        tableMutation.setFillsViewportHeight(true);
        tableMutation.setRowHeight(28);
        tableMutation.setShowGrid(true);
        tableMutation.setGridColor(new java.awt.Color(240, 240, 240));
        tableMutation.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tableMutation);

        GroupLayout ptL = new GroupLayout(pnlTable); pnlTable.setLayout(ptL);
        ptL.setHorizontalGroup(ptL.createParallelGroup()
            .addComponent(jScrollPane1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        ptL.setVerticalGroup(ptL.createParallelGroup()
            .addComponent(jScrollPane1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        lblHint = new javax.swing.JLabel("* Format tanggal: yyyy-MM-dd  (contoh: 2026-01-01)");
        lblHint.setFont(new java.awt.Font("Segoe UI", 0, 11));
        lblHint.setForeground(new java.awt.Color(150, 150, 150));

        // --- ROOT ---
        GroupLayout layout = new GroupLayout(this); setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addContainerGap(20, 20)
            .addGroup(layout.createParallelGroup()
                .addComponent(pnlHeader, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlFilter, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblHint)
                .addComponent(pnlTable,  0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap(20, 20));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addContainerGap(20, 20)
            .addComponent(pnlHeader, GroupLayout.PREFERRED_SIZE, 60,  GroupLayout.PREFERRED_SIZE)
            .addGap(12)
            .addComponent(pnlFilter, GroupLayout.PREFERRED_SIZE, 48,  GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 4, 4)
            .addComponent(lblHint)
            .addGap(8)
            .addComponent(pnlTable,  GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap(20, 20));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEndDate;
    private javax.swing.JLabel lblHint;
    private javax.swing.JLabel lblStartDate;
    private javax.swing.JLabel lblSub;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlFilter;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JTable tableMutation;
    private javax.swing.JTextField txtEndDate;
    private javax.swing.JTextField txtStartDate;
    // End of variables declaration//GEN-END:variables
}
