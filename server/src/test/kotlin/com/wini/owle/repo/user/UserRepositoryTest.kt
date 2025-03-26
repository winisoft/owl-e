package com.wini.owle.repo.user

import com.wini.owle.entity.DbLayerIntegrationTests
import com.wini.owle.entity.EntityTestInstanceFactory
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.Users
import com.wini.owle.entity.user.UserDao
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


open class UserRepositoryTest : DbLayerIntegrationTests<Long, UserDao>() {

    private val realRepo by KoinJavaComponent.inject<UserRepository>(UserRepository::class.java)

    override fun getRepo() = realRepo

    override val implicatedTables = listOf(Users)

    override val testModule: Module
        get() = module {
            single<UserRepository> { UserRepository() }
        }

    override fun getInstance(): UserDao {
        return userDao()
    }

}