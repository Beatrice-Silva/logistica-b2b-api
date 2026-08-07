CREATE DATABASE  IF NOT EXISTS `db_logisticab2b` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_logisticab2b`;
-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: db_logisticab2b
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `lojas`
--

DROP TABLE IF EXISTS `lojas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lojas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome_estabelecimento` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cnpj` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id_usuario` bigint NOT NULL,
  `contato_email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cidade` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `endereco` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ativo` bit(1) DEFAULT NULL,
  `codigo_lon` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cnpj` (`cnpj`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `lojas_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lojas`
--

LOCK TABLES `lojas` WRITE;
/*!40000 ALTER TABLE `lojas` DISABLE KEYS */;
INSERT INTO `lojas` VALUES (1,'Loja Central','12.345.678/0001-01',2,'central@loja.com','Londrina','Av. Higienopolis, 1000',NULL,NULL),(2,'Mercado Norte','98.765.432/0001-99',4,'norte@mercado.com','Londrina','Rua Sergipe, 500',NULL,NULL),(3,'Tech Store','11.222.333/0001-44',2,'tech@store.com','Cambe','Av. Inglaterra, 200',NULL,NULL),(4,'Farmacia Boa','55.666.777/0001-88',4,'farmacia@boa.com','Londrina','Rua Para, 750',NULL,NULL),(5,'Papelaria Escola','33.444.555/0001-22',2,'papel@escola.com','Ibipora','Av. Parana, 300',NULL,NULL);
/*!40000 ALTER TABLE `lojas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pacotes`
--

DROP TABLE IF EXISTS `pacotes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pacotes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo_lon` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `otp_codigo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `otp_expira` datetime DEFAULT NULL,
  `id_loja` bigint NOT NULL,
  `endereco` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status_atual` enum('CRIADO','COLETADO','EM_TRANSITO','ENTREGUE','DEVOLVIDO','ARQUIVADO') COLLATE utf8mb4_unicode_ci DEFAULT 'CRIADO',
  `peso` double NOT NULL,
  `desc_observ` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `codigo_rastreio` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `endereco_destino` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email_destinatario` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_lon` (`codigo_lon`),
  UNIQUE KEY `UK9d6ixdppn62pd2dloo6jv6n58` (`codigo_rastreio`),
  KEY `id_loja` (`id_loja`),
  CONSTRAINT `pacotes_ibfk_1` FOREIGN KEY (`id_loja`) REFERENCES `lojas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pacotes`
--

LOCK TABLES `pacotes` WRITE;
/*!40000 ALTER TABLE `pacotes` DISABLE KEYS */;
INSERT INTO `pacotes` VALUES (1,'LON2026001',NULL,NULL,1,'Rua Tupi, 123 - Londrina','CRIADO',2.5,'Fragil',NULL,NULL,NULL),(2,'LON2026002',NULL,NULL,1,'Av. Maringa, 456 - Londrina','COLETADO',1.2,NULL,NULL,NULL,NULL),(3,'LON2026003',NULL,NULL,2,'Rua Goias, 789 - Cambe','CRIADO',5,'Urgente',NULL,NULL,NULL),(4,'LON2026004',NULL,NULL,3,'Rua Sao Paulo, 321 - Londrina','ENTREGUE',0.8,'Deixar portaria',NULL,NULL,NULL),(5,'LON2026005',NULL,NULL,2,'Av. Tiradentes, 654 - Ibipora','DEVOLVIDO',3.3,'Ausente',NULL,NULL,NULL);
/*!40000 ALTER TABLE `pacotes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_historico`
--

DROP TABLE IF EXISTS `status_historico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status_historico` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_pacote` bigint NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `data_hora` datetime NOT NULL,
  `desc_observ` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id_usuario` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `status_historico_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_historico`
--

LOCK TABLES `status_historico` WRITE;
/*!40000 ALTER TABLE `status_historico` DISABLE KEYS */;
INSERT INTO `status_historico` VALUES (1,1,'Criado','2026-06-20 08:00:00','Pacote cadastrado',2),(2,2,'Criado','2026-06-20 09:00:00','Pacote cadastrado',2),(3,2,'Coletado','2026-06-20 10:30:00','Coletado por Ana',3),(4,3,'Em transito','2026-06-21 14:00:00','Saiu para entrega',3),(5,4,'Entregue','2026-06-21 16:05:00','Entregue com sucesso',3);
/*!40000 ALTER TABLE `status_historico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `senha` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bloqueado_ate` datetime(6) DEFAULT NULL,
  `criado_em` datetime(6) DEFAULT NULL,
  `perfil_role` enum('ADMIN','ENTREGADOR','OPERADOR') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tentativas_otp` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Admin Sistema','admin@log.com','admin123',NULL,NULL,NULL,NULL),(2,'Carlos Silva','carlos@loja.com','loja123',NULL,NULL,NULL,NULL),(3,'Ana Souza','ana@entrega.com','ana123',NULL,NULL,NULL,NULL),(4,'Marcos Lima','marcos@loja.com','marcos1',NULL,NULL,NULL,NULL),(5,'Julia Costa','julia@log.com','julia123',NULL,NULL,NULL,NULL),(10,'Naomi','aimed09513@gmail.com','loja2',NULL,'2026-08-04 15:51:57.000000','OPERADOR',0),(11,'Marina','marina2@teste.com','1234567M',NULL,'2026-08-05 00:53:20.805469','OPERADOR',0);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-07 12:06:50
