package com.wini.owle.data.ext

@Suppress("ConstPropertyName")
object ValArgs {

    const val users = "(id, email, password, resetToken, oauthProvider, oauthId, subscriptionStatus, apiKey, createdAt, updatedAt)"

    const val distributions = "(id, name)"

    const val python_versions = "(id, major, minor, micro, phase, phase_number, is_post_release, post_number, is_dev_release, dev_number, has_overflow)"

    const val overflow_version_components = "(id, python_version_id, component_type, string_value)"

    const val version_specifiers = "(id, raw_specifier, environment_marker)"

    const val specifier_elements = "(id, operator, python_version_id, wildcard_segment, position)"

    const val distribution_versions = "(id, distribution_id, python_version_id, version_string)"

    const val version_specifier_specifier_elements = "(version_specifier_id, specifier_element_id)"

    const val distribution_install_options = "(id, distribution_id, name, kind)"

    const val distribution_option_version_specifiers = "(id, install_option_id, version_specifier_id)"

    const val workflow_folders = "(id, name, description, parentFolderId, createdAt, updatedAt)"

    const val workflows = "(id, name, description, pythonDistributionVersionId, parentFolderId, createdAt, updatedAt)"

    const val workflow_metadata = "(id, workflow_id, name, version, copyright_information, contact_information, third_party_licenses, discretion_advised_content, commercial_usage, credit_notation, allow_redistribution, modification, distribution_version, distribution_accepted_atDATETIMENOTNULL, distribution_updated_atDATETIMENOTNULL)"

    const val workflow_metadata_contributors = "(metadataId, contributorUserId)"

    const val workflow_environment_dependencies = "(id, workflowId, distributionId, versionSpecifierId, distributionOptInId)"

    const val package_files = "(id, distribution_version_id, filename, python_tag, abi_tag, platform_tag, file_type, file_url)"

    const val type_definitions = "(id, name, namespace, distribution_version_id)"

    const val type_declarations = "(id, type_definition_id, alias)"

    const val type_union_members = "(unionTypeDeclarationId, memberTypeDeclarationId)"

    const val type_parameters = "(id, typeDeclarationId, position, parameterTypeDeclarationId)"

    const val type_bounds = "(id, typeVariableDeclarationId, boundTypeDeclarationId)"

    const val type_variance = "(id, typeVariableDeclarationId, variance)"

    const val type_schema_refs = "(id, parentRefId, name, typeDeclarationId, description)"

    const val tools = "(id, name, description, body, returnTypeDeclarationId)"

    const val tool_params = "(id, toolId, name, typeDeclarationId, defaultValue)"

    const val phases = "(id, name, description, displayOrder, parentPhaseId, workflowId)"

    const val steps = "(id, name, description, parentPhaseId, workflowId, toolId, isWorkflowHead, defaultNextId, displayOrder, createdAt, updatedAt)"

    const val metadata_step_exclusions = "(metadata_id, excluded_step_id)"

    const val data_points = "(id, workflowId, name, presetValue, storeMethod, typeDeclarationId, createdAt, updatedAt)"

    const val transformations = "(id, name, description, examples)"

    const val transform_target_types = "(transformationId, eligibleTypeDeclarationId, returnTypeDeclarationId)"

    const val transformation_args = "(id, transformationId, typeDeclarationId, name, description)"

    const val applied_transforms = "(id, transformationId, occurrence, previousTransformId, dataPointId, toolResultStepId)"

    const val applied_transform_args = "(id, appliedTransformId, transformationArgId, value)"

    const val error_watches = "(id, stepId, errorType, onMetStepId)"

    const val error_watch_fns = "(id, errorWatchId, functionName, position, errorWatchId, position, errorWatchId, functionName, position)"

    const val inquisitions = "(id, stepId, class, dataPointId, onMetStepId, executionOrder)"

    const val revisions = "(id, dataPointId, stepId, transformationId)"

    const val provisions = "(id, step_id, tool_param_id)"

    const val provision_data_points = "(provisionId, dataPointId, provisionId, dataPointId)"

    const val workflow_junctions = "(id, sourceStepId, targetWorkflowId, triggerInquisitionId)"

    const val prereq_injections = "(id, junctionId, sourceDataPointId, injectedDataPointId, name, description)"

    const val payment_methods = "(id, stripePaymentMethodId, userId, cardBrand, lastFour, isDefault, createdAt, updatedAt)"

    const val tags = "(id, userId, name, description)"

    const val workflow_tags = "(workflowId, tagId)"

    const val step_tags = "(stepId, tagId)"

    const val tag_relations = "(parentTagId, childTagId, parentTagId, childTagId)"

    const val workflow_executions = "(id, workflowId, userId, startedAt, completedAt, status)"

    const val step_executions = "(id, workflowExecutionId, stepId, startedAt, completedAt, status)"

    const val execution_logs = "(id, stepExecutionId, timestamp, logLevel, message)"

    const val benchmarking_data = "(id, workflowExecutionId, totalExecutionTime, totalSteps, aiDeterminedSteps, predeterminedSteps)"

    const val step_benchmarking_data = "(id, stepExecutionId, executionTime)"

    const val test_cases = "(id, userId, name, description, workflowId, stepId)"

    const val test_inputs = "(id, testCaseId, dataPointName, value, generationMethod)"

    const val test_expected_outcomes = "(id, testCaseId, dataPointName, expectedValue, comparisonOperator)"

    const val test_results = "(id, testCaseId, workflowExecutionId, passed, executedAt)"

}