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

/**
 * Canned [steps] DTO instances for tests.
 *
 * One factory per DTO package, composed by [DtoTestInstanceFactory]. Cross-package
 * instances are obtained from the sibling factory that owns them, so each file stays
 * responsible for exactly the types its own package declares.
 */
object StepsDtoFactory {

    fun toolParamDto(
        id: ULong = 1UL,
        name: String = "Tool Param",
        typeDeclaration: TypeNode = TypingDtoFactory.typeNode(),
        tool: Tool = WorkflowsDtoFactory.toolDto(),
        hasDefault: Boolean = true
    ) : ToolParam {
        return ToolParam(
            id = id,
            name = name,
            typeDeclaration = typeDeclaration,
            tool = tool
        )
    }

    fun errorWatchDto(
        id: ULong = 11UL,
        step: Step = WorkflowsDtoFactory.stepDto(),
        errorType: String = "RuntimeException",
        triggerStep: Step = WorkflowsDtoFactory.stepDto()
    ) : ErrorWatch {
        return ErrorWatch(
            id = id,
            step = step,
            errorType = errorType,
            triggerStep = triggerStep
        )
    }

    fun errorWatchFnDto(
        id: ULong = 1UL,
        errorWatch: ErrorWatch = errorWatchDto(),
        functionNode: TypeNode = TypingDtoFactory.typeNode(),
        position: ULong = 1uL,
    ) : ErrorWatchFn {
        return ErrorWatchFn(
            id = id,
            errorWatch = errorWatch,
            functionNode = functionNode,
            position = position
        )
    }

    fun inquisitionDto(
        id: ULong = 1UL,
        step: Step = WorkflowsDtoFactory.stepDto(),
        condition: TypeNode = TypingDtoFactory.typeNode(),
        dataPoint: DataPoint = WorkflowsDtoFactory.dataPointDto(),
        executionOrder: ULong = 1UL
    ) : Inquisition {
        return Inquisition(
            id = id,
            step = step,
            executionOrder = executionOrder
        )
    }

    fun dataPointInjectionDto(
        id: ULong = 1UL,
        junction: WorkflowJunction = workflowJunctionDto(),
        sourceDataPoint: DataPoint = WorkflowsDtoFactory.dataPointDto(),
        injectedDataPoint: DataPoint = WorkflowsDtoFactory.dataPointDto(),
        name: String = "Name",
        description: String? = "Description"
    ) : DataPointInjection {
        return DataPointInjection(
            id = id,
            junction = junction,
            sourceDataPoint = sourceDataPoint,
            injectedDataPoint = injectedDataPoint,
            name = name,
            description = description
        )
    }

    fun workflowJunctionDto(
        id: ULong = 1UL,
        sourceStep: Step = WorkflowsDtoFactory.stepDto(),
        targetWorkflow: Workflow = WorkflowsDtoFactory.workflowDto(),
        triggerInquisition: Inquisition = inquisitionDto()
    ) : WorkflowJunction {
        return WorkflowJunction(
            id = id,
            sourceStep = sourceStep,
            targetWorkflow = targetWorkflow,
        )
    }

    fun dataPointReadDto(
        id: ULong = 1UL,
        step: Step = WorkflowsDtoFactory.stepDto(),
        toolParam: ToolParam = toolParamDto(),
        dataPoints: List<DataPoint> = emptyList()
    ) : DataPointRead {
        return DataPointRead(
            id = id
        )
    }

    fun dataPointWriteDto(
        id: ULong = 1UL,
        dataPoint: DataPoint = WorkflowsDtoFactory.dataPointDto(),
        step: Step = WorkflowsDtoFactory.stepDto()
    ) : DataPointWrite {
        return DataPointWrite(
            id = id,
            dataPoint = dataPoint,
            step = step,
            transformOccurrence = null
        )
    }
}
