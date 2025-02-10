package com.wini.owle.repo.transforms

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.transformationArgDao
import com.wini.owle.entity.transforms.TransformationArgDao
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

open class TransformationArgRepositoryTest : UnconstrainedDeleteRepoTest<Long, TransformationArgDao>() {
    private val realRepo by KoinJavaComponent.inject<TransformationArgRepository>(TransformationArgRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<TransformationArgDao>): List<TransformationArgDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    name = "Updated Transformation Arg Name 1"
                }
                get(1).apply {
                    name = "Updated Transformation Arg Name 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: TransformationArgDao): TransformationArgDao {
        return transaction {
            entity.apply {
                name = "Updated Transformation Arg Name"
            }
        }
    }

    override val implicatedTables = listOf(
        TransformationArgs,
        Transformations,
        TypeDeclarations,
        TypeDefinitions,
        VersionSpecs
    )

    override val testModule: Module
        get() = module {
            single<TransformationArgRepository> { TransformationArgRepository() }
        }

    override fun getInstance(): TransformationArgDao {
        return transformationArgDao()
    }

    override fun getList(): List<TransformationArgDao> {
        return listOf(transformationArgDao(), transformationArgDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, TransformationArgs)
    }


}