package com.wini.owle.entity

import com.wini.owle.entity.environs.*
import com.wini.owle.entity.environs.enums.PackageRepoTypeEntity
import com.wini.owle.entity.runs.*
import com.wini.owle.entity.runs.enums.ComparisonOperatorEntity
import com.wini.owle.entity.runs.enums.ExecutionStatusEntity
import com.wini.owle.entity.runs.enums.GenerationMethodEntity
import com.wini.owle.entity.runs.enums.LogLevelEntity
import com.wini.owle.entity.runs.step.StepRunEntity
import com.wini.owle.entity.runs.step.StepRunLogLineEntity
import com.wini.owle.entity.runs.test.BenchmarkReportEntity
import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.entity.runs.test.TestExpectationEntity
import com.wini.owle.entity.runs.test.TestInputEntity
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import com.wini.owle.entity.runs.workflow.WorkflowTestResultEntity
import com.wini.owle.entity.steps.*
import com.wini.owle.entity.transforms.*
import com.wini.owle.entity.user.enums.CardBrandEntity
import com.wini.owle.entity.transforms.enums.TransformOccurrenceEntity
import com.wini.owle.entity.typing.*
import com.wini.owle.entity.typing.enums.VarianceCardinalityEntity
import com.wini.owle.entity.user.*
import com.wini.owle.entity.user.enums.SubscriptionStatusEntity
import com.wini.owle.entity.user.tags.TagEntity
import com.wini.owle.entity.user.tags.TagRelationEntity
import com.wini.owle.entity.workflows.*
import com.wini.owle.entity.workflows.enums.StoreMethodEntity
import com.wini.owle.models.metadata.UserDesignedMetadata
import kotlinx.datetime.Instant
import java.math.BigDecimal
import io.mockk.mockk
import io.mockk.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.statements.api.ExposedBlob


@Suppress("UNCHECKED_CAST")
object EntityTestInstanceFactory {

    fun appliedTransformArgDao(
        id: Long = 1,
        appliedTransformEntity: AppliedTransformEntity = appliedTransformDao(),
        transformationArg: TransformArgEntity = transformationArgDao(),
        value: String = "exampleArgumentValue"
    ): AppliedTransformArgEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.appliedTransformEntity } returns appliedTransformEntity
        every { this@mockk.transformationArg } returns transformationArg
        every { this@mockk.value } returns value
    }

    fun appliedTransformDao(
        id: Long = 1,
        transformation: TransformEntity = transformationDao(),
        occurrence: TransformOccurrenceEntity = TransformOccurrenceEntity.PREREQINJECTION,
        resultType: TypeDeclarationEntity = typeDeclarationDao(),
        dataPointEntity: DataPointEntity? = null
    ): AppliedTransformEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.transformation } returns transformation
        every { this@mockk.occurrence } returns occurrence
        every { this@mockk.resultType } returns resultType
        every { this@mockk.dataPointEntity } returns dataPointEntity
    }

    fun benchmarkingDataDao(
        id: Long = 1,
        workflowExecution: WorkflowRunEntity = workflowExecutionDao(),
        totalExecutionTime: BigDecimal = 127.0.toBigDecimal(),
        totalSteps: Int = 10,
        aiDeterminedSteps: Int = 3,
        predeterminedSteps: Int = 8
    ): BenchmarkReportEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.workflowExecution } returns workflowExecution
        every { this@mockk.totalExecutionTime } returns totalExecutionTime
        every { this@mockk.totalSteps } returns totalSteps
        every { this@mockk.aiDeterminedSteps } returns aiDeterminedSteps
        every { this@mockk.predeterminedSteps } returns predeterminedSteps
    }

    fun dataPointDao(
        id: Long = 1,
        workflow: WorkflowEntity = workflowDao(),
        name: String = "ExampleDataPoint",
        description: String? = "Data point description",
        presetValue: String? = null,
        storeMethod: StoreMethodEntity = StoreMethodEntity.BLOB,
        typeDeclarationEntity: TypeDeclarationEntity = typeDeclarationDao(),
        createdAt: Instant = mockk(),
        updatedAt: Instant = mockk()
    ): DataPointEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.workflow } returns workflow
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.presetValue } returns presetValue
        every { this@mockk.storeMethod } returns storeMethod
        every { this@mockk.typeDeclarationEntity } returns typeDeclarationEntity
        every { this@mockk.createdAt } returns createdAt
        every { this@mockk.updatedAt } returns updatedAt
    }

    fun errorWatchDao(
        id: Long = 1,
        step: StepEntity = stepDao(),
        errorType: String = "RuntimeException",
        onMetWorkflowStep: StepEntity = stepDao()
    ): ErrorWatchEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.step } returns step
        every { this@mockk.errorType } returns errorType
        every { this@mockk.onMetStep } returns onMetWorkflowStep
    }

    fun errorWatchFnDao(
        id: Long = 1,
        errorWatchEntity: ErrorWatchEntity = errorWatchDao(),
        functionName: String = "exampleFunction",
        position: Int = 1
    ): ErrorWatchFnEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.errorWatchEntity } returns errorWatchEntity
        every { this@mockk.functionName } returns functionName
        every { this@mockk.position } returns position
    }

    fun executionLogDao(
        id: Long = 1,
        stepExecution: StepRunEntity = stepExecutionDao(),
        timestamp: Instant = mockk(),
        logLevel: LogLevelEntity = LogLevelEntity.WARNING,
        message: String = "Execution started"
    ): StepRunLogLineEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.stepExecution } returns stepExecution
        every { this@mockk.timestamp } returns timestamp
        every { this@mockk.logLevel } returns logLevel
        every { this@mockk.message } returns message
    }

    fun inquisitionDao(
        id: Long = 1,
        step: StepEntity = stepDao(),
        className: String = "Condition",
        dataPointEntity: DataPointEntity = dataPointDao(),
        onMetWorkflowStep: StepEntity = stepDao(),
        executionOrder: Int = 1
    ): InquisitionEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.onMetWorkflowStep } returns onMetWorkflowStep
        every { this@mockk.className } returns className
        every { this@mockk.dataPointEntity } returns dataPointEntity
        every { this@mockk.onMetWorkflowStep } returns onMetWorkflowStep
        every { this@mockk.executionOrder } returns executionOrder
    }

    fun packageDao(
        id: Long = 1,
        name: String = "Example Package",
        disambiguation: String? = "Example Disambiguation",
        versionSpec: VersionSpecEntity = versionSpecDao()
    ): PyPackageEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.name } returns name
        every { this@mockk.disambiguation } returns disambiguation
        every { this@mockk.versionSpec } returns versionSpec
    }

    fun packageRepoDao(
        id: Long = 1,
        sourceType: PackageRepoTypeEntity = PackageRepoTypeEntity.PYPI,
        sourceUrl: String? = "https://pypi.org"
    ): PyPackageRepoEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.sourceType } returns sourceType
        every { this@mockk.sourceUrl } returns sourceUrl
    }

    fun paymentMethodDao(
        id: Long = 1,
        stripePaymentMethodId: String? = "pm_123example",
        userEntity: UserEntity = userDao(),
        cardBrand: CardBrandEntity? = CardBrandEntity.VISA,
        lastFour: String? = "4242",
        isDefault: Boolean = true,
        createdAt: Instant = mockk(),
        updatedAt: Instant = mockk()
    ): PaymentMethodEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.stripePaymentMethodId } returns stripePaymentMethodId
        every { this@mockk.userEntity } returns userEntity
        every { this@mockk.cardBrand } returns cardBrand
        every { this@mockk.lastFour } returns lastFour
        every { this@mockk.isDefault } returns isDefault
        every { this@mockk.createdAt } returns createdAt
        every { this@mockk.updatedAt } returns updatedAt
    }

    fun phaseDao(
        id: Long = 1,
        workflow: WorkflowEntity = workflowDao(),
        parentPhase: PhaseEntity? = null,
        name: String = "Initial Phase",
        description: String? = "Description of the phase",
        displayOrder: Int = 0
    ): PhaseEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.workflow } returns workflow
        every { this@mockk.parentPhase } returns parentPhase
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.displayOrder } returns displayOrder
    }

    fun preReqInjectionDao(
        id: Long = 1,
        junction: WorkflowJunctionEntity = workflowJunctionDao(),
        sourceDataPointEntity: DataPointEntity = dataPointDao(),
        injectedDataPointEntity: DataPointEntity = dataPointDao(),
        name: String? = "Example Injection",
        description: String? = "A prerequisite data injection"
    ): InitialDataPointInjectorEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.junction } returns junction
        every { this@mockk.sourceDataPointEntity } returns sourceDataPointEntity
        every { this@mockk.injectedDataPointEntity } returns injectedDataPointEntity
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
    }

    fun provisionDao(
        id: Long = 1,
        step: StepEntity = stepDao(),
        toolParam: ToolParamEntity = toolParamDao(),
        dataPoints: SizedIterable<DataPointEntity> = mockk()
    ): ToolParamDataPointEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.step } returns step
        every { this@mockk.toolParam } returns toolParam
        every { this@mockk.dataPointEntity } returns dataPoints
    }

    fun provisionDataPointDao(
        provisionId: Long = 1,
        dataPointId: Long = 1,
        provision: ToolParamDataPointEntity = provisionDao(provisionId),
        dataPointEntity: DataPointEntity = dataPointDao(dataPointId)
    ): ProvisionDataPointEntity = mockk {
        val compositeID = ProvisionDataPointEntity.buildId(provisionId, dataPointId)
        every { this@mockk.id } returns EntityID(compositeID, mockk())
        every { this@mockk.provision } returns provision
        every { this@mockk.dataPointEntity } returns dataPointEntity
    }

    fun revisionDao(
        id: Long = 1,
        dataPointEntity: DataPointEntity = dataPointDao(),
        step: StepEntity = stepDao()
    ): DataPointSaveEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.dataPointEntity } returns dataPointEntity
        every { this@mockk.step } returns step
    }

    fun stepBenchmarkingDataDao(
        id: Long = 1,
        stepExecution: StepRunEntity = stepExecutionDao(),
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
        parentPhase: PhaseEntity? = null,
        displayOrder: Int = 0,
        workflow: WorkflowEntity = workflowDao(),
        isWorkflowHead: Boolean = true,
        tool: ToolEntity = toolDao(),
        defaultNext: StepEntity? = null
    ): StepEntity = mockk {
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
        workflowExecution: WorkflowRunEntity = workflowExecutionDao(),
        step: StepEntity = stepDao(),
        startedAt: Instant = Instant.parse("2024-12-20T10:00:00Z"),
        completedAt: Instant? = Instant.parse("2024-12-20T10:00:00Z"),
        status: ExecutionStatusEntity = ExecutionStatusEntity.RUNNING
    ): StepRunEntity = mockk {
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
        userEntity: UserEntity = userDao()
    ): TagEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.userEntity } returns userEntity
    }

    fun tagRelationDao(
        parentTagId: Long = 1,
        childTagId: Long = 1,
        parentTagEntity: TagEntity = tagDao(parentTagId),
        childTagEntity: TagEntity = tagDao(childTagId)
    ): TagRelationEntity = mockk {
        val compositeID = TagRelationEntity.buildId(parentTagId, childTagId)
        every { this@mockk.id } returns EntityID(compositeID, mockk())
        every { this@mockk.parentTagEntity } returns parentTagEntity
        every { this@mockk.childTagEntity } returns childTagEntity
    }

    fun testCaseDao(
        id: Long = 1,
        userEntity: UserEntity = userDao(),
        name: String = "Sample Test Case",
        description: String? = "Validates a workflow",
        workflow: WorkflowEntity? = null,
        step: StepEntity? = null,
        inputs: List<TestInputEntity> = emptyList(),
        expectedOutcomes: List<TestExpectationEntity> = emptyList(),
        results: List<WorkflowTestResultEntity> = emptyList()
    ): TestCaseEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.userEntity } returns userEntity
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
        testCaseEntity: TestCaseEntity = testCaseDao(),
        dataPointName: String = "expectedDataPoint",
        expectedValue: String? = "expectedValue",
        comparisonOperator: ComparisonOperatorEntity = ComparisonOperatorEntity.CONTAINS
    ): TestExpectationEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.testCaseEntity } returns testCaseEntity
        every { this@mockk.dataPointName } returns dataPointName
        every { this@mockk.expectedValue } returns expectedValue
        every { this@mockk.comparisonOperator } returns comparisonOperator
    }

    fun testInputDao(
        id: Long = 1,
        testCaseEntity: TestCaseEntity = testCaseDao(),
        dataPointName: String = "exampleDataPoint",
        value: String? = "testValue",
        generationMethod: GenerationMethodEntity = GenerationMethodEntity.MANUAL
    ): TestInputEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.testCaseEntity } returns testCaseEntity
        every { this@mockk.dataPointName } returns dataPointName
        every { this@mockk.value } returns value
        every { this@mockk.generationMethod } returns generationMethod
    }

    fun testResultDao(
        id: Long = 1,
        testCaseEntity: TestCaseEntity = testCaseDao(),
        workflowExecution: WorkflowRunEntity = workflowExecutionDao(),
        passed: Boolean = true,
        executedAt: Instant = mockk()
    ): WorkflowTestResultEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.testCaseEntity } returns testCaseEntity
        every { this@mockk.workflowExecution } returns workflowExecution
        every { this@mockk.passed } returns passed
        every { this@mockk.executedAt } returns executedAt
    }

    fun toolDao(
        id: Long = 1,
        name: String = "ExampleTool",
        description: String? = "Tool description",
        body: String = "fun ok()",
        returnTypeDeclarationEntity: TypeDeclarationEntity? = null
    ): ToolEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.body } returns body
        every { this@mockk.returnTypeDeclarationEntity } returns returnTypeDeclarationEntity
    }

    fun toolPackageDependencyDao(
        toolId: Long = 1,
        packageId: Long = 1,
        versionSpecId: Long = 1,
        tool: ToolEntity = toolDao(toolId),
        packageEntity: PyPackageEntity = packageDao(packageId),
        versionSpec: VersionSpecEntity = versionSpecDao(versionSpecId)
    ): ToolPyPackDepEntity = mockk {
        val compositeID = ToolPyPackDepEntity.buildId(toolId, packageId, versionSpecId)
        every { this@mockk.id } returns EntityID(compositeID, mockk())
        every { this@mockk.tool } returns tool
        every { this@mockk.packageEntity } returns packageEntity
        every { this@mockk.versionSpec } returns versionSpec
    }

    fun toolParamDao(
        id: Long = 1,
        name: String = "exampleParam",
        tool: ToolEntity = toolDao(),
        typeDeclarationEntity: TypeDeclarationEntity = typeDeclarationDao(),
        hasDefault: Boolean = false
    ): ToolParamEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.name } returns name
        every { this@mockk.tool } returns tool
        every { this@mockk.typeDeclarationEntity } returns typeDeclarationEntity
        every { this@mockk.hasDefault } returns hasDefault
    }

    fun transformationArgDao(
        id: Long = 1,
        transformation: TransformEntity = transformationDao(),
        typeDeclarationEntity: TypeDeclarationEntity = typeDeclarationDao(),
        name: String = "Argument Name",
        description: String? = "Argument Description"
    ): TransformArgEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.transformation } returns transformation
        every { this@mockk.typeDeclarationEntity } returns typeDeclarationEntity
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
    }

    fun transformationDao(
        id: Long = 1,
        name: String = "Example Transformation",
        description: String = "Transforms data",
        examples: String? = "Input -> Output",
        returnsType: TypeDeclarationEntity? = null
    ): TransformEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.examples } returns examples
        every { this@mockk.returnsTypeDeclarationEntity } returns returnsType
    }

    fun transformationTargetTypeDao(
        transformationId: Long = 1,
        typeDeclarationId: Long = 1,
        transformation: TransformEntity = transformationDao(transformationId),
        typeDeclarationEntity: TypeDeclarationEntity = typeDeclarationDao(typeDeclarationId)
    ): TransformTargetTypeEntity = mockk {
        val compositeID = TransformTargetTypeEntity.buildId(transformationId, typeDeclarationId)
        every { this@mockk.id } returns EntityID(compositeID, mockk())
        every { this@mockk.transformation } returns transformation
        every { this@mockk.eligibleTypeDeclarationEntity } returns typeDeclarationEntity
    }

    fun typeBoundDao(
        id: Long = 1,
        boundTypeDeclarationEntity: TypeDeclarationEntity = typeDeclarationDao(),
        typeVariableDeclaration: TypeDeclarationEntity = typeDeclarationDao()
    ): TypeBoundEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.boundTypeDeclarationEntity } returns boundTypeDeclarationEntity
        every { this@mockk.typeVariableDeclaration } returns typeVariableDeclaration
    }

    fun typeDeclarationDao(
        id: Long = 1,
        typeDefinitionEntity: TypeDefinitionEntity = typeDefinitionDao(),
        alias: String? = "CustomType"
    ): TypeDeclarationEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.typeDefinitionEntity } returns typeDefinitionEntity
        every { this@mockk.alias } returns alias
    }

    fun typeDefinitionDao(
        id: Long = 1,
        name: String = "List",
        namespace: String = "builtins",
        versionSpec: VersionSpecEntity = versionSpecDao()
    ): TypeDefinitionEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.name } returns name
        every { this@mockk.namespace } returns namespace
        every { this@mockk.versionSpec } returns versionSpec
    }

    fun typeParameterDao(
        id: Long = 1,
        typeDeclarationEntity: TypeDeclarationEntity = typeDeclarationDao(),
        position: Int = 1,
        parameterTypeDeclarationEntity: TypeDeclarationEntity = typeDeclarationDao()
    ): TypeParameterEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.typeDeclarationEntity } returns typeDeclarationEntity
        every { this@mockk.position } returns position
        every { this@mockk.parameterTypeDeclarationEntity } returns parameterTypeDeclarationEntity
    }

    fun typeSchemaRefDao(
        id: Long = 1,
        parentRef: TypeSchemaRefEntity? = null,
        name: String = "exampleSchema",
        typeDeclarationEntity: TypeDeclarationEntity = typeDeclarationDao(),
        description: String? = "Schema reference description",
        childRefs: List<TypeSchemaRefEntity> = emptyList()
    ): TypeSchemaRefEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.parentRef } returns parentRef
        every { this@mockk.name } returns name
        every { this@mockk.typeDeclarationEntity } returns typeDeclarationEntity
        every { this@mockk.description } returns description
        //every { this@mockk.childRefs } returns childRefs
    }

    fun typeUnionMemberDao(
        unionTypeDeclarationId: Long = 1,
        memberTypeDeclarationId: Long = 1,
        unionTypeDeclarationEntity: TypeDeclarationEntity = typeDeclarationDao(unionTypeDeclarationId),
        memberTypeDeclarationEntity: TypeDeclarationEntity = typeDeclarationDao(memberTypeDeclarationId)
    ): TypeUnionMemberEntity = mockk {
        val compositeID = TagRelationEntity.buildId(unionTypeDeclarationId, memberTypeDeclarationId)
        every { this@mockk.id } returns EntityID(compositeID, mockk())
        every { this@mockk.unionTypeDeclarationEntity } returns unionTypeDeclarationEntity
        every { this@mockk.memberTypeDeclarationEntity } returns memberTypeDeclarationEntity
    }

    fun typeVarianceDao(
        id: Long = 1,
        typeVariableDeclaration: TypeDeclarationEntity = typeDeclarationDao(),
        varianceCardinality: VarianceCardinalityEntity = VarianceCardinalityEntity.COVARIANT
    ): TypeVarianceEntity = mockk {
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
        subscriptionStatus: SubscriptionStatusEntity = SubscriptionStatusEntity.REGISTERED,
        apiKey: String? = null,
        createdAt: Instant = mockk(),
        updatedAt: Instant = mockk(),
        paymentMethodEntities: List<PaymentMethodEntity> = emptyList()
    ): UserEntity = mockk {
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
    ): VersionSpecEntity = mockk {
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
        parentFolder: WorkflowFolderEntity? = null,
        metadata: UserDesignedMetadata = mockk(),
        createdAt: Instant = mockk(),
        updatedAt: Instant = mockk()
    ): WorkflowEntity = mockk {
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
        workflow: WorkflowEntity = workflowDao(),
        userEntity: UserEntity = userDao(),
        startedAt: Instant = Instant.parse("2024-12-20T10:00:00Z"),
        completedAt: Instant? = Instant.parse("2024-12-20T10:00:00Z"),
        status: ExecutionStatusEntity = ExecutionStatusEntity.SKIPPED,
        stepExecutions: List<StepRunEntity> = emptyList()
    ): WorkflowRunEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.workflow } returns workflow
        every { this@mockk.userEntity } returns userEntity
        every { this@mockk.startedAt } returns startedAt
        every { this@mockk.completedAt } returns completedAt
        every { this@mockk.status } returns status
        //every { this@mockk.stepExecutions } returns stepExecutions
    }

    fun workflowFolderDao(
        id: Long = 1,
        parentFolder: WorkflowFolderEntity? = null,
        name: String = "ExampleFolder",
        description: String? = "Folder description",
        img: ExposedBlob? = null,
        createdAt: Instant = mockk(),
        updatedAt: Instant = mockk()
    ): WorkflowFolderEntity = mockk {
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
        sourceStep: StepEntity = stepDao(),
        targetWorkflow: WorkflowEntity = workflowDao(),
        triggerInquisitionEntity: InquisitionEntity? = null
    ): WorkflowJunctionEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.sourceStep } returns sourceStep
        every { this@mockk.targetWorkflow } returns targetWorkflow
        every { this@mockk.triggerInquisitionEntity } returns triggerInquisitionEntity
    }


}