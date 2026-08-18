package com.wini.owle.entity

import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.runs.enums.GenerationMethod
import com.wini.owle.dto.runs.enums.LogLevel
import com.wini.owle.dto.runs.enums.TestAssertion
import com.wini.owle.dto.transforms.enums.TransformOccurrenceInitiator
import com.wini.owle.dto.typing.enums.VarianceCardinality
import com.wini.owle.dto.user.enums.CardBrand
import com.wini.owle.dto.user.enums.UserStatus
import com.wini.owle.dto.workflows.enums.StoreMethod
import com.wini.owle.entity.environs.pkg.PackagePythonRequirementEntity
import com.wini.owle.entity.environs.pkg.DistributionPackageEntity
import com.wini.owle.entity.runs.step.StepTestRunEntity
import com.wini.owle.entity.runs.step.ExecutionNotificationEntity
import com.wini.owle.entity.runs.test.BenchmarkReportEntity
import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.entity.runs.test.TestOutcomeExpectationEntity
import com.wini.owle.entity.runs.test.TestInputEntity
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import com.wini.owle.entity.runs.workflow.TestResultEntity
import com.wini.owle.entity.steps.*
import com.wini.owle.entity.transforms.*
import com.wini.owle.entity.typing.*
import com.wini.owle.entity.user.*
import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.tags.TagRelationEntity
import com.wini.owle.entity.workflows.*
import kotlinx.datetime.Instant
import java.math.BigDecimal
import io.mockk.mockk
import io.mockk.*
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.statements.api.ExposedBlob
import org.jetbrains.exposed.v1.jdbc.SizedIterable


@Suppress("UNCHECKED_CAST")
object InstanceMockEntityFactory {

    fun transformOccurrenceArg(
        id: ULong = 1UL,
        transformOccurrenceEntity: TransformOccurrenceEntity = transformOccurrence(),
        transformArg: TransformArgEntity = transformationArg(),
        value: String = "example argument value"
    ): TransformOccurrenceArgEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.transformOccurrenceEntity } returns transformOccurrenceEntity
        every { this@mockk.transformationArg } returns transformArg
        every { this@mockk.value } returns value
    }

    fun transformOccurrence(
        id: ULong = 1UL,
        transformation: TransformEntity = transformationDao(),
        occurrence: TransformOccurrenceInitiator = TransformOccurrenceInitiator.DATA_POINT_INJECTION,
        resultType: TypeNodeEntity = typeDeclarationDao(),
        dataPoint: DataPointEntity? = null
    ): TransformOccurrenceEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.transformation } returns transformation
        every { this@mockk.occurrence } returns occurrence
        every { this@mockk.resultType } returns resultType
    }

    fun benchmarkingDataDao(
        id: ULong = 1UL,
        workflowExecution: WorkflowRunEntity = workflowExecutionDao(),
        totalExecutionTime: BigDecimal = 127.0.toBigDecimal(),
    ): BenchmarkReportEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.workflowExecution } returns workflowExecution
        every { this@mockk.totalExecutionTime } returns totalExecutionTime
    }

    fun dataPointDao(
        id: ULong = 1UL,
        workflow: WorkflowEntity = workflowDao(),
        name: String = "ExampleDataPoint",
        description: String? = "Data point description",
        presetValue: String? = null,
        storeMethod: StoreMethod = StoreMethod.BLOB,
        typeNode: TypeNodeEntity = typeDeclarationDao(),
        createdAt: Instant = mockk(),
        updatedAt: Instant = mockk()
    ): DataPointEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.workflow } returns workflow
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.presetValue } returns presetValue
        every { this@mockk.storeMethod } returns storeMethod
        every { this@mockk.typeNode } returns typeNode
        every { this@mockk.createdAt } returns createdAt
        every { this@mockk.updatedAt } returns updatedAt
    }

    fun errorWatchDao(
        id: ULong = 1UL,
        step: StepEntity = stepDao(),
        errorType: String = "RuntimeException",
        onMetWorkflowStep: StepEntity = stepDao()
    ): ErrorWatchEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.step } returns step
        every { this@mockk.errorType } returns errorType
        every { this@mockk.onMetStep } returns onMetWorkflowStep
    }

    fun errorWatchFnDao(
        id: ULong = 1UL,
        errorWatchEntity: ErrorWatchEntity = errorWatchDao(),
        functionNode: TypeNodeEntity = typeNodeDao(),
        position: Int = 1
    ): ErrorWatchFnEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.errorWatch } returns errorWatchEntity
        every { this@mockk.functionNode } returns functionNode
        every { this@mockk.position } returns position
    }

    fun executionLogDao(
        id: ULong = 1UL,
        stepExecution: StepTestRunEntity = stepExecutionDao(),
        timestamp: Instant = mockk(),
        logLevel: LogLevel = LogLevel.WARNING,
        message: String = "Execution started"
    ): ExecutionNotificationEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.stepExecution } returns stepExecution
        every { this@mockk.timestamp } returns timestamp
        every { this@mockk.logLevel } returns logLevel
        every { this@mockk.message } returns message
    }

    fun inquisitionDao(
        id: ULong = 1UL,
        step: StepEntity = stepDao(),
        classNode: TypeNodeEntity = typeNodeDao(),
        dataPointEntity: DataPointEntity = dataPointDao(),
        onMetWorkflowStep: StepEntity = stepDao(),
        executionOrder: Int = 1
    ): InquisitionEntity = mockk {
        every { this@mockk.id } returns EntityID(id, mockk())
        every { this@mockk.onMetWorkflowStep } returns onMetWorkflowStep
        every { this@mockk.condition } returns typeNodeDao()
        every { this@mockk.dataPoint } returns dataPointEntity
        every { this@mockk.onMetWorkflowStep } returns onMetWorkflowStep
        every { this@mockk.executionOrder } returns executionOrder
    }

    fun packageDao(
        id: ULong = 1UL,
        name: String = "Example Package",
        disambiguation: String? = "Example Disambiguation",
        versionSpec: PackagePythonRequirementEntity = mockk()
    ): DistributionPackageEntity = mockk {
        every { this@mockk.id } returns mockk()
    }

    fun paymentMethodDao(
        id: ULong = 1UL,
        stripePaymentMethodId: String? = "pm_123example",
        userEntity: UserEntity = userDao(),
        cardBrand: CardBrand? = CardBrand.VISA,
        lastFour: String? = "4242",
        isDefault: Boolean = true,
        createdAt: Instant = mockk(),
        updatedAt: Instant = mockk()
    ): PaymentMethodEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.stripePaymentMethodId } returns stripePaymentMethodId
        every { this@mockk.user } returns userEntity
       // every { this@mockk.cardBrand } returns cardBrand
        every { this@mockk.lastFour } returns lastFour
        every { this@mockk.isDefault } returns isDefault
        every { this@mockk.createdAt } returns createdAt
        every { this@mockk.updatedAt } returns updatedAt
    }

    fun phaseDao(
        id: ULong = 1UL,
        workflow: WorkflowEntity = workflowDao(),
        parentPhase: PhaseEntity? = null,
        name: String = "Initial Phase",
        description: String? = "Description of the phase",
        displayOrder: Int = 0
    ): PhaseEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.workflow } returns workflow
        //every { this@mockk.parentPhase } returns parentPhase
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.displayOrder } returns displayOrder
    }

    fun preReqInjectionDao(
        id: ULong = 1UL,
        junction: WorkflowJunctionEntity = workflowJunctionDao(),
        sourceDataPointEntity: DataPointEntity = dataPointDao(),
        injectedDataPointEntity: DataPointEntity = dataPointDao(),
        name: String? = "Example Injection",
        description: String? = "A prerequisite data injection"
    ): DataPointInjectionEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.junction } returns junction
        every { this@mockk.sourceDataPoint } returns sourceDataPointEntity
        every { this@mockk.injectedDataPoint } returns injectedDataPointEntity
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
    }

    fun provisionDao(
        id: ULong = 1UL,
        step: StepEntity = stepDao(),
        toolParam: ToolParamEntity = toolParamDao(),
        dataPoints: SizedIterable<DataPointEntity> = mockk()
    ): ToolParamDataPointReadEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.step } returns step
        every { this@mockk.toolParam } returns toolParam
        every { this@mockk.dataPointEntity } returns dataPoints
    }

    fun revisionDao(
        id: ULong = 1UL,
        dataPointEntity: DataPointEntity = dataPointDao(),
        step: StepEntity = stepDao()
    ): DataPointWriteEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.dataPoint } returns dataPointEntity
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
        id: ULong = 1UL,
        name: String = "Example Step",
        description: String? = "A step in a workflow",
        parentPhase: PhaseEntity? = null,
        displayOrder: Int = 0,
        workflow: WorkflowEntity = workflowDao(),
        isWorkflowHead: Boolean = true,
        tool: ToolEntity = toolDao(),
        defaultNext: StepEntity? = null
    ): StepEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.parentPhase } returns parentPhase
        every { this@mockk.displayOrder } returns displayOrder
        every { this@mockk.workflow } returns workflow
        every { this@mockk.isWorkflowHead } returns isWorkflowHead
        every { this@mockk.tool } returns tool
        //every { this@mockk.defaultNext } returns defaultNext
    }

    fun stepExecutionDao(
        id: ULong = 1UL,
        workflowExecution: WorkflowRunEntity = workflowExecutionDao(),
        step: StepEntity = stepDao(),
        startedAt: Instant = Instant.parse("2024-12-20T10:00:00Z"),
        completedAt: Instant? = Instant.parse("2024-12-20T10:00:00Z"),
        status: ExecutionStatus = ExecutionStatus.RUNNING
    ): StepTestRunEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.step } returns step
        every { this@mockk.status } returns status
    }

    fun tagDao(
        id: ULong = 1UL,
        name: String = "Example Tag",
        description: String? = "A descriptive tag",
        userEntity: UserEntity = userDao()
    ): TagEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
    }

    fun tagRelationDao(
        parentTagId: ULong = 1UL,
        childTagId: ULong = 1UL,
        parentTagEntity: TagEntity = tagDao(parentTagId),
        childTagEntity: TagEntity = tagDao(childTagId)
    ): TagRelationEntity = mockk {
        val compositeID = TagRelationEntity.buildId(parentTagId, childTagId)
        every { this@mockk.id } returns EntityID(compositeID, mockk())
        every { this@mockk.parentTag } returns parentTagEntity
        every { this@mockk.childTag } returns childTagEntity
    }

    fun testCaseDao(
        id: ULong = 1UL,
        user: UserEntity = userDao(),
        name: String = "Sample Test Case",
        description: String? = "Validates a workflow",
        workflow: WorkflowEntity? = null,
        step: StepEntity? = null,
        inputs: List<TestInputEntity> = emptyList(),
        expectedOutcomes: List<TestOutcomeExpectationEntity> = emptyList(),
        results: List<TestResultEntity> = emptyList()
    ): TestCaseEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.workflow } returns workflow
        every { this@mockk.step } returns step
//        every { this@mockk.inputs } returns inputs
//        every { this@mockk.expectedOutcomes } returns expectedOutcomes
//        every { this@mockk.results } returns results
    }

    fun testExpectedOutcomeDao(
        id: ULong = 1UL,
        testCaseEntity: TestCaseEntity = testCaseDao(),
        dataPointName: String = "expectedDataPoint",
        expectedValue: String? = "expectedValue",
        assertion: TestAssertion = TestAssertion.CONTAINS
    ): TestOutcomeExpectationEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.dataPoint } returns dataPointDao(name = dataPointName)
        every { this@mockk.assertion } returns assertion
    }

    fun testInputDao(
        id: ULong = 1UL,
        testCaseEntity: TestCaseEntity = testCaseDao(),
        dataPointName: String = "exampleDataPoint",
        value: String? = "testValue",
        generationMethod: GenerationMethod = GenerationMethod.MANUAL
    ): TestInputEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.testCase } returns testCaseEntity
        every { this@mockk.dataPoint } returns dataPointDao(name = dataPointName)
        every { this@mockk.value } returns value
        every { this@mockk.generationMethod } returns generationMethod
    }

    fun testResultDao(
        id: ULong = 1UL,
        testCaseEntity: TestCaseEntity = testCaseDao(),
        workflowExecution: WorkflowRunEntity = workflowExecutionDao(),
        passed: Boolean = true,
        executedAt: Instant = mockk()
    ): TestResultEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.testCase } returns testCaseEntity
        every { this@mockk.workflowExecution } returns workflowExecution
        every { this@mockk.passed } returns passed
        every { this@mockk.executedAt } returns executedAt
    }

    fun toolDao(
        id: ULong = 1UL,
        name: String = "ExampleTool",
        description: String? = "Tool description",
        body: String = "fun ok()",
        returnTypeNode: TypeNodeEntity? = null
    ): ToolEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.body } returns body
        every { this@mockk.returnTypeNode } returns returnTypeNode
    }

    fun toolParamDao(
        id: ULong = 1UL,
        name: String = "exampleParam",
        tool: ToolEntity = toolDao(),
        typeNode: TypeNodeEntity = typeDeclarationDao()
    ): ToolParamEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.name } returns name
        every { this@mockk.tool } returns tool
        every { this@mockk.typeNode } returns typeNode
    }

    fun transformationArg(
        id: ULong = 1UL,
        transformation: TransformEntity = transformationDao(),
        typeNode: TypeNodeEntity = typeDeclarationDao(),
        name: String = "Argument Name",
        description: String? = "Argument Description"
    ): TransformArgEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.transformation } returns transformation
        every { this@mockk.typeNode } returns typeNode
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
    }

    fun transformationDao(
        id: ULong = 1UL,
        name: String = "Example Transformation",
        description: String = "Transforms data",
        examples: String? = "Input -> Output",
        returnsType: TypeNodeEntity? = null
    ): TransformEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.examples } returns examples
    }

    fun transformationTargetTypeDao(
        transformationId: ULong = 1UL,
        typeDeclarationId: ULong = 1UL,
        transformation: TransformEntity = transformationDao(transformationId),
        typeNode: TypeNodeEntity = typeDeclarationDao(typeDeclarationId)
    ): TransformTargetTypeEntity = mockk {
        val compositeID = TransformTargetTypeEntity.buildId(transformationId, typeDeclarationId)
        every { this@mockk.id } returns EntityID(compositeID, mockk())
        every { this@mockk.transformation } returns transformation
        every { this@mockk.eligibleTypeNode } returns typeNode
    }

    fun typeBoundDao(
        id: ULong = 1UL,
        boundTypeNode: TypeNodeEntity = typeDeclarationDao(),
        typeVariableDeclaration: TypeNodeEntity = typeDeclarationDao()
    ): TypeVarBoundEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.boundTypeNode } returns boundTypeNode
        every { this@mockk.typeVarNode } returns typeVariableDeclaration
    }

    fun typeDeclarationDao(
        id: ULong = 1UL,
        typeVarConstraint: TypeVarConstraintEntity = typeDefinitionDao(),
        alias: String? = "CustomType"
    ): TypeNodeEntity = mockk {
        every { this@mockk.id } returns mockk()
        //every { this@mockk.typeDefinitionEntity } returns typeVarConstraint
        every { this@mockk.alias } returns alias
    }

    fun typeDefinitionDao(
        id: ULong = 1UL,
        name: String = "List",
        namespace: String = "builtins"
    ): TypeVarConstraintEntity = mockk {
        every { this@mockk.id } returns mockk()
        //every { this@mockk.name } returns name
    }

    fun typeNodeDao(
        id: ULong = 1UL,
        alias: String? = "GenericType"
    ): TypeNodeEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.alias } returns alias
    }

    fun typeParameterDao(
        id: ULong = 1UL,
        typeNode: TypeNodeEntity = typeDeclarationDao(),
        position: Int = 1,
        parameterTypeNode: TypeNodeEntity = typeDeclarationDao()
    ): TypeParameterEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.typeNode } returns typeNode
        every { this@mockk.position } returns position
        every { this@mockk.parameterTypeNode } returns parameterTypeNode
    }

    fun typeSchemaRefDao(
        id: ULong = 1UL,
        parentRef: TypeSchemaRefEntity? = null,
        name: String = "exampleSchema",
        typeNode: TypeNodeEntity = typeDeclarationDao(),
        description: String? = "Schema reference description",
        childRefs: List<TypeSchemaRefEntity> = emptyList()
    ): TypeSchemaRefEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.parentRef } returns parentRef
        every { this@mockk.name } returns name
        every { this@mockk.typeNode } returns typeNode
        every { this@mockk.description } returns description
        //every { this@mockk.childRefs } returns childRefs
    }

    fun typeUnionMemberDao(
        unionTypeDeclarationId: ULong = 1UL,
        memberTypeDeclarationId: ULong = 1UL,
        unionTypeNode: TypeNodeEntity = typeDeclarationDao(unionTypeDeclarationId),
        memberTypeNode: TypeNodeEntity = typeDeclarationDao(memberTypeDeclarationId)
    ): TypeUnionMemberEntity = mockk {
        val compositeID = TagRelationEntity.buildId(unionTypeDeclarationId, memberTypeDeclarationId)
        every { this@mockk.id } returns EntityID(compositeID, mockk())
        every { this@mockk.unionTypeNode } returns unionTypeNode
        every { this@mockk.memberTypeNode } returns memberTypeNode
    }

    fun typeVarianceDao(
        id: ULong = 1UL,
        typeVariableDeclaration: TypeNodeEntity = typeDeclarationDao(),
        varianceCardinality: VarianceCardinality = VarianceCardinality.COVARIANT
    ): TypeVarVarianceEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.typeVarNode } returns typeVariableDeclaration
        every { this@mockk.varianceCardinality } returns varianceCardinality
    }

    fun userDao(
        id: ULong = 1UL,
        email: String = "jim@jim.com",
        password: String = "superSecretStuff1!",
        resetToken: String? = null,
        oauthProvider: String? = null,
        oauthId: String? = null,
        userStatus: UserStatus = UserStatus.REGISTERED,
        apiKey: String? = null,
        createdAt: Instant = mockk(),
        updatedAt: Instant = mockk(),
        paymentMethodEntities: List<PaymentMethodEntity> = emptyList()
    ): UserEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.email } returns email
        every { this@mockk.password } returns password
        every { this@mockk.resetToken } returns resetToken
        every { this@mockk.oauthProvider } returns oauthProvider
        every { this@mockk.oauthId } returns oauthId
        //every { this@mockk.subscriptionStatus } returns subscriptionStatus
        every { this@mockk.apiKey } returns apiKey
        every { this@mockk.createdAt } returns createdAt
        every { this@mockk.updatedAt } returns updatedAt
        //every { this@mockk.paymentMethods } returns paymentMethods
    }

    fun workflowDao(
        id: ULong = 1UL,
        name: String = "Example Workflow",
        description: String? = "A sample workflow description",
        pythonVersion: String = "3.9",
        parentFolder: WorkflowFolderEntity? = null,
        createdAt: Instant = mockk(),
        updatedAt: Instant = mockk()
    ): WorkflowEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.pythonVersion } returns mockk()
        every { this@mockk.parentFolder } returns parentFolder
        every { this@mockk.createdAt } returns createdAt
        every { this@mockk.updatedAt } returns updatedAt
    }

    fun workflowExecutionDao(
        id: ULong = 1UL,
        workflow: WorkflowEntity = workflowDao(),
        userEntity: UserEntity = userDao(),
        startedAt: Instant = Instant.parse("2024-12-20T10:00:00Z"),
        completedAt: Instant? = Instant.parse("2024-12-20T10:00:00Z"),
        status: ExecutionStatus = ExecutionStatus.FAILED,
        stepExecutions: List<StepTestRunEntity> = emptyList()
    ): WorkflowRunEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.workflow } returns workflow
        every { this@mockk.startedAt } returns startedAt
        every { this@mockk.completedAt } returns completedAt
        every { this@mockk.status } returns status
        //every { this@mockk.stepExecutions } returns stepExecutions
    }

    fun workflowFolderDao(
        id: ULong = 1UL,
        parentFolder: WorkflowFolderEntity? = null,
        name: String = "ExampleFolder",
        description: String? = "Folder description",
        img: ExposedBlob? = null,
        createdAt: Instant = mockk(),
        updatedAt: Instant = mockk()
    ): WorkflowFolderEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.parentFolder } returns parentFolder
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.createdAt } returns createdAt
        every { this@mockk.updatedAt } returns updatedAt
    }

    fun workflowJunctionDao(
        id: ULong = 1UL,
        sourceStep: StepEntity = stepDao(),
        targetWorkflow: WorkflowEntity = workflowDao(),
        triggerInquisitionEntity: InquisitionEntity? = null
    ): WorkflowJunctionEntity = mockk {
        every { this@mockk.id } returns mockk()
        every { this@mockk.sourceStep } returns sourceStep
        every { this@mockk.targetWorkflow } returns targetWorkflow
        every { this@mockk.triggerInquisitionEntity } returns triggerInquisitionEntity
    }


}