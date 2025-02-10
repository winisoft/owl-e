package com.wini.owle.repo

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


abstract class UnconstrainedDeleteRepoTest<ID: Any, T: Entity<ID>>: BaseOwleRepoTest<ID, T>() {

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

    @Test
    @DisplayName("Delete Multiple")
    fun `test delete multiple successfully removes the targeted records`() {
        transaction {
            val list = getList()
            val idList = list.map { it.id }
            val repo = getRepo()

            val id1 = idList[0]
            val id2 = idList[1]

            repo.delete(idList)

            val fetched1 = repo.getById(id1)
            val fetched2 = repo.getById(id2)
            assertNull(fetched1, "Entity with id ${id1.value} should no longer exist after deletion")
            assertNull(fetched2, "Entity with id ${id2.value} should no longer exist after deletion")
        }
    }
}