package com.wini.owle.mapper.workflows

import com.wini.owle.dto.StepDto
import com.wini.owle.entity.Steps
import com.wini.owle.entity.Tools
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class StepsMapper(
    private val workflowMapper: WorkflowsMapper,
    private val toolMapper: ToolsMapper,
    private val phasesMapper: PhasesMapper
) : DbMapper<StepDto, StepDao>() {

    override val applyProps: StepDao.(StepDto) -> Unit = { dto ->
        workflow = workflowMapper.run { dto.workflow.toEntity() }
        name = dto.name
        description = dto.description
        tool = toolMapper.run { dto.tool.toEntity() }
        defaultNext = null //TODO
        isWorkflowHead = dto.isWorkflowHead
        parentPhase = phasesMapper.run { dto.parentPhase?.toEntity() }
    }

    override fun StepDto.toEntity(): StepDao {
        return if (id == 0L) {
            StepDao.new { applyProps(this@toEntity) }
        } else {
            StepDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("Step with id=$id not found")
        }
    }

    override fun StepDao.toDto(): StepDto {
        return StepDto(
            id = id.value,
            name = name,
            description = description,
            workflow = workflowMapper.run { workflow.toDto() },
            isWorkflowHead = isWorkflowHead,
            tool = toolMapper.run { tool.toDto() },
            nextStep = null, //TODO
            displayOrder = displayOrder,
            parentPhase = phasesMapper.run { parentPhase?.toDto() }
        )
    }
}