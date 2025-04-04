package com.wini.owle.repo.typing

import com.wini.owle.TestHelper
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.typeBoundDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.repo.OwleRepoImplTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull


open class TypeBoundDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.TypingModule.TypeBound)
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

            val fetchedTypeParameter = TypeBoundRepository().getById(EntityID(typeBoundsId, TypeBounds))
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

            val fetchedTypeParameter = TypeBoundRepository().getById(EntityID(typeBoundsId, TypeBounds))
            assertNull(
                fetchedTypeParameter,
                "TypeBounds should have been deleted on cascade from TypeDeclaration delete"
            )
        }
    }

}