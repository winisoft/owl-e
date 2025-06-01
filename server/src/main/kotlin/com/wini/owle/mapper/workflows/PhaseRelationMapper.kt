package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.Phase
import com.wini.owle.dto.workflows.PhaseRelation
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.workflows.PhaseEntity
import com.wini.owle.entity.workflows.PhaseRelationEntity
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class PhaseRelationMapper(
    val daoDelegate: DaoDelegate<CompositeID, PhaseRelation, PhaseRelationEntity>,
    val phaseMapper: DbMapper<ULong, Phase, PhaseEntity>
): DbMapper<CompositeID, PhaseRelation, PhaseRelationEntity> {

    override fun toEntity(dto: PhaseRelation): PhaseRelationEntity {
        return daoDelegate.findOrCreate(dto, {})
    }

    override fun toDto(entity: PhaseRelationEntity): PhaseRelation {
        return PhaseRelation(
            parent = phaseMapper.toDto(entity.parent),
            child = phaseMapper.toDto(entity.child)
        )
    }


}