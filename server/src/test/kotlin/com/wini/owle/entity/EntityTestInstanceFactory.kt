package com.wini.owle.entity

import com.wini.owle.entity.environs.*
import com.wini.owle.entity.runs.*
import com.wini.owle.entity.steps.*
import com.wini.owle.entity.transforms.*
import com.wini.owle.entity.typing.*
import com.wini.owle.entity.user.*
import com.wini.owle.entity.workflows.*
import com.wini.owle.models.metadata.UserDesignedMetadata
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.math.BigDecimal
import io.mockk.mockk
import io.mockk.*
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.statements.api.ExposedBlob

@Suppress("UNCHECKED_CAST")
object EntityTestInstanceFactory {

    fun appliedTransformArgDao(
        id: Long = 1,
        appliedTransform: AppliedTransformDao = appliedTransformDao(),
        transformationArg: TransformationArgDao = transformationArgDao(),
        value: String = "exampleArgumentValue"
    ): AppliedTransformArgDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.appliedTransform } returns appliedTransform
        every { this@mockk.transformationArg } returns transformationArg
        every { this@mockk.value } returns value
    }

    fun appliedTransformDao(
        id: Long = 1,
        transformation: TransformationDao = transformationDao(),
        occurrence: TransformOccurrence = TransformOccurrence.PREREQINJECTION,
        resultType: TypeDeclarationDao = typeDeclarationDao(),
        dataPoint: DataPointDao? = null
    ): AppliedTransformDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.transformation } returns transformation
        every { this@mockk.occurrence } returns occurrence
        every { this@mockk.resultType } returns resultType
        every { this@mockk.dataPoint } returns dataPoint
    }

    fun benchmarkingDataDao(
        id: Long = 1,
        workflowExecution: WorkflowExecutionDao = workflowExecutionDao(),
        totalExecutionTime: BigDecimal = 127.0.toBigDecimal(),
        totalSteps: Int = 10,
        aiDeterminedSteps: Int = 3,
        predeterminedSteps: Int = 8
    ): BenchmarkingDataDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.workflowExecution } returns workflowExecution
        every { this@mockk.totalExecutionTime } returns totalExecutionTime
        every { this@mockk.totalSteps } returns totalSteps
        every { this@mockk.aiDeterminedSteps } returns aiDeterminedSteps
        every { this@mockk.predeterminedSteps } returns predeterminedSteps
    }

    fun dataPointDao(
        id: Long = 1,
        workflow: WorkflowDao = workflowDao(),
        name: String = "ExampleDataPoint",
        description: String? = "Data point description",
        presetValue: String? = null,
        storeMethod: StoreMethod = StoreMethod.BLOB,
        typeDeclaration: TypeDeclarationDao = typeDeclarationDao(),
        createdAt: LocalDateTime = mockk(),
        updatedAt: LocalDateTime = mockk()
    ): DataPointDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.workflow } returns workflow
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.presetValue } returns presetValue
        every { this@mockk.storeMethod } returns storeMethod
        every { this@mockk.typeDeclaration } returns typeDeclaration
        every { this@mockk.createdAt } returns createdAt
        every { this@mockk.updatedAt } returns updatedAt
    }

    fun errorWatchDao(
        id: Long = 1,
        step: StepDao = stepDao(),
        errorType: String = "RuntimeException",
        onMetWorkflowStep: StepDao = stepDao()
    ): ErrorWatchDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.step } returns step
        every { this@mockk.errorType } returns errorType
        every { this@mockk.onMetStep } returns onMetWorkflowStep
    }

    fun errorWatchFnDao(
        id: Long = 1,
        errorWatch: ErrorWatchDao = errorWatchDao(),
        functionName: String = "exampleFunction",
        position: Int = 1
    ): ErrorWatchFnDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.errorWatch } returns errorWatch
        every { this@mockk.functionName } returns functionName
        every { this@mockk.position } returns position
    }

    fun executionLogDao(
        id: Long = 1,
        stepExecution: StepExecutionDao = stepExecutionDao(),
        timestamp: LocalDateTime = mockk(),
        logLevel: LogLevel = LogLevel.WARNING,
        message: String = "Execution started"
    ): ExecutionLogDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.stepExecution } returns stepExecution
        every { this@mockk.timestamp } returns timestamp
        every { this@mockk.logLevel } returns logLevel
        every { this@mockk.message } returns message
    }

    fun inquisitionDao(
        id: Long = 1,
        step: StepDao = stepDao(),
        className: String = "Condition",
        dataPoint: DataPointDao = dataPointDao(),
        onMetWorkflowStep: StepDao = stepDao(),
        executionOrder: Int = 1
    ): InquisitionDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.onMetWorkflowStep } returns onMetWorkflowStep
        every { this@mockk.className } returns className
        every { this@mockk.dataPoint } returns dataPoint
        every { this@mockk.onMetWorkflowStep } returns onMetWorkflowStep
        every { this@mockk.executionOrder } returns executionOrder
    }

    fun packageDao(
        id: Long = 1,
        name: String = "Example Package",
        disambiguation: String? = "Example Disambiguation",
        versionSpec: VersionSpecDao = versionSpecDao()
    ): PackageDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.name } returns name
        every { this@mockk.disambiguation } returns disambiguation
        every { this@mockk.versionSpec } returns versionSpec
    }

    fun packageRepoDao(
        id: Long = 1,
        sourceType: PackageRepoType = PackageRepoType.PYPI,
        sourceUrl: String? = "https://pypi.org"
    ): PackageRepoDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.sourceType } returns sourceType
        every { this@mockk.sourceUrl } returns sourceUrl
    }

    fun paymentMethodDao(
        id: Long = 1,
        stripePaymentMethodId: String? = "pm_123example",
        user: UserDao = userDao(),
        cardBrand: CardBrand? = CardBrand.VISA,
        lastFour: String? = "4242",
        isDefault: Boolean = true,
        createdAt: LocalDateTime = mockk(),
        updatedAt: LocalDateTime = mockk()
    ): PaymentMethodDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.stripePaymentMethodId } returns stripePaymentMethodId
        every { this@mockk.user } returns user
        every { this@mockk.cardBrand } returns cardBrand
        every { this@mockk.lastFour } returns lastFour
        every { this@mockk.isDefault } returns isDefault
        every { this@mockk.createdAt } returns createdAt
        every { this@mockk.updatedAt } returns updatedAt
    }

    fun phaseDao(
        id: Long = 1,
        workflow: WorkflowDao = workflowDao(),
        parentPhase: PhaseDao? = null,
        name: String = "Initial Phase",
        description: String? = "Description of the phase",
        displayOrder: Int = 0
    ): PhaseDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.workflow } returns workflow
        every { this@mockk.parentPhase } returns parentPhase
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.displayOrder } returns displayOrder
    }

    fun preReqInjectionDao(
        id: Long = 1,
        junction: WorkflowJunctionDao = workflowJunctionDao(),
        sourceDataPoint: DataPointDao = dataPointDao(),
        injectedDataPoint: DataPointDao = dataPointDao(),
        name: String? = "Example Injection",
        description: String? = "A prerequisite data injection"
    ): PreReqInjectionDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.junction } returns junction
        every { this@mockk.sourceDataPoint } returns sourceDataPoint
        every { this@mockk.injectedDataPoint } returns injectedDataPoint
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
    }

    fun provisionDao(
        id: Long = 1,
        step: StepDao = stepDao(),
        toolParam: ToolParamDao = toolParamDao(),
        dataPoints: SizedIterable<DataPointDao> = mockk()
    ): ProvisionDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.step } returns step
        every { this@mockk.toolParam } returns toolParam
        every { this@mockk.dataPoint } returns dataPoints
    }

    fun provisionDataPointDao(
        provisionId: Long = 1,
        dataPointId: Long = 1,
        provision: ProvisionDao = provisionDao(provisionId),
        dataPoint: DataPointDao = dataPointDao(dataPointId)
    ): ProvisionDataPointDao = mockk {
        val compositeID = ProvisionDataPointDao.buildId(provisionId, dataPointId)
        every { this@mockk.id } returns EntityID(compositeID, mockk())
        every { this@mockk.provision } returns provision
        every { this@mockk.dataPoint } returns dataPoint
    }

    fun revisionDao(
        id: Long = 1,
        dataPoint: DataPointDao = dataPointDao(),
        step: StepDao = stepDao()
    ): RevisionDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.dataPoint } returns dataPoint
        every { this@mockk.step } returns step
    }

    fun stepBenchmarkingDataDao(
        id: Long = 1,
        stepExecution: StepExecutionDao = stepExecutionDao(),
        executionTime: BigDecimal = 123.45.toBigDecimal()
    ): StepBenchmarkingDataDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.stepExecution } returns stepExecution
        every { this@mockk.executionTime } returns executionTime
    }

    fun stepDao(
        id: Long = 1,
        name: String = "Example Step",
        description: String? = "A step in a workflow",
        parentPhase: PhaseDao? = null,
        displayOrder: Int = 0,
        workflow: WorkflowDao = workflowDao(),
        isWorkflowHead: Boolean = true,
        tool: ToolDao = toolDao(),
        defaultNext: StepDao? = null
    ): StepDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.parentPhase } returns parentPhase
        every { this@mockk.displayOrder } returns displayOrder
        every { this@mockk.workflow } returns workflow
        every { this@mockk.isWorkflowHead } returns isWorkflowHead
        every { this@mockk.tool } returns tool
        every { this@mockk.defaultNext } returns defaultNext
    }

    fun stepExecutionDao(
        id: Long = 1,
        workflowExecution: WorkflowExecutionDao = workflowExecutionDao(),
        step: StepDao = stepDao(),
        startedAt: LocalDateTime = Instant.parse("2024-12-20T10:00:00Z").toLocalDateTime(TimeZone.UTC),
        completedAt: LocalDateTime? = Instant.parse("2024-12-20T10:00:00Z").toLocalDateTime(TimeZone.UTC),
        status: ExecutionStatus = ExecutionStatus.RUNNING
    ): StepExecutionDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.workflowExecution } returns workflowExecution
        every { this@mockk.step } returns step
        every { this@mockk.startedAt } returns startedAt
        every { this@mockk.completedAt } returns completedAt
        every { this@mockk.status } returns status
    }

    fun tagDao(
        id: Long = 1,
        name: String = "Example Tag",
        description: String? = "A descriptive tag",
        user: UserDao = userDao()
    ): TagDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.user } returns user
    }

    fun tagRelationDao(
        parentTagId: Long = 1,
        childTagId: Long = 1,
        parentTag: TagDao = tagDao(parentTagId),
        childTag: TagDao = tagDao(childTagId)
    ): TagRelationDao = mockk {
        val compositeID = TagRelationDao.buildId(parentTagId, childTagId)
        every { this@mockk.id } returns EntityID(compositeID, mockk())
        every { this@mockk.parentTag } returns parentTag
        every { this@mockk.childTag } returns childTag
    }

    fun testCaseDao(
        id: Long = 1,
        user: UserDao = userDao(),
        name: String = "Sample Test Case",
        description: String? = "Validates a workflow",
        workflow: WorkflowDao? = null,
        step: StepDao? = null,
        inputs: List<TestInputDao> = emptyList(),
        expectedOutcomes: List<TestExpectedOutcomeDao> = emptyList(),
        results: List<TestResultDao> = emptyList()
    ): TestCaseDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.user } returns user
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.workflow } returns workflow
        every { this@mockk.step } returns step
//        every { this@mockk.inputs } returns inputs
//        every { this@mockk.expectedOutcomes } returns expectedOutcomes
//        every { this@mockk.results } returns results
    }

    fun testExpectedOutcomeDao(
        id: Long = 1,
        testCase: TestCaseDao = testCaseDao(),
        dataPointName: String = "expectedDataPoint",
        expectedValue: String? = "expectedValue",
        comparisonOperator: ComparisonOperator = ComparisonOperator.CONTAINS
    ): TestExpectedOutcomeDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.testCase } returns testCase
        every { this@mockk.dataPointName } returns dataPointName
        every { this@mockk.expectedValue } returns expectedValue
        every { this@mockk.comparisonOperator } returns comparisonOperator
    }

    fun testInputDao(
        id: Long = 1,
        testCase: TestCaseDao = testCaseDao(),
        dataPointName: String = "exampleDataPoint",
        value: String? = "testValue",
        generationMethod: GenerationMethod = GenerationMethod.MANUAL
    ): TestInputDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.testCase } returns testCase
        every { this@mockk.dataPointName } returns dataPointName
        every { this@mockk.value } returns value
        every { this@mockk.generationMethod } returns generationMethod
    }

    fun testResultDao(
        id: Long = 1,
        testCase: TestCaseDao = testCaseDao(),
        workflowExecution: WorkflowExecutionDao = workflowExecutionDao(),
        passed: Boolean = true,
        executedAt: LocalDateTime = mockk()
    ): TestResultDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.testCase } returns testCase
        every { this@mockk.workflowExecution } returns workflowExecution
        every { this@mockk.passed } returns passed
        every { this@mockk.executedAt } returns executedAt
    }

    fun toolDao(
        id: Long = 1,
        name: String = "ExampleTool",
        description: String? = "Tool description",
        body: String = "fun ok()",
        returnTypeDeclaration: TypeDeclarationDao? = null
    ): ToolDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.body } returns body
        every { this@mockk.returnTypeDeclaration } returns returnTypeDeclaration
    }

    fun toolPackageDependencyDao(
        toolId: Long = 1,
        packageId: Long = 1,
        versionSpecId: Long = 1,
        tool: ToolDao = toolDao(toolId),
        packageEntity: PackageDao = packageDao(packageId),
        versionSpec: VersionSpecDao = versionSpecDao(versionSpecId)
    ): ToolPackageDependencyDao = mockk {
        val compositeID = ToolPackageDependencyDao.buildId(toolId, packageId, versionSpecId)
        every { this@mockk.id } returns EntityID(compositeID, mockk())
        every { this@mockk.tool } returns tool
        every { this@mockk.packageEntity } returns packageEntity
        every { this@mockk.versionSpec } returns versionSpec
    }

    fun toolParamDao(
        id: Long = 1,
        name: String = "exampleParam",
        tool: ToolDao = toolDao(),
        typeDeclaration: TypeDeclarationDao = typeDeclarationDao(),
        hasDefault: Boolean = false
    ): ToolParamDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.name } returns name
        every { this@mockk.tool } returns tool
        every { this@mockk.typeDeclaration } returns typeDeclaration
        every { this@mockk.hasDefault } returns hasDefault
    }

    fun transformationArgDao(
        id: Long = 1,
        transformation: TransformationDao = transformationDao(),
        typeDeclaration: TypeDeclarationDao = typeDeclarationDao(),
        name: String = "Argument Name",
        description: String? = "Argument Description"
    ): TransformationArgDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.transformation } returns transformation
        every { this@mockk.typeDeclaration } returns typeDeclaration
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
    }

    fun transformationDao(
        id: Long = 1,
        name: String = "Example Transformation",
        description: String = "Transforms data",
        examples: String? = "Input -> Output",
        returnsType: TypeDeclarationDao? = null
    ): TransformationDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.examples } returns examples
        every { this@mockk.returnsTypeDeclaration } returns returnsType
    }

    fun transformationTargetTypeDao(
        transformationId: Long = 1,
        typeDeclarationId: Long = 1,
        transformation: TransformationDao = transformationDao(transformationId),
        typeDeclaration: TypeDeclarationDao = typeDeclarationDao(typeDeclarationId)
    ): TransformTargetTypeDao = mockk {
        val compositeID = TransformTargetTypeDao.buildId(transformationId, typeDeclarationId)
        every { this@mockk.id } returns EntityID(compositeID, mockk())
        every { this@mockk.transformation } returns transformation
        every { this@mockk.eligibleTypeDeclaration } returns typeDeclaration
    }

    fun typeBoundDao(
        id: Long = 1,
        boundTypeDeclaration: TypeDeclarationDao = typeDeclarationDao(),
        typeVariableDeclaration: TypeDeclarationDao = typeDeclarationDao()
    ): TypeBoundDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.boundTypeDeclaration } returns boundTypeDeclaration
        every { this@mockk.typeVariableDeclaration } returns typeVariableDeclaration
    }

    fun typeDeclarationDao(
        id: Long = 1,
        typeDefinition: TypeDefinitionDao = typeDefinitionDao(),
        alias: String? = "CustomType"
    ): TypeDeclarationDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.typeDefinition } returns typeDefinition
        every { this@mockk.alias } returns alias
    }

    fun typeDefinitionDao(
        id: Long = 1,
        name: String = "List",
        namespace: String = "builtins",
        versionSpec: VersionSpecDao = versionSpecDao()
    ): TypeDefinitionDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.name } returns name
        every { this@mockk.namespace } returns namespace
        every { this@mockk.versionSpec } returns versionSpec
    }

    fun typeParameterDao(
        id: Long = 1,
        typeDeclaration: TypeDeclarationDao = typeDeclarationDao(),
        position: Int = 1,
        parameterTypeDeclaration: TypeDeclarationDao = typeDeclarationDao()
    ): TypeParameterDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.typeDeclaration } returns typeDeclaration
        every { this@mockk.position } returns position
        every { this@mockk.parameterTypeDeclaration } returns parameterTypeDeclaration
    }

    fun typeSchemaRefDao(
        id: Long = 1,
        parentRef: TypeSchemaRefDao? = null,
        name: String = "exampleSchema",
        typeDeclaration: TypeDeclarationDao = typeDeclarationDao(),
        description: String? = "Schema reference description",
        childRefs: List<TypeSchemaRefDao> = emptyList()
    ): TypeSchemaRefDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.parentRef } returns parentRef
        every { this@mockk.name } returns name
        every { this@mockk.typeDeclaration } returns typeDeclaration
        every { this@mockk.description } returns description
        //every { this@mockk.childRefs } returns childRefs
    }

    fun typeUnionMemberDao(
        unionTypeDeclarationId: Long = 1,
        memberTypeDeclarationId: Long = 1,
        unionTypeDeclaration: TypeDeclarationDao = typeDeclarationDao(unionTypeDeclarationId),
        memberTypeDeclaration: TypeDeclarationDao = typeDeclarationDao(memberTypeDeclarationId)
    ): TypeUnionMemberDao = mockk {
        val compositeID = TagRelationDao.buildId(unionTypeDeclarationId, memberTypeDeclarationId)
        every { this@mockk.id } returns EntityID(compositeID, mockk())
        every { this@mockk.unionTypeDeclaration } returns unionTypeDeclaration
        every { this@mockk.memberTypeDeclaration } returns memberTypeDeclaration
    }

    fun typeVarianceDao(
        id: Long = 1,
        typeVariableDeclaration: TypeDeclarationDao = typeDeclarationDao(),
        varianceCardinality: VarianceCardinality = VarianceCardinality.COVARIANT
    ): TypeVarianceDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.typeVariableDeclaration } returns typeVariableDeclaration
        every { this@mockk.varianceCardinality } returns varianceCardinality
    }

    fun userDao(
        id: Long = 1,
        email: String = "jim@jim.com",
        password: String = "superSecretStuff1!",
        resetToken: String? = null,
        oauthProvider: String? = null,
        oauthId: String? = null,
        subscriptionStatus: SubscriptionStatus = SubscriptionStatus.REGISTERED,
        apiKey: String? = null,
        createdAt: LocalDateTime = mockk(),
        updatedAt: LocalDateTime = mockk(),
        paymentMethods: List<PaymentMethodDao> = emptyList()
    ): UserDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.email } returns email
        every { this@mockk.password } returns password
        every { this@mockk.resetToken } returns resetToken
        every { this@mockk.oauthProvider } returns oauthProvider
        every { this@mockk.oauthId } returns oauthId
        every { this@mockk.subscriptionStatus } returns subscriptionStatus
        every { this@mockk.apiKey } returns apiKey
        every { this@mockk.createdAt } returns createdAt
        every { this@mockk.updatedAt } returns updatedAt
        //every { this@mockk.paymentMethods } returns paymentMethods
    }

    fun versionSpecDao(
        id: Long = 1,
        versionMin: String? = "1.0",
        versionMax: String? = "2.0",
        versionExact: String? = "3.0"
    ): VersionSpecDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.versionMin } returns versionMin
        every { this@mockk.versionMax } returns versionMax
        every { this@mockk.versionExact } returns versionExact
    }

    fun workflowDao(
        id: Long = 1,
        name: String = "Example Workflow",
        description: String? = "A sample workflow description",
        pythonVersion: String = "3.9",
        parentFolder: WorkflowFolderDao? = null,
        metadata: UserDesignedMetadata = mockk(),
        createdAt: LocalDateTime = mockk(),
        updatedAt: LocalDateTime = mockk()
    ): WorkflowDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.pythonVersion } returns pythonVersion
        every { this@mockk.parentFolder } returns parentFolder
        every { this@mockk.createdAt } returns createdAt
        every { this@mockk.updatedAt } returns updatedAt
    }

    fun workflowExecutionDao(
        id: Long = 1,
        workflow: WorkflowDao = workflowDao(),
        user: UserDao = userDao(),
        startedAt: LocalDateTime = Instant.parse("2024-12-20T10:00:00Z").toLocalDateTime(TimeZone.UTC),
        completedAt: LocalDateTime? = Instant.parse("2024-12-20T10:00:00Z").toLocalDateTime(TimeZone.UTC),
        status: ExecutionStatus = ExecutionStatus.SKIPPED,
        stepExecutions: List<StepExecutionDao> = emptyList()
    ): WorkflowExecutionDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.workflow } returns workflow
        every { this@mockk.user } returns user
        every { this@mockk.startedAt } returns startedAt
        every { this@mockk.completedAt } returns completedAt
        every { this@mockk.status } returns status
        //every { this@mockk.stepExecutions } returns stepExecutions
    }

    fun workflowFolderDao(
        id: Long = 1,
        parentFolder: WorkflowFolderDao? = null,
        name: String = "ExampleFolder",
        description: String? = "Folder description",
        img: ExposedBlob? = null,
        createdAt: LocalDateTime = mockk(),
        updatedAt: LocalDateTime = mockk()
    ): WorkflowFolderDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.parentFolder } returns parentFolder
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.img } returns img
        every { this@mockk.createdAt } returns createdAt
        every { this@mockk.updatedAt } returns updatedAt
    }

    fun workflowJunctionDao(
        id: Long = 1,
        sourceStep: StepDao = stepDao(),
        targetWorkflow: WorkflowDao = workflowDao(),
        triggerInquisition: InquisitionDao? = null
    ): WorkflowJunctionDao = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.sourceStep } returns sourceStep
        every { this@mockk.targetWorkflow } returns targetWorkflow
        every { this@mockk.triggerInquisition } returns triggerInquisition
    }


}