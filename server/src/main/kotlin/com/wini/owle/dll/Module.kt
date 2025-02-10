package com.wini.owle.dll

import com.wini.owle.dll.user.PaymentMethodDll
import com.wini.owle.dll.workflows.PhaseDll
import com.wini.owle.dll.transforms.TransformationArgDll
import com.wini.owle.dll.typing.TypeDefinitionDll
import com.wini.owle.dll.typing.TypeSchemaRefDll
import com.wini.owle.dll.typing.TypeVarianceDll
import com.wini.owle.dll.environs.PackageDll
import com.wini.owle.dll.environs.PackageRepoDll
import com.wini.owle.dll.environs.ToolPackageDependencyDll
import com.wini.owle.dll.environs.VersionSpecDll
import com.wini.owle.dll.runs.*
import com.wini.owle.dll.steps.*
import com.wini.owle.dll.transforms.AppliedTransformArgDll
import com.wini.owle.dll.transforms.AppliedTransformDll
import com.wini.owle.dll.transforms.TransformTargetTypeDll
import com.wini.owle.dll.transforms.TransformationDll
import com.wini.owle.dll.typing.TypeDeclarationDll
import com.wini.owle.dll.typing.TypeParameterDll
import com.wini.owle.dll.typing.TypeUnionMemberDll
import com.wini.owle.dll.user.TagRelationDll
import com.wini.owle.dll.user.TagDll
import com.wini.owle.dll.user.UserDll
import com.wini.owle.dll.workflows.StepDll
import com.wini.owle.dll.workflows.ToolDll
import com.wini.owle.dll.workflows.ToolParamDll
import com.wini.owle.dll.workflows.WorkflowDll
import com.wini.owle.dll.workflows.WorkflowFolderDll
import com.wini.owle.dto.*
import com.wini.owle.services.serviceModule
import org.jetbrains.exposed.dao.id.CompositeID
import org.koin.dsl.module


val dllModule = module {
    includes(serviceModule)

    single<PackageRepoDll> { PackageRepoDll(get()) }
    single<PackageDll> { PackageDll(get()) }
    single<VersionSpecDll> { VersionSpecDll(get()) }
    single<BenchmarkingDataDll> { BenchmarkingDataDll(get()) }
    single<ExecutionLogDll> { ExecutionLogDll(get()) }
    single<StepExecutionDll> { StepExecutionDll(get()) }
    single<TestCaseDll> { TestCaseDll(get()) }
    single<TestExpectedOutcomeDll> { TestExpectedOutcomeDll(get()) }
    single<TestInputDll> { TestInputDll(get()) }
    single<TestResultDll> { TestResultDll(get()) }
    single<WorkflowExecutionDll> { WorkflowExecutionDll(get()) }
    single<ErrorWatchFnDll> { ErrorWatchFnDll(get()) }
    single<ErrorWatchDll> { ErrorWatchDll(get()) }
    single<InquisitionDll> { InquisitionDll(get()) }
    single<WorkflowJunctionDll> { WorkflowJunctionDll(get()) }
    single<AppliedTransformArgDll> { AppliedTransformArgDll(get()) }
    single<AppliedTransformDll> { AppliedTransformDll(get()) }
    single<TransformationArgDll> { TransformationArgDll(get()) }
    single<TransformationDll> { TransformationDll(get()) }
    single<TypeDeclarationDll> { TypeDeclarationDll(get(), get(), get()) }
    single<TypeDefinitionDll> { TypeDefinitionDll(get()) }
    single<TypeParameterDll> { TypeParameterDll(get()) }
    single<TypeSchemaRefDll> { TypeSchemaRefDll(get()) }
    single<TypeVarianceDll> { TypeVarianceDll(get()) }
    single<PaymentMethodDll> { PaymentMethodDll(get()) }
    single<TagDll> { TagDll(get()) }
    single<UserDll> { UserDll(get()) }
    single<PhaseDll> { PhaseDll(get(), get()) }
    single<StepDll> { StepDll(get(), get()) }
    single<ToolParamDll> { ToolParamDll(get()) }
    single<ToolDll> { ToolDll(get()) }
    single<WorkflowFolderDll> { WorkflowFolderDll(get()) }
    single<WorkflowDll> { WorkflowDll(get()) }
    single<TagRelationDll> { TagRelationDll(get()) }
    single<ToolPackageDependencyDll> { ToolPackageDependencyDll(get()) }
    single<TypeUnionMemberDll> { TypeUnionMemberDll(get()) }
    single<TransformTargetTypeDll> { TransformTargetTypeDll(get()) }
    single<ProvisionDataPointDll> { ProvisionDataPointDll(get()) }
    single<PreReqInjectionDll> { PreReqInjectionDll(get()) }
}