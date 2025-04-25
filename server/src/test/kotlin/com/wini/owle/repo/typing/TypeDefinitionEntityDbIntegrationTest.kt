package com.wini.owle.repo.typing

import com.wini.owle.TestHelper
import com.wini.owle.entity.EntityTestInstanceFactory
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.repo.OwleRepoImplTest
import com.wini.owle.repo.environs.VersionSpecRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNull


open class TypeDefinitionEntityDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.TypingModule.TypeDefinition)
    }

    @Test
    @DisplayName("Delete Cascade From Package")
    fun `test on delete of dependent Package entity cascades to this`() {
        transaction {
            val versionSpec = EntityTestInstanceFactory.versionSpecDao()
            val pkg = EntityTestInstanceFactory.packageDao(versionSpec = versionSpec)
            val pkgId = pkg.id.value

            VersionSpecRepository().delete(versionSpec.id)

            val fetchedTypeDef = VersionSpecRepository().getById(EntityID(pkgId, VersionSpecs))
            assertNull(fetchedTypeDef, "Package should have been deleted on cascade from VersionSpec delete")
        }
    }

}