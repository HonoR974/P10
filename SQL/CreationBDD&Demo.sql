-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: p10_review
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
-- Table structure for table `bibliotheque`
--

DROP TABLE IF EXISTS `bibliotheque`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bibliotheque` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `adresse` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bibliotheque`
--

LOCK TABLES `bibliotheque` WRITE;
/*!40000 ALTER TABLE `bibliotheque` DISABLE KEYS */;
INSERT INTO `bibliotheque` VALUES (1,'avenue de la madelaine ','La nouvelle Bibliotheque');
/*!40000 ALTER TABLE `bibliotheque` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examplaire`
--

DROP TABLE IF EXISTS `examplaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examplaire` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `edition` varchar(255) DEFAULT NULL,
  `emprunt` bit(1) DEFAULT NULL,
  `livre_id` bigint DEFAULT NULL,
  `reserver` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5hsq1l47fb935aiqufstduraq` (`livre_id`),
  CONSTRAINT `FK5hsq1l47fb935aiqufstduraq` FOREIGN KEY (`livre_id`) REFERENCES `livre` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examplaire`
--

LOCK TABLES `examplaire` WRITE;
/*!40000 ALTER TABLE `examplaire` DISABLE KEYS */;
INSERT INTO `examplaire` VALUES (45,'Pocket',_binary '',1,NULL),(51,'Pocket',_binary '\0',3,NULL),(52,'Pocket',_binary '\0',3,NULL),(53,'Pocket',_binary '\0',3,NULL),(70,'Albain Michel ',_binary '\0',6,NULL),(71,'Albain Michel ',_binary '\0',6,NULL),(72,'Albain Michel ',_binary '\0',6,NULL),(73,'Albain Michel ',_binary '\0',6,NULL),(74,'Alisio ',_binary '\0',7,NULL),(75,'Alisio ',_binary '\0',7,NULL),(76,'Alisio ',_binary '\0',7,NULL),(77,'Alisio ',_binary '\0',7,NULL),(78,'Alisio ',_binary '\0',7,NULL),(79,'10/18 ',_binary '\0',8,NULL),(80,'10/18 ',_binary '\0',8,NULL),(82,'Pocket',_binary '\0',9,NULL),(83,'Pocket',_binary '\0',9,NULL),(84,'Pocket',_binary '\0',10,NULL),(85,'Pocket',_binary '\0',10,NULL),(86,'Pocket',_binary '\0',10,NULL),(87,'Pocket',_binary '\0',10,NULL),(88,'Pocket',_binary '\0',10,NULL),(89,'Pocket',_binary '\0',10,NULL),(96,'Michel Lafon',_binary '\0',4,NULL),(97,'Michel Lafon',_binary '\0',4,NULL),(98,'Livre de Poche',_binary '\0',11,NULL),(99,'Livre de Poche',_binary '\0',11,NULL),(100,'Babel',_binary '\0',13,NULL),(103,'Pocket',_binary '\0',1,NULL);
/*!40000 ALTER TABLE `examplaire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image_gallery`
--

DROP TABLE IF EXISTS `image_gallery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image_gallery` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `image` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image_gallery`
--

LOCK TABLES `image_gallery` WRITE;
/*!40000 ALTER TABLE `image_gallery` DISABLE KEYS */;
INSERT INTO `image_gallery` VALUES (3,'La-fille-de-papier.jpg',NULL),(8,'Minier-Bernard-N-EteinsPasLaLumiere.jpg',NULL),(18,'CoderPropremenent.jpg',NULL),(19,'Arche.jpg',NULL),(20,'Arche.jpg',NULL),(21,'21-Lecon.jpg',NULL),(24,'DeepWork.jpg',NULL),(26,'leCoeurLacche.jpg',NULL),(28,'UnePutain.jpg',NULL),(32,'QueTaVolont??.jpg',NULL),(33,'Thanatonautes.jpg',NULL),(34,'Wow.jpg',NULL),(35,'millenium.jpg',NULL),(36,'millenium.jpg',NULL);
/*!40000 ALTER TABLE `image_gallery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `livre`
--

DROP TABLE IF EXISTS `livre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `livre` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `auteur` varchar(255) DEFAULT NULL,
  `titre` varchar(255) DEFAULT NULL,
  `bibliotheque_id` bigint DEFAULT NULL,
  `image_id` bigint DEFAULT NULL,
  `description` text,
  `disponible` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKc2553wppf7coad67jpsh9egga` (`titre`),
  KEY `FKr1d20hy7k1mp2fphrnl87mycb` (`bibliotheque_id`),
  KEY `FK4g9h7xlayt8p25wqvr1ep568l` (`image_id`),
  CONSTRAINT `FK4g9h7xlayt8p25wqvr1ep568l` FOREIGN KEY (`image_id`) REFERENCES `image_gallery` (`id`),
  CONSTRAINT `FKr1d20hy7k1mp2fphrnl87mycb` FOREIGN KEY (`bibliotheque_id`) REFERENCES `bibliotheque` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livre`
--

LOCK TABLES `livre` WRITE;
/*!40000 ALTER TABLE `livre` DISABLE KEYS */;
INSERT INTO `livre` VALUES (1,'Bernard Minier ','N\'??teins pas la lumi??re ',1,8,'Le soir de No??l, Christine Steinmeyer, animatrice radio ?? Toulouse, trouve dans sa bo??te aux lettres le courrier d\'une femme qui annonce son suicide. Elle est convaincue que le message ne lui est pas destin??.',_binary '\0'),(3,'Guillaume Musso','La Fille de Papier',1,3,'Ensemble, Tom et Billie vont vivre une aventure extraordinaire o?? la r??alit?? et la fiction s\'entrem??lent et se bousculent dans un jeu s??duisant et mortel... Une com??die vive et piquante. Un suspense romantique et fantastique. Quand la vie ne tient plus qu\'?? un livre !',_binary ''),(4,'Julie de Lestrange','La Nouvelle Arche',1,20,'Mathilde est l\'une des premi??res. Aujourd\'hui ??g??e de 20 ans, elle s\'occupe des futures g??n??rations qui grandissent au Centre. Comme elle, ces sp??cimens n\'auront pas d\'enfance. Comme elle, ils na??tront, pr??ts ?? se battre, pour affronter l\'ennemi invisible qui terrorise leur Communaut??.',_binary ''),(6,'Yuval Noah Harari','21 le??ons pour le XXIe si??cle',1,21,'Apr??s Sapiens qui explorait le pass?? de notre humanit?? et Homo deus la piste d\'un avenir gouvern?? par l\'intelligence artificielle, 21 le??ons pour le XXIe si??cle nous confronte aux grands d??fis contemporains. Pourquoi la d??mocratie lib??rale est-elle en crise ? Sommes-nous ?? l\'aube d\'une nouvelle guerre mondiale ? Que faire devant l\'??pid??mie de \"fake news\" ? Quelle civilisation domine le monde : l\'Occident, la Chine ou l\'Islam ? Que devons-nous enseigner ?? nos enfants ?',_binary ''),(7,'Cal Newport','Deep work',1,24,'???Deep Work??? de Cal Newport est un livre sur la science de la productivit??. Cal Newport affirme que le meilleur moyen d???obtenir un travail plus significatif est de travailler en profondeur ??? de travailler dans un ??tat de concentration ??lev??e sans distractions sur une seule t??che.',_binary ''),(8,'Margaret Atwood','C\'est le coeur qui l??che en dernier',1,26,'Avec C???est le coeur qui l??che en dernier, Margaret Atwood nous livre un roman aussi hilarant qu???inqui??tant, une implacable satire de nos vices et travers qui nous enferment dans de viles obsessions quand le monde entier est en passe de dispara??tre.',_binary ''),(9,'Bernard Minier','Une putain d\'histoire',1,28,'Une ??le bois??e au large de Seattle... \" Au commencement est la peur.La peur de se noyer.La peur des autres, ceux qui me d??testent, ceux qui veulent ma peau. Autant vous le dire tout de suite : Ce n\'est pas une histoire banale. ??a non. c\'est une putain d\'histoire.Ouais, une putain d\'histoire... \" Un thriller implacable',_binary ''),(10,'Maxime Chattam','Que ta volont?? soit faite',1,32,'Jon Petersen. Enfant?? dans le sang, ??lev?? ?? la dure par son grand-p??re Ingmar, figure imposante et violente, et ses deux tantes, Rackel et Hannah. ... D??s lors que l\'on touche ?? ses fourmili??res, Jon enrage et bien lui aura pris ?? ce jeune Tyler qui finira sous les coups acharn??s de ce dernier et en sang, le visage d??moli.',_binary ''),(11,'BernarWerber','Les Thanatonautes',1,33,'L\'homme a tout explor?? : le monde de l\'espace, le monde sous-marin, le monde souterrain ; pourtant il lui manque la connaissance d\'un monde : le continent des morts.',_binary ''),(13,'Stieg Larson','Les hommes qui n\'aimaient pas les femmes : Mill??nium 1',1,35,'Contraint d\'abandonner son poste de r??dacteur pour avoir diffam?? un requin de la finance, Mikael Blomkvist est bient??t associ?? ?? Lisbeth Salander, jeune femme rebelle et fouineuse, pour travailler avec Henrik Vanger, un industriel d??sireux de faire la lumi??re sur la disparition, vieille de plus de trente ans, de sa petite ni??ce, au cours d\'une r??union familiale... Le premier volet de la s??rie culte.',_binary '');
/*!40000 ALTER TABLE `livre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pret`
--

DROP TABLE IF EXISTS `pret`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pret` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_debut` date DEFAULT NULL,
  `date_fin` date DEFAULT NULL,
  `email` bit(1) DEFAULT NULL,
  `prolonger` bit(1) DEFAULT NULL,
  `examplaire_id` bigint DEFAULT NULL,
  `statut_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `image_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKakhpji20vitn7fv5oa1f2nxj7` (`examplaire_id`),
  KEY `FKrbbbegkxh6c9wnybu9phurbs3` (`statut_id`),
  KEY `FK48na46jxkydna4wrnq22fb3e` (`user_id`),
  KEY `FKm79pvipv5c4a9yxyfu5imc4dj` (`image_id`),
  CONSTRAINT `FK48na46jxkydna4wrnq22fb3e` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKakhpji20vitn7fv5oa1f2nxj7` FOREIGN KEY (`examplaire_id`) REFERENCES `examplaire` (`id`),
  CONSTRAINT `FKm79pvipv5c4a9yxyfu5imc4dj` FOREIGN KEY (`image_id`) REFERENCES `image_gallery` (`id`),
  CONSTRAINT `FKrbbbegkxh6c9wnybu9phurbs3` FOREIGN KEY (`statut_id`) REFERENCES `statut` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pret`
--

LOCK TABLES `pret` WRITE;
/*!40000 ALTER TABLE `pret` DISABLE KEYS */;
INSERT INTO `pret` VALUES (161,'2021-11-09','2021-12-07',_binary '\0',_binary '\0',103,3,10,8),(165,'2021-11-11','2021-12-09',_binary '\0',_binary '\0',45,2,9,8),(166,'2021-12-05','2022-02-01',_binary '\0',_binary '',73,3,2,21);
/*!40000 ALTER TABLE `pret` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (15,'2021-11-11 15:20:42.404000',NULL,NULL,_binary '\0',1,7,14);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statut`
--

DROP TABLE IF EXISTS `statut`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `statut` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statut`
--

LOCK TABLES `statut` WRITE;
/*!40000 ALTER TABLE `statut` DISABLE KEYS */;
INSERT INTO `statut` VALUES (1,'En Creation'),(2,'Valider'),(3,'Fini'),(4,'A Rendre'),(5,'En Attente'),(6,'InList'),(7,'First');
/*!40000 ALTER TABLE `statut` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `matching_password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'smiirf123@gmail.com',_binary '\0','$2a$10$GjecpwSxYjJImn9CZ/KEA.RZqsr21ChnYnPAx9KQHML4lfoLEEtam','personnage',NULL),(4,'admin@gmail.com',_binary '\0','$2a$10$Xu78tXpKulfVkwLSFtzpdOss/n21rZliNevW23CoWBhXVibeQbmva','ADMIN',NULL),(5,'batch@gmail.com',_binary '\0','$2a$10$DI4JR2gs8Vs8d0ggQ65uR.9THZnf4FEhgYO7SdruVcItLDjmRJDEG','batch',NULL),(9,'damien.dorval1@gmail.com',_binary '\0','$2a$10$ImdlyDi9X.hprpplEFj3Ou7IHX.StErQHzFSw8IJ1EuTwLvW.lYa2','sebastien974',NULL),(10,'angela2A@gmail.com',_binary '\0','$2a$10$sj/laeon/KbaVEBGhGHcq.Q8RVcosXwQ1cw54U5Zo9D19CMnVm.S2','Angela2A',NULL),(14,'hg.dev974@gmail.com',_binary '\0','$2a$10$U7r2dHt6VT/4FpmJlWhgD.0yy/sGEMQ.lmcKmyfFBAjZGQcCU1yyu','gandalfe789','$2a$10$U7r2dHt6VT/4FpmJlWhgD.0yy/sGEMQ.lmcKmyfFBAjZGQcCU1yyu'),(15,NULL,_binary '\0','$2a$10$P5q.qY3JkrCN6zo1XrXSiebHTBr7rRgWrKXcrWw72ZYzh6/6lXGuu','samuel456','$2a$10$P5q.qY3JkrCN6zo1XrXSiebHTBr7rRgWrKXcrWw72ZYzh6/6lXGuu');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification_token`
--

DROP TABLE IF EXISTS `verification_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verification_token` (
  `id` bigint NOT NULL,
  `expiry_date` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrdn0mss276m9jdobfhhn2qogw` (`user_id`),
  CONSTRAINT `FKrdn0mss276m9jdobfhhn2qogw` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_token`
--

LOCK TABLES `verification_token` WRITE;
/*!40000 ALTER TABLE `verification_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `verification_token` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-12 15:24:55

