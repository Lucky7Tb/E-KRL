-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 24, 2019 at 11:45 AM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.2

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
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(7, 'lucky', 'e10adc3949ba59abbe56e057f20f883e'),
(8, 'harry', 'db05833c29e688b5ab54d5e8608a72ec'),
(9, 'iman', '6f978ed59837d790d959998df31b3069'),
(10, 'admin', '21232f297a57a5a743894a0e4a801fc3'),
(11, 'user', 'e10adc3949ba59abbe56e057f20f883e');

-- --------------------------------------------------------

--
-- Table structure for table `jadwal_pemberangkatan`
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
-- Dumping data for table `jadwal_pemberangkatan`
--

INSERT INTO `jadwal_pemberangkatan` (`id`, `stasiun_pemberangkatan`, `stasiun_tujuan`, `tanggal`, `waktu_berangkat`, `waktu_sampai`, `harga`, `harga_eksekutif`, `harga_firstclass`, `ekonomi`, `eksekutif`, `firstclass`) VALUES
('PBKT072520', 'BD - Bandung - Bandung', 'CN - Cirebon - Cirebon', '2019-05-24', '02:00:00', '09:00:00', 100000, 100000, 100000, 20, 20, 20),
('PBKT205651', 'BD - Bandung - Bandung', 'PSE - Pasar Senen - Jakarta', '2019-05-20', '09:00:00', '12:00:00', 50000, 100000, 150000, 20, 20, 10),
('PBKT210826', 'BD - Bandung - Bandung', 'ML - Malang - Malang', '2018-12-30', '03:00:00', '06:00:00', 10000, 20000, 30000, 20, 20, 30);

-- --------------------------------------------------------

--
-- Table structure for table `pemesanan`
--

CREATE TABLE `pemesanan` (
  `id_pemesanan` varchar(255) NOT NULL,
  `id_pemberangkatan` varchar(255) NOT NULL,
  `Nama_pemesan` varchar(255) NOT NULL,
  `tanggal_pemesanan` date NOT NULL,
  `total_harga` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pemesanan`
--

INSERT INTO `pemesanan` (`id_pemesanan`, `id_pemberangkatan`, `Nama_pemesan`, `tanggal_pemesanan`, `total_harga`) VALUES
('093857', 'PBKT210826', 'Tyo', '2019-05-24', 10000),
('100247', 'PBKT072520', 'fitria', '2019-05-24', 100000),
('102839', 'PBKT210826', 'hadaina', '2019-05-24', 20000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jadwal_pemberangkatan`
--
ALTER TABLE `jadwal_pemberangkatan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pemesanan`
--
ALTER TABLE `pemesanan`
  ADD PRIMARY KEY (`id_pemesanan`),
  ADD KEY `id_pemberangkatan` (`id_pemberangkatan`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `pemesanan`
--
ALTER TABLE `pemesanan`
  ADD CONSTRAINT `pemeberangkatan` FOREIGN KEY (`id_pemberangkatan`) REFERENCES `jadwal_pemberangkatan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
