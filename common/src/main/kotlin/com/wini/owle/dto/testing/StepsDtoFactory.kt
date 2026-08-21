package com.wini.owle.dto.testing

import com.wini.owle.dto.steps.DataPointInjection
import com.wini.owle.dto.steps.DataPointRead
import com.wini.owle.dto.steps.DataPointWrite
import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.dto.steps.Inquisition
import com.wini.owle.dto.steps.ToolParam
import com.wini.owle.dto.steps.WorkflowJunction
import com.wini.owle.dto.transforms.TransformOccurrence
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Tool
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.dto.typing.CallableParameter

/**
 * Canned [steps] DTO instances for tests.
 *
 * One factory per DTO package, composed by [DtoTestInstanceFactory]. Cross-package
 * instances are obtained from the sibling factory that owns them, so each file stays
 * responsible for exactly the types its own package declares.
 */
object StepsDtoFactory {

    fun toolParamDto(
        id: ULong = 1UL,
        name: String = "Tool Param",
        typeDeclaration: TypeNode = TypingDtoFactory.typeNode(),
        tool: Tool = WorkflowsDtoFactory.toolDto(),
        hasDefault: Boolean = true
    ) : ToolParam {
        return ToolParam(
            id = id,
            name = name,
            typeDeclaration = typeDeclaration,
            tool = tool
        )
    }

    fun errorWatchDto(
        id: ULong = 11UL,
        step: Step = WorkflowsDtoFactory.stepDto(),
        errorType: String = "RuntimeException",
        triggerStep: Step = WorkflowsDtoFactory.stepDto()
    ) : ErrorWatch {
        return ErrorWatch(
            id = id,
            step = step,
            errorType = errorType,
            triggerStep = triggerStep
        )
    }

    fun errorWatchFnDto(
        id: ULong = 1UL,
        errorWatch: ErrorWatch = errorWatchDto(),
        functionNode: TypeNode = TypingDtoFactory.typeNode(),
        position: ULong = 1uL,
    ) : ErrorWatchFn {
        return ErrorWatchFn(
            id = id,
            errorWatch = errorWatch,
            functionNode = functionNode,
            position = position
        )
    }

    fun inquisitionDto(
        id: ULong = 1UL,
        step: Step = WorkflowsDtoFactory.stepDto(),
        condition: TypeNode = TypingDtoFactory.typeNode(),
        dataPoint: DataPoint = WorkflowsDtoFactory.dataPointDto(),
        executionOrder: ULong = 1UL
    ) : Inquisition {
        return Inquisition(
            id = id,
            step = step,
            executionOrder = executionOrder
        )
    }

    fun dataPointInjectionDto(
        id: ULong = 1UL,
        junction: WorkflowJunction = workflowJunctionDto(),
        sourceDataPoint: DataPoint = WorkflowsDtoFactory.dataPointDto(),
        injectedDataPoint: DataPoint = WorkflowsDtoFactory.dataPointDto(),
        name: String = "Name",
        description: String? = "Description"
    ) : DataPointInjection {
        return DataPointInjection(
            id = id,
            junction = junction,
            sourceDataPoint = sourceDataPoint,
            injectedDataPoint = injectedDataPoint,
            name = name,
            description = description
        )
    }

    fun workflowJunctionDto(
        id: ULong = 1UL,
        sourceStep: Step = WorkflowsDtoFactory.stepDto(),
        targetWorkflow: Workflow = WorkflowsDtoFactory.workflowDto(),
        triggerInquisition: Inquisition = inquisitionDto()
    ) : WorkflowJunction {
        return WorkflowJunction(
            id = id,
            sourceStep = sourceStep,
            targetWorkflow = targetWorkflow,
        )
    }

    /**
     * A read owned by a step. Pass [inquisition] instead (and `step = null`) for the other
     * owner; the database CHECK allows exactly one.
     */
    fun dataPointReadDto(
        id: ULong = 1UL,
        step: Step? = WorkflowsDtoFactory.stepDto(),
        inquisition: Inquisition? = null,
        callableParameter: CallableParameter = TypingDtoFactory.callableParameterDto(),
        dataPoint: DataPoint = WorkflowsDtoFactory.dataPointDto(),
        transformOccurrence: TransformOccurrence? = null
    ) : DataPointRead {
        return DataPointRead(
            id = id,
            step = step,
            inquisition = inquisition,
            callableParameter = callableParameter,
            dataPoint = dataPoint,
            transformOccurrence = transformOccurrence
        )
    }

    fun dataPointWriteDto(
        id: ULong = 1UL,
        dataPoint: DataPoint = WorkflowsDtoFactory.dataPointDto(),
        step: Step = WorkflowsDtoFactory.stepDto()
    ) : DataPointWrite {
        return DataPointWrite(
            id = id,
            dataPoint = dataPoint,
            step = step,
            transformOccurrence = null
        )
    }
}
