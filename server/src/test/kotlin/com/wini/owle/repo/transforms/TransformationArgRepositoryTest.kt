package com.wini.owle.repo.transforms

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.transformationArgDao
import com.wini.owle.entity.transforms.TransformationArgDao
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


open class TransformationArgRepositoryTest : DbLayerIntegrationTests<Long, TransformationArgDao>() {

    private val realRepo by KoinJavaComponent.inject<TransformationArgRepository>(TransformationArgRepository::class.java)

    override fun getRepo() = realRepo

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

}