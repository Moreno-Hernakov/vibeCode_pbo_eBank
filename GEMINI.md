# Project Instructions: NetBeans GUI Support

Setiap kali lu bikin atau modifikasi UI (Java Swing) di proyek ini, lu WAJIB ngikutin aturan ini biar gue bisa edit pake fitur drag-and-drop di NetBeans:

1.  **Format File .java**: Gunakan struktur "NetBeans-managed code". Artinya, kode inisialisasi komponen harus ada di dalam blok `// <editor-fold>` dan method `initComponents()` yang diapit oleh comment `// GEN-BEGIN:initComponents` dan `// GEN-END:initComponents`.
2.  **File .form**: Wajib sertakan file `.form` (XML) sebagai pendamping file `.java` di folder yang sama. File ini harus berisi metadata komponen yang sinkron dengan kode di `initComponents()`.
3.  **Layout Manager**: Gunakan `GroupLayout` (Matisse) secara default, karena ini adalah layout standar yang dipake NetBeans GUI Builder.
4.  **Event Handlers**: Hubungkan event (seperti klik tombol) menggunakan pattern NetBeans, yaitu dengan mendaftarkan listener di `initComponents()` dan membuat method handler khusus (misal: `private void btnLoginActionPerformed(java.awt.event.ActionEvent evt)`).
5.  **Variables Declaration**: Deklarasi variabel komponen Swing harus diletakkan di bagian bawah file, di dalam blok `// Variables declaration - do not modify`.

Jangan pernah bikin UI pake "Pure Manual Coding" (seperti GridBagLayout manual tanpa file .form) kecuali gue yang minta. Gue mau fleksibilitas buat ngerapiin UI sendiri lewat GUI Designer NetBeans.
