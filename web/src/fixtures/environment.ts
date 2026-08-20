/**
 * Environment resolution, migration impact, and an execution timeline — for the same workflow as
 * `scenario.ts`.
 *
 * The numbers are the point. The constraint chart only earns its place if the bands genuinely
 * disagree, so `pandas` and `numpy` here pull in opposite directions and leave a two-series window;
 * the migration report only earns its place if moving somewhere plausible actually breaks
 * something, so 3.12 → 3.13 is blocked by one package and three steps.
 */

import type {
  EnvironmentTemplate,
  MigrationImpact,
  PythonRelease,
  ResolvedEnvironment,
} from '../models/environment'
import type { ExecutionLog, WorkflowExecution } from '../types/runs'
import {
  downloadStep,
  extractTextStep,
  fields,
  invoiceWorkflow,
  parseFieldsStep,
  pdfPath,
  rawText,
  writeCsvStep,
} from './scenario'

// ---------------------------------------------------------------------------------------------
// the axis
// ---------------------------------------------------------------------------------------------

const release = (id: number, minor: number, micro: number, isStable = true): PythonRelease => ({
  id,
  major: 3,
  minor,
  micro,
  label: `3.${minor}.${micro}`,
  isStable,
  sortKey: 3 * 1_000_000 + minor * 1_000 + micro,
})

export const pythonAxis: PythonRelease[] = [
  release(874_738, 9, 22),
  release(874_739, 10, 15),
  release(874_741, 11, 9),
  release(874_742, 12, 4),
  release(874_743, 13, 1),
  release(874_744, 14, 0, false),
]

const bySeries = (s: string) => pythonAxis.find((r) => `${r.major}.${r.minor}` === s)!

// ---------------------------------------------------------------------------------------------
// a resolved environment, with the bands genuinely in tension
// ---------------------------------------------------------------------------------------------

export const invoiceEnvironment: ResolvedEnvironment = {
  axis: pythonAxis,
  current: bySeries('3.12'),
  resolving: false,
  bands: [
    {
      distribution: 'requests',
      pinnedVersion: '2.32.3',
      direct: true,
      via: null,
      specifier: '>=3.8',
      supportedSeries: ['3.9', '3.10', '3.11', '3.12', '3.13', '3.14'],
      extras: [],
      availableExtras: ['socks', 'use_chardet_on_py3'],
    },
    {
      distribution: 'pdfplumber',
      pinnedVersion: '0.11.4',
      direct: true,
      via: null,
      specifier: '>=3.8',
      supportedSeries: ['3.9', '3.10', '3.11', '3.12', '3.13', '3.14'],
      extras: [],
      availableExtras: [],
    },
    {
      // the upper edge
      distribution: 'pandas',
      pinnedVersion: '2.2.3',
      direct: true,
      via: null,
      specifier: '>=3.11,<3.14',
      supportedSeries: ['3.11', '3.12', '3.13'],
      extras: ['excel'],
      availableExtras: ['excel', 'parquet', 'plot', 'aws'],
    },
    {
      // and the other upper edge, which is what actually pins the window at 3.12
      distribution: 'numpy',
      pinnedVersion: '1.26.4',
      direct: false,
      via: 'pandas',
      specifier: '>=3.9,<3.13',
      supportedSeries: ['3.9', '3.10', '3.11', '3.12'],
      extras: [],
      availableExtras: [],
    },
    {
      distribution: 'python-dateutil',
      pinnedVersion: '2.9.0',
      direct: false,
      via: 'pandas',
      specifier: '>=3.7',
      supportedSeries: ['3.9', '3.10', '3.11', '3.12', '3.13', '3.14'],
      extras: [],
      availableExtras: [],
    },
  ],
  viableSeries: ['3.11', '3.12'],
  recommended: bySeries('3.12'),
  conflicts: [
    {
      message:
        'pandas requires 3.11 or later and numpy requires below 3.13, leaving 3.11 – 3.12.',
      blamed: ['pandas', 'numpy'],
      severity: 'narrowing',
    },
  ],
}

/** The over-constrained case — what the view has to say when the window closes entirely. */
export const overConstrainedEnvironment: ResolvedEnvironment = {
  ...invoiceEnvironment,
  bands: [
    ...invoiceEnvironment.bands,
    {
      distribution: 'tensorflow',
      pinnedVersion: '2.16.1',
      direct: true,
      via: null,
      specifier: '>=3.9,<3.12',
      supportedSeries: ['3.9', '3.10', '3.11'],
      extras: [],
      availableExtras: [],
    },
  ],
  viableSeries: [],
  recommended: null,
  conflicts: [
    {
      message:
        'pandas requires 3.11 or later; tensorflow requires below 3.12. Only 3.11 satisfies both, ' +
        'and numpy excludes nothing further — but the workflow is currently on 3.12.',
      blamed: ['pandas', 'tensorflow'],
      severity: 'blocking',
    },
  ],
}

export const templates: EnvironmentTemplate[] = [
  {
    id: 'data-processing',
    name: 'Data processing',
    description: 'Tabular work: read, reshape, aggregate, write.',
    distributions: ['pandas', 'numpy', 'openpyxl'],
  },
  {
    id: 'web-scraping',
    name: 'Web scraping',
    description: 'Fetch pages, parse markup, follow links politely.',
    distributions: ['requests', 'beautifulsoup4', 'lxml'],
  },
  {
    id: 'documents',
    name: 'Document extraction',
    description: 'PDFs and images in, structured fields out.',
    distributions: ['pdfplumber', 'pillow', 'pytesseract'],
  },
]

// ---------------------------------------------------------------------------------------------
// the migration report — 3.12 to 3.13
// ---------------------------------------------------------------------------------------------

/**
 * Moving to 3.13 from 3.12.
 *
 * Shows all three outcome kinds at once, and — the part users do not anticipate — three steps
 * broken by *type* availability rather than by anything in the package list. `numpy` is the hard
 * block; `pandas` and `requests` can be bumped; `pdfplumber` is already fine.
 */
export const migrationTo313: MigrationImpact = {
  from: bySeries('3.12'),
  target: bySeries('3.13'),
  packages: [
    { kind: 'ok', distribution: 'pdfplumber' },
    { kind: 'ok', distribution: 'python-dateutil' },
    { kind: 'bump', distribution: 'requests', from: '2.32.3', to: '2.32.4' },
    { kind: 'bump', distribution: 'pandas', from: '2.2.3', to: '2.3.1' },
    {
      kind: 'blocked',
      distribution: 'numpy',
      reason: 'No release of numpy supports Python 3.13. Pinned at 1.26.4 by pandas.',
    },
  ],
  affectedSteps: [
    {
      stepId: extractTextStep.id,
      stepName: extractTextStep.name,
      toolName: 'pdf_to_text',
      missingTypes: ['pdfplumber.page.Page'],
    },
    {
      stepId: parseFieldsStep.id,
      stepName: parseFieldsStep.name,
      toolName: 'extract_invoice_fields',
      missingTypes: ['re.Pattern[str]'],
    },
    {
      stepId: writeCsvStep.id,
      stepName: writeCsvStep.name,
      toolName: 'write_csv',
      missingTypes: ['pandas.DataFrame'],
    },
  ],
  transformChainsToReview: 2,
}

/** The uneventful case, so a screen can show what "nothing to worry about" looks like. */
export const migrationTo311: MigrationImpact = {
  from: bySeries('3.12'),
  target: bySeries('3.11'),
  packages: [
    { kind: 'ok', distribution: 'requests' },
    { kind: 'ok', distribution: 'pdfplumber' },
    { kind: 'ok', distribution: 'pandas' },
    { kind: 'ok', distribution: 'numpy' },
    { kind: 'ok', distribution: 'python-dateutil' },
  ],
  affectedSteps: [],
  transformChainsToReview: 0,
}

// ---------------------------------------------------------------------------------------------
// an execution
// ---------------------------------------------------------------------------------------------

export const lastRun: WorkflowExecution = {
  id: 4001,
  workflow: invoiceWorkflow,
  startedAt: '2026-08-18T16:02:11Z',
  completedAt: '2026-08-18T16:03:51Z',
  status: 'COMPLETED',
}

/**
 * The timeline, exercising most of the event vocabulary — including an error watch firing and the
 * retry that followed, since a run where nothing goes wrong tells a designer very little.
 */
export const runLog: ExecutionLog[] = [
  {
    id: 5001, workflowExecution: lastRun, seq: 1, loggedAt: '2026-08-18T16:02:11Z',
    eventKind: 'RUN_STARTED', logLevel: 'INFO',
    workflow: invoiceWorkflow, step: null, inquisition: null, dataPoint: null,
    detail: null, message: 'Run started on Python 3.12.4',
  },
  {
    id: 5002, workflowExecution: lastRun, seq: 2, loggedAt: '2026-08-18T16:02:11Z',
    eventKind: 'STEP_ENTERED', logLevel: 'INFO',
    workflow: invoiceWorkflow, step: downloadStep, inquisition: null, dataPoint: null,
    detail: null, message: 'Download Invoice',
  },
  {
    id: 5003, workflowExecution: lastRun, seq: 3, loggedAt: '2026-08-18T16:02:14Z',
    eventKind: 'ERROR_WATCH_FIRED', logLevel: 'WARN',
    workflow: invoiceWorkflow, step: downloadStep, inquisition: null, dataPoint: null,
    detail: 'HTTPError: 503 Service Unavailable',
    message: 'HTTPError caught; routing to Retry Download',
  },
  {
    id: 5004, workflowExecution: lastRun, seq: 4, loggedAt: '2026-08-18T16:02:29Z',
    eventKind: 'TOOL_RETURNED', logLevel: 'INFO',
    workflow: invoiceWorkflow, step: downloadStep, inquisition: null, dataPoint: null,
    detail: null, message: 'fetch_pdf returned after 1 retry',
  },
  {
    id: 5005, workflowExecution: lastRun, seq: 5, loggedAt: '2026-08-18T16:02:29Z',
    eventKind: 'DATA_POINT_WRITTEN', logLevel: 'DEBUG',
    workflow: invoiceWorkflow, step: downloadStep, inquisition: null, dataPoint: pdfPath,
    detail: '/workspace/invoices/NW-2026-0814.pdf', message: 'pdf_path written',
  },
  {
    id: 5006, workflowExecution: lastRun, seq: 6, loggedAt: '2026-08-18T16:02:44Z',
    eventKind: 'DATA_POINT_WRITTEN', logLevel: 'DEBUG',
    workflow: invoiceWorkflow, step: extractTextStep, inquisition: null, dataPoint: rawText,
    detail: '14,882 characters across 3 pages', message: 'raw_text written',
  },
  {
    id: 5007, workflowExecution: lastRun, seq: 7, loggedAt: '2026-08-18T16:03:02Z',
    eventKind: 'PARAM_RESOLVED', logLevel: 'TRACE',
    workflow: invoiceWorkflow, step: parseFieldsStep, inquisition: null, dataPoint: rawText,
    detail: "raw_text -> .split('\\n') -> [0] -> text: str",
    message: 'text resolved through 2 transforms',
  },
  {
    id: 5008, workflowExecution: lastRun, seq: 8, loggedAt: '2026-08-18T16:03:11Z',
    eventKind: 'INQUISITION_EVALUATED', logLevel: 'DEBUG',
    workflow: invoiceWorkflow, step: parseFieldsStep, inquisition: null, dataPoint: fields,
    detail: 'fields is not empty', message: 'No redirect taken',
  },
  {
    id: 5009, workflowExecution: lastRun, seq: 9, loggedAt: '2026-08-18T16:03:51Z',
    eventKind: 'WORKFLOW_ENDED', logLevel: 'INFO',
    workflow: invoiceWorkflow, step: writeCsvStep, inquisition: null, dataPoint: null,
    detail: null, message: 'Completed in 1m 40s',
  },
]
