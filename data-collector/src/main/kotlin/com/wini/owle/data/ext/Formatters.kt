package com.wini.owle.data.ext

import com.wini.owle.dto.environs.*
import com.wini.owle.dto.steps.*
import com.wini.owle.dto.runs.step.*
import com.wini.owle.dto.runs.workflow.*
import com.wini.owle.dto.runs.test.*
import com.wini.owle.dto.typing.*
import com.wini.owle.dto.transforms.*
import com.wini.owle.dto.workflows.*
import com.wini.owle.dto.user.*
import com.wini.owle.dto.user.tags.*
import com.wini.owle.data.util.Sql


fun Distribution.format() : String {
    return ""
}
fun PyPackage.format(): String {
    return ""
}
fun PyPackageRepo.format(): String {
    return ""
}
fun PythonVersion.format(): String = Sql[
    id,
    major,
    minor,
    micro,
    phase,
    phaseNumber,
    isPostRelease,
    postNumber,
    isDevRelease,
    devNumber,
    hasOverflow
]

fun SpecifierElement.format(): String = Sql[
    id, operator, pythonVersion.id, wildcardSegment, position
]

fun ToolPyPackDependency.format(): String = Sql[
    toolId, packageId, versionSpecId
]

fun VersionOverflow.format(): String = Sql[
    id, pythonVersion.id, componentType, stringValue,
]

fun VersionSpecifier.format(): String = Sql [
    id, rawSpecifier, environmentMarker
    // cannot insert versionSpecifiers as it is a list and requires the association table inserts to be reflected
]

fun VersionSpecSpecElement.format(): String  = Sql[
    versionSpecifierId, specifierElementId
]
fun StepRunLogLine.format(): String  = Sql[
    id, stepRun.id, timestamp, logLevel, message
]
fun ErrorWatch.format(): String  = Sql[
    id, step.id, errorType, triggerStep
]
fun TestExpectation.format(): String  = Sql[
    id, testCase.id, dataPointName, expectedValue, comparisonOperator
]
fun TestCase.format(): String  = Sql[
    id, user.id, name, description, workflow?.id, step?.id,
    //inputs, expectedOutcomes, results --> all link-table-associated lists
]
fun BenchmarkReport.format(): String  = Sql[
    id, workflowExecution.id, totalExecutionTime, aiDeterminedSteps, predeterminedSteps
]
fun WorkflowTestResult.format(): String  = Sql[
    id, testCase.id, workflowExecution.id, passed, executedAt
]
fun TestInput.format(): String  = Sql[
    id, testCase.id, dataPointName, value, generationMethod
]
fun WorkflowExecution.format(): String  = Sql[
    id, workflow.id, user.id, startedAt, completedAt, status
    // stepRuns -> link-table-associated list
]
fun StepRun.format(): String  = Sql[
    id, workflowExecution.id, step.id, startedAt, completedAt, status
]
fun DataPointSave.format(): String  = Sql[
    id, dataPoint.id, step.id
]
fun Inquisition.format(): String  = Sql[
    id, step.id, className, dataPoint.id, onMetWorkflowStep.id, executionOrder
]
fun ErrorWatchFn.format(): String  = Sql[
    id, errorWatch.id, functionName, position
]
fun InitialDataPointInjector.format(): String  = Sql[
    id, junction.id, sourceDataPoint.id, injectedDataPoint.id, name, description
]
fun Provision.format(): String  = Sql[
    id, step.id, toolParam.id
    // dataPoints -> link-table-associated list
]
fun ProvisionDataPoint.format(): String  = Sql[
    provisionId, dataPointId
]
fun ToolParam.format(): String  = Sql[
    id, name, tool.id, typeDeclaration.id, hasDefault,
]
fun WorkflowJunction.format(): String  = Sql[
    id, sourceStep.id, targetWorkflow.id, triggerInquisition?.id
]
fun AppliedTransform.format(): String  = Sql[
    id, transformation.id, occurrence, resultType.id, dataPoint?.id
]
fun AppliedTransformArg.format(): String  = Sql[
    id, appliedTransform.id, transformationArg.id, value
]
fun Transform.format(): String  = Sql[
    id, name, description, examples, returnsType?.id
]
fun TransformArg.format(): String  = Sql[
    id, transformation.id, typeDeclaration.id, name, description
]
fun TransformTargetType.format(): String  = Sql[
    transformationId, typeDeclarationId
]
fun TypeBound.format(): String  = Sql[
    id, boundTypeDeclaration.id, typeVariableDeclaration.id
]
fun TypeDeclaration.format(): String  = Sql[
    id, typeDefinition.id, alias
]
fun TypeDefinition.format(): String  = Sql[
    id, name, namespace, versionSpec.id
]
fun TypeParameter.format(): String  = Sql[
    id, typeDeclaration.id, position, parameterTypeDeclaration.id
]
fun TypeSchemaRef.format(): String  = Sql[
    id, parentRef?.id, name, typeDeclaration.id,
]
fun TypeUnionMember.format(): String  = Sql[
    unionTypeDeclarationId, memberTypeDeclarationId
]
fun TypeVariance.format(): String  = Sql[
    id, typeVariableDeclaration.id, varianceCardinality
]
fun StepTag.format(): String  = Sql[
    stepId, tagId
]
fun Tag.format(): String  = Sql[
    id, name, description
]
fun TagRelation.format(): String  = Sql[
    parentTagId, childTagId
]
fun WorkflowTag.format(): String  = Sql[
    workflowId, tagId
]
fun PaymentMethod.format(): String  = Sql[
    id, stripePaymentMethodId, user, cardBrand, lastFour, isDefault, createdAt, updatedAt
]
fun User.format(): String  = Sql[
    id, email, password, resetToken, oauthProvider, oauthId, subscriptionStatus, apiKey, createdAt, updatedAt
    // paymentMethods
]
fun DataPoint.format(): String  = Sql[
    id, workflow.id, name, description, presetValue, storeMethod, typeDeclaration.id, createdAt, updatedAt
]
fun Phase.format(): String  = Sql[
    id, workflow.id, parentPhase?.id, name, description, displayOrder
]
fun Step.format(): String  = Sql[
    id, name, description, parentPhase?.id, displayOrder, workflow, isWorkflowHead, tool.id, nextStep?.id
]
fun Tool.format(): String  = Sql[
    id, name, description, body, returnTypeDeclaration?.id
]
fun Workflow.format(): String  = Sql[
    id, name, description, pythonVersion, parentFolder, createdAt, updatedAt
]
fun WorkflowFolder.format(): String  = Sql[
    id, parentFolder, name, description, createdAt, updatedAt
]
fun WorkflowMetadata.format(): String  = Sql[
    id, name, version,
    //contributors,
    copyrightInformation, contactInformation, thirdPartyLicenses, discretionAdvisedContent, commercialUsage,
    creditNotation, allowRedistribution, modification, distributionVersion, distributionAcceptedAt, distributionUpdatedAt,
    //excludedSteps
]