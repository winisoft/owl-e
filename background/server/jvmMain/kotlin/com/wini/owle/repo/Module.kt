package com.wini.owle.repo

import com.wini.owle.repo.environs.PackageRepoRepository
import com.wini.owle.repo.environs.ToolPackageDependencyRepository
import com.wini.owle.repo.environs.VersionSpecRepository
import com.wini.owle.repo.runs.*
import com.wini.owle.repo.steps.*
import com.wini.owle.repo.transforms.*
import com.wini.owle.repo.typing.*
import com.wini.owle.repo.user.PaymentMethodRepository
import com.wini.owle.repo.user.TagRelationRepository
import com.wini.owle.repo.user.TagRepository
import com.wini.owle.repo.user.UserRepository
import com.wini.owle.repo.workflows.*
import org.koin.dsl.module


val repositoryModule = module {
    single<PackageRepoRepository> { PackageRepoRepository() }
    single<VersionSpecRepository> { VersionSpecRepository() }
    single<TypeDefinitionRepository> { TypeDefinitionRepository() }
    single<TypeDeclarationRepository> { TypeDeclarationRepository() }
    single<TypeParameterRepository> { TypeParameterRepository() }
    single<TypeBoundRepository> { TypeBoundRepository() }
    single<TypeVarianceRepository> { TypeVarianceRepository() }
    single<TypeSchemaRefRepository> { TypeSchemaRefRepository() }
    single<WorkflowFolderRepository> { WorkflowFolderRepository() }
    single<WorkflowRepository> { WorkflowRepository() }
    single<ToolRepository> { ToolRepository() }
    single<ToolParamRepository> { ToolParamRepository() }
    single<PhaseRepository> { PhaseRepository() }
    single<StepRepository> { StepRepository() }
    single<DataPointRepository> { DataPointRepository() }
    single<TransformationRepository> { TransformationRepository() }
    single<TransformationArgRepository> { TransformationArgRepository() }
    single<AppliedTransformRepository> { AppliedTransformRepository() }
    single<AppliedTransformArgRepository> { AppliedTransformArgRepository() }
    single<ErrorWatchRepository> { ErrorWatchRepository() }
    single<ErrorWatchFnRepository> { ErrorWatchFnRepository() }
    single<InquisitionRepository> { InquisitionRepository() }
    single<RevisionRepository> { RevisionRepository() }
    single<WorkflowJunctionRepository> { WorkflowJunctionRepository() }
    single<PreReqInjectionRepository> { PreReqInjectionRepository() }
    single<RevisionRepository> { RevisionRepository() }
    single<ProvisionRepository> { ProvisionRepository() }
    single<UserRepository> { UserRepository() }
    single<PaymentMethodRepository> { PaymentMethodRepository() }
    single<TagRepository> { TagRepository() }
    single<WorkflowExecutionRepository> { WorkflowExecutionRepository() }
    single<StepExecutionRepository> { StepExecutionRepository() }
    single<ExecutionLogRepository> { ExecutionLogRepository() }
    single<BenchmarkingDataRepository> { BenchmarkingDataRepository() }
    single<StepBenchmarkingDataRepository> { StepBenchmarkingDataRepository() }
    single<TestCaseRepository> { TestCaseRepository() }
    single<TestInputRepository> { TestInputRepository() }
    single<TestExpectedOutcomeRepository> { TestExpectedOutcomeRepository() }
    single<TestResultRepository> { TestResultRepository() }
    single<ProvisionRepository> { ProvisionRepository() }
    single<TypeUnionMemberRepository> { TypeUnionMemberRepository() }
    single<TransformTargetTypeRepository> { TransformTargetTypeRepository() }
    single<TagRelationRepository> { TagRelationRepository() }
    single<ToolPackageDependencyRepository> { ToolPackageDependencyRepository() }
    single<ProvisionDataPointRepository> { ProvisionDataPointRepository() }
}