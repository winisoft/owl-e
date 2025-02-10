package com.wini.owle.repo.workflows

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.toolDao
import com.wini.owle.entity.workflows.ToolDao
import com.wini.owle.repo.BaseOwleRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import kotlin.test.assertNotNull

import com.wini.owle.repo.UnconstrainedDeleteRepoTest

open class ToolRepositoryTest : UnconstrainedDeleteRepoTest<Long, ToolDao>() {

    private val realRepo by KoinJavaComponent.inject<ToolRepository>(ToolRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<ToolDao>): List<ToolDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    body = "Updated Tool Body 1"
                }
                get(1).apply {
                    body = "Updated Tool Body 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: ToolDao): ToolDao {
        return transaction {
            entity.apply {
                body = "Updated Tool Body"
            }
        }
    }

    override val implicatedTables = listOf(Tools, TypeDeclarations, TypeDefinitions, VersionSpecs)

    override val testModule = module {
        single<ToolRepository> { ToolRepository() }
        single { EntityTestInstanceFactory }
    }

    override fun getInstance(): ToolDao {
        return toolDao()
    }

    override fun getList(): List<ToolDao> {
        return listOf(toolDao(), toolDao())
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, Tools)
    }


}