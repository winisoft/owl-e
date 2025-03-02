package com.wini.owle.repo.typing

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.typeParameterDao
import com.wini.owle.entity.typing.TypeParameterDao
import com.wini.owle.repo.BaseOwleRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import kotlin.test.assertNotNull
import kotlin.test.assertNull

import com.wini.owle.repo.UnconstrainedDeleteRepoTest


open class TypeParameterRepositoryTest : UnconstrainedDeleteRepoTest<Long, TypeParameterDao>() {

    private val realRepo by KoinJavaComponent.inject<TypeParameterRepository>(TypeParameterRepository::class.java)

    override fun getRepo() = realRepo

    override fun modifyEntities(entities: List<TypeParameterDao>): List<TypeParameterDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    position = 2
                }
                get(1).apply {
                    position = 3
                }
            }
        }
    }

    override fun modifyEntity(entity: TypeParameterDao): TypeParameterDao {
        return transaction {
            entity.apply {
                position = 2
            }
        }
    }

    override val implicatedTables = listOf(TypeParameters, TypeDeclarations, TypeDefinitions, VersionSpecs)

    override val testModule: Module
        get() = module {
            single<TypeParameterRepository> { TypeParameterRepository() }
        }

    override fun getInstance(): TypeParameterDao {
        return typeParameterDao()
    }

    override fun getList(): List<TypeParameterDao> {
        return listOf(typeParameterDao(), typeParameterDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, TypeParameters)
    }



    @Test
    @DisplayName("Delete Cascade From TypeDefinition")
    fun `test 'on delete' of dependent TypeDefinition entity cascades to TypeParameter`() {
        transaction {
            val typeDefinition = EntityTestInstanceFactory.typeDefinitionDao()
            val typeDeclaration = EntityTestInstanceFactory.typeDeclarationDao(typeDefinition = typeDefinition)
            val typeParameter = EntityTestInstanceFactory.typeParameterDao(typeDeclaration = typeDeclaration)
            val typeParameterId = typeParameter.id.value

            TypeDefinitionRepository().delete(typeDefinition.id)

            val fetchedTypeParameter = getRepo().getById(EntityID(typeParameterId, TypeParameters))
            assertNull(
                fetchedTypeParameter,
                "TypeParameter should have been deleted on cascade from TypeDefinition delete"
            )
        }
    }

    @Test
    @DisplayName("Delete Cascade From TypeDeclaration")
    fun `test 'on delete' of dependent TypeDeclaration entity cascades to TypeParameter`() {
        transaction {
            val typeDeclaration = EntityTestInstanceFactory.typeDeclarationDao()
            val typeParameter = EntityTestInstanceFactory.typeParameterDao(typeDeclaration = typeDeclaration)
            val typeParameterId = typeParameter.id.value

            TypeDeclarationRepository().delete(typeDeclaration.id)

            val fetchedTypeParameter = getRepo().getById(EntityID(typeParameterId, TypeParameters))
            assertNull(
                fetchedTypeParameter,
                "TypeParameter should have been deleted on cascade from TypeDeclaration delete"
            )
        }
    }
}