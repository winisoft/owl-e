package com.wini.owle.repo.runs

import com.wini.owle.entity.EntityTestInstanceFactory
import com.wini.owle.entity.EntityTestInstanceFactory.testCaseDao
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.TestCases
import com.wini.owle.entity.Users
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.runs.TestCaseDao
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent


open class TestCaseRepositoryTest : UnconstrainedDeleteRepoTest<Long, TestCaseDao>() {

    private val realRepo by KoinJavaComponent.inject<TestCaseRepository>(TestCaseRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<TestCaseDao>): List<TestCaseDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    description = "Updated test case description 1"
                }
                get(1).apply {
                    description = "Updated test case description 2"
                }
            }
        }
    }

    override fun modifyEntity(entity: TestCaseDao): TestCaseDao {
        return transaction {
            entity.apply {
                description = "Updated test case description"
            }
        }
    }

    override val implicatedTables = listOf(TestCases, Workflows, Users)

    override val testModule = module {
        single<TestCaseRepository> { TestCaseRepository() }
        single { EntityTestInstanceFactory }
    }

    override fun getInstance(): TestCaseDao {
        return testCaseDao()
    }

    override fun getList(): List<TestCaseDao> {
        val user1 = userDao(email = "jim@jim.com")
        val user2 = userDao(email = "jon@jon.com")
        return listOf(testCaseDao(user = user1), testCaseDao(user = user2))
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, TestCases)
    }


}