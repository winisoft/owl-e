package com.wini.owle.entity.environs.pkg

import com.wini.owle.entity.environs.PackageExtras
import com.wini.owle.entity.environs.version.VersionEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * A named optional feature set of a package — the `[dev]` in `requests[dev]`. Requirements
 * reference these by name to pull in the extra's own dependency set.
 */
class PackageExtraEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<PackageExtraEntity>(PackageExtras)

    var distribution by DistributionEntity referencedOn PackageExtras.distribution

    var packageVersion by VersionEntity referencedOn PackageExtras.packageVersion

    /** Part of the composite key, so read-only here: the DaoDelegate sets it via the CompositeID. */
    val name: String get() = id.value[PackageExtras.name].value

    var description by PackageExtras.description
}
