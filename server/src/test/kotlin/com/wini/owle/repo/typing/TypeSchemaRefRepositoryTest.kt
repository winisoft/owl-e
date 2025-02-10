package com.wini.owle.repo.typing

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeSchemaRefDao
import com.wini.owle.entity.typing.TypeSchemaRefDao
import com.wini.owle.repo.BaseOwleRepoTest
import com.wini.owle.repo.OwleRepo
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

import com.wini.owle.repo.UnconstrainedDeleteRepoTest

open class TypeSchemaRefRepositoryTest: UnconstrainedDeleteRepoTest<Long, TypeSchemaRefDao>() {

    override val implicatedTables: List<IdTable<*>>
        get() = listOf(TypeDefinitions, TypeDeclarations, TypeSchemaRefs)

    override val testModule: Module
        get() = module { }

    override fun getInstance(): TypeSchemaRefDao {
        return TypeSchemaRefDao.new {
            this.parentRef = null
            this.name = "exampleSchema"
            this.typeDeclaration = typeDeclarationDao()
            this.description = "Schema reference description"
        }
    }

    override fun getList(): List<TypeSchemaRefDao> {
        return listOf(getInstance(), getInstance())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, TypeSchemaRefs)
    }

    override fun getRepo(): OwleRepo<Long, TypeSchemaRefDao> {
        return TypeSchemaRefRepository()
    }

    override fun modifyEntities(entities: List<TypeSchemaRefDao>): List<TypeSchemaRefDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    name = "New Name 1"
                }
                get(1).apply {
                    name = "New Name 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: TypeSchemaRefDao): TypeSchemaRefDao {
        return transaction {
            entity.apply {
                name = "New Name"
            }
        }
    }

    @Test
    @DisplayName("Delete Cascade From TypeDefinition")
    fun `test 'on delete' of dependent TypeDefinition entity cascades to TypeSchemaRef`() {
        transaction {
            val typeDefinition = typeDefinitionDao()
            val typeDeclaration = typeDeclarationDao(typeDefinition = typeDefinition)
            val typeSchemaRef = typeSchemaRefDao(typeDeclaration = typeDeclaration)
            val typeSchemaRefId = typeSchemaRef.id.value

            TypeDefinitionRepository().delete(typeDefinition.id)

            val fetchedTypeSchemaRef = getRepo().getById(makeEntityID(typeSchemaRefId))
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
            val typeSchemaRef = typeSchemaRefDao(typeDeclaration = typeDeclaration)
            val typeSchemaRefId = typeSchemaRef.id.value

            val repo = getRepo()
            repo.delete(typeDeclaration.id)

            val fetchedTypeSchemaRef = repo.getById(makeEntityID(typeSchemaRefId))
            assertNull(
                fetchedTypeSchemaRef,
                "TypeSchemaRef should have been deleted on cascade from TypeDeclaration delete"
            )
        }
    }

//    @Test
//    fun `test integrated MyClass schema with nested fields`() {
//        /**
//         * Represents a class "MyClass" that has
//         *   - a 'name' field of type str
//         *   - a 'subclass' field referencing "AnotherClass"
//         */
//        val myClassDefId = typeDefinitionDao(name = "MyClass", namespace = "myModule")
//        val myClassDeclId = typeDeclarationDao(typeDefinition = myClassDefId)
//
//        val rootSchemaRefId = typeSchemaRefDao(
//            parentRef = null,
//            typeDeclaration = myClassDeclId,
//            name = "MyClassRoot",
//            description = "Root reference for MyClass"
//        )
//
//        val strDefId = typeDefinitionDao(name = "str", namespace = "builtins")
//        val strDeclId = typeDeclarationDao(typeDefinition = strDefId)
//
//        typeSchemaRefDao(
//            parentRef = rootSchemaRefId,
//            typeDeclaration = strDeclId,
//            name = "name",
//            description = "String field"
//        )
//
//        val anotherClassDefId = typeDefinitionDao(name = "AnotherClass", namespace = "myModule")
//        val anotherClassDeclId = typeDeclarationDao(typeDefinition = anotherClassDefId)
//
//        typeSchemaRefDao(
//            parentRef = rootSchemaRefId,
//            typeDeclaration = anotherClassDeclId,
//            name = "subclass",
//            description = "AnotherClass field"
//        )

        // Validation
        //val schemaHierarchy = loadSchemaHierarchy(rootSchemaRefId) // e.g. a function that loads children recursively
        /*
          Expected structure:
          - MyClassRoot
              -> name        (str)
              -> subclass    (AnotherClass)
        */
//        assertEquals(2, schemaHierarchy.children.size)
//        assertTrue(schemaHierarchy.children.any { it.name == "name" })
//        assertTrue(schemaHierarchy.children.any { it.name == "subclass" })
//    }

}