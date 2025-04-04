package com.wini.owle.repo.environs

import com.wini.owle.TestHelper
import com.wini.owle.entity.EntityTestInstanceFactory.packageDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.versionSpecDao
import com.wini.owle.entity.TypeDefinitions
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.repo.typing.TypeDefinitionRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull

open class VersionSpecDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.EnvironsModule.VersionSpec)
    }

    @Test
    @DisplayName("Delete Cascade From Package")
    fun `test on delete of dependent Package entity cascades to this`() {
        transaction {
            val versionSpec = versionSpecDao()
            val pkg = packageDao(versionSpec = versionSpec)
            val pkgId = pkg.id.value

            VersionSpecRepository().delete(versionSpec.id)

            val fetchedTypeDef = VersionSpecRepository().getById(EntityID(pkgId, VersionSpecs))
            assertNull(fetchedTypeDef, "Package should have been deleted on cascade from VersionSpec delete")
        }

    }

    @Test
    @DisplayName("Delete Cascade From TypeDefinition")
    fun `test on delete of dependent TypeDefinition entity cascades to this`() {
        transaction {
            val versionSpec = versionSpecDao()
            val typeDef = typeDefinitionDao(versionSpec = versionSpec)
            val typeDefId = typeDef.id.value

            VersionSpecRepository().delete(versionSpec.id)

            val fetchedTypeDef = TypeDefinitionRepository().getById(EntityID(typeDefId, TypeDefinitions))
            assertNull(fetchedTypeDef, "TypeDefinition should have been deleted on cascade from VersionSpec delete")
        }
    }
}