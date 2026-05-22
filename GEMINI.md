# Project Instructions: NetBeans GUI & Architecture

## 1. NetBeans GUI Support
Setiap kali lu bikin atau modifikasi UI (Java Swing) di proyek ini, lu WAJIB ngikutin aturan ini biar gue bisa edit pake fitur drag-and-drop di NetBeans:

*   **Format File .java**: Gunakan struktur "NetBeans-managed code" (pake `initComponents()` dan `// GEN-BEGIN`).
*   **File .form**: Wajib sertakan file `.form` (XML) sebagai pendamping file `.java` di folder yang sama.
*   **Layout Manager**: Gunakan `GroupLayout` (Matisse) secara default.
*   **Event Handlers**: Gunakan pattern NetBeans (method `...ActionPerformed`).
*   **Variables Declaration**: Deklarasi variabel komponen harus di dalam blok managed di bawah.

## 2. Struktur Folder (MVC Pattern)
Proyek ini harus rapi dengan pembagian package sebagai berikut:

*   **`com.ebanking.config`**: Tempat koneksi database (misal: `DBConnection.java`).
*   **`com.ebanking.model`**: Class data/entity (POJO) (misal: `User.java`).
*   **`com.ebanking.dao`**: Data Access Object untuk query SQL (misal: `UserDAO.java`).
*   **`com.ebanking.view`**: UI/Form Swing beserta file `.form`-nya (misal: `LoginForm.java`).

Jangan pernah nyampur logic database di dalam class View. Selalu pake DAO sebagai perantara. Gue mau proyek ini gampang di-maintain dan kelihatan pro.

