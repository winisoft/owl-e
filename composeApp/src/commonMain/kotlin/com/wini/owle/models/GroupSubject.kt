package com.wini.owle.models

//export interface Package {
//    id: number;
//    name: string;
//    disambiguation?: string | null;
//    versionSpec: VersionSpec;
//}
//export interface PackageRepo {
//    id: number;
//    sourceType: string;
//    sourceUrl: string;
//}
//export interface ToolPackageDependency {
//    toolId: number;
//    tool: Tool;
//    packageId: number;
//    package: Package;
//    versionSpecId: number;
//    versionSpec: VersionSpec;
//}
//export interface VersionSpec {
//    id: number;
//    packageId: number;
//    package: Package;
//    versionMin?: string | null;
//    versionMax?: string | null;
//    versionExact?: string | null;
//}
//export interface BenchmarkingData {
//    id: number;
//    workflowExecutionId: number;
//    workflowExecution: WorkflowExecution;
//    totalExecutionTime: number; // in seconds
//    totalSteps: number;
//    aiDeterminedSteps: number;
//    predeterminedSteps: number;
//}
//export interface ExecutionLog {
//    id: number;
//    stepExecutionId: number;
//    stepExecution: StepExecution;
//    timestamp: Date;
//    logLevel: 'DEBUG' | 'INFO' | 'WARNING' | 'ERROR' | 'CRITICAL';
//    message: string;
//}
//export interface StepBenchmarkingData {
//    id: number;
//    stepExecutionId: number;
//    stepExecution: StepExecution;
//    executionTime: number;
//    resourceUsage?: string | null; // e.g., CPU, memory
//}
//export interface StepExecution {
//    id: number;
//    workflowExecutionId: number;
//    workflowExecution: WorkflowExecution;
//    stepId: number;
//    step: Step;
//    startedAt: Date;
//    completedAt?: Date | null;
//    status: 'running' | 'completed' | 'failed' | 'skipped';
//    logs: ExecutionLog[];
//}
//export interface TestCase {
//    id: number;
//    userId: number;
//    user: User;
//    name: string;
//    description?: string | null;
//    workflowId?: number | null;
//    workflow?: Workflow | null;
//    stepId?: number | null;
//    step?: Step | null;
//    createdAt: Date;
//    updatedAt: Date;
//    inputs: TestInput[];
//    expectedOutcomes: TestExpectedOutcome[];
//    results: TestResult[];
//}
//export interface TestExpectedOutcome {
//    id: number;
//    testCaseId: number;
//    testCase: TestCase;
//    dataPointName: string;
//    expectedValue?: string | null;
//    comparisonOperator:'equals'|'not_equals'|'contains'|'greater_than'|'less_than';
//}
//export interface TestInput {
//    id: number;
//    testCaseId: number;
//    testCase: TestCase;
//    dataPointName: string;
//    value?: string | null;
//    generationMethod: 'manual' | 'ai_generated';
//}
//export interface TestResult {
//    id: number;
//    testCaseId: number;
//    testCase: TestCase;
//    workflowExecutionId: number;
//    workflowExecution: WorkflowExecution;
//    passed: boolean;
//    executedAt: Date;
//}
//export interface WorkflowExecution {
//    id: number;
//    workflowId: number;
//    workflow: Workflow;
//    userId: number;
//    user: User;
//    startedAt: Date;
//    completedAt?: Date | null;
//    status: 'running' | 'completed' | 'failed' | 'cancelled';
//    stepExecutions: StepExecution[];
//}
//export interface ErrorWatch {
//    id: number;
//    workflow_step_id: number;
//    error_type: string;
//    on_met_wfstep_id: number;
//    workflowStep: Step;
//    onMetWorkflowStep: Step;
//    callStackFnFilters: ErrorWatchFn[];
//}
//export interface ErrorWatchFn {
//    id: number;
//    errorWatchId: number;
//    functionName: string;
//    position: number;
//    errorWatch: ErrorWatch;
//}
//export interface Inquisition {
//    id: number;
//    workflowStepId: number;
//    workflowStep: Step;
//    class: string;
//    dataPointId: number;
//    dataPoint: DataPoint;
//    onMetWorkflowStepId: number;
//    onMetWorkflowStep: Step;
//    executionOrder: number;
//    transformations: AppliedTransform[];
//}
//
//export interface NewInquisition {
//    id: number;
//    workflowStepId: number;
//    class: string;
//    dataPointId: number;
//    onMetWorkflowStepId: number;
//    executionOrder: number;
//    transformations: AppliedTransform[];
//}
//
//export interface PreReqInjection {
//    id: number;
//    junction_id: number;
//    source_data_point_id: number;
//    injected_data_point_id: number;
//    name: string;
//    description: string;
//    sourceDataPoint: DataPoint;
//    injectedDataPoint: DataPoint;
//}
//export interface ProvisionDataPoint {
//    provisionId: number;
//    provision: Provision;
//    dataPointId: number;
//    dataPoint: DataPoint;
//}
//export interface Provision {
//    id: number;
//    stepId: number;
//    step: Step;
//    toolParamId: number;
//    toolParam: ToolParam;
//    dataPoints: ProvisionDataPoint[];
//}
//
//export interface NewProvision {
//    stepId: number;
//    toolParamId: number;
//    provisionDataPointIds: number[];
//}
//
//export interface Revision {
//    id: number;
//    dataPointId: number;
//    dataPoint: DataPoint;
//    stepId: number;
//    step: Step;
//    transformationId?: number | null;
//    transformation?: Transformation | null;
//}
//
//export interface NewRevision {
//    dataPointId: number;
//    stepId: number;
//    transformationId?: number | null;
//}
//
//export interface WorkflowJunction {
//    id: number;
//    sourceStepId: number;
//    sourceStep: Step;
//    targetWorkflowId: number;
//    targetWorkflow: Workflow;
//    triggerInquisitionId?: number | null;
//    triggerInquisition?: Inquisition | null;
//    prereqInjections: PreReqInjection[];
//}
//export interface ErrorWatchFnTemplate {
//    id: number;
//    templateId: number;
//    template: ErrorWatchTemplate;
//    functionName: string;
//    position: number;
//}
//export interface ErrorWatchTemplate {
//    id: number;
//    userId: number;
//    user: User;
//    name: string;
//    description?: string | null;
//    errorType: string;
//    version: string;
//    createdAt: Date;
//    updatedAt: Date;
//    functions: ErrorWatchFnTemplate[];
//}
//export interface StepTemplate {
//    id: number;
//    userId: number;
//    user: User;
//    name: string;
//    description?: string | null;
//    toolId: number;
//    tool: Tool;
//    version: string;
//    createdAt: Date;
//    updatedAt: Date;
//    provisions: StepTemplateProvision[];
//    inquisitions: StepTemplateInquisition[];
//    errorWatches: StepTemplateErrorWatch[];
//}
//export interface StepTemplateErrorWatch {
//    id: number;
//    templateId: number;
//    template: StepTemplate;
//    errorWatchTemplateId: number;
//    errorWatchTemplate: ErrorWatchTemplate;
//}
//export interface StepTemplateInquisition {
//    id: number;
//    templateId: number;
//    template: StepTemplate;
//    class: string;
//    dataPointName: string;
//    executionOrder: number;
//}
//export interface StepTemplateProvision {
//    id: number;
//    templateId: number;
//    template: StepTemplate;
//    toolParamId: number;
//    toolParam: ToolParam;
//}
//export interface WorkflowTemplate {
//    id: number;
//    name: string;
//    description?: string | null;
//    version: string;
//    metadataId?: number | null;
//    metadata?: Metadata | null;
//    steps: WorkflowTemplateStep[];
//}
//export interface WorkflowTemplateStep {
//    id: number;
//    templateId: number;
//    template: WorkflowTemplate;
//    stepTemplateId: number;
//    stepTemplate: StepTemplate;
//    position: number;
//    phaseName?: string | null;
//}
//export interface AppliedTransformArg {
//    id: number;
//    appliedTransformId: number;
//    appliedTransform: AppliedTransform;
//    transformationArgId: number;
//    transformationArg: TransformationArg;
//    value: string;
//}
//export interface AppliedTransform {
//    id: number;
//    transformationId: number;
//    transformation: Transformation;
//    occurrence: string;
//    previousTransformId?: number | null;
//    previousTransform?: AppliedTransform | null;
//    resultTypeId: number;
//    resultType: TypeDeclaration;
//    dataPointId?: number | null;
//    dataPoint?: DataPoint | null;
//    toolResultStepId?: number | null;
//    toolResultStep?: Step | null;
//    args: AppliedTransformArg[];
//}
//export interface TransformationArg {
//    id: number;
//    transformationId: number;
//    transformation: Transformation;
//    typeId: number;
//    type: TypeDeclaration;
//    name: string;
//    description?: string | null;
//}
//export interface TransformationChainTemplate {
//    id: number;
//    userId: number;
//    user: User;
//    name: string;
//    description?: string | null;
//    version: string;
//    createdAt: Date;
//    updatedAt: Date;
//}
//export interface Transformation {
//    id: number;
//    name: string;
//    description: string;
//    examples: string;
//    returnsTypeId?: number | null;
//    returnsType?: TypeDeclaration | null;
//    args: TransformationArg[];
//    targetTypes: TransformTargetType[];
//    appliedTransforms: AppliedTransform[];
//}
//export interface TransformTargetType {
//    transformationId: number;
//    transformation: Transformation;
//    eligibleTypeDeclarationId: number;
//    eligibleTypeDeclaration: TypeDeclaration;
//    resultTypeDeclarationId: number;
//    resultTypeDeclaration: TypeDeclaration;
//}
//export interface TypeBound {
//    id: number;
//    typeVariableDeclarationId: number;
//    typeVariableDeclaration: TypeDeclaration;
//    boundTypeDeclarationId: number;
//    boundTypeDeclaration: TypeDeclaration;
//}
//export interface TypeDeclaration {
//    id: number;
//    typeDefinitionId: number;
//    typeDefinition: TypeDefinition;
//    alias?: string | null;
//}
//export interface TypeDefinition {
//    id: number;
//    name: string;
//    namespace: string;
//    versionSpecId: number;
//    versionSpec: VersionSpec;
//    declarations: TypeDeclaration[];
//}
//export interface TypeParameter {
//    id: number;
//    typeDeclarationId: number;
//    typeDeclaration: TypeDeclaration;
//    position: number;
//    parameterTypeDeclarationId: number;
//    parameterTypeDeclaration: TypeDeclaration;
//}
//export interface TypeSchemaRef {
//    id: number;
//    name: string;
//    description?: string | null;
//    typeDeclarationId: number;
//    type: TypeDeclaration;
//    parentRefId?: number | null;
//    parentRef?: TypeSchemaRef | null;
//    childRefs: TypeSchemaRef[];
//}
//export interface TypeUnionMember {
//    unionTypeDeclarationId: number;
//    unionTypeDeclaration: TypeDeclaration;
//    memberTypeDeclarationId: number;
//    memberTypeDeclaration: TypeDeclaration;
//}
//export interface TypeVariance {
//    id: number;
//    typeVariableDeclarationId: number;
//    typeVariableDeclaration: TypeDeclaration;
//    variance: 'covariant' | 'contravariant' | 'invariant';
//}
//
//export interface PaymentMethod {
//    stripe_payment_method_id: string;
//    user_id: number;
//    card_brand?: 'American Express'|'Diners Club'|'Discover'|'Eftpos Australia'|'Girocard'|'JCB'|'MasterCard'|'UnionPay'| 'Visa'|'Unknown' | null;
//    last_four?: string | null;
//    is_default: boolean;
//    created_at: Date;
//    updated_at: Date;
//    user: User;
//}
//export interface Tag {
//    id: number;
//    name: string;
//    description?: string | null;
//    appliesTo: Step[];
//}
//export interface TagRelation {
//    parentTagId: number;
//    parentTag: Tag;
//    childTagId: number;
//    childTag: Tag;
//}
//export interface User {
//    id: number;
//    email: string;
//    password: string;
//    reset_token?: string | null;
//    oauth_provider?: string | null;
//    oauth_id?: string | null;
//    subscription_status: 'active' | 'on hold' | 'registered' | 'cancelled';
//    api_key?: string | null;
//    created_at: Date;
//    updated_at: Date;
//    paymentMethods: PaymentMethod[];
//}
//export interface DataPoint {
//    id: number;
//    workflowId: number;
//    workflow: Workflow;
//    isReadOnly: boolean;
//    typeDeclarationId: number;
//    typeDeclaration: TypeDeclaration;
//    name: string;
//    description?: string | null;
//    textValue?: string | null;
//    blobValue?: Buffer | null;
//    storeMethod:'text'|'blob'|'file';
//}
//
//export interface NewDataPoint {
//    workflowId: number;
//    isReadOnly: boolean;
//    typeDeclarationId: number;
//    name: string;
//    description?: string | null;
//    textValue?: string | null;
//    blobValue?: Buffer | null;
//    storeMethod:'text'|'blob'|'file';
//}
//
//export interface Tool {
//    id: number;
//    name: string;
//    description?: string | null;
//    body: string;
//    returnTypeDeclarationId?: number | null;
//    returnTypeDeclaration?: TypeDeclaration | null;
//    steps: Step[];
//    packageDependencies: ToolPackageDependency[];
//}
//export interface ToolParam {
//    id: number;
//    toolId: number;
//    name: string;
//    typeDeclarationId?: number | null;
//    hasDefault: boolean;
//    typeDeclaration?: TypeDeclaration | null;
//}



//interface WorkflowFolder extends  {
//    val elementType: GroupedElementType;
//    parent_folder_id?: number | undefined;
//    createdAt: Date;
//    updatedAt: Date;
//    parentFolder?: WorkflowFolder;
//    subFolders: WorkflowFolder[];
//    childWorkflows: WorkflowSummary[];
//}

//export interface Workflow extends GroupSubject, OwleGroup {
//    pythonVersion: string;
//    steps: Step[];
//    dataPoints: DataPoint[];
//    metadata?: Metadata | null;
//}