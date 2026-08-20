/**
 * The folder tree, and what the Explorer draws from it.
 *
 * `WorkflowHierarchyService.getHierarchy(maxDepth)` returns this shape from the server, recursively
 * to a depth — roughly five levels, prefetched, which is why expanding a folder is instant and why
 * counts can be shown without a round trip.
 *
 * This is the model I should have carried over first: it is the one the top-priority screen needs.
 */

import type { Workflow, WorkflowFolder } from '../types/workflow'
import type { ContainedCount, ElementRef } from './element'

/** Mirrors `com.wini.owle.model.workflows.WorkflowFolderNode`. */
export interface WorkflowFolderNode {
  folder: WorkflowFolder
  childFolders: WorkflowFolderNode[]
  workflows: Workflow[]
}

// ---------------------------------------------------------------------------------------------
// counting
// ---------------------------------------------------------------------------------------------

/**
 * Totals beneath a node, split into what is directly here and what sits deeper.
 *
 * That split is exactly what the card's phrasing rule needs: `contains 10 workflowFolders` when
 * nesting only goes one level, `contains 10 workflowFolders (3 nested)` once it goes further.
 */
export function countFolders(node: WorkflowFolderNode): ContainedCount {
  const direct = node.childFolders.length
  const deeper = node.childFolders.reduce((sum, c) => sum + countFolders(c).total, 0)
  return { kind: 'workflowFolder', total: direct + deeper, nested: deeper }
}

export function countWorkflows(node: WorkflowFolderNode): ContainedCount {
  const direct = node.workflows.length
  const deeper = node.childFolders.reduce((sum, c) => sum + countWorkflows(c).total, 0)
  return { kind: 'workflow', total: direct + deeper, nested: deeper }
}

/** Depth of the subtree, 1 for a folder with no children. Tells the Explorer how far it can go. */
export function depthOf(node: WorkflowFolderNode): number {
  return node.childFolders.length === 0
    ? 1
    : 1 + Math.max(...node.childFolders.map(depthOf))
}

// ---------------------------------------------------------------------------------------------
// finding and pathing
// ---------------------------------------------------------------------------------------------

/** The chain from the root to a folder, root first — what the breadcrumb is built from. */
export function pathToFolder(
  root: WorkflowFolderNode,
  folderId: number,
): WorkflowFolderNode[] | null {
  if (root.folder.id === folderId) return [root]
  for (const child of root.childFolders) {
    const below = pathToFolder(child, folderId)
    if (below) return [root, ...below]
  }
  return null
}

/** Every folder in the tree, depth-first — for search over the prefetched set. */
export function flattenFolders(node: WorkflowFolderNode): WorkflowFolderNode[] {
  return [node, ...node.childFolders.flatMap(flattenFolders)]
}

/** Every workflow in the tree, paired with the folder path it was found under. */
export function flattenWorkflows(
  node: WorkflowFolderNode,
  trail: string[] = [],
): { workflow: Workflow; path: string[] }[] {
  const here = [...trail, node.folder.name]
  return [
    ...node.workflows.map((workflow) => ({ workflow, path: here })),
    ...node.childFolders.flatMap((c) => flattenWorkflows(c, here)),
  ]
}

// ---------------------------------------------------------------------------------------------
// the Explorer
// ---------------------------------------------------------------------------------------------

/** Windows-Explorer's two modes: rows with columns, or a grid of cards. */
export type ExplorerView = 'details' | 'icons'

export type ExplorerColumn =
  | 'name'
  | 'description'
  | 'contents'
  | 'pythonVersion'
  | 'createdAt'
  | 'updatedAt'

/**
 * Whether search looks only where the user is, or across everything prefetched.
 *
 * `subtree` is the more useful default for a deep tree — the point of prefetching five levels is to
 * be able to answer without a round trip — but results then need to show *where* they came from,
 * which `ExplorerRow.path` carries.
 */
export type SearchScope = 'currentScope' | 'subtree'

export interface ExplorerRow {
  ref: ElementRef
  icon: string
  name: string
  description: string | null
  /** Rendered contents summary — `10 folders · 25 workflows`. Null for leaves. */
  contents: string | null
  pythonVersion: string | null
  createdAt: string
  updatedAt: string
  /** Where it was found, when the result came from deeper than the current scope. */
  path: string[] | null
}

export interface ExplorerState {
  view: ExplorerView
  scope: number
  query: string
  searchScope: SearchScope
  sortBy: ExplorerColumn
  sortAscending: boolean
  focused: ElementRef | null
  selected: ElementRef[]
}
