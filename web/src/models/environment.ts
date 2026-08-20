/**
 * The workflow environment: what it runs on, and what happens when that changes.
 *
 * The organising idea is that **the Python version is a derived answer, not an opening question**.
 * A user knows what the workflow must do, which implies libraries, which each declare a
 * Requires-Python; the intersection of those constraints is what can run it at all. The server can
 * compute that, because `package_python_requirements` stores each constraint as a structured
 * operator-plus-version rather than as a string.
 *
 * `workflows.python_version_id` is non-nullable, so a workflow always *has* a version. The
 * resolution below revises it rather than supplying it for the first time — see [ResolvedEnvironment
 * .recommended] and the note on provisional versions there.
 */

// ---------------------------------------------------------------------------------------------
// versions
// ---------------------------------------------------------------------------------------------

/**
 * A Python release, flattened for display.
 *
 * `sortKey` exists because comparing releases correctly means comparing epoch, then release
 * components, then phase — and phase ranks alpha < beta < candidate < final, which is the order the
 * database enum is now declared in. Precomputing it server-side keeps that rule in one place.
 */
export interface PythonRelease {
  id: number
  major: number
  minor: number
  micro: number
  /** `3.12.4`, or `3.14.0rc1` for a prerelease. */
  label: string
  /** True for `final`; prereleases are offered but not recommended. */
  isStable: boolean
  sortKey: number
}

/** `3.12` — the series a release belongs to, which is the axis users actually think in. */
export const seriesOf = (r: PythonRelease): string => `${r.major}.${r.minor}`

// ---------------------------------------------------------------------------------------------
// requirements
// ---------------------------------------------------------------------------------------------

/**
 * One distribution's claim on the Python version, as a band across the version axis.
 *
 * [specifier] is the human form (`>=3.11,<3.14`) for display; [supportedSeries] is what the chart
 * draws. Both come from the server so the client never parses PEP 440.
 */
export interface RequirementBand {
  distribution: string
  /** The pinned version, or null for "install as-is" — `workflow_requirements` allows either. */
  pinnedVersion: string | null

  /**
   * Whether the user chose this, or it arrived as a dependency of something they chose.
   *
   * Drawn distinctly, because "what can I remove to widen this range" is only answerable if you can
   * tell which rows were choices.
   */
  direct: boolean
  /** For transitive requirements, what pulled it in — `numpy (via pandas)`. */
  via: string | null

  /** `>=3.11,<3.14`, for the row label. */
  specifier: string
  /** `["3.11", "3.12", "3.13"]` — the series this admits. */
  supportedSeries: string[]

  /** Optional feature sets enabled on this distribution — `requests[socks]`. */
  extras: string[]
  /** Extras it offers but which are not enabled, so they can be toggled on. */
  availableExtras: string[]
}

/**
 * A reason the viable set is narrow or empty.
 *
 * [blamed] is the point: "no viable Python version" is useless, "pandas >=3.11 and numpy <3.13
 * leave 3.11–3.12" is actionable. Never report a conflict without naming what forms each edge.
 */
export interface EnvironmentConflict {
  message: string
  blamed: string[]
  severity: 'narrowing' | 'blocking'
}

/**
 * The whole picture the environment view draws.
 *
 * [viableSeries] is the intersection of every band. [recommended] is the newest stable release in
 * it — the answer the user confirms rather than guesses.
 *
 * On the non-nullable column: a newly created workflow should be given the recommended version for
 * an empty requirement set (the newest stable release), so the field is satisfied from birth and
 * the environment view *revises* rather than initialises. That keeps the schema honest without
 * making the user choose before anything is known — which would be the easter-egg hunt this design
 * exists to avoid.
 */
export interface ResolvedEnvironment {
  bands: RequirementBand[]
  /** Every release the chart draws, in order — the axis. */
  axis: PythonRelease[]
  /** The intersection, as series (`["3.11", "3.12"]`). Empty means over-constrained. */
  viableSeries: string[]
  recommended: PythonRelease | null
  current: PythonRelease
  conflicts: EnvironmentConflict[]
  /** True while the server is still resolving — this is the one screen where waiting is plausible. */
  resolving: boolean
}

/** Whether the workflow's current version is still admitted by everything it requires. */
export const currentIsViable = (env: ResolvedEnvironment): boolean =>
  env.viableSeries.includes(seriesOf(env.current))

// ---------------------------------------------------------------------------------------------
// getting started
// ---------------------------------------------------------------------------------------------

/**
 * How a user opens an empty environment.
 *
 * The empty state matters more here than anywhere else in the product: this is where a workflow
 * begins, and a blank search over 596,684 distributions is not a starting point. Owl-E's job is to
 * prepare the field, so all three of these should be offered rather than only the first.
 */
export type EnvironmentStart =
  /** Search and add distributions directly. The expert path. */
  | { kind: 'intent' }
  /** A named starting set — something to react to rather than a blank page. */
  | { kind: 'template'; templateId: string }
  /** Copy another workflow's environment wholesale. Likely the most-used path in practice. */
  | { kind: 'existingWorkflow'; workflowId: number }

export interface EnvironmentTemplate {
  id: string
  name: string
  description: string
  distributions: string[]
}

// ---------------------------------------------------------------------------------------------
// changing version — the impact report
// ---------------------------------------------------------------------------------------------

/**
 * What a change of Python version does to one distribution.
 *
 * `bump` cases should be batchable — "upgrade these 4 packages" as one action — leaving only the
 * genuinely blocked ones needing individual decisions.
 */
export type PackageOutcome =
  | { kind: 'ok'; distribution: string }
  | { kind: 'bump'; distribution: string; from: string; to: string }
  | { kind: 'blocked'; distribution: string; reason: string }

/**
 * A step whose tool may not survive the move.
 *
 * The consequence users do not anticipate. `type_nodes` are scoped to a Python version, so a tool
 * referencing something absent from the target — `asyncio.TaskGroup` below 3.11 — breaks even
 * though nothing in the package list said so. **Downgrades are more dangerous here than upgrades.**
 *
 * Named by *step*, not by type: the user needs to know which of their work is affected, not which
 * CPython symbol moved.
 */
export interface StepImpact {
  stepId: number
  stepName: string
  toolName: string
  /** The symbols unavailable in the target, for the detail view. */
  missingTypes: string[]
}

/**
 * The report shown **before anything changes**.
 *
 * Nothing is applied until the whole thing is accepted — half-migrating is worse than not
 * migrating. Presented over the same constraint chart as [ResolvedEnvironment], with the target
 * marked and blocking bands highlighted, so there is one visual language for "what runs" and "what
 * would break if I moved".
 */
export interface MigrationImpact {
  from: PythonRelease
  target: PythonRelease
  packages: PackageOutcome[]
  affectedSteps: StepImpact[]
  /** Bindings built on types that changed shape. Countable before it is diagnosable. */
  transformChainsToReview: number
}

export const isBlocked = (impact: MigrationImpact): boolean =>
  impact.packages.some((p) => p.kind === 'blocked') || impact.affectedSteps.length > 0

export const bumpable = (impact: MigrationImpact): Extract<PackageOutcome, { kind: 'bump' }>[] =>
  impact.packages.filter((p): p is Extract<PackageOutcome, { kind: 'bump' }> => p.kind === 'bump')
