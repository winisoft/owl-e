package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.tags.targets.DistributionTag
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.environs.pkg.DistributionEntity
import com.wini.owle.entity.tags.DistributionTags.distribution
import com.wini.owle.entity.tags.DistributionTags.tag
import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.tags.targets.DistributionTagEntity
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


object DistributionTagDaoDelegate: CompositeIdDaoDelegate<
        DistributionTag,
        DistributionTagEntity,
        >(DistributionTagEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {
            it[distribution] = DistributionEntity[params["distribution"] as String].id
            it[tag] = TagEntity[params["tag"] as ULong].id
        }
    }

    override fun extractId(dto: DistributionTag): CompositeID {
        return CompositeID.Companion {
            it[distribution] = dto.distribution.name
            it[tag] = dto.tag.id
        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}