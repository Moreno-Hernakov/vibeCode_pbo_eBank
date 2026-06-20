/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ebanking.view.page;

import com.ebanking.config.DBConnection;
import com.ebanking.model.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.sql.SQLException;

/**
 *
 * @author natan
 */
    public class AdminPage extends JPanel implements Page {

        private final User user;
        private JTable table;
        private DefaultTableModel model;

        private JButton btnTambah;
        private JButton btnLock;
        private JButton btnUnlock;
        private JButton btnDelete;

        public AdminPage(User user) throws SQLException {
            this.user = user;

            setLayout(new BorderLayout());
          
            
            menuTable();

        }

        private void menuTable() throws SQLException {

            // ===== TITLE =====
            JLabel title = new JLabel("Admin Management");
            title.setHorizontalAlignment(SwingConstants.CENTER);

            add(title, BorderLayout.NORTH);

            // ===== TABLE =====
            
            String query = "SELECT mc.cif_number, mc.customer_name, mu.username, ma.account_number, mu.status FROM m_customer mc\n" +
                            "JOIN m_user mu ON mu.cif_number=mc.cif_number\n" +
                            "JOIN m_account ma ON ma.cif_number=mc.cif_number";
            
            model = DBConnection.selectToTable(query, null);
            


            table = new JTable(model);
            table.setFillsViewportHeight(true);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            
            table.getColumnModel().getColumn(0).setPreferredWidth(100);
            table.getColumnModel().getColumn(1).setPreferredWidth(200);
            table.getColumnModel().getColumn(2).setPreferredWidth(150);
            table.getColumnModel().getColumn(3).setPreferredWidth(200);
            table.getColumnModel().getColumn(4).setPreferredWidth(120);

            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);

            scrollPane.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
            );
            
            scrollPane.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
            );

            JPanel centerPanel = new JPanel(new BorderLayout());

            centerPanel.setBorder(
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            );

            centerPanel.add(scrollPane, BorderLayout.CENTER);

            add(centerPanel, BorderLayout.CENTER);
            

            // ===== BUTTON PANEL =====
            JPanel actionPanel = new JPanel(new FlowLayout());

            btnTambah = new JButton("Tambah");
            btnLock = new JButton("Lock");
            btnUnlock = new JButton("Unlock");
            btnDelete = new JButton("Delete");

            actionPanel.add(btnTambah);
            actionPanel.add(btnLock);
            actionPanel.add(btnUnlock);
            actionPanel.add(btnDelete);
            
            actionPanel.setBorder(
                    BorderFactory.createEmptyBorder(0, 0, 10, 0)
            );

            add(actionPanel, BorderLayout.SOUTH);

            // ===== ACTION BUTTON =====
            btnTambah.addActionListener(e -> tambahCustomer());

            btnLock.addActionListener(e -> lockCustomer());

            btnUnlock.addActionListener(e -> unlockCustomer());

            btnDelete.addActionListener(e -> deleteCustomer());
        }

        private void tambahCustomer() {

            JOptionPane.showMessageDialog(this,
                    "Menu tambah customer");
        }

        private void lockCustomer() {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this,
                        "Pilih customer dulu");
                return;
            }

            model.setValueAt("LOCKED", row, 4);
        }

        private void unlockCustomer() {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this,
                        "Pilih customer dulu");
                return;
            }

            model.setValueAt("ACTIVE", row, 4);
        }

        private void deleteCustomer() {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this,
                        "Pilih customer dulu");
                return;
            }

            model.removeRow(row);
        }

        @Override
        public String getRoute() {
            return "/admin";
        }

        @Override
        public JPanel getRoot() {
            return this;
        }

        @Override
        public void onShow() {

        }
        
    }

  
    

