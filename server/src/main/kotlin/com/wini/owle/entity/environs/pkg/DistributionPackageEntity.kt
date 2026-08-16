package com.wini.owle.entity.environs.pkg

import com.wini.owle.entity.environs.DistributionPackages
import com.wini.owle.entity.environs.Versions
import com.wini.owle.entity.environs.version.VersionEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * One released version of a distribution — the PyPI metadata row.
 *
 * Keyed by (version, distribution) rather than a surrogate id, which is why everything that
 * points at a package carries both halves.
 */
class DistributionPackageEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<DistributionPackageEntity>(DistributionPackages) {

        fun buildId(distribution: String, packageId: ULong): CompositeID {
            return CompositeID {
                it[DistributionPackages.distribution] = distribution
                it[DistributionPackages.version] = EntityID(packageId, Versions)
            }
        }

        fun create(distribution: String, packageId: ULong): DistributionPackageEntity {
            val compositeID = buildId(distribution, packageId)
            return DistributionPackageEntity(EntityID(compositeID, DistributionPackages))
        }
    }

    var distribution by DistributionEntity referencedOn DistributionPackages.distribution

    var version by VersionEntity referencedOn DistributionPackages.version

    /** The version as published, before normalization into [version]'s components. */
    var versionString: String by DistributionPackages.versionString

    var summary: String? by DistributionPackages.summary
    var keywords: String? by DistributionPackages.keywords
    var description: String? by DistributionPackages.description
    var descriptionContentType: String? by DistributionPackages.descriptionContentType
    var license: String? by DistributionPackages.license
    var homePage: String? by DistributionPackages.homePage
    var projectUrl: String? by DistributionPackages.projectUrl
    var downloadUrl: String? by DistributionPackages.downloadUrl
    var docsUrl: String? by DistributionPackages.docsUrl
    var yanked: Boolean? by DistributionPackages.yanked
    var lastSerial: UInt? by DistributionPackages.lastSerial
}
