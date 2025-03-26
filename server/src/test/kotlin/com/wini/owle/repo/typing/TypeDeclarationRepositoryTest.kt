package com.wini.owle.repo.typing

import com.wini.owle.entity.DbLayerIntegrationTests
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import kotlin.test.assertNull


open class TypeDeclarationRepositoryTest: DbLayerIntegrationTests<Long, TypeDeclarationDao>() {

    @Test
    @DisplayName("Delete Cascade From TypeDefinition")
    fun `test 'on delete' of dependent TypeDefinition entity cascades to this`() {
        transaction {
            val typeDefinition = typeDefinitionDao()
            val typeDeclaration = typeDeclarationDao(typeDefinition = typeDefinition)
            val typeDeclarationId = typeDeclaration.id.value

            TypeDefinitionRepository().delete(typeDefinition.id)

            val fetchedTypeDeclaration = getRepo().getById(EntityID(typeDeclarationId, TypeDeclarations))
            assertNull(
                fetchedTypeDeclaration,
                "TypeDeclaration should have been deleted on cascade from TypeDefinition delete"
            )
        }
    }

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, TypeDeclarationDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): TypeDeclarationDao {
        TODO("Not yet implemented")
    }
}