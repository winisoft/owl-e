package com.wini.owle.dll

import com.wini.owle.dll.environs.PackageDll
import com.wini.owle.dll.environs.PackageRepoDll
import com.wini.owle.dll.environs.ToolPackageDependencyDll
import com.wini.owle.dll.environs.VersionSpecDll
import com.wini.owle.dll.runs.*
import com.wini.owle.dll.steps.*
import com.wini.owle.dll.transforms.*
import com.wini.owle.dll.typing.*
import com.wini.owle.dll.user.PaymentMethodDll
import com.wini.owle.dll.user.TagDll
import com.wini.owle.dll.user.TagRelationDll
import com.wini.owle.dll.user.UserDll
import com.wini.owle.dll.workflows.*
import com.wini.owle.dto.environs.PackageDto
import com.wini.owle.dto.environs.PackageRepoDto
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.dto.environs.VersionSpecDto
import com.wini.owle.dto.runs.*
import com.wini.owle.dto.steps.*
import com.wini.owle.dto.transforms.*
import com.wini.owle.dto.typing.*
import com.wini.owle.dto.user.PaymentMethodDto
import com.wini.owle.dto.user.TagDto
import com.wini.owle.dto.user.TagRelationDto
import com.wini.owle.dto.user.UserDto
import com.wini.owle.dto.workflows.*
import com.wini.owle.services.serviceModule
import org.jetbrains.exposed.dao.id.CompositeID
import org.koin.dsl.module


val dllModule = module {
    includes(serviceModule)

    single<DLL<Long, PackageRepoDto>> { PackageRepoDll(get()) }
    single<DLL<Long, PackageDto>> { PackageDll(get()) }
    single<DLL<Long, VersionSpecDto>> { VersionSpecDll(get()) }
    single<DLL<Long, BenchmarkingDataDto>> { BenchmarkingDataDll(get()) }
    single<DLL<Long, ExecutionLogDto>> { ExecutionLogDll(get()) }
    single<DLL<Long, StepExecutionDto>> { StepExecutionDll(get()) }
    single<DLL<Long, TestCaseDto>> { TestCaseDll(get()) }
    single<DLL<Long, TestExpectedOutcomeDto>> { TestExpectedOutcomeDll(get()) }
    single<DLL<Long, TestInputDto>> { TestInputDll(get()) }
    single<DLL<Long, TestResultDto>> { TestResultDll(get()) }
    single<DLL<Long, WorkflowExecutionDto>> { WorkflowExecutionDll(get()) }
    single<DLL<Long, ErrorWatchFnDto>> { ErrorWatchFnDll(get()) }
    single<DLL<Long, ErrorWatchDto>> { ErrorWatchDll(get()) }
    single<DLL<Long, InquisitionDto>> { InquisitionDll(get()) }
    single<DLL<Long, WorkflowJunctionDto>> { WorkflowJunctionDll(get()) }
    single<DLL<Long, AppliedTransformArgDto>> { AppliedTransformArgDll(get()) }
    single<DLL<Long, AppliedTransformDto>> { AppliedTransformDll(get()) }
    single<DLL<Long, TransformationArgDto>> { TransformationArgDll(get()) }
    single<DLL<Long, TransformationDto>> { TransformationDll(get()) }
    single<DLL<Long, TypeDeclarationDto>> { TypeDeclarationDll(get(), get(), get()) }
    single<DLL<Long, TypeDefinitionDto>> { TypeDefinitionDll(get()) }
    single<DLL<Long, TypeParameterDto>> { TypeParameterDll(get()) }
    single<DLL<Long, TypeSchemaRefDto>> { TypeSchemaRefDll(get()) }
    single<DLL<Long, TypeVarianceDto>> { TypeVarianceDll(get()) }
    single<DLL<Long, PaymentMethodDto>> { PaymentMethodDll(get()) }
    single<DLL<Long, TagDto>> { TagDll(get()) }
    single<DLL<Long, UserDto>> { UserDll(get()) }
    single<DLL<Long, PhaseDto>> { PhaseDll(get(), get()) }
    single<DLL<Long, StepDto>> { StepDll(get(), get()) }
    single<DLL<Long, ToolParamDto>> { ToolParamDll(get()) }
    single<DLL<Long, ToolDto>> { ToolDll(get()) }
    single<DLL<Long, WorkflowFolderDto>> { WorkflowFolderDll(get()) }
    single<DLL<Long, WorkflowDto>> { WorkflowDll(get()) }
    single<DLL<CompositeID, TagRelationDto>> { TagRelationDll(get()) }
    single<DLL<CompositeID, ToolPackageDependencyDto>> { ToolPackageDependencyDll(get()) }
    single<DLL<CompositeID, TypeUnionMemberDto>> { TypeUnionMemberDll(get()) }
    single<DLL<CompositeID, TransformationTargetTypeDto>> { TransformTargetTypeDll(get()) }
    single<DLL<CompositeID, ProvisionDataPointDto>> { ProvisionDataPointDll(get()) }
    single<DLL<Long, PreReqInjectionDto>> { PreReqInjectionDll(get()) }
}