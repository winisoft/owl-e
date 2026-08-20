/**
 * Transforms — converting a value from the type it has to the type something needs.
 *
 * A [Transform] is the reusable definition (`.split(',')`, `[0]`, `not x`). A [TransformOccurrence]
 * is one *use* of it at a particular place, with its arguments bound. Occurrences chain, which is
 * how a multi-hop conversion is represented.
 */

import type { TypeNode } from './typing'

/** Where a conversion is being applied — the same transform is reusable in each context. */
export type TransformOccurrenceInitiator =
  | 'DATA_POINT_READ'
  | 'DATA_POINT_WRITE'
  | 'INQUISITION'
  | 'DATA_POINT_INJECTION'

/** The definition. `examples` is what the UI shows beside the description — `ex: result[0]`. */
export interface Transform {
  id: number
  name: string
  description: string | null
  examples: string | null
}

/** A parameter of the transform itself — the `','` in `.split(',')`. */
export interface TransformArg {
  id: number
  transform: Transform
  typeNode: TypeNode
  name: string
  description: string | null
}

/**
 * Which types a transform accepts, and what it produces for each.
 *
 * This is the table that makes route-finding possible: given a source type and a target type, the
 * server can search these edges for a chain that connects them, rather than making the user hunt
 * hop by hop.
 */
export interface TransformTargetType {
  transform: Transform
  eligibleTypeNode: TypeNode
  returnTypeNode: TypeNode
}

/** One use of a transform at one site. */
export interface TransformOccurrence {
  id: number
  transform: Transform
  initiator: TransformOccurrenceInitiator
}

/** The value bound to one of the transform's own arguments at this occurrence. */
export interface TransformOccurrenceArg {
  id: number
  transformOccurrence: TransformOccurrence
  transformArg: TransformArg
  value: string
}

/**
 * Two occurrences in sequence — the output of the antecedent feeds the subsequent.
 *
 * A chain of these is what carries `str` to `int` when no single transform does it.
 */
export interface TransformOccurrenceChain {
  antecedentOccurrence: TransformOccurrence
  subsequentOccurrence: TransformOccurrence
}
