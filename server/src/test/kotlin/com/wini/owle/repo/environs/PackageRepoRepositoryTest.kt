package com.wini.owle.repo.environs

import com.wini.owle.TestHelper
import com.wini.owle.entity.EntityTestInstanceFactory.packageRepoDao
import com.wini.owle.entity.PackageRepositories
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.repo.BaseOwleRepoTest
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent



open class PackageRepoRepositoryTest: UnconstrainedDeleteRepoTest<Long, PackageRepoDao>() {

    private val realRepo by KoinJavaComponent.inject<PackageRepoRepository>(PackageRepoRepository::class.java)

    override fun getRepo() = realRepo

    override val implicatedTables = TestHelper.ImplicatedTables.EnvironsModule.PackageRepo

    override val testModule: Module
        get() = module {
            single<PackageRepoRepository> { PackageRepoRepository() }
        }

    override fun getInstance(): PackageRepoDao {
        return packageRepoDao()
    }

    override fun getList(): List<PackageRepoDao> {
        return listOf(
            packageRepoDao(sourceUrl = "https://sourceUrl1.com"),
            packageRepoDao(sourceUrl = "https://sourceUrl2.com")
        )
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, PackageRepositories)
    }

    override fun modifyEntity(entity: PackageRepoDao): PackageRepoDao {
        return transaction {
            entity.apply {
                sourceUrl = "https://new-pypi.org"
            }
        }
    }

    override fun modifyEntities(entities: List<PackageRepoDao>): List<PackageRepoDao> {
        return transaction {
            entities.apply {
                get(0).apply { sourceUrl = "https://new-pypi1.org" }
                get(1).apply { sourceUrl = "https://new-pypi2.org" }
            }
        }
    }

}