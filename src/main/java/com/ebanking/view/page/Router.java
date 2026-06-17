package com.ebanking.view.page;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

/**
 * Router sederhana berbasis CardLayout.
 *
 * Menyimpan tabel rute (route -> Page) dan menukar isi body container
 * saat navigate() dipanggil. Header & sidebar tidak terpengaruh.
 */
public class Router {

    private final JPanel container;
    private final CardLayout cardLayout;
    private final Map<String, Page> pages = new HashMap<>();

    /**
     * @param container panel body yang layout-nya sudah di-set CardLayout.
     */
    public Router(JPanel container) {
        this.container = container;
        this.cardLayout = (CardLayout) container.getLayout();
    }

    /** Daftarkan satu halaman; panel root-nya ditambahkan ke CardLayout dengan key = route. */
    public void register(Page page) {
        pages.put(page.getRoute(), page);
        container.add(page.getRoot(), page.getRoute());
    }

    /** Pindah ke halaman sesuai route. Diam saja kalau route tidak terdaftar. */
    public void navigate(String route) {
        Page page = pages.get(route);
        if (page == null) {
            return;
        }
        page.onShow();
        cardLayout.show(container, route);
    }

    /** Cek apakah route punya halaman terdaftar. */
    public boolean hasRoute(String route) {
        return pages.containsKey(route);
    }
}
