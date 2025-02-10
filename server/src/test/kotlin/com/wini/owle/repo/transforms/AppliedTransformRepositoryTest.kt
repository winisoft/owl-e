package com.wini.owle.repo.transforms

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.appliedTransformDao
import com.wini.owle.entity.transforms.AppliedTransformDao
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

open class AppliedTransformRepositoryTest : UnconstrainedDeleteRepoTest<Long, AppliedTransformDao>() {
    private val realRepo by KoinJavaComponent.inject<AppliedTransformRepository>(AppliedTransformRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<AppliedTransformDao>): List<AppliedTransformDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    occurrence = TransformOccurrence.REVISION
                }
                get(1).apply {
                    occurrence = TransformOccurrence.PROVISION
                }
            }
        }
    }

    override fun modifyEntity(entity: AppliedTransformDao): AppliedTransformDao {
        return transaction {
            entity.apply {
                occurrence = TransformOccurrence.REVISION
            }
        }
    }

    override val implicatedTables = listOf(
        AppliedTransforms,
        Transformations,
        TypeDeclarations,
        TypeDefinitions,
        VersionSpecs,
        DataPoints,
        Workflows,
        Steps,
        Tools
    )

    override val testModule: Module
        get() = module {
            single<AppliedTransformRepository> { AppliedTransformRepository() }
        }

    override fun getInstance(): AppliedTransformDao {
        return appliedTransformDao()
    }

    override fun getList(): List<AppliedTransformDao> {
        return listOf(appliedTransformDao(), appliedTransformDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, AppliedTransforms)
    }


}