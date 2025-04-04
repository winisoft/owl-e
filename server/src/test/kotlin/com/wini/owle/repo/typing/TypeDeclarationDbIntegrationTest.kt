package com.wini.owle.repo.typing

import com.wini.owle.TestHelper
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.repo.OwleRepoImplTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull


open class TypeDeclarationDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.TypingModule.TypeDeclaration)
    }

    @Test
    @DisplayName("Delete Cascade From TypeDefinition")
    fun `test 'on delete' of dependent TypeDefinition entity cascades to this`() {
        transaction {
            val typeDefinition = typeDefinitionDao()
            val typeDeclaration = typeDeclarationDao(typeDefinition = typeDefinition)
            val typeDeclarationId = typeDeclaration.id.value

            TypeDefinitionRepository().delete(typeDefinition.id)

            val fetchedTypeDeclaration = TypeDeclarationRepository().getById(EntityID(typeDeclarationId, TypeDeclarations))
            assertNull(
                fetchedTypeDeclaration,
                "TypeDeclaration should have been deleted on cascade from TypeDefinition delete"
            )
        }
    }

}