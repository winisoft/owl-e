package com.wini.owle.repo

import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest
import org.junit.jupiter.api.Tag
import com.wini.owle.TestHelper


@Tag("integration")
abstract class DbLayerRepoIntegrationTests<ID: Any, T: Entity<ID>>: KoinTest {
    abstract val testModule: Module
    abstract fun getRepo(): OwleRepo<ID, T>
    abstract fun getInstance(): T

    @BeforeEach
    fun setUp() {
        stopKoin()
        startKoin { modules(testModule) }
        TestHelper.resetAll()
    }

    @Test
    @DisplayName("Delete")
    fun `test delete successfully removes the targeted record`() {
        transaction {
            val created = getInstance()
            val id = created.id
            val repo = getRepo()

            repo.delete(id)

            val fetched = repo.getById(id)
            assertNull(fetched, "Entity should no longer exist after deletion")
        }
    }
}
