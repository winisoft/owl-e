package com.wini.owle.mapper.workflows

import com.wini.owle.dto.PhaseDto
import com.wini.owle.entity.Phases
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.workflows.PhaseDao
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class PhasesMapper(
    private val workflowMapper: WorkflowsMapper
) : DbMapper<PhaseDto, PhaseDao>() {

    override val applyProps: PhaseDao.(PhaseDto) -> Unit = { dto ->
        workflow = workflowMapper.run { dto.workflow.toEntity() }
        parentPhase = dto.parentPhase?.toEntity()
        name = dto.name
        description = dto.description
        displayOrder = dto.displayOrder
    }

    override fun PhaseDto.toEntity(): PhaseDao {
        return if (id == 0L) {
            PhaseDao.new { applyProps(this@toEntity) }
        } else {
            PhaseDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("Phase with id=$id not found")
        }
    }

    override fun PhaseDao.toDto(): PhaseDto {
        return PhaseDto(
            id = id.value,
            workflow = workflowMapper.run { workflow.toDto() },
            parentPhase = parentPhase?.toDto(),
            name = name,
            description = description,
            displayOrder = displayOrder
        )
    }
}