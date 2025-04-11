package com.wini.owle.mapper

import com.wini.owle.entity.environs.*
import com.wini.owle.entity.runs.*
import com.wini.owle.entity.steps.*
import com.wini.owle.entity.transforms.*
import com.wini.owle.entity.typing.*
import com.wini.owle.entity.user.*
import com.wini.owle.entity.workflows.*
import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.mapper.environs.PackageRepoMapper
import com.wini.owle.mapper.environs.PackagesMapper
import com.wini.owle.mapper.environs.ToolPackageDependencyMapper
import com.wini.owle.mapper.environs.VersionSpecsMapper
import com.wini.owle.mapper.environs.enums.PackageRepoTypeMapper
import com.wini.owle.mapper.runs.*
import com.wini.owle.mapper.runs.enums.ComparisonOperatorMapper
import com.wini.owle.mapper.runs.enums.ExecutionStatusMapper
import com.wini.owle.mapper.runs.enums.GenerationMethodMapper
import com.wini.owle.mapper.runs.enums.LogLevelMapper
import com.wini.owle.mapper.steps.*
import com.wini.owle.mapper.transforms.*
import com.wini.owle.mapper.transforms.enums.TransformOccurrenceMapper
import com.wini.owle.mapper.typing.*
import com.wini.owle.mapper.typing.enums.VarianceCardinalityMapper
import com.wini.owle.mapper.user.*
import com.wini.owle.mapper.user.enums.CardBrandMapper
import com.wini.owle.mapper.user.enums.SubscriptionStatusMapper
import com.wini.owle.mapper.workflows.*
import com.wini.owle.mapper.workflows.enums.AuthorshipCreditMapper
import com.wini.owle.mapper.workflows.enums.CommercialUseMapper
import com.wini.owle.mapper.workflows.enums.ModificationTermsMapper
import com.wini.owle.mapper.workflows.enums.StoreMethodMapper
import org.jetbrains.exposed.dao.id.CompositeID
import org.koin.dsl.module


val daoFactoryModule = module {
    single<DaoFactory<VersionSpecDao, Long>> { ExposedDaoFactory(VersionSpecDao) }
    single<DaoFactory<PackageRepoDao, Long>> { ExposedDaoFactory(PackageRepoDao) }
    single<DaoFactory<PackageDao, Long>> { ExposedDaoFactory(PackageDao) }
    single<DaoFactory<ToolPackageDependencyDao, CompositeID>> { ExposedDaoFactory(ToolPackageDependencyDao) }
    single<DaoFactory<BenchmarkingDataDao, Long>> { ExposedDaoFactory(BenchmarkingDataDao) }
    single<DaoFactory<ExecutionLogDao, Long>> { ExposedDaoFactory(ExecutionLogDao) }
    single<DaoFactory<StepBenchmarkingDataDao, Long>> { ExposedDaoFactory(StepBenchmarkingDataDao) }
    single<DaoFactory<StepExecutionDao, Long>> { ExposedDaoFactory(StepExecutionDao) }
    single<DaoFactory<TestCaseDao, Long>> { ExposedDaoFactory(TestCaseDao) }
    single<DaoFactory<TestExpectedOutcomeDao, Long>> { ExposedDaoFactory(TestExpectedOutcomeDao) }
    single<DaoFactory<TestInputDao, Long>> { ExposedDaoFactory(TestInputDao) }
    single<DaoFactory<TestResultDao, Long>> { ExposedDaoFactory(TestResultDao) }
    single<DaoFactory<WorkflowExecutionDao, Long>> { ExposedDaoFactory(WorkflowExecutionDao) }
    single<DaoFactory<ErrorWatchDao, Long>> { ExposedDaoFactory(ErrorWatchDao) }
    single<DaoFactory<ErrorWatchFnDao, Long>> { ExposedDaoFactory(ErrorWatchFnDao) }
    single<DaoFactory<InquisitionDao, Long>> { ExposedDaoFactory(InquisitionDao) }
    single<DaoFactory<PreReqInjectionDao, Long>> { ExposedDaoFactory(PreReqInjectionDao) }
    single<DaoFactory<ProvisionDataPointDao, CompositeID>> { ExposedDaoFactory(ProvisionDataPointDao) }
    single<DaoFactory<ProvisionDao, Long>> { ExposedDaoFactory(ProvisionDao) }
    single<DaoFactory<RevisionDao, Long>> { ExposedDaoFactory(RevisionDao) }
    single<DaoFactory<WorkflowJunctionDao, Long>> { ExposedDaoFactory(WorkflowJunctionDao) }
    single<DaoFactory<AppliedTransformArgDao, Long>> { ExposedDaoFactory(AppliedTransformArgDao) }
    single<DaoFactory<AppliedTransformDao, Long>> { ExposedDaoFactory(AppliedTransformDao) }
    single<DaoFactory<TransformationArgDao, Long>> { ExposedDaoFactory(TransformationArgDao) }
    single<DaoFactory<TransformationDao, Long>> { ExposedDaoFactory(TransformationDao) }
    single<DaoFactory<TransformTargetTypeDao, CompositeID>> { ExposedDaoFactory(TransformTargetTypeDao) }
    single<DaoFactory<TypeBoundDao, Long>> { ExposedDaoFactory(TypeBoundDao) }
    single<DaoFactory<TypeDeclarationDao, Long>> { ExposedDaoFactory(TypeDeclarationDao) }
    single<DaoFactory<TypeDefinitionDao, Long>> { ExposedDaoFactory(TypeDefinitionDao) }
    single<DaoFactory<TypeParameterDao, Long>> { ExposedDaoFactory(TypeParameterDao) }
    single<DaoFactory<TypeSchemaRefDao, Long>> { ExposedDaoFactory(TypeSchemaRefDao) }
    single<DaoFactory<TypeUnionMemberDao, CompositeID>> { ExposedDaoFactory(TypeUnionMemberDao) }
    single<DaoFactory<TypeVarianceDao, Long>> { ExposedDaoFactory(TypeVarianceDao) }
    single<DaoFactory<PaymentMethodDao, Long>> { ExposedDaoFactory(PaymentMethodDao) }
    single<DaoFactory<StepTagDao, CompositeID>> { ExposedDaoFactory(StepTagDao) }
    single<DaoFactory<TagRelationDao, CompositeID>> { ExposedDaoFactory(TagRelationDao) }
    single<DaoFactory<TagDao, Long>> { ExposedDaoFactory(TagDao) }
    single<DaoFactory<UserDao, Long>> { ExposedDaoFactory(UserDao) }
    single<DaoFactory<WorkflowTagDao, CompositeID>> { ExposedDaoFactory(WorkflowTagDao) }
    single<DaoFactory<DataPointDao, Long>> { ExposedDaoFactory(DataPointDao) }
    single<DaoFactory<PhaseDao, Long>> { ExposedDaoFactory(PhaseDao) }
    single<DaoFactory<StepDao, Long>> { ExposedDaoFactory(StepDao) }
    single<DaoFactory<ToolParamDao, Long>> { ExposedDaoFactory(ToolParamDao) }
    single<DaoFactory<ToolDao, Long>> { ExposedDaoFactory(ToolDao) }
    single<DaoFactory<WorkflowFolderDao, Long>> { ExposedDaoFactory(WorkflowFolderDao) }
    single<DaoFactory<WorkflowDao, Long>> { ExposedDaoFactory(WorkflowDao) }
    single<DaoFactory<WorkflowMetadataDao, Long>> { ExposedDaoFactory(WorkflowMetadataDao) }
}

val mapperModule = module {

    includes(daoFactoryModule)

    single<VersionSpecsMapper> { VersionSpecsMapper(get()) }
    factory<DaoFactory<VersionSpecDao, Long>> { ExposedDaoFactory(VersionSpecDao) }
    single<TypeUnionMembersMapper> { TypeUnionMembersMapper(get()) }
    single<TransformTargetTypesMapper> { TransformTargetTypesMapper(get()) }
    single<TagRelationsMapper> { TagRelationsMapper(get()) }
    single<WorkflowFoldersMapper> { WorkflowFoldersMapper(get(), get()) }
    single<WorkflowsMapper> { WorkflowsMapper(get(), get(), get()) }
    single<PackageRepoTypeMapper> { PackageRepoTypeMapper() }

    single<ComparisonOperatorMapper> { ComparisonOperatorMapper() }
    single<ExecutionStatusMapper> { ExecutionStatusMapper() }
    single<GenerationMethodMapper> { GenerationMethodMapper() }
    single<LogLevelMapper> { LogLevelMapper() }
    single<TransformOccurrenceMapper> { TransformOccurrenceMapper() }
    single<VarianceCardinalityMapper> { VarianceCardinalityMapper() }
    single<CardBrandMapper> { CardBrandMapper()  }
    single<SubscriptionStatusMapper> { SubscriptionStatusMapper() }
    single<AuthorshipCreditMapper> { AuthorshipCreditMapper() }
    single<CommercialUseMapper> { CommercialUseMapper() }
    single<ModificationTermsMapper> { ModificationTermsMapper() }
    single<StoreMethodMapper> { StoreMethodMapper() }
    single<DateTimeConverter> { DateTimeConverter }

    single<TypeDeclarationsMapper> { TypeDeclarationsMapper(get(), get()) }
    single<TagsMapper> { TagsMapper(get(), get()) }
    //single<StepTagsMapper> { StepTagsMapper() }
    //single<WorkflowTagsMapper> { WorkflowTagsMapper() }
    single<ToolParamsMapper> { ToolParamsMapper(get(), get(), get()) }
    single<PhasesMapper> { PhasesMapper(get(), get()) }
    single<BenchmarkingDataMapper> { BenchmarkingDataMapper(get(), get()) }
    single<AppliedTransformArgsMapper> { AppliedTransformArgsMapper(get(), get(), get()) }
    single<PreReqInjectionsMapper> { PreReqInjectionsMapper(get(), get(), get()) }
    single<TestInputsMapper> { TestInputsMapper(get(), get(), get()) }
    single<TestExpectedOutcomesMapper> { TestExpectedOutcomesMapper(get(), get(), get()) }
    single<TestResultsMapper> { TestResultsMapper(get(), get(), get(), get()) }
    single<StepExecutionsMapper> { StepExecutionsMapper(get(), get(), get(), get(), get()) }
    single<TransformationArgsMapper> { TransformationArgsMapper(get(), get(), get()) }
    single<ErrorWatchFnsMapper> { ErrorWatchFnsMapper(get(), get()) }
    single<PackagesMapper> { PackagesMapper(get(), get()) }
    single<TypeParametersMapper> { TypeParametersMapper(get(), get()) }
    single<TypeSchemaRefsMapper> { TypeSchemaRefsMapper(get(), get()) }
    single<StepBenchmarkingDataMapper> { StepBenchmarkingDataMapper(get(), get()) }
    single<DataPointsMapper> { DataPointsMapper(get(), get(), get(), get()) }
    single<ToolsMapper> { ToolsMapper(get(), get()) }
    single<ToolPackageDependencyMapper> { ToolPackageDependencyMapper(get()) }
    single<PackageRepoMapper> { PackageRepoMapper(get(), get()) }
    single<TransformationsMapper> { TransformationsMapper(get(), get()) }
    single<UsersMapper> { UsersMapper(get(), get(), get()) }
    single<StepsMapper> { StepsMapper(get(), get(), get(), get()) }
    single<InquisitionsMapper> { InquisitionsMapper(get(), get(), get()) }
    single<AppliedTransformsMapper> { AppliedTransformsMapper(get(), get(), get(), get(), get()) }
    single<WorkflowJunctionsMapper> { WorkflowJunctionsMapper(get(), get(), get(), get()) }
    single<WorkflowExecutionsMapper> { WorkflowExecutionsMapper(get(), get(), get(), get(), get(), get()) }
    single<TestCasesMapper> { TestCasesMapper(get(), get(), get(), get(), get(), get(), get()) }
    single<ProvisionsMapper> { ProvisionsMapper(get(), get(), get(), get()) }
    single<ErrorWatchesMapper> { ErrorWatchesMapper(get(), get()) }
    single<TypeDefinitionsMapper> { TypeDefinitionsMapper(get(), get()) }
    single<TypeBoundsMapper> { TypeBoundsMapper(get(), get()) }
    single<TypeVariancesMapper> { TypeVariancesMapper(get(), get()) }
    single<RevisionsMapper> { RevisionsMapper(get(), get(), get()) }
    single<PaymentMethodsMapper> { PaymentMethodsMapper(get(), get(), get(), get()) }
    single<ProvisionDataPointMapper> { ProvisionDataPointMapper(get()) }
    single<PackageRepoMapper> { PackageRepoMapper(get(), get()) }
    single<ExecutionLogMapper> { ExecutionLogMapper(get(), get(), get(), get()) }
}


