# PLAN: Integrasi OOP Skeleton & Database (Duet Maut DBConnection)

Dokumen ini menjelaskan rancangan integrasi skeleton kode transaksi (seperti `TransferService`) dengan database MySQL menggunakan utilitas `DBConnection` secara aman dan bersih (*zero boilerplate*).

---

## 1. Konsep Utama: Overloaded DBConnection (Transaction-Safe)

Untuk mendukung transaksi database beruntun (seperti transfer: kurangi saldo A -> tambah saldo B -> catat log) yang membutuhkan rollback jika salah satu gagal, `DBConnection.java` menyediakan method `executeUpdate` yang di-overload:

```java
// Versi 1: Mandiri (Untuk query sekali jalan, auto open-close connection)
public static int executeUpdate(String query, List<Object> params) throws SQLException {
    try (Connection conn = getConnection()) {
        return executeUpdate(conn, query, params);
    }
}

// Versi 2: Transaksi (Menggunakan koneksi aktif dari Service Layer untuk Commit/Rollback)
public static int executeUpdate(Connection conn, String query, List<Object> params) throws SQLException {
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        fillParameters(stmt, params);
        return stmt.executeUpdate();
    }
}
```

---

## 2. Model & DAO Layer (Zero Boilerplate)

Semua query SQL diisolasi di DAO, dan DAO memanfaatkan method di atas untuk mengisi parameter tanda tanya (`?`) secara otomatis.

### A. Model Baru
* **`com.ebanking.model.Account`**: Mewakili tabel `m_account`.
* **`com.ebanking.model.Transaction`**: Mewakili tabel `t_transaction`.

### B. DAO Baru
* **`com.ebanking.dao.AccountDAO`**:
  - `getByAccountNumber(String accNo)`: Mengambil data akun aktif.
  - `updateBalance(Connection conn, String accNo, double newBalance)`: Update saldo dengan mengirim koneksi transaksi.
* **`com.ebanking.dao.TransactionDAO`**:
  - `save(Connection conn, Transaction tx)`: Menyimpan data mutasi transaksi.

---

## 3. Service Layer (`TransferService`)

Di level ini, logika bisnis dan kontrol transaksi dikelola secara murni tanpa menulis string query SQL:

```java
@Override
public void execute() {
    if (!validate()) return;

    try (Connection conn = DBConnection.getConnection()) {
        conn.setAutoCommit(false); // Mulai transaksi DB

        try {
            Account sender = accountDAO.getByAccountNumber(user.getAccountNumber());
            Account receiver = accountDAO.getByAccountNumber(destinationAccount);

            if (sender.getBalance() < amount) throw new SQLException("Saldo kurang!");

            // Potong saldo pengirim
            accountDAO.updateBalance(conn, sender.getAccountNumber(), sender.getBalance() - amount);
            
            // Tambah saldo penerima
            accountDAO.updateBalance(conn, receiver.getAccountNumber(), receiver.getBalance() + amount);

            // Simpan riwayat transaksi
            Transaction tx = new Transaction(...);
            transactionDAO.save(conn, tx);

            conn.commit(); // Sukses semua -> Simpan ke DB
        } catch (SQLException e) {
            conn.rollback(); // Ada error -> Batalkan semua!
        }
    } catch (SQLException e) {}
}
```

---

*Silakan berikan konfirmasi **'gas'** atau **'lanjut'** di chat untuk memulai eksekusi.*
