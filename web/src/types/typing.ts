/**
 * The Python type system, as the API serializes it.
 *
 * `type_nodes` is a universal spine: every type of every form is a row, and the satellite tables
 * hang off it. That is right for storage and wrong for display — a UI wants `dict[str, list[int]]`
 * as a tree, not as four rows and three join tables. `src/models/typeDisplay.ts` does that
 * assembly; these are the pieces it assembles from.
 */

import type { Version } from './environs'

/** The form a type expression takes. Five, since the discriminator was narrowed to one axis. */
export type TypeNodeKind =
  | 'BASE'
  | 'INSTANTIATION'
  | 'TYPEVAR'
  | 'UNION'
  | 'CALLABLE'

export interface TypeNode {
  id: number
  /** Types are scoped to a Python release — `asyncio.TaskGroup` exists in 3.11 and not 3.10. */
  version: Version
  namespace: string | null
  name: string | null
  kind: TypeNodeKind
  isGeneric: boolean
}

/** `asyncio.Task`, or just `str` when there is no namespace. */
export const qualifiedName = (node: TypeNode): string =>
  node.namespace ? `${node.namespace}.${node.name ?? '?'}` : (node.name ?? '?')

// ---------------------------------------------------------------------------------------------
// relations between nodes
// ---------------------------------------------------------------------------------------------

export type TypeNodeRelationKind = 'INHERITANCE' | 'INSTANTIATION'

export interface TypeNodeRelation {
  parentNode: TypeNode
  childNode: TypeNode
  relationKind: TypeNodeRelationKind
  /** Base order — `class C(A, B)` resolves differently from `class C(B, A)`. */
  position: number
}

/** Order-free by design: `str | int` and `int | str` are the same type. */
export interface TypeUnionMember {
  unionTypeNode: TypeNode
  memberTypeNode: TypeNode
}

/** `list[str]` — the instantiation node, and what it instantiates. */
export interface TypeInstantiation {
  id: number
  instantiationNode: TypeNode
  originNode: TypeNode
}

/** Ordered, unlike union members: `dict[str, int]` is not `dict[int, str]`. */
export interface TypeInstantiationArg {
  id: number
  instantiation: TypeInstantiation
  paramNode: TypeNode
  paramPosition: number
}

/** `class Mapping(Generic[K, V])` — which type variables, in what order. */
export interface TypeGenericParam {
  id: number
  classNode: TypeNode
  typevarNode: TypeNode
  position: number
}

export interface TypeAlias {
  id: number
  target: TypeNode
}

// ---------------------------------------------------------------------------------------------
// type variables
// ---------------------------------------------------------------------------------------------

export type VarianceCardinality = 'COVARIANT' | 'CONTRAVARIANT' | 'INVARIANT'

/** `TypeVar("T", bound=Sized)` — an upper bound. At most one. */
export interface TypeVarBound {
  id: number
  typeVarNode: TypeNode
  boundTypeNode: TypeNode
}

/** `TypeVar("T", str, bytes)` — exactly one of these, not a subtype. Several. */
export interface TypeVarConstraint {
  id: number
  typeVarNode: TypeNode
  constraintNode: TypeNode
}

export interface TypeVarVariance {
  id: number
  typeVarNode: TypeNode
  varianceCardinality: VarianceCardinality
}

// ---------------------------------------------------------------------------------------------
// callables
// ---------------------------------------------------------------------------------------------

export interface CallableSignature {
  id: number
  typeNode: TypeNode
  returnTypeNode: TypeNode
  docstring: string | null
  isStatic: boolean
  isClassmethod: boolean
}

export interface CallableParameter {
  id: number
  signature: CallableSignature
  paramTypeNode: TypeNode
  position: number
  paramName: string
  /** `*args` */
  isVarArg: boolean
  /** `**kwargs` */
  isKwarg: boolean
  /** Precedes a `/` — cannot be passed by name. */
  isPositionalOnly: boolean
  /** Follows a `*` — cannot be passed by position. */
  isKeywordOnly: boolean
  /** Source text; a default can be any expression, so it is for display, not evaluation. */
  defaultValueText: string | null
}

/** Many-to-many: a function object can be bound in several places. */
export interface CallableOwner {
  ownerNode: TypeNode
  callableNode: TypeNode
}

// ---------------------------------------------------------------------------------------------
// composition
// ---------------------------------------------------------------------------------------------

export type SchemaMembership = 'ATTRIBUTE' | 'METHOD' | 'PROPERTY' | 'CLASSVAR'

/** *On this class there is a member called `name`, of this kind, whose type is `typeNode`.* */
export interface TypeSchemaRef {
  id: number
  name: string
  membership: SchemaMembership
  typeNode: TypeNode | null
  description: string | null
  position: number
}
