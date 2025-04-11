package com.wini.owle.api

import com.wini.owle.api.environs.PackageApi
import com.wini.owle.api.environs.PackageRepoApi
import com.wini.owle.api.environs.ToolPackageDependencyApi
import com.wini.owle.api.environs.VersionSpecApi
import com.wini.owle.api.runs.*
import com.wini.owle.api.steps.*
import com.wini.owle.api.transforms.*
import com.wini.owle.api.typing.*
import com.wini.owle.api.user.PaymentMethodApi
import com.wini.owle.api.user.TagApi
import com.wini.owle.api.user.TagRelationApi
import com.wini.owle.api.user.UserApi
import com.wini.owle.validators.validatorsModule
import com.wini.owle.api.workflows.PhaseApi
import com.wini.owle.api.workflows.WorkflowApi
import com.wini.owle.api.workflows.WorkflowFolderApi
import com.wini.owle.dll.dllModule
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
import com.wini.owle.entity.composteid.compositeIdFactoryModule
import org.koin.dsl.module


val apiModule = module {
    includes(validatorsModule, dllModule, compositeIdFactoryModule)

    single<LongIdEndpointImpl<PackageRepoDto>> { PackageRepoApi(get(), get()) }
    single<LongIdEndpointImpl<PackageDto>> { PackageApi(get(), get()) }
    single<LongIdEndpointImpl<VersionSpecDto>> { VersionSpecApi(get(), get()) }
    single<LongIdEndpointImpl<BenchmarkingDataDto>> { BenchmarkingDataApi(get(), get()) }
    single<LongIdEndpointImpl<ExecutionLogDto>> { ExecutionLogApi(get(), get()) }
    single<LongIdEndpointImpl<StepExecutionDto>> { StepExecutionApi(get(), get()) }
    single<LongIdEndpointImpl<TestCaseDto>> { TestCaseApi(get(), get()) }
    single<LongIdEndpointImpl<TestExpectedOutcomeDto>> { TestExpectedOutcomeApi(get(), get()) }
    single<LongIdEndpointImpl<TestInputDto>> { TestInputApi(get(), get()) }
    single<LongIdEndpointImpl<TestResultDto>> { TestResultApi(get(), get()) }
    single<LongIdEndpointImpl<WorkflowExecutionDto>> { WorkflowExecutionApi(get(), get()) }
    single<LongIdEndpointImpl<ErrorWatchFnDto>> { ErrorWatchFnApi(get(), get()) }
    single<LongIdEndpointImpl<ErrorWatchDto>> { ErrorWatchApi(get(), get()) }
    single<LongIdEndpointImpl<InquisitionDto>> { InquisitionApi(get(), get()) }
    single<LongIdEndpointImpl<WorkflowJunctionDto>> { WorkflowJunctionApi(get(), get()) }
    single<LongIdEndpointImpl<AppliedTransformArgDto>> { AppliedTransformArgApi(get(), get()) }
    single<LongIdEndpointImpl<AppliedTransformDto>> { AppliedTransformApi(get(), get()) }
    single<LongIdEndpointImpl<TransformationArgDto>> { TransformationArgApi(get(), get()) }
    single<LongIdEndpointImpl<TransformationDto>> { TransformationApi(get(), get()) }
    single<LongIdEndpointImpl<TypeDeclarationDto>> { TypeDeclarationApi(get(), get()) }
    single<LongIdEndpointImpl<TypeDefinitionDto>> { TypeDefinitionApi(get(), get()) }
    single<LongIdEndpointImpl<TypeParameterDto>> { TypeParameterApi(get(), get()) }
    single<LongIdEndpointImpl<TypeSchemaRefDto>> { TypeSchemaRefApi(get(), get()) }
    single<LongIdEndpointImpl<TypeVarianceDto>> { TypeVarianceApi(get(), get()) }
    single<LongIdEndpointImpl<PaymentMethodDto>> { PaymentMethodApi(get(), get()) }
    single<LongIdEndpointImpl<TagDto>> { TagApi(get(), get()) }
    single<LongIdEndpointImpl<UserDto>> { UserApi(get(), get()) }
    single<LongIdEndpointImpl<PhaseDto>> { PhaseApi(get(), get()) }
    single<LongIdEndpointImpl<StepDto>> { StepApi(get(), get()) }
    single<LongIdEndpointImpl<ToolParamDto>> { ToolParamApi(get(), get()) }
    single<LongIdEndpointImpl<ToolDto>> { ToolApi(get(), get()) }
    single<LongIdEndpointImpl<WorkflowFolderDto>> { WorkflowFolderApi(get(), get()) }
    single<LongIdEndpointImpl<WorkflowDto>> { WorkflowApi(get(), get()) }
    single<CompositeIdEndpointImpl<ProvisionDataPointDto>> { ProvisionDataPointApi(get(), get(), get()) }
    single<CompositeIdEndpointImpl<TypeUnionMemberDto>> { TypeUnionMemberApi(get(), get(), get()) }
    single<CompositeIdEndpointImpl<TagRelationDto>> { TagRelationApi(get(), get(), get()) }
    single<CompositeIdEndpointImpl<TransformationTargetTypeDto>> { TransformTargetTypeApi(get(), get(), get()) }
    single<CompositeIdEndpointImpl<ToolPackageDependencyDto>> { ToolPackageDependencyApi(get(), get(), get()) }
    single<ProvisionDataPointApi> { ProvisionDataPointApi(get(), get(), get()) }
    single<TypeUnionMemberApi> { TypeUnionMemberApi(get(), get(), get()) }
    single<TagRelationApi> { TagRelationApi(get(), get(), get()) }
    single<TransformTargetTypeApi> { TransformTargetTypeApi(get(), get(), get()) }
    single<ToolPackageDependencyApi> { ToolPackageDependencyApi(get(), get(), get()) }

}

// single<PackageRepoApi> { PackageRepoApi(get(), get()) }
//    single<PackageApi> { PackageApi(get(), get()) }
//    single<VersionSpecApi> { VersionSpecApi(get(), get()) }
//    single<BenchmarkingDataApi> { BenchmarkingDataApi(get(), get()) }
//    single<ExecutionLogApi> { ExecutionLogApi(get(), get()) }
//    single<StepExecutionApi> { StepExecutionApi(get(), get()) }
//    single<TestCaseApi> { TestCaseApi(get(), get()) }
//    single<TestExpectedOutcomeApi> { TestExpectedOutcomeApi(get(), get()) }
//    single<TestInputApi> { TestInputApi(get(), get()) }
//    single<TestResultApi> { TestResultApi(get(), get()) }
//    single<WorkflowExecutionApi> { WorkflowExecutionApi(get(), get()) }
//    single<ErrorWatchFnApi> { ErrorWatchFnApi(get(), get()) }
//    single<ErrorWatchApi> { ErrorWatchApi(get(), get()) }
//    single<InquisitionApi> { InquisitionApi(get(), get()) }
//    single<WorkflowJunctionApi> { WorkflowJunctionApi(get(), get()) }
//    single<AppliedTransformArgApi> { AppliedTransformArgApi(get(), get()) }
//    single<AppliedTransformApi> { AppliedTransformApi(get(), get()) }
//    single<TransformationArgApi> { TransformationArgApi(get(), get()) }
//    single<TransformationApi> { TransformationApi(get(), get()) }
//    single<TypeDeclarationApi> { TypeDeclarationApi(get(), get()) }
//    single<TypeDefinitionApi> { TypeDefinitionApi(get(), get()) }
//    single<TypeParameterApi> { TypeParameterApi(get(), get()) }
//    single<TypeSchemaRefApi> { TypeSchemaRefApi(get(), get()) }
//    single<TypeVarianceApi> { TypeVarianceApi(get(), get()) }
//    single<PaymentMethodApi> { PaymentMethodApi(get(), get()) }
//    single<TagApi> { TagApi(get(), get()) }
//    single<UserApi> { UserApi(get(), get()) }
//    single<PhaseApi> { PhaseApi(get(), get()) }
//    single<StepApi> { StepApi(get(), get()) }
//    single<ToolParamApi> { ToolParamApi(get(), get()) }
//    single<ToolApi> { ToolApi(get(), get()) }
//    single<WorkflowFolderApi> { WorkflowFolderApi(get(), get()) }
//    single<WorkflowApi> { WorkflowApi(get(), get()) }
//    single<ProvisionDataPointApi> { ProvisionDataPointApi(get(), get(), get()) }
//    single<TypeUnionMemberApi> { TypeUnionMemberApi(get(), get(), get()) }
//    single<TagRelationApi> { TagRelationApi(get(), get(), get()) }
//    single<TransformTargetTypeApi> { TransformTargetTypeApi(get(), get(), get()) }
//    single<ToolPackageDependencyApi> { ToolPackageDependencyApi(get(), get(), get()) }