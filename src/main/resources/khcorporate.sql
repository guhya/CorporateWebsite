-- phpMyAdmin SQL Dump
-- version 3.4.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 03, 2016 at 03:00 PM
-- Server version: 5.0.95
-- PHP Version: 5.5.30

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `khcorporate`
--

-- --------------------------------------------------------

--
-- Table structure for table `persistent_logins`
--

CREATE TABLE IF NOT EXISTS `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`series`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tbCareer`
--

CREATE TABLE IF NOT EXISTS `tbCareer` (
  `seq` int(11) NOT NULL auto_increment,
  `title` varchar(255) default NULL,
  `content` text,
  `startDate` datetime default NULL,
  `endDate` datetime default NULL,
  `viewCount` int(11) NOT NULL,
  `regIp` varchar(20) default NULL,
  `regId` varchar(50) default NULL,
  `regDate` datetime default NULL,
  `modIp` varchar(20) default NULL,
  `modId` varchar(50) default NULL,
  `modDate` datetime default NULL,
  `delYn` varchar(1) default NULL,
  PRIMARY KEY  (`seq`),
  KEY `title` (`title`,`delYn`),
  KEY `delYn` (`delYn`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbCatalog`
--

CREATE TABLE IF NOT EXISTS `tbCatalog` (
  `seq` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `description` text,
  `url` varchar(2000) default NULL,
  `viewCount` int(11) NOT NULL,
  `regIp` varchar(20) default NULL,
  `regId` varchar(50) default NULL,
  `regDate` datetime default NULL,
  `modIp` varchar(20) default NULL,
  `modId` varchar(50) default NULL,
  `modDate` datetime default NULL,
  `delYn` varchar(1) default NULL,
  PRIMARY KEY  (`seq`),
  KEY `name` (`name`,`delYn`),
  KEY `delYn` (`delYn`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbContact`
--

CREATE TABLE IF NOT EXISTS `tbContact` (
  `seq` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `email` varchar(255) default NULL,
  `title` varchar(255) default NULL,
  `content` text,
  `regIp` varchar(20) default NULL,
  `regId` varchar(50) default NULL,
  `regDate` datetime default NULL,
  `modIp` varchar(20) default NULL,
  `modId` varchar(50) default NULL,
  `modDate` datetime default NULL,
  `delYn` varchar(1) default NULL,
  PRIMARY KEY  (`seq`),
  KEY `name` (`name`,`delYn`),
  KEY `title` (`title`),
  KEY `delYn` (`delYn`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbEvent`
--

CREATE TABLE IF NOT EXISTS `tbEvent` (
  `seq` int(11) NOT NULL auto_increment,
  `title` varchar(255) default NULL,
  `content` text,
  `startDate` datetime default NULL,
  `endDate` datetime default NULL,
  `viewCount` int(11) NOT NULL,
  `regIp` varchar(20) default NULL,
  `regId` varchar(50) default NULL,
  `regDate` datetime default NULL,
  `modIp` varchar(20) default NULL,
  `modId` varchar(50) default NULL,
  `modDate` datetime default NULL,
  `delYn` varchar(1) default NULL,
  PRIMARY KEY  (`seq`),
  KEY `title` (`title`,`delYn`),
  KEY `startDate` (`startDate`,`endDate`),
  KEY `delYn` (`delYn`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbFile`
--

CREATE TABLE IF NOT EXISTS `tbFile` (
  `seq` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `channel` varchar(255) default NULL,
  `category` varchar(255) default NULL,
  `ownerSeq` int(11) default NULL,
  `originalName` varchar(255) default NULL,
  `size` int(11) default NULL,
  `path` varchar(255) default NULL,
  `regIp` varchar(20) default NULL,
  `regId` varchar(50) default NULL,
  `regDate` datetime default NULL,
  `modIp` varchar(20) default NULL,
  `modId` varchar(50) default NULL,
  `modDate` datetime default NULL,
  `delYn` varchar(1) default NULL,
  PRIMARY KEY  (`seq`),
  KEY `channel` (`channel`),
  KEY `category` (`category`),
  KEY `ownerSeq` (`ownerSeq`),
  KEY `delYn` (`delYn`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=210 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbNews`
--

CREATE TABLE IF NOT EXISTS `tbNews` (
  `seq` int(11) NOT NULL auto_increment,
  `title` varchar(255) default NULL,
  `content` text,
  `viewCount` int(11) NOT NULL,
  `regIp` varchar(20) default NULL,
  `regId` varchar(50) default NULL,
  `regDate` datetime default NULL,
  `modIp` varchar(20) default NULL,
  `modId` varchar(50) default NULL,
  `modDate` datetime default NULL,
  `delYn` varchar(1) default NULL,
  PRIMARY KEY  (`seq`),
  KEY `title` (`title`,`delYn`),
  KEY `delYn` (`delYn`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=27 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbProduct`
--

CREATE TABLE IF NOT EXISTS `tbProduct` (
  `seq` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `description` text,
  `extra1` text,
  `extra2` text,
  `extra3` text,
  `extra4` text,
  `extra5` text,
  `extra6` text,
  `extra7` text,
  `extra8` text,
  `extra9` text,
  `extra10` text,
  `viewCount` int(11) NOT NULL,
  `regIp` varchar(20) default NULL,
  `regId` varchar(50) default NULL,
  `regDate` datetime default NULL,
  `modIp` varchar(20) default NULL,
  `modId` varchar(50) default NULL,
  `modDate` datetime default NULL,
  `delYn` varchar(1) default NULL,
  PRIMARY KEY  (`seq`),
  KEY `name` (`name`,`delYn`),
  KEY `delYn` (`delYn`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=30 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbService`
--

CREATE TABLE IF NOT EXISTS `tbService` (
  `seq` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `description` text,
  `extra1` text,
  `extra2` text,
  `extra3` text,
  `extra4` text,
  `extra5` text,
  `extra6` text,
  `extra7` text,
  `extra8` text,
  `extra9` text,
  `extra10` text,
  `viewCount` int(11) NOT NULL,
  `regIp` varchar(20) default NULL,
  `regId` varchar(50) default NULL,
  `regDate` datetime default NULL,
  `modIp` varchar(20) default NULL,
  `modId` varchar(50) default NULL,
  `modDate` datetime default NULL,
  `delYn` varchar(1) default NULL,
  PRIMARY KEY  (`seq`),
  KEY `name` (`name`,`delYn`),
  KEY `delYn` (`delYn`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbSetting`
--

CREATE TABLE IF NOT EXISTS `tbSetting` (
  `seq` int(11) NOT NULL auto_increment,
  `title` varchar(255) default NULL,
  `startDate` datetime default NULL,
  `endDate` datetime default NULL,
  `regIp` varchar(20) default NULL,
  `regId` varchar(50) default NULL,
  `regDate` datetime default NULL,
  `modIp` varchar(20) default NULL,
  `modId` varchar(50) default NULL,
  `modDate` datetime default NULL,
  `delYn` varchar(1) default NULL,
  `useYn` varchar(1) default NULL,
  `defaultYn` varchar(1) default NULL,
  PRIMARY KEY  (`seq`),
  KEY `title` (`title`,`delYn`),
  KEY `startDate` (`startDate`,`endDate`),
  KEY `delYn` (`delYn`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbTerm`
--

CREATE TABLE IF NOT EXISTS `tbTerm` (
  `seq` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `description` text,
  `taxonomy` varchar(3) default NULL,
  `lineage` varchar(255) default NULL,
  `parent` int(11) default NULL,
  `regIp` varchar(20) default NULL,
  `regId` varchar(50) default NULL,
  `regDate` datetime default NULL,
  `modIp` varchar(20) default NULL,
  `modId` varchar(50) default NULL,
  `modDate` datetime default NULL,
  `delYn` varchar(1) default NULL,
  `fixYn` varchar(1) default NULL,
  PRIMARY KEY  (`seq`),
  KEY `delYn` (`delYn`),
  KEY `taxonomy` (`taxonomy`),
  KEY `lineage` (`lineage`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=132 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbTermRelationship`
--

CREATE TABLE IF NOT EXISTS `tbTermRelationship` (
  `seq` int(11) NOT NULL auto_increment,
  `channel` varchar(255) default NULL,
  `ownerSeq` int(11) default NULL,
  `termSeq` int(11) default NULL,
  `taxonomy` varchar(3) default NULL,
  `regIp` varchar(20) default NULL,
  `regId` varchar(50) default NULL,
  `regDate` datetime default NULL,
  `modIp` varchar(20) default NULL,
  `modId` varchar(50) default NULL,
  `modDate` datetime default NULL,
  `delYn` varchar(1) default NULL,
  PRIMARY KEY  (`seq`),
  KEY `channel` (`channel`,`ownerSeq`,`termSeq`),
  KEY `delYn` (`delYn`),
  KEY `taxonomy` (`taxonomy`),
  KEY `ownerSeq` (`ownerSeq`),
  KEY `termSeq` (`termSeq`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=571 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbUser`
--

CREATE TABLE IF NOT EXISTS `tbUser` (
  `seq` int(11) NOT NULL auto_increment,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `firstName` varchar(50) default NULL,
  `lastName` varchar(50) default NULL,
  `email` varchar(255) default NULL,
  `enabled` varchar(1) default NULL,
  `regIp` varchar(20) default NULL,
  `regId` varchar(50) default NULL,
  `regDate` datetime default NULL,
  `modIp` varchar(20) default NULL,
  `modId` varchar(50) default NULL,
  `modDate` datetime default NULL,
  `delYn` varchar(1) default NULL,
  PRIMARY KEY  (`seq`),
  UNIQUE KEY `username` (`username`),
  KEY `delYn` (`delYn`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=20 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbUserRole`
--

CREATE TABLE IF NOT EXISTS `tbUserRole` (
  `seq` int(11) NOT NULL auto_increment,
  `username` varchar(100) NOT NULL,
  `role` varchar(100) NOT NULL,
  `regId` varchar(100) default NULL,
  `regIp` varchar(96) default NULL,
  `regDate` datetime default NULL,
  `modId` varchar(100) default NULL,
  `modIp` varchar(96) default NULL,
  `modDate` datetime default NULL,
  `delYn` varchar(1) NOT NULL,
  PRIMARY KEY  (`seq`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=77 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
