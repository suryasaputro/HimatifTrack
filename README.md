# 📱 HimatifTrack

**HimatifTrack** adalah aplikasi Android yang dikembangkan untuk membantu pengelolaan data anggota **Himpunan Mahasiswa Teknologi Informasi** secara digital.  
Aplikasi ini memudahkan pendataan, pencatatan, dan pengelolaan informasi anggota dengan antarmuka modern dan mudah digunakan.

---

## 🚀 Fitur Aplikasi HimatifTrack

- **Input Data Anggota Secara Lengkap**  
  Pengguna dapat mengisi formulir lengkap data anggota, mulai dari **Nama, NIM, Angkatan, Email, Tanggal Lahir, Jenis Kelamin, Alamat**, hingga **Hobi**, yang seluruhnya tertata dengan baik dan mudah diakses.

- **Dropdown Pintar untuk Angkatan & Gender**  
  Menggunakan komponen **MaterialAutoCompleteTextView**, pengguna dapat memilih **angkatan** (dari tahun 2015 hingga 2025) dan **jenis kelamin** melalui dropdown yang interaktif dan responsif.

- **Date Picker untuk Tanggal Lahir**  
  Input tanggal lahir dilengkapi dengan komponen **DatePickerDialog** sehingga pengguna dapat memilih tanggal dengan lebih praktis dan menghindari kesalahan penulisan format.

- **Validasi Otomatis**  
  Sistem akan secara otomatis memeriksa apakah semua kolom yang wajib diisi sudah lengkap, serta memastikan **format email valid** sebelum data disimpan. Ini mencegah input kosong atau salah.

- **Penyimpanan Data Lokal (Offline)**  
  Data anggota yang diinput akan langsung disimpan menggunakan **SQLite database lokal**, sehingga aplikasi dapat digunakan **tanpa koneksi internet** dan tetap menyimpan data dengan aman.

- **Kelola Data Anggota**  
  Pengguna dapat menampilkan daftar seluruh anggota yang telah diinput, lalu melakukan aksi **ubah data (edit)** atau **hapus data** secara langsung melalui daftar.

- **Animasi Transisi yang Halus**  
  Navigasi antar tampilan (form, daftar, detail) menggunakan **efek transisi Zoom & Fade**, memberikan pengalaman pengguna yang lebih modern dan nyaman digunakan.

- **Desain UI Modern & Konsisten**  
  Menggunakan **Material Design** dan font khusus **Comfortaa**, tampilan aplikasi terlihat profesional, ringan, dan tetap ramah untuk semua pengguna.

---

## 📸 Cuplikan Tampilan
- Halaman Menu Utama
<p align="center">
  <img src="https://github.com/user-attachments/assets/77e6933c-a189-4e58-8dc1-82d07efe995c"
       width="300"
       style="border-radius: 16px;" />
</p>
- Halaman Input Data
<p align="center">
  <img src="https://github.com/user-attachments/assets/3f50be48-1542-4343-ab7f-2256affb361b"
       width="300"
       style="border-radius: 16px;" />
</p>
- Halaman Lihat Data
<p align="center">
  <img src="https://github.com/user-attachments/assets/76c9d8db-0b64-4d79-a071-24afc1a9047d"
       width="300"
       style="border-radius: 16px;" />
</p>
- Halaman Detail 
<p align="center">
  <img src="https://github.com/user-attachments/assets/76c9d8db-0b64-4d79-a071-24afc1a9047d"
       width="300"
       style="border-radius: 16px;" />
</p>

---

## 📦 Unduh APK

Unduh versi terbaru aplikasi melalui halaman [GitHub Releases](https://github.com/suryasaputro/HimatifTrack/releases/tag/1.0.0).  
📁 File: `HimatifTrack-v1.0-debug.apk` *(versi debug, untuk keperluan uji coba)*

---

## 🔧 Teknologi yang Digunakan

- **Bahasa Pemrograman**: Java  
- **IDE**: Android Studio  
- **Database**: SQLite  
- **UI/UX**: XML + Material Components  
- **Font**: Comfortaa  
- **Minimal SDK**: Android 7.0 (API 24)

---

## 🛠 Cara Build Aplikasi

-  Clone repositori ini:
   ```bash
   git clone https://github.com/suryasaputro/HimatifTrack.git
-  Buka project dengan Android Studio
-  Sync Gradle & build project
-  Untuk membuat APK:
   - Debug: Build → Build APK(s)
   - Release: Build → Generate Signed APK
  
