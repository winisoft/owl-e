package com.wini.owle.models

import com.wini.owle.dto.environs.PackageRepoTypeDto
import com.wini.owle.dto.runs.*
import com.wini.owle.dto.transforms.TransformOccurrenceDto
import com.wini.owle.dto.typing.VarianceCardinalityDto
import com.wini.owle.dto.user.CardBrandDto
import com.wini.owle.dto.user.SubscriptionStatusDto
import com.wini.owle.dto.workflows.StoreMethodDto
import com.wini.owle.models.metadata.UserDesignedMetadata
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
data class DataPoint(
    val id: Long,
    val workflowId: Long,
    val name: String,
    val description: String? = null,
    val presetValue: String? = null,
    val storeMethod: StoreMethodDto,
    val typeDeclaration: TypeDeclaration,
    val createdAt: String,
    val updatedAt: String
) {

    val created: LocalDateTime
        get() = LocalDateTime.parse(createdAt)

    val updated: LocalDateTime
        get() = LocalDateTime.parse(updatedAt)
}

@Serializable
data class WorkflowFolder(
    val id: Long,
    val name: String,
    private val createdAt: String,
    private val updatedAt: String,
    val subfolders: List<WorkflowFolder> = emptyList(),
    val childWorkflowsCount: Int = 0,
    val parentFolder: WorkflowFolder? = null,
    val description: String? = null,
    val img: ByteArray? = null,
): GroupType by GroupType.WorkflowFolder,
    GroupedElementType by GroupedElementType.WorkflowFolder {

    val created: LocalDateTime
        get() = LocalDateTime.parse(createdAt)

    val updated: LocalDateTime
        get() = LocalDateTime.parse(updatedAt)

    val hasSubfolders: Boolean
        get() = subfolders.isNotEmpty()

    val workflowsCountText: String
        get() = "$childWorkflowsCount ${if (childWorkflowsCount == 1) "Workflow" else "Workflows"}"

    val childFoldersText: String
        get() = "${subfolders.size} ${if (subfolders.size == 1) "Child Folder" else "Child Folders"}"
}


@Serializable
data class PackageRepo(
    val id: Long,
    val sourceType: PackageRepoTypeDto,
    val sourceUrl: String?
)

@Serializable
data class Tool(
    val id: Long,
    val name: String,
    val description: String? = null,
    val body: String,
    val returnTypeDeclaration: TypeDeclaration?
)

@Serializable
data class Workflow(
    val id: Long,
    val name: String,
    val description: String?,
    val pythonVersion: String,
    val parentFolder: WorkflowFolder?,
    val dataPoints: List<DataPoint> = emptyList(),
    val steps: List<Step>,
    val phases: List<Phase>,
    val metadata: UserDesignedMetadata,
    val createdAt: String,
    val updatedAt: String
) : GroupType by GroupType.Workflow,
    GroupedElementType by GroupedElementType.Workflow {

        val dataPointsText: String
            get() = "${dataPoints.size} ${if (dataPoints.size == 1) "Data Point" else "Data Points"}"

        val rootLevelChildren: List<GroupedElementType>
            get() {
                return listOf(
                    steps.filter { it.atWorkflowRootLevel },
                    phases.filter { it.atWorkflowRootLevel }
                ).sortedBy {
                    (it as DisplayOrderSubject).displayOrder
                }.map {
                    it as GroupedElementType
                }
            }
    }

@Serializable
data class WorkflowSummary(
    val id: Long,
    val name: String,
    val description: String?,
    val stepsCount: Int,
    val phasesCount: Int,
    val dataPointsCount: Int,
    val junctionsCount: Int,
    private val createdAt: String,
    private val updatedAt: String
) : GroupType by GroupType.Workflow,
    GroupedElementType by GroupedElementType.Workflow {

    val hasPhases: Boolean
        get() = phasesCount > 0

    val hasJunctions: Boolean
        get() = phasesCount > 0

    val stepsCountText: String
        get() = "$stepsCount ${if (stepsCount == 1) "Step" else "Steps"}"

    val phasesCountText: String
        get() = "$phasesCount ${if (phasesCount == 1) "Phase" else "Phases"}"

    val dataPointsText: String
        get() = "$dataPointsCount ${if (dataPointsCount == 1) "Data Point" else "Data Points"}"

    val junctionsText: String
        get() = "$junctionsCount ${if (junctionsCount == 1) "Junction" else "Junctions"}"

    val created: LocalDateTime
        get() = LocalDateTime.parse(createdAt)

    val updated: LocalDateTime
        get() = LocalDateTime.parse(updatedAt)

    }

@Serializable
data class Phase(
    val id: Long = 0L,
    val workflow: Workflow,
    val parentPhase: Phase?,
    val name: String,
    val description: String?,
    override val displayOrder: Int
) : GroupType by GroupType.Phase,
    GroupedElementType by GroupedElementType.Phase,
    DisplayOrderSubject {

    val atWorkflowRootLevel: Boolean
        get() = parentPhase == null

    }

@Serializable
data class Step(
    val id: Long,
    val name: String,
    val description: String?,
    val parentPhase: Phase?,
    override val displayOrder: Int,
    val workflow: Workflow,
    val isWorkflowHead: Boolean,
    val tool: Tool,
    val nextStep: Step?
) : GroupedElementType by GroupedElementType.Step,
    DisplayOrderSubject {

        val atWorkflowRootLevel: Boolean
            get() = parentPhase == null
    }

@Serializable
data class VersionSpec(
    val id: Long,
    val versionMin: String? = null,
    val versionMax: String? = null,
    val versionExact: String? = null
)


@Serializable
data class Package(
    val id: Long,
    val name: String,
    val disambiguation: String?,
    val versionSpec: VersionSpec
)

@Serializable
data class ToolPackageDependency(
    val toolId: Long,
    val packageId: Long,
    val versionSpecId: Long
)

@Serializable
data class BenchmarkingData(
    val id: Long,
    val workflowExecution: WorkflowExecution,
    val totalExecutionTime: Double,
    val totalSteps: Int,
    val aiDeterminedSteps: Int,
    val predeterminedSteps: Int
)

@Serializable
data class ExecutionLog(
    val id: Long,
    val stepExecution: StepExecution,
    val timestamp: String,
    val logLevel: LogLevelDto,
    val message: String
)

@Serializable
data class StepBenchmarkingData(
    val id: Long,
    val stepExecution: StepExecution,
    val executionTime: Double
)

@Serializable
data class StepExecution(
    val id: Long,
    val workflowExecution: WorkflowExecution,
    val step: Step,
    val startedAt: LocalDateTime,
    val completedAt: LocalDateTime?,
    val status: ExecutionStatusDto
)

@Serializable
data class TestCase(
    val id: Long,
    val user: User,
    val name: String,
    val description: String?,
    val workflow: Workflow?,
    val step: Step?,
    val inputs: List<TestInput>,
    val expectedOutcomes: List<TestExpectedOutcome>,
    val results: List<TestResult>
)

@Serializable
data class TestInput(
    val id: Long,
    val testCase: TestCase,
    val dataPointName: String,
    val value: String?,
    val generationMethod: GenerationMethodDto
)

@Serializable
data class TestExpectedOutcome(
    val id: Long,
    val testCase: TestCase,
    val dataPointName: String,
    val expectedValue: String?,
    val comparisonOperator: ComparisonOperatorDto
)

@Serializable
data class TestResult(
    val id: Long,
    val testCase: TestCase,
    val workflowExecution: WorkflowExecution,
    val passed: Boolean,
    val executedAt: String
)

@Serializable
data class WorkflowExecution(
    val id: Long,
    val workflow: Workflow,
    val user: User,
    val startedAt: LocalDateTime,
    val completedAt: LocalDateTime?,
    val status: ExecutionStatusDto,
    val stepExecutions: List<StepExecution>
)

@Serializable
data class ErrorWatch(
    val id: Long,
    val step: Step,
    val errorType: String,
    val triggerStep: Step
)

@Serializable
data class WorkflowJunction(
    val id: Long,
    val sourceStep: Step,
    val targetWorkflow: Workflow,
    val triggerInquisition: Inquisition?
)

@Serializable
data class ErrorWatchFn(
    val id: Long,
    val errorWatch: ErrorWatch,
    val functionName: String,
    val position: Int
)

@Serializable
data class Inquisition(
    val id: Long,
    val step: Step,
    val className: String,
    val dataPoint: DataPoint,
    val onMetWorkflowStep: Step,
    val executionOrder: Int
)

@Serializable
data class PreReqInjection(
    val id: Long,
    val junction: WorkflowJunction,
    val sourceDataPoint: DataPoint,
    val injectedDataPoint: DataPoint,
    val name: String?,
    val description: String?
)

@Serializable
data class AppliedTransform(
    val id: Long,
    val transformation: Transformation,
    val occurrence: TransformOccurrenceDto,
    val resultType: TypeDeclaration,
    val dataPoint: DataPoint?
)

@Serializable
data class AppliedTransformArg(
    val id: Long,
    val appliedTransform: AppliedTransform,
    val transformationArg: TransformationArg,
    val value: String
)

@Serializable
data class TransformationArg(
    val id: Long,
    val transformation: Transformation,
    val typeDeclaration: TypeDeclaration,
    val name: String,
    val description: String?
)

@Serializable
data class ToolParam(
    val id: Long,
    val name: String,
    val tool: Tool,
    val typeDeclaration: TypeDeclaration,
    val hasDefault: Boolean,
)

@Serializable
data class Provision(
    val id: Long,
    val step: Step,
    val toolParam: ToolParam,
    val dataPoints: List<ProvisionDataPoint>
)

@Serializable
data class Transformation(
    val id: Long,
    val name: String,
    val description: String,
    val examples: String?,
    val returnsType: TypeDeclaration?
)

@Serializable
data class TransformationTargetType(
    val transformationId: Long,
    val typeDeclarationId: Long
)

@Serializable
data class TypeDeclaration(
    val id: Long,
    val typeDefinition: TypeDefinition,
    val pkg: Package,
    val alias: String? = null
)

@Serializable
data class TypeDefinition(
    val id: Long,
    val name: String,
    val namespace: String,
    val versionSpec: VersionSpec
)

@Serializable
data class TypeParameter(
    val id: Long,
    val typeDeclaration: TypeDeclaration,
    val position: Int,
    val parameterTypeDeclaration: TypeDeclaration
)

@Serializable
data class TypeBounds(
    val id: Long,
    val boundTypeDeclaration: TypeDeclaration,
    val typeVariableDeclaration: TypeDeclaration
)

@Serializable
data class TypeSchemaRef(
    val id: Long,
    val parentRef: TypeSchemaRef?,
    val name: String,
    val typeDeclaration: TypeDeclaration,
    val description: String?,
    val childRefs: List<TypeSchemaRef>
)

@Serializable
data class TypeVariance(
    val id: Long,
    val typeVariableDeclaration: TypeDeclaration,
    val varianceCardinality: VarianceCardinalityDto
)

@Serializable
data class Tag(
    val id: Long,
    val name: String,
    val description: String? = null,
    val user: User
)

@Serializable
data class StepTag(
    val stepId: Long,
    val tagId: Long
)

@Serializable
data class WorkflowTag(
    val workflowId: Long,
    val tagId: Long
)

@Serializable
data class User(
    val id: Long,
    val email: String,
    val password: String,
    val resetToken: String? = null,
    val oauthProvider: String? = null,
    val oauthId: String? = null,
    val subscriptionStatus: SubscriptionStatusDto,
    val apiKey: String? = null,
    val createdAt: String,
    val updatedAt: String,
    val paymentMethods: List<PaymentMethod> = emptyList()
)

@Serializable
data class Revision(
    val id: Long,
    val dataPoint: DataPoint,
    val step: Step,
)

@Serializable
data class ProvisionDataPoint(
    val provisionId: Long,
    val dataPointId: Long
)

@Serializable
data class TypeUnionMember(
    val unionTypeDeclarationId: Long,
    val memberTypeDeclarationId: Long
)

@Serializable
data class PaymentMethod(
    val id: Long,
    val stripePaymentMethodId: String?,
    val user: User,
    val cardBrand: CardBrandDto?,
    val lastFour: String?,
    val isDefault: Boolean,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
data class TagRelation(
    val parentTagId: Long,
    val childTagId: Long
)


