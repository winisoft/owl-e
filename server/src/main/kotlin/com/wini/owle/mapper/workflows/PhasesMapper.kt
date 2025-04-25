package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.Phase
import com.wini.owle.entity.workflows.PhaseEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class PhasesMapper(
    private val daoFactory: DaoFactory<PhaseEntity, Long>,
    private val workflowMapper: WorkflowsMapper
) : DbMapper<Phase, PhaseEntity>() {

    override fun toEntity(dto: Phase): PhaseEntity {
        val applyProps: PhaseEntity.() -> Unit = {
            workflow = workflowMapper.toEntity(dto.workflow)
            parentPhase = dto.parentPhase?.let { toEntity(it) }
            name = dto.name
            description = dto.description
            displayOrder = dto.displayOrder
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else
            PhaseEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Phase with id=${dto.id} not found")
    }

    override fun toDto(entity: PhaseEntity): Phase {
        return Phase(
            id = entity.id.value,
            workflow = workflowMapper.toDto(entity.workflow),
            parentPhase = entity.parentPhase?.let { toDto(it) },
            name = entity.name,
            description = entity.description,
            displayOrder = entity.displayOrder
        )
    }
}