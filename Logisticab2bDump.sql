CREATE DATABASE  IF NOT EXISTS `db_logisticab2b` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `db_logisticab2b`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db_logisticab2b
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome_estabelecimento` varchar(255) DEFAULT NULL,
  `cnpj` varchar(255) DEFAULT NULL,
  `id_usuario` bigint(20) NOT NULL,
  `contato_email` varchar(255) DEFAULT NULL,
  `cidade` varchar(255) DEFAULT NULL,
  `endereco` varchar(255) NOT NULL,
  `ativo` bit(1) DEFAULT NULL,
  `codigo_lon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cnpj` (`cnpj`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `lojas_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo_lon` varchar(30) NOT NULL,
  `otp_codigo` varchar(255) DEFAULT NULL,
  `otp_expira` datetime DEFAULT NULL,
  `id_loja` bigint(20) NOT NULL,
  `endereco` varchar(100) NOT NULL,
  `status_atual` enum('CRIADO','COLETADO','EM_TRANSITO','ENTREGUE','DEVOLVIDO','ARQUIVADO') DEFAULT 'CRIADO',
  `peso` double NOT NULL,
  `desc_observ` varchar(255) DEFAULT NULL,
  `codigo_rastreio` varchar(255) DEFAULT NULL,
  `endereco_destino` varchar(255) DEFAULT NULL,
  `email_destinatario` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_lon` (`codigo_lon`),
  UNIQUE KEY `UK9d6ixdppn62pd2dloo6jv6n58` (`codigo_rastreio`),
  KEY `id_loja` (`id_loja`),
  CONSTRAINT `pacotes_ibfk_1` FOREIGN KEY (`id_loja`) REFERENCES `lojas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pacotes`
--

LOCK TABLES `pacotes` WRITE;
/*!40000 ALTER TABLE `pacotes` DISABLE KEYS */;
INSERT INTO `pacotes` VALUES (6,'LON2026006',NULL,NULL,6,'Av. Dez de Dezembro, 1840 - Londrina','CRIADO',8.4,'Caixa com produtos alimentícios não perecíveis','BRLON260806001','Rua Professor João Cândido, 412 - Londrina','cliente01@email.com'),(7,'LON2026007',NULL,NULL,7,'Rua Sergipe, 1180 - Londrina','COLETADO',2.3,'Produtos de cozinha','BRLON260806002','Rua Espírito Santo, 845 - Londrina','cliente02@email.com'),(8,'LON2026008',NULL,NULL,8,'Av. Roberto Koch, 620 - Cambé','EM_TRANSITO',1.15,'Equipamento eletrônico - manusear com cuidado','BRLON260806003','Rua Belo Horizonte, 1270 - Londrina','cliente03@email.com'),(9,'LON2026009',NULL,NULL,9,'Av. Bandeirantes, 945 - Londrina','ENTREGUE',0.75,'Medicamentos de uso permitido','BRLON260805004','Rua Pará, 530 - Londrina','cliente04@email.com'),(10,'LON2026010',NULL,NULL,10,'Rua Goiás, 1525 - Londrina','DEVOLVIDO',1.8,'Cliente solicitou devolução','BRLON260804005','Rua Santos Dumont, 910 - Londrina','cliente05@email.com'),(11,'LON2026011',NULL,NULL,11,'Av. Higienópolis, 2260 - Londrina','ARQUIVADO',3.2,'Entrega concluída e processo arquivado','BRLON260803006','Rua Paranaguá, 122 - Londrina','cliente06@email.com'),(12,'LON2026012',NULL,NULL,12,'Av. Maringá, 3100 - Londrina','CRIADO',6.7,'Ração para cães - saco fechado','BRLON260808007','Rua Montevidéu, 385 - Londrina','cliente07@email.com'),(13,'LON2026013',NULL,NULL,13,'Rua Paraná, 870 - Ibiporã','COLETADO',12.5,'Material para construção','BRLON260808008','Rua Piauí, 640 - Londrina','cliente08@email.com'),(14,'LON2026014',NULL,NULL,14,'Av. Presidente Getúlio Vargas, 540 - Rolândia','EM_TRANSITO',4.6,'Objetos decorativos - frágil','BRLON260807009','Rua Maranhão, 1440 - Londrina','cliente09@email.com'),(15,'LON2026015',NULL,NULL,15,'Rua Prefeito Hugo Cabral, 730 - Londrina','ENTREGUE',2.1,'Calçados esportivos','BRLON260802010','Rua Alagoas, 290 - Londrina','cliente10@email.com'),(16,'LON2026016',NULL,NULL,6,'Av. Dez de Dezembro, 1840 - Londrina','CRIADO',4.35,'Cesta de produtos para escritório','BRLON260809011','Rua Mato Grosso, 760 - Londrina','cliente11@email.com'),(17,'LON2026017',NULL,NULL,7,'Rua Sergipe, 1180 - Londrina','COLETADO',3.9,'Utensílios domésticos','BRLON260809012','Rua Goiás, 2180 - Londrina','cliente12@email.com'),(18,'LON2026018',NULL,NULL,8,'Av. Roberto Koch, 620 - Cambé','EM_TRANSITO',0.95,'Acessórios para computador','BRLON260809013','Rua Santa Catarina, 1120 - Londrina','cliente13@email.com'),(19,'LON2026019',NULL,NULL,9,'Av. Bandeirantes, 945 - Londrina','ENTREGUE',1.2,'Produtos de higiene','BRLON260801014','Rua Pernambuco, 320 - Londrina','cliente14@email.com'),(20,'LON2026020',NULL,NULL,10,'Rua Goiás, 1525 - Londrina','DEVOLVIDO',2.75,'Endereço não localizado após tentativas','BRLON260806015','Rua Minas Gerais, 580 - Londrina','cliente15@email.com'),(21,'LON2026021',NULL,NULL,11,'Av. Higienópolis, 2260 - Londrina','ARQUIVADO',1.65,'Documento entregue ao destinatário','BRLON260730016','Rua Sergipe, 420 - Londrina','cliente16@email.com'),(22,'LON2026022',NULL,NULL,12,'Av. Maringá, 3100 - Londrina','EM_TRANSITO',5.8,'Ração e acessórios para animais','BRLON260810017','Rua Araguaia, 880 - Londrina','cliente17@email.com'),(23,'LON2026023',NULL,NULL,13,'Rua Paraná, 870 - Ibiporã','COLETADO',18.2,'Caixas de ferramentas','BRLON260810018','Av. Inglaterra, 1760 - Londrina','cliente18@email.com'),(24,'LON2026024',NULL,NULL,14,'Av. Presidente Getúlio Vargas, 540 - Rolândia','CRIADO',7.25,'Itens decorativos em vidro','BRLON260811019','Rua Professor Samuel Moura, 650 - Londrina','cliente19@email.com'),(25,'LON2026025',NULL,NULL,15,'Rua Prefeito Hugo Cabral, 730 - Londrina','ENTREGUE',1.85,'Vestuário esportivo','BRLON260807020','Rua Humaitá, 1050 - Londrina','cliente20@email.com');
/*!40000 ALTER TABLE `pacotes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_historico`
--

DROP TABLE IF EXISTS `status_historico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status_historico` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_pacote` bigint(20) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `data_hora` datetime NOT NULL,
  `desc_observ` varchar(255) DEFAULT NULL,
  `id_usuario` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `status_historico_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_historico`
--

LOCK TABLES `status_historico` WRITE;
/*!40000 ALTER TABLE `status_historico` DISABLE KEYS */;
INSERT INTO `status_historico` VALUES (6,6,'Criado','2026-08-06 08:12:00','Pacote cadastrado pela loja',13),(7,7,'Criado','2026-08-06 09:05:00','Pacote cadastrado pela loja',15),(8,7,'Coletado','2026-08-06 11:35:00','Pacote coletado no estabelecimento',14),(9,8,'Criado','2026-08-06 09:40:00','Pedido preparado para coleta',17),(10,8,'Coletado','2026-08-06 13:20:00','Coleta realizada pelo entregador',16),(11,8,'Em transito','2026-08-07 07:45:00','Pacote encaminhado para unidade de distribuição',16),(12,9,'Criado','2026-08-05 08:15:00','Pacote cadastrado',19),(13,9,'Coletado','2026-08-05 10:10:00','Pacote coletado na drogaria',18),(14,9,'Em transito','2026-08-05 13:40:00','Saiu para entrega',18),(15,9,'Entregue','2026-08-05 16:18:00','Entregue ao destinatário mediante confirmação',18),(16,10,'Criado','2026-08-04 08:50:00','Pedido criado pela loja',13),(17,10,'Coletado','2026-08-04 11:25:00','Pacote retirado para transporte',14),(18,10,'Em transito','2026-08-04 14:15:00','Pacote em rota de entrega',14),(19,10,'Devolvido','2026-08-05 17:30:00','Destinatário solicitou devolução',14),(20,11,'Criado','2026-08-03 09:00:00','Pacote cadastrado',15),(21,11,'Coletado','2026-08-03 11:10:00','Coleta realizada',16),(22,11,'Em transito','2026-08-03 13:30:00','Pacote em rota',16),(23,11,'Entregue','2026-08-03 15:55:00','Entrega confirmada pelo destinatário',16),(24,11,'Arquivado','2026-08-04 08:00:00','Processo de entrega finalizado e arquivado',15),(25,12,'Criado','2026-08-08 08:30:00','Pacote aguardando coleta',17),(26,13,'Criado','2026-08-08 09:10:00','Material separado para transporte',19),(27,13,'Coletado','2026-08-08 14:20:00','Material coletado no estabelecimento',20),(28,14,'Criado','2026-08-07 08:05:00','Pedido cadastrado',13),(29,14,'Coletado','2026-08-07 10:45:00','Pacote coletado na loja',18),(30,14,'Em transito','2026-08-08 07:20:00','Pacote em deslocamento para Londrina',18),(31,15,'Criado','2026-08-02 08:40:00','Pedido cadastrado',15),(32,15,'Coletado','2026-08-02 10:30:00','Coleta realizada',14),(33,15,'Em transito','2026-08-02 13:10:00','Saiu para entrega',14),(34,15,'Entregue','2026-08-02 15:42:00','Entrega realizada com sucesso',14),(35,16,'Criado','2026-08-09 09:15:00','Pacote cadastrado e aguardando coleta',13),(36,17,'Criado','2026-08-09 09:50:00','Pedido preparado pela loja',15),(37,17,'Coletado','2026-08-09 12:40:00','Pacote coletado pelo entregador',16),(38,18,'Criado','2026-08-09 10:20:00','Pedido cadastrado',17),(39,18,'Coletado','2026-08-09 13:00:00','Pacote coletado',18),(40,18,'Em transito','2026-08-10 07:35:00','Pacote saiu para entrega',18),(41,19,'Criado','2026-08-01 08:20:00','Pedido cadastrado',19),(42,19,'Coletado','2026-08-01 10:05:00','Coleta realizada',20),(43,19,'Em transito','2026-08-01 13:25:00','Saiu para entrega',20),(44,19,'Entregue','2026-08-01 16:02:00','Entrega confirmada',20),(45,20,'Criado','2026-08-06 08:35:00','Pedido criado',13),(46,20,'Coletado','2026-08-06 11:00:00','Pacote coletado',14),(47,20,'Em transito','2026-08-06 14:10:00','Saiu para entrega',14),(48,20,'Devolvido','2026-08-07 18:05:00','Endereço não localizado após duas tentativas',14),(49,21,'Criado','2026-07-30 08:00:00','Pacote cadastrado',15),(50,21,'Coletado','2026-07-30 10:15:00','Pacote coletado',16),(51,21,'Em transito','2026-07-30 13:00:00','Em rota de entrega',16),(52,21,'Entregue','2026-07-30 16:20:00','Entrega confirmada pelo destinatário',16),(53,21,'Arquivado','2026-07-31 08:10:00','Registro finalizado',15),(54,22,'Criado','2026-08-10 08:10:00','Pedido cadastrado',17),(55,22,'Coletado','2026-08-10 11:20:00','Pacote coletado',18),(56,22,'Em transito','2026-08-11 07:25:00','Pacote saiu para entrega',18),(57,23,'Criado','2026-08-10 09:00:00','Material separado para coleta',19),(58,23,'Coletado','2026-08-10 14:05:00','Material coletado no estabelecimento',20),(59,24,'Criado','2026-08-11 08:45:00','Pedido cadastrado e aguardando coleta',13),(60,25,'Criado','2026-08-07 08:30:00','Pedido cadastrado',15),(61,25,'Coletado','2026-08-07 10:40:00','Pacote coletado na loja',16),(62,25,'Em transito','2026-08-07 13:15:00','Saiu para entrega',16),(63,25,'Entregue','2026-08-07 16:45:00','Entrega realizada com sucesso',16);
/*!40000 ALTER TABLE `status_historico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `bloqueado_ate` datetime(6) DEFAULT NULL,
  `criado_em` datetime(6) DEFAULT NULL,
  `perfil_role` enum('ADMIN','ENTREGADOR','OPERADOR') DEFAULT 'ENTREGADOR',
  `tentativas_otp` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (12,'Rafael Oliveira','rafael@logistica.com','rafael123',NULL,'2026-06-02 08:15:00.000000','ADMIN',0),(13,'Fernanda Martins','fernanda@logistica.com','fernanda123',NULL,'2026-06-06 09:20:00.000000','OPERADOR',0),(14,'Lucas Ferreira','lucas@logistica.com','lucas123',NULL,'2026-06-08 07:45:00.000000','ENTREGADOR',0),(15,'Beatriz Almeida','beatriz@logistica.com','beatriz123',NULL,'2026-06-12 10:10:00.000000','OPERADOR',0),(16,'Gabriel Santos','gabriel@logistica.com','gabriel123',NULL,'2026-06-15 06:50:00.000000','ENTREGADOR',0),(17,'Patricia Rodrigues','patricia@logistica.com','patricia123',NULL,'2026-06-20 08:30:00.000000','OPERADOR',0),(18,'Eduardo Gomes','eduardo@logistica.com','eduardo123',NULL,'2026-06-25 09:00:00.000000','ENTREGADOR',0),(19,'Camila Barbosa','camila@logistica.com','camila123',NULL,'2026-07-01 08:40:00.000000','OPERADOR',0),(20,'Thiago Mendes','thiago@logistica.com','thiago123',NULL,'2026-07-05 07:30:00.000000','ENTREGADOR',0);
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

-- Dump completed on 2026-08-11 15:54:51
