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

//    single<DtoValidator<PackageDto>> { PackageValidator() }
//    single<DtoValidator<PackageRepoDto>> { PackageRepoValidator() }
//    single<DtoValidator<ToolPackageDependencyDto>> { ToolPackageDependencyValidator() }
//    single<DtoValidator<VersionSpecDto>> { VersionSpecValidator() }
//
//    single<DtoValidator<BenchmarkingDataDto>> { BenchmarkingDataValidator() }
//    single<DtoValidator<ExecutionLogDto>> { ExecutionLogValidator() }
//    single<DtoValidator<StepExecutionDto>> { StepExecutionValidator() }
//    single<DtoValidator<TestCaseDto>> { TestCaseValidator() }
//    single<DtoValidator<TestExpectedOutcomeDto>> { TestExpectedOutcomeValidator() }
//    single<DtoValidator<TestInputDto>> { TestInputValidator() }
//    single<DtoValidator<TestResultDto>> { TestResultValidator() }
//    single<DtoValidator<WorkflowExecutionDto>> { WorkflowExecutionValidator() }
//
//    single<DtoValidator<ErrorWatchDto>> { ErrorWatchValidator() }
//    single<DtoValidator<ErrorWatchFnDto>> { ErrorWatchFnValidator() }
//    single<DtoValidator<InquisitionDto>> { InquisitionValidator() }
//    single<DtoValidator<PreReqInjectionDto>> { PreReqInjectionValidator() }
//    single<DtoValidator<ProvisionDto>> { ProvisionValidator() }
//    single<DtoValidator<ProvisionDataPointDto>> { ProvisionDataPointValidator() }
//    single<DtoValidator<RevisionDto>> { RevisionValidator() }
//    single<DtoValidator<StepDto>> { StepValidator() }
//    single<DtoValidator<ToolDto>> { ToolValidator() }
//    single<DtoValidator<ToolParamDto>> { ToolParamValidator() }
//    single<DtoValidator<WorkflowJunctionDto>> { WorkflowJunctionValidator() }
//
//    single<DtoValidator<AppliedTransformDto>> { AppliedTransformValidator() }
//    single<DtoValidator<AppliedTransformArgDto>> { AppliedTransformArgValidator() }
//    single<DtoValidator<TransformationDto>> { TransformationValidator() }
//    single<DtoValidator<TransformationArgDto>> { TransformationArgValidator() }
//    single<DtoValidator<TransformTargetTypeDto>> { TransformTargetTypeValidator() }
//
//    single<DtoValidator<TypeBoundsDto>> { TypeBoundsValidator() }
//    single<DtoValidator<TypeDeclarationDto>> { TypeDeclarationValidator() }
//    single<DtoValidator<TypeDefinitionDto>> { TypeDefinitionValidator() }
//    single<DtoValidator<TypeParameterDto>> { TypeParameterValidator() }
//    single<DtoValidator<TypeSchemaRefDto>> { TypeSchemaRefValidator() }
//    single<DtoValidator<TypeUnionMemberDto>> { TypeUnionMemberValidator() }
//    single<DtoValidator<TypeVarianceDto>> { TypeVarianceValidator() }
//
//    single<DtoValidator<PaymentMethodDto>> { PaymentMethodValidator() }
//    single<DtoValidator<TagDto>> { TagValidator() }
//    single<DtoValidator<TagRelationDto>> { TagRelationValidator() }
//    single<DtoValidator<UserDto>> { UserValidator() }
//    single<DtoValidator<DataPointsDto>> { DataPointsValidator() }
//    single<DtoValidator<PhaseDto>> { PhaseValidator() }
//    single<DtoValidator<WorkflowFolderDto>> { WorkflowFolderValidator() }
    single<DtoValidator<WorkflowDto>> { WorkflowValidator() }

}





