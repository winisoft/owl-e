package com.wini.owle.services

import com.wini.owle.services.runs.BenchmarkingDataService
import com.wini.owle.services.runs.ExecutionLogService
import com.wini.owle.services.runs.StepExecutionService
import com.wini.owle.services.runs.TestCaseService
import com.wini.owle.services.environs.VersionSpecService
import com.wini.owle.mapper.mapperModule
import com.wini.owle.repo.idFactoryModule
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

    includes(repositoryModule, mapperModule, idFactoryModule)

    single<PackageRepoService> { PackageRepoService(get(), get(), get()) }
    single<PackageService> { PackageService(get(), get(), get()) }
    single<VersionSpecService> { VersionSpecService(get(), get(), get()) }
    single<BenchmarkingDataService> { BenchmarkingDataService(get(), get(), get()) }
    single<ExecutionLogService> { ExecutionLogService(get(), get(), get()) }
    single<StepExecutionService> { StepExecutionService(get(), get(), get()) }
    single<TestCaseService> { TestCaseService(get(), get(), get()) }
    single<TestExpectedOutcomeService> { TestExpectedOutcomeService(get(), get(), get()) }
    single<TestInputService> { TestInputService(get(), get(), get()) }
    single<TestResultService> { TestResultService(get(), get(), get()) }
    single<WorkflowExecutionService> { WorkflowExecutionService(get(), get(), get()) }
    single<ErrorWatchFnService> { ErrorWatchFnService(get(), get(), get()) }
    single<ErrorWatchService> { ErrorWatchService(get(), get(), get()) }
    single<InquisitionService> { InquisitionService(get(), get(), get()) }
    single<WorkflowJunctionService> { WorkflowJunctionService(get(), get(), get()) }
    single<AppliedTransformArgService> { AppliedTransformArgService(get(), get(), get()) }
    single<AppliedTransformService> { AppliedTransformService(get(), get(), get()) }
    single<TransformationArgService> { TransformationArgService(get(), get(), get()) }
    single<TransformationService> { TransformationService(get(), get(), get()) }
    single<TypeDeclarationService> { TypeDeclarationService(get(), get(), get()) }
    single<TypeDefinitionService> { TypeDefinitionService(get(), get(), get()) }
    single<TypeParameterService> { TypeParameterService(get(), get(), get()) }
    single<TypeSchemaRefService> { TypeSchemaRefService(get(), get(), get()) }
    single<TypeVarianceService> { TypeVarianceService(get(), get(), get()) }
    single<PaymentMethodService> { PaymentMethodService(get(), get(), get()) }
    single<TagService> { TagService(get(), get(), get()) }
    single<UserService> { UserService(get(), get(), get()) }
    single<PhaseService> { PhaseService(get(), get(), get()) }
    single<StepService> { StepService(get(), get(), get()) }
    single<ToolParamService> { ToolParamService(get(), get(), get()) }
    single<ToolService> { ToolService(get(), get(), get()) }
    single<WorkflowFolderService> { WorkflowFolderService(get(), get(), get()) }
    single<WorkflowService> { WorkflowService(get(), get(), get()) }
    single<TypeUnionMemberService> { TypeUnionMemberService(get(), get(), get()) }
    single<TagRelationService> { TagRelationService(get(), get(), get()) }
    single<TransformTargetTypeService> { TransformTargetTypeService(get(), get(), get()) }
    single<ToolPackageDependencyService> { ToolPackageDependencyService(get(), get(), get()) }
    single<ProvisionDataPointService> { ProvisionDataPointService(get(), get(), get()) }
    single<PreReqInjectionService> { PreReqInjectionService(get(), get(), get()) }
}