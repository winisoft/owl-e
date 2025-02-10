package com.wini.owle.dto

import com.wini.owle.dto.DtoTestInstanceFactory.appliedTransformArgDto
import com.wini.owle.dto.DtoTestInstanceFactory.appliedTransformDto
import com.wini.owle.dto.DtoTestInstanceFactory.benchmarkingDataDto
import com.wini.owle.dto.DtoTestInstanceFactory.dataPointDto
import com.wini.owle.dto.DtoTestInstanceFactory.errorWatchDto
import com.wini.owle.dto.DtoTestInstanceFactory.errorWatchFnDto
import com.wini.owle.dto.DtoTestInstanceFactory.executionLogDto
import com.wini.owle.dto.DtoTestInstanceFactory.inquisitionDto
import com.wini.owle.dto.DtoTestInstanceFactory.packageRepoDto
import com.wini.owle.dto.DtoTestInstanceFactory.paymentMethodDto
import com.wini.owle.dto.DtoTestInstanceFactory.phaseDto
import com.wini.owle.dto.DtoTestInstanceFactory.preReqInjectionDto
import com.wini.owle.dto.DtoTestInstanceFactory.provisionDataPointDto
import com.wini.owle.dto.DtoTestInstanceFactory.provisionDto
import com.wini.owle.dto.DtoTestInstanceFactory.revisionDto
import com.wini.owle.dto.DtoTestInstanceFactory.stepDto
import com.wini.owle.dto.DtoTestInstanceFactory.tagDto
import com.wini.owle.dto.DtoTestInstanceFactory.tagRelationDto
import com.wini.owle.dto.DtoTestInstanceFactory.testCaseDto
import com.wini.owle.dto.DtoTestInstanceFactory.testExpectedOutcomeDto
import com.wini.owle.dto.DtoTestInstanceFactory.testInputDto
import com.wini.owle.dto.DtoTestInstanceFactory.testResultDto
import com.wini.owle.dto.DtoTestInstanceFactory.toolDto
import com.wini.owle.dto.DtoTestInstanceFactory.toolParamDto
import com.wini.owle.dto.DtoTestInstanceFactory.transformationArgDto
import com.wini.owle.dto.DtoTestInstanceFactory.transformationDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDeclarationDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDefinitionDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeParameterDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeSchemaRefDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeUnionMemberDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeVarianceDto
import com.wini.owle.dto.DtoTestInstanceFactory.userDto
import com.wini.owle.dto.DtoTestInstanceFactory.versionSpecDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowExecutionDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowFolderDto
import org.junit.jupiter.api.Nested

//
//open class DtoTests {
//
//    @Nested
//    inner class EnvironsModuleTest {
//        @Nested
//        inner class PackageRepoDtoTest(override val getInstance: () -> PackageRepoDto = { packageRepoDto() })
//            : BaseDtoTest<PackageRepoDto>(PackageRepoDto::class.java)
//
//        @Nested
//        inner class VersionSpecDtoTest(override val getInstance: () -> VersionSpecDto = { versionSpecDto() })
//            : BaseDtoTest<VersionSpecDto>(VersionSpecDto::class.java)
//    }
//
//    @Nested
//    inner class RunsModuleTest {
//
//        @Nested
//        inner class TestCaseDtoTest(override val getInstance: () -> TestCaseDto = { testCaseDto() })
//            : BaseDtoTest<TestCaseDto>(TestCaseDto::class.java)
//
//        @Nested
//        inner class TestInputDtoTest(override val getInstance: () -> TestInputDto = { testInputDto() })
//            : BaseDtoTest<TestInputDto>(TestInputDto::class.java)
//
//        @Nested
//        inner class TestExpectedOutcomeDtoTest(override val getInstance: () -> TestExpectedOutcomeDto = { testExpectedOutcomeDto() })
//            : BaseDtoTest<TestExpectedOutcomeDto>(TestExpectedOutcomeDto::class.java)
//
//        @Nested
//        inner class TestResultDtoTest(override val getInstance: () -> TestResultDto = { testResultDto() })
//            : BaseDtoTest<TestResultDto>(TestResultDto::class.java)
//
//        @Nested
//        inner class BenchmarkingDataDtoTest(override val getInstance: () -> BenchmarkingDataDto = { benchmarkingDataDto() })
//            : BaseDtoTest<BenchmarkingDataDto>(BenchmarkingDataDto::class.java)
//
//        @Nested
//        inner class ExecutionLogDtoTest(override val getInstance: () -> ExecutionLogDto = { executionLogDto() })
//            : BaseDtoTest<ExecutionLogDto>(ExecutionLogDto::class.java)
//
//        @Nested
//        inner class WorkflowExecutionTest(override val getInstance: () -> WorkflowExecutionDto = { workflowExecutionDto() })
//            : BaseDtoTest<WorkflowExecutionDto>(WorkflowExecutionDto::class.java)
//
//    }
//
//    @Nested
//    inner class StepsModuleTest {
//
//        @Nested
//        inner class ErrorWatchDtoTest(override val getInstance: () -> ErrorWatchDto = { errorWatchDto() })
//            : BaseDtoTest<ErrorWatchDto>(ErrorWatchDto::class.java)
//
//        @Nested
//        inner class ErrorWatchFnDtoTest(override val getInstance: () -> ErrorWatchFnDto = { errorWatchFnDto() })
//            : BaseDtoTest<ErrorWatchFnDto>(ErrorWatchFnDto::class.java)
//
//        @Nested
//        inner class InquisitionDtoTest(override val getInstance: () -> InquisitionDto = { inquisitionDto() })
//            : BaseDtoTest<InquisitionDto>(InquisitionDto::class.java)
//
//        @Nested
//        inner class PreReqInjectionDtoTest(override val getInstance: () -> PreReqInjectionDto = { preReqInjectionDto() })
//            : BaseDtoTest<PreReqInjectionDto>(PreReqInjectionDto::class.java)
//
//        @Nested
//        inner class ProvisionDtoTest(override val getInstance: () -> ProvisionDto = { provisionDto() })
//            : BaseDtoTest<ProvisionDto>(ProvisionDto::class.java)
//
//        @Nested
//        inner class RevisionDtoTest(override val getInstance: () -> RevisionDto = { revisionDto() })
//            : BaseDtoTest<RevisionDto>(RevisionDto::class.java)
//
//        @Nested
//        inner class ProvisionDataPointDtoTest(override val getInstance: () -> ProvisionDataPointDto = { provisionDataPointDto() })
//            : BaseDtoTest<ProvisionDataPointDto>(ProvisionDataPointDto::class.java)
//
//    }
//
//    @Nested
//    inner class TransformsModuleTest {
//
//        @Nested
//        inner class AppliedTransformArgDtoTest(override val getInstance: () -> AppliedTransformArgDto = { appliedTransformArgDto() })
//            : BaseDtoTest<AppliedTransformArgDto>(AppliedTransformArgDto::class.java)
//
//        @Nested
//        inner class AppliedTransformDtoTest(override val getInstance: () -> AppliedTransformDto = { appliedTransformDto() })
//            : BaseDtoTest<AppliedTransformDto>(AppliedTransformDto::class.java)
//
//        @Nested
//        inner class TransformationArgDtoTest(override val getInstance: () -> TransformationArgDto = { transformationArgDto() })
//            : BaseDtoTest<TransformationArgDto>(TransformationArgDto::class.java)
//
//        @Nested
//        inner class TransformationDtoTest(override val getInstance: () -> TransformationDto = { transformationDto() })
//            : BaseDtoTest<TransformationDto>(TransformationDto::class.java)
//
//    }
//
//    @Nested
//    inner class TypingModuleTest {
//
//        @Nested
//        inner class TypeDeclarationDtoTest(override val getInstance: () -> TypeDeclarationDto = { typeDeclarationDto() })
//            : BaseDtoTest<TypeDeclarationDto>(TypeDeclarationDto::class.java)
//
//        @Nested
//        inner class TypeDefinitionDtoTest(override val getInstance: () -> TypeDefinitionDto = { typeDefinitionDto() })
//            : BaseDtoTest<TypeDefinitionDto>(TypeDefinitionDto::class.java)
//
//        @Nested
//        inner class TypeParameterDtoTest(override val getInstance: () -> TypeParameterDto = { typeParameterDto() })
//            : BaseDtoTest<TypeParameterDto>(TypeParameterDto::class.java)
//
//        @Nested
//        inner class TypeSchemaRefDtoTest(override val getInstance: () -> TypeSchemaRefDto = { typeSchemaRefDto() })
//            : BaseDtoTest<TypeSchemaRefDto>(TypeSchemaRefDto::class.java)
//
//        @Nested
//        inner class TypeUnionMemberDtoTest(override val getInstance: () -> TypeUnionMemberDto = { typeUnionMemberDto() })
//            : BaseDtoTest<TypeUnionMemberDto>(TypeUnionMemberDto::class.java)
//
//        @Nested
//        inner class TypeVarianceDtoTest(override val getInstance: () -> TypeVarianceDto = { typeVarianceDto() })
//            : BaseDtoTest<TypeVarianceDto>(TypeVarianceDto::class.java)
//
//    }
//
//    @Nested
//    inner class UserModuleTest {
//
//        @Nested
//        inner class UserDtoTest(override val getInstance: () -> UserDto = { userDto() })
//            : BaseDtoTest<UserDto>(UserDto::class.java)
//
//        @Nested
//        inner class PaymentMethodDtoTest(override val getInstance: () -> PaymentMethodDto = { paymentMethodDto() })
//            : BaseDtoTest<PaymentMethodDto>(PaymentMethodDto::class.java)
//
//        @Nested
//        inner class TagDtoTest(override val getInstance: () -> TagDto = { tagDto() })
//            : BaseDtoTest<TagDto>(TagDto::class.java)
//
//        @Nested
//        inner class TagRelationDtoTest(override val getInstance: () -> TagRelationDto = { tagRelationDto() })
//            : BaseDtoTest<TagRelationDto>(TagRelationDto::class.java)
//
//    }
//
//    @Nested
//    inner class WorkflowsModuleTest {
//
//        @Nested
//        inner class StepDtoTest(override val getInstance: () -> StepDto = { stepDto() })
//            : BaseDtoTest<StepDto>(StepDto::class.java)
//
//        @Nested
//        inner class WorkflowDtoTest(override val getInstance: () -> WorkflowDto = { workflowDto() })
//            : BaseDtoTest<WorkflowDto>(WorkflowDto::class.java)
//
//        @Nested
//        inner class DataPointDtoTest(override val getInstance: () -> DataPointDto = { dataPointDto() })
//            : BaseDtoTest<DataPointDto>(DataPointDto::class.java)
//
//        @Nested
//        inner class PhaseDtoTest(override val getInstance: () -> PhaseDto = { phaseDto() })
//            : BaseDtoTest<PhaseDto>(PhaseDto::class.java)
//
//        @Nested
//        inner class ToolDtoTest(override val getInstance: () -> ToolDto = { toolDto() })
//            : BaseDtoTest<ToolDto>(ToolDto::class.java)
//
//        @Nested
//        inner class ToolParamDtoTest(override val getInstance: () -> ToolParamDto = { toolParamDto() })
//            : BaseDtoTest<ToolParamDto>(ToolParamDto::class.java)
//
//        @Nested
//        inner class WorkflowFolderDtoTest(override val getInstance: () -> WorkflowFolderDto = { workflowFolderDto() })
//            : BaseDtoTest<WorkflowFolderDto>(WorkflowFolderDto::class.java)
//
//    }
//
//}