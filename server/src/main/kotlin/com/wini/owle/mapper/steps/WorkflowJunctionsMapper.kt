package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.WorkflowJunction
import com.wini.owle.entity.steps.WorkflowJunctionEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.StepsMapper // Assumed updated
import com.wini.owle.mapper.workflows.WorkflowMapper // Assumed updated
// Assuming InquisitionsMapper exists in this package and is updated


class WorkflowJunctionsMapper(
    private val daoDelegate: DaoDelegate<ULong, WorkflowJunction, WorkflowJunctionEntity>,
    private val stepsMapper: StepsMapper,
    private val workflowMapper: WorkflowMapper,
    private val inquisitionsMapper: InquisitionsMapper
): DbMapper<ULong, WorkflowJunction, WorkflowJunctionEntity> {

    override fun toEntity(dto: WorkflowJunction): WorkflowJunctionEntity {
        return daoDelegate.findOrCreate(dto) {
            sourceStep = stepsMapper.toEntity(dto.sourceStep)
            targetWorkflow = workflowMapper.toEntity(dto.targetWorkflow)
            triggerInquisitionEntity = dto.triggerInquisition?.let { inquisitionsMapper.toEntity(it) }
        }
    }

    override fun toDto(entity: WorkflowJunctionEntity): WorkflowJunction {
        return WorkflowJunction(
            id = entity.id.value,
            sourceStep = stepsMapper.toDto(entity.sourceStep),
            targetWorkflow = workflowMapper.toDto(entity.targetWorkflow),
        )
    }
}