package com.wini.owle.entity.tags

import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a hierarchical relationship between tags, allowing tags to have parent-child associations.
 * Useful for organizing tags in a way that reflects dependency or hierarchy.
 */
class TagRelationEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {
    companion object : CompositeEntityClass<TagRelationEntity>(TagRelations) {

        fun buildId(parentTagId: ULong, childTagId: ULong): CompositeID {
            return CompositeID {
                it[TagRelations.parentTag] = parentTagId
                it[TagRelations.childTag] = childTagId
            }
        }

        /**
         * Creates a new TagRelationDao by building the CompositeID from the
         * two TagDao objects. This matches typical Exposed usage for composite keys.
         */
        fun create(parentTagId: ULong, childTagId: ULong): TagRelationEntity {
            val compositeId = buildId(parentTagId, childTagId)
            return TagRelationEntity(EntityID(compositeId, TagRelations))
        }
    }

    /**
     * The parent tag in the relationship.
     */
    var parentTag by TagEntity referencedOn TagRelations.parentTag

    /**
     * The child tag in the relationship.
     */
    var childTag by TagEntity referencedOn TagRelations.childTag
}