package com.wini.owle.repo.typing

import com.wini.owle.TestHelper
import com.wini.owle.entity.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull


open class TypeParameterDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.TypingModule.TypeParameter)
    }

    @Test
    @DisplayName("Delete Cascade From TypeDefinition")
    fun `test 'on delete' of dependent TypeDefinition entity cascades to TypeParameter`() {
        transaction {
            val typeDefinition = EntityTestInstanceFactory.typeDefinitionDao()
            val typeDeclaration = EntityTestInstanceFactory.typeDeclarationDao(typeDefinitionEntity = typeDefinition)
            val typeParameter = EntityTestInstanceFactory.typeParameterDao(typeDeclarationEntity = typeDeclaration)
            val typeParameterId = typeParameter.id.value

            TypeDefinitionRepository().delete(typeDefinition.id)

            val fetchedTypeParameter = TypeParameterRepository().getById(EntityID(typeParameterId, TypeParameters))
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
            val typeParameter = EntityTestInstanceFactory.typeParameterDao(typeDeclarationEntity = typeDeclaration)
            val typeParameterId = typeParameter.id.value

            TypeDeclarationRepository().delete(typeDeclaration.id)

            val fetchedTypeParameter = TypeParameterRepository().getById(EntityID(typeParameterId, TypeParameters))
            assertNull(
                fetchedTypeParameter,
                "TypeParameter should have been deleted on cascade from TypeDeclaration delete"
            )
        }
    }

}