package com.wini.owle.service

import com.wini.owle.dto.BaseDto
import com.wini.owle.services.Service
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest
import org.junit.jupiter.api.Tag
import com.wini.owle.TestHelper


@Tag("integration")
abstract class GenericServiceIntegrationTest<ID: Any, T: BaseDto>: KoinTest {
    abstract val testModule: Module
    abstract fun getService(): Service<ID, T>
    abstract fun getInstance(): T

    @BeforeEach
    fun setUp() {
        stopKoin()
        startKoin { modules(testModule) }
        TestHelper.resetAll()
    }

//    @Test
//    @DisplayName("Delete")
//    fun `test delete successfully removes the targeted record`() {
//        transaction {
//            val created = getInstance()
//            val id = created.id
//            val repo = getRepo()
//
//            repo.delete(id)
//
//            val fetched = repo.getById(id)
//            assertNull(fetched, "Entity should no longer exist after deletion")
//        }
//    }
}
