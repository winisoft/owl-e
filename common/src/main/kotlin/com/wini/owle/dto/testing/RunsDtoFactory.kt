package com.wini.owle.dto.testing

import com.wini.owle.dto.environs.enums.EnvMarkerExpressionMode
import com.wini.owle.dto.environs.enums.EnvMarkerVariable
import com.wini.owle.dto.environs.marker.EnvMarkerComparison
import com.wini.owle.dto.environs.marker.EnvMarkerExpression
import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.dto.environs.pkg.DistributionPackage
import com.wini.owle.dto.environs.pkg.PackageDistRequirement
import com.wini.owle.dto.environs.version.PythonVersion
import com.wini.owle.dto.environs.version.PythonVersionSpecifier
import com.wini.owle.dto.environs.version.Version
import com.wini.owle.dto.environs.version.VersionOverflow
import com.wini.owle.dto.environs.version.VersionSpecifier
import com.wini.owle.dto.environs.version.enums.OverflowViolation
import com.wini.owle.dto.environs.version.enums.PrereleasePhase
import com.wini.owle.dto.environs.version.enums.VersionOperator
import com.wini.owle.dto.environs.version.enums.WildcardSegment
import com.wini.owle.dto.runs.enums.ComparisonOperator
import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.runs.enums.GenerationMethod
import com.wini.owle.dto.runs.enums.LogLevel
import com.wini.owle.dto.runs.step.StepTestRun
import com.wini.owle.dto.runs.step.ExecutionNotification
import com.wini.owle.dto.runs.test.BenchmarkReport
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.dto.runs.test.TestOutcomeExpectation
import com.wini.owle.dto.runs.test.TestInput
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.dto.runs.workflow.TestOutcomeActual
import com.wini.owle.dto.steps.DataPointInjection
import com.wini.owle.dto.steps.DataPointRead
import com.wini.owle.dto.steps.DataPointWrite
import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.dto.steps.Inquisition
import com.wini.owle.dto.steps.ToolParam
import com.wini.owle.dto.steps.WorkflowJunction
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.tags.TagRelation
import com.wini.owle.dto.tags.enums.TagSource
import com.wini.owle.dto.transforms.Transform
import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.dto.transforms.TransformOccurrence
import com.wini.owle.dto.transforms.TransformOccurrenceArg
import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.dto.typing.TypeParameter
import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.dto.typing.TypeVarBound
import com.wini.owle.dto.typing.TypeVarVariance
import com.wini.owle.dto.typing.enums.VarianceCardinality
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.dto.user.User
import com.wini.owle.dto.user.enums.CardBrand
import com.wini.owle.dto.user.enums.UserStatus
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Grouper
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Tool
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.dto.workflows.enums.StoreMethod
import com.wini.owle.dto.runs.test.TestExpectedOutcome
import com.wini.owle.dto.runs.workflow.TestResult
import com.wini.owle.dto.runs.enums.TestAssertion

/**
 * Canned [runs] DTO instances for tests.
 *
 * One factory per DTO package, composed by [DtoTestInstanceFactory]. Cross-package
 * instances are obtained from the sibling factory that owns them, so each file stays
 * responsible for exactly the types its own package declares.
 */
object RunsDtoFactory {

    fun testCaseDto(
        id: ULong = 1UL,
        user: User = UserDtoFactory.userDto(),
        name: String = "Test Case Name",
        description: String = "Test Case Description",
        workflow: Workflow = WorkflowsDtoFactory.workflowDto(),
        step: Step = WorkflowsDtoFactory.stepDto(),
        inputs: List<TestInput> = emptyList(),
        expectedOutcomes: List<TestExpectedOutcome> = emptyList(),
        results: List<TestResult> = emptyList()
    ) : TestCase {
        return TestCase(
            id = id,
            name = name,
            description = description,
            workflow = workflow,
            step = step,
            inputs = inputs,
            expectedOutcomes = expectedOutcomes,
            results = results
        )
    }

    fun workflowExecutionDto(
        id: ULong = 1UL,
        workflow: Workflow = WorkflowsDtoFactory.workflowDto(),
        user: User = UserDtoFactory.userDto(),
        startedAt: String = "2024-12-21T10:00:00",
        completedAt: String = "2024-12-21T10:00:00",
        status: ExecutionStatus = ExecutionStatus.FAILED,
        stepTestRuns: List<StepTestRun> = emptyList()
    ) : WorkflowExecution {
        return WorkflowExecution(
            id = id,
            workflow = workflow,
            startedAt = startedAt,
            completedAt = completedAt,
            status = status,
        )
    }

    fun benchmarkReportDto(
        id: ULong = 1UL,
        workflowExecution: WorkflowExecution = workflowExecutionDto(),
        totalExecutionTime: Double = 123.45,
        totalSteps: Int = 35,
        aiDeterminedSteps: Int = 4,
        preDeterminedSteps: Int = 8
    ) : BenchmarkReport {
        return BenchmarkReport(
            id = id,
            workflowExecution = workflowExecution
        )
    }

    fun executionLogDto(
        id: ULong = 1UL,
        stepTestRun: StepTestRun = stepRun(),
        timeStamp: String = "2024-12-21T12:00:00Z",
        logLevel: LogLevel = LogLevel.INFO,
        message: String = "Execution Started"
    ) : ExecutionNotification {
        return ExecutionNotification(
            id = id,
            stepTestRun = stepTestRun,
            timestamp = timeStamp,
            logLevel = logLevel,
            message = message
        )
    }

    fun stepRunLogLine(
        id: ULong = 1UL,
        stepTestRun: StepTestRun = stepRun(),
        timestamp: String = "2024-12-21T10:00:00",
        logLevel: LogLevel = LogLevel.INFO,
        message: String = "text ok test text"
    ) : ExecutionNotification {
        return ExecutionNotification(
            id = id,
            stepTestRun = stepTestRun,
            timestamp = timestamp,
            logLevel = logLevel,
            message = message
        )
    }

    fun stepRun(
        id: ULong = 1UL,
        workflowExecution: WorkflowExecution = workflowExecutionDto(),
        step: Step = WorkflowsDtoFactory.stepDto(),
        startedAt: String = "2024-12-21T10:00:00",
        completedAt: String = "2024-12-21T10:00:00",
        status: ExecutionStatus = ExecutionStatus.FAILED
    ) : StepTestRun {
        return StepTestRun(
            id = id,
            step = step,
            started = startedAt,
            completed = completedAt,
            status = status
        )
    }

    fun testInputDto(
        id: ULong = 6UL,
        dataPoint: DataPoint = WorkflowsDtoFactory.dataPointDto(),
        value: String = "value1",
        generationMethod: GenerationMethod = GenerationMethod.MANUAL,
        testCase: TestCase = testCaseDto()
    ): TestInput {
        return TestInput(
            id = id,
            dataPoint = dataPoint,
            value = value,
            testCase = testCase
        )
    }

    fun testExpectedOutcomeDto(
        id: ULong = 7UL,
        dataPoint: DataPoint = WorkflowsDtoFactory.dataPointDto(),
        expectedValue: String = "expectedValue1",
        operator: ComparisonOperator = ComparisonOperator.EQUALS,
        testCase: TestCase = testCaseDto()
    ): TestExpectedOutcome {
        return TestExpectedOutcome(
            id = id,
            testCase = testCase,
            dataPoint = dataPoint,
            expectedValue = expectedValue,
            operator = operator
        )
    }

    fun testResultDto(
        id: ULong = 1UL,
        testCase: TestCase = testCaseDto(),
        workflowExecution: WorkflowExecution = workflowExecutionDto(),
        passed: Boolean = true,
        executedAt: String = "2024-12-21T00:00:00Z"
    ): TestResult {
        return TestResult(
            id = id,
            testCase = testCase,
            workflowExecution = workflowExecution,
            passed = passed,
            executedAt = executedAt
        )
    }

    fun testOutcomeExpectationDto(
        id: ULong = 1UL,
        dataPoint: DataPoint = WorkflowsDtoFactory.dataPointDto(),
        assertion: TestAssertion = TestAssertion.EQUALS
    ): TestOutcomeExpectation = TestOutcomeExpectation(
        id = id,
        dataPoint = dataPoint,
        assertion = assertion
    )

    fun testOutcomeActualDto(
        id: ULong = 1UL,
        dataPoint: DataPoint? = WorkflowsDtoFactory.dataPointDto(),
        passed: Boolean = true
    ): TestOutcomeActual = TestOutcomeActual(
        id = id,
        dataPoint = dataPoint,
        passed = passed
    )
}
