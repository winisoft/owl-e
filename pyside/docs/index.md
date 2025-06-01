

# PySide

## Data Model

These are the (currently) most relevant DDLs for the tables of the backend MySQL instance.

```sql
-- owle_dev.distributions definition

CREATE TABLE `distributions` (
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.env_marker_expressions definition

CREATE TABLE `env_marker_expressions` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `requirement_id` bigint unsigned NOT NULL,
  `parent_id` bigint unsigned DEFAULT NULL,
  `mode` enum('COMPARATIVE','LOGICAL') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `requirementId` (`requirement_id`),
  KEY `parentId` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34675 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.transform_target_types definition

CREATE TABLE `transform_target_types` (
  `transform_id` bigint unsigned NOT NULL,
  `eligible_type_node_id` bigint unsigned NOT NULL,
  `return_type_node_id` bigint unsigned NOT NULL,
  KEY `fk_ttt_eligible_type_node` (`eligible_type_node_id`),
  KEY `fk_ttt_return_type_node` (`return_type_node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.`transforms` definition

CREATE TABLE `transforms` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `examples` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.users definition

CREATE TABLE `users` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `reset_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `oauth_provider` enum('google','github') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `oauth_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `user_status` enum('registered','active','cancelled','on hold','deleted') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'registered',
  `api_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name_personal_first` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.versions definition

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


-- owle_dev.env_marker_comparisons definition

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


-- owle_dev.env_marker_logicals definition

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


-- owle_dev.packages definition

CREATE TABLE `packages` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
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
  PRIMARY KEY (`id`,`distribution`),
  KEY `packages_ibfk_2` (`distribution`),
  CONSTRAINT `packages_ibfk_1` FOREIGN KEY (`id`) REFERENCES `versions` (`id`),
  CONSTRAINT `packages_ibfk_2` FOREIGN KEY (`distribution`) REFERENCES `distributions` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4347077 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.python_versions definition

CREATE TABLE `python_versions` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `is_supported` tinyint DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `python_versions_ibfk_1` FOREIGN KEY (`id`) REFERENCES `versions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4344488 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.type_nodes definition

CREATE TABLE `type_nodes` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `version_id` bigint unsigned NOT NULL,
  `namespace` varchar(1024) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `kind` enum('base','typevar','union','instantiation','callable','method','attribute','class','generic','primitive','function') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `alias` varchar(255) DEFAULT NULL,
  `is_generic` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_version_constraint` (`version_id`),
  CONSTRAINT `fk_version_constraint` FOREIGN KEY (`version_id`) REFERENCES `versions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50185 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.type_schema_refs definition

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
) ENGINE=InnoDB AUTO_INCREMENT=499451 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.type_union_members definition

CREATE TABLE `type_union_members` (
  `union_node_id` bigint unsigned NOT NULL,
  `member_node_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`union_node_id`,`member_node_id`),
  KEY `fk_member_node` (`member_node_id`),
  CONSTRAINT `fk_member_node` FOREIGN KEY (`member_node_id`) REFERENCES `type_nodes` (`id`),
  CONSTRAINT `fk_union_node` FOREIGN KEY (`union_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.typevar_bounds definition

CREATE TABLE `typevar_bounds` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `typevar_node_id` bigint unsigned NOT NULL,
  `bound_node_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tvar_node` (`typevar_node_id`),
  KEY `fk_bound_node` (`bound_node_id`),
  CONSTRAINT `fk_bound_node` FOREIGN KEY (`bound_node_id`) REFERENCES `type_nodes` (`id`),
  CONSTRAINT `fk_tvar_node` FOREIGN KEY (`typevar_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.typevar_constraints definition

CREATE TABLE `typevar_constraints` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `typevar_node_id` bigint unsigned NOT NULL,
  `constraint_node_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tvar_node2` (`typevar_node_id`),
  KEY `fk_constraint_node` (`constraint_node_id`),
  CONSTRAINT `fk_constraint_node` FOREIGN KEY (`constraint_node_id`) REFERENCES `type_nodes` (`id`),
  CONSTRAINT `fk_tvar_node2` FOREIGN KEY (`typevar_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.typevar_variances definition

CREATE TABLE `typevar_variances` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `typevar_node_id` bigint unsigned NOT NULL,
  `variance` enum('covariant','contravariant','invariant') NOT NULL DEFAULT 'invariant',
  PRIMARY KEY (`id`),
  UNIQUE KEY `typevar_node_id` (`typevar_node_id`),
  CONSTRAINT `fk_tvar_variance` FOREIGN KEY (`typevar_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.version_overflows definition

CREATE TABLE `version_overflows` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `version_id` bigint unsigned NOT NULL,
  `violation` enum('epoch','major','minor','micro','micro_extra','phase_number','post_number','dev_number') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `string_value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_component` (`version_id`,`violation`),
  CONSTRAINT `version_overflows_ibfk_1` FOREIGN KEY (`version_id`) REFERENCES `versions` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.package_dist_requirements definition

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
  CONSTRAINT `fk_required_package` FOREIGN KEY (`required_dist`, `required_package_id`) REFERENCES `packages` (`distribution`, `id`),
  CONSTRAINT `package_dist_requirements_ibfk_1` FOREIGN KEY (`dependent_dist`, `dependent_package_id`) REFERENCES `packages` (`distribution`, `id`)
) ENGINE=InnoDB AUTO_INCREMENT=1026409 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.python_version_specifiers definition

CREATE TABLE `python_version_specifiers` (
  `version_id` bigint unsigned NOT NULL,
  `operator` enum('LT','LE','NE','EQ','GE','GT','COMPATIBLE','EXACT') NOT NULL,
  `wildcard` enum('NONE','MAJOR','MINOR','MICRO') NOT NULL DEFAULT 'NONE',
  PRIMARY KEY (`version_id`,`operator`,`wildcard`),
  UNIQUE KEY `uniq_pvs` (`version_id`,`operator`,`wildcard`),
  CONSTRAINT `pvs_fk_version` FOREIGN KEY (`version_id`) REFERENCES `python_versions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.tools definition

CREATE TABLE `tools` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `signature_node_id` bigint unsigned DEFAULT NULL,
  `description` varchar(8192) DEFAULT NULL,
  `body` text NOT NULL,
  `return_type_node_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tools_signature_node` (`signature_node_id`),
  KEY `fk_tools_return_type_node` (`return_type_node_id`),
  CONSTRAINT `fk_tools_return_type_node` FOREIGN KEY (`return_type_node_id`) REFERENCES `type_nodes` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_tools_signature_node` FOREIGN KEY (`signature_node_id`) REFERENCES `type_nodes` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.transform_args definition

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.type_generic_params definition

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.type_node_relations definition

CREATE TABLE `type_node_relations` (
  `parent_node_id` bigint unsigned NOT NULL,
  `child_node_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`child_node_id`,`parent_node_id`),
  KEY `fk_parent_node` (`parent_node_id`),
  CONSTRAINT `fk_child_node` FOREIGN KEY (`child_node_id`) REFERENCES `type_nodes` (`id`),
  CONSTRAINT `fk_parent_node` FOREIGN KEY (`parent_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- owle_dev.package_python_requirements definition

CREATE TABLE `package_python_requirements` (
  `distribution` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `package_id` bigint unsigned NOT NULL,
  `version_id` bigint unsigned NOT NULL,
  `operator` enum('LT','LE','NE','EQ','GE','GT','COMPATIBLE','EXACT') NOT NULL,
  `wildcard` enum('NONE','MAJOR','MINOR','MICRO') NOT NULL DEFAULT 'NONE',
  PRIMARY KEY (`package_id`,`distribution`),
  KEY `ppr_fk_spec` (`version_id`,`operator`,`wildcard`),
  CONSTRAINT `ppr_fk_pkg` FOREIGN KEY (`package_id`, `distribution`) REFERENCES `packages` (`id`, `distribution`) ON DELETE CASCADE,
  CONSTRAINT `ppr_fk_spec` FOREIGN KEY (`version_id`, `operator`, `wildcard`) REFERENCES `python_version_specifiers` (`version_id`, `operator`, `wildcard`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.tool_params definition

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


-- owle_dev.data_point_injections definition

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


-- owle_dev.data_point_reads definition

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


-- owle_dev.data_point_write_args definition

CREATE TABLE `data_point_write_args` (
  `revision_id` bigint unsigned NOT NULL,
  `data_point_id` bigint unsigned NOT NULL,
  `position` int unsigned NOT NULL,
  PRIMARY KEY (`revision_id`,`position`),
  KEY `ra_fk_datapoint` (`data_point_id`),
  CONSTRAINT `fk_revision_args_rev` FOREIGN KEY (`revision_id`) REFERENCES `data_point_writes` (`id`) ON DELETE CASCADE,
  CONSTRAINT `ra_fk_datapoint` FOREIGN KEY (`data_point_id`) REFERENCES `data_points` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.data_point_writes definition

CREATE TABLE `data_point_writes` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `data_point_id` bigint unsigned NOT NULL,
  `step_id` bigint unsigned NOT NULL,
  `transform_occurrence_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dataPointId` (`data_point_id`),
  KEY `stepId` (`step_id`),
  KEY `data_point_writes_transform_occurrences` (`transform_occurrence_id`),
  CONSTRAINT `data_point_writes_ibfk_1` FOREIGN KEY (`data_point_id`) REFERENCES `data_points` (`id`),
  CONSTRAINT `data_point_writes_ibfk_2` FOREIGN KEY (`step_id`) REFERENCES `steps` (`id`),
  CONSTRAINT `data_point_writes_transform_occurrences` FOREIGN KEY (`transform_occurrence_id`) REFERENCES `transform_occurrences` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.data_points definition

CREATE TABLE `data_points` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `workflow_id` bigint unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.error_watch_fns definition

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


-- owle_dev.error_watches definition

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


-- owle_dev.inquisition_args definition

CREATE TABLE `inquisition_args` (
  `inquisition_id` bigint unsigned NOT NULL,
  `data_point_id` bigint unsigned NOT NULL,
  `position` int unsigned NOT NULL,
  PRIMARY KEY (`inquisition_id`,`position`),
  KEY `fk_inquisition_args_dp` (`data_point_id`),
  CONSTRAINT `fk_inquisition_args_dp` FOREIGN KEY (`data_point_id`) REFERENCES `data_points` (`id`),
  CONSTRAINT `fk_inquisition_args_inq` FOREIGN KEY (`inquisition_id`) REFERENCES `inquisitions` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.inquisitions definition

CREATE TABLE `inquisitions` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `step_id` bigint unsigned NOT NULL,
  `condition_node_id` bigint unsigned NOT NULL,
  `note` varchar(8192) DEFAULT NULL,
  `data_point_id` bigint unsigned NOT NULL,
  `trigger_step_id` bigint unsigned NOT NULL,
  `execution_order` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `stepId` (`step_id`),
  KEY `dataPointId` (`data_point_id`),
  KEY `onMetStepId` (`trigger_step_id`),
  KEY `fk_inquisitions_condition_node` (`condition_node_id`),
  CONSTRAINT `fk_inquisitions_condition_node` FOREIGN KEY (`condition_node_id`) REFERENCES `type_nodes` (`id`),
  CONSTRAINT `inquisitions_ibfk_1` FOREIGN KEY (`step_id`) REFERENCES `steps` (`id`),
  CONSTRAINT `inquisitions_ibfk_2` FOREIGN KEY (`data_point_id`) REFERENCES `data_points` (`id`),
  CONSTRAINT `inquisitions_ibfk_3` FOREIGN KEY (`trigger_step_id`) REFERENCES `steps` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.transform_chains definition

CREATE TABLE `transform_chains` (
  `parent_transform_id` bigint unsigned NOT NULL,
  `child_transform_id` bigint unsigned NOT NULL,
  `depth` int unsigned NOT NULL,
  PRIMARY KEY (`parent_transform_id`,`child_transform_id`),
  KEY `fk_atc_descendant` (`child_transform_id`),
  CONSTRAINT `fk_atc_ancestor` FOREIGN KEY (`parent_transform_id`) REFERENCES `transform_occurrences` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_atc_descendant` FOREIGN KEY (`child_transform_id`) REFERENCES `transform_occurrences` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- owle_dev.transform_occurence_args definition

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


-- owle_dev.transform_occurrences definition

CREATE TABLE `transform_occurrences` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `transform_id` bigint unsigned NOT NULL,
  `occurrence` enum('provision','revision','inquisition','prereqInjection') NOT NULL,
  `prev_transform_id` bigint unsigned DEFAULT NULL,
  `dataPointId` bigint unsigned DEFAULT NULL,
  `toolResultStepId` bigint unsigned DEFAULT NULL,
  `is_persistent` tinyint(1) GENERATED ALWAYS AS ((cast(`occurrence` as char charset utf8mb4) = _utf8mb4'revision')) STORED,
  PRIMARY KEY (`id`),
  KEY `transformationId` (`transform_id`),
  KEY `previousTransformId` (`prev_transform_id`),
  KEY `dataPointId` (`dataPointId`),
  KEY `toolResultStepId` (`toolResultStepId`),
  CONSTRAINT `transform_occurrences_ibfk_1` FOREIGN KEY (`transform_id`) REFERENCES `transforms` (`id`),
  CONSTRAINT `transform_occurrences_ibfk_2` FOREIGN KEY (`prev_transform_id`) REFERENCES `transform_occurrences` (`id`),
  CONSTRAINT `transform_occurrences_ibfk_3` FOREIGN KEY (`dataPointId`) REFERENCES `data_points` (`id`),
  CONSTRAINT `transform_occurrences_ibfk_4` FOREIGN KEY (`toolResultStepId`) REFERENCES `steps` (`id`),
  CONSTRAINT `chkapplied_transformsSource` CHECK ((((`dataPointId` is not null) and (`toolResultStepId` is null)) or ((`dataPointId` is null) and (`toolResultStepId` is not null))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```