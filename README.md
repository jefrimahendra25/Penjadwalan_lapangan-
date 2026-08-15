# Penjadwalan Lapangan - Android Application

Aplikasi Android untuk penjadwalan dan booking lapangan olahraga. Aplikasi ini dibangun dengan Kotlin dan Jetpack Compose menggunakan arsitektur MVVM.

## Fitur

- **Daftar Lapangan**: Menampilkan semua lapangan olahraga yang tersedia dengan detail lengkap
- **Detail Lapangan**: Informasi lengkap tentang lapangan termasuk fasilitas, lokasi, dan harga
- **Sistem Booking**: Booking lapangan dengan pemilihan tanggal dan time slot
- **Calendar Integration**: Pemilihan tanggal dengan date picker
- **Time Slot Management**: Sistem slot waktu yang menunjukkan ketersediaan
- **Manajemen Booking**: Daftar semua booking dengan status (Confirmed, Pending, Cancelled, Completed)
- **Pencarian dan Filter**: Cari booking berdasarkan nama dan filter berdasarkan status
- **Local Storage**: Menggunakan Room Database untuk penyimpanan data lokal
- **Sample Data**: Data lapangan sample otomatis dimuat saat pertama kali aplikasi dibuka

## Teknologi

- **Kotlin**: Bahasa pemrograman utama
- **Jetpack Compose**: UI toolkit modern untuk Android
- **Material Design 3**: Desain material terbaru
- **Room Database**: Local database untuk penyimpanan data
- **Navigation Compose**: Navigasi antar screen
- **Coroutines & Flow**: Asynchronous programming
- **MVVM Architecture**: Model-View-ViewModel pattern

## Struktur Project

```
app/
├── src/main/
│   ├── java/com/penjadwalan/lapangan/
│   │   ├── data/
│   │   │   ├── Field.kt
│   │   │   ├── Booking.kt
│   │   │   ├── TimeSlot.kt
│   │   │   ├── FieldDao.kt
│   │   │   ├── BookingDao.kt
│   │   │   ├── AppDatabase.kt
│   │   │   ├── Converters.kt
│   │   │   ├── FieldRepository.kt
│   │   │   └── BookingRepository.kt
│   │   ├── viewmodel/
│   │   │   ├── FieldViewModel.kt
│   │   │   ├── BookingViewModel.kt
│   │   │   ├── FieldViewModelFactory.kt
│   │   │   └── BookingViewModelFactory.kt
│   │   ├── ui/
│   │   │   ├── screens/
│   │   │   │   ├── HomeScreen.kt
│   │   │   │   ├── FieldListScreen.kt
│   │   │   │   ├── FieldDetailScreen.kt
│   │   │   │   ├── BookingScreen.kt
│   │   │   │   └── BookingListScreen.kt
│   │   │   └── theme/
│   │   │       ├── Color.kt
│   │   │       ├── Type.kt
│   │   │       └── Theme.kt
│   │   ├── MainActivity.kt
│   │   └── PenjadwalanApplication.kt
│   └── res/
├── build.gradle.kts
└── proguard-rules.pro
```

## Cara Menjalankan

### Prasyarat

- Android Studio Hedgehog (2023.1.1) atau versi lebih baru
- JDK 17
- Android SDK dengan API level 34
- Gradle 8.2

### Langkah-langkah

1. Clone atau download project ini
2. Buka project di Android Studio
3. Tunggu Gradle sync selesai
4. Connect Android device atau start emulator
5. Run aplikasi dengan tombol Run atau Shift+F10

## Cara Menggunakan Aplikasi

1. **Home Screen**: Pilih antara "Daftar Lapangan" atau "Daftar Booking"
2. **Daftar Lapangan**: 
   - Lihat semua lapangan yang tersedia
   - Klik pada lapangan untuk melihat detail
3. **Detail Lapangan**:
   - Lihat informasi lengkap lapangan
   - Klik "Booking Sekarang" untuk melakukan booking
4. **Booking**:
   - Pilih tanggal menggunakan date picker
   - Pilih jam yang tersedia (time slot)
   - Isi nama dan nomor telepon
   - Konfirmasi booking
5. **Daftar Booking**:
   - Lihat semua booking yang telah dibuat
   - Filter berdasarkan status
   - Cari booking berdasarkan nama
   - Update status atau hapus booking

## Data Sample

Aplikasi secara otomatis memuat 6 lapangan sample saat pertama kali dibuka:
- Lapangan Futsal Merdeka
- Gor Basket Bung Karno
- Lapangan Voli Pantai Indah
- Gelanggang Badminton Champion
- Lapangan Sepak Bola Gelora
- Lapangan Tenis Royal

## Customization

### Menambah Lapangan Baru

Edit file `PenjadwalanApplication.kt` dan tambahkan lapangan baru di dalam fungsi `insertSampleData()`:

```kotlin
Field(
    name = "Nama Lapangan",
    type = "Jenis Olahraga",
    location = "Alamat Lapangan",
    pricePerHour = 100000.0,
    description = "Deskripsi lapangan",
    facilities = "Fasilitas 1, Fasilitas 2, Fasilitas 3",
    isOpen = true
)
```

### Mengubah Warna Theme

Edit file `ui/theme/Color.kt` untuk mengubah skema warna aplikasi.

## Troubleshooting

### Build Error
Jika mengalami error saat build, coba:
- Clean project: Build > Clean Project
- Rebuild project: Build > Rebuild Project
- Invalidate caches: File > Invalidate Caches > Invalidate and Restart

### Database Error
Jika database tidak terinisialisasi dengan benar:
- Uninstall aplikasi dari device/emulator
- Install ulang aplikasi

## Kontribusi

Kontribusi sangat diterima! Silakan fork project dan buat pull request untuk perubahan yang diusulkan.

## License

Project ini dibuat untuk tujuan pembelajaran dan pengembangan.

## Kontak

Untuk pertanyaan atau saran, silakan hubungi developer.

---

**Dibuat dengan ❤️ menggunakan Kotlin dan Jetpack Compose**
