/**
 * The universal element model.
 *
 * Every workflow-configuration element — folder, workflow, grouper, step, data point — is presented
 * by the same card, so the card is written once against this shape rather than once per type. What
 * differs between types is *data*: which icon, which facts, which actions. Keeping that as data
 * rather than as branching is what stops the card from becoming six cards.
 *
 * These are **view models**, not DTOs. `src/types/workflow.ts` holds the shapes the API sends; this
 * holds what the UI needs to draw, which is a different and usually smaller thing. A card needs
 * "contains 10 groupers (3 nested)"; it does not need the groupers.
 */

import type { HexColor } from '../types/workflow'

// ---------------------------------------------------------------------------------------------
// identity
// ---------------------------------------------------------------------------------------------

/** Every type that gets a card. */
export type ElementKind =
  | 'workflowFolder'
  | 'workflow'
  | 'grouper'
  | 'step'
  | 'dataPoint'
  | 'inquisition'
  | 'errorWatch'
  | 'junction'
  | 'tool'
  | 'transform'
  | 'multiflow'
  | 'tag'

/**
 * Enough to address an element.
 *
 * Composite-keyed types (tags, junction members) do not currently get cards; when they do this
 * needs a second shape, because their id is several named columns rather than a number.
 */
export interface ElementRef {
  kind: ElementKind
  id: number
}

export const refEquals = (a: ElementRef, b: ElementRef): boolean =>
  a.kind === b.kind && a.id === b.id

/** The icon asset for each kind. `phase` is the historical name for grouper. */
export const ICON_FOR: Record<ElementKind, string> = {
  workflowFolder: 'folder',
  workflow: 'workflow',
  grouper: 'phase',
  step: 'step',
  dataPoint: 'data_item',
  inquisition: 'inspector',
  errorWatch: 'error_watch',
  junction: 'junction',
  tool: 'function',
  transform: 'functions',
  multiflow: 'workflows',
  tag: 'star',
}

/** A step that begins its workflow carries a different icon. */
export const iconForStep = (isWorkflowHead: boolean): string =>
  isWorkflowHead ? 'workflow_head' : 'step'

// ---------------------------------------------------------------------------------------------
// what a card says about its contents
// ---------------------------------------------------------------------------------------------

/**
 * A "contains x of these" fact.
 *
 * [total] counts everything beneath, at any depth. [nested] counts only what sits inside a child
 * rather than directly here. When nesting only goes one level, [nested] is zero and the phrasing
 * drops the parenthetical — which is exactly the rule [describeCount] implements.
 */
export interface ContainedCount {
  kind: ElementKind
  total: number
  nested: number
}

/** `contains 10 groupers` — or `contains 10 groupers (3 nested)` once nesting runs deeper. */
export function describeCount(count: ContainedCount, plural: string): string {
  return count.nested > 0
    ? `contains ${count.total} ${plural} (${count.nested} nested)`
    : `contains ${count.total} ${plural}`
}

/**
 * A line of body text on a card.
 *
 * `tone` exists for the one specified case where a fact is a warning rather than a statement — a
 * data point that permits writes but has no step writing to it.
 */
export interface Fact {
  text: string
  tone?: 'normal' | 'muted' | 'warning'
}

// ---------------------------------------------------------------------------------------------
// view size and edit mode
// ---------------------------------------------------------------------------------------------

export type ViewSize = 'compact' | 'medium' | 'full'

/** Which resize buttons a card offers, given where it is now. */
export function sizeActionsFor(size: ViewSize): ActionId[] {
  switch (size) {
    case 'compact':
      return ['expandToMedium', 'expandToFull']
    case 'medium':
      return ['collapseToCompact', 'expandToFull']
    case 'full':
      return ['collapseToCompact', 'collapseToMedium']
  }
}

/**
 * Editing is a state of the card, not a dialog.
 *
 * `original` is what cancel restores. Holding it here rather than re-fetching is what lets cancel
 * be instant and trustworthy.
 */
export interface EditState<T> {
  editing: boolean
  original: T | null
  draft: T | null
}

// ---------------------------------------------------------------------------------------------
// actions
// ---------------------------------------------------------------------------------------------

export type ActionId =
  // edit lifecycle
  | 'edit'
  | 'cancelEdit'
  | 'saveEdit'
  // creation
  | 'newWorkflowFolder'
  | 'newWorkflow'
  | 'addExistingWorkflow'
  | 'newGrouper'
  | 'newStep'
  | 'addExistingStep'
  | 'newDataPoint'
  // clipboard
  | 'copy'
  | 'clone'
  | 'paste'
  // destructive
  | 'dissolve'
  | 'delete'
  // size
  | 'expandToMedium'
  | 'expandToFull'
  | 'collapseToCompact'
  | 'collapseToMedium'
  // special views
  | 'python'
  // traces
  | 'traceWrites'
  | 'traceReads'
  | 'traceRouteTo'
  // step
  | 'nextStep'

/** Bottom-bar actions — operations on an element's *composition* rather than on the element. */
export type CompositionActionId =
  | 'assignTool'
  | 'assignInquisition'
  | 'addErrorWatch'
  | 'addDataPointRead'
  | 'addDataPointWrite'

export interface ActionDescriptor {
  id: ActionId | CompositionActionId
  icon: string
  /** Short name, for the tooltip's first line. */
  label: string
  /**
   * What will happen, filled in with specifics where possible — "Delete this grouper and move its
   * 12 steps to Extract Phase". Required for destructive and unfamiliar actions; the label alone
   * is not enough for `dissolve`, `clone`, or `delete`.
   */
  description?: string
  /** Shown but not clickable. `reason` becomes the tooltip — that is the point of disabling. */
  disabled?: boolean
  disabledReason?: string
  /** Prompts before running. */
  destructive?: boolean
}

/**
 * The action set for each kind, in toolbar order, excluding the size controls.
 *
 * Availability that depends on runtime state — paste legality, whether a trace has anything to
 * show — is applied on top by [availableActions]; this is the *catalogue*.
 */
export const ACTIONS_FOR: Record<ElementKind, ActionId[]> = {
  workflowFolder: [
    'edit', 'newWorkflowFolder', 'newWorkflow', 'addExistingWorkflow',
    'copy', 'clone', 'paste', 'dissolve', 'delete',
  ],
  workflow: [
    'edit', 'python', 'newGrouper', 'newStep', 'addExistingStep',
    'copy', 'clone', 'paste', 'delete',
  ],
  grouper: [
    'edit', 'newGrouper', 'newStep', 'addExistingStep',
    'copy', 'clone', 'paste', 'dissolve', 'delete',
  ],
  dataPoint: [
    'edit', 'copy', 'clone', 'paste', 'traceWrites', 'traceReads', 'delete',
  ],
  step: [
    'edit', 'traceRouteTo', 'nextStep', 'delete',
  ],
  // not yet specified — they will follow the same pattern
  inquisition: ['edit', 'copy', 'clone', 'delete'],
  errorWatch: ['edit', 'copy', 'clone', 'delete'],
  junction: ['edit', 'copy', 'delete'],
  tool: ['edit', 'copy', 'clone', 'delete'],
  transform: ['edit', 'copy', 'clone', 'delete'],
  multiflow: ['edit', 'copy', 'clone', 'delete'],
  tag: ['edit', 'copy', 'delete'],
}

/** Types that carry a bottom bar, and what is on it. */
export const COMPOSITION_ACTIONS_FOR: Partial<Record<ElementKind, CompositionActionId[]>> = {
  step: [
    'assignTool', 'assignInquisition', 'addErrorWatch',
    'addDataPointRead', 'addDataPointWrite',
  ],
}

// ---------------------------------------------------------------------------------------------
// the card
// ---------------------------------------------------------------------------------------------

/**
 * Everything the card component draws.
 *
 * Assembled per element by a per-kind adapter — that adapter is the only place that knows a
 * workflow has a Python version and a grouper has a colour.
 */
export interface ElementCard {
  ref: ElementRef
  icon: string
  name: string
  description: string | null
  /** Steps and groupers only; an accent, never a fill. */
  color?: HexColor | null
  /** Body lines. Compact shows the first few; medium and full show all. */
  facts: Fact[]
  size: ViewSize
  editing: boolean
  /** Resolved for the current state — already filtered and annotated. */
  actions: ActionDescriptor[]
  /** Bottom bar, where the kind has one. */
  compositionActions: ActionDescriptor[]
}
