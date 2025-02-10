package com.wini.owle.entity

import com.wini.owle.dto.ResourceUsageDto
import com.wini.owle.entity.environs.PackageDao
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.environs.ToolPackageDependencyDao
import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.entity.runs.*
import com.wini.owle.entity.steps.*
import com.wini.owle.entity.transforms.*
import com.wini.owle.entity.typing.*
import com.wini.owle.entity.user.PaymentMethodDao
import com.wini.owle.entity.user.TagDao
import com.wini.owle.entity.user.TagRelationDao
import com.wini.owle.entity.user.UserDao
import com.wini.owle.entity.workflows.*
import com.wini.owle.models.metadata.UserDesignedMetadata
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import kotlin.reflect.KClass


@Suppress("UNCHECKED_CAST")
object EntityTestInstanceFactory {

    fun transformTargetTypeDao(
        transformation: TransformationDao? = null,
        eligibleType: TypeDeclarationDao? = null
    ): TransformTargetTypeDao {
        return TransformTargetTypeDao.new {
            this.transformation = transformation ?: transformationDao()
            this.eligibleTypeDeclaration = eligibleType ?: typeDeclarationDao()
        }
    }

    fun stepDao(
        workflow: WorkflowDao? = null,
        tool: ToolDao? = null,
        defaultNext: StepDao? = null
    ): StepDao {
        val commonlyHeldWorkflow = workflow ?: workflowDao()
        return StepDao.new {
            this.name = "Example Step"
            this.description = "A step in a workflow"
            this.workflow = commonlyHeldWorkflow
            this.isWorkflowHead = true
            this.tool = tool ?: toolDao()
            this.defaultNext = defaultNext
            this.displayOrder = 0
        }
    }

    fun packageRepoDao(
        sourceType: PackageRepoType? = null,
        sourceUrl: String? = null
    ): PackageRepoDao {
        return PackageRepoDao.new {
            this.sourceType = sourceType ?: PackageRepoType.PYPI
            this.sourceUrl = sourceUrl ?: "https://pypi.org"
        }
    }

    fun versionSpecDao(
        versionMin: String? = null,
        versionMax: String? = null,
        versionExact: String? = null
    ): VersionSpecDao {
        return VersionSpecDao.new {
            this.versionMin = versionMin ?: "1.0"
            this.versionMax = versionMax ?: "2.0"
            this.versionExact = versionExact ?: "3.0"
        }
    }

    fun workflowDao(
        parentFolder: WorkflowFolderDao? = null
    ): WorkflowDao {
        return WorkflowDao.new {
            name = "Example Workflow"
            description = "A sample workflow description"
            pythonVersion = "3.9"
            this.parentFolder = parentFolder ?: workflowFolderDao()
            metadata = UserDesignedMetadata.mock
        }
    }

    fun userDao(
        email: String? = null,
        password: String? = null,
        subscriptionStatus: SubscriptionStatus? = null
    ): UserDao {
        return UserDao.new {
            this.email = email ?: "jim@jim.com"
            this.password = password ?: "superSecretStuff1!"
            this.subscriptionStatus = subscriptionStatus ?: SubscriptionStatus.REGISTERED
        }
    }

    fun testCaseDao(
        workflow: WorkflowDao? = null,
        user: UserDao? = null
    ): TestCaseDao {
        return TestCaseDao.new {
            name = "Sample Test Case"
            description = "Validates a workflow"
            this.user = user ?: userDao()
            this.workflow = workflow ?: workflowDao()
        }
    }

    fun toolDao(returnTypeDeclaration: TypeDeclarationDao? = null): ToolDao {
        return ToolDao.new {
            name = "ExampleTool"
            description = "Tool description"
            body = "print('Hello, world!')"
            this.returnTypeDeclaration = returnTypeDeclaration ?: typeDeclarationDao()
        }
    }

    fun dataPointDao(
        workflow: WorkflowDao? = null,
        typeDeclaration: TypeDeclarationDao? = null
    ): DataPointDao {
        return DataPointDao.new {
            this.name = "ExampleDataPoint"
            this.typeDeclaration = typeDeclaration ?: typeDeclarationDao()
            this.description = "Data point description"
            this.workflow = workflow ?: workflowDao()
        }
    }

    fun toolParamDao(
        tool: ToolDao? = null,
        typeDeclaration: TypeDeclarationDao? = null
    ): ToolParamDao {
        return ToolParamDao.new {
            this.tool = tool ?: toolDao()
            this.name = "exampleParam"
            this.typeDeclaration = typeDeclaration ?: typeDeclarationDao()
        }
    }

    fun typeDeclarationDao(
        alias: String? = null,
        typeDefinition: TypeDefinitionDao? = null
    ): TypeDeclarationDao {
        return TypeDeclarationDao.new {
            this.alias = alias ?: "CustomType"
            this.typeDefinition = typeDefinition ?: typeDefinitionDao()
        }
    }

    fun typeDefinitionDao(
        name: String? = null,
        namespace: String? = null,
        versionSpec: VersionSpecDao? = null
    ): TypeDefinitionDao {
        return TypeDefinitionDao.new {
            this.name = name ?: "List"
            this.namespace = namespace ?: "builtins"
            this.versionSpec = versionSpec ?: versionSpecDao()
        }
    }

    fun paymentMethodDao(
        user: UserDao? = null,
        lastFour: String? = null
    ): PaymentMethodDao {
        return PaymentMethodDao.new {
            this.user = user ?: userDao()
            this.cardBrand = CardBrand.VISA
            this.lastFour = lastFour ?: "4242"
            this.isDefault = true
        }
    }

    fun typeParameterDao(
        typeDeclaration: TypeDeclarationDao? = null,
        position: Int? = null,
        parameterTypeDeclaration: TypeDeclarationDao? = null
    ): TypeParameterDao {
        return TypeParameterDao.new {
            this.typeDeclaration = typeDeclaration ?: typeDeclarationDao()
            this.position = position ?: 1
            this.parameterTypeDeclaration = parameterTypeDeclaration ?: typeDeclarationDao()
        }
    }

    fun toolPackageDependencyDao(
        tool: ToolDao? = null,
        pkg: PackageDao? = null,
        versionSpec: VersionSpecDao? = null
    ): ToolPackageDependencyDao {
        return ToolPackageDependencyDao.new {
            this.tool = tool ?: toolDao()
            this.packageEntity = pkg ?: packageDao()
            this.versionSpec = versionSpec ?: versionSpecDao()
        }
    }

    fun typeSchemaRefDao(
        name: String? = null,
        typeDeclaration: TypeDeclarationDao? = null,
        description: String? = null
    ): TypeSchemaRefDao {
        return TypeSchemaRefDao.new {
            this.parentRef = null
            this.name = name ?: "exampleSchema"
            this.typeDeclaration = typeDeclaration ?: typeDeclarationDao()
            this.description = description ?: "Schema reference description"
        }
    }

    fun typeVarianceDao(
        typeVariableDeclaration: TypeDeclarationDao? = null,
        varianceCardinality: VarianceCardinality? = null
    ): TypeVarianceDao {
        return TypeVarianceDao.new {
            this.typeVariableDeclaration = typeVariableDeclaration ?: typeDeclarationDao()
            this.varianceCardinality = varianceCardinality ?: VarianceCardinality.COVARIANT
        }
    }

    fun phaseDao(
        workflow: WorkflowDao? = null,
        parentPhase: PhaseDao? = null
    ): PhaseDao {
        return PhaseDao.new {
            this.workflow = workflow ?: workflowDao()
            this.parentPhase = parentPhase
            this.name = "InitialPhase"
            this.description = "Description of the phase"
            this.displayOrder = 0
        }
    }

    fun provisionDao(
        step: StepDao? = null,
        toolParam: ToolParamDao? = null
    ): ProvisionDao {
        return ProvisionDao.new {
            this.step = step ?: stepDao()
            this.toolParam = toolParam ?: toolParamDao()
        }
    }

    fun revisionDao(
        dataPoint: DataPointDao? = null,
        step: StepDao? = null,
        transformation: TransformationDao? = null
    ): RevisionDao {
        return RevisionDao.new {
            this.dataPoint = dataPoint ?: dataPointDao()
            this.step = step ?: stepDao()
        }
    }

    fun testResultDao(
        testCase: TestCaseDao? = null,
        workflowExecution: WorkflowExecutionDao? = null
    ): TestResultDao {
        return TestResultDao.new {
            this.testCase = testCase ?: testCaseDao()
            this.workflowExecution = workflowExecution ?: workflowExecutionDao()
            this.passed = true
        }
    }

    fun workflowFolderDao(
        parentFolder: WorkflowFolderDao? = null
    ): WorkflowFolderDao {
        return WorkflowFolderDao.new {
            this.parentFolder = parentFolder
            this.name = "ExampleFolder"
            this.description = "Folder description"
        }
    }

    fun workflowExecutionDao(
        workflow: WorkflowDao? = null,
        user: UserDao? = null
    ): WorkflowExecutionDao {
        return WorkflowExecutionDao.new {
            this.workflow = workflow ?: workflowDao()
            this.user = user ?: userDao()
            this.status = ExecutionStatus.SKIPPED
            this.startedAt = Instant.parse("2024-12-20T10:00:00Z").toLocalDateTime(TimeZone.UTC)
            this.completedAt = Instant.parse("2024-12-20T10:00:00Z").toLocalDateTime(TimeZone.UTC)
        }
    }

    fun executionLogDao(
        stepExecution: StepExecutionDao? = null,
        logLevel: LogLevel? = null,
        message: String? = null
    ): ExecutionLogDao {
        return ExecutionLogDao.new {
            this.stepExecution = stepExecution ?: stepExecutionDao()
            this.timestamp = Instant.parse("2024-12-20T10:00:00Z").toLocalDateTime(TimeZone.UTC)
            this.logLevel = logLevel ?: LogLevel.WARNING
            this.message = message ?: "Execution started"
        }
    }

    fun errorWatchDao(
        step: StepDao? = null,
        onMetStep: StepDao? = null
    ): ErrorWatchDao {
        return ErrorWatchDao.new {
            this.step = step ?: stepDao()
            this.errorType = "RuntimeException"
            this.onMetStep = onMetStep ?: stepDao()
        }
    }

    fun errorWatchFnDao(
        errorWatch: ErrorWatchDao? = null
    ): ErrorWatchFnDao {
        return ErrorWatchFnDao.new {
            this.errorWatch = errorWatch ?: errorWatchDao()
            this.functionName = "exampleFunction"
            this.position = 1
        }
    }

    fun inquisitionDao(
        workflowStep: StepDao? = null,
        dataPoint: DataPointDao? = null,
        onMetWorkflowStep: StepDao? = null
    ): InquisitionDao {
        return InquisitionDao.new {
            this.workflowStep = workflowStep ?: stepDao()
            this.className = "Condition"
            this.dataPoint = dataPoint ?: dataPointDao()
            this.onMetWorkflowStep = onMetWorkflowStep ?: stepDao()
            this.executionOrder = 1
        }
    }

    fun stepExecutionDao(
        workflowExecution: WorkflowExecutionDao? = null,
        step: StepDao? = null,
        status: ExecutionStatus? = null
    ): StepExecutionDao {
        return StepExecutionDao.new {
            this.workflowExecution = workflowExecution ?: workflowExecutionDao()
            this.step = step ?: stepDao()
            this.startedAt = Instant.parse("2024-12-20T10:00:00Z").toLocalDateTime(TimeZone.UTC)
            this.completedAt = Instant.parse("2024-12-20T10:00:00Z").toLocalDateTime(TimeZone.UTC)
            this.status = status ?: ExecutionStatus.RUNNING
        }
    }

    fun benchmarkingDataDao(
        workflowExecution: WorkflowExecutionDao? = null,
        totalExecutionTime: BigDecimal = 127.0.toBigDecimal(),
        totalSteps: Int = 10,
        aiDeterminedSteps: Int = 3,
        predeterminedSteps: Int = 8
    ): BenchmarkingDataDao {
        return BenchmarkingDataDao.new {
            this.workflowExecution = workflowExecution ?: workflowExecutionDao()
            this.totalExecutionTime = totalExecutionTime
            this.totalSteps = totalSteps
            this.aiDeterminedSteps = aiDeterminedSteps
            this.predeterminedSteps = predeterminedSteps
        }
    }

    fun workflowJunctionDao(
        sourceStep: StepDao? = null,
        targetWorkflow: WorkflowDao? = null,
        triggerInquisition: InquisitionDao? = null
    ): WorkflowJunctionDao {
        return WorkflowJunctionDao.new {
            this.sourceStep = sourceStep ?: stepDao()
            this.targetWorkflow = targetWorkflow ?: workflowDao()
            this.triggerInquisition = triggerInquisition ?: inquisitionDao()
        }
    }

    fun transformationDao(
        returnsTypeDeclaration: TypeDeclarationDao? = null
    ): TransformationDao {
        return TransformationDao.new {
            this.name = "Example Transformation"
            this.description = "Transforms data"
            this.examples = "Input -> Output"
            this.returnsTypeDeclaration = returnsTypeDeclaration ?: typeDeclarationDao()
        }
    }

    fun tagDao(
        name: String? = null,
        description: String? = null,
        user: UserDao? = null
    ): TagDao {
        return TagDao.new {
            this.name = name ?: "Example Tag"
            this.description = description ?: "A descriptive tag"
            this.user = user ?: userDao()
        }
    }

    fun testInputDao(
        testCase: TestCaseDao? = null
    ): TestInputDao {
        return TestInputDao.new {
            this.testCase = testCase ?: testCaseDao()
            this.dataPointName = "exampleDataPoint"
            this.value = "testValue"
            this.generationMethod = GenerationMethod.MANUAL
        }
    }

    fun testExpectedOutcomeDao(
        testCase: TestCaseDao? = null
    ): TestExpectedOutcomeDao {
        return TestExpectedOutcomeDao.new {
            this.testCase = testCase ?: testCaseDao()
            this.dataPointName = "expectedDataPoint"
            this.expectedValue = "expectedValue"
            this.comparisonOperator = ComparisonOperator.CONTAINS
        }
    }

    fun preReqInjectionDao(
        junction: WorkflowJunctionDao? = null,
        sourceDataPoint: DataPointDao? = null,
        injectedDataPoint: DataPointDao? = null
    ): PreReqInjectionDao {
        return PreReqInjectionDao.new {
            this.junction = junction ?: workflowJunctionDao()
            this.sourceDataPoint = sourceDataPoint ?: dataPointDao()
            this.injectedDataPoint = injectedDataPoint ?: dataPointDao()
            this.name = "Example Injection"
            this.description = "A prerequisite data injection"
        }
    }

    fun appliedTransformDao(
        transformation: TransformationDao? = null,
        resultType: TypeDeclarationDao? = null,
        dataPoint: DataPointDao? = null,
        toolResultStep: StepDao? = null
    ): AppliedTransformDao {
        return AppliedTransformDao.new {
            this.transformation = transformation ?: transformationDao()
            this.occurrence = TransformOccurrence.PREREQINJECTION
            this.resultType = resultType ?: typeDeclarationDao()
            this.dataPoint = dataPoint ?: dataPointDao()
        }
    }

    fun appliedTransformArgDao(
        appliedTransform: AppliedTransformDao? = null,
        transformationArg: TransformationArgDao? = null
    ): AppliedTransformArgDao {
        return AppliedTransformArgDao.new {
            this.appliedTransform = appliedTransform ?: appliedTransformDao()
            this.transformationArg = transformationArg ?: transformationArgDao()
            this.value = "exampleArgumentValue"
        }
    }

    fun provisionDataPointDao(
        provision: ProvisionDao? = null,
        dataPoint: DataPointDao? = null
    ): ProvisionDataPointDao {
        return ProvisionDataPointDao.new {
            this.provision = provision ?: provisionDao()
            this.dataPoint = dataPoint ?: dataPointDao()
        }
    }

    fun transformationArgDao(
        transformation: TransformationDao? = null,
        typeDeclaration: TypeDeclarationDao? = null
    ): TransformationArgDao {
        return TransformationArgDao.new {
            this.transformation = transformation ?: transformationDao()
            this.typeDeclaration = typeDeclaration ?: typeDeclarationDao()
            this.name = "Argument Name"
            this.description = "Argument Description"
        }
    }

    fun tagRelationDao(
        parentTag: TagDao? = null,
        childTag: TagDao? = null
    ): TagRelationDao {
        return TagRelationDao.new {
            this.parentTag = parentTag ?: tagDao()
            this.childTag = childTag ?: tagDao()
        }
    }

    fun packageDao(
        name: String? = null,
        disambiguation: String? = null,
        versionSpec: VersionSpecDao? = null
    ): PackageDao {
        return PackageDao.new {
            this.name = name ?: "Example Package"
            this.disambiguation = disambiguation ?: "Example Disambiguation"
            this.versionSpec = versionSpec ?: versionSpecDao()
        }
    }

    fun stepBenchmarkingDataDao(
        stepExecution: StepExecutionDao? = null
    ): StepBenchmarkingDataDao {
        return StepBenchmarkingDataDao.new {
            this.stepExecution = stepExecution ?: stepExecutionDao()
            this.executionTime = BigDecimal.valueOf(123.45)
            this.resourceUsage = ResourceUsageDto()
        }
    }

    fun typeUnionMemberDao(
        unionTypeDeclaration: TypeDeclarationDao? = null,
        memberTypeDeclaration: TypeDeclarationDao? = null
    ): TypeUnionMemberDao {
        return TypeUnionMemberDao.new {
            this.unionTypeDeclaration = unionTypeDeclaration ?: typeDeclarationDao()
            this.memberTypeDeclaration = memberTypeDeclaration ?: typeDeclarationDao()
        }
    }

    fun typeBoundDao(
        typeVariableDeclaration: TypeDeclarationDao = typeDeclarationDao(),
        boundTypeDeclaration: TypeDeclarationDao = typeDeclarationDao()
    ) : TypeBoundDao {
        return TypeBoundDao.new {
            this.typeVariableDeclaration = typeVariableDeclaration
            this.boundTypeDeclaration = boundTypeDeclaration
        }
    }
}