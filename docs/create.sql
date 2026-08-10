CREATE DATABASE  IF NOT EXISTS `owle_dev` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `owle_dev`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: owle_dev
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
-- Table structure for table `benchmark_reports`
--

DROP TABLE IF EXISTS `benchmark_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `benchmark_reports` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `workflow_run_id` bigint unsigned NOT NULL,
  `execution_time` decimal(10,3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `workflowExecutionId` (`workflow_run_id`),
  CONSTRAINT `benchmark_reports_ibfk_1` FOREIGN KEY (`workflow_run_id`) REFERENCES `workflow_executions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `callable_owners`
--

DROP TABLE IF EXISTS `callable_owners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `callable_owners` (
  `owner_node_id` bigint unsigned NOT NULL,
  `callable_node_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`owner_node_id`,`callable_node_id`),
  KEY `callable_node_id` (`callable_node_id`),
  CONSTRAINT `callable_owners_ibfk_1` FOREIGN KEY (`owner_node_id`) REFERENCES `type_nodes` (`id`),
  CONSTRAINT `callable_owners_ibfk_2` FOREIGN KEY (`callable_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `callable_parameters`
--

DROP TABLE IF EXISTS `callable_parameters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `callable_parameters` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `signature_id` bigint unsigned NOT NULL,
  `position` int unsigned NOT NULL,
  `param_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `param_type_node_id` bigint unsigned NOT NULL,
  `is_vararg` tinyint(1) NOT NULL DEFAULT '0',
  `is_kwarg` tinyint(1) NOT NULL DEFAULT '0',
  `default_value_text` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `signature_id` (`signature_id`,`position`),
  KEY `param_type_node_id` (`param_type_node_id`),
  CONSTRAINT `callable_parameters_ibfk_1` FOREIGN KEY (`signature_id`) REFERENCES `callable_signatures` (`id`),
  CONSTRAINT `callable_parameters_ibfk_2` FOREIGN KEY (`param_type_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `callable_signatures`
--

DROP TABLE IF EXISTS `callable_signatures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `callable_signatures` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `type_node_id` bigint unsigned NOT NULL,
  `return_type_node_id` bigint unsigned NOT NULL,
  `docstring` varchar(8192) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_static` tinyint(1) NOT NULL DEFAULT '0',
  `is_classmethod` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_callable_signature_type_node` (`type_node_id`),
  KEY `fk_return_type_node` (`return_type_node_id`),
  CONSTRAINT `fk_return_type_node` FOREIGN KEY (`return_type_node_id`) REFERENCES `type_nodes` (`id`),
  CONSTRAINT `fk_signature_type_node` FOREIGN KEY (`type_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_point_injections`
--

DROP TABLE IF EXISTS `data_point_injections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data_point_injections` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `junction_id` bigint unsigned NOT NULL,
  `src_data_point_id` bigint unsigned NOT NULL,
  `target_data_point_id` bigint unsigned NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(8192) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `junctionId` (`junction_id`),
  KEY `sourceDataPointId` (`src_data_point_id`),
  KEY `injectedDataPointId` (`target_data_point_id`),
  CONSTRAINT `data_point_injections_ibfk_1` FOREIGN KEY (`junction_id`) REFERENCES `workflow_junctions` (`id`),
  CONSTRAINT `data_point_injections_ibfk_2` FOREIGN KEY (`src_data_point_id`) REFERENCES `data_points` (`id`),
  CONSTRAINT `data_point_injections_ibfk_3` FOREIGN KEY (`target_data_point_id`) REFERENCES `data_points` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_point_reads`
--

DROP TABLE IF EXISTS `data_point_reads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data_point_reads` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `step_id` bigint unsigned NOT NULL,
  `tool_param_id` bigint unsigned NOT NULL,
  `transform_occurrence_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `step_id` (`step_id`),
  KEY `tool_param_id` (`tool_param_id`),
  KEY `data_point_reads_transform_occurrences` (`transform_occurrence_id`),
  CONSTRAINT `data_point_reads_ibfk_1` FOREIGN KEY (`step_id`) REFERENCES `steps` (`id`) ON DELETE CASCADE,
  CONSTRAINT `data_point_reads_ibfk_2` FOREIGN KEY (`tool_param_id`) REFERENCES `tool_params` (`id`) ON DELETE CASCADE,
  CONSTRAINT `data_point_reads_transform_occurrences` FOREIGN KEY (`transform_occurrence_id`) REFERENCES `transform_occurrences` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_point_write_args`
--

DROP TABLE IF EXISTS `data_point_write_args`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data_point_write_args` (
  `revision_id` bigint unsigned NOT NULL,
  `data_point_id` bigint unsigned NOT NULL,
  `position` int unsigned NOT NULL,
  PRIMARY KEY (`revision_id`,`position`),
  KEY `ra_fk_datapoint` (`data_point_id`),
  CONSTRAINT `fk_revision_args_rev` FOREIGN KEY (`revision_id`) REFERENCES `data_point_writes` (`id`) ON DELETE CASCADE,
  CONSTRAINT `ra_fk_datapoint` FOREIGN KEY (`data_point_id`) REFERENCES `data_points` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_point_writes`
--

DROP TABLE IF EXISTS `data_point_writes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data_point_writes` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `data_point_id` bigint unsigned NOT NULL,
  `step_id` bigint unsigned NOT NULL,
  `transform_occurrence_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dataPointId` (`data_point_id`),
  KEY `stepId` (`step_id`),
  KEY `data_point_writes_transform_occurrences` (`transform_occurrence_id`),
  CONSTRAINT `data_point_writes_ibfk_1` FOREIGN KEY (`data_point_id`) REFERENCES `data_points` (`id`) ON DELETE CASCADE,
  CONSTRAINT `data_point_writes_ibfk_2` FOREIGN KEY (`step_id`) REFERENCES `steps` (`id`) ON DELETE CASCADE,
  CONSTRAINT `data_point_writes_transform_occurrences` FOREIGN KEY (`transform_occurrence_id`) REFERENCES `transform_occurrences` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_points`
--

DROP TABLE IF EXISTS `data_points`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data_points` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `workflow_id` bigint unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(8192) DEFAULT NULL,
  `is_prerequisite` tinyint DEFAULT NULL,
  `preset_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `store_method` enum('text','blob','file') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `type_node_id` bigint unsigned NOT NULL,
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `workflowId` (`workflow_id`),
  KEY `fk_data_points_type_node` (`type_node_id`),
  CONSTRAINT `data_points_ibfk_1` FOREIGN KEY (`workflow_id`) REFERENCES `workflows` (`id`),
  CONSTRAINT `fk_data_points_type_node` FOREIGN KEY (`type_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `distributions`
--

DROP TABLE IF EXISTS `distributions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `distributions` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `env_marker_comparisons`
--

DROP TABLE IF EXISTS `env_marker_comparisons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `env_marker_comparisons` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `expression_id` bigint unsigned NOT NULL,
  `variable` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `operator` enum('==','~=','!=','<','<=','>','>=','in','not in') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `value` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `expressionId` (`expression_id`),
  CONSTRAINT `env_marker_comparisons_env_marker_expressions_fk` FOREIGN KEY (`expression_id`) REFERENCES `env_marker_expressions` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30237 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `env_marker_expressions`
--

DROP TABLE IF EXISTS `env_marker_expressions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `env_marker_expressions` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `requirement_id` bigint unsigned NOT NULL,
  `parent_id` bigint unsigned DEFAULT NULL,
  `mode` enum('COMPARATIVE','LOGICAL') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `requirementId` (`requirement_id`),
  KEY `parentId` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34675 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `env_marker_logicals`
--

DROP TABLE IF EXISTS `env_marker_logicals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `env_marker_logicals` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `expression_id` bigint unsigned NOT NULL,
  `operator` enum('AND','OR') NOT NULL,
  `left_child_id` bigint unsigned NOT NULL,
  `right_child_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `expressionId` (`expression_id`),
  KEY `leftChildId` (`left_child_id`),
  KEY `rightChildId` (`right_child_id`),
  CONSTRAINT `env_marker_logicals_env_marker_expressions_fk` FOREIGN KEY (`expression_id`) REFERENCES `env_marker_expressions` (`id`) ON DELETE CASCADE,
  CONSTRAINT `env_marker_logicals_env_marker_expressions_fk_1` FOREIGN KEY (`left_child_id`) REFERENCES `env_marker_expressions` (`id`),
  CONSTRAINT `env_marker_logicals_env_marker_expressions_fk_2` FOREIGN KEY (`right_child_id`) REFERENCES `env_marker_expressions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4430 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `error_watch_fns`
--

DROP TABLE IF EXISTS `error_watch_fns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `error_watch_fns` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `error_watch_id` bigint unsigned NOT NULL,
  `function_node_id` bigint unsigned NOT NULL,
  `position` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `errorWatchId` (`error_watch_id`,`position`),
  UNIQUE KEY `errorWatchId_2` (`error_watch_id`,`function_node_id`,`position`),
  KEY `fk_error_watch_fns_fn_node` (`function_node_id`),
  CONSTRAINT `error_watch_fns_ibfk_1` FOREIGN KEY (`error_watch_id`) REFERENCES `error_watches` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_error_watch_fns_fn_node` FOREIGN KEY (`function_node_id`) REFERENCES `type_nodes` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `error_watches`
--

DROP TABLE IF EXISTS `error_watches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `error_watches` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `step_id` bigint unsigned NOT NULL,
  `error_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `trigger_step_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `stepId` (`step_id`),
  KEY `onMetStepId` (`trigger_step_id`),
  CONSTRAINT `error_watches_ibfk_1` FOREIGN KEY (`step_id`) REFERENCES `steps` (`id`),
  CONSTRAINT `error_watches_ibfk_2` FOREIGN KEY (`trigger_step_id`) REFERENCES `steps` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inquisition_args`
--

DROP TABLE IF EXISTS `inquisition_args`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inquisition_args` (
  `inquisition_id` bigint unsigned NOT NULL,
  `data_point_id` bigint unsigned NOT NULL,
  `position` int unsigned NOT NULL,
  PRIMARY KEY (`inquisition_id`,`position`),
  KEY `fk_inquisition_args_dp` (`data_point_id`),
  CONSTRAINT `fk_inquisition_args_dp` FOREIGN KEY (`data_point_id`) REFERENCES `data_points` (`id`),
  CONSTRAINT `fk_inquisition_args_inq` FOREIGN KEY (`inquisition_id`) REFERENCES `inquisitions` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inquisitions`
--

DROP TABLE IF EXISTS `inquisitions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inquisitions` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `step_id` bigint unsigned NOT NULL,
  `note` varchar(8192) DEFAULT NULL,
  `data_point_id` bigint unsigned NOT NULL,
  `trigger_step_id` bigint unsigned NOT NULL,
  `execution_order` bigint unsigned NOT NULL,
  `signature_id` bigint unsigned DEFAULT NULL,
  `body` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `stepId` (`step_id`),
  KEY `dataPointId` (`data_point_id`),
  KEY `onMetStepId` (`trigger_step_id`),
  KEY `fk_inquisition_signature` (`signature_id`),
  CONSTRAINT `fk_inquisition_signature` FOREIGN KEY (`signature_id`) REFERENCES `callable_signatures` (`id`),
  CONSTRAINT `fk_inquisitions_signature` FOREIGN KEY (`signature_id`) REFERENCES `callable_signatures` (`id`),
  CONSTRAINT `inquisitions_ibfk_1` FOREIGN KEY (`step_id`) REFERENCES `steps` (`id`),
  CONSTRAINT `inquisitions_ibfk_2` FOREIGN KEY (`data_point_id`) REFERENCES `data_points` (`id`),
  CONSTRAINT `inquisitions_ibfk_3` FOREIGN KEY (`trigger_step_id`) REFERENCES `steps` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `metadata`
--

DROP TABLE IF EXISTS `metadata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `metadata` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `workflow_id` bigint unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `version` varchar(20) NOT NULL DEFAULT '1.0.0',
  `copyright_information` text,
  `contact_information` text,
  `third_party_licenses` text,
  `discretion_advised_content` tinyint(1) NOT NULL DEFAULT '0',
  `commercial_usage` enum('PERSONAL_NON_PROFIT','PERSONAL_PROFIT','CORPORATION') NOT NULL DEFAULT 'PERSONAL_NON_PROFIT',
  `credit_notation` enum('REQUIRED','UNNECESSARY') NOT NULL DEFAULT 'REQUIRED',
  `allow_redistribution` tinyint(1) NOT NULL DEFAULT '0',
  `modification` enum('PROHIBITED','ALLOW_MODIFICATION','ALLOW_MODIFICATION_REDISTRIBUTION') NOT NULL DEFAULT 'PROHIBITED',
  `distribution_version` varchar(255) NOT NULL DEFAULT '1.0.0',
  `distribution_accepted_at` datetime NOT NULL,
  `distribution_updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `workflow_id` (`workflow_id`),
  CONSTRAINT `metadata_ibfk_1` FOREIGN KEY (`workflow_id`) REFERENCES `workflows` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `metadata_step_exclusions`
--

DROP TABLE IF EXISTS `metadata_step_exclusions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `metadata_step_exclusions` (
  `metadata_id` bigint unsigned NOT NULL,
  `excluded_step_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`metadata_id`,`excluded_step_id`),
  KEY `excluded_step_id` (`excluded_step_id`),
  CONSTRAINT `metadata_step_exclusions_ibfk_1` FOREIGN KEY (`metadata_id`) REFERENCES `metadata` (`id`) ON DELETE CASCADE,
  CONSTRAINT `metadata_step_exclusions_ibfk_2` FOREIGN KEY (`excluded_step_id`) REFERENCES `steps` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `package_dist_requirements`
--

DROP TABLE IF EXISTS `package_dist_requirements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `package_dist_requirements` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `dependent_dist` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dependent_package_id` bigint unsigned NOT NULL,
  `required_dist` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `required_package_id` bigint unsigned DEFAULT NULL,
  `required_extra_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `operator` enum('LT','LE','NE','EQ','GE','GT','COMPATIBLE','EXACT') NOT NULL,
  `wildcard_segment` enum('MAJOR','MINOR','MICRO','NONE') NOT NULL,
  `raw` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dependentDist` (`dependent_dist`,`dependent_package_id`),
  KEY `targetDistribution` (`required_dist`),
  KEY `targetVersionId` (`required_package_id`),
  KEY `fk_markers_extras` (`dependent_dist`,`dependent_package_id`,`required_extra_name`),
  KEY `fk_markers_extra` (`required_dist`,`required_package_id`,`required_extra_name`),
  CONSTRAINT `fk_required_package` FOREIGN KEY (`required_dist`, `required_package_id`) REFERENCES `packages` (`distribution`, `version_id`),
  CONSTRAINT `package_dist_requirements_ibfk_1` FOREIGN KEY (`dependent_dist`, `dependent_package_id`) REFERENCES `packages` (`distribution`, `version_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1026409 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `package_python_requirements`
--

DROP TABLE IF EXISTS `package_python_requirements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `package_python_requirements` (
  `distribution` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `package_id` bigint unsigned NOT NULL,
  `version_id` bigint unsigned NOT NULL,
  `operator` enum('LT','LE','NE','EQ','GE','GT','COMPATIBLE','EXACT') NOT NULL,
  `wildcard` enum('NONE','MAJOR','MINOR','MICRO') NOT NULL DEFAULT 'NONE',
  PRIMARY KEY (`package_id`,`distribution`),
  KEY `ppr_fk_spec` (`version_id`,`operator`,`wildcard`),
  CONSTRAINT `ppr_fk_pkg` FOREIGN KEY (`package_id`, `distribution`) REFERENCES `packages` (`version_id`, `distribution`) ON DELETE CASCADE,
  CONSTRAINT `ppr_fk_spec` FOREIGN KEY (`version_id`, `operator`, `wildcard`) REFERENCES `python_version_specifiers` (`version_id`, `operator`, `wildcard`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `packages`
--

DROP TABLE IF EXISTS `packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packages` (
  `version_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `distribution` varchar(255) NOT NULL,
  `version_str` varchar(255) NOT NULL,
  `summary` varchar(4096) DEFAULT NULL,
  `keywords` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `description` text,
  `description_content_type` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `license` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `home_page` varchar(255) DEFAULT NULL,
  `project_url` varchar(255) DEFAULT NULL,
  `download_url` varchar(255) DEFAULT NULL,
  `docs_url` varchar(255) DEFAULT NULL,
  `yanked` tinyint DEFAULT NULL,
  `last_serial` int unsigned DEFAULT NULL,
  `info` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  PRIMARY KEY (`version_id`,`distribution`),
  KEY `packages_ibfk_2` (`distribution`),
  CONSTRAINT `packages_ibfk_1` FOREIGN KEY (`version_id`) REFERENCES `versions` (`id`),
  CONSTRAINT `packages_ibfk_2` FOREIGN KEY (`distribution`) REFERENCES `distributions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4347077 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_methods`
--

DROP TABLE IF EXISTS `payment_methods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_methods` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `stripe_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `user_id` bigint unsigned NOT NULL,
  `card_brand` enum('American Express','Diners Club','Discover','Eftpos Australia','Girocard','JCB','MasterCard','UnionPay','Visa','Unknown') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `last_four` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `is_default` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `userId` (`user_id`),
  CONSTRAINT `payment_methods_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `phase_relations`
--

DROP TABLE IF EXISTS `phase_relations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phase_relations` (
  `parent_id` bigint unsigned NOT NULL,
  `child_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`parent_id`,`child_id`),
  KEY `child_id` (`child_id`),
  CONSTRAINT `phase_relations_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `phases` (`id`),
  CONSTRAINT `phase_relations_ibfk_2` FOREIGN KEY (`child_id`) REFERENCES `phases` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `phases`
--

DROP TABLE IF EXISTS `phases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phases` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `workflow_id` bigint unsigned NOT NULL,
  `name` varchar(1024) NOT NULL,
  `description` varchar(8192) DEFAULT NULL,
  `display_order` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `workflowId` (`workflow_id`),
  CONSTRAINT `phases_ibfk_2` FOREIGN KEY (`workflow_id`) REFERENCES `workflows` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `provision_data_points`
--

DROP TABLE IF EXISTS `provision_data_points`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `provision_data_points` (
  `provision_id` bigint unsigned NOT NULL,
  `data_point_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`provision_id`,`data_point_id`),
  KEY `pdp_fk_datapoint` (`data_point_id`),
  CONSTRAINT `pdp_fk_datapoint` FOREIGN KEY (`data_point_id`) REFERENCES `data_points` (`id`) ON DELETE CASCADE,
  CONSTRAINT `pdp_fk_provision` FOREIGN KEY (`provision_id`) REFERENCES `data_point_reads` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `python_version_specifiers`
--

DROP TABLE IF EXISTS `python_version_specifiers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `python_version_specifiers` (
  `version_id` bigint unsigned NOT NULL,
  `operator` enum('LT','LE','NE','EQ','GE','GT','COMPATIBLE','EXACT') NOT NULL,
  `wildcard` enum('NONE','MAJOR','MINOR','MICRO') NOT NULL DEFAULT 'NONE',
  PRIMARY KEY (`version_id`,`operator`,`wildcard`),
  UNIQUE KEY `uniq_pvs` (`version_id`,`operator`,`wildcard`),
  CONSTRAINT `pvs_fk_version` FOREIGN KEY (`version_id`) REFERENCES `python_versions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `python_versions`
--

DROP TABLE IF EXISTS `python_versions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `python_versions` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `is_supported` tinyint DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `python_versions_ibfk_1` FOREIGN KEY (`id`) REFERENCES `versions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4344488 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `step_default_routes`
--

DROP TABLE IF EXISTS `step_default_routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `step_default_routes` (
  `step_id` bigint unsigned NOT NULL,
  `next_step_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`step_id`),
  KEY `next_step_id` (`next_step_id`),
  CONSTRAINT `step_default_routes_ibfk_1` FOREIGN KEY (`step_id`) REFERENCES `steps` (`id`),
  CONSTRAINT `step_default_routes_ibfk_2` FOREIGN KEY (`next_step_id`) REFERENCES `steps` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `steps`
--

DROP TABLE IF EXISTS `steps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `steps` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `workflow_id` bigint unsigned NOT NULL,
  `parent_phase_id` bigint unsigned DEFAULT NULL,
  `tool_id` bigint unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(8192) DEFAULT NULL,
  `is_workflow_head` tinyint(1) NOT NULL DEFAULT '0',
  `display_order` int unsigned NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `parentPhaseId` (`parent_phase_id`),
  KEY `workflowId` (`workflow_id`),
  KEY `toolId` (`tool_id`),
  CONSTRAINT `steps_ibfk_1` FOREIGN KEY (`parent_phase_id`) REFERENCES `phases` (`id`),
  CONSTRAINT `steps_ibfk_2` FOREIGN KEY (`workflow_id`) REFERENCES `workflows` (`id`),
  CONSTRAINT `steps_ibfk_3` FOREIGN KEY (`tool_id`) REFERENCES `tools` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag_relations`
--

DROP TABLE IF EXISTS `tag_relations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag_relations` (
  `parent_tag_id` bigint unsigned NOT NULL,
  `child_tag_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`parent_tag_id`,`child_tag_id`),
  KEY `childTagId` (`child_tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tagged_distributions`
--

DROP TABLE IF EXISTS `tagged_distributions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tagged_distributions` (
  `distribution` varchar(255) NOT NULL,
  `tag_id` bigint unsigned NOT NULL,
  KEY `tagged_distributions_distributions_FK` (`distribution`),
  KEY `tagged_distributions_tags_FK` (`tag_id`),
  CONSTRAINT `tagged_distributions_distributions_FK` FOREIGN KEY (`distribution`) REFERENCES `distributions` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tagged_distributions_tags_FK` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tagged_packages`
--

DROP TABLE IF EXISTS `tagged_packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tagged_packages` (
  `distribution` varchar(255) NOT NULL,
  `package_id` bigint unsigned NOT NULL,
  `tag_id` bigint unsigned NOT NULL,
  KEY `distribution` (`distribution`,`package_id`),
  KEY `tagId` (`tag_id`),
  CONSTRAINT `tagged_packages_ibfk_1` FOREIGN KEY (`distribution`, `package_id`) REFERENCES `packages` (`distribution`, `version_id`),
  CONSTRAINT `tagged_packages_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tagged_phases`
--

DROP TABLE IF EXISTS `tagged_phases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tagged_phases` (
  `phase_id` bigint unsigned NOT NULL,
  `tag_id` bigint unsigned NOT NULL,
  KEY `phaseId` (`phase_id`),
  KEY `tagId` (`tag_id`),
  CONSTRAINT `tagged_phases_ibfk_1` FOREIGN KEY (`phase_id`) REFERENCES `phases` (`id`),
  CONSTRAINT `tagged_phases_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tagged_steps`
--

DROP TABLE IF EXISTS `tagged_steps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tagged_steps` (
  `step_id` bigint unsigned NOT NULL,
  `tag_id` bigint unsigned NOT NULL,
  KEY `stepId` (`step_id`),
  KEY `tagId` (`tag_id`),
  CONSTRAINT `tagged_steps_ibfk_1` FOREIGN KEY (`step_id`) REFERENCES `steps` (`id`),
  CONSTRAINT `tagged_steps_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tagged_tools`
--

DROP TABLE IF EXISTS `tagged_tools`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tagged_tools` (
  `tool_id` bigint unsigned NOT NULL,
  `tag_id` bigint unsigned NOT NULL,
  KEY `toolId` (`tool_id`),
  KEY `tagId` (`tag_id`),
  CONSTRAINT `tagged_tools_ibfk_1` FOREIGN KEY (`tool_id`) REFERENCES `tools` (`id`),
  CONSTRAINT `tagged_tools_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tagged_transforms`
--

DROP TABLE IF EXISTS `tagged_transforms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tagged_transforms` (
  `transform_id` bigint unsigned NOT NULL,
  `tag_id` bigint unsigned NOT NULL,
  KEY `transformId` (`transform_id`),
  KEY `tagId` (`tag_id`),
  CONSTRAINT `tagged_transforms_ibfk_1` FOREIGN KEY (`transform_id`) REFERENCES `transforms` (`id`),
  CONSTRAINT `tagged_transforms_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tagged_workflow_folders`
--

DROP TABLE IF EXISTS `tagged_workflow_folders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tagged_workflow_folders` (
  `workflow_folder_id` bigint unsigned NOT NULL,
  `tag_id` bigint unsigned NOT NULL,
  KEY `workflowFolderId` (`workflow_folder_id`),
  KEY `tagId` (`tag_id`),
  CONSTRAINT `tagged_workflow_folders_ibfk_1` FOREIGN KEY (`workflow_folder_id`) REFERENCES `workflow_folders` (`id`),
  CONSTRAINT `tagged_workflow_folders_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tagged_workflows`
--

DROP TABLE IF EXISTS `tagged_workflows`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tagged_workflows` (
  `workflow_id` bigint unsigned NOT NULL,
  `tag_id` bigint unsigned NOT NULL,
  KEY `workflowId` (`workflow_id`),
  KEY `tagId` (`tag_id`),
  CONSTRAINT `tagged_workflows_ibfk_1` FOREIGN KEY (`workflow_id`) REFERENCES `workflows` (`id`),
  CONSTRAINT `tagged_workflows_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tags` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(8192) DEFAULT NULL,
  `source` enum('system','pypi','user') DEFAULT NULL,
  `user_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`user_id`),
  CONSTRAINT `tags_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93640 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `test_cases`
--

DROP TABLE IF EXISTS `test_cases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_cases` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(8192) DEFAULT NULL,
  `workflow_id` bigint unsigned DEFAULT NULL,
  `step_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`user_id`),
  KEY `workflowId` (`workflow_id`),
  KEY `stepId` (`step_id`),
  CONSTRAINT `test_cases_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `test_cases_ibfk_2` FOREIGN KEY (`workflow_id`) REFERENCES `workflows` (`id`),
  CONSTRAINT `test_cases_ibfk_3` FOREIGN KEY (`step_id`) REFERENCES `steps` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `test_expected_outcomes`
--

DROP TABLE IF EXISTS `test_expected_outcomes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_expected_outcomes` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `test_case_id` bigint unsigned NOT NULL,
  `data_point_id` bigint unsigned NOT NULL,
  `expected_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `operator` enum('equals','notEquals','contains','greaterThan','lessThan') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `testCaseId` (`test_case_id`),
  KEY `dataPointId` (`data_point_id`),
  CONSTRAINT `test_expected_outcomes_ibfk_1` FOREIGN KEY (`test_case_id`) REFERENCES `test_cases` (`id`),
  CONSTRAINT `test_expected_outcomes_ibfk_2` FOREIGN KEY (`data_point_id`) REFERENCES `data_points` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `test_inputs`
--

DROP TABLE IF EXISTS `test_inputs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_inputs` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `test_case_id` bigint unsigned NOT NULL,
  `data_point_id` bigint unsigned NOT NULL,
  `value` text,
  PRIMARY KEY (`id`),
  KEY `testCaseId` (`test_case_id`),
  KEY `dataPointId` (`data_point_id`),
  CONSTRAINT `test_inputs_ibfk_1` FOREIGN KEY (`test_case_id`) REFERENCES `test_cases` (`id`),
  CONSTRAINT `test_inputs_ibfk_2` FOREIGN KEY (`data_point_id`) REFERENCES `data_points` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `test_results`
--

DROP TABLE IF EXISTS `test_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_results` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `test_case_id` bigint unsigned NOT NULL,
  `workflowExecutionId` bigint unsigned NOT NULL,
  `passed` tinyint(1) NOT NULL,
  `executedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `testCaseId` (`test_case_id`),
  KEY `workflowExecutionId` (`workflowExecutionId`),
  CONSTRAINT `test_results_ibfk_1` FOREIGN KEY (`test_case_id`) REFERENCES `test_cases` (`id`),
  CONSTRAINT `test_results_ibfk_2` FOREIGN KEY (`workflowExecutionId`) REFERENCES `workflow_executions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `test_step_run`
--

DROP TABLE IF EXISTS `test_step_run`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_step_run` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `step_id` bigint unsigned NOT NULL,
  `started` timestamp NULL DEFAULT NULL,
  `completed` timestamp NULL DEFAULT NULL,
  `status` enum('ready','running','completed','errored','failed','cancelled') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ready',
  PRIMARY KEY (`id`),
  KEY `stepId` (`step_id`),
  CONSTRAINT `test_step_runs_step_id_fk` FOREIGN KEY (`step_id`) REFERENCES `steps` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tool_params`
--

DROP TABLE IF EXISTS `tool_params`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tool_params` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tool_id` bigint unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `type_node_id` bigint unsigned DEFAULT NULL,
  `defaultValue` text,
  PRIMARY KEY (`id`),
  KEY `toolId` (`tool_id`),
  KEY `fk_tool_params_type_node` (`type_node_id`),
  CONSTRAINT `fk_tool_params_type_node` FOREIGN KEY (`type_node_id`) REFERENCES `type_nodes` (`id`),
  CONSTRAINT `tool_params_ibfk_1` FOREIGN KEY (`tool_id`) REFERENCES `tools` (`id`),
  CONSTRAINT `tool_params_chk_1` CHECK ((((`type_node_id` is not null) and (`defaultValue` is null)) or ((`defaultValue` is not null) and (`type_node_id` is not null))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tools`
--

DROP TABLE IF EXISTS `tools`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tools` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `signature_id` bigint unsigned DEFAULT NULL,
  `name` text NOT NULL,
  `description` varchar(8192) DEFAULT NULL,
  `body` text NOT NULL,
  `return_type_node_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tools_return_type_node` (`return_type_node_id`),
  KEY `fk_tools_signature` (`signature_id`),
  CONSTRAINT `fk_tools_return_type_node` FOREIGN KEY (`return_type_node_id`) REFERENCES `type_nodes` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_tools_signature` FOREIGN KEY (`signature_id`) REFERENCES `callable_signatures` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transform_args`
--

DROP TABLE IF EXISTS `transform_args`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transform_args` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `transform_id` bigint unsigned NOT NULL,
  `type_node_id` bigint unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(8192) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `transformationId` (`transform_id`),
  KEY `fk_transformation_args_type_node` (`type_node_id`),
  CONSTRAINT `fk_transformation_args_type_node` FOREIGN KEY (`type_node_id`) REFERENCES `type_nodes` (`id`),
  CONSTRAINT `transform_args_ibfk_1` FOREIGN KEY (`transform_id`) REFERENCES `transforms` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transform_occurence_args`
--

DROP TABLE IF EXISTS `transform_occurence_args`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transform_occurence_args` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `transform_occurrence_id` bigint unsigned NOT NULL,
  `transform_arg_id` bigint unsigned NOT NULL,
  `value` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `appliedTransformId` (`transform_occurrence_id`),
  KEY `transformationArgId` (`transform_arg_id`),
  CONSTRAINT `transform_occurence_args_ibfk_1` FOREIGN KEY (`transform_occurrence_id`) REFERENCES `transform_occurrences` (`id`),
  CONSTRAINT `transform_occurence_args_ibfk_2` FOREIGN KEY (`transform_arg_id`) REFERENCES `transform_args` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transform_occurrence_chains`
--

DROP TABLE IF EXISTS `transform_occurrence_chains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transform_occurrence_chains` (
  `antecedent_occurrence_id` bigint unsigned NOT NULL,
  `subsequent_occurrence_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`antecedent_occurrence_id`,`subsequent_occurrence_id`),
  KEY `fk_atc_descendant` (`subsequent_occurrence_id`),
  CONSTRAINT `fk_atc_ancestor` FOREIGN KEY (`antecedent_occurrence_id`) REFERENCES `transform_occurrences` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_atc_descendant` FOREIGN KEY (`subsequent_occurrence_id`) REFERENCES `transform_occurrences` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transform_occurrences`
--

DROP TABLE IF EXISTS `transform_occurrences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transform_occurrences` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `transform_id` bigint unsigned NOT NULL,
  `occurrence` enum('read','write','inquisition','inject') NOT NULL,
  `dataPointId` bigint unsigned DEFAULT NULL,
  `toolResultStepId` bigint unsigned DEFAULT NULL,
  `is_persistent` tinyint(1) GENERATED ALWAYS AS ((cast(`occurrence` as char charset utf8mb4) = _utf8mb4'revision')) STORED,
  PRIMARY KEY (`id`),
  KEY `transformationId` (`transform_id`),
  KEY `dataPointId` (`dataPointId`),
  KEY `toolResultStepId` (`toolResultStepId`),
  CONSTRAINT `transform_occurrences_ibfk_1` FOREIGN KEY (`transform_id`) REFERENCES `transforms` (`id`),
  CONSTRAINT `transform_occurrences_ibfk_3` FOREIGN KEY (`dataPointId`) REFERENCES `data_points` (`id`),
  CONSTRAINT `transform_occurrences_ibfk_4` FOREIGN KEY (`toolResultStepId`) REFERENCES `steps` (`id`),
  CONSTRAINT `chkapplied_transformsSource` CHECK ((((`dataPointId` is not null) and (`toolResultStepId` is null)) or ((`dataPointId` is null) and (`toolResultStepId` is not null))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transform_target_types`
--

DROP TABLE IF EXISTS `transform_target_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transform_target_types` (
  `transform_id` bigint unsigned NOT NULL,
  `eligible_type_node_id` bigint unsigned NOT NULL,
  `return_type_node_id` bigint unsigned NOT NULL,
  KEY `fk_ttt_eligible_type_node` (`eligible_type_node_id`),
  KEY `fk_ttt_return_type_node` (`return_type_node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transforms`
--

DROP TABLE IF EXISTS `transforms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transforms` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `examples` varchar(2046) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `signature_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_transforms_signature` (`signature_id`),
  CONSTRAINT `fk_transforms_signature` FOREIGN KEY (`signature_id`) REFERENCES `callable_signatures` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_aliases`
--

DROP TABLE IF EXISTS `type_aliases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_aliases` (
  `alias_id` bigint unsigned NOT NULL,
  `target_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`alias_id`),
  KEY `fk_alias_target` (`target_id`),
  CONSTRAINT `fk_alias_source` FOREIGN KEY (`alias_id`) REFERENCES `type_nodes` (`id`),
  CONSTRAINT `fk_alias_target` FOREIGN KEY (`target_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_generic_params`
--

DROP TABLE IF EXISTS `type_generic_params`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_generic_params` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `class_node_id` bigint unsigned NOT NULL,
  `typevar_node_id` bigint unsigned NOT NULL,
  `position` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `class_node_id` (`class_node_id`,`typevar_node_id`),
  KEY `fk_cls_gen_typevar` (`typevar_node_id`),
  CONSTRAINT `fk_cls_gen_class` FOREIGN KEY (`class_node_id`) REFERENCES `type_nodes` (`id`),
  CONSTRAINT `fk_cls_gen_typevar` FOREIGN KEY (`typevar_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18556 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_instantiation_args`
--

DROP TABLE IF EXISTS `type_instantiation_args`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_instantiation_args` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `instantiation_id` bigint unsigned NOT NULL,
  `param_node_id` bigint unsigned NOT NULL,
  `param_position` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_instantiation_param` (`instantiation_id`,`param_position`),
  KEY `fk_inst_arg_node` (`param_node_id`),
  CONSTRAINT `fk_inst_arg_inst` FOREIGN KEY (`instantiation_id`) REFERENCES `type_instantiations` (`id`),
  CONSTRAINT `fk_inst_arg_node` FOREIGN KEY (`param_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74209 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_instantiations`
--

DROP TABLE IF EXISTS `type_instantiations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_instantiations` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `instantiation_node_id` bigint unsigned NOT NULL,
  `origin_node_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_instantiation_origin` (`instantiation_node_id`,`origin_node_id`),
  KEY `fk_inst_origin` (`origin_node_id`),
  CONSTRAINT `fk_inst_node` FOREIGN KEY (`instantiation_node_id`) REFERENCES `type_nodes` (`id`),
  CONSTRAINT `fk_inst_origin` FOREIGN KEY (`origin_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=181509 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_node_relations`
--

DROP TABLE IF EXISTS `type_node_relations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_node_relations` (
  `parent_node_id` bigint unsigned NOT NULL,
  `child_node_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`child_node_id`,`parent_node_id`),
  KEY `fk_parent_node` (`parent_node_id`),
  CONSTRAINT `fk_child_node` FOREIGN KEY (`child_node_id`) REFERENCES `type_nodes` (`id`),
  CONSTRAINT `fk_parent_node` FOREIGN KEY (`parent_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_nodes`
--

DROP TABLE IF EXISTS `type_nodes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_nodes` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `version_id` bigint unsigned NOT NULL,
  `namespace` varchar(1024) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `kind` enum('base','typevar','union','instantiation','callable','alias') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_generic` tinyint(1) NOT NULL DEFAULT '0',
  `dedup_hash` char(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_dedup_hash` (`dedup_hash`),
  KEY `fk_version_constraint` (`version_id`),
  CONSTRAINT `fk_version_constraint` FOREIGN KEY (`version_id`) REFERENCES `versions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=990926 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_schema_refs`
--

DROP TABLE IF EXISTS `type_schema_refs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_schema_refs` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `parent_ref_id` bigint unsigned DEFAULT NULL,
  `class_node_id` bigint unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `member_kind` enum('attribute','method','property','classvar') NOT NULL,
  `type_node_id` bigint unsigned DEFAULT NULL,
  `description` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `position` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_parent_schema` (`parent_ref_id`),
  KEY `fk_type_node` (`type_node_id`),
  KEY `fk_class_node` (`class_node_id`),
  CONSTRAINT `fk_class_node` FOREIGN KEY (`class_node_id`) REFERENCES `type_nodes` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_parent_schema` FOREIGN KEY (`parent_ref_id`) REFERENCES `type_schema_refs` (`id`),
  CONSTRAINT `fk_type_node` FOREIGN KEY (`type_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2960290 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_union_members`
--

DROP TABLE IF EXISTS `type_union_members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_union_members` (
  `union_node_id` bigint unsigned NOT NULL,
  `member_node_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`union_node_id`,`member_node_id`),
  KEY `fk_member_node` (`member_node_id`),
  CONSTRAINT `fk_member_node` FOREIGN KEY (`member_node_id`) REFERENCES `type_nodes` (`id`),
  CONSTRAINT `fk_union_node` FOREIGN KEY (`union_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `typevar_bounds`
--

DROP TABLE IF EXISTS `typevar_bounds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `typevar_bounds` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `typevar_node_id` bigint unsigned NOT NULL,
  `bound_node_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tvar_node` (`typevar_node_id`),
  KEY `fk_bound_node` (`bound_node_id`),
  CONSTRAINT `fk_bound_node` FOREIGN KEY (`bound_node_id`) REFERENCES `type_nodes` (`id`),
  CONSTRAINT `fk_tvar_node` FOREIGN KEY (`typevar_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `typevar_constraints`
--

DROP TABLE IF EXISTS `typevar_constraints`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `typevar_constraints` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `typevar_node_id` bigint unsigned NOT NULL,
  `constraint_node_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tvar_node2` (`typevar_node_id`),
  KEY `fk_constraint_node` (`constraint_node_id`),
  CONSTRAINT `fk_constraint_node` FOREIGN KEY (`constraint_node_id`) REFERENCES `type_nodes` (`id`),
  CONSTRAINT `fk_tvar_node2` FOREIGN KEY (`typevar_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=881 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `typevar_variances`
--

DROP TABLE IF EXISTS `typevar_variances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `typevar_variances` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `typevar_node_id` bigint unsigned NOT NULL,
  `variance` enum('covariant','contravariant','invariant') NOT NULL DEFAULT 'invariant',
  PRIMARY KEY (`id`),
  UNIQUE KEY `typevar_node_id` (`typevar_node_id`),
  CONSTRAINT `fk_tvar_variance` FOREIGN KEY (`typevar_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=649 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `user_name` varchar(52) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `name_personal_first` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `name_personal_last` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `reset_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `oauth_provider` enum('google','github') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `oauth_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `user_status` enum('registered','active','cancelled','on hold','deleted') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'registered',
  `api_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `version_overflows`
--

DROP TABLE IF EXISTS `version_overflows`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `version_overflows` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `version_id` bigint unsigned NOT NULL,
  `violation` enum('epoch','major','minor','micro','micro_extra','phase_number','post_number','dev_number') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `string_value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_component` (`version_id`,`violation`),
  CONSTRAINT `version_overflows_ibfk_1` FOREIGN KEY (`version_id`) REFERENCES `versions` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `versions`
--

DROP TABLE IF EXISTS `versions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `versions` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `epoch` bigint unsigned NOT NULL DEFAULT '0',
  `major` bigint unsigned NOT NULL,
  `minor` bigint unsigned NOT NULL DEFAULT '0',
  `micro` bigint unsigned NOT NULL DEFAULT '0',
  `phase` enum('final','alpha','beta','candidate') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'final',
  `phase_number` bigint unsigned DEFAULT NULL,
  `post_number` bigint unsigned DEFAULT NULL,
  `dev_number` bigint unsigned DEFAULT NULL,
  `local` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `has_overflow` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_normal_version` (`epoch`,`major`,`minor`,`micro`,`phase`,`phase_number`,`post_number`,`dev_number`,`local`),
  KEY `idx_version_components` (`major`,`minor`,`micro`),
  CONSTRAINT `versions_chk_dev` CHECK ((((`dev_number` is not null) and (`phase` = _utf8mb4'final') and (`post_number` is null)) or (`dev_number` is null))),
  CONSTRAINT `versions_chk_phase` CHECK ((((`phase` = _utf8mb4'final') and (`phase_number` is null)) or ((`phase` <> _utf8mb4'final') and (`phase_number` is not null)))),
  CONSTRAINT `versions_chk_post` CHECK ((((`post_number` is not null) and (`phase` = _utf8mb4'final')) or (`post_number` is null)))
) ENGINE=InnoDB AUTO_INCREMENT=439505 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workflow_executions`
--

DROP TABLE IF EXISTS `workflow_executions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workflow_executions` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `workflow_id` bigint unsigned NOT NULL,
  `started_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `completed_at` timestamp NULL DEFAULT NULL,
  `status` enum('running','completed','failed','cancelled') NOT NULL DEFAULT 'running',
  PRIMARY KEY (`id`),
  KEY `workflowId` (`workflow_id`),
  CONSTRAINT `workflow_executions_ibfk_1` FOREIGN KEY (`workflow_id`) REFERENCES `workflows` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workflow_folder_relations`
--

DROP TABLE IF EXISTS `workflow_folder_relations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workflow_folder_relations` (
  `parent_id` bigint unsigned NOT NULL,
  `child_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`parent_id`,`child_id`),
  KEY `child_id` (`child_id`),
  CONSTRAINT `workflow_folder_relations_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `workflow_folders` (`id`),
  CONSTRAINT `workflow_folder_relations_ibfk_2` FOREIGN KEY (`child_id`) REFERENCES `workflow_folders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workflow_folders`
--

DROP TABLE IF EXISTS `workflow_folders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workflow_folders` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(8192) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `workflow_folders_users_FK` (`user_id`),
  CONSTRAINT `workflow_folders_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1703 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workflow_junctions`
--

DROP TABLE IF EXISTS `workflow_junctions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workflow_junctions` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `src_step_id` bigint unsigned NOT NULL,
  `target_workflow_id` bigint unsigned NOT NULL,
  `inquisition_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sourceStepId` (`src_step_id`),
  KEY `targetWorkflowId` (`target_workflow_id`),
  KEY `triggerInquisitionId` (`inquisition_id`),
  CONSTRAINT `workflow_junctions_ibfk_1` FOREIGN KEY (`src_step_id`) REFERENCES `steps` (`id`),
  CONSTRAINT `workflow_junctions_ibfk_2` FOREIGN KEY (`target_workflow_id`) REFERENCES `workflows` (`id`),
  CONSTRAINT `workflow_junctions_ibfk_3` FOREIGN KEY (`inquisition_id`) REFERENCES `inquisitions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workflow_requirements`
--

DROP TABLE IF EXISTS `workflow_requirements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workflow_requirements` (
  `workflow_id` bigint unsigned NOT NULL,
  `required_dist` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `required_package_id` bigint unsigned DEFAULT NULL,
  `required_extra_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`workflow_id`,`required_dist`),
  UNIQUE KEY `workflow_id` (`workflow_id`,`required_dist`,`required_package_id`,`required_extra_name`),
  KEY `required_dist` (`required_dist`,`required_package_id`,`required_extra_name`),
  CONSTRAINT `workflow_requirements_ibfk_1` FOREIGN KEY (`workflow_id`) REFERENCES `workflows` (`id`) ON DELETE CASCADE,
  CONSTRAINT `workflow_requirements_ibfk_2` FOREIGN KEY (`required_dist`, `required_package_id`) REFERENCES `packages` (`distribution`, `version_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workflows`
--

DROP TABLE IF EXISTS `workflows`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workflows` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(8192) DEFAULT NULL,
  `python_version_id` bigint unsigned NOT NULL,
  `parent_folder_id` bigint unsigned DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `parentFolderId` (`parent_folder_id`),
  KEY `pythonVersionId` (`python_version_id`),
  KEY `workflows_users_FK` (`user_id`),
  CONSTRAINT `workflows_ibfk_1` FOREIGN KEY (`parent_folder_id`) REFERENCES `workflow_folders` (`id`),
  CONSTRAINT `workflows_ibfk_2` FOREIGN KEY (`python_version_id`) REFERENCES `python_versions` (`id`),
  CONSTRAINT `workflows_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=218 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-20  8:36:04
