package com.wini.owle.dto

import com.wini.owle.dto.environs.*
import com.wini.owle.dto.environs.enums.PyPackageRepoType
import com.wini.owle.dto.runs.*
import com.wini.owle.dto.runs.enums.ComparisonOperator
import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.runs.enums.GenerationMethod
import com.wini.owle.dto.runs.enums.LogLevel
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.dto.runs.step.StepRunLogLine
import com.wini.owle.dto.runs.test.*
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.dto.steps.*
import com.wini.owle.dto.transforms.*
import com.wini.owle.dto.transforms.enums.TransformOccurrence
import com.wini.owle.dto.typing.*
import com.wini.owle.dto.typing.enums.VarianceCardinalityDto
import com.wini.owle.dto.user.*
import com.wini.owle.dto.user.enums.CardBrand
import com.wini.owle.dto.user.enums.SubscriptionStatus
import com.wini.owle.dto.user.tags.Tag
import com.wini.owle.dto.user.tags.TagRelation
import com.wini.owle.dto.workflows.*
import com.wini.owle.dto.workflows.enums.AuthorshipCreditDto
import com.wini.owle.dto.workflows.enums.CommercialUse
import com.wini.owle.dto.workflows.enums.ModificationTermsDto
import com.wini.owle.dto.workflows.enums.StoreMethod


@Suppress("UNCHECKED_CAST")
object DtoTestInstanceFactory {

    fun transformationTargetTypeDto(
        transformationId: Long = 1L,
        typeDeclarationId: Long = 2L
    ): TransformTargetType = TransformTargetType(
        transformationId = transformationId,
        typeDeclarationId = typeDeclarationId
    )

    fun toolPackageDependencyDto(
        toolId: Long = 1L,
        packageId: Long = 2L,
        versionSpecId: Long = 3L
    ): ToolPyPackDep {
        return ToolPyPackDep(
            toolId = toolId,
            packageId = packageId,
            versionSpecId = versionSpecId
        )
    }

    fun packageDto(
        id: Long = 1L,
        name: String = "Package Name",
        disambiguation: String = "Package Disambiguation",
        versionSpec: VersionSpec = versionSpecDto()
    ) : PyPackage {
        return PyPackage(
            id = id,
            name = name,
            disambiguation = disambiguation,
            versionSpec = versionSpec
        )
    }

    fun packageRepoDto(
        id: Long = 1L,
        sourceType: PyPackageRepoType = PyPackageRepoType.Pypi,
        sourceUrl: String = "https://pypi.org"
    ): PyPackageRepo {
        return PyPackageRepo(
            id = id,
            sourceType = sourceType,
            sourceUrl = sourceUrl
        )
    }

    fun versionSpecDto(
        id: Long = 2L,
        versionMin: String? = "1.0",
        versionMax: String? = "2.0",
        versionExact: String? = null
    ): VersionSpec {
        return VersionSpec(
            id = id,
            versionMin = versionMin,
            versionMax = versionMax,
            versionExact = versionExact
        )
    }

    fun stepDto(
        id: Long = 3L,
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
            displayOrder = displayOrder,
            nextStep = nextStep
        )
    }

    fun workflowMetadataDto(
        id: Long = 1L,
        name: String = "John Arbuckle",
        version: String = "1.0.0",
        contributors: List<User> = emptyList(),
        //authors = listOf("Garfield Cat", "Nermal"),
        copyrightInformation:String = "2025",
        contactInformation: String = "lasagnalonely@email.com",
        thirdPartyLicenses: String = "Ok",
        distributionVersion: String = "1.0.0",
        distributionAcceptedAt: String = "",
        distributionUpdatedAt: String = "",
        discretionAdvisedContent: Boolean = true,
        commercialUsage: CommercialUse = CommercialUse.Corporation,
        authorshipCredit: AuthorshipCreditDto = AuthorshipCreditDto.Required,
        allowRedistribution: Boolean = false,
        modification: ModificationTermsDto = ModificationTermsDto.Prohibited,
        excludedSteps: List<Step> = emptyList()
    ): WorkflowMetadata {
        return WorkflowMetadata(
            id = id,
            name = name,
            version = version,
            contributors = contributors,
            copyrightInformation = copyrightInformation,
            contactInformation = contactInformation,
            thirdPartyLicenses = thirdPartyLicenses,
            discretionAdvisedContent = discretionAdvisedContent,
            commercialUsage = commercialUsage,
            creditNotation = authorshipCredit,
            allowRedistribution = allowRedistribution,
            modification = modification,
            distributionVersion = distributionVersion,
            distributionAcceptedAt = distributionAcceptedAt,
            distributionUpdatedAt = distributionUpdatedAt,
            excludedSteps = excludedSteps
        )
    }

    fun workflowDto(
        id: Long = 1L,
        name: String = "Example Workflow",
        description: String? = "A sample workflow description",
        pythonVersion: String = "3.9",
        parentFolder: WorkflowFolder? = null,
        metadata: WorkflowMetadata = workflowMetadataDto(),
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
        id: Long = 1L,
        name: String = "Tool Name",
        description: String? = "Tool Description",
        body: String = "a sample of code",
        returnTypeDeclaration: TypeDeclaration? = typeDeclarationDto(),
        steps: List<Step> = emptyList(),
        packageDependencies: List<ToolPyPackDep> = emptyList()
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
        id: Long = 1L,
        user: User = userDto(),
        name: String = "Test Case Name",
        description: String = "Test Case Description",
        workflow: Workflow = workflowDto(),
        step: Step = stepDto(),
        inputs: List<TestInput> = emptyList(),
        expectedOutcomes: List<TestExpectation> = emptyList(),
        results: List<WorkflowTestResult> = emptyList()
    ) : TestCase {
        return com.wini.owle.dto.runs.test.TestCase(
            id = id,
            user = user,
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
        id: Long = 1L,
        name: String = "Data Point Name",
        description: String? = "Data Point Description",
        presetValue: String? = "Some stuff",
        storeMethod: StoreMethod = StoreMethod.Blob,
        typeDeclaration: TypeDeclaration = typeDeclarationDto(),
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
        id: Long = 1L,
        name: String = "Tool Param",
        typeDeclaration: TypeDeclaration = typeDeclarationDto(),
        tool: Tool = toolDto(),
        hasDefault: Boolean = true
    ) : ToolParam {
        return ToolParam(
            id = id,
            name = name,
            typeDeclaration = typeDeclaration,
            tool = tool,
            hasDefault = hasDefault
        )
    }

    fun typeDeclarationDto(
        id: Long = 1L,
        typeDefinition: TypeDefinition = typeDefinitionDto(),
        alias: String? = "alias"
    ): TypeDeclaration {
        return TypeDeclaration(
            id = id,
            typeDefinition = typeDefinition,
            alias = alias
        )
    }

    fun typeDefinitionDto(
        id: Long = 1L,
        name: String = "Type Definition Name",
        namespace: String = "type.definition.namespace",
        versionSpec: VersionSpec = versionSpecDto()
    ) : TypeDefinition {
        return TypeDefinition(
            id = id,
            name = name,
            namespace = namespace,
            versionSpec = versionSpec
        )
    }

    fun paymentMethodDto(
        id: Long = 1L,
        stripePaymentMethodId: String? = "1AF002B1B22200",
        user: User = userDto(),
        cardBrand: CardBrand? = CardBrand.Visa,
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
        id: Long = 1L,
        name: String = "Tag Name",
        description: String? = "Tag Description",
        user: User = userDto()
    ) : Tag {
        return Tag(
            id = id,
            name = name,
            description = description,
            user = user
        )
    }

    fun tagRelationDto(
        parentTagId: Long = 1L,
        childTagId: Long = 2L
    ) : TagRelation {
        return TagRelation(
            parentTagId = parentTagId,
            childTagId = childTagId
        )
    }

    fun workflowExecutionDto(
        id: Long = 1L,
        workflow: Workflow = workflowDto(),
        user: User = userDto(),
        startedAt: String = "2024-12-21T10:00:00",
        completedAt: String = "2024-12-21T10:00:00",
        status: ExecutionStatus = ExecutionStatus.Failed,
        stepExecutions: List<StepExecution> = emptyList()
    ) : WorkflowExecution {
        return WorkflowExecution(
            id = id,
            workflow = workflow,
            user = user,
            startedAt = startedAt,
            completedAt = completedAt,
            status = status,
            stepExecutions = stepExecutions
        )
    }

    fun benchmarkingDataDto(
        id: Long = 1L,
        workflowExecution: WorkflowExecution = workflowExecutionDto(),
        totalExecutionTime: Double = 123.45,
        totalSteps: Int = 35,
        aiDeterminedSteps: Int = 4,
        preDeterminedSteps: Int = 8
    ) : BenchmarkReport {
        return BenchmarkReport(
            id = id,
            workflowExecution = workflowExecution,
            totalExecutionTime = totalExecutionTime,
            totalSteps = totalSteps,
            aiDeterminedSteps = aiDeterminedSteps,
            predeterminedSteps = preDeterminedSteps
        )
    }

    fun userDto(
        id: Long = 1L,
        email: String = "jim@jim.com",
        password: String = "secretStuff1!",
        resetToken: String? = "3ij3oi2joi342j",
        oauthProvider: String? = "Google",
        oauthId: String? = "IROEJOWejofdjsojfoaj3ioj439u2jiof",
        subscriptionStatus: SubscriptionStatus = SubscriptionStatus.Registered,
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
            subscriptionStatus = subscriptionStatus,
            apiKey = apiKey,
            createdAt = createdAt,
            updatedAt = updatedAt,
            paymentMethods = paymentMethods
        )
    }

    fun executionLogDto(
        id: Long = 1L,
        stepExecution: StepExecution = stepExecutionDto(),
        timeStamp: String = "2024-12-21T12:00:00Z",
        logLevel: LogLevel = LogLevel.Info,
        message: String = "Execution Started"
    ) : StepRunLogLine {
        return StepRunLogLine(
            id = id,
            stepExecution = stepExecution,
            timestamp = timeStamp,
            logLevel = logLevel,
            message = message
        )
    }

    fun stepBenchmarkingDataDto(
        id: Long = 0L,
        stepExecution: StepExecution = stepExecutionDto(),
        executionTime: Double = 123.45
    ) : StepBenchmarkingDataDto {
        return StepBenchmarkingDataDto(
            id = id,
            stepExecution = stepExecution,
            executionTime = executionTime
        )
    }

    fun stepExecutionDto(
        id: Long = 1L,
        workflowExecution: WorkflowExecution = workflowExecutionDto(),
        step: Step = stepDto(),
        startedAt: String = "2024-12-21T10:00:00",
        completedAt: String = "2024-12-21T10:00:00",
        status: ExecutionStatus = ExecutionStatus.Failed
    ) : StepExecution {
        return StepExecution(
            id = id,
            workflowExecution = workflowExecution,
            step = step,
            startedAt = startedAt,
            completedAt = completedAt,
            status = status
        )
    }

    fun errorWatchDto(
        id: Long = 11L,
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
        id: Long = 1L,
        errorWatch: ErrorWatch = errorWatchDto(),
        functionName: String = "exampleFunction",
        position: Int = 1,
    ) : ErrorWatchFn {
        return ErrorWatchFn(
            id = id,
            errorWatch = errorWatch,
            functionName = functionName,
            position = position
        )
    }

    fun inquisitionDto(
        id: Long = 1L,
        step: Step = stepDto(),
        className: String = "Condition",
        dataPoint: DataPoint = dataPointDto(),
        onMetWorkflowStep: Step = stepDto(),
        executionOrder: Int = 1
    ) : Inquisition {
        return Inquisition(
            id = id,
            step = step,
            className = className,
            dataPoint = dataPoint,
            onMetWorkflowStep = onMetWorkflowStep,
            executionOrder = executionOrder
        )
    }

    fun preReqInjectionDto(
        id: Long = 1L,
        junction: WorkflowJunction = workflowJunctionDto(),
        sourceDataPoint: DataPoint = dataPointDto(),
        injectedDataPoint: DataPoint = dataPointDto(),
        name: String = "Name",
        description: String? = "Description"
    ) : InitialDataPointInjector {
        return InitialDataPointInjector(
            id = id,
            junction = junction,
            sourceDataPoint = sourceDataPoint,
            injectedDataPoint = injectedDataPoint,
            name = name,
            description = description
        )
    }

    fun workflowJunctionDto(
        id: Long = 1L,
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
        id: Long = 1L,
        step: Step = stepDto(),
        toolParam: ToolParam = toolParamDto(),
        dataPoints: List<DataPoint> = emptyList()
    ) : Provision {
        return Provision(
            id = id,
            step = step,
            toolParam = toolParam,
            dataPoints = dataPoints
        )
    }

    fun transformationDto(
        id: Long = 1L,
        name: String = "Transformation Name",
        description: String = "Transformation Description",
        examples: String? = "Example",
        returnsType: TypeDeclaration = typeDeclarationDto()
    ) : Transform {
        return Transform(
            id = id,
            name = name,
            description = description,
            examples = examples,
            returnsType = returnsType
        )
    }

    fun revisionDto(
        id: Long = 1L,
        dataPoint: DataPoint = dataPointDto(),
        step: Step = stepDto()
    ) : DataPointSave {
        return DataPointSave(
            id = id,
            dataPoint = dataPoint,
            step = step
        )
    }

    fun typeParameterDto(
        id: Long = 1L,
        typeDeclaration: TypeDeclaration = typeDeclarationDto(),
        position: Int = 1,
        parameterTypeDeclaration: TypeDeclaration = typeDeclarationDto()
    ) : TypeParameter {
        return TypeParameter(
            id = id,
            typeDeclaration = typeDeclaration,
            position = position,
            parameterTypeDeclaration = parameterTypeDeclaration
        )
    }

    fun typeSchemaRefDto(
        id: Long = 1L,
        parentRef: TypeSchemaRef? = null,
        name: String = "exampleSchema",
        typeDeclaration: TypeDeclaration = typeDeclarationDto(),
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
        id: Long = 1L,
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

    fun typeUnionMemberDto(
        unionTypeDeclarationId: Long = 21L,
        memberTypeDeclarationId: Long = 22L
    ): TypeUnionMember {
        return TypeUnionMember(
            unionTypeDeclarationId = unionTypeDeclarationId,
            memberTypeDeclarationId = memberTypeDeclarationId
        )
    }


    fun typeVarianceDto(
        id: Long = 23L,
        typeVariableDeclaration: TypeDeclaration = typeDeclarationDto(),
        varianceCardinality: VarianceCardinalityDto = VarianceCardinalityDto.Covariant
    ): TypeVariance {
        return TypeVariance(
            id = id,
            typeVariableDeclaration = typeVariableDeclaration,
            varianceCardinality = varianceCardinality
        )
    }

    fun provisionDataPointDto(
        provision: Provision = provisionDto(),
        dataPoint: DataPoint = dataPointDto()
    ): ProvisionDataPoint {
        return ProvisionDataPoint(
            provisionId = provision.id,
            dataPointId = dataPoint.id
        )
    }

    fun workflowFolderDto(
        id: Long = 32L,
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
            img = null,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun testInputDto(
        id: Long = 6L,
        dataPointName: String = "input1",
        value: String = "value1",
        generationMethod: GenerationMethod = GenerationMethod.Manual,
        testCase: TestCase = testCaseDto()
    ): TestInput {
        return TestInput(
            id = id,
            dataPointName = dataPointName,
            value = value,
            generationMethod = generationMethod,
            testCase = testCase
        )
    }
    fun testExpectedOutcomeDto(
        id: Long = 7L,
        dataPointName: String = "output1",
        expectedValue: String = "expectedValue1",
        comparisonOperator: ComparisonOperator = ComparisonOperator.Equals,
        testCase: TestCase = testCaseDto()
    ): TestExpectation {
        return TestExpectation(
            id = id,
            dataPointName = dataPointName,
            expectedValue = expectedValue,
            comparisonOperator = comparisonOperator,
            testCase = testCase
        )
    }

    fun testResultDto(
        id: Long = 1L,
        testCase: TestCase = testCaseDto(),
        workflowExecution: WorkflowExecution = workflowExecutionDto(),
        passed: Boolean = true,
        executedAt: String = "2024-12-21T00:00:00Z"
    ): WorkflowTestResult {
        return WorkflowTestResult(
            id = id,
            testCase = testCase,
            workflowExecution = workflowExecution,
            passed = passed,
            executedAt = executedAt
        )
    }

    fun typeBoundDto(
        id: Long = 1L,
        boundTypeDeclaration: TypeDeclaration = typeDeclarationDto(),
        typeVariableDeclaration: TypeDeclaration = typeDeclarationDto()
    ) : TypeBound {
        return TypeBound(
            id = id,
            boundTypeDeclaration = boundTypeDeclaration,
            typeVariableDeclaration = typeVariableDeclaration
        )
    }

    fun appliedTransformArgDto(
        id: Long = 0L,
        appliedTransform: AppliedTransform = appliedTransformDto(),
        transformationArg: TransformArg = transformationArgDto(),
        value: String = ""
    ) : AppliedTransformArg {
        return AppliedTransformArg(
            id = id,
            appliedTransform = appliedTransform,
            transformationArg = transformationArg,
            value = value
        )
    }

    fun transformationArgDto(
        id: Long = 0L,
        transformation: Transform = transformationDto(),
        typeDeclaration: TypeDeclaration = typeDeclarationDto(),
        name: String = "Transformation Arg Name",
        description: String? = "Transformation Arg Description"
    ): TransformArg {
        return TransformArg(
            id = id,
            transformation = transformation,
            typeDeclaration = typeDeclaration,
            name = name,
            description = description
        )
    }

    fun appliedTransformDto(
        id: Long = 0L,
        transformation: Transform = transformationDto(),
        occurrence: TransformOccurrence = TransformOccurrence.PreReqInjection,
        resultType: TypeDeclaration = typeDeclarationDto(),
        dataPoint: DataPoint? = dataPointDto(),
    ) : AppliedTransform {
        return AppliedTransform(
            id = id,
            transformation = transformation,
            occurrence = occurrence,
            resultType = resultType,
            dataPoint = dataPoint
        )
    }


    fun <T : BaseDto> createDtoInstance(dtoClass: Class<T>): T {
        return when (dtoClass) {
            PyPackageRepo::class.java -> packageRepoDto() as T
            VersionSpec::class.java -> versionSpecDto() as T
            Step::class.java -> stepDto() as T
            Workflow::class.java -> workflowDto() as T
            Tool::class.java -> toolDto() as T
            TestCase::class.java -> testCaseDto() as T
            DataPoint::class.java -> dataPointDto() as T
            ToolParam::class.java -> toolParamDto() as T
            TypeDeclaration::class.java -> typeDeclarationDto() as T
            TypeDefinition::class.java -> typeDefinitionDto() as T
            PaymentMethod::class.java -> paymentMethodDto() as T
            Tag::class.java -> tagDto() as T
            TagRelation::class.java -> tagRelationDto() as T
            BenchmarkReport::class.java -> benchmarkingDataDto() as T
            User::class.java -> userDto() as T
            StepRunLogLine::class.java -> executionLogDto() as T
            ErrorWatch::class.java -> errorWatchDto() as T
            ErrorWatchFn::class.java -> errorWatchFnDto() as T
            Inquisition::class.java -> inquisitionDto() as T
            InitialDataPointInjector::class.java -> preReqInjectionDto() as T
            Provision::class.java -> provisionDto() as T
            DataPointSave::class.java -> revisionDto() as T
            TypeParameter::class.java -> typeParameterDto() as T
            TypeSchemaRef::class.java -> typeSchemaRefDto() as T
            TypeUnionMember::class.java -> typeUnionMemberDto() as T
            TypeVariance::class.java -> typeVarianceDto() as T
            Phase::class.java -> phaseDto() as T
            ProvisionDataPoint::class.java -> provisionDataPointDto() as T
            WorkflowFolder::class.java -> workflowFolderDto() as T
            TestInput::class.java -> testInputDto() as T
            TestExpectation::class.java -> testExpectedOutcomeDto() as T
            WorkflowTestResult::class.java -> testResultDto() as T
            PyPackage::class.java -> packageDto() as T
            WorkflowExecution::class.java -> workflowExecutionDto() as T
            AppliedTransformArg::class.java -> appliedTransformArgDto() as T
            AppliedTransform::class.java -> appliedTransformDto() as T
            Transform::class.java -> transformationDto() as T
            TransformArg::class.java -> transformationArgDto() as T
            else -> throw IllegalArgumentException("Unsupported DTO type: ${dtoClass.simpleName}")
        }
    }
}