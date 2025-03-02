package com.wini.owle.api

import com.wini.owle.api.environs.PackageApi
import com.wini.owle.dto.*
import org.koin.dsl.module

val apiModule = module {

    single<GenericLongPkApi<PackageRepoDto>> { PackageRepoApi(get()) }
    single<GenericLongPkApi<PackageDto>> { PackageApi(get()) }
    single<GenericLongPkApi<VersionSpecDto>> { VersionSpecApi(get()) }
    single<GenericLongPkApi<BenchmarkingDataDto>> { BenchmarkingDataApi(get()) }
    single<GenericLongPkApi<ExecutionLogDto>> { ExecutionLogApi(get()) }
    single<GenericLongPkApi<StepExecutionDto>> { StepExecutionApi(get()) }
    single<GenericLongPkApi<TestCaseDto>> { TestCaseApi(get()) }
    single<GenericLongPkApi<TestExpectedOutcomeDto>> { TestExpectedOutcomeApi(get()) }
    single<GenericLongPkApi<TestInputDto>> { TestInputApi(get()) }
    single<GenericLongPkApi<TestResultDto>> { TestResultApi(get()) }
    single<GenericLongPkApi<WorkflowExecutionDto>> { WorkflowExecutionApi(get()) }
    single<GenericLongPkApi<ErrorWatchFnDto>> { ErrorWatchFnApi(get()) }
    single<GenericLongPkApi<ErrorWatchDto>> { ErrorWatchApi(get()) }
    single<GenericLongPkApi<InquisitionDto>> { InquisitionApi(get()) }
    single<GenericLongPkApi<WorkflowJunctionDto>> { WorkflowJunctionApi(get()) }
    single<GenericLongPkApi<AppliedTransformArgDto>> { AppliedTransformArgApi(get()) }
    single<GenericLongPkApi<AppliedTransformDto>> { AppliedTransformApi(get()) }
    single<GenericLongPkApi<TransformationArgDto>> { TransformationArgApi(get()) }
    single<GenericLongPkApi<TransformationDto>> { TransformationApi(get()) }
    single<GenericLongPkApi<TypeDeclarationDto>> { TypeDeclarationApi(get()) }
    single<GenericLongPkApi<TypeDefinitionDto>> { TypeDefinitionApi(get()) }
    single<GenericLongPkApi<TypeParameterDto>> { TypeParameterApi(get()) }
    single<GenericLongPkApi<TypeSchemaRefDto>> { TypeSchemaRefApi(get()) }
    single<GenericLongPkApi<TypeVarianceDto>> { TypeVarianceApi(get()) }
    single<GenericLongPkApi<PaymentMethodDto>> { PaymentMethodApi(get()) }
    single<GenericLongPkApi<TagDto>> { TagApi(get()) }
    single<GenericLongPkApi<UserDto>> { UserApi(get()) }
    single<GenericLongPkApi<PhaseDto>> { PhaseApi(get()) }
    single<GenericLongPkApi<StepDto>> { StepApi(get()) }
    single<GenericLongPkApi<ToolParamDto>> { ToolParamApi(get()) }
    single<GenericLongPkApi<ToolDto>> { ToolApi(get()) }
    single<GenericLongPkApi<WorkflowFolderDto>> { WorkflowFolderApi(get()) }
    single<GenericLongPkApi<WorkflowDto>> { WorkflowApi(get()) }
    single<GenericCompositeIdApi<ProvisionDataPointDto>> { ProvisionDataPointApi(get(), get()) }
    single<GenericCompositeIdApi<TypeUnionMemberDto>> { TypeUnionMemberApi(get(), get()) }
    single<GenericCompositeIdApi<TagRelationDto>> { TagRelationApi(get(), get()) }
    single<GenericCompositeIdApi<TransformationTargetTypeDto>> { TransformTargetTypeApi(get(), get()) }
    single<GenericCompositeIdApi<ToolPackageDependencyDto>> { ToolPackageDependencyApi(get(), get()) }
}

val routesModule = module {

    includes(apiModule)

    single { EnvironsRoutes(get(), get(), get(), get()) }
    single {  RunsRoutes(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    single {  StepsRoutes(get(), get(), get(), get(), get(), get(), get(), get()) }
    single {  TransformsRoutes(get(), get(), get(), get(), get()) }
    single {  TypingRoutes(get(), get(), get(), get(), get(), get(), get()) }
    single {  UserRoutes(get(), get(), get(), get()) }
    single { WorkflowsRoutes(get(), get(), get(), get(), get(), get(), get()) }
}