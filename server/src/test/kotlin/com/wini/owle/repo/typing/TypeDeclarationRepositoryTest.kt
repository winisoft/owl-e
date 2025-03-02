package com.wini.owle.repo.typing

import com.wini.owle.entity.EntityTestInstanceFactory
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.TypeDefinitions
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.repo.BaseOwleRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import kotlin.test.assertNotNull
import kotlin.test.assertNull

import com.wini.owle.repo.UnconstrainedDeleteRepoTest

open class TypeDeclarationRepositoryTest: UnconstrainedDeleteRepoTest<Long, TypeDeclarationDao>() {

    private val realRepo by KoinJavaComponent.inject<TypeDeclarationRepository>(TypeDeclarationRepository::class.java)

    override fun getRepo() = realRepo

    override fun modifyEntities(entities: List<TypeDeclarationDao>): List<TypeDeclarationDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    alias = "Updated Alias 1"
                }
                get(1).apply {
                    alias = "Updated Alias 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: TypeDeclarationDao): TypeDeclarationDao {
        return transaction {
            entity.apply {
                alias = "Updated Alias"
            }
        }
    }

    override val implicatedTables = listOf(TypeDeclarations, TypeDefinitions, VersionSpecs)

    override val testModule: Module
        get() = module {
            single<TypeDeclarationRepository> { TypeDeclarationRepository() }
        }

    override fun getInstance(): TypeDeclarationDao {
        return typeDeclarationDao()
    }

    override fun getList(): List<TypeDeclarationDao> {
        return listOf(typeDeclarationDao(), typeDeclarationDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, TypeDeclarations)
    }



    @Test
    @DisplayName("Delete Cascade From TypeDefinition")
    fun `test 'on delete' of dependent TypeDefinition entity cascades to this`() {
        transaction {
            val typeDefinition = typeDefinitionDao()
            val typeDeclaration = typeDeclarationDao(typeDefinition = typeDefinition)
            val typeDeclarationId = typeDeclaration.id.value

            TypeDefinitionRepository().delete(typeDefinition.id)

            val fetchedTypeDeclaration = getRepo().getById(EntityID(typeDeclarationId, TypeDeclarations))
            assertNull(
                fetchedTypeDeclaration,
                "TypeDeclaration should have been deleted on cascade from TypeDefinition delete"
            )
        }
    }
}