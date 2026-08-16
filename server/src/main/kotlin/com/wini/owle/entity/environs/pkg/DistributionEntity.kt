package com.wini.owle.entity.environs.pkg

import com.wini.owle.entity.StringEntityClass
import com.wini.owle.entity.environs.DistributionPackages
import com.wini.owle.entity.environs.Distributions
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * A PyPI project name. Its id *is* the distribution name — every released version of it is a
 * [DistributionPackageEntity] hanging off this row.
 */
class DistributionEntity(id: EntityID<String>) : Entity<String>(id) {

    companion object : StringEntityClass<DistributionEntity>(Distributions, DistributionEntity::class.java)

    val name: String get() = id.value

    /**
     * Every released version of this distribution.
     */
    val packages: SizedIterable<DistributionPackageEntity>
            by DistributionPackageEntity referrersOn DistributionPackages.distribution
}
