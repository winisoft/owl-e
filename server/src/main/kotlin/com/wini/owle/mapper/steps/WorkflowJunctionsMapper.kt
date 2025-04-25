package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.WorkflowJunction
import com.wini.owle.entity.steps.WorkflowJunctionEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.StepsMapper // Assumed updated
import com.wini.owle.mapper.workflows.WorkflowsMapper // Assumed updated
// Assuming InquisitionsMapper exists in this package and is updated


class WorkflowJunctionsMapper(
    private val daoFactory: DaoFactory<WorkflowJunctionEntity, Long>,
    private val stepsMapper: StepsMapper,
    private val workflowsMapper: WorkflowsMapper,
    private val inquisitionsMapper: InquisitionsMapper
): DbMapper<WorkflowJunction, WorkflowJunctionEntity>() {

    override fun toEntity(dto: WorkflowJunction): WorkflowJunctionEntity {
        val applyProps: WorkflowJunctionEntity.() -> Unit = {
            sourceStep = stepsMapper.toEntity(dto.sourceStep)
            targetWorkflow = workflowsMapper.toEntity(dto.targetWorkflow)
            triggerInquisitionEntity = dto.triggerInquisition?.let { inquisitionsMapper.toEntity(it) }
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            WorkflowJunctionEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("WorkflowJunction with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: WorkflowJunctionEntity): WorkflowJunction {
        return WorkflowJunction(
            id = entity.id.value,
            sourceStep = stepsMapper.toDto(entity.sourceStep),
            targetWorkflow = workflowsMapper.toDto(entity.targetWorkflow),
            triggerInquisition = entity.triggerInquisitionEntity?.let { inquisitionsMapper.toDto(it) }
        )
    }
}