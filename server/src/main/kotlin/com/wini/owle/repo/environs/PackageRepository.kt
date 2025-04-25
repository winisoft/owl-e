package com.wini.owle.repo.environs

import com.wini.owle.entity.environs.PyPackageEntity
import com.wini.owle.entity.environs.VersionSpecEntity
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.sql.transactions.transaction


/**
 * Provides data access operations for `PackageEntity` objects.
 */
class PackageRepository: OwleRepoImpl<Long, PyPackageEntity>(PyPackageEntity) {

    fun createPackageWithVersionSpec(
        packageName: String,
        disambiguation: String?,
        versionSpec: VersionSpecEntity
    ): PyPackageEntity {
        return transaction {
            val newPackage = PyPackageEntity.new {
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
        versionSpec: VersionSpecEntity?
    ): PyPackageEntity? {
        return transaction {
            val packageEntity = PyPackageEntity.findById(packageId) ?: return@transaction null
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