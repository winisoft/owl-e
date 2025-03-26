package com.wini.owle.repo.workflows

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.toolDao
import com.wini.owle.entity.workflows.ToolDao
import com.wini.owle.repo.OwleRepo
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
import org.jetbrains.exposed.dao.id.IdTable
import org.koin.core.module.Module

open class ToolRepositoryTest : DbLayerIntegrationTests<Long, ToolDao>() {

    private val realRepo by KoinJavaComponent.inject<ToolRepository>(ToolRepository::class.java)
    override val implicatedTables: List<IdTable<*>>
        get() = TODO("Not yet implemented")
    override val testModule: Module
        get() = TODO("Not yet implemented")

    override fun getRepo(): OwleRepo<Long, ToolDao> {
        TODO("Not yet implemented")
    }

    override fun getInstance(): ToolDao {
        TODO("Not yet implemented")
    }

}