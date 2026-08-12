package com.wini.owle.gen.model

import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.WorkflowRequirement

/**
 * A Multiflow is the unit of execution generation.
 *
 * It is one **root** workflow that is guaranteed to run, plus the closure of workflows it
 * *may* hand off to through junctions. Because workflows are reusable and the same workflow
 * can belong to many Multiflows, junction-reachability is a Multiflow-level fact assembled
 * from the junction wiring at build time — it is never a property stored on a Workflow.
 *
 * A Multiflow (not a Workflow) is what compiles to a runnable Python program. See
 * docs/EXECUTION-GENERATION.md.
 */
data class Multiflow(
    val id: Long,
    val name: String,
    val description: String?,

    /** The workflow guaranteed to execute first; its head step is the program's entry point. */
    val rootWorkflowId: Long
)

/**
 * The complete, pre-resolved input to Python generation for an entire Multiflow.
 *
 * Every member workflow arrives fully detailed, so the emitter is a mechanical walk: all of
 * the reasoning that requires domain knowledge happens in the *builder* that produces this
 * structure, not in the code that emits from it.
 */
data class MultiflowBlueprint(
    val multiflow: Multiflow,

    /**
     * The root workflow (isRoot = true) first, followed by every workflow reachable from it
     * through junctions. A workflow appears once regardless of how many junctions target it.
     */
    val workflows: List<WorkflowExecutionDetail>
) {
    val root: WorkflowExecutionDetail get() = workflows.first { it.isRoot }
}

/**
 * One member workflow of a Multiflow, fully resolved for emission.
 *
 * This supersedes the workflow-rooted `ExecutionBlueprint` in Members.kt: that type described
 * a single workflow as if it were the whole world, which is wrong once junctions exist. The
 * per-step detail it referenced ([StepExecutionDetail]) is unchanged and reused here — only
 * the root moved up from Workflow to Multiflow.
 */
data class WorkflowExecutionDetail(
    val workflowId: Long,
    val name: String,

    /** True for the Multiflow's guaranteed entry workflow. */
    val isRoot: Boolean,

    /**
     * The `python_versions.id` this workflow declares. The Multiflow must reconcile these
     * across members into a single container image (see EXECUTION-GENERATION.md §6).
     */
    val pythonVersionId: Long,

    /** This workflow's declared package requirements; unioned across the Multiflow for the image. */
    val packageRequirements: List<WorkflowRequirement>,

    /** All datapoints in this workflow's scope, whether read or written. */
    val dataPoints: List<DataPoint>,

    /** The head step: where this workflow begins when entered — as the root, or via a junction. */
    val entryStepId: Long,

    /** Fully-annotated step execution units, in no required order (routing is explicit per step). */
    val steps: List<StepExecutionDetail>
)
