-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 20 Bulan Mei 2019 pada 10.37
-- Versi server: 10.1.38-MariaDB
-- Versi PHP: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_e_ticket`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(7, 'lucky', 'e10adc3949ba59abbe56e057f20f883e'),
(8, 'harry', 'db05833c29e688b5ab54d5e8608a72ec'),
(9, 'iman', '6f978ed59837d790d959998df31b3069'),
(10, 'admin', '21232f297a57a5a743894a0e4a801fc3'),
(11, 'user', 'e10adc3949ba59abbe56e057f20f883e');

-- --------------------------------------------------------

--
-- Struktur dari tabel `jadwal_pemberangkatan`
--

CREATE TABLE `jadwal_pemberangkatan` (
  `id` varchar(255) NOT NULL,
  `stasiun_pemberangkatan` varchar(255) NOT NULL,
  `stasiun_tujuan` varchar(255) NOT NULL,
  `tanggal` date NOT NULL,
  `waktu_berangkat` time NOT NULL,
  `waktu_sampai` time NOT NULL,
  `harga` int(50) NOT NULL,
  `harga_eksekutif` int(255) NOT NULL,
  `harga_firstclass` int(255) NOT NULL,
  `ekonomi` int(11) NOT NULL,
  `eksekutif` int(11) NOT NULL,
  `firstclass` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `jadwal_pemberangkatan`
--

INSERT INTO `jadwal_pemberangkatan` (`id`, `stasiun_pemberangkatan`, `stasiun_tujuan`, `tanggal`, `waktu_berangkat`, `waktu_sampai`, `harga`, `harga_eksekutif`, `harga_firstclass`, `ekonomi`, `eksekutif`, `firstclass`) VALUES
('PBKT210546', 'BD - Bandung - Bandung', 'ML - Malang - Malang', '2017-01-01', '04:00:00', '07:00:00', 50000, 90000, 100000, 20, 20, 50),
('PBKT212647', 'BD - Bandung - Bandung', 'SMC - Semarang Poncol - Semarang', '2017-01-01', '04:00:00', '05:00:00', 100000, 150000, 200000, 20, 20, 20),
('PBKT212841', 'BD - Bandung - Bandung', ' CN - Cirebon - Cirebon', '2017-01-01', '04:00:00', '05:00:00', 100000, 150000, 200000, 50, 50, 50),
('PBKT225745', 'BD - Bandung - Bandung', 'PSE - Pasar Senen - Jakarta', '2018-12-30', '12:00:00', '13:00:00', 50000, 50000, 50000, 20, 20, 20);

-- --------------------------------------------------------

--
-- Struktur dari tabel `pemesanan`
--

CREATE TABLE `pemesanan` (
  `id_pemesanan` varchar(255) NOT NULL,
  `id_pemberangkatan` varchar(255) NOT NULL,
  `Nama_pemesan` varchar(255) NOT NULL,
  `tanggal_pemesanan` date NOT NULL,
  `total_harga` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `jadwal_pemberangkatan`
--
ALTER TABLE `jadwal_pemberangkatan`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `pemesanan`
--
ALTER TABLE `pemesanan`
  ADD PRIMARY KEY (`id_pemesanan`),
  ADD KEY `id_pemberangkatan` (`id_pemberangkatan`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `pemesanan`
--
ALTER TABLE `pemesanan`
  ADD CONSTRAINT `pemeberangkatan` FOREIGN KEY (`id_pemberangkatan`) REFERENCES `jadwal_pemberangkatan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
