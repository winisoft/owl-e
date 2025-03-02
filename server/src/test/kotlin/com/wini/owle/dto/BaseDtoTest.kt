package com.wini.owle.dto

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@OptIn(InternalSerializationApi::class)
abstract class BaseDtoTest<T : BaseDto>(
    private val dtoClass: Class<T> // Reflection-based type
) {

    private val exampleInstance: T = DtoTestInstanceFactory.createDtoInstance(dtoClass)

    @Test
    fun `serialization should produce valid JSON`() {
        val json = Json.encodeToString(dtoClass.kotlin.serializer(), exampleInstance)
        assertNotNull(json, "JSON serialization returned null")
    }

    @Test
    fun `deserialization should recreate original object`() {
        val json = Json.encodeToString(dtoClass.kotlin.serializer(), exampleInstance)
        val deserialized = Json.decodeFromString(dtoClass.kotlin.serializer(), json)
        assertEquals(exampleInstance, deserialized, "Deserialized object does not match the original")
    }

    @Test
    fun `round-trip serialization-deserialization should be consistent`() {
        val json = Json.encodeToString(dtoClass.kotlin.serializer(), exampleInstance)
        val deserialized = Json.decodeFromString(dtoClass.kotlin.serializer(), json)
        val reserialized = Json.encodeToString(dtoClass.kotlin.serializer(), deserialized)
        assertEquals(json, reserialized, "Round-trip serialization did not produce consistent JSON")
    }
}

class DtoTest{

    @Nested
    inner class EnvironsModuleTest {
        @Nested
        inner class PackageRepoDtoTest : BaseDtoTest<PackageRepoDto>(PackageRepoDto::class.java)
        @Nested
        inner class VersionSpecDtoTest : BaseDtoTest<VersionSpecDto>(VersionSpecDto::class.java)
    }

    @Nested
    inner class RunsModuleTest {
        @Nested
        inner class TestCaseDtoTest : BaseDtoTest<TestCaseDto>(TestCaseDto::class.java)
        @Nested
        inner class TestInputDtoTest : BaseDtoTest<TestInputDto>(TestInputDto::class.java)
        @Nested
        inner class TestExpectedOutcomeDtoTest : BaseDtoTest<TestExpectedOutcomeDto>(TestExpectedOutcomeDto::class.java)
        @Nested
        inner class TestResultDtoTest : BaseDtoTest<TestResultDto>(TestResultDto::class.java)
        @Nested
        inner class BenchmarkingDataDtoTest : BaseDtoTest<BenchmarkingDataDto>(BenchmarkingDataDto::class.java)
        @Nested
        inner class ExecutionLogDtoTest : BaseDtoTest<ExecutionLogDto>(ExecutionLogDto::class.java)
        @Nested
        inner class WorkflowExecutionTest : BaseDtoTest<WorkflowExecutionDto>(WorkflowExecutionDto::class.java)
    }

    @Nested
    inner class StepsModuleTest {
        @Nested
        inner class ErrorWatchDtoTest : BaseDtoTest<ErrorWatchDto>(ErrorWatchDto::class.java)
        @Nested
        inner class ErrorWatchFnDtoTest : BaseDtoTest<ErrorWatchFnDto>(ErrorWatchFnDto::class.java)
        @Nested
        inner class InquisitionDtoTest : BaseDtoTest<InquisitionDto>(InquisitionDto::class.java)
        @Nested
        inner class PreReqInjectionDtoTest : BaseDtoTest<PreReqInjectionDto>(PreReqInjectionDto::class.java)
        @Nested
        inner class ProvisionDtoTest : BaseDtoTest<ProvisionDto>(ProvisionDto::class.java)
        @Nested
        inner class RevisionDtoTest : BaseDtoTest<RevisionDto>(RevisionDto::class.java)
        @Nested
        inner class ProvisionDataPointDtoTest : BaseDtoTest<ProvisionDataPointDto>(ProvisionDataPointDto::class.java)
    }

    @Nested
    inner class TransformsModuleTest {
        @Nested
        inner class AppliedTransformArgDtoTest : BaseDtoTest<AppliedTransformArgDto>(AppliedTransformArgDto::class.java)
        @Nested
        inner class AppliedTransformDtoTest : BaseDtoTest<AppliedTransformDto>(AppliedTransformDto::class.java)
        @Nested
        inner class TransformationArgDtoTest : BaseDtoTest<TransformationArgDto>(TransformationArgDto::class.java)
        @Nested
        inner class TransformationDtoTest : BaseDtoTest<TransformationDto>(TransformationDto::class.java)
        @Nested
        inner class TransformTargetTypeDtoTest : BaseDtoTest<TransformationTargetTypeDto>(TransformationTargetTypeDto::class.java)
    }

    @Nested
    inner class TypingModuleTest {
        @Nested
        inner class TypeDeclarationDtoTest : BaseDtoTest<TypeDeclarationDto>(TypeDeclarationDto::class.java)
        @Nested
        inner class TypeDefinitionDtoTest : BaseDtoTest<TypeDefinitionDto>(TypeDefinitionDto::class.java)
        @Nested
        inner class TypeParameterDtoTest : BaseDtoTest<TypeParameterDto>(TypeParameterDto::class.java)
        @Nested
        inner class TypeSchemaRefDtoTest : BaseDtoTest<TypeSchemaRefDto>(TypeSchemaRefDto::class.java)
        @Nested
        inner class TypeUnionMemberDtoTest : BaseDtoTest<TypeUnionMemberDto>(TypeUnionMemberDto::class.java)
        @Nested
        inner class TypeVariancesDtoTest : BaseDtoTest<TypeVarianceDto>(TypeVarianceDto::class.java)
    }

    @Nested
    inner class UserModuleTest {
        @Nested
        inner class UserDtoTest : BaseDtoTest<UserDto>(UserDto::class.java)
        @Nested
        inner class PaymentMethodDtoTest : BaseDtoTest<PaymentMethodDto>(PaymentMethodDto::class.java)
        @Nested
        inner class TagDtoTest : BaseDtoTest<TagDto>(TagDto::class.java)
        @Nested
        inner class TagRelationDtoTest : BaseDtoTest<TagRelationDto>(TagRelationDto::class.java)
    }

    @Nested
    inner class WorkflowsModuleTest {
        @Nested
        inner class StepDtoTest : BaseDtoTest<StepDto>(StepDto::class.java)
        @Nested
        inner class WorkflowDtoTest : BaseDtoTest<WorkflowDto>(WorkflowDto::class.java)
        @Nested
        inner class DataPointDtoTest : BaseDtoTest<DataPointDto>(DataPointDto::class.java)
        @Nested
        inner class PhaseDtoTest : BaseDtoTest<PhaseDto>(PhaseDto::class.java)
        @Nested
        inner class ToolDtoTest : BaseDtoTest<ToolDto>(ToolDto::class.java)
        @Nested
        inner class ToolParamDtoTest : BaseDtoTest<ToolParamDto>(ToolParamDto::class.java)
        @Nested
        inner class WorkflowFolderDtoTest : BaseDtoTest<WorkflowFolderDto>(WorkflowFolderDto::class.java)
    }
}







