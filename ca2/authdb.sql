CREATE DATABASE  IF NOT EXISTS `authdb` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `authdb`;
-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: authdb
-- ------------------------------------------------------
-- Server version	5.7.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `groupid` varchar(32) NOT NULL,
  `userid` varchar(32) NOT NULL,
  PRIMARY KEY (`groupid`,`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES ('manage','a'),('manage','fff'),('manage','zxc');
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notes`
--

DROP TABLE IF EXISTS `notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notes` (
  `notesid` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(32) NOT NULL,
  `posteddate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `category` varchar(32) NOT NULL,
  `content` varchar(2048) NOT NULL,
  `userid` varchar(32) NOT NULL,
  PRIMARY KEY (`notesid`,`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notes`
--

LOCK TABLES `notes` WRITE;
/*!40000 ALTER TABLE `notes` DISABLE KEYS */;
INSERT INTO `notes` VALUES (27,'love','2016-11-03 16:00:00','For Sale','asdfasdf','a'),(35,'xx','2016-11-03 16:00:00','Tuition','xxx','a'),(36,'xx','2016-11-03 16:00:00','Tuition','xxx','a'),(37,'xx','2016-11-03 16:00:00','Jobs','xxx','a'),(38,'a','2016-11-03 16:00:00','Jobs','a','a'),(39,'b','2016-11-03 16:00:00','Jobs','b','a'),(40,'c','2016-11-03 16:00:00','Jobs','c','a'),(41,'c','2016-11-03 16:00:00','Jobs','c','a'),(42,'c','2016-11-03 16:00:00','Jobs','c','a'),(43,'c','2016-11-03 16:00:00','Jobs','c','a'),(44,'c','2016-11-03 16:00:00','Jobs','cf','a'),(45,'c','2016-11-03 16:00:00','Jobs','cf','a'),(46,'c','2016-11-03 16:00:00','Jobs','cf','a'),(47,'c','2016-11-03 16:00:00','Jobs','cf','a'),(48,'c','2016-11-03 16:00:00','Jobs','cf','a'),(49,'c','2016-11-03 16:00:00','Jobs','cf','a'),(50,'c','2016-11-03 16:00:00','Jobs','cf','a'),(51,'c','2016-11-03 16:00:00','Jobs','cf','a'),(52,'c','2016-11-03 16:00:00','Jobs','cf','a'),(53,'c','2016-11-03 16:00:00','Jobs','cf','a'),(54,'c','2016-11-03 16:00:00','Jobs','cf','a'),(55,'c','2016-11-03 16:00:00','Jobs','cf','a'),(56,'c','2016-11-03 16:00:00','Jobs','cf','a'),(57,'c','2016-11-03 16:00:00','Jobs','cf','a'),(58,'test','2016-11-03 16:00:00','Jobs','yes','zxc'),(59,'','2016-11-03 16:00:00','Social','','a'),(60,'asss','2016-11-03 16:00:00','Social','add','zxc'),(61,'f','2016-11-03 16:00:00','Social','','a'),(62,'','2016-11-03 16:00:00','Social','','a'),(63,'','2016-11-03 16:00:00','Social','','a'),(64,'','2016-11-03 16:00:00','Social','','a'),(65,'','2016-11-03 16:00:00','Social','','a'),(66,'','2016-11-03 16:00:00','Social','','a'),(67,'','2016-11-03 16:00:00','Social','','a'),(68,'','2016-11-03 16:00:00','Social','','a'),(69,'','2016-11-03 16:00:00','Social','','a');
/*!40000 ALTER TABLE `notes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `userid` varchar(32) NOT NULL,
  `password` varchar(128) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('111','222'),('123','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),('a','ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb'),('fff','f284bdc3c1c9e24a494e285cb387c69510f28de51c15bb93179d9c7f28705398'),('zxc','657f18518eaa2f41307895e18c3ba0d12d97b8a23c6de3966f52c6ba39a07ee4');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-04 14:06:06
