package com.ebanking.view.page;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Fallback page yang ditampilkan ketika route ada di DB (m_menu)
 * tapi belum didaftarkan di PageRegistry (IsiBank.buildRegistry()).
 */
public class NotFoundPage extends JPanel implements Page {

    private final String route;

    public NotFoundPage(String route) {
        this.route = route;
        JLabel label = new JLabel("Menu not found: " + route, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        add(label);
    }

    @Override public String getRoute() { return route; }
    @Override public JPanel getRoot()  { return this; }
    @Override public void onShow()     {}
}
