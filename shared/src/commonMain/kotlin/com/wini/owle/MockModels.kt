package com.wini.owle

import com.wini.owle.dto.DecimalValue
import com.wini.owle.dto.ResourceUsageDto
import com.wini.owle.dto.enums.*
import kotlinx.datetime.LocalDateTime
import com.wini.owle.models.*
import com.wini.owle.models.metadata.UserDesignedMetadata
import kotlin.reflect.KClass


@Suppress("UNCHECKED_CAST")
object MockModels {

    fun pakkage(
        id: Long = 0L,
        name: String = "Package Name",
        disambiguation: String = "Package Disambiguation",
        versionSpec: VersionSpec = versionSpec()
    ) : Package {
        return Package(
            id = id,
            name = name,
            disambiguation = disambiguation,
            versionSpec = versionSpec
        )
    }

    fun packageRepo(
        id: Long = 1L,
        sourceType: PackageRepoType = PackageRepoType.Pypi,
        sourceUrl: String = "https://pypi.org"
    ): PackageRepo {
        return PackageRepo(
            id = id,
            sourceType = sourceType,
            sourceUrl = sourceUrl
        )
    }

    fun versionSpec(
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

    fun step(
        id: Long = 3L,
        name: String = "Example Step",
        description: String? = "A step in a workflow",
        workflow: Workflow = workflow(),
        isWorkflowHead: Boolean = true,
        tool: Tool = tool(),
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

    fun workflow(
        id: Long = 1L,
        name: String = "Example Workflow",
        description: String? = "A sample workflow description",
        pythonVersion: String = "3.9",
        parentFolder: WorkflowFolder? = null,
        metadata: UserDesignedMetadata = UserDesignedMetadata.mock,
        createdAt: String = "2024-12-20T15:00:00",
        updatedAt: String = "2024-12-21T15:00:00"
    ): Workflow {
        return Workflow(
            id = id,
            name = name,
            description = description,
            steps = emptyList(),
            phases = emptyList(),
            dataPoints = emptyList(),
            pythonVersion = pythonVersion,
            parentFolder = parentFolder,
            metadata = metadata,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun tool(
        id: Long = 1L,
        name: String = "Tool Name",
        description: String? = "Tool Description",
        body: String = "a sample of code",
        returnTypeDeclaration: TypeDeclaration? = typeDeclaration(),
        steps: List<Step> = emptyList(),
        packageDependencies: List<ToolPackageDependency> = emptyList()
    ): Tool {
        return Tool(
            id = id,
            name = name,
            description = description,
            body = body,
            returnTypeDeclaration = returnTypeDeclaration
        )
    }

    fun testCase(
        id: Long = 1L,
        user: User = user(),
        name: String = "Test Case Name",
        description: String = "Test Case Description",
        workflow: Workflow = workflow(),
        step: Step = step(),
        inputs: List<TestInput> = emptyList(),
        expectedOutcomes: List<TestExpectedOutcome> = emptyList(),
        results: List<TestResult> = emptyList()
    ) : TestCase {
        return TestCase(
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

    fun dataPoint(
        id: Long = 1L,
        workflowId: Long = 2L,
        name: String = "Data Point Name",
        description: String? = "Data Point Description",
        presetValue: String? = "Some stuff",
        storeMethod: StoreMethod = StoreMethod.Blob,
        typeDeclaration: TypeDeclaration = typeDeclaration()
    ): DataPoint {
        return DataPoint(
            id = id,
            name = name,
            description = description,
            presetValue = presetValue,
            storeMethod = storeMethod,
            typeDeclaration = typeDeclaration,
            workflowId = workflowId,
            createdAt = "",
            updatedAt = ""
        )
    }

    fun toolParam(
        id: Long = 1L,
        name: String = "Tool Param",
        typeDeclaration: TypeDeclaration = typeDeclaration(),
        tool: Tool = tool(),
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

    fun typeDeclaration(
        id: Long = 1L,
        typeDefinition: TypeDefinition = typeDefinition(),
        alias: String? = "alias"
    ): TypeDeclaration {
        return TypeDeclaration(
            id = id,
            typeDefinition = typeDefinition,
            alias = alias,
            pkg = Package(
                id = 0L,
                name = "",
                disambiguation = null,
                versionSpec = VersionSpec(
                    0L,
                    null,
                    null,
                    null
                )
            )
        )
    }

    fun typeDefinition(
        id: Long = 1L,
        name: String = "Type Definition Name",
        namespace: String = "type.definition.namespace",
        versionSpec: VersionSpec = versionSpec()
    ) : TypeDefinition {
        return TypeDefinition(
            id = id,
            name = name,
            namespace = namespace,
            versionSpec = versionSpec
        )
    }

    fun paymentMethod(
        id: Long = 1L,
        stripePaymentMethodId: String? = "1AF002B1B22200",
        user: User = user(),
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

    fun tag(
        id: Long = 1L,
        name: String = "Tag Name",
        description: String? = "Tag Description",
        user: User = user()
    ) : Tag {
        return Tag(
            id = id,
            name = name,
            description = description,
            user = user
        )
    }

    fun tagRelation(
        parentTagId: Long = 1L,
        childTagId: Long = 2L
    ) : TagRelation {
        return TagRelation(
            parentTagId = parentTagId,
            childTagId = childTagId
        )
    }

    fun workflowExecution(
        id: Long = 1L,
        workflow: Workflow = workflow(),
        user: User = user(),
        startedAt: LocalDateTime = LocalDateTime.parse("2024-12-21T10:00:00"),
        completedAt: LocalDateTime = LocalDateTime.parse("2024-12-21T10:00:00"),
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

    fun benchmarkingData(
        id: Long = 1L,
        workflowExecution: WorkflowExecution = workflowExecution(),
        totalExecutionTime: DecimalValue = DecimalValue("123.45"),//123.45
        totalSteps: Int = 35,
        aiDeterminedSteps: Int = 4,
        preDeterminedSteps: Int = 8
    ) : BenchmarkingData {
        return BenchmarkingData(
            id = id,
            workflowExecution = workflowExecution,
            totalExecutionTime = totalExecutionTime,
            totalSteps = totalSteps,
            aiDeterminedSteps = aiDeterminedSteps,
            predeterminedSteps = preDeterminedSteps
        )
    }

    fun user(
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

    fun executionLog(
        id: Long = 1L,
        stepExecution: StepExecution = stepExecution(),
        timeStamp: String = "2024-12-21T12:00:00Z",
        logLevel: LogLevel = LogLevel.Info,
        message: String = "Execution Started"
    ) : ExecutionLog {
        return ExecutionLog(
            id = id,
            stepExecution = stepExecution,
            timestamp = timeStamp,
            logLevel = logLevel,
            message = message
        )
    }

    fun stepBenchmarkingData(
        id: Long = 0L,
        stepExecution: StepExecution = stepExecution(),
        executionTime: DecimalValue = DecimalValue("123.45"),
        resourceUsage: ResourceUsageDto = ResourceUsageDto()
    ) : StepBenchmarkingData {
        return StepBenchmarkingData(
            id = id,
            stepExecution = stepExecution,
            executionTime = executionTime,
            resourceUsage = resourceUsage
        )
    }

    fun stepExecution(
        id: Long = 1L,
        workflowExecution: WorkflowExecution = workflowExecution(),
        step: Step = step(),
        startedAt: LocalDateTime = LocalDateTime.parse("2024-12-21T10:00:00"),
        completedAt: LocalDateTime = LocalDateTime.parse("2024-12-21T10:00:00"),
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

    fun errorWatch(
        id: Long = 11L,
        step: Step = step(),
        errorType: String = "RuntimeException",
        triggerStep: Step = step()
    ) : ErrorWatch {
        return ErrorWatch(
            id = id,
            step = step,
            errorType = errorType,
            triggerStep = triggerStep
        )
    }

    fun errorWatchFn(
        id: Long = 1L,
        errorWatch: ErrorWatch = errorWatch(),
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

    fun inquisition(
        id: Long = 1L,
        step: Step = step(),
        className: String = "Condition",
        dataPoint: DataPoint = dataPoint(),
        onMetWorkflowStep: Step = step(),
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

    fun preReqInjection(
        id: Long = 1L,
        junction: WorkflowJunction = workflowJunction(),
        sourceDataPoint: DataPoint = dataPoint(),
        injectedDataPoint: DataPoint = dataPoint(),
        name: String = "Name",
        description: String? = "Description"
    ) : PreReqInjection {
        return PreReqInjection(
            id = id,
            junction = junction,
            sourceDataPoint = sourceDataPoint,
            injectedDataPoint = injectedDataPoint,
            name = name,
            description = description
        )
    }

    fun workflowJunction(
        id: Long = 1L,
        sourceStep: Step = step(),
        targetWorkflow: Workflow = workflow(),
        triggerInquisition: Inquisition = inquisition()
    ) : WorkflowJunction {
        return WorkflowJunction(
            id = id,
            sourceStep = sourceStep,
            targetWorkflow = targetWorkflow,
            triggerInquisition = triggerInquisition
        )
    }

    fun provision(
        id: Long = 1L,
        step: Step = step(),
        toolParam: ToolParam = toolParam(),
        dataPoints: List<ProvisionDataPoint> = emptyList()
    ) : Provision {
        return Provision(
            id = id,
            step = step,
            toolParam = toolParam,
            dataPoints = dataPoints
        )
    }

    fun transformation(
        id: Long = 1L,
        name: String = "Transformation Name",
        description: String = "Transformation Description",
        examples: String? = "Example",
        returnsType: TypeDeclaration = typeDeclaration()
    ) : Transformation {
        return Transformation(
            id = id,
            name = name,
            description = description,
            examples = examples,
            returnsType = returnsType
        )
    }

    fun revision(
        id: Long = 1L,
        dataPoint: DataPoint = dataPoint(),
        step: Step = step()
    ) : Revision {
        return Revision(
            id = id,
            dataPoint = dataPoint,
            step = step
        )
    }

    fun typeParameter(
        id: Long = 1L,
        typeDeclaration: TypeDeclaration = typeDeclaration(),
        position: Int = 1,
        parameterTypeDeclaration: TypeDeclaration = typeDeclaration()
    ) : TypeParameter {
        return TypeParameter(
            id = id,
            typeDeclaration = typeDeclaration,
            position = position,
            parameterTypeDeclaration = parameterTypeDeclaration
        )
    }

    fun typeSchemaRef(
        id: Long = 1L,
        parentRef: TypeSchemaRef? = null,
        name: String = "exampleSchema",
        typeDeclaration: TypeDeclaration = typeDeclaration(),
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

    fun phase(
        id: Long = 1L,
        workflow: Workflow = workflow(),
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

    fun typeUnionMember(
        unionTypeDeclarationId: Long = 21L,
        memberTypeDeclarationId: Long = 22L
    ): TypeUnionMember {
        return TypeUnionMember(
            unionTypeDeclarationId = unionTypeDeclarationId,
            memberTypeDeclarationId = memberTypeDeclarationId
        )
    }


    fun typeVariance(
        id: Long = 23L,
        typeVariableDeclaration: TypeDeclaration = typeDeclaration(),
        varianceCardinality: VarianceCardinality = VarianceCardinality.Covariant
    ): TypeVariance {
        return TypeVariance(
            id = id,
            typeVariableDeclaration = typeVariableDeclaration,
            varianceCardinality = varianceCardinality
        )
    }

    fun provisionDataPoint(
        provision: Provision = provision(),
        dataPoint: DataPoint = dataPoint()
    ): ProvisionDataPoint {
        return ProvisionDataPoint(
            provisionId = provision.id,
            dataPointId = dataPoint.id
        )
    }

    fun workflowFolder(
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

    fun testInput(
        id: Long = 6L,
        dataPointName: String = "input1",
        value: String = "value1",
        generationMethod: GenerationMethod = GenerationMethod.Manual,
        testCase: TestCase = testCase()
    ): TestInput {
        return TestInput(
            id = id,
            dataPointName = dataPointName,
            value = value,
            generationMethod = generationMethod,
            testCase = testCase
        )
    }
    fun testExpectedOutcome(
        id: Long = 7L,
        dataPointName: String = "output1",
        expectedValue: String = "expectedValue1",
        comparisonOperator: ComparisonOperator = ComparisonOperator.Equals,
        testCase: TestCase = testCase()
    ): TestExpectedOutcome {
        return TestExpectedOutcome(
            id = id,
            dataPointName = dataPointName,
            expectedValue = expectedValue,
            comparisonOperator = comparisonOperator,
            testCase = testCase
        )
    }

    fun testResult(
        id: Long = 1L,
        testCase: TestCase = testCase(),
        workflowExecution: WorkflowExecution = workflowExecution(),
        passed: Boolean = true,
        executedAt: String = "2024-12-21T00:00:00Z"
    ): TestResult {
        return TestResult(
            id = id,
            testCase = testCase,
            workflowExecution = workflowExecution,
            passed = passed,
            executedAt = executedAt
        )
    }

    fun typeBound(
        id: Long = 1L,
        boundTypeDeclaration: TypeDeclaration = typeDeclaration(),
        typeVariableDeclaration: TypeDeclaration = typeDeclaration()
    ) : TypeBounds {
        return TypeBounds(
            id = id,
            boundTypeDeclaration = boundTypeDeclaration,
            typeVariableDeclaration = typeVariableDeclaration
        )
    }

    fun appliedTransformArg(
        id: Long = 0L,
        appliedTransform: AppliedTransform = appliedTransform(),
        transformationArg: TransformationArg = transformationArg(),
        value: String = ""
    ) : AppliedTransformArg {
        return AppliedTransformArg(
            id = id,
            appliedTransform = appliedTransform,
            transformationArg = transformationArg,
            value = value
        )
    }

    fun transformationArg(
        id: Long = 0L,
        transformation: Transformation = transformation(),
        typeDeclaration: TypeDeclaration = typeDeclaration(),
        name: String = "Transformation Arg Name",
        description: String? = "Transformation Arg Description"
    ): TransformationArg {
        return TransformationArg(
            id = id,
            transformation = transformation,
            typeDeclaration = typeDeclaration,
            name = name,
            description = description
        )
    }

    fun appliedTransform(
        id: Long = 0L,
        transformation: Transformation = transformation(),
        occurrence: TransformOccurrence = TransformOccurrence.PreReqInjection,
        resultType: TypeDeclaration = typeDeclaration(),
        dataPoint: DataPoint? = dataPoint(),
    ) : AppliedTransform {
        return AppliedTransform(
            id = id,
            transformation = transformation,
            occurrence = occurrence,
            resultType = resultType,
            dataPoint = dataPoint
        )
    }


    fun createDtoInstance(dtoClass: KClass<*>): Any {
        return when (dtoClass) {
            PackageRepo::class -> packageRepo()
            VersionSpec::class -> versionSpec()
            Step::class -> step()
            Workflow::class -> workflow()
            Tool::class -> tool()
            TestCase::class -> testCase()
            DataPoint::class -> dataPoint()
            ToolParam::class -> toolParam()
            TypeDeclaration::class -> typeDeclaration()
            TypeDefinition::class -> typeDefinition()
            PaymentMethod::class -> paymentMethod()
            Tag::class -> tag()
            TagRelation::class -> tagRelation()
            BenchmarkingData::class -> benchmarkingData()
            User::class -> user()
            ExecutionLog::class -> executionLog()
            ErrorWatch::class -> errorWatch()
            ErrorWatchFn::class -> errorWatchFn()
            Inquisition::class -> inquisition()
            PreReqInjection::class -> preReqInjection()
            Provision::class -> provision()
            Revision::class -> revision()
            TypeParameter::class -> typeParameter()
            TypeSchemaRef::class -> typeSchemaRef()
            TypeUnionMember::class -> typeUnionMember()
            TypeVariance::class -> typeVariance()
            Phase::class -> phase()
            ProvisionDataPoint::class -> provisionDataPoint()
            WorkflowFolder::class -> workflowFolder()
            TestInput::class -> testInput()
            TestExpectedOutcome::class -> testExpectedOutcome()
            TestResult::class -> testResult()
            Package::class -> pakkage()
            else -> throw IllegalArgumentException("Unsupported  type: ${dtoClass.simpleName}")
        }
    }

    fun workflowSummary(
        id: Long = 0L,
        name: String = "Generate 3-D Image",
        description: String = "Create a 3D object model in Blender using an image and a depth map",
        createdAt: String = "",
        updatedAt: String = "",
        stepsCount: Int = 123,
        phasesCount: Int = 20,
        dataPointsCount: Int = 45,
        junctionsCount: Int = 0
    ): WorkflowSummary {
        return WorkflowSummary(
            id = id,
            name = name,
            description = description,
            createdAt = createdAt,
            updatedAt = updatedAt,
            stepsCount = stepsCount,
            phasesCount = phasesCount,
            dataPointsCount = dataPointsCount,
            junctionsCount = junctionsCount
        )
    }

    fun workflowSummaries(): List<WorkflowSummary> {
        return listOf(
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
            workflowSummary(),
        )
    }

    fun workflowFolderHierarchy(): WorkflowFolder {
        val root = workflowFolder()
        val root1 = workflowFolder(name = "Root Level 1 of 4")
        val root2 = workflowFolder(name = "Root Level 2 of 4")
        val root3 = workflowFolder(name = "Root Level 3 of 4")
        val root4 = workflowFolder(name = "Root Level 4 of 4")
        val under11 = workflowFolder(name = "Under 1 - 1 of 3")
        val under111 = workflowFolder(name = "Under 1:1")
        val under12 = workflowFolder(name = "Under 1 - 2 of 3")
        val under13 = workflowFolder(name = "Under 1 - 3 of 3")
        val under131 = workflowFolder(name = "Under 1:3")
        val under21 = workflowFolder(name = "Under 2 - 1 of 2")
        val under211 = workflowFolder(name = "Under 2:1 - 1 of 2")
        val under212 = workflowFolder(name = "Under 2:1 - 2 of 2")
        val under22 = workflowFolder(name = "Under 2 - 2 of 2")
        val under221 = workflowFolder(name = "Under 2:2")
        val under31 = workflowFolder(name = "Under 3 - 1 of 4")
        val under32 = workflowFolder(name = "Under 3 - 2 of 4")
        val under321 = workflowFolder(name = "Under 3:2")
        val under33 = workflowFolder(name = "Under 3 - 3 of 4")
        val under34 = workflowFolder(name = "Under 3 - 4 of 4")
        val under341 = workflowFolder(name = "Under 3:4")
        val under41 = workflowFolder(name = "Under 4 - 1 of 3")
        val under411 = workflowFolder(name = "Under 4:1")
        val under42 = workflowFolder(name = "Under 4 - 2 of 3")
        val under43 = workflowFolder(name = "Under 4 - 3 of 3")
        val under431 = workflowFolder(name = "Under 4:3")
        return root.copy(
            subfolders = listOf(
                root1.copy(subfolders = listOf(
                    under11.copy(subfolders = listOf(under111)),
                    under12,
                    under13.copy(subfolders = listOf(under131))
                )),
                root2.copy(subfolders = listOf(
                    under21.copy(subfolders = listOf(under211, under212)),
                    under22.copy(subfolders = listOf(under221))
                )),
                root3.copy(subfolders = listOf(
                    under31,
                    under32.copy(subfolders = listOf(under321)),
                    under33,
                    under34.copy(subfolders = listOf(under341))
                )),
                root4.copy(subfolders = listOf(
                    under41.copy(subfolders = listOf(under411)),
                    under42,
                    under43.copy(subfolders = listOf(under431))
                ))
            )
        )
    }
}