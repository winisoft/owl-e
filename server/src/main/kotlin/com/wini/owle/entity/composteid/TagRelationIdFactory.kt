package com.wini.owle.entity.composteid

import com.wini.owle.dto.user.TagRelationDto
import com.wini.owle.entity.TagRelations
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column

object TagRelationIdFactory: CompositeIdFactoryImpl<TagRelationDto>() {

    override fun fromDtoInstance(dto: TagRelationDto): CompositeID? {
        return CompositeID {
            it[TagRelations.parentTag] = dto.parentTagId
            it[TagRelations.childTag] = dto.childTagId
        }
    }

    override val fkReferences: Map<Column<EntityID<Long>>, String> = buildMap {
        TagRelations.parentTag to "parentTagId"
        TagRelations.childTag to "childTagId"
    }
}