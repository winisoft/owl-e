package com.wini.owle.mapper

import com.wini.owle.mapper.environs.PackageRepoMapper
import com.wini.owle.mapper.environs.PackagesMapper
import com.wini.owle.mapper.environs.ToolPackageDependencyMapper
import com.wini.owle.mapper.environs.VersionSpecsMapper
import com.wini.owle.mapper.runs.*
import com.wini.owle.mapper.steps.*
import com.wini.owle.mapper.transforms.*
import com.wini.owle.mapper.typing.*
import com.wini.owle.mapper.user.*
import com.wini.owle.mapper.workflows.*
import org.koin.dsl.module


val mapperModule = module {

    single<TypeUnionMembersMapper> { TypeUnionMembersMapper() }
    single<TransformTargetTypesMapper> { TransformTargetTypesMapper() }
    single<TagRelationsMapper> { TagRelationsMapper() }
    single<WorkflowFoldersMapper> { WorkflowFoldersMapper() }
    single<WorkflowsMapper> { WorkflowsMapper(get(), get()) }
    single<VersionSpecsMapper> { VersionSpecsMapper() }
    single<TypeDeclarationsMapper> { TypeDeclarationsMapper(get()) }
    single<TagsMapper> { TagsMapper(get()) }
    single<StepTagsMapper> { StepTagsMapper() }
    single<WorkflowTagsMapper> { WorkflowTagsMapper() }
    single<ToolParamsMapper> { ToolParamsMapper(get(), get()) }
    single<PhasesMapper> { PhasesMapper(get()) }
    single<BenchmarkingDataMapper> { BenchmarkingDataMapper(get()) }
    single<AppliedTransformArgsMapper> { AppliedTransformArgsMapper(get(), get()) }
    single<PreReqInjectionsMapper> { PreReqInjectionsMapper(get(), get()) }
    single<TestInputsMapper> { TestInputsMapper(get()) }
    single<TestExpectedOutcomesMapper> { TestExpectedOutcomesMapper(get()) }
    single<TestResultsMapper> { TestResultsMapper(get(), get()) }
    single<StepExecutionsMapper> { StepExecutionsMapper(get(), get()) }
    single<TransformationArgsMapper> { TransformationArgsMapper(get(), get()) }
    single<ErrorWatchFnsMapper> { ErrorWatchFnsMapper(get()) }
    single<PackagesMapper> { PackagesMapper(get()) }
    single<TypeParametersMapper> { TypeParametersMapper(get()) }
    single<TypeSchemaRefsMapper> { TypeSchemaRefsMapper(get()) }
    single<StepBenchmarkingDataMapper> { StepBenchmarkingDataMapper(get()) }
    single<DataPointsMapper> { DataPointsMapper(get(), get()) }
    single<ToolsMapper> { ToolsMapper(get()) }
    single<ToolPackageDependencyMapper> { ToolPackageDependencyMapper() }
    single<PackageRepoMapper> { PackageRepoMapper() }
    single<TransformationsMapper> { TransformationsMapper(get()) }
    single<UsersMapper> { UsersMapper() }
    single<StepsMapper> { StepsMapper(get(), get(), get()) }
    single<InquisitionsMapper> { InquisitionsMapper(get(), get()) }
    single<AppliedTransformsMapper> { AppliedTransformsMapper(get(), get(), get(), get()) }
    single<WorkflowJunctionsMapper> { WorkflowJunctionsMapper(get(), get(), get()) }
    single<WorkflowExecutionsMapper> { WorkflowExecutionsMapper(get(), get(), get()) }
    single<TestCasesMapper> { TestCasesMapper(get(), get(), get(), get(), get(), get()) }
    single<ProvisionsMapper> { ProvisionsMapper(get(), get(), get()) }
    single<ErrorWatchesMapper> { ErrorWatchesMapper(get()) }
    single<TypeDefinitionsMapper> { TypeDefinitionsMapper(get()) }
    single<TypeBoundsMapper> { TypeBoundsMapper(get()) }
    single<TypeVariancesMapper> { TypeVariancesMapper(get()) }
    single<RevisionsMapper> { RevisionsMapper(get(), get()) }
    single<PaymentMethodsMapper> { PaymentMethodsMapper(get()) }
    single<ProvisionDataPointMapper> { ProvisionDataPointMapper() }
    single<PackageRepoMapper> { PackageRepoMapper() }
    single<ExecutionLogMapper> { ExecutionLogMapper(get()) }
    single<UserDesignedMetadataMapper> { UserDesignedMetadataMapper() }
}


