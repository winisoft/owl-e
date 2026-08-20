/**
 * Tags, and the things they attach to.
 *
 * Every target is a composite key of (subject, tag), which matters to the client: those endpoints
 * take their id as named query parameters rather than a path segment. See `api/client.ts`.
 */

import type { Grouper, Step, Tool, Workflow, WorkflowFolder } from './workflow'
import type { Transform } from './transforms'
import type { Distribution, Version } from './environs'

/** Where a tag came from. System tags are Owl-E's own; pypi tags are harvested classifiers. */
export type TagSource = 'SYSTEM' | 'PYPI' | 'USER'

/** Tags nest — `childTags` is populated when the hierarchy was requested. */
export interface Tag {
  id: number
  name: string
  description: string | null
  source: TagSource | null
  childTags: Tag[] | null
}

/** Parent/child between tags, as its own record. */
export interface TagRelation {
  parentTag: Tag
  childTag: Tag
}

export interface WorkflowTag { workflow: Workflow; tag: Tag }
export interface WorkflowFolderTag { workflowFolder: WorkflowFolder; tag: Tag }
export interface GrouperTag { grouper: Grouper; tag: Tag }
export interface StepTag { step: Step; tag: Tag }
export interface ToolTag { tool: Tool; tag: Tag }
export interface TransformTag { transform: Transform; tag: Tag }
export interface DistributionTag { distribution: Distribution; tag: Tag }
export interface PackageTag { distribution: Distribution; packageVersion: Version; tag: Tag }
