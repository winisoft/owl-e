package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.tags.TagRelation
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.tags.TagRelationEntity
import com.wini.owle.entity.tags.TagRelations
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.Column

object TagRelationDaoDelegate: CompositeIdDaoDelegate<
        TagRelation,
        TagRelationEntity
    >(TagRelationEntity.Companion) {

    fun buildId(parentTag: TagEntity, childTag: TagEntity): CompositeID {
        return CompositeID {
            it[TagRelations.parentTag] = parentTag.id
            it[TagRelations.childTag] = childTag.id
        }
    }

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {
            it[TagRelations.parentTag] = (params["parentTagId"] as Tag).id
            it[TagRelations.childTag] = (params["childTagId"] as Tag).id
        }
    }

    override fun extractId(dto: TagRelation): CompositeID {
        return CompositeID.Companion {
            it[TagRelations.parentTag] = dto.parentTagId
            it[TagRelations.childTag] = dto.childTagId
        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(
        "parentTagId" to TagRelations.parentTag,
        "childTagId" to TagRelations.childTag
    )

}