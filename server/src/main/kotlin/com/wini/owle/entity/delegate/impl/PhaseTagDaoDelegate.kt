package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.tags.targets.PhaseTag
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.tags.targets.PhaseTagEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.Column

object PhaseTagDaoDelegate: CompositeIdDaoDelegate<
        PhaseTag,
        PhaseTagEntity
        >(PhaseTagEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {

        }
    }

    override fun extractId(dto: PhaseTag): CompositeID {
        return CompositeID.Companion {

        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}