/**
 * The workflow domain — the elements a user actually arranges.
 *
 * `ULong` ids arrive as JSON numbers. They can exceed `Number.MAX_SAFE_INTEGER` in principle; in
 * practice these are auto-increment keys and will not, so `number` is used rather than paying
 * `bigint`'s serialization cost throughout.
 */

import type { PythonVersion } from './environs'
import type { TypeNode } from './typing'

/** ISO-8601, as `kotlinx.datetime` writes it. */
export type Timestamp = string

/** `#RRGGBB` — the database constrains the column to exactly this. */
export type HexColor = string

export interface WorkflowFolder {
  id: number
  parentFolder: WorkflowFolder | null
  name: string
  description: string | null
  createdAt: Timestamp
  updatedAt: Timestamp
}

export interface Workflow {
  id: number
  name: string
  description: string | null
  pythonVersion: PythonVersion
  parentFolder: WorkflowFolder | null
  createdAt: Timestamp
  updatedAt: Timestamp
}

/**
 * A container on the canvas, not a stage in a sequence.
 *
 * Groupers nest and carry a position of their own. They group **spatially**; they impose no order
 * on what they contain. Sequence comes from `Step.defaultNextStep` and from redirects.
 */
export interface Grouper {
  id: number
  workflow: Workflow
  parentGrouper: Grouper | null
  name: string
  description: string | null
  posX: number
  posY: number
  color: HexColor | null
}

/**
 * One unit of work: a tool invocation placed on the canvas.
 *
 * `defaultNextStep` is the edge the runner follows when nothing redirects it — inquisitions and
 * error watches can route elsewhere at run time. A null one means the workflow may end here.
 * `isWorkflowHead` marks where execution starts; exactly one step per workflow carries it.
 */
export interface Step {
  id: number
  name: string
  description: string | null
  parentGrouper: Grouper | null
  defaultNextStep: Step | null
  posX: number
  posY: number
  color: HexColor | null
  workflow: Workflow
  isWorkflowHead: boolean
  tool: Tool
  createdAt: Timestamp
  updatedAt: Timestamp
}

/** How a data point's value is held. */
export type StoreMethod = 'TEXT' | 'BLOB' | 'FILE'

/**
 * A typed named value living in a workflow.
 *
 * `maxWrites` bounds how many steps may write to it: zero makes it read-only, one makes it
 * write-once. A data point that permits writes but has none is a warning worth surfacing — the
 * workflow declares an intent nothing fulfils.
 */
export interface DataPoint {
  id: number
  workflow: Workflow
  name: string
  description: string | null
  presetValue: string | null
  storeMethod: StoreMethod | null
  typeNode: TypeNode
  maxWrites: number
  createdAt: Timestamp
  updatedAt: Timestamp
}

/** The Python callable a step invokes. `body` is its source. */
export interface Tool {
  id: number
  name: string
  description: string | null
  body: string
  returnTypeNode: TypeNode | null
}

/** A workflow composed of other workflows, run in order. */
export interface Multiflow {
  id: number
  name: string
  description: string | null
}

export interface MultiflowWorkflow {
  multiflow: Multiflow
  workflow: Workflow
  position: number
}

/** A distribution this workflow requires, optionally pinned to a version. */
export interface WorkflowRequirement {
  workflow: Workflow
  requiredDist: string
  /** Null means "install as-is" — no version was pinned. */
  requiredVersion: number | null
}
