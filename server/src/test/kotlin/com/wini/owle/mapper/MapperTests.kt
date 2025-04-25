package com.wini.owle.mapper

import com.wini.owle.mapper.environs.PyPackageMapperTest
import com.wini.owle.mapper.environs.PyPackageRepoMapperTest
import com.wini.owle.mapper.environs.VersionSpecsMapperTest
import com.wini.owle.mapper.environs.enums.PyPackageRepoTypeEntityMapperTest
import com.wini.owle.mapper.runs.*
import com.wini.owle.mapper.runs.enums.ComparisonOperatorEntityMapperTest
import com.wini.owle.mapper.runs.enums.ExecutionStatusEntityMapperTest
import com.wini.owle.mapper.runs.enums.GenerationMethodEntityMapperTest
import com.wini.owle.mapper.runs.enums.LogLevelEntityMapperTest
import com.wini.owle.mapper.steps.*
import com.wini.owle.mapper.transforms.*
import com.wini.owle.mapper.transforms.enums.TransformEntityOccurrenceMapperTest
import com.wini.owle.mapper.typing.*
import com.wini.owle.mapper.typing.enums.VarianceCardinalityEntityMapperTest
import com.wini.owle.mapper.user.PaymentMethodEntityMapperTest
import com.wini.owle.mapper.user.TagsMapperTest
import com.wini.owle.mapper.user.UserEntityMapperTest
import com.wini.owle.mapper.user.enums.CardBrandEntityMapperTest
import com.wini.owle.mapper.user.enums.SubscriptionStatusEntityMapperTest
import com.wini.owle.mapper.workflows.*
import com.wini.owle.mapper.workflows.enums.AuthorshipCreditEntityMapperTest
import com.wini.owle.mapper.workflows.enums.CommercialUseEntityMapperTest
import com.wini.owle.mapper.workflows.enums.ModificationTermsEntityMapperTest
import com.wini.owle.mapper.workflows.enums.StoreMethodEntityMapperTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@Nested
@DisplayName("Mapper Tests")
open class MapperTests {

    @Nested
    @DisplayName("DateTime Converter Tests")
    inner class DateTimeConverterTests: DateTimeConverterTest()

    @Nested
    @DisplayName("Enum Mapper Tests")
    inner class EnumMapperTests {

        @Nested inner class PyPackageRepoTypeEntityMapperTests: PyPackageRepoTypeEntityMapperTest()

        @Nested inner class ComparisonOperatorEntityMapperTests: ComparisonOperatorEntityMapperTest()

        @Nested inner class ExecutionStatusEntityMapperTests: ExecutionStatusEntityMapperTest()

        @Nested inner class GenerationMethodEntityMapperTests: GenerationMethodEntityMapperTest()

        @Nested inner class LogLevelEntityMapperTests: LogLevelEntityMapperTest()

        @Nested inner class TransformEntityOccurrenceMapperTests: TransformEntityOccurrenceMapperTest()

        @Nested inner class VarianceCardinalityEntityMapperTests: VarianceCardinalityEntityMapperTest()

        @Nested inner class CardBrandEntityMapperTests: CardBrandEntityMapperTest()

        @Nested inner class SubscriptionStatusEntityMapperTests: SubscriptionStatusEntityMapperTest()

        @Nested inner class AuthorshipCreditEntityMapperTests: AuthorshipCreditEntityMapperTest()

        @Nested inner class CommercialUseEntityMapperTests: CommercialUseEntityMapperTest()

        @Nested inner class ModificationTermsEntityMapperTests: ModificationTermsEntityMapperTest()

        @Nested inner class StoreMethodEntityMapperTests: StoreMethodEntityMapperTest()
    }

    @Nested
    @DisplayName("Environs Module")
    inner class MapperEnvironsModuleTest {

        @Nested @DisplayName("PackageRepoMapper")
        inner class PyPackageRepoTest: PyPackageRepoMapperTest()

        @Nested @DisplayName("PackageMapper")
        inner class PyPackageTest: PyPackageMapperTest()

        @Nested @DisplayName("VersionSpecMapper")
        inner class VersionSpecTest: VersionSpecsMapperTest()

    }

    @Nested
    @DisplayName("Runs Module")
    inner class MapperRunsModuleTest {

        @Nested @DisplayName("BenchmarkingDataMapper")
        inner class BenchmarkingDataTest: BenchmarkingDataMapperTest()

        @Nested @DisplayName("ExecutionLogMapper")
        inner class ExecutionLogTest: ExecutionLogMapperTest()

        @Nested @DisplayName("StepBenchmarkingDataMapper")
        inner class StepBenchmarkingTest: StepBenchmarkingDataMapperTest()

        @Nested @DisplayName("StepExecutionMapper")
        inner class StepExecutionTest: StepExecutionMapperTest()

        @Nested @DisplayName("TestCaseMapper")
        inner class TestCaseTestEntity: TestCaseMapperTestEntity()

        @Nested @DisplayName("TestExpectedOutcomeMapper")
        inner class TestExpectedOutcomeTest: TestExpectedOutcomeMapperTest()

        @Nested @DisplayName("TestInputMapper")
        inner class TestInputTestEntity: TestInputMapperTestEntity()

        @Nested @DisplayName("TestResultMapper")
        inner class TestResultWorkflowTest: TestResultMapperWorkflowTest()

        @Nested @DisplayName("WorkflowExecutionMapper")
        inner class WorkflowExecutionTest: WorkflowExecutionMapperTest()

    }

    @Nested
    @DisplayName("Steps Module")
    inner class MapperStepsModuleTest {

        @Nested @DisplayName("ErrorWatchMapper")
        inner class ErrorWatchEntityTest: ErrorWatchEntityMapperTest()

        @Nested @DisplayName("ErrorWatchFnMapper")
        inner class ErrorWatchEntityFnTest: ErrorWatchEntityFnMapperTest()

        @Nested @DisplayName("InquisitionMapper")
        inner class InquisitionEntityTest: InquisitionEntityMapperTest()

        @Nested @DisplayName("PreReqInjectionMapper")
        inner class PreReqInjectionTest: PreReqInjectionMapperTest()

        @Nested @DisplayName("ProvisionMapper")
        inner class ProvisionTest: ProvisionMapperTest()

        @Nested @DisplayName("RevisionMapper")
        inner class RevisionTest: RevisionMapperTest()

        @Nested @DisplayName("WorkflowJunctionMapper")
        inner class WorkflowJunctionTest: WorkflowJunctionMapperTest()

    }

    @Nested
    @DisplayName("Transforms Module")
    inner class MapperTransformsModuleTest {

        @Nested @DisplayName("AppliedTransformArgMapper")
        inner class AppliedTransformEntityEntityArgTest: AppliedTransformEntityEntityArgMapperTest()

        @Nested @DisplayName("AppliedTransformMapper")
        inner class AppliedTransformEntityEntityTest: AppliedTransformEntityEntityMapperTest()

        @Nested @DisplayName("TransformationArgMapper")
        inner class TransformationArgTest: TransformationArgMapperTest()

        @Nested @DisplayName("TransformationMapper")
        inner class TransformationTest: TransformationMapperTest()

    }

    @Nested
    @DisplayName("Typing Module")
    inner class MapperTypingModuleTests {

        @Nested @DisplayName("TypeBoundMapper")
        inner class TypeBoundEntityTest: TypeBoundEntityMapperTest()

        @Nested @DisplayName("TypeDeclaratonMapper")
        inner class TypeDeclarationEntityTest: TypeDeclarationEntityMapperTest()

        @Nested @DisplayName("TypeDefinitionMapper")
        inner class TypeDefinitionEntityTest: TypeDefinitionEntityMapperTest()

        @Nested @DisplayName("TypeParameterMapper")
        inner class TypeParameterTest: TypeParameterMapperTest()

        @Nested @DisplayName("TypeSchemaRefMapper")
        inner class TypeSchemaRefTest: TypeSchemaRefMapperTest()

        @Nested @DisplayName("TypeVarianceMapper")
        inner class TypeVariancesTest: TypeVariancesMapperTest()

    }

    @Nested
    @DisplayName("User Module")
    inner class MapperUserEntityModuleTest {

        @Nested @DisplayName("PaymentMethodMapper")
        inner class PaymentMethodEntityTest: PaymentMethodEntityMapperTest()

        @Nested @DisplayName("TagMapper")
        inner class TagEntityTest: TagsMapperTest()

        @Nested @DisplayName("UserMapper")
        inner class UserEntityTest: UserEntityMapperTest()

    }

    @Nested
    @DisplayName("Workflows Module")
    inner class MapperWorkflowsModuleTest {

        @Nested @DisplayName("DataPointMapper")
        inner class DataPointEntityTest: DataPointEntityMapperTest()

        @Nested @DisplayName("PhaseMapper")
        inner class PhaseTest: PhaseMapperTest()

        @Nested @DisplayName("StepMapper")
        inner class StepTest: StepMapperTest()

        @Nested @DisplayName("ToolParamMapper")
        inner class ToolParamTest: ToolParamMapperTest()

        @Nested @DisplayName("ToolMapper")
        inner class ToolTest: ToolMapperTest()

        @Nested @DisplayName("WorkflowFolderMapper")
        inner class WorkflowFolderTest: WorkflowFolderMapperTest()

        @Nested @DisplayName("WorkflowMapper")
        inner class WorkflowTest: WorkflowsMapperTest()

    }
}