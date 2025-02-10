package com.wini.owle.repo.environs

import com.wini.owle.entity.EntityTestInstanceFactory
import com.wini.owle.entity.EntityTestInstanceFactory.packageDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.versionSpecDao
import com.wini.owle.entity.Packages
import com.wini.owle.entity.TypeDefinitions
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.repo.BaseOwleRepoTest
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import com.wini.owle.repo.typing.TypeDefinitionRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull


open class VersionSpecRepositoryTest: UnconstrainedDeleteRepoTest<Long, VersionSpecDao>() {

    private val realRepo by KoinJavaComponent.inject<VersionSpecRepository>(VersionSpecRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<VersionSpecDao>): List<VersionSpecDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    versionMin = "1.1"
                }
                get(1).apply {
                    versionMin = "2.1"
                }
            }
        }
    }

    override fun modifyEntity(entity: VersionSpecDao): VersionSpecDao {
        return transaction {
            entity.apply {
                versionMin = "1.1"
            }
        }
    }

    override val implicatedTables = listOf(VersionSpecs)
    override fun getInstance(): VersionSpecDao = versionSpecDao()
    override fun getList(): List<VersionSpecDao> {
        return listOf(
            versionSpecDao(versionMin = "1.0"),
            versionSpecDao(versionMin = "2.0")
        )
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, VersionSpecs)
    }

    override val testModule = module {
        single<VersionSpecRepository> { VersionSpecRepository() }
        single { EntityTestInstanceFactory }
    }

    @Test
    @DisplayName("Delete Cascade From Package")
    fun `test on delete of dependent Package entity cascades to this`() {
        transaction {
            val versionSpec = versionSpecDao()
            val pkg = packageDao(versionSpec = versionSpec)
            val pkgId = pkg.id.value

            VersionSpecRepository().delete(versionSpec.id)

            val fetchedTypeDef = VersionSpecRepository().getById(EntityID(pkgId, VersionSpecs))
            assertNull(fetchedTypeDef, "Package should have been deleted on cascade from VersionSpec delete")
        }
    }

    @Test
    @DisplayName("Delete Cascade From TypeDefinition")
    fun `test on delete of dependent TypeDefinition entity cascades to this`() {
        transaction {
            val versionSpec = versionSpecDao()
            val typeDef = typeDefinitionDao(versionSpec = versionSpec)
            val typeDefId = typeDef.id.value

            VersionSpecRepository().delete(versionSpec.id)

            val fetchedTypeDef = TypeDefinitionRepository().getById(EntityID(typeDefId, TypeDefinitions))
            assertNull(fetchedTypeDef, "TypeDefinition should have been deleted on cascade from VersionSpec delete")
        }
    }
}