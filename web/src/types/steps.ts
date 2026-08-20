/**
 * What lives inside a step: routing, conditions, failure handling, and the reads and writes that
 * move values between steps and data points.
 */

import type { DataPoint, Step, Tool, Workflow } from './workflow'
import type { CallableParameter, TypeNode } from './typing'
import type { TransformOccurrence } from './transforms'

// ---------------------------------------------------------------------------------------------
// routing
// ---------------------------------------------------------------------------------------------

/**
 * A branch from one workflow into another.
 *
 * The source step hands control to the target workflow. Data crosses the boundary through
 * [DataPointInjection], since the two workflows have separate data stores.
 */
export interface WorkflowJunction {
  id: number
  sourceStep: Step
  targetWorkflow: Workflow
}

/**
 * A condition evaluated at a step, which may redirect execution.
 *
 * `executionOrder` matters: a step's inquisitions are evaluated in sequence and the first to
 * redirect wins, so the order is part of the meaning rather than a display preference.
 */
export interface Inquisition {
  id: number
  step: Step
  executionOrder: number
}

/** Where an inquisition sends execution when it fires — to a step in some workflow. */
export interface InquisitionStepRedirect {
  id: number
  step: Step
  workflow: Workflow
}

/** Or across a junction, into another workflow entirely. */
export interface InquisitionJunctionRedirect {
  id: number
  junction: WorkflowJunction
  step: Step
}

// ---------------------------------------------------------------------------------------------
// failure handling
// ---------------------------------------------------------------------------------------------

/**
 * Watches a step for a named failure and routes execution when it occurs.
 *
 * `errorType` is the Python exception name as text — `HTTPError`, `TimeoutError` — because the
 * exception hierarchy the workflow will encounter belongs to the environment, not to Owl-E.
 */
export interface ErrorWatch {
  id: number
  step: Step
  errorType: string
  /** Where execution goes when the error fires. */
  triggerStep: Step
}

/**
 * A function run as part of handling the watched error, in `position` order.
 *
 * Logging, cleanup, notification — the things that happen *before* control moves to the trigger
 * step, rather than instead of it.
 */
export interface ErrorWatchFn {
  id: number
  errorWatch: ErrorWatch
  functionNode: TypeNode
  position: number
}

// ---------------------------------------------------------------------------------------------
// reads and writes
// ---------------------------------------------------------------------------------------------

/**
 * A value flowing *into* a callable parameter.
 *
 * Exactly one of [step] or [inquisition] is set — the database enforces it — because a read is
 * fulfilling a parameter for one or the other. `transformOccurrence` is present when the data
 * point's type had to be converted to reach the parameter's type.
 */
export interface DataPointRead {
  id: number
  step: Step | null
  inquisition: Inquisition | null
  callableParameter: CallableParameter
  dataPoint: DataPoint
  transformOccurrence: TransformOccurrence | null
}

/**
 * A value flowing *out of* a step into a data point.
 *
 * A data point's `maxWrites` bounds how many of these may exist, which is what makes a data point
 * read-only when the bound is zero.
 */
export interface DataPointWrite {
  id: number
  dataPoint: DataPoint
  step: Step
  transformOccurrence: TransformOccurrence | null
}

/** An ordered argument to a write, where the written value is assembled from several sources. */
export interface DataPointWriteArg {
  dataPointWrite: DataPointWrite
  dataPoint: DataPoint
  position: number
}

/**
 * Carrying a value across a junction.
 *
 * Two workflows have separate data stores, so a value that must survive the crossing is injected:
 * `sourceDataPoint` in the origin workflow becomes `injectedDataPoint` in the target.
 */
export interface DataPointInjection {
  id: number
  junction: WorkflowJunction
  sourceDataPoint: DataPoint
  injectedDataPoint: DataPoint
  name: string | null
  description: string | null
}

// ---------------------------------------------------------------------------------------------
// tool parameters
// ---------------------------------------------------------------------------------------------

/** A named, typed parameter on a tool — what a [DataPointRead] fulfils. */
export interface ToolParam {
  id: number
  name: string
  tool: Tool
  typeDeclaration: TypeNode
}
