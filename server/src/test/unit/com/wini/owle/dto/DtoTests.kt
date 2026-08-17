package com.wini.owle.dto

import com.wini.owle.dto.steps.*
import com.wini.owle.dto.transforms.*
import com.wini.owle.dto.typing.*
import com.wini.owle.dto.user.*
import com.wini.owle.dto.workflows.*
import com.wini.owle.dto.runs.step.ExecutionNotification
import com.wini.owle.dto.runs.test.*
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.dto.runs.workflow.TestOutcomeActual
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.tags.TagRelation
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@Nested
@DisplayName("DTO Tests")
open class DtoTest {

    @Nested
    @DisplayName("Environs Module")
    inner class EnvironsModule {

    }

    @Nested
    @DisplayName("Runs Module")
    inner class RunsModule {
        @Nested inner class TestCaseDtoTestEntity : BaseDtoTest<TestCase>(TestCase::class.java)
        @Nested inner class TestInputDtoTestEntity : BaseDtoTest<TestInput>(TestInput::class.java)
        @Nested inner class TestExpectedOutcomeDtoTest : BaseDtoTest<TestOutcomeExpectation>(TestOutcomeExpectation::class.java)
        @Nested inner class TestResultDtoWorkflowTest : BaseDtoTest<TestOutcomeActual>(TestOutcomeActual::class.java)
        @Nested inner class BenchmarkReportTest : BaseDtoTest<BenchmarkReport>(BenchmarkReport::class.java)
        @Nested inner class StepRunLogLineTest : BaseDtoTest<ExecutionNotification>(ExecutionNotification::class.java)
        @Nested inner class WorkflowExecutionTest : BaseDtoTest<WorkflowExecution>(WorkflowExecution::class.java)
    }

    @Nested
    @DisplayName("Steps Module")
    inner class StepsModule {
        @Nested inner class ErrorWatchEntityDtoTest : BaseDtoTest<ErrorWatch>(ErrorWatch::class.java)
        @Nested inner class ErrorWatchEntityFnDtoTest : BaseDtoTest<ErrorWatchFn>(ErrorWatchFn::class.java)
        @Nested inner class InquisitionEntityDtoTest : BaseDtoTest<Inquisition>(Inquisition::class.java)
        @Nested inner class InitialDataPointInjectorTest : BaseDtoTest<DataPointInjection>(DataPointInjection::class.java)
        @Nested inner class ProvisionTest : BaseDtoTest<DataPointRead>(DataPointRead::class.java)
        @Nested inner class DataPointSaveTest : BaseDtoTest<DataPointWrite>(DataPointWrite::class.java)
    }

    @Nested
    @DisplayName("Transforms Module")
    inner class TransformsModule {
        @Nested inner class AppliedTransformEntityEntityArgDtoTest : BaseDtoTest<TransformOccurrenceArg>(TransformOccurrenceArg::class.java)
        @Nested inner class AppliedTransformEntityEntityDtoTest : BaseDtoTest<TransformOccurrence>(TransformOccurrence::class.java)
        @Nested inner class TransformArgTest : BaseDtoTest<TransformArg>(TransformArg::class.java)
        @Nested inner class TransformTest : BaseDtoTest<Transform>(Transform::class.java)
    }

    @Nested
    @DisplayName("Typing Module")
    inner class TypingModule {
        @Nested inner class TypeDeclarationEntityDtoTest : BaseDtoTest<TypeNode>(TypeNode::class.java)
        @Nested inner class TypeParameterTest : BaseDtoTest<TypeParameter>(TypeParameter::class.java)
        @Nested inner class TypeSchemaRefTest : BaseDtoTest<TypeSchemaRef>(TypeSchemaRef::class.java)
        @Nested inner class TypeUnionMemberTest : BaseDtoTest<TypeUnionMember>(TypeUnionMember::class.java)
        @Nested inner class TypeVariancesDtoTest : BaseDtoTest<TypeVarVariance>(TypeVarVariance::class.java)
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
        @Nested
        open inner class WorkflowTest : BaseDtoTest<Workflow>(Workflow::class.java)
        @Nested inner class DataPointEntityDtoTest : BaseDtoTest<DataPoint>(DataPoint::class.java)
        @Nested inner class PhaseTest : BaseDtoTest<Grouper>(Grouper::class.java)
        @Nested inner class ToolTest : BaseDtoTest<Tool>(Tool::class.java)
        @Nested inner class ToolParamTest : BaseDtoTest<ToolParam>(ToolParam::class.java)
        @Nested inner class WorkflowFolderTest : BaseDtoTest<WorkflowFolder>(WorkflowFolder::class.java)
    }

}
