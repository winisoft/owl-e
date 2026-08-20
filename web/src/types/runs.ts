/**
 * Executions, logs, and tests — what happened when a workflow actually ran.
 */

import type { DataPoint, Step, Workflow } from './workflow'
import type { Inquisition } from './steps'

/** Shared by workflow executions and step test runs. */
export type ExecutionStatus =
  | 'READY'
  | 'RUNNING'
  | 'COMPLETED'
  | 'ERRORED'
  | 'FAILED'
  | 'CANCELLED'

export type ExecutionLogLevel = 'TRACE' | 'DEBUG' | 'INFO' | 'WARN' | 'ERROR'

/** What kind of thing happened. The vocabulary of the execution timeline. */
export type ExecutionEventKind =
  | 'RUN_STARTED'
  | 'WORKFLOW_ENTERED'
  | 'STEP_ENTERED'
  | 'PARAM_RESOLVED'
  | 'TOOL_INVOKED'
  | 'TOOL_RETURNED'
  | 'GUARDED_OK'
  | 'ERROR_WATCH_FIRED'
  | 'DATA_POINT_WRITTEN'
  | 'INQUISITION_EVALUATED'
  | 'ROUTE_TAKEN'
  | 'JUNCTION_TAKEN'
  | 'INJECTION_MIGRATED'
  | 'WORKFLOW_ENDED'
  | 'MULTIFLOW_COMPLETED'
  | 'RUN_FAILED'
  | 'LOG'

export interface WorkflowExecution {
  id: number
  workflow: Workflow
  startedAt: string
  completedAt: string | null
  status: ExecutionStatus
}

/**
 * One entry in an execution's timeline.
 *
 * `seq` orders entries within a run; timestamps alone are not enough because events inside one
 * step can share a millisecond. The nullable subject references say *what the entry is about*, and
 * which of them is set depends on `eventKind`.
 */
export interface ExecutionLog {
  id: number
  workflowExecution: WorkflowExecution
  seq: number
  loggedAt: string
  eventKind: ExecutionEventKind
  logLevel: ExecutionLogLevel
  workflow: Workflow | null
  step: Step | null
  inquisition: Inquisition | null
  dataPoint: DataPoint | null
  detail: string | null
  message: string | null
}

/** Running one step in isolation, which is how a step is tested without its workflow. */
export interface StepTestRun {
  id: number
  step: Step
  started: string | null
  completed: string | null
  status: ExecutionStatus
}

export interface ExecutionNotification {
  id: number
  stepTestRun: StepTestRun
  timestamp: string
  logLevel: ExecutionLogLevel
  message: string
}

// ---------------------------------------------------------------------------------------------
// tests
// ---------------------------------------------------------------------------------------------

/** How an expected outcome is compared against what actually happened. */
export type ComparisonOperator =
  | 'EQUALS'
  | 'NOT_EQUALS'
  | 'CONTAINS'
  | 'GREATER_THAN'
  | 'LESS_THAN'

/** Exactly one of `workflow` or `step` is set — a case tests one or the other. */
export interface TestCase {
  id: number
  name: string
  description: string | null
  workflow: Workflow | null
  step: Step | null
  inputs: TestInput[]
  expectedOutcomes: TestExpectedOutcome[]
  results: TestResult[]
}

/** A data point seeded before the run. */
export interface TestInput {
  id: number
  testCase: TestCase
  dataPoint: DataPoint
  value: string | null
}

/** An assertion about a data point after the run. */
export interface TestExpectedOutcome {
  id: number
  testCase: TestCase
  dataPoint: DataPoint
  expectedValue: string | null
  operator: ComparisonOperator
}

export interface TestResult {
  id: number
  testCase: TestCase
  workflowExecution: WorkflowExecution
  passed: boolean
  executedAt: string
}

export interface BenchmarkReport {
  id: number
  workflowExecution: WorkflowExecution
}
