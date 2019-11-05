-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 04, 2019 at 06:55 AM
-- Server version: 5.7.27-0ubuntu0.18.04.1
-- PHP Version: 7.2.24-0ubuntu0.18.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `status`
--

-- --------------------------------------------------------

--
-- Table structure for table `admingcm`
--

CREATE TABLE `admingcm` (
  `sn` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `fcmid` varchar(300) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admingcm`
--

INSERT INTO `admingcm` (`sn`, `name`, `fcmid`, `status`) VALUES
(1, 'Salman', 'dDCt7Mj1sVQ:APA91bFsQYGncRvfYPXayoGJ6of_DuOukzbm1PSrCcmOuurw2bXLwxvRbedQPAJdxvRO9afIJakhyxrXHO1fnzvCI3QJRv8XbyiKnd0a7aHQJrd5B1jMkPnjOqmCcHS03tSqwexr4XtT', 1);

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `sn` bigint(20) NOT NULL,
  `postid` varchar(100) NOT NULL,
  `userid` varchar(50) NOT NULL,
  `comment` varchar(2000) CHARACTER SET utf8mb4 NOT NULL,
  `postdate` varchar(50) NOT NULL,
  `replay` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`sn`, `postid`, `userid`, `comment`, `postdate`, `replay`, `status`) VALUES
(6, '6', '1', 'YnNic2JubnNuZA==', '03-11-2019 08:13:21 pm', 0, 1),
(8, '6', '3', 'Z2dnZw==', '03-11-2019 08:14:25 pm', 0, 0),
(9, '6', '3', 'IGhq', '03-11-2019 08:15:35 pm', 0, 1),
(10, '6', '1', 'YmhkdHV1aA==', '03-11-2019 08:17:06 pm', 0, 1),
(11, '6', '1', 'IHZiaGho', '03-11-2019 08:24:56 pm', 0, 1),
(13, '6', '3', 'Zmhq', '03-11-2019 08:27:11 pm', 0, 1),
(14, '6', '3', 'Y2Nj', '03-11-2019 08:27:21 pm', 0, 1),
(15, '6', '3', 'Z2g=', '03-11-2019 08:30:43 pm', 0, 1),
(16, '6', '1', 'dmJiaGg=', '03-11-2019 08:31:01 pm', 0, 1),
(17, '6', '3', 'Z2g=', '03-11-2019 08:32:33 pm', 11, 1),
(18, '6', '3', 'ZGZnZ2g=', '03-11-2019 08:40:25 pm', 0, 1),
(21, '13', '36126', '8J+YmPCfmJjwn5iY8J+YmOC0pOC0vuC0meC1jeC0leC1jeC0uOC1jSDgtK7gtYHgtKTgtY3gtKTg\ntYfinaTvuI8=', '04-11-2019 01:19:12 am', 0, 1),
(22, '13', '25714', '8J+OgvCfjoLwn46C', '04-11-2019 03:06:44 am', 0, 1),
(23, '13', '12', 'aGJk8J+Ogg==', '04-11-2019 06:29:47 am', 0, 1),
(24, '16', '12', '8J+YjvCfmI4=', '04-11-2019 06:48:38 am', 0, 1),
(25, '16', '14', '8J+YgvCfmILwn5iC', '04-11-2019 06:49:42 am', 0, 1),
(26, '16', '13', '8J+Yq/CfmKvwn5ir', '04-11-2019 06:50:42 am', 0, 1),
(27, '17', '12', 'VURGIHZhcnVtIGVsbGFtIHNoZXJpeWFrdW3wn5iO', '04-11-2019 06:52:21 am', 0, 1),
(28, '17', '13', '4LSF4LSk4LS+4LSj4LWNIOC0juC0suC1jeC0suC0vuC0giDgtLbgtYbgtLDgtL/gtK/gtL7gtJXg\ntYHgtIIuIOC0heC0suC1jeC0suC1h+C1vSDgtLbgtYbgtLDgtL/gtK/gtL7gtJXgtY3gtJXgtYHg\ntIIg8J+YivCfmIrwn5GN', '04-11-2019 06:52:52 am', 0, 1),
(29, '17', '14', '8J+YhvCfmIbwn5iG8J+YhvCfmIXwn5iC', '04-11-2019 06:53:04 am', 0, 1),
(30, '17', '12', '8J+YgvCfmILwn5iC', '04-11-2019 06:53:19 am', 0, 1),
(31, '18', '13', 'aSBkbyBubw==', '04-11-2019 06:55:59 am', 0, 1),
(32, '18', '14', '8J+Ygg==', '04-11-2019 06:56:12 am', 0, 1),
(33, '18', '13', 'c2hpYnUg4LSk4LS+4LS04LWGIOC0ieC0s+C1jeC0syBhYWx1a2FydWRlIOC0quC1i+C0uOC1jeC0\nseC1jeC0seC1jSDgtI7gtKTgtY3gtLAg4LSy4LWI4LSV4LWN4oCMIOC0leC0vuC0o+C0v+C0leC1\njeC0leC1geC0qOC1jeC0qOC1geC0o+C1jeC0n+C1jSBub2trLi4g', '04-11-2019 06:57:05 am', 0, 1),
(34, '18', '13', '4LSO4LSo4LWN4LSo4LS/4LSf4LWN4LSf4LWNIOC0suC1iOC0leC1jeKAjCDgtIXgtJ/gtL/gtJrg\ntY3gtJrgtLXgtbwgbm9raw==', '04-11-2019 06:57:24 am', 0, 1),
(35, '18', '13', 'NeC0suC1iOC0leC1jSBvbGxha2lsIDEw4LSy4LWI4LSV4LWNIOC0leC0vuC0o+C0v+C0leC1jeC0\nleC1geC0qOC1jeC0qOC1gSA=', '04-11-2019 06:57:54 am', 0, 1),
(36, '18', '14', 'ZW50aGlsLi4zLjUg4LSS4LSV4LWN4LSV4LWGIOC0leC0vuC0o+C0v+C0leC1jeC0leC1geC0qOC1\njeC0qOC1gSDgtJLgtLPgtY3gtLPgtYE=', '04-11-2019 07:02:20 am', 0, 1),
(37, '25', '12', '8J+YgvCfmILwn5iC8J+YgvCfmII=', '04-11-2019 07:39:02 am', 0, 1),
(38, '25', '13', '4LSO4LSo4LWN4LSk4LS+4LSj4LWNIOC0leC0vuC0sOC0o+C0giA=', '04-11-2019 07:41:41 am', 0, 1),
(39, '25', '12', 'VHJpcHAgYWF2YW5lbnUgNG5lIDEwIGFheWkgdGhvbm51bSAxMG5lIDE1IGFheWkgdGhvbm51bQ==', '04-11-2019 07:43:13 am', 0, 1),
(40, '13', '17', 'VGh1bmphbnRlICB0aGF0aGVlZWU=', '04-11-2019 07:45:43 am', 0, 1),
(41, '25', '13', '8J+YgfCfmIHwn5iB8J+YgQ==', '04-11-2019 07:45:45 am', 0, 1),
(42, '26', '12', 'Z29vZCBtb3JuaW5n', '04-11-2019 07:51:37 am', 0, 1),
(43, '26', '19', 'bW5nIGppbm4=', '04-11-2019 07:52:06 am', 0, 1),
(44, '25', '12', '8J+YjiDwn5qs', '04-11-2019 07:52:31 am', 0, 1),
(45, '16', '19', 'ZmFsYXN0aGVlbiBhdHRhYWNrIG9ra2UgbmFkYW5udSBpdGhpbmU=', '04-11-2019 07:52:40 am', 0, 1),
(46, '26', '12', '8J+Yig==', '04-11-2019 07:52:46 am', 0, 1),
(47, '17', '19', '8J+YgvCfmILwn5iC', '04-11-2019 07:55:45 am', 0, 1),
(48, '16', '13', '4LSH4LSo4LWN4LSk4LWN4LSv4LSv4LS/4LW9IOC0kuC0sOC1gSBhcHAg4LSo4LWB4LSCIOC0leC1\ngeC0tOC0quC1jeC0quC0ruC0v+C0suC1jeC0si4uIOC0quC0v+C0qOC1jeC0qOC1hiDgtI7gtJng\ntY3gtJngtKjgtYYg4LSH4LSk4LS/4LSo4LWNIOC0ruC0vuC0pOC1jeC0sOC0giDwn5iB8J+YgfCf\nmIE=', '04-11-2019 07:58:47 am', 0, 1),
(49, '32', '12', 'eWVz', '04-11-2019 08:49:48 am', 0, 1),
(50, '32', '19', 'eWVzIGVudGV5aWx1bSBhbmdhbm5l', '04-11-2019 08:50:22 am', 0, 1),
(51, '32', '13', '4LSO4LSo4LS/4LSV4LWN4LSV4LWNIOC0heC0seC0v+C0r+C0v+C0suC1jeC0si4uIOC0heC0quC1\njeC0quC1iiDgtKjgtL/gtJngtY3gtJngtb7gtJXgtY3gtJXgtYHgtIIg4LSV4LWB4LS04LSq4LWN\n4LSq4LSCIOC0h+C0o+C1jeC0n+C1jSDgtIXgtLLgtY3gtLLgtYYg', '04-11-2019 08:51:44 am', 0, 1),
(52, '32', '12', 'eWVz', '04-11-2019 08:52:05 am', 0, 1),
(53, '32', '13', 'ZW50aG8g4LSV4LWB4LS04LSq4LWN4LSq4LSCIGluZGFsbG8g4LSu4LWB4LSk4LWN4LSk4LWB4LSu\n4LSj4LS/4LSV4LSz4LWGIA==', '04-11-2019 08:54:09 am', 0, 1),
(54, '32', '12', 'ZXJla3VyZQ==', '04-11-2019 08:54:25 am', 0, 1),
(55, '32', '13', 'bW0=', '04-11-2019 08:54:42 am', 0, 1),
(56, '32', '12', 'cGF6aGUgYWFsa2thcmEgYWFyZW0ga2FudW5uaWxsYQ==', '04-11-2019 08:55:15 am', 0, 1),
(57, '32', '19', 'cHJibG0gb25udWxsYSBhaCBub3R0aSB2YXJ1bm5hdGggb2trZSBwYXpoYXlhIHBvc3QgYWFuIHRv\nIGl2aWRlIGlwbyBhYXJ1bSBldHRoaXlpbGEgYWFya3VtIGFyaW5naXQgaWxsYSBvayBhYXlhdGgg\nYXBw', '04-11-2019 08:56:23 am', 0, 1),
(58, '32', '13', '4LSF4LSk4LS/4LSo4LWNIOC0qOC0vuC0muC1jeC0muC1gSDgtKrgtLTgtK8g4LSS4LSw4LS+4LSz\n4LWGIOC0quC1i+C0uOC1jeC0seC1jeC0seC1geC0giDgtIfgtKrgtY3gtKrgtYog4LSH4LSy4LWN\n4LSy4LSy4LWN4LSy4LWLIOC0quC0v+C0qOC1jeC0qOC1hiDgtI7gtJngtY3gtJngtKjgtYYg4LSo\n4LWL4LSf4LWN4LSf4LS/IOC0teC0sOC1geC0giA=', '04-11-2019 08:57:39 am', 0, 1),
(59, '32', '13', '4LSo4LS+4LSa4LWN4LSa4LWBIOC0qOC0v+C0qOC0leC1jeC0leC1jSDgtKjgtL/gtKjgtY3gtLHg\ntYYg4LSq4LS04LSvIOC0quC1i+C0uOC1jeC0seC1jeC0seC1jSDgtIfgtKPgtY3gtJ/gtYsgbm9r\nayA=', '04-11-2019 08:57:57 am', 0, 1),
(60, '32', '12', 'ZWxsYW0gcG95aSBmYXZvcml0ZSB1c2VycyB1bQ==', '04-11-2019 08:58:48 am', 0, 1),
(61, '32', '13', '4LSq4LS/4LSo4LWN4LSo4LWGIOC0juC0meC1jeC0meC0qOC1hiDgtKjgtYvgtJ/gtY3gtJ/gtL8g\n4LS14LSw4LWB4LSo4LWN4LSo4LSk4LWNIA==', '04-11-2019 08:59:19 am', 0, 1),
(62, '32', '19', 'aWxsYSBwb3N0IGlsbGE=', '04-11-2019 08:59:42 am', 0, 1),
(63, '32', '19', 'c2hhcmlra3VtIG9rIGFheWl0YSBpdGg=', '04-11-2019 08:59:58 am', 0, 1),
(64, '32', '19', 'dGltZSBlZHVra3VtIGVubiBwYXJhbmd1IHNhbG1hYW4=', '04-11-2019 09:00:10 am', 0, 1),
(65, '32', '13', 'ZGFhIOC0juC0qOC1jeC0seC1hiDgtIgg4LSq4LWL4LS44LWN4LSx4LWN4LSx4LS/4LSo4LWNIOC0\nsuC1iOC0leC1jeKAjCDgtIbgtLDgtYbgtJXgtY3gtJXgtYYg4LSF4LSf4LS/4LSV4LWN4LSV4LWB\n4LSo4LWN4LSo4LWB4LSj4LWN4LSf4LWNLiBidXQg4LSy4LWI4LSV4LWN4oCMIOC0leC0vuC0o+C0\nv+C0leC1jeC0leC1geC0qOC1jeC0qOC0v+C0suC1jeC0siA=', '04-11-2019 09:00:29 am', 0, 1),
(66, '32', '19', 'YXRoIGVudGV5aW51bSB1bmQ=', '04-11-2019 09:01:48 am', 0, 1),
(67, '32', '19', 'YXRoYW5uZSBwYXJhbmdlIHNoYXJpa3VtIHJlYWR5IGFheWl0YSBlbm4gYXBwIHNoYXJpIGFhdnVt\nIHdhaXQgYWFr', '04-11-2019 09:02:20 am', 0, 1),
(68, '32', '13', 'bW0g', '04-11-2019 09:02:23 am', 0, 1),
(69, '32', '12', '8J+Yr/CfmI4=', '04-11-2019 09:02:46 am', 0, 1),
(70, '38', '19', 'Z3VkIG1uZw==', '04-11-2019 09:02:51 am', 0, 1),
(71, '32', '19', 'bmkgdXBhZGF0ZSBhYWtpbm8=', '04-11-2019 09:03:20 am', 0, 1),
(72, '32', '12', 'YWFra2k=', '04-11-2019 09:03:33 am', 0, 1),
(73, '32', '13', '4LSq4LS/4LSo4LWN4LSo4LWGIOC0juC0meC1jeC0meC0qOC1hiDgtIfgtKPgtY3gtJ/gtY0g4LSc\n4LS/4LSo4LWN4LSo4LWGIHNoZXJpeWF5bw==', '04-11-2019 09:03:58 am', 0, 1),
(74, '38', '20', 'Z3VkIG1ybmcgbmFjaHU=', '04-11-2019 09:04:01 am', 0, 1),
(75, '32', '12', 'ZXl5IG5lcmF0aGUgcG9sZSB0aGFubmUgMm5lIDUgYWF5aSBrYW51bm51', '04-11-2019 09:05:18 am', 0, 1),
(76, '36', '8', '4LS24LWB4LSt4LSm4LS/4LSo4LSCIOC0puC0v+C0suC1jeC0suC1geC0uOC1jSDwn5iN8J+YjQ==', '04-11-2019 09:06:46 am', 0, 1),
(77, '32', '19', '8J+YgvCfmILwn5iC', '04-11-2019 09:07:49 am', 0, 1),
(78, '38', '19', 'bnRoZWxsYSBuaW50ZXl5aWwgcHJibG0gdW5kbw==', '04-11-2019 09:08:19 am', 0, 1),
(79, '34', '19', 'Z3VkIG1uZw==', '04-11-2019 09:08:34 am', 1, 1),
(80, '32', '19', 'Y29udGFhY3QgdWxsYSBmcmllbmRzaW5vZCBva2tlIHZhcmFhbiBwYXJh', '04-11-2019 09:09:08 am', 0, 1),
(81, '32', '13', '8J+YgfCfmIHwn5iB', '04-11-2019 09:13:28 am', 0, 1),
(82, '38', '13', '4LSu4LWL4LW84LSj4LS/4LSC4LSX4LWNIA==', '04-11-2019 09:16:12 am', 0, 1),
(83, '38', '36914', '4LSq4LWL4LS44LWN4LSx4LWN4LSx4LWN4oCMIOC0h+C0n+C0vuC1uyDgtKrgtLHgtY3gtLHgtYHg\ntKjgtY3gtKjgtL/gtLLgtY3gtLIgCuC0heC0quC1jeC0quC1iyDgtK7gtYHgtJXgtY3gtJXgtYHg\ntKjgtY3gtKjgtYEg8J+Ygg==', '04-11-2019 09:17:20 am', 0, 1),
(84, '38', '19', 'aXRoaWwgaXBwbyBvcnUgY21udCBub3R0aSB2YW5udSBidXQgZXZpZGUga2FhbnVubmlsbGHwn5iC', '04-11-2019 09:20:54 am', 0, 1),
(85, '38', '13', 'eXM=', '04-11-2019 09:21:53 am', 0, 1),
(86, '38', '19', 'bmludGV5aWwgdmFubmlubw==', '04-11-2019 09:22:10 am', 0, 1),
(87, '38', '13', '4LSO4LSo4LWN4LSx4LWG4LSv4LWB4LSCIOC0leC0vuC0o+C1geC0qOC1jeC0qOC0v+C0suC1jeC0\nsiDgtJ7gtL7gtbsg4LSV4LSj4LWN4LSf4LWBLi4uIOC0ruC0v+C0o+C1jeC0n+C0vuC0pOC1hiDg\ntKjgtL/gtKjgtY3gtKjgtKTgtL7gtKPgtY0g', '04-11-2019 09:22:19 am', 0, 1),
(88, '38', '13', 'bW0g4LS14LSo4LWN4LSo4LWNIA==', '04-11-2019 09:22:31 am', 0, 1),
(89, '38', '13', '4LSo4LWG4LSv4LS/4LSCIHJpamkg4LSF4LSZ4LWN4LSZ4LSo4LWGIGVudGhvIGFsbGU=', '04-11-2019 09:22:56 am', 0, 1),
(90, '38', '19', 'eWVz', '04-11-2019 09:24:11 am', 0, 1),
(91, '38', '13', 'bW0=', '04-11-2019 09:24:46 am', 0, 1),
(92, '32', '36914', 'a29vaQ==', '04-11-2019 09:26:00 am', 0, 1),
(93, '32', '19', 'aXZhbCBwcmV0aGFtIGFhbm8gaXRoaWx1bSB2YW5udfCfmILwn5iC', '04-11-2019 09:27:02 am', 0, 1),
(94, '32', '13', 'YWFh', '04-11-2019 09:27:11 am', 0, 1),
(95, '32', '13', '4LSH4LSk4LWNIOC0juC0qOC1jeC0pOC1jSBvbGtvcHBsZSBwYXJpcGFkaXlhbg==', '04-11-2019 09:27:34 am', 0, 1),
(96, '32', '36914', '8J+YgvCfmILwn5iC', '04-11-2019 09:34:25 am', 0, 1),
(100, '38', '36', '4LSu4LWL4LW84LSj4LS/4LSC4LSX4LWNIA==', '04-11-2019 09:41:44 am', 0, 0),
(105, '49', '12', 'Z29vZCBtb3JuaW5n', '04-11-2019 09:45:51 am', 0, 1),
(107, '50', '19', 'c2FtYWFkaGFhbmFtIGFheWlsbGUgbmluYWtr8J+Ygg==', '04-11-2019 09:48:20 am', 4, 1),
(108, '50', '12', 'Z29vZCBtb3JuaW5n', '04-11-2019 09:48:47 am', 6, 1),
(115, '54', '12', '8J+YjvCflbrinIzvuI8=', '04-11-2019 09:54:57 am', 0, 1),
(116, '49', '8', '4LS24LWB4LSt4LSm4LS/4LSo4LSCIA==', '04-11-2019 09:55:38 am', 0, 1),
(117, '18', '40', '4LSa4LS/4LSo4LWN4LSk4LSv4LS/4LSy4LWGIOC0quC1jeC0sOC0s+C0r+C0pOC1jeC0pOC0v+C1\nvSDgtJLgtLLgtL/gtJrgtY3gtJrgtYEg4LSq4LWL4LSv4LS/IA==', '04-11-2019 09:59:31 am', 0, 1),
(118, '18', '14', '8J+Ygg==', '04-11-2019 10:02:15 am', 0, 1),
(119, '56', '14', '8J+YgvCfmILwn5iCYXlpbnU=', '04-11-2019 10:04:25 am', 0, 1),
(120, '52', '47', 'Z2QgbW5nIGppbm4gYnJv8J+YjQ==', '04-11-2019 10:12:00 am', 18, 1),
(122, '56', '19', '8J+YgvCfmILwn5iC', '04-11-2019 10:22:42 am', 0, 1),
(123, '56', '12', 'ZWxsYW0gcG95aWxsZQ==', '04-11-2019 10:24:38 am', 0, 1),
(128, '49', '40', '4LS24LWB4LStIOC0oeC1hyA=', '04-11-2019 10:33:44 am', 0, 1),
(129, '56', '36914', 'Y29tbWVudA==', '04-11-2019 10:43:10 am', 0, 1),
(130, '69', '12', 'YWE=', '04-11-2019 10:45:16 am', 1, 1),
(131, '69', '50', '8J+YlQ==', '04-11-2019 10:45:41 am', 0, 1),
(132, '69', '36914', 'Y29tbWVudA==', '04-11-2019 10:50:07 am', 0, 1),
(133, '54', '36914', 'aGFh', '04-11-2019 10:54:19 am', 0, 1),
(134, '62', '19', 'YWp18J+YjQ==', '04-11-2019 11:03:00 am', 0, 1),
(135, '56', '19', 'cG95aSBkYQ==', '04-11-2019 11:03:18 am', 0, 1),
(136, '56', '19', 'bW9kaGkga29uZCBwb3lpIGVsbGFhbQ==', '04-11-2019 11:03:25 am', 0, 1),
(137, '73', '13', '4LS44LSC4LSt4LS14LSCIOC0leC0s+C0seC0vuC0r+C0vyDwn6Sp8J+kqfCfpKk=', '04-11-2019 11:07:02 am', 0, 1),
(138, '74', '36841', '8J+Yo/CfmKM=', '04-11-2019 11:07:12 am', 0, 1),
(139, '74', '12', 'MSBraXR0aXlhIDQgc291amFueWFt8J+YjuKcjO+4jw==', '04-11-2019 11:07:45 am', 0, 1),
(140, '73', '13', '4LSo4LS+4LSa4LWN4LSa4LWBIOC0h+C0quC1jeC0quC1iy4uLiDgtI7gtKjgtL/gtJXgtY3gtJXg\ntY0g4LSS4LSw4LWBIOC0leC0ruC0qOC1jeC0seC1jSDgtLXgtKjgtY3gtKjgtYEuLi4gYnV0IOC0\nleC0vuC0o+C1geC0qOC1jeC0qOC0v+C0suC1jeC0siDwn5ir', '04-11-2019 11:08:21 am', 0, 1),
(141, '73', '19', 'ZW5pa2t1bSB2YW5udfCfmILwn5iC8J+Ygg==', '04-11-2019 11:08:43 am', 0, 1),
(142, '74', '13', 'ZGFhIOC0h+C0pOC0v+C1vSDgtLXgtYfgtLHgtYYg4LSS4LSw4LWBIOC0leC0ruC0qOC1jeC0seC1\njSDgtLXgtKjgtY3gtKjgtYEuLi4gYnV0IOC0leC0vuC0o+C1geC0qOC1jeC0qOC0v+C0suC1jeC0\nsiDwn5iB', '04-11-2019 11:09:02 am', 0, 1),
(143, '73', '13', '8J+kqfCfpKnwn6Sp', '04-11-2019 11:09:23 am', 0, 1),
(144, '74', '57', '8J+YkPCfmJA=', '04-11-2019 11:11:39 am', 0, 1),
(145, '74', '57', '4LSH4LSq4LWN4LSq4LWLIOC0leC0vuC0qOC1geC0o+C1jeC0n+C1iyDwn5ij', '04-11-2019 11:11:50 am', 0, 1),
(146, '73', '19', 'eWFrc2hpIGFhdmFhbmEgc2FhZGh5YXRoYQ==', '04-11-2019 11:12:12 am', 0, 1),
(147, '74', '57', '4LSo4LWN4LSx4LWGIOC0leC0ruC0qOC1jeC0seC1jSDgtIbgtK/gtL/gtLDgtYHgtKjgtY3gtKjg\ntYEg4LSk4LS+4LSo4LWHIGRsdCBhYXZ1bm518J+YkA==', '04-11-2019 11:12:15 am', 0, 1),
(148, '74', '13', 'bW0g4LSH4LSq4LWN4LSq4LWKIG9rIA==', '04-11-2019 11:12:45 am', 0, 1),
(149, '74', '13', '4LSH4LSk4LWNIOC0huC0sOC0viDgtIbgtb4uLi4g4LSo4LWG4LSv4LS/4LSCIOC0kuC0qOC1jeC0\nqOC1geC0ruC0v+C0suC1jeC0suC0suC1jeC0suC1iyA=', '04-11-2019 11:13:02 am', 0, 1),
(150, '74', '57', '8J+YhvCfmIY=', '04-11-2019 11:13:03 am', 0, 1),
(151, '74', '57', '4LSG4LSz4LWGIOC0quC0seC0nuC1jeC0nuC0vuC1vSDgtIXgtLHgtYDgtLIg8J+YkA==', '04-11-2019 11:13:20 am', 7, 1),
(152, '73', '13', '8J+YgfCfmIHwn5iB4LSF4LSk4LS+4LS14LWB4LSCIA==', '04-11-2019 11:13:29 am', 0, 1),
(153, '74', '13', '4LSO4LSo4LWN4LSo4LS+4LSy4LWB4LSCIOC0kuC0qOC1jeC0qOC1jSBwYXJh', '04-11-2019 11:13:47 am', 0, 1),
(154, '74', '12', 'Y2hpbGEgY21udCBrYW51bm5pbGxhIG5vdGkgdmFubmF0dHVt', '04-11-2019 11:13:48 am', 0, 1),
(155, '74', '13', 'bW0g', '04-11-2019 11:14:03 am', 0, 1),
(156, '74', '36', '8J+YgvCfmILwn5iC', '04-11-2019 11:15:29 am', 0, 0),
(157, '13', '58', '4LS54LS+4LSq4LWN4LSq4LS/IOC0rOC1vOC0pOC1jeC0oeC1h+C0r+C1jSDgtLfgtL7gtLDgtYLg\ntJbgtY0g', '04-11-2019 11:16:06 am', 0, 1),
(158, '74', '13', '4LSH4LSc4LWN4LSc4LWNIOC0teC0qOC1jeC0qOC1iyDwn5iB8J+YgeC0heC0qOC0leC1jSDgtI7g\ntKjgtY3gtKTgtYbgtJXgtL/gtLLgtYHgtIIg4LSV4LWB4LS04LSq4LWN4LSq4LSCIOC0pOC1i+C0\nqOC1jeC0qOC1geC0o+C1jeC0n+C1iyA=', '04-11-2019 11:16:18 am', 0, 1),
(159, '73', '19', '8J+YgvCfmII=', '04-11-2019 11:16:53 am', 0, 1),
(160, '74', '36', '4LSq4LS04LSvIOC0quC1i+C0suC1hiDgtIfgtKPgtY3gtJ/gtY0g', '04-11-2019 11:16:54 am', 0, 0),
(161, '74', '13', '4LSV4LSu4LSo4LWN4LSx4LWNIOC0juC0suC1jeC0suC0vuC0giB2YXJ1bm51bmRv', '04-11-2019 11:17:41 am', 0, 1),
(162, '74', '57', '4LSH4LSu4LWL4LSc4LS/IOC0kuC0leC1jeC0leC1hiDgtI7gtKjgtY3gtKTgtL7gtKPgtY0g4LSH\n4LSZ4LWN4LSZ4LSo4LWGIPCfpK7wn6Su', '04-11-2019 11:17:47 am', 1, 1),
(163, '74', '58', '8J+YhPCfmIQ=', '04-11-2019 11:18:02 am', 0, 1),
(164, '74', '13', '4LSV4LSu4LSo4LWN4LSx4LWNIOC0teC0sOC1geC0qOC1jeC0qOC1geC0o+C1jeC0n+C1iyDgtK7g\ntYjgtKgg4LSo4LS/4LSo4LSV4LWN4LSV4LWNIA==', '04-11-2019 11:18:34 am', 0, 1),
(165, '74', '58', '4LSJ4LSj4LWN4LSf4LWNIA==', '04-11-2019 11:19:21 am', 0, 1),
(166, '74', '13', 'MuC0suC1iOC0leC1jSDgtJXgtL/gtJ/gtY3gtJ/gtL/gtK/gtL7gtb0g4LSF4LSk4LWNIOC0heC0\nnuC1jeC0muC0vuC0r+C0vyDgtJXgtL7gtKPgtL/gtJXgtY3gtJXgtYHgtKjgtY3gtKjgtYEg', '04-11-2019 11:19:30 am', 0, 1),
(167, '74', '13', '4LSy4LWI4LSV4LWN4oCMIOC0juC0meC1jeC0meC0qOC1huC0r+C0vuC0o+C1jSA=', '04-11-2019 11:19:48 am', 0, 1),
(168, '74', '13', '4LSq4LS04LSvIOC0quC1i+C0uOC1jeC0seC1jeC0seC1jeKAjCDgtIfgtKPgtY3gtJ/gtYsg', '04-11-2019 11:20:01 am', 0, 1),
(169, '74', '58', '4LSq4LS04LSvIOC0quC1i+C0uOC1jeC0seC1jeC0seC1jeKAjCDgtIfgtLLgtY3gtLIg', '04-11-2019 11:20:15 am', 0, 1),
(170, '74', '12', 'YmFneWFt', '04-11-2019 11:20:25 am', 0, 1),
(171, '74', '13', 'bW0g', '04-11-2019 11:20:32 am', 0, 1),
(172, '74', '58', 'TGlra2FuIOC0nuC0vuC1uyDgtKrgtYvgtLjgtY3gtLHgtY3gtLHgtY3igIwg4LSH4LSf4LWN4LSf\n4LS/4LSy4LWN4LSyIPCfmIQ=', '04-11-2019 11:20:32 am', 0, 1),
(173, '74', '36', '8J+YgvCfmILwn5iC4LSs4LWCIOC0uSDgtLkg4LS5IOC0ueC0viA=', '04-11-2019 11:20:43 am', 0, 0),
(174, '74', '58', '4LSq4LWL4LSf4LWAIOC0pOC1huC0o+C1jeC0n+C0vyA=', '04-11-2019 11:21:07 am', 0, 1),
(175, '74', '13', '4LSS4LSw4LWBIOC0quC1i+C0uOC1jeC0seC1jeC0seC1jeKAjCDgtIfgtJ/gtY3gtJ/gtY0gbm9r\nayDgtIXgtKrgtY3gtKrgtYog4LSF4LSx4LS/4LSv4LS+4LSCLi4g4LSV4LSu4LSo4LWN4LSx4LWN\nIOC0teC0sOC1geC0qOC1jeC0qOC1geC0o+C1jeC0n+C1iyBpbGxheW8g4LSO4LSo4LWN4LSo4LWN\nLi4g', '04-11-2019 11:21:09 am', 0, 1),
(176, '74', '57', '8J+krvCfpK7wn6Su', '04-11-2019 11:21:28 am', 0, 1),
(177, '74', '13', '4LSH4LS14LS/4LSf4LWGIOC0r+C0leC1jeC0t+C0vyDgtLXgtKjgtY3gtKjgtL/gtJ/gtY3gtJ/g\ntY0gaW5k', '04-11-2019 11:21:34 am', 0, 1),
(178, '74', '13', '4LSH4LSk4LWNIOC0huC0sOC0viDgtIgg4LSo4LWG4LSv4LS/4LSCIOC0h+C0suC1jeC0suC0vuC0\npOC1jeC0pOC0teC1uyA=', '04-11-2019 11:21:53 am', 0, 1),
(179, '78', '57', '4LSq4LWL4LSv4LWN4oCMIOC0pOC1guC0meC1jeC0meC0vyDgtJrgtL7gtLXgtY3igIwg8J+krg==', '04-11-2019 11:22:13 am', 0, 1),
(180, '78', '13', '4LSO4LSo4LWN4LSk4LS+4LSj4LWNIOC0h+C0qOC0vyBjaGV5eWEuLi4g4LSV4LSw4LSe4LWN4LSe\n4LWBIHRoZWVya2FubyDwn5iB', '04-11-2019 11:22:41 am', 0, 1),
(181, '74', '12', '8J+ZhPCfmYTwn5mE', '04-11-2019 11:22:49 am', 0, 1),
(182, '76', '36914', 'aGFhYQ==', '04-11-2019 11:23:00 am', 0, 1),
(183, '79', '13', '4LSH4LSq4LWN4LSq4LWKIOC0qOC0v+C0qOC0leC1jeC0leC1jSDgtI7gtKTgtY3gtLAg4LSy4LWI\n4LSV4LWNIGltZA==', '04-11-2019 11:23:14 am', 0, 1),
(184, '79', '13', 'aW5k', '04-11-2019 11:23:20 am', 0, 1),
(185, '74', '13', '8J+YgfCfmIE=', '04-11-2019 11:23:34 am', 0, 1),
(186, '74', '36', '4LS14LW7IOC0heC0suC1jeC0siDgtIXgtKTgtY0g4LS14LW+IOC0huC0o+C1jSA=', '04-11-2019 11:23:35 am', 0, 0),
(187, '78', '14', '8J+YgvCfmILwn5iC', '04-11-2019 11:23:41 am', 0, 1),
(188, '78', '36', '8J+YgvCfmILwn5iC', '04-11-2019 11:23:52 am', 0, 0),
(189, '74', '13', '8J+ZhPCfmYTwn5mE', '04-11-2019 11:23:58 am', 0, 1),
(190, '74', '13', '4LSO4LSo4LWN4LSo4LS+4LW9IOC0qOC1huC0r+C0v+C0giDgtLXgtYbgtJrgtY3gtJrgtYLgtJ/g\ntYYg', '04-11-2019 11:24:11 am', 0, 1),
(191, '78', '14', 'ZWxsYXZ1ZGV5dW0g4LSq4LWL4LS44LWN4LSx4LWN4LSx4LS/4LW9IOC0quC1i+C0r+C0vy4u4LSH\n4LSo4LS/IOC0h+C0pOC1gSDgtKrgtYvgtLLgtYYgZWxpa2tubfCfmILwn5iC8J+YgvCfmII=', '04-11-2019 11:24:21 am', 0, 1),
(192, '79', '58', '4LSw4LSj4LWN4LSf4LWNIA==', '04-11-2019 11:24:37 am', 0, 1),
(193, '78', '14', 'IOC0teC0n+C1jeC0n+C0pOC1jeC0pOC0vyDgtLXgtJ/gtY3gtJ/gtY0g4LSS4LSV4LWN4LSV4LWG\nIGVwcHpodW0g4LSJ4LSj4LWN4LSf4LWNIOC0suC1hvCfmII=', '04-11-2019 11:24:44 am', 0, 1),
(194, '78', '13', '4LSO4LSo4LWN4LSk4LS+4LSv4LS+4LSy4LWB4LSCIOC0q+C1iOC0leC1jSDgtIXgtJ/gtL/gtJXg\ntb4g4LSw4LSV4LWN4LS34LSq4LWG4LSf4LWN4LSf4LWBIA==', '04-11-2019 11:24:45 am', 0, 1),
(195, '79', '14', '8J+Ygg==', '04-11-2019 11:24:56 am', 0, 1),
(196, '78', '13', '4LSH4LSo4LS/IOC0juC0meC1jeC0meC0qOC1hiDgtLXgtKjgtY3gtKjgtL7gtLLgtYHgtIIg4LSF\n4LSx4LS/4LSv4LS/4LSy4LWN4LSy4LSy4LWN4LSy4LWLIA==', '04-11-2019 11:25:01 am', 0, 1),
(197, '74', '12', 'bWFuYXNpbGF5aQ==', '04-11-2019 11:25:02 am', 0, 1),
(198, '73', '36914', 'bmhhbnVtIA==', '04-11-2019 11:25:05 am', 0, 1),
(199, '78', '36', '4LSo4LWN4LSx4LWGIOC0quC1i+C0uOC1jeC0seC1jeC0seC1jeKAjCDgtI7gtLLgtY3gtLLgtYrg\ntIIg4LSq4LWL4LSv4LS/IPCfmKXwn5it8J+YrfCfmK0=', '04-11-2019 11:25:06 am', 0, 0),
(200, '79', '13', '4LSF4LSk4LWNIOC0huC0sOC1huC0leC1jeC0leC1hiDgtLLgtYjgtJXgtY3igIwg4LSG4LSj4LWN\nIA==', '04-11-2019 11:25:31 am', 0, 1),
(201, '73', '13', '4LSH4LSq4LWN4LSq4LWL4LS04LWB4LSCIOC0teC0qOC1jeC0qOC1gSDgtIXgtLLgtY3gtLLgtYYu\nLi4gYnV0IOC0leC0vuC0o+C1geC0qOC1jeC0qOC0v+C0suC1jeC0siDwn6Sp', '04-11-2019 11:26:16 am', 0, 1),
(202, '78', '13', '4LSo4LS/4LSo4LWN4LSx4LWGIOC0ruC0vuC0pOC1jeC0sOC0giDgtIXgtLLgtY3gtLIuLiDgtIXg\ntKrgtY3gtKrgtYogZW50ZXlv', '04-11-2019 11:26:41 am', 0, 1),
(203, '79', '58', '4LSo4LWAIOC0teC0n+C1jeC0n+C1gS4uIOC0quC0v+C0qOC1jeC0qOC1hiDgtLXgtYfgtLHgtYYg\nYXJv', '04-11-2019 11:27:04 am', 0, 1),
(204, '82', '58', '4LSZ4LWHIA==', '04-11-2019 11:27:39 am', 0, 1),
(205, '79', '12', '4pyM77iP8J+YjuKcjO+4jw==', '04-11-2019 11:27:52 am', 0, 1),
(206, '78', '58', '8J+ko/CfpKPwn5iC8J+YgvCfmILwn5iC8J+YgvCfmII=', '04-11-2019 11:27:57 am', 0, 1),
(207, '79', '13', 'c2hpYnUuLi4g4LSH4LSj4LWN4LSf4LWNIOC0heC0meC1jeC0meC0qOC1hiDgtK7gtYLgtKjgtY3g\ntKjgtYEg4LSy4LWI4LSV4LWNLi4gYnV0IOC0h+C0pOC0v+C1vSDgtJXgtL7gtKPgtL/gtJXgtY3g\ntJXgtYHgtKjgtY3gtKjgtKTgtY0gNOC0suC1iOC0leC1jSDgtIbgtKPgtY0uLi4g4LSH4LSo4LS/\n4LSv4LWB4LSCIOC0leC1guC0n+C1geC0giDgtKjgtL/gtKjgtY3gtLHgtYYg4LSt4LS+4LSX4LWN\n4LSv4LSCIOC0ruC1iOC0qCDwn6Sp8J+kqfCfmIE=', '04-11-2019 11:28:13 am', 0, 1),
(208, '76', '58', '4LSu4LS0IOC0quC1huC0r+C1jeC0pOC1iyA=', '04-11-2019 11:28:15 am', 1, 1),
(209, '79', '58', '8J+ko/CfpKPwn5iC8J+Ygg==', '04-11-2019 11:28:31 am', 0, 1),
(210, '79', '12', 'NSBseWsgNCBuYW1l', '04-11-2019 11:28:36 am', 0, 1),
(211, '82', '60', '4LSS4LSo4LWN4LSo4LWB4LSCIOC0juC0tOC1geC0pOC0vuC1uyDgtJXgtL/gtJ/gtY3gtJ/gtYHg\ntKjgtY3gtKjgtL/gtLLgtY3gtLIg4LSq4LWG4LSj4LWN4LSj4LWGIPCfmIDwn5iA', '04-11-2019 11:28:39 am', 0, 1),
(212, '78', '13', '4LSV4LSw4LSe4LWN4LSe4LWL4LSz4LS/IPCfmKvwn5ir8J+Yqw==', '04-11-2019 11:28:42 am', 0, 1),
(213, '79', '58', '4LSo4LWN4LSoIOC0nuC0vuC1uyDgtKrgtYvgtK/gtL8g4LSk4LWC4LSZ4LWN4LSZ4LS/IOC0muC0\nvuC0teC0n+C1jeC0n+C1hiA=', '04-11-2019 11:29:03 am', 0, 1),
(214, '82', '60', '4LSO4LSy4LWN4LSy4LS+4LSCIOC0quC1geC0pOC0v+C0r+C0pOC1jSDgtIbgtK/gtLLgtY3gtLLg\ntYsg', '04-11-2019 11:29:07 am', 0, 1),
(215, '79', '13', '4LSH4LSo4LS/4LSv4LWB4LSCIOC0leC1guC0n+C1geC0giDgtJzgtL/gtKjgtY3gtKjgtYYg8J+k\nqQ==', '04-11-2019 11:29:11 am', 0, 1),
(216, '79', '13', '4LSq4LWG4LSf4LWN4LSf4LSo4LWN4LSo4LWNIG5va2sg4LSk4LWC4LSZ4LWN4LSZ4LS+4LW7IPCf\nkY0=', '04-11-2019 11:29:25 am', 0, 1),
(217, '38', '20', 'Z3VkIG1ybmcgdmF0dHV6ICYgamFtc2hp4pyM77iP4pyM77iP', '04-11-2019 11:29:36 am', 0, 1),
(218, '82', '58', '4LSq4LSa4LWN4LSa4LWHIOC0h+C0nOC1jSDgtKrgtLTgtYfgtKTgtY0g4LSk4LSo4LWN4LSo4LWG\nIOC0heC0suC1jeC0suC1hiDwn6Sj', '04-11-2019 11:29:36 am', 0, 1),
(219, '79', '60', '8J+YjvCfmI4=', '04-11-2019 11:29:40 am', 0, 1),
(220, '79', '13', '4LSv4LS+4LS44LWA4LW7IOC0k+C0pOC0vuC1uyDgtJ7gtL7gtbsg4LS14LSw4LS+4LSCIPCfmJw=', '04-11-2019 11:29:55 am', 0, 1),
(221, '79', '58', 'QWxsYWgg4LSy4LWI4LSV4LWNIPCfmK4=', '04-11-2019 11:29:55 am', 0, 1),
(222, '78', '64', '4LSV4LS+4LSx4LWN4LSx4LS/4LW9IOC0quC0seC0qOC1jeC0qOC1jSDgtKrgtYvgtK/gtY3igIwg', '04-11-2019 11:30:28 am', 0, 1),
(223, '38', '13', '8J+YjfCfmI0=', '04-11-2019 11:30:55 am', 0, 1),
(224, '82', '60', '4LSG4LSf4LWAIA==', '04-11-2019 11:31:04 am', 0, 1),
(225, '82', '60', '4LSH4LSk4LWBIOC0juC0quC1jeC0quC0s+C0viDgtKTgtYHgtLHgtKjgtY3gtKjgtYYg', '04-11-2019 11:31:27 am', 0, 1),
(226, '82', '58', '4LSG4LS14LWLIOC0nuC0vuC1uyDgtIfgtKrgtY3gtKrgtYvgtb4ga2VyaXl1bGx1', '04-11-2019 11:32:16 am', 0, 1),
(227, '38', '58', '4LSI4LSm4LWNIOC0ruC1geC0rOC0vuC0seC0leC1jSA=', '04-11-2019 11:32:31 am', 0, 1),
(228, '34', '58', 'bXJuZw==', '04-11-2019 11:32:42 am', 1, 1),
(229, '78', '14', '8J+YgvCfmILwn5iC', '04-11-2019 11:32:47 am', 0, 1),
(230, '73', '19', 'ZWxsYWEgcG9zdGlsdW0gdmFydW5udW5k', '04-11-2019 11:32:48 am', 0, 1),
(231, '38', '13', '8J+ZhPCfmYTwn5mE4LSV4LS/4LSz4LS/IHBveW8g4LSo4LS/4LSo4LWN4LSx4LWGIA==', '04-11-2019 11:32:59 am', 0, 1),
(232, '73', '19', 'bXVuYiB1bmRhYXlpbm8gaW5nYXRoZSBwZXIgdWxsYSBhYWFs', '04-11-2019 11:33:02 am', 0, 1),
(233, '73', '13', 'bW0=', '04-11-2019 11:33:26 am', 0, 1),
(234, '77', '14', '8J+Ygg==', '04-11-2019 11:33:27 am', 0, 1),
(235, '76', '14', '8J+Ygg==', '04-11-2019 11:33:35 am', 1, 1),
(236, '82', '60', '8J+RjfCfkY0=', '04-11-2019 11:33:40 am', 0, 1),
(237, '74', '14', '8J+Ygg==', '04-11-2019 11:33:45 am', 0, 1),
(238, '38', '58', '4LSc4LSC4LS34LS/IOC0qOC0v+C0qOC1jeC0qOC1hiDgtIngtKrgtY3gtKrgtL/gtLLgtL/gtJ/g\ntY3gtJ/gtYEg4LS14LWG4LSa4LWN4LSa4LWLIA==', '04-11-2019 11:33:49 am', 0, 1),
(239, '79', '12', 'cGV0dGFubnUgY2hha28=', '04-11-2019 11:33:52 am', 0, 1),
(240, '73', '14', '8J+Ygg==', '04-11-2019 11:33:55 am', 0, 1),
(241, '70', '14', '8J+Ygg==', '04-11-2019 11:34:03 am', 0, 1),
(242, '69', '14', '8J+Ygg==', '04-11-2019 11:34:13 am', 0, 1),
(243, '66', '14', '8J+Ygg==', '04-11-2019 11:34:22 am', 0, 1),
(244, '31', '64', '4LS54LS+4LSv4LWNIOC0nOC0v+C0qOC1jeC0qOC1hiDgtJfgtYHgtKHgtY0g4LSu4LWL4LW84LSj\n4LS/4LSC4LSX4LWNIA==', '04-11-2019 11:34:25 am', 1, 1),
(245, '62', '14', '8J+Ygg==', '04-11-2019 11:34:33 am', 0, 1),
(246, '38', '13', 'ZW50aGUg4LSF4LSZ4LWN4LSZ4LSo4LWGIOC0muC1i+C0puC0v+C0muC1jeC0muC0pOC1jSA=', '04-11-2019 11:34:40 am', 0, 1),
(247, '38', '58', 'RHDwn5iE', '04-11-2019 11:34:57 am', 0, 1),
(248, '38', '20', '4LS54LS+4LSq4LWN4LSq4LS/IOC0leC1jeC0sOC0v+C0uOC1jeC0ruC0uOC1jeKcjO+4jw==', '04-11-2019 11:35:02 am', 0, 1),
(249, '66', '12', '8J+ZhPCfmYQ=', '04-11-2019 11:35:07 am', 0, 1),
(250, '25', '64', '4LS14LWL4LSv4LS/4LS44LWNIOC0ieC0o+C1jeC0n+C0suC1jeC0suC1iyDgtIXgtKTgtY0g4LSV\n4LS+4LSj4LWB4LSo4LWN4LSo4LS/4LSy4LWN4LSy4LWHIA==', '04-11-2019 11:35:17 am', 0, 1),
(251, '82', '60', '4LSH4LSc4LWN4LSc4LWNIOC0uOC1geC0luC0ruC0suC1jeC0suC1hyA=', '04-11-2019 11:35:18 am', 0, 1),
(252, '82', '58', '4LSF4LW94LS54LSC4LSm4LWB4LSy4LS/4LSy4LWN4LSy4LS+4LS54LWNLiBBdmRl', '04-11-2019 11:35:42 am', 0, 1),
(253, '25', '12', 'dm9pY2Xwn5mE8J+ZhA==', '04-11-2019 11:35:49 am', 0, 1),
(254, '38', '13', '4LSo4LS/4LSZ4LWN4LSZ4LSz4LWB4LSf4LWGIOC0heC0teC0v+C0n+C1hiDgtIfgtJngtY3gtJng\ntKjgtYYg4LSJ4LSq4LWN4LSq4LS/4LW9IOC0h+C0n+C0vuC0seC1geC0o+C1jeC0n+C1iyDwn5iB', '04-11-2019 11:36:13 am', 0, 1),
(255, '82', '60', '4LSO4LSo4LS/4LSV4LWN4LSV4LWB4LSCIA==', '04-11-2019 11:36:15 am', 0, 1),
(256, '38', '58', '4LSJ4LSj4LWN4LSf4LWNIPCfmK4=', '04-11-2019 11:36:49 am', 0, 1),
(257, '82', '60', '4LSH4LSk4LS/4LSy4LWN4LSy4LS+4LSk4LWGIOC0kuC0sOC1gSDgtLDgtLjgtK7gtL/gtLLgtY3g\ntLIg', '04-11-2019 11:37:06 am', 0, 1),
(258, '38', '13', 'aXRob2trZSDgtLjgtILgtK3gtLXgtIIgcGljIOC0heC0suC1jeC0suC1hi4uIOC0h+C0qOC1jeC0\nqOC0suC1hiBpbnRheWlsIGZiIOC0r+C0v+C1vSDgtI7gtKTgtY3gtLAg4LSy4LWI4LSV4LWN4oCM\nIOC0leC0v+C0n+C1jeC0n+C0vyDgtIXgtLHgtL/gtK/gtYsg4LSIIHBpYyDgtKjgtY0g', '04-11-2019 11:37:16 am', 0, 1),
(259, '51', '64', '4LSe4LS+4LW7IOC0quC0oOC0v+C0muC1jeC0muC1gSA=', '04-11-2019 11:37:39 am', 0, 1),
(260, '38', '13', '4LSk4LWL4LSo4LWN4LSo4LS/IOC0h+C0meC1jeC0meC0s+C1hiDgtIXgtLXgtL/gtJ/gtYYg4LSJ\n4LSq4LWN4LSq4LS/4LW9IOC0h+C0n+C0vuC0seC1geC0o+C1jeC0n+C0qOC1jeC0qOC1jSA=', '04-11-2019 11:37:47 am', 0, 1),
(261, '25', '13', '8J+ZhPCfmYR2b2ljbyBpdGhpbG8=', '04-11-2019 11:38:30 am', 0, 1),
(262, '78', '57', '8J+Yhg==', '04-11-2019 11:42:00 am', 0, 1),
(263, '85', '8', '4LS44LWB4LSW4LSCIA==', '04-11-2019 11:42:18 am', 0, 1),
(264, '74', '57', '8J+krvCfpK4=', '04-11-2019 11:42:20 am', 0, 1),
(265, '87', '13', '4LSH4LS14LS/4LSf4LWGIOC0qOC1huC0r+C0v+C0giDgtIngtLPgtY3gtLPgtKTgtYHgtIIg4LSH\n4LSy4LWN4LSy4LS+4LSk4LWN4LSk4LSk4LWB4LSCIOC0kuC0leC1jeC0leC1hiDgtJXgtKPgtJXg\ntY3gtJXgtL7gtKPgtY0uLi4g8J+YgQ==', '04-11-2019 11:43:59 am', 0, 1),
(266, '87', '57', '8J+krvCfpK4=', '04-11-2019 11:44:13 am', 0, 1),
(267, '85', '63', 'bW0=', '04-11-2019 11:45:48 am', 0, 1),
(268, '87', '13', '8J+YpPCfmKTwn5ik8J+YpA==', '04-11-2019 11:45:52 am', 0, 1),
(269, '69', '50', '8J+YlQ==', '04-11-2019 11:53:22 am', 0, 1),
(270, '91', '36914', '8J+YgvCfmII=', '04-11-2019 11:54:17 am', 0, 1),
(271, '92', '36914', 'aGFwcHkgYmlydGhkYXkg', '04-11-2019 12:00:09 pm', 0, 1),
(272, '92', '8', '4LSS4LSw4LS+4LSv4LS/4LSw4LSCIOC0nOC0qOC1jeC0ruC0puC0v+C0qOC0vuC0tuC0guC0uOC0\nleC1viDgtLbgtL/gtLUg8J+OiPCfjojwn46I8J+OivCfjorwn46KCgpHb2QgYmxlc3MgVQ==', '04-11-2019 12:01:05 pm', 0, 1),
(273, '87', '57', '8J+ZhPCfmYQ=', '04-11-2019 12:01:10 pm', 0, 1),
(274, '90', '57', '8J+YnfCfmJ3wn5id8J+YnfCfmJ0=', '04-11-2019 12:02:01 pm', 0, 1),
(275, '92', '65', 'aGFwcHkgYmlydGhkYXkg', '04-11-2019 12:02:34 pm', 0, 1),
(276, '92', '12', 'aGFwcHkgYmlydGhkYXk=', '04-11-2019 12:02:47 pm', 0, 1),
(277, '92', '58', 'aHBweSBicmR58J+MuQ==', '04-11-2019 12:05:19 pm', 0, 1),
(278, '77', '68', 'eWVz', '04-11-2019 12:05:40 pm', 0, 1),
(279, '87', '13', '8J+Yq/CfmKs=', '04-11-2019 12:07:44 pm', 0, 1),
(280, '92', '25303', 'aGFwcHkgYmlydGhkYXnwn5iN8J+YmPCfmJjwn5iY8J+YmA==', '04-11-2019 12:10:44 pm', 0, 1),
(281, '79', '25303', 'aGxvbw==', '04-11-2019 12:11:07 pm', 0, 1),
(282, '87', '57', '8J+YhvCfmIY=', '04-11-2019 12:11:10 pm', 0, 1),
(283, '87', '13', '8J+kqfCfpKk=', '04-11-2019 12:15:02 pm', 0, 1),
(284, '92', '47', 'aGFwcHkgYidkYXkgc2l2YSZnb2QgYmxlc3MgdXV1dS4uLi4uLi4uIPCfjoE=', '04-11-2019 12:16:21 pm', 0, 1),
(285, '84', '66', '8J+ko/CfpKM=', '04-11-2019 12:17:18 pm', 0, 1),
(286, '84', '66', '4LSO4LSo4LWN4LSx4LWH4LSCIA==', '04-11-2019 12:17:27 pm', 0, 1),
(287, '83', '66', '8J+ko/CfpKPwn6Sj', '04-11-2019 12:17:44 pm', 0, 1),
(288, '99', '13', '8J+Zi+KAjeKZgu+4jw==', '04-11-2019 12:23:48 pm', 0, 1),
(289, '99', '13', '4LSm4LS+4LS44LSq4LWN4LSq4LS+IPCfmYvigI3imYLvuI/wn5mL4oCN4pmC77iP', '04-11-2019 12:24:00 pm', 0, 1),
(290, '83', '13', 'ZW50aGUgbGlra3VubmF0', '04-11-2019 12:24:19 pm', 0, 1),
(291, '83', '13', '4LSO4LSy4LWN4LSy4LS+4LS14LSw4LWB4LSCIOC0leC1geC0n+C1geC0meC1jeC0meC1geC0giDw\nn5ik', '04-11-2019 12:24:34 pm', 0, 1),
(292, '92', '66', '4LSq4LS/4LSx4LSo4LWN4LSo4LS+4LW+IOC0huC0tuC0guC0uOC0leC1vi4uLi4g4LS24LS/4LS1\nLi4uIPCfkpDwn6aL', '04-11-2019 12:25:10 pm', 0, 1),
(293, '92', '13', 'aGFwcHkgYmlydGhkYXkgbXV0aHVtYW5lZWXwn5iN8J+YjfCfmI3wn5iN8J+OgvCfjoLwn46C8J+O\ngvCfjbzwn4288J+NvPCfjbzwn4288J+NvPCfjbzwn4288J+NvPCfjbDwn42w8J+NsPCfjbDwn42w', '04-11-2019 12:25:39 pm', 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `comments_replay`
--

CREATE TABLE `comments_replay` (
  `sn` bigint(20) NOT NULL,
  `commentid` varchar(50) NOT NULL,
  `userid` varchar(50) NOT NULL,
  `comment` varchar(2000) CHARACTER SET utf8mb4 NOT NULL,
  `postdate` varchar(50) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `comments_replay`
--

INSERT INTO `comments_replay` (`sn`, `commentid`, `userid`, `comment`, `postdate`, `status`) VALUES
(1, '1', '1', 'amNjaWN1Y3V1dXU=', '03-11-2019 07:01:25 pm', 0),
(2, '1', '1', 'YmJodWN1Yw==\n', '03-11-2019 07:01:36 pm', 1),
(3, '3', '1', 'dmhoaA==', '03-11-2019 07:03:17 pm', 0),
(4, '2', '1', 'dnZiYnY=', '03-11-2019 07:04:03 pm', 0),
(5, '5', '1', 'Ynpuem5uem5zbm5zbmTwn42L8J+Ni/CfmJDwn42M8J+OkfCfjZHwn5iO8J+Yg/CfmJfwn5iQ8J+Y\njvCfmI7wn5iQ8J+Yr/CfmKPwn6Sp8J+kqfCfpKk=', '03-11-2019 08:11:14 pm', 1),
(6, '17', '1', 'Z2hqYmJi', '03-11-2019 08:32:58 pm', 1),
(7, '17', '1', 'Ympqam5iYg==', '03-11-2019 08:33:15 pm', 1),
(8, '17', '3', 'Z2pqZmho', '03-11-2019 08:33:57 pm', 1),
(9, '17', '3', 'Z2ho', '03-11-2019 08:34:03 pm', 1),
(10, '17', '1', 'eGh4anhoeGh4aHpoemh6', '03-11-2019 08:34:06 pm', 1),
(11, '17', '3', 'eHh1eHV4', '03-11-2019 08:34:15 pm', 1),
(12, '17', '3', 'dmdnaGI=', '03-11-2019 08:37:28 pm', 1),
(13, '17', '3', 'Y2dn', '03-11-2019 08:38:55 pm', 1),
(14, '17', '1', 'eGh4anhqeHVmdQ==', '03-11-2019 08:39:13 pm', 1),
(15, '17', '3', 'ZmZnZ3U=', '03-11-2019 08:39:22 pm', 1),
(16, '17', '3', 'IGNjdg==', '03-11-2019 08:39:30 pm', 1),
(17, '19', '3', 'dmdoag==', '03-11-2019 09:06:17 pm', 1),
(18, '74', '27', 'c3ViaQ==', '04-11-2019 09:12:10 am', 0),
(19, '79', '21', '4LS24LWB4LSt4LSm4LS/4LSo4LSC', '04-11-2019 09:40:44 am', 1),
(20, '107', '24', '8J+ZgfCfmYHwn5mB', '04-11-2019 09:49:45 am', 1),
(21, '108', '24', 'bW5n', '04-11-2019 09:49:52 am', 1),
(22, '108', '12', '8J+YiuKcjO+4jw==', '04-11-2019 09:50:04 am', 1),
(23, '107', '19', 'c3R1dHVzIG9ra2UgcG95aWxsZfCfmILwn5iC8J+Ygg==', '04-11-2019 09:50:47 am', 1),
(24, '108', '24', '8J+Zgg==', '04-11-2019 09:52:46 am', 1),
(25, '108', '12', 'Q2hheWEga3VkaWNoYQ==', '04-11-2019 09:53:01 am', 1),
(26, '107', '24', 'bW3imLnvuI8=', '04-11-2019 09:53:33 am', 1),
(27, '108', '24', 'a2RjaG5kIGlya251', '04-11-2019 09:53:53 am', 1),
(28, '107', '19', 'YmVzdA==', '04-11-2019 09:53:59 am', 1),
(29, '108', '12', 'YWFoIGhh', '04-11-2019 09:54:32 am', 1),
(30, '120', '12', 'bXV0aGVlZfCfmI3wn5iN8J+YjfCfmI3wn5iN', '04-11-2019 10:23:17 am', 1),
(31, '104', '36841', 'ZGk=', '04-11-2019 10:28:24 am', 1),
(32, '120', '47', 'b3J1cGFkIG1pc3MgY2hleXRodSBuaW5uZSDwn5CS', '04-11-2019 10:49:48 am', 1),
(33, '130', '36914', '8J+Ygg==', '04-11-2019 10:50:22 am', 1),
(34, '120', '12', 'bmlubmVt8J+YjfCfmI3wn5iN8J+YjfCfkJLwn5iY8J+YmPCfmJjwn5iY', '04-11-2019 10:52:18 am', 1),
(35, '120', '47', 'c2hlcmlrdW0g', '04-11-2019 10:52:34 am', 1),
(36, '120', '12', 'eWVz', '04-11-2019 10:53:28 am', 1),
(37, '120', '12', 'bmVlIHBpbm5lIHZlcmUgb25uaWx1bSBpbGxhbG8=', '04-11-2019 10:53:59 am', 1),
(38, '120', '47', 'bW0=', '04-11-2019 10:54:20 am', 1),
(39, '120', '47', 'aXRoIGlwcG8gZWxsYXJrdW0gc2hlcml5YXlpIGthbnVtbw==', '04-11-2019 10:54:58 am', 1),
(40, '120', '12', 'YXJpeWlsbGE=', '04-11-2019 10:55:10 am', 1),
(41, '120', '12', 'c2hhcmVjaGF0IHVuZGE=', '04-11-2019 10:55:24 am', 1),
(42, '120', '47', 'aWxsYQ==', '04-11-2019 10:55:37 am', 1),
(43, '120', '47', 'bmUgdW5kbw==', '04-11-2019 10:55:50 am', 1),
(44, '104', '36', '8J+ZhPCfmYQ=', '04-11-2019 10:56:22 am', 0),
(45, '120', '12', 'eWVz', '04-11-2019 10:56:44 am', 1),
(46, '120', '47', 'bW0=', '04-11-2019 10:56:58 am', 1),
(47, '120', '47', 'aGVsbyB1bmR5cnVubnUgbXVuYg==', '04-11-2019 10:57:13 am', 1),
(48, '120', '47', 'aXBvIGF0aHVuIGlsbGE=', '04-11-2019 10:57:23 am', 1),
(49, '120', '12', 'YXRoaWx1bSB1bmQ=', '04-11-2019 10:57:38 am', 1),
(50, '151', '36', '8J+Yiw==', '04-11-2019 11:15:50 am', 0),
(51, '151', '57', '8J+ZhPCfmYQ=', '04-11-2019 11:17:55 am', 1),
(52, '162', '13', 'ZW50aGU=', '04-11-2019 11:18:42 am', 1),
(53, '151', '36', '8J+ZhPCfmYQ=', '04-11-2019 11:19:46 am', 1),
(54, '151', '36', '4LSo4LWAIOC0heC0suC1jeC0suC1hiA=', '04-11-2019 11:19:53 am', 1),
(55, '151', '57', '4LSG4LSw4LWNIPCfmYQ=', '04-11-2019 11:20:45 am', 1),
(56, '151', '36', '4LSS4LSo4LWN4LSo4LWNIOC0quC1i+C0n+C1gCA=', '04-11-2019 11:21:00 am', 1),
(57, '151', '57', '8J+krg==', '04-11-2019 11:21:17 am', 1),
(58, '151', '36', '8J+Yo/CfmKM=', '04-11-2019 11:21:30 am', 1),
(59, '244', '12', 'aGlpaSBnb29kIG1vcm5pbmcgZGVhcvCfmIo=', '04-11-2019 11:34:52 am', 1),
(60, '208', '8', '4LSH4LSo4LWN4LSo4LSy4LWGIOC0ieC0o+C1jeC0n+C0vuC0r+C0v+C0sOC1geC0qOC1jeC0qOC1\ngSA=', '04-11-2019 11:41:10 am', 1),
(61, '235', '8', '8J+kqfCfpKk=', '04-11-2019 11:41:28 am', 1),
(62, '120', '12', 'a29vb29vaWk=', '04-11-2019 11:47:42 am', 1),
(63, '228', '21', '4LS24LWB4LSt4LSm4LS/4LSo4LSC', '04-11-2019 11:48:59 am', 1);

-- --------------------------------------------------------

--
-- Table structure for table `instagram`
--

CREATE TABLE `instagram` (
  `sn` bigint(20) NOT NULL,
  `types` varchar(30) NOT NULL,
  `url` varchar(500) NOT NULL,
  `title` varchar(1000) CHARACTER SET utf8mb4 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `instagram`
--

INSERT INTO `instagram` (`sn`, `types`, `url`, `title`) VALUES
(15, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/d210245366740c728d475e915975b982/5E43FDE7/t51.2885-15/e35/69615275_2203722489926797_2120890585823081573_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', ''),
(16, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/ded78068e0e6815f9f9ed9794b5bd9d9/5E44C32A/t51.2885-15/e35/69646052_152158895854610_2382201923661107650_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=102', ''),
(17, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/de7f5e66c1ed45280fa15a3b2bc6ac5e/5E43585E/t51.2885-15/e35/67970327_2349935995256934_793234199761200642_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', ''),
(18, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/424d6685c2fec52714e718c1537dbe13/5E642E0D/t51.2885-15/e35/65309990_645038712667454_4065094902153471488_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=102', ''),
(19, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/14c468804d596a559f6e04bb3c8182a5/5E40699E/t51.2885-15/e15/65314786_355836081769280_110910221220631740_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', ''),
(20, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/dc5d03c1036454b7b9ea1259796e249d/5E618EA5/t51.2885-15/e15/65275082_626847387814400_942307386813269342_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', ''),
(21, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/9ab2853709698b9c6bf09769f148d1e2/5E421A27/t51.2885-15/e15/65091349_949816118697087_579944318523233937_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=103', ''),
(22, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/1aafc0d674b0e5b0a233e06e764a8314/5E638368/t51.2885-15/e15/64788478_1103708249820452_873365370492794328_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', ''),
(23, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/d8fe8844bed54a595d783f553b1d5cbe/5E4E84ED/t51.2885-15/e15/64334599_882639768764887_6726553199946500441_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=107', ''),
(24, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/3067f60d3ca6312dfdd7ac57e3b1ef68/5E60D778/t51.2885-15/e15/63317762_326409211604336_174229275248852872_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', ''),
(25, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/bafee98ba7e817ebd560b448205185aa/5E54E4F6/t51.2885-15/e15/65494698_485905405285498_6193353280411153585_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=110', ''),
(26, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/6824b15c096280acefdcda6168a711dc/5E4B943C/t51.2885-15/e15/65142634_2547507801947067_2193378740586721683_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=103', ''),
(27, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/36baa1f28af762ce9cf91f390f5d7959/5DC25D3A/t50.2886-16/77109641_1569644696511892_5651443394649491544_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com', 'follow more quotes and videos @psychochan__🔍🔍🔍\n.\n.\nFollow me@psychochan__\nFollow me@psychochan__\nFollow me@psychochan__ .\n.\n.\n.\nVideo @self_lover._143\n#mallupost  #mallu  #malayalamtypography  #nanokadhakal  #malayalam #imalayali #kerala  #keralagodsowncountry  #aristotle_2nd_  #mallumedia  #mallutypography #branthan #typovideo #illustration #mtm #kavithakal #malayalis #keralatypography  #positivemalayalam  #videoedits  #viplavam #typography \n@nerkoli @braanthan\n@typo_chekkan\n@typography_gods_own_country @typogram_kerala  @typo_branthan @malluthugs  @malayalam_typography_ @malayalimukk @thoolika_viplavam  @thoolika_suhrth  @thonnivasi__  @kuttoosan.media  @kuttoooosan @_shaji_papan_  @adobe__life__  @_oru__pavam__jinnu_  @theeppori_  @thala_thericha_rider  @post_muthalali @pachapparishkari  @thallipoli_page #malayalamcinemasetting @sreenathbhasi  #travelblogger #travelblogger #sad #thattathin_marayathu  #charlie #dulquersalmaan'),
(28, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/959615c9df17f6155a7905915e1669e8/5E52CB7A/t51.2885-15/e35/72685619_2214739525490562_4296132811421414829_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', '...\nMention Them💔\n.\nCredit - @ameenkhaleel311\n.\nഇങ്ങോട്ട് ഇടിച്ചു\nകയറി വന്ന്\nമനസ്സിൽ\nആഴത്തിൽ\nസ്ഥാനം പിടിച്ചു\nപറ്റിയ ശേഷം\nപരിചയ ഭാവം പോലും\nകാണിക്കാതെ\nനടന്നകലുന്നതാണത്രേ\nഇന്നത്തെ ട്രെന്റ്...\n.\n.\n.\n#keralavibes #keraladiaries🌴 #malayali\n#malayalamlovequotes #entekeralam #malayalam\n#mallugram #kerala #malluquotes\n#keralite #instawriting #malayalamquotes\n#typography #keralaattraction #kerala🌴\n#malayalee #malayalis #typography_kerala\n#keralagram #keralagodsowncountry #lovemalayalam #malayalamstatus #kavitha #malayalamwriter\n#godsowncountry #keralatypography #malayalamquotes\n#malayalamtypography #keralaphotography #kerala_360 #braanthan'),
(29, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/50330a146c3836e9fb647de2ace82e84/5E51E964/t51.2885-15/e35/74601600_238127583828204_1432121593626314781_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', '...\nCredit - @ana_kh_a\n.\nഅങ്ങനെയും ഇല്ലേ\nചില ബന്ധങ്ങൾ...\nജീവന്റെ പാതി എന്ന്\nതോന്നിക്കുന്ന ചിലർ...\nഒരു നേരം അവർ\nമിണ്ടാതിരിക്കുമ്പോഴേക്കും\nദേഷ്യവും വിഷമവും\nനമ്മളിൽ വരുത്തുന്നവർ...\nവഴക്കുണ്ടാക്കുമ്പോൾ\nഉള്ളു വിങ്ങിപ്പിക്കുകയും\nകണ്ണ് നനയ്ക്കുകയും\nചെയ്യുന്നവർ...\nചില സമയങ്ങളിൽ\nസുഹൃത്തായും അതേ\nസമയം കൂടപ്പിറപ്പായും\nഒപ്പം നിൽക്കുന്നവർ...\nസൗഹൃദത്തിനും പ്രണയത്തിനും\nഅപ്പുറം തിരിച്ചറിയാനോ\nനിർവചിക്കാനോ കഴിയാത്ത\nചില ബന്ധങ്ങൾ...\nഒരു കൈ അകലെ എന്നും\nകൂടെ വേണമെന്ന്\nആഗ്രഹിക്കുന്ന ചിലർ...!\n.\n.\n.\n#keralavibes #keraladiaries🌴 #malayali\n#malayalamlovequotes #entekeralam #malayalam\n#mallugram #kerala #malluquotes\n#keralite #instawriting #malayalamquotes\n#typography #keralaattraction #kerala🌴\n#malayalee #malayalis #typography_kerala\n#keralagram #keralagodsowncountry #lovemalayalam #malayalamstatus #kavitha #malayalamwriter\n#godsowncountry #keralatypography #malayalamquotes\n#malayalamtypography #keralaphotography #kerala_360 #braanthan'),
(30, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/9f80e030d7a92cb49c2844549f6e1cf1/5E48DC75/t51.2885-15/e35/73157133_1503227913153448_2687809069181387481_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', '...\nCredit - @nav______ya\n.\nനമ്മൾ കാരണം ആരും\nവിഷമിക്കേണ്ടി വരരുത്,\nചിലപ്പോൾ നമ്മൾ\nആഗ്രഹിച്ചത് നമുക്ക്\nകിട്ടീയെന്നു വരില്ല,\nഎന്നാലും ആരോടും\nപരിഭവം കാട്ടരുത് ആരെയും\nപിടിച്ച് സ്നേഹിപ്പിക്കരുത്,\nനമുക്ക് കിട്ടണ്ടത്\nആണേൽ അവർ തരും,\nപക്ഷെ വേണ്ട\nഎന്നു വെച്ചതിനെ വീണ്ടും\nവാശിക്ക് അടിമപ്പെടുത്തരുത്\nഅതിനു ആയുസ്\nകുറവായിരിക്കും....\nആർക്കോ വേണ്ടി നിർബന്ധ\nസ്നേഹം അത് കിട്ടിയിട്ട്\nഒരു കാര്യവുമില്ല\nമനസ്സറിഞ്ഞു കിട്ടണം...\n.\n.\n.\n#keralavibes #keraladiaries🌴 #malayali\n#malayalamlovequotes #entekeralam #malayalam\n#mallugram #kerala #malluquotes\n#keralite #instawriting #malayalamquotes\n#typography #keralaattraction #kerala🌴\n#malayalee #malayalis #typography_kerala\n#keralagram #keralagodsowncountry #lovemalayalam #malayalamstatus #kavitha #malayalamwriter\n#godsowncountry #keralatypography #malayalamquotes\n#malayalamtypography #keralaphotography #kerala_360 #braanthan'),
(31, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/627eae3d2eec9a6cc4d3ba60392309a4/5E469CAB/t51.2885-15/e35/74698417_539476759965944_7847541981705234198_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', '...\nCredit - @ajmalnafi\n.\nഅവസാന ശ്രമവും അറ്റുവീണു...\nഇനി നിന്നിലേക്ക് ഞാൻ വരില്ല...\nതിരക്കാണെന്ന് പറഞ്ഞു\nഓടി ഒളിക്കുമ്പോൾ ഇടക്കിടെ\nഅറിഞ്ഞുകൊണ്ട് നിന്റെ\nമുന്നിലേക്ക് ഓടിവന്നത്\nഇഷ്ടകൂടുതൽ കൊണ്ടായിരുന്നു...\nഇനി നീ മിണ്ടാതിരിക്കുമ്പോൾ\nപരാതികളുടെ ഭണ്ഡാരമേന്തി\nനിനക്കു മുന്നിലേക്ക്\nഞാൻ വരില്ല...\nപലപ്പോഴായും നീ സമ്മാനിച്ച\nനല്ല ഓർമകളെ മറവിക്ക്\nവിട്ടുകൊടുക്കാൻ\nതയ്യാറെടുക്കുക്കയാണ് ഞാൻ ഇന്ന്,\nകഴിയുമോ എന്നറിയില്ല\nഎനിക്കിതൊക്കെ...\nഎങ്കിലും അവസാന\nഒരു ശ്രമമായി മാത്രം...\n.\n.\n.#keralavibes #keraladiaries🌴 #malayali\n#malayalamlovequotes #entekeralam #malayalam\n#mallugram #kerala #malluquotes\n#keralite #instawriting #malayalamquotes\n#typography #keralaattraction #kerala🌴\n#malayalee #malayalis #typography_kerala\n#keralagram #keralagodsowncountry #lovemalayalam #malayalamstatus #kavitha #malayalamwriter\n#godsowncountry #keralatypography #malayalamquotes\n#malayalamtypography #keralaphotography #kerala_360 #braanthan'),
(32, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/f6b6a347509f4ad13ca3344d70e3ef48/5E44221C/t51.2885-15/e35/72719425_421368758510365_6785432213488699536_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', '...\ncredit - @_brandhan___\n.\nകൈയ്യിൽ പണമുള്ളവർ \nവിൽക്കപ്പെട്ട\nസ്വാതന്ത്രത്തിന്റെ \nപാട്ട് പാടുന്നു,\n.\nപാട്ട് ചെവിയിൽ \nതുളച്ചുകയറിയവർ \nകീബോർഡ് വിപ്ലവത്തിൽ \nഹാഷ്ടാഗ് തൂക്കിയിട്ട് \nസ്വാതന്ത്രത്തിന്റെ\nകനലൊരുക്കുന്നു,\n.\nമറ്റ് ചിലർ കണ്ണുകളടച്ചു \nചെവി പൊത്തി \nരംഗോലിക്കായുള്ള \nനെട്ടോട്ടത്തിലാണ് \nഅവർക്കിന്നും ഇവിടം \nപാഠപുസ്തകത്തിലെ \nസ്വതന്ത്രയിന്ത്യ തന്നെ...\n.\n.\n.\n#keralavibes #keraladiaries🌴 #malayali\n#malayalamlovequotes #entekeralam #malayalam\n#mallugram #kerala #malluquotes\n#keralite #instawriting #malayalamquotes\n#typography #keralaattraction #kerala🌴\n#malayalee #malayalis #typography_kerala\n#keralagram #keralagodsowncountry #lovemalayalam #malayalamstatus #kavitha #malayalamwriter\n#godsowncountry #keralatypography #malayalamquotes\n#malayalamtypography #keralaphotography #kerala_360 #braanthan'),
(33, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/1b3b568221d9277604304f006c76d981/5E40E459/t51.2885-15/e35/71965351_553011972190154_5840643577738845018_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=102', '...\nCredit - @zairaa_zaii\n.\nകത്തിയെരിയുന്ന\nവെയിലിൽ\nആശ്വാസമായി\nപെയ്യുന്ന കുളിർ\nതെന്നലുപോലെ\nചിലർ ജീവിതത്തിലേക്ക്\nപറയാതങ്ങ് കയറിവരും...\nഎന്നിട്ടോ ഒരുനാൾ\nഅങ്ങ് പൊയ്ക്കളയും\nവന്നതുപോലെ\nപറയാതെ തന്നെ...\nനെഞ്ചിൽ ഒരായിരം\nചോദ്യങ്ങളും സംശയങ്ങളും\nബാക്കിയാക്കി...\n.\n.\n.\n#keralavibes #keraladiaries🌴 #malayali\n#malayalamlovequotes #entekeralam #malayalam\n#mallugram #kerala #malluquotes\n#keralite #instawriting #malayalamquotes\n#typography #keralaattraction #kerala🌴\n#malayalee #malayalis #typography_kerala\n#keralagram #keralagodsowncountry #lovemalayalam #malayalamstatus #kavitha #malayalamwriter\n#godsowncountry #keralatypography #malayalamquotes\n#malayalamtypography #keralaphotography #kerala_360 #braanthan'),
(34, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/c77411f86c40dcc5ed654bcce8f4f977/5E6407E6/t51.2885-15/e35/74618227_418632005730858_1403352200570692931_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', '...\nCredit - @aks_black99\n.\nഅതേ...\nഞാനൊന്നു ചോദിച്ചോട്ടേ..,\nപെട്ടെന്ന് ഒരു ദിവസം\nഞാനില്ലാണ്ടായാൽ\nമാഷ് എന്ത് ചെയ്യും? അറിയില്ല...!\nനീയില്ലാത്തിടങ്ങളിൽ\nഹൃദയം ഓർമ്മകളുടെ\nശവപ്പറമ്പാവുമെന്നും..,\nആകാശം\nചോർന്നൊലിക്കുമെന്നും..,\nതോരാമഴയിൽ\nനനഞ്ഞൊട്ടുന്ന തൂവലുകൾ\nപിന്നീടൊരിക്കലും\nപറക്കാതാവുമെന്നും..,\nകെട്ടിപ്പിടിക്കാൻ \nനീയില്ലാതെ ഞാൻ തനിയെ\nനിന്ന് വിറയ്ക്കുമെന്നും\nമാത്രം അറിയാം...\n.\n.\n.\n#keralavibes #keraladiaries🌴 #malayali\n#malayalamlovequotes #entekeralam #malayalam\n#mallugram #kerala #malluquotes\n#keralite #instawriting #malayalamquotes\n#typography #keralaattraction #kerala🌴\n#malayalee #malayalis #typography_kerala\n#keralagram #keralagodsowncountry #lovemalayalam #malayalamstatus #kavitha #malayalamwriter\n#godsowncountry #keralatypography #malayalamquotes\n#malayalamtypography #keralaphotography #kerala_360 #braanthan'),
(35, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/477cecaceebc783342851f3d4ee7a07a/5E5C5A46/t51.2885-15/e35/73423652_145475576812987_7171355213645360320_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=107', '...\nCredit - @sanamohan2300\n.\n“ക്ഷമ”യോളം\nനല്ല മരുന്ന്\nവേറെയില്ലല്ലോ..!\nഅതിപ്പോൾ\nമനസ്സിലാക്കാനായാലും\nപരിഭവം\nകേൾക്കാനായാലും നിന്റെ ദേഷ്യം\nകുറയ്ക്കാനായാലും...\n.\n.\n.\n#keralavibes #keraladiaries🌴 #malayali\n#malayalamlovequotes #entekeralam #malayalam\n#mallugram #kerala #malluquotes\n#keralite #instawriting #malayalamquotes\n#typography #keralaattraction #kerala🌴\n#malayalee #malayalis #typography_kerala\n#keralagram #keralagodsowncountry #lovemalayalam #malayalamstatus #kavitha #malayalamwriter\n#godsowncountry #keralatypography #malayalamquotes\n#malayalamtypography #keralaphotography #kerala_360 #braanthan'),
(36, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/561a05809b77420d19942e4fa8f9715b/5E558D31/t51.2885-15/e35/74838709_155300199016704_7081040576911297304_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', '...\nCredit - @younz_r\n.\nഇനിയൊരു\nമടക്കമുണ്ടെന്നെനിക്കു\nതോന്നുന്നില്ല...\nഅകലെമാഞ്ഞ\nപൂക്കാലമൊന്നും\nഇനിയീ വസന്തം\nതിരികെ തരില്ലെനിക്ക്...\nപതിയെ പെയ്തൊഴിഞ്ഞ\nചാറ്റലെന്നുമിനീയീ\nഇടവഴികളെ തലോടുമില്ല...\nപാതി മെയ്യൊഴിഞ്ഞെന്റെ\nആത്മാവും, ഒരു പറ്റം\nചിതലരിച്ച കിനാക്കളും\nഞാൻ തിരികെ\nനൽകാം നിനക്കു...\nഎന്റെ പാഴ്‌സഞ്ചിയിലിനി\nഅതുമാത്രം ബാക്കി...\n.\n.\n.#keralavibes #keraladiaries🌴 #malayali\n#malayalamlovequotes #entekeralam #malayalam\n#mallugram #kerala #malluquotes\n#keralite #instawriting #malayalamquotes\n#typography #keralaattraction #kerala🌴\n#malayalee #malayalis #typography_kerala\n#keralagram #keralagodsowncountry #lovemalayalam #malayalamstatus #kavitha #malayalamwriter\n#godsowncountry #keralatypography #malayalamquotes\n#malayalamtypography #keralaphotography #kerala_360 #braanthan'),
(37, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/092ae14e73254b0e2695b7e3df7c88a6/5E3EAF90/t51.2885-15/e35/71174851_548106106004075_3899958006616725352_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=111', '...\nCredit - @ajmalnafi\n.\nനമ്മെ വേണ്ടെന്ന്\nപറഞ്ഞു വിട്ടുപോയവരെ,\nഒരിക്കൽ തിരികെ\nവരുമെന്ന് കരുതി\nകാത്തിരിക്കുന്നത്\nവെറുതെയല്ലേ...?\nഅത്രമാത്രം നമ്മെ\nസ്നേഹിച്ചിരുന്നെങ്കിൽ,\nകുറച്ചെങ്കിലും\nമനസ്സിലാക്കിയിരുന്നെങ്കിൽ,\nഅവർക്ക് എങ്ങനെയാണ്\nനമ്മെ ഉപേക്ഷിച്ചുപോകാൻ\nകഴിയുക..?\n.\n.\n.\n#keralavibes #keraladiaries🌴 #malayali\n#malayalamlovequotes #entekeralam #malayalam\n#mallugram #kerala #malluquotes\n#keralite #instawriting #malayalamquotes\n#typography #keralaattraction #kerala🌴\n#malayalee #malayalis #typography_kerala\n#keralagram #keralagodsowncountry #lovemalayalam #malayalamstatus #kavitha #malayalamwriter\n#godsowncountry #keralatypography #malayalamquotes\n#malayalamtypography #keralaphotography #kerala_360 #braanthan'),
(38, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/9981acd00185a38bd806c2a13f53c886/5E49B422/t51.2885-15/e35/75454100_3004320086263902_2149355809899292012_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', '...\nCredit - @nav______ya\n.\nഅപ്രതീക്ഷിതമായി\nനമ്മളിലേക്ക് കടന്നു\nവരുന്ന ചിലരില്ലേ....\nവേനലിൽ പെയ്യുന്ന\nപുതുമഴപോലെ വന്ന്‌\nഎന്നോ മണ്മറഞ്ഞ നമ്മളുടെ\nപുഞ്ചിരിയെ ഉണർത്തുന്നവർ,\nമിന്നാമിനുങ്ങിന്റെ വെട്ടവും\nചിത്രശലഭത്തിന്റെ\nആയുസും ഉള്ളവർ,\nഎത്ര പെട്ടന്നാണവർ\nവ്യാഖ്യാനങ്ങളില്ലാത്ത\nബന്ധത്തിനുടമകളാകുന്നത്\nവിരലിലെണ്ണാവുന്ന\nദിനങ്ങളെ തരുന്നുള്ളുവെങ്കിലും\nവീണ്ടെടുത്ത പുഞ്ചിരി\nനിലനിർത്തി അവർ മടങ്ങുന്നു...\n.\n.\n.\n#keralavibes #keraladiaries🌴 #malayali\n#malayalamlovequotes #entekeralam #malayalam\n#mallugram #kerala #malluquotes\n#keralite #instawriting #malayalamquotes\n#typography #keralaattraction #kerala🌴\n#malayalee #malayalis #typography_kerala\n#keralagram #keralagodsowncountry #lovemalayalam #malayalamstatus #kavitha #malayalamwriter\n#godsowncountry #keralatypography #malayalamquotes\n#malayalamtypography #keralaphotography #kerala_360 #braanthan'),
(39, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/f07de5789f0b2b5586ac526a728e306f/5E4EB363/t51.2885-15/e35/70843921_394598034763900_7974450343155946698_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', '...\nCredit - @aks_black99\n.\nഒരിക്കലും മറക്കില്ല\nഎന്ന് പറയുന്നത് തന്നെ\nഒരു വലിയ കള്ളമല്ലേടോ..,\nമറക്കും.., മറക്കണം..,\nസ്നേഹക്കൂടുതൽ കൊണ്ട് \nപരസ്പരം ഏൽപ്പിച്ച\nമുറിവുകളുടെ  ആഴം\nഅളന്ന് നോക്കാൻ...\nഅത്രത്തോളം ആഴത്തിൽ\nഓർത്തോർത്തിരിക്കാൻ...\nഇടയ്ക്കൊന്നു മറക്കണം..!\nമറവിയുടെ രണ്ടറ്റത്ത്\nഒറ്റയ്ക്ക് ഒറ്റയ്ക്ക്\nഹൃദയത്തിന് മുകളിൽ\nകരിങ്കല്ല് ചുമന്നും,\nകണ്ണുകളിൽ കടലൊളിപ്പിച്ചും..,\nശ്വാസം മുട്ടിയും...\nമരിക്കാതെ മരവിച്ചും..\nഒടുവിൽ, ഓർക്കാതിരിക്കാനാവില്ല\nഎന്ന് തിരിച്ചറിയുന്ന നിമിഷം\nനമ്മുക്ക് വീണ്ടും രണ്ടറ്റങ്ങളിൽ\nനിന്നും പ്രണയിച്ച് തുടങ്ങാം..!\n.\n.\n.\n#keralavibes #keraladiaries🌴 #malayali\n#malayalamlovequotes #entekeralam #malayalam\n#mallugram #kerala #malluquotes\n#keralite #instawriting #malayalamquotes\n#typography #keralaattraction #kerala🌴\n#malayalee #malayalis #typography_kerala\n#keralagram #keralagodsowncountry #lovemalayalam #malayalamstatus #kavitha #malayalamwriter\n#godsowncountry #keralatypography #malayalamquotes\n#malayalamtypography #keralaphotography #kerala_360 #braanthan'),
(40, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/216ae1e9805a6f3b15e0c4b37c17cbeb/5E5AAE08/t51.2885-15/e35/71146230_791646417935525_6403829986230644433_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'ടീമേ.. 🙂\nFollow 👇for more quotes\n@komaali_official\n@komaali_official @komaali_official\n@komaali_official .\n.\n.\n\nFollow 👇my hashtag\n#komaali_ .\n.\n.\n\n#bineeshbastin #supportbineeshbastin #malayalaquotes #entekeralam #ezhuth #pranayam #kottayam #palakkad #trissur #kollam #malappuram #kavitha #nanokadhakal #malayalam #malayalamcinema #support #mallu #mallugram #malluwritings #malluthoughts #mallupage #kerala #keralagram #keralapage'),
(41, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/952113f241f5a228547c5d111610017d/5E48D53B/t51.2885-15/e35/75272057_165740171176508_4157037807120628573_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', 'ഒരിക്കൽ ആ മരുഭൂമികളെല്ലാം കടലുകൾ ആയിരുന്നിരിക്കാം.. 🙂\nFollow 👇for more quotes\n@komaali_official\n@komaali_official @komaali_official\n@komaali_official .\n.\n.\n\nFollow 👇my hashtag\n#komaali_ .\n.\n.\n\n#malayalaquotes #entekeralam #ezhuth #pranayam #kottayam #palakkad #trissur #kollam #malappuram #kavitha #nanokadhakal #malayalamlovequotes #malayalamlove #lovemalayalam #malayalam #malayalamcinema #malayalamdialogue #lost #mallu #mallugram #malluwritings #malluthoughts #mallupage #kerala #keralagram #keralapage'),
(42, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/54dc2ddd9f5541b2b10c58ed6308535a/5E60334F/t51.2885-15/e35/71515432_168944257630204_8124503778923268530_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', 'നീചപീഠം 🙂\nFollow 👇for more quotes\n@komaali_official\n@komaali_official @komaali_official\n@komaali_official .\n.\n.\n\nFollow 👇my hashtag\n#komaali_ .\n.\n.\n\n#walayar #sisters #entekeralam #ezhuth #pranayam #kottayam #palakkad #trissur #kollam #malappuram #kavitha #fuckrules #nanokadhakal #justice #walayarsisters #keralagodsowncountry #malayalam #rules #malayalamdialogue #lost #mallu #mallugram #malluwritings #malluthoughts #mallupage #kerala #keralagram #keralapage #hashtag'),
(43, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/e96b2fb38e6792f232bfc5cf69f8ddac/5E3F9571/t51.2885-15/e35/73385926_404456950230482_4602167537531938009_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=102', 'Happy diwali 🙂\nFollow 👇for more quotes\n@komaali_official\n@komaali_official @komaali_official\n@komaali_official .\n.\n.\n\nFollow 👇my hashtag\n#komaali_ .\n.\n.\n\n#diwali #malayalaquotes #entekeralam #ezhuth #pranayam #kottayam #palakkad #trissur #kollam #malappuram #kavitha #nanokadhakal #malayalamlovequotes #malayalamlove #lovemalayalam #malayalam #diwali2019 #malayalamdialogue #lost #mallu #mallugram #malluwritings #malluthoughts #mallupage #kerala #keralagram #keralapage'),
(44, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/bdd07efa4395173f205dbdf49988328b/5E46B19A/t51.2885-15/e35/71514355_141063933962774_5189070685116658043_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=102', 'പ്രണയം < സൗഹൃദം ✌\nFollow 👇for more quotes\n@komaali_official\n@komaali_official @komaali_official\n@komaali_official .\n.\n.\n\nFollow 👇my hashtag\n#komaali_ .\n.\n.\n\n#pmagafoor #malayalaquotes #entekeralam #ezhuth #pranayam #kottayam #palakkad #trissur #kollam #malappuram #kavitha #nanokadhakal #friends #malayalamlovequotes #malayalamlove #lovemalayalam #malayalam #thasvy #malayalamdialogue #mallu #mallugram #malluwritings #malluthoughts #mallupage #kerala #keralagram #keralapage'),
(45, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/1cdefa85b887750fd6e85144964b5d7f/5E43AC9B/t51.2885-15/e35/75196167_3018400014855081_4144861964320549217_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=109', 'Finishing point 🙂\nFollow 👇for more quotes\n@komaali_official\n@komaali_official @komaali_official\n@komaali_official .\n.\n.\n\nFollow 👇my hashtag\n#komaali_ .\n.\n.\n\n#malayalaquotes #entekeralam #ezhuth #pranayam #kottayam #palakkad #trissur #kollam #malappuram #kavitha #nanokadhakal #thasvy #malayalamlovequotes #malayalamlove #lovemalayalam #malayalam #pmagafoor #malayalamdialogue #lost #mallu #mallugram #malluwritings #malluthoughts #mallupage #kerala #keralagram #keralapage'),
(46, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/c894139e217d3cdbf7c123d393410edd/5E5A14B0/t51.2885-15/e35/72490023_700963953722615_2773730980362722366_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=111', 'നഷ്ടങ്ങളും അവ തന്ന സങ്കടങ്ങളും പോട്ടെ, സന്തോഷം പൂവണിയട്ടെ 🙂\nFollow 👇for more quotes\n@komaali_official\n@komaali_official @komaali_official\n@komaali_official .\n.\n.\n\nFollow 👇my hashtag\n#komaali_ .\n.\n.\n\n#malayalaquotes #entekeralam #ezhuth #pranayam #kottayam #palakkad #trissur #komaali_official #malappuram #kavitha #inspirationalquotes #motivationalquotes #malayalamlove #lovemalayalam #malayalam #malayalamdialogue #lost #mallu #mallugram #malluwritings #malluthoughts #mallupage #kerala #keralagram #keralapage #pmagafoor #whatsappstatus #thasvy'),
(47, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/0691d3431af57a9edeabec91f0cd788a/5DC2AA58/t50.2886-16/74287974_160056805192689_736719180040634186_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=109', 'നീ 🙂\nFollow 👇for more quotes\n@komaali_official\n@komaali_official @komaali_official\n@komaali_official .\n.\n.\n\nFollow 👇my hashtag\n#komaali_ .\n.\n.\n\n#whatsappstatus #malayalaquotes #entekeralam #ezhuth #pranayam #kottayam #palakkad #trissur #kollam #malappuram #kavitha #nanokadhakal #komaali_official #malayalamlovequotes #malayalamlove #lovemalayalam #malayalam #malayalamdialogue #lost #mallu #mallugram #malluwritings #malluthoughts #mallupage #kerala #keralagram #keralapage'),
(48, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/fce0e80bd7e7e86afcc22339e9558d12/5E40244C/t51.2885-15/e35/71965770_140270214008425_391757882959960449_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', 'വേർപിരിഞ്ഞു 🙂\nFollow 👇for more quotes\n@komaali_official\n@komaali_official @komaali_official\n@komaali_official .\n.\n.\n\nFollow 👇my hashtag\n#komaali_ .\n.\n.\n\n#malayalamtypography #malayalaquotes #entekeralam #ezhuth #pranayam #kottayam #palakkad #trissur #kollam #malappuram #kavitha #nanokadhakal #malayalamlovequotes #malayalamlove #lovemalayalam #malayalam #malayalamcinema #malayalamdialogue #lost #mallu #mallugram #malluwritings #malluthoughts #mallupage #kerala #keralagram #keralapage'),
(49, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/dec43496ee3720ee7468155b1c525f95/5E42EA8C/t51.2885-15/e35/72334291_2433660553377799_7168423647391628415_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=107', 'ചിലരുടെ ഓർമ്മകൾ 🙂\nFollow 👇for more quotes\n@komaali_official\n@komaali_official @komaali_official\n@komaali_official .\n.\n.\n\nFollow 👇my hashtag\n#komaali_ .\n.\n.\n\n#malayalaquotes #entekeralam #ezhuth #pranayam #kottayam #palakkad #trissur #kollam #malappuram #kavitha #nanokadhakal #komaali_official #malayalamlovequotes #malayalamlove #lovemalayalam #malayalam #malayalamdialogue #lost #mallu #mallugram #malluwritings #malluthoughts #mallupage #kerala #keralagram #keralapage'),
(50, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/956a79ab7d0d9f53b1b00dd7a78f6933/5E49B625/t51.2885-15/e35/71194655_522877061864292_1378498207274206979_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', 'ചില കാത്തിരിപ്പുകൾ 🙂\nFollow 👇for more quotes\n@komaali_official\n@komaali_official @komaali_official\n@komaali_official .\n.\n.\n\nFollow 👇my hashtag\n#komaali_ .\n.\n.\n\n#malayalaquotes #entekeralam #ezhuth #pranayam #kottayam #palakkad #trissur #kollam #malappuram #kavitha #nanokadhakal #malayalamlovequotes #lovemalayalam #malayalam #muhammed_muhsin #malayalamdialogue #lost #mallu #mallugram #malluwritings #malluthoughts #mallupage #kerala #komaali_official #keralagram #keralapage #pmagafoor #thasvy #whatsapp_status'),
(51, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/06eeded6a9e946a0ae973880b9c78eb8/5E4DDBD3/t51.2885-15/e35/70066257_386704082240847_4607670554301396785_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=103', 'സ്വന്തമല്ലായ്മ 🙂\nFollow 👇for more quotes\n@komaali_official\n@komaali_official @komaali_official\n@komaali_official .\n.\n.\n\nFollow 👇my hashtag\n#komaali_ .\n.\n.\n\n#malayalamtypography #malayalaquotes #entekeralam #ezhuth #pranayam #kottayam #palakkad #trissur #kollam #malappuram #kavitha #nanokadhakal #malayalamlovequotes #malayalamlove #lovemalayalam #malayalam #malayalamcinema #malayalamdialogue #lost #mallu #mallugram #malluwritings #malluthoughts #mallupage #kerala #keralagram #keralapage'),
(52, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/1bdbb9c82bb0cc14f91ac0fb0d8ab9ed/5DC29C2F/t50.2886-16/74988563_258377111787894_3005747664601441097_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', 'സഖാവ്.....♥️\n..🎧ᴜsᴇ ʜᴇᴀᴅᴘʜᴏɴᴇs ғᴏʀ ʙᴇsᴛ ᴇxᴘᴇʀɪᴇɴᴄᴇ 🎧.....\n© 𝐢 𝐝𝐨 𝐧𝐨𝐭 𝐨𝐰𝐧 𝐭𝐡𝐞 𝐢𝐦𝐚𝐠𝐞𝐬 𝐧𝐨𝐫 𝐭𝐡𝐞 𝐬𝐨𝐧𝐠. 𝐭𝐡𝐢𝐬 𝐯𝐢𝐝𝐞𝐨 𝐰𝐚𝐬 𝐧𝐨𝐭 𝐦𝐚𝐝𝐞 𝐟𝐨𝐫 𝐜𝐨𝐦𝐦𝐞𝐫𝐜𝐢𝐚𝐥 𝐩𝐮𝐫𝐩𝐨𝐬𝐞\n.\n.\n.\n.\n.\n.\n.\n.#typography #malayalamlovestatus #malayalamlovequotes #lovemalayalam #malayalamtypography #typogram_kerala #typography_kerala #kerala_360 #malayalamtypography #malayalam #mallu #india #kerala #malayalamquotes #kannur #thalathirinhavan #typographykerala #pottan #keralite #sanyaasi #mallu #anonymouz_soul #vaaka #sakha #sakhav'),
(53, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/d67aced9defa150a1b4d07e06c0b5d8a/5DC275BD/t50.2886-16/77430509_1343805539113934_1233613373200903971_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=102', 'ഓ.... മൃദുലേ......🖤\n..🎧ᴜsᴇ ʜᴇᴀᴅᴘʜᴏɴᴇs ғᴏʀ ʙᴇsᴛ ᴇxᴘᴇʀɪᴇɴᴄᴇ 🎧....\n.© 𝐢 𝐝𝐨 𝐧𝐨𝐭 𝐨𝐰𝐧 𝐭𝐡𝐞 𝐢𝐦𝐚𝐠𝐞𝐬 𝐧𝐨𝐫 𝐭𝐡𝐞 𝐬𝐨𝐧𝐠. 𝐭𝐡𝐢𝐬 𝐯𝐢𝐝𝐞𝐨 𝐰𝐚𝐬 𝐧𝐨𝐭 𝐦𝐚𝐝𝐞 𝐟𝐨𝐫 𝐜𝐨𝐦𝐦𝐞𝐫𝐜𝐢𝐚𝐥 𝐩𝐮𝐫𝐩𝐨𝐬𝐞\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n#typography #malayalamlovestatus #malayalamlovequotes #lovemalayalam #malayalamtypography #typogram_kerala #typography_kerala #kerala_360 #malayalamtypography #malayalam #mallu #india #kerala #malayalamquotes #kannur #thalathirinhavan #typographykerala #pottan #keralite #sanyaasi #mallu #anonymouz_soul'),
(54, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/5a28f1cd958aaca47b9999ccfc53c080/5DC22DA5/t50.2886-16/76642373_530082814239829_9097925254536072897_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', 'kannatthil muthamittal ...🖤\n..🎧ᴜsᴇ ʜᴇᴀᴅᴘʜᴏɴᴇs ғᴏʀ ʙᴇsᴛ ᴇxᴘᴇʀɪᴇɴᴄᴇ 🎧.....\n© 𝐢 𝐝𝐨 𝐧𝐨𝐭 𝐨𝐰𝐧 𝐭𝐡𝐞 𝐢𝐦𝐚𝐠𝐞𝐬 𝐧𝐨𝐫 𝐭𝐡𝐞 𝐬𝐨𝐧𝐠. 𝐭𝐡𝐢𝐬 𝐯𝐢𝐝𝐞𝐨 𝐰𝐚𝐬 𝐧𝐨𝐭 𝐦𝐚𝐝𝐞 𝐟𝐨𝐫 𝐜𝐨𝐦𝐦𝐞𝐫𝐜𝐢𝐚𝐥 𝐩𝐮𝐫𝐩𝐨𝐬𝐞\n.\n.\n.\n.\n.\n.\n.\n.\n.#typography #malayalamlovestatus #malayalamlovequotes #lovemalayalam #malayalamtypography #typogram_kerala #typography_kerala #kerala_360 #malayalamtypography #malayalam #mallu #india #kerala #malayalamquotes #kannur #thalathirinhavan #typographykerala #pottan #keralite #sanyaasi #mallu #anonymouz_soul'),
(55, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/65397a0fe6029a46afab7783ac97ff6b/5DC25C7E/t50.2886-16/74291182_501121727134677_6747700603501939770_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=109', 'മായാനദി...🖤\n..🎧ᴜsᴇ ʜᴇᴀᴅᴘʜᴏɴᴇs ғᴏʀ ʙᴇsᴛ ᴇxᴘᴇʀɪᴇɴᴄᴇ 🎧.....\n© 𝐢 𝐝𝐨 𝐧𝐨𝐭 𝐨𝐰𝐧 𝐭𝐡𝐞 𝐢𝐦𝐚𝐠𝐞𝐬 𝐧𝐨𝐫 𝐭𝐡𝐞 𝐬𝐨𝐧𝐠. 𝐭𝐡𝐢𝐬 𝐯𝐢𝐝𝐞𝐨 𝐰𝐚𝐬 𝐧𝐨𝐭 𝐦𝐚𝐝𝐞 𝐟𝐨𝐫 𝐜𝐨𝐦𝐦𝐞𝐫𝐜𝐢𝐚𝐥 𝐩𝐮𝐫𝐩𝐨𝐬𝐞\n.\n.\n.\n.\n.\n.\n.\n.\n.\n#typography #malayalamlovestatus #malayalamlovequotes #lovemalayalam #malayalamtypography #typogram_kerala #typography_kerala #kerala_360 #malayalamtypography #malayalam #mallu #india #kerala #malayalamquotes #kannur #thalathirinhavan #typographykerala #pottan #keralite #sanyaasi #mallu #anonymouz_soul'),
(56, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/df4337bd8769497b25a89aa0608ee723/5DC240C3/t50.2886-16/74327707_189290602231589_3073095804218972056_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', '🥰\n..🎧ᴜsᴇ ʜᴇᴀᴅᴘʜᴏɴᴇs ғᴏʀ ʙᴇsᴛ ᴇxᴘᴇʀɪᴇɴᴄᴇ 🎧.....\n© 𝐢 𝐝𝐨 𝐧𝐨𝐭 𝐨𝐰𝐧 𝐭𝐡𝐞 𝐢𝐦𝐚𝐠𝐞𝐬 𝐧𝐨𝐫 𝐭𝐡𝐞 𝐬𝐨𝐧𝐠. 𝐭𝐡𝐢𝐬 𝐯𝐢𝐝𝐞𝐨 𝐰𝐚𝐬 𝐧𝐨𝐭 𝐦𝐚𝐝𝐞 𝐟𝐨𝐫 𝐜𝐨𝐦𝐦𝐞𝐫𝐜𝐢𝐚𝐥 𝐩𝐮𝐫𝐩𝐨𝐬𝐞\n.ft :@anjujosephofficial\n.\n.\n.\n.\n.\n.\n.\n. .\n.\n.#typography #malayalamlovestatus #malayalamlovequotes #lovemalayalam #malayalamtypography #typogram_kerala #typography_kerala #kerala_360 #malayalamtypography #malayalam #mallu #india #kerala #malayalamquotes #kannur #thalathirinhavan #typographykerala #pottan #keralite #sanyaasi #mallu #anonymouz_soul'),
(57, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/9314d41c76a80bc2ab4e08c924639d70/5DC28D51/t50.2886-16/73058871_396788044593923_4917241463749149745_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=111', 'Dear comrade....❣️.\n..🎧ᴜsᴇ ʜᴇᴀᴅᴘʜᴏɴᴇs ғᴏʀ ʙᴇsᴛ ᴇxᴘᴇʀɪᴇɴᴄᴇ 🎧.....© 𝐢 𝐝𝐨 𝐧𝐨𝐭 𝐨𝐰𝐧 𝐭𝐡𝐞 𝐢𝐦𝐚𝐠𝐞𝐬 𝐧𝐨𝐫 𝐭𝐡𝐞 𝐬𝐨𝐧𝐠. 𝐭𝐡𝐢𝐬 𝐯𝐢𝐝𝐞𝐨 𝐰𝐚𝐬 𝐧𝐨𝐭 𝐦𝐚𝐝𝐞 𝐟𝐨𝐫 𝐜𝐨𝐦𝐦𝐞𝐫𝐜𝐢𝐚𝐥 𝐩𝐮𝐫𝐩𝐨𝐬𝐞\n.\n.\n.\n.\n.\n.\n.\n.\n.#typography #malayalamlovestatus #malayalamlovequotes #lovemalayalam #malayalamtypography #typogram_kerala #typography_kerala #kerala_360 #malayalamtypography #malayalam #mallu #india #kerala #malayalamquotes #kannur #thalathirinhavan #typographykerala #pottan #keralite #sanyaasi #mallu #anonymouz_soul'),
(58, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/cb28e20338c17ad1bf52c05118d113a7/5DC2983C/t50.2886-16/74355900_101703791229306_974721763298752348_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', 'Oh kaadhal kanmani.....❣️\n..🎧ᴜsᴇ ʜᴇᴀᴅᴘʜᴏɴᴇs ғᴏʀ ʙᴇsᴛ ᴇxᴘᴇʀɪᴇɴᴄᴇ 🎧.....\n© 𝐢 𝐝𝐨 𝐧𝐨𝐭 𝐨𝐰𝐧 𝐭𝐡𝐞 𝐢𝐦𝐚𝐠𝐞𝐬 𝐧𝐨𝐫 𝐭𝐡𝐞 𝐬𝐨𝐧𝐠. 𝐭𝐡𝐢𝐬 𝐯𝐢𝐝𝐞𝐨 𝐰𝐚𝐬 𝐧𝐨𝐭 𝐦𝐚𝐝𝐞 𝐟𝐨𝐫 𝐜𝐨𝐦𝐦𝐞𝐫𝐜𝐢𝐚𝐥 𝐩𝐮𝐫𝐩𝐨𝐬𝐞\n.\n.\n.\n.\n.\n#dqsalmaan #dulquersalmaan #nithyamenon\n#okkanmani❤️ #typography #malayalamlovestatus #malayalamlovequotes #lovemalayalam #malayalamtypography #typogram_kerala #typography_kerala #kerala_360 #malayalamtypography #malayalam #mallu #india #kerala #malayalamquotes #kannur #thalathirinhavan #typographykerala #pottan #keralite #sanyaasi #mallu #anonymouz_soul'),
(59, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/1ebedb2e07f0815dccec08c7abdfa300/5DC273B0/t50.2886-16/72928598_1114915215565542_8392434189509591892_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', 'തട്ടത്തിൻ മറയത്ത്...🖤\n..🎧ᴜsᴇ ʜᴇᴀᴅᴘʜᴏɴᴇs ғᴏʀ ʙᴇsᴛ ᴇxᴘᴇʀɪᴇɴᴄᴇ 🎧.....\n© 𝐢 𝐝𝐨 𝐧𝐨𝐭 𝐨𝐰𝐧 𝐭𝐡𝐞 𝐢𝐦𝐚𝐠𝐞𝐬 𝐧𝐨𝐫 𝐭𝐡𝐞 𝐬𝐨𝐧𝐠. 𝐭𝐡𝐢𝐬 𝐯𝐢𝐝𝐞𝐨 𝐰𝐚𝐬 𝐧𝐨𝐭 𝐦𝐚𝐝𝐞 𝐟𝐨𝐫 𝐜𝐨𝐦𝐦𝐞𝐫𝐜𝐢𝐚𝐥 𝐩𝐮𝐫𝐩𝐨𝐬𝐞\n.\n.\n.\n.\n.\n.#nivin #nivinpauly #nivinpaulyfans\n#typography #malayalamlovestatus #malayalamlovequotes #lovemalayalam #malayalamtypography #typogram_kerala #typography_kerala #kerala_360 #malayalamtypography #malayalam #mallu #india #kerala #malayalamquotes #kannur #thalathirinhavan #typographykerala #pottan #keralite #sanyaasi #mallu #anonymouz_soul'),
(60, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/9909a8e67a6d687e074a56de7e6f16de/5DC28338/t50.2886-16/72749926_408347923197701_5711808179771949818_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'പ്രേമം...🖤\n..🎧ᴜsᴇ ʜᴇᴀᴅᴘʜᴏɴᴇs ғᴏʀ ʙᴇsᴛ ᴇxᴘᴇʀɪᴇɴᴄᴇ 🎧.....\n© 𝐢 𝐝𝐨 𝐧𝐨𝐭 𝐨𝐰𝐧 𝐭𝐡𝐞 𝐢𝐦𝐚𝐠𝐞𝐬 𝐧𝐨𝐫 𝐭𝐡𝐞 𝐬𝐨𝐧𝐠. 𝐭𝐡𝐢𝐬 𝐯𝐢𝐝𝐞𝐨 𝐰𝐚𝐬 𝐧𝐨𝐭 𝐦𝐚𝐝𝐞 𝐟𝐨𝐫 𝐜𝐨𝐦𝐦𝐞𝐫𝐜𝐢𝐚𝐥 𝐩𝐮𝐫𝐩𝐨𝐬𝐞\n.\n.\n.\n.\n.\n.#nivin #nivinpauly #nivinpaulyfans\n#typography #malayalamlovestatus #malayalamlovequotes #lovemalayalam #malayalamtypography #typogram_kerala #typography_kerala #kerala_360 #malayalamtypography #malayalam #mallu #india #kerala #malayalamquotes #kannur #thalathirinhavan #typographykerala #pottan #keralite #sanyaasi #mallu #anonymouz_soul'),
(61, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/2e95e2cca97069bb2f37eedb8821c7b5/5DC24397/t50.2886-16/73045182_387251238834131_1349402422304486519_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=111', 'Missing..... 🖤\n...🎧ᴜsᴇ ʜᴇᴀᴅᴘʜᴏɴᴇs ғᴏʀ ʙᴇsᴛ ᴇxᴘᴇʀɪᴇɴᴄᴇ 🎧.....\n© 𝐢 𝐝𝐨 𝐧𝐨𝐭 𝐨𝐰𝐧 𝐭𝐡𝐞 𝐢𝐦𝐚𝐠𝐞𝐬 𝐧𝐨𝐫 𝐭𝐡𝐞 𝐬𝐨𝐧𝐠. 𝐭𝐡𝐢𝐬 𝐯𝐢𝐝𝐞𝐨 𝐰𝐚𝐬 𝐧𝐨𝐭 𝐦𝐚𝐝𝐞 𝐟𝐨𝐫 𝐜𝐨𝐦𝐦𝐞𝐫𝐜𝐢𝐚𝐥 𝐩𝐮𝐫𝐩𝐨𝐬𝐞\n.\n.@the_smiliee_girl 🎶🎶\n.\n.\n.\n.\n.\n#typography #malayalamlovestatus #malayalamlovequotes #lovemalayalam #malayalamtypography #typogram_kerala #typography_kerala #kerala_360 #malayalamtypography #malayalam #mallu #india #kerala #malayalamquotes #kannur #thalathirinhavan #typographykerala #pottan #keralite #sanyaasi #mallu #anonymouz_soul'),
(62, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/3a6176ec75d8c4c2fe640f2f506232a0/5DC294CA/t50.2886-16/72710252_149683629605096_1979159319244943116_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', 'നെഞ്ചിനിലെ......❣️\n..🎧ᴜsᴇ ʜᴇᴀᴅᴘʜᴏɴᴇs ғᴏʀ ʙᴇsᴛ ᴇxᴘᴇʀɪᴇɴᴄᴇ 🎧.....\n© 𝐢 𝐝𝐨 𝐧𝐨𝐭 𝐨𝐰𝐧 𝐭𝐡𝐞 𝐢𝐦𝐚𝐠𝐞𝐬 𝐧𝐨𝐫 𝐭𝐡𝐞 𝐬𝐨𝐧𝐠. 𝐭𝐡𝐢𝐬 𝐯𝐢𝐝𝐞𝐨 𝐰𝐚𝐬 𝐧𝐨𝐭 𝐦𝐚𝐝𝐞 𝐟𝐨𝐫 𝐜𝐨𝐦𝐦𝐞𝐫𝐜𝐢𝐚𝐥 𝐩𝐮𝐫𝐩𝐨𝐬𝐞\n.\n.\n.\n.\n.\n.\n.\n.\n.\n#typography #malayalamlovestatus #malayalamlovequotes #lovemalayalam #malayalamtypography #typogram_kerala #typography_kerala #kerala_360 #malayalamtypography #malayalam #mallu #india #kerala #malayalamquotes #kannur #thalathirinhavan #typographykerala #pottan #keralite #sanyaasi #mallu #anonymouz_soul'),
(63, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/e08a3c4603ab6ff5298b9619d0476661/5DC2789A/t50.2886-16/72352725_2400959723492160_8793771908358318525_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', 'സാഹിബാ......🖤\n..🎧ᴜsᴇ ʜᴇᴀᴅᴘʜᴏɴᴇs ғᴏʀ ʙᴇsᴛ ᴇxᴘᴇʀɪᴇɴᴄᴇ 🎧....\n.© 𝐢 𝐝𝐨 𝐧𝐨𝐭 𝐨𝐰𝐧 𝐭𝐡𝐞 𝐢𝐦𝐚𝐠𝐞𝐬 𝐧𝐨𝐫 𝐭𝐡𝐞 𝐬𝐨𝐧𝐠. 𝐭𝐡𝐢𝐬 𝐯𝐢𝐝𝐞𝐨 𝐰𝐚𝐬 𝐧𝐨𝐭 𝐦𝐚𝐝𝐞 𝐟𝐨𝐫 𝐜𝐨𝐦𝐦𝐞𝐫𝐜𝐢𝐚𝐥 𝐩𝐮𝐫𝐩𝐨𝐬𝐞\n.\n.\n.\n.\n.\n.\n.\n.\n.#typography #malayalamlovestatus #malayalamlovequotes #lovemalayalam #malayalamtypography #typogram_kerala #typography_kerala #kerala_360 #malayalamtypography #malayalam #mallu #india #kerala #malayalamquotes #kannur #thalathirinhavan #typographykerala #pottan #keralite #sanyaasi #mallu #anonymouz_soul'),
(64, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/1c6c26b36131c462b66eae8adde9ca4d/5E4456D4/t51.2885-15/e35/75538145_2410722465846561_8423330871031374660_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=109', 'അങ്ങ് തെളിയിച്ചു കൊടുക്കണം 😁\n@vishnu_pariyanampatta വരികൾ 😊'),
(65, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/cfbb9bc614c4c7f82f542fbf9ab0efdc/5E5C35F1/t51.2885-15/e35/74627681_2620141808063341_5575269491414271683_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', 'ഉള്ളതല്ലേ 🙄'),
(66, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/9d0c2382fa3ff95bafe967969b0e7756/5E49E386/t51.2885-15/e35/74850520_542267006530469_1020851874972454589_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'ഐക്യദാർഢ്യം 💪\n@bineeshbastin'),
(67, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/ec80d9993262593f0bfb709964132a4f/5E54FBBC/t51.2885-15/e35/73178491_748129188981772_8056962201126229897_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', 'പ്രതീക്ഷിക്കാതെ നേരത്ത് കണ്ടിരുന്നുവെങ്കിലെന്നു ആഗ്രഹിച്ചു പോയി 😊'),
(68, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/a9fad0d45567e5bff83f8277158a6a97/5E620C2F/t51.2885-15/e35/74823358_476145223110300_6260251421951253692_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', 'ഇത് ഇന്ത്യയാണ്..\nഇവിടെ ഇങ്ങനെയാണ്..\nകൂടെ കേരളവും ഇങ്ങനെയായത് എങ്ങനെയെന്നാണ് 🙄😐 എഴുതിയ ആളുടെ പേരറിയില്ല..അതുകൊണ്ട് കടപ്പാട് വെച്ചിട്ടുണ്ട് 🤞\nഎഴുതിയ ആളുടെ പേര് പറഞ്ഞാൽ ഉടനടി ചേർക്കുന്നതാണ് ☝️'),
(69, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/40506d5bf2cd5f3e9358743d4ee37ad4/5E559EE8/t51.2885-15/e35/73101134_2296442653797432_4537563892251736224_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', 'We want #Justice'),
(70, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/7c278c2a756bfe378d610724d370f68e/5E46F652/t51.2885-15/e35/75153176_677902816065645_4097440720621465048_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=109', 'ദീപാവലി ആശംസകൾ 😍\n@mahinshajahan വരികൾ 🤞'),
(71, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/a6cdb73d0b60ad7e9abd215b80ba3346/5E63C6C1/t51.2885-15/e35/73393279_143842253597639_8260203634085844828_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=111', 'മെയിൻ കാര്യമാണ് 😶'),
(72, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/875161658cca7e0264a8a40e3b92fcb4/5E4B4CBD/t51.2885-15/e35/71170969_522664251889984_5730197909179313112_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', 'ഉറങ്ങാത്തവരുടെ കൂടെയാണ് 😁'),
(73, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/2b63964681795b0ee03f70adf2ffb2cb/5E5A0341/t51.2885-15/e35/74602364_156722105428145_8719195351148646780_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'ആ ചില ഇഷ്ടങ്ങൾ 😁'),
(74, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/909e7b6b5cab88da984498da22d44559/5E4DC402/t51.2885-15/e35/71753655_773830783050881_4371206203120796775_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'കുറച്ചാളുകൾ 😊\n@iyyaskm വരികൾ 😉'),
(75, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/590c5c110c1342f7dd4542548dcbe416/5E418520/t51.2885-15/e35/74711308_161563231594942_8179942955415429902_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=110', 'കവി അയ്യപ്പൻ 💞'),
(76, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/b92c4b4d544e4bfbcbfeddd65524b42d/5E438A12/t51.2885-15/e35/70964005_163587068168596_7484449525751018967_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', 'പ്രണയമാണ്, സ്വന്തമാക്കിയിരിക്കും, പെണ്ണേ 😍\nFOLLO&SUPPORT 👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻 @ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n#malayalam #malayalamcinema #malayalamtypography #malayalamquotes #malayalamsongs #malayalammovie #malayalamstatus #malayalamactress #malayalamcomedy #malayalamquotes💔 #typographymalayalam #kasargode #malapuram #kozhikode #thrissur #kochi #kollam #pathanamthitta #thiruvanathapuram #alapuzha #alappuzha #kerala #keralasaree #keralawedding #keralagodsowncountry #keralaattraction #support #following #ente_kanthari_pennu'),
(77, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/144b1e25dfc737c0b5b8ae4a79c97eb4/5E5A49E1/t51.2885-15/e35/72368321_146706453253653_5924822190011944040_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=109', '❤️എന്റെ പ്രണയം❤️ FOLLO&SUPPORT 👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻 @ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n#malayalam #malayalamcinema #malayalamtypography #malayalamquotes #malayalamsongs #malayalammovie #malayalamstatus #malayalamactress #malayalamcomedy #malayalamquotes💔 #typographymalayalam #kasargode #malapuram #kozhikode #thrissur #kochi #kollam #pathanamthitta #thiruvanathapuram #alapuzha #alappuzha #kerala #keralasaree #keralawedding #keralagodsowncountry #keralaattraction #support #following #ente_kanthari_pennu'),
(78, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/d328a57e1e87ca62fd97d262e198c2ee/5E4C1DC6/t51.2885-15/e35/76870951_141134233876736_7561992949935990243_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', 'പ്രണയിച്ചു തുടങ്ങി കഴിഞ്ഞാൽ  പിന്നെ ഭയങ്കര പ്ലാനിങ് ആണ്. എന്തെങ്കിലും പഠിക്കാൻ പോണം. നല്ലൊരു ജോലി മേടിക്കണം, എന്നിട്ട് പോയി പെണ്ണ് ചോദിക്കണം🤭 കടപ്പാട് :കൂട്ടുകാരൻ😍\n\nFOLLO&SUPPORT 👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻 @ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n#malayalam #malayalamcinema #malayalamtypography #malayalamquotes #malayalamsongs #malayalammovie #malayalamstatus #malayalamactress #malayalamcomedy #malayalamquotes💔 #typographymalayalam #kasargode #malapuram #kozhikode #thrissur #kochi #kollam #pathanamthitta #thiruvanathapuram #alapuzha #alappuzha #kerala #keralasaree #keralawedding #keralagodsowncountry #keralaattraction #support #following #ente_kanthari_pennu'),
(79, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/b1824922122cb3aa22d39c1d67f3f639/5E4C9E49/t51.2885-15/e35/74519945_1529106343896375_6558264147533941535_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=103', 'കടപ്പാട് : shilpa kr @mazha_typography___\n\nFOLLO&SUPPORT 👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻 @ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n#malayalam #malayalamcinema #malayalamtypography #malayalamquotes #malayalamsongs #malayalammovie #malayalamstatus #malayalamactress #malayalamcomedy #malayalamquotes💔 #typographymalayalam #kasargode #malapuram #kozhikode #thrissur #kochi #kollam #pathanamthitta #thiruvanathapuram #alapuzha #alappuzha #kerala #keralasaree #keralawedding #keralagodsowncountry #keralaattraction #support #following #ente_kanthari_pennu'),
(80, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/e7f95361c8db10d7848954d6f39a8cea/5E4F8DF9/t51.2885-15/e35/73414107_407396843526011_2756906342459727657_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=111', 'അതെ ഞാൻ കമ്മ്യൂണിസ്റ്റാ❤️🚩 FOLLO&SUPPORT 👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻 @ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n#malayalam #malayalamcinema #malayalamtypography #malayalamquotes #malayalamsongs #malayalammovie #malayalamstatus #malayalamactress #malayalamcomedy #malayalamquotes💔 #typographymalayalam #kasargode #malapuram #kozhikode #thrissur #kochi #kollam #pathanamthitta #thiruvanathapuram #alapuzha #alappuzha #kerala #keralasaree #keralawedding #keralagodsowncountry #keralaattraction #support #following #ente_kanthari_pennu'),
(81, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/e07328c7ddde7d6c94b0be3624528a86/5E479869/t51.2885-15/e35/72489741_931885953858908_7749474566806101376_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', '❤️❤️❤️❤️❤️❤️ FOLLO&SUPPORT 👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻 @ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n#malayalam #malayalamcinema #malayalamtypography #malayalamquotes #malayalamsongs #malayalammovie #malayalamstatus #malayalamactress #malayalamcomedy #malayalamquotes💔 #typographymalayalam #kasargode #malapuram #kozhikode #thrissur #kochi #kollam #pathanamthitta #thiruvanathapuram #alapuzha #alappuzha #kerala #keralasaree #keralawedding #keralagodsowncountry #keralaattraction #support #following #ente_kanthari_pennu'),
(82, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/142c2b21f3d0ee654dee6960d0af4836/5E54C205/t51.2885-15/e35/73521736_557147948398166_300495930061868494_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=102', 'പൂക്കളെ നിങ്ങൾക്ക് നുള്ളിയെറിയാനാവും പക്ഷെ വസന്തം അത് വരിക തന്നെ ചെയ്യും... കടപ്പാട് :  FOLLO&SUPPORT 👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻 @ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n#malayalam #malayalamcinema #malayalamtypography #malayalamquotes #malayalamsongs #malayalammovie #malayalamstatus #malayalamactress #malayalamcomedy #malayalamquotes💔 #typographymalayalam #kasargode #malapuram #kozhikode #thrissur #kochi #kollam #pathanamthitta #thiruvanathapuram #alapuzha #alappuzha #kerala #keralasaree #keralawedding #keralagodsowncountry #keralaattraction #support #following #ente_kanthari_pennu'),
(83, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/9e2106dd8b63e3a00bf0eddd413c0995/5E53DF58/t51.2885-15/e35/70486861_197587391254112_6886244589232215060_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', '\'നഷ്ടം\' എന്നൊന്നില്ല നമുക്ക്. സഖാവെ പ്രണയവും നമുക്ക് \'പോരാട്ടമല്ലേ\' FOLLO&SUPPORT 👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻 @ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n#malayalam #malayalamcinema #malayalamtypography #malayalamquotes #malayalamsongs #malayalammovie #malayalamstatus #malayalamactress #malayalamcomedy #malayalamquotes💔 #typographymalayalam #kasargode #malapuram #kozhikode #thrissur #kochi #kollam #pathanamthitta #thiruvanathapuram #alapuzha #alappuzha #kerala #keralasaree #keralawedding #keralagodsowncountry #keralaattraction #support #following #ente_kanthari_pennu'),
(84, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/a07bbbccf5be1f733f1630e00dc53338/5E55AB7A/t51.2885-15/e35/70725711_157230225372678_6353627918788753455_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', 'കാരിവള ഇട്ടകൈകൾ ചെങ്കൊടി എന്തട്ടെ കരിമഷി എഴുതാൻ ഉയർത്തിയ കൈകൾ വാനിൽ ഉയരട്ടെ... നിശബ്ദമാക്കിയ ശബ്ദം വാനം തുളക്കട്ടെ പെണ്ണെ.... കടപ്പാട് : \nFOLLO&SUPPORT 👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻 @ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n#malayalam #malayalamcinema #malayalamtypography #malayalamquotes #malayalamsongs #malayalammovie #malayalamstatus #malayalamactress #malayalamcomedy #malayalamquotes💔 #typographymalayalam #kasargode #malapuram #kozhikode #thrissur #kochi #kollam #pathanamthitta #thiruvanathapuram #alapuzha #alappuzha #kerala #keralasaree #keralawedding #keralagodsowncountry #keralaattraction #support #following #ente_kanthari_pennu'),
(85, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/dfe949c6569215ffd66a6ba48229272d/5E623077/t51.2885-15/e35/71702095_3369020356443247_3297694960207923653_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=107', 'പ്രണയത്തിലേക്ക് അല്ലല്ലോ സഖാവേ. വിപ്ലവത്തിലേക്ക് അല്ലേ ഞാൻ നിന്റെ കൈ പിടിച്ചു നടന്നത്\n\nFOLLO&SUPPORT 👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻 @ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n#malayalam #malayalamcinema #malayalamtypography #malayalamquotes #malayalamsongs #malayalammovie #malayalamstatus #malayalamactress #malayalamcomedy #malayalamquotes💔 #typographymalayalam #kasargode #malapuram #kozhikode #thrissur #kochi #kollam #pathanamthitta #thiruvanathapuram #alapuzha #alappuzha #kerala #keralasaree #keralawedding #keralagodsowncountry #keralaattraction #support #following #ente_kanthari_pennu'),
(86, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/125205ca99225d844522fe864c58113a/5E4CB96F/t51.2885-15/e35/69981102_135727481119285_7065855731345255450_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', 'എനിക്ക് ആരോടോ  എന്തോ  പറയാനുണ്ട്  പക്ഷേ അവർ മാത്രം കേൾക്കാനില്ല എന്നാണ് പലരുടെയും സങ്കടം ☹️😭 FOLLO&SUPPORT 👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻 @ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n#malayalam #malayalamcinema #malayalamtypography #malayalamquotes #malayalamsongs #malayalammovie #malayalamstatus #malayalamactress #malayalamcomedy #malayalamquotes💔 #typographymalayalam #kasargode #malapuram #kozhikode #thrissur #kochi #kollam #pathanamthitta #thiruvanathapuram #alapuzha #alappuzha #kerala #keralasaree #keralawedding #keralagodsowncountry #keralaattraction #support #following #ente_kanthari_pennu'),
(87, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/a3f7c76a3140f4ba42840a3dd8a848b3/5E496A9E/t51.2885-15/e35/71056421_1220367078148322_6544011869123836209_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=102', '❤️❤️❤️❤️❤️❤️❤️\nപറയാൻ മറന്നതല്ല സഖാവെ പറയാതെ തന്നെ നീ അറിയും എന്നുള്ളതുകൊണ്ടാണ് പലപ്പോഴും പ്രണയം മൗനമായി നിൽക്കുന്നത്\n\nകടപ്പാട് :  FOLLO&SUPPORT 👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻👇🏻 @ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n@ente_kanthari_pennu\n\n#malayalam #malayalamcinema #malayalamtypography #malayalamquotes #malayalamsongs #malayalammovie #malayalamstatus #malayalamactress #malayalamcomedy #malayalamquotes💔 #typographymalayalam #kasargode #malapuram #kozhikode #thrissur #kochi #kollam #pathanamthitta #thiruvanathapuram #alapuzha #alappuzha #kerala #keralasaree #keralawedding #keralagodsowncountry #keralaattraction #support #following #ente_kanthari_pennu'),
(88, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/6340670a93690e9dff038d6fd5c7b9ee/5E547C42/t51.2885-15/e35/72699615_518624445588372_277361454357332903_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', 'ചിലതിന്റെ വേദനയൊന്നും അത്രപെട്ടെന്നു പോകില്ല ,ഇങ്ങനെ നീറി നീറി ഇല്ലായ്മ ചെയ്യും...ഒടുക്കം ഒന്നുമാകാതെ മാനത്തോട്ടും നോക്കി ഇരിക്കും ,ദേ ദിത് പോലെ ♥\n.\n.\n.\npc @annieniyas\n.\n.\nFollow mr.@kurutham_kettavan_ \nFollow mr.@kurutham_kettavan_\n.\n.\n#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#lovequotesforher❤️ #lovequotesforlifee#lovequotesforher❤️#lovequotesforhim#relationshipquotes#relationshipgoals❤️#goals❤️#entekeralam🌴#enteidukki😍#entekeralam🌴#entekottayam❤#typography_keralas#typographydesign#typogram#typo#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#loveyourself#nosepiercing#nose#piercings#stud#nosestud#mookuthilove#mookkuthi#entekottayam❤#entekeralam🌴#enteidukki#enteidukki😍#malayalamstatus#malayalamtypography#typographydesign#typography_kerala#typogram .\n.\n@entekottayam \n@idukki.p.o .'),
(89, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/5a003403d0e00ddb8bbe3cfe82b46b0b/5E44752F/t51.2885-15/e35/75616351_146341320011884_7314300815402155019_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', 'തെളിവുകൾ ഉണ്ടായിരുന്നു , സാക്ഷി മൊഴികൾ ഉണ്ടായിരുന്നു , എന്നിട്ടും......എന്നിട്ടും അവരെ വെറുതെ വിട്ടു ,\nഇവിടെ മനുഷ്യനേക്കാൾ വലുതല്ല ഒരു രാഷ്ട്രീയ പാർട്ടിയും മനുഷ്യനും ജീവനുമാണ് വില ,\n.\nപിഞ്ചു കുഞ്ഞുങ്ങൾ സ്വന്തം ഇഷ്ടപ്രകാരമാണ് ഇതിനു മുതിർന്നതെന്നും , അവർ സ്വയം ആത്മഹത്യ ചെയ്യുകയാണ് ചെയ്തതും എന്നൊക്കെയുള്ള ന്യായീകരണം ,\nപാർട്ടിയെ കണ്ണടച്ച് വിശ്വസിക്കുന്ന പോഴന്മാരോട് പറഞ്ഞാൽ മതി .\nമനുഷ്യന്മാരോട് പറയാൻ നിൽക്കണ്ട 🖕\n.\nwe need justice to our sisters\n.\n.\npc @rr_rahulravi\n.\n#weneed#justice#justiceforsisters#rebel#justice#kerala#malayalam#malayalam#keralam#keralam#walayar#walayar'),
(90, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/db6159cea997918657d096f532e30496/5E5881F7/t51.2885-15/e35/73281063_808823562868280_5052244408107878526_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', 'മെഡിക്കൽ സയൻസിൽ ഇതിനു പട്ടിയോമാനിയ കയ്യിലിരിപ്പൊസ്‌കി എന്ന് പറയും , പ്രധിവിധി ഒന്നുമില്ലാത്തൊണ്ട് കൂടെ കൂടി അനുഭവിക്കുക എന്ന് മാത്രേ ഉള്ളൂ , Soul For a Soul 😝 ♥\n.\n.\n.\npc @gabriel__son_of__meena__\n.\n.\nFollow mr.@kurutham_kettavan_ \nFollow mr.@kurutham_kettavan_\n.\n.\n#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#lovequotesforher❤️ #lovequotesforlifee#lovequotesforher❤️#lovequotesforhim#relationshipquotes#relationshipgoals❤️#goals❤️#entekeralam🌴#enteidukki😍#entekeralam🌴#entekottayam❤#typography_keralas#typographydesign#typogram#typo#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#loveyourself#nosepiercing#nose#piercings#stud#nosestud#mookuthilove#mookkuthi#entekottayam❤#entekeralam🌴#enteidukki#enteidukki😍#malayalamstatus#malayalamtypography#typographydesign#typography_kerala#typogram .\n.\n@entekottayam \n@idukki.p.o .');
INSERT INTO `instagram` (`sn`, `types`, `url`, `title`) VALUES
(91, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/ca759d5621d6ffa1943b4b122518de91/5DC27225/t50.2886-16/74726418_763268027451939_6846299325226557317_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=103', 'If my absence doesnt alter you  ഞാനിങ്ങനെ പുറകെ നടക്കുന്നതിൽ ഒരർത്ഥവുമില്ല..അത്രന്നെ ♥\n.\n.\n.\nVideo credit  @_a_s_u_r_a_n_\n.\n.\nFollow mr.@kurutham_kettavan_ \nFollow mr.@kurutham_kettavan_\n.\n.\n#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#lovequotesforher❤️ #lovequotesforlifee#lovequotesforher❤️#lovequotesforhim#relationshipquotes#relationshipgoals❤️#goals❤️#entekeralam🌴#enteidukki😍#entekeralam🌴#entekottayam❤#typography_keralas#typographydesign#typogram#typo#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#loveyourself#nosepiercing#nose#piercings#stud#nosestud#mookuthilove#mookkuthi#entekottayam❤#entekeralam🌴#enteidukki#enteidukki😍#malayalamstatus#malayalamtypography#typographydesign#typography_kerala#typogram .\n.\n@entekottayam \n@idukki.p.o .'),
(92, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/1c4ab7183f1bcffefbfcc97bde305e6e/5E4AC0A1/t51.2885-15/e35/70972489_156430355566870_7759152064883496291_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=110', 'ആയിരംപേരിൽ ഒരാൾക്ക് മാത്രം കിട്ടുന്ന ഭാഗ്യം , കിട്ടിയവരൊന്നും കളയരുത് കിട്ടാത്തവരൊന്നും കരയരുത്...ഓരോന്നിനും അതിന്റെതായ സമയമുണ്ട് ദാസാ ♥\n.\n.\n.\npc @annieniyas\n.\n.\nFollow mr.@kurutham_kettavan_ \nFollow mr.@kurutham_kettavan_\n.\n.\n#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#lovequotesforher❤️ #lovequotesforlifee#lovequotesforher❤️#lovequotesforhim#relationshipquotes#relationshipgoals❤️#goals❤️#entekeralam🌴#enteidukki😍#entekeralam🌴#entekottayam❤#typography_keralas#typographydesign#typogram#typo#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#loveyourself#nosepiercing#nose#piercings#stud#nosestud#mookuthilove#mookkuthi#entekottayam❤#entekeralam🌴#enteidukki#enteidukki😍#malayalamstatus#malayalamtypography#typographydesign#typography_kerala#typogram .\n.\n@entekottayam \n@idukki.p.o .'),
(93, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/73732bafe83f02a1a0ac6b15143510b6/5E4C42F6/t51.2885-15/e35/70796222_142214650374434_4007167142826949197_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=102', 'ഇതുമാത്രേ ഇടുന്നുള്ളോ , വെറുപ്പിക്കലാണ് എന്നതൊക്കെ നിരോധിച്ചിരിക്കുന്നു , ഒരാൾക്ക് ചെയ്തു കൊടുത്ത work ഇഷ്ടായപ്പോ ഇട്ടു എന്ന് മാത്രം 😁\n.\n.\n.\nTAG your മൂക്കുത്തി partner ♥\n.\n.\n.\npc @_snehah\n.\n.\nFollow mr.@kurutham_kettavan_ \nFollow mr.@kurutham_kettavan_\n.\n.\n#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#lovequotesforher❤️ #lovequotesforlifee#lovequotesforher❤️#lovequotesforhim#relationshipquotes#relationshipgoals❤️#goals❤️#entekeralam🌴#enteidukki😍#entekeralam🌴#entekottayam❤#typography_keralas#typographydesign#typogram#typo#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#loveyourself#nosepiercing#nose#piercings#stud#nosestud#mookuthilove#mookkuthi#entekottayam❤#entekeralam🌴#enteidukki#enteidukki😍#malayalamstatus#malayalamtypography#typographydesign#typography_kerala#typogram .\n.\n@entekottayam \n@idukki.p.o .'),
(94, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/f1d1083bd98658d06b2059e8490c29b0/5E5346FF/t51.2885-15/e35/72189869_1299898440187610_6872066614867269523_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=102', 'ക്യാപ്ഷൻ കണ്ടിഷ്ടപ്പെട്ട് അത് പോസ്റ്റായിട്ട് ഇടാൻ പറഞ്ഞത്കൊണ്ട് ഇട്ടതാ , മൂ*ക്കോ എന്നുള്ളത് ഊ*ക്കൊ എന്നാണ് suggestin തന്നത് ,അവസ്ഥ അങ്ങനെയാവും 😁\nഒരുപാടിഷ്ടം അളിയോ 😍\n.\n.\nFollow mr.@kurutham_kettavan_ \nFollow mr.@kurutham_kettavan_\n.\n.\n#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#lovequotesforher❤️ #lovequotesforlifee#lovequotesforher❤️#lovequotesforhim#relationshipquotes#relationshipgoals❤️#goals❤️#entekeralam🌴#enteidukki😍#entekeralam🌴#entekottayam❤#typography_keralas#typographydesign#typogram#typo#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#loveyourself#nosepiercing#nose#piercings#stud#nosestud#mookuthilove#mookkuthi#entekottayam❤#entekeralam🌴#enteidukki#enteidukki😍#malayalamstatus#malayalamtypography#typographydesign#typography_kerala#typogram .\n.\n@entekottayam \n@idukki.p.o .'),
(95, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/1c44c323805bf7b1b1c611b2423e4f16/5E4B7129/t51.2885-15/e35/73032892_172079640581717_7193845993737226820_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'എന്റെ മുടിഞ്ഞ വിധി ,എന്റെ ഒടുക്കത്തെ തീരുമാനങ്ങൾ ആണ് 😁\n.\n.\n.\npc @a_coloured_art\n.\n.\nFollow mr.@kurutham_kettavan_ \nFollow mr.@kurutham_kettavan_\n.\n.\n#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#lovequotesforher❤️ #lovequotesforlifee#lovequotesforher❤️#lovequotesforhim#relationshipquotes#relationshipgoals❤️#goals❤️#entekeralam🌴#enteidukki😍#entekeralam🌴#entekottayam❤#typography_keralas#typographydesign#typogram#typo#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#loveyourself#nosepiercing#nose#piercings#stud#nosestud#mookuthilove#mookkuthi#entekottayam❤#entekeralam🌴#enteidukki#enteidukki😍#malayalamstatus#malayalamtypography#typographydesign#typography_kerala#typogram .\n.\n@entekottayam \n@idukki.p.o .'),
(96, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/367453ae000fd57713c2b78f585fc47a/5E456004/t51.2885-15/e35/70266582_2110261559269603_3489824946184913573_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=107', 'നമ്മളെ സ്നേഹിക്കുന്നവരോട് ചെയ്യാവുന്ന ഏറ്റവും വലിയ ക്രൂരത അവരുടെ സ്നേഹത്തെ പരിഗണിക്കാതിരിക്കുന്നതാണ് ❤\n.\n.\nഇന്ന് വായിച്ചോണ്ടിരുന്നപ്പോൾ strike ചെയ്തൊരു വരികൾ .\n.\nQuote from ദൈവത്തിന്റെ ചാരന്മാർ by @josephannamkutty\n.\n.\n.\n.pc @kattankappi2018\n.\n.\nFollow mr.@kurutham_kettavan_ \nFollow mr.@kurutham_kettavan_\n.\n.\n#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#lovequotesforher❤️ #lovequotesforlifee#lovequotesforher❤️#lovequotesforhim#relationshipquotes#relationshipgoals❤️#goals❤️#entekeralam🌴#enteidukki😍#entekeralam🌴#entekottayam❤#typography_keralas#typographydesign#typogram#typo#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#loveyourself#nosepiercing#nose#piercings#stud#nosestud#mookuthilove#mookkuthi#entekottayam❤#entekeralam🌴#enteidukki#enteidukki😍#malayalamstatus#malayalamtypography#typographydesign#typography_kerala#typogram .\n.\n@entekottayam \n@idukki.p.o .'),
(97, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/7e41f099fdf0e7f8882b7175c7e957ed/5E46A4A9/t51.2885-15/e35/70508663_525800364904930_4267338929496694785_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=110', 'ഈ കഷ്ടപ്പാടിന്റെയെടക്ക് ഇഷ്ടങ്ങളൊക്കെ കണ്ട് പിടിക്കാൻ കഷ്ടപ്പെടുലോ കർത്താവേ 😁\n.\n.\n.\npc to the respective owner\n.\n.\nFollow mr.@kurutham_kettavan_ \nFollow mr.@kurutham_kettavan_\n.\n.\n#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#lovequotesforher❤️ #lovequotesforlifee#lovequotesforher❤️#lovequotesforhim#relationshipquotes#relationshipgoals❤️#goals❤️#entekeralam🌴#enteidukki😍#entekeralam🌴#entekottayam❤#typography_keralas#typographydesign#typogram#typo#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#loveyourself#nosepiercing#nose#piercings#stud#nosestud#mookuthilove#mookkuthi#entekottayam❤#entekeralam🌴#enteidukki#enteidukki😍#malayalamstatus#malayalamtypography#typographydesign#typography_kerala#typogram .\n.\n@entekottayam \n@idukki.p.o .'),
(98, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/831a9e26d129dc782dde863e765efbe9/5E53BE04/t51.2885-15/e35/70511105_483713655802978_3440071165750015664_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=110', 'മൂളികേൾക്കാൻ ഒരാളെ കിട്ടട്ടെ എന്നിട്ട് ബാക്കി പറയാം 😎\n.\n.\n.\npc @illusioner__\n.\n.\nFollow mr.@kurutham_kettavan_ .\n.\n.\n#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#lovequotesforher❤️ #lovequotesforlifee#lovequotesforher❤️#lovequotesforhim#relationshipquotes#relationshipgoals❤️#goals❤️#entekeralam🌴#enteidukki😍#entekeralam🌴#entekottayam❤#typography_keralas#typographydesign#typogram#typo#kuruthamkettavan_quotes #kuruthakkedu_unlimitted#kuruthamkettavan#loveyourself#nosepiercing#nose#piercings#stud#nosestud#mookuthilove#mookkuthi#entekottayam❤#entekeralam🌴#enteidukki#enteidukki😍#malayalamstatus#malayalamtypography#typographydesign#typography_kerala#typogram .\n.\n@entekottayam \n@idukki.p.o .'),
(99, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/fb64f5eba6f4b077bbe4c2adbf5e11b0/5E4A79B7/t51.2885-15/e35/72093092_1518083404990491_6751435661304447123_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=103', 'വാർത്തകൾ കൊണ്ട് പൊതിഞ്ഞ് കെട്ടിയ ഒറ്റക്കണ്ണുള്ള മനുഷ്യരാണ് നമ്മൾ. പ്രതികരിക്കുക, ഉള്ള കണ്ണുകൂടി മൂടിക്കെട്ടി ശവപ്പെട്ടിയിൽ വെക്കുന്നതിന് മുമ്പ്\nProtest in all the possible ways\nConcept and photography : @rishwinjayan \nIn frame : @ezhuthpura .\n.\n.\n#ezhuthpura #entekeralam #photographylovers #photographersofinstagram #imalayali #igwriter #protests #walayar #rapevictim #entekottayam #entekollam #fortkochi #mmadethrissur #mykannur #photos #opinionswelcome'),
(100, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/fdcfa9b497b9a12dad759d699c1c442a/5E5EA585/t51.2885-15/e35/70576219_998801800460166_8012727734330756722_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', 'കൊന്നവരും, കൊല്ലാൻ കൂട്ടുനിന്നവരും എല്ലാവരും ചേർന്ന് പിറവി ആഘോഷിക്കുന്ന നാട്\n.\n.\n.\n#ezhuthpura #keralapiravi #socialise #entekeralam #igwriter #malayalamtypography #entekottayam #imalayali #mykozhikode #beingmalayali #mykannur #entekollam #mmadethrissur #fortkochi'),
(101, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/8d6ae00eb55361c5f50618bf54b66fe9/5DC24A41/t50.16885-16/10000000_168679920917157_414827986424172973_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=109', 'കോളേജ് കാലഘട്ടത്തെ അനശ്വരമാക്കിയ ഒരു പ്രണയം നമുക്കെല്ലാവർക്കും ഉണ്ടായിരിക്കും. പലരും പൂർത്തികരിച്ചിരിക്കാം പലരും പാതി വഴിയിൽ ഉപേക്ഷിച്ചിരിക്കാം. പക്ഷെ ആരുമൊരിക്കലും മറന്നിട്ടുണ്ടായിരിക്കില്ല. ഇതെന്റെ കഥയാണ്. പാതി വഴിയിൽ ഉപേക്ഷിക്കാൻ തോന്നാത്ത, പൂർത്തീകരിക്കും എന്നൊരു ഉറപ്പുമില്ലാത്ത വളരെ വൈകി ഞാൻ തിരിച്ചറിഞ്ഞ എന്റെ പ്രണയത്തിന്റെ കഥ. അവൾ, അവളെനിക്കെന്നും ഒരു അത്ഭുതമായിരുന്നു. ❤️ please rotate your phone to watch the video'),
(102, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/2aba1dd9807e8cd371a8f12120dac20f/5E4951CD/t51.2885-15/e35/72155420_689528868200994_9116408341533306369_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', 'പാതി (കവിത)\n------------------------------------------------------------\nഇതുപോലുള്ള പോസ്റ്റുകൾ കാണാൻ പേജ് ഫോളോ ചെയ്യുക 🤗\nPlease do share your feedbacks in the comment box ------------------------------------------------------------\n#ezhuthpura #poemsofinstagram #poetryofinstagram #entekeralam #lovepoem #writersuniverse #entekottayam #fortkochi #mykozhikode #entekollam #trivandrumvibes #wayanadvibes #readersofinstagram #lifepartner'),
(103, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/070c55d681e56c675ad141cc74aa8f9b/5DC25FDD/t50.2886-16/74803721_111902693370924_7719345619316162933_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', 'ഏറ്റവും നല്ല അദ്ധ്യാപകർ ❤️\n------------------------------------------------------------\nഇതുപോലുള്ള പോസ്റ്റുകൾ കാണാൻ പേജ് ഫോളോ ചെയ്യാൻ മറക്കല്ലേ 🤗\n------------------------------------------------------------\n#ezhuthpura #imalayali #igwriter #speaker #storytellers #bestteacher #mylife #entekeralam #beingmalayali #fortkochi #entekottayam #mykannur #entekollam #mmadethrissur #mypalakkad #kanyakumarians #trivandrumdays #followme💋 #likethepost #sharethepost'),
(104, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/2fd0a2528599845f8016982de5e3b1e5/5DC25AE6/t50.2886-16/73245131_956428688058226_4401197815082624863_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=110', 'നിങ്ങൾ ജീവിക്കുകയാണോ അതോ മറ്റെന്തെങ്കിലും ജോലി ചെയ്യുകയാണോ?\n------------------------------------------------------------\nഇതുപോലുള്ള പോസ്റ്റുകൾ കാണാൻ പേജ് ഫോളോ ചെയ്യാൻ മറക്കല്ലേ 🤗\n------------------------------------------------------------\n#ezhuthpura #entekeralam #fortkochi #edutokmalayalam #lovemalayalam #mykannur #entekottayam #entekollam #kasarkode #mykozhikode #mmadethrissur #videoinstagram #oneminutevideo'),
(105, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/c9213c6d82916d2b8f840a51382dd390/5DC210B0/t50.2886-16/72876574_160190748392349_2409671880962418796_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=109', 'തളർന്ന് പോയിട്ടുണ്ടാവരുത്‌ 💚\n------------------------------------------------------------\nകൂടുതൽ പോസ്റ്റുകൾക്ക് പേജ് ഫോളോ ചെയ്യാൻ മറക്കല്ലേ 🤗\n------------------------------------------------------------\n#ezhuthpura #entekeralam #entekottayam #mykozhikode #mykannur #gnpc #videotutorial #october #mmadethrissur #imalayali #beingmalayali #shares #beingmalayali #smile😊 #lifesayings #followyourdreams #dreams #dreamhigh'),
(106, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/66a068d07e9a41c6beb3f00b7a91dddb/5E4E65AE/t51.2885-15/e35/73251146_805443709889856_9076234258924767792_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', 'എഴുത്ത്, ഏറ്റവും നല്ല വിനോദങ്ങളിലൊന്ന് 💚\n.\n.\n.\n#ezhuthpura #entekeralam #imalayali #entekottayam #fortkochi #mykannur #mmadethrissur #kanyakumari #entetrivandrum #entepathanamthitta #mykozhikode #entekollam #igwriter #blogger #malayalamwriters #malayalamtypography'),
(107, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/95cf79edce4429ed30172d7bf9fd526a/5E53F299/t51.2885-15/e35/71704510_148688723047014_3896587442791675084_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', 'ദൈവം... ആ വാക്ക് വിഭജിച്ചപോലെ മറ്റൊരു വാക്കും ചിലപ്പോൾ വിഭജിച്ചിട്ടുണ്ടായിരിക്കില്ല. അഭിപ്രായങ്ങൾ കമന്റ് ബോക്സിൽ രേഖപ്പെടുത്താം. .\n.\n.\n#ezhuthpura #entekeralam #entekottayam #entepathanamthitta #mykannur #mmadethrissur #palakkad #munnar #malakkapara #piravom #entealappuzha #fortkochi #gods #opinion #trueorfalse #commentyouropinion'),
(108, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/a8b10f084a91d1d9c13bdb79fbfe987a/5E6503BD/t51.2885-15/e35/70790229_437886980191718_8594270104433210282_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=102', 'അവൾ അത്രമേൽ മറക്കാൻ സാധിക്കാത്തവൾ\n.\n.\n.\n#ezhuthpura #entekeralam #lovelovelove #igwriter #lovequotes #entekottayam #imalayali #neverforgether #stillloveyou #beingmalayali #malayalamtypography #mykozhikode #fortkochi #mykannur'),
(109, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/72893c40d18754a32fc526074e6341e0/5E6319A3/t51.2885-15/e35/70251390_133576284661136_3981715641703824988_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', 'നമ്മൾ ഒന്നാണ്, ഒറ്റക്കെട്ടാണ് 💚\n#notoracism m #notobodyshaming .\n.\n.\n#ezhuthpura #igwriter #bodyshaming #racism #entekeralam #entekottayam #socialissues #fortkochi #mykannur #mmadethrissur #palakkad #thalasseryinstagram #entekollam #mykozhikode #holdeachother #instalove #shareandcare #weareallone'),
(110, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/158f42976d93e94ed55cb51db2cad6a8/5E43EAE9/t51.2885-15/e35/74948902_503044370544270_5077403928427803144_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', 'ആണും പെണ്ണും . \n#kadalaass_official \n#kadalaass'),
(111, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/64b1ab263ff1f46614ab9e3c3c1b927d/5E54B5B2/t51.2885-15/e35/75601589_427039521288099_2863954510294526162_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=103', 'മഴയും കുഴിയും . \n#kadalaass_official\n#kadalaass'),
(112, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/c7be0dac3cc1eb021ce23d736ed2534c/5E43F249/t51.2885-15/e35/72704472_166071224452520_211314844577481322_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', 'ഇപ്പോളാ ഇതൊക്കെ അറിയുന്നത്... .\n\n#kadalaass_official\n#kadalaass'),
(113, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/8d1d9e00caffe14765e2d9a0e9dcc68e/5E5F02BD/t51.2885-15/e35/72279046_105210257521939_746403159457901406_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=107', 'സത്യമല്ലേ... .\n.\n\n#kadalaas_official \n#kadalaass'),
(114, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/ca6ecbf85d5c44e7c23d18d4cbac0edb/5E46FA66/t51.2885-15/e35/72586427_206940520304521_6401260468056442135_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', 'ഹൃദയത്തിൽ വീണത് . \n#kadalaass_official \n#kadalaass'),
(115, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/bec1eb20be5ae19ea5422468ca1c80c5/5E4D9BAE/t51.2885-15/e35/74353442_1453398671484392_4445735177051903789_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', 'ഉറുമ്പും കോഴിയും #kadalaas_official \n#kadalaass'),
(116, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/95d5473c6e2ff1f04cc6b03ce177dc78/5E3E39AD/t51.2885-15/e35/71530419_531218734106174_8045799291637994996_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=102', 'കൂടെയുണ്ട് \n#kadalaas_official \n#kadalaass'),
(117, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/df1d79b66eec598c8d3f9a834bed284a/5E3DFDCD/t51.2885-15/e35/71721499_2506857699560962_6850000951792029725_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=110', 'ഓരോ മരണങ്ങൾ \n#kadalaas_official \n#kadalaass'),
(118, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/88488874426e7b41c4cd5d465a8aa928/5E4A12EB/t51.2885-15/e35/71153600_976352306061072_2410951752535763468_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', 'കേരളത്തിനിന്ന് 63...\n#കേരളപ്പിറവി \n#kadalaass #kadalaas_official'),
(119, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/6b36cce3857b34fe4e080d56533948db/5E4817A1/t51.2885-15/e35/73398036_430209701013839_1576165266276629890_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=111', 'പിന്നെയാ കടമൊന്നു \nവീട്ടാനവൻ നെട്ടോട്ടമോടിടും മരണം വന്ന് മൂക്കിൽ \nപഞ്ഞി കേറ്റും വരെ... #kadalaass #kadalaass_official #malayali #kerala'),
(120, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/19152f52474feb29ea32443df32e64b3/5E568DCC/t51.2885-15/e35/72579507_2583529311928192_3101719904553227519_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', 'ഇന്ത്യയിൽ ഏറ്റവും അധികം ഉച്ചരിക്കപ്പെട്ട കള്ളം \n#kadalaass #kadalaass_official #women #india'),
(121, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/602b41ef067cbc4b053d079e195ed728/5E420C8A/t51.2885-15/e35/72690532_751804968622001_569563293335346375_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'ഉത്തരവാദിത്വം . \n#kadalaass \n#kadalaass_official'),
(122, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/9dcecd2593ab959fb71bc65f273156a3/5DC23105/t50.2886-16/76636403_268535040698418_3735403213933907159_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', 'that കലിപ്പൻ ചങ്ക് 😡😍😊\n🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸\nTYPO 217 🔹🔸🔹🔸🔹🔸🔹🔸🔹🔹🔸🔹🔸🔹🔸🔹🔸🔹\nfollow @the_typotive 🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹\nഇതൊക്കെ sample പൂരം മുഴുവൻ കൂടണെങ്കിൽ പ്രൊഫൈലിൽ കേറി കൂടെ കൂടിക്കോ 😘\n🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹\n➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️\n#kerala🌴 #malayalamquotes💔 #malayalamtypography #malayalamcinema #love #motivationalvideos #malayalamstatusvideos #mallugram #lifequotes #relationshipgoals #malayalamstatus #motivation #art #malayalamtrolls #positivevibes #karikku #malluvideos #motivationalquotes #whatsappstatus #friends #feelgoodquotes #angry #mallugirl #typotivetypography #yakshikunjubgm #instagood #photooftheday #friendshipgoals💕 #chunks #friends👭 🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸\nfollow @the_typotive\n🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸\nfollow @the_typotive\n🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸\nfollow @the_typotive\n🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸🔹🔸 ➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️\n𝘿𝙈 4 𝘿𝙀𝙎𝙄𝙂𝙉𝙄𝙉𝙂, 𝙏𝙔𝙋𝙊𝙂𝙍𝘼𝙋𝙃𝙔 & 𝙑𝙄𝘿𝙀𝙊 𝙒𝙊𝙍𝙆𝙎\n➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️➡️\n[ Check Out Profile For More ]'),
(123, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/587df5d773c1fe3a912674b1241ddd10/5E5BF209/t51.2885-15/e35/72275476_120282249397057_3867940465311461025_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', 'Sample'),
(124, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/84e742e291e604f1a3c3ab838aefb29f/5E64F73E/t51.2885-15/e35/72973315_106291540703422_1973397283072152056_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=110', 'Sample'),
(125, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/5b1c70ccdf8a69c3fb7c750753e7fc5b/5E461228/t51.2885-15/e35/72847026_414244292846116_8825845106390949404_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', 'Sample'),
(126, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/547a6fa219be3e4f7be49b8fe9447086/5E3FB771/t51.2885-15/e35/71546359_154509372444508_6122153570131780395_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', 'Sample'),
(127, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/1769ae38ca5468c0585386befbf5ed04/5E4E00C6/t51.2885-15/e35/74388358_945234352530424_6000543338531792261_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', 'Sample'),
(128, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/946c608b945c6d90e40b8ebede5328af/5E5D6FA8/t51.2885-15/e35/72632176_2283685851878016_3665712277465910746_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', 'Sample'),
(129, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/1027d10e5d21d9bd750359d555c39a60/5E623C46/t51.2885-15/e35/73144914_147767556490916_3094502251687805964_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', 'Sample'),
(130, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/c8ef3eeaa2ad4eb1b78079a2b50bef74/5E5CF1DE/t51.2885-15/e35/73457378_139731050664263_1678666686416858398_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=111', 'Sample'),
(131, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/89e8164cc111add2924197f9fbf40f21/5E569EDC/t51.2885-15/e35/75208801_2278766138902193_3569145730954941479_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'Sample'),
(132, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/db9d8c2f1f71ede335cc5cd7b3aa0d3e/5E500B99/t51.2885-15/e35/73401820_167183961144355_1525261596754274762_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=107', 'Sample'),
(133, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/faf3baec5c2f70bca49214b4c5fcf0bf/5E54BDE0/t51.2885-15/e35/73685219_2467506770203445_460028745293495908_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'Sample'),
(134, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/9757a8745c15b9bfea287259fe31ed14/5E581024/t51.2885-15/e35/72213509_722822208185173_7654628028062579445_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', 'Sample'),
(135, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/1d31af5b7fc1a8a15832594b24652006/5E4FF766/t51.2885-15/e35/72602012_794649064282065_589967122600719903_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=109', 'Sample'),
(136, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/2ce568ab43390d96adec12e5d3837796/5E4A2271/t51.2885-15/e35/76742570_158176975276413_2680235722383857490_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=109', 'Sample'),
(137, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/b7e8fab1fcf8e2fde3a9f9addb447645/5E53A0DE/t51.2885-15/e35/70906262_160345685052696_8830387665757522096_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', 'Sample'),
(138, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/f35a9786d746b73e85911a1dca28081a/5E499B27/t51.2885-15/e35/74796185_238896417074279_621334145960966009_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=111', 'Sample'),
(139, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/e215e58e8d20343af8e97e63d7160bee/5E48AE29/t51.2885-15/e35/75272184_2190080794626212_7168765437387197196_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', 'Sample'),
(140, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/d93c2c67d5ffdbac42d529916b5854f6/5E5E8036/t51.2885-15/e35/75252767_561858441285412_5288926188208267367_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', 'Sample'),
(141, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/96a185fc5dba0a383a9e52abc06c0c22/5E4EE9CC/t51.2885-15/e35/74891094_475322056409917_1229256252526116966_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', 'Sample'),
(142, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/7db5521c3f79d618908a52fe903bc0ad/5E4B01A2/t51.2885-15/e35/74893083_504235317094537_7848130945449068305_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', 'Sample'),
(143, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/7aa42d15a47a5e93015e95cd9e37a1bb/5E3F12D9/t51.2885-15/e35/71738666_2431935723733331_5968014076203170716_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=111', 'Sample'),
(144, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/c9f42b30740dde76bcb649987a220d6c/5E5B296A/t51.2885-15/e35/70598783_173722973800759_5339123008321244477_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=107', 'Sample'),
(145, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/d4390f296335e0f353e2737bfef9ddec/5E6410AA/t51.2885-15/e35/72588600_145535376721321_2015133478496568721_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', 'Sample'),
(146, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/6458e1c56888de82971c9aa90fbc6540/5E3F32A8/t51.2885-15/e35/74971925_137673624198393_33407950290490838_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=109', 'Sample'),
(147, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/fb5e7c9050f615594e2cb0bf96856d0b/5E523D4B/t51.2885-15/e35/71892627_198862084580718_6989645961017141311_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', 'Sample'),
(148, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/f1cb8e3bff258d66bd2fa89b9527a856/5E3F21EE/t51.2885-15/e35/72777581_145447326716291_4948223391949385530_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=111', '#Save food\n\n#kuripp4vzm #mallureposts #malappuramsolved #malayali #malappuramneeds #malayalammotivationalvideos #malayalamquotes💔 #malayalamqoute #mallureposts #mallus #relationshipquotes #realationshipquotes #friendshipquotes #love #likeforfollow #malayalee#malayalamtypography'),
(149, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/22a8121aaf2bf4930152bd586d93e2f5/5E4B410B/t51.2885-15/e35/71754093_467248567247045_5564345728794494283_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'കൂടെ ഉള്ള എല്ലാം #kuripp4vzm #mallureposts #malappuramsolved #malayali #malappuramneeds #malayalammotivationalvideos #malayalamquotes💔 #malayalamqoute #mallureposts #mallus #relationshipquotes #realationshipquotes #friendshipquotes #love #likeforfollow #malayalee#malayalamtypography'),
(150, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/f3b57b6cbf2e47fd6b80d510294a42cc/5E47AA70/t51.2885-15/e35/70119652_169219997563873_5848296458514070158_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', 'ചില സ്വകാര്യം\n\n#kuripp4vzm #mallureposts #malappuramsolved #malayali #malappuramneeds #malayalammotivationalvideos #malayalamquotes💔 #malayalamqoute #mallureposts #mallus #relationshipquotes #realationshipquotes #friendshipquotes #love #likeforfollow #malayalee#malayalamtypography'),
(151, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/12bf1b35840efcba1b9c44ab920e31f4/5E5A41C5/t51.2885-15/e35/71707824_1410216139132746_8444683833644644580_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', 'അതും ഒരുതരം ഒഴിവാക്കലാണ്\n\n#kuripp4vzm #mallureposts #malappuramsolved #malayali #malappuramneeds #malayalammotivationalvideos #malayalamquotes💔 #malayalamqoute #mallureposts #mallus #relationshipquotes #realationshipquotes #friendshipquotes #love #likeforfollow #malayalee#malayalamtypography'),
(152, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/32bb3164fa277fabefdff1a2781b4c5a/5E5CCCAC/t51.2885-15/e35/73033120_171384353915556_1707559764295521655_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', 'അത് അങ്ങനെ തന്നെ യാ....😀😀😀😀\n@luttapi._ \n#kuripp4vzm #mallureposts #malappuramsolved #malayali #malappuramneeds #malayalammotivationalvideos #malayalamquotes💔 #malayalamqoute #mallureposts #mallus #relationshipquotes #realationshipquotes #friendshipquotes #love #likeforfollow #malayalee#malayalamtypography'),
(153, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/ea67cd1f8b986be27379a8efcc586c80/5E3F0BD0/t51.2885-15/e35/72876159_467836827183218_3552532688098716350_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=102', 'അമ്മ ഇഷ്ടം 💓💓💓😍💔 .\n #kuripp4vzm #mallureposts #malappuramsolved #malayali #malappuramneeds #malayalammotivationalvideos #malayalamquotes💔 #malayalamqoute #mallureposts #mallus #relationshipquotes #realationshipquotes #friendshipquotes #love #likeforfollow #malayalee#malayalamtypography'),
(154, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/a26d79cc8d5579532538a07c90890883/5E406C7E/t51.2885-15/e35/75243095_527040128111280_5296083519308428830_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', 'അങ്ങനെ തന്നെ\n💓💓💓😍💔 .\n #kuripp4vzm #mallureposts #malappuramsolved #malayali #malappuramneeds #malayalammotivationalvideos #malayalamquotes💔 #malayalamqoute #mallureposts #mallus #relationshipquotes #realationshipquotes #friendshipquotes #love #likeforfollow #malayalee#malayalamtypography'),
(155, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/3342d00368769fc08f0f691b15ab7d0b/5E5788DB/t51.2885-15/e35/70912136_721602991673667_3133376549510846557_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=103', '#Justice for sister  #kuripp4vzm #mallureposts #malappuramsolved #malayali #malappuramneeds #malayalammotivationalvideos #malayalamquotes💔 #malayalamqoute #mallureposts #mallus #relationshipquotes #realationshipquotes #friendshipquotes #love #likeforfollow #malayalee#malayalamtypography'),
(156, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/eb3569008368e482787c1f8a3f93dc3f/5E4CA85E/t51.2885-15/e35/71727263_1364590300390453_9166058190586345288_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', '💓💓💓😍💔 .\n #kuripp4vzm #mallureposts #malappuramsolved #malayali #malappuramneeds #malayalammotivationalvideos #malayalamquotes💔 #malayalamqoute #mallureposts #mallus #relationshipquotes #realationshipquotes #friendshipquotes #love #likeforfollow #malayalee#malayalamtypography'),
(157, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/0d3c8df8223f24d65fe1fff49bcbab76/5E4D030F/t51.2885-15/e35/75640813_722291381618877_3597624726469573216_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=109', '😍😍😍 #kuripp4vzm #mallureposts #malappuramsolved #malayali #malappuramneeds #malayalammotivationalvideos #malayalamquotes💔 #malayalamqoute #mallureposts #mallus #relationshipquotes #realationshipquotes #friendshipquotes #love #likeforfollow #malayalee#malayalamtypography'),
(158, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/77c2ba355ae9aa58fe1ea82a4029096e/5E50A234/t51.2885-15/e35/73420154_103777164337896_450130569501649792_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', 'മുള്ളിനെ മുള്ള് കൊണ്ട് എടുക്കുന്നില്ല അത് പോലെ\n\n#kuripp4vzm #mallureposts #malappuramsolved #malayali #malappuramneeds #malayalammotivationalvideos #malayalamquotes💔 #malayalamqoute #mallureposts #mallus #relationshipquotes #realationshipquotes #friendshipquotes #love #likeforfollow #malayalee#malayalamtypography'),
(159, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/da0a3b90c376f2dd674ce6bc9b8cd443/5E416DC0/t51.2885-15/e35/70513344_418121778896677_5022656601092710605_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', 'അവന്റെ  result  അവനേക്കാളും അസ്വത്തിച്ചത് നമ്മളായിരിക്കും #kuripp4vzm #mallureposts #malappuramsolved #malayali #malappuramneeds #malayalammotivationalvideos #malayalamquotes💔 #malayalamqoute #mallureposts #mallus #relationshipquotes #realationshipquotes #friendshipquotes #love #likeforfollow #malayalee#malayalamtypography'),
(160, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/ad8567380a63df8f23eb058118458fae/5E625710/t51.2885-15/e35/73497474_726021107879367_8263986861665546456_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=1', 'നീ നിന്നിടം ശൂന്യമാണെന്നും...!'),
(161, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/e0d37afe9144d49a89fbf2d7882c389a/5E4BFD15/t51.2885-15/e35/72604357_156047112269817_5612689039786606538_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=109', 'ക്ഷമ... 😇'),
(162, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/73f9939f12856c807575c473254dae79/5E634AD0/t51.2885-15/e35/72081771_2368931543359606_6033319519636168207_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=110', 'ബിനീഷേട്ടനോടൊപ്പം!\n@bineeshbastin'),
(163, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/3555f4b03c4f82c7e999f7c3490d92a8/5E407A08/t51.2885-15/e35/71521237_189449112096737_5622295973561190840_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'ഇടയ്ക്ക് കുറച്ചൊക്കെ അകന്നു നിൽക്കണം...!'),
(164, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/2913d22d5ea78ffc4bfde5aca90c2fd0/5E49B881/t51.2885-15/e35/76740073_100274308079120_4899451396819209430_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=108', 'ജീവിതത്തിലെ ചില ഓർമ്മപ്പെടുത്തലുകൾ...!'),
(165, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/786fa8fd6d070177927da1866d36d482/5E6100E2/t51.2885-15/e35/73423622_1134617500077094_7187088445420306744_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=110', 'നീതി.... !!!!'),
(166, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/ccd2008f5abe639a910634137d0976c6/5E4226D2/t51.2885-15/e35/71195151_968055083551643_2904484526339736760_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=111', 'സ്വയം കോമാളികൾ ആയി മാറാറുണ്ട് ഇടയ്ക്ക്....'),
(167, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/de73a5aee76130df330551dc9827bac9/5E624099/t51.2885-15/e35/71837328_755291438232233_5915443328875938556_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'ദീപാവലി ആശംസകൾ...! #deepavali #crackers #sweets'),
(168, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/0621a84b456442086e0a9c88af0b6806/5E61DDFA/t51.2885-15/e35/72755944_556497518452759_7129848977696892351_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=107', 'ഒരു വാക്കും പറയാതെ അകന്നു പോകുന്നവർ...!'),
(169, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/c7c30c7d6279492c420cf52ea916abb3/5E3FFE69/t51.2885-15/e35/73182989_176422026844704_2077203250422053826_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', 'അത്രയും പ്രിയപ്പെട്ട ചിലർ നമ്മെ മനസ്സിലാക്കാത്ത അവസ്ഥ...'),
(170, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/b43558d3de0d46df2cbeb82a92706ac8/5E4943DA/t51.2885-15/e35/75566985_414747002536209_4588820241436204816_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=109', 'നമ്മളെ സ്നേഹിക്കുന്നവർക്കായും കുറച്ചു സമയം ചെലവഴിക്കണം...'),
(171, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/2b67e790bc57e1eeace8262bd0d33b22/5E4C61C4/t51.2885-15/e35/71186045_102468364477990_1974864268517495966_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=110', 'അകന്നു പോയവരെ \nകുറിച്ചോർത്തിട്ടെന്ത് നേട്ടം...'),
(172, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/002c8e39157dfe060d41d1b3df33fd63/5E584AE3/t51.2885-15/e35/75484616_162910204793380_3182520256795906467_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', 'മുറിവുകളാണെന്റെ കവിതകളിലാകയും മുറിവുകൾ 💔\n@otta_chirakulla_poombatta . .\nവരികൾ : [ @aks_black99 ]\n. . \n#ottachirakullapoombatta #otta_chirakulla_poombatta #verukalofficial #verukal #branthan #kattan #kattankappi #malayalam #malayalamquotes💔'),
(173, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/4119d8c45f59b7b00152febdd486aa42/5E5ACF15/t51.2885-15/e35/74645227_538261110285509_3134765999710647937_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'Sample'),
(174, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/6ec415d0d92f0c54ee784a07973e94d5/5E4F5E80/t51.2885-15/e35/69768945_181118326351118_4881950500918015213_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=102', 'Sample'),
(175, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/62ffb6010e1cc6b80a95c8b674736199/5E5C8CF1/t51.2885-15/e35/74366702_130790974561427_6023884870215090849_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=111', 'അതെന്ത് ചോദ്യാണ് 🤗🤗\n@otta_chirakulla_poombatta .\n.\nവരികൾ : [ @amrutha_b_p ]\n.\n.\n. . #otta_chirakulla_poombatta\n#malayalamlove #malayalamtypography #malayalamstatus #malayalamquotes #braanthan #kerala #palakkad #thrissur #kottayam #verukalofficial #malayalam #typography #tovino #pranayam #kavithakal #nanokadhakal #ezhuthu'),
(176, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/91173ae353d0cd0fc1ee6cd1f571d281/5E431DC3/t51.2885-15/e35/74581075_761391507637060_6177997344840984247_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', 'നീയാകുന്ന തുരുത്ത് ☹️\n@otta_chirakulla_poombatta . \n#otta_chirakulla_poombatta \n#malayalam #malayalamquotes #kavithakal #malayalamtypography #typography #malayalamstatus #malayalamtypography #malayalamquotes #entekeralam #ezhuth #pranayam #kottayam #palakkad #thrissur #Kochi #kollam #nanokadhakal #kavitha #malayalamcinema #malayalamlovequotes #malayalamlove #lovemalayalam  #typo #idukki #braanthan #mallupage'),
(177, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/3e1aab338232e013734664bcc48344d2/5E5A1181/t51.2885-15/e35/71899413_2139238243037124_5923283326109219610_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', 'Sample'),
(178, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/9fd5975662c69a43d6b00c40298b6088/5E615F5C/t51.2885-15/e35/73138405_262509201295540_6532031756737324271_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', 'Sample'),
(179, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/f255f92a329916031faf6a064f538ce2/5E62EF2A/t51.2885-15/e35/74974777_418793642155048_548257928524735076_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', 'നീ വന്ന്‌ മിണ്ടും വരെ🙌😊ഞാൻ ഇങ്ങനെ ഒറ്റപ്പെട്ട് തന്നെയായിരിക്കും😊\n@otta_chirakulla_poombatta .\n.\n#otta_chirakulla_poombatta .\n#malayalam #malayalis #malayalamcinema #nanokathakal #nanokavithakal #lovemalayalam #braanthan #kerala #keralagallery #keralagram #palakkad #kannur #wayanad #kochi'),
(180, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/d18268a7e930ca1f66ae3fd4d1fbaa23/5E3E10C4/t51.2885-15/e35/70509689_2346039118841718_6578376761013287052_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', 'കുറച്ച് സമയത്തേക്ക് എങ്കിലും നമ്മടെ മൂഡ് മാറ്റുന്ന ചില ചിരികളുണ്ട് 😄\n@otta_chirakulla_poombatta .\n.\n#otta_chirakulla_poombatta .\n#malayalam #malayalee #malayalis #malayalamcinema #malayalamquotes #verukalofficial #kerala #prithviraj #prithvirajsukumaran #tovino #kerala #keralagram #keralagallery #keralatalents #braanthan #thoolika @kadhakaran @kalosmi_ #kattankappi'),
(181, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/d6a19d63915a5fafabaacb456e974c40/5E5603DA/t51.2885-15/e35/70725711_971177976550830_8845667838543354606_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', 'Sample'),
(182, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/facaf85d539a06e436ced2f43c143f58/5E55537D/t51.2885-15/e35/70760349_389466731993991_1889349047051918743_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=103', 'Sample'),
(183, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/daee3c753ab498dfda1b5955ed61f9c3/5E61BC77/t51.2885-15/e35/71278147_1177941139066317_5317465392081620885_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=109', 'Sample'),
(184, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/91034216b64f851642c6c1287bbe15c7/5DC246BE/t50.2886-16/77241589_2722248324463345_77596650113794921_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', 'ബാബു നമ്പൂഡിഡി 💗\n.\n.\n.\n.\n#nanokadhakal #malayalamkavitha #mallugram#malayali  #malayalamtypography#malayalmcaliography#dq #dulquer#dulquersalmaan#entekottayam#kerela #kerelam#wondersofkerela#travell#mallutraveller#maalayaly#motivationalquotes #goodmorning  #kerala #malayaly #malayalamstatus #typovideo\n\nTo download video status visit youtube page - \"maalayaly \"\nTo download visit - fb page - \"malayaly \"\nSharechat download search for \"maalayaly\"'),
(185, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/9fe3a285dbe1aac0aefe93a1074135a7/5E41F970/t51.2885-15/fr/e15/p1080x1080/70504980_534889303965510_7046725916842559263_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', 'Sample'),
(186, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/e85bfe3c88788048ac1ff7f9c2f477bb/5E4FC36B/t51.2885-15/fr/e15/p1080x1080/75440993_427352044825471_4122610841196176330_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=107', 'Sample'),
(187, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/76b0cbaea56503637495eed8723e73a0/5DC270F9/t50.2886-16/76651840_144779183583785_8402591529629169198_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'Sample'),
(188, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/bcfec8f270bda4fdf3c026502bf4e1f5/5DC22900/t50.2886-16/74950025_554609248416293_5354346357863222126_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'Sample'),
(189, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/50b0ef1afcd708012bb99c2ebeebd907/5DC25C4D/t50.2886-16/75351788_1280394465476969_3877065347437264755_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'Sample'),
(190, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/d36fabfdeb020bf4c49706529c12f58a/5DC28C54/t50.2886-16/74394182_229354574713404_1933824505817357546_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=100', 'Sample'),
(191, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/21dd4e0ed91c2692aa95caf149e5407e/5DC26405/t50.2886-16/74272362_452648705371701_506650176293347090_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=109', 'Sample'),
(192, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/d81b6f37b9ebd53e46b3014675cb2796/5DC23FA3/t50.2886-16/74716288_142106123766961_3463572031645243358_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=101', 'Sample'),
(193, '2', 'https://scontent-ort2-1.cdninstagram.com/vp/8a6c995ed2a9832abc80fed7a4820208/5E44BCB5/t51.2885-15/e35/72789104_764511730657470_5883027328184229697_n.jpg?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=106', 'Sample'),
(194, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/aa41310d4629a3ea529e1b53fa687f4e/5DC22576/t50.2886-16/73202535_2309428159368665_1319363020855509814_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=105', 'Sample'),
(195, '1', 'https://scontent-ort2-1.cdninstagram.com/vp/507c18af28f880826a3cf04062f447ab/5DC253B2/t50.2886-16/72251774_535258103875096_1108521812321527158_n.mp4?_nc_ht=scontent-ort2-1.cdninstagram.com&_nc_cat=104', 'Sample');

-- --------------------------------------------------------

--
-- Table structure for table `instagramlastid`
--

CREATE TABLE `instagramlastid` (
  `sn` int(11) NOT NULL,
  `pageid` varchar(100) NOT NULL,
  `lastid` varchar(200) NOT NULL DEFAULT 'NA'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `instagramlastid`
--

INSERT INTO `instagramlastid` (`sn`, `pageid`, `lastid`) VALUES
(1, 'verukal', '2169130188431342548'),
(2, 'valappottukal', '2169447641402173920'),
(3, 'kadalaass_official', '2169530652005007674'),
(4, 'broi_typography', 'NA'),
(5, 'malayaaaly', '2167542352468826994'),
(6, 'anonymouz_soul', '2168309241109283606'),
(7, 'kurutham_kettavan_', '2169002087425554584'),
(8, 'vakkukal_viriyunnidam_vavi_66', 'NA'),
(9, 'balyakaalasakhi', '2169460454950459853'),
(10, '_pattam', '2169387431237005798'),
(11, 'pakshikal', '2169413961451566062'),
(12, 'manamezhuth', '2168956156005207909'),
(13, 'aruvi_ozhukunnaval', '2169009572882631196'),
(14, 'psychochan__', '2169531825328007110'),
(15, 'kaaalann', '2169035367726412329'),
(16, 'kattankappi2018', '2169074596532696861'),
(17, 'ente_kanthari_pennu', '2168962758879997806'),
(18, 'kuripp_____', '2169049836019913508'),
(19, 'kuruthamketton', '2168996327664727154'),
(20, 'the_typotive', '2168759954164740123'),
(21, 'koottali_', '2168389516230271598'),
(22, 'komaali_official', '2167455082769323917'),
(23, 'vella_varakal', '2169516009765298105'),
(24, 'otta_chirakulla_poombatta', '2169026076210396726'),
(25, 'green_tree_of_hopes', '2169039389443137802'),
(26, 'chinthakalude_chavattu_kutta', '2168277749630026593'),
(27, 'chekavanofficial', '2166907255756552799'),
(28, 'niram_ezhuthu', '2139709456187275448'),
(29, 'maalagayuda_ezhuthukal', '2168750974411635536'),
(30, 'ezhuthpura', '2169419521043930857'),
(31, 'ezhuthiloode', '2169020812182690002');

-- --------------------------------------------------------

--
-- Table structure for table `likes`
--

CREATE TABLE `likes` (
  `sn` bigint(20) NOT NULL,
  `statusid` varchar(50) NOT NULL,
  `userids` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `likes`
--

INSERT INTO `likes` (`sn`, `statusid`, `userids`) VALUES
(4, '6', ',3,1,'),
(5, '8', ',14954,4,5,36126,7,1934,25714,9,10,23553,11,9790,14,31767,35770,16,19,36914,25,8,31,32,38,39,27,45,36190,55,64,54,25303,'),
(6, '11', ',7,9,9790,14,36914,8,54,'),
(7, '12', ',1934,9,9790,14,36914,54,'),
(8, '13', ',8,36126,9,14,21802,19412,36914,9523,54,'),
(9, '16', ',14,13,21802,36914,8,54,'),
(10, '17', ',13,14,35770,21802,8,36,'),
(11, '18', ',13,14,35770,31767,21802,15,19,36914,8,36,40,'),
(13, '23', ',15,'),
(14, '26', ',12,14,19,13,36914,8,28,36,40,'),
(15, '25', ',19,13,36914,22,8,36,40,64,'),
(16, '31', ',19,13,36914,8,28,36,40,9523,8413,64,54,'),
(17, '33', ',19,12,13,36914,21,8,34,28,36,40,'),
(18, '32', ',19,13,36914,12,8,28,36,40,8413,54,'),
(19, '34', ',19,12,36914,8,34,28,36,40,54,'),
(20, '38', ',36914,12,19,8,13,30,34,36,40,'),
(21, '42', ',36914,'),
(22, '43', ',36914,36662,14,12,34,27,28,19,36,20,40,25714,9523,49,54,'),
(23, '45', ',34,28,41,36,19412,12,19,40,36960,54,'),
(25, '46', ',34,36,19412,12,19,40,54,'),
(26, '48', ',35080,36662,19412,40,12,19,34,38,36960,36,54,'),
(28, '49', ',19412,12,19,34,27,8,36,54,'),
(29, '50', ',40,19,12,34,24,36662,38,47,36487,36,54,'),
(30, '51', ',19,34,8,40,36662,38,9523,36960,36,54,'),
(31, '52', ',34,8,40,36662,47,36,64,'),
(32, '54', ',12,8,34,40,36662,47,27,49,36,28,54,'),
(33, '53', ',8,34,40,36662,47,36,54,'),
(34, '55', ',34,40,36662,38,47,49,19412,36,28,54,'),
(35, '56', ',19,34,40,47,36662,12,49,19412,36,54,'),
(36, '57', ',47,9523,36662,36914,40,12,49,14,36,28,58,54,'),
(37, '60', ',36662,'),
(38, '61', ',36662,'),
(39, '62', ',12,14,38,9523,36914,13,19412,36,36662,19,28,58,54,72,'),
(40, '66', ',9523,36914,13,19412,36662,36,19,28,14,54,72,'),
(41, '69', ',13,19412,36914,36662,36,19,28,14,54,68,19193,'),
(42, '70', ',36914,19,13,28,14,54,68,66,'),
(43, '72', ',36914,'),
(44, '73', ',19,13,28,63,14,54,68,66,'),
(45, '74', ',13,36012,56,28,8,63,14,54,66,'),
(46, '75', ',8,13,54,63,28,45,67,68,72,66,'),
(47, '78', ',13,63,64,14,28,54,72,66,'),
(48, '79', ',13,36662,36,14,12,63,60,20,28,54,68,25303,21,72,66,'),
(49, '77', ',13,63,28,14,20,54,60,8,68,'),
(50, '76', ',13,36914,28,14,20,54,60,63,68,72,'),
(51, '82', ',60,36662,63,13,28,45,54,8,66,'),
(52, '81', ',36662,12,58,63,20,28,45,54,60,8,67,68,72,66,'),
(53, '83', ',28,13,45,54,8,63,36914,66,'),
(54, '84', ',28,45,60,54,8,63,72,66,'),
(55, '85', ',58,45,60,54,36662,8,63,36914,25303,66,'),
(56, '87', ',8,57,63,'),
(57, '89', ',54,60,63,8,12,67,34,68,72,'),
(58, '88', ',60,13,63,34,68,72,'),
(59, '90', ',60,13,34,68,72,'),
(60, '91', ',12,13,36914,34,32378,72,'),
(61, '92', ',36914,8,65,12,25303,54,36662,'),
(62, '93', ',36914,8,72,36662,66,'),
(63, '94', ',8,12,36914,25303,54,36662,66,'),
(64, '99', ',36914,36662,13,66,'),
(65, '98', ',36914,36662,12,13,40,'),
(66, '101', ',66,13,40,');

-- --------------------------------------------------------

--
-- Table structure for table `registerbypass`
--

CREATE TABLE `registerbypass` (
  `sn` int(11) NOT NULL,
  `mobile` varchar(50) NOT NULL,
  `vcode` varchar(50) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `registerbypass`
--

INSERT INTO `registerbypass` (`sn`, `mobile`, `vcode`, `status`) VALUES
(1, '9037631786', '6227', 1);

-- --------------------------------------------------------

--
-- Table structure for table `registration`
--

CREATE TABLE `registration` (
  `sn` bigint(20) NOT NULL,
  `regdate` varchar(50) NOT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 NOT NULL,
  `countrycode` varchar(10) NOT NULL,
  `mobile` varchar(50) NOT NULL,
  `fcmid` varchar(300) NOT NULL,
  `androidid` varchar(50) NOT NULL,
  `claim` int(11) NOT NULL DEFAULT '0',
  `showmobile` int(11) NOT NULL DEFAULT '0',
  `verified` int(11) NOT NULL DEFAULT '0',
  `block` int(11) NOT NULL DEFAULT '0',
  `vkey` varchar(100) NOT NULL DEFAULT 'NA',
  `shortstatus` varchar(250) NOT NULL DEFAULT ' ',
  `blocklist` varchar(500) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL DEFAULT '0',
  `imgsig` varchar(50) NOT NULL DEFAULT 'NA',
  `status` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `registration`
--

INSERT INTO `registration` (`sn`, `regdate`, `name`, `countrycode`, `mobile`, `fcmid`, `androidid`, `claim`, `showmobile`, `verified`, `block`, `vkey`, `shortstatus`, `blocklist`, `imgsig`, `status`) VALUES
(1, '03-11-2019 05:35:05 pm', 'സൽമാൻ പൊന്നാനി', '+91', '9037631786', 'daULVDyRG1U:APA91bEAr38OSJ41-HWMaW1cpVzyxdoLU0zWbo5FmXwPQe9f5HxguN7SGnHouZNX_23pOiLjAebnsLOMw21bJ-JzvmsFSWZ3HHeDy8rAaDeD9CfNoxZQ0xeDpqD2R3QaSQtHQC748cvn', 'e9144c577281c153', 0, 0, 1, 0, 'NA', 'സുഹിയുടെ പിരാന്തൻ', '0', '2019-11-03 12:26:05', 1),
(2, '03-11-2019 05:39:48 pm', 'salman', '+91', '7034333356', 'c_hxGTT5rCs:APA91bEbb0IUmUsFBRQOZI4oF-WhmZf14FL1x1F4bjKjUnpQx_dV1peL5KjU1VeeAWB7OaxvEjhR3K1H-hEw8p-EVRSQCuC9_fci3rS4OGI8XFfX1gdDTpxvxQgis0NJpwHnCxgsPrJ2', 'c9fb819d5b83a757', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(3, '03-11-2019 08:12:26 pm', 'Nisar Nisam', '+91', '9946967447', 'eSMnecAyl2Y:APA91bFEjbFjifMeRz69EsFfNsMjSRouI8O97Pewst9Sxq0bTBQ2H8fNHuaRxZAvrJDakDweGdOhskhcWNMR29DIRH62fMCd0WZqAlTE7yRtYnRNFQdQWDng7PJkouTwf3y016YqknnW', '322bd2445b6a3c7d', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(4, '03-11-2019 11:19:56 pm', 'ali Chekkode ', '+91', '9895599348', 'ebXRQ0hY0vQ:APA91bHAsJTYTWqmi6inPA29f6-UQVz4rDT2JsfVu7k81Y41gn-szQnwK1JJSzXtczF-QVTCL7DebKtGpSxVtdPvO1AD4ms6Gz9RFY_aue3VA_3BIzn9_H1cx1o9xehIr_Oe6SPKVpJq', '3a8768b302324e70', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(5, '03-11-2019 11:32:31 pm', 'Achu', '+91', '9961642491', 'dEeJfGITiko:APA91bEVS1sip3Ry3UEtl9mmQqvUWDgI6_yKn4bz1VdqjYBNUoGMASVTQh420JtogZNL2XDzwNMTPg0uTpUkdwGgpe_UdegjnlizIWpiEcjL2j1WagQFaVs14QPD-6n1mBIUANxW4LYg', 'c9e564bccd00704c', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(6, '03-11-2019 11:33:55 pm', 'നിഖിൽ വടകര ', '+91', '9605788959', 'eWx7kuQpDko:APA91bHHtIumIfkQzNOsNhmAdLe73zRJ0BiOmf9H7rPN4yIG2VsmOXKcdv9kwgvmUdAPzSpXwbHMPKVoQYMsMN6P95WMSedaAptGX6lKBJ3v-1X2aBFKgb8W_gdQvQSTvaaByqPubRxO', 'd78b65c2d63654e3', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(7, '03-11-2019 11:48:28 pm', 'ηωғαℓ', '+91', '9995907995', 'ekBkpsonaS8:APA91bEd6TOy520rgSoyj4DuFlkYUMA2lzbaiFwtQ0GtVSQ6QB2m-ypEvmlnlVzh5GYq8Z4ckL-H2DvC7qMTj4beaFHUE49OrdTu-oD0WTb6r7O6iYxeY1LDsZT-Nt_1qDFV8_JdmRGn', '2af4213dd856b788', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-03 18:56:49', 1),
(8, '04-11-2019 12:49:31 am', 'ഇക്കാന്റെ കുഞ്ഞോൾ ', '+91', '9526773396', 'cQGKVZwyTI0:APA91bHg4vyWlWWQ8psuThe3YVTb5jYrwb2c0MfDlabadCH6hLRp86ZLVnu5h9Eq03d_BZ-xWdT0UxTDJVc9QbhRMBozHuNp9E_D9pc9fy9xA0yCwUvXN5Vd1OkPxGsYSgWF1OPFu3JD', '12acf976f208c678', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-03 19:29:12', 1),
(9, '04-11-2019 01:19:32 am', 'nisar', '+91', '9946222572', 'cHg-RbKYSgU:APA91bFjiZaZvGCXEOlJNPOwyy2QYSXoTOvMcyGmKWpkvLtZ2CXB2lFGyjvEPMA74cq3GENUfSxnt6euASzj9BJfESeCuLJE2JwzqjI8Ux7ld0n1erx27yepEEU3KW-QlUSfp2hvPk2z', 'be91b46473677e83', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(10, '04-11-2019 02:34:37 am', 'സാഹിബ്‌ 💚🇲🇷', '+91', '9048923360', 'd8vT2ims8m0:APA91bFW9eMKwVJpGxmbtq7OypPhvULYnBWL52vVhq9GgBou9uwv4LZwM4zfynMqvLlJy0P6nadqxx5VV9IpjLGafWoV1XagFdQkO5wiyF7Mz3JTCUaqMp-eHWDQOD-3weZlBeeethqQ', 'f96c7b5850619426', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(11, '04-11-2019 04:02:31 am', 'shiha..🌹👈', '+973', '33487274', 'dYFH2snS4h0:APA91bH-cCGItbZvXor2_qziuknzIh6JcEvIjDLvRd_c0xZmWIJrY0oHUQGr81ftMOQsdEYptqZ2BCOgEDoIOIwvg_O0ea8f9i7yR6UmHuh5KmKngnck1KV1v1zAbJ_TJA1tb-VXbXvL', 'c6363e794d61e0f3', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(12, '04-11-2019 09:01:18 am', 'ജിന്ന്', '+91', '7907598459', 'chndWYr539A:APA91bExRcWQjx6IE6sbQVcJVhN-Vk75YBDNuiN1J6etv8BbzNjv0VUrGVIYwYOo_Z3OF1wKvSbsEb1FAN9Ovos8cl8nuXDPYB4PRDvHQtsNahxKp4P5YC6zdATN8Bmzfi_UsokhUV7E', '9a0eb6eb698c76df', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 00:55:36', 1),
(13, '04-11-2019 06:42:43 am', '🦅JAMSHI 🦅MLP ⚽️', '+91', '9746164640', 'eepZvMjbKRM:APA91bH2fvZ2bPdklnnVNCeNiOhZ3wfvW6Z_iaGl24u3rrqa5N2Q1cWa5KDms3mv3Ic6W91921MYmnfdLBKqHSrVY6x20oVGqBi-b-7om19YGZYIRlim95GV4JyL1cjNlPYTeMht1PNw', '9433986ab3f3b20e', 0, 0, 0, 0, 'NA', 'jamshi', '0', '2019-11-04 06:11:38', 1),
(14, '04-11-2019 06:47:58 am', 'സിബു', '+91', '9995690670', 'd5XBq5JoZss:APA91bFk_08fx9vV5ksFpqH2Vr5fZ2lMkCc7SJFgX735U7l3DMsskUnnsXlRwaZoqSifh3OZhQz8kG-HUdeOsx6RvKckwyXqh3FOJ84FI78ea7hC0IbJqHYsdSc8f0mPAEjPz0Ah0YqG', '2e53463da4775951', 0, 0, 0, 0, 'NA', 'if u need blood call me9995690670', '0', 'NA', 1),
(15, '04-11-2019 07:21:29 am', '💕 Shamna 💕', '+91', '7511112757', 'fL0BXlN3Qrg:APA91bF44-XvSb_5OEPPsLvjfNvNZmb-1KLDPwM9DBS5nABFGCcMgvlyNxaVhYHodtY720BGCsX0ucIjbnaMzPeAf5SCRUxPzUPcbaVaWmZb8V6MOw18eaqRvALyUSBYOoitIogLR9fM', 'be6251a3072d044a', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 01:55:11', 1),
(16, '04-11-2019 07:28:39 am', 'nazra', '+91', '9744149807', 'cwaignPGcIg:APA91bHHz2vXdjhPqYMTcf74a5LRe-RlgiLemS4fM9hP5lICt9e8sFR-ckbh8tJBjissGYLPa38I6L9VZmx4DYYf5Nt8TYnoJnRAaEmSoyXuFHP4BPmv7APsMmqAbq5Gh5LQGy4jxHEN', 'd04b935b43efc7fa', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(17, '04-11-2019 07:42:57 am', 'shabeer', '+91', '9544789101', 'd_JjUG35RDc:APA91bEC_AiNXpQKFUyV_MRKLx9gH5_GCKElu1za9-qpPY1NnE1vo7dC-JkcTBCpa-cFqLqHHM5cmUWyJIhDTGznteGjPXSZ4q2FKAK7xXW_0qkilPEqFcpNAmxuwAp9gBrQluXIvCSR', '72fdb53c408bfedb', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(18, '04-11-2019 07:44:44 am', 'shijoy', '+91', '9847178221', 'fIdCZqfx2Vg:APA91bFdhaAVprcDjfAceeUD13asQQ24g44LNlKomKFLK3k-WPrZdPyvaIroNJPF6_0ICeLMyQo6jyvEwle790UwrS_6IOT1gGBPajjluPsFjLhTFg5VwxRTk8Rp5OxQVbgXWj1T79LE', '50fda50c1759a43c', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(19, '04-11-2019 07:48:35 am', 'നാച്ചൂസ് 💖💖💜', '+971', '0528159062', 'eMMylFxBl8o:APA91bFM9SSdkFHVtVbRsOJtJDHZ3KEJyS7gUqXNCkkvqsDOx21tB5k2i9PD7_xh3oS6WWGPjUhQBp3VLlFgcREP4hLJdWptJyx1GNTBPle75oe3-xq6_lKirprTL-8Uzxy9s4Yyynkk', '3d247da29c53c2e2', 0, 0, 0, 0, 'NA', '✌✌✌', '0', '2019-11-04 02:24:03', 1),
(20, '04-11-2019 08:58:03 am', 'subru', '+91', '9020644252', 'fAYK_gKpIvE:APA91bEEVhSCpAZpQOrO_9j3hYYdgS8ee-1HGVHkjX58_ryeI55go-gnwhNfgu4B8k1uQwkk-I0P_ZvJwjBEXjSY0XLJbtdrM9WBcprV_5ViUMpaap-wZw6t_YELJJ3RWNOAXStt7bXt', '4f8685a262be63dc', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 03:35:54', 1),
(21, '04-11-2019 08:59:33 am', 'ജോ എബ്രഹാം', '+965', '94155445', 'fLq5ZD2zCLc:APA91bHZMHPSRAkyNdYLYDw3ja8nOhd2Jnvho0f-w_LTS5RqjdUGRZ7PaYUrD751h7XihDc8HZrJmYwir42IEAG7vhXUGCLg6S7lZL5_jAPkFrlcHYSo4h_wN2FKffOvzd_Sdcqdoijj', '11fc655650531fd6', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 03:30:54', 1),
(22, '04-11-2019 09:04:43 am', 'Asharudheenmas', '+91', '9995664427', 'cTLYZa8X7_A:APA91bFwUitTrFvcTdSPesGNbccNA3G0K-fzUk0_Z7GzOSwiO_wN-3NE04bmNbZlwiqqMuxCvJSmhREWCkpEnGwxIyWP106FS1uiRhDEXjU_Qd55V9sAfFcOVc1D33DkKmnnzqzn2nK-', '6702d7dabe395818', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 03:37:26', 1),
(23, '04-11-2019 09:05:11 am', 'santhosh', '+91', '9447525527', 'dy4W0gHIMd0:APA91bEqp4B7quD2vBJb3z4DtTW48nyMwS4njfTxjKzZZVHT0B76Xi62K8lsSqUPuzulMGPj6vTtDRPNijvSLuf2OPA--rnpS-vEe39bdkJi2EfVdcl7xY9UN72THlp-vJdfb5ueLd-a', '2a192dec605d3f4a', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(24, '04-11-2019 09:05:41 am', '    ', '+91', '9846632432', 'ddWETpbOX4s:APA91bENk9vRE4jamdB0IPmYtmY4d8rpTpnHygqsVPMufZ8GJNf21cDaSKx5hAiaCMfRL5qwBOI3eEuybZzgOwMPlFiU0Sy5t_46IvsHbqzwC9GMLVqFwtoSMOggFL9jVTzjcy4FlsaN', '3098b72e69aa5cab', 0, 0, 0, 0, 'NA', '  ', '0', '2019-11-04 04:09:16', 1),
(25, '04-11-2019 09:05:47 am', 'ismu$rashi ', '+974', '66693090', 'eLSMMw5xGR4:APA91bH6J6F8nG_QTkxC08SxH3zQQbtvkShDntoi6IFlftsoPn3vQZYGCLQdbNeIBeTbjubgQFc9VnTUd6w3fjWOrPycr-NNgHk4AKtVb7E9Ztu9X9JL4NwJlC_FyqPvN191NACsYCO0', 'e34fa38584c2432f', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(26, '04-11-2019 09:06:57 am', 'Noushad ', '+91', '9746260361', 'e9XqNz-5IcA:APA91bGDc5iy5WwkXaZkd-JPThNI4kzzOZRukKG0K7H4Dkg2hF4Kuf0_ilZjPj7BRV810R-fdlUj33VtE6Mn6DESFeV_7QyZxlGbxV50Wj9gv69r_eA6FjSpoH2i3RHbaJcS_DMaa4h_', '1c3b1405bbc07445', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(27, '04-11-2019 09:08:16 am', ' ', '+91', '8281535536', 'fDpD51W-XuU:APA91bE01v9kDgIUsuyoJOltHJ2ewyySKdN-D8gdSn6hdICs_gf66aOgAGJP3uaMBCgf_NBvpqCGU8rQcO-m26vRlx2myGA9vlOmAJpjREvO_8fpZ_otRBcWQPUk3dNdGpDGTsREVRbp', 'e60712ed2bd7a5b2', 0, 0, 0, 0, 'NA', '  ', '0', '2019-11-04 03:41:33', 1),
(28, '04-11-2019 09:12:50 am', '📚എന്റെ👬 കിതാബ്💖', '+91', '9048483390', 'cwFeYPF6w9A:APA91bE21T_XcxepIBIWf-LHIuougq0jN7WN619352sn-6v-technews4jVZUCha54_7Eivc9D-g7yjRGx33qWHWKv6GrB7cJ17jwNYuNjHfCOlPnKqzuvx-kXzBW38Co_oYvzlwfmN-', '35ad5772083f7a57', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(29, '04-11-2019 09:16:29 am', 'ashik', '+91', '8111960477', 'cr12Tkk3UWM:APA91bHZ7-EOakmClcEChvcEl1mZ6294TW4hdWEF46lef8T4y2tUrgm8zpDuTit7yVOCGhzLDdArXeh2RqO7M7u81ysf7erExPxriPWr4CKaxVvgcDmZF87UfAGmEh30g6MbzNZgQACs', '6d4a564c0e5354c0', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(30, '04-11-2019 09:24:55 am', 'Sajna', '+91', '7902949834', 'cwCibLWOvao:APA91bF1FwJfUA-7coSIA192K_CYn-uEMMIKkHy1eoi12zNd8uk_37hCeQuyHTVxSowvf2el7Scujy88Vgu8tU3LPOFGzYy-IJb2enLp-c-zDUy5EHoVAFZN7p71xJP_Wjahd0l3_EVo', 'b9838489a9b97e4a', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 03:57:25', 1),
(31, '04-11-2019 09:25:25 am', 'Thomman ', '+91', '9961681287', 'cm5250pNrMo:APA91bF48_r_9TcxoMxTYhWVwGSR1ovSDAi22Q6b5juL7LyQNYvY917CImVF0DoGE9kLt_jXgFmlGStQBLPn3qQfyVFk--ecHBJeHxur5x1evQUf6ORoNtQP-4yVWBtsXdBosRjR75yF', '9f236e1ead1fe113', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 03:56:29', 1),
(32, '04-11-2019 09:26:41 am', 'shaheer abudhabi ', '+971', '528052313', 'fwX7U5cOZck:APA91bGHO8BpiYpRo9bkDWcV1X93PZKZY2N84SLdSp5F83m6Lb42l_W2dFVCDedUEQjBN1MpNUtYxg_UwiDUJUD1WDfLhkamLaWm2wuQEMFkGoX-FJ6UCFceSXe42w--8E2mQxTYTnVG', 'fd74f0c2962f57ce', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(33, '04-11-2019 09:26:42 am', 'ലല്ലു ', '+91', '9846220279', 'emg7APaC3uQ:APA91bF297rRQ7SwQJDJsRef3dP496DR5jCpDPCKmuAQMtNmkDp9PqrVzNL6KHORDGehovHgaSdGkr_-Ut7K1kdjYBdW03KaXg6VbCPzvgFrjIYN2ncYRDfptJz5lmk_i_ubpO_6mlxP', 'cbb540fb6db433de', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(34, '04-11-2019 09:29:07 am', 'ശിവ', '+91', '9995130910', 'cD0lb1MZ0no:APA91bEQ0s-DSR54U6xLExHm0LAr0i8kM1e7Uf5miXkK2YB4_LCDwhrBbMlUpSGxQT8s8UYTUSGsKmQ3bKKpsyJByXnwmGTETDuwGEa8-aDFF4OpFfn70XeEKyWuItmDMVJVQOnW5hX0', '72ce52f38e335e3c', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 04:00:01', 1),
(35, '04-11-2019 09:38:50 am', 'Suru', '+91', '9747942503', 'dHrlGt-xWTI:APA91bG80tWv2wTCeJnxzeJNmmuHrXT78bP8xewDCW6Fi53vJ_cx30KNz8RbtARwAkBbQxiWzvzR4UGZLoUhxvgrxJmfadrt5MgeDVjtLvCRlMucyzEH8Wp-qq5RUeey_6ag7pd88hKp', 'c10fca9fc3769777', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(36, '04-11-2019 09:32:36 am', 'കുറുമ്പി പാറു ', '+91', '7034004564', 'fKC4EOGSZMU:APA91bF9WG_LjoxhYatbZcsacgdFyE0t8ZP11GJ39dAg_V0VLte6Fuzn6ceKRtWPktKdA5kWBF_koJ2sTSizpCsPHDrD8erN400mcg-h--8-H6bT2M3PRmSJnRyhmCRoHvn6719CVIZk', 'a4c6948358187a99', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 06:34:05', 1),
(37, '04-11-2019 09:35:11 am', 'ഹലോ ', '+91', '8113869679', 'dhrxnR_YZsA:APA91bG9O8LItHLvuzxJcpFEdzNjkwwC-uTUzC8L6KTo68G_HJxj_GOopT28zP0RszIFNSM_e11QvjWk7Ze39a4Il5VTYC9gsHt-7wT6pp3nWOkf1rJxNzthNqyLDG3_clufkdHSAjHP', '7acc5ac685722303', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(38, '04-11-2019 09:35:25 am', 'Nizar', '+91', '9746764454', 'evMssEeLgoA:APA91bFPRwxwUJhG0M7YSZdv-v1mo7X4C5lJLkdioX5fP2G9KZIe2Xxbrh4ufz27X9k_8JSGq2jSplHq-aDaqGU28zHy_a_hJqN7JGLZUtbRUxx0IQSGSmXEk26diywCaomJhVcjQwzS', '4b752bb03dfcb629', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 04:12:51', 1),
(39, '04-11-2019 09:38:10 am', 'ഹസീന ', '+968', '95443813', 'dGZQEHLeYj0:APA91bHJxU10PPvdoxBYS_UniAkompPJF-D7toiUjHH_DT6gnzBMNnJgkLSj2hF_p-TwscEAK3zkKmO9h7x57lmuCC3fvAarcog7Y3AHADzc_gmBc6nHlIQuQUNhS19vG1rwj6wpZXMz', '72787fb3c253a8d1', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(40, '04-11-2019 09:40:15 am', 'asf vga', '+91', '9846643956', 'dvRxetEUqOw:APA91bFUk11bv6ud3maX7JuayqXMmnfnSHqRWh_--9fDyk_jeUVWaeN2iK9OhJvBx8L0WM6SSo7wc8lHOIgP6e6UYInWMxQIOqbHVk8Pie8c0FlB4guk0HlDGaGRT-u0G5MJtGJ1q39v', '1d6e6609cdb4cc25', 0, 0, 0, 0, 'NA', '11/11/1996', '0', '2019-11-04 04:26:01', 1),
(41, '04-11-2019 09:40:26 am', 'krishnan', '+91', '9495149251', 'e8KprnkED9k:APA91bHcQCg_Lucy6rHiU0uSKia04rukhRXkxb4fsEXxwdcIvxDg3tKqQDgRcF9hCPKMmSWCYCpl0TfKyQHmvePSqCd9kJFf5xHKGBqIyLhsTzyerj6vTjyo6bjvvTytZBiskaA43CEE', '066f85794ac6de8b', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(42, '04-11-2019 09:43:57 am', 'ഏട്ടന്റെ അനു', '+91', '9526116319', 'fRX1eDajTaU:APA91bHluzUuaHkl_sLfzhdmgQHD0TH3RdK4tx9bnxadhdf-QaMl1HtOc_TBECT7lr8E5FXl3YU2Y10nbxmH0olwKG5haApdonAIEi_az1oWaSqxmx8dvJQW-9k6Prx4tjwmhzT-bw9B', '114bce55abede885', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(43, '04-11-2019 09:44:16 am', 'Anu_', '+91', '7736191140', 'evtqP8JDKbs:APA91bEqbYaH0ej5PJo5RtYArp2eCRV5aN3iKs3pzfUe9jJg0RBDuVBYXPjfB_UHMLawLnpAhdWTJxJKdnhjVR7fXKKF8YWCCLnd0ABKwXUXxEj9VVwvbNLDAvwSwbSLOnhq1xXRoDil', 'ed20a8b463387132', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 04:16:14', 1),
(44, '04-11-2019 09:54:03 am', 'vaishnavi', '+91', '8075808375', 'cDEfRcGOfDQ:APA91bEyqglie8MmmDBBqBRC6CpR2HGXxyCoR_tdZNCGygrwl9BgY8NVENKEugiAnOMJmE0yy946bSNuTxUtjPxjrYsq2EB6g15V_B7VS-nMvq6XGcKwHzFsbsWC_w0MUGcRADh4o2ph', '7b4d26ab9f9d12a9', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(45, '04-11-2019 10:02:10 am', '         മാനസം', '+91', '9072167437', 'cOpRAJPToIg:APA91bE5hBuWCBkBJpY6bY3xsKS8qFC3IxxchPuucTecuyzL8QJW2BrOO8cr4U0xtoUhTePHVgdlxBsTcoEgai87NpFLt2xIU8ENlRxa2ocB-ILJwHIrCkoclQRyuHQorWPjCycGjV-b', '616458b6887706a0', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 04:35:27', 1),
(46, '04-11-2019 10:02:21 am', 'Sreeraj ', '+91', '9544900680', 'cKZ3pslneqg:APA91bE7zybohfxN9WoLocKKF-za0QKV8KUMuE3y93KWFCOLLLLCxHKc9kGiR-WbrWHGKqjyDBO8AJFXelArWmL3mAchulZujbqtFW7i0SHNr0dbcvtNgXrVwaqtKNzhRMcu3WlyJST2', '929495fa47a9632c', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(47, '04-11-2019 10:10:37 am', '🖤neenu🖤', '+91', '8921296741', 'dp8GTLr7OhU:APA91bHFG9XOKit8zDff1BWZ4GlfEDuVpU5zQZ5Dv1Z41ni1_dH5PrKLtRJVM-4URO42722XLIf3zY7aaqehNn7ZktxDwE7S9D9T2FYU9HYK5uITPIs93fDzXx6sBCywEX_yRJL7NYtG', 'feaf2523caa4e7b5', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 05:24:04', 1),
(48, '04-11-2019 10:16:04 am', 'സഖി ', '+91', '7025449542', 'dM2_baiC_YQ:APA91bHV5_Z2kkDdFQF5hxM6OCJobUU18t25FpxmNHCi4M6uhx5RTC5vW1FbLlbizT9C8R5ac5MOrpL13V4beR7x_98U_YgxzZ8iBf1RKgrSKKFj_DRKa88UTbcefqmIdOady0dJKtuL', '5318eaaa0e9fc873', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(49, '04-11-2019 10:26:38 am', 'അജു......🌷', '+91', '9633575712', 'fz08P9bBQLA:APA91bG4aNShlqq7kRCo2aJz3VqUCknao6ZNb0QJrPLr3PE0aSwGSxUSPeP3tjU1pEzWi5J2gKKib-wXQk3hP1HnjMw6WnShxr6D_eKKMsdY_aH3NdYhg9N23_sl2gRX9eqa4M2lOoAG', '48bbbcab121312de', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 04:59:50', 1),
(50, '04-11-2019 10:37:40 am', 'mr🤡chengodan', '+91', '8086725245', 'e758dHSox2g:APA91bHJyv_zl1QhSzsC0xpZeNGpMzIGe7ccT2MDzP418euI8k9W72aQdKywQM20vFIOT2Juv_bC1Skwu28lZwrpqAoixl2fNq9l0AwX6pofyTSs-Qgbgx-sOFeFMk9KGri3up7_9oVq', 'c85df11d88ad95f9', 0, 0, 0, 0, 'NA', 'പുതിയ ലോകം⚠️', '0', '2019-11-04 05:16:50', 1),
(51, '04-11-2019 10:45:59 am', 'പൊടി മീശക്കാരൻ', '+91', '9539221665', 'dePm_y2SGIo:APA91bEPIzJ76riU3-pSyVQDkWB_wsuP3iH-x5D9wmUrauZ_YM1j7KjdGFVolhOJNRKeSEGghZ6DnOYMmKnvpSEd-y-28FpKPfaZM_-RwPeiV2eDwR1cdBZVw06cUi_JHmoLYogNtUDT', '8234fcd06a557231', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(52, '04-11-2019 11:00:07 am', '  ', '+91', '9895958321', 'cjodhO1Qcvo:APA91bG8mxsM6j5lploO7hXfBTAl6DUIihvUaEgx90yHgZ5wWTh6GeIekllY3rTKdU8eiOJJWI5YmWJt-mEHp1EAIK_Kiq2gvKRy9bqD8pj_ZZyugnySdQxCC2hOcJMa6nTvsHD0iCva', '9fdbd1535f54b9a3', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(53, '04-11-2019 11:01:59 am', 'Nishad', '+974', '55662201', 'cc69QhxCj9s:APA91bFRmVwYwzM8K0bypc0EiL2Ot_b_kv0ydCxIlL03bz_gAsbGaFtAC2REFTbCcFD4g55ECv6lq31AJ2obqxkisL7F80F9Ng7DqY5YU-8Ae3ow7qcCUicDuM-fM85frUtpcIxx5NMw', '1d3271b4a6b015ba', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(54, '04-11-2019 11:02:39 am', '                Dillu Dillz ജാഡ ', '+91', '7902469707', 'dNB8YTjtJd0:APA91bFzKzXza9NX1H6V6WgMvIsR7FXceJWFM5Ew-EsWB-eaPC_JnQpSqC-F23Au8lxuvvvASRXY-YO7BRtQgNKulHDiU51ZMZf0Xgpkd5b8Z570EFT1JcKXfYvOr2393KsbNcs5pc5C', 'b3034fc374262c9d', 0, 0, 0, 0, 'NA', '                           ..... ✍️', '0', '2019-11-04 06:07:02', 1),
(55, '04-11-2019 11:02:57 am', 'niyasmuhammed', '+966', '0541445875', 'ddPwK8Qn3gY:APA91bEMegIpvsTAMHpcQGlSNkXonCcKcuINf-P07vfiHOm14Cqkfbl2aUMIJJz4XF7Bd5Bvt2xyvNM6Zk9i087vDDb4P1AZ63fDeR5gxORgDQXFKezkD6K8-pVRyQeQj6dnAqhBwnlU', 'ae99b31ce41ab69b', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(56, '04-11-2019 11:10:13 am', 'musthafa zamrin', '+91', '9567309183', 'dRDi15YqLio:APA91bGWQ9zVDrbG-ika9C2q6m7EtTU2zAZjjM68ftl2wzTy2m8KBmDsYCQMTZnRhGY6Qz0NfTu1OF6BrqWCij2ecpPdzOCKX_Rjund5LW7Dgujdao60LA69qj3RTkIDK7BVWHjS_qbT', 'fe33a2ed31587248', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(57, '04-11-2019 11:11:21 am', '       ', '+91', '7306616156', 'fFYyvoQCizA:APA91bG9Bv3ey0qngO6Fv_HjsHxWQoYz5UsUvpGun52gn_TP4X2BBNFh1MJgSFRZJoV-F0wtnSbRsp7UcjaOmVN_CpFpWgZkS9XTeMROtS0kBUUtUB36o8P-FVX9ebjwFIVz1KhLWzfF', 'afc9bbaf4e0509f7', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(58, '04-11-2019 11:14:33 am', 'മൈന ', '+91', '8137860652', 'cF9IRh3IGLw:APA91bHENyZroOzWLOOQGqxlsK6L-wFTr0_9kIjo15joG-eljbY6Zd6Fszpe23mo-Z2Smi49rMYW0vKct70O-X-rbSXZuZnlNQXPCqVyHyni_TEBhRYH7tFba4YVfjn5PI20F6p3OE8g', '66da00d3e7f8c369', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 05:47:21', 1),
(59, '04-11-2019 11:15:43 am', 'Sreebu', '+91', '7736259772', 'fziP4OESRDI:APA91bFn0iokAwVZadFnEPldQhXPesY1R_qkPndo5l5LOkeS6_lPuVsTYF2LXkkUdjerphXb8WtgB2HDKP38dlT2rffPLGKSds8cQ5eFqY2ye89wy5Rb2Kq6Qa9rpFe95TzptokJyujB', '13c56b61430cc08d', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(60, '04-11-2019 11:18:45 am', '❤️രാജകുമാരൻ❤️', '+91', '6363520170', 'feb6mOm9hi0:APA91bFCdoshmzJaPEZgDLYjObvBy2thFpxQ2gmvXd9Js5i6ueAzANsZQn1lfGjA5ySwreIi_yUz1JzgQMUy6vWaOTwZzHOOvH8fTMk4JsoFwH5_cWN4cV3b03Tj-V4OzAMLalJsvZEU', '33b877012fd8e6b3', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 05:57:15', 1),
(61, '04-11-2019 11:21:03 am', 'Ayisha', '+91', '9847193368', 'eg6hU25NLGg:APA91bGXqNHbKpfXhteSkye-XKrSoE4yaCsYli81DLcwVW6ZRe57tMYLl8gRPzlqh-vwFCSmfM4LXSzzF0F7O9-bWlnWKI3FIGBtXGwnLuX8_D4AH-kwQJFKr7cEF4ojZmE586mTvnXl', 'b50a7ae0ed38e5e4', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 05:53:46', 1),
(62, '04-11-2019 11:27:01 am', '    ', '+91', '9961320809', 'fxgoonxkCt0:APA91bEPdzOZ0l3DYwgESJZMHsyWqhwFr18RMolGHqraBGRwi4fVROasvZ_IC8VadhJvuTaWbtMJqoBDr2_edwRj7gIy_YBQFAE-GYz-4N-vczK8ink7_bEcSPPshKst_4gAhgR74S2_', '201cebe36fc85a05', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(63, '04-11-2019 11:28:15 am', '🤙മുത്തു🤞', '+91', '8136906046', 'dvEp4Zp7Cbw:APA91bHBUXyU_oBzSUmFjjbAwwyxXE3v6UyPyhLQcOD1OHeTyq76LJ0GeR9oaIfyiLTJvucC5hAlqj4KfUM_tCNfOGM0ec7q7ievzDpXE04yuCl_AHZceDBRUMn7qH5KgWRbkASIIwTm', 'cced99e4661e7b41', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 06:01:55', 1),
(64, '04-11-2019 11:29:50 am', 'sala', '+91', '9961417645', 'dWoogLsbGUc:APA91bHuhZ2MbxtDnTf3m5AL6nlBHyUr4k8izEJkYVmaiRBYkI3ULNLCgUNBqsy1jzXlM2w_6kzjfxXgbFONZ8KjdfXl5zpqz04vfub56B1i08hBAlIRs6g5nPAn6OsxGfthUWg2JbfX', 'd1d86c8c0a7621db', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(65, '04-11-2019 11:36:08 am', 'abbas vengara ', '+966', '501230325', 'eyHowXqcpWc:APA91bHU649IqFKGYJiIB3esLobx8wprYJS3IYLTV8NDF5mIZcDEm-cLCfXaqmjqIiKmD8_dYA3bGthJvYdd6iYIv8nxCnXg275iBCEzBsyzrV_255OChJuJxhW1LspbCvTZtVXi90X5', '775888116b8db621', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(66, '04-11-2019 11:40:44 am', 'മർഹബ ', '+91', '8594029374', 'e3146xfiKeo:APA91bHRXAgKTGdXOzvgXttGoQNGLR-wDQO_HpXRg09AXqVmGsTCMlsUFCKaZkpvesTzM-YoT5w8tB6-bp1uzBTHK0RhaKOhpeWWsC8PnBAEI3XCyICwBiNDHvVBAnsjbC2ve4Nr4Kou', '1f7949b4534eaf11', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 06:53:26', 1),
(67, '04-11-2019 11:47:32 am', 'athmika', '+91', '9496861195', 'eXK-mht_LUo:APA91bFLnvTrRFYF8DFEM9yYxpzsGbch5nFsn-GSxIIJHciAnmwL5-AeBIA4a3GkVHeyJ1ZcwGZNFmvKfwu4_dT6UeeeOQNxii0O-d0BlV_vNDvpqYUT1d-NZCM4pZiMpaq3-1RDwXg9', 'fe920401bcf7ccd9', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(68, '04-11-2019 11:47:33 am', 'NVN', '+91', '7012739687', 'fp3Mcd5rS30:APA91bHL7A6wFvk6HXYJktmt_6Ggnb4PIsESGK-hXYJLC5XaqXuukiF59OVQQqg8ODpzqKHOm0n92mgQU_bXHVJ1UeJdg8k-iJTcj6x6brawYH4hsisamAZIp5xVb0aHpBS-ZL1jYrH3', 'd1eb085a8e9396b6', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 06:32:51', 1),
(69, '04-11-2019 11:53:41 am', 'aa', '+91', '9895910243', 'ffyk7x2rUds:APA91bF9_cdbwBHtcOcm7BtijmwQsyue17Vr-RxtJTfhzCgmMPwIhSI8DBgz-K3Aj-j46mOTA55Exdrnt_8Oa_aqTY9d-lEEFcDbJn8SPXBBq9dXxEQ2KDWoutCxkIiW2Z5RueGe7u7U', '81e7d844f8ca5663', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(70, '04-11-2019 12:07:20 pm', '    ', '+91', '7902600809', 'dENTOSKLvGI:APA91bHMH4VaVos75V_1ZSnuB4HbnDfVDSkqu7i5tr7tOoLCApw71tLLrkjKn2FKZJXXV1R62ygtflN9oCJcAHZLAYOOjHOiAc0j2memvsHOG36WvlcWFIhvEe4WwQRah30ofCuZft7q', '201cebe36fc85a05', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(71, '04-11-2019 12:07:30 pm', 'Shahid Oorakam', '+91', '9995971431', 'dpzwfT9AEb4:APA91bE6RKR7sqqveDck3CcNzoHO0z-6MCNjyF160r5mu0QAZuN1pEzKSQBFC4i8-gyvLs2BHbDn6pi-0tWADw3Ths_Y8nsiK1LUT078J_vXyO1PU2WhRhioBCOBQ-bvxm-GojTy_5-m', '194f45c0760049c0', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(72, '04-11-2019 12:11:17 pm', 'ബഷീർ കണ്ണൂർ', '+971', '544622586', 'fV5U7NEPUOs:APA91bFiVWrIYvkuKi7DeFKxxcNBSiaHV28_SfCRnEGALqDgIlFmYbG3Y-QZaMWzv0kzXqICBtiR8ujcT5B4CzqUvzj_v15zHsDRrk-cr-_oy6RygNfFfm2VPWcSs7JvQheZJhw3Cbh0', 'a7999bf612b1263c', 0, 0, 0, 0, 'NA', ' ', '0', 'NA', 1),
(73, '04-11-2019 12:20:22 pm', '🐅$ü££û{സുല്ലു}🐅', '+91', '9496159607', 'el4TuKpcZbk:APA91bHK6ulLKaBJZbJ-ys4iwMhMZtpH0KoKdO9t98XYIi3fqMzKxCOAlJ2cFNTuJLU0qDAeLw2IYDO9oCAXbT1RhKzV30XG3uotQU1JkY4ocnykGjalwLvW9gRY-7KdTBdPqELir2K-', '0f46d69f9144a2bc', 0, 0, 0, 0, 'NA', ' ', '0', '2019-11-04 06:55:30', 1);

-- --------------------------------------------------------

--
-- Table structure for table `reportstatus`
--

CREATE TABLE `reportstatus` (
  `sn` bigint(20) NOT NULL,
  `regdate` varchar(50) NOT NULL,
  `reportuserid` varchar(50) NOT NULL,
  `statususerid` varchar(50) NOT NULL,
  `statusid` varchar(50) NOT NULL,
  `reporttype` varchar(20) NOT NULL,
  `statustype` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `status`
--

CREATE TABLE `status` (
  `sn` bigint(20) NOT NULL,
  `userid` varchar(50) NOT NULL,
  `postdate` varchar(50) NOT NULL,
  `status` varchar(6000) CHARACTER SET utf8mb4 NOT NULL,
  `commentboxblock` varchar(10) NOT NULL DEFAULT '0',
  `visibilestatus` int(11) NOT NULL DEFAULT '1',
  `likes` int(11) NOT NULL DEFAULT '0',
  `comment` int(11) NOT NULL DEFAULT '0',
  `statustype` int(11) NOT NULL DEFAULT '0',
  `photourl` varchar(10) NOT NULL DEFAULT '0',
  `photodemension` varchar(10) NOT NULL DEFAULT '0',
  `pinned` int(11) NOT NULL DEFAULT '0',
  `photostatus` varchar(10) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `status`
--

INSERT INTO `status` (`sn`, `userid`, `postdate`, `status`, `commentboxblock`, `visibilestatus`, `likes`, `comment`, `statustype`, `photourl`, `photodemension`, `pinned`, `photostatus`) VALUES
(6, '3', '03-11-2019 08:12:59 pm', 'KuC0quC1iuC0qOC1jeC0qOC0vuC0qOC0vyDgtK7gtYHgtKTgtb0g4LSq4LS+4LSy4LSq4LWN4LSq\n4LWG4LSf4LWN4LSf4LS/IOC0teC0sOC1huC0r+C1geC0s+C1jeC0syDgtK7gtYfgtJbgtLLgtK/g\ntL/gtb0g4LSV4LSf4LSy4LS+4LSV4LWN4LSw4LSu4LSj4LSCLi4uKgogICAgICAgICDigLzigLzi\ngLzigLwK4LSV4LS04LS/4LSe4LWN4LSeIOC0sOC0o+C1jeC0n+C1gSDgtKbgtL/gtLXgtLjgtJng\ntY3gtJngtLPgtL/gtLLgtL7gtK/gtL8g4LSG4LSw4LSC4LSt4LS/4LSa4LWN4LSaIOC0leC0n+C0\nsuC0vuC0leC1jeC0sOC0ruC0o+C0giDgtKrgtYrgtKjgtY3gtKjgtL7gtKjgtL8g4LSk4LS+4LSy\n4LWC4LSV4LWN4LSV4LS/4LSy4LWN4oCNIOC0sOC1guC0leC1jeC0t+C0ruC0vuC0r+C0vy4KICAg\nICAg4LSq4LWK4LSo4LWN4LSo4LS+4LSo4LS/IOC0heC0tOC1gOC0leC1jeC0leC0suC1jeKAjSDg\ntK7gtYHgtKTgtLLgtY3igI0g4LSq4LWB4LSk4LWB4LSq4LWK4LSo4LWN4LSo4LS+4LSo4LS/IOC0\nteC0sOC1huC0r+C1geC0s+C1jeC0syDgtKjgtJfgtLDgtLjgtK0g4LSq4LSw4LS/4LSn4LS/4LSv\n4LS/4LSy4LWB4LSCLCDgtLXgtYbgtLPgtL/gtK/gtJngtY3gtJXgtYvgtJ/gtY0sIOC0quC1huC0\nsOC1geC0ruC1jeC0quC0n+C0quC1jeC0quC1jSDgtKrgtJ7gtY3gtJrgtL7gtK/gtKTgtY3gtKTg\ntL/gtLLgtYYg4LSk4LWA4LSw4LSm4LWH4LS2IOC0ruC1h+C0luC0suC0r+C0v+C0suC1geC0ruC0\nvuC0o+C1jSDgtJXgtJ/gtLLgtL7gtJXgtY3gtLDgtK7gtKPgtIIg4LSw4LWC4LSV4LWN4LS34LSu\n4LS+4LSv4LSk4LWNLgogICAgICrgtKrgtYrgtKjgtY3gtKjgtL7gtKjgtL8g4LSy4LWI4LSx4LWN\n4LSx4LWNIOC0ueC1l+C0uOC1jSDgtKrgtLDgtL/gtLjgtLDgtIIsIOC0ruC0sOC0leC1jeC0leC0\nn+C0teC1jSwg4LSu4LWB4LSV4LWN4LSV4LS+4LSf4LS/LCDgtIXgtLLgtL/gtK/gtL7gtLDgtY3i\ngI0g4LSq4LSz4LSz4LS/LCDgtI7gtIIu4LSHLuC0juC0uOC0v+C0qOC1jSDgtKrgtL/gtLHgtJXg\ntYHgtLXgtLbgtIIsIOC0ruC1geC0seC0v+C0nuC1jeC0nuC0tOC0vyzgtKrgtYrgtLLgtYDgtLjg\ntY0g4LS44LWH4LWN4LSx4LWN4LSx4LS34LSo4LWN4LSx4LWGIOC0quC0v+C0seC0leC1geC0teC0\ntuC0giwg4LSu4LWB4LSy4LWN4LSy4LSx4LWL4LSh4LWNLCDgtKrgtYHgtKTgtYHgtKrgtYrgtKjg\ntY3gtKjgtL7gtKjgtL8sIOC0teC1huC0s+C0v+C0r+C0meC1jeC0leC1i+C0n+C1jSDgtKTgtKPg\ntY3gtKPgtL/gtKTgtY3gtKTgtYHgtLEsIOC0quC0pOC1jeC0pOC1geC0ruC1geC0seC0vywg4LSq\n4LS+4LSy4LSq4LWN4LSq4LWG4LSf4LWN4LSf4LS/IOC0heC0nOC1jeC0ruC1gOC0sOC1jeKAjSDg\ntKjgtJfgtLDgtY3igI0qIOC0juC0qOC1jeC0qOC0v+C0teC0v+C0n+C0meC1jeC0meC0s+C0v+C0\nsuC1jeKAjSDgtJXgtJ/gtLLgtY3igI0g4LSG4LSe4LWN4LSe4LSf4LS/4LSV4LWN4LSV4LWB4LSV\n4LSv4LS+4LSj4LWNLgoK4LSF4LSk4LS/4LS24LSV4LWN4LSk4LSu4LS+4LSvIOC0pOC0v+C0sOC0\nruC0vuC0suC0leC0s+C0v+C0suC1jeKAjSBf4LSV4LSf4LSy4LWN4oCN4LS14LWG4LSz4LWN4LSz\n4LSCIOC0qOC0v+C0sOC0teC0p+C0vyAg4LS14LWA4LSf4LWB4LSV4LSz4LS/4LSy4LWH4LSV4LWN\n4LSV4LWNIOC0leC0r+C0seC0vy5fICrgtK7gtYHgtLHgtL/gtJ7gtY3gtJ7gtLTgtL8g4LSu4LWH\n4LSW4LSy4LSv4LS/4LSy4LS+4LSj4LWNIOC0leC0n+C0suC0vuC0leC1jeC0sOC0ruC0o+C0giDg\ntK3gtYDgtKTgtL8g4LS14LS/4LSk4LSv4LWN4LSV4LWN4LSV4LWB4LSo4LWN4LSo4LSk4LWNLiog\n4LSV4LWC4LSf4LS+4LSk4LWGIOC0pOC0o+C1jeC0o+C0v+C0pOC1jeC0pOC1geC0seC0r+C0v+C0\nsuC1geC0giDgtJXgtJ/gtLLgtL7gtJXgtY3gtLDgtK7gtKPgtIIg4LS24LSV4LWN4LSk4LSu4LS+\n4LSj4LWNLiAKCuC0teC1h+C0suC0v+C0r+C1h+C0seC1jeC0sSDgtLjgtK7gtK/gtK7gtL7gtK8g\n4LSJ4LSa4LWN4LSa4LSu4LWB4LSk4LSy4LWN4oCNIOC0teC1iOC0leC1geC0qOC1jeC0qOC1h+C0\nsOC0giDgtLXgtLDgtYbgtK/gtYHgtLPgtY3gtLMg4LS44LSu4LSv4LSZ4LWN4LSZ4LSz4LS/4LSy\n4LS+4LSj4LWNIOC0leC0n+C0suC1jeKAjSDgtKTgtL/gtLDgtK7gtL7gtLLgtJXgtLPgtY3igI0g\n4LSG4LSe4LWN4LSe4LSf4LS/4LSV4LWN4LSV4LWB4LSo4LWN4LSo4LSk4LWNLiAKICrgtIgg4LSt\n4LS+4LSX4LSk4LWN4LSk4LWGIOC0qOC1guC0seC1geC0leC0o+C0leC1jeC0leC0v+C0qOC1jSDg\ntKTgtYbgtJngtY3gtJngtYHgtJXgtLPgtYHgtIIg4LSP4LSk4LWNIOC0qOC0v+C0ruC0v+C0t+C0\nteC1geC0giwg4LSo4LS/4LSy4LSCIOC0quC1iuC0pOC1jeC0pOC1geC0ruC1huC0qOC1jeC0qCDg\ntLjgtY3gtKXgtL/gtKTgtL/gtK/gtL/gtLLgtL7gtKPgtY0u4LSV4LSf4LSy4LWN4oCN4LSt4LS/\n4LSk4LWN4LSk4LS/4LSV4LSz4LWN4oCNIOC0quC1guC0sOC1jeKAjeC0o+C1jeC0o+C0ruC0vuC0\nr+C1geC0giwg4LSH4LSy4LWN4LSy4LS+4LSk4LWN4LSkIOC0reC0vuC0l+C0meC1jeC0meC0s+C0\nv+C0suC0vuC0o+C1jSDgtKTgtL/gtLDgtK7gtL7gtLLgtJXgtLPgtY3igI0g4LSo4LWH4LSw4LS/\n4LSf4LWN4LSf4LWNIOC0teC1gOC0n+C1geC0leC0s+C0v+C0suC1h+C0leC1jeC0leC1huC0pOC1\njeC0pOC1geC0qOC1jeC0qOC0pOC1jS4uKgrwn5GJIOC0leC0n+C1vSDgtLjgtKjgtY3gtKbgtbzg\ntLbgtKjgtIIg4LSo4LSf4LSk4LWN4LSk4LWB4LSo4LWN4LSo4LS14LW8IOC0tuC1jeC0sOC0puC1\njeC0p+C0v+C0leC1jeC0leC1geC0lS4uCvCfkYkg4LSV4LSf4LSy4LS/4LW9IOC0leC1geC0s+C0\nv+C0leC1jeC0leC1geC0leC0r+C1iywg4LSu4LSk4LWN4LS44LWN4LSvIOC0rOC0qOC1jeC0p+C0\nqOC0giDgtKjgtJ/gtKTgtY3gtKTgtYHgtJXgtK/gtYsg4LSa4LWG4LSv4LWN4LSv4LS+4LSk4LS/\n4LSw4LS/4LSV4LWN4LSV4LWB4LSVLi4K8J+aqPCfmqjwn5qo8J+aqPCfmqjwn5qo8J+aqPCfmqjw\nn5qo\n', '0', 0, 2, 10, 0, '0', '0', 0, '0'),
(8, '1', '03-11-2019 09:51:12 pm', '4LSq4LWN4LSw4LS/4LSv4LSq4LWN4LSq4LWG4LSf4LWN4LSf4LS14LSw4LWGLi4gISDgtLjgtJng\ntY3gtJXgtJ/gtK7gtYHgtKPgtY3gtJ/gtY0uIPCfmKog4LSF4LSq4LWN4LSw4LSk4LWA4LSV4LWN\n4LS34LS/4LSk4LSu4LS+4LSv4LS/IOC0teC0qOC1jeC0qCDgtJLgtLDgtYEg4LS54LS+4LSV4LWN\n4LSV4LS/4LSZ4LWNIOC0huC0o+C1jSDgtLjgtYbgtbzgtLXgtbwg4LSh4LWM4LW6IOC0huC0teC0\nvuC1uyDgtJXgtL7gtLDgtKPgtIIuLiDgtIXgtKTgtY0g4LSV4LWK4LSj4LWN4LSf4LWNIOC0pOC0\nqOC1jeC0qOC1hiDgtKjgtL/gtJngtY3gtJngtLPgtYHgtJ/gtYYg4LS44LWN4LSx4LWN4LSx4LS+\n4LSx4LWN4LSx4LS44LWB4LSV4LSz4LWB4LSCIOC0leC0ruC0qOC1jeC0seC1geC0leC0s+C1geC0\ngiDgtJLgtLDgtL7gtLTgtY3gtJrgtJXgtY3gtJXgtYHgtLPgtY3gtLPgtL/gtb0g4LSk4LSo4LWN\n4LSo4LWGIOC0seC1gOC0uOC1jeC0seC1jeC0seC1i+C1vCDgtJrgtYbgtK/gtY3gtK/gtYHgtKjg\ntY3gtKjgtKTgtL7gtK/gtL/gtLDgtL/gtJXgtY3gtJXgtYHgtIIuLiDgtIXgtKTgtY0g4LS14LSw\n4LWGIOC0qOC0suC1jeC0siDgtJrgtL/gtKjgtY3gtKTgtJXgtb4g4LSO4LS04LWB4LSk4LS/IOC0\nuOC0ueC0leC0sOC0v+C0leC1jeC0leC1geC0ruC1huC0suC1jeC0suC1iy4uIPCfmLDwn5mP8J+Z\njyDgtIfgtKjgtL/gtK/gtYrgtLDgtYEg4LS54LS+4LSV4LWN4LSV4LS/4LSZ4LWNIOC0uOC0vuC0\np+C1jeC0r+C0ruC0suC1jeC0suC0vuC0pOC1jeC0pCDgtLXgtL/gtKfgtKTgtY3gtKTgtL/gtb0g\n4LS44LWG4LW84LS14LW8IOC0leC1jeC0sOC0ruC1gOC0leC0sOC0v+C0muC1jeC0muC1gSDgtI7g\ntKjgtY3gtKjgtYHgtIIg4LSF4LSx4LS/4LSv4LS/4LSV4LWN4LSV4LWB4LSo4LWN4LSo4LWBLi4g\n4LSq4LWN4LSw4LS/4LSv4LSq4LWG4LSf4LWN4LSf4LS14LSw4LWGLi4g4LSo4LSo4LWN4LSm4LS/\nICEhISE=\n', '1', 1, 31, 0, 0, '0', '0', 0, '0'),
(9, '2541', '03-11-2019 11:18:20 pm', '4LSu4LWK4LSk4LSy4LS+4LSz4LS/IPCfmKogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg\nICAgICAgICAgICAgICAgICA=\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(11, '7', '04-11-2019 12:16:01 am', '4LSq4LS+4LSk4LS/IOC0sOC0vuC0pOC1jeC0sOC0v+C0r+C1geC0giDgtLXgtYDgtJ/gtY0gCuC0\nleC0vuC0pOC1jeC0pOC0v+C0sOC0v+C0leC1jeC0leC1geC0giAK4LSF4LSu4LWN4LSu4LSv4LWB\n4LSz4LWN4LSz4LS/4LSf4LSk4LWN4LSk4LWL4LSz4LSCIOC0ruC0vuC0pOC1jeC0sOC0gi4uISE=\n', '1', 1, 7, 0, 0, '0', '0', 0, '0'),
(12, '5', '04-11-2019 12:32:26 am', '4LSk4LWL4LW94LSq4LS/4LSa4LWN4LSa4LS14LW8wqDgtI7gtLLgtY3gtLLgtL7gtILvuI8g4LS4\n4LWN4LSo4LWH4LS54LS/4LSa4LWN4LSa4LS14LW8IOC0huC0r+C0pOC0v+C0qOC0vuC1vS4uLi4g\n4LSk4LWL4LW94LSV4LS+4LSk4LS/4LSw4LS/4LSV4LWN4LSV4LS+4LW7wqAg4LSG4LSv4LS/4LSy\n4LWN4LSyLi4uLvCfmIoK\n', '0', 1, 6, 0, 0, '0', '0', 0, '0'),
(13, '8', '04-11-2019 12:50:53 am', '4LS54LWD4LSm4LSv4LSk4LWN4LSk4LS/4LW9IOC0qOC0v+C0qOC1jeC0qOC1jSDgtKjgtYfgtLDg\ntYHgtKjgtY3gtKjgtYEuLi4uIArgtJLgtLDgtL7gtK/gtL/gtLDgtIIg4LSc4LSo4LWN4LSu4LSm\n4LS/4LSo4LS+4LS24LSC4LS44LSV4LW+IOC0juC0qOC1jeC0seC1hiDgtKrgtY3gtLDgtL/gtK/g\ntKrgtY3gtKrgtYbgtJ/gtY3gtJ8g4LSV4LS+4LSV4LWN4LSV4LWC4LSo4LWNIPCfjoLwn46C8J+O\ngvCfjoLwn6eB8J+ngfCfjbDwn42w8J+NsPCfjbDwn42l8J+NpfCfjaXwn42r8J+Nq/Cfjazwn42s\n8J+NrPCfjanwn42p8J+NrfCfja3wn42h8J+NnvCfjZ7wn42e8J+NnvCfmI3wn5iN8J+YjfCfmI3w\nn5iY8J+YmPCfmJjwn5iY8J+YmAouLi4uCuC0iCDgtLjgtY3gtLHgtY3gtLHgtL7gtLHgtY3gtLHg\ntLjgtY0g4LSV4LWB4LSf4LWB4LSC4LSs4LSk4LWN4LSk4LS/4LW9IOC0nuC0vuC1uyDgtLXgtKjg\ntY3gtKjgtL/gtJ/gtY3gtJ/gtY0gIOC0teC1vOC0t+C0giAzIOC0huC0r+C0vy4uLiAg4LSS4LSw\n4LWB4LSq4LS+4LSf4LWNIOC0qOC0suC1jeC0siDgtLjgtZfgtLngtYPgtKbgtJngtY3gtJngtLPg\ntYYg4LSV4LS/4LSf4LWN4LSf4LS/Li4uICDgtLngtYPgtKbgtK/gtIIg4LS54LWD4LSm4LSv4LSk\n4LWN4LSk4LWL4LSf4LWNIOC0muC1h+C1vOC0pOC1jeC0pOC1jSDgtLXgtYbgtJXgtY3gtJXgtL7g\ntbsg4LSV4LS04LS/4LSv4LWB4LSo4LWN4LSoIOC0kuC0pOC1jeC0pOC0v+C0sOC0vyDgtLjgtY3g\ntKjgtYfgtLngtKzgtKjgtY3gtKfgtJngtY3gtJngtb4uLi4gCuC0teC0v+C0sOC0suC0v+C1vSDg\ntI7gtKPgtY3gtKPgtL7gtLXgtYHgtKjgtY3gtKgg4LSo4LSy4LWN4LSy4LS14LSw4LS+4LSvIOC0\nleC1geC0seC0muC1jeC0muC1gSDgtJXgtYLgtJ/gtY3gtJ/gtYHgtJXgtL7gtbwuLi4gIOC0heC0\nteC0sOC1iuC0leC1jeC0leC1hiDgtIfgtKjgtY3gtKjgtY0g4LSO4LS14LS/4LSf4LWGIOC0huC0\no+C0vuC0teC1iyDgtI7gtKjgtY3gtKjgtLHgtL/gtK/gtL/gtLLgtY3gtLIuLi4gIOC0quC0leC1\njeC0t+C1hiDgtJPgtbzgtJXgtY3gtJXgtL7gtLHgtYHgtKPgtY3gtJ/gtY0g4LSGIOC0qOC0suC1\njeC0siDgtKjgtL/gtK7gtL/gtLfgtJngtY3gtJngtb4uLi4gIOC0teC0tOC0leC1jeC0leC1jSDg\ntJXgtYLgtJ/gtL/gtK/gtYHgtIIg4LSq4LSw4LS+4LSk4LS/IOC0quC0sOC0v+C0reC0teC0meC1\njeC0meC1viDgtKrgtLHgtJ7gtY3gtJ7gtYHgtIIg4LSV4LSu4LSo4LWN4LSx4LWNIOC0rOC1i+C0\nleC1jeC0uOC0v+C1vSDgtJrgtLHgtKrgtLHgtL4g4LS44LSC4LS44LS+4LSw4LS/4LSa4LWN4LSa\nIOC0qOC0vuC0s+C1geC0leC1vi4uLiAgIOC0leC1geC0nuC1jeC0nuC1i+C1uy4uICDgtIXgtLDg\ntYHgtKPgtYfgtJ/gtY3gtJ/gtbsg4LS54LSs4LS/4LSV4LWN4LSVIOC0uOC1veC0ruC1geC0leC1\njeC0lSDgtK7gtYHgtKTgtY3gtKTgtY0uLiDgtLHgtL7gtKvgtL/gtJXgtY3gtJUgICDgtLjgtYbg\ntLLgtY3gtLLgtYEuIOC0qOC0v+C0uOC1gi4uIOC0ruC0v+C0qOC1jeC0qOC1guC0uOC1jS4uLiAg\nIOC0puC0v+C0suC1jeC0suC1gS4uLiAg4LSo4LWC4LSx4LS/Li4gIOC0nOC1huC0uOC0vy4uLiAu\nLiAg4LSF4LSZ4LWN4LSZ4LSo4LWGIOC0kuC0pOC1jeC0pOC0v+C0sOC0vyDgtKrgtYfgtLDgtY0u\nLi4gIOC0muC0v+C0suC0sOC1hiDgtIfgtJ/gtJXgtY3gtJXgtY0g4LSH4LS14LS/4LSf4LWGIOC0\nleC0vuC0o+C0vuC0seC1geC0o+C1jeC0n+C1jSDgtJ/gtY3gtJ/gtYsuLi4uIOKdo+Kdo+Kdo/Cf\npbDwn6WwCgogCiDgtIXgtKTgtL/gtLLgtYrgtJXgtY3gtJXgtYYg4LS14LS/4LSk4LWN4LSv4LS4\n4LWN4LSk4LSu4LS+4LSvIOC0uOC1jeC0qOC1h+C0ueC0giDgtJXgtYrgtKPgtY3gtJ/gtYHgtIIg\n4LSq4LSw4LS/4LSX4LSj4LSoIOC0leC1iuC0o+C1jeC0n+C1geC0giDgtJXgtYbgtK/gtbwg4LSV\n4LWK4LSj4LWN4LSf4LWB4LSCIOC0kuC0sOC1hyDgtKrgtYvgtLLgtYYg4LSo4LS/4LSo4LWN4LSo\nIOC0juC0qOC1jeC0seC1hiDgtKrgtY3gtLDgtL/gtK/gtKrgtY3gtKrgtYbgtJ/gtY3gtJ8g4LSV\n4LS+4LSV4LWN4LSV4LWBLi4uLiAgKOC0t+C0vuC0sOC1guC0luC1jSDgtLfgtL4uLi4gKQoKCgog\n4LSP4LSk4LWNIOC0uOC0meC1jeC0leC0n+C0pOC1jeC0pOC0v+C0suC1geC0giDgtLjgtKjgtY3g\ntKTgtYvgtLfgtKTgtY3gtKTgtL/gtLLgtYHgtIIg4LSH4LSo4LWN4LSo4LWB4LSCIOC0juC0qOC1\njeC0qOC1geC0giAg4LSO4LSq4LWN4LSq4LWL4LS04LWB4LSCIOC0leC1guC0n+C1hiDgtKjgtL/g\ntKjgtY3gtKgg4LSGIOC0teC0suC0v+C0ryDgtK7gtKjgtLjgtL/gtKjgtY3gtLHgtYYg4LSJ4LSf\n4LSuLi4gIAoKCiDgtIYg4LSq4LWN4LSw4LS/4LSv4LSq4LWN4LSq4LWG4LSf4LWN4LSfIOC0juC0\nqOC1jeC0seC1hiDgtJXgtL7gtJXgtY3gtJXgtYLgtKjgtY3gtLHgtYYgCiAgIOC0quC0v+C0seC0\nqOC1jeC0qOC0vuC1viDgtJ7gtL7gtbsg4LSO4LSZ4LWN4LSZ4LSo4LWGIOC0ruC0seC0leC1jeC0\nleC1geC0gi4uLiAgCgoKCiIiIArgtI7gtKjgtY3gtLHgtYYg4LST4LSw4LWLIOC0muC0suC0qOC0\nmeC1jeC0meC0s+C0v+C0suC1geC0giDgtKjgtL/gtLbgtY3gtLXgtL7gtLjgtIIg4LSq4LWL4LSy\n4LWGICDgtKjgtYAg4LS14LSo4LWN4LSo4LSj4LSe4LWN4LSe4LSq4LWN4LSq4LWL4LW+IOC0kuC0\nsOC1gSDgtJXgtYLgtJ/gtYbgtKrgtY3gtKrgtL/gtLHgtKrgtY3gtKrgtL/gtKjgtY3gtLHgtYYg\n4LS44LWN4LSo4LWH4LS54LSCIOC0ruC0vuC0pOC1jeC0sOC0ruC0vuC0r+C0v+C0sOC1geC0qOC1\njeC0qOC0v+C0suC1jeC0siDgtJ7gtL7gtbsg4LSo4LS/4LSo4LWN4LSo4LS/4LW9IOC0leC0o+C1\njeC0n+C0pOC1jS4uLiAg4LSS4LSw4LS/4LSV4LWN4LSV4LSy4LWB4LSCIOC0pOC0qOC0v+C0muC1\njeC0muC0vuC0leC1jeC0leC0vuC0pOC1hiDgtKjgtYbgtJ7gtY3gtJrgtYvgtJ/gtY0g4LSa4LWH\n4LW84LSk4LWN4LSkIOC0hiDgtLjgtY3gtKjgtYfgtLngtKTgtY3gtKTgtL/gtKjgtY3gtLHgtYYg\n4LSV4LSw4LWB4LSk4LWN4LSk4LWNIOC0leC1guC0n+C1hiDgtIbgtK/gtL/gtLDgtYHgtKjgtY3g\ntKjgtYEuLi4gCgoKICAgICDgtI7gtKjgtY3gtLHgtYYg4LSV4LS+4LSV4LWN4LSV4LWC4LSo4LWN\n4LSx4LWGIOC0huC0pOC1jeC0ruC0vuC1vOC0pOC1jeC0peC0ruC0vuC0ryDgtLjgtY3gtKjgtYfg\ntLngtKTgtY3gtKTgtL/gtKjgtY0g4LSo4LW94LSV4LS+4LW7IOC0juC0qOC1jeC0qOC0v+C1vSDg\ntJLgtKjgtY3gtKjgtYHgtK7gtL/gtLLgtY3gtLIuLi4gIOC0iCDgtLjgtY3gtKjgtYfgtLngtK7g\ntLLgtY3gtLLgtL7gtKTgtYYuLi4gIOC0juC0meC1jeC0leC0v+C0suC1geC0giAg4LSe4LS+4LW7\nIOC0qOC1veC0leC1geC0qOC1jeC0qOC1gS4g4LSO4LSo4LWN4LSx4LWGIOC0leC0vuC0leC1jeC0\nleC1guC0qOC1jSDwn5iN8J+YjQoK4LSO4LSo4LWN4LSx4LWGIOC0ueC1g+C0puC0r+C0giDgtKjg\ntL/gtLHgtJ7gtY3gtJ4g4LSc4LSo4LWN4LSu4LSm4LS/4LSo4LS+4LS24LSC4LS44LSV4LW+Li4u\nLiAgCgoK4LSS4LSw4LS/4LSV4LWN4LSV4LSy4LWB4LSCIOC0quC0v+C0sOC0v+C0r+C0vuC0pOC1\nhiDgtIgg4LS44LWN4LSo4LWH4LS54LSCIOC0juC0qOC1jeC0qOC1geC0giDgtKjgtL/gtLLgtKjg\ntL/gtb3gtJXgtY3gtJXgtJ/gtY3gtJ/gtYYuLi4gIOC0heC0pOC1i+C0n+C1iuC0quC1jeC0quC0\ngiDgtI7gtKjgtY3gtLHgtYYg4LSH4LSV4LWN4LSV4LS+4LSo4LWN4LSx4LWGIOC0nOC1gOC0teC0\nv+C0pOC0giDgtI7gtKjgtY3gtKjgtYHgtIIg4LS44LSo4LWN4LSk4LWL4LS34LS14LWB4LSCIOC0\nuOC0ruC0vuC0p+C0vuC0qOC0teC1geC0giDgtKjgtL/gtLHgtJ7gtY3gtJ7gtKTgtL7gtLXgtJ/g\ntY3gtJ/gtYYuLi4uLiAg4LSG4LSu4LWA4LW7Li4uLiDwn6Ww8J+lsPCfpbAKCgoKIOC0quC1jeC0\nsOC0vuC1vOC0pOC1jeC0peC0qOC0r+C1i+C0n+C1hiDgtJXgtL7gtJXgtY3gtJXgtYLgtKjgtY3g\ntLHgtYYg4LSV4LWB4LSe4LWN4LSe4LWL4LW+IOKdo+Kdow==\n', '0', 1, 9, 5, 0, '0', '0', 0, '0'),
(14, '9790', '04-11-2019 06:45:27 am', '8J+YrfCfmK3wn5it8J+YrfCfmK3wn5it8J+YrfCfmK3wn5it8J+YrfCfmK3wn5it8J+YrfCfmK0K\nCuC0juC0qOC1jeC0seC1hiDgtI7gtLLgtY3gtLLgtL4g4LS44LWN4LSx4LWN4LSx4LS+4LSx4LWN\n4LSx4LS44LWB4LSCIOC0quC1i+C0r+C0vyDwn5itCgogICAgICAgICAgICDgtJLgtLDgtYHgtKrg\ntL7gtJ/gtY0g4LST4LW84LSu4LSV4LSz4LWGICDgtLXgtL7gtJXgtY3gtJXgtYHgtJXgtLPgtL/g\ntb0g4LSk4LSz4LSa4LWN4LSa4LS/4LSf4LWN4LSf4LSq4LWN4LSq4LWL4LW+IOC0kuC0sOC1geC0\nquC0vuC0n+C1jSDgtKrgtY3gtLDgtKTgtYDgtJXgtY3gtLfgtK/gtL7gtK/gtL/gtLDgtYHgtKjg\ntY3gtKjgtYEg4LSIIOC0teC0vuC0leC1jeC0leC1geC0leC1viDgtJLgtLDgtL/gtJXgtY3gtJXg\ntLLgtYHgtIIg4LSu4LSw4LS/4LSV4LWN4LSV4LS/4LSy4LWN4LSy4LS+IOC0juC0qOC1jeC0qOC1\njSDwn5iiCiAgICAgICAgICAgICAgICDgtKrgtY3gtLDgtKTgtYDgtJXgtY3gtLcg4LSF4LSq4LWN\n4LSw4LSk4LWN4LSv4LSV4LWN4LS34LSu4LS+4LSv4LS/IOC0juC0suC1jeC0suC0vuC0giDgtJXg\ntYrgtKPgtY3gtJ/gtYHgtKrgtYvgtK/gtL8gCiAgICAgICAgICAgICAgICAgICAg4LST4LW84LSk\n4LWN4LSk4LS/4LSw4LS/4LSV4LWN4LSV4LS+4LW7IOC0qOC0suC1jeC0siDgtKjgtLLgtY3gtLIg\n4LSF4LSo4LWB4LSt4LS14LSZ4LWN4LSZ4LSz4LWGIOC0ruC0vuC0r+C0vuC0suC1i+C0leC0pOC1\njeC0pOC0v+C0suC1hiDgtLbgtL/gtLXgtLDgtL7gtKTgtY3gtLDgtL/gtKrgtYvgtLLgtYYg4LSS\n4LSz4LS/4LSq4LWN4LSq4LS/4LSa4LWN4LSa4LWB4LS14LWG4LSa4LWN4LSaIOC0qOC0v+C0ruC0\nv+C0t+C0meC1jeC0meC1viAg4LSO4LSy4LWN4LSy4LS+4LSCIOC0leC0vuC0seC1jeC0seC0v+C1\nvSDgtKrgtLHgtKTgtY3gtKTgtL/gtLXgtL/gtJ/gtY3gtJ8g4LSq4LSf4LWN4LSf4LSC4LSq4LWL\n4LSy4LWG4LSv4LS+4LSv4LS/IPCfmKIKCiAgICAgICAgICAgICAgICDgtI7gtKjgtY3gtLHgtYYg\n4LSq4LWL4LS44LWN4LSx4LWN4LSx4LWB4LSV4LW+IOC0pOC0v+C0sOC0v+C0leC1hiDgtLLgtK3g\ntL/gtJXgtY3gtJXgtYHgtK7gtYbgtKjgtY3gtKjgtY0g4LSq4LWN4LSw4LSk4LWA4LSV4LWN4LS3\n4LSv4LWL4LSf4LWGLgogICAgICAgICAgICAgICAgICAg8J+Viu+4jyDinI3vuI/gtKbgtL/gtLLg\ntY3gtLLgtYE=\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(15, '9790', '04-11-2019 06:46:33 am', '4LS54LWD4LSm4LSv4LSk4LWN4LSk4LS/4LW9IOC0qOC0v+C0qOC1jeC0qOC1jSDgtKjgtYfgtLDg\ntYHgtKjgtY3gtKjgtYEuLi4uIArgtJLgtLDgtL7gtK/gtL/gtLDgtIIg4LSc4LSo4LWN4LSu4LSm\n4LS/4LSo4LS+4LS24LSC4LS44LSV4LW+IOC0juC0qOC1jeC0seC1hiDgtKrgtY3gtLDgtL/gtK/g\ntKrgtY3gtKrgtYbgtJ/gtY3gtJ8g4LSV4LS+4LSV4LWN4LSV4LWC4LSo4LWNIPCfjoLwn46C8J+O\ngvCfjoLwn6eB8J+ngfCfjbDwn42w8J+NsPCfjbDwn42l8J+NpfCfjaXwn42r8J+Nq/Cfjazwn42s\n8J+NrPCfjanwn42p8J+NrfCfja3wn42h8J+NnvCfjZ7wn42e8J+NnvCfmI3wn5iN8J+YjfCfmI3w\nn5iY8J+YmPCfmJjwn5iY8J+YmAoKIiIiCgoK4LSIIOC0uOC1jeC0seC1jeC0seC0vuC0seC1jeC0\nseC0uOC1jSDgtJXgtYHgtJ/gtYHgtILgtKzgtKTgtY3gtKTgtL/gtb0g4LSe4LS+4LW7IOC0teC0\nqOC1jeC0qOC0v+C0n+C1jeC0n+C1jSAg4LS14LW84LS34LSCIDMg4LSG4LSv4LS/Li4uICDgtJLg\ntLDgtYHgtKrgtL7gtJ/gtY0g4LSo4LSy4LWN4LSyIOC0uOC1l+C0ueC1g+C0puC0meC1jeC0meC0\ns+C1hiDgtJXgtL/gtJ/gtY3gtJ/gtL8uLi4gIOC0ueC1g+C0puC0r+C0giDgtLngtYPgtKbgtK/g\ntKTgtY3gtKTgtYvgtJ/gtY0g4LSa4LWH4LW84LSk4LWN4LSk4LWNIOC0teC1huC0leC1jeC0leC0\nvuC1uyDgtJXgtLTgtL/gtK/gtYHgtKjgtY3gtKgg4LSS4LSk4LWN4LSk4LS/4LSw4LS/IOC0uOC1\njeC0qOC1h+C0ueC0rOC0qOC1jeC0p+C0meC1jeC0meC1vi4uLiAK4LS14LS/4LSw4LSy4LS/4LW9\nIOC0juC0o+C1jeC0o+C0vuC0teC1geC0qOC1jeC0qCDgtKjgtLLgtY3gtLLgtLXgtLDgtL7gtK8g\n4LSV4LWB4LSx4LSa4LWN4LSa4LWBIOC0leC1guC0n+C1jeC0n+C1geC0leC0vuC1vC4uLiAg4LSF\n4LS14LSw4LWK4LSV4LWN4LSV4LWGIOC0h+C0qOC1jeC0qOC1jSDgtI7gtLXgtL/gtJ/gtYYg4LSG\n4LSj4LS+4LS14LWLIOC0juC0qOC1jeC0qOC0seC0v+C0r+C0v+C0suC1jeC0si4uLiAg4LSq4LSV\n4LWN4LS34LWGIOC0k+C1vOC0leC1jeC0leC0vuC0seC1geC0o+C1jeC0n+C1jSDgtIYg4LSo4LSy\n4LWN4LSyIOC0qOC0v+C0ruC0v+C0t+C0meC1jeC0meC1vi4uLiAg4LS14LS04LSV4LWN4LSV4LWN\nIOC0leC1guC0n+C0v+C0r+C1geC0giDgtKrgtLDgtL7gtKTgtL8g4LSq4LSw4LS/4LSt4LS14LSZ\n4LWN4LSZ4LW+IOC0quC0seC0nuC1jeC0nuC1geC0giDgtJXgtK7gtKjgtY3gtLHgtY0g4LSs4LWL\n4LSV4LWN4LS44LS/4LW9IOC0muC0seC0quC0seC0viDgtLjgtILgtLjgtL7gtLDgtL/gtJrgtY3g\ntJog4LSo4LS+4LSz4LWB4LSV4LW+Li4uICAg4LSV4LWB4LSe4LWN4LSe4LWL4LW7Li4gIOC0heC0\nsOC1geC0o+C1h+C0n+C1jeC0n+C1uyDgtLngtKzgtL/gtJXgtY3gtJUg4LS44LW94LSu4LWB4LSV\n4LWN4LSVIOC0ruC1geC0pOC1jeC0pOC1jS4uIOC0seC0vuC0q+C0v+C0leC1jeC0lSAgIOC0uOC1\nhuC0suC1jeC0suC1gS4g4LSo4LS/4LS44LWCLi4g4LSu4LS/4LSo4LWN4LSo4LWC4LS44LWNLi4u\nICAg4LSm4LS/4LSy4LWN4LSy4LWBLi4uICDgtKjgtYLgtLHgtL8uLiAg4LSc4LWG4LS44LS/Li4u\nIC4uICDgtIXgtJngtY3gtJngtKjgtYYg4LSS4LSk4LWN4LSk4LS/4LSw4LS/IOC0quC1h+C0sOC1\njS4uLiAg4LSa4LS/4LSy4LSw4LWGIOC0h+C0n+C0leC1jeC0leC1jSDgtIfgtLXgtL/gtJ/gtYYg\n4LSV4LS+4LSj4LS+4LSx4LWB4LSj4LWN4LSf4LWNIOC0n+C1jeC0n+C1iy4uLi4g4p2j4p2j4p2j\n8J+lsPCfpbAKCiAKIOC0heC0pOC0v+C0suC1iuC0leC1jeC0leC1hiDgtLXgtL/gtKTgtY3gtK/g\ntLjgtY3gtKTgtK7gtL7gtK8g4LS44LWN4LSo4LWH4LS54LSCIOC0leC1iuC0o+C1jeC0n+C1geC0\ngiDgtKrgtLDgtL/gtJfgtKPgtKgg4LSV4LWK4LSj4LWN4LSf4LWB4LSCIOC0leC1huC0r+C1vCDg\ntJXgtYrgtKPgtY3gtJ/gtYHgtIIg4LSS4LSw4LWHIOC0quC1i+C0suC1hiDgtKjgtL/gtKjgtY3g\ntKgg4LSO4LSo4LWN4LSx4LWGIOC0quC1jeC0sOC0v+C0r+C0quC1jeC0quC1huC0n+C1jeC0nyDg\ntJXgtL7gtJXgtY3gtJXgtYEuLi4uICAo4LS34LS+4LSw4LWC4LSW4LWNIOC0t+C0vi4uLiApCgoK\nCiDgtI/gtKTgtY0g4LS44LSZ4LWN4LSV4LSf4LSk4LWN4LSk4LS/4LSy4LWB4LSCIOC0uOC0qOC1\njeC0pOC1i+C0t+C0pOC1jeC0pOC0v+C0suC1geC0giDgtIfgtKjgtY3gtKjgtYHgtIIg4LSO4LSo\n4LWN4LSo4LWB4LSCICDgtI7gtKrgtY3gtKrgtYvgtLTgtYHgtIIg4LSV4LWC4LSf4LWGIOC0qOC0\nv+C0qOC1jeC0qCDgtIYg4LS14LSy4LS/4LSvIOC0ruC0qOC0uOC0v+C0qOC1jeC0seC1hiDgtIng\ntJ/gtK4uLiAgCgoKIOC0hiDgtKrgtY3gtLDgtL/gtK/gtKrgtY3gtKrgtYbgtJ/gtY3gtJ8g4LSO\n4LSo4LWN4LSx4LWGIOC0leC0vuC0leC1jeC0leC1guC0qOC1jeC0seC1hiAKICAg4LSq4LS/4LSx\n4LSo4LWN4LSo4LS+4LW+IOC0nuC0vuC1uyDgtI7gtJngtY3gtJngtKjgtYYg4LSu4LSx4LSV4LWN\n4LSV4LWB4LSCLi4uICAKCgoKIiIgCuC0juC0qOC1jeC0seC1hiDgtJPgtLDgtYsg4LSa4LSy4LSo\n4LSZ4LWN4LSZ4LSz4LS/4LSy4LWB4LSCIOC0qOC0v+C0tuC1jeC0teC0vuC0uOC0giDgtKrgtYvg\ntLLgtYYgIOC0qOC1gCDgtLXgtKjgtY3gtKjgtKPgtJ7gtY3gtJ7gtKrgtY3gtKrgtYvgtb4g4LSS\n4LSw4LWBIOC0leC1guC0n+C1huC0quC1jeC0quC0v+C0seC0quC1jeC0quC0v+C0qOC1jeC0seC1\nhiDgtLjgtY3gtKjgtYfgtLngtIIg4LSu4LS+4LSk4LWN4LSw4LSu4LS+4LSv4LS/4LSw4LWB4LSo\n4LWN4LSo4LS/4LSy4LWN4LSyIOC0nuC0vuC1uyDgtKjgtL/gtKjgtY3gtKjgtL/gtb0g4LSV4LSj\n4LWN4LSf4LSk4LWNLi4uICDgtJLgtLDgtL/gtJXgtY3gtJXgtLLgtYHgtIIg4LSk4LSo4LS/4LSa\n4LWN4LSa4LS+4LSV4LWN4LSV4LS+4LSk4LWGIOC0qOC1huC0nuC1jeC0muC1i+C0n+C1jSDgtJrg\ntYfgtbzgtKTgtY3gtKQg4LSGIOC0uOC1jeC0qOC1h+C0ueC0pOC1jeC0pOC0v+C0qOC1jeC0seC1\nhiDgtJXgtLDgtYHgtKTgtY3gtKTgtY0g4LSV4LWC4LSf4LWGIOC0huC0r+C0v+C0sOC1geC0qOC1\njeC0qOC1gS4uLiAKCgogICAgIOC0juC0qOC1jeC0seC1hiDgtJXgtL7gtJXgtY3gtJXgtYLgtKjg\ntY3gtLHgtYYg4LSG4LSk4LWN4LSu4LS+4LW84LSk4LWN4LSl4LSu4LS+4LSvIOC0uOC1jeC0qOC1\nh+C0ueC0pOC1jeC0pOC0v+C0qOC1jSDgtKjgtb3gtJXgtL7gtbsg4LSO4LSo4LWN4LSo4LS/4LW9\nIOC0kuC0qOC1jeC0qOC1geC0ruC0v+C0suC1jeC0si4uLiAg4LSIIOC0uOC1jeC0qOC1h+C0ueC0\nruC0suC1jeC0suC0vuC0pOC1hi4uLiAg4LSO4LSZ4LWN4LSV4LS/4LSy4LWB4LSCICDgtJ7gtL7g\ntbsg4LSo4LW94LSV4LWB4LSo4LWN4LSo4LWBLiDgtI7gtKjgtY3gtLHgtYYg4LSV4LS+4LSV4LWN\n4LSV4LWC4LSo4LWNIPCfmI3wn5iNCgrgtI7gtKjgtY3gtLHgtYYg4LS54LWD4LSm4LSv4LSCIOC0\nqOC0v+C0seC0nuC1jeC0niDgtJzgtKjgtY3gtK7gtKbgtL/gtKjgtL7gtLbgtILgtLjgtJXgtb4u\nLi4uICAKCgrgtJLgtLDgtL/gtJXgtY3gtJXgtLLgtYHgtIIg4LSq4LS/4LSw4LS/4LSv4LS+4LSk\n4LWGIOC0iCDgtLjgtY3gtKjgtYfgtLngtIIg4LSO4LSo4LWN4LSo4LWB4LSCIOC0qOC0v+C0suC0\nqOC0v+C1veC0leC1jeC0leC0n+C1jeC0n+C1hi4uLiAg4LSF4LSk4LWL4LSf4LWK4LSq4LWN4LSq\n4LSCIOC0juC0qOC1jeC0seC1hiDgtIfgtJXgtY3gtJXgtL7gtKjgtY3gtLHgtYYg4LSc4LWA4LS1\n4LS/4LSk4LSCIOC0juC0qOC1jeC0qOC1geC0giDgtLjgtKjgtY3gtKTgtYvgtLfgtLXgtYHgtIIg\n4LS44LSu4LS+4LSn4LS+4LSo4LS14LWB4LSCIOC0qOC0v+C0seC0nuC1jeC0nuC0pOC0vuC0teC0\nn+C1jeC0n+C1hi4uLi4uICDgtIbgtK7gtYDgtbsuLi4uIPCfpbDwn6Ww8J+lsAoKCgog4LSq4LWN\n4LSw4LS+4LW84LSk4LWN4LSl4LSo4LSv4LWL4LSf4LWGIOC0leC0vuC0leC1jeC0leC1guC0qOC1\njeC0seC1hiDgtJXgtYHgtJ7gtY3gtJ7gtYvgtb4g4p2j4p2j\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(16, '13', '04-11-2019 06:48:10 am', '4LSH4LSk4LWNIOC0teC1gOC0o+C1jeC0n+C1geC0giDgtKTgtYHgtLHgtKjgtY3gtKjgtYvwn5mE\n8J+ZhPCfmYQK4LSq4LWL4LS44LWN4LSx4LWN4LSx4LWN4oCMIOC0juC0suC1jeC0suC0vuC0giDg\ntKrgtYvgtK/gtL8g8J+Yq/CfmKvwn5ir8J+Yq/CfmKsuLi4g\n', '0', 1, 6, 5, 0, '0', '0', 0, '0'),
(17, '14', '04-11-2019 06:51:37 am', '4LSu4LSV4LWN4LSV4LSz4LWGIOC0oeC1i+C0o+C1jeC0n+C1jSDgtLXgtLHgtL8uLiDgtI7gtLLg\ntY3gtLLgtL7gtIIg4LS24LWG4LSw4LS/IOC0r+C0vuC0leC1geC0gvCfmILwn5iC8J+YgvCfmILw\nn5iC8J+YgvCfmILwn5iC8J+YgvCfmILwn5iC8J+YgvCfmII=\n', '0', 1, 6, 5, 0, '0', '0', 0, '0'),
(18, '14', '04-11-2019 06:54:33 am', '4LSH4LS14LS/4LSf4LWGIOC0quC1i+C0r+C0vyDgtI7gtLLgtY3gtLLgtL4g4LSJ4LSf4LS+4LSv\n4LS/4LSq4LWN4LSq4LWNIOC0leC0s+C1geC0gi4uLi4uLi4uLvCfmIbwn5iG8J+YhvCfmILwn5iC\n8J+YgvCfmILwn5iC8J+YgvCfmILwn5iCLi4uLg==\n', '0', 1, 11, 8, 0, '0', '0', 0, '0'),
(19, '21802', '04-11-2019 07:12:46 am', '4LSS4LSw4LWBIOC0quC1geC0pOC1gSDgtKrgtY3gtLDgtK3gtL7gtKTgtIIg4LSV4LWC4LSf4LS/\nIOC0leC0vuC0o+C0vuC1uyDgtLjgtL7gtKfgtL/gtJrgtY3gtJrgtKTgtL/gtb0g4LS14LSz4LSw\n4LWGIOC0teC0s+C0sOC1hiDgtLjgtKjgtY3gtKTgtYvgtLfgtIIg4LSO4LSy4LWN4LSy4LS+4LS1\n4LW84LSV4LWN4LSV4LWB4LSCIOC0iCDgtKbgtL/gtKjgtIIg4LSo4LSy4LWN4LSy4LWK4LSw4LWB\nIOC0puC0v+C0qOC0giDgtIbgtJXgtJ/gtY3gtJ/gtYYg4LSO4LSo4LWN4LSo4LWNIOC0quC1jeC0\nsOC0vuC1vOC0pOC1jeC0peC0v+C0leC1jeC0leC1geC0qOC1jeC0qOC1gSAgICAgICAKCgogICAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAq4LS24LWB4LSt4LSm4LS/4LSo4LSCIOC0\nqOC1h+C0sOC1geC0qOC1jeC0qOC1gSogCgog\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(20, '21802', '04-11-2019 07:15:50 am', '4LSS4LSw4LWBIOC0quC1geC0pOC1gSDgtKrgtY3gtLDgtK3gtL7gtKTgtIIg4LSV4LWC4LSf4LS/\nIOC0leC0vuC0o+C0vuC1uyDgtLjgtL7gtKfgtL/gtJrgtY3gtJrgtKTgtL/gtb0g4LS14LSz4LSw\n4LWGIOC0teC0s+C0sOC1hiDgtLjgtKjgtY3gtKTgtYvgtLfgtIIg4LSO4LSy4LWN4LSy4LS+4LS1\n4LW84LSV4LWN4LSV4LWB4LSCIOC0iCDgtKbgtL/gtKjgtIIg4LSo4LSy4LWN4LSy4LWK4LSw4LWB\nIOC0puC0v+C0qOC0giDgtIbgtJXgtJ/gtY3gtJ/gtYYg4LSO4LSo4LWN4LSo4LWNIOC0quC1jeC0\nsOC0vuC1vOC0pOC1jeC0peC0v+C0leC1jeC0leC1geC0qOC1jeC0qOC1gSAgICAgICAKCgogICAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAq4LS24LWB4LSt4LSm4LS/4LSo4LSCIOC0\nqOC1h+C0sOC1geC0qOC1jeC0qOC1gSogCgog\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(21, '31767', '04-11-2019 07:16:04 am', '4LSa4LS/4LSy4LW8IOC0qOC0ruC1jeC0ruC0s+C1hiDgtJLgtLTgtL/gtLXgtL7gtJXgtY3gtJXg\ntL8g4LSq4LWL4LSv4LSq4LWN4LSq4LWL4LW+IOC0juC0qOC1jeC0pOC1iyDgtJzgtYDgtLXgtL/g\ntJXgtY3gtJXgtL7gtbsg4LSS4LSw4LWBIOC0leC1iuC0pOC0vyDgtIfgtKrgtY3gtKrgtYvgtb4g\n4LS54LS+4LSq4LWN4LSq4LS/IOC0huC0o+C1jSDgtKjgtK7gtY3gtK7gtLPgtY0g4LSO4LSo4LWN\n4LSo4LWB4LSCIOC0h+C0meC1jeC0meC0qOC1hiDgtIbgtLXgtJ/gtY3gtJ/gtYYKCuC0l+C1geC0\noeC1jSDgtK7gtYvgtbzgtKPgtL/gtILgtJfgtY0g\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(22, '31767', '04-11-2019 07:16:58 am', '4LSa4LS/4LSy4LW8IOC0qOC0ruC1jeC0ruC0s+C1hiDgtJLgtLTgtL/gtLXgtL7gtJXgtY3gtJXg\ntL8g4LSq4LWL4LSv4LSq4LWN4LSq4LWL4LW+IOC0juC0qOC1jeC0pOC1iyDgtJzgtYDgtLXgtL/g\ntJXgtY3gtJXgtL7gtbsg4LSS4LSw4LWBIOC0leC1iuC0pOC0vyDgtIfgtKrgtY3gtKrgtYvgtb4g\n4LS54LS+4LSq4LWN4LSq4LS/ICDgtIbgtKPgtY0g4LSo4LSu4LWN4LSu4LSz4LWNIOC0juC0qOC1\njeC0qOC1geC0giDgtIfgtJngtY3gtJngtKjgtYYg4LSG4LS14LSf4LWN4LSf4LWG\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(23, '35770', '04-11-2019 07:23:41 am', '4LS14LW84LSj4LWN4LSj4LSa4LWN4LSa4LWB4LS14LSw4LWB4LSV4LSz4LS/4LW9IOC0juC0tOC1\ngeC0pOC0v+C0ryDgtLXgtL7gtJXgtY3gtJXgtYHgtJXgtb7gtJXgtY3gtJXgtYEg4LS44LWN4LS1\n4LW84LSj4LSk4LWN4LSk4LS/4LSo4LWN4LSx4LWGIOC0ruC1guC0suC1jeC0r+C0ruC1geC0o+C1\njeC0n+C1huC0meC1jeC0leC0v+C1vSDgtIXgtKTgtY0gICLgtK7gtL7gtKTgtL4g4LSq4LS/4LSk\n4LS+IOC0l+C1geC0sOC1gSDgtI7gtKjgtY3gtKgg4LSIIOC0ruC1guC0qOC1jeC0qOC1jSDgtLXg\ntL7gtJXgtY3gtJXgtL7gtKPgtY0g\n', '0', 1, 1, 0, 0, '0', '0', 0, '0'),
(25, '13', '04-11-2019 07:37:58 am', '4LS44LW94LSu4LS+4LW7IOC0ruC1iuC0pOC0suC0vuC0s+C0vy4uLiDgtIfgtKTgtL/gtb0g4LSO\n4LSo4LWN4LSk4LS+4LSj4LWNIOC0h+C0quC1jeC0quC1iiDgtKrgtY3gtLDgtLbgtY3gtKjgtIIg\n4LS24LWG4LSw4LS/4LSV4LWN4LSV4LWB4LSCLi4uIAogICAgIOC0iCDgtIbgtKrgtY3gtKrgtY0g\n4LSJ4LSj4LWN4LSf4LS+4LSV4LWN4LSV4LS/4LSv4LSk4LWNIOC0tuC1huC0sOC0v+C0r+C0vuC0\nn+C1jeC0n+C0v+C0suC1jeC0si4uLiAKICAg4LSy4LWI4LSV4LWN4oCMIDEwIOC0o+C1jeC0o+C0\ngiDgtJXgtL7gtKPgtL/gtJXgtY3gtJXgtYHgtKjgtY3gtKjgtYEuIOC0pOC1geC0seC0qOC1jeC0\nqOC1gSDgtKjgtYvgtJXgtY3gtJXgtYHgtK7gtY3gtKrgtYvgtb4gNOC0o+C1jeC0o+C0giDgtJLg\ntJXgtY3gtJXgtYbgtK/gtYrgtLPgtY3gtLPgtYEuLi4gCiAgICAg4LSO4LSo4LWN4LSk4LS+4LSj\n4LWNIOC0h+C0pOC0v+C1vSDgtIfgtKrgtY3gtKrgtYog4LSo4LSf4LSV4LWN4LSV4LWB4LSo4LWN\n4LSo4LSk4LWNIPCfmKs=\n', '0', 1, 8, 8, 0, '0', '0', 0, '0'),
(26, '19', '04-11-2019 07:51:08 am', '4LSu4LSo4LS44LWN4LS44LWNIOC0uOC0meC1jeC0leC0n+C0giDgtJXgtYrgtKPgtY3gtJ/gtY0g\n4LSu4LWC4LSf4LWB4LSu4LWN4LSq4LWL4LW+IArgtJXgtKPgtY3gtKPgtYHgtJXgtb4g4LSm4LWB\n4LSD4LSW4LSCIOC0leC1iuC0o+C1jeC0n+C1jSDgtKjgtL/gtLHgtK/gtYHgtK7gtY3gtKrgtYvg\ntb4gLi4KMy7gtJXgtL7gtLDgtY3gtK/gtJngtY3gtJngtb4g4LST4LW84LSV4LWN4LSV4LWB4LSV\nIC4uLgoK4LSF4LSz4LWN4LSz4LS+4LS54LWBIOC0leC1guC0n+C1hiDgtIngtKPgtY3gtJ/gtY0g\nLi4uLgoK4LSH4LSq4LWN4LSq4LWL4LS04LWB4LSCIOC0leC1guC0n+C1hiDgtIngtKPgtY3gtJ/g\ntY0gLi4uCgrgtI7gtKrgtY3gtKrgtYvgtLTgtYHgtIIg4LSV4LWC4LSf4LWGIOC0ieC0o+C1jeC0\nn+C1jSAuLi4uLgoKCgoKCiDgtJfgtYHgtKHgtY0g4LSu4LWL4LW84LSj4LS/4LSC4LSX4LWNIA==\n', '0', 1, 9, 3, 0, '0', '0', 0, '0'),
(27, '19412', '04-11-2019 08:30:38 am', 'CuC0leC0s+C0meC1jeC0leC0ruC0v+C0suC1jeC0suC0vuC0pOC1jeC0pCwg4LSF4LSz4LS14LS/\n4LSy4LWN4LSy4LS+4LSk4LWN4LSkLCDgtLjgtY3gtKjgtYfgtLngtIIg4LSV4LS/4LSf4LWN4LSf\n4LS/4LSv4LS14LW84LSV4LWN4LSV4LWGIOC0uOC1jeC0qOC1h+C0ueC0pOC1jeC0pOC0v+C0qOC1\njeC0seC1hiDgtLXgtL/gtLIg4LSu4LSo4LS44LS/4LSy4LS+4LS14LWCLvCfmIoK4LSh4LS/4LSc\n4LWGIOKcje+4jwo=\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(28, '19412', '04-11-2019 08:32:04 am', 'CuC0leC0s+C0meC1jeC0leC0ruC0v+C0suC1jeC0suC0vuC0pOC1jeC0pCwg4LSF4LSz4LS14LS/\n4LSy4LWN4LSy4LS+4LSk4LWN4LSkLCDgtLjgtY3gtKjgtYfgtLngtIIg4LSV4LS/4LSf4LWN4LSf\n4LS/4LSv4LS14LW84LSV4LWN4LSV4LWGIOC0uOC1jeC0qOC1h+C0ueC0pOC1jeC0pOC0v+C0qOC1\njeC0seC1hiDgtLXgtL/gtLIg4LSu4LSo4LS44LS/4LSy4LS+4LS14LWCLvCfmIoK4LSh4LS/4LSc\n4LWGIOKcje+4jwo=\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(29, '21802', '04-11-2019 08:41:00 am', '8J+YjfCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI3w\nn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCf\nmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI0=\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(30, '21802', '04-11-2019 08:41:22 am', '4LSS4LSw4LWBIOC0quC1geC0pOC1gSDgtKrgtY3gtLDgtK3gtL7gtKTgtIIg4LSV4LWC4LSf4LS/\nIOC0leC0vuC0o+C0vuC1uyDgtLjgtL7gtKfgtL/gtJrgtY3gtJrgtKTgtL/gtb0g4LS14LSz4LSw\n4LWGIOC0teC0s+C0sOC1hiDgtLjgtKjgtY3gtKTgtYvgtLfgtIIg4LSO4LSy4LWN4LSy4LS+4LS1\n4LW84LSV4LWN4LSV4LWB4LSCIOC0iCDgtKbgtL/gtKjgtIIg4LSo4LSy4LWN4LSy4LWK4LSw4LWB\nIOC0puC0v+C0qOC0giDgtIbgtJXgtJ/gtY3gtJ/gtYYg4LSO4LSo4LWN4LSo4LWNIOC0quC1jeC0\nsOC0vuC1vOC0pOC1jeC0peC0v+C0leC1jeC0leC1geC0qOC1jeC0qOC1gSAgICAgICAKCgogICAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAq4LS24LWB4LSt4LSm4LS/4LSo4LSCIOC0\nqOC1h+C0sOC1geC0qOC1jeC0qOC1gSogCgog\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(31, '12', '04-11-2019 08:44:59 am', '4LSq4LWN4LSw4LSt4LS+4LSk4LS44LWC4LSw4LWN4LSv4LSo4LWN4LSx4LWGIArgtIbgtJfgtK7g\ntKjgtIIK4LSG4LSY4LWL4LS34LS/4LSV4LWN4LSV4LWB4LSo4LWN4LSoIArgtJrgtYbgtLHgtYEg\n4LSa4LS+4LSx4LWN4LSx4LW94LSu4LS0Li4K4LSa4LWA4LS14LWA4LSf4LWN4LSV4LSz4LWB4LSf\n4LWGIOC0huC1vOC0pOC1jeC0pCDgtKjgtL7gtKbgtJngtY3gtJngtb7gtJXgtYrgtKrgtY3gtKrg\ntIIgCuC0leC0v+C0s+C0v+C0leC0s+C1geC0n+C1hiDgtLjgtY3gtLXgtLAg4LSk4LS+4LSzIOC0\nruC1h+C0s+C0gi4uCuC0quC1geC1veC0qOC0vuC0ruC1jeC0quC1geC0leC0s+C0v+C1vSAK4LSq\n4LSx4LWN4LSx4LS/4LSq4LS/4LSf4LS/4LSa4LWN4LSaIOC0ueC0v+C0riDgtJXgtKPgtJngtY3g\ntJngtb4uLi4gCuC0juC0pOC1jeC0sCDgtK7gtKjgtYvgtLngtLDgtIIg4LSIIOC0quC1jeC0sOC0\nreC0vuC0pOC0gi4u\n', '0', 1, 11, 1, 0, '0', '0', 0, '0'),
(32, '13', '04-11-2019 08:49:21 am', '4LSV4LWC4LSv4LWNIOC0h+C0pOC0v+C1vSDgtJXgtYLgtLHgtYcg4LSq4LWH4LSw4LWGIAog4LSo\n4LWL4LSf4LWN4LSf4LS/4LSr4LS/4LSV4LWN4LSV4LWH4LS34LW7IOC0teC0sOC1geC0qOC1jeC0\nqOC1geC0o+C1jeC0n+C0suC1jeC0suC1iy4uIOC0rOC0n+C1jeC0n+C1jeKAjCDgtIbgtKrgtY3g\ntKrgtY0g4LSk4LWB4LSx4LSo4LWN4LSo4LS+4LW9IOC0heC0teC0sOC1huC0r+C1iuC0qOC1jeC0\nqOC1geC0giDgtJXgtL7gtKPgtL7gtbsg4LSq4LSx4LWN4LSx4LWB4LSo4LWN4LSo4LS/4LSy4LWN\n4LSyLi4uIOC0h+C0pOC1jSDgtI7gtKjgtY3gtLHgtYYg4LSa4LS/4LSo4LWN4LSk4LSV4LW+4LSV\n4LWN4LSV4LWNIOC0ruC0vuC0pOC1jeC0sOC0ruC1geC0s+C1jeC0syDgtJXgtYHgtLTgtKrgtY3g\ntKrgtIIg4LSG4LSj4LWLLi4uIOC0heC0pOC1iyDgtKjgtL/gtJngtY3gtJngtb7gtJXgtY3gtJXg\ntYHgtIIg4LSH4LSj4LWN4LSf4LWLIA==\n', '0', 1, 10, 33, 0, '0', '0', 0, '0'),
(33, '19', '04-11-2019 08:50:07 am', '4LSV4LS+4LSj4LS+4LSk4LS+4LSV4LWB4LSu4LWN4LSq4LWL4LW+IArgtK7gtLHgtKjgtY3gtKjg\ntYEg4LSk4LWB4LSf4LSZ4LWN4LSZ4LWB4LSo4LWN4LSo4LSk4LS+4LSV4LSw4LWB4LSk4LWNIAog\nICAgICDgtLjgtZfgtLngtYPgtKbgtIIuLi4gCuC0leC0vuC0sOC0o+C0giDgtKTgtL/gtLDgtJXg\ntY3gtJXgtL8gCuC0pOC0v+C0sOC0nuC1jeC0nuC1gSDgtLXgtLDgtYHgtKjgtY3gtKjgtKTgtL7g\ntJXgtKPgtIIgCiAgICAg4LS44LWX4LS54LWD4LSm4LSCCgrinIzvuI/inIzvuI/inIzvuI8=\n', '0', 1, 10, 0, 0, '0', '0', 0, '0'),
(34, '21', '04-11-2019 08:59:43 am', '4LSS4LSw4LWBIOC0quC1geC0pOC1gSDgtKrgtY3gtLDgtK3gtL7gtKTgtIIg4LSV4LWC4LSf4LS/\nIOC0leC0vuC0o+C0vuC1uyDgtLjgtL7gtKfgtL/gtJrgtY3gtJrgtKTgtL/gtb0g4LS14LSz4LSw\n4LWGIOC0teC0s+C0sOC1hiDgtLjgtKjgtY3gtKTgtYvgtLfgtIIg4LSO4LSy4LWN4LSy4LS+4LS1\n4LW84LSV4LWN4LSV4LWB4LSCIOC0iCDgtKbgtL/gtKjgtIIg4LSo4LSy4LWN4LSy4LWK4LSw4LWB\nIOC0puC0v+C0qOC0giDgtIbgtJXgtJ/gtY3gtJ/gtYYg4LSO4LSo4LWN4LSo4LWNIOC0quC1jeC0\nsOC0vuC1vOC0pOC1jeC0peC0v+C0leC1jeC0leC1geC0qOC1jeC0qOC1gSAgICAgICAKCgogICAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAq4LS24LWB4LSt4LSm4LS/4LSo4LSCIOC0\nqOC1h+C0sOC1geC0qOC1jeC0qOC1gSogCgog\n', '0', 1, 9, 2, 0, '0', '0', 0, '0'),
(35, '9790', '04-11-2019 09:00:50 am', '4LSo4LSu4LWN4LSu4LSz4LWG4LSq4LWN4LSq4LSx4LWN4LSx4LS/IOC0ruC0qOC0uOC1jeC0uOC0\nv+C0suC0vuC0leC1geC0qOC1jeC0qOC0teC1vCAK4LSV4LWC4LSf4LWN4LSf4LWB4LSV4LWC4LSf\n4LS+4LW7IOC0h+C0t+C1jeKAjOC0n+C0quC1jeC0quC1huC0n+C1geC0giAK4LSu4LSo4LS44LWN\n4LS44LS/4LSy4LS+4LSV4LWN4LSV4LS+4LSk4LWN4LSk4LS14LW84LSV4LWN4LSV4LWNIOC0leC1\nguC0n+C1jeC0n+C1geC0leC1guC0n+C0vuC0qOC1geC0giAK4LSH4LS34LWN4oCM4LSf4LSu4LWB\n4LSj4LWN4LSf4LS+4LS14LS/4LSy4LWN4LSyLiEKCuC0uOC1l+C0ueC1g+C0puC0giDgtKrgtL/g\ntJ/gtL/gtJrgtY3gtJrgtYHgtLXgtL7gtJngtY3gtJngtL7gtKjgtL7gtLXgtL/gtLLgtY3gtLIg\nCuC0quC1jeC0sOC0o+C0r+C0guC0quC1i+C0suC1hiDgtK7gtKjgtLjgtY3gtLjgtL/gtb0g4LSk\n4LWL4LSo4LWN4LSo4LWB4LSo4LWN4LSo4LSk4LS+IOC0heC0pOC1jSAKCgrwn5WK77iP4pyN77iP\n4LSm4LS/4LSy4LWN4LSy4LWB\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(36, '19412', '04-11-2019 09:01:01 am', 'CuC0leC0s+C0meC1jeC0leC0ruC0v+C0suC1jeC0suC0vuC0pOC1jeC0pCwg4LSF4LSz4LS14LS/\n4LSy4LWN4LSy4LS+4LSk4LWN4LSkLCDgtLjgtY3gtKjgtYfgtLngtIIg4LSV4LS/4LSf4LWN4LSf\n4LS/4LSv4LS14LW84LSV4LWN4LSV4LWGIOC0uOC1jeC0qOC1h+C0ueC0pOC1jeC0pOC0v+C0qOC1\njeC0seC1hiDgtLXgtL/gtLIg4LSu4LSo4LS44LS/4LSy4LS+4LS14LWCLvCfmIoK4pyN77iPCg==\n', '0', 1, 0, 1, 0, '0', '0', 0, '0'),
(37, '9790', '04-11-2019 09:01:27 am', '4LSo4LSu4LWN4LSu4LSz4LWG4LSq4LWN4LSq4LSx4LWN4LSx4LS/IOC0ruC0qOC0uOC1jeC0uOC0\nv+C0suC0vuC0leC1geC0qOC1jeC0qOC0teC1vCAK4LSV4LWC4LSf4LWN4LSf4LWB4LSV4LWC4LSf\n4LS+4LW7IOC0h+C0t+C1jeKAjOC0n+C0quC1jeC0quC1huC0n+C1geC0giAK4LSu4LSo4LS44LWN\n4LS44LS/4LSy4LS+4LSV4LWN4LSV4LS+4LSk4LWN4LSk4LS14LW84LSV4LWN4LSV4LWNIOC0leC1\nguC0n+C1jeC0n+C1geC0leC1guC0n+C0vuC0qOC1geC0giAK4LSH4LS34LWN4oCM4LSf4LSu4LWB\n4LSj4LWN4LSf4LS+4LS14LS/4LSy4LWN4LSyLiEKCuC0uOC1l+C0ueC1g+C0puC0giDgtKrgtL/g\ntJ/gtL/gtJrgtY3gtJrgtYHgtLXgtL7gtJngtY3gtJngtL7gtKjgtL7gtLXgtL/gtLLgtY3gtLIg\nCuC0quC1jeC0sOC0o+C0r+C0guC0quC1i+C0suC1hiDgtK7gtKjgtLjgtY3gtLjgtL/gtb0g4LSk\n4LWL4LSo4LWN4LSo4LWB4LSo4LWN4LSo4LSk4LS+IOC0heC0pOC1jSAKCgrwn5WK4pyN4LSm4LS/\n4LSy4LWN4LSy4LWB8J+SnvCfkp7wn5KeOgoK\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(38, '20', '04-11-2019 09:01:47 am', 'CgouLi4uLi4uLi4uLuC0juC0suC1jeC0suC0vuC0teC1vOC0leC1jeC0leC1geC0giDgtI7gtKjg\ntY3gtLHgtYYg4LS24LWB4LSt4LSm4LS/4LSoIOC0huC0tuC0guC0uOC0leC1vvCfjLnwn4y58J+M\nuQ==\n', '0', 1, 9, 25, 0, '0', '0', 0, '0'),
(39, '9790', '04-11-2019 09:02:06 am', '4LSo4LSu4LWN4LSu4LSz4LWG4LSq4LWN4LSq4LSx4LWN4LSx4LS/IOC0ruC0qOC0uOC1jeC0uOC0\nv+C0suC0vuC0leC1geC0qOC1jeC0qOC0teC1vCAK4LSV4LWC4LSf4LWN4LSf4LWB4LSV4LWC4LSf\n4LS+4LW7IOC0h+C0t+C1jeKAjOC0n+C0quC1jeC0quC1huC0n+C1geC0giAK4LSu4LSo4LS44LWN\n4LS44LS/4LSy4LS+4LSV4LWN4LSV4LS+4LSk4LWN4LSk4LS14LW84LSV4LWN4LSV4LWNIOC0leC1\nguC0n+C1jeC0n+C1geC0leC1guC0n+C0vuC0qOC1geC0giAK4LSH4LS34LWN4oCM4LSf4LSu4LWB\n4LSj4LWN4LSf4LS+4LS14LS/4LSy4LWN4LSyLiEKCuC0uOC1l+C0ueC1g+C0puC0giDgtKrgtL/g\ntJ/gtL/gtJrgtY3gtJrgtYHgtLXgtL7gtJngtY3gtJngtL7gtKjgtL7gtLXgtL/gtLLgtY3gtLIg\nCuC0quC1jeC0sOC0o+C0r+C0guC0quC1i+C0suC1hiDgtK7gtKjgtLjgtY3gtLjgtL/gtb0g4LSk\n4LWL4LSo4LWN4LSo4LWB4LSo4LWN4LSo4LSk4LS+IOC0heC0pOC1jSAKCgrwn5WK4pyN4LSm4LS/\n4LSy4LWN4LSy4LWB8J+SnvCfkp7wn5KeOgoK\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(40, '36914', '04-11-2019 09:02:55 am', '4LSX4LWB4LSh4LWNIOC0ruC1i+C1vOC0o+C0v+C0guC0l+C1jSDgtJPgtb4g4LSr4LWN4LSw4LSj\n4LWN4LSf4LWN4oCM4LS44LWNIPCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCf\nmI3wn5iN8J+YjfCfmI0=\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(41, '35770', '04-11-2019 09:09:11 am', '4LSO4LW7IOC0luC0suC1jeC0rOC0v+C0suC1hiDgtKrgtY3gtLDgtKPgtK/gtKTgtY3gtKTgtL/g\ntKjgtY3gtLHgtYYg4LSu4LWC4LSz4LS/4LSq4LWN4LSq4LS+4LSf4LWN4LSf4LWNIArgtKjgtL/g\ntKjgtK/gtY3gtJXgtY3gtJXgtL7gtK/gtL8g4LSS4LSw4LWBIOC0h+C0tuC1vSDgtLDgtL7gtJfg\ntKTgtY3gtKTgtL/gtb0uLi4u4LSq4LS+4LSf4LSf4LWN4LSf4LWGIOC0quC1huC0o+C1jeC0o+C1\nhiA=\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(42, '36914', '04-11-2019 09:16:22 am', '4LSO4LS14LS/4LSf4LWG4LSv4LWLIOC0juC0qOC1jeC0pOC1iyDgtKTgtJXgtLDgtL7gtbwg4LSq\n4LSf4LWN4LSf4LS/4LSf4LWN4LSf4LWB4LSj4LWN4LSf4LWNIArgtLjgtb3gtK7gtYEg4LSO4LSy\n4LWN4LSy4LS+4LSCIOC0juC0pOC1jeC0sOC0r+C1geC0giDgtKrgtYbgtJ/gtY3gtJ/gtKjgtY3g\ntKjgtY0g4LS24LWG4LSw4LS/4LSv4LS+4LSV4LWB4LSu4LWG4LSo4LWN4LSo4LWNIOC0quC1jeC0\nsOC0pOC1gOC0leC1jeC0t+C0v+C0leC1jeC0leC1geC0qOC1jeC0qOC1gSDwn5iN8J+YjfCfmI0=\n', '0', 1, 1, 0, 0, '0', '0', 0, '0'),
(43, '27', '04-11-2019 09:35:51 am', 'CuC0leC0o+C1jeC0o+C1geC0leC1viDgtKrgtLDgtLjgtY3gtKrgtLDgtIIg4LSV4LSl4LSV4LW+\nIOC0quC0seC0nuC1jeC0nuC0v+C0n+C1jeC0n+C0v+C0suC1jeC0si4uLiDgtIngtLPgtY3gtLPg\ntL/gtLLgtYYg4LSF4LSo4LWB4LSw4LS+4LSX4LSCIOC0leC1iCDgtLXgtL/gtLDgtLLgtYHgtJXg\ntLPgtL/gtb0g4LSk4LS+4LSz4LSu4LS/4LSf4LWN4LSf4LS/4LSf4LWN4LSf4LS/4LSy4LWN4LSy\nLi4uIOC0muC1geC0o+C1jeC0n+C1geC0leC1viDgtJXgtYLgtJ/gtL/gtJrgtYfgtbzgtKjgtY3g\ntKjgtYrgtLDgtYEg4LS14LS44LSo4LWN4LSk4LS14LWB4LSCIOC0ieC0o+C1jeC0n+C0vuC0r+C0\nv+C0n+C1jeC0n+C0v+C0suC1jeC0si4uLiDgtJLgtLTgtL/gtJ7gtY3gtJ4g4LS14LS04LS/4LSv\n4LWL4LSw4LSZ4LWN4LSZ4LSz4LS/4LW9IOC0leC0vuC0pOC1jeC0pOC1geC0qOC0v+C0qOC1jeC0\nqOC0v+C0n+C1jeC0n+C0v+C0suC1jeC0si4uLi4g4LSF4LSV4LWN4LS34LSw4LSZ4LWN4LSZ4LSz\n4LS/4LW9IOC0quC1jeC0sOC0o+C0r+C0pOC1jeC0pOC1hiDgtJXgtYvgtbzgtKTgtY3gtKTgtL/g\ntKPgtJXgtY3gtJXgtL/gtK/gtL/gtJ/gtY3gtJ/gtL/gtLLgtY3gtLIuLi4g4LSq4LSw4LS44LWN\n4LSq4LSw4LSCIOC0quC1jeC0sOC0o+C0r+C0pOC1jeC0pOC0v+C0qOC1jeC0seC1hiDgtKrgtYLg\ntJXgtY3gtJXgtb4g4LSV4LWIIOC0ruC0vuC0seC0v+C0r+C0v+C0n+C1jeC0n+C0v+C0suC1jeC0\nsi4uLiDgtI7gtJngtY3gtJXgtL/gtLLgtYHgtIIg4LSo4LS/4LS44LWN4LS44LS54LSv4LS+4LSv\n4LS/IOC0huC0leC0vuC0tuC0teC1geC0giDgtK3gtYLgtK7gtL/gtK/gtYHgtIIg4LSq4LWL4LSy\n4LWGIOC0quC1jeC0sOC0o+C0r+C0v+C0leC1jeC0leC1geC0leC0r+C0vuC0o+C1jS4uLiDgtKrg\ntLDgtLjgtY3gtKrgtLDgtIIuLi4uIA==\n', '1', 1, 15, 0, 0, '0', '0', 0, '0'),
(45, '34', '04-11-2019 09:39:59 am', 'I+C0uOC1i+C0seC0vyDgtKrgtLLgtKrgtY3gtKrgtYvgtLTgtYHgtIIg4LSG4LSm4LWN4LSv4LSC\nIOC0leC1jeC0t+C0riDgtJrgtYvgtKbgtL/gtJXgtY3gtJXgtYHgtKjgtY3gtKjgtKTgtY0gIOC0\npOC1huC0seC1jeC0seC1jSDgtJrgtYbgtK/gtY3gtKTgtLXgtLDgtL7gtK/gtL/gtLDgtL/gtJXg\ntY3gtJXgtL/gtLLgtY3gtLIg4LSs4LSo4LWN4LSn4LSZ4LWN4LSZ4LW+4LSV4LWN4LSV4LWN4oCM\nIOC0teC0v+C0suC0leC1veC0quC1jeC0quC0v+C0leC1jeC0leC1geC0qOC1jeC0qOC0teC0sOC0\nvuC0r+C0v+C0sOC0v+C0leC1jeC0leC1geC0gi4uLi4uLi4=\n', '0', 1, 10, 0, 0, '0', '0', 0, '0'),
(46, '34', '04-11-2019 09:40:36 am', '4LSu4LSx4LSV4LWN4LSV4LSw4LWB4LSk4LWNIOC0pOC0ruC1jeC0ruC0v+C1vSDgtKrgtJngtY3g\ntJXgtL/gtJ/gtY3gtJ8g4LSw4LS54LS44LWN4LSv4LSZ4LWN4LSZ4LW+4LSV4LWN4LSV4LWNIOC0\nteC0v+C0tuC1jeC0teC0vuC0uOC0giDgtI7gtKjgtY3gtKjgtYrgtLDgtbzgtKTgtY3gtKXgtIIg\n4LSV4LWC4LSf4LS/4LSv4LWB4LSj4LWN4LSf4LSo4LWN4LSo4LWNLi4uLg==\n', '0', 1, 7, 0, 0, '0', '0', 0, '0'),
(48, '38', '04-11-2019 09:41:49 am', '8J+SmuC0leC0o+C1jeC0n+C0teC1vCDgtLXgtYDgtKPgtY3gtJ/gtYHgtIIg4LS14LWA4LSj4LWN\n4LSf4LWB4LSCIOC0leC0o+C1jeC0n+C1huC0qOC1jeC0qOC1gSDgtKrgtLHgtK/gtYHgtK7gtY3g\ntKrgtYvgtb4g4LSo4LS/4LSo4LWN4LSx4LWGIOC0luC1veC0rOC1jSDgtLXgtYfgtKbgtKjgtL/g\ntJXgtY3gtJXgtYHgtKjgtY3gtKjgtL/gtLLgtY3gtLLgtYcuLi4KCvCfkpbgtJXgtL7gtKPgtYfg\ntKPgtY3gtJ/gtYcg4LSo4LSu4LWB4LSV4LWN4LSV4LWB4LSCPz8/4LSV4LSw4LSz4LS/4LSo4LWN\n4LSx4LWGIOC0leC0sOC0s+C0vuC0ryDgtK7gtYHgtKTgtY3gtKTgtY0g4LSo4LSs4LS/IOC0pOC0\nmeC1jeC0meC0s+C1hiAg2LXZhNmJINin2YTZhNmHINi52YTZitmHINmI2LPZhNmFIC4uKgoKICAg\nICAgICAgICAgICDgtIfgtbvgtLfgtL4g4LSF4LSy4LWN4LSy4LS+4LS54LWNLi4uCgrwn5KT4LSV\n4LS+4LSj4LWB4LSCISEhIOC0uOC1jeC0teC0suC0vuC0pOC1jeC0pOC0v+C0qOC1hiDgtK7gtYHg\ntLHgtYHgtJXgtYYg4LSq4LS/4LSf4LS/4LSa4LWN4LSa4LWNIOC0leC1iuC0o+C1jeC0n+C1jSDg\ntKjgtL7gtKXgtKjgtL/gtLLgtYfgtJXgtY3gtJXgtY0g4LSk4LWX4LSsIOC0muC1huC0r+C1jeC0\npOC1gSDgtK7gtJ/gtJngtY3gtJngtYIuLi4g4LSk4LWA4LSw4LWN4oCN4LSa4LWN4LSa4LSv4LS+\n4LSv4LWB4LSCIOC0leC0vuC0o+C1geC0gi4uLiEhCgog2LXZjtmE2ZHZjtmJINin2YTZhNmR2Y7Z\nh9mPINi52Y7ZhNmw2Ykg2YXZj9it2Y7ZhdmR2Y7Yr9mSINi12Y7ZhNmR2Y7ZiSDYp9mE2YTZkdmO\n2YfZjyDYudmO2YTZjtmK2YfZkCDZiNmO2LPZjtmE2ZHZjtmF\n', '0', 1, 11, 0, 0, '0', '0', 0, '0'),
(49, '40', '04-11-2019 09:44:34 am', '4LSX4LWB4LSh4LWNIOC0ruC1i+C1vOC0o+C0v+C0guC0l+C1jSAgICAgICAgICAgICAgICAgICAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIA==\n', '0', 1, 8, 3, 0, '0', '0', 0, '0'),
(50, '24', '04-11-2019 09:45:50 am', 'CuC0kuC0seC1jeC0seC0teC1gOC0tOC1jeC0muC1jeC0muC0r+C0v+C0suC1geC0n+C0nuC1jeC0\nnuC1geC0quC1i+C0leC1geC0qOC1jeC0qCAK4LSc4LWA4LS14LSo4LWN4LSx4LWG4LSv4LWK4LSw\n4LWBIOC0muC0v+C0suC1jeC0suC1geC0quC0vuC0pOC1jeC0sOC0giDgtK7gtL7gtKTgtY3gtLDg\ntIIgCiLgtJzgtYDgtLXgtL/gtKTgtII=\n', '1', 1, 11, 2, 0, '0', '0', 0, '0'),
(51, '28', '04-11-2019 09:46:46 am', '4LSJ4LSq4LSm4LWH4LS24LS/4LSV4LWN4LSV4LS+4LW7CiAgICAgICDgtJ7gtL7gtbsK4LSG4LSw\n4LWB4LSu4LSy4LWN4LSyIOC0juC0qOC1jeC0qOC0seC0v+C0r+C0vuC0ggogICAgICDgtI7gtJng\ntY3gtJXgtL/gtLLgtYHgtIIK4LSq4LSx4LSv4LWB4LS14LS+Li4uLi4K4LSo4LS/4LSZ4LWN4LSZ\n4LW+4LSV4LWNIArgtI7gtKjgtY3gtKjgtYYg4LSV4LSj4LWN4LSf4LWNCuC0quC0oOC0v+C0muC1\nguC0n+C1hvCfmJw=\n', '0', 1, 10, 1, 0, '0', '0', 0, '0'),
(52, '12', '04-11-2019 09:49:08 am', '4LSu4LSo4LS44LWN4LS44LS/4LW9IOC0qOC0v+C0seC0nuC1jeC0nuC0vuC0n+C1geC0giAK4LSo\n4LS/4LW7IOC0muC1iOC0pOC0qOC1jeC0r+C0giAK4LSk4LWC4LSV4LWB4LSCIOC0sOC1guC0quC0\ngi4uLiAK4LSS4LSw4LWB4LSu4LS+4LSk4LWN4LSwCuC0ruC0v+C0qOC1jeC0qOC0v+C0ruC0seC0\nr+C1geC0qOC1jeC0qOC1gSAgCuC0muC0v+C0sOC0v+C0pOC1guC0leC1geC0giAK4LSo4LS/4LW7\nIOC0teC0puC0qOC0gi4uLiAK4LSF4LSV4LWN4LS34LSw4LSZ4LWN4LSZ4LSz4LS/4LW9IOC0qOC0\nv+C0seC0r+C1geC0giAK4LSo4LS/4LW7IOC0quC1jeC0sOC0o+C0r+C0vuC1vOC0puC1jeC0sCAK\n4LSt4LS+4LS14LSCIArgtI7gtbsg4LSu4LWL4LS54LSZ4LWN4LSZ4LSz4LWGIArgtKTgtLDgtLPg\ntL/gtKTgtK7gtL7gtJXgtYHgtKjgtY3gtKjgtYEuLi4gCuC0ruC0tOC0teC0v+C0suC1jeC0suC0\nv+C1uyAK4LSu4LWL4LS54LS14LW84LSj4LWN4LSj4LSZ4LWN4LSZ4LW+IArgtIXgtLLgtL/gtJ7g\ntY3gtJ7gtYEg4LSa4LWH4LSw4LWB4LSo4LWN4LSo4LWBIArgtLngtYPgtKbgtKTgtY3gtKTgtL/g\ntLLgtYbgtKjgtY3gtKjgtYHgtIIuLiAK4LSo4LS/4LSo4LSV4LWN4LSV4LS+4LSv4LWNIArgtK7g\ntYDgtJ/gtY3gtJ/gtYHgtKjgtY3gtKgg4LS14LWA4LSj4LSv4LS+4LSv4LWNIArgtK7gtL7gtLHg\ntL/gtJ/gtYHgtKjgtY3gtKjgtYEg4LSe4LS+4LW7Li4uLi4g4pyN77iP\n', '1', 1, 7, 1, 0, '0', '0', 0, '0'),
(53, '31', '04-11-2019 09:50:54 am', '4LSV4LS+4LSj4LS+4LSk4LS+4LSV4LWB4LSu4LWN4LSq4LWL4LW+IArgtK7gtLHgtKjgtY3gtKjg\ntYEg4LSk4LWB4LSf4LSZ4LWN4LSZ4LWB4LSo4LWN4LSo4LSk4LS+4LSV4LSw4LWB4LSk4LWNIAog\nICAgICDgtLjgtZfgtLngtYPgtKbgtIIuLi4gCuC0leC0vuC0sOC0o+C0giDgtKTgtL/gtLDgtJXg\ntY3gtJXgtL8gCuC0pOC0v+C0sOC0nuC1jeC0nuC1gSDgtLXgtLDgtYHgtKjgtY3gtKjgtKTgtL7g\ntJXgtKPgtIIgCiAgICAg4LS44LWX4LS54LWD4LSm4LSCLi4uLg==\n', '0', 1, 7, 0, 0, '0', '0', 0, '0'),
(54, '40', '04-11-2019 09:52:51 am', '4LSH4LS14LS/4LSf4LWGIOC0h+C0pOC1huC0qOC1jeC0pOC1gSDgtKrgtLHgtY3gtLHgtL8uIOC0\nteC0suC1jeC0siDgtKrgtY3gtLDgtLPgtK/gtLXgtYHgtIIg4LS14LSo4LWN4LSo4LWLLiDgtJLg\ntKjgtY3gtKjgtYHgtIIg4LSV4LS+4LSj4LWB4LSo4LWN4LSo4LS/4LSy4LWN4LSyIA==\n', '0', 1, 11, 2, 0, '0', '0', 0, '0'),
(55, '34', '04-11-2019 09:55:31 am', '4LSF4LSx4LS/4LSv4LS+4LSk4LWGIOC0teC1gOC0oyDgtIngtLHgtYHgtK7gtY3gtKrgtL/gtKjg\ntY3gtLHgtYYg4LS24LS14LSCIOC0muC0vuC0r+C0r+C0v+C1vSDgtJXgtKPgtY3gtJ/gtKrgtY3g\ntKrgtYsg4LSo4LWG4LSx4LWN4LSx4LS/IOC0muC1geC0s+C0v+C0nuC1jeC0nuC1gSAuLi4g4LSF\n4LSx4LS/4LSe4LWN4LSe4LWL4LSj4LWN4LSf4LWNIOC0leC1iuC0qOC1jeC0qOC0v+C0n+C1jeC0\nnyDgtJXgtYvgtLTgtYDgtK/gtYHgtJ/gtYYg4LS24LS14LSCIOC0rOC0v+C0sOC0v+C0r+C0vuC0\no+C1gOC1vSDgtJXgtKPgtY3gtJ/gtKrgtY3gtKrgtYsg4LSa4LWB4LSj4LWN4LSf4LWBIOC0teC0\nv+C0n+C1vOC0qOC1jeC0qOC1gQ==\n', '0', 1, 10, 0, 0, '0', '0', 0, '0'),
(56, '19', '04-11-2019 10:03:55 am', '4LSH4LSk4LS+4LSj4LWNIOC0nuC0vuC1uyDgtKrgtYvgtLjgtY3gtLHgtY3gtLHgtY3igIwg4LSH\n4LSf4LS+4LSk4LWGIOC0kuC0sOC1gSDgtKbgtL/gtLXgtLjgtIIg4LSO4LSy4LWN4LSy4LS+4LSC\nIOC0kuC0seC1jeC0sSDgtIXgtJ/gtL/gtJXgtY3gtJXgtY0g4LSq4LWL4LS14LWB4LSCIOC0h+C0\nquC1jeC0quC1iiDgtI7gtLLgtY3gtLLgtL7gtLDgtYfgtIIg4LSq4LWL4LS44LWN4LSx4LWN4LSx\n4LWN4oCMIOC0quC1i+C0r+C0v+C0suC1jeC0suC1hyDgtJrgtL/gtLLgtLDgtYYg4LSO4LSk4LWN\n4LSw4LWGIOC0quC1i+C0uOC1jeC0seC1jeC0seC1jeKAjCAgIOC0juC0suC1jeC0suC0vuC0giDg\ntKrgtYvgtK/gtL8g8J+YgvCfmILwn5iC4pyM77iP\n', '0', 1, 10, 6, 0, '0', '0', 0, '0'),
(57, '27', '04-11-2019 10:13:13 am', 'CuC0quC1iuC0n+C0vyDgtKrgtL/gtJ/gtL/gtJrgtY3gtJog4LSq4LWB4LS44LWN4oCM4LSk4LSV\n4LSZ4LWN4LSZ4LW+IOC0quC1huC0seC1geC0leC1jeC0leC0v+C0r+C1huC0n+C1geC0leC1jeC0\nleC0o+C0gi4uLi4g4LSa4LS/4LSk4LW9IOC0rOC0vuC0leC1jeC0leC0vyDgtLXgtYbgtJrgtY3g\ntJrgtYHgtKrgtYvgtK8g4LSF4LSV4LWN4LS34LSw4LSZ4LWN4LSZ4LW+4LSV4LWN4LSV4LWB4LSu\n4LWA4LSk4LWGIOC0teC1gOC0o+C1jeC0n+C1geC0giDgtLXgtYDgtKPgtY3gtJ/gtYHgtIIgIOC0\njuC0tOC1geC0pOC0vyDgtKjgtL/gtKjgtY3gtKjgtYYg4LSq4LWB4LSo4LW84LSc4LSo4LS/4LSq\n4LWN4LSq4LS/4LSV4LWN4LSV4LSj4LSCLi4uICA=\n', '1', 1, 12, 0, 0, '0', '0', 0, '0'),
(59, '35770', '04-11-2019 10:21:05 am', '4LSG4LSm4LWN4LSv4LS+4LSo4LWB4LSw4LS+4LSX4LSk4LWN4LSk4LS/4LSo4LWN4LSx4LWGIOC0\nruC1iuC0tOC0vyDgtKjgtL/gtbsg4LSV4LS+4LSk4LS/4LW9IOC0muC1iuC0suC1jeC0suC0v+C0\nr+C0pOC1geC0gi4uLuC0hiDgtKjgtL/gtK7gtL/gtLfgtIIg4LSu4LSx4LWB4LSu4LWK4LS04LS/\n4LSv4LS/4LSy4LWN4LSy4LS+4LSk4LWGIOC0qOC1gCDgtKTgtKjgtY3gtKgg4LSG4LSm4LWN4LSv\n4LSa4LWB4LSC4LSs4LSo4LS14LWB4LSCIOC0ruC0seC0leC1jeC0leC0vuC0qOC0vuC0leC0vuC0\npOC1jeC0pCDgtKrgtY3gtLDgtL/gtK/gtKjgtL/gtK7gtL/gtLfgtIIg4LSk4LSo4LWN4LSo4LWG\nIA==\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(60, '23199', '04-11-2019 10:21:15 am', '4LSc4LWA4LS14LS/4LSk4LSCIOC0teC1jeC0r+C0pOC1jeC0r+C0uOC1jeC0pOC0pSDgtJXgtYrg\ntKPgtY3gtJ/gtY0g4LS44LSu4LWN4LSq4LSo4LWN4LSo4LSu4LS+4LSj4LWNLi4g4LSc4LWA4LS1\n4LS/4LSk4LSk4LWN4LSk4LS/4LW9IOC0leC0v+C0n+C1jeC0n+C0v+C0ryDgtLjgtZfgtLngtYPg\ntKbgtJngtY3gtJngtLPgtYHgtIIg4LSF4LSk4LWB4LSq4LWL4LSy4LWGIOC0teC1jeC0r+C0pOC1\njeC0r+C0uOC1jeC0peC0sOC0vuC0o+C1jeKAjCDinIzvuI8KCuC0juC0suC1jeC0suC0viDgtLjg\ntY3gtKjgtYfgtLngtIIg4LSo4LS/4LSx4LSe4LWN4LSeIOC0leC1guC0n+C1jeC0n+C1geC0leC0\nvuC1vOC0leC1jeC0leC1geC0giDgtKjgtKjgtY3gtK4g4LSo4LS/4LSx4LSe4LWN4LSeIOC0puC0\nv+C0qOC0giDgtKjgtYfgtLDgtYHgtKjgtY3gtKjgtYEg8J+SkA==\n', '0', 1, 1, 0, 0, '0', '0', 0, '0'),
(61, '36841', '04-11-2019 10:21:30 am', 'CgrgtIfgtKTgtY0g4LSw4LSj4LWN4LSf4LWA4LS44LSCIOC0juC0n+C1huC0sOC1jeC0qOC1jeC0\nqOC1jSDwn5iQ8J+YkPCfmJAKCuC0h+C0ruC1i+C0nOC0vyDgtI7gtLLgtY3gtLLgtYrgtIIg4LSu\n4LS+4LSx4LWN4LSx4LS/IOC0huC0leC1hiDgtIXgtb3gtJXgtYHgtb3gtKTgtY3gtKTgtL7gtJXg\ntY3gtJXgtL8g4LSy4LWN4LSy4LWHIPCfmKPwn5ij8J+YowoK\n', '0', 1, 1, 0, 0, '0', '0', 0, '0'),
(62, '49', '04-11-2019 10:29:25 am', '4LSa4LS/4LSyIOC0uOC0meC1jeC0leC0n+C0meC1jeC0meC1viDgtIbgtLDgtYvgtJ/gtYbgtJng\ntY3gtJXgtL/gtLLgtYHgtIIg4LSq4LSx4LSe4LWN4LSe4LS/4LSy4LWN4LSy4LWG4LSZ4LWN4LSV\n4LS/4LW9IOC0reC1jeC0sOC0vuC0qOC1jeC0pOC1jeKAjCDgtKrgtL/gtJ/gtL/gtJXgtY3gtJXg\ntYHgtK7gtYbgtKjgtY3gtKjgtYEg4LSk4LWL4LSo4LWN4LSo4LS+4LSx4LWB4LSj4LWN4LSf4LWN\nLiDgtKrgtLHgtK/gtL7gtbvgtK7gtL7gtKTgtY3gtLDgtIIg4LS44LWN4LSo4LWH4LS54LS14LWB\n4LSCIOC0heC0teC0leC0vuC0tuC0teC1geC0giDgtLjgtY3gtLXgtL7gtKTgtKjgtY3gtKTgtY3g\ntLDgtY3gtK/gtLXgtYHgtIIg4LSJ4LSz4LWN4LSz4LS14LSw4LWB4LSf4LWGIOC0heC0n+C1geC0\nleC1jeC0leC1vSDgtJPgtJ/gtL/gtJrgtY3gtJrgtYbgtLLgtY3gtLLgtYHgtK7gtY3gtKrgtYvg\ntb4g4LSF4LS14LSw4LWB4LSf4LWGIOC0muC0v+C0sOC0v+C0leC1jeC0leC1geC0qOC1jeC0qCDg\ntK7gtYHgtJbgtKTgtY3gtKTgtYHgtKjgtYvgtJXgtY3gtJXgtL8g4LSS4LSo4LWN4LSo4LWB4LSC\nIOC0quC0seC0r+C0vuC1uyDgtJXgtLTgtL/gtK/gtL7gtKTgtYYg4LSa4LS/4LSw4LS/4LSa4LWN\n4LSa4LWG4LSo4LWN4LSo4LWBIOC0teC0sOC1geC0pOC1jeC0pOC0vyDgtKTgtL/gtLDgtL/gtJXg\ntYYg4LSq4LWL4LSo4LWN4LSo4LS/4LSf4LWN4LSf4LWB4LSu4LWB4LSj4LWN4LSf4LWNLgrgtJXg\ntKPgtY3gtKPgtY0g4LSo4LS/4LSx4LSe4LWN4LSe4LWNLi4KCuC0heC0nOC1gS4uLi4uLvCfjLk=\n', '0', 1, 14, 2, 0, '0', '0', 0, '0'),
(63, '36841', '04-11-2019 10:31:19 am', 'CuC0leC0ruC0qOC1jeC0seC1jeC0qOC1geC0giDgtKrgtYvgtLfgtY3igIzgtJ/gtY3gtJ/gtY3g\ntKjgtYHgtIIgIOC0kuC0leC1jeC0leC1hiDgtJzgtYDgtLXgtbsg4LS14LWG4LSa4LWN4LSa4LWL\nIOC0uOC1jeC0teC0r+C0giDgtKHgtL/gtLLgtYDgtLHgtY3gtLHgtY0g4LSG4LS14LWB4LSo4LWN\n4LSo4LWBIPCfmJAKCiAgICAgICAgICAgIA==\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(64, '9523', '04-11-2019 10:32:51 am', '4LS54LS/4LSc4LS+4LSs4LWNIOC0heC0suC0meC1jeC0leC0vuC0sOC0ruC0vuC0leC1jeC0leC0\nv+C0ryDgtKrgtYbgtKPgtY3gtKPgtL/gtKjgtYvgtJ/gtLLgtY3gtLIuLi4gCiAgIOC0ueC0v+C0\nnOC0vuC0rOC1jSDgtIXgtK3gtL/gtK7gtL7gtKjgtK7gtL7gtJXgtY3gtJXgtL/gtK8g4LSq4LWG\n4LSj4LWN4LSj4LS/4LSo4LWL4LSf4LS+IOC0qOC0v+C0leC1jeC0leC1jSDgtIfgtLfgtY3gtJ/g\ntY3gtJ/gtILwn5iN8J+YjfCfmI0g\n', '1', 1, 0, 0, 0, '0', '0', 0, '0'),
(65, '9523', '04-11-2019 10:34:41 am', '4LS54LS/4LSc4LS+4LSs4LWNIOC0heC0suC0meC1jeC0leC0vuC0sOC0ruC0vuC0leC1jeC0leC0\nv+C0ryDgtKrgtYbgtKPgtY3gtKPgtL/gtKjgtYvgtJ/gtLLgtY3gtLIuLi4gCiAg4LS54LS/4LSc\n4LS+4LSs4LWNIOC0heC0reC0v+C0ruC0vuC0qOC0ruC0vuC0leC1jeC0leC0v+C0ryDgtKrgtYbg\ntKPgtY3gtKPgtL/gtKjgtYvgtJ/gtL4g4LSo4LS/4LSV4LWN4LSV4LWNIOC0h+C0t+C1jeC0n+C1\njeC0n+C0giDwn5iN8J+YjQ==\n', '1', 1, 0, 0, 0, '0', '0', 0, '0'),
(66, '12', '04-11-2019 10:34:44 am', '4LSo4LSo4LSe4LWN4LSe4LWK4LSf4LWN4LSf4LS/4LSvIOC0qOC0v+C0tOC1vSDgtKrgtYvgtLLg\ntYHgtIIgCuC0leC0o+C1jeC0o+C1jSDgtJrgtL/gtK7gtY3gtK7gtYHgtKjgtY3gtKgg4LSIIOC0\nuOC0qOC1jeC0p+C1jeC0r+C0r+C0v+C1vQrgtKjgtL/gtKjgtY3gtKjgtYvgtJ/gtYbgtKjgtL/g\ntJXgtY3gtJXgtY0g4LSF4LSt4LS/4LSo4LS/4LS14LWH4LS24LSu4LS+4LSj4LWNLgrgtKjgtJfg\ntY3gtKjgtK7gtL7gtK8g4LSq4LWB4LSx4LSC4LSu4LWH4LSo4LS/4LSv4LS/4LSy4LWC4LSf4LWG\nIOC0muC1geC0o+C1jeC0n+C1geC0leC0s+C1i+C0n+C0v+C0muC1jeC0muC1jSDgtLXgtL/gtJ/g\ntLXgtYHgtJXgtLPgtL/gtb0g4LSo4LS+4LS14LWNIOC0leC1iuC0o+C1jeC0n+C1jSDgtJXgtLPg\ntIIg4LS14LSw4LSa4LWN4LSa4LWNLCDgtJrgtYHgtKPgtY3gtJ/gtYHgtJXgtLPgtL7gtb0g4LSV\n4LS14LW84LSo4LWN4LSo4LWG4LSf4LWB4LSV4LWN4LSV4LWB4LSu4LWN4LSq4LWL4LW+IOC0qOC0\nv+C0qOC1jeC0qOC0v+C0suC1hiDgtLDgtYvgtK7gtJXgtYLgtKrgtLDgtJngtY3gtJngtLPgtYYg\n4LSq4LWL4LSy4LWB4LSCIOC0nuC0vuC1uyDgtKrgtY3gtLDgtKPgtK/gtL/gtJXgtY3gtJXgtYHg\ntKjgtY3gtKjgtYEuCiDgtJrgtYHgtILgtKzgtKjgtKTgtY3gtKTgtL7gtb0g4LSq4LWK4LSz4LWN\n4LSz4LS/4LSvIOC0quC1guC0teC0v+C1vSDgtKjgtL/gtKjgtY3gtKjgtYHgtIIg4LSc4LSy4LSn\n4LS+4LSw4LSV4LW+IOC0uOC1jeC0teC0r+C0giDgtK7gtLHgtKjgtY3gtKjgtYEg4LSS4LS04LWB\n4LSV4LWB4LSu4LWN4LSq4LWL4LW+LCDgtK7gtKfgtYHgtKrgtL7gtKjgtKTgtY3gtKTgtL7gtb0g\n4LS14LS/4LS34LSCIOC0pOC1gOC0o+C1jeC0n+C0vyDgtI7gtKjgtY3gtLHgtYYg4LSm4LS+4LS5\n4LSZ4LWN4LSZ4LW+IOC0kuC0n+C1geC0meC1jeC0meC0vuC0pOC1jeC0pOC0pOC0vuC0teC0o+C0\ngi4KIOC0uOC0v+C0sOC0leC0s+C0v+C1vSDgtIXgtJfgtY3gtKjgtL8g4LSq4LSf4LW84LSk4LWN\n4LSk4LS/IOC0puC0v+C0qOC0vuC0qOC1jeC0pOC1jeC0r+C0pOC1jeC0pOC0v+C1vQrgtKjgtL/g\ntbsg4LS14LS/4LSv4LW84LSq4LWN4LSq4LS/4LW7IOC0rOC0vuC0t+C1jeC0quC0p+C0vuC0sCDg\ntKjgtYHgtJXgtLDgtYHgtK7gtY3gtKrgtYvgtb5fCuC0leC1iuC0pOC0v+C0r+C0qOC1huC0qOC1\njeC0qOC1jSDgtKrgtYfgtLDgtYEg4LSo4LW94LSV4LSj4LSu4LWG4LSo4LS/4LSV4LWN4LSV4LWN\nLg==\n', '0', 1, 11, 2, 0, '0', '0', 0, '0'),
(67, '9523', '04-11-2019 10:39:33 am', '4pi64LSO4LSo4LWN4LSx4LWGIOC0luC1veC0rOC0v+C1veKdpCDgtKjgtL/gtKjgtJXgtY3gtJXg\ntYrgtLDgtL/gtJ/gtK7gtYHgtKPgtY3gtJ/gtY0uLi7wn5KT4LSu4LSx4LWN4LSx4LS+4LW84LSV\n4LWN4LSV4LWB4LSCIOC0qOC1veC0leC0vuC0pOC1hiDgtKjgtL/gtKjgtJXgtY3gtJXgtL7gtK/g\ntY0g4LSu4LS+4LSx4LWN4LSx4LS/4LS14LWG4LSa4LWN4LSa4LWK4LSw4LS/4LSf4LSCLi4u8J+Y\njeC0heC0pOC1jSDgtIfgtKjgtL8g4LSO4LSk4LWN4LSw4LWGIOC0ruC1iuC0nuC1jeC0muC0pOC1\njeC0pOC0v+C0ruC0vuC1vPCfkbHwn4+74oCN4pmA4LS14LSo4LWN4LSo4LWNIOC0ruC1geC1u+C0\nquC1jeC0quC0v+C1vSDgtKjgtL/gtKjgtY3gtKjgtL7gtLLgtYHgtIIs8J+YieC0heC0pOC1huC0\nqOC1jeC0qOC1geC0giDgtIXgtLXgtL/gtJ/gtYYg4LSk4LSo4LWN4LSo4LWGIOC0leC0vuC0o+C1\ngeC0gi4uLvCfmJgq\n', '1', 1, 0, 0, 0, '0', '0', 0, '0'),
(68, '9523', '04-11-2019 10:40:47 am', '4LSH4LSf4LWB4LSo4LWN4LSoIOC0uOC1jeC0seC1jeC0seC0vuC0seC1jeC0seC0uOC1jSDgtJLg\ntKjgtY3gtKjgtYHgtIIg4LSk4LSo4LWN4LSo4LWGIOC0leC0vuC0o+C1geC0qOC1jeC0qOC0v+C0\nsuC1jeC0suC0vuC0suC1iyDgtLjgtb3gtK7gtL7gtbsg4LSs4LWN4LSw4LWLLi4gCgogICAg\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(69, '50', '04-11-2019 10:44:51 am', '4LSq4LWB4LSk4LS/4LSv4LWK4LSw4LWBIOC0suC1i+C0leC0giDgtKrgtL/gtLHgtLXgtL/gtK/g\ntYbgtJ/gtYHgtJXgtY3gtJXgtYHgtILgtLXgtLDgtYYg4LSe4LS+4LW7IOC0qOC0vuC0s+C0v+C0\npOC1geC0teC0sOC1hiDgtIbgtLjgtY3gtLXgtKbgtL/gtJrgtY3gtJrgtY0g4LSV4LSj4LWN4LSf\n4LWNIOC0pOC1gOC1vOC0pOC1jeC0pCDgtLjgtY3gtLXgtKrgtY3igIzgtKjgtJngtY3gtJngtYHg\ntJ/gtYYg4LST4LW84LSu4LWN4LSu4LSV4LSz4LS/4LW9IOC0nOC1gOC0teC0v+C0leC1jeC0leC0\nn+C1jeC0n+C1hi4uLvCflJzwn5mMCgrCruKApuKApuKApuKApvCfkaTigKbigKbigKbigKbCqQ==\n', '0', 1, 11, 5, 0, '0', '0', 0, '0'),
(70, '13', '04-11-2019 11:01:49 am', '4LS44LWN4LS14LSv4LSCIOC0ruC0qOC0uOC1jeC0uOC0v+C0suC0vuC0leC1jeC0leC0vuC1uyDg\ntJLgtLDgtb3gtKrgtY3gtKrgtKjgtYfgtLDgtIIg4LSo4LS+4LSCIOC0qOC0ruC1jeC0ruC1geC0\nleC1jeC0leC0vuC0r+C1jSDgtK7gtL7gtKTgtY3gtLDgtIIg4LSu4LS+4LSx4LWN4LSx4LS/4LS1\n4LWG4LSa4LWN4LSa4LWB4LSV4LWK4LSj4LWN4LSf4LWNIOC0qOC0ruC1jeC0ruC1i+C0n+C1gSDg\ntKTgtKjgtY3gtKjgtYYg4LS44LWN4LS14LSv4LSCIOC0muC1i+C0puC0v+C0leC1jeC0leC1geC0\nlSDgtKjgtL7gtK7gtYrgtLDgtYEg4LS24LSw4LS/4LSv4LWLIOC0pOC1huC0seC1jeC0seC1iy4u\nLi4uLj8qCgoKICDgtI7gtIIg4LW9IOC0quC0vyA=\n', '0', 1, 8, 1, 0, '0', '0', 0, '0'),
(71, '23199', '04-11-2019 11:03:00 am', '4LSc4LWA4LS14LS/4LSk4LSCIOC0juC0qOC1jeC0qOC1geC0giDgtLXgtY3gtK/gtKTgtY3gtK/g\ntLjgtY3gtKXgtKQg4LSV4LWK4LSj4LWN4LSf4LWNIOC0uOC0ruC1jeC0quC0qOC1jeC0qOC0ruC0\nvuC0o+C1jS4uIOC0nOC1gOC0teC0v+C0pOC0pOC1jeC0pOC0v+C1vSDgtJXgtL/gtJ/gtY3gtJ/g\ntL/gtK8g4LS44LWX4LS54LWD4LSm4LSZ4LWN4LSZ4LSz4LWB4LSCIOC0heC0pOC1jSDgtKrgtYvg\ntLLgtYYg4LS14LWN4LSv4LSk4LWN4LSv4LS44LWN4LSl4LSu4LS+4LSj4LWNLi4gCgrgtLjgtY3g\ntKjgtYfgtLngtIIg4LSo4LS/4LSx4LSe4LWN4LSeIOC0juC0suC1jeC0suC0viDgtJXgtYLgtJ/g\ntY3gtJ/gtYHgtJXgtL7gtbzgtJXgtY3gtJXgtYHgtIIg4LSo4LSo4LWN4LSuIOC0qOC0v+C0seC0\nnuC1jeC0niDgtKbgtL/gtKjgtIIg4LSo4LWH4LSw4LWB4LSo4LWN4LSo4LWBIPCfkpDinIzvuI8=\n', '0', 1, 0, 0, 0, '0', '0', 0, '0');
INSERT INTO `status` (`sn`, `userid`, `postdate`, `status`, `commentboxblock`, `visibilestatus`, `likes`, `comment`, `statustype`, `photourl`, `photodemension`, `pinned`, `photostatus`) VALUES
(72, '36914', '04-11-2019 11:04:30 am', '4LSo4LSu4LWN4LSu4LSz4LWGIOC0uOC1jeC0seC1jeC0seC0vuC0seC1jeC0seC0uOC1geC0giDg\ntK7gtKPgtY3gtJ/gtY3gtLjgtYEg4LSS4LSo4LWN4LSo4LWB4LSCIOC0leC0vuC0o+C0vuC1uyDg\ntIfgtLLgtY3gtLIgCuC0leC0ruC0qOC1jeC0seC1jeKAjCDgtIfgtJ/gtY3gtJ/gtL7gtb0g4LSF\n4LSq4LWN4LSq4LWLIOC0ruC0vuC0nuC1jeC0nuC1gSDgtKrgtYvgtLXgtYHgtKjgtY3gtKjgtYEg\n', '0', 1, 1, 0, 0, '0', '0', 0, '0'),
(73, '19', '04-11-2019 11:04:59 am', '4LSH4LSk4LS/4LW9IOC0seC0v+C0nOC1gSDgtKjgtL/gtLfgtYEgIOC0juC0qOC1jeC0qCDgtKrg\ntY3gtLDgtYfgtKTgtIIg4LSH4LSx4LSZ4LWN4LSZ4LS/4LSf4LWN4LSf4LWNICDgtIngtKPgtY3g\ntJ/gtY0g4LSO4LSy4LWN4LSy4LS+4LSw4LWB4LSCIOC0tuC1jeC0sOC0puC1jeC0p+C0v+C0leC1\njeC0leC1geC0lSAg4LST4LW+IOC0juC0suC1jeC0suC0viDgtKrgtYvgtLjgtY3gtLHgtY3gtLHg\ntL/gtLLgtYHgtIIg4LSV4LSu4LSo4LWN4LSx4LWNIOC0qOC1i+C0n+C1jeC0n+C0vyDgtLXgtLDg\ntYHgtKjgtY3gtKjgtYEg4LSs4LSf4LWN4LSf4LWN4oCMIOC0qOC1i+C0leC1jeC0leC1geC0ruC1\njeC0quC1iyDgtJXgtL7gtKPgtYHgtKjgtY3gtKjgtL/gtLLgtY3gtLIg8J+YgvCfmILinIzvuI8=\n', '0', 1, 8, 13, 0, '0', '0', 0, '0'),
(74, '13', '04-11-2019 11:06:03 am', '4LSa4LSZ4LWN4LSZ4LS+4LSv4LS/4LSu4LS+4LSw4LWGIOC0h+C0pOC0v+C0quC1jeC0quC1iyDg\ntK3gtK/gtJngtY3gtJXgtLAg4LSV4LWL4LSu4LSh4LS/IOC0pOC0qOC1jeC0qOC1hi4uIPCfmIHw\nn5iB8J+YgeC0kuC0sOC1gSDgtKrgtYvgtLjgtY3gtLHgtY3gtLHgtL/gtKjgtY0g4LSS4LSw4LWB\nIOC0suC1iOC0leC1jSDgtJXgtL/gtJ/gtY3gtJ/gtL/gtK/gtL7gtb0g4LSF4LSk4LWNIOC0heC0\nnuC1jeC0muC0vuC0r+C0vyDgtJXgtL7gtKPgtL/gtJXgtY3gtJXgtL4uLiDwn6Sp8J+kqfCfpKnw\nn6Sp8J+kqfCfpKnwn6Sp8J+kqeC0rOC0suC1jeC0suC0vuC0pOC1jeC0pCDgtJzgtL7gtKTgtL8u\nLiAK4LSH4LSo4LS/IOC0h+C0meC1jeC0meC0qOC1hiDgtKTgtKjgtY3gtKjgtYYg4LSq4LWL4LSv\n4LS+4LW9IOC0ruC0pOC0v+C0r+C0vuC0r+C0v+C0sOC1geC0qOC1jeC0qOC1gSDwn6Sp8J+kqQ==\n', '0', 1, 9, 38, 0, '0', '0', 0, '0'),
(75, '54', '04-11-2019 11:13:57 am', 'CuC0leC0s+C0meC1jeC0leC0ruC0v+C0suC1jeC0suC0vuC0pOC1jeC0pCwg4LSF4LSz4LS14LS/\n4LSy4LWN4LSy4LS+4LSk4LWN4LSkLCDgtLjgtY3gtKjgtYfgtLngtIIg4LSV4LS/4LSf4LWN4LSf\n4LS/4LSv4LS14LW84LSV4LWN4LSV4LWGIOC0uOC1jeC0qOC1h+C0ueC0pOC1jeC0pOC0v+C0qOC1\njeC0seC1hiDgtLXgtL/gtLIg4LSu4LSo4LS44LS/4LSy4LS+4LS14LWCLvCfmIoK4LSh4LS/4LSc\n4LWGIOKcje+4jwo=\n', '1', 1, 10, 0, 0, '0', '0', 0, '0'),
(76, '8', '04-11-2019 11:18:25 am', '4LSH4LS14LS/4LSf4LWGIOC0juC0qOC1jeC0pOC0viDgtJLgtLDgtYEg4LSF4LSo4LSV4LWN4LSV\n4LS14LWB4LSu4LS/4LSy4LWN4LSy4LSy4LWLLi4uICDgtI7gtLLgtY3gtLLgtL7gtLXgtLDgtYHg\ntIIgIOC0ruC0tOC0r+C0v+C1vSAg4LSS4LSy4LS/4LSa4LWN4LSa4LWBICDgtKrgtYvgtK/gtYsu\nLi4g8J+ZhPCfmYQ=\n', '0', 1, 10, 3, 0, '0', '0', 0, '0'),
(77, '28', '04-11-2019 11:18:47 am', '4LSa4LS/4LSyIOC0h+C0t+C1jeC0n+C0meC1jeC0meC1vuKZpe+4jwrgtJLgtJXgtY3gtJXgtYYg\nCuC0quC1jeC0sOC0o+C0r+C0pOC1jeC0pOC0v+C0qOC1geC0giAK4LSG4LSq4LWN4LSq4LWB4LSx\n4LSCIOC0huC0o+C1jfCfkazwn5Gs\n', '0', 1, 9, 2, 0, '0', '0', 0, '0'),
(78, '14', '04-11-2019 11:20:00 am', '4LSO4LSo4LWN4LSx4LWGIOC0qOC1i+C0n+C1jeC0n+C0vyDgtJLgtJXgtY3gtJXgtYYg4LSq4LWL\n4LSv4LS/Li4uLi45MDAg4LSo4LWL4LSf4LWN4LSf4LS/IOC0ieC0o+C1jeC0n+C0vuC0r+C0v+C0\nsOC1geC0qOC1jeC0qOC1gfCfmILwn5iC8J+Ygi4u4LSF4LSk4LWK4LSV4LWN4LSV4LWGIOC0quC1\ni+C0r+C0v/CfmILwn5iC8J+YgvCfmILgtI7gtKjgtY3gtKTgtL4g4LSa4LWG4LSv4LWN4LSv4LS+\nIOC0ruC0leC1jeC0leC0s+C1hw==\n', '0', 1, 8, 13, 0, '0', '0', 0, '0'),
(79, '58', '04-11-2019 11:22:31 am', '4LSa4LS/4LSy4LSw4LS/4LSZ4LWN4LSZ4LSo4LWGIOC0muC1h+C0seC0v+C1vSDgtJXgtL7gtLLg\ntYLgtKjgtY3gtKjgtYHgtKjgtY3gtKjgtKTgtYHgtJXgtYrgtKPgtY3gtJ/gtL7gtKPgtY0g4LSo\n4LSu4LWN4LSu4LW+IOC0muC1i+C0seC0v+C1vSDgtLXgtL/gtLDgtLLgtYLgtKjgtY3gtKjgtYHg\ntKjgtY3gtKjgtKTgtY0uLi4hISEhCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg\nICAg\n', '0', 1, 15, 18, 0, '0', '0', 0, '0'),
(80, '36914', '04-11-2019 11:24:50 am', '4LSa4LS/4LSyIOC0uOC0meC1jeC0leC0n+C0meC1jeC0meC1viDgtIbgtLDgtYvgtJ/gtYbgtJng\ntY3gtJXgtL/gtLLgtYHgtIIg4LSq4LSx4LSe4LWN4LSe4LS/4LSy4LWN4LSy4LWG4LSZ4LWN4LSV\n4LS/4LW9IOC0reC1jeC0sOC0vuC0qOC1jeC0pOC1jeKAjCDgtKrgtL/gtJ/gtL/gtJXgtY3gtJXg\ntYHgtK7gtYbgtKjgtY3gtKjgtYEg4LSk4LWL4LSo4LWN4LSo4LS+4LSx4LWB4LSj4LWN4LSf4LWN\nLiDgtKrgtLHgtK/gtL7gtbvgtK7gtL7gtKTgtY3gtLDgtIIg4LS44LWN4LSo4LWH4LS54LS14LWB\n4LSCIOC0heC0teC0leC0vuC0tuC0teC1geC0giDgtLjgtY3gtLXgtL7gtKTgtKjgtY3gtKTgtY3g\ntLDgtY3gtK/gtLXgtYHgtIIg4LSJ4LSz4LWN4LSz4LS14LSw4LWB4LSf4LWGIOC0heC0n+C1geC0\nleC1jeC0leC1vSDgtJPgtJ/gtL/gtJrgtY3gtJrgtYbgtLLgtY3gtLLgtYHgtK7gtY3gtKrgtYvg\ntb4g4LSF4LS14LSw4LWB4LSf4LWGIOC0muC0v+C0sOC0v+C0leC1jeC0leC1geC0qOC1jeC0qCDg\ntK7gtYHgtJbgtKTgtY3gtKTgtYHgtKjgtYvgtJXgtY3gtJXgtL8g4LSS4LSo4LWN4LSo4LWB4LSC\nIOC0quC0seC0r+C0vuC1uyDgtJXgtLTgtL/gtK/gtL7gtKTgtYYg4LSa4LS/4LSw4LS/4LSa4LWN\n4LSa4LWG4LSo4LWN4LSo4LWBIOC0teC0sOC1geC0pOC1jeC0pOC0vyDgtKTgtL/gtLDgtL/gtJXg\ntYYg4LSq4LWL4LSo4LWN4LSo4LS/4LSf4LWN4LSf4LWB4LSu4LWB4LSj4LWN4LSf4LWNLgrgtJXg\ntKPgtY3gtKPgtY0g4LSo4LS/4LSx4LSe4LWN4LSe4LWNLg==\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(81, '54', '04-11-2019 11:24:51 am', 'CuC0uOC1jeC0teC0quC1jeC0qOC0meC1jeC0meC0s+C1hiDgtIbgtJfgtY3gtLDgtLngtJngtY3g\ntJngtLPgtY3igI0g4LSV4LWK4LSj4LWN4LSf4LWNICDgtLXgtYDgtKPgtY3gtJ/gtYHgtIIg4LSJ\n4LSv4LS/4LSw4LWN4oCN4LSk4LWN4LSk4LWN4LS04LWB4LSo4LWN4LSo4LWH4LSy4LWN4oCN4LSq\n4LWN4LSq4LS/4LSV4LWN4LSV4LWB4LSo4LWN4LSoIOC0kuC0sOC1gSDgtK7gtL7gtLngtL4g4LS4\n4LS+4LSX4LSw4LSu4LS+4LSj4LWNIOC0uOC1l+C0ueC1g+C0puC0giAKCuC0iCDgtKTgtYDgtLDg\ntKTgtY3gtKTgtY0g4LSO4LSo4LS/4LSV4LWN4LSV4LWNIOC0leC0v+C0n+C1jeC0n+C0v+C0ryDg\ntKrgtKTgtY3gtKTgtLDgtK7gtL7gtLHgtY3gtLHgtYrgtKTgtY3gtKQg4LSk4LSZ4LWN4LSV4LSV\n4LWN4LSV4LWB4LSf4LSCIOC0uOC1jeC0qOC1h+C0ueC0giDgtJXgtYrgtKPgtY3gtJ/gtY0g4LS4\n4LSo4LWN4LSk4LWL4LS34LSk4LWN4LSk4LS/4LSo4LWN4LSx4LWGIOC0huC0qOC0qOC1jeC0pOC0\nvuC0tuC1jeC0sOC1gSDgtKrgtYrgtLTgtL/gtKrgtY3gtKrgtL/gtJrgtY3gtJouLi7gtKrgtY3g\ntLDgtL/gtK/gtKrgtYbgtJ/gtY3gtJ/gtLXgtbsgCgrgtIfgtLXgtL/gtJ/gtYYg4LSo4LS/4LSo\n4LWN4LSo4LWNIOC0leC0v+C0n+C1jeC0n+C0v+C0ryDgtJrgtL/gtLIg4LSo4LSy4LWN4LSyIOC0\nuOC1jeC0qOC1h+C0ueC0v+C0pOC0sOC0v+C1vSAg4LSo4LS/4LSo4LWN4LSo4LWB4LSCIOC0leC0\nv+C0n+C1jeC0n+C0v+C0ryDgtKTgtJngtY3gtJUg4LSo4LS/4LSn4LS/IAoKICAgICAgICAgICAg\nICAgICAgICAgICAgICAgICAg4p2k77iPICgg4LS34LS+4LSw4LWB4LSW4LWNICkg4p2k77iPCuC0\nh+C0teC0qOC1jeC0seC1hiDgtJzgtKjgtY3gtK7gtKbgtL/gtKjgtK7gtL7gtKPgtL/gtKjgtY3g\ntKjgtY0g4LSq4LSf4LSa4LWN4LSa4LS14LSo4LWN4oCNIOC0puC1gOC0sOC1jeKAjeC0mOC0vuC0\nr+C1geC0uOC1jeC0uOC1geC0giDgtIbgtKvgtL/gtK/gtKTgtY3gtKTgtYHgtIIg4LSP4LSx4LWN\n4LSx4LS/IOC0leC1iuC0n+C1geC0pOC1jeC0pOC1jSAKCuC0heC0teC0qOC1jeC0seC1hiDgtIbg\ntJfgtY3gtLDgtLngtJngtY3gtJngtLPgtYbgtLLgtY3gtLLgtL7gtIIg4LSq4LSf4LSa4LWN4LSa\n4LS14LSo4LWN4oCN4LSo4LS/4LSx4LS14LWH4LSx4LWN4LSx4LS/IOC0leC1iuC0n+C1geC0leC1\njeC0leC0n+C1jeC0n+C1hiAK4LSO4LSo4LWN4LSx4LWGIOC0quC1jeC0sOC0v+C0ryDgtKrgtYbg\ntJ/gtY3gtJ/gtLXgtKjgtY0gIOC0ruC0qOC0uOC1jeC0uOC1jSDgtKTgtYHgtLHgtKjgtY3gtKjg\ntY0g4LS14LWG4LSa4LWN4LSa4LWNIOC0qOC1h+C0sOC1geC0qOC1jeC0qOC1gSDgtJLgtLDgtL7g\ntK/gtL/gtLDgtIIg4LSc4LSo4LWN4LSu4LSm4LS/4LSo4LS+4LS24LSC4LS44LSV4LSz4LWN4oCN\nIAoKICAgICAgICAgICAg8J+Nq/CfjoLwn46CICDgtLngtL7gtKrgtY3gtKrgtL8gIOC0rOC1vOC0\npOC1jeC0pOC1jSDgtKHgtYcgICDwn6eU4LS34LS+4LSw4LWCLi7wn46C8J+OgvCfjasKCuC0oeC0\nv+C0nOC1hiDinI3vuI8K\n', '1', 1, 14, 0, 0, '0', '0', 0, '0'),
(82, '60', '04-11-2019 11:25:20 am', '4LSO4LSo4LWN4LSx4LWGIOC0leC0v+C0pOC0vuC0rOC0v+C0suC1hiDgtKrgtYbgtKPgtY3gtKPg\ntYYgCvCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI3w\nn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCf\nmI3wn5iN8J+YjfCfmI3wn5iN8J+YjfCfmI3wn5iN8J+YjQ==\n', '0', 1, 9, 12, 0, '0', '0', 0, '0'),
(83, '13', '04-11-2019 11:31:34 am', '4LSS4LSw4LWBIOC0juC0pOC1jeC0pOC1geC0giDgtKrgtL/gtJ/gtL/gtK/gtYHgtIIg4LSV4LS/\n4LSf4LWN4LSf4LWB4LSo4LWN4LSo4LS/4LSy4LWN4LSy4LSy4LWN4LSy4LWLIOC0puC1iOC0teC0\nruC1hyDwn5ir8J+Yq/CfmKvwn5ir8J+Yq/CfmKvwn5ir8J+Yq/CfmKvwn5ir8J+Yq/CfmKvwn5ir\n8J+Yq/CfmKvwn5ir8J+Yq/CfmKvwn5ir\n', '0', 1, 8, 3, 0, '0', '0', 0, '0'),
(84, '20', '04-11-2019 11:32:40 am', '4LSO4LSo4LWN4LSx4LWGIOC0teC0v+C0suC0quC1jeC0quC1huC0n+C1jeC0nyAzNTAwIOC0uOC1\njeC0seC1jeC0seC0vuC0seC1jeC0seC0uOC1jSDgtJXgtL7gtKPgtL7gtbvgtKHgtY0g4LSq4LWL\n4LSv4LS/4LSf4LWN4LSf4LWB4LSj4LWN4LSf4LWNIOC0leC0v+C0n+C1jeC0n+C1geC0qOC1jeC0\nqOC0teC1vCDgtKTgtL/gtLDgtL/gtJrgtY3gtJrgtYfgtLLgtY3gtKrgtL/gtJXgtY3gtJXgtYHg\ntJUuLjM1MDAg4LSw4LWC4LS1IOC0quC0vuC0sOC0v+C0puC1i+C0t+C0v+C0leC0giDgtKTgtLDg\ntYHgtKjgtY3gtKjgtKTgtL7gtKPgtY3wn5mG4oCN4pmC77iP8J+bje+4j+KcjO+4jw==\n', '0', 1, 8, 2, 0, '0', '0', 0, '0'),
(85, '63', '04-11-2019 11:34:27 am', 'CuC0leC1geC0seC0muC1jeC0muC1gSDgtKjgtL7gtLPgtYHgtJXgtLPgtL7gtK/gtL8g4LSH4LSk\n4LS/4LW9IOC0leC1huC0r+C0seC1gOC0n+C1jeC0n+C1jS4uLi4uCgrgtI7gtLLgtY3gtLLgtL7g\ntLXgtLDgtYbgtK/gtYHgtIIg4LSS4LSo4LWN4LSo4LWNIOC0leC0vuC0o+C0vuC0giDgtI7gtKjg\ntY3gtKjgtY0g4LS14LS/4LSa4LS+4LSw4LS/4LSa4LWN4LSa4LWBLvCfmI3wn5iN8J+YiwoK4LS4\n4LWB4LSX4LS+4LSj4LWLIC4uLi4uLi4uLi4uPwo=\n', '0', 1, 10, 2, 0, '0', '0', 0, '0'),
(86, '21787', '04-11-2019 11:41:12 am', '4LSS4LSw4LWBIOC0leC0vuC0sOC1jeC0r+C0pOC1jeC0pOC0v+C1vSDgtIbgtK/gtL/gtLDgtIIg\n4LSV4LSz4LWN4LSz4LSk4LWN4LSk4LSw4LSZ4LWN4LSZ4LSz4LWB4LSj4LWN4LSf4LS+4LSV4LWN\n4LSV4LS+4LSCIAoK4LSq4LSV4LWN4LS34LWGIOC0uOC0pOC1jeC0r+C0giDgtJLgtKjgtY3gtKjg\ntY0g4LSu4LS+4LSk4LWN4LSw4LSCLi4uLg==\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(87, '57', '04-11-2019 11:43:11 am', 'CgogICAgICAg4LSo4LWG4LSv4LS/4LSCIOC0juC0qOC1jeC0pOC0v+C0qOC0viDgtIfgtLXgtL/g\ntJ/gtYYg8J+ZhPCfmYTwn5iG8J+krvCfpK7wn6SuICAgICAgICAgICAgICAgICAgICAK\n', '0', 1, 3, 7, 0, '0', '0', 0, '0'),
(88, '13', '04-11-2019 11:43:23 am', '4LST4LW84LSu4LSV4LSz4LWG8J+NgfCfjYEuLi4uIOC0h+C0qOC0v+C0r+C1huC0meC1jeC0leC0\nv+C0suC1geC0ruC1huC0qOC1jeC0qOC1hiDgtLXgtYfgtKbgtKjgtL/gtKrgtY3gtKrgtL/gtJXg\ntY3gtJXgtL7gtKTgtYYg4LSu4LSx4LS14LS/4LSv4LWB4LSf4LWGIOC0suC1i+C0leC0pOC1jeC0\npOC1h+C0leC1jeC0leC1gSDgtKjgtYAg4LSv4LS+4LSk4LWN4LSw4LSv4LS+4LS14LWB4LSVIC4u\nLi4uIOC0quC1geC0suC0sOC0vuC0pOC1hiDgtKrgtYvgtK8g4LS44LWN4LS14LSq4LWN4LSo4LSZ\n4LWN4LSZ4LSz4LWGIOC0k+C1vOC0pOC1jeC0pOC1jSDgtIfgtKjgtL/gtK/gtYHgtIIg4LSIIOC0\nleC0o+C1jeC0o+C1geC0leC1viDgtKjgtKjgtK/gtY3gtJXgtY3gtJXgtL7gtbsg4LSO4LSo4LS/\n4LSV4LWN4LSV4LWBIOC0teC0r+C1jeC0ryAgCgrgtI7gtIIg4LW9IOC0quC0vyDinI3vuI/inI3v\nuI/inI3vuI8=\n', '0', 1, 6, 0, 0, '0', '0', 0, '0'),
(89, '54', '04-11-2019 11:44:00 am', 'CuC0j+C0pOC1gSDgtKTgtLDgtIIuLuC0seC0v+C0suC1h+C0t+C1u+C0t+C0v+C0quC1jeC0quC0\nvuC0r+C0vuC0suC1geC0giAg4LSF4LSk4LS/4LW9IOC0huC0p+C0v+C0quC0pOC1jeC0r+C0teC1\ngeC0giDgtLjgtY3gtLXgtL7gtLDgtY3igI3gtKTgtY3gtKXgtKTgtK/gtYHgtIIg4LSH4LSy4LWN\n4LSy4LWG4LSZ4LWN4LSV4LS/4LSy4LWN4oCNIOC0hiDgtKzgtKjgtY3gtKfgtKTgtY3gtKTgtL/g\ntKjgtY0gIOC0huC0pOC1jeC0ruC0vuC0sOC1jeKAjeC0pOC1jeC0peC0pCDgtIngtKPgtY3gtJ/g\ntL7gtJXgtL/gtLLgtY3gtLIuLi4g4LSO4LSo4LWN4LSo4LWNIOC0teC1huC0muC1jeC0muC1jSAg\n4LSF4LSo4LWN4LSn4LSu4LS+4LSvIOC0huC0p+C0v+C0quC0pOC1jeC0r+C0giAg4LSJ4LSj4LWN\n4LSf4LWG4LSZ4LWN4LSV4LS/4LW9ICDgtKjgtYvgtJ/gtY3gtJ/gtY0gIOC0puC0vyDgtKrgtYvg\ntK/gtL/gtKjgtY3gtLHgtY0gLi4gJ+C0heC0qOC1jeC0p+C0ruC0vuC0rycuLiAg4LSG4LSn4LS/\n4LSq4LSk4LWN4LSv4LSCIC4uIOC0teC0suC1jeC0suC0vuC0pOC1huC0r+C0meC1jeC0meC1gSDg\ntKrgtL/gtJ/gtL/gtK7gtYHgtLHgtYHgtJXgtY3gtJXgtYHgtKjgtY3gtKjgtYHgtKPgtY3gtJ/g\ntYbgtJngtY3gtJXgtL/gtb0gIOC0heC0pOC0v+C0qOC0p+C0v+C0leC0giDgtIbgtK/gtYHgtLjg\ntY3gtLjgtYEg4LSV4LS+4LSj4LS/4LSy4LWN4LSyIC4g4LSF4LSf4LS/4LSu4LSv4LWB4LSCIOC0\nieC0n+C0ruC0r+C1geC0giDgtKTgtK7gtY3gtK7gtL/gtLLgtLLgtY3gtLIg4LSa4LS/4LSo4LWN\n4LSk4LSV4LSz4LS/4LSy4LWB4LSCIOC0uOC1jeC0teC0quC1jeC0qOC0meC1jeC0meC0s+C0v+C0\nsuC1geC0giDgtJXgtL7gtLTgtY3gtJrgtKrgtY3gtKrgtL7gtJ/gtYHgtJXgtLPgtL/gtLLgtYHg\ntK7gtYrgtJXgtY3gtJXgtYYg4LSP4LSk4LS+4LSj4LWN4LSf4LWNIOC0uOC0ruC0vuC0qOC0pOC0\nr+C1geC0s+C1jeC0syDgtLDgtKPgtY3gtJ/gtYEg4LS14LWN4LSv4LSV4LWN4LSk4LS/4LSV4LW+\nIOC0pOC0ruC1jeC0ruC0v+C0suC0vuC0o+C1jSDgtIbgtLDgtYvgtJfgtY3gtK/gtJXgtLDgtK7g\ntL7gtK8g4LSS4LSw4LWBIOC0rOC0qOC1jeC0p+C0giDgtIngtJ/gtLLgtYbgtJ/gtYHgtJXgtY3g\ntJXgtYHgtKjgtY3gtKjgtKTgtY0g4LSO4LSo4LWN4LSo4LS+4LSj4LWBIOC0juC0qOC1jeC0seC1\nhuC0r+C1iuC0sOC1gS4uIOC0leC0vuC0tOC1jeC0muC0quC0vuC0n+C1jSAg4LSO4LSo4LWN4LSo\n4LS/4LSw4LWB4LSo4LWN4LSo4LS+4LSy4LWB4LSCICDgtKrgtLDgtLjgtY3gtKrgtLDgtIIg4LSF\n4LSx4LS/4LSe4LWN4LSe4LWBIOC0leC1iuC0o+C1jeC0n+C1geC0s+C1jeC0syDgtLXgtL/gtKfg\ntYfgtK/gtKTgtY3gtLXgtKTgtY3gtKTgtL/gtKjgtYHgtIIg4LSG4LSn4LS/4LSq4LSk4LWN4LSv\n4LSk4LWN4LSk4LS/4LSo4LWB4LSCIOC0kuC0sOC1gSDgtKrgtY3gtLDgtKTgtY3gtK/gtYfgtJUg\n4LS44LWB4LSW4LSCIOC0pOC0qOC1jeC0qOC1huC0r+C0viAgCgrgtKHgtL/gtJzgtYYg4pyN77iP\nCg==\n', '1', 1, 9, 0, 0, '0', '0', 0, '0'),
(90, '60', '04-11-2019 11:48:40 am', '4LSV4LWB4LSx4LWGIOC0quC0sOC0pOC0vyDgtI7gtKjgtY3gtKjgtL/gtJ/gtY3gtJ/gtYHgtIIg\n4LSo4LS/4LSo4LWN4LSo4LS/4LSy4LWGIAoK4LSk4LWH4LW7IOC0juC0teC0v+C0n+C1hiDgtK/g\ntL7gtKPgtYbgtKjgtY3gtKjgtY0g4LSV4LSj4LWN4LSf4LWB4LSq4LS/4LSf4LS/4LSV4LWN4LSV\n4LS+4LW7IOC0leC0tOC0v+C0nuC1jeC0nuC0v+C0suC1jeC0siAKCuC0juC0teC0v+C0n+C1huC0\nr+C0vuC0n+C1gCDwn5iY8J+YmPCfmJjwn5iY8J+YmPCfmJjwn5iY8J+YmPCfmJjwn5iY8J+YmPCf\nmJjwn5iY\n', '0', 1, 5, 1, 0, '0', '0', 0, '0'),
(91, '13', '04-11-2019 11:49:58 am', '4LSV4LS+4LSw4LSj4LSZ4LWN4LSZ4LW+IOC0kuC0qOC1jeC0qOC1geC0ruC0v+C0suC1jeC0suC0\nvuC0pOC1hiAK4LSGIOC0leC0o+C1jeC0o+C1geC0leC1viDgtIfgtJ/gtK/gtY3gtJXgtY3gtJXg\ntL/gtJ/gtYYgCuC0qOC0v+C0seC0nuC1jeC0nuC1gSDgtJXgtYrgtKPgtY3gtJ/gtYcg4LSH4LSw\n4LWB4LSo4LWN4LSo4LWBLi4uIArgtJXgtKPgtY3gtKPgtYHgtKjgtYDgtbwgIOC0pOC1geC0s+C1\njeC0s+C0v+C0leC1viAK4LSV4LS14LS/4LSz4LWB4LSV4LSz4LWGIArgtJrgtYHgtILgtKzgtL/g\ntJrgtY3gtJrgtYHgtJXgtYrgtKPgtY3gtJ/gtYfgtK/gtL/gtLDgtYHgtKjgtY3gtKjgtYEuLi4K\n4LSO4LSo4LWN4LSk4LS/4LSo4LS+4LSv4LS/4LSw4LWB4LSo4LWN4LSo4LWBLi4uLj8gCuC0heC0\nseC0v+C0r+C0v+C0suC1jeC0si4uLi4K4LSq4LWN4LSw4LS/4LSv4LSq4LWN4LSq4LWG4LSf4LWN\n4LSf4LSk4LWG4LSo4LWN4LSk4LWLIOC0kuC0qOC1jeC0qOC1jSAK4LSo4LS34LWN4LSf4LWN4LSf\n4LSq4LWG4LSf4LWN4LSf4LSk4LS/4LSo4LS+4LSy4LS+4LSV4LS+4LSCLi4uLiAK4LSk4LSz4LW8\n4LSo4LWN4LSo4LWBICDgtKrgtYvgtK/gtKrgtY3gtKrgtYvgtb4gCuC0pOC0vuC0meC1jeC0meC0\nvyAg4LSo4LS/4LSo4LWN4LSo4LSk4LS+4LSV4LS+4LSCLi4uLiAK4LSq4LWB4LSe4LWN4LSa4LS/\n4LSw4LS/4LSv4LWB4LSf4LWGIOC0ruC1geC0qOC1jeC0qOC0v+C1vSAK4LSk4LWL4LSx4LWN4LSx\n4LWB4LSq4LWL4LSv4LSk4LS+4LSV4LS+4LSCLi4uLgrgtI7gtJngtY3gtJXgtL/gtLLgtYHgtIIg\n4LS54LWD4LSm4LSv4LSCIArgtKTgtYfgtJngtY3gtJngtL/gtJXgtY3gtJXgtYrgtKPgtY3gtJ/g\ntYfgtK/gtL/gtLDgtYHgtKjgtY3gtKjgtYEuLi4gCuC0leC0sOC0o+C0ruC0seC0v+C0r+C0vuC0\npOC1hiEhIS4uLi4KCuC0juC0giDgtb0g4LSq4LS/IOKcje+4j+Kcje+4jw==\n', '0', 1, 6, 1, 0, '0', '0', 0, '0'),
(92, '34', '04-11-2019 11:58:44 am', '4LSH4LSo4LWN4LSo4LWNIOC0juC0qOC1jeC0seC1hiDgtKzgtbzgtKTgtY3gtKHgtYcg4LSG4LSj\n4LWNIC4uLi4uIPCfmIrwn5iK8J+YivCfmIrwn6ST8J+kk/CfpJPwn6ST8J+kk/CfpJPwn6ST8J+k\nk/CfpJPwn6ST8J+kk/CfpJPwn6ST8J+kkw==\n', '0', 1, 7, 9, 0, '0', '0', 0, '0'),
(93, '68', '04-11-2019 12:03:37 pm', '4LST4LS44LWN4LSV4LS+4LW8IOC0leC1iuC0n+C1geC0leC1jeC0leC1h+C0o+C1jeC0nyAg4LSa\n4LS/4LSyIOC0qOC0n+C1u+C0ruC0vuC0sOC1geC0giDgtKjgtJ/gtL/gtJXgtLPgtYHgtIIg4LSJ\n4LSj4LWN4LSf4LWNIOC0qOC0ruC1jeC0ruC1geC0leC1jeC0leC1iuC0quC1jeC0quC0ggrgtJXg\ntL/gtJ/gtY3gtJ/gtYHgtKjgtY3gtKgg4LS44LWN4LSo4LWH4LS54LSk4LWN4LSk4LS/4LW9IOC0\nleC1guC0n+C1geC0pOC1vQrgtKTgtL/gtLDgtL/gtJrgtY3gtJrgtYEg4LSV4LWK4LSf4LWB4LSk\n4LWN4LSk4LS/4LSf4LWN4LSf4LWB4LSCIAog4LSS4LSw4LWBIOC0teC1huC0uOC1jeC0seC1jeC0\nseC1jSDgtKrgtYfgtKrgtY3gtKrgtbwg4LS14LS/4LSyIOC0quC1i+C0suC1geC0giDgtKTgtLDg\ntL7gtKTgtY3gtKQgCiDgtJrgtL/gtLIKCgoKCgogIuC0heC0reC0v+C0qOC0ryDgtJXgtYHgtLLg\ntKrgtKTgtL/gtJXgtb4iCgoKCgouLi4uLi4u8J+MuvCfjLrgtKjgtKjgtY3gtKbgtYHwn4y68J+M\nui4uLi4uCgoKCgoK\n', '0', 1, 5, 0, 0, '0', '0', 0, '0'),
(94, '54', '04-11-2019 12:07:31 pm', 'CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICDinaTvuI8g4LS54LS+4LSq4LWN4LSq\n4LS/IOC0rOC1vOC0pOC1jeC0pOC1jQogICAgICAgICAgICAgICAgICAgICAgICAgICAg4LSh4LWH\nIOC0uOC1jeC0qOC1h+C0ueC0v+C0pOC0viAgICAgICAgICAgICAgICAgICAgICAgICAgIAoK4LS4\n4LW84LS14LWN4LS1IOC0kOC0tuC0sOC1jeC0r+C0teC1geC0giDgtKbgtYjgtLXgtIIg4LSo4LS/\n4LSo4LWN4LSo4LS/4LW9IOC0muC1iuC0sOC0v+C0r+C0n+C1jeC0n+C1hiDgtIfgtKjgtL/gtK/g\ntYHgtLPgtY3gtLMg4LSG4LSv4LWB4LS44LWN4LS44LS/4LSy4LWB4LSCIPCfmIogICAgICAgICAg\nICAgICAK4LS24LS/4LS1IPCfjoIgCgrgtKHgtL/gtJzgtYYg4pyN77iPCg==\n', '1', 1, 7, 0, 0, '0', '0', 0, '0'),
(95, '25303', '04-11-2019 12:13:00 pm', 'Li4uLi4g4LSS4LSw4LS+4LW+4LSV4LWN4LSV4LWNIOC0quC0leC0sOC0giDgtK7gtLHgtY3gtLHg\ntYrgtLDgtL7gtb4g4LS14LSw4LWB4LSu4LWG4LSo4LWN4LSo4LWNIOC0ruC0qOC0uOC1geC0gi4u\nLi4g4LSG4LSw4LWB4LSCIOC0huC1vOC0leC1jeC0leC1geC0giDgtKrgtJXgtLDgtK7gtL7gtLXg\ntL/gtLLgtY3gtLLgtKjgtY3gtKjgtY0g4LSV4LS+4LSy4LS14LWB4LSCIOC0pOC1huC0s+C0v+C0\nr+C0v+C0leC1jeC0leC1geC0gjogLi4g4LSV4LS+4LSy4LSk4LWN4LSk4LS/4LSo4LWN4LSx4LWG\nIOC0k+C0n+C1jeC0n+C0pOC1jeC0pOC0v+C1vSDgtK7gtKjgtLjgtY3gtLjgtY0g4LSk4LWL4LW9\n4LSV4LWN4LSV4LWB4LSCCgouLvCfkp4=\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(96, '25303', '04-11-2019 12:14:05 pm', 'Li4uLi4g4LSS4LSw4LS+4LW+4LSV4LWN4LSV4LWNIOC0quC0leC0sOC0giDgtK7gtLHgtY3gtLHg\ntYrgtLDgtL7gtb4g4LS14LSw4LWB4LSu4LWG4LSo4LWN4LSo4LWNIOC0ruC0qOC0uOC1geC0gi4u\nLi4g4LSG4LSw4LWB4LSCIOC0huC1vOC0leC1jeC0leC1geC0giDgtKrgtJXgtLDgtK7gtL7gtLXg\ntL/gtLLgtY3gtLLgtKjgtY3gtKjgtY0g4LSV4LS+4LSy4LS14LWB4LSCIOC0pOC1huC0s+C0v+C0\nr+C0v+C0leC1jeC0leC1geC0gjogLi4g4LSV4LS+4LSy4LSk4LWN4LSk4LS/4LSo4LWN4LSx4LWG\nIOC0k+C0n+C1jeC0n+C0pOC1jeC0pOC0v+C1vSDgtK7gtKjgtLjgtY3gtLjgtY0g4LSk4LWL4LW9\n4LSV4LWN4LSV4LWB4LSCCgouLvCfkp4=\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(97, '25303', '04-11-2019 12:16:32 pm', 'Li4uLi4g4LSS4LSw4LS+4LW+4LSV4LWN4LSV4LWNIOC0quC0leC0sOC0giDgtK7gtLHgtY3gtLHg\ntYrgtLDgtL7gtb4g4LS14LSw4LWB4LSu4LWG4LSo4LWN4LSo4LWNIOC0ruC0qOC0uOC1geC0gi4u\nLi4g4LSG4LSw4LWB4LSCIOC0huC1vOC0leC1jeC0leC1geC0giDgtKrgtJXgtLDgtK7gtL7gtLXg\ntL/gtLLgtY3gtLLgtKjgtY3gtKjgtY0g4LSV4LS+4LSy4LS14LWB4LSCIOC0pOC1huC0s+C0v+C0\nr+C0v+C0leC1jeC0leC1geC0gjogLi4g4LSV4LS+4LSy4LSk4LWN4LSk4LS/4LSo4LWN4LSx4LWG\nIOC0k+C0n+C1jeC0n+C0pOC1jeC0pOC0v+C1vSDgtK7gtKjgtLjgtY3gtLjgtY0g4LSk4LWL4LW9\n4LSV4LWN4LSV4LWB4LSCCgouLvCfkp4=\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(98, '66', '04-11-2019 12:19:32 pm', '4LSq4LWN4LSw4LSj4LSv4LS14LWB4LSCIOC0quC1jeC0sOC1h+C0pOC0teC1geC0giAK4LSS4LSw\n4LWB4LSq4LWL4LSy4LWG4LSv4LS+4LSj4LWNLi4uIArgtIXgtKjgtYHgtK/gtYvgtJzgtY3gtK/g\ntK7gtL7gtK8g4LS24LSw4LWA4LSw4LSk4LWN4LSk4LWGIArgtLLgtK3gtL/gtJrgtY3gtJrgtL7g\ntb0g4LSF4LSk4LWNIOC0quC1jeC0sOC0teC1h+C0tuC0v+C0leC1jeC0leC1geC0lSDgtKTgtKjg\ntY3gtKjgtYYg4LSa4LWG4LSv4LWN4LSv4LWB4LSCIPCfmJ0=\n', '1', 1, 5, 0, 0, '0', '0', 0, '0'),
(99, '73', '04-11-2019 12:20:47 pm', 'Li4uLi4g4LSS4LSw4LS+4LW+4LSV4LWN4LSV4LWNIOC0quC0leC0sOC0giDgtK7gtLHgtY3gtLHg\ntYrgtLDgtL7gtb4g4LS14LSw4LWB4LSu4LWG4LSo4LWN4LSo4LWNIOC0ruC0qOC0uOC1geC0gi4u\nLi4g4LSG4LSw4LWB4LSCIOC0huC1vOC0leC1jeC0leC1geC0giDgtKrgtJXgtLDgtK7gtL7gtLXg\ntL/gtLLgtY3gtLLgtKjgtY3gtKjgtY0g4LSV4LS+4LSy4LS14LWB4LSCIOC0pOC1huC0s+C0v+C0\nr+C0v+C0leC1jeC0leC1geC0gjogLi4g4LSV4LS+4LSy4LSk4LWN4LSk4LS/4LSo4LWN4LSx4LWG\nIOC0k+C0n+C1jeC0n+C0pOC1jeC0pOC0v+C1vSDgtK7gtKjgtLjgtY3gtLjgtY0g4LSk4LWL4LW9\n4LSV4LWN4LSV4LWB4LSCCgouLvCfkp4=\n', '0', 1, 4, 2, 0, '0', '0', 0, '0'),
(100, '36914', '04-11-2019 12:22:09 pm', '4LSq4LWN4LSw4LSj4LSv4LS14LWB4LSCIOC0quC1jeC0sOC1h+C0pOC0teC1geC0giAK4LSS4LSw\n4LWB4LSq4LWL4LSy4LWG4LSv4LS+4LSj4LWNLi4uIArgtIXgtKjgtYHgtK/gtYvgtJzgtY3gtK/g\ntK7gtL7gtK8g4LS24LSw4LWA4LSw4LSk4LWN4LSk4LWGIArgtLLgtK3gtL/gtJrgtY3gtJrgtL7g\ntb0g4LSF4LSk4LWNIOC0quC1jeC0sOC0teC1h+C0tuC0v+C0leC1jeC0leC1geC0lSDgtKTgtKjg\ntY3gtKjgtYYg4LSa4LWG4LSv4LWN4LSv4LWB4LSCIPCfmJ0=\n', '0', 1, 0, 0, 0, '0', '0', 0, '0'),
(101, '12', '04-11-2019 12:23:45 pm', '4LSo4LWAIOC0juC0qOC1jeC0qOC0pOC0v+C0qOC1hiAK4LSe4LS+4LW7IOC0teC0uOC0qOC1jeC0\npOC0ruC1huC0qOC1jeC0qOC1jSDgtLXgtL/gtLPgtL/gtJXgtY3gtJXgtYHgtKjgtY3gtKjgtYEu\nLi4KCuC0juC0qOC1jeC0pOC1huC0qOC1jeC0qOC0vuC1vSAK4LSO4LSo4LWN4LSx4LWGIOC0uOC0\nmeC1jeC0leC0n+C0meC1jeC0meC0s+C1geC0n+C1hiDgtJXgtL7gtJ/gtY3gtJ/gtL/gtLLgtYfg\ntJXgtY3gtJXgtY0gCuC0qOC1gCDgtKrgtYLgtJXgtY3gtJXgtLPgtYYg4LSF4LSv4LSV4LWN4LSV\n4LWB4LSo4LWN4LSo4LWBLi4uCgrgtKjgtYAg4LSO4LSo4LWN4LSo4LSk4LS/4LSo4LWGIArgtJ7g\ntL7gtbsg4LSu4LS04LSv4LWG4LSo4LWN4LSo4LWBIOC0teC0v+C0s+C0v+C0leC1jeC0leC1geC0\nqOC1jeC0qOC1gS4uLgoK4LSO4LSo4LWN4LSk4LWG4LSo4LWN4LSo4LS+4LW9CuC0kuC0seC1jeC0\nseC0quC1jeC0quC1huC0n+C0suC0v+C0qOC1jeC0seC1hiDgtK7gtLDgtYHgtK3gtYLgtK7gtL/g\ntK/gtL/gtLLgtYfgtJXgtY3gtJXgtY0gCuC0qOC1gCDgtJLgtLDgtYEg4LSu4LS04LSv4LS+4LSv\n4LS/IOC0quC1huC0r+C1jeC0pOC0v+C0seC0meC1jeC0meC1geC0qOC1jeC0qOC1gS4uLgoK4LSo\n4LWAIOC0juC0qOC1jeC0qOC0pOC0v+C0qOC1hiAK4LSe4LS+4LW7IOC0huC0leC0vuC0tuC0ruC1\nhuC0qOC1jeC0qOC1gSDgtLXgtL/gtLPgtL/gtJXgtY3gtJXgtYHgtKjgtY3gtKjgtYEuLi4uCgrg\ntI7gtKjgtY3gtKTgtYbgtKjgtY3gtKjgtL7gtb0gCuC0qOC0v+C0qOC1jeC0seC1hiDgtKjgtJXg\ntY3gtLfgtKTgtY3gtLDgtJngtY3gtJngtb4g4LSO4LSo4LS/4LSV4LWN4LSV4LWNIArgtLXgtLTg\ntL/gtJXgtL7gtJ/gtY3gtJ/gtYHgtKjgtY3gtKjgtYEuLi7inI3vuI8=\n', '0', 1, 3, 0, 0, '0', '0', 0, '0');

-- --------------------------------------------------------

--
-- Table structure for table `statusmedia`
--

CREATE TABLE `statusmedia` (
  `sn` bigint(20) NOT NULL,
  `regdate` varchar(50) NOT NULL,
  `mediatype` varchar(10) NOT NULL,
  `title` varchar(200) NOT NULL,
  `fbid` varchar(250) NOT NULL,
  `dim` varchar(30) NOT NULL,
  `imgsrc` varchar(400) NOT NULL,
  `videosrc` varchar(400) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `statusmedia`
--

INSERT INTO `statusmedia` (`sn`, `regdate`, `mediatype`, `title`, `fbid`, `dim`, `imgsrc`, `videosrc`, `status`) VALUES
(1, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_766099730497020', '720x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/p720x720/75226373_766099733830353_2072678017921974272_n.jpg?_nc_cat=107&_nc_oc=AQntfBij8jIqt9AbZMJ1TnFraqOEVx7YRY9m8Evh9PRI2nSt6zuneX3dhK5nxkBjAVs&_nc_ht=scontent.xx&oh=57c27da26bdabfd4a49dce1f14cd41be&oe=5E58C3DC', 'NA', 1),
(2, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_766099863830340', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/73409235_766099870497006_1458429692376776704_o.jpg?_nc_cat=103&_nc_oc=AQk3WAPjoqLUds6PjLphAw6nH_1UOx0vOC-a0oznN1jwL0gsHIcnn_L34fexnYu_w-A&_nc_ht=scontent.xx&oh=a5525c8191f90169ccad719d1b1c18da&oe=5E50EF88', 'NA', 1),
(3, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_766099950496998', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/73256188_766099957163664_2753261774051475456_o.jpg?_nc_cat=109&_nc_oc=AQmBUmLXz8emwc6wJTpVMPmOcSshQvuDHzOBaxq5TWLf4fO6lwGMWrqKhKqF2dHtfNI&_nc_ht=scontent.xx&oh=1ebb207eeac71280e23c54cdbff15019&oe=5E184F0F', 'NA', 1),
(4, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_766100010496992', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/73106566_766100013830325_7959302675617546240_o.jpg?_nc_cat=104&_nc_oc=AQld5nnWZZrd-5xJoMuDak6LSmOhlTGMTJrgLIrKSeOfygpJVVsthR58b0TSmr1F2V8&_nc_ht=scontent.xx&oh=3ea065afdceb5ddb6259fd84b859ac0a&oe=5E478559', 'NA', 1),
(5, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_766100057163654', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/73107422_766100060496987_4260298792433090560_o.jpg?_nc_cat=107&_nc_oc=AQlZFZEeEwBbi3nE3tyJnohZ4eNmdJKSfI14Q0phzErS5JAex3Fl8S98_8ag_f3DNq4&_nc_ht=scontent.xx&oh=1c4219d52def38305693587b82af292b&oe=5E4EFB80', 'NA', 1),
(6, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_766100120496981', '579x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/72845036_766100123830314_4490373468855992320_o.jpg?_nc_cat=103&_nc_oc=AQlx0FuY84pE9w5-N4_n9K93fUDRsOsZpiCyV1x1n00u6POYJp6dVYHRdwcAU1EmL_Y&_nc_ht=scontent.xx&oh=086a3cc8069d95d7552762822060eb84&oe=5E4ED459', 'NA', 1),
(7, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_766100207163639', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/75521914_766100210496972_6014640603244003328_o.jpg?_nc_cat=107&_nc_oc=AQmkhn60kjdcsmgouKe380tIE7uCFAXrZ9_IbmCC0i0RulAoMw0Sv-Vpjyo5FTtZHtQ&_nc_ht=scontent.xx&oh=42678167ce9d93ac7068d344b37449b5&oe=5E1E6EC9', 'NA', 1),
(8, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_766100257163634', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/73370508_766100260496967_7757656198748307456_o.jpg?_nc_cat=104&_nc_oc=AQlQbqlbnQF1Jm7GmhSiFRnthFLjgPfqAMHIICNWYneGSEULHLyp_F-erynTEu5ysio&_nc_ht=scontent.xx&oh=496cfe158b1d0196f842d133a13b5e80&oe=5E51385D', 'NA', 1),
(9, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_766100310496962', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/75362462_766100313830295_4201132839086850048_o.jpg?_nc_cat=108&_nc_oc=AQlFVYYlnurgzBqA0Wvl6VB7QcxPfpT1aOZXHs7EO48CrZhgXMQd3tCSpLr9_iX9T_s&_nc_ht=scontent.xx&oh=106d8b9b1d4a4402a7335aff12e3114f&oe=5E4E830C', 'NA', 1),
(10, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_766100383830288', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/73390784_766100387163621_201423117095010304_o.jpg?_nc_cat=100&_nc_oc=AQnpD7EMbJgDDyWpn5_Lzh-UunDMKE8mo1jscKRl2NN2u6N11uHbPGr6I-fz7SDzvTQ&_nc_ht=scontent.xx&oh=1adc740917ac6289c142aeca48cf079f&oe=5E4E271A', 'NA', 1),
(11, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_766100440496949', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/73119866_766100447163615_570324141685604352_o.jpg?_nc_cat=103&_nc_oc=AQmfmo_hkUsF2RtT2LPLQ0kSqmrhK35SD-cwMADedTEqHbg0av_QyZeOgdwLR5wQlgA&_nc_ht=scontent.xx&oh=dbd83fa8de0dc2942c845d3becb69b4d&oe=5E52F9EB', 'NA', 1),
(12, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_766100533830273', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/73236406_766100537163606_7507293926475169792_o.jpg?_nc_cat=101&_nc_oc=AQmc_bUqTFPDr0KWQKiLao4iZsXkA832C5-zQy3A6mqCwG-zB2evURBYbCacNfJqr28&_nc_ht=scontent.xx&oh=f4e214e1cab54236595ca1b772b5ef8c&oe=5E181CCD', 'NA', 1),
(13, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_766100633830263', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/75625398_766100637163596_1924720573577953280_o.jpg?_nc_cat=101&_nc_oc=AQk1vhLbggznf5mJrA3S0HNgWfjoCNUkPVLD-GpkyV4A_23SYSUTZDf0v2yUOk5R_yk&_nc_ht=scontent.xx&oh=2edc16630adf94a6aa31a6d962d540ef&oe=5E1A41F7', 'NA', 1),
(14, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767440240362969', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/75366340_767440247029635_6273933783267803136_o.jpg?_nc_cat=108&_nc_oc=AQnS0jfDOkA2Wx7V7-8flGnVjebmvCgy4cOB6KQQsgOvOpgYeCFPh9nUbEXbhtOdga8&_nc_ht=scontent.xx&oh=13a678d2caf6ec3d48b68badb4648342&oe=5E1C475E', 'NA', 1),
(15, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767440317029628', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/74404811_767440323696294_4193047069230891008_o.jpg?_nc_cat=101&_nc_oc=AQnBerKrCkhF8lGsJjX8a4I7ZS5KsxmXWwI4hNghGdpEXq9g8QCrtJ63eEoYrbcXJJA&_nc_ht=scontent.xx&oh=b3976515ff362ec9d27d15e43a9058d7&oe=5E5F7852', 'NA', 1),
(16, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767440410362952', '517x646', 'https://scontent.xx.fbcdn.net/v/t1.0-9/76678359_767440413696285_3794464590809530368_n.jpg?_nc_cat=109&_nc_oc=AQkBVsRdTDExHrMDUW7Xw0sYAZYBmbhro-fX8IcCnNUn12l9sHrFRR3NFtuF-zdWNZE&_nc_ht=scontent.xx&oh=35337259522cc09e92bbcdec96be621a&oe=5E5793D7', 'NA', 1),
(17, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767440487029611', '517x646', 'https://scontent.xx.fbcdn.net/v/t1.0-9/73140167_767440490362944_8596606218103947264_n.jpg?_nc_cat=103&_nc_oc=AQn7_SnEB_GT68DnWXhbTvxtmkmvhJf0_AsXe4f2C3aGEsmMDnP0wrX9lrRjacm_3gY&_nc_ht=scontent.xx&oh=1c9873ad1a5fcfef18de875b7825892f&oe=5E5AB6BF', 'NA', 1),
(18, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767440570362936', '517x646', 'https://scontent.xx.fbcdn.net/v/t1.0-9/74914164_767440573696269_905735145900736512_n.jpg?_nc_cat=103&_nc_oc=AQk7NRhUVG60ltgrUFPTrlwrxRwCfugSKrvgy4qPJkWxnMFd5wNMiZ2eW8QPPikC9zY&_nc_ht=scontent.xx&oh=550879bbc202fc0d06830c97d6803d7d&oe=5E553296', 'NA', 1),
(19, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767440640362929', '517x646', 'https://scontent.xx.fbcdn.net/v/t1.0-9/73161299_767440643696262_8926758339807805440_n.jpg?_nc_cat=102&_nc_oc=AQnVBM8jr3O9zguu6xSWIllah70hpJAJU2COBhL4jL2wLFzHxfC-NyV1uU-WL6s8u2U&_nc_ht=scontent.xx&oh=59b890548a7703e83d79a5d09dc03873&oe=5E52972C', 'NA', 1),
(20, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767440697029590', '579x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/75224839_767440703696256_7537150662216253440_o.jpg?_nc_cat=102&_nc_oc=AQmldcKniCPzKG97KBwtrYYfB-xXJY_w5rZZCFNTY9jD7sW4Dg0N34-WN5x1KKKTOfI&_nc_ht=scontent.xx&oh=f00ed2455c4a26d686114832a8b60157&oe=5E60065E', 'NA', 1),
(21, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767440787029581', '580x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/73289099_767440793696247_6848061195803426816_o.jpg?_nc_cat=111&_nc_oc=AQm3e4pl1KWh0tkuhk7-96rlYu5iesc8BcMTTb2jpPr7hoiflrWAPfOjpD9CmITKfAs&_nc_ht=scontent.xx&oh=80af12227aef89d0f3225927fb562bc8&oe=5E56070B', 'NA', 1),
(22, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767440850362908', '581x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/73160279_767440853696241_1191511759991603200_o.jpg?_nc_cat=109&_nc_oc=AQlwfJd7chereeGz0RfdU42KK4hbPPN3bJ4yHhSgm2nl2S9pgCL_2K8ojfHVDbBTCds&_nc_ht=scontent.xx&oh=abeb7fff3dbf9a0c530ecf16a207fb5c&oe=5E55F53E', 'NA', 1),
(23, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767440893696237', '582x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/76747474_767440897029570_7453250417414111232_o.jpg?_nc_cat=101&_nc_oc=AQkdMdlZtucrIqgcI0F1W00Th51LnW63c-t0pXMOR8Zo0dfyX0NZlnj4hJT3QBbwfgM&_nc_ht=scontent.xx&oh=899a39efae6496fb884bd6506670d03a&oe=5E61F199', 'NA', 1),
(24, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767440983696228', '579x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/73151297_767440990362894_7707104605473079296_o.jpg?_nc_cat=100&_nc_oc=AQnj68Nogngfw912nO6nzr9imCQmTfgX9ZU9Kj8TGjhS3ccUVONilOG2haOX-VkJGQw&_nc_ht=scontent.xx&oh=a9c7b98feb24474fce0f03fe2edde59c&oe=5E1C92C1', 'NA', 1),
(25, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767441043696222', '580x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/73271368_767441050362888_5621548755179274240_o.jpg?_nc_cat=106&_nc_oc=AQm6WOjaguUaRWnD3T39fuPIOc_4Et0pup9tDgBBs_P2ADUAn9OF3NBqPdtYOYHiiMg&_nc_ht=scontent.xx&oh=5a6190790bd0ff0b69921f80e86ba80c&oe=5E5885D6', 'NA', 1),
(26, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767441110362882', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/74609447_767441113696215_5054371660585500672_o.jpg?_nc_cat=103&_nc_oc=AQnH4pIySbGJ_EOcs5F18L5E82kMW0VfnU23RPirwLiBeWtDbP06BiwhJdCbUlKf_X8&_nc_ht=scontent.xx&oh=3463511a8b870b6942237dad6965b738&oe=5E547DED', 'NA', 1),
(27, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767441167029543', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/75252850_767441170362876_2191614853578227712_n.jpg?_nc_cat=101&_nc_oc=AQmsrGNGcwN_ioMzMTjGhISweiIB5QISBLyKzXmx1L34CeUjFXHbKjAn5bwOYfpbx2g&_nc_ht=scontent.xx&oh=70e3624ff1405c8c36221ea942109313&oe=5E55445A', 'NA', 1),
(28, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767441247029535', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/74693535_767441250362868_7950151818296688640_n.jpg?_nc_cat=110&_nc_oc=AQlMQJcvVvFBqn_8JPsCXOkE-OXwZgkPPlt6km7DdslRw5uHXlGQLbHm1udQknZdpZc&_nc_ht=scontent.xx&oh=c81baa2b91910ed4eda731718a0764ce&oe=5E585657', 'NA', 1),
(29, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767441300362863', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/74601291_767441303696196_259021609241673728_n.jpg?_nc_cat=111&_nc_oc=AQm3BEhY7m18pjmUaODq3Y7_YT6TnFio1qkiapDIo_GMeCONy_BFex0_lVwgaTAWF-A&_nc_ht=scontent.xx&oh=2afc0a1f75c7b5d54953fff0bd4e532e&oe=5E4DF61E', 'NA', 1),
(30, '03-11-2019 09:18:56 pm', '2', 'NA', '153834575056875_767441393696187', '576x720', 'https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/72884154_767441397029520_5297189560810209280_n.jpg?_nc_cat=104&_nc_oc=AQmWxfPplJLdAdiZGQp3L6ln_qTilkRpCoFV5I7uS-O2BbuVh_eKjrzAvSc1rrcE_-k&_nc_ht=scontent.xx&oh=624423d5a5aa24206382a75b1b7fab47&oe=5E4EE17F', 'NA', 1);

-- --------------------------------------------------------

--
-- Table structure for table `statusmedialstid`
--

CREATE TABLE `statusmedialstid` (
  `sn` int(11) NOT NULL,
  `lastid` varchar(200) NOT NULL DEFAULT 'NA'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `statusmedialstid`
--

INSERT INTO `statusmedialstid` (`sn`, `lastid`) VALUES
(1, '153834575056875_767441393696187');

-- --------------------------------------------------------

--
-- Table structure for table `statusmedia_temp`
--

CREATE TABLE `statusmedia_temp` (
  `sn` bigint(20) NOT NULL,
  `regdate` varchar(50) NOT NULL,
  `mediatype` varchar(50) NOT NULL,
  `title` varchar(300) NOT NULL,
  `fbid` varchar(200) NOT NULL,
  `dim` varchar(50) NOT NULL,
  `imgsrc` varchar(400) NOT NULL,
  `videosrc` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admingcm`
--
ALTER TABLE `admingcm`
  ADD PRIMARY KEY (`sn`),
  ADD KEY `sn` (`sn`,`name`,`fcmid`,`status`);

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`sn`),
  ADD KEY `sn` (`sn`,`postid`,`userid`,`postdate`,`replay`,`status`);

--
-- Indexes for table `comments_replay`
--
ALTER TABLE `comments_replay`
  ADD PRIMARY KEY (`sn`),
  ADD KEY `sn` (`sn`,`commentid`,`userid`,`postdate`,`status`);

--
-- Indexes for table `instagram`
--
ALTER TABLE `instagram`
  ADD PRIMARY KEY (`sn`),
  ADD KEY `sn` (`sn`,`types`,`url`);

--
-- Indexes for table `instagramlastid`
--
ALTER TABLE `instagramlastid`
  ADD PRIMARY KEY (`sn`),
  ADD KEY `sn` (`sn`,`pageid`,`lastid`);

--
-- Indexes for table `likes`
--
ALTER TABLE `likes`
  ADD PRIMARY KEY (`sn`),
  ADD KEY `sn` (`sn`,`statusid`,`userids`);

--
-- Indexes for table `registerbypass`
--
ALTER TABLE `registerbypass`
  ADD PRIMARY KEY (`sn`),
  ADD KEY `sn` (`sn`,`mobile`,`vcode`,`status`);

--
-- Indexes for table `registration`
--
ALTER TABLE `registration`
  ADD PRIMARY KEY (`sn`),
  ADD KEY `sn` (`sn`,`regdate`,`countrycode`,`mobile`,`fcmid`,`androidid`,`claim`,`showmobile`,`verified`,`block`,`vkey`,`status`),
  ADD KEY `sn_2` (`sn`,`regdate`,`name`,`countrycode`,`mobile`,`fcmid`,`androidid`),
  ADD KEY `showmobile` (`showmobile`,`verified`,`block`,`vkey`,`shortstatus`,`blocklist`,`imgsig`,`status`);

--
-- Indexes for table `reportstatus`
--
ALTER TABLE `reportstatus`
  ADD PRIMARY KEY (`sn`),
  ADD KEY `sn` (`sn`,`regdate`,`reportuserid`,`statususerid`,`statusid`,`reporttype`,`statustype`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`sn`),
  ADD KEY `sn` (`sn`,`userid`,`postdate`,`commentboxblock`,`visibilestatus`,`likes`,`comment`,`statustype`,`photourl`,`photodemension`,`pinned`,`photostatus`);

--
-- Indexes for table `statusmedia`
--
ALTER TABLE `statusmedia`
  ADD PRIMARY KEY (`sn`),
  ADD KEY `sn` (`sn`,`regdate`,`mediatype`,`title`,`fbid`,`dim`,`imgsrc`,`status`),
  ADD KEY `videosrc` (`videosrc`);

--
-- Indexes for table `statusmedialstid`
--
ALTER TABLE `statusmedialstid`
  ADD PRIMARY KEY (`sn`),
  ADD KEY `sn` (`sn`,`lastid`);

--
-- Indexes for table `statusmedia_temp`
--
ALTER TABLE `statusmedia_temp`
  ADD PRIMARY KEY (`sn`),
  ADD KEY `sn` (`sn`,`regdate`,`mediatype`,`title`,`fbid`,`dim`),
  ADD KEY `imgsrc` (`imgsrc`),
  ADD KEY `videosrc` (`videosrc`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admingcm`
--
ALTER TABLE `admingcm`
  MODIFY `sn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `sn` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=294;
--
-- AUTO_INCREMENT for table `comments_replay`
--
ALTER TABLE `comments_replay`
  MODIFY `sn` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;
--
-- AUTO_INCREMENT for table `instagram`
--
ALTER TABLE `instagram`
  MODIFY `sn` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=196;
--
-- AUTO_INCREMENT for table `instagramlastid`
--
ALTER TABLE `instagramlastid`
  MODIFY `sn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT for table `likes`
--
ALTER TABLE `likes`
  MODIFY `sn` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;
--
-- AUTO_INCREMENT for table `registerbypass`
--
ALTER TABLE `registerbypass`
  MODIFY `sn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `registration`
--
ALTER TABLE `registration`
  MODIFY `sn` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;
--
-- AUTO_INCREMENT for table `reportstatus`
--
ALTER TABLE `reportstatus`
  MODIFY `sn` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `status`
--
ALTER TABLE `status`
  MODIFY `sn` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;
--
-- AUTO_INCREMENT for table `statusmedia`
--
ALTER TABLE `statusmedia`
  MODIFY `sn` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;
--
-- AUTO_INCREMENT for table `statusmedialstid`
--
ALTER TABLE `statusmedialstid`
  MODIFY `sn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `statusmedia_temp`
--
ALTER TABLE `statusmedia_temp`
  MODIFY `sn` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
