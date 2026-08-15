package com.wini.owle.dto

import com.wini.owle.dto.environs.enums.EnvMarkerExpressionMode
import com.wini.owle.dto.environs.enums.EnvMarkerVariable

import com.wini.owle.dto.environs.marker.EnvMarkerComparison
import com.wini.owle.dto.environs.marker.EnvMarkerExpression
import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.dto.environs.pkg.DistributionPackage
import com.wini.owle.dto.environs.pkg.PackageDistRequirement
import com.wini.owle.dto.environs.version.PythonVersion
import com.wini.owle.dto.environs.version.PythonVersionSpecifier
import com.wini.owle.dto.environs.version.Version
import com.wini.owle.dto.environs.version.VersionOverflow
import com.wini.owle.dto.environs.version.VersionSpecifier
import com.wini.owle.dto.environs.version.enums.OverflowViolation
import com.wini.owle.dto.environs.version.enums.PrereleasePhase
import com.wini.owle.dto.environs.version.enums.VersionOperator
import com.wini.owle.dto.environs.version.enums.WildcardSegment
import com.wini.owle.dto.runs.enums.ComparisonOperator
import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.runs.enums.GenerationMethod
import com.wini.owle.dto.runs.enums.LogLevel
import com.wini.owle.dto.runs.enums.TestAssertion
import com.wini.owle.dto.runs.step.StepTestRun
import com.wini.owle.dto.runs.step.ExecutionNotification
import com.wini.owle.dto.runs.test.BenchmarkReport
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.dto.runs.test.TestOutcomeExpectation
import com.wini.owle.dto.runs.test.TestInput
import com.wini.owle.dto.runs.workflow.WorkflowRun
import com.wini.owle.dto.runs.workflow.TestOutcomeActual
import com.wini.owle.dto.steps.DataPointInjection
import com.wini.owle.dto.steps.DataPointRead
import com.wini.owle.dto.steps.DataPointWrite
import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.dto.steps.Inquisition
import com.wini.owle.dto.steps.ToolParam
import com.wini.owle.dto.steps.WorkflowJunction
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.tags.TagRelation
import com.wini.owle.dto.tags.enums.TagSource
import com.wini.owle.dto.transforms.Transform
import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.dto.transforms.TransformOccurrence
import com.wini.owle.dto.transforms.TransformOccurrenceArg
import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.dto.typing.TypeParameter
import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.dto.typing.TypeVarBound
import com.wini.owle.dto.typing.TypeVarVariance
import com.wini.owle.dto.typing.enums.VarianceCardinality
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.dto.user.User
import com.wini.owle.dto.user.enums.CardBrand
import com.wini.owle.dto.user.enums.UserStatus
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Phase
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Tool
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.dto.workflows.enums.StoreMethod

@Suppress("UNCHECKED_CAST")
object DtoTestInstanceFactory {

    fun envMarkerComparison(
        id: ULong = 1UL,
        expression: EnvMarkerExpression = envMarkerExpression(),
        variable: EnvMarkerVariable? = EnvMarkerVariable.SYS_PLATFORM,
        operator: ComparisonOperator = ComparisonOperator.EQUALS,
        value: String = "darwin"
    ): EnvMarkerComparison = envMarkerComparison(
        id,
        expression,
        variable,
        operator,
        value
    )

    fun typeUnionMember(
        unionTypeNode: TypeNode = typeNode(),
        memberTypeNode: TypeNode = typeNode(),
    ): TypeUnionMember = TypeUnionMember(
        unionTypeNode = unionTypeNode,
        memberTypeNode = memberTypeNode
    )

    fun envMarkerExpression(
        id: ULong = 1UL,
        requirement: PackageDistRequirement = packageRequiredDist(),
        parent: EnvMarkerExpression? = envMarkerExpression(),
        mode: EnvMarkerExpressionMode = EnvMarkerExpressionMode.COMPARATIVE
    ) : EnvMarkerExpression = EnvMarkerExpression(
        id = id,
        parent = parent,
        mode = mode
    )

//    fun distributionPackage(
//        distribution: Distribution = distribution(),
//        version: Version = version(),
//        tags: List<Tag> = emptyList(),
//        extras: List<PackageExtra> = emptyList(),
//        requirements: List<PackageRequiredDist> = emptyList()
//    ): DistributionPackage = DistributionPackage(
//        distribution = distribution,
//        version = version,
//        requirements = requirements
//    )

    fun distributionPackage(): DistributionPackage = DistributionPackage(
        distribution = distribution(),
        version = version(),
        versionString = "",
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        false,
        null,
        //emptyList()
    )

    fun packageRequiredDist(
        id: ULong = 1UL,
        dependentDistribution: Distribution = distribution(),
        dependentVersion: Version = version(),
        requiredDistribution: Distribution = distribution(),
        requiredVersion: Version? = version(),
        operator: VersionOperator = VersionOperator.EXACT,
        wildcardSegment: WildcardSegment = WildcardSegment.MINOR
    ) : PackageDistRequirement = PackageDistRequirement(
        id = id,
        dependentDistribution = dependentDistribution,
        dependentVersion = dependentVersion,
        requiredDistribution = requiredDistribution,
        requiredVersion = requiredVersion,
        operator = operator,
        wildcardSegment = wildcardSegment
    )

    fun distribution(
        name: String = "numpy"
    ): Distribution = Distribution(name)

    fun version(
        id: ULong = 10UL,
        epoch: ULong = 0UL,
        major: ULong = 1UL,
        minor: ULong = 0UL,
        micro: ULong = 0UL,
        phase: PrereleasePhase = PrereleasePhase.FINAL,
        phaseNumber: ULong? = null,
        postNumber: ULong? = null,
        devNumber: ULong? = null,
        local: String? = null,
        versionString: String = "1.0",
        hasOverflow: Boolean = false,
        overflowComponents: List<VersionOverflow> = emptyList()
    ): Version = Version(
        id = id,
        epoch = epoch,
        major = major,
        minor = minor,
        micro = micro,
        phase = phase,
        phaseNumber = phaseNumber,
        postNumber = postNumber,
        devNumber = devNumber,
        local = local,
        hasOverflow = hasOverflow,
        overflowComponents = overflowComponents
    )

    fun versionOverflow(
        id: ULong,
        version: Version,
        violation: OverflowViolation,
        stringValue: String
    ): VersionOverflow = VersionOverflow(
        id = 1UL,
        version = version(),
        violation = OverflowViolation.PHASE_NUMBER,
        stringValue = ""
    )

    fun versionSpecifier(
        id: ULong = 0UL
    ): VersionSpecifier = VersionSpecifier(
        id = id
    )


    fun stepDto(
        id: ULong = 3UL,
        name: String = "Example Step",
        description: String? = "A step in a workflow",
        workflow: Workflow = workflowDto(),
        isWorkflowHead: Boolean = true,
        tool: Tool = toolDto(),
        parentPhase: Phase? = null,
        displayOrder: Int = 0,
        nextStep: Step? = null
    ): Step {
        return Step(
            id = id,
            name = name,
            description = description,
            workflow = workflow,
            isWorkflowHead = isWorkflowHead,
            tool = tool,
            parentPhase = parentPhase,
            displayOrder = displayOrder
        )
    }

//    fun workflowMetadataDto(
//        id: Long = 1L,
//        name: String = "John Arbuckle",
//        version: String = "1.0.0",
//        contributors: List<User> = emptyList(),
//        //authors = listOf("Garfield Cat", "Nermal"),
//        copyrightInformation:String = "2025",
//        contactInformation: String = "lasagnalonely@email.com",
//        thirdPartyLicenses: String = "Ok",
//        distributionVersion: String = "1.0.0",
//        distributionAcceptedAt: String = "",
//        distributionUpdatedAt: String = "",
//        discretionAdvisedContent: Boolean = true,
//        commercialUsage: CommercialUse = CommercialUse.PERSONAL_PROFIT,
//        authorshipCredit: AuthorshipCreditDto = AuthorshipCredit,
//        allowRedistribution: Boolean = false,
//        modification: ModificationTermsDto = ModificationTermsDto.Prohibited,
//        excludedSteps: List<Step> = emptyList()
//    ): WorkflowMetadata {
//        return WorkflowMetadata(
//            id = id,
//            name = name,
//            version = version,
//            contributors = contributors,
//            copyrightInformation = copyrightInformation,
//            contactInformation = contactInformation,
//            thirdPartyLicenses = thirdPartyLicenses,
//            discretionAdvisedContent = discretionAdvisedContent,
//            commercialUsage = commercialUsage,
//            creditNotation = authorshipCredit,
//            allowRedistribution = allowRedistribution,
//            modification = modification,
//            distributionVersion = distributionVersion,
//            distributionAcceptedAt = distributionAcceptedAt,
//            distributionUpdatedAt = distributionUpdatedAt,
//            excludedSteps = excludedSteps
//        )
//    }

    fun pythonVersionSpecifier(
        id: ULong = 1UL,
        pythonVersion: PythonVersion = pythonVersion(),
        operator: VersionOperator = VersionOperator.EXACT,
        wildcard: WildcardSegment = WildcardSegment.MINOR
    ): PythonVersionSpecifier = PythonVersionSpecifier(
        id = id,
        pythonVersion = pythonVersion,
        operator = operator,
        wildcard = wildcard
    )

    fun pythonVersion(
        id: ULong = 1UL,
        version: Version = version()
    ): PythonVersion = PythonVersion(
        id,
        version
    )

    fun workflowDto(
        id: ULong = 1UL,
        name: String = "Example Workflow",
        description: String? = "A sample workflow description",
        pythonVersion: PythonVersion = pythonVersion(),
        parentFolder: WorkflowFolder? = null,
        createdAt: String = "2024-12-20T15:00:00",
        updatedAt: String = "2024-12-21T15:00:00"
    ): Workflow {
        return Workflow(
            id = id,
            name = name,
            description = description,
            pythonVersion = pythonVersion,
            parentFolder = parentFolder,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun toolDto(
        id: ULong = 1UL,
        name: String = "Tool Name",
        description: String? = "Tool Description",
        body: String = "a sample of code",
        returnTypeDeclaration: TypeNode? = typeNode(),
        steps: List<Step> = emptyList()
    ): Tool {
        return Tool(
            id = id,
            name = name,
            description = description,
            body = body,
            returnTypeDeclaration = returnTypeDeclaration
        )
    }

    fun testCaseDto(
        id: ULong = 1UL,
        user: User = userDto(),
        name: String = "Test Case Name",
        description: String = "Test Case Description",
        workflow: Workflow = workflowDto(),
        step: Step = stepDto(),
        inputs: List<TestInput> = emptyList(),
        expectedOutcomes: List<TestOutcomeExpectation> = emptyList(),
        results: List<TestOutcomeActual> = emptyList()
    ) : TestCase {
        return TestCase(
            id = id,
            name = name,
            description = description,
            workflow = workflow,
            step = step,
            inputs = inputs,
            expectedOutcomes = expectedOutcomes,
            results = results
        )
    }

    fun dataPointDto(
        id: ULong = 1UL,
        name: String = "Data Point Name",
        description: String? = "Data Point Description",
        presetValue: String? = "Some stuff",
        storeMethod: StoreMethod = StoreMethod.BLOB,
        typeDeclaration: TypeNode = typeNode(),
        workflow: Workflow = workflowDto()
    ): DataPoint {
        return DataPoint(
            id = id,
            name = name,
            description = description,
            presetValue = presetValue,
            storeMethod = storeMethod,
            typeDeclaration = typeDeclaration,
            workflow = workflow,
            createdAt = "2024-12-21T15:00:00",
            updatedAt = "2024-12-21T15:00:00"
        )
    }

    fun toolParamDto(
        id: ULong = 1UL,
        name: String = "Tool Param",
        typeDeclaration: TypeNode = typeNode(),
        tool: Tool = toolDto(),
        hasDefault: Boolean = true
    ) : ToolParam {
        return ToolParam(
            id = id,
            name = name,
            typeDeclaration = typeDeclaration,
            tool = tool
        )
    }

    fun paymentMethodDto(
        id: ULong = 1UL,
        stripePaymentMethodId: String? = "1AF002B1B22200",
        user: User = userDto(),
        cardBrand: CardBrand? = CardBrand.VISA,
        lastFour: String? = "4103",
        isDefault: Boolean = true,
        createdAt: String = "2024-12-21T15:00:00",
        updatedAt: String = "2024-12-22T15:00:00"
    ) : PaymentMethod {
        return PaymentMethod(
            id = id,
            stripePaymentMethodId = stripePaymentMethodId,
            user = user,
            cardBrand = cardBrand,
            lastFour = lastFour,
            isDefault = isDefault,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun tagDto(
        id: ULong = 1UL,
        name: String = "Tag Name",
        description: String? = "Tag Description",
        source: TagSource = TagSource.SYSTEM,
        userId: ULong  = 1UL
    ) : Tag {
        return Tag(
            id = id,
            name = name,
            description = description,
            source = source
        )
    }

    fun tagRelationDto(
        parentTagId: ULong = 1UL,
        childTagId: ULong = 2UL
    ) : TagRelation {
        return TagRelation(
            parentTagId = parentTagId,
            childTagId = childTagId
        )
    }

    fun workflowExecutionDto(
        id: ULong = 1UL,
        workflow: Workflow = workflowDto(),
        user: User = userDto(),
        startedAt: String = "2024-12-21T10:00:00",
        completedAt: String = "2024-12-21T10:00:00",
        status: ExecutionStatus = ExecutionStatus.FAILED,
        stepTestRuns: List<StepTestRun> = emptyList()
    ) : WorkflowRun {
        return WorkflowRun(
            id = id,
            workflow = workflow,
            startedAt = startedAt,
            completedAt = completedAt,
            status = status,
            stepTestRuns = stepTestRuns
        )
    }

    fun benchmarkData(
        id: ULong = 1UL,
        workflowRun: WorkflowRun = workflowExecutionDto(),
        totalExecutionTime: Double = 123.45,
        totalSteps: Int = 35,
        aiDeterminedSteps: Int = 4,
        preDeterminedSteps: Int = 8
    ) : BenchmarkReport {
        return BenchmarkReport(
            id = id,
            workflowRun = workflowRun
        )
    }

    fun userDto(
        id: ULong = 1UL,
        email: String = "jim@jim.com",
        password: String = "secretStuff1!",
        resetToken: String? = "3ij3oi2joi342j",
        oauthProvider: String? = "Google",
        oauthId: String? = "IROEJOWejofdjsojfoaj3ioj439u2jiof",
        userStatus: UserStatus = UserStatus.REGISTERED,
        apiKey: String? = "jowejrojoewjrew",
        createdAt: String = "2024-12-24T15:00:00",
        updatedAt: String = "2024-12-25T15:00:00",
        paymentMethods: List<PaymentMethod> = emptyList()
    ) : User {
        return User(
            id = id,
            email = email,
            password = password,
            resetToken = resetToken,
            oauthProvider = oauthProvider,
            oauthId = oauthId,
            userStatus = userStatus,
            apiKey = apiKey,
            createdAt = createdAt,
            updatedAt = updatedAt,
            paymentMethods = paymentMethods
        )
    }

    fun executionLogDto(
        id: ULong = 1UL,
        stepTestRun: StepTestRun = stepRun(),
        timeStamp: String = "2024-12-21T12:00:00Z",
        logLevel: LogLevel = LogLevel.INFO,
        message: String = "Execution Started"
    ) : ExecutionNotification {
        return ExecutionNotification(
            id = id,
            stepTestRun = stepTestRun,
            timestamp = timeStamp,
            logLevel = logLevel,
            message = message
        )
    }

//    fun stepBenchmarkingDataDto(
//        id: ULong = 0UL,
//        stepExecution: StepExecution = stepExecutionDto(),
//        executionTime: Double = 123.45
//    ) : BenchmarkReport {
//        return BenchmarkReport(
//            id = id,
//            stepExecution = stepExecution,
//            executionTime = executionTime
//        )
//    }

    fun stepRunLogLine(
        id: ULong = 1UL,
        stepTestRun: StepTestRun = stepRun(),
        timestamp: String = "2024-12-21T10:00:00",
        logLevel: LogLevel = LogLevel.INFO,
        message: String = "text ok test text"
    ) : ExecutionNotification {
        return ExecutionNotification(
            id = id,
            stepTestRun = stepTestRun,
            timestamp = timestamp,
            logLevel = logLevel,
            message = message
        )
    }

    fun stepRun(
        id: ULong = 1UL,
        workflowRun: WorkflowRun = workflowExecutionDto(),
        step: Step = stepDto(),
        startedAt: String = "2024-12-21T10:00:00",
        completedAt: String = "2024-12-21T10:00:00",
        status: ExecutionStatus = ExecutionStatus.FAILED
    ) : StepTestRun {
        return StepTestRun(
            id = id,
            step = step,
            startedAt = startedAt,
            completedAt = completedAt,
            status = status
        )
    }

    fun errorWatch(
        id: ULong = 11UL,
        step: Step = stepDto(),
        errorType: String = "RuntimeException",
        triggerStep: Step = stepDto()
    ) : ErrorWatch {
        return ErrorWatch(
            id = id,
            step = step,
            errorType = errorType,
            triggerStep = triggerStep
        )
    }

    fun errorWatchFnDto(
        id: ULong = 1UL,
        errorWatch: ErrorWatch = errorWatch(),
        functionNode: TypeNode = typeNode(),
        position: Int = 1,
    ) : ErrorWatchFn {
        return ErrorWatchFn(
            id = id,
            errorWatch = errorWatch,
            functionNode = functionNode,
            position = position
        )
    }

    fun inquisitionDto(
        id: ULong = 1UL,
        step: Step = stepDto(),
        condition: TypeNode = typeNode(),
        dataPoint: DataPoint = dataPointDto(),
        onMetWorkflowStep: Step = stepDto(),
        executionOrder: Int = 1
    ) : Inquisition {
        return Inquisition(
            id = id,
            step = step,
            condition = condition,
            dataPoint = dataPoint,
            onMetWorkflowStep = onMetWorkflowStep,
            executionOrder = executionOrder
        )
    }

    fun preReqInjectionDto(
        id: ULong = 1UL,
        junction: WorkflowJunction = workflowJunctionDto(),
        sourceDataPoint: DataPoint = dataPointDto(),
        injectedDataPoint: DataPoint = dataPointDto(),
        name: String = "Name",
        description: String? = "Description"
    ) : DataPointInjection {
        return DataPointInjection(
            id = id,
            junction = junction,
            sourceDataPoint = sourceDataPoint,
            injectedDataPoint = injectedDataPoint,
            name = name,
            description = description
        )
    }

    fun workflowJunctionDto(
        id: ULong = 1UL,
        sourceStep: Step = stepDto(),
        targetWorkflow: Workflow = workflowDto(),
        triggerInquisition: Inquisition = inquisitionDto()
    ) : WorkflowJunction {
        return WorkflowJunction(
            id = id,
            sourceStep = sourceStep,
            targetWorkflow = targetWorkflow,
            triggerInquisition = triggerInquisition
        )
    }

    fun provisionDto(
        id: ULong = 1UL,
        step: Step = stepDto(),
        toolParam: ToolParam = toolParamDto(),
        dataPoints: List<DataPoint> = emptyList()
    ) : DataPointRead {
        return DataPointRead(
            id = id
        )
    }

    fun transformTargetType(
        transformationId: ULong = 1UL,
        typeDeclarationId: ULong = 2UL
    ): TransformTargetType = TransformTargetType(
        transformationId = transformationId,
        typeDeclarationId = typeDeclarationId
    )

    fun transformationDto(
        id: ULong = 1UL,
        name: String = "Transformation Name",
        description: String = "Transformation Description",
        examples: String? = "Example",
        returnsType: TypeNode = typeNode()
    ) : Transform {
        return Transform(
            id = id,
            name = name,
            description = description,
            examples = examples
        )
    }

    fun revisionDto(
        id: ULong = 1UL,
        dataPoint: DataPoint = dataPointDto(),
        step: Step = stepDto()
    ) : DataPointWrite {
        return DataPointWrite(
            id = id,
            dataPoint = dataPoint,
            step = step
        )
    }

    fun typeParameterDto(
        id: ULong = 1UL,
        typeDeclaration: TypeNode = typeNode(),
        position: Int = 1,
        parameterTypeDeclaration: TypeNode = typeNode()
    ) : TypeParameter {
        return TypeParameter(
            id = id,
            typeDeclaration = typeDeclaration,
            position = position,
            parameterTypeDeclaration = parameterTypeDeclaration
        )
    }

    fun typeSchemaRefDto(
        id: ULong = 1UL,
        parentRef: TypeSchemaRef? = null,
        name: String = "exampleSchema",
        typeDeclaration: TypeNode = typeNode(),
        description: String = "Schema reference description",
        childRefs: List<TypeSchemaRef> = emptyList()
    ) : TypeSchemaRef {
        return TypeSchemaRef(
            id = id,
            parentRef = parentRef,
            name = name,
            typeDeclaration = typeDeclaration,
            description = description,
            childRefs = childRefs
        )
    }

    fun phaseDto(
        id: ULong = 1UL,
        workflow: Workflow = workflowDto(),
        parentPhase: Phase? = null,
        name: String = "Phase Name",
        description: String? = "Phase Description",
        displayOrder: Int = 1
    ) : Phase {
        return Phase(
            id = id,
            workflow = workflow,
            parentPhase = parentPhase,
            name = name,
            description = description,
            displayOrder = displayOrder
        )
    }

    fun typeNode(): TypeNode = TypeNode(
        id = 1UL,
        version = version(),
        null,
        "",
        null,
        false
    )

    fun typeUnionMemberDto(
        unionTypeDeclarationId: TypeNode = typeNode(),
        memberTypeDeclarationId: TypeNode = typeNode()
    ): TypeUnionMember {
        return TypeUnionMember(
            unionTypeNode = unionTypeDeclarationId,
            memberTypeNode = memberTypeDeclarationId
        )
    }


    fun typeVarianceDto(
        id: ULong = 23UL,
        typeVariableDeclaration: TypeNode = typeNode(),
        varianceCardinality: VarianceCardinality = VarianceCardinality.COVARIANT
    ): TypeVarVariance {
        return TypeVarVariance(
            typeNode = typeVariableDeclaration,
            cardinality = varianceCardinality
        )
    }

    fun workflowFolderDto(
        id: ULong = 32UL,
        parentFolder: WorkflowFolder? = null,
        name: String = "ExampleFolder",
        description: String? = "Folder description",
        img: String? = null,
        createdAt: String = "2024-12-21T10:00:00",
        updatedAt: String = "2024-12-21T10:00:00"
    ): WorkflowFolder {
        return WorkflowFolder(
            id = id,
            parentFolder = parentFolder,
            name = name,
            description = description,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun testInputDto(
        id: ULong = 6UL,
        dataPoint: DataPoint = dataPointDto(),
        value: String = "value1",
        generationMethod: GenerationMethod = GenerationMethod.MANUAL,
        testCase: TestCase = testCaseDto()
    ): TestInput {
        return TestInput(
            id = id,
            dataPoint = dataPoint,
            value = value,
            generationMethod = generationMethod,
            testCase = testCase
        )
    }
    fun testExpectedOutcomeDto(
        id: ULong = 7UL,
        dataPoint: DataPoint = dataPointDto(),
        expectedValue: String = "expectedValue1",
        assertion: TestAssertion = TestAssertion.EQUALS,
        testCase: TestCase = testCaseDto()
    ): TestOutcomeExpectation {
        return TestOutcomeExpectation(
            id = id,
            dataPoint = dataPoint,
            assertion = assertion
        )
    }

    fun testResultDto(
        id: ULong = 1UL,
        testCase: TestCase = testCaseDto(),
        workflowRun: WorkflowRun = workflowExecutionDto(),
        passed: Boolean = true,
        executedAt: String = "2024-12-21T00:00:00Z"
    ): TestOutcomeActual {
        return TestOutcomeActual(
            id = id,
            passed = passed
        )
    }

    fun typeBoundDto(
        id: ULong = 1UL,
        boundTypeDeclaration: TypeNode = typeNode(),
        typeVariableDeclaration: TypeNode = typeNode()
    ) : TypeVarBound {
        return TypeVarBound(
            boundTypeNode = boundTypeDeclaration,
            typeVarNode = typeVariableDeclaration
        )
    }

    fun appliedTransformArg(
        id: ULong = 0UL,
        transformOccurrence: TransformOccurrence = appliedTransformDto(),
        transformationArg: TransformArg = transformationArg(),
        value: String = ""
    ) : TransformOccurrenceArg {
        return TransformOccurrenceArg(
            id = id,
            transformOccurrence = transformOccurrence,
            transformationArg = transformationArg,
            value = value
        )
    }

    fun transformationArg(
        id: ULong = 0UL,
        transformation: Transform = transformationDto(),
        name: String = "Transformation Arg Name",
        typeNode: TypeNode = typeNode(),
        description: String? = "Transformation Arg Description"
    ): TransformArg {
        return TransformArg(
            id = id,
            transformation = transformation,
            name = name,
            typeNode = typeNode,
            description = description
        )
    }

    fun appliedTransformDto(
        id: ULong = 0UL,
        transformation: Transform = transformationDto(),
        occurrence: com.wini.owle.dto.transforms.enums.TransformOccurrence = com.wini.owle.dto.transforms.enums.TransformOccurrence.DATA_POINT_INJECTION,
        resultType: TypeNode = typeNode()
    ) : TransformOccurrence {
        return TransformOccurrence(
            id = id,
            transformation = transformation,
            occurrence = occurrence,
            resultType = resultType
        )
    }


    fun <T : BaseDto> createDtoInstance(dtoClass: Class<T>): T {
        return when (dtoClass) {
            Step::class.java -> stepDto() as T
            Workflow::class.java -> workflowDto() as T
            Tool::class.java -> toolDto() as T
            TestCase::class.java -> testCaseDto() as T
            DataPoint::class.java -> dataPointDto() as T
            ToolParam::class.java -> toolParamDto() as T
            TypeNode::class.java -> typeNode() as T
            PaymentMethod::class.java -> paymentMethodDto() as T
            Tag::class.java -> tagDto() as T
            TagRelation::class.java -> tagRelationDto() as T
            BenchmarkReport::class.java -> benchmarkData() as T
            User::class.java -> userDto() as T
            ExecutionNotification::class.java -> executionLogDto() as T
            ErrorWatch::class.java -> errorWatch() as T
            ErrorWatchFn::class.java -> errorWatchFnDto() as T
            Inquisition::class.java -> inquisitionDto() as T
            DataPointInjection::class.java -> preReqInjectionDto() as T
            DataPointRead::class.java -> provisionDto() as T
            DataPointWrite::class.java -> revisionDto() as T
            TypeParameter::class.java -> typeParameterDto() as T
            TypeSchemaRef::class.java -> typeSchemaRefDto() as T
            TypeUnionMember::class.java -> typeUnionMemberDto() as T
            TypeVarVariance::class.java -> typeVarianceDto() as T
            Phase::class.java -> phaseDto() as T
            WorkflowFolder::class.java -> workflowFolderDto() as T
            TestInput::class.java -> testInputDto() as T
            TestOutcomeExpectation::class.java -> testExpectedOutcomeDto() as T
            TestOutcomeActual::class.java -> testResultDto() as T
            DistributionPackage::class.java -> distributionPackage() as T
            WorkflowRun::class.java -> workflowExecutionDto() as T
            TransformOccurrenceArg::class.java -> appliedTransformArg() as T
            com.wini.owle.dto.transforms.enums.TransformOccurrence::class.java -> appliedTransformDto() as T
            Transform::class.java -> transformationDto() as T
            TransformArg::class.java -> transformationArg() as T
            else -> throw IllegalArgumentException("Unsupported DTO type: ${dtoClass.simpleName}")
        }
    }
}