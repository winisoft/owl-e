package com.wini.owle.repo.typing

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeSchemaRefDao
import com.wini.owle.entity.typing.TypeSchemaRefDao
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import kotlin.test.assertNull


open class TypeSchemaRefRepositoryTest: DbLayerIntegrationTests<Long, TypeSchemaRefDao>() {


    @Test
    @DisplayName("Delete Cascade From TypeDefinition")
    fun `test 'on delete' of dependent TypeDefinition entity cascades to TypeSchemaRef`() {
        transaction {
            val typeDefinition = typeDefinitionDao()
            val typeDeclaration = typeDeclarationDao(typeDefinition = typeDefinition)
            val typeSchemaRef = typeSchemaRefDao(typeDeclaration = typeDeclaration)
            val typeSchemaRefId = typeSchemaRef.id.value

            TypeDefinitionRepository().delete(typeDefinition.id)

            val fetchedTypeSchemaRef = getRepo().getById(EntityID(typeSchemaRefId, TypeSchemaRefs))
            assertNull(
                fetchedTypeSchemaRef,
                "TypeSchemaRef should have been deleted on cascade from TypeDefinition delete"
            )
        }
    }

    @Test
    @DisplayName("Delete Cascade From TypeDeclaration")
    fun `test 'on delete' of dependent TypeDeclaration entity cascades to TypeSchemaRef`() {
        transaction {
            val typeDeclaration = typeDeclarationDao()
            val typeSchemaRef = typeSchemaRefDao(typeDeclaration = typeDeclaration)
            val typeSchemaRefId = typeSchemaRef.id.value

            val repo = getRepo()
            repo.delete(typeDeclaration.id)

            val fetchedTypeSchemaRef = repo.getById(EntityID(typeSchemaRefId, TypeSchemaRefs))
            assertNull(
                fetchedTypeSchemaRef,
                "TypeSchemaRef should have been deleted on cascade from TypeDeclaration delete"
            )
        }
    }

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, TypeSchemaRefDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): TypeSchemaRefDao {
        TODO("Not yet implemented")
    }
}