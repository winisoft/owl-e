package com.wini.owle.dto

import com.wini.owle.dto.enums.*
import com.wini.owle.mapper.toDecimalValue
import kotlinx.datetime.LocalDateTime
import java.math.BigDecimal


@Suppress("UNCHECKED_CAST")
object DtoTestInstanceFactory {

    fun packageDto(
        id: Long = 0L,
        name: String = "Package Name",
        disambiguation: String = "Package Disambiguation",
        versionSpec: VersionSpecDto = versionSpecDto()
    ) : PackageDto {
        return PackageDto(
            id = id,
            name = name,
            disambiguation = disambiguation,
            versionSpec = versionSpec
        )
    }

    fun packageRepoDto(
        id: Long = 1L,
        sourceType: PackageRepoType = PackageRepoType.Pypi,
        sourceUrl: String = "https://pypi.org"
    ): PackageRepoDto {
        return PackageRepoDto(
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
    ): VersionSpecDto {
        return VersionSpecDto(
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
        workflow: WorkflowDto = workflowDto(),
        isWorkflowHead: Boolean = true,
        tool: ToolDto = toolDto(),
        parentPhase: PhaseDto? = null,
        displayOrder: Int = 0,
        nextStep: StepDto? = null
    ): StepDto {
        return StepDto(
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

    fun workflowDto(
        id: Long = 1L,
        name: String = "Example Workflow",
        description: String? = "A sample workflow description",
        pythonVersion: String = "3.9",
        parentFolder: WorkflowFolderDto? = null,
        metadata: UserDesignedMetadataDto = UserDesignedMetadataDto.mock,
        createdAt: String = "2024-12-20T15:00:00",
        updatedAt: String = "2024-12-21T15:00:00"
    ): WorkflowDto {
        return WorkflowDto(
            id = id,
            name = name,
            description = description,
            pythonVersion = pythonVersion,
            parentFolder = parentFolder,
            metadata = metadata,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun toolDto(
        id: Long = 1L,
        name: String = "Tool Name",
        description: String? = "Tool Description",
        body: String = "a sample of code",
        returnTypeDeclaration: TypeDeclarationDto? = typeDeclarationDto(),
        steps: List<StepDto> = emptyList(),
        packageDependencies: List<ToolPackageDependencyDto> = emptyList()
    ): ToolDto {
        return ToolDto(
            id = id,
            name = name,
            description = description,
            body = body,
            returnTypeDeclaration = returnTypeDeclaration
        )
    }

    fun testCaseDto(
        id: Long = 1L,
        user: UserDto = userDto(),
        name: String = "Test Case Name",
        description: String = "Test Case Description",
        workflow: WorkflowDto = workflowDto(),
        step: StepDto = stepDto(),
        inputs: List<TestInputDto> = emptyList(),
        expectedOutcomes: List<TestExpectedOutcomeDto> = emptyList(),
        results: List<TestResultDto> = emptyList()
    ) : TestCaseDto {
        return TestCaseDto(
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
        typeDeclaration: TypeDeclarationDto = typeDeclarationDto(),
        workflow: WorkflowDto = workflowDto()
    ): DataPointDto {
        return DataPointDto(
            id = id,
            name = name,
            description = description,
            presetValue = presetValue,
            storeMethod = storeMethod,
            typeDeclaration = typeDeclaration,
            workflow = workflow,
            createdAt = TODO(),
            updatedAt = TODO()
        )
    }

    fun toolParamDto(
        id: Long = 1L,
        name: String = "Tool Param",
        typeDeclaration: TypeDeclarationDto = typeDeclarationDto(),
        tool: ToolDto = toolDto(),
        hasDefault: Boolean = true
    ) : ToolParamDto {
        return ToolParamDto(
            id = id,
            name = name,
            typeDeclaration = typeDeclaration,
            tool = tool,
            hasDefault = hasDefault
        )
    }

    fun typeDeclarationDto(
        id: Long = 1L,
        typeDefinition: TypeDefinitionDto = typeDefinitionDto(),
        alias: String? = "alias"
    ): TypeDeclarationDto {
        return TypeDeclarationDto(
            id = id,
            typeDefinition = typeDefinition,
            alias = alias
        )
    }

    fun typeDefinitionDto(
        id: Long = 1L,
        name: String = "Type Definition Name",
        namespace: String = "type.definition.namespace",
        versionSpec: VersionSpecDto = versionSpecDto()
    ) : TypeDefinitionDto {
        return TypeDefinitionDto(
            id = id,
            name = name,
            namespace = namespace,
            versionSpec = versionSpec
        )
    }

    fun paymentMethodDto(
        id: Long = 1L,
        stripePaymentMethodId: String? = "1AF002B1B22200",
        user: UserDto = userDto(),
        cardBrand: CardBrand? = CardBrand.Visa,
        lastFour: String? = "4103",
        isDefault: Boolean = true,
        createdAt: String = "2024-12-21T15:00:00",
        updatedAt: String = "2024-12-22T15:00:00"
    ) : PaymentMethodDto {
        return PaymentMethodDto(
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
        user: UserDto = userDto()
    ) : TagDto {
        return TagDto(
            id = id,
            name = name,
            description = description,
            user = user
        )
    }

    fun tagRelationDto(
        parentTagId: Long = 1L,
        childTagId: Long = 2L
    ) : TagRelationDto {
        return TagRelationDto(
            parentTagId = parentTagId,
            childTagId = childTagId
        )
    }

    fun workflowExecutionDto(
        id: Long = 1L,
        workflow: WorkflowDto = workflowDto(),
        user: UserDto = userDto(),
        startedAt: LocalDateTime = LocalDateTime.parse("2024-12-21T10:00:00"),
        completedAt: LocalDateTime = LocalDateTime.parse("2024-12-21T10:00:00"),
        status: ExecutionStatus = ExecutionStatus.Failed,
        stepExecutions: List<StepExecutionDto> = emptyList()
    ) : WorkflowExecutionDto {
        return WorkflowExecutionDto(
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
        workflowExecution: WorkflowExecutionDto = workflowExecutionDto(),
        totalExecutionTime: DecimalValue = BigDecimal.valueOf(123.45).toDecimalValue(),
        totalSteps: Int = 35,
        aiDeterminedSteps: Int = 4,
        preDeterminedSteps: Int = 8
    ) : BenchmarkingDataDto {
        return BenchmarkingDataDto(
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
        paymentMethods: List<PaymentMethodDto> = emptyList()
    ) : UserDto {
        return UserDto(
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
        stepExecution: StepExecutionDto = stepExecutionDto(),
        timeStamp: String = "2024-12-21T12:00:00Z",
        logLevel: LogLevel = LogLevel.Info,
        message: String = "Execution Started"
    ) : ExecutionLogDto {
        return ExecutionLogDto(
            id = id,
            stepExecution = stepExecution,
            timestamp = timeStamp,
            logLevel = logLevel,
            message = message
        )
    }

    fun stepBenchmarkingDataDto(
        id: Long = 0L,
        stepExecution: StepExecutionDto = stepExecutionDto(),
        executionTime: DecimalValue = BigDecimal.valueOf(123.45).toDecimalValue(),
        resourceUsage: ResourceUsageDto = ResourceUsageDto()
    ) : StepBenchmarkingDataDto {
        return StepBenchmarkingDataDto(
            id = id,
            stepExecution = stepExecution,
            executionTime = executionTime,
            resourceUsage = resourceUsage
        )
    }

    fun stepExecutionDto(
        id: Long = 1L,
        workflowExecution: WorkflowExecutionDto = workflowExecutionDto(),
        step: StepDto = stepDto(),
        startedAt: LocalDateTime = LocalDateTime.parse("2024-12-21T10:00:00"),
        completedAt: LocalDateTime = LocalDateTime.parse("2024-12-21T10:00:00"),
        status: ExecutionStatus = ExecutionStatus.Failed
    ) : StepExecutionDto {
        return StepExecutionDto(
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
        step: StepDto = stepDto(),
        errorType: String = "RuntimeException",
        triggerStep: StepDto = stepDto()
    ) : ErrorWatchDto {
        return ErrorWatchDto(
            id = id,
            step = step,
            errorType = errorType,
            triggerStep = triggerStep
        )
    }

    fun errorWatchFnDto(
        id: Long = 1L,
        errorWatch: ErrorWatchDto = errorWatchDto(),
        functionName: String = "exampleFunction",
        position: Int = 1,
    ) : ErrorWatchFnDto {
        return ErrorWatchFnDto(
            id = id,
            errorWatch = errorWatch,
            functionName = functionName,
            position = position
        )
    }

    fun inquisitionDto(
        id: Long = 1L,
        step: StepDto = stepDto(),
        className: String = "Condition",
        dataPoint: DataPointDto = dataPointDto(),
        onMetWorkflowStep: StepDto = stepDto(),
        executionOrder: Int = 1
    ) : InquisitionDto {
        return InquisitionDto(
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
        junction: WorkflowJunctionDto = workflowJunctionDto(),
        sourceDataPoint: DataPointDto = dataPointDto(),
        injectedDataPoint: DataPointDto = dataPointDto(),
        name: String = "Name",
        description: String? = "Description"
    ) : PreReqInjectionDto {
        return PreReqInjectionDto(
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
        sourceStep: StepDto = stepDto(),
        targetWorkflow: WorkflowDto = workflowDto(),
        triggerInquisition: InquisitionDto = inquisitionDto()
    ) : WorkflowJunctionDto {
        return WorkflowJunctionDto(
            id = id,
            sourceStep = sourceStep,
            targetWorkflow = targetWorkflow,
            triggerInquisition = triggerInquisition
        )
    }

    fun provisionDto(
        id: Long = 1L,
        step: StepDto = stepDto(),
        toolParam: ToolParamDto = toolParamDto(),
        dataPoints: List<ProvisionDataPointDto> = emptyList()
    ) : ProvisionDto {
        return ProvisionDto(
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
        returnsType: TypeDeclarationDto = typeDeclarationDto()
    ) : TransformationDto {
        return TransformationDto(
            id = id,
            name = name,
            description = description,
            examples = examples,
            returnsType = returnsType
        )
    }

    fun revisionDto(
        id: Long = 1L,
        dataPoint: DataPointDto = dataPointDto(),
        step: StepDto = stepDto()
    ) : RevisionDto {
        return RevisionDto(
            id = id,
            dataPoint = dataPoint,
            step = step
        )
    }

    fun typeParameterDto(
        id: Long = 1L,
        typeDeclaration: TypeDeclarationDto = typeDeclarationDto(),
        position: Int = 1,
        parameterTypeDeclaration: TypeDeclarationDto = typeDeclarationDto()
    ) : TypeParameterDto {
        return TypeParameterDto(
            id = id,
            typeDeclaration = typeDeclaration,
            position = position,
            parameterTypeDeclaration = parameterTypeDeclaration
        )
    }

    fun typeSchemaRefDto(
        id: Long = 1L,
        parentRef: TypeSchemaRefDto? = null,
        name: String = "exampleSchema",
        typeDeclaration: TypeDeclarationDto = typeDeclarationDto(),
        description: String = "Schema reference description",
        childRefs: List<TypeSchemaRefDto> = emptyList()
    ) : TypeSchemaRefDto {
        return TypeSchemaRefDto(
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
        workflow: WorkflowDto = workflowDto(),
        parentPhase: PhaseDto? = null,
        name: String = "Phase Name",
        description: String? = "Phase Description",
        displayOrder: Int = 1
    ) : PhaseDto {
        return PhaseDto(
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
    ): TypeUnionMemberDto {
        return TypeUnionMemberDto(
            unionTypeDeclarationId = unionTypeDeclarationId,
            memberTypeDeclarationId = memberTypeDeclarationId
        )
    }


    fun typeVarianceDto(
        id: Long = 23L,
        typeVariableDeclaration: TypeDeclarationDto = typeDeclarationDto(),
        varianceCardinality: VarianceCardinality = VarianceCardinality.Covariant
    ): TypeVarianceDto {
        return TypeVarianceDto(
            id = id,
            typeVariableDeclaration = typeVariableDeclaration,
            varianceCardinality = varianceCardinality
        )
    }

    fun provisionDataPointDto(
        provision: ProvisionDto = provisionDto(),
        dataPoint: DataPointDto = dataPointDto()
    ): ProvisionDataPointDto {
        return ProvisionDataPointDto(
            provisionId = provision.id,
            dataPointId = dataPoint.id
        )
    }

    fun workflowFolderDto(
        id: Long = 32L,
        parentFolder: WorkflowFolderDto? = null,
        name: String = "ExampleFolder",
        description: String? = "Folder description",
        img: String? = null,
        createdAt: String = "2024-12-21T10:00:00",
        updatedAt: String = "2024-12-21T10:00:00"
    ): WorkflowFolderDto {
        return WorkflowFolderDto(
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
        testCase: TestCaseDto = testCaseDto()
    ): TestInputDto {
        return TestInputDto(
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
        testCase: TestCaseDto = testCaseDto()
    ): TestExpectedOutcomeDto {
        return TestExpectedOutcomeDto(
            id = id,
            dataPointName = dataPointName,
            expectedValue = expectedValue,
            comparisonOperator = comparisonOperator,
            testCase = testCase
        )
    }

    fun testResultDto(
        id: Long = 1L,
        testCase: TestCaseDto = testCaseDto(),
        workflowExecution: WorkflowExecutionDto = workflowExecutionDto(),
        passed: Boolean = true,
        executedAt: String = "2024-12-21T00:00:00Z"
    ): TestResultDto {
        return TestResultDto(
            id = id,
            testCase = testCase,
            workflowExecution = workflowExecution,
            passed = passed,
            executedAt = executedAt
        )
    }

    fun typeBoundDto(
        id: Long = 1L,
        boundTypeDeclaration: TypeDeclarationDto = typeDeclarationDto(),
        typeVariableDeclaration: TypeDeclarationDto = typeDeclarationDto()
    ) : TypeBoundsDto {
        return TypeBoundsDto(
            id = id,
            boundTypeDeclaration = boundTypeDeclaration,
            typeVariableDeclaration = typeVariableDeclaration
        )
    }

    fun appliedTransformArgDto(
        id: Long = 0L,
        appliedTransform: AppliedTransformDto = appliedTransformDto(),
        transformationArg: TransformationArgDto = transformationArgDto(),
        value: String = ""
    ) : AppliedTransformArgDto {
        return AppliedTransformArgDto(
            id = id,
            appliedTransform = appliedTransform,
            transformationArg = transformationArg,
            value = value
        )
    }

    fun transformationArgDto(
        id: Long = 0L,
        transformation: TransformationDto = transformationDto(),
        typeDeclaration: TypeDeclarationDto = typeDeclarationDto(),
        name: String = "Transformation Arg Name",
        description: String? = "Transformation Arg Description"
    ): TransformationArgDto {
        return TransformationArgDto(
            id = id,
            transformation = transformation,
            typeDeclaration = typeDeclaration,
            name = name,
            description = description
        )
    }

    fun appliedTransformDto(
        id: Long = 0L,
        transformation: TransformationDto = transformationDto(),
        occurrence: TransformOccurrence = TransformOccurrence.PreReqInjection,
        resultType: TypeDeclarationDto = typeDeclarationDto(),
        dataPoint: DataPointDto? = dataPointDto(),
    ) : AppliedTransformDto {
        return AppliedTransformDto(
            id = id,
            transformation = transformation,
            occurrence = occurrence,
            resultType = resultType,
            dataPoint = dataPoint
        )
    }


    fun <T : BaseDto> createDtoInstance(dtoClass: Class<T>): T {
        return when (dtoClass) {
            PackageRepoDto::class.java -> packageRepoDto() as T
            VersionSpecDto::class.java -> versionSpecDto() as T
            StepDto::class.java -> stepDto() as T
            WorkflowDto::class.java -> workflowDto() as T
            ToolDto::class.java -> toolDto() as T
            TestCaseDto::class.java -> testCaseDto() as T
            DataPointDto::class.java -> dataPointDto() as T
            ToolParamDto::class.java -> toolParamDto() as T
            TypeDeclarationDto::class.java -> typeDeclarationDto() as T
            TypeDefinitionDto::class.java -> typeDefinitionDto() as T
            PaymentMethodDto::class.java -> paymentMethodDto() as T
            TagDto::class.java -> tagDto() as T
            TagRelationDto::class.java -> tagRelationDto() as T
            BenchmarkingDataDto::class.java -> benchmarkingDataDto() as T
            UserDto::class.java -> userDto() as T
            ExecutionLogDto::class.java -> executionLogDto() as T
            ErrorWatchDto::class.java -> errorWatchDto() as T
            ErrorWatchFnDto::class.java -> errorWatchFnDto() as T
            InquisitionDto::class.java -> inquisitionDto() as T
            PreReqInjectionDto::class.java -> preReqInjectionDto() as T
            ProvisionDto::class.java -> provisionDto() as T
            RevisionDto::class.java -> revisionDto() as T
            TypeParameterDto::class.java -> typeParameterDto() as T
            TypeSchemaRefDto::class.java -> typeSchemaRefDto() as T
            TypeUnionMemberDto::class.java -> typeUnionMemberDto() as T
            TypeVarianceDto::class.java -> typeVarianceDto() as T
            PhaseDto::class.java -> phaseDto() as T
            ProvisionDataPointDto::class.java -> provisionDataPointDto() as T
            WorkflowFolderDto::class.java -> workflowFolderDto() as T
            TestInputDto::class.java -> testInputDto() as T
            TestExpectedOutcomeDto::class.java -> testExpectedOutcomeDto() as T
            TestResultDto::class.java -> testResultDto() as T
            PackageDto::class.java -> packageDto() as T
            else -> throw IllegalArgumentException("Unsupported DTO type: ${dtoClass.simpleName}")
        }
    }
}