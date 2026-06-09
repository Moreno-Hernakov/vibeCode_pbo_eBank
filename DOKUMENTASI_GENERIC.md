# 📚 Panduan Konsep & Implementasi Generics (Untuk Nilai A+ Dosen)

Dokumen ini menjelaskan teori, struktur, dan cara mempresentasikan konsep **Generics** yang kita gunakan pada proyek **eBanking Pro** ini.

---

## 1. Apa itu Generics? (Teori Dasar)

**Generics** adalah fitur di Java (sejak Java 5) yang memungkinkan sebuah Class, Interface, atau Method untuk memiliki **tipe data parameter dinamis** yang ditentukan saat objek tersebut dideklarasikan atau diinstansiasi.

Di proyek kita, kita menggunakan simbol **`<T>`** (singkatan dari *Type* / Tipe Data) sebagai parameter bayangan (placeholder).

---

## 2. Struktur Generics di Proyek Kita

Kita menerapkan Generics di level **DAO (Data Access Object)** menggunakan interface generic `BaseDAO<T>` agar method-method CRUD (Create, Read, Update, Delete) menjadi standar dan reusable.

```
                  ┌────────────────────────┐
                  │   BaseDAO<T> (Generic) │
                  └───────────┬────────────┘
                              │
             ┌────────────────┴────────────────┐
             ▼                                 ▼
   ┌──────────────────┐              ┌──────────────────┐
   │ UserDAO          │              │ AccountDAO       │
   │ implements       │              │ implements       │
   │ BaseDAO<User>    │              │ BaseDAO<Account> │
   └──────────────────┘              └──────────────────┘
```

### A. Kontrak Generic (`BaseDAO.java`)
Ini adalah interface generic yang bertindak sebagai "cetakan kosong" untuk semua DAO.

```java
package com.ebanking.dao;

import java.util.List;

public interface BaseDAO<T> {
    T getById(Long id);       // Return-type dinamis (T)
    List<T> getAll();         // Return List berisi objek dinamis (T)
    boolean save(T entity);   // Parameter input dinamis (T)
    boolean update(T entity); // Parameter input dinamis (T)
    boolean delete(Long id);
}
```

### B. Implementasi Nyata 1 (`UserDAO.java`)
Saat kita membuat `UserDAO`, kita menetapkan tipe data konkret `User` untuk menggantikan `<T>`.

```java
public class UserDAO implements BaseDAO<User> {
    @Override
    public User getById(Long id) { ... } // T otomatis menjadi User

    @Override
    public boolean save(User entity) { ... } // Parameter otomatis bertipe User
}
```

### C. Implementasi Nyata 2 (`AccountDAO.java`)
Begitu pula saat membuat `AccountDAO`, `<T>` digantikan oleh tipe data `Account`.

```java
public class AccountDAO implements BaseDAO<Account> {
    @Override
    public Account getById(Long id) { ... } // T otomatis menjadi Account

    @Override
    public boolean save(Account entity) { ... } // Parameter otomatis bertipe Account
}
```

---

## 3. Cara Menggunakannya di Service Layer

Di level logika bisnis (Service), kita bisa memanggil DAO tersebut dengan memanfaatkan polymorphism:

```java
// Tipe referensi menggunakan BaseDAO<User> (Generic Interface)
// Objek aslinya menggunakan UserDAO (Concrete Class)
private final BaseDAO<User> userDAO = new UserDAO();

public User getDetailUser(Long id) {
    return userDAO.getById(id); // Langsung mengembalikan objek User secara aman
}
```

---

## 4. Keuntungan Menggunakan Generics (Bahan Sidang Dosen)

Kalo ditanya dosen, *"Kenapa kamu pakai Generics di DAO?"*, jawab dengan 3 poin sakti ini:

1. **Type-Safety (Keamanan Tipe Data)**: 
   Mencegah error runtime akibat salah memasukkan tipe data (misal tidak sengaja memasukkan objek `Account` ke method simpan `User`), karena compiler Java akan langsung menolak sebelum program dijalankan (compile-time error).
2. **Menghilangkan Casting**: 
   Sebelum ada Generics, kita harus melakukan casting manual (contoh: `User u = (User) userDAO.getById(id)`). Dengan Generics, Java sudah otomatis tahu tipe datanya sehingga casting tidak diperlukan lagi.
3. **Mencegah Duplikasi Kode (DRY - Don't Repeat Yourself)**:
   Kita hanya perlu mendefinisikan kontrak CRUD sekali di `BaseDAO<T>`, tidak perlu membuat interface berbeda seperti `IUserDAO`, `IAccountDAO`, dst.

---
*Dibuat untuk memudahkan presentasi tugas UAS kelompok kita.*
