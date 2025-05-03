package com.wini.owle.validators

import com.wini.owle.dto.environs.*
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.dto.runs.step.StepRunLogLine
import com.wini.owle.dto.runs.test.*
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.dto.steps.*
import com.wini.owle.dto.transforms.*
import com.wini.owle.dto.typing.*
import com.wini.owle.dto.user.*
import com.wini.owle.dto.user.tags.Tag
import com.wini.owle.dto.user.tags.TagRelation
import com.wini.owle.dto.workflows.*

import com.wini.owle.validators.environs.*
import com.wini.owle.validators.runs.*
import com.wini.owle.validators.steps.*
import com.wini.owle.validators.transforms.*
import com.wini.owle.validators.typing.*
import com.wini.owle.validators.user.*
import com.wini.owle.validators.workflows.*


import org.koin.dsl.module

val validatorsModule = module {

    single<DtoValidator<PyPackage>> { PackageValidator(get(), ) }
    single<DtoValidator<PyPackageRepo>> { PackageRepoValidator() }
    single<DtoValidator<ToolPyPackDep>> { ToolPackageDependencyValidator() }
    single<DtoValidator<VersionSpec>> { VersionSpecValidator() }

    single<DtoValidator<BenchmarkReport>> { BenchmarkingDataValidator(get()) }
    single<DtoValidator<StepRunLogLine>> { ExecutionLogValidator(get(),) }
    single<DtoValidator<StepExecution>> { StepExecutionValidator(get(), get()) }
    single<DtoValidator<TestCase>> { TestCaseValidator(get(), get(), get(), get(), get(), get())  }
    single<DtoValidator<TestExpectation>> { TestExpectedOutcomeValidator(get(),) }
    single<DtoValidator<TestInput>> { TestInputValidator(get(),) }
    single<DtoValidator<WorkflowTestResult>> { TestResultValidator(get(), get()) }
    single<DtoValidator<WorkflowExecution>> { WorkflowExecutionValidator(get(), get(), get()) }

    single<DtoValidator<ErrorWatch>> { ErrorWatchValidator(get()) }
    single<DtoValidator<ErrorWatchFn>> { ErrorWatchFnValidator() }
    single<DtoValidator<Inquisition>> { InquisitionValidator(get(), get()) }

    single<DtoValidator<InitialDataPointInjector>> { PreReqInjectionValidator(get(), get()) }
    single<DtoValidator<Provision>> { ProvisionValidator(get(), get(), get()) }
    single<DtoValidator<ProvisionDataPoint>> { ProvisionDataPointValidator() }
    single<DtoValidator<DataPointSave>> { RevisionValidator(get(), get()) }
    single<DtoValidator<Step>> { StepValidator(get(), get(), get()) }
    single<DtoValidator<Tool>> { ToolValidator(get()) }
    single<DtoValidator<ToolParam>> { ToolParamValidator(get(), get()) }
    single<DtoValidator<WorkflowJunction>> { WorkflowJunctionValidator(get(), get(), get()) }

    single<DtoValidator<AppliedTransform>> { AppliedTransformValidator(get(), get(), get()) }
    single<DtoValidator<AppliedTransformArg>> { AppliedTransformArgValidator(get(), get()) }
    single<DtoValidator<Transform>> { TransformationValidator(get()) }
    single<DtoValidator<TransformArg>> { TransformationArgValidator(get(), get()) }
    single<DtoValidator<TransformTargetType>> { TransformationTargetTypeValidator() }

    single<DtoValidator<TypeBound>> { TypeBoundsValidator(get()) }
    single<DtoValidator<TypeDeclaration>> { TypeDeclarationValidator(get()) }
    single<DtoValidator<TypeDefinition>> { TypeDefinitionValidator(get()) }
    single<DtoValidator<TypeParameter>> { TypeParameterValidator(get()) }
    single<DtoValidator<TypeSchemaRef>> { TypeSchemaRefValidator(get()) }
    single<DtoValidator<TypeUnionMember>> { TypeUnionMemberValidator() }
    single<DtoValidator<TypeVariance>> { TypeVarianceValidator(get()) }

    single<DtoValidator<PaymentMethod>> { PaymentMethodValidator(get()) }
    single<DtoValidator<Tag>> { TagValidator(get()) }
    single<DtoValidator<TagRelation>> { TagRelationValidator() }
    single<DtoValidator<User>> { UserValidator() }
    single<DtoValidator<DataPoint>> { DataPointValidator(get(), get()) }
    single<DtoValidator<Phase>> { PhaseValidator(get()) }
    single<DtoValidator<WorkflowFolder>> { WorkflowFolderValidator() }
    single<DtoValidator<Workflow>> { WorkflowValidator() }


    single<PackageValidator> { PackageValidator(get(), ) }
    single<PackageRepoValidator> { PackageRepoValidator() }
    single<ToolPackageDependencyValidator> { ToolPackageDependencyValidator() }
    single<VersionSpecValidator> { VersionSpecValidator() }

    single<BenchmarkingDataValidator> { BenchmarkingDataValidator(get()) }
    single<ExecutionLogValidator> { ExecutionLogValidator(get(),) }
    single<StepExecutionValidator> { StepExecutionValidator(get(), get()) }
    single<TestCaseValidator> { TestCaseValidator(get(), get(), get(), get(), get(), get()) }
    single<TestExpectedOutcomeValidator> { TestExpectedOutcomeValidator(get()) }
    single<TestInputValidator> { TestInputValidator(get(),) }
    single<TestResultValidator> { TestResultValidator(get(), get()) }
    single<WorkflowExecutionValidator> { WorkflowExecutionValidator(get(), get(), get()) }

    single<ErrorWatchValidator> { ErrorWatchValidator(get()) }
    single<ErrorWatchFnValidator> { ErrorWatchFnValidator() }
    single<InquisitionValidator> { InquisitionValidator(get(), get()) }

    single<PreReqInjectionValidator> { PreReqInjectionValidator(get(), get()) }
    single<ProvisionValidator> { ProvisionValidator(get(), get(), get()) }
    single<ProvisionDataPointValidator> { ProvisionDataPointValidator() }
    single<RevisionValidator> { RevisionValidator(get(), get()) }
    single<StepValidator> { StepValidator(get(), get(), get()) }
    single<ToolValidator> { ToolValidator(get()) }
    single<ToolParamValidator> { ToolParamValidator(get(), get()) }
    single<WorkflowJunctionValidator> { WorkflowJunctionValidator(get(), get(), get()) }

    single<AppliedTransformValidator> { AppliedTransformValidator(get(), get(), get()) }
    single<AppliedTransformArgValidator> { AppliedTransformArgValidator(get(), get()) }
    single<TransformationValidator> { TransformationValidator(get()) }
    single<TransformationArgValidator> { TransformationArgValidator(get(), get()) }
    single<TransformationTargetTypeValidator> { TransformationTargetTypeValidator() }

    single<TypeBoundsValidator> { TypeBoundsValidator(get()) }
    single<TypeDeclarationValidator> { TypeDeclarationValidator(get()) }
    single<TypeDefinitionValidator> { TypeDefinitionValidator(get()) }
    single<TypeParameterValidator> { TypeParameterValidator(get()) }
    single<TypeSchemaRefValidator> { TypeSchemaRefValidator(get()) }
    single<TypeUnionMemberValidator> { TypeUnionMemberValidator() }
    single<TypeVarianceValidator> { TypeVarianceValidator(get()) }

    single<PaymentMethodValidator> { PaymentMethodValidator(get()) }
    single<TagValidator> { TagValidator(get()) }
    single<TagRelationValidator> { TagRelationValidator() }
    single<UserValidator> { UserValidator() }
    single<DataPointValidator> { DataPointValidator(get(), get()) }
    single<PhaseValidator> { PhaseValidator(get()) }
    single<WorkflowFolderValidator> { WorkflowFolderValidator() }
    single<WorkflowValidator> { WorkflowValidator() }
}





