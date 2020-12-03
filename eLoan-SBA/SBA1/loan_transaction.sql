-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 08, 2020 at 10:32 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `loan_transaction`
--

-- --------------------------------------------------------

--
-- Table structure for table `approved_loan`
--

CREATE TABLE `approved_loan` (
  `applno` int(11) NOT NULL,
  `amotsanctioned` float DEFAULT NULL,
  `loanterm` int(2) DEFAULT NULL,
  `psd` date DEFAULT NULL,
  `lcd` date DEFAULT NULL,
  `emi` float NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `approved_loan`
--

INSERT INTO `approved_loan` (`applno`, `amotsanctioned`, `loanterm`, `psd`, `lcd`, `emi`) VALUES
(1, 1000, 2, '2020-11-12', '2021-01-12', 500),
(3, 0, 0, '2020-11-08', '2020-11-08', 0);

-- --------------------------------------------------------

--
-- Table structure for table `loan_info`
--

CREATE TABLE `loan_info` (
  `applno` int(11) NOT NULL,
  `purpose` varchar(50) DEFAULT NULL,
  `amtrequest` float DEFAULT NULL,
  `doa` date DEFAULT NULL,
  `bstructure` varchar(15) DEFAULT NULL,
  `bindicator` varchar(15) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `mobile` varchar(10) DEFAULT NULL,
  `status` varchar(15) DEFAULT NULL,
  `taxpayer` varchar(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `loan_info`
--

INSERT INTO `loan_info` (`applno`, `purpose`, `amtrequest`, `doa`, `bstructure`, `bindicator`, `address`, `email`, `mobile`, `status`, `taxpayer`) VALUES
(1, 'Purchase new property', 10000, '2020-11-08', 'individual', 'salary', 'A-101, XYZ society, 50034', 'abc@gmail.com', '8123456789', 'Processed', 'Y'),
(2, 'Build house', 5000, '2020-11-08', 'individual', 'salary', 'a-101,202,303', 'abc@mail112', '12355', 'Rejected', 'N'),
(3, 'Purchase new property', 5000, '2020-11-08', 'individual', 'salary', 'a-101,202,303', 'abc@mail112', '123456789', 'Approved', 'Y'),
(4, 'Get land', 25000, '2020-11-08', 'Organization', 'Business', 'XYZ, 500291', 'org123@mail.com', '9012345678', 'Initiated', 'N');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `username` varchar(15) NOT NULL,
  `password` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `password`) VALUES
('a1', 'a1'),
('admin', 'admin'),
('user1', 'user1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `approved_loan`
--
ALTER TABLE `approved_loan`
  ADD PRIMARY KEY (`applno`);

--
-- Indexes for table `loan_info`
--
ALTER TABLE `loan_info`
  ADD PRIMARY KEY (`applno`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `loan_info`
--
ALTER TABLE `loan_info`
  MODIFY `applno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
