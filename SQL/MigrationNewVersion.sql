-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: exam
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_demande` datetime(6) DEFAULT NULL,
  `date_debut` datetime(6) DEFAULT NULL,
  `date_fin` datetime(6) DEFAULT NULL,
  `mail_send` bit(1) NOT NULL,
  `livre_reservation_id` bigint DEFAULT NULL,
  `statut_reservation_id` bigint DEFAULT NULL,
  `user_reservation_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaxplnjxcr0brj6qll3379exp9` (`livre_reservation_id`),
  KEY `FK2i2cs4lerdn26kceigr5aiiny` (`statut_reservation_id`),
  KEY `FK5497tv27p6rqv4umhmuayi2sp` (`user_reservation_id`),
  CONSTRAINT `FK2i2cs4lerdn26kceigr5aiiny` FOREIGN KEY (`statut_reservation_id`) REFERENCES `statut` (`id`),
  CONSTRAINT `FK5497tv27p6rqv4umhmuayi2sp` FOREIGN KEY (`user_reservation_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKaxplnjxcr0brj6qll3379exp9` FOREIGN KEY (`livre_reservation_id`) REFERENCES `livre` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (15,'2021-11-11 15:20:42.404000','2021-12-18 16:16:02.000000','2021-12-20 16:16:02.000000',_binary '',1,3,14),(21,'2021-12-18 16:35:27.406000',NULL,NULL,_binary '\0',1,7,2);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;


LOCK TABLES `statut` WRITE;
/*!40000 ALTER TABLE `statut` DISABLE KEYS */;
INSERT INTO `statut` VALUES (1,'En Creation'),(2,'Valider'),(3,'Fini'),(4,'A Rendre'),(5,'En Attente'),(6,'InList'),(7,'First');
/*!40000 ALTER TABLE `statut` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-18 18:14:05
