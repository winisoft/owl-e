package com.wini.owle.entity.tags.targets

import com.wini.owle.entity.environs.version.VersionEntity
import com.wini.owle.entity.tags.PackageTags
import com.wini.owle.entity.tags.TagEntity
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a tag a given user has associated with a Package
 */
class PackageTagEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {
    companion object : CompositeEntityClass<PackageTagEntity>(PackageTags)

    /**
     * The packageEntity for which the tag is applied
     */
    var version by VersionEntity referencedOn PackageTags.version

    /**
     * The tag associated with the packageEntity
     */
    var tag by TagEntity.Companion referencedOn PackageTags.tag
}