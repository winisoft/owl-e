package com.wini.owle.dto

//@Suppress("UNCHECKED_CAST")
//object TestInstanceFactory {
//
//    fun <T : BaseDto> createTestInstance(dtoClass: Class<T>): T {
//        return when (dtoClass) {
//            PackageRepoDto::class.java -> PackageRepoDto(
//                id = 1L,
//                sourceType = PackageRepoType.Pypi,
//                sourceUrl = "https://pypi.org"
//            ) as T
//            VersionSpecDto::class.java -> VersionSpecDto(
//                id = 2L,
//                versionMin = "1.0",
//                versionMax = "2.0",
//                versionExact = null
//            ) as T
//            StepDto::class.java -> StepDto(
//                id = 3L,
//                name = "Example Step",
//                description = "A step in a workflow",
//                workflow = createTestInstance(WorkflowDto::class.java),
//                isWorkflowHead = true,
//                tool = createTestInstance(ToolDto::class.java),
//                parentPhase = null,
//                displayOrder = 0,
//                nextStep = null
//            ) as T
//            WorkflowDto::class.java -> WorkflowDto(
//                id = 4L,
//                name = "Example Workflow",
//                description = "A sample workflow description",
//                pythonVersion = "3.9",
//                folderId = 1L,
//                metadata = UserDesignedMetadataDto(
//                    name = "John Arbuckle",
//                    version = "1.0.0",
//                    authors = listOf("Garfield Cat", "Nermal"),
//                    copyrightInformation = "2025",
//                    contactInformation = "lasagnalonely@email.com",
//                    thirdPartyLicenses = "Ok",
//                    discretionAdvisedContent = true,
//                    commercialUsage = CommercialUsage.CORPORATION,
//                    creditNotation = CreditNotation.REQUIRED,
//                    allowRedistribution = false,
//                    modification = Modification.PROHIBITED,
//                    distributionAgreement = DistributionAgreement(
//                        version = "1.0.0",
//                        acceptedAt = "",
//                        updatedAt = ""
//                    ),
//                    sharingConfig = SharingConfig(
//                        includeAllSteps = true,
//                        excludedSteps = emptyList()
//                    )
//                ),
//                createdAt = "2024-12-20T15:00:00",
//                updatedAt = "2024-12-21T09:00:00",
//                stepIds = listOf(1L, 2L, 3L),
//                dataPointIds = listOf(101L, 102L)
//            ) as T
//            ToolDto::class.java -> ToolDto(
//                id = 30L,
//                name = "ExampleTool",
//                description = "Tool description",
//                body = "print('Hello, world!')",
//                returnTypeDeclaration = null,
//                steps = emptyList(),
//                packageDependencies = emptyList()
//            ) as T
//            TestCaseDto::class.java -> TestCaseDto(
//                id = 5L,
//                name = "Sample Test Case",
//                description = "Validates a workflow",
//                workflow = null,
//                user = null,
//                stepId = 3L,
//                inputs = emptyList(),
//                expectedOutcomes = emptyList(),
//                results = emptyList()
//            ) as T
//            DataPointDto::class.java -> DataPointDto(
//                id = 27L,
//                name = "ExampleDataPoint",
//                typeDeclarationId = 17L,
//                description = "Data point description",
//                workflowId = 44L,
//                presetValue = "Some helpful info",
//                storeMethod = StoreMethod.Blob,
//                typeDeclaration = null,
//                workflow = null
//            ) as T
//            ToolParamDto::class.java -> ToolParamDto(
//                id = 31L,
//                toolId = 30L,
//                name = "exampleParam",
//                typeDeclarationId = 17L,
//                hasDefault = true
//            ) as T
//            TypeDeclarationDto::class.java -> TypeDeclarationDto(
//                id = 17L,
//                typeDefinitionId = 18L,
//                typeDefinition = TypeDefinitionDto(
//                    id = 18L,
//                    name = "List",
//                    namespace = "builtins",
//                    versionSpecId = 2L,
//                    declarations = emptyList()
//                ),
//                alias = null,
//                transformations = emptyList(),
//                memberTypeDeclaration = null,
//                unionTypeDeclaration = null
//            ) as T
//            TypeDefinitionDto::class.java -> TypeDefinitionDto(
//                id = 18L,
//                name = "List",
//                namespace = "builtins",
//                versionSpecId = 2L,
//                declarations = emptyList()
//            ) as T
//            PaymentMethodDto::class.java -> PaymentMethodDto(
//                stripePaymentMethodId = "pm_123456789",
//                userId = 24L,
//                cardBrand = CardBrand.Visa,
//                lastFour = "4242",
//                isDefault = true,
//                id = 222L,
//                user = null,
//                createdAt = "2024-12-21T10:00:00",
//                updatedAt = "2024-12-21T10:00:00"
//            ) as T
//            TagDto::class.java -> TagDto(
//                id = 25L,
//                name = "TestTag",
//                description = "A tag for testing purposes",
//                userId = 99L
//            ) as T
//            TagRelationDto::class.java -> TagRelationDto(
//                parentTagId = 25L,
//                childTagId = 26L
//            ) as T
//            BenchmarkingDataDto::class.java -> BenchmarkingDataDto(
//                id = 9L,
//                workflowExecutionId = 1L,
//                totalExecutionTime = DecimalValue("127.0"),
//                totalSteps = 10,
//                aiDeterminedSteps = 3,
//                predeterminedSteps = 7
//            ) as T
//            UserDto::class.java -> UserDto(
//                id = 24L,
//                email = "example@example.com",
//                subscriptionStatus = SubscriptionStatus.Registered,
//                createdAt = "2024-12-20T12:00:00Z",
//                updatedAt = "2024-12-21T12:00:00Z",
//                password = "my_secure_password",
//                resetToken = "reset_token_123",
//                oauthProvider = "google",
//                oauthId = "oauth_id_456",
//                apiKey = "api_key_789",
//                paymentMethods = emptyList() // Can be populated with PaymentMethodDto instances as needed
//            ) as T
//
//            ExecutionLogDto::class.java -> ExecutionLogDto(
//                id = 10L,
//                stepExecutionId = 2L,
//                timestamp = "2024-12-21T12:00:00Z",
//                logLevel = LogLevel.Info,
//                message = "Execution started"
//            ) as T
//            ErrorWatchDto::class.java -> ErrorWatchDto(
//                id = 11L,
//                stepId = 3L,
//                errorType = "RuntimeException",
//                triggerStepId = 4L,
//                callStackFnFilters = emptyList()
//            ) as T
//            ErrorWatchFnDto::class.java -> ErrorWatchFnDto(
//                id = 12L,
//                errorWatchId = 11L,
//                functionName = "exampleFunction",
//                position = 1
//            ) as T
//            InquisitionDto::class.java -> InquisitionDto(
//                id = 13L,
//                workflowStepId = 5L,
//                className = "Condition",
//                dataPointId = 2L,
//                onMetWorkflowStepId = 6L,
//                executionOrder = 1,
//                transformations = emptyList()
//            ) as T
//            PreReqInjectionDto::class.java -> PreReqInjectionDto(
//                id = 14L,
//                junctionId = 3L,
//                sourceDataPointId = 4L,
//                injectedDataPointId = 5L,
//                name = "InjectionExample",
//                description = "Description of injection"
//            ) as T
//            ProvisionDto::class.java -> ProvisionDto(
//                id = 15L,
//                stepId = 7L,
//                toolParamId = 8L,
//                dataPoints = emptyList()
//            ) as T
//            RevisionDto::class.java -> RevisionDto(
//                id = 16L,
//                dataPointId = 9L,
//                stepId = 10L,
//                transformationId = null,
//                dataPoint = createTestInstance(DataPointDto::class.java),
//                step = createTestInstance(StepDto::class.java),
//                transformation = null
//            ) as T
//            TypeParameterDto::class.java -> TypeParameterDto(
//                id = 19L,
//                typeDeclarationId = 17L,
//                position = 1,
//                parameterTypeDeclarationId = 20L
//            ) as T
//            TypeSchemaRefDto::class.java -> TypeSchemaRefDto(
//                id = 20L,
//                parentRefId = null,
//                name = "exampleSchema",
//                typeDeclarationId = 18L,
//                description = "Schema reference description",
//                childRefs = emptyList()
//            ) as T
//            TypeUnionMemberDto::class.java -> TypeUnionMemberDto(
//                unionTypeDeclarationId = 21L,
//                memberTypeDeclarationId = 22L
//            ) as T
//            TypeVarianceDto::class.java -> TypeVarianceDto(
//                id = 23L,
//                typeVariableDeclarationId = 24L,
//                varianceCardinality = VarianceCardinality.Covariant
//            ) as T
//            PhaseDto::class.java -> PhaseDto(
//                id = 28L,
//                workflowId = 1L,
//                parentPhaseId = null,
//                name = "InitialPhase",
//                description = "Description of the phase",
//                displayOrder = 1,
//                workflow = createTestInstance(WorkflowDto::class.java)
//            ) as T
//            ProvisionDataPointDto::class.java -> ProvisionDataPointDto(
//                provisionId = 15L,
//                dataPointId = 27L
//            ) as T
//            WorkflowFolderDto::class.java -> WorkflowFolderDto(
//                id = 32L,
//                parentFolderId = null,
//                name = "ExampleFolder",
//                description = "Folder description",
//                img = null,
//                createdAt = "2024-12-21T10:00:00",
//                updatedAt = "2024-12-21T10:00:00",
//                subFolderIds = emptyList(),
//                workflowIds = emptyList()
//            ) as T
//            TestInputDto::class.java -> TestInputDto(
//                id = 6L,
//                dataPointName = "input1",
//                value = "value1",
//                generationMethod = GenerationMethod.Manual,
//                testCaseId = 5L
//            ) as T
//            TestExpectedOutcomeDto::class.java -> TestExpectedOutcomeDto(
//                id = 7L,
//                dataPointName = "output1",
//                expectedValue = "expectedValue1",
//                comparisonOperator = ComparisonOperator.Equals,
//                testCaseId = 5L
//            ) as T
//            TestResultDto::class.java -> TestResultDto(
//                id = 8L,
//                testCaseId = 5L,
//                workflowExecutionId = 2L,
//                passed = true,
//                executedAt = "2024-12-21T00:00:00Z"
//            ) as T
//
//            else -> throw IllegalArgumentException("Unsupported DTO type: ${dtoClass.simpleName}")
//        }
//    }
//}
//
