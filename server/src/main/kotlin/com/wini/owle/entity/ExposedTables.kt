package com.wini.owle.entity

import com.wini.owle.dto.runs.ResourceUsageDto
import com.wini.owle.entity.environs.PackageRepoType
import com.wini.owle.entity.runs.ComparisonOperator
import com.wini.owle.entity.runs.ExecutionStatus
import com.wini.owle.entity.runs.GenerationMethod
import com.wini.owle.entity.runs.LogLevel
import com.wini.owle.entity.transforms.TransformOccurrence
import com.wini.owle.entity.typing.VarianceCardinality
import com.wini.owle.entity.user.CardBrand
import com.wini.owle.entity.user.SubscriptionStatus
import com.wini.owle.entity.workflows.StoreMethod
import com.wini.owle.models.metadata.UserDesignedMetadata
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.id.CompositeIdTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.json.json
import org.jetbrains.exposed.sql.json.jsonb
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime


object VersionSpecs : LongIdTable("version_specs") {
    val versionMin = varchar("versionMin", 127).nullable()
    val versionMax = varchar("versionMax", 127).nullable()
    val versionExact = varchar("versionExact", 127).nullable()
}

object Packages : LongIdTable("packages") {
    val name = varchar("name", 255)
    val disambiguation = varchar("disambiguation", 255).nullable()
    val versionSpec = reference("versionSpecId", VersionSpecs, onDelete = ReferenceOption.CASCADE)
}

object TypeDefinitions : LongIdTable("type_definitions") {
    val name = varchar("name", 255)
    val namespace = varchar("namespace", 512)
    val versionSpec = optReference("versionSpecId", VersionSpecs, onDelete = ReferenceOption.CASCADE)
}

object TypeDeclarations : LongIdTable("type_declarations") {
    val typeDefinition = reference("typeDefinitionId", TypeDefinitions, onDelete = ReferenceOption.CASCADE)
    val alias = varchar("alias", 255).nullable()
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

object TypeParameters : LongIdTable("type_parameters") {
    val typeDeclaration = reference("typeDeclarationId", TypeDeclarations, onDelete = ReferenceOption.CASCADE)
    val position = integer("position")
    val parameterTypeDeclaration = reference("parameterTypeDeclarationId", TypeDeclarations, onDelete = ReferenceOption.CASCADE)
}

object TypeBounds : LongIdTable("type_bounds") {
    val typeVariableDeclaration = reference("typeVariableDeclarationId", TypeDeclarations, onDelete = ReferenceOption.CASCADE)
    val boundTypeDeclaration = reference("boundTypeDeclarationId", TypeDeclarations, onDelete = ReferenceOption.CASCADE)
}

object TypeVariances : LongIdTable("type_variance") {
    val typeVariableDeclaration = reference("typeVariableDeclarationId", TypeDeclarations, onDelete = ReferenceOption.CASCADE)
    val variance = enumerationByName("variance", 13, VarianceCardinality::class)
        .default(VarianceCardinality.INVARIANT)
}

object TypeSchemaRefs : LongIdTable("type_schema_refs") {
    // parentRef is nullable, but if it's deleted, all children vanish
    val parentRef = reference("parentRefId", TypeSchemaRefs, onDelete = ReferenceOption.CASCADE).nullable()
    val name = varchar("name", 255)
    val typeDeclaration = reference("typeDeclarationId", TypeDeclarations, onDelete = ReferenceOption.CASCADE)
    val description = text("description").nullable()
}

object WorkflowFolders : LongIdTable("workflow_folders") {
    val name = varchar("name", 255)
    val description = text("description").nullable()
    val img = blob("img").nullable()

    val parentFolder = reference("parentFolderId", WorkflowFolders, onDelete = ReferenceOption.CASCADE).nullable()
    val createdAt = datetime("createdAt").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updatedAt").defaultExpression(CurrentDateTime)
}

object Workflows : LongIdTable("workflows") {
    val name = varchar("name", 255)
    val description = text("description").nullable()
    val pythonVersion = varchar("pythonVersion", 127).default("3.11")
    val parentFolder = reference("parentFolderId", WorkflowFolders, onDelete = ReferenceOption.CASCADE).nullable()
    val img = blob("img").nullable()
    val metadata = jsonb<UserDesignedMetadata>("metadata", Json)
    val createdAt = datetime("createdAt").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updatedAt").defaultExpression(CurrentDateTime)
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

object Phases : LongIdTable("phases") {
    val name = varchar("name", 512)
    val description = text("description").nullable()
    val displayOrder = integer("displayOrder")
    val parentPhase = reference("parentPhaseId", Phases, onDelete = ReferenceOption.CASCADE).nullable()
    val workflow = reference("workflowId", Workflows, onDelete = ReferenceOption.CASCADE)
}

object Steps : LongIdTable("steps") {
    val name = varchar("name", 512)
    val description = text("description").nullable()
    val parentPhase = reference("parentPhaseId", Phases, onDelete = ReferenceOption.CASCADE).nullable()
    val isWorkflowHead = bool("isWorkflowHead")

    val workflow = reference("workflowId", Workflows, onDelete = ReferenceOption.CASCADE)
    val tool = reference("toolId", Tools, onDelete = ReferenceOption.CASCADE)

    // We typically use SET_NULL if the 'defaultNext' step is removed
    val defaultNext = reference("defaultNextId", Steps, onDelete = ReferenceOption.SET_NULL).nullable()

    val displayOrder = integer("displayOrder")
    val metadata = json("metadata", UserDesignedMetadata.jsonConfig, UserDesignedMetadata.serializer()).nullable()
    val img = blob("img").nullable()
    val createdAt = datetime("createdAt").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updatedAt").defaultExpression(CurrentDateTime)
}

object DataPoints : LongIdTable("data_points") {
    val workflow = reference("workflowId", Workflows, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255)
    val presetValue = text("presetValue").nullable()
    val storeMethod = enumerationByName("storeMethod", 10, StoreMethod::class).default(StoreMethod.TEXT)
    val typeDeclaration = reference("typeDeclarationId", TypeDeclarations, onDelete = ReferenceOption.CASCADE)
    val description = text("description").nullable()
    val createdAt = datetime("createdAt").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updatedAt").defaultExpression(CurrentDateTime)
}

object PackageRepositories : LongIdTable("packagerepositories") {
    val sourceType = enumerationByName("sourcetype", 50, PackageRepoType::class)
    val sourceUrl = text("sourceurl").nullable()
}

object Transformations : LongIdTable("transformations") {
    val name = varchar("name", 255)
    val description = text("description")
    val examples = text("examples").nullable()
    val returnsTypeDeclaration = reference("returnsTypeDeclarationId", TypeDeclarations, onDelete = ReferenceOption.CASCADE).nullable()
}

object TransformationArgs : LongIdTable("transformation_args") {
    val transformation = reference("transformationId", Transformations, onDelete = ReferenceOption.CASCADE)
    val typeDeclaration = reference("typeDeclarationId", TypeDeclarations, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255)
    val description = text("description").nullable()
}

object AppliedTransforms : LongIdTable("applied_transforms") {
    val transformation = reference("transformationId", Transformations, onDelete = ReferenceOption.CASCADE)
    val occurrence = enumerationByName("occurrence", 20, TransformOccurrence::class)
    val previousTransform = reference("previousTransformId", AppliedTransforms, onDelete = ReferenceOption.CASCADE).nullable()
    val resultTypeDeclaration = reference("resultTypeDeclarationId", TypeDeclarations, onDelete = ReferenceOption.CASCADE)
    val dataPoint = reference("dataPointId", DataPoints, onDelete = ReferenceOption.CASCADE).nullable()
    val toolResultStep = reference("toolResultStepId", Steps, onDelete = ReferenceOption.CASCADE).nullable()
}

object AppliedTransformArgs : LongIdTable("applied_transform_args") {
    val appliedTransform = reference("appliedTransformId", AppliedTransforms, onDelete = ReferenceOption.CASCADE)
    val transformationArg = reference("transformationArgId", TransformationArgs, onDelete = ReferenceOption.CASCADE)
    val value = text("value")
}

object ErrorWatches : LongIdTable("error_watches") {
    val step = reference("stepId", Steps, onDelete = ReferenceOption.CASCADE)
    val errorType = varchar("errorType", 255)
    val onMetStep = reference("onMetStepId", Steps, onDelete = ReferenceOption.CASCADE)
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
    val transformation = reference("transformationId", Transformations, onDelete = ReferenceOption.CASCADE).nullable()
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

object Users : LongIdTable("users") {
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)
    val resetToken = varchar("resetToken", 255).nullable()
    val oauthProvider = varchar("oauthProvider", 50).nullable()
    val oauthId = varchar("oauthId", 255).nullable()
    val subscriptionStatus = enumerationByName("subscriptionStatus", 20, SubscriptionStatus::class)
        .default(SubscriptionStatus.REGISTERED)
    val apiKey = varchar("apiKey", 255).nullable()
    val createdAt = datetime("createdAt").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updatedAt").defaultExpression(CurrentDateTime)
}

object PaymentMethods : LongIdTable("payment_methods") {
    val stripePaymentMethodId = varchar("stripePaymentMethodId", 255).nullable()

    // Payment method definitely doesn't make sense if user is removed => cascade
    val user = reference("userId", Users, onDelete = ReferenceOption.CASCADE)

    val cardBrand = enumerationByName("cardBrand", 50, CardBrand::class).nullable()
    val lastFour = varchar("lastFour", 4).nullable()
    val isDefault = bool("isDefault").default(false)
    val createdAt = datetime("createdAt").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updatedAt").defaultExpression(CurrentDateTime)
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
    val startedAt = datetime("startedAt").defaultExpression(CurrentDateTime)
    val completedAt = datetime("completedAt").nullable()
    val status = enumerationByName("status", 20, ExecutionStatus::class).default(ExecutionStatus.RUNNING)
}

object StepExecutions : LongIdTable("step_executions") {
    val workflowExecution = reference("workflowExecutionId", WorkflowExecutions, onDelete = ReferenceOption.CASCADE)
    val step = reference("stepId", Steps, onDelete = ReferenceOption.CASCADE)
    val startedAt = datetime("startedAt").defaultExpression(CurrentDateTime)
    val completedAt = datetime("completedAt").nullable()
    val status = enumerationByName("status", 20, ExecutionStatus::class).default(ExecutionStatus.RUNNING)
}

object ExecutionLogs : LongIdTable("execution_logs") {
    val stepExecution = reference("stepExecutionId", StepExecutions, onDelete = ReferenceOption.CASCADE)
    val timestamp = datetime("timestamp").defaultExpression(CurrentDateTime)
    val logLevel = enumerationByName("logLevel", 10, LogLevel::class)
    val message = text("message")
}

object BenchmarkingData : LongIdTable("benchmarking_data") {
    val workflowExecution = reference("workflowExecutionId", WorkflowExecutions, onDelete = ReferenceOption.CASCADE)
    val totalExecutionTime = decimal("totalExecutionTime", 10, 2)
    val totalSteps = integer("totalSteps")
    val aiDeterminedSteps = integer("aiDeterminedSteps")
    val predeterminedSteps = integer("predeterminedSteps")
}

object StepBenchmarkingDatas : LongIdTable("step_benchmarking_data") {
    val stepExecution = reference("stepExecutionId", StepExecutions, onDelete = ReferenceOption.CASCADE)
    val executionTime = decimal("executionTime", 10, 2)
    val resourceUsage = json("resourceUsage", ResourceUsageDto.jsonConfig, ResourceUsageDto.serializer()).nullable()
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
    val executedAt = datetime("executedAt").defaultExpression(CurrentDateTime)
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
