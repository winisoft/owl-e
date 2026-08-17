package com.wini.owle.entity

import com.wini.owle.dto.environs.version.enums.PrereleasePhase
import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.runs.enums.GenerationMethod
import com.wini.owle.dto.runs.enums.LogLevel
import com.wini.owle.dto.runs.enums.TestAssertion
import com.wini.owle.dto.transforms.enums.TransformOccurrence
import com.wini.owle.dto.typing.enums.VarianceCardinality
import com.wini.owle.entity.InstanceMockEntityFactory.transformOccurrence
import com.wini.owle.entity.InstanceMockEntityFactory.transformationArg
import com.wini.owle.entity.delegate.impl.TagRelationDaoDelegate
import com.wini.owle.entity.delegate.impl.TypeUnionMemberDaoDelegate
import com.wini.owle.entity.environs.pkg.DistributionPackageEntity
import com.wini.owle.entity.environs.version.PythonVersionEntity
import com.wini.owle.entity.environs.version.VersionEntity
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
import com.wini.owle.entity.workflows.enums.StoreMethodEntity
import com.wini.owle.schema.Schema
import kotlinx.datetime.Instant
import java.math.BigDecimal
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.jdbc.EmptySizedIterable
import org.jetbrains.exposed.v1.jdbc.SizedIterable



@Suppress("UNCHECKED_CAST")
object InstanceDbFakeEntityFactory {

    fun transformOccurrenceArgEntity(
        id: ULong = 1UL,
        transformOccurrenceEntity: () -> TransformOccurrenceEntity = { transformOccurrence() },
        transformArg: () -> TransformArgEntity = { transformationArg() },
        value: String = "example argument value"
    ): TransformOccurrenceArgEntity = TransformOccurrenceArgEntity.new(id) {
        this.transformOccurrenceEntity = transformOccurrenceEntity()
        this.transformationArg = transformArg()
        this.value = value
    }

    fun transformEntity(
        id: ULong = 1UL,
        name: String = "Transform Name",
        description: String = "Transform Description",
        examples: String = "it[index]",
        eligibleTypesDeclarationEntity: () -> SizedIterable<TypeNodeEntity> = { EmptySizedIterable() }
    ): TransformEntity = TransformEntity.new(id) {
        this.name = name
        this.description = description
        this.examples = examples
        this.eligibleTypes = eligibleTypesDeclarationEntity()
    }

    fun transformArgEntity(
        id: ULong = 1UL,
        transformation: () -> TransformEntity = { transformEntity() },
        typeNode: () -> TypeNodeEntity = { typeNodeEntity() },
        name: String = "Arg Name",
        description: String = "Arg Description"
    ): TransformArgEntity = TransformArgEntity.new(id) {
        this.transformation = transformation()
        this.typeNode = typeNode()
        this.name = name
        this.description = description
    }

    fun typeNodeEntity(
        id: ULong = 1UL,
        version: () -> VersionEntity = { versionEntity() },
        namespace: String = "builtins",
        name: String = "str",
        alias: String? = "string",
        isGeneric: Boolean = false
    ): TypeNodeEntity = TypeNodeEntity.new(id) {
        this.version = version()
        this.namespace = namespace
        this.name = name
        this.alias = alias
        this.isGeneric = isGeneric
    }

    fun versionEntity(
        id: ULong = 1UL,
        epoch: ULong = 1UL,
        major: ULong = 1UL,
        minor: ULong = 1UL,
        micro: ULong = 1UL,
        phase: PrereleasePhase = PrereleasePhase.FINAL,
        phaseNumber: ULong? = null,
        postNumber: ULong? = null,
        devNumber: ULong? = null,
        local: String? = null,
        hasOverflow: Boolean = false
    ): VersionEntity = VersionEntity.new(id) {
        this.epoch = epoch
        this.major = major
        this.minor = minor
        this.micro = micro
        this.phase = phase
        this.phaseNumber = phaseNumber
        this.postNumber = postNumber
        this.devNumber = devNumber
        this.local = local
        this.hasOverflow = hasOverflow
    }

    fun appliedTransformEntity(
        id: ULong = 1UL,
        transformation: () -> TransformEntity = { transformEntity() },
        occurrence: TransformOccurrence = TransformOccurrence.DATA_POINT_INJECTION,
        resultType: () -> TypeNodeEntity = { typeDeclarationEntity() },
        dataPoint: () -> DataPointEntity? = { null },
    ): TransformOccurrenceEntity = TransformOccurrenceEntity.new(id) {
        this.transformation = transformation()
        this.occurrence = occurrence
        this.resultType = resultType()
    }

    fun benchmarkingDataEntity(
        id: ULong = 1UL,
        workflowExecution: () -> WorkflowRunEntity = { workflowExecutionEntity() },
        totalExecutionTime: BigDecimal = 127.0.toBigDecimal(),
    ): BenchmarkReportEntity = BenchmarkReportEntity.new(id) {
        this.workflowExecution = workflowExecution()
        this.totalExecutionTime = totalExecutionTime
    }

    fun dataPointEntity(
        id: ULong = 1UL,
        workflow: () -> WorkflowEntity = { workflowEntity() },
        name: String = "ExampleDataPoint",
        description: String? = "Data point description",
        presetValue: String? = null,
        storeMethod: StoreMethodEntity = StoreMethodEntity.BLOB,
        typeNode: () -> TypeNodeEntity = { typeDeclarationEntity() },
        createdAt: Instant = Schema.Utils.instant,
        updatedAt: Instant = Schema.Utils.instant
    ): DataPointEntity = DataPointEntity.new(id) {
        this.workflow = workflow()
        this.name = name
        this.description = description
        this.presetValue = presetValue
        this.storeMethod = storeMethod
        this.typeNode = typeNode()
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }

    fun errorWatchEntity(
        id: ULong = 1UL,
        step: () -> StepEntity = { stepEntity() },
        errorType: String = "RuntimeException",
        onMetWorkflowStep: () -> StepEntity = { step() },
    ): ErrorWatchEntity = ErrorWatchEntity.new(id) {
        this.step = step()
        this.errorType = errorType
        this.onMetStep = onMetWorkflowStep()
    }

    fun errorWatchFnEntity(
        id: ULong = 1UL,
        errorWatch: () -> ErrorWatchEntity = { errorWatchEntity() },
        functionNode: () -> TypeNodeEntity = { typeNodeEntity() },
        position: Int = 1
    ): ErrorWatchFnEntity = ErrorWatchFnEntity.new(id) {
        this.errorWatch = errorWatchEntity()
        this.functionNode = functionNode()
        this.position = position
    }

    fun executionLogEntity(
        id: ULong = 1UL,
        stepExecution: () -> StepTestRunEntity = { stepExecutionEntity() },
        timestamp: Instant = Schema.Utils.instant,
        logLevel: LogLevel = LogLevel.WARNING,
        message: String = "Execution started"
    ): ExecutionNotificationEntity = ExecutionNotificationEntity.new(id) {
        this.stepExecution = stepExecution()
        this.timestamp = timestamp
        this.logLevel = logLevel
        this.message = message
    }

    fun inquisitionEntity(
        id: ULong = 1UL,
        step: () -> StepEntity = { stepEntity() },
        classNode: () -> TypeNodeEntity = { typeNodeEntity() },
        dataPoint: () -> DataPointEntity = { dataPointEntity() },
        onMetWorkflowStep: () -> StepEntity = { step() },
        executionOrder: Int = 1
    ): InquisitionEntity = InquisitionEntity.new(id) {
        this.onMetWorkflowStep = onMetWorkflowStep()
        this.condition = classNode()
        this.dataPoint = dataPoint()
        this.onMetWorkflowStep = onMetWorkflowStep()
        this.executionOrder = executionOrder
    }

    fun packageEntity(
        id: ULong = 1UL,
        name: String = "Example Package",
        disambiguation: String? = "Example Disambiguation",
        //versionSpec: () -> PackagePythonRequirementEntity = {  },
    ): DistributionPackageEntity = DistributionPackageEntity.new(
        CompositeID {

        }
    ) {
    }

    fun paymentMethodEntity(
        id: ULong = 1UL,
        stripePaymentMethodId: String? = "pm_123example",
        user: () -> UserEntity = { userEntity() },
        cardBrand: CardBrandEntity? = CardBrandEntity.VISA,
        lastFour: String? = "4242",
        isDefault: Boolean = true,
        createdAt: Instant = Schema.Utils.instant,
        updatedAt: Instant = Schema.Utils.instant,
    ): PaymentMethodEntity = PaymentMethodEntity.new(id) {
        this.stripePaymentMethodId = stripePaymentMethodId
        this.user = userEntity()
       // this.cardBrand = cardBrand
        this.lastFour = lastFour
        this.isDefault = isDefault
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }

    fun phaseEntityEntity(
        id: ULong = 1UL,
        workflow: () -> WorkflowEntity = { workflowEntity() },
        name: String = "Initial Phase",
        description: String? = "Description of the phase",
        displayOrder: Int = 0
    ): PhaseEntity = PhaseEntity.new(id) {       
        this.workflow = workflow()
        this.name = name
        this.description = description
        this.displayOrder = displayOrder
    }

    fun preReqInjectionEntity(
        id: ULong = 1UL,
        junction: () -> WorkflowJunctionEntity = { workflowJunctionEntity() },
        sourceDataPointEntity: () -> DataPointEntity = { dataPointEntity() },
        injectedDataPointEntity: () -> DataPointEntity = { dataPointEntity() },
        name: String? = "Example Injection",
        description: String? = "A prerequisite data injection"
    ): DataPointInjectionEntity = DataPointInjectionEntity.new(id) {
        this.junction = junction()
        this.sourceDataPoint = sourceDataPointEntity()
        this.injectedDataPoint = injectedDataPointEntity()
        this.name = name
        this.description = description
    }

    fun provisionEntity(
        id: ULong = 1UL,
        step: () -> StepEntity = { stepEntity() },
        toolParam: () -> ToolParamEntity = { toolParamEntity() },
        dataPoints: SizedIterable<DataPointEntity> = EmptySizedIterable()
    ): ToolParamDataPointReadEntity = ToolParamDataPointReadEntity.new(id) {
        this.step = step()
        this.toolParam = toolParam()
        this.dataPointEntity = dataPoints
    }

    fun provisionDataPointEntity(
        provisionId: ULong = 1UL,
        dataPointId: ULong = 1UL,
        provision: () -> ToolParamDataPointReadEntity = { provisionEntity(provisionId) },
        dataPointEntity: () -> DataPointEntity = { dataPointEntity(dataPointId) },
    ): DataPointWriteLinkEntity = DataPointWriteLinkEntity.new(
        DataPointWriteLinkEntity.buildId(provisionId, dataPointId)
    ) {
        this.dataPoint = dataPointEntity()
    }

    fun revisionEntity(
        id: ULong = 1UL,
        dataPoint: () -> DataPointEntity = { dataPointEntity() },
        step: () -> StepEntity = { stepEntity() },
    ): DataPointWriteEntity = DataPointWriteEntity.new(id) {
        this.dataPoint = dataPointEntity()
        this.step = step()
    }

    fun stepEntity(
        id: ULong = 1UL,
        name: String = "Example Step",
        description: String? = "A step in a workflow",
        parentPhase: () -> PhaseEntity? = { null },
        displayOrder: Int = 0,
        workflow: () -> WorkflowEntity = { workflowEntity() },
        isWorkflowHead: Boolean = true,
        tool: () -> ToolEntity = { toolEntity() },
        defaultNext: () -> StepEntity? = { null },
    ): StepEntity = StepEntity.new(id) {
        this.name = name
        this.description = description
        this.parentPhase = parentPhase()
        this.displayOrder = displayOrder
        this.workflow = workflow()
        this.isWorkflowHead = isWorkflowHead
        this.tool = tool()
        //this.defaultNext = defaultNext
    }

    fun stepExecutionEntity(
        id: ULong = 1UL,
        workflowExecution: () -> WorkflowRunEntity = { workflowExecutionEntity() },
        step: () -> StepEntity = { stepEntity() },
        startedAt: Instant = Schema.Utils.instant,
        completedAt: Instant? = Schema.Utils.instant,
        status: ExecutionStatus = ExecutionStatus.RUNNING,
    ): StepTestRunEntity = StepTestRunEntity.new(id) {
        this.step = step()
        this.status = status
    }

    fun tagEntity(
        id: ULong = 1UL,
        name: String = "Example Tag",
        description: String? = "A descriptive tag",
        user: () -> UserEntity = { userEntity() },
    ): TagEntity = TagEntity.new(id) {
        this.name = name
        this.description = description
    }

    fun tagRelationEntity(
        parentTagId: ULong = 1UL,
        childTagId: ULong = 2UL,
    ): TagRelationEntity = TagRelationEntity.new(
        TagRelationDaoDelegate.buildId(tagEntity(parentTagId), tagEntity(childTagId))
    ) {}
    
    fun tagRelationEntity(
        parentTag: () -> TagEntity = { tagEntity(1UL) },
        childTag: () -> TagEntity = { tagEntity(2UL) },
    ): TagRelationEntity = TagRelationEntity.new(
        TagRelationDaoDelegate.buildId(parentTag(), childTag())
    ) {}

    fun testCaseEntity(
        id: ULong = 1UL,
        user: () -> UserEntity = { userEntity() },
        name: String = "Sample Test Case",
        description: String? = "Validates a workflow",
        workflow: () -> WorkflowEntity? = { null },
        step: () -> StepEntity? = { null },
        inputs: List<TestInputEntity> = emptyList(),
        expectedOutcomes: List<TestOutcomeExpectationEntity> = emptyList(),
        results: List<TestResultEntity> = emptyList()
    ): TestCaseEntity = TestCaseEntity.new(id) {
        this.name = name
        this.description = description
        this.workflow = workflow()
        this.step = step()
//        this.inputs = inputs
//        this.expectedOutcomes = expectedOutcomes
//        this.results = results
    }

    fun testExpectedOutcomeEntity(
        id: ULong = 1UL,
        testCase: () -> TestCaseEntity = { testCaseEntity() },
        dataPoint: () -> DataPointEntity = { dataPointEntity() },
        expectedValue: String? = "expectedValue",
        assertion: TestAssertion = TestAssertion.CONTAINS
    ): TestOutcomeExpectationEntity = TestOutcomeExpectationEntity.new(id) {
        this.dataPoint = dataPoint()
        this.assertion = assertion
    }

    fun testInputEntity(
        id: ULong = 1UL,
        testCase: () -> TestCaseEntity = { testCaseEntity() },
        dataPoint: () -> DataPointEntity = { dataPointEntity() },
        value: String? = "testValue",
        generationMethod: GenerationMethod = GenerationMethod.MANUAL
    ): TestInputEntity = TestInputEntity.new(id) {
        this.testCase = testCaseEntity()
        this.dataPoint = dataPoint()
        this.value = value
        this.generationMethod = generationMethod
    }

    fun testResultEntity(
        id: ULong = 1UL,
        testCase: () -> TestCaseEntity = { testCaseEntity() },
        workflowExecution: () -> WorkflowRunEntity = { workflowExecutionEntity() },
        passed: Boolean = true,
        executedAt: Instant = Schema.Utils.instant,
    ): TestResultEntity = TestResultEntity.new(id) {
        this.testCase = testCase()
        this.workflowExecution = workflowExecution()
        this.passed = passed
        this.executedAt = executedAt
    }

    fun toolEntity(
        id: ULong = 1UL,
        name: String = "ExampleTool",
        description: String? = "Tool description",
        body: String = "fun ok()",
        returnTypeNode: () -> TypeNodeEntity? = { null },
    ): ToolEntity = ToolEntity.new(id) {
        this.name = name
        this.description = description
        this.body = body
        this.returnTypeNode = returnTypeNode()
    }

    fun toolParamEntity(
        id: ULong = 1UL,
        name: String = "exampleParam",
        tool: () -> ToolEntity = { toolEntity() },
        typeNode: () -> TypeNodeEntity = { typeDeclarationEntity() },
    ): ToolParamEntity = ToolParamEntity.new(id) {
        this.name = name
        this.tool = tool()
        this.typeNode = typeNode()
    }

    fun transformationArgEntity(
        id: ULong = 1UL,
        transformation: () -> TransformEntity = { transformationEntity() },
        typeNode: () -> TypeNodeEntity = { typeDeclarationEntity() },
        name: String = "Argument Name",
        description: String? = "Argument Description"
    ): TransformArgEntity = TransformArgEntity.new(id) {
        this.transformation = transformation()
        this.typeNode = typeNode()
        this.name = name
        this.description = description
    }

    fun transformationEntity(
        id: ULong = 1UL,
        name: String = "Example Transformation",
        description: String = "Transforms data",
        examples: String? = "Input -> Output",
        returnsType: () -> TypeNodeEntity? = { null },
    ): TransformEntity = TransformEntity.new(id) {
        this.name = name
        this.description = description
        this.examples = examples
    }

    fun transformationTargetTypeEntity(
        transformationId: ULong = 1UL,
        typeDeclarationId: ULong = 1UL,
        transformation: () -> TransformEntity = { transformationEntity(transformationId) },
        typeNode: () -> TypeNodeEntity = { typeDeclarationEntity(typeDeclarationId) },
    ): TransformTargetTypeEntity = TransformTargetTypeEntity.new(
        TransformTargetTypeEntity.buildId(transformationId, typeDeclarationId)
    ) {
        this.transformation = transformation()
        this.eligibleTypeNode = typeNode()
    }

    fun typeBoundEntity(
        id: ULong = 1UL,
        boundTypeNode: () -> TypeNodeEntity = { typeDeclarationEntity() },
        typeVariableDeclaration: () -> TypeNodeEntity = { typeDeclarationEntity() },
    ): TypeVarBoundEntity = TypeVarBoundEntity.new(
        CompositeID {

        }
    ) {
        this.boundTypeNode = boundTypeNode()
        this.typeVarNode = typeVariableDeclaration()
    }

    fun typeDeclarationEntity(
        id: ULong = 1UL,
        typeVarConstraint: () -> TypeVarConstraintEntity = { typeDefinitionEntity() },
        alias: String? = "CustomType"
    ): TypeNodeEntity = TypeNodeEntity.new(id) {
        //this.typeDefinitionEntity = typeVarConstraint
        this.alias = alias
    }

    fun typeDefinitionEntity(
        id: ULong = 1UL,
        name: String = "List",
        namespace: String = "builtins"
    ): TypeVarConstraintEntity = TypeVarConstraintEntity.new(
        CompositeID {

        }
    ) {
        //this.name = name
    }

    fun typeNodeEntity(
        id: ULong = 1UL,
        alias: String? = "GenericType"
    ): TypeNodeEntity = TypeNodeEntity.new(id) {
        this.alias = alias
    }

    fun typeParameterEntity(
        id: ULong = 1UL,
        typeNode: () -> TypeNodeEntity = { typeDeclarationEntity() },
        position: Int = 1,
        parameterTypeNode: () -> TypeNodeEntity = { typeDeclarationEntity() },
    ): TypeParameterEntity = TypeParameterEntity.new(id) {
        this.typeNode = typeNode()
        this.position = position
        this.parameterTypeNode = parameterTypeNode()
    }

    fun typeSchemaRefEntity(
        id: ULong = 1UL,
        parentRef: () -> TypeSchemaRefEntity? = { null },
        name: String = "exampleSchema",
        typeNode: () -> TypeNodeEntity = { typeDeclarationEntity() },
        description: String? = "Schema reference description",
        childRefs: List<TypeSchemaRefEntity> = emptyList()
    ): TypeSchemaRefEntity = TypeSchemaRefEntity.new(id) {
        this.parentRef = parentRef()
        this.name = name
        this.typeNode = typeNode()
        this.description = description
        //this.childRefs = childRefs
    }

    fun typeUnionMemberEntity(
        unionTypeId: ULong = 1UL,
        memberTypeId: ULong = 1UL,
    ): TypeUnionMemberEntity = TypeUnionMemberEntity.new(
        TypeUnionMemberDaoDelegate.buildId(unionTypeId, memberTypeId)
    ){}

    fun typeUnionMemberEntity(
        unionType: () -> TypeNodeEntity = { typeNodeEntity(1UL) },
        memberType: () -> TypeNodeEntity = { typeNodeEntity(2UL) }
    ): TypeUnionMemberEntity = TypeUnionMemberEntity.new(
        TypeUnionMemberDaoDelegate.buildId(unionType(), memberType())
    ){}

    fun typeVarianceEntity(
        id: ULong = 1UL,
        typeVariableDeclaration: () -> TypeNodeEntity = { typeDeclarationEntity() },
        varianceCardinality: VarianceCardinality = VarianceCardinality.COVARIANT
    ): TypeVarVarianceEntity = TypeVarVarianceEntity.new(
        CompositeID {

        }
    ) {
        this.typeVarNode = typeVariableDeclaration()
        this.varianceCardinality = varianceCardinality
    }

    fun userEntity(
        id: ULong = 1UL,
        email: String = "jim@jim.com",
        password: String = "superSecretStuff1!",
        resetToken: String? = null,
        oauthProvider: String? = null,
        oauthId: String? = null,
        subscriptionStatus: SubscriptionStatus = SubscriptionStatus.REGISTERED,
        apiKey: String? = null,
        createdAt: Instant = Schema.Utils.instant,
        updatedAt: Instant = Schema.Utils.instant,
        paymentMethodEntities: List<PaymentMethodEntity> = emptyList()
    ): UserEntity = UserEntity.new(id) {
        this.email = email
        this.password = password
        this.resetToken = resetToken
        this.oauthProvider = oauthProvider
        this.oauthId = oauthId
        //this.subscriptionStatus = subscriptionStatus
        this.apiKey = apiKey
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        //this.paymentMethods = paymentMethods
    }


//    fun userDesignedMetadataEntity(
//        name: String,
//        version: String = "1.0.0",
//        authors: List<String> = emptyList(),
//        copyrightInformation: String? = null,
//        contactInformation: String? = null,
//        thirdPartyLicenses: String? = null,
//        discretionAdvisedContent: Boolean = false,
//        commercialUsage: CommercialUse = CommercialUse.PERSONAL_NON_PROFIT,
//        creditNotation: AuthorshipCredit = AuthorshipCredit.REQUIRED,
//        allowRedistribution: Boolean = false,
//        modification: ModificationTerms = ModificationTerms.PROHIBITED,
//        distributionAgreement: DistributionAgreement = DistributionAgreement(),
//        sharingConfig: StepInclusions = StepInclusions()
//    ): UserDesignedMetadata = UserDesignedMetadata(
//        this.name = name
//        this.version = version
//        this.authors = authors
//        this.copyrightInformation = copyrightInformation
//        this.contactInformation = contactInformation
//        this.thirdPartyLicenses = thirdPartyLicenses
//        this.discretionAdvisedContent = discretionAdvisedContent
//        this.commercialUsage = commercialUsage
//        this.creditNotation = creditNotation
//        this.allowRedistribution = allowRedistribution
//        this.modification = modification
//        this.distributionAgreement = distributionAgreement
//        this.sharingConfig = sharingConfig
//    )

    fun workflowExecutionEntity(
        id: ULong = 1UL,
        workflow: () -> WorkflowEntity = { workflowEntity() },
        user: () -> UserEntity = { userEntity() },
        startedAt: Instant = Schema.Utils.instant,
        completedAt: Instant? = Schema.Utils.instant,
        status: ExecutionStatus = ExecutionStatus.FAILED,
        stepExecutions: List<StepTestRunEntity> = emptyList()
    ): WorkflowRunEntity = WorkflowRunEntity.new(id) {
        this.workflow = workflow()
        this.startedAt = startedAt
        this.completedAt = completedAt
        this.status = status
        //this.stepExecutions = stepExecutions
    }

    fun pythonVersionEntity(
        id: ULong = 1UL
    ): PythonVersionEntity = PythonVersionEntity.new(id) {
        this.version = versionEntity(id)
    }

    fun pythonVersionEntity(
        version: VersionEntity
    ): PythonVersionEntity = PythonVersionEntity(version.id)


    fun workflowEntity(
        id: ULong = 1UL,
        name: String = "Example Workflow",
        description: String? = "A sample workflow description",
        pythonVersion: () -> PythonVersionEntity = { pythonVersionEntity() },
        parentFolder: () -> WorkflowFolderEntity? = { null },
        createdAt: Instant = Schema.Utils.instant,
        updatedAt: Instant = Schema.Utils.instant,
    ): WorkflowEntity = WorkflowEntity.new(id) {
        this.name = name
        this.description = description
        this.pythonVersion = pythonVersion()
        this.parentFolder = parentFolder()
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }

    fun workflowFolderEntity(
        id: ULong = 1UL,
        parentFolder: () -> WorkflowFolderEntity? = { null },
        name: String = "ExampleFolder",
        description: String? = "Folder description",
        createdAt: Instant = Schema.Utils.instant,
        updatedAt: Instant = Schema.Utils.instant,
    ): WorkflowFolderEntity = WorkflowFolderEntity.new(id) {
        this.parentFolder = parentFolder()
        this.name = name
        this.description = description
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }

    fun workflowJunctionEntity(
        id: ULong = 1UL,
        sourceStep: () -> StepEntity = { stepEntity() },
        targetWorkflow: () -> WorkflowEntity = { workflowEntity() },
        triggerInquisitionEntity: () -> InquisitionEntity? = { null },
    ): WorkflowJunctionEntity = WorkflowJunctionEntity.new(id) {
        this.sourceStep = sourceStep()
        this.targetWorkflow = targetWorkflow()
        this.triggerInquisitionEntity = triggerInquisitionEntity()
    }


}