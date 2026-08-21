package com.wini.owle.dto.testing

import com.wini.owle.dto.environs.version.PythonVersion
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Grouper
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Tool
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.dto.workflows.enums.StoreMethod

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
