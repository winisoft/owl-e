package com.wini.owle.mapper

import com.wini.owle.entity.environs.*
import com.wini.owle.entity.runs.*
import com.wini.owle.entity.steps.*
import com.wini.owle.entity.transforms.*
import com.wini.owle.entity.typing.*
import com.wini.owle.entity.user.*
import com.wini.owle.entity.workflows.*
import com.wini.owle.entity.environs.VersionSpecEntity
import com.wini.owle.entity.runs.step.StepRunEntity
import com.wini.owle.entity.runs.step.StepRunLogLineEntity
import com.wini.owle.entity.runs.test.BenchmarkReportEntity
import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.entity.runs.test.TestExpectationEntity
import com.wini.owle.entity.runs.test.TestInputEntity
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import com.wini.owle.entity.runs.workflow.WorkflowTestResultEntity
import com.wini.owle.entity.user.tags.StepTagEntity
import com.wini.owle.entity.user.tags.TagEntity
import com.wini.owle.entity.user.tags.TagRelationEntity
import com.wini.owle.entity.user.tags.WorkflowTagEntity
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
    single<DaoFactory<VersionSpecEntity, Long>> { ExposedDaoFactory(VersionSpecEntity) }
    single<DaoFactory<PyPackageRepoEntity, Long>> { ExposedDaoFactory(PyPackageRepoEntity) }
    single<DaoFactory<PyPackageEntity, Long>> { ExposedDaoFactory(PyPackageEntity) }
    single<DaoFactory<ToolPyPackDepEntity, CompositeID>> { ExposedDaoFactory(ToolPyPackDepEntity) }
    single<DaoFactory<BenchmarkReportEntity, Long>> { ExposedDaoFactory(BenchmarkReportEntity) }
    single<DaoFactory<StepRunLogLineEntity, Long>> { ExposedDaoFactory(StepRunLogLineEntity) }
    single<DaoFactory<StepBenchmarkingDataDao, Long>> { ExposedDaoFactory(StepBenchmarkingDataDao) }
    single<DaoFactory<StepRunEntity, Long>> { ExposedDaoFactory(StepRunEntity) }
    single<DaoFactory<TestCaseEntity, Long>> { ExposedDaoFactory(TestCaseEntity) }
    single<DaoFactory<TestExpectationEntity, Long>> { ExposedDaoFactory(TestExpectationEntity) }
    single<DaoFactory<TestInputEntity, Long>> { ExposedDaoFactory(TestInputEntity) }
    single<DaoFactory<WorkflowTestResultEntity, Long>> { ExposedDaoFactory(WorkflowTestResultEntity) }
    single<DaoFactory<WorkflowRunEntity, Long>> { ExposedDaoFactory(WorkflowRunEntity) }
    single<DaoFactory<ErrorWatchEntity, Long>> { ExposedDaoFactory(ErrorWatchEntity) }
    single<DaoFactory<ErrorWatchFnEntity, Long>> { ExposedDaoFactory(ErrorWatchFnEntity) }
    single<DaoFactory<InquisitionEntity, Long>> { ExposedDaoFactory(InquisitionEntity) }
    single<DaoFactory<InitialDataPointInjectorEntity, Long>> { ExposedDaoFactory(InitialDataPointInjectorEntity) }
    single<DaoFactory<ProvisionDataPointEntity, CompositeID>> { ExposedDaoFactory(ProvisionDataPointEntity) }
    single<DaoFactory<ToolParamDataPointEntity, Long>> { ExposedDaoFactory(ToolParamDataPointEntity) }
    single<DaoFactory<DataPointSaveEntity, Long>> { ExposedDaoFactory(DataPointSaveEntity) }
    single<DaoFactory<WorkflowJunctionEntity, Long>> { ExposedDaoFactory(WorkflowJunctionEntity) }
    single<DaoFactory<AppliedTransformArgEntity, Long>> { ExposedDaoFactory(AppliedTransformArgEntity) }
    single<DaoFactory<AppliedTransformEntity, Long>> { ExposedDaoFactory(AppliedTransformEntity) }
    single<DaoFactory<TransformArgEntity, Long>> { ExposedDaoFactory(TransformArgEntity) }
    single<DaoFactory<TransformEntity, Long>> { ExposedDaoFactory(TransformEntity) }
    single<DaoFactory<TransformTargetTypeEntity, CompositeID>> { ExposedDaoFactory(TransformTargetTypeEntity) }
    single<DaoFactory<TypeBoundEntity, Long>> { ExposedDaoFactory(TypeBoundEntity) }
    single<DaoFactory<TypeDeclarationEntity, Long>> { ExposedDaoFactory(TypeDeclarationEntity) }
    single<DaoFactory<TypeDefinitionEntity, Long>> { ExposedDaoFactory(TypeDefinitionEntity) }
    single<DaoFactory<TypeParameterEntity, Long>> { ExposedDaoFactory(TypeParameterEntity) }
    single<DaoFactory<TypeSchemaRefEntity, Long>> { ExposedDaoFactory(TypeSchemaRefEntity) }
    single<DaoFactory<TypeUnionMemberEntity, CompositeID>> { ExposedDaoFactory(TypeUnionMemberEntity) }
    single<DaoFactory<TypeVarianceEntity, Long>> { ExposedDaoFactory(TypeVarianceEntity) }
    single<DaoFactory<PaymentMethodEntity, Long>> { ExposedDaoFactory(PaymentMethodEntity) }
    single<DaoFactory<StepTagEntity, CompositeID>> { ExposedDaoFactory(StepTagEntity) }
    single<DaoFactory<TagRelationEntity, CompositeID>> { ExposedDaoFactory(TagRelationEntity) }
    single<DaoFactory<TagEntity, Long>> { ExposedDaoFactory(TagEntity) }
    single<DaoFactory<UserEntity, Long>> { ExposedDaoFactory(UserEntity) }
    single<DaoFactory<WorkflowTagEntity, CompositeID>> { ExposedDaoFactory(WorkflowTagEntity) }
    single<DaoFactory<DataPointEntity, Long>> { ExposedDaoFactory(DataPointEntity) }
    single<DaoFactory<PhaseEntity, Long>> { ExposedDaoFactory(PhaseEntity) }
    single<DaoFactory<StepEntity, Long>> { ExposedDaoFactory(StepEntity) }
    single<DaoFactory<ToolParamEntity, Long>> { ExposedDaoFactory(ToolParamEntity) }
    single<DaoFactory<ToolEntity, Long>> { ExposedDaoFactory(ToolEntity) }
    single<DaoFactory<WorkflowFolderEntity, Long>> { ExposedDaoFactory(WorkflowFolderEntity) }
    single<DaoFactory<WorkflowEntity, Long>> { ExposedDaoFactory(WorkflowEntity) }
    single<DaoFactory<WorkflowMetadataEntity, Long>> { ExposedDaoFactory(WorkflowMetadataEntity) }
}

val mapperModule = module {

    includes(daoFactoryModule)

    single<VersionSpecsMapper> { VersionSpecsMapper(get()) }
    factory<DaoFactory<VersionSpecEntity, Long>> { ExposedDaoFactory(VersionSpecEntity) }
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


