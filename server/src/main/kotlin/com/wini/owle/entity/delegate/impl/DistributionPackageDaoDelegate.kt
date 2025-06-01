package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.dto.environs.pkg.DistributionPackage
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.environs.pkg.DistributionPackageEntity
import com.wini.owle.entity.environs.pkg.DistributionPackages
import com.wini.owle.entity.environs.Versions
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.Column

object DistributionPackageDaoDelegate: CompositeIdDaoDelegate<
        DistributionPackage,
        DistributionPackageEntity
        >(DistributionPackageEntity.Companion) {

    fun buildId(
        distributionName: String,
        versionId: ULong
    ): CompositeID {
        return CompositeID.Companion {
            it[DistributionPackages.distribution] = distributionName
            //it[DistributionPackages.version] = EntityID<ULong>(versionId, Versions)
        }

    }

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {
            it[DistributionPackages.distribution] = (params["distributionId"] as Distribution).name
            //it[DistributionPackages.version] = (params["versionId"] as Version).id
        }
    }

    override fun extractId(dto: DistributionPackage): CompositeID {
        return CompositeID.Companion {
            it[DistributionPackages.distribution] = dto.distribution.name
            it[DistributionPackages.version] = EntityID(dto.version.id, Versions)
        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(
        "distributionId" to DistributionPackages.distribution,
        "versionId" to DistributionPackages.version
    )

}