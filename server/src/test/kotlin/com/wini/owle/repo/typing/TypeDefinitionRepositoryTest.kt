package com.wini.owle.repo.typing

import com.wini.owle.entity.EntityTestInstanceFactory
import com.wini.owle.entity.EntityTestInstanceFactory.typeBoundDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeParameterDao
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.TypeDefinitions
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.entity.typing.TypeDefinitionDao
import com.wini.owle.repo.BaseOwleRepoTest
import com.wini.owle.repo.environs.VersionSpecRepository
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
import kotlin.test.assertNull

import com.wini.owle.repo.UnconstrainedDeleteRepoTest

open class TypeDefinitionRepositoryTest : UnconstrainedDeleteRepoTest<Long, TypeDefinitionDao>() {
    private val realRepo by KoinJavaComponent.inject<TypeDefinitionRepository>(TypeDefinitionRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<TypeDefinitionDao>): List<TypeDefinitionDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    name = "Updated Type Name 1"
                }
                get(1).apply {
                    name = "Updated Type Name 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: TypeDefinitionDao): TypeDefinitionDao {
        return transaction {
            entity.apply {
                name = "Updated Type Name"
            }
        }
    }

    override val implicatedTables = listOf(TypeDefinitions, VersionSpecs)

    override val testModule: Module
        get() = module {
            single<TypeDefinitionRepository> { TypeDefinitionRepository() }
        }

    override fun getInstance(): TypeDefinitionDao {
        return typeDefinitionDao()
    }

    override fun getList(): List<TypeDefinitionDao> {
        return listOf(typeDefinitionDao(), typeDefinitionDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, TypeDefinitions)
    }



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

}