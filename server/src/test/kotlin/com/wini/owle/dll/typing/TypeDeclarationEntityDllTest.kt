package com.wini.owle.dll.typing

import com.wini.owle.async.AsyncResult
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeParameterDao
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.TypeDefinitions
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.mapper.mapperModule
import com.wini.owle.repo.repositoryModule
import com.wini.owle.services.serviceModule
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals
import kotlin.test.assertTrue


open class TypeDeclarationEntityDllTest : KoinTest {

    private val dll: TypeDeclarationDll by inject()
    val implicatedTables = listOf(TypeDeclarations, TypeDefinitions, VersionSpecs)

    @BeforeEach
    fun setUp() {

        stopKoin()
        startKoin {
            modules(
                serviceModule,         // from main
                repositoryModule,      // from main
                mapperModule,          // from main
                //dllModule,             // from main, if you need it
                module {               // The purely test-tailored bits
                    single<TypeDeclarationDll> { TypeDeclarationDll(get(), get(), get()) }
                }
            )
        }
    }

    /**
     * Representing a Parameterized Type With Nested Generics
     * Example Scenario: Dict[str, List\[int\]].
     *
     * Type Definitions:
     *      Dict in typing (or collections in older Python).
     *      str in builtins.
     *      List in builtins (or typing).
     *      int in builtins.
     * Type Declarations
     *      A TypeDeclarationEntity for Dict.
     *      A TypeDeclarationEntity for str.
     *      A TypeDeclarationEntity for List.
     *      A TypeDeclarationEntity for int.
     * Parameterize
     *      Create TypeParameterEntity records linking the Dict declaration to the str declaration (parameter at position 0).
     *      Create a second TypeParameterEntity linking the Dict declaration to the List declaration (parameter at position 1).
     *      Create a TypeParameterEntity for the List declaration referencing the int declaration (position 0).
     * Validation
     *      Use a custom function (or direct repository queries) to reconstruct the final type representation as Dict[str, List[int]].
     *      Check that the parameter positions are correct.
     *      Confirm no extraneous records exist, etc.
     */
    @Test
    @Disabled
    fun `test integrated creation of Dict with key 'str' and value List of int`() {
        /**
         * Creates the chain of TypeDefinitionEntities and TypeDeclarationEntities
         * needed to represent Dict[String, List[Int]] and verifies they reconstruct properly.
         */
        transaction {
            // 1. Create Type Definitions
            val dictDefId = typeDefinitionDao(name = "Dict", namespace = "typing")
            val strDefId  = typeDefinitionDao(name = "str",  namespace = "builtins")
            val listDefId = typeDefinitionDao(name = "List", namespace = "typing")
            val intDefId  = typeDefinitionDao(name = "int",  namespace = "builtins")

            // 2. Create Type Declarations
            val dictDeclId = typeDeclarationDao(typeDefinitionEntity = dictDefId)
            val strDeclId  = typeDeclarationDao(typeDefinitionEntity = strDefId)
            val listDeclId = typeDeclarationDao(typeDefinitionEntity = listDefId)
            val intDeclId  = typeDeclarationDao(typeDefinitionEntity = intDefId)

            // 3. Parameterize: Dict[...] has two type parameters
            typeParameterDao(typeDeclarationEntity = dictDeclId, parameterTypeDeclarationEntity = strDeclId,  position = 0)
            typeParameterDao(typeDeclarationEntity = dictDeclId, parameterTypeDeclarationEntity = listDeclId, position = 1)

            // Parameterize: List[...] has one type parameter
            typeParameterDao(typeDeclarationEntity = listDeclId, parameterTypeDeclarationEntity = intDeclId,  position = 0)

            // 4. Validation
            val reconstructedStringResult = dll.reconstructType(dictDeclId.id.value)
            assertTrue(reconstructedStringResult is AsyncResult.Success, "Failed to reconstruct type label")

            val reconstructedString = reconstructedStringResult.data
            assertEquals("Dict[str, List[int]]", reconstructedString)
        }
    }

}
