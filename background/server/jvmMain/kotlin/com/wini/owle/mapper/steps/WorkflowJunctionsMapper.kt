package com.wini.owle.mapper.steps

import com.wini.owle.dto.WorkflowJunctionDto
import com.wini.owle.entity.Inquisitions
import com.wini.owle.entity.Steps
import com.wini.owle.entity.WorkflowJunctions
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.steps.WorkflowJunctionDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.mapper.workflows.WorkflowsMapper
import org.jetbrains.exposed.dao.id.EntityID


class WorkflowJunctionsMapper(
    private val stepsMapper: StepsMapper,
    private val workflowsMapper: WorkflowsMapper,
    private val inquisitionsMapper: InquisitionsMapper
) : DbMapper<WorkflowJunctionDto, WorkflowJunctionDao>() {

    override val applyProps: WorkflowJunctionDao.(WorkflowJunctionDto) -> Unit = { dto ->
        sourceStep = stepsMapper.run { dto.sourceStep.toEntity() }
        targetWorkflow = workflowsMapper.run { dto.targetWorkflow.toEntity() }
        triggerInquisition = inquisitionsMapper.run { dto.triggerInquisition?.toEntity() }
    }

    override fun WorkflowJunctionDto.toEntity(): WorkflowJunctionDao {
        return if (id == 0L) {
            WorkflowJunctionDao.new { applyProps(this@toEntity) }
        } else {
            WorkflowJunctionDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("WorkflowJunction with id=$id not found")
        }
    }

    override fun WorkflowJunctionDao.toDto(): WorkflowJunctionDto {
        return WorkflowJunctionDto(
            id = id.value,
            sourceStep = stepsMapper.run { sourceStep.toDto() },
            targetWorkflow = workflowsMapper.run { targetWorkflow.toDto() },
            triggerInquisition = inquisitionsMapper.run { triggerInquisition?.toDto() }
        )
    }
}
