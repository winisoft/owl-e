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
import com.wini.owle.dto.environs.PyPackage
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.environs.ToolPyPackDependency
import com.wini.owle.dto.environs.VersionSpec
import com.wini.owle.dto.runs.step.StepRun
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
import com.wini.owle.services.serviceModule
import org.jetbrains.exposed.dao.id.CompositeID
import org.koin.dsl.module


val dllModule = module {
    includes(serviceModule)

    single<DLL<Long, PyPackageRepo>> { PackageRepoDll(get()) }
    single<DLL<Long, PyPackage>> { PackageDll(get()) }
    single<DLL<Long, VersionSpec>> { VersionSpecDll(get()) }
    single<DLL<Long, BenchmarkReport>> { BenchmarkingDataDll(get()) }
    single<DLL<Long, StepRunLogLine>> { ExecutionLogDll(get()) }
    single<DLL<Long, StepRun>> { StepExecutionDll(get()) }
    single<DLL<Long, TestCase>> { TestCaseDll(get()) }
    single<DLL<Long, TestExpectation>> { TestExpectedOutcomeDll(get()) }
    single<DLL<Long, TestInput>> { TestInputDll(get()) }
    single<DLL<Long, WorkflowTestResult>> { TestResultDll(get()) }
    single<DLL<Long, WorkflowExecution>> { WorkflowExecutionDll(get()) }
    single<DLL<Long, ErrorWatchFn>> { ErrorWatchFnDll(get()) }
    single<DLL<Long, ErrorWatch>> { ErrorWatchDll(get()) }
    single<DLL<Long, Inquisition>> { InquisitionDll(get()) }
    single<DLL<Long, WorkflowJunction>> { WorkflowJunctionDll(get()) }
    single<DLL<Long, AppliedTransformArg>> { AppliedTransformArgDll(get()) }
    single<DLL<Long, AppliedTransform>> { AppliedTransformDll(get()) }
    single<DLL<Long, TransformArg>> { TransformationArgDll(get()) }
    single<DLL<Long, Transform>> { TransformationDll(get()) }
    single<DLL<Long, TypeDeclaration>> { TypeDeclarationDll(get(), get(), get()) }
    single<DLL<Long, TypeDefinition>> { TypeDefinitionDll(get()) }
    single<DLL<Long, TypeParameter>> { TypeParameterDll(get()) }
    single<DLL<Long, TypeSchemaRef>> { TypeSchemaRefDll(get()) }
    single<DLL<Long, TypeVariance>> { TypeVarianceDll(get()) }
    single<DLL<Long, PaymentMethod>> { PaymentMethodDll(get()) }
    single<DLL<Long, Tag>> { TagDll(get()) }
    single<DLL<Long, User>> { UserDll(get()) }
    single<DLL<Long, Phase>> { PhaseDll(get(), get()) }
    single<DLL<Long, Step>> { StepDll(get(), get()) }
    single<DLL<Long, ToolParam>> { ToolParamDll(get()) }
    single<DLL<Long, Tool>> { ToolDll(get()) }
    single<DLL<Long, WorkflowFolder>> { WorkflowFolderDll(get()) }
    single<DLL<Long, Workflow>> { WorkflowDll(get()) }
    single<DLL<CompositeID, TagRelation>> { TagRelationDll(get()) }
    single<DLL<CompositeID, ToolPyPackDependency>> { ToolPackageDependencyDll(get()) }
    single<DLL<CompositeID, TypeUnionMember>> { TypeUnionMemberDll(get()) }
    single<DLL<CompositeID, TransformTargetType>> { TransformTargetTypeDll(get()) }
    single<DLL<CompositeID, ProvisionDataPoint>> { ProvisionDataPointDll(get()) }
    single<DLL<Long, InitialDataPointInjector>> { PreReqInjectionDll(get()) }
}