# 🏛️ Blueprint Arsitektur Project: eBanking Pro (Full OOP Points)

Halo Tim! Dokumen ini adalah panduan biar kita dapet **NILAI MAKSIMAL** sesuai kriteria di `RULE.md`. Jangan kaget liat foldernya agak banyak, ini taktik kita buat pamer ilmu ke dosen.

## 📂 Struktur Folder Baru (MVC + Service Layer)
```text
com.ebanking.
├── config/         # [STATIC] Tempat Helper (Response Code, Koneksi DB).
├── model/          # [MODIFIER] POJO Class. WAJIB private + Getter/Setter.
├── dao/            # [GENERIC] Interface BaseDAO<T> & implementasinya.
│   ├── BaseDAO.java       # Generic Interface
│   └── UserDAO.java       # Implements BaseDAO<User>
├── service/        # [ABSTRACT & INTERFACE] Pusat Otak & 4 Pilar OOP.
│   ├── IValidatable.java  # Interface (Kontrak Bisnis)
│   └── BaseTransaction.java # Abstract Class (Bapak Transaksi)
└── service.impl/   # [INHERITANCE] Implementasi nyata (TransferService, dll).
└── view/           # [GUI + EVENT LISTENER] Urusan Swing UI kalian.
```

## 🎯 Strategi "Sapu Bersih" Nilai (Berdasarkan RULE.md)

Biar kaga ada poin yang kelewat, ini tabel korelasi antara kriteria dosen sama apa yang kita bikin:

| Kriteria Penilaian | Implementasi di Project Kita | Lokasi File / Folder |
| :--- | :--- | :--- |
| **Inheritance** | `TransferService` extends `BaseTransaction` | `service.impl/` |
| **Static** | `ResponseHelper.isSuccess()` & konstanta RC | `config/ResponseHelper.java` |
| **Modifier + Getter Setter** | Field `private` di Model + Getter/Setter lengkap | `model/*.java` |
| **Polymorphism** | Manggil `execute()` dari referensi `BaseTransaction` | `view/` & `service/` |
| **Array of Object** | `List<Menu>` atau `List<Account>` (Collection of Objects) | `model/User.java` |
| **Abstract Class** | `BaseTransaction` (Induk segala transaksi) | `service/BaseTransaction.java` |
| **Generic** | `BaseDAO<T>` (Generic Interface) | `dao/BaseDAO.java` |
| **Interface** | `IValidatable` & `BaseDAO` | `service/` & `dao/` |
| **CRUD & Transaksi** | Fitur Login, Transfer, Mutasi, & Kelola User | `service.impl/` & `dao/` |
| **Database (8+ Tabel)** | Udah ada 9 tabel (User, Cust, Acc, Trx, dll) | `DB_eBanking.sql` |
| **GUI & Event Listener** | Pake Java Swing (NetBeans Matisse) | `view/*.java` |

## 🔄 Alur Kerja (Data Flow)
**UI (View)** ➔ **Service (Logic)** ➔ **DAO (Database)** ➔ **DB (MySQL)**
-   **Temen UI**: Jangan panggil DAO langsung! Panggil Service. Lu tinggal panggil `service.execute()` beres.
-   **Temen DB**: Tabel minimal 8. Kalo fitur Menu ilang karena ketimpa, lapor AI biar disapu (suntik ulang).

---
*Dibuat oleh AI Developer Assistant Lu (Tukang Sapu SQL).*
