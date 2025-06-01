package com.wini.owle.mapper.tags.targets

import com.wini.owle.dto.tags.targets.PhaseTag
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.tags.targets.PhaseTagEntity
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class PhaseTagMapper(
    private val daoDelegate: DaoDelegate<CompositeID, PhaseTag, PhaseTagEntity>
): DbMapper<CompositeID, PhaseTag, PhaseTagEntity> {

    override fun toEntity(dto: PhaseTag): PhaseTagEntity {
        TODO("Not yet implemented")
    }

    override fun toDto(entity: PhaseTagEntity): PhaseTag {
        TODO("Not yet implemented")
    }

}