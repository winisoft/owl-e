package com.wini.owle.repo.user

import com.wini.owle.entity.EntityTestInstanceFactory
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.Users
import com.wini.owle.entity.user.UserDao
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

open class UserRepositoryTest : UnconstrainedDeleteRepoTest<Long, UserDao>() {
    private val realRepo by KoinJavaComponent.inject<UserRepository>(UserRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<UserDao>): List<UserDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    email = "updated_email1@example.com"
                }
                get(1).apply {
                    email = "updated_email2@example.com"
                }
            }
        }
    }

    override fun modifyEntity(entity: UserDao): UserDao {
        return transaction {
            entity.apply {
                email = "updated_email@example.com"
            }
        }
    }

    override val implicatedTables = listOf(Users)

    override val testModule: Module
        get() = module {
            single<UserRepository> { UserRepository() }
        }

    override fun getInstance(): UserDao {
        return userDao()
    }

    override fun getList(): List<UserDao> {
        return listOf(userDao(email = "jim@jim.com"), userDao(email = "jon@jon.com"))
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, Users)
    }


}