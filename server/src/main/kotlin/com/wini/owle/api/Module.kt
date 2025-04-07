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
import com.wini.owle.api.workflows.PhaseApi
import com.wini.owle.api.workflows.WorkflowApi
import com.wini.owle.api.workflows.WorkflowFolderApi
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
import org.koin.dsl.module


val apiModule = module {

    single<LongIdEndpointImpl<PackageRepoDto>> { PackageRepoApi(get()) }
    single<LongIdEndpointImpl<PackageDto>> { PackageApi(get()) }
    single<LongIdEndpointImpl<VersionSpecDto>> { VersionSpecApi(get()) }
    single<LongIdEndpointImpl<BenchmarkingDataDto>> { BenchmarkingDataApi(get()) }
    single<LongIdEndpointImpl<ExecutionLogDto>> { ExecutionLogApi(get()) }
    single<LongIdEndpointImpl<StepExecutionDto>> { StepExecutionApi(get()) }
    single<LongIdEndpointImpl<TestCaseDto>> { TestCaseApi(get()) }
    single<LongIdEndpointImpl<TestExpectedOutcomeDto>> { TestExpectedOutcomeApi(get()) }
    single<LongIdEndpointImpl<TestInputDto>> { TestInputApi(get()) }
    single<LongIdEndpointImpl<TestResultDto>> { TestResultApi(get()) }
    single<LongIdEndpointImpl<WorkflowExecutionDto>> { WorkflowExecutionApi(get()) }
    single<LongIdEndpointImpl<ErrorWatchFnDto>> { ErrorWatchFnApi(get()) }
    single<LongIdEndpointImpl<ErrorWatchDto>> { ErrorWatchApi(get()) }
    single<LongIdEndpointImpl<InquisitionDto>> { InquisitionApi(get()) }
    single<LongIdEndpointImpl<WorkflowJunctionDto>> { WorkflowJunctionApi(get()) }
    single<LongIdEndpointImpl<AppliedTransformArgDto>> { AppliedTransformArgApi(get()) }
    single<LongIdEndpointImpl<AppliedTransformDto>> { AppliedTransformApi(get()) }
    single<LongIdEndpointImpl<TransformationArgDto>> { TransformationArgApi(get()) }
    single<LongIdEndpointImpl<TransformationDto>> { TransformationApi(get()) }
    single<LongIdEndpointImpl<TypeDeclarationDto>> { TypeDeclarationApi(get()) }
    single<LongIdEndpointImpl<TypeDefinitionDto>> { TypeDefinitionApi(get()) }
    single<LongIdEndpointImpl<TypeParameterDto>> { TypeParameterApi(get()) }
    single<LongIdEndpointImpl<TypeSchemaRefDto>> { TypeSchemaRefApi(get()) }
    single<LongIdEndpointImpl<TypeVarianceDto>> { TypeVarianceApi(get()) }
    single<LongIdEndpointImpl<PaymentMethodDto>> { PaymentMethodApi(get()) }
    single<LongIdEndpointImpl<TagDto>> { TagApi(get()) }
    single<LongIdEndpointImpl<UserDto>> { UserApi(get()) }
    single<LongIdEndpointImpl<PhaseDto>> { PhaseApi(get()) }
    single<LongIdEndpointImpl<StepDto>> { StepApi(get()) }
    single<LongIdEndpointImpl<ToolParamDto>> { ToolParamApi(get()) }
    single<LongIdEndpointImpl<ToolDto>> { ToolApi(get()) }
    single<LongIdEndpointImpl<WorkflowFolderDto>> { WorkflowFolderApi(get()) }
    single<LongIdEndpointImpl<WorkflowDto>> { WorkflowApi(get()) }
    single<CompositeIdEndpointImpl<ProvisionDataPointDto>> { ProvisionDataPointApi(get()) }
    single<CompositeIdEndpointImpl<TypeUnionMemberDto>> { TypeUnionMemberApi(get()) }
    single<CompositeIdEndpointImpl<TagRelationDto>> { TagRelationApi(get()) }
    single<CompositeIdEndpointImpl<TransformationTargetTypeDto>> { TransformTargetTypeApi(get()) }
    single<CompositeIdEndpointImpl<ToolPackageDependencyDto>> { ToolPackageDependencyApi(get()) }
}