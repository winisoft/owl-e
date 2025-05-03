package com.wini.owle.api

import com.wini.owle.api.environs.PyPackageApi
import com.wini.owle.api.environs.PyPackageRepoApi
import com.wini.owle.api.environs.ToolPyPackDepApi
import com.wini.owle.api.environs.VersionSpecApi
import com.wini.owle.api.runs.step.StepRunApi
import com.wini.owle.api.runs.step.StepRunLogLineApi
import com.wini.owle.api.runs.test.BenchmarkReportApi
import com.wini.owle.api.runs.test.TestCaseApi
import com.wini.owle.api.runs.test.TestExpectationApi
import com.wini.owle.api.runs.test.TestInputApi
import com.wini.owle.api.runs.workflow.WorkflowExecutionApi
import com.wini.owle.api.runs.workflow.WorkflowTestResultApi
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
import com.wini.owle.dll.dllModule
import com.wini.owle.dto.environs.PyPackage
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.environs.ToolPyPackDep
import com.wini.owle.dto.environs.VersionSpec
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.dto.runs.step.StepRunLogLine
import com.wini.owle.dto.runs.test.*
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.dto.steps.*
import com.wini.owle.dto.transforms.*
import com.wini.owle.dto.typing.*
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.dto.user.tags.Tag
import com.wini.owle.dto.user.tags.TagRelation
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.*
import com.wini.owle.entity.composteid.compositeIdFactoryModule
import com.wini.owle.validators.validatorsModule
import org.koin.dsl.module


val apiModule = module {
    includes(validatorsModule, dllModule, compositeIdFactoryModule)

    single<LongIdEndpointImpl<PyPackageRepo>> { PyPackageRepoApi(get(), get()) }
    single<LongIdEndpointImpl<PyPackage>> { PyPackageApi(get(), get()) }
    single<LongIdEndpointImpl<VersionSpec>> { VersionSpecApi(get(), get()) }
    single<LongIdEndpointImpl<BenchmarkReport>> { BenchmarkReportApi(get(), get()) }
    single<LongIdEndpointImpl<StepRunLogLine>> { StepRunLogLineApi(get(), get()) }
    single<LongIdEndpointImpl<StepExecution>> { StepRunApi(get(), get()) }
    single<LongIdEndpointImpl<TestCase>> { TestCaseApi(get(), get()) }
    single<LongIdEndpointImpl<TestExpectation>> { TestExpectationApi(get(), get()) }
    single<LongIdEndpointImpl<TestInput>> { TestInputApi(get(), get()) }
    single<LongIdEndpointImpl<WorkflowTestResult>> { WorkflowTestResultApi(get(), get()) }
    single<LongIdEndpointImpl<WorkflowExecution>> { WorkflowExecutionApi(get(), get()) }
    single<LongIdEndpointImpl<ErrorWatchFn>> { ErrorWatchFnApi(get(), get()) }
    single<LongIdEndpointImpl<ErrorWatch>> { ErrorWatchApi(get(), get()) }
    single<LongIdEndpointImpl<Inquisition>> { InquisitionApi(get(), get()) }
    single<LongIdEndpointImpl<WorkflowJunction>> { WorkflowJunctionApi(get(), get()) }
    single<LongIdEndpointImpl<AppliedTransformArg>> { AppliedTransformArgApi(get(), get()) }
    single<LongIdEndpointImpl<AppliedTransform>> { AppliedTransformApi(get(), get()) }
    single<LongIdEndpointImpl<TransformArg>> { TransformationArgApi(get(), get()) }
    single<LongIdEndpointImpl<Transform>> { TransformationApi(get(), get()) }
    single<LongIdEndpointImpl<TypeDeclaration>> { TypeDeclarationApi(get(), get()) }
    single<LongIdEndpointImpl<TypeDefinition>> { TypeDefinitionApi(get(), get()) }
    single<LongIdEndpointImpl<TypeParameter>> { TypeParameterApi(get(), get()) }
    single<LongIdEndpointImpl<TypeSchemaRef>> { TypeSchemaRefApi(get(), get()) }
    single<LongIdEndpointImpl<TypeVariance>> { TypeVarianceApi(get(), get()) }
    single<LongIdEndpointImpl<PaymentMethod>> { PaymentMethodApi(get(), get()) }
    single<LongIdEndpointImpl<Tag>> { TagApi(get(), get()) }
    single<LongIdEndpointImpl<User>> { UserApi(get(), get()) }
    single<LongIdEndpointImpl<Phase>> { PhaseApi(get(), get()) }
    single<LongIdEndpointImpl<Step>> { StepApi(get(), get()) }
    single<LongIdEndpointImpl<ToolParam>> { ToolParamApi(get(), get()) }
    single<LongIdEndpointImpl<Tool>> { ToolApi(get(), get()) }
    single<LongIdEndpointImpl<WorkflowFolder>> { WorkflowFolderApi(get(), get()) }
    single<LongIdEndpointImpl<Workflow>> { WorkflowApi(get(), get()) }
    single<CompositeIdEndpointImpl<ProvisionDataPoint>> { ProvisionDataPointApi(get(), get(), get()) }
    single<CompositeIdEndpointImpl<TypeUnionMember>> { TypeUnionMemberApi(get(), get(), get()) }
    single<CompositeIdEndpointImpl<TagRelation>> { TagRelationApi(get(), get(), get()) }
    single<CompositeIdEndpointImpl<TransformTargetType>> { TransformTargetTypeApi(get(), get(), get()) }
    single<CompositeIdEndpointImpl<ToolPyPackDep>> { ToolPyPackDepApi(get(), get(), get()) }
    single<ProvisionDataPointApi> { ProvisionDataPointApi(get(), get(), get()) }
    single<TypeUnionMemberApi> { TypeUnionMemberApi(get(), get(), get()) }
    single<TagRelationApi> { TagRelationApi(get(), get(), get()) }
    single<TransformTargetTypeApi> { TransformTargetTypeApi(get(), get(), get()) }
    single<ToolPyPackDepApi> { ToolPyPackDepApi(get(), get(), get()) }

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