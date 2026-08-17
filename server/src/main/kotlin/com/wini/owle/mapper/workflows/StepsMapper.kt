package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.Step
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper


class StepsMapper(
    private val daoDelegate: DaoDelegate<ULong, Step, StepEntity>,
    private val workflowMapper: WorkflowMapper,
    private val toolMapper: ToolMapper,
    private val phaseMapper: PhaseMapper
): DbMapper<ULong, Step, StepEntity> {

    override fun toEntity(dto: Step): StepEntity {
        return daoDelegate.findOrCreate(dto) {
            workflow = workflowMapper.toEntity(dto.workflow)
            name = dto.name
            description = dto.description
            tool = toolMapper.toEntity(dto.tool)
            isWorkflowHead = dto.isWorkflowHead
            parentPhase = dto.parentGrouper?.let { phaseMapper.toEntity(it) }
            displayOrder = dto.displayOrder
        }
    }

    override fun toDto(entity: StepEntity): Step {
        return Step(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            workflow = workflowMapper.toDto(entity.workflow),
            isWorkflowHead = entity.isWorkflowHead,
            tool = toolMapper.toDto(entity.tool),
            displayOrder = entity.displayOrder,
            parentGrouper = entity.parentPhase?.let { phaseMapper.toDto(it) },
        )
    }
}