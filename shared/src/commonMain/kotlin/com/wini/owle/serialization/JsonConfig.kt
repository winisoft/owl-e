package com.wini.owle.serialization

import com.wini.owle.dto.*
import com.wini.owle.dto.PackageRepoDto
import com.wini.owle.dto.VersionSpecDto
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val polymorphicJson = Json {
    serializersModule = SerializersModule {
        polymorphic(BaseDto::class) {
            subclass(PackageRepoDto::class, PackageRepoDto.serializer())
            subclass(VersionSpecDto::class, VersionSpecDto.serializer())
            subclass(StepDto::class, StepDto.serializer())
            subclass(WorkflowDto::class, WorkflowDto.serializer())
            subclass(TestCaseDto::class, TestCaseDto.serializer())
            subclass(TestInputDto::class, TestInputDto.serializer())
            subclass(TestExpectedOutcomeDto::class, TestExpectedOutcomeDto.serializer())
            subclass(TestResultDto::class, TestResultDto.serializer())
            subclass(BenchmarkingDataDto::class, BenchmarkingDataDto.serializer())
            subclass(ExecutionLogDto::class, ExecutionLogDto.serializer())
            subclass(ErrorWatchDto::class, ErrorWatchDto.serializer())
            subclass(ErrorWatchFnDto::class, ErrorWatchFnDto.serializer())
            subclass(InquisitionDto::class, InquisitionDto.serializer())
            subclass(PreReqInjectionDto::class, PreReqInjectionDto.serializer())
            subclass(ProvisionDto::class, ProvisionDto.serializer())
            subclass(RevisionDto::class, RevisionDto.serializer())
            subclass(TypeDeclarationDto::class, TypeDeclarationDto.serializer())
            subclass(TypeDefinitionDto::class, TypeDefinitionDto.serializer())
            subclass(TypeParameterDto::class, TypeParameterDto.serializer())
            subclass(TypeSchemaRefDto::class, TypeSchemaRefDto.serializer())
            subclass(TypeUnionMemberDto::class, TypeUnionMemberDto.serializer())
            subclass(TypeVarianceDto::class, TypeVarianceDto.serializer())
            subclass(UserDto::class, UserDto.serializer())
            subclass(PaymentMethodDto::class, PaymentMethodDto.serializer())
            subclass(TagDto::class, TagDto.serializer())
            subclass(TagRelationDto::class, TagRelationDto.serializer())
            subclass(DataPointDto::class, DataPointDto.serializer())
            subclass(PhaseDto::class, PhaseDto.serializer())
            subclass(ProvisionDataPointDto::class, ProvisionDataPointDto.serializer())
            subclass(ToolDto::class, ToolDto.serializer())
            subclass(ToolParamDto::class, ToolParamDto.serializer())
            subclass(WorkflowFolderDto::class, WorkflowFolderDto.serializer())
        }
    }
    encodeDefaults = true
    prettyPrint = true
}
