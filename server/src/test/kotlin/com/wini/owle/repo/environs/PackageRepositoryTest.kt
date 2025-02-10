package com.wini.owle.repo.environs

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.packageDao
import com.wini.owle.entity.environs.PackageDao
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.repo.BaseOwleRepoTest
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


open class PackageRepositoryTest: UnconstrainedDeleteRepoTest<Long, PackageDao>() {

    private val realRepo by KoinJavaComponent.inject<PackageRepository>(PackageRepository::class.java)

    override fun getRepo() = realRepo

    override fun modifyEntities(entities: List<PackageDao>): List<PackageDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    name = "Updated Package Name 1"
                }
                get(1).apply {
                    name = "Updated Package Name 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: PackageDao): PackageDao {
        return transaction {
            entity.apply {
                name = "Updated Package Name"
            }
        }
    }

    override val implicatedTables = listOf(Packages, VersionSpecs)

    override val testModule: Module
        get() = module {
            single<PackageRepository> { PackageRepository() }
        }

    override fun getInstance(): PackageDao {
        return packageDao()
    }

    override fun getList(): List<PackageDao> {
        return listOf(
            packageDao(name = "Package 1"),
            packageDao(name = "Package 2")
        )
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, Packages)
    }

}