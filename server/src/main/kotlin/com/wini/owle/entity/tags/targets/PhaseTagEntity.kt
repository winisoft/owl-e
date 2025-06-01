package com.wini.owle.entity.tags.targets

import com.wini.owle.entity.tags.PhaseTags
import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.workflows.PhaseEntity
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a tag a given user has associated with a Phase
 */
class PhaseTagEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {
    companion object : CompositeEntityClass<PhaseTagEntity>(PhaseTags)

    /**
     * The phase for which the tag is applied
     */
    var phase by PhaseEntity referencedOn PhaseTags.phase

    /**
     * The tag associated with the phase
     */
    var tagEntity by TagEntity.Companion referencedOn PhaseTags.tag
}