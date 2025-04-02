package com.wini.owle.mapper


import com.wini.owle.mapper.environs.PackageMapperTest
import com.wini.owle.mapper.environs.PackageRepoMapperTest
import com.wini.owle.mapper.environs.VersionSpecsMapperTest
import com.wini.owle.mapper.runs.*
import com.wini.owle.mapper.steps.*
import com.wini.owle.mapper.transforms.*
import com.wini.owle.mapper.typing.*
import com.wini.owle.mapper.user.PaymentMethodMapperTest
import com.wini.owle.mapper.user.TagRelationsMapperTest
import com.wini.owle.mapper.user.TagsMapperTest
import com.wini.owle.mapper.user.UserMapperTest
import com.wini.owle.mapper.workflows.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested


@DisplayName("Mapper Tests")
open class MapperTests {

    @Nested
    @DisplayName("Environs Module")
    inner class MapperEnvironsModuleTest {

        @Nested
        @DisplayName("PackageRepoMapper")
        inner class PackageRepoTest: PackageRepoMapperTest()

        @Nested
        @DisplayName("PackageMapper")
        inner class PackageTest: PackageMapperTest()

        @Nested
        @DisplayName("VersionSpecMapper")
        inner class VersionSpecTest: VersionSpecsMapperTest()

    }

    @Nested
    @DisplayName("Runs Module")
    inner class MapperRunsModuleTest {

        @Nested
        @DisplayName("BenchmarkingDataMapper")
        inner class BenchmarkingDataTest: BenchmarkingDataMapperTest()

        @Nested
        @DisplayName("ExecutionLogMapper")
        inner class ExecutionLogTest: ExecutionLogMapperTest()

        @Nested
        @DisplayName("StepBenchmarkingDataMapper")
        inner class StepBenchmarkingTest: StepBenchmarkingDataMapperTest()

        @Nested
        @DisplayName("StepExecutionMapper")
        inner class StepExecutionTest: StepExecutionMapperTest()

        @Nested
        @DisplayName("TestCaseMapper")
        inner class TestCaseTest: TestCaseMapperTest()

        @Nested
        @DisplayName("TestExpectedOutcomeMapper")
        inner class TestExpectedOutcomeTest: TestExpectedOutcomeMapperTest()

        @Nested
        @DisplayName("TestInputMapper")
        inner class TestInputTest: TestInputMapperTest()

        @Nested
        @DisplayName("TestResultMapper")
        inner class TestResultTest: TestResultMapperTest()

        @Nested
        @DisplayName("WorkflowExecutionMapper")
        inner class WorkflowExecutionTest: WorkflowExecutionMapperTest()

    }

    @Nested
    @DisplayName("Steps Module")
    inner class MapperStepsModuleTest {

        @Nested
        @DisplayName("ErrorWatchMapper")
        inner class ErrorWatchTest: ErrorWatchMapperTest()

        @Nested
        @DisplayName("ErrorWatchFnMapper")
        inner class ErrorWatchFnTest: ErrorWatchFnMapperTest()

        @Nested
        @DisplayName("InquisitionMapper")
        inner class InquisitionTest: InquisitionMapperTest()

        @Nested
        @DisplayName("PreReqInjectionMapper")
        inner class PreReqInjectionTest: PreReqInjectionMapperTest()

        @Nested
        @DisplayName("ProvisionMapper")
        inner class ProvisionTest: ProvisionMapperTest()

        @Nested
        @DisplayName("RevisionMapper")
        inner class RevisionTest: RevisionMapperTest()

        @Nested
        @DisplayName("WorkflowJunctionMapper")
        inner class WorkflowJunctionTest: WorkflowJunctionMapperTest()

    }

    @Nested
    @DisplayName("Transforms Module")
    inner class MapperTransformsModuleTest {

        @Nested
        @DisplayName("AppliedTransformArgMapper")
        inner class AppliedTransformArgTest: AppliedTransformArgMapperTest()

        @Nested
        @DisplayName("AppliedTransformMapper")
        inner class AppliedTransformTest: AppliedTransformMapperTest()

        @Nested
        @DisplayName("TransformationArgMapper")
        inner class TransformationArgTest: TransformationArgMapperTest()

        @Nested
        @DisplayName("TransformationMapper")
        inner class TransformationTest: TransformationMapperTest()

    }

    @Nested
    @DisplayName("Typing Module")
    inner class MapperTypingModuleTests {

        @Nested
        @DisplayName("TypeBoundMapper")
        inner class TypeBoundTest: TypeBoundMapperTest()

        @Nested
        @DisplayName("TypeDeclaratonMapper")
        inner class TypeDeclarationTest: TypeDeclarationMapperTest()

        @Nested
        @DisplayName("TypeDefinitionMapper")
        inner class TypeDefinitionTest: TypeDefinitionMapperTest()

        @Nested
        @DisplayName("TypeParameterMapper")
        inner class TypeParameterTest: TypeParameterMapperTest()

        @Nested
        @DisplayName("TypeSchemaRefMapper")
        inner class TypeSchemaRefTest: TypeSchemaRefMapperTest()

        @Nested
        @DisplayName("TypeVarianceMapper")
        inner class TypeVariancesTest: TypeVariancesMapperTest()

    }

    @Nested
    @DisplayName("User Module")
    inner class MapperUserModuleTest {

        @Nested
        @DisplayName("PaymentMethodMapper")
        inner class PaymentMethodTest: PaymentMethodMapperTest()

        @Nested
        @DisplayName("TagMapper")
        inner class TagTest: TagsMapperTest()

        @Nested
        @DisplayName("TagRelationsMapper")
        inner class TagRelationsTest: TagRelationsMapperTest()

        @Nested
        @DisplayName("UserMapper")
        inner class UserTest: UserMapperTest()

    }

    @Nested
    @DisplayName("Workflows Module")
    inner class MapperWorkflowsModuleTest {

        @Nested
        @DisplayName("DataPointMapper")
        inner class DataPointTest: DataPointMapperTest()

        @Nested
        @DisplayName("PhaseMapper")
        inner class PhaseTest: PhaseMapperTest()

        @Nested
        @DisplayName("StepMapper")
        inner class StepTest: StepMapperTest()

        @Nested
        @DisplayName("ToolParamMapper")
        inner class ToolParamTest: ToolParamMapperTest()

        @Nested
        @DisplayName("ToolMapper")
        inner class ToolTest: ToolMapperTest()

        @Nested
        @DisplayName("WorkflowFolderMapper")
        inner class WorkflowFolderTest: WorkflowFolderMapperTest()

        @Nested
        @DisplayName("WorkflowMapper")
        inner class WorkflowTest: WorkflowsMapperTest()

    }
}