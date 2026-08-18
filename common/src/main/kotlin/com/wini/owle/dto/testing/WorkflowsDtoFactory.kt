package com.wini.owle.dto.testing

import com.wini.owle.dto.environs.enums.EnvMarkerExpressionMode
import com.wini.owle.dto.environs.enums.EnvMarkerVariable
import com.wini.owle.dto.environs.marker.EnvMarkerComparison
import com.wini.owle.dto.environs.marker.EnvMarkerExpression
import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.dto.environs.pkg.DistributionPackage
import com.wini.owle.dto.environs.pkg.PackageDistRequirement
import com.wini.owle.dto.environs.version.PythonVersion
import com.wini.owle.dto.environs.version.PythonVersionSpecifier
import com.wini.owle.dto.environs.version.Version
import com.wini.owle.dto.environs.version.VersionOverflow
import com.wini.owle.dto.environs.version.VersionSpecifier
import com.wini.owle.dto.environs.version.enums.OverflowViolation
import com.wini.owle.dto.environs.version.enums.PrereleasePhase
import com.wini.owle.dto.environs.version.enums.VersionOperator
import com.wini.owle.dto.environs.version.enums.WildcardSegment
import com.wini.owle.dto.runs.enums.ComparisonOperator
import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.runs.enums.GenerationMethod
import com.wini.owle.dto.runs.enums.LogLevel
import com.wini.owle.dto.runs.step.StepTestRun
import com.wini.owle.dto.runs.step.ExecutionNotification
import com.wini.owle.dto.runs.test.BenchmarkReport
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.dto.runs.test.TestOutcomeExpectation
import com.wini.owle.dto.runs.test.TestInput
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.dto.runs.workflow.TestOutcomeActual
import com.wini.owle.dto.steps.DataPointInjection
import com.wini.owle.dto.steps.DataPointRead
import com.wini.owle.dto.steps.DataPointWrite
import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.dto.steps.Inquisition
import com.wini.owle.dto.steps.ToolParam
import com.wini.owle.dto.steps.WorkflowJunction
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.tags.TagRelation
import com.wini.owle.dto.tags.enums.TagSource
import com.wini.owle.dto.transforms.Transform
import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.dto.transforms.TransformOccurrence
import com.wini.owle.dto.transforms.TransformOccurrenceArg
import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.dto.typing.TypeParameter
import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.dto.typing.TypeVarBound
import com.wini.owle.dto.typing.TypeVarVariance
import com.wini.owle.dto.typing.enums.VarianceCardinality
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.dto.user.User
import com.wini.owle.dto.user.enums.CardBrand
import com.wini.owle.dto.user.enums.UserStatus
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Grouper
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Tool
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.dto.workflows.enums.StoreMethod
import com.wini.owle.dto.runs.test.TestExpectedOutcome
import com.wini.owle.dto.runs.workflow.TestResult

/**
 * Canned [workflows] DTO instances for tests.
 *
 * One factory per DTO package, composed by [DtoTestInstanceFactory]. Cross-package
 * instances are obtained from the sibling factory that owns them, so each file stays
 * responsible for exactly the types its own package declares.
 */
object WorkflowsDtoFactory {

    fun stepDto(
        id: ULong = 3UL,
        name: String = "Example Step",
        description: String? = "A step in a workflow",
        workflow: Workflow = workflowDto(),
        isWorkflowHead: Boolean = true,
        tool: Tool = toolDto(),
        parentGrouper: Grouper? = null,
        defaultNextStep: Step? = null,
        posX: Double = 0.0,
        posY: Double = 0.0,
        color: String = "#4488CC",
        createdAt: String = "2024-12-21T15:00:00Z",
        updatedAt: String = "2024-12-21T15:00:00Z",
        nextStep: Step? = null
    ): Step {
        return Step(
            id = id,
            name = name,
            description = description,
            workflow = workflow,
            isWorkflowHead = isWorkflowHead,
            tool = tool,
            parentGrouper = parentGrouper,
            defaultNextStep = defaultNextStep,
            posX = posX,
            posY = posY,
            color = color,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun workflowDto(
        id: ULong = 1UL,
        name: String = "Example Workflow",
        description: String? = "A sample workflow description",
        pythonVersion: PythonVersion = EnvironsDtoFactory.pythonVersion(),
        parentFolder: WorkflowFolder? = null,
        createdAt: String = "2024-12-20T15:00:00",
        updatedAt: String = "2024-12-21T15:00:00"
    ): Workflow {
        return Workflow(
            id = id,
            name = name,
            description = description,
            pythonVersion = pythonVersion,
            parentFolder = parentFolder,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun toolDto(
        id: ULong = 1UL,
        name: String = "Tool Name",
        description: String? = "Tool Description",
        body: String = "a sample of code",
        returnTypeDeclaration: TypeNode? = TypingDtoFactory.typeNode(),
        steps: List<Step> = emptyList()
    ): Tool {
        return Tool(
            id = id,
            name = name,
            description = description,
            body = body,
            returnTypeNode = returnTypeDeclaration
        )
    }

    fun dataPointDto(
        id: ULong = 1UL,
        name: String = "Data Point Name",
        description: String? = "Data Point Description",
        presetValue: String? = "Some stuff",
        storeMethod: StoreMethod = StoreMethod.BLOB,
        typeDeclaration: TypeNode = TypingDtoFactory.typeNode(),
        workflow: Workflow = workflowDto()
    ): DataPoint {
        return DataPoint(
            id = id,
            name = name,
            description = description,
            presetValue = presetValue,
            storeMethod = storeMethod,
            typeNode = typeDeclaration,
            workflow = workflow,
            createdAt = "2024-12-21T15:00:00",
            updatedAt = "2024-12-21T15:00:00"
        )
    }

    fun grouperDto(
        id: ULong = 1UL,
        workflow: Workflow = workflowDto(),
        parentGrouper: Grouper? = null,
        defaultNextStep: Step? = null,
        name: String = "Phase Name",
        description: String? = "Phase Description",
        posX: Double = 0.0,
        posY: Double = 0.0
    ) : Grouper {
        return Grouper(
            id = id,
            workflow = workflow,
            parentGrouper = parentGrouper,
            name = name,
            description = description,
            posX = posX,
            posY = posY
        )
    }

    fun workflowFolderDto(
        id: ULong = 32UL,
        parentFolder: WorkflowFolder? = null,
        name: String = "ExampleFolder",
        description: String? = "Folder description",
        img: String? = null,
        createdAt: String = "2024-12-21T10:00:00",
        updatedAt: String = "2024-12-21T10:00:00"
    ): WorkflowFolder {
        return WorkflowFolder(
            id = id,
            parentFolder = parentFolder,
            name = name,
            description = description,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
