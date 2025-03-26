package com.wini.owle.repo.typing

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.typeBoundDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.typing.TypeBoundDao
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import kotlin.test.assertNull


open class TypeBoundRepositoryTest : DbLayerIntegrationTests<Long, TypeBoundDao>() {


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

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, TypeBoundDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): TypeBoundDao {
        TODO("Not yet implemented")
    }

}