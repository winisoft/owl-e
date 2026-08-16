package com.wini.owle.entity.tags.targets

import com.wini.owle.entity.environs.pkg.DistributionEntity
import com.wini.owle.entity.environs.version.VersionEntity
import com.wini.owle.entity.tags.PackageTags
import com.wini.owle.entity.tags.TagEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * Represents a tag a given user has associated with a Package.
 *
 * A package is keyed by (distribution, version) rather than a single id, so both halves are here.
 */
class PackageTagEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<PackageTagEntity>(PackageTags)

    /**
     * The distribution half of the package key.
     */
    var distribution by DistributionEntity referencedOn PackageTags.distribution

    /**
     * The version half of the package key.
     */
    var packageVersion by VersionEntity referencedOn PackageTags.packageVersion

    /**
     * The tag associated with the package
     */
    var tag by TagEntity referencedOn PackageTags.tag
}
