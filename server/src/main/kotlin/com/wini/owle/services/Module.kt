package com.wini.owle.services

import com.wini.owle.services.runs.BenchmarkingDataService
import com.wini.owle.services.runs.ExecutionLogService
import com.wini.owle.services.runs.StepExecutionService
import com.wini.owle.services.runs.TestCaseService
import com.wini.owle.services.environs.VersionSpecService
import com.wini.owle.mapper.mapperModule
import com.wini.owle.repo.repositoryModule
import com.wini.owle.services.environs.PackageRepoService
import com.wini.owle.services.environs.PackageService
import com.wini.owle.services.environs.ToolPackageDependencyService
import com.wini.owle.services.runs.TestExpectedOutcomeService
import com.wini.owle.services.runs.TestInputService
import com.wini.owle.services.runs.TestResultService
import com.wini.owle.services.runs.WorkflowExecutionService
import com.wini.owle.services.steps.*
import com.wini.owle.services.transforms.*
import com.wini.owle.services.typing.*
import com.wini.owle.services.user.PaymentMethodService
import com.wini.owle.services.user.TagRelationService
import com.wini.owle.services.user.TagService
import com.wini.owle.services.user.UserService
import com.wini.owle.services.workflows.*
import org.jetbrains.exposed.dao.id.CompositeID
import org.koin.dsl.module


val serviceModule = module {
    includes(repositoryModule, mapperModule)
    single<PackageRepoService> { PackageRepoService(get(), get()) }
    single<PackageService> { PackageService(get(), get()) }
    single<VersionSpecService> { VersionSpecService(get(), get()) }
    single<BenchmarkingDataService> { BenchmarkingDataService(get(), get()) }
    single<ExecutionLogService> { ExecutionLogService(get(), get()) }
    single<StepExecutionService> { StepExecutionService(get(), get()) }
    single<TestCaseService> { TestCaseService(get(), get()) }
    single<TestExpectedOutcomeService> { TestExpectedOutcomeService(get(), get()) }
    single<TestInputService> { TestInputService(get(), get()) }
    single<TestResultService> { TestResultService(get(), get()) }
    single<WorkflowExecutionService> { WorkflowExecutionService(get(), get()) }
    single<ErrorWatchFnService> { ErrorWatchFnService(get(), get()) }
    single<ErrorWatchService> { ErrorWatchService(get(), get()) }
    single<InquisitionService> { InquisitionService(get(), get()) }
    single<WorkflowJunctionService> { WorkflowJunctionService(get(), get()) }
    single<AppliedTransformArgService> { AppliedTransformArgService(get(), get()) }
    single<AppliedTransformService> { AppliedTransformService(get(), get()) }
    single<TransformationArgService> { TransformationArgService(get(), get()) }
    single<TransformationService> { TransformationService(get(), get()) }
    single<TypeDeclarationService> { TypeDeclarationService(get(), get()) }
    single<TypeDefinitionService> { TypeDefinitionService(get(), get()) }
    single<TypeParameterService> { TypeParameterService(get(), get()) }
    single<TypeSchemaRefService> { TypeSchemaRefService(get(), get()) }
    single<TypeVarianceService> { TypeVarianceService(get(), get()) }
    single<PaymentMethodService> { PaymentMethodService(get(), get()) }
    single<TagService> { TagService(get(), get()) }
    single<UserService> { UserService(get(), get()) }
    single<PhaseService> { PhaseService(get(), get()) }
    single<StepService> { StepService(get(), get()) }
    single<ToolParamService> { ToolParamService(get(), get()) }
    single<ToolService> { ToolService(get(), get()) }
    single<WorkflowFolderService> { WorkflowFolderService(get(), get()) }
    single<WorkflowService> { WorkflowService(get(), get()) }
    single<TypeUnionMemberService> { TypeUnionMemberService(get(), get()) }
    single<TagRelationService> { TagRelationService(get(), get()) }
    single<TransformTargetTypeService> { TransformTargetTypeService(get(), get()) }
    single<ToolPackageDependencyService> { ToolPackageDependencyService(get(), get()) }
    single<ProvisionDataPointService> { ProvisionDataPointService(get(), get()) }
}