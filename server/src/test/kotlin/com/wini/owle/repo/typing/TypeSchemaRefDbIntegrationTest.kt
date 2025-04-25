package com.wini.owle.repo.typing

import com.wini.owle.TestHelper
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeSchemaRefDao
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull


open class TypeSchemaRefDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.TypingModule.TypeSchemaRef)
    }

    @Test
    @DisplayName("Delete Cascade From TypeDefinition")
    fun `test 'on delete' of dependent TypeDefinition entity cascades to TypeSchemaRef`() {
        transaction {
            val typeDefinition = typeDefinitionDao()
            val typeDeclaration = typeDeclarationDao(typeDefinitionEntity = typeDefinition)
            val typeSchemaRef = typeSchemaRefDao(typeDeclarationEntity = typeDeclaration)
            val typeSchemaRefId = typeSchemaRef.id.value

            TypeDefinitionRepository().delete(typeDefinition.id)

            val fetchedTypeSchemaRef = TypeSchemaRefRepository().getById(EntityID(typeSchemaRefId, TypeSchemaRefs))
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
            val typeSchemaRef = typeSchemaRefDao(typeDeclarationEntity = typeDeclaration)
            val typeSchemaRefId = typeSchemaRef.id.value

            val repo = TypeSchemaRefRepository()
            repo.delete(typeDeclaration.id)

            val fetchedTypeSchemaRef = repo.getById(EntityID(typeSchemaRefId, TypeSchemaRefs))
            assertNull(
                fetchedTypeSchemaRef,
                "TypeSchemaRef should have been deleted on cascade from TypeDeclaration delete"
            )
        }
    }

}