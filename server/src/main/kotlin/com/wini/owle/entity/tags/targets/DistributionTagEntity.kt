package com.wini.owle.entity.tags.targets

import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.environs.pkg.DistributionEntity
import com.wini.owle.entity.tags.DistributionTags
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a tag a given user has associated with a Distribution
 */
class DistributionTagEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<DistributionTagEntity>(DistributionTags)

    /**
     * The distribution for which the tag is applied
     */
    var distribution by DistributionEntity referencedOn DistributionTags.distribution

    /**
     * The tag associated with the distribution
     */
    var tag by TagEntity.Companion referencedOn DistributionTags.tag

}