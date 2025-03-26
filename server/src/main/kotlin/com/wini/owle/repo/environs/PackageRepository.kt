package com.wini.owle.repo.environs

import com.wini.owle.entity.environs.PackageDao
import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Provides data access operations for `PackageEntity` objects.
 */
class PackageRepository : OwleLongIdRepoImpl<PackageDao>() {

    override val entityClass: LongEntityClass<PackageDao>
        get() = PackageDao

    fun createPackageWithVersionSpec(
        packageName: String,
        disambiguation: String?,
        versionSpec: VersionSpecDao
    ): PackageDao {
        return transaction {
            val newPackage = PackageDao.new {
                name = packageName
                this.disambiguation = disambiguation
                this.versionSpec = versionSpec
            }
            newPackage.flush()
            newPackage
        }
    }

    // Example for an update with nested properties
    fun updatePackageWithVersionSpec(
        packageId: Long,
        packageName: String,
        versionSpec: VersionSpecDao?
    ): PackageDao? {
        return transaction {
            val packageEntity = PackageDao.findById(packageId) ?: return@transaction null
            packageEntity.apply {
                name = packageName
                if (versionSpec != null) {
                    this.versionSpec = versionSpec
                }
            }.also {
                it.flush()
            }
        }
    }

}