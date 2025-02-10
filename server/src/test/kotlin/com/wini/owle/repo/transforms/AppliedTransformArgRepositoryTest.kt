package com.wini.owle.repo.transforms

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.transforms.AppliedTransformArgDao
import com.wini.owle.entity.transforms.AppliedTransformDao
import com.wini.owle.entity.transforms.TransformationArgDao
import com.wini.owle.entity.transforms.TransformationDao
import com.wini.owle.repo.BaseOwleRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import kotlin.test.assertNotNull

import com.wini.owle.repo.UnconstrainedDeleteRepoTest

open class AppliedTransformArgRepositoryTest : UnconstrainedDeleteRepoTest<Long, AppliedTransformArgDao>() {
    private val realRepo by KoinJavaComponent.inject<AppliedTransformArgRepository>(AppliedTransformArgRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<AppliedTransformArgDao>): List<AppliedTransformArgDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    value = "Updated Arg Value 1"
                }
                get(1).apply {
                    value = "Updated Arg Value 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: AppliedTransformArgDao): AppliedTransformArgDao {
        return transaction {
            entity.apply {
                value = "Updated Arg Value"
            }
        }
    }

    override val implicatedTables = listOf(
        AppliedTransformArgs,
        AppliedTransforms,
        Transformations,
        TypeDeclarations,
        TypeDefinitions,
        VersionSpecs,
        DataPoints,
        Workflows,
        Steps,
        Tools,
        TransformationArgs
    )

    override val testModule: Module
        get() = module {
            single<AppliedTransformArgRepository> { AppliedTransformArgRepository() }
        }

    override fun getInstance(): AppliedTransformArgDao {
        val typeDefinition = EntityTestInstanceFactory.typeDefinitionDao()
        val resultTypeDef = EntityTestInstanceFactory.typeDefinitionDao()
        val resultType = typeDeclarationDao(typeDefinition = resultTypeDef)
        val transformationReturnsType = typeDeclarationDao(typeDefinition = typeDefinition)
        val transformation = TransformationDao.new {
            this.name = "Example Transformation"
            this.description = "Transforms data"
            this.examples = "Input -> Output"
            this.returnsTypeDeclaration = transformationReturnsType
        }
        val appliedTransform = AppliedTransformDao.new {
            this.transformation = transformation
            this.occurrence = TransformOccurrence.PREREQINJECTION
            this.resultType = resultType
            this.dataPoint = dataPoint ?: EntityTestInstanceFactory.dataPointDao()
        }
        val transformArg = TransformationArgDao.new {
            this.transformation = transformation
            this.typeDeclaration = transformationReturnsType
            this.name = "Argument Name"
            this.description = "Argument Description"
        }

        val appliedTransformArg = AppliedTransformArgDao.new {
            this.appliedTransform = appliedTransform
            this.transformationArg = transformArg
            this.value = "exampleArgumentValue"
        }

        return appliedTransformArg
    }

    override fun getList(): List<AppliedTransformArgDao> {
        return listOf(getInstance(), getInstance())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, AppliedTransformArgs)
    }


}