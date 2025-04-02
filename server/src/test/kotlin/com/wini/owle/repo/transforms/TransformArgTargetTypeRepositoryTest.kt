package com.wini.owle.repo.transforms

import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.entity.transforms.TransformationDao
import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.entity.typing.TypeDefinitionDao
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

open class TransformArgTargetTypeRepositoryTest {

//    override val implicatedTables: List<IdTable<*>>
//        get() = listOf(VersionSpecs, Transformations, TypeDeclarations, TypeDefinitions)
//
//    override val testModule: Module
//        get() = module {
//
//        }
//
//    override fun getInstance(): TransformTargetTypeDao {
//        return transformTargetTypeDao()
//    }
//
//    override fun getList(): List<TransformTargetTypeDao> {
//        return listOf(transformTargetTypeDao(), transformTargetTypeDao())
//    }
//
//    override fun makeEntityID(id: CompositeID): EntityID<CompositeID> {
//        return EntityID(id, TransformTargetTypes)
//    }
//
//    override fun getRepo(): OwleRepo<CompositeID, TransformTargetTypeDao> {
//        return TransformTargetTypeRepository()
//    }
//
//    override fun modifyEntities(entities: List<TransformTargetTypeDao>): List<TransformTargetTypeDao> {
//        return transaction {
//            entities.apply {
//                get(0).apply {
//                    this.transformation = transformationDao()
//                }
//                get(1).apply {
//                    this.transformation = transformationDao()
//                }
//            }
//        }
//    }
//
//    override fun modifyEntity(entity: TransformTargetTypeDao): TransformTargetTypeDao {
//        return transaction {
//            entity.apply {
//                this.transformation = transformationDao()
//            }
//        }
//    }

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