package com.wini.owle.dto

import com.wini.owle.dto.environs.*
import com.wini.owle.dto.runs.*
import com.wini.owle.dto.steps.*
import com.wini.owle.dto.transforms.*
import com.wini.owle.dto.typing.*
import com.wini.owle.dto.user.*
import com.wini.owle.dto.workflows.*
import com.wini.owle.dto.runs.TestCaseDto
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested


open class DtoTest {

    @Nested
    @DisplayName("Environs Module")
    inner class EnvironsModule {
        @Nested inner class PackageRepoDtoTest: BaseDtoTest<PackageRepoDto>(PackageRepoDto::class.java)
        @Nested inner class PackageDtoTest: BaseDtoTest<PackageRepoDto>(PackageRepoDto::class.java)
        @Nested inner class VersionSpecDtoTest: BaseDtoTest<VersionSpecDto>(VersionSpecDto::class.java)
    }

    @Nested
    @DisplayName("Runs Module")
    inner class RunsModule {
        @Nested inner class TestCaseDtoTest : BaseDtoTest<TestCaseDto>(TestCaseDto::class.java)
        @Nested inner class TestInputDtoTest : BaseDtoTest<TestInputDto>(TestInputDto::class.java)
        @Nested inner class TestExpectedOutcomeDtoTest : BaseDtoTest<TestExpectedOutcomeDto>(TestExpectedOutcomeDto::class.java)
        @Nested inner class TestResultDtoTest : BaseDtoTest<TestResultDto>(TestResultDto::class.java)
        @Nested inner class BenchmarkingDataDtoTest : BaseDtoTest<BenchmarkingDataDto>(BenchmarkingDataDto::class.java)
        @Nested inner class ExecutionLogDtoTest : BaseDtoTest<ExecutionLogDto>(ExecutionLogDto::class.java)
        @Nested inner class WorkflowExecutionTest : BaseDtoTest<WorkflowExecutionDto>(WorkflowExecutionDto::class.java)
    }

    @Nested
    @DisplayName("Steps Module")
    inner class StepsModule {
        @Nested inner class ErrorWatchDtoTest : BaseDtoTest<ErrorWatchDto>(ErrorWatchDto::class.java)
        @Nested inner class ErrorWatchFnDtoTest : BaseDtoTest<ErrorWatchFnDto>(ErrorWatchFnDto::class.java)
        @Nested inner class InquisitionDtoTest : BaseDtoTest<InquisitionDto>(InquisitionDto::class.java)
        @Nested inner class PreReqInjectionDtoTest : BaseDtoTest<PreReqInjectionDto>(PreReqInjectionDto::class.java)
        @Nested inner class ProvisionDtoTest : BaseDtoTest<ProvisionDto>(ProvisionDto::class.java)
        @Nested inner class RevisionDtoTest : BaseDtoTest<RevisionDto>(RevisionDto::class.java)
        @Nested inner class ProvisionDataPointDtoTest : BaseDtoTest<ProvisionDataPointDto>(ProvisionDataPointDto::class.java)
    }

    @Nested
    @DisplayName("Transforms Module")
    inner class TransformsModule {
        @Nested inner class AppliedTransformArgDtoTest : BaseDtoTest<AppliedTransformArgDto>(AppliedTransformArgDto::class.java)
        @Nested inner class AppliedTransformDtoTest : BaseDtoTest<AppliedTransformDto>(AppliedTransformDto::class.java)
        @Nested inner class TransformationArgDtoTest : BaseDtoTest<TransformationArgDto>(TransformationArgDto::class.java)
        @Nested inner class TransformationDtoTest : BaseDtoTest<TransformationDto>(TransformationDto::class.java)
    }

    @Nested
    @DisplayName("Typing Module")
    inner class TypingModule {
        @Nested inner class TypeDeclarationDtoTest : BaseDtoTest<TypeDeclarationDto>(TypeDeclarationDto::class.java)
        @Nested inner class TypeDefinitionDtoTest : BaseDtoTest<TypeDefinitionDto>(TypeDefinitionDto::class.java)
        @Nested inner class TypeParameterDtoTest : BaseDtoTest<TypeParameterDto>(TypeParameterDto::class.java)
        @Nested inner class TypeSchemaRefDtoTest : BaseDtoTest<TypeSchemaRefDto>(TypeSchemaRefDto::class.java)
        @Nested inner class TypeUnionMemberDtoTest : BaseDtoTest<TypeUnionMemberDto>(TypeUnionMemberDto::class.java)
        @Nested inner class TypeVariancesDtoTest : BaseDtoTest<TypeVarianceDto>(TypeVarianceDto::class.java)
    }

    @Nested
    @DisplayName("User Module")
    inner class UserModule {
        @Nested inner class UserDtoTest : BaseDtoTest<UserDto>(UserDto::class.java)
        @Nested inner class PaymentMethodDtoTest : BaseDtoTest<PaymentMethodDto>(PaymentMethodDto::class.java)
        @Nested inner class TagDtoTest : BaseDtoTest<TagDto>(TagDto::class.java)
        @Nested inner class TagRelationDtoTest : BaseDtoTest<TagRelationDto>(TagRelationDto::class.java)
    }

    @Nested
    @DisplayName("Workflows Module")
    inner class WorkflowsModule {
        @Nested inner class StepDtoTest : BaseDtoTest<StepDto>(StepDto::class.java)
        @Nested inner class WorkflowDtoTest : BaseDtoTest<WorkflowDto>(WorkflowDto::class.java)
        @Nested inner class DataPointDtoTest : BaseDtoTest<DataPointDto>(DataPointDto::class.java)
        @Nested inner class PhaseDtoTest : BaseDtoTest<PhaseDto>(PhaseDto::class.java)
        @Nested inner class ToolDtoTest : BaseDtoTest<ToolDto>(ToolDto::class.java)
        @Nested inner class ToolParamDtoTest : BaseDtoTest<ToolParamDto>(ToolParamDto::class.java)
        @Nested inner class WorkflowFolderDtoTest : BaseDtoTest<WorkflowFolderDto>(WorkflowFolderDto::class.java)
    }

}
