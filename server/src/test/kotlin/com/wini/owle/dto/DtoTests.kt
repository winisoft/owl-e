package com.wini.owle.dto

import com.wini.owle.dto.environs.*
import com.wini.owle.dto.steps.*
import com.wini.owle.dto.transforms.*
import com.wini.owle.dto.typing.*
import com.wini.owle.dto.user.*
import com.wini.owle.dto.workflows.*
import com.wini.owle.dto.runs.step.StepRunLogLine
import com.wini.owle.dto.runs.test.*
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.dto.user.tags.Tag
import com.wini.owle.dto.user.tags.TagRelation
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@Nested
@DisplayName("DTO Tests")
open class DtoTest {

    @Nested
    @DisplayName("Environs Module")
    inner class EnvironsModule {
        @Nested inner class PyPyPackageRepoTest: BaseDtoTest<PyPackageRepo>(PyPackageRepo::class.java)
        @Nested inner class PyPackageTest: BaseDtoTest<PyPackageRepo>(PyPackageRepo::class.java)
        @Nested inner class VersionSpecTest: BaseDtoTest<VersionSpec>(VersionSpec::class.java)
    }

    @Nested
    @DisplayName("Runs Module")
    inner class RunsModule {
        @Nested inner class TestCaseDtoTestEntity : BaseDtoTest<TestCase>(TestCase::class.java)
        @Nested inner class TestInputDtoTestEntity : BaseDtoTest<TestInput>(TestInput::class.java)
        @Nested inner class TestExpectedOutcomeDtoTest : BaseDtoTest<TestExpectation>(TestExpectation::class.java)
        @Nested inner class TestResultDtoWorkflowTest : BaseDtoTest<WorkflowTestResult>(WorkflowTestResult::class.java)
        @Nested inner class BenchmarkReportTest : BaseDtoTest<BenchmarkReport>(BenchmarkReport::class.java)
        @Nested inner class StepRunLogLineTest : BaseDtoTest<StepRunLogLine>(StepRunLogLine::class.java)
        @Nested inner class WorkflowExecutionTest : BaseDtoTest<WorkflowExecution>(WorkflowExecution::class.java)
    }

    @Nested
    @DisplayName("Steps Module")
    inner class StepsModule {
        @Nested inner class ErrorWatchEntityDtoTest : BaseDtoTest<ErrorWatch>(ErrorWatch::class.java)
        @Nested inner class ErrorWatchEntityFnDtoTest : BaseDtoTest<ErrorWatchFn>(ErrorWatchFn::class.java)
        @Nested inner class InquisitionEntityDtoTest : BaseDtoTest<Inquisition>(Inquisition::class.java)
        @Nested inner class InitialDataPointInjectorTest : BaseDtoTest<InitialDataPointInjector>(InitialDataPointInjector::class.java)
        @Nested inner class ProvisionTest : BaseDtoTest<Provision>(Provision::class.java)
        @Nested inner class DataPointSaveTest : BaseDtoTest<DataPointSave>(DataPointSave::class.java)
        @Nested inner class ProvisionDataPointEntityDtoTest : BaseDtoTest<ProvisionDataPoint>(ProvisionDataPoint::class.java)
    }

    @Nested
    @DisplayName("Transforms Module")
    inner class TransformsModule {
        @Nested inner class AppliedTransformEntityEntityArgDtoTest : BaseDtoTest<AppliedTransformArg>(AppliedTransformArg::class.java)
        @Nested inner class AppliedTransformEntityEntityDtoTest : BaseDtoTest<AppliedTransform>(AppliedTransform::class.java)
        @Nested inner class TransformArgTest : BaseDtoTest<TransformArg>(TransformArg::class.java)
        @Nested inner class TransformTest : BaseDtoTest<Transform>(Transform::class.java)
    }

    @Nested
    @DisplayName("Typing Module")
    inner class TypingModule {
        @Nested inner class TypeDeclarationEntityDtoTest : BaseDtoTest<TypeDeclaration>(TypeDeclaration::class.java)
        @Nested inner class TypeDefinitionEntityDtoTest : BaseDtoTest<TypeDefinition>(TypeDefinition::class.java)
        @Nested inner class TypeParameterTest : BaseDtoTest<TypeParameter>(TypeParameter::class.java)
        @Nested inner class TypeSchemaRefTest : BaseDtoTest<TypeSchemaRef>(TypeSchemaRef::class.java)
        @Nested inner class TypeUnionMemberTest : BaseDtoTest<TypeUnionMember>(TypeUnionMember::class.java)
        @Nested inner class TypeVariancesDtoTest : BaseDtoTest<TypeVariance>(TypeVariance::class.java)
    }

    @Nested
    @DisplayName("User Module")
    inner class UserEntityModule {
        @Nested inner class UserEntityDtoTest : BaseDtoTest<User>(User::class.java)
        @Nested inner class PaymentMethodEntityDtoTest : BaseDtoTest<PaymentMethod>(PaymentMethod::class.java)
        @Nested inner class TagEntityDtoTest : BaseDtoTest<Tag>(Tag::class.java)
        @Nested inner class TagEntityRelationDtoTest : BaseDtoTest<TagRelation>(TagRelation::class.java)
    }

    @Nested
    @DisplayName("Workflows Module")
    inner class WorkflowsModule {
        @Nested inner class StepTest : BaseDtoTest<Step>(Step::class.java)
        @Nested inner class WorkflowTest : BaseDtoTest<Workflow>(Workflow::class.java)
        @Nested inner class DataPointEntityDtoTest : BaseDtoTest<DataPoint>(DataPoint::class.java)
        @Nested inner class PhaseTest : BaseDtoTest<Phase>(Phase::class.java)
        @Nested inner class ToolTest : BaseDtoTest<Tool>(Tool::class.java)
        @Nested inner class ToolParamTest : BaseDtoTest<ToolParam>(ToolParam::class.java)
        @Nested inner class WorkflowFolderTest : BaseDtoTest<WorkflowFolder>(WorkflowFolder::class.java)
    }

}
