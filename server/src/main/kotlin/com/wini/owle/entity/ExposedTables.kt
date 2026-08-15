package com.wini.owle.entity

import com.wini.owle.dto.typing.enums.VarianceCardinality
import com.wini.owle.dto.user.enums.UserStatus
import com.wini.owle.dto.workflows.enums.StoreMethod
import com.wini.owle.entity.runs.enums.ComparisonOperator
import com.wini.owle.entity.runs.enums.GenerationMethod
import com.wini.owle.entity.runs.enums.LogLevel
import com.wini.owle.entity.transforms.Transforms
import com.wini.owle.entity.user.enums.CardBrand
import com.wini.owle.entity.workflows.enums.AuthorshipCredit
import com.wini.owle.entity.workflows.enums.CommercialUse
import com.wini.owle.entity.workflows.enums.ModificationTerms
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.timestamp
import org.jetbrains.exposed.v1.json.json
import org.jetbrains.exposed.v1.json.jsonb

object BenchmarkReports: LongIdTable("benchmark_reports") {

}

object CallableOwners: LongIdTable("callable_owners") {

}

object CallableParameters: LongIdTable("callable_parameters") {

}

object CallableSignatures: LongIdTable("callable_signatures") {

}

object DataPointInjections: LongIdTable("data_point_injections") {

}

object TestCases : LongIdTable("test_cases") {
    val user = reference("userId", Users, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255)
    val description = text("description").nullable()
    val workflow = reference("workflowId", Workflows, onDelete = ReferenceOption.CASCADE).nullable()
    val step = reference("stepId", Steps, onDelete = ReferenceOption.CASCADE).nullable()
}

object TestInputs : LongIdTable("test_inputs") {
    val testCase = reference("testCaseId", TestCases, onDelete = ReferenceOption.CASCADE)
    val dataPointName = varchar("dataPointName", 255)
    val value = text("value").nullable()
    val generationMethod = enumerationByName("generationMethod", 20, GenerationMethod::class)
}

object TestExpectedOutcomes : LongIdTable("test_expected_outcomes") {
    val testCase = reference("testCaseId", TestCases, onDelete = ReferenceOption.CASCADE)
    val dataPointName = varchar("dataPointName", 255)
    val expectedValue = text("expectedValue").nullable()
    val comparisonOperator = enumerationByName("comparisonOperator", 20, ComparisonOperator::class)
}

object TestResults : LongIdTable("test_results") {
    val testCase = reference("testCaseId", TestCases, onDelete = ReferenceOption.CASCADE)
    val workflowExecution = reference("workflowExecutionId", WorkflowExecutions, onDelete = ReferenceOption.CASCADE)
    val passed = bool("passed")
    val executedAt = timestamp("executedAt").defaultExpression(CurrentTimestamp)
}

object DataPointWriteArgs: LongIdTable("data_point_write_args") {

}

object DataPointWrites: LongIdTable("data_point_writes") {

}

object DataPoints : LongIdTable("data_points") {
    val workflow = reference("workflowId", Workflows, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255)
    val presetValue = text("presetValue").nullable()
    val storeMethod = enumerationByName("storeMethod", 10, StoreMethod::class).default(St.TEXT)
    val typeNodeId = reference("type_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val description = text("description").nullable()
    val createdAt = timestamp("createdAt").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updatedAt").defaultExpression(CurrentTimestamp)
}

object Distributions: LongIdTable("distributions") {

}

object EnvMarkerComparisons: LongIdTable("env_marker_comparisons") {

}

object EnvMarkerExpressions: LongIdTable("env_marker_expressions") {

}

object EnvMarkerLogicals: LongIdTable("env_marker_logicals") {

}

object PaymentMethods : LongIdTable("payment_methods") {
    val stripePaymentMethodId = varchar("stripePaymentMethodId", 255).nullable()

    // Payment method definitely doesn't make sense if user is removed => cascade
    val user = reference("userId", Users, onDelete = ReferenceOption.CASCADE)

    val cardBrand = enumerationByName("cardBrand", 50, CardBrand::class).nullable()
    val lastFour = varchar("lastFour", 4).nullable()
    val isDefault = bool("isDefault").default(false)
    val createdAt = timestamp("createdAt").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updatedAt").defaultExpression(CurrentTimestamp)
}

object ErrorWatchFns : LongIdTable("error_watch_fns") {
    val errorWatch = reference("errorWatchId", ErrorWatches, onDelete = ReferenceOption.CASCADE)
    val functionName = varchar("functionName", 255)
    val position = integer("position")
    init {
        uniqueIndex("unique_error_watch_position", errorWatch, position)
        uniqueIndex("unique_error_watch_function_position", errorWatch, functionName, position)
    }
}

object ErrorWatches : LongIdTable("error_watches") {
    val step = reference("stepId", Steps, onDelete = ReferenceOption.CASCADE)
    val errorType = varchar("errorType", 255)
    val onMetStep = reference("onMetStepId", Steps, onDelete = ReferenceOption.CASCADE)
}

object ExecutionLogs : LongIdTable("execution_logs") {
    val stepExecution = reference("stepExecutionId", StepExecutions, onDelete = ReferenceOption.CASCADE)
    val timestamp = timestamp("timestamp").defaultExpression(CurrentTimestamp)
    val logLevel = enumerationByName("logLevel", 10, LogLevel::class)
    val message = text("message")
}

object Groupers: LongIdTable("groupers") {
    val name = varchar("name", 512)
    val description = text("description").nullable()
    val displayOrder = integer("displayOrder")
    val parentPhase = reference("parentPhaseId", Groupers, onDelete = ReferenceOption.CASCADE).nullable()
    val workflow = reference("workflowId", Workflows, onDelete = ReferenceOption.CASCADE)
}

object Packages : LongIdTable("packages") {
    val name = varchar("name", 255)
    val disambiguation = varchar("disambiguation", 255).nullable()
    val versionSpec = reference("versionSpecId", VersionSpecs, onDelete = ReferenceOption.CASCADE)
}

object InquisitionDataPointReads: LongIdTable("inquisition_data_point_reads") {

}

object InquisitionJunctionRedirects: LongIdTable("inquisition_junction_redirects") {

}

object InquisitionStepRedirects: LongIdTable("inquisition_step_redirects") {

}


object Metadata: LongIdTable("metadata") {

}

object MultiflowWorkflows: LongIdTable("multiflow_workflows") {

}

object Multiflows: LongIdTable("multiflows") {

}

object PackageDistRequirements: LongIdTable("package_dist_requirements") {

}

object PackageExtras: LongIdTable("package_extras") {

}

object PackagePythonRequirements: LongIdTable("package_python_requirements") {
}

object PythonVersionSpecifiers: LongIdTable("python_version_specifiers") {

}

object PythonVersions: LongIdTable("python_versions") {

}

object StepDataPointReads: LongIdTable("step_data_point_reads") {

}

object StepDefaultRoutes: LongIdTable("step_default_routes") {

}

object TaggedDistributions: LongIdTable("tagged_distributions") {

}

object TaggedGroupers: LongIdTable("tagged_groupers") {

}

object TaggedPackages: LongIdTable("tagged_packages") {

}

object TaggedSteps: LongIdTable("tagged_steps") {

}

object TaggedTools: LongIdTable("tagged_tools") {

}

object TaggedTransforms: LongIdTable("tagged_transforms") {

}

object TaggedWorkflowFolders: LongIdTable("tagged_workflow_folders") {

}

object TaggedWorkflows: LongIdTable("tagged_workflows") {

}

object TestStepRun: LongIdTable("test_step_run") {

}

object TransformArgs: LongIdTable("transform_args") {

}

object TransformOccurrenceArgs: LongIdTable("transform_occurence_args") {

}

object TransformOccurrenceChains: LongIdTable("transform_occurrence_chains") {

}

object TransformOccurrences: LongIdTable("transform_occurrences") {

}

object Transforms: LongIdTable("transforms") {

}

object TypeAliases: LongIdTable("type_aliases") {

}

object TypeGeneric_params: LongIdTable("type_generic_params") {

}

object TypeInstantiation_args: LongIdTable("type_instantiation_args") {

}

object TypeInstantiations: LongIdTable("type_instantiations") {

}

object TypeNodeRelations: LongIdTable("type_node_relations") {

}

object TypeNodes: LongIdTable("type_nodes") {

}

object TypeSAchema_refs: LongIdTable("type_schema_refs") {

}

object TypesUnion_members: LongIdTable("type_union_members") {

}

object Typevar_bounds: LongIdTable("typevar_bounds") {

}

object Typevar_constraints: LongIdTable("typevar_constraints") {

}

object Typevar_variances: LongIdTable("typevar_variances") {

}


object VersionOverflows: LongIdTable("version_overflows") {

}

object Versions: LongIdTable("versions") {

}


object WorkflowFolderRelations: LongIdTable("workflow_folder_relations") {

}


object WorkflowRequirements: LongIdTable("workflow_requirements") {

}

object Wrkflows: LongIdTable("workflows") {

}

object VersionSpecs : LongIdTable("version_specs") {
    val versionMin = varchar("versionMin", 127).nullable()
    val versionMax = varchar("versionMax", 127).nullable()
    val versionExact = varchar("versionExact", 127).nullable()
}

object MetadataStepExclusions : CompositeIdTable("metadata_step_exclusions") {
    val metadata = reference("metadata_id", WorkflowMetadata)
    val step = reference("excluded_step_id", Steps)
    override val primaryKey = PrimaryKey(metadata, step)
}


object Provisions : LongIdTable("provisions") {
    val step = reference("step_id", Steps, onDelete = ReferenceOption.CASCADE)
    val toolParam = reference("tool_param_id", ToolParams, onDelete = ReferenceOption.CASCADE)
}

object ProvisionDataPoints : CompositeIdTable("provision_data_points") {
    val provision = reference("provisionId", Provisions, onDelete = ReferenceOption.CASCADE)
    val dataPoint = reference("dataPointId", DataPoints, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(provision, dataPoint)
}





object Tools : LongIdTable("tools") {
    val name = varchar("name", 512)
    val description = text("description").nullable()
    val body = text("body")
    val returnTypeDeclaration = reference("returnTypeDeclarationId", TypeDeclarations, onDelete = ReferenceOption.CASCADE).nullable()
}

object ToolParams : LongIdTable("tool_params") {
    val tool = reference("toolId", Tools, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255)
    val typeDeclaration = reference("typeDeclarationId", TypeDeclarations, onDelete = ReferenceOption.CASCADE)
    val hasDefault = bool("hasDefault").default(false)
}


object Steps : LongIdTable("steps") {
    val name = varchar("name", 512)
    val description = text("description").nullable()
    val parentPhase = reference("parentPhaseId", Groupers, onDelete = ReferenceOption.CASCADE).nullable()
    val isWorkflowHead = bool("isWorkflowHead")

    val workflow = reference("workflowId", Workflows, onDelete = ReferenceOption.CASCADE)
    val tool = reference("toolId", Tools, onDelete = ReferenceOption.CASCADE)

    // We typically use SET_NULL if the 'defaultNext' step is removed
    val defaultNext = reference("defaultNextId", Steps, onDelete = ReferenceOption.SET_NULL).nullable()

    val displayOrder = integer("displayOrder")
    //val metadata = json("metadata", UserDesignedMetadata.jsonConfig, UserDesignedMetadata.serializer()).nullable()
    val img = blob("img").nullable()
    val createdAt = timestamp("createdAt").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updatedAt").defaultExpression(CurrentTimestamp)
}




object Inquisitions : LongIdTable("inquisitions") {
    val step = reference("stepId", Steps, onDelete = ReferenceOption.CASCADE)
    val clazz = varchar("class", 255)
    val dataPoint = reference("dataPointId", DataPoints, onDelete = ReferenceOption.CASCADE)
    val onMetStep = reference("onMetStepId", Steps, onDelete = ReferenceOption.CASCADE)
    val executionOrder = integer("executionOrder")
}

object Revisions : LongIdTable("revisions") {
    val dataPoint = reference("dataPointId", DataPoints, onDelete = ReferenceOption.CASCADE)
    val step = reference("stepId", Steps, onDelete = ReferenceOption.CASCADE)
    val transformation = reference("transformationId", Transforms, onDelete = ReferenceOption.CASCADE).nullable()
}

object WorkflowJunctions : LongIdTable("workflow_junctions") {
    val sourceStep = reference("sourceStepId", Steps, onDelete = ReferenceOption.CASCADE)
    val targetWorkflow = reference("targetWorkflowId", Workflows, onDelete = ReferenceOption.CASCADE)
    val triggerInquisition = reference("triggerInquisitionId", Inquisitions, onDelete = ReferenceOption.CASCADE).nullable()
}

object PrereqInjections : LongIdTable("prereq_injections") {
    val junction = reference("junctionId", WorkflowJunctions, onDelete = ReferenceOption.CASCADE)
    val sourceDataPoint = reference("sourceDataPointId", DataPoints, onDelete = ReferenceOption.CASCADE)
    val injectedDataPoint = reference("injectedDataPointId", DataPoints, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255).nullable()
    val description = text("description").nullable()
}



object Tags : LongIdTable("tags") {
    val name = varchar("name", 255)
    val description = text("description").nullable()
    val user = reference("userId", Users, onDelete = ReferenceOption.CASCADE)
}

object WorkflowTags: CompositeIdTable("workflow_tags") {
    val workflow = reference("workflowId", Workflows, onDelete = ReferenceOption.CASCADE)
    val tag = reference("tagId", Tags, onDelete = ReferenceOption.CASCADE)
}

object StepTags: CompositeIdTable("step_tags") {
    val step = reference("stepId", Steps, onDelete = ReferenceOption.CASCADE)
    val tag = reference("tagId", Tags, onDelete = ReferenceOption.CASCADE)
}

object WorkflowExecutions : LongIdTable("workflow_executions") {
    val workflow = reference("workflowid", Workflows, onDelete = ReferenceOption.CASCADE)
    val user = reference("userId", Users, onDelete = ReferenceOption.CASCADE)
    val startedAt = timestamp("startedAt").defaultExpression(CurrentTimestamp)
    val completedAt = timestamp("completedAt").nullable()
    val status = enumerationByName("status", 20, ExecutionStatusEntity::class).default(ExecutionStatusEntity.RUNNING)
}

object StepExecutions : LongIdTable("step_executions") {
    val workflowExecution = reference("workflowExecutionId", WorkflowExecutions, onDelete = ReferenceOption.CASCADE)
    val step = reference("stepId", Steps, onDelete = ReferenceOption.CASCADE)
    val startedAt = timestamp("startedAt").defaultExpression(CurrentTimestamp)
    val completedAt = timestamp("completedAt").nullable()
    val status = enumerationByName("status", 20, ExecutionStatusEntity::class).default(ExecutionStatusEntity.RUNNING)
}



object BenchmarkingData : LongIdTable("benchmarking_data") {
    val workflowExecution = reference("workflowExecutionId", WorkflowExecutions, onDelete = ReferenceOption.CASCADE)
    val totalExecutionTime = decimal("totalExecutionTime", 10, 3)
    val totalSteps = integer("totalSteps")
    val aiDeterminedSteps = integer("aiDeterminedSteps")
    val predeterminedSteps = integer("predeterminedSteps")
}

object StepBenchmarkingDatas : LongIdTable("step_benchmarking_data") {
    val stepExecution = reference("stepExecutionId", StepExecutions, onDelete = ReferenceOption.CASCADE)
    val executionTime = decimal("executionTime", 10, 3)
}



object TypeUnionMembers : CompositeIdTable("type_union_members") {
    val unionTypeDeclaration = reference("unionTypeDeclarationId", TypeDeclarations, onDelete = ReferenceOption.CASCADE)
    val memberTypeDeclaration = reference("memberTypeDeclarationId", TypeDeclarations, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(unionTypeDeclaration, memberTypeDeclaration)
}

object ToolPackageDependencies : CompositeIdTable("tool_package_dependencies") {
    val tool = reference("toolId", Tools, onDelete = ReferenceOption.CASCADE)
    val packageEntity = reference("packageId", Packages, onDelete = ReferenceOption.CASCADE)
    val versionSpec = reference("versionSpecId", VersionSpecs, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(tool, packageEntity, versionSpec)
}

object TransformTargetTypes : CompositeIdTable("transform_target_types") {
    val transformation = reference("transformationId", Transformations, onDelete = ReferenceOption.CASCADE)
    val eligibleTypeDeclaration = reference("eligibleTypeDeclarationId", TypeDeclarations, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(transformation, eligibleTypeDeclaration, name = "PK_TransformTargetTypes")
}

object TagRelations : CompositeIdTable("tag_relations") {
    val parentTag = reference("parentTagId", Tags, onDelete = ReferenceOption.CASCADE)
    val childTag = reference("childTagId", Tags, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(parentTag, childTag)
}
