/**
 * One coherent scenario, built to exercise the structural conditions that matter.
 *
 * Not a grab-bag of samples: a single invoice-extraction workflow inside a realistic folder tree,
 * chosen so that every awkward case a screen has to survive appears somewhere in it —
 *
 * - folder nesting deep enough that the `(y nested)` count phrasing fires
 * - groupers nested inside groupers, each with its own colour
 * - a workflow head, a default-next chain, and a terminal step with no next
 * - a read-only data point, a normal one, and one that **permits writes but has none** — the
 *   warning case
 * - a union type (`str | None`), a nested generic (`dict[str, str | None]`), and a plain base
 * - a signature using positional-only, `*args`, keyword-only, and defaults all at once
 * - a two-hop transform chain resolving `str` to `str` through `list[str]`
 * - an error watch with a handler function, an inquisition with a redirect, and a junction
 *   carrying a data point into another workflow
 *
 * Values are plausible rather than lorem — a mockup built on `Foo/Bar/Baz` hides the layout
 * problems that `Northwind Q3 Invoice Reconciliation` exposes.
 */

import type {
  DataPoint,
  Grouper,
  Step,
  Tool,
  Workflow,
  WorkflowFolder,
} from '../types/workflow'
import type { PythonVersion, Version } from '../types/environs'
import type {
  CallableParameter,
  CallableSignature,
  TypeNode,
} from '../types/typing'
import type {
  DataPointRead,
  DataPointWrite,
  ErrorWatch,
  ErrorWatchFn,
  Inquisition,
  InquisitionStepRedirect,
  WorkflowJunction,
  DataPointInjection,
} from '../types/steps'
import type {
  Transform,
  TransformOccurrence,
  TransformOccurrenceChain,
  TransformTargetType,
} from '../types/transforms'
import type { StepTag, Tag, WorkflowTag } from '../types/tags'
import type { WorkflowFolderNode } from '../models/hierarchy'
import type { RenderedSignature, RenderedType } from '../models/typeDisplay'

const T = '2026-08-12T09:14:22Z'
const T2 = '2026-08-18T16:03:51Z'

// ---------------------------------------------------------------------------------------------
// versions
// ---------------------------------------------------------------------------------------------

const version = (id: number, major: number, minor: number, micro: number): Version => ({
  id,
  epoch: 0,
  major,
  minor,
  micro,
  phase: 'FINAL',
  phaseNumber: null,
  postNumber: null,
  devNumber: null,
  local: null,
  hasOverflow: false,
  versionOverflows: [],
})

export const py312: PythonVersion = { id: 874_742, version: version(874_742, 3, 12, 4) }
export const py311: PythonVersion = { id: 874_741, version: version(874_741, 3, 11, 9) }

/** A version that overflowed — `20200101000000000000000000.0.0`, which PyPI genuinely contains. */
export const overflowedVersion: Version = {
  ...version(874_900, 0, 0, 0),
  hasOverflow: true,
  versionOverflows: [
    {
      id: 9001,
      version: version(874_900, 0, 0, 0),
      violation: 'MAJOR',
      stringValue: '20200101000000000000000000',
    },
  ],
}

// ---------------------------------------------------------------------------------------------
// types
// ---------------------------------------------------------------------------------------------

const node = (
  id: number,
  name: string,
  kind: TypeNode['kind'],
  namespace: string | null = 'builtins',
  isGeneric = false,
): TypeNode => ({ id, version: py312.version, namespace, name, kind, isGeneric })

export const tStr = node(1000, 'str', 'BASE')
export const tInt = node(1001, 'int', 'BASE')
export const tBool = node(1002, 'bool', 'BASE')
export const tNone = node(1003, 'NoneType', 'BASE')
export const tList = node(1010, 'list', 'BASE', 'builtins', true)
export const tDict = node(1011, 'dict', 'BASE', 'builtins', true)
export const tPath = node(1012, 'Path', 'BASE', 'pathlib')

export const tStrOrNone = node(1020, null as unknown as string, 'UNION', null)
export const tListStr = node(1030, null as unknown as string, 'INSTANTIATION', null)
export const tDictStrOptStr = node(1031, null as unknown as string, 'INSTANTIATION', null)

/** `str | None` — the optional case, which the UI renders specially. */
export const rStrOrNone: RenderedType = {
  kind: 'union',
  nodeId: tStrOrNone.id,
  members: [
    { kind: 'base', nodeId: tStr.id, namespace: 'builtins', name: 'str' },
    { kind: 'base', nodeId: tNone.id, namespace: 'builtins', name: 'NoneType' },
  ],
}

export const rStr: RenderedType = {
  kind: 'base',
  nodeId: tStr.id,
  namespace: 'builtins',
  name: 'str',
}

/** `list[str]` — the intermediate type in the transform chain below. */
export const rListStr: RenderedType = {
  kind: 'instantiation',
  nodeId: tListStr.id,
  origin: { kind: 'base', nodeId: tList.id, namespace: 'builtins', name: 'list' },
  args: [rStr],
}

/** `dict[str, str | None]` — a generic whose argument is itself a union. */
export const rDictStrOptStr: RenderedType = {
  kind: 'instantiation',
  nodeId: tDictStrOptStr.id,
  origin: { kind: 'base', nodeId: tDict.id, namespace: 'builtins', name: 'dict' },
  args: [rStr, rStrOrNone],
}

/** `T`, bound to `Sized` — covariant. */
export const rTypeVarT: RenderedType = {
  kind: 'typevar',
  nodeId: 1040,
  name: 'T',
  bound: { kind: 'base', nodeId: 1041, namespace: 'typing', name: 'Sized' },
  constraints: [],
  variance: 'COVARIANT',
}

// ---------------------------------------------------------------------------------------------
// the tool, and a signature using every passing style at once
// ---------------------------------------------------------------------------------------------

export const extractTool: Tool = {
  id: 500,
  name: 'extract_invoice_fields',
  description:
    'Pulls invoice fields out of extracted text using the supplied regex patterns. Returns a ' +
    'mapping of field name to value, with None where a pattern did not match.',
  body:
    'def extract_invoice_fields(text, /, *patterns, strict=False, encoding="utf-8"):\n' +
    '    ...',
  returnTypeNode: tDictStrOptStr,
}

/**
 * `extract_invoice_fields(text: str, /, *patterns: str, strict: bool = False,
 * encoding: str = "utf-8") -> dict[str, str | None]`
 *
 * Deliberately exercises all four: `text` is positional-only, `patterns` is a vararg, and both
 * `strict` and `encoding` are keyword-only with defaults — so a signature renderer has to place
 * both the `/` and the `*` correctly, and a binding surface has to collapse two optionals.
 */
export const extractSignature: RenderedSignature = {
  name: 'extract_invoice_fields',
  returns: rDictStrOptStr,
  docstring: extractTool.description,
  isStatic: false,
  isClassmethod: false,
  params: [
    {
      name: 'text',
      type: rStr,
      defaultText: null,
      isVarArg: false,
      isKwarg: false,
      positionalOnly: true,
      keywordOnly: false,
    },
    {
      name: 'patterns',
      type: rStr,
      defaultText: null,
      isVarArg: true,
      isKwarg: false,
      positionalOnly: false,
      keywordOnly: false,
    },
    {
      name: 'strict',
      type: { kind: 'base', nodeId: tBool.id, namespace: 'builtins', name: 'bool' },
      defaultText: 'False',
      isVarArg: false,
      isKwarg: false,
      positionalOnly: false,
      keywordOnly: true,
    },
    {
      name: 'encoding',
      type: rStr,
      defaultText: '"utf-8"',
      isVarArg: false,
      isKwarg: false,
      positionalOnly: false,
      keywordOnly: true,
    },
  ],
}

const signatureNode = node(1050, 'extract_invoice_fields', 'CALLABLE', 'owle.tools')

export const extractCallableSignature: CallableSignature = {
  id: 600,
  typeNode: signatureNode,
  returnTypeNode: tDictStrOptStr,
  docstring: extractTool.description,
  isStatic: false,
  isClassmethod: false,
}

export const extractParams: CallableParameter[] = extractSignature.params.map((p, i) => ({
  id: 610 + i,
  signature: extractCallableSignature,
  paramTypeNode: p.name === 'strict' ? tBool : tStr,
  position: i,
  paramName: p.name,
  isVarArg: p.isVarArg,
  isKwarg: p.isKwarg,
  isPositionalOnly: p.positionalOnly,
  isKeywordOnly: p.keywordOnly,
  defaultValueText: p.defaultText,
}))

const downloadTool: Tool = {
  id: 501,
  name: 'fetch_pdf',
  description: 'Downloads a PDF over HTTP and writes it to the workspace.',
  body: 'def fetch_pdf(url, /, *, timeout=30.0):\n    ...',
  returnTypeNode: tStr,
}

const ocrTool: Tool = {
  id: 502,
  name: 'pdf_to_text',
  description: 'Extracts text from a PDF, one page per line.',
  body: 'def pdf_to_text(path, /):\n    ...',
  returnTypeNode: tStr,
}

const csvTool: Tool = {
  id: 503,
  name: 'write_csv',
  description: 'Writes a mapping to CSV and returns the path written.',
  body: 'def write_csv(rows, /, *, delimiter=","):\n    ...',
  returnTypeNode: tStr,
}

// ---------------------------------------------------------------------------------------------
// folders — nested deeply enough that the "(y nested)" phrasing fires
// ---------------------------------------------------------------------------------------------

export const rootFolder: WorkflowFolder = {
  id: 1,
  parentFolder: null,
  name: 'My Workflows',
  description: 'Everything you build lives here.',
  createdAt: T,
  updatedAt: T2,
}

const clientProjects: WorkflowFolder = {
  id: 2,
  parentFolder: rootFolder,
  name: 'Client Projects',
  description: 'Billable work, one folder per client.',
  createdAt: T,
  updatedAt: T2,
}

const northwind: WorkflowFolder = {
  id: 3,
  parentFolder: clientProjects,
  name: 'Northwind Trading',
  description: 'Invoice and reconciliation pipelines for Northwind.',
  createdAt: T,
  updatedAt: T2,
}

const acme: WorkflowFolder = {
  id: 4,
  parentFolder: clientProjects,
  name: 'Acme Industrial',
  description: null,
  createdAt: T,
  updatedAt: T,
}

const experiments: WorkflowFolder = {
  id: 5,
  parentFolder: rootFolder,
  name: 'Experiments',
  description: 'Things that are not ready to be relied on.',
  createdAt: T,
  updatedAt: T,
}

// ---------------------------------------------------------------------------------------------
// the workflow
// ---------------------------------------------------------------------------------------------

export const invoiceWorkflow: Workflow = {
  id: 10,
  name: 'Invoice Extraction',
  description: 'Downloads a supplier invoice, extracts its fields, and writes them to CSV.',
  pythonVersion: py312,
  parentFolder: northwind,
  createdAt: T,
  updatedAt: T2,
}

const reconciliationWorkflow: Workflow = {
  id: 11,
  name: 'Monthly Reconciliation',
  description: 'Matches extracted invoices against the ledger.',
  pythonVersion: py312,
  parentFolder: northwind,
  createdAt: T,
  updatedAt: T,
}

const leadImport: Workflow = {
  id: 12,
  name: 'Lead Import',
  description: 'Pulls leads from the CRM export and normalises them.',
  pythonVersion: py311,
  parentFolder: acme,
  createdAt: T,
  updatedAt: T,
}

const ocrTrial: Workflow = {
  id: 13,
  name: 'PDF OCR Trial',
  description: 'Testing whether OCR beats text extraction on scanned invoices.',
  pythonVersion: py312,
  parentFolder: experiments,
  createdAt: T2,
  updatedAt: T2,
}

/**
 * The tree as `WorkflowHierarchyService` returns it.
 *
 * Root holds 4 folders — 2 directly, 2 nested — and 4 workflows, all of them nested. So the root
 * card reads `contains 4 workflowFolders (2 nested)` and `contains 4 workflows (4 nested)`, which
 * is the phrasing rule doing its job.
 */
export const folderTree: WorkflowFolderNode = {
  folder: rootFolder,
  workflows: [],
  childFolders: [
    {
      folder: clientProjects,
      workflows: [],
      childFolders: [
        {
          folder: northwind,
          workflows: [invoiceWorkflow, reconciliationWorkflow],
          childFolders: [],
        },
        { folder: acme, workflows: [leadImport], childFolders: [] },
      ],
    },
    { folder: experiments, workflows: [ocrTrial], childFolders: [] },
  ],
}

// ---------------------------------------------------------------------------------------------
// groupers — nested, coloured
// ---------------------------------------------------------------------------------------------

export const fetchGrouper: Grouper = {
  id: 100,
  workflow: invoiceWorkflow,
  parentGrouper: null,
  name: 'Fetch',
  description: 'Get the document in front of us.',
  posX: 40,
  posY: 40,
  color: '#6897bb',
}

export const extractGrouper: Grouper = {
  id: 101,
  workflow: invoiceWorkflow,
  parentGrouper: null,
  name: 'Extract',
  description: 'Turn the document into fields.',
  posX: 40,
  posY: 240,
  color: '#6a8759',
}

/** Nested inside Extract — which is what makes the parent's count read `(1 nested)`. */
export const normaliseGrouper: Grouper = {
  id: 102,
  workflow: invoiceWorkflow,
  parentGrouper: extractGrouper,
  name: 'Normalise',
  description: 'Clean the values before they are written.',
  posX: 80,
  posY: 320,
  color: '#9876aa',
}

// ---------------------------------------------------------------------------------------------
// steps — a head, a chain, and a terminal
// ---------------------------------------------------------------------------------------------

const step = (
  id: number,
  name: string,
  description: string,
  tool: Tool,
  parentGrouper: Grouper | null,
  posX: number,
  posY: number,
  isWorkflowHead = false,
  color: string | null = null,
): Step => ({
  id,
  name,
  description,
  parentGrouper,
  defaultNextStep: null,
  posX,
  posY,
  color,
  workflow: invoiceWorkflow,
  isWorkflowHead,
  tool,
  createdAt: T,
  updatedAt: T2,
})

export const downloadStep = step(
  200, 'Download Invoice', 'Fetches the supplier PDF over HTTPS.',
  downloadTool, fetchGrouper, 60, 80, true,
)
export const retryStep = step(
  204, 'Retry Download', 'Waits and tries once more before giving up.',
  downloadTool, fetchGrouper, 260, 80, false, '#cc7832',
)
export const extractTextStep = step(
  201, 'Extract Text', 'Reads the PDF into a single block of text.',
  ocrTool, extractGrouper, 60, 280,
)
export const parseFieldsStep = step(
  202, 'Parse Fields', 'Applies the field patterns to the extracted text.',
  extractTool, normaliseGrouper, 100, 360,
)
export const writeCsvStep = step(
  203, 'Write CSV', 'Writes the normalised fields out.',
  csvTool, normaliseGrouper, 100, 440,
)

// the chain, wired after construction because it is circular by nature
downloadStep.defaultNextStep = extractTextStep
extractTextStep.defaultNextStep = parseFieldsStep
parseFieldsStep.defaultNextStep = writeCsvStep
retryStep.defaultNextStep = extractTextStep
// writeCsvStep has none — "May end workflow"

export const steps: Step[] = [
  downloadStep,
  retryStep,
  extractTextStep,
  parseFieldsStep,
  writeCsvStep,
]

// ---------------------------------------------------------------------------------------------
// data points — including the read-only and the warning cases
// ---------------------------------------------------------------------------------------------

const dataPoint = (
  id: number,
  name: string,
  description: string,
  typeNode: TypeNode,
  maxWrites: number,
  presetValue: string | null = null,
): DataPoint => ({
  id,
  workflow: invoiceWorkflow,
  name,
  description,
  presetValue,
  storeMethod: 'TEXT',
  typeNode,
  maxWrites,
  createdAt: T,
  updatedAt: T2,
})

/** maxWrites 0 — reads as `read-only value`. */
export const sourceUrl = dataPoint(
  300, 'source_url', 'Where the supplier publishes the invoice.', tStr, 0,
  'https://portal.northwind.example/invoices/latest.pdf',
)

export const pdfPath = dataPoint(
  301, 'pdf_path', 'Local path the downloaded PDF was written to.', tStr, 1,
)

export const rawText = dataPoint(
  302, 'raw_text', 'Everything the extractor found, one page per line.', tStr, 1,
)

/** A generic whose argument is a union — the interesting rendering case. */
export const fields = dataPoint(
  303, 'fields', 'Field name to value, with None where nothing matched.', tDictStrOptStr, 1,
)

/**
 * **The warning case.** Writes are permitted and none exist, so the card reads
 * `0 steps may overwrite` in a warning style — the workflow declares an intent nothing fulfils.
 */
export const outCsv = dataPoint(
  304, 'out_csv', 'Path of the CSV that was written.', tStr, 2,
)

export const dataPoints: DataPoint[] = [sourceUrl, pdfPath, rawText, fields, outCsv]

// ---------------------------------------------------------------------------------------------
// transforms — a two-hop chain
// ---------------------------------------------------------------------------------------------

export const splitTransform: Transform = {
  id: 700,
  name: 'Result Split',
  description: "The tool's result, split into a list of elements delimited by some character",
  examples: "ex: result.split(',')",
}

export const indexTransform: Transform = {
  id: 701,
  name: 'Result Indexed',
  description: 'A specified index of the result',
  examples: 'ex: result[0]',
}

export const transformTargets: TransformTargetType[] = [
  { transform: splitTransform, eligibleTypeNode: tStr, returnTypeNode: tListStr },
  { transform: indexTransform, eligibleTypeNode: tListStr, returnTypeNode: tStr },
]

const splitOccurrence: TransformOccurrence = {
  id: 710,
  transform: splitTransform,
  initiator: 'DATA_POINT_READ',
}

const indexOccurrence: TransformOccurrence = {
  id: 711,
  transform: indexTransform,
  initiator: 'DATA_POINT_READ',
}

/** `raw_text: str → .split('\n') → list[str] → [0] → str`, feeding a parameter that wants `str`. */
export const transformChain: TransformOccurrenceChain[] = [
  { antecedentOccurrence: splitOccurrence, subsequentOccurrence: indexOccurrence },
]

// ---------------------------------------------------------------------------------------------
// reads and writes
// ---------------------------------------------------------------------------------------------

/** The transformed read: `raw_text` reaching `text: str` through two hops. */
export const textParamRead: DataPointRead = {
  id: 800,
  step: parseFieldsStep,
  inquisition: null,
  callableParameter: extractParams[0],
  dataPoint: rawText,
  transformOccurrence: indexOccurrence,
}

/** A direct read — types already agree, so no transform. */
export const encodingParamRead: DataPointRead = {
  id: 801,
  step: parseFieldsStep,
  inquisition: null,
  callableParameter: extractParams[3],
  dataPoint: sourceUrl,
  transformOccurrence: null,
}

export const writes: DataPointWrite[] = [
  { id: 810, dataPoint: pdfPath, step: downloadStep, transformOccurrence: null },
  { id: 811, dataPoint: rawText, step: extractTextStep, transformOccurrence: null },
  { id: 812, dataPoint: fields, step: parseFieldsStep, transformOccurrence: null },
  // note: nothing writes out_csv — deliberately, to produce the warning
]

// ---------------------------------------------------------------------------------------------
// failure handling
// ---------------------------------------------------------------------------------------------

export const httpErrorWatch: ErrorWatch = {
  id: 900,
  step: downloadStep,
  errorType: 'HTTPError',
  triggerStep: retryStep,
}

export const errorWatchFns: ErrorWatchFn[] = [
  {
    id: 910,
    errorWatch: httpErrorWatch,
    functionNode: node(1060, 'log_fetch_failure', 'CALLABLE', 'owle.handlers'),
    position: 0,
  },
  {
    id: 911,
    errorWatch: httpErrorWatch,
    functionNode: node(1061, 'notify_operator', 'CALLABLE', 'owle.handlers'),
    position: 1,
  },
]

// ---------------------------------------------------------------------------------------------
// conditions and branching
// ---------------------------------------------------------------------------------------------

export const emptyFieldsInquisition: Inquisition = {
  id: 950,
  step: parseFieldsStep,
  executionOrder: 0,
}

export const inquisitionRedirect: InquisitionStepRedirect = {
  id: 960,
  step: retryStep,
  workflow: invoiceWorkflow,
}

export const reconciliationJunction: WorkflowJunction = {
  id: 970,
  sourceStep: writeCsvStep,
  targetWorkflow: reconciliationWorkflow,
}

/** Carrying `out_csv` across the junction, since the two workflows have separate data stores. */
export const csvInjection: DataPointInjection = {
  id: 980,
  junction: reconciliationJunction,
  sourceDataPoint: outCsv,
  injectedDataPoint: {
    ...outCsv,
    id: 305,
    workflow: reconciliationWorkflow,
    name: 'incoming_csv',
  },
  name: 'CSV handover',
  description: 'The extracted invoice, handed to reconciliation.',
}

// ---------------------------------------------------------------------------------------------
// tags
// ---------------------------------------------------------------------------------------------

export const tags: Tag[] = [
  { id: 1, name: 'billing', description: 'Anything invoice or ledger related.', source: 'USER', childTags: null },
  { id: 2, name: 'pdf', description: null, source: 'USER', childTags: null },
  { id: 3, name: 'Topic :: Text Processing', description: null, source: 'PYPI', childTags: null },
  { id: 4, name: 'needs-review', description: 'Flagged during the last audit.', source: 'SYSTEM', childTags: null },
]

export const workflowTags: WorkflowTag[] = [
  { workflow: invoiceWorkflow, tag: tags[0] },
  { workflow: invoiceWorkflow, tag: tags[1] },
]

export const stepTags: StepTag[] = [{ step: parseFieldsStep, tag: tags[3] }]
