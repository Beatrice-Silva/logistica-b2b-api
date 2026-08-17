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
  `nome_estabelecimento` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cnpj` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `id_usuario` bigint NOT NULL,
  `contato_email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cidade` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `endereco` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `ativo` bit(1) DEFAULT b'1',
  `codigo_lon` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cnpj` (`cnpj`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `lojas_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lojas`
--

LOCK TABLES `lojas` WRITE;
/*!40000 ALTER TABLE `lojas` DISABLE KEYS */;
INSERT INTO `lojas` VALUES (6,'Supermercado Primavera','14.582.731/0001-09',13,'contato@superprimavera.com.br','Londrina','Av. Dez de Dezembro, 1840',_binary '','LON-LOJ-006'),(7,'Casa & Cia Utilidades','27.416.853/0001-62',15,'contato@casaecia.com.br','Londrina','Rua Sergipe, 1180',_binary '','LON-LOJ-007'),(8,'Eletronica Paraná','39.725.164/0001-35',17,'vendas@eletronicaparana.com.br','Cambé','Av. Roberto Koch, 620',_binary '','LON-LOJ-008'),(9,'Drogaria Saúde Total','08.613.492/0001-74',19,'logistica@saudetotal.com.br','Londrina','Av. Bandeirantes, 945',_binary '','LON-LOJ-009'),(10,'Moda Urbana','31.904.728/0001-48',13,'pedidos@modaurbana.com.br','Londrina','Rua Goiás, 1525',_binary '','LON-LOJ-010'),(11,'Livraria Páginas','22.681.357/0001-91',15,'contato@paginaslivraria.com.br','Londrina','Av. Higienópolis, 2260',_binary '','LON-LOJ-011'),(12,'Pet Center Londrina','45.318.672/0001-20',17,'atendimento@petcenterlondrina.com.br','Londrina','Av. Maringá, 3100',_binary '','LON-LOJ-012'),(13,'Constrular Materiais','16.753.904/0001-57',19,'expedicao@constrular.com.br','Ibiporã','Rua Paraná, 870',_binary '','LON-LOJ-013'),(14,'Bela Casa Decor','34.297.518/0001-83',13,'pedidos@belacasadecor.com.br','Rolândia','Av. Presidente Getúlio Vargas, 540',_binary '','LON-LOJ-014'),(15,'Esporte & Movimento','51.684.239/0001-16',15,'vendas@esportemovimento.com.br','Londrina','Rua Prefeito Hugo Cabral, 730',_binary '','LON-LOJ-015');
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
  `codigo_lon` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `otp_codigo` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `otp_expira` datetime DEFAULT NULL,
  `id_loja` bigint NOT NULL,
  `endereco` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status_atual` enum('CRIADO','COLETADO','EM_TRANSITO','ENTREGUE','DEVOLVIDO','ARQUIVADO') COLLATE utf8mb4_general_ci DEFAULT 'CRIADO',
  `peso` double DEFAULT NULL,
  `desc_observ` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `codigo_rastreio` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `endereco_destino` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email_destinatario` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_lon` (`codigo_lon`),
  UNIQUE KEY `UK_rastreio` (`codigo_rastreio`),
  KEY `id_loja` (`id_loja`),
  CONSTRAINT `pacotes_ibfk_1` FOREIGN KEY (`id_loja`) REFERENCES `lojas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pacotes`
--

LOCK TABLES `pacotes` WRITE;
/*!40000 ALTER TABLE `pacotes` DISABLE KEYS */;
INSERT INTO `pacotes` VALUES (6,'LON2026006',NULL,NULL,6,'Av. Dez de Dezembro, 1840 - Londrina','CRIADO',8.4,'Caixa com produtos alimentícios não perecíveis','BRLON260806001','Rua Professor João Cândido, 412 - Londrina','cliente01@email.com'),(7,'LON2026007',NULL,NULL,7,'Rua Sergipe, 1180 - Londrina','COLETADO',2.3,'Produtos de cozinha','BRLON260806002','Rua Espírito Santo, 845 - Londrina','cliente02@email.com'),(8,'LON2026008',NULL,NULL,8,'Av. Roberto Koch, 620 - Cambé','EM_TRANSITO',1.15,'Equipamento eletrônico - manusear com cuidado','BRLON260806003','Rua Belo Horizonte, 1270 - Londrina','cliente03@email.com'),(9,'LON2026009',NULL,NULL,9,'Av. Bandeirantes, 945 - Londrina','ENTREGUE',0.75,'Medicamentos de uso permitido','BRLON260805004','Rua Pará, 530 - Londrina','cliente04@email.com'),(10,'LON2026010',NULL,NULL,10,'Rua Goiás, 1525 - Londrina','DEVOLVIDO',1.8,'Cliente solicitou devolução','BRLON260804005','Rua Santos Dumont, 910 - Londrina','cliente05@email.com'),(11,'LON2026011',NULL,NULL,11,'Av. Higienópolis, 2260 - Londrina','ARQUIVADO',3.2,'Entrega concluída e processo arquivado','BRLON260803006','Rua Paranaguá, 122 - Londrina','cliente06@email.com'),(12,'LON2026012',NULL,NULL,12,'Av. Maringá, 3100 - Londrina','CRIADO',6.7,'Ração para cães - saco fechado','BRLON260808007','Rua Montevidéu, 385 - Londrina','cliente07@email.com'),(13,'LON2026013',NULL,NULL,13,'Rua Paraná, 870 - Ibiporã','COLETADO',12.5,'Material para construção','BRLON260808008','Rua Piauí, 640 - Londrina','cliente08@email.com'),(14,'LON2026014',NULL,NULL,14,'Av. Presidente Getúlio Vargas, 540 - Rolândia','EM_TRANSITO',4.6,'Objetos decorativos - frágil','BRLON260807009','Rua Maranhão, 1440 - Londrina','cliente09@email.com'),(15,'LON2026015',NULL,NULL,15,'Rua Prefeito Hugo Cabral, 730 - Londrina','ENTREGUE',2.1,'Calçados esportivos','BRLON260802010','Rua Alagoas, 290 - Londrina','cliente10@email.com');
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
  `status` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `data_hora` datetime NOT NULL,
  `desc_observ` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `id_usuario` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `status_historico_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_historico`
--

LOCK TABLES `status_historico` WRITE;
/*!40000 ALTER TABLE `status_historico` DISABLE KEYS */;
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
  `nome` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `senha` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bloqueado_ate` datetime(6) DEFAULT NULL,
  `criado_em` datetime(6) DEFAULT NULL,
  `perfil_role` enum('ADMIN','ENTREGADOR','OPERADOR') COLLATE utf8mb4_general_ci DEFAULT 'ENTREGADOR',
  `tentativas_otp` int DEFAULT '0',
  `status` varchar(50) COLLATE utf8mb4_general_ci DEFAULT 'ATIVO',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (12,'Rafael Oliveira','rafael@logistica.com','rafael123',NULL,'2026-06-02 08:15:00.000000','ADMIN',0,'ATIVO'),(13,'Fernanda Martins','fernanda@logistica.com','fernanda123',NULL,'2026-06-06 09:20:00.000000','OPERADOR',0,'ATIVO'),(14,'Lucas Ferreira','lucas@logistica.com','lucas123',NULL,'2026-06-08 07:45:00.000000','ENTREGADOR',0,'ATIVO'),(15,'Beatriz Almeida','beatriz@logistica.com','beatriz123',NULL,'2026-06-12 10:10:00.000000','OPERADOR',0,'ATIVO'),(16,'Gabriel Santos','gabriel@logistica.com','gabriel123',NULL,'2026-06-15 06:50:00.000000','ENTREGADOR',0,'ATIVO'),(17,'Patricia Rodrigues','patricia@logistica.com','patricia123',NULL,'2026-06-20 08:30:00.000000','OPERADOR',0,'ATIVO'),(18,'Eduardo Gomes','eduardo@logistica.com','eduardo123',NULL,'2026-06-25 09:00:00.000000','ENTREGADOR',0,'ATIVO'),(19,'Camila Barbosa','camila@logistica.com','camila123',NULL,'2026-07-01 08:40:00.000000','OPERADOR',0,'ATIVO'),(20,'Thiago Mendes','thiago@logistica.com','thiago123',NULL,'2026-07-05 07:30:00.000000','ENTREGADOR',0,'ATIVO');
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

-- Dump completed on 2026-08-17 11:58:03
