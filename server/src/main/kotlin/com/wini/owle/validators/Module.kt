package com.wini.owle.validators

import com.wini.owle.dto.environs.*
import com.wini.owle.dto.runs.*
import com.wini.owle.dto.steps.*
import com.wini.owle.dto.transforms.*
import com.wini.owle.dto.typing.*
import com.wini.owle.dto.user.*
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

    single<DtoValidator<PackageDto>> { PackageValidator(get(), ) }
    single<DtoValidator<PackageRepoDto>> { PackageRepoValidator() }
    single<DtoValidator<ToolPackageDependencyDto>> { ToolPackageDependencyValidator() }
    single<DtoValidator<VersionSpecDto>> { VersionSpecValidator() }

    single<DtoValidator<BenchmarkingDataDto>> { BenchmarkingDataValidator(get()) }
    single<DtoValidator<ExecutionLogDto>> { ExecutionLogValidator(get(),) }
    single<DtoValidator<StepExecutionDto>> { StepExecutionValidator(get(), get(), get()) }
    single<DtoValidator<TestCaseDto>> { TestCaseValidator(get(), get(), get()) }
    single<DtoValidator<TestExpectedOutcomeDto>> { TestExpectedOutcomeValidator(get(),) }
    single<DtoValidator<TestInputDto>> { TestInputValidator(get(),) }
    single<DtoValidator<TestResultDto>> { TestResultValidator(get(), get()) }
    single<DtoValidator<WorkflowExecutionDto>> { WorkflowExecutionValidator(get(), get(), get()) }

    single<DtoValidator<ErrorWatchDto>> { ErrorWatchValidator(get()) }
    single<DtoValidator<ErrorWatchFnDto>> { ErrorWatchFnValidator(get()) }
    single<DtoValidator<InquisitionDto>> { InquisitionValidator(get(), get()) }

    single<DtoValidator<PreReqInjectionDto>> { PreReqInjectionValidator(get(), get()) }
    single<DtoValidator<ProvisionDto>> { ProvisionValidator(get(), get(), get()) }
    single<DtoValidator<ProvisionDataPointDto>> { ProvisionDataPointValidator() }
    single<DtoValidator<RevisionDto>> { RevisionValidator(get(), get()) }
    single<DtoValidator<StepDto>> { StepValidator(get(), get(), get()) }
    single<DtoValidator<ToolDto>> { ToolValidator(get()) }
    single<DtoValidator<ToolParamDto>> { ToolParamValidator(get(), get()) }
    single<DtoValidator<WorkflowJunctionDto>> { WorkflowJunctionValidator(get(), get(), get()) }

    single<DtoValidator<AppliedTransformDto>> { AppliedTransformValidator(get(), get(), get()) }
    single<DtoValidator<AppliedTransformArgDto>> { AppliedTransformArgValidator(get(), get()) }
    single<DtoValidator<TransformationDto>> { TransformationValidator(get()) }
    single<DtoValidator<TransformationArgDto>> { TransformationArgValidator(get(), get()) }
    single<DtoValidator<TransformationTargetTypeDto>> { TransformationTargetTypeValidator() }

    single<DtoValidator<TypeBoundsDto>> { TypeBoundsValidator(get()) }
    single<DtoValidator<TypeDeclarationDto>> { TypeDeclarationValidator(get()) }
    single<DtoValidator<TypeDefinitionDto>> { TypeDefinitionValidator(get()) }
    single<DtoValidator<TypeParameterDto>> { TypeParameterValidator(get()) }
    single<DtoValidator<TypeSchemaRefDto>> { TypeSchemaRefValidator(get()) }
    single<DtoValidator<TypeUnionMemberDto>> { TypeUnionMemberValidator() }
    single<DtoValidator<TypeVarianceDto>> { TypeVarianceValidator(get()) }

    single<DtoValidator<PaymentMethodDto>> { PaymentMethodValidator(get()) }
    single<DtoValidator<TagDto>> { TagValidator(get()) }
    single<DtoValidator<TagRelationDto>> { TagRelationValidator() }
    single<DtoValidator<UserDto>> { UserValidator() }
    single<DtoValidator<DataPointDto>> { DataPointValidator(get(), get()) }
    single<DtoValidator<PhaseDto>> { PhaseValidator(get()) }
    single<DtoValidator<WorkflowFolderDto>> { WorkflowFolderValidator() }
    single<DtoValidator<WorkflowDto>> { WorkflowValidator() }


    single<PackageValidator> { PackageValidator(get(), ) }
    single<PackageRepoValidator> { PackageRepoValidator() }
    single<ToolPackageDependencyValidator> { ToolPackageDependencyValidator() }
    single<VersionSpecValidator> { VersionSpecValidator() }

    single<BenchmarkingDataValidator> { BenchmarkingDataValidator(get()) }
    single<ExecutionLogValidator> { ExecutionLogValidator(get(),) }
    single<StepExecutionValidator> { StepExecutionValidator(get(), get(), get()) }
    single<TestCaseValidator> { TestCaseValidator(get(), get(), get()) }
    single<TestExpectedOutcomeValidator> { TestExpectedOutcomeValidator(get()) }
    single<TestInputValidator> { TestInputValidator(get(),) }
    single<TestResultValidator> { TestResultValidator(get(), get()) }
    single<WorkflowExecutionValidator> { WorkflowExecutionValidator(get(), get(), get()) }

    single<ErrorWatchValidator> { ErrorWatchValidator(get()) }
    single<ErrorWatchFnValidator> { ErrorWatchFnValidator(get(),) }
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





