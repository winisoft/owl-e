package com.wini.owle.repo.typing

import com.wini.owle.entity.DbLayerIntegrationTests
import com.wini.owle.entity.EntityTestInstanceFactory
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.entity.typing.TypeDefinitionDao
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import com.wini.owle.repo.environs.VersionSpecRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import kotlin.test.assertNull


open class TypeDefinitionRepositoryTest : DbLayerIntegrationTests<Long, TypeDefinitionDao>() {


    @Test
    @DisplayName("Delete Cascade From Package")
    fun `test on delete of dependent Package entity cascades to this`() {
        transaction {
            val versionSpec = EntityTestInstanceFactory.versionSpecDao()
            val pkg = EntityTestInstanceFactory.packageDao(versionSpec = versionSpec)
            val pkgId = pkg.id.value

            VersionSpecRepository().delete(versionSpec.id)

            val fetchedTypeDef = VersionSpecRepository().getById(EntityID(pkgId, VersionSpecs))
            assertNull(fetchedTypeDef, "Package should have been deleted on cascade from VersionSpec delete")
        }
    }

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, TypeDefinitionDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): TypeDefinitionDao {
        TODO("Not yet implemented")
    }

}