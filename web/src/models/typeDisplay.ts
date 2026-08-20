/**
 * Rendering a Python type.
 *
 * The API stores types as a flat spine — `dict[str, list[int | None]]` is one `type_nodes` row per
 * part plus rows in `type_instantiations`, `type_instantiation_args` and `type_union_members` to
 * connect them. That shape is right for storage and useless for display: a UI needs the tree.
 *
 * [RenderedType] is that tree, and everything else here is about turning it into something on
 * screen. Two output forms, because the UI needs both:
 *
 * - [formatType] gives a string — for a badge, a tooltip, a table cell.
 * - [segmentType] gives addressable pieces — so `list` and `str` in `list[str]` can each be
 *   hovered, clicked through to, or coloured separately. A type browser needs this; a badge doesn't.
 */

import type { TypeNode, VarianceCardinality } from '../types/typing'

// ---------------------------------------------------------------------------------------------
// the tree
// ---------------------------------------------------------------------------------------------

/**
 * A type expression, assembled. Recursive, because Python's type expressions are.
 *
 * **These discriminators are deliberately lower-case and are not the wire enum.** `TypeNodeKind`
 * arrives from the API as `BASE`, `UNION`, `INSTANTIATION` — the Kotlin constant names. This is a
 * UI construct with a different membership (it has `unresolved`, which no row ever holds), so the
 * casing difference is a useful reminder of which side of the boundary you are on.
 */
export type RenderedType =
  /** A named class — `str`, `asyncio.Task`. */
  | {
      kind: 'base'
      nodeId: number
      namespace: string | null
      name: string
    }
  /** A parameterised generic — `list[str]`, `dict[str, int]`. Arguments are **ordered**. */
  | {
      kind: 'instantiation'
      nodeId: number
      origin: RenderedType
      args: RenderedType[]
    }
  /**
   * A union — `str | int`. Members are **unordered**: `str | int` and `int | str` are the same
   * type, so any ordering here is a display choice, not a fact. [sortUnionMembers] makes that
   * choice consistently.
   */
  | {
      kind: 'union'
      nodeId: number
      members: RenderedType[]
    }
  /** A type variable — `T`, with whatever bounds or constraints it carries. */
  | {
      kind: 'typevar'
      nodeId: number
      name: string
      bound: RenderedType | null
      constraints: RenderedType[]
      variance: VarianceCardinality
    }
  /** A callable used as a type — `(str, int) -> bool`. */
  | {
      kind: 'callable'
      nodeId: number
      params: RenderedType[]
      returns: RenderedType
    }
  /** The node exists but its detail was not loaded. Renders as its name, or `?`. */
  | {
      kind: 'unresolved'
      nodeId: number
      label: string
    }

// ---------------------------------------------------------------------------------------------
// optionality
// ---------------------------------------------------------------------------------------------

const isNone = (t: RenderedType): boolean =>
  t.kind === 'base' && t.name === 'NoneType'

/**
 * `str | None` is overwhelmingly the most common union and reads badly among the others.
 *
 * Split it out so the UI can render `str | None` — or `Optional[str]`, or a `?` marker — rather
 * than showing a two-member union like any other.
 */
export function asOptional(
  t: RenderedType,
): { inner: RenderedType; optional: boolean; noneNodeId: number | null } {
  if (t.kind !== 'union') return { inner: t, optional: false, noneNodeId: null }
  const none = t.members.find(isNone)
  const withoutNone = t.members.filter((m) => !isNone(m))
  if (!none) return { inner: t, optional: false, noneNodeId: null }
  // the None member's id is carried out so the segment for it stays addressable like any other
  const noneNodeId = none.kind === 'base' ? none.nodeId : null
  if (withoutNone.length === 1) return { inner: withoutNone[0], optional: true, noneNodeId }
  return { inner: { ...t, members: withoutNone }, optional: true, noneNodeId }
}

/**
 * A stable display order for union members.
 *
 * The set is the identity, so any order is equally correct and an *inconsistent* one is what hurts
 * — the same type reading differently in two places. `None` last, everything else alphabetical.
 */
export function sortUnionMembers(members: RenderedType[]): RenderedType[] {
  return [...members].sort((a, b) => {
    if (isNone(a)) return 1
    if (isNone(b)) return -1
    return formatType(a).localeCompare(formatType(b))
  })
}

// ---------------------------------------------------------------------------------------------
// string form
// ---------------------------------------------------------------------------------------------

export interface FormatOptions {
  /** Show `asyncio.Task` rather than `Task`. Off by default — namespaces are long and repetitive. */
  qualified?: boolean
  /** Render optionals as `Optional[str]` rather than `str | None`. */
  optionalSyntax?: boolean
}

/** `dict[str, list[int | None]]`. */
export function formatType(t: RenderedType, opts: FormatOptions = {}): string {
  const { optional, inner } = asOptional(t)
  if (optional) {
    const body = formatType(inner, opts)
    return opts.optionalSyntax ? `Optional[${body}]` : `${body} | None`
  }

  switch (t.kind) {
    case 'base':
      return opts.qualified && t.namespace ? `${t.namespace}.${t.name}` : t.name
    case 'instantiation':
      return `${formatType(t.origin, opts)}[${t.args.map((a) => formatType(a, opts)).join(', ')}]`
    case 'union':
      return sortUnionMembers(t.members)
        .map((m) => formatType(m, opts))
        .join(' | ')
    case 'typevar':
      return t.name
    case 'callable':
      return `(${t.params.map((p) => formatType(p, opts)).join(', ')}) -> ${formatType(t.returns, opts)}`
    case 'unresolved':
      return t.label
  }
}

// ---------------------------------------------------------------------------------------------
// addressable form
// ---------------------------------------------------------------------------------------------

/**
 * One piece of a rendered type.
 *
 * `nodeId` is set on the pieces that *are* a type — so `list` and `str` in `list[str]` are each
 * navigable — and absent on the brackets and commas holding them together.
 */
export interface TypeSegment {
  text: string
  kind: 'name' | 'namespace' | 'punctuation' | 'typevar'
  nodeId?: number
}

/** The same output as [formatType], broken into pieces the UI can attach behaviour to. */
export function segmentType(t: RenderedType, opts: FormatOptions = {}): TypeSegment[] {
  const punct = (text: string): TypeSegment => ({ text, kind: 'punctuation' })

  const { optional, inner, noneNodeId } = asOptional(t)
  if (optional) {
    const noneSeg: TypeSegment = { text: 'None', kind: 'name', ...(noneNodeId != null && { nodeId: noneNodeId }) }
    return opts.optionalSyntax
      ? [punct('Optional['), ...segmentType(inner, opts), punct(']')]
      : [...segmentType(inner, opts), punct(' | '), noneSeg]
  }

  switch (t.kind) {
    case 'base': {
      const segs: TypeSegment[] = []
      if (opts.qualified && t.namespace) {
        segs.push({ text: `${t.namespace}.`, kind: 'namespace' })
      }
      segs.push({ text: t.name, kind: 'name', nodeId: t.nodeId })
      return segs
    }
    case 'instantiation':
      return [
        ...segmentType(t.origin, opts),
        punct('['),
        ...t.args.flatMap((a, i) => (i === 0 ? segmentType(a, opts) : [punct(', '), ...segmentType(a, opts)])),
        punct(']'),
      ]
    case 'union':
      return sortUnionMembers(t.members).flatMap((m, i) =>
        i === 0 ? segmentType(m, opts) : [punct(' | '), ...segmentType(m, opts)],
      )
    case 'typevar':
      return [{ text: t.name, kind: 'typevar', nodeId: t.nodeId }]
    case 'callable':
      return [
        punct('('),
        ...t.params.flatMap((p, i) => (i === 0 ? segmentType(p, opts) : [punct(', '), ...segmentType(p, opts)])),
        punct(') -> '),
        ...segmentType(t.returns, opts),
      ]
    case 'unresolved':
      return [{ text: t.label, kind: 'name', nodeId: t.nodeId }]
  }
}

// ---------------------------------------------------------------------------------------------
// signatures
// ---------------------------------------------------------------------------------------------

/**
 * A parameter as it appears in a rendered signature.
 *
 * `positionalOnly` and `keywordOnly` are not decoration: they decide where the `/` and `*`
 * separators go, and a generated call that ignores them is not legal Python.
 */
export interface RenderedParameter {
  name: string
  type: RenderedType
  defaultText: string | null
  isVarArg: boolean
  isKwarg: boolean
  positionalOnly: boolean
  keywordOnly: boolean
}

export interface RenderedSignature {
  name: string
  params: RenderedParameter[]
  returns: RenderedType
  docstring: string | null
  isStatic: boolean
  isClassmethod: boolean
}

/**
 * `read_csv(path: str, /, sep: str = ",", *cols: str, encoding: str = "utf-8") -> list[str]`
 *
 * The separators are inserted where the passing styles change: a `/` after the last
 * positional-only parameter, and a bare `*` before the first keyword-only one — unless a `*args`
 * is already there, which serves the same purpose.
 */
export function formatSignature(sig: RenderedSignature, opts: FormatOptions = {}): string {
  const parts: string[] = []
  let emittedSlash = false
  let emittedStar = false

  sig.params.forEach((p, i) => {
    const prev = sig.params[i - 1]
    if (!emittedSlash && prev?.positionalOnly && !p.positionalOnly) {
      parts.push('/')
      emittedSlash = true
    }
    if (!emittedStar && p.keywordOnly && !p.isKwarg) {
      parts.push('*')
      emittedStar = true
    }
    if (p.isVarArg || p.isKwarg) emittedStar = true

    const prefix = p.isVarArg ? '*' : p.isKwarg ? '**' : ''
    const type = `: ${formatType(p.type, opts)}`
    const dflt = p.defaultText != null ? ` = ${p.defaultText}` : ''
    parts.push(`${prefix}${p.name}${type}${dflt}`)
  })

  // a signature that is entirely positional-only still needs its trailing separator
  if (!emittedSlash && sig.params.length > 0 && sig.params[sig.params.length - 1].positionalOnly) {
    parts.push('/')
  }

  return `${sig.name}(${parts.join(', ')}) -> ${formatType(sig.returns, opts)}`
}

// ---------------------------------------------------------------------------------------------
// assembly
// ---------------------------------------------------------------------------------------------

/**
 * What the client needs to build a [RenderedType] without walking the API graph itself.
 *
 * The server is far better placed to assemble this — it holds the relations already — so the
 * expectation is that a type arrives pre-rendered. This shape exists so the client can also do it
 * from loose parts when it has them, and so the two agree on what the result looks like.
 */
export interface TypeGraphSlice {
  nodes: Map<number, TypeNode>
  /** instantiation node id → { origin, ordered args }. */
  instantiations: Map<number, { originId: number; argIds: number[] }>
  /** union node id → member node ids. */
  unions: Map<number, number[]>
  /** typevar node id → its bound, constraints and variance. */
  typevars: Map<
    number,
    { boundId: number | null; constraintIds: number[]; variance: VarianceCardinality }
  >
}

/** Builds the tree for one node, guarding against the cycles a malformed graph could contain. */
export function renderType(
  nodeId: number,
  slice: TypeGraphSlice,
  seen: Set<number> = new Set(),
): RenderedType {
  const node = slice.nodes.get(nodeId)
  if (!node) return { kind: 'unresolved', nodeId, label: '?' }
  if (seen.has(nodeId)) {
    return { kind: 'unresolved', nodeId, label: node.name ?? '?' }
  }
  const nextSeen = new Set(seen).add(nodeId)

  switch (node.kind) {
    case 'INSTANTIATION': {
      const inst = slice.instantiations.get(nodeId)
      if (!inst) break
      return {
        kind: 'instantiation',
        nodeId,
        origin: renderType(inst.originId, slice, nextSeen),
        args: inst.argIds.map((id) => renderType(id, slice, nextSeen)),
      }
    }
    case 'UNION': {
      const members = slice.unions.get(nodeId)
      if (!members) break
      return {
        kind: 'union',
        nodeId,
        members: members.map((id) => renderType(id, slice, nextSeen)),
      }
    }
    case 'TYPEVAR': {
      const tv = slice.typevars.get(nodeId)
      return {
        kind: 'typevar',
        nodeId,
        name: node.name ?? 'T',
        bound: tv?.boundId != null ? renderType(tv.boundId, slice, nextSeen) : null,
        constraints: (tv?.constraintIds ?? []).map((id) => renderType(id, slice, nextSeen)),
        variance: tv?.variance ?? 'INVARIANT',
      }
    }
    default:
      break
  }

  return { kind: 'base', nodeId, namespace: node.namespace, name: node.name ?? '?' }
}
