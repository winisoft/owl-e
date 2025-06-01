package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.Phase
import com.wini.owle.entity.workflows.PhaseEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper


class PhaseMapper(
    private val daoDelegate: DaoDelegate<ULong, Phase, PhaseEntity>,
    private val workflowMapper: WorkflowMapper
): DbMapper<ULong, Phase, PhaseEntity> {

    override fun toEntity(dto: Phase): PhaseEntity {
        return daoDelegate.findOrCreate(dto) {
            workflow = workflowMapper.toEntity(dto.workflow)
            name = dto.name
            description = dto.description
            displayOrder = dto.displayOrder
        }
    }

    override fun toDto(entity: PhaseEntity): Phase {
        return Phase(
            id = entity.id.value,
            workflow = workflowMapper.toDto(entity.workflow),
            name = entity.name,
            description = entity.description,
            displayOrder = entity.displayOrder
        )
    }
}