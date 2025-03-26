package com.wini.owle.repo.typing

import com.wini.owle.entity.*
import com.wini.owle.entity.typing.TypeParameterDao
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import kotlin.test.assertNull


open class TypeParameterRepositoryTest : DbLayerIntegrationTests<Long, TypeParameterDao>() {

    @Test
    @DisplayName("Delete Cascade From TypeDefinition")
    fun `test 'on delete' of dependent TypeDefinition entity cascades to TypeParameter`() {
        transaction {
            val typeDefinition = EntityTestInstanceFactory.typeDefinitionDao()
            val typeDeclaration = EntityTestInstanceFactory.typeDeclarationDao(typeDefinition = typeDefinition)
            val typeParameter = EntityTestInstanceFactory.typeParameterDao(typeDeclaration = typeDeclaration)
            val typeParameterId = typeParameter.id.value

            TypeDefinitionRepository().delete(typeDefinition.id)

            val fetchedTypeParameter = getRepo().getById(EntityID(typeParameterId, TypeParameters))
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
            val typeParameter = EntityTestInstanceFactory.typeParameterDao(typeDeclaration = typeDeclaration)
            val typeParameterId = typeParameter.id.value

            TypeDeclarationRepository().delete(typeDeclaration.id)

            val fetchedTypeParameter = getRepo().getById(EntityID(typeParameterId, TypeParameters))
            assertNull(
                fetchedTypeParameter,
                "TypeParameter should have been deleted on cascade from TypeDeclaration delete"
            )
        }
    }

    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, TypeParameterDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): TypeParameterDao {
        TODO("Not yet implemented")
    }
}