package com.wini.owle.api



class EnvironsRoutes(
    private val packageApi: PackageApi,
    private val packageRepoApi: PackageRepoApi,
    private val toolPackageDependencyApi: ToolPackageDependencyApi,
    private val versionSpecApi: VersionSpecApi,
): RoutedApiModule by listOf(
    packageApi, packageRepoApi, toolPackageDependencyApi, versionSpecApi
) as RoutedApiModule

class RunsRoutes(
    private val benchmarkingDataApi: BenchmarkingDataApi,
    private val executionLogApi: ExecutionLogApi,
    private val stepBenchmarkingDataApi: BenchmarkingDataApi,
    private val stepExecutionApi: StepExecutionApi,
    private val testCaseApi: TestCaseApi,
    private val testExpectedOutcomeApi: TestExpectedOutcomeApi,
    private val testInputApi: TestInputApi,
    private val testResultApi: TestResultApi,
    private val workflowExecutionApi: WorkflowExecutionApi
): RoutedApiModule by listOf(
    benchmarkingDataApi,
    executionLogApi,
    stepBenchmarkingDataApi,
    stepExecutionApi,
    testCaseApi,
    testExpectedOutcomeApi,
    testInputApi,
    testResultApi,
    workflowExecutionApi,
) as RoutedApiModule

class StepsRoutes(
    private val inquisitionApi: InquisitionApi,
    private val preReqInjectionApi: PreReqInjectionApi,
    private val provisionDataPointApi: ProvisionDataPointApi,
    private val provisionApi: ProvisionApi,
    private val revisionApi: RevisionApi,
    private val workflowJunctionApi: WorkflowJunctionApi,
    private val errorWatchApi: ErrorWatchApi,
    private val errorWatchFnApi: ErrorWatchFnApi,
): RoutedApiModule by listOf(
    inquisitionApi,
    preReqInjectionApi,
    provisionDataPointApi,
    provisionApi,
    revisionApi,
    workflowJunctionApi,
    errorWatchApi,
    errorWatchFnApi,
) as RoutedApiModule

class TransformsRoutes(
    private val transformTargetTypeApi: TransformTargetTypeApi,
    private val appliedTransformArgApi: AppliedTransformArgApi,
    private val appliedTransformApi: AppliedTransformApi,
    private val transformationArgApi: TransformationArgApi,
    private val transformationApi: TransformationApi,
): RoutedApiModule by listOf(
    transformTargetTypeApi,
    appliedTransformArgApi,
    appliedTransformApi,
    transformationArgApi,
    transformationApi,
) as RoutedApiModule


class TypingRoutes(
    private val typeVarianceApi: TypeVarianceApi,
    private val typeBoundApi: TypeBoundsApi,
    private val typeDeclarationApi: TypeDeclarationApi,
    private val typeDefinitionApi: TypeDefinitionApi,
    private val typeParameterApi: TypeParameterApi,
    private val typeSchemaRefApi: TypeSchemaRefApi,
    private val typeUnionMemberApi: TypeUnionMemberApi,
): RoutedApiModule by listOf(
    typeVarianceApi,
    typeBoundApi,
    typeDeclarationApi,
    typeDefinitionApi,
    typeParameterApi,
    typeSchemaRefApi,
    typeUnionMemberApi,
) as RoutedApiModule

class UserRoutes(
    private val userApi: UserApi,
    private val paymentMethodApi: PaymentMethodApi,
    private val tagApi: TagApi,
    private val tagRelationApi: TagRelationApi,
): RoutedApiModule by listOf(
    userApi,
    paymentMethodApi,
    tagApi,
    tagRelationApi
) as RoutedApiModule


class  WorkflowsRoutes(
    private val workflowFolderApi: WorkflowFolderApi,
    private val dataPointApi: DataPointsApi,
    private val phaseApi: PhaseApi,
    private val stepApi: StepApi,
    private val toolApi: ToolApi,
    private val toolParamApi: ToolParamApi,
    private val workflowApi: WorkflowApi,
): RoutedApiModule by listOf(
    workflowFolderApi,
    dataPointApi,
    phaseApi,
    stepApi,
    toolApi,
    toolParamApi,
    workflowApi
) as RoutedApiModule
