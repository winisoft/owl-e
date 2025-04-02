package com.wini.owle.mapper.steps

import com.wini.owle.dto.WorkflowJunctionDto
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.steps.WorkflowJunctionDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.StepsMapper // Assumed updated
import com.wini.owle.mapper.workflows.WorkflowsMapper // Assumed updated
// Assuming InquisitionsMapper exists in this package and is updated
import com.wini.owle.mapper.steps.InquisitionsMapper


class WorkflowJunctionsMapper(
    private val daoFactory: DaoFactory<WorkflowJunctionDao, Long>,
    private val stepsMapper: StepsMapper,
    private val workflowsMapper: WorkflowsMapper,
    private val inquisitionsMapper: InquisitionsMapper // Assumed updated
): DbMapper<WorkflowJunctionDto, WorkflowJunctionDao>() {

    override fun toEntity(dto: WorkflowJunctionDto): WorkflowJunctionDao {
        val applyProps: WorkflowJunctionDao.() -> Unit = {
            sourceStep = stepsMapper.toEntity(dto.sourceStep)
            targetWorkflow = workflowsMapper.toEntity(dto.targetWorkflow)
            triggerInquisition = dto.triggerInquisition?.let { inquisitionsMapper.toEntity(it) }
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            WorkflowJunctionDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("WorkflowJunction with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: WorkflowJunctionDao): WorkflowJunctionDto {
        return WorkflowJunctionDto(
            id = entity.id.value,
            sourceStep = stepsMapper.toDto(entity.sourceStep),
            targetWorkflow = workflowsMapper.toDto(entity.targetWorkflow),
            triggerInquisition = entity.triggerInquisition?.let { inquisitionsMapper.toDto(it) }
        )
    }
}