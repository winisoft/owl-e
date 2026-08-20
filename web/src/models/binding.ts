/**
 * Binding a tool's parameters to data points.
 *
 * A type-directed problem: typed sources connected to typed sinks, with a conversion pipeline
 * between. Everything here exists to make the types visible, the mismatches obvious, and the
 * resolution cheap.
 */

// ---------------------------------------------------------------------------------------------
// what a slot can be
// ---------------------------------------------------------------------------------------------

/**
 * A parameter slot is a state machine, not a checkbox.
 *
 * `unresolved` is the one that must block saving: a chain was started and does not yet produce the
 * parameter's type, so the slot looks bound and is not.
 */
export type SlotState =
  /** Nothing bound, and nothing to fall back on. Yellow stroke — needs attention. */
  | 'emptyRequired'
  /** Nothing bound, but the signature has a default. Muted, with an override affordance. */
  | 'emptyDefault'
  /** Bound, and the source's type is already the parameter's type. */
  | 'bound'
  /** Bound through one or more transforms. Settled, but visibly doing work. */
  | 'boundTransformed'
  /** A chain exists but does not yet produce the required type. Error; blocks saving. */
  | 'unresolved'

/**
 * How a parameter may be passed, which decides how the slot is labelled.
 *
 * From `callable_parameters.is_positional_only` / `is_keyword_only`, which exist because a
 * generated call site has to be legal Python — `def f(a, /, b, *, c)` means `a` can never be passed
 * by name and `c` can never be passed by position.
 */
export type PassingStyle = 'positionalOnly' | 'keywordOnly' | 'either'

export interface ParameterSlot {
  name: string
  /** Display form of the parameter's type — `str`, `list[int]`, `dict[str, Any]`. */
  typeLabel: string
  /** The type node behind it, for matching. */
  typeNodeId: number
  position: number

  passingStyle: PassingStyle
  /** `*args` — takes many, so the slot repeats. */
  isVarArg: boolean
  /** `**kwargs` — takes named extras. */
  isKwarg: boolean

  /** Source text of the default, or null when the parameter is required. Display only. */
  defaultText: string | null

  binding: Binding | null
  state: SlotState
}

/** Required parameters first — the ones that are actually work. */
export const isRequired = (slot: ParameterSlot): boolean => slot.defaultText === null

/**
 * Most Python functions have more optional parameters than required ones. Showing twelve slots when
 * two need filling buries the work, so the defaulted ones collapse behind a disclosure.
 */
export function partitionSlots(slots: ParameterSlot[]): {
  required: ParameterSlot[]
  optional: ParameterSlot[]
} {
  return {
    required: slots.filter(isRequired),
    optional: slots.filter((s) => !isRequired(s)),
  }
}

// ---------------------------------------------------------------------------------------------
// the chain
// ---------------------------------------------------------------------------------------------

/** One conversion in the pipeline. Reads left to right: `in` is what it receives. */
export interface TransformStep {
  transformId: number
  /** `.split(',')`, `[0]`, `not result`. */
  label: string
  inTypeLabel: string
  outTypeLabel: string
  outTypeNodeId: number
}

export interface Binding {
  dataPointId: number
  dataPointName: string
  sourceTypeLabel: string
  sourceTypeNodeId: number
  /** Empty when the source's type already matches. */
  chain: TransformStep[]
}

/** What the binding produces after its chain — the type the slot actually receives. */
export function resultTypeNodeId(binding: Binding): number {
  return binding.chain.length === 0
    ? binding.sourceTypeNodeId
    : binding.chain[binding.chain.length - 1].outTypeNodeId
}

export function stateFor(slot: ParameterSlot, binding: Binding | null): SlotState {
  if (!binding) return isRequired(slot) ? 'emptyRequired' : 'emptyDefault'
  if (resultTypeNodeId(binding) !== slot.typeNodeId) return 'unresolved'
  return binding.chain.length > 0 ? 'boundTransformed' : 'bound'
}

// ---------------------------------------------------------------------------------------------
// resolution
// ---------------------------------------------------------------------------------------------

/**
 * A complete route from a source type to a target type.
 *
 * The system knows every transform's applicable input types and its output type, so finding a route
 * from `str` to `int` is a graph search it can run itself. Offering finished routes — ranked by
 * length — is the difference between this feeling clever and feeling like paperwork; assembling a
 * three-hop chain one dropdown at a time when the answer is computable is the failure mode.
 *
 * Hop-by-hop selection stays available underneath for what the search does not cover.
 */
export interface ResolutionPath {
  steps: TransformStep[]
  /** `str → .split(',') → list[str] → [0] → str`, for the option label. */
  summary: string
}

/**
 * Whether the data point search restricts results to the parameter's type.
 *
 * **Checked by default.** Checked, it prevents mistakes and shortens the list; unchecked, it opens
 * the whole transform apparatus — so unchecking should feel like deliberately entering a more
 * powerful mode rather than like relaxing a filter.
 */
export interface BindingSearchState {
  filterToParameterType: boolean
  query: string
}

// ---------------------------------------------------------------------------------------------
// choosing the tool
// ---------------------------------------------------------------------------------------------

/**
 * Modes, not phases.
 *
 * `create` is the escape hatch from `find` when nothing matches, not a later step. Changing the
 * tool once bindings exist returns to `find` with those bindings held — a graceful path, not a
 * reset — which is why this is a mode rather than a wizard position.
 */
export type ToolMode = 'find' | 'create' | 'bind'

/**
 * The third appearance of one search pattern — data points, tools, transforms all use it. The
 * shell, the autocomplete and the result cards should be one component with pluggable facets.
 *
 * Facets are additive, and each should show its contribution: a result count that moves as criteria
 * are added is what tells the user they are converging.
 */
export interface ToolSearchFacets {
  /** Free text over the name. */
  name: string
  /** Narrow to tools returning this type. */
  returnTypeLabel: string | null
  /** Accumulating; each added tag narrows again. */
  tags: string[]
}

export interface ToolSearchResult {
  toolId: number
  name: string
  description: string | null
  returnTypeLabel: string | null
  /** Enough of the signature to recognise it — `(path: str, encoding: str = "utf-8")`. */
  signature: string
  tags: string[]
}
