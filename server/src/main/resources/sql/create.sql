DROP DATABASE IF EXISTS owle_db;
CREATE DATABASE owle_db;
USE owle_db;

START TRANSACTION;

-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
-- ----------------------------------------------------------------------------------
--                     PACKAGES & TYPES                                            --
-- ----------------------------------------------------------------------------------
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

-- Version Specifications
CREATE TABLE version_specs ( 
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  versionMin VARCHAR(127), 
  versionMax VARCHAR(127),
  versionExact VARCHAR(127) 
);

CREATE TABLE packages (
    id BIGINT not null auto_increment PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    disambiguation VARCHAR(255),
    versionSpecId BIGINT NOT NULL,
    FOREIGN KEY(versionSpecId) REFERENCES version_specs(id)
);

CREATE TABLE type_definitions (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    namespace VARCHAR(512) NOT NULL,
    versionSpecId BIGINT NOT NULL,
    FOREIGN KEY(versionSpecId) REFERENCES version_specs(id)
);

CREATE TABLE type_declarations (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    typeDefinitionId BIGINT NOT NULL, 
    alias VARCHAR(255),
    FOREIGN KEY(typeDefinitionId) REFERENCES type_definitions(id)
);

CREATE TABLE type_union_members (
    unionTypeDeclarationId BIGINT NOT NULL,
    memberTypeDeclarationId BIGINT NOT NULL,
    PRIMARY KEY (unionTypeDeclarationId, memberTypeDeclarationId),
    FOREIGN KEY(unionTypeDeclarationId) REFERENCES type_declarations(id),
    FOREIGN KEY(memberTypeDeclarationId) REFERENCES type_declarations(id)
);

CREATE TABLE type_parameters (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    typeDeclarationId BIGINT NOT NULL, 
    position BIGINT NOT NULL, 
    parameterTypeDeclarationId BIGINT NOT NULL, 
    FOREIGN KEY(typeDeclarationId) REFERENCES type_declarations(id),
    FOREIGN KEY(parameterTypeDeclarationId) REFERENCES type_declarations(id)
);

CREATE TABLE type_bounds (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    typeVariableDeclarationId BIGINT NOT NULL,
    boundTypeDeclarationId BIGINT NOT NULL,
    FOREIGN KEY(typeVariableDeclarationId) REFERENCES type_declarations(id),
    FOREIGN KEY(boundTypeDeclarationId) REFERENCES type_declarations(id)
);

CREATE TABLE type_variance (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    typeVariableDeclarationId BIGINT NOT NULL,
    variance ENUM('covariant', 'contravariant', 'invariant') DEFAULT 'invariant',
    FOREIGN KEY(typeVariableDeclarationId) REFERENCES type_declarations(id)
);

-- the data elements that comprise the data model specification of a type defined in a third-party package
CREATE TABLE type_schema_refs (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  parentRefId BIGINT,
  name VARCHAR(255) NOT NULL,
  typeDeclarationId BIGINT NOT NULL,
  description TEXT,
  FOREIGN KEY(parentRefId) REFERENCES type_schema_refs(id),
  FOREIGN KEY(typeDeclarationId) REFERENCES type_declarations(id)
);



-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
-- ----------------------------------------------------------------------------------
--                  WORKFLOW CONFIGUATION                                          --
-- ----------------------------------------------------------------------------------
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

CREATE TABLE workflow_folders (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    img BLOB,
    parentFolderId BIGINT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY(parentFolderId) REFERENCES workflow_folders(id)
);

CREATE TABLE workflows (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  pythonVersion VARCHAR(127) NOT NULL DEFAULT '3.11',
  parentFolderId BIGINT,
  img BLOB,
  metadata JSON,
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY(parentFolderId) REFERENCES workflow_folders(id)
);

-- Main metadata table
CREATE TABLE workflow_metadata (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    workflow_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    version VARCHAR(20) NOT NULL DEFAULT '1.0.0',
    copyright_information TEXT,
    contact_information TEXT,
    third_party_licenses TEXT,
    discretion_advised_content BOOLEAN NOT NULL DEFAULT FALSE,
    commercial_usage ENUM('PERSONAL_NON_PROFIT', 'PERSONAL_PROFIT', 'CORPORATION') NOT NULL DEFAULT 'PERSONAL_NON_PROFIT',
    credit_notation ENUM('REQUIRED', 'UNNECESSARY') NOT NULL DEFAULT 'REQUIRED',
    allow_redistribution BOOLEAN NOT NULL DEFAULT FALSE,
    modification ENUM('PROHIBITED', 'ALLOW_MODIFICATION', 'ALLOW_MODIFICATION_REDISTRIBUTION') NOT NULL DEFAULT 'PROHIBITED',
    distribution_version VARCHAR(20) NOT NULL DEFAULT '1.0.0',
    distribution_accepted_at DATETIME NOT NULL,
    distribution_updated_at DATETIME NOT NULL,

    FOREIGN KEY (workflow_id) REFERENCES workflows(id)
);

-- Contributors join table
CREATE TABLE workflow_metadata_contributors (
    metadata_id BIGINT NOT NULL,
    contributor_user_id BIGINT NOT NULL,

    PRIMARY KEY (metadata_id, contributor_user_id),
    FOREIGN KEY (metadata_id) REFERENCES workflow_metadata(id) ON DELETE CASCADE,
    FOREIGN KEY (contributor_user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Step exclusions join table
CREATE TABLE metadata_step_exclusions (
    metadata_id BIGINT NOT NULL,
    excluded_step_id BIGINT NOT NULL,

    PRIMARY KEY (metadata_id, excluded_step_id),
    FOREIGN KEY (metadata_id) REFERENCES workflow_metadata(id) ON DELETE CASCADE,
    FOREIGN KEY (excluded_step_id) REFERENCES steps(id) ON DELETE CASCADE
);


CREATE TABLE tools (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(512) NOT NULL,
  description TEXT,
  body TEXT NOT NULL,
  returnTypeDeclarationId BIGINT DEFAULT NULL,
  FOREIGN KEY(returnTypeDeclarationId) REFERENCES type_declarations(id)
);

-- Tool Parameters Table (for tools)
CREATE TABLE tool_params (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  toolId BIGINT NOT NULL,
  name VARCHAR(255) NOT NULL,
  typeDeclarationId BIGINT NOT NULL,
  hasDefault BOOLEAN NOT NULL DEFAULT FALSE,
  FOREIGN KEY(toolId) REFERENCES tools(id),
  FOREIGN KEY(typeDeclarationId) REFERENCES type_declarations(id)
);

CREATE TABLE phases (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(512) NOT NULL,
    description TEXT,
    displayOrder BIGINT NOT NULL,
    parentPhaseId BIGINT,
    workflowId BIGINT NOT NULL,
    FOREIGN KEY(parentPhaseId) REFERENCES phases(id) ON DELETE CASCADE,
    FOREIGN KEY(workflowId) REFERENCES workflows(id) ON DELETE CASCADE
);

CREATE TABLE steps (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(512) NOT NULL,
    description TEXT,
    parentPhaseId BIGINT,
    workflowId BIGINT NOT NULL,
    toolId BIGINT NOT NULL,
    isWorkflowHead BOOLEAN NOT NULL DEFAULT FALSE,
    defaultNextId BIGINT,
    displayOrder BIGINT NOT NULL,
    metadata JSON,
    img BLOB,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY(parentPhaseId) REFERENCES phases(id),
    FOREIGN KEY(workflowId) REFERENCES workflows(id),
    FOREIGN KEY(toolId) REFERENCES tools(id),
    FOREIGN KEY(defaultNextId) REFERENCES steps(id)
);

CREATE TABLE data_points (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  workflowId BIGINT NOT NULL,
  name VARCHAR(255) NOT NULL,
  presetValue TEXT,
  storeMethod ENUM('text', 'blob', 'file'),
  typeDeclarationId BIGINT NOT NULL,
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY(workflowId) REFERENCES workflows(id),
  FOREIGN KEY(typeDeclarationId) REFERENCES type_declarations(id)
);

CREATE TABLE tool_package_dependencies(
  toolId BIGINT NOT NULL,
  packageId BIGINT NOT NULL,
  versionSpecId BIGINT NOT NULL,
  PRIMARY KEY (toolId, packageId, versionSpecId),
  FOREIGN KEY(toolId) REFERENCES tools(id),
  FOREIGN KEY(packageId) REFERENCES packages(id),
  FOREIGN KEY(versionSpecId) REFERENCES version_specs(id)
);

-- Enables the users to set a predetermined source for the fulfillment of a dependencies in a workflow environment
CREATE TABLE packageRepositories (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sourceType ENUM('pypi', 'conda', 'custom', 'github', 'local') NOT NULL,
    sourceUrl TEXT 
);


-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
-- ----------------------------------------------------------------------------------
--                        TRANSFORMATIONS                                         --
-- ----------------------------------------------------------------------------------
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 


-- Transformations Table
CREATE TABLE transformations (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    examples TEXT,
    returnsTypeDeclarationId BIGINT,
    FOREIGN KEY(returnsTypeDeclarationId) REFERENCES type_declarations(id)
);

-- Transform Target Types Table
CREATE TABLE transform_target_types(
    transformationId BIGINT NOT NULL,
    eligibleTypeDeclarationId BIGINT NOT NULL,
    PRIMARY KEY(transformationId, eligibleTypeDeclarationId),
    FOREIGN KEY(transformationId) REFERENCES transformations(id),
    FOREIGN KEY(eligibleTypeDeclarationId) REFERENCES type_declarations(id)
);

-- Transformation Arguments Table
CREATE TABLE transformation_args (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    transformationId BIGINT NOT NULL,
    typeDeclarationId BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    FOREIGN KEY(transformationId) REFERENCES transformations(id),
    FOREIGN KEY(typeDeclarationId) REFERENCES type_declarations(id)
);

-- Applied Transforms Table
CREATE TABLE applied_transforms (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    transformationId BIGINT NOT NULL,
    occurrence ENUM('provision', 'revision', 'inquisition', 'prereqInjection') NOT NULL,
    previousTransformId BIGINT NULL,
    dataPointId BIGINT NULL,
    toolResultStepId BIGINT NULL,
    FOREIGN KEY(transformationId) REFERENCES transformations(id),
    FOREIGN KEY(previousTransformId) REFERENCES applied_transforms(id),
    FOREIGN KEY(dataPointId) REFERENCES data_points(id),
    FOREIGN KEY(toolResultStepId) REFERENCES steps(id)
--     CONSTRAINT chkapplied_transformsSource
--     CHECK (
--         (dataPointId IS NOT NULL AND toolResultStepId IS NULL) OR
--         (dataPointId IS NULL AND toolResultStepId IS NOT NULL)
--     )
);

-- Applied Transformation Arguments Table
CREATE TABLE applied_transform_args (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    appliedTransformId BIGINT NOT NULL,
    transformationArgId BIGINT NOT NULL,
    value TEXT NOT NULL,
    FOREIGN KEY(appliedTransformId) REFERENCES applied_transforms(id),
    FOREIGN KEY(transformationArgId) REFERENCES transformation_args(id)
);


-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
-- ----------------------------------------------------------------------------------
--                       STEP-LEVEL CONFIGURATION                                  --
-- ----------------------------------------------------------------------------------
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 


-- Error Watches Table
CREATE TABLE error_watches (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    stepId BIGINT NOT NULL,
    errorType VARCHAR(255) NOT NULL,
    onMetStepId BIGINT NOT NULL,
    FOREIGN KEY(stepId) REFERENCES steps(id),
    FOREIGN KEY(onMetStepId) REFERENCES steps(id)
);

-- Error Watch Functions Table
CREATE TABLE error_watch_fns (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    errorWatchId BIGINT NOT NULL,
    functionName VARCHAR(255) NOT NULL,
    position BIGINT NOT NULL,
    FOREIGN KEY(errorWatchId) REFERENCES error_watches(id) ON DELETE CASCADE,
    UNIQUE (errorWatchId, position),
    UNIQUE (errorWatchId, functionName, position)
);

CREATE TABLE inquisitions (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    stepId BIGINT NOT NULL,
    class VARCHAR(255) NOT NULL,
    dataPointId BIGINT NOT NULL,
    onMetStepId BIGINT NOT NULL,
    executionOrder BIGINT NOT NULL,
    FOREIGN KEY(stepId) REFERENCES steps(id),
    FOREIGN KEY(dataPointId) REFERENCES data_points(id),
    FOREIGN KEY(onMetStepId) REFERENCES steps(id)
);

-- Revisions Table
CREATE TABLE revisions (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    dataPointId BIGINT NOT NULL,
    stepId BIGINT NOT NULL,
    transformationId BIGINT,
    FOREIGN KEY(dataPointId) REFERENCES data_points(id),
    FOREIGN KEY(stepId) REFERENCES steps(id),
    FOREIGN KEY(transformationId) REFERENCES transformations(id)
);

CREATE TABLE provisions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    step_id BIGINT NOT NULL,
    tool_param_id BIGINT NOT NULL,
    FOREIGN KEY (step_id) REFERENCES steps(id) ON DELETE CASCADE,
    FOREIGN KEY (tool_param_id) REFERENCES tool_params(id) ON DELETE CASCADE
);

CREATE TABLE provision_data_points (
    provisionId BIGINT NOT NULL,
    dataPointId BIGINT NOT NULL,
    PRIMARY KEY (provisionId, dataPointId),
    FOREIGN KEY (provisionId) REFERENCES provisions(id),
    FOREIGN KEY (dataPointId) REFERENCES data_points (id)
);

-- Workflow Junctions Table
CREATE TABLE workflow_junctions (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sourceStepId BIGINT NOT NULL,
    targetWorkflowId BIGINT NOT NULL,
    triggerInquisitionId BIGINT,
    FOREIGN KEY(sourceStepId) REFERENCES steps(id),
    FOREIGN KEY(targetWorkflowId) REFERENCES workflows(id),
    FOREIGN KEY(triggerInquisitionId) REFERENCES inquisitions(id)
);

-- Prereq Injections Table
CREATE TABLE prereq_injections (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    junctionId BIGINT NOT NULL,
    sourceDataPointId BIGINT NOT NULL, 
    injectedDataPointId BIGINT NOT NULL,
    name VARCHAR(255),
    description TEXT,
    FOREIGN KEY(junctionId) REFERENCES workflow_junctions(id),
    FOREIGN KEY(sourceDataPointId) REFERENCES data_points(id), 
    FOREIGN KEY(injectedDataPointId) REFERENCES data_points(id)
);

-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
-- ----------------------------------------------------------------------------------
--                              USERS & CONTENT                                  --
-- ----------------------------------------------------------------------------------
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 


-- Users Table
CREATE TABLE users (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  resetToken VARCHAR(255),
  oauthProvider VARCHAR(50),
  oauthId VARCHAR(255),
  subscriptionStatus ENUM('registered', 'active', 'cancelled', 'on hold') NOT NULL DEFAULT 'registered',
  apiKey VARCHAR(255),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE payment_methods (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    stripePaymentMethodId VARCHAR(255),
    userId BIGINT NOT NULL,
    cardBrand ENUM('American Express', 'Diners Club', 'Discover', 'Eftpos Australia', 'Girocard', 'JCB', 'MasterCard', 'UnionPay', 'Visa', 'Unknown') DEFAULT NULL,
    lastFour VARCHAR(4),
    isDefault BOOLEAN DEFAULT FALSE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY(userId) REFERENCES users(id)
);


CREATE TABLE tags (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    FOREIGN KEY(userId) REFERENCES users(id)
);

CREATE TABLE workflow_tags(
    workflowId BIGINT NOT NULL,
    tagId BIGINT NOT NULL,
    FOREIGN KEY(workflowId) REFERENCES workflows(id),
    FOREIGN KEY(tagId) REFERENCES tags(id)
);

CREATE TABLE step_tags(
    stepId BIGINT NOT NULL,
    tagId BIGINT NOT NULL,
    FOREIGN KEY(stepId) REFERENCES steps(id),
    FOREIGN KEY(tagId) REFERENCES tags(id)
);

CREATE TABLE tag_relations(
    parentTagId BIGINT NOT NULL,
    childTagId BIGINT NOT NULL,
    PRIMARY KEY(parentTagId, childTagId),
    FOREIGN KEY(parentTagId) REFERENCES tags(id),
    FOREIGN KEY(childTagId) REFERENCES tags(id)
);


-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
-- ----------------------------------------------------------------------------------
--                    EXECUTIONS AND INSIGHTS                                     --
-- ----------------------------------------------------------------------------------
-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

CREATE TABLE workflow_executions (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    workflowId BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    startedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completedAt TIMESTAMP DEFAULT NULL,
    status ENUM('running', 'completed', 'failed', 'cancelled') NOT NULL DEFAULT 'running',
    FOREIGN KEY(workflowId) REFERENCES workflows(id),
    FOREIGN KEY(userId) REFERENCES users(id)
);

CREATE TABLE step_executions (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    workflowExecutionId BIGINT NOT NULL,
    stepId BIGINT NOT NULL,
    startedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completedAt TIMESTAMP NULL,
    status ENUM('running', 'completed', 'failed', 'skipped') NOT NULL DEFAULT 'running',
    FOREIGN KEY(workflowExecutionId) REFERENCES workflow_executions(id),
    FOREIGN KEY(stepId) REFERENCES steps(id)
);

CREATE TABLE execution_logs (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    stepExecutionId BIGINT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    logLevel ENUM('DEBUG', 'INFO', 'WARNING', 'ERROR', 'CRITICAL') NOT NULL,
    message TEXT NOT NULL,
    FOREIGN KEY(stepExecutionId) REFERENCES step_executions(id)
);

-- 
-- Benchmarking Data
-- 
CREATE TABLE benchmarking_data (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    workflowExecutionId BIGINT NOT NULL,
    totalExecutionTime DECIMAL(10, 3) NOT NULL, -- in seconds
    totalSteps BIGINT NOT NULL,
    aiDeterminedSteps BIGINT NOT NULL,
    predeterminedSteps BIGINT NOT NULL,
    FOREIGN KEY(workflowExecutionId) REFERENCES workflow_executions(id)
);

CREATE TABLE step_benchmarking_data (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    stepExecutionId BIGINT NOT NULL,
    executionTime DECIMAL(10, 3) NOT NULL, -- in seconds
    FOREIGN KEY(stepExecutionId) REFERENCES step_executions(id)
);

-- 
-- Testing Capabilities
-- 
CREATE TABLE test_cases (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    workflowId BIGINT,
    stepId BIGINT,
    FOREIGN KEY(userId) REFERENCES users(id),
    FOREIGN KEY(workflowId) REFERENCES workflows(id),
    FOREIGN KEY(stepId) REFERENCES steps(id)
);

CREATE TABLE test_inputs (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    testCaseId BIGINT NOT NULL,
    dataPointName VARCHAR(255) NOT NULL,
    value TEXT,
    generationMethod ENUM('manual', 'aiGenerated') NOT NULL,
    FOREIGN KEY(testCaseId) REFERENCES test_cases(id)
);

CREATE TABLE test_expected_outcomes (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    testCaseId BIGINT NOT NULL,
    dataPointName VARCHAR(255) NOT NULL,
    expectedValue TEXT,
    comparisonOperator ENUM('equals', 'notEquals', 'contains', 'greaterThan', 'lessThan') NOT NULL,
    FOREIGN KEY(testCaseId) REFERENCES test_cases(id)
);

CREATE TABLE test_results (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    testCaseId BIGINT NOT NULL,
    workflowExecutionId BIGINT NOT NULL,
    passed BOOLEAN NOT NULL,
    executedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(testCaseId) REFERENCES test_cases(id),
    FOREIGN KEY(workflowExecutionId) REFERENCES workflow_executions(id)
);

COMMIT;