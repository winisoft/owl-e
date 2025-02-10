package com.wini.owle.repo.transforms

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.transformationDao
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

open class TransformationRepositoryTest : UnconstrainedDeleteRepoTest<Long, TransformationDao>() {
    private val realRepo by KoinJavaComponent.inject<TransformationRepository>(TransformationRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<TransformationDao>): List<TransformationDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    name = "Updated Transformation Name 1"
                }
                get(1).apply {
                    name = "Updated Transformation Name 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: TransformationDao): TransformationDao {
        return transaction {
            entity.apply {
                name = "Updated Transformation Name"
            }
        }
    }

    override val implicatedTables = listOf(Transformations, TypeDeclarations, TypeDefinitions, VersionSpecs)

    override val testModule: Module
        get() = module {
            single<TransformationRepository> { TransformationRepository() }
        }

    override fun getInstance(): TransformationDao {
        return transformationDao()
    }

    override fun getList(): List<TransformationDao> {
        return listOf(transformationDao(), transformationDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, Transformations)
    }


}