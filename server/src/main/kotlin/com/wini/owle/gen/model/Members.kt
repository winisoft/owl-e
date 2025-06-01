package com.wini.owle.gen.model

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.steps.DataPointRead
import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.dto.transforms.TransformOccurrence
import com.wini.owle.dto.transforms.TransformOccurrenceArg
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Tool


/**
 * ExecutionBlueprint represents the complete semantic surface of a single workflow.
 * It includes all steps, associated datapoints, and any reachable external workflows.
 *
 * This structure exists to support Python code generation by exposing everything
 * that might influence execution or require logic branching.
 */
data class ExecutionBlueprint(
    val workflowId: Long,
    val name: String,

    /** All datapoints available in this workflow scope, whether read or written */
    val dataPoints: List<DataPoint>,

    /** The full list of step execution units for this workflow, each fully annotated */
    val steps: List<StepExecutionDetail>,

    /** Any other workflows this one might jump to under conditional or indirect circumstances */
    val reachableWorkflows: List<WorkflowJumpTarget>
)

/**
 * A reference to another workflow this one may invoke (jump to) during execution.
 * These jumps might result from inquisition answers, escalated errors, or other logic.
 */
data class WorkflowJumpTarget(
    val targetWorkflowId: Long,

    /** The entry step of the target workflow that this jump would land on */
    val entryStepId: Long,

    /** A human-readable indication of what motivated the jump (e.g., "inquisitionBranch") */
    val reason: String
)

/**
 * Fully-detailed representation of a single step in the workflow's execution logic.
 * Captures all operational, transitional, and failure-resolution influences.
 */
data class StepExecutionDetail(
    val step: Step,

    /** The tool invoked during this step */
    val tool: Tool,

    /** Inputs to the tool, resolved via bindings to datapoint reads */
    val toolParamResolutions: List<ToolParamResolution>,

    /** How the tool's result will affect data, including what transform (if any) applies */
    val resultResolutions: List<ResultResolution>,

    /** Error watches bound to this step, and their active filter functions */
    val errorWatches: List<ErrorWatchDetail>,

    /** The default next step after this one, if no branching condition interferes */
    val defaultNext: StepDefaultRoute?,

    /** Inquisition-based conditional step transitions, keyed by answer-matching conditions */
    val inquisitionBranches: List<StepDefaultRoute>,

    /** Any jumps out of this workflow entirely that might occur from this step */
    val crossWorkflowRoutes: List<WorkflowJumpTarget>
)

/**
 * A possible route to another step, which may be conditional.
 * If condition is null, this is considered a default or fallback transition.
 */
data class StepDefaultRoute(

    val refStep: Step,

    val defaultNext: Step

): CompositeDto {

    override val owleId: String = ""
}

/**
 * Conditions that influence step routing.
 * More cases may be added as new workflow routing logic is introduced.
 */
sealed interface RouteCondition {

    /** Route taken when a specific inquisition produces a matching answer */
    data class InquisitionMatch(
        val inquisitionId: Long,
        val answerValue: String
    ) : RouteCondition

    /** Route taken when a specific error watch is triggered */
    data class ErrorTrigger(
        val errorWatchId: Long
    ) : RouteCondition
}

/**
 * A binding from a tool input to a datapoint read.
 * This provides the data needed to call a tool within a step.
 */
data class ToolParamResolution(
    val dataPointRead: DataPointRead
)

/**
 * Specifies how a step's result updates a datapoint, optionally via a transform.
 * This informs the generator what post-processing or mutation needs to happen.
 */
data class ResultResolution(
    val dataPoint: DataPoint,
    val transformOccurrence: TransformOccurrence,
    val transformOccurrenceArgs: List<TransformOccurrenceArg>
)

/**
 * Describes a configured error watch for a step.
 * If any function filters match runtime conditions, a branch or jump may occur.
 */
data class ErrorWatchDetail(
    val errorWatch: ErrorWatch,
    val fnFilters: List<ErrorWatchFn>
)

