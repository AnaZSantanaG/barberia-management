-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: barberia_system
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `citas`
--

DROP TABLE IF EXISTS `citas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `citas` (
  `id_cita` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `id_peluquero` int NOT NULL,
  `id_estilo` int NOT NULL,
  `fecha_cita` datetime NOT NULL,
  `metodo_pago` enum('EFECTIVO','TARJETA DEBITO','TARJETA CREDITO','TRANSFERENCIA','OTRO') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'EFECTIVO',
  `estado` enum('PENDIENTE','REALIZADO','CANCELADO') COLLATE utf8mb4_unicode_ci DEFAULT 'PENDIENTE',
  `notas` text COLLATE utf8mb4_unicode_ci,
  `mora_aplicada` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id_cita`),
  KEY `id_estilo` (`id_estilo`),
  KEY `citas_ibfk_1` (`id_cliente`),
  KEY `citas_ibfk_2` (`id_peluquero`),
  CONSTRAINT `citas_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_clientes`) ON DELETE CASCADE,
  CONSTRAINT `citas_ibfk_2` FOREIGN KEY (`id_peluquero`) REFERENCES `peluqueros` (`id_Peluquero`) ON DELETE CASCADE,
  CONSTRAINT `citas_ibfk_3` FOREIGN KEY (`id_estilo`) REFERENCES `estilos_corte` (`id_estilos`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id_clientes` int NOT NULL AUTO_INCREMENT,
  `id_users` int NOT NULL,
  `nombre` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefono` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Ciudad` text COLLATE utf8mb4_unicode_ci,
  `preferencias` text COLLATE utf8mb4_unicode_ci,
  `activo` tinyint(1) DEFAULT '1',
  `fecha_registro` datetime DEFAULT CURRENT_TIMESTAMP,
  `foto_perfil` longblob,
  PRIMARY KEY (`id_clientes`),
  KEY `id_users` (`id_users`),
  CONSTRAINT `clientes_ibfk_1` FOREIGN KEY (`id_users`) REFERENCES `users` (`idusers`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `disponibilidad_pel`
--

DROP TABLE IF EXISTS `disponibilidad_pel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disponibilidad_pel` (
  `id_disponibilidad` int NOT NULL AUTO_INCREMENT,
  `id_peluquero` int NOT NULL,
  `dia_semana` enum('LUNES','MARTES','MIERCOLES','JUEVES','VIERNES','SABADOS','DOMINGO') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `hora_inicio` time DEFAULT NULL,
  `hora_fin` time DEFAULT NULL,
  PRIMARY KEY (`id_disponibilidad`),
  KEY `id_peluquero` (`id_peluquero`),
  CONSTRAINT `disponibilidad_pel_ibfk_1` FOREIGN KEY (`id_peluquero`) REFERENCES `peluqueros` (`id_Peluquero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `estilos_corte`
--

DROP TABLE IF EXISTS `estilos_corte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estilos_corte` (
  `id_estilos` int NOT NULL AUTO_INCREMENT,
  `nombre_estilo` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `decripcion` text COLLATE utf8mb4_unicode_ci,
  `precio` decimal(10,2) NOT NULL,
  `tiempo_estimado` int DEFAULT NULL,
  `imagen_estilo` longblob,
  `popularidad` int DEFAULT '0',
  PRIMARY KEY (`id_estilos`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `galeria_trabajos`
--

DROP TABLE IF EXISTS `galeria_trabajos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `galeria_trabajos` (
  `id_trabajo` int NOT NULL AUTO_INCREMENT,
  `id_cita` int DEFAULT NULL,
  `imagen_antes` longblob,
  `imagen_despues` longblob,
  `notas` text COLLATE utf8mb4_unicode_ci,
  `fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_trabajo`),
  KEY `id_cita` (`id_cita`),
  CONSTRAINT `galeria_trabajos_ibfk_1` FOREIGN KEY (`id_cita`) REFERENCES `citas` (`id_cita`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `metodo_pago`
--

DROP TABLE IF EXISTS `metodo_pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `metodo_pago` (
  `id_metodo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `descripcion` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `requiere_comprobante` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id_metodo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `moras`
--

DROP TABLE IF EXISTS `moras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `moras` (
  `id_mora` int NOT NULL AUTO_INCREMENT,
  `id_cita` int NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `fecha_aplicacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_mora`),
  KEY `id_cita` (`id_cita`),
  CONSTRAINT `moras_ibfk_1` FOREIGN KEY (`id_cita`) REFERENCES `citas` (`id_cita`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `peluqueros`
--

DROP TABLE IF EXISTS `peluqueros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `peluqueros` (
  `id_Peluquero` int NOT NULL AUTO_INCREMENT,
  `id_users` int NOT NULL,
  `nombre_completo` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Ciudad` text COLLATE utf8mb4_unicode_ci,
  `telefono` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `years_experiencia` int DEFAULT NULL,
  `nombreBarberia` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `especialidades` text COLLATE utf8mb4_unicode_ci,
  `biografia` text COLLATE utf8mb4_unicode_ci,
  `foto_perfil` longblob,
  PRIMARY KEY (`id_Peluquero`),
  KEY `id_users` (`id_users`),
  CONSTRAINT `peluqueros_ibfk_1` FOREIGN KEY (`id_users`) REFERENCES `users` (`idusers`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transacciones`
--

DROP TABLE IF EXISTS `transacciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transacciones` (
  `id_transacciones` int NOT NULL AUTO_INCREMENT,
  `id_cita` int NOT NULL,
  `id_metodo` int NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `comision` decimal(5,2) NOT NULL,
  `referencia` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fecha_pago` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_transacciones`),
  KEY `id_cita` (`id_cita`),
  KEY `id_metodo` (`id_metodo`),
  CONSTRAINT `transacciones_ibfk_1` FOREIGN KEY (`id_cita`) REFERENCES `citas` (`id_cita`),
  CONSTRAINT `transacciones_ibfk_2` FOREIGN KEY (`id_metodo`) REFERENCES `metodo_pago` (`id_metodo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `idusers` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `clave` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tipo` enum('peluquero','cliente','Admin') COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_registro` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idusers`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `vista_cancelaciones_penalidades`
--

DROP TABLE IF EXISTS `vista_cancelaciones_penalidades`;
/*!50001 DROP VIEW IF EXISTS `vista_cancelaciones_penalidades`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_cancelaciones_penalidades` AS SELECT 
 1 AS `id_cita`,
 1 AS `cliente`,
 1 AS `peluquero`,
 1 AS `servicio`,
 1 AS `precio_original`,
 1 AS `fecha_cita_programada`,
 1 AS `estado`,
 1 AS `motivo_cancelacion`,
 1 AS `monto_penalidad`,
 1 AS `fecha_penalidad`,
 1 AS `metodo_penalidad`,
 1 AS `estado_penalidad`,
 1 AS `dias_anticipacion_cancelacion`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `vista_cancelaciones_penalidades`
--

/*!50001 DROP VIEW IF EXISTS `vista_cancelaciones_penalidades`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_cancelaciones_penalidades` AS select `c`.`id_cita` AS `id_cita`,`cl`.`nombre` AS `cliente`,`p`.`nombre_completo` AS `peluquero`,`ec`.`nombre_estilo` AS `servicio`,`ec`.`precio` AS `precio_original`,`c`.`fecha_cita` AS `fecha_cita_programada`,`c`.`estado` AS `estado`,`c`.`notas` AS `motivo_cancelacion`,`t`.`monto` AS `monto_penalidad`,`t`.`fecha_pago` AS `fecha_penalidad`,`mp`.`nombre` AS `metodo_penalidad`,(case when (`t`.`id_transacciones` is not null) then 'penalidad aplicada' when ((`c`.`estado` = 'cancelado') and (timestampdiff(HOUR,`c`.`fecha_cita`,now()) < 24)) then 'penalidad pendiente' else 'sin penalidad' end) AS `estado_penalidad`,(to_days(`c`.`fecha_cita`) - to_days(now())) AS `dias_anticipacion_cancelacion` from (((((`citas` `c` join `clientes` `cl` on((`c`.`id_cliente` = `cl`.`id_clientes`))) join `peluqueros` `p` on((`c`.`id_peluquero` = `p`.`id_Peluquero`))) join `estilos_corte` `ec` on((`c`.`id_estilo` = `ec`.`id_estilos`))) left join `transacciones` `t` on(((`c`.`id_cita` = `t`.`id_cita`) and (`t`.`id_metodo` = (select `metodo_pago`.`id_metodo` from `metodo_pago` where (`metodo_pago`.`nombre` = 'penalidad') limit 1))))) left join `metodo_pago` `mp` on((`t`.`id_metodo` = `mp`.`id_metodo`))) where (`c`.`estado` = 'cancelado') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-19  1:01:57
