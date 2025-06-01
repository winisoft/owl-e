package com.wini.owle.entity.environs.pkg

import com.wini.owle.entity.environs.DistributionPackages
import com.wini.owle.entity.environs.PackageDistRequirements
import com.wini.owle.entity.environs.marker.EnvMarkerExpressionEntity
import com.wini.owle.entity.environs.version.VersionEntity
import com.wini.owle.entity.environs.Versions
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a unique Python library package, along with its specific version details.
 * Used to store information about packages that can be installed in the workflow.
 */
class DistributionPackageEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    var distribution by DistributionEntity referencedOn DistributionPackages.distribution
    var version by VersionEntity referencedOn DistributionPackages.version

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
    var yanked: Boolean by DistributionPackages.yanked
    var lastSerial: UInt? by DistributionPackages.lastSerial

    var envMarkerExpression by EnvMarkerExpressionEntity referencedOn PackageDistRequirements

//    val requirements: List<VersionEntity>
//        get() {
//            return VersionEntity.find {
//                (PackageDistRequirements.dependentVersion eq version.id)
//            }.toList()
//        }


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

}

//val compatVersions by PackagePythonCompatEntity.Companion referrersOn PackagePythonCompats.packageId
