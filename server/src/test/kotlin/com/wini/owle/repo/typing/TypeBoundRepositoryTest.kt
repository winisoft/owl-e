package com.wini.owle.repo.typing

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.typeBoundDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeVarianceDao
import com.wini.owle.entity.typing.TypeBoundDao
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
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

import com.wini.owle.repo.UnconstrainedDeleteRepoTest

open class TypeBoundRepositoryTest : UnconstrainedDeleteRepoTest<Long, TypeBoundDao>() {

    private val realRepo by KoinJavaComponent.inject<TypeBoundRepository>(TypeBoundRepository::class.java)

    override fun getRepo() = realRepo

    override fun modifyEntities(entities: List<TypeBoundDao>): List<TypeBoundDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    this.boundTypeDeclaration = typeDeclarationDao()
                }
                get(1).apply {
                    this.boundTypeDeclaration = typeDeclarationDao()
                }
            }
        }
    }

    override fun modifyEntity(entity: TypeBoundDao): TypeBoundDao {
        return transaction {
            entity.apply {
                this.boundTypeDeclaration = typeDeclarationDao()
            }
        }
    }

    override val implicatedTables = listOf(TypeBounds, TypeDeclarations, TypeDefinitions, VersionSpecs)

    override val testModule: Module
        get() = module {
            single<TypeBoundRepository> { TypeBoundRepository() }
        }

    override fun getInstance(): TypeBoundDao {
        return typeBoundDao()
    }

    override fun getList(): List<TypeBoundDao> {
        return listOf(typeBoundDao(), typeBoundDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, TypeBounds)
    }



    @Test
    @DisplayName("Delete Cascade From TypeDefinition")
    fun `test 'on delete' of dependent TypeDefinition entity cascades to Bounds`() {
        transaction {
            val typeDefinition = typeDefinitionDao()
            val typeDeclaration = typeDeclarationDao(typeDefinition = typeDefinition)
            val typeBounds = typeBoundDao(boundTypeDeclaration = typeDeclaration)
            val typeBoundsId = typeBounds.id.value

            TypeDefinitionRepository().delete(typeDefinition.id)

            val fetchedTypeParameter = getRepo().getById(EntityID(typeBoundsId, TypeBounds))
            assertNull(
                fetchedTypeParameter,
                "TypeBounds should have been deleted on cascade from TypeDefinition delete"
            )
        }
    }

    @Test
    @DisplayName("Delete Cascade From TypeDeclaration")
    fun `test 'on delete' of dependent TypeDeclaration entity cascades to TypeBounds`() {
        transaction {
            val typeDeclaration = typeDeclarationDao()
            val typeBounds = typeBoundDao(boundTypeDeclaration = typeDeclaration)
            val typeBoundsId = typeBounds.id.value

            TypeDeclarationRepository().delete(typeDeclaration.id)

            val fetchedTypeParameter = getRepo().getById(EntityID(typeBoundsId, TypeBounds))
            assertNull(
                fetchedTypeParameter,
                "TypeBounds should have been deleted on cascade from TypeDeclaration delete"
            )
        }
    }

}