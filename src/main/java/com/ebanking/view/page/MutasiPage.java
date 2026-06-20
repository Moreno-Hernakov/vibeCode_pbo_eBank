package com.ebanking.view.page;

import com.ebanking.model.User;
import javax.swing.JPanel;

public class MutasiPage extends javax.swing.JPanel implements Page {

    private final User user;

    public MutasiPage(User user) {
        this.user = user;
        initComponents();
    }

    @Override public String getRoute() { return "/mutasi"; }
    @Override public JPanel getRoot()  { return this; }
    @Override public void onShow()     {}

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
