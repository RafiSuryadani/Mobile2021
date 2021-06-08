-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 07, 2021 at 05:46 AM
-- Server version: 10.3.28-MariaDB-cll-lve
-- PHP Version: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `u1474912_data_karyawan`
--

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `nik` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `is_admin` int(11) NOT NULL DEFAULT 0 COMMENT '0=not admin; 1=admin',
  `create_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `nik`, `username`, `password`, `name`, `email`, `photo`, `address`, `is_admin`, `create_at`) VALUES
(1, '9999999999999999999', 'developer', 'e10adc3949ba59abbe56e057f20f883e', 'Developer HESOYAM', 'developer@developer.com', 'upload/8e7b6075208f5820ca034e08fea088c7.jpg', 'di rumah', 1, '2021-06-06 18:55:51'),
(5, '007', 'Rsans', 'e10adc3949ba59abbe56e057f20f883e', 'Rsans', 'pacheshaarawy@gmail.com', 'upload/acdc9aa7e105daff6fa56f2d907fbc56.jpg', 'di marso', 0, '2021-06-06 18:55:51'),
(7, '696969', 'hamzah', 'e10adc3949ba59abbe56e057f20f883e', 'hamzah', 'hamzah@umn.ac.id', 'upload/0802657ad963ba821afad9a7ed45c655.jpg', 'Sragen', 0, '2021-06-06 18:55:51'),
(9, '123', 'aaa', 'e10adc3949ba59abbe56e057f20f883e', 'test', 'aaa@gmail.com', 'upload/a0d684edb4026007a9ed713abb012801.jpg', 'bali\n', 0, '2021-06-06 18:55:51');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
