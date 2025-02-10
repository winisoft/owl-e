package com.wini.owle.repo

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

abstract class BaseOwleRepoTest<ID: Any, T: Entity<ID>>: OwleRepoTest<ID, T> {

    @BeforeEach
    fun setUp() {
        stopKoin()
        startKoin { modules(testModule) }
        Database.connect(
            url = "jdbc:mysql://localhost:3306/owle_test_db",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "test_runner",
            password = "test1234"
        )
        transaction {
            exec("SET FOREIGN_KEY_CHECKS=0;")
            SchemaUtils.drop(*implicatedTables.toTypedArray())
            exec("SET FOREIGN_KEY_CHECKS=1;")
            SchemaUtils.create(*implicatedTables.toTypedArray())
        }
    }

    @Test
    @DisplayName("Get All")
    fun `test get all`() {
        transaction {
            val created = getList()
            val fetched = getRepo().getAll()

            assertNotNull(fetched, "Entities should exist")
            assertEquals(2, fetched.count(), "Two entities should exist")
            assertEquals(created[0], fetched[0], "Fetched entity should match created one by field values")
            assertEquals(created[1], fetched[1], "Fetched entity should match created one by field values")
        }
    }

    @Test
    @DisplayName("Get By Id Multiple")
    fun `test get by id multiple`() {
        transaction {
            val created = getList()
            val repo = getRepo()
            val idList = created.map { it.id }
            val fetched = repo.getByIds(idList)

            assertNotNull(fetched, "Entities should exist")
            assertEquals(2, fetched.count(), "Two entities should exist")
            assertNotNull(fetched[0], "Entity 1 should exist")
            assertNotNull(fetched[1], "Entity 2 should exist")
            assertEquals(created[0], fetched[0], "Fetched entity should match created one by field values")
            assertEquals(created[1], fetched[1], "Fetched entity should match created one by field values")
        }
    }

    @Test
    @DisplayName("Create")
    fun `test create entity is committed and recalled by PK`() {
        transaction {
            val created = getInstance()
            val fetched = getRepo().getById(created.id)

            assertNotNull(fetched, "Entity should be retrievable after creation")
            Assertions.assertEquals(created, fetched, "Fetched entity should match created one by field values")
        }
    }

    @Test
    @DisplayName("Create Multiple")
    fun `test create multiple entities committed and recalled by PK`() {
        transaction {
            val list = getList()
            val repo = getRepo()

            val id1 = list[0].id.value
            val id2 = list[1].id.value

            repo.create(list)

            val fetched1 = repo.getById(makeEntityID(id1))
            val fetched2 = repo.getById(makeEntityID(id2))

            assertNotNull(fetched1, "Entity with id $id1 should have been created")
            assertNotNull(fetched2, "Entity with id $id2 should have been created")
            assertEquals(list[0], fetched1, "Entity with id $id1 should have values consistent with created entity")
            assertEquals(list[1], fetched2, "Entity with id $id2 should have values consistent with created entity")
        }
    }

    @Test
    @DisplayName("Update")
    fun `test entity update is persisted`() {
        transaction {
            val created = getInstance()
            val updatedInstance = modifyEntity(created)
            getRepo().update(updatedInstance)

            val fetched = getRepo().getById(created.id)
            assertEquals(
                updatedInstance, fetched, "Fetched entity should reflect updates to it"
            )
        }
    }

    @Test
    @DisplayName("Update Multiple")
    fun `test update multiple entities are persisted`() {
        transaction {
            val list = getList()
            val repo = getRepo()

            val id1 = list[0].id.value
            val id2 = list[1].id.value

            val updatedList = modifyEntities(list)
            val updated1 = updatedList[0]
            val updated2 = updatedList[1]
            repo.update(updatedList)

            val fetched1 = repo.getById(makeEntityID(id1))
            val fetched2 = repo.getById(makeEntityID(id2))
            assertEquals(
                updated1, fetched1, "Fetched entity should reflect updates to it"
            )
            assertEquals(
                updated2, fetched2, "Fetched entity should reflect updates to it"
            )
        }
    }


}