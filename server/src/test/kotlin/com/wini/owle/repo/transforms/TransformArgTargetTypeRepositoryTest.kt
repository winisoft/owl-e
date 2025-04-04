package com.wini.owle.repo.transforms

import com.wini.owle.TestHelper
import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.entity.transforms.TransformationDao
import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.entity.typing.TypeDefinitionDao
import com.wini.owle.repo.OwleRepoImplTest
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


open class TransformArgTargetTypeRepositoryTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.TransformsModule.TransformTargetType)
    }

    @Test
    @DisplayName("Transformation to TypeDeclaration Relationship")
    fun `test Transformation to TypeDeclaration relationship`() {
        return transaction {
            val versionSpec = VersionSpecDao.new {
                versionMin = "1.0"
                versionMax = "2.0"
                versionExact = null
            }
            val typeDefinition = TypeDefinitionDao.new {
                name = "List"
                namespace = "builtins"
                this.versionSpec = versionSpec
            }
            val typeDeclaration = TypeDeclarationDao.new {
                alias = "CustomType"
                this.typeDefinition = typeDefinition
            }
            val transformation = TransformationDao.new {
                name = "Example Transformation"
                description = "Transforms data"
                examples = "Input -> Output"
                returnsTypeDeclaration = typeDeclaration
                eligibleTypeDeclarations = SizedCollection(listOf(typeDeclaration))
            }

            assertEquals(1, transformation.eligibleTypeDeclarations.count())
            assertEquals("CustomType", transformation.eligibleTypeDeclarations.first().alias)
            assertEquals(1, typeDeclaration.transformations.count())
            assertEquals("Example Transformation", typeDeclaration.transformations.first().name)
        }
    }
}