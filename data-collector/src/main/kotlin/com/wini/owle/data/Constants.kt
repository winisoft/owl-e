package com.wini.owle.data

import java.nio.file.Path


object Constants {


    const val TOP_DOWNLOADS = "C:/Users/wini/Downloads/top_downloads.csv"
    const val PYTHON_VERSIONS = ""
    const val SIMPLE_INDEX_URL = "https://pypi.org/simple/"
    const val DISTRIBUTIONS_CHUNK_SIZE = 250000

    const val BIGINT_UNSIGNED_MAX_VAL: ULong = ULong.MAX_VALUE
    const val BIGINT_UNSIGNED_MAX_VAL_LEN: Int = 19

    const val DISTRIBUTIONS_FILE_PATTERN = "(\\d{3})_distributions_(\\d{2})\\.sql"


    const val OUTPUT_DIR_BASE = "db_build_bundle"
    const val OUTPUT_DIR_SQL = "$OUTPUT_DIR_BASE/sql"
    const val OUTPUT_DIR_LOGS = "$OUTPUT_DIR_BASE/logs"
    const val OUTPUT_DIR_BG = "$OUTPUT_DIR_BASE/bg"

    const val DB_NAME = "owle_db"

    const val TEST_DB_NAME = "owle_test_db"

    const val CREATE_SCRIPT_PATH = "/reference/sql/create.sql"
    const val DOWNLOADS_CSV_DIR = "/reference/downloads"

}

object Table {
    const val users = "users"
    const val distributions = "distributions"
    const val python_versions = "python_versions"
    const val overflow_version_components = "overflow_version_components"
    const val version_specifiers = "version_specifiers"
    const val specifier_elements = "specifier_elements"
    const val distribution_versions = "distribution_versions"
    const val version_specifier_specifier_elements = "version_specifier_specifier_elements"
    const val distribution_install_options = "distribution_install_options"
    const val distribution_option_version_specifiers = "distribution_option_version_specifiers"
    const val workflow_folders = "workflow_folders"
    const val workflows = "workflows"
    const val workflow_metadata = "workflow_metadata"
    const val workflow_metadata_contributors = "workflow_metadata_contributors"
    const val workflow_environment_dependencies = "workflow_environment_dependencies"
    const val package_files = "package_files"
    const val type_definitions = "type_definitions"
    const val type_declarations = "type_declarations"
    const val type_union_members = "type_union_members"
    const val type_parameters = "type_parameters"
    const val type_bounds = "type_bounds"
    const val type_variance = "type_variance"
    const val type_schema_refs = "type_schema_refs"
    const val tools = "tools"
    const val tool_params = "tool_params"
    const val phases = "phases"
    const val steps = "steps"
    const val metadata_step_exclusions = "metadata_step_exclusions"
    const val data_points = "data_points"
    const val transformations = "transformations"
    const val transform_target_types = "transform_target_types"
    const val transformation_args = "transformation_args"
    const val applied_transforms = "applied_transforms"
    const val applied_transform_args = "applied_transform_args"
    const val error_watches = "error_watches"
    const val error_watch_fns = "error_watch_fns"
    const val inquisitions = "inquisitions"
    const val revisions = "revisions"
    const val provisions = "provisions"
    const val provision_data_points = "provision_data_points"
    const val workflow_junctions = "workflow_junctions"
    const val prereq_injections = "prereq_injections"
    const val payment_methods = "payment_methods"
    const val tags = "tags"
    const val workflow_tags = "workflow_tags"
    const val step_tags = "step_tags"
    const val tag_relations = "tag_relations"
    const val workflow_executions = "workflow_executions"
    const val step_executions = "step_executions"
    const val execution_logs = "execution_logs"
    const val benchmarking_data = "benchmarking_data"
    const val step_benchmarking_data = "step_benchmarking_data"
    const val test_cases = "test_cases"
    const val test_inputs = "test_inputs"
    const val test_expected_outcomes = "test_expected_outcomes"
    const val test_results = "test_results"

}