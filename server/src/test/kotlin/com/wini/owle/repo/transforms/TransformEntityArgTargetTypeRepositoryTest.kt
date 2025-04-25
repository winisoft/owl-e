package com.wini.owle.repo.transforms

import com.wini.owle.TestHelper
import com.wini.owle.entity.environs.VersionSpecEntity
import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.entity.typing.TypeDeclarationEntity
import com.wini.owle.entity.typing.TypeDefinitionEntity
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


open class TransformEntityArgTargetTypeRepositoryTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.TransformsModule.TransformTargetType)
    }

    @Test
    @DisplayName("Transformation to TypeDeclaration Relationship")
    fun `test Transformation to TypeDeclaration relationship`() {
        return transaction {
            val versionSpec = VersionSpecEntity.new {
                versionMin = "1.0"
                versionMax = "2.0"
                versionExact = null
            }
            val typeDefinitionEntity = TypeDefinitionEntity.new {
                name = "List"
                namespace = "builtins"
                this.versionSpec = versionSpec
            }
            val typeDeclarationEntity = TypeDeclarationEntity.new {
                alias = "CustomType"
                this.typeDefinitionEntity = typeDefinitionEntity
            }
            val transformation = TransformEntity.new {
                name = "Example Transformation"
                description = "Transforms data"
                examples = "Input -> Output"
                returnsTypeDeclarationEntity = typeDeclarationEntity
                eligibleTypeDeclarationsEntity = SizedCollection(listOf(typeDeclarationEntity))
            }

            assertEquals(1, transformation.eligibleTypeDeclarationsEntity.count())
            assertEquals("CustomType", transformation.eligibleTypeDeclarationsEntity.first().alias)
            assertEquals(1, typeDeclarationEntity.transformations.count())
            assertEquals("Example Transformation", typeDeclarationEntity.transformations.first().name)
        }
    }
}