package com.wini.owle.dto

import com.wini.owle.dto.testing.EnvironsDtoFactory
import com.wini.owle.dto.testing.RunsDtoFactory
import com.wini.owle.dto.testing.StepsDtoFactory
import com.wini.owle.dto.testing.TagsDtoFactory
import com.wini.owle.dto.testing.TransformsDtoFactory
import com.wini.owle.dto.testing.TypingDtoFactory
import com.wini.owle.dto.testing.UserDtoFactory
import com.wini.owle.dto.testing.WorkflowsDtoFactory
import com.wini.owle.dto.*

/**
 * Canned DTO instances for tests.
 *
 * The instances themselves live in one factory per DTO package (see
 * [com.wini.owle.dto.testing]); this object only composes them and resolves a DTO class to the
 * factory that builds it. Splitting it that way keeps each file responsible for a single
 * package's types instead of one file tracking every type in the domain.
 *
 * Every branch below is keyed by a [BaseDto] subtype. That is load-bearing: the registry used to
 * carry an entry keyed by an *enum* of a similar name, which made [createDtoInstance] fail to
 * compile in a way that pointed nowhere useful.
 */
object DtoTestInstanceFactory {

    val environs = EnvironsDtoFactory
    val runs = RunsDtoFactory
    val steps = StepsDtoFactory
    val tags = TagsDtoFactory
    val transforms = TransformsDtoFactory
    val typing = TypingDtoFactory
    val user = UserDtoFactory
    val workflows = WorkflowsDtoFactory

    @Suppress("UNCHECKED_CAST")
    fun <T : BaseDto> createDtoInstance(dtoClass: Class<T>): T {
        return when (dtoClass) {
            com.wini.owle.dto.environs.marker.EnvMarkerComparison::class.java -> EnvironsDtoFactory.envMarkerComparison() as T
            com.wini.owle.dto.environs.marker.EnvMarkerExpression::class.java -> EnvironsDtoFactory.envMarkerExpression() as T
            com.wini.owle.dto.environs.pkg.Distribution::class.java -> EnvironsDtoFactory.distribution() as T
            com.wini.owle.dto.environs.pkg.DistributionPackage::class.java -> EnvironsDtoFactory.distributionPackage() as T
            com.wini.owle.dto.environs.pkg.PackageDistRequirement::class.java -> EnvironsDtoFactory.packageRequiredDist() as T
            com.wini.owle.dto.environs.version.PythonVersion::class.java -> EnvironsDtoFactory.pythonVersion() as T
            com.wini.owle.dto.environs.version.PythonVersionSpecifier::class.java -> EnvironsDtoFactory.pythonVersionSpecifier() as T
            com.wini.owle.dto.environs.version.Version::class.java -> EnvironsDtoFactory.version() as T
            com.wini.owle.dto.environs.version.VersionOverflow::class.java -> EnvironsDtoFactory.versionOverflow() as T
            com.wini.owle.dto.runs.step.ExecutionNotification::class.java -> RunsDtoFactory.executionLogDto() as T
            com.wini.owle.dto.runs.step.StepTestRun::class.java -> RunsDtoFactory.stepRun() as T
            com.wini.owle.dto.runs.test.BenchmarkReport::class.java -> RunsDtoFactory.benchmarkReportDto() as T
            com.wini.owle.dto.runs.test.TestCase::class.java -> RunsDtoFactory.testCaseDto() as T
            com.wini.owle.dto.runs.test.TestExpectedOutcome::class.java -> RunsDtoFactory.testExpectedOutcomeDto() as T
            com.wini.owle.dto.runs.test.TestInput::class.java -> RunsDtoFactory.testInputDto() as T
            com.wini.owle.dto.runs.test.TestOutcomeExpectation::class.java -> RunsDtoFactory.testOutcomeExpectationDto() as T
            com.wini.owle.dto.runs.workflow.TestOutcomeActual::class.java -> RunsDtoFactory.testOutcomeActualDto() as T
            com.wini.owle.dto.runs.workflow.TestResult::class.java -> RunsDtoFactory.testResultDto() as T
            com.wini.owle.dto.runs.workflow.WorkflowExecution::class.java -> RunsDtoFactory.workflowExecutionDto() as T
            com.wini.owle.dto.steps.DataPointInjection::class.java -> StepsDtoFactory.dataPointInjectionDto() as T
            com.wini.owle.dto.steps.DataPointRead::class.java -> StepsDtoFactory.dataPointReadDto() as T
            com.wini.owle.dto.steps.DataPointWrite::class.java -> StepsDtoFactory.dataPointWriteDto() as T
            com.wini.owle.dto.steps.ErrorWatch::class.java -> StepsDtoFactory.errorWatchDto() as T
            com.wini.owle.dto.steps.ErrorWatchFn::class.java -> StepsDtoFactory.errorWatchFnDto() as T
            com.wini.owle.dto.steps.Inquisition::class.java -> StepsDtoFactory.inquisitionDto() as T
            com.wini.owle.dto.steps.ToolParam::class.java -> StepsDtoFactory.toolParamDto() as T
            com.wini.owle.dto.steps.WorkflowJunction::class.java -> StepsDtoFactory.workflowJunctionDto() as T
            com.wini.owle.dto.tags.Tag::class.java -> TagsDtoFactory.tagDto() as T
            com.wini.owle.dto.tags.TagRelation::class.java -> TagsDtoFactory.tagRelationDto() as T
            com.wini.owle.dto.transforms.Transform::class.java -> TransformsDtoFactory.transformDto() as T
            com.wini.owle.dto.transforms.TransformArg::class.java -> TransformsDtoFactory.transformArgDto() as T
            com.wini.owle.dto.transforms.TransformOccurrence::class.java -> TransformsDtoFactory.transformOccurrenceDto() as T
            com.wini.owle.dto.transforms.TransformOccurrenceArg::class.java -> TransformsDtoFactory.transformOccurrenceArgDto() as T
            com.wini.owle.dto.transforms.TransformTargetType::class.java -> TransformsDtoFactory.transformTargetType() as T
            com.wini.owle.dto.typing.TypeNode::class.java -> TypingDtoFactory.typeNode() as T
            com.wini.owle.dto.typing.TypeSchemaRef::class.java -> TypingDtoFactory.typeSchemaRefDto() as T
            com.wini.owle.dto.typing.TypeUnionMember::class.java -> TypingDtoFactory.typeUnionMember() as T
            com.wini.owle.dto.typing.TypeVarBound::class.java -> TypingDtoFactory.typeVarBoundDto() as T
            com.wini.owle.dto.typing.TypeVarVariance::class.java -> TypingDtoFactory.typeVarianceDto() as T
            com.wini.owle.dto.user.PaymentMethod::class.java -> UserDtoFactory.paymentMethodDto() as T
            com.wini.owle.dto.user.User::class.java -> UserDtoFactory.userDto() as T
            com.wini.owle.dto.workflows.DataPoint::class.java -> WorkflowsDtoFactory.dataPointDto() as T
            com.wini.owle.dto.workflows.Grouper::class.java -> WorkflowsDtoFactory.grouperDto() as T
            com.wini.owle.dto.workflows.Step::class.java -> WorkflowsDtoFactory.stepDto() as T
            com.wini.owle.dto.workflows.Tool::class.java -> WorkflowsDtoFactory.toolDto() as T
            com.wini.owle.dto.workflows.Workflow::class.java -> WorkflowsDtoFactory.workflowDto() as T
            com.wini.owle.dto.workflows.WorkflowFolder::class.java -> WorkflowsDtoFactory.workflowFolderDto() as T
            else -> throw IllegalArgumentException("Unsupported DTO type: ${dtoClass.simpleName}")
        }
    }
}
