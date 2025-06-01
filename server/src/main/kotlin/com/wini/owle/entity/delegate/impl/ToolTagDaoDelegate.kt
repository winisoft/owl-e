package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.tags.targets.ToolTag
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.tags.targets.ToolTagEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.Column

object ToolTagDaoDelegate: CompositeIdDaoDelegate<
        ToolTag,
        ToolTagEntity
        >(ToolTagEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {

        }
    }

    override fun extractId(dto: ToolTag): CompositeID {
        return CompositeID.Companion {

        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}