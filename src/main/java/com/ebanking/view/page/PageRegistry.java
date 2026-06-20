package com.ebanking.view.page;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Registry yang memetakan route_path ke Supplier&lt;Page&gt;.
 *
 * Cara daftarkan page baru:
 *   1. Tambah baris register() di IsiBank.buildRegistry()
 *   2. Insert row di tabel m_menu dengan route_path yang sama
 *
 * Hanya route yang terdaftar di sini DAN ada di m_menu (DB) yang
 * akan muncul di sidebar dan di-load ke CardLayout.
 */
public class PageRegistry {

    private final Map<String, Supplier<Page>> registry = new HashMap<>();

    /** Daftarkan satu route beserta factory-nya. */
    public PageRegistry register(String route, Supplier<Page> factory) {
        registry.put(route, factory);
        return this;
    }

    /**
     * Buat instance Page untuk route yang diberikan.
     * @return instance baru, atau null kalau route tidak terdaftar.
     */
    public Page create(String route) {
        Supplier<Page> factory = registry.get(route);
        return (factory != null) ? factory.get() : null;
    }
}
