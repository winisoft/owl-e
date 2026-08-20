/**
 * Where code comes from, and whether it will run here.
 *
 * Versions are stored decomposed rather than as strings so that ordering is a comparison of numbers
 * rather than a parse — see `docs/TYPE-MODEL-REVIEW.md` §1. The consequence for the client is that
 * a version arrives as components and must be *formatted*, not just printed.
 */

/** Ascending, per PEP 440: alpha < beta < candidate < final. The enum is declared in this order. */
export type PrereleasePhase = 'ALPHA' | 'BETA' | 'CANDIDATE' | 'FINAL'

/** Which component could not be held by the column meant for it. */
export type OverflowViolation =
  | 'EPOCH'
  | 'MAJOR'
  | 'MINOR'
  | 'MICRO'
  /** Release segments past the fourth — an overflow of arity, not magnitude. */
  | 'MICRO_EXTRA'
  | 'PHASE_NUMBER'
  | 'POST_NUMBER'
  | 'DEV_NUMBER'
  /** Could not be represented componentwise at all; `stringValue` holds the whole original. */
  | 'WHOLE_VERSION'

export interface Version {
  id: number
  epoch: number
  major: number
  minor: number
  micro: number
  phase: PrereleasePhase
  phaseNumber: number | null
  postNumber: number | null
  devNumber: number | null
  local: string | null
  /**
   * "Do not trust my numeric columns."
   *
   * PEP 440 puts no upper bound on components, and these are 64-bit. A version too large is stored
   * as far as it fits, the remainder preserved as text in [VersionOverflow], and this flag set.
   * Any comparison or ordering must decide what to do when it sees one — usually exclude it.
   */
  hasOverflow: boolean
  /** Present on the wire; empty for the overwhelming majority of versions. */
  versionOverflows: VersionOverflow[]
}

export interface VersionOverflow {
  id: number
  version: Version
  violation: OverflowViolation
  /** The original text of whatever did not fit. */
  stringValue: string
}

/** `1.4.2`, `2.0.0rc1`, `1.0.post2`, `3!1.0` — reassembled from components. */
export function formatVersion(v: Version): string {
  const epoch = v.epoch > 0 ? `${v.epoch}!` : ''
  const release = `${v.major}.${v.minor}.${v.micro}`
  const phase =
    v.phase === 'FINAL'
      ? ''
      : `${{ ALPHA: 'a', BETA: 'b', CANDIDATE: 'rc' }[v.phase]}${v.phaseNumber ?? 0}`
  const post = v.postNumber != null ? `.post${v.postNumber}` : ''
  const dev = v.devNumber != null ? `.dev${v.devNumber}` : ''
  const local = v.local ? `+${v.local}` : ''
  return `${epoch}${release}${phase}${post}${dev}${local}`
}

/** `3.12` — the series, which is how people actually talk about Python releases. */
export const versionSeries = (v: Version): string => `${v.major}.${v.minor}`

/** Marks a version row as denoting a Python interpreter release rather than a package release. */
export interface PythonVersion {
  id: number
  version: Version
}

// ---------------------------------------------------------------------------------------------
// distributions and packages
// ---------------------------------------------------------------------------------------------

/** A name on an index — `requests`. The name is the whole identity, which is why it is the key. */
export interface Distribution {
  id: string
  name: string
}

/** A distribution *at a version*. Its natural key is the pair, and that is its actual key. */
export interface DistributionPackage {
  version: Version
  distribution: Distribution
  versionString: string
  summary: string | null
  keywords: string | null
  description: string | null
  license: string | null
  homePage: string | null
  yanked: boolean | null
}

/** PEP 440 comparison operators. `COMPATIBLE` is `~=`; `EXACT` is `===`. */
export type VersionOperator =
  | 'LESS_THAN'
  | 'LESS_THAN_OR_EQ'
  | 'NOT_EQ'
  | 'EQUALS'
  | 'GREATER_THAN_OR_EQ'
  | 'GREATER_THAN'
  | 'COMPATIBLE'
  | 'EXACT'

/** Which component a `==1.4.*` wildcard sits at. */
export type WildcardSegment = 'NONE' | 'MAJOR' | 'MINOR' | 'MICRO'

/** A package's Requires-Python. The intersection of these is what makes version resolution work. */
export interface PackagePythonRequirement {
  distribution: Distribution
  packageVersion: number
  pythonVersion: PythonVersion
  operator: VersionOperator
  wildcard: WildcardSegment
}

/** A dependency of one package on another, optionally conditional on an environment marker. */
export interface PackageDistRequirement {
  id: number
  dependentDist: Distribution
  requiredDist: Distribution
  requiredExtraName: string | null
  operator: VersionOperator
  wildcardSegment: WildcardSegment
  /** The raw requirement line, for display — `pandas>=1.5; python_version >= "3.9"`. */
  raw: string | null
}

/** An optional feature set — the `[socks]` in `requests[socks]`. */
export interface PackageExtra {
  distribution: Distribution
  packageVersion: number
  name: string
  description: string | null
}

// ---------------------------------------------------------------------------------------------
// environment markers
// ---------------------------------------------------------------------------------------------

/** PEP 508's closed set of marker variables. */
export type EnvMarkerVariable =
  | 'PYTHON_VERSION'
  | 'PYTHON_FULL_VERSION'
  | 'OS_NAME'
  | 'SYS_PLATFORM'
  | 'PLATFORM_MACHINE'
  | 'PLATFORM_PYTHON_IMPLEMENTATION'
  | 'PLATFORM_RELEASE'
  | 'PLATFORM_VERSION'
  | 'IMPLEMENTATION_NAME'
  | 'IMPLEMENTATION_VERSION'
  /** Not a property of the machine but of the request. */
  | 'EXTRA'

export type EnvMarkerOperator =
  | 'EQ'
  | 'NE'
  | 'LT'
  | 'LE'
  | 'GT'
  | 'GE'
  | 'IN'
  | 'NOT_IN'
  /** PEP 440 compatible-release, `~=`. */
  | 'COMPATIBLE'

/** How each operator is written in a marker, for display. */
export const ENV_MARKER_OPERATOR_SYMBOL: Record<EnvMarkerOperator, string> = {
  EQ: '==', NE: '!=', LT: '<', LE: '<=', GT: '>', GE: '>=',
  IN: 'in', NOT_IN: 'not in', COMPATIBLE: '~=',
}

export type EnvMarkerLogicOp = 'AND' | 'OR'

/** Which body table holds this node's detail. */
export type EnvMarkerExpressionMode = 'COMPARATIVE' | 'LOGICAL'

/**
 * A node in a marker's expression tree.
 *
 * Markers nest arbitrarily — `python_version >= "3.8" and (sys_platform == "win32" or extra ==
 * "dev")` — so they are shredded into a tree rather than kept as text. `parent` is null at the root.
 */
export interface EnvMarkerExpression {
  id: number
  parent: EnvMarkerExpression | null
  mode: EnvMarkerExpressionMode
}

/** The body of a LOGICAL node. Strictly binary; chains fold **left**. */
export interface EnvMarkerLogical {
  id: number
  expression: EnvMarkerExpression
  operator: EnvMarkerLogicOp
  leftChild: EnvMarkerExpression
  rightChild: EnvMarkerExpression
}

/** The body of a COMPARATIVE node — a leaf. */
export interface EnvMarkerComparison {
  id: number
  expression: EnvMarkerExpression
  variable: EnvMarkerVariable | null
  operator: EnvMarkerOperator
  value: string
}
