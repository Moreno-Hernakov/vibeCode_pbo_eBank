package com.ebanking.view.page;

import javax.swing.JPanel;

/**
 * Kontrak untuk setiap halaman body yang bisa di-route oleh {@link Router}.
 *
 * Setiap halaman wajib menyebutkan route-nya, panel root yang ditampilkan,
 * dan aksi yang dijalankan tiap kali halaman dibuka (mis. refresh data).
 */
public interface Page {

    /** Route unik halaman ini, mis. "/transfer". Harus sama dengan route_path di m_menu. */
    String getRoute();

    /** Panel yang akan ditampilkan di area body. */
    JPanel getRoot();

    /** Dipanggil tiap kali halaman dibuka. Tempat refresh data / reset form. */
    void onShow();
}
