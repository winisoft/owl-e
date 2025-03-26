package com.wini.owle.entity.user

import com.wini.owle.entity.TagRelations
import com.wini.owle.entity.TransformTargetTypes
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a hierarchical relationship between tags, allowing tags to have parent-child associations.
 * Useful for organizing tags in a way that reflects dependency or hierarchy.
 */
class TagRelationDao(id: EntityID<CompositeID>) : CompositeEntity(id) {
    companion object : CompositeEntityClass<TagRelationDao>(TagRelations) {

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
        fun create(parentTagId: Long, childTagId: Long): TagRelationDao {
            val compositeId = buildId(parentTagId, childTagId)
            return TagRelationDao(EntityID(compositeId, TagRelations))
        }
    }

    /**
     * The parent tag in the relationship.
     */
    var parentTag by TagDao referencedOn TagRelations.parentTag

    /**
     * The child tag in the relationship.
     */
    var childTag by TagDao referencedOn TagRelations.childTag
}