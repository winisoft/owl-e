package com.wini.owle.entity.user.tags

import com.wini.owle.entity.TagRelations
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a hierarchical relationship between tags, allowing tags to have parent-child associations.
 * Useful for organizing tags in a way that reflects dependency or hierarchy.
 */
class TagRelationEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {
    companion object : CompositeEntityClass<TagRelationEntity>(TagRelations) {

        fun buildId(parentTagId: Long, childTagId: Long): CompositeID {
            return CompositeID {
                it[TagRelations.parentTag] = parentTagId
                it[TagRelations.childTag] = childTagId
            }
        }

        /**
         * Creates a new TagRelationDao by building the CompositeID from the
         * two TagDao objects. This matches typical Exposed usage for composite keys.
         */
        fun create(parentTagId: Long, childTagId: Long): TagRelationEntity {
            val compositeId = buildId(parentTagId, childTagId)
            return TagRelationEntity(EntityID(compositeId, TagRelations))
        }
    }

    /**
     * The parent tag in the relationship.
     */
    var parentTagEntity by TagEntity referencedOn TagRelations.parentTag

    /**
     * The child tag in the relationship.
     */
    var childTagEntity by TagEntity referencedOn TagRelations.childTag
}