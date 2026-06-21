# rules.md — Panduan Teknis Proyek Java OOP

## Syarat Wajib

- Bahasa: Java
- Harus ada CRUD (minimal untuk entitas utama)
- Harus ada transaksi (minimal 1 alur transaksi lengkap)
- Database minimal 8 tabel
- Tampilan GUI (Java Swing)
- Terhubung ke database (MySQL via JDBC)

---

## Poin OOP yang Harus Dipenuhi

Semua poin di bawah wajib ada dalam kode. Tandai di checklist setelah diimplementasikan.

| # | Poin | Keterangan Singkat |
|---|------|--------------------|
| 1 | Bahasa Java | Seluruh kode ditulis Java SE 11+ |
| 2 | Class buatan sendiri | Minimal ada class Model, DAO, Service, View, Controller |
| 3 | Inheritance | Semua class model extends satu abstract parent |
| 4 | Static | Gunakan pada koneksi DB (Singleton) dan session manager |
| 5 | Modifier + Getter Setter | Semua field `private`, akses via getter/setter `public` |
| 6 | Polymorphism & Array of Object | Simpan objek turunan dalam `List<ParentClass>` atau array |
| 7 | Abstract class buatan sendiri | Parent class model harus abstract, ada minimal 1 abstract method |
| 8 | Generic | Buat `GenericDAO<T>` untuk operasi CRUD yang bisa dipakai semua entitas |
| 9 | Interface buatan sendiri | Minimal 2 interface, contoh: `Identifiable`, `Printable` |
| 10 | GUI | Gunakan Java Swing: JFrame, JPanel, JTable, JButton, dll. |
| 11 | Event Listener | Setiap tombol dan interaksi pakai listener (`ActionListener`, `KeyAdapter`, dll.) |
| 12 | Koneksi Database | MySQL + JDBC (`DriverManager.getConnection`) |

---

## Struktur Folder Proyek

```
src/
├── main/
│   ├── Main.java                    # Entry point
│   └── MainFrame.java               # JFrame utama
│
├── model/
│   ├── base/
│   │   ├── Entity.java              # [ABSTRACT CLASS] — field id, createdAt
│   │   ├── Identifiable.java        # [INTERFACE] — getId(), setId()
│   │   └── Printable.java           # [INTERFACE] — print()
│   └── *.java                       # Semua entitas extends Entity
│
├── dao/
│   ├── generic/
│   │   └── GenericDAO.java          # [GENERIC] — GenericDAO<T extends Entity>
│   └── *DAO.java                    # DAO per entitas
│
├── service/
│   └── *.java                       # Business logic & validasi
│
├── view/
│   └── *.java                       # JPanel per halaman/fitur
│
├── controller/
│   └── *.java                       # Koordinasi view ↔ service ↔ dao
│
└── util/
    ├── DatabaseConnection.java      # [STATIC] Singleton koneksi DB
    └── SessionManager.java          # [STATIC] Simpan user aktif
```

---

## Aturan per Layer

### model/base/Entity.java
- Harus `abstract`
- Field minimal: `id` (int), `createdAt` (Timestamp)
- Wajib ada minimal 1 `abstract` method, contoh: `toDisplayString()`
- Field `protected`, akses via getter/setter di subclass

```java
public abstract class Entity {
    protected int id;
    protected Timestamp createdAt;

    public abstract String toDisplayString();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
}
```

### model/base/Identifiable.java & Printable.java
- Dua interface terpisah, buatan sendiri
- `Identifiable`: deklarasikan `getId()` dan `setId()`
- `Printable`: deklarasikan `print()`
- Class model yang relevan implements salah satu atau keduanya

### model/*.java (Entitas)
- Semua `extends Entity`
- Field: `private`
- Getter/setter: `public`
- Override `toDisplayString()`

### dao/generic/GenericDAO.java
- Class generic: `GenericDAO<T extends Entity>`
- Method minimal: `findAll(...)`, `save(...)`
- Gunakan `PreparedStatement` untuk semua query

```java
public class GenericDAO<T extends Entity> {
    public List<T> findAll(String query, ResultSetMapper<T> mapper) throws SQLException { ... }
    public boolean save(String query, Object... params) throws SQLException { ... }
}
```

### util/DatabaseConnection.java
- Singleton pattern
- Field `instance` harus `private static`
- Method `getInstance()` harus `public static`
- Handle exception dengan `JOptionPane` jika gagal konek

```java
public class DatabaseConnection {
    private static Connection instance;

    public static Connection getInstance() {
        if (instance == null) {
            instance = DriverManager.getConnection(URL, USER, PASS);
        }
        return instance;
    }
}
```

### util/SessionManager.java
- Field `currentUser` harus `private static`
- Method: `setCurrentUser()`, `getCurrentUser()`, `logout()` — semua `static`

### view/*.java
- Satu JPanel per fitur/halaman
- Semua aksi tombol pakai event listener (bukan logic langsung di view)
- Delegate ke controller

### controller/*.java
- Tidak boleh ada query SQL langsung di controller
- Koordinasi: view → controller → service → dao

---

## Komponen Swing yang Wajib Dipakai

| Komponen | Fungsi |
|---|---|
| `JFrame` | Window utama |
| `JPanel` | Container tiap halaman |
| `JTable` + `DefaultTableModel` | Tampilkan data |
| `JTextField` | Input teks |
| `JButton` | Aksi |
| `JLabel` | Label |
| `JOptionPane` | Dialog pesan / konfirmasi |
| `JScrollPane` | Wrap tabel |
| `JComboBox` | Dropdown pilihan |
| `JPasswordField` | Input password |

Event listener yang wajib ada minimal:
- `ActionListener` (tombol)
- `KeyAdapter` → `keyReleased` (pencarian real-time)
- `ListSelectionListener` (pilih baris tabel)

---

## Aturan Database

- Minimal 8 tabel
- Semua tabel punya primary key `INT AUTO_INCREMENT`
- Gunakan `FOREIGN KEY` untuk relasi antar tabel
- Nama kolom: `snake_case`
- Gunakan `TIMESTAMP DEFAULT CURRENT_TIMESTAMP` untuk kolom waktu
- Semua query pakai `PreparedStatement` (bukan `Statement` biasa)
- Transaksi DB (`commit`/`rollback`) wajib dipakai di alur transaksi utama

---

## Aturan Transaksi

Alur transaksi utama wajib:
1. Validasi input sebelum eksekusi
2. Buka koneksi → set `autoCommit(false)`
3. Insert header transaksi
4. Loop insert detail transaksi
5. Update data terkait (misal: stok)
6. `commit()` jika semua berhasil
7. `rollback()` jika ada error
8. Tampilkan konfirmasi ke user

```java
conn.setAutoCommit(false);
try {
    // insert header
    // insert detail
    // update stok
    conn.commit();
} catch (SQLException e) {
    conn.rollback();
    JOptionPane.showMessageDialog(null, "Transaksi gagal: " + e.getMessage());
}
```

---

## Dependency

**Maven (pom.xml):**
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.3.0</version>
</dependency>
```

**Manual:** Download `mysql-connector-j-8.x.x.jar`, tambahkan ke classpath/Build Path di IDE.

---

## Checklist Akhir

Sebelum submit, pastikan semua terpenuhi:

- [ ] Kode ditulis 100% Java
- [ ] Ada class model buatan sendiri (bukan hanya class utility)
- [ ] Ada inheritance (extends dari abstract class sendiri)
- [ ] Ada penggunaan `static` field dan method
- [ ] Semua field model `private` + getter/setter
- [ ] Ada polymorphism: objek turunan disimpan sebagai tipe parent
- [ ] Ada `List<ParentType>` atau array of object
- [ ] Ada abstract class dengan minimal 1 abstract method
- [ ] Ada generic class (`GenericDAO<T>` atau setara)
- [ ] Ada minimal 2 interface buatan sendiri
- [ ] Ada GUI Swing (minimal JFrame + JTable + JButton)
- [ ] Ada event listener pada tombol dan komponen interaktif
- [ ] Aplikasi tersambung ke database MySQL via JDBC
- [ ] Ada minimal 8 tabel di database
- [ ] Ada fitur CRUD minimal untuk 1 entitas
- [ ] Ada alur transaksi lengkap dengan commit/rollback