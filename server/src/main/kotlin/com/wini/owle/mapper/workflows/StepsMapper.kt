package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.Step
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class StepsMapper(
    private val daoFactory: DaoFactory<StepEntity, Long>,
    private val workflowMapper: WorkflowsMapper,
    private val toolMapper: ToolsMapper,
    private val phasesMapper: PhasesMapper
) : DbMapper<Step, StepEntity>() {

    override fun toEntity(dto: Step): StepEntity {

        val applyProps: StepEntity.() -> Unit = {
            workflow = workflowMapper.toEntity(dto.workflow)
            name = dto.name
            description = dto.description
            tool = toolMapper.toEntity(dto.tool)
            defaultNext = dto.nextStep?.let { toEntity(it) }
            isWorkflowHead = dto.isWorkflowHead
            parentPhase = dto.parentPhase?.let { phasesMapper.toEntity(it) }
            displayOrder = dto.displayOrder
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            StepEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Step with id=${dto.id} not found")
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
            nextStep = entity.defaultNext?.let { toDto(it) },
            displayOrder = entity.displayOrder,
            parentPhase = entity.parentPhase?.let { phasesMapper.toDto(it) }
        )
    }
}