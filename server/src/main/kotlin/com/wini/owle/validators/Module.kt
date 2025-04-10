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

    single<DtoValidator<ErrorWatchDto>> { ErrorWatchValidator(get(), get()) }
    single<DtoValidator<ErrorWatchFnDto>> { ErrorWatchFnValidator(get(),) }
    single<DtoValidator<InquisitionDto>> { InquisitionValidator(get(), get(), get()) }

    single<DtoValidator<PreReqInjectionDto>> { PreReqInjectionValidator(get(), get()) }
    single<DtoValidator<ProvisionDto>> { ProvisionValidator(get(), get(), get()) }
    single<DtoValidator<ProvisionDataPointDto>> { ProvisionDataPointValidator() }
    single<DtoValidator<RevisionDto>> { RevisionValidator(get(), get()) }
    single<DtoValidator<StepDto>> { StepValidator(get(), get(), get()) }
    single<DtoValidator<ToolDto>> { ToolValidator(get()) }
    single<DtoValidator<ToolParamDto>> { ToolParamValidator(get(), get()) }
    single<DtoValidator<WorkflowJunctionDto>> { WorkflowJunctionValidator(get(), get(), get()) }

    single<DtoValidator<AppliedTransformDto>> { AppliedTransformValidator(get(), get(), get(), get()) }
    single<DtoValidator<AppliedTransformArgDto>> { AppliedTransformArgValidator(get(), get()) }
    single<DtoValidator<TransformationDto>> { TransformationValidator(get()) }
    single<DtoValidator<TransformationArgDto>> { TransformationArgValidator(get(), get()) }
    single<DtoValidator<TransformationTargetTypeDto>> { TransformationTargetTypeValidator() }

    single<DtoValidator<TypeBoundsDto>> { TypeBoundsValidator(get(), get()) }
    single<DtoValidator<TypeDeclarationDto>> { TypeDeclarationValidator(get(),) }
    single<DtoValidator<TypeDefinitionDto>> { TypeDefinitionValidator(get(),) }
    single<DtoValidator<TypeParameterDto>> { TypeParameterValidator(get(), get()) }
    single<DtoValidator<TypeSchemaRefDto>> { TypeSchemaRefValidator(get(), get()) }
    single<DtoValidator<TypeUnionMemberDto>> { TypeUnionMemberValidator() }
    single<DtoValidator<TypeVarianceDto>> { TypeVarianceValidator(get()) }

    single<DtoValidator<PaymentMethodDto>> { PaymentMethodValidator(get()) }
    single<DtoValidator<TagDto>> { TagValidator(get()) }
    single<DtoValidator<TagRelationDto>> { TagRelationValidator() }
    single<DtoValidator<UserDto>> { UserValidator() }
    single<DtoValidator<DataPointDto>> { DataPointValidator(get(), get()) }
    single<DtoValidator<PhaseDto>> { PhaseValidator(get(), get()) }
    single<DtoValidator<WorkflowFolderDto>> { WorkflowFolderValidator(get(),) }
    single<DtoValidator<WorkflowDto>> { WorkflowValidator(get(),) }

}





