package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.tags.targets.GrouperTag
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.tags.targets.GrouperTagEntity
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


object GrouperTagDaoDelegate: CompositeIdDaoDelegate<
        GrouperTag,
        GrouperTagEntity,
        >(GrouperTagEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {

        }
    }

    override fun extractId(dto: GrouperTag): CompositeID {
        return CompositeID.Companion {

        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}