-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: barberia_system
-- ------------------------------------------------------
-- Server version	8.0.42

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
  PRIMARY KEY (`id_cita`),
  KEY `id_cliente` (`id_cliente`),
  KEY `id_peluquero` (`id_peluquero`),
  KEY `id_estilo` (`id_estilo`),
  CONSTRAINT `citas_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_clientes`),
  CONSTRAINT `citas_ibfk_2` FOREIGN KEY (`id_peluquero`) REFERENCES `peluqueros` (`id_Peluquero`),
  CONSTRAINT `citas_ibfk_3` FOREIGN KEY (`id_estilo`) REFERENCES `estilos_corte` (`id_estilos`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `citas`
--

LOCK TABLES `citas` WRITE;
/*!40000 ALTER TABLE `citas` DISABLE KEYS */;
/*!40000 ALTER TABLE `citas` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`id_clientes`),
  KEY `id_users` (`id_users`),
  CONSTRAINT `clientes_ibfk_1` FOREIGN KEY (`id_users`) REFERENCES `users` (`idusers`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,2,'ana zuleica','8099088099','Santo Domingo',NULL,1,'2025-08-09 14:17:54'),(2,4,'prueba3','1234567890','Santo Domingo',NULL,1,'2025-08-11 09:15:14');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `disponibilidad_pel`
--

LOCK TABLES `disponibilidad_pel` WRITE;
/*!40000 ALTER TABLE `disponibilidad_pel` DISABLE KEYS */;
/*!40000 ALTER TABLE `disponibilidad_pel` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estilos_corte`
--

LOCK TABLES `estilos_corte` WRITE;
/*!40000 ALTER TABLE `estilos_corte` DISABLE KEYS */;
/*!40000 ALTER TABLE `estilos_corte` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `galeria_trabajos`
--

LOCK TABLES `galeria_trabajos` WRITE;
/*!40000 ALTER TABLE `galeria_trabajos` DISABLE KEYS */;
/*!40000 ALTER TABLE `galeria_trabajos` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `metodo_pago`
--

LOCK TABLES `metodo_pago` WRITE;
/*!40000 ALTER TABLE `metodo_pago` DISABLE KEYS */;
/*!40000 ALTER TABLE `metodo_pago` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `peluqueros`
--

LOCK TABLES `peluqueros` WRITE;
/*!40000 ALTER TABLE `peluqueros` DISABLE KEYS */;
INSERT INTO `peluqueros` VALUES (1,3,'juan perez','Santo Domingo','8099876543',5,'Nombre por defecto',NULL,NULL,NULL),(2,5,'prueba4','Santiago','1234567890',3,'prueba4',NULL,NULL,NULL);
/*!40000 ALTER TABLE `peluqueros` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `transacciones`
--

LOCK TABLES `transacciones` WRITE;
/*!40000 ALTER TABLE `transacciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `transacciones` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin@barbershop.com','admin123','Admin','2025-08-08 21:21:48'),(2,'prueba1@gmail.com','$2a$10$5mPVGvMTXY8TwiWPOEyASukuItFjrJbMRx7NTUJ62DKwZMhqEWnHG','cliente','2025-08-09 18:17:54'),(3,'prueba2@gmail.com','$2a$10$sYJLU7yUiT9XMM2.R3P8U.GDmMpyEflFt6dJIfY8TnNur4cskmM0.','peluquero','2025-08-11 06:38:38'),(4,'prueba3@gmail.com','$2a$10$VEVUUB2eFU.arFoAXkNnMOXLrlTrbU2Urhql2zf83BCsoSjEyBt/G','cliente','2025-08-11 13:15:14'),(5,'prueba4@gmail.com','$2a$10$drb/XLsdr1s0n64jkN2Ge.62vbeJQc/APjZITb9DjLIUMz/6USygu','peluquero','2025-08-11 13:23:40');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping routines for database 'barberia_system'
--
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_popularidad_estilo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizar_popularidad_estilo`(
    in p_id_cita int)
begin
    declare v_id_estilo int;
    declare v_estado varchar(20);
    
    # verificar que la cita existe y está realizada
    select id_estilo, estado into v_id_estilo, v_estado
    from citas
    where id_cita = p_id_cita;
    
    if v_id_estilo is null then
        select 'error: la cita no existe.' as mensaje;
    elseif v_estado != 'realizado' then
        select 'error: solo se puede actualizar popularidad de citas realizadas.' as mensaje;
    else
        # actualizar popularidad con transacción
        start transaction;
        
        update estilos_corte 
        set popularidad = popularidad + 1 
        where id_estilos = v_id_estilo;
        
        # registrar en galería de trabajos si no existe
        if not exists (select 1 from galeria_trabajos where id_cita = p_id_cita) then
            insert into galeria_trabajos (id_cita, notas)
            values (p_id_cita, 'popularidad actualizada automáticamente');
        end if;
        
        commit;
        
        select concat('popularidad actualizada para el estilo id ', v_id_estilo) as resultado;
    end if;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_agendar_cita` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_agendar_cita`(
	in p_id_cliente int,
    in p_id_peluquero int,
    in p_id_estilo int,
    in p_fecha_cita datetime,
    in p_notas text,
    in p_metodo_pago varchar(20)
)
begin
	declare v_dia_semana varchar(10);
    declare v_hora_inicio  time;
    declare v_hora_fin  time;
    declare v_tiempo_estimado int;
    declare v_fecha_fin datetime;
    
    #obtener dia de la semana
    set v_dia_semana = upper(dayname(p_fecha_cita));
    
select 
    hora_inicio, hora_fin
into v_hora_inicio , v_hora_fin from
    disponibilidad_pel
where
    id_peluquero = p_id_peluquero
        and dia_semana = v_dia_semana;
    
select 
    tiempo_estimado
into v_tiempo_estimado from
    estilos_corte
where
    id_estilos = p_id_estilo;
	set v_fecha_fin = DATE_ADD(p_fecha_cita, interval v_tiempo_estimado minute);
    
    
    If v_hora_inicio is null then
		select 'Error: El peluquero no trabaja este dia.' as mensaje;
        elseif time(p_fecha_cita) < v_hora_inicio or time(v_fecha_fin) > v_hora_fin then
        select CONCAT('Error: El peluquero solo trabaja de ', v_hora_inicio, ' a ', v_hora_fin, ' este día.') AS mensaje;
	else
		#verificar solapamiento de horarios
        if not exists (
				select 1 from citas
				where id_peluquero = p_id_peluquero
				and fecha_cita = p_fecha_cita
				and estado != 'CANCELADO'
				and (
					(p_fecha_cita between fecha_cita and DATE_ADD(fecha_cita, interval (select tiempo_estimado from estilos_corte where id_estilos = citas.id_estilo) minute))
					OR
					(v_fecha_fin between fecha_cita and DATE_ADD(fecha_cita, interval (select tiempo_estimado from estilos_corte where id_estilos = citas.id_estilo) minute))
				)
			) then
				#Insertar la cita
				insert into citas(id_cliente, id_peluquero, id_estilo, fecha_cita, metodo_pago, notas)
				values(p_id_cliente, p_id_peluquero, p_id_estilo, p_fecha_cita, p_metodo_pago, p_notas);
	select 
		'Cita agendada exitosamente' as mensaje,
		last_insert_id() as id_cita;
				
			  else select 'Error: El peluquero ya tiene una cita en ese horario' as mensaje;  
		end if;
    end if;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_cancelar_cita` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_cancelar_cita`(
in p_id_cita int,
in p_motivo text,
in p_id_usuario  int
)
begin
	declare v_estado_actual varchar(20);
    declare v_id_cliente int;
    declare v_tipo_usuario varchar(20);
    declare v_fecha_cita datetime;
    declare v_horas_anticipacion int;
    declare v_penalidad_aplicable boolean default false;
    declare v_mensaje_penalidad varchar(255) default '';
    declare v_id_estilo int;
    declare v_precio_estilo decimal(10,2);
    declare v_monto_penalidad decimal(10,2) default 0;
    
select 
    c.estado, c.id_cliente, c.fecha_cita, c.id_estilo, ec.precio
into v_estado_actual , v_id_cliente , v_fecha_cita , v_id_estilo , v_precio_estilo from
    citas c
        join
    estilos_corte ec ON c.id_estilo = ec.id_estilos
where
    c.id_cita = p_id_cita;
    
select 
    tipo
into v_tipo_usuario from
    users
where
    idusers = p_id_usuario;
    
    #validaciones
    if v_estado_actual is null then
        select 'error: la cita no existe.' as mensaje;
    elseif v_estado_actual = 'CANCELADO' then
        select 'error: la cita ya está cancelada.' as mensaje;
    elseif v_estado_actual = 'REALIZADO' then
        select 'error: no se puede cancelar una cita ya realizada.' as mensaje;
    elseif v_tipo_usuario = 'cliente' and 
          (select id_users from clientes where id_clientes = v_id_cliente) != p_id_usuario then
        select 'error: solo el cliente de esta cita o un peluquero pueden cancelarla.' as mensaje;
    else
        # calcular horas de anticipación (redondeadas)
        set v_horas_anticipacion = timestampdiff(hour, now(), v_fecha_cita);
        
        # determinar si aplica penalidad (menos de 24 horas de anticipación)
        if v_horas_anticipacion < 24 then
            set v_penalidad_aplicable = true;
            set v_monto_penalidad = v_precio_estilo * 0.10; -- 10% del valor del servicio
            set v_mensaje_penalidad = concat(
                ' atención: cancelación con menos de 24 horas de anticipación. ',
                'se aplicará una penalidad del 10% (', format(v_monto_penalidad, 2), '). '
            );
            # registrar transaccion de penalidad (si existe metodo de pago)
            if exists (select 1 from citas where id_cita = p_id_cita and metodo_pago is not null) then
                insert into transacciones (
                    id_cita, 
                    id_metodo, 
                    monto, 
                    comision, 
                    referencia
                ) values (
                    p_id_cita,
                    (select id_metodo from metodo_pago where nombre = 'penalidad' limit 1),
                    v_monto_penalidad,
                    0,
                    concat('penalidad-cancelacion-', p_id_cita)
                );
                
                set v_mensaje_penalidad = concat(v_mensaje_penalidad,
                    'este monto será cargado a tu método de pago registrado.');
            else
                set v_mensaje_penalidad = concat(v_mensaje_penalidad,
                    'deberás pagar esta penalidad en tu próxima visita.');
            end if;
        end if;
        
 update citas 
set 
    estado = 'cancelado',
    notas = concat('cancelación (',
            now(),
            '): ',
            p_motivo,
            if(v_penalidad_aplicable,
                concat(' [cancelación urgente - penalidad: ',
                        format(v_monto_penalidad, 2),
                        ']'),
                ''),
            ' | ',
            coalesce(notas, ''))
where
    id_cita = p_id_cita;
    # mensaje de confirmación con advertencia de penalidad si aplica
        select concat(
            'cita cancelada correctamente. ', 
            v_mensaje_penalidad,
            if(v_penalidad_aplicable, 
               ' hemos registrado una deuda por penalidad que deberá ser saldada para futuras citas.', 
               '')
        ) as mensaje;
    end if;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_obtener_horarios_disponibles` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_obtener_horarios_disponibles`(
    in p_id_peluquero int,
    in p_fecha date)
begin
    declare v_dia_semana varchar(10);
    declare v_hora_inicio time;
    declare v_hora_fin time;
    
    # obtener día de la semana
    set v_dia_semana = upper(dayname(p_fecha));
    
    # obtener horario del peluquero
    select hora_inicio, hora_fin into v_hora_inicio, v_hora_fin
    from disponibilidad_pel
    where id_peluquero = p_id_peluquero and dia_semana = v_dia_semana;
    
    if v_hora_inicio is null then
        select 'el peluquero no trabaja este día.' as mensaje;
    else
        # mostrar horarios disponibles (excluyendo citas existentes)
        select 
            time_format(v_hora_inicio, '%h:%i') as hora_inicio_jornada,
            time_format(v_hora_fin, '%h:%i') as hora_fin_jornada,
            time_format(date_add(time(p_fecha), interval 30 minute), '%h:%i') as intervalo_recomendado,
            (
                select group_concat(
                    time_format(fecha_cita, '%h:%i'), ' - ', 
                    time_format(date_add(fecha_cita, interval tiempo_estimado minute), '%h:%i'))
                from citas
                join estilos_corte on citas.id_estilo = estilos_corte.id_estilos
                where id_peluquero = p_id_peluquero
                and date(fecha_cita) = p_fecha
                and estado != 'cancelado'
            ) as horarios_ocupados;
    end if;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_registrar_pago` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_registrar_pago`(
    in p_id_cita int,
    in p_id_metodo int,
    in p_monto decimal(10,2),
    in p_comision decimal(5,2),
    in p_referencia varchar(50))
begin
    declare v_estado_cita varchar(20);
    declare v_precio_estilo decimal(10,2);
    
    # verificar estado de la cita
    select estado into v_estado_cita
    from citas
    where id_cita = p_id_cita;
    
    # obtener precio del estilo
    select precio into v_precio_estilo
    from estilos_corte
    where id_estilos = (select id_estilo from citas where id_cita = p_id_cita);
    
    if v_estado_cita is null then
        select 'error: la cita no existe.' as mensaje;
    elseif v_estado_cita = 'cancelado' then
        select 'error: no se puede pagar una cita cancelada.' as mensaje;
    elseif p_monto < v_precio_estilo then
        select concat('error: el monto (', p_monto, ') es menor que el precio del estilo (', v_precio_estilo, ').') as mensaje;
    else
        # registrar transacción
        insert into transacciones (id_cita, id_metodo, monto, comision, referencia)
        values (p_id_cita, p_id_metodo, p_monto, p_comision, p_referencia);
        
        # actualizar estado de la cita si es necesario
        if v_estado_cita = 'pendiente' then
            update citas set estado = 'realizado' where id_cita = p_id_cita;
        end if;
        
        select 'pago registrado correctamente.' as mensaje, last_insert_id() as id_transaccion;
    end if;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

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

-- Dump completed on 2025-08-12 14:05:44
