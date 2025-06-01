package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.tags.targets.PackageTag
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.tags.targets.PackageTagEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.Column

object PackageTagDaoDelegate: CompositeIdDaoDelegate<
        PackageTag,
        PackageTagEntity
        >(PackageTagEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {

        }
    }

    override fun extractId(dto: PackageTag): CompositeID {
        return CompositeID.Companion {

        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}