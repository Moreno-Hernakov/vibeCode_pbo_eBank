package com.ebanking.view.page;

import com.ebanking.model.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Halaman Mutasi Rekening (route "/mutasi").
 * Masih placeholder; isi sebenarnya menyusul pada task wiring service.
 */
public class MutasiPage extends JPanel implements Page {

    private final User user;

    public MutasiPage(User user) {
        this.user = user;
        setBackground(new Color(245, 245, 245));
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Mutasi Rekening");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(title, BorderLayout.CENTER);
    }

    @Override
    public String getRoute() {
        return "/mutasi";
    }

    @Override
    public JPanel getRoot() {
        return this;
    }

    @Override
    public void onShow() {
        // TODO: refresh data / reset form saat halaman dibuka
    }
}
