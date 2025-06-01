package com.wini.owle.mapper.tags.targets

import com.wini.owle.dto.tags.targets.StepTag
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.tags.targets.StepTagEntity
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class StepTagMapper(
    private val daoDelegate: DaoDelegate<CompositeID, StepTag, StepTagEntity>
): DbMapper<CompositeID, StepTag, StepTagEntity> {

    override fun toEntity(dto: StepTag): StepTagEntity {
        TODO("Not yet implemented")
    }

    override fun toDto(entity: StepTagEntity): StepTag {
        TODO("Not yet implemented")
    }

}