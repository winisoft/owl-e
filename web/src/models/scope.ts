/**
 * Where the user is, how they got there, and what is on the clipboard.
 *
 * Under the cubby model a user is always inside exactly one scope, and everything not in it is off
 * screen. That makes the scope trail load-bearing rather than decorative — it is often the only
 * thing on screen saying where you are.
 */

import type { ElementKind, ElementRef } from './element'

// ---------------------------------------------------------------------------------------------
// containment — the breadcrumb
// ---------------------------------------------------------------------------------------------

export interface ScopeSegment {
  ref: ElementRef
  name: string
  icon: string
}

/**
 * The path from the root folder to the current scope, root first.
 *
 * Always at least one segment: every user has exactly one root folder and it cannot be deleted or
 * dissolved, so the trail can never be empty.
 */
export type Scope = ScopeSegment[]

/**
 * Which segments to draw when the trail is too long for the space.
 *
 * Keeps the root and the last [tailLength] segments; everything between collapses behind an
 * ellipsis that can list them. The root survives because it is the only fixed point, and the tail
 * survives because it is where the user actually is.
 */
export function elideScope(
  scope: Scope,
  maxSegments: number,
  tailLength = 2,
): { head: ScopeSegment | null; hidden: ScopeSegment[]; tail: Scope } {
  if (scope.length <= maxSegments) {
    return { head: null, hidden: [], tail: scope }
  }
  const [root, ...rest] = scope
  const tail = rest.slice(-tailLength)
  return { head: root, hidden: rest.slice(0, -tailLength), tail }
}

// ---------------------------------------------------------------------------------------------
// chronology — history
// ---------------------------------------------------------------------------------------------

/**
 * Somewhere the user has been.
 *
 * Deliberately separate from [Scope]: the breadcrumb answers "what contains this", history answers
 * "where was I". Conflating them is a common mistake and produces a control that does neither well.
 */
export interface VisitedScope {
  ref: ElementRef
  name: string
  icon: string
  /** The containment path at the time of the visit, for showing where it was. */
  path: string
  visitedAt: string
}

// ---------------------------------------------------------------------------------------------
// clipboard
// ---------------------------------------------------------------------------------------------

/**
 * What has been copied.
 *
 * A list, because multi-select may make bulk copy possible; a single copy is a list of one. The
 * kinds are carried separately so legality can be judged without inspecting every ref.
 */
export interface Clipboard {
  refs: ElementRef[]
  kinds: ElementKind[]
}

/**
 * What each container will accept.
 *
 * Wini's rule: **paste is hidden, not disabled, when the clipboard cannot go here.** A disabled
 * paste button on every folder while a step is copied would be noise on every card in the tree.
 * (Contrast `nextStep`, which *is* disabled-with-a-reason, because its unavailability is a fact
 * about that specific step worth explaining.)
 */
export const ACCEPTS: Partial<Record<ElementKind, ElementKind[]>> = {
  workflowFolder: ['workflowFolder', 'workflow'],
  workflow: ['grouper', 'step'],
  grouper: ['grouper', 'step'],
  dataPoint: ['dataPoint'],
}

/** Whether every item on the clipboard is legal in this scope. Empty clipboard pastes nothing. */
export function canPasteInto(clipboard: Clipboard | null, target: ElementKind): boolean {
  if (!clipboard || clipboard.kinds.length === 0) return false
  const accepted = ACCEPTS[target]
  if (!accepted) return false
  return clipboard.kinds.every((kind) => accepted.includes(kind))
}

// ---------------------------------------------------------------------------------------------
// traces
// ---------------------------------------------------------------------------------------------

/**
 * The trace family — *what reaches this, what does this reach*.
 *
 * One shape for all of them so they get one presentation. They will multiply: every relationship
 * in the schema is a candidate.
 */
export type TraceKind =
  /** Steps that may write to this data point. */
  | 'writes'
  /** Steps, inquisitions and injections that read this data point. */
  | 'reads'
  /** Steps that default-next here, plus inquisitions and error watches that redirect here. */
  | 'routeTo'

export interface TraceHit {
  ref: ElementRef
  name: string
  icon: string
  /** Why this hit is in the result — "default next step", "redirected by inquisition". */
  via: string
  /** Whether it is currently on screen, which decides highlight versus panel. */
  inView: boolean
}

export interface TraceResult {
  subject: ElementRef
  kind: TraceKind
  hits: TraceHit[]
}
