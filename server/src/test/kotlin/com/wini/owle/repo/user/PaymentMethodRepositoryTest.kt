package com.wini.owle.repo.user

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.paymentMethodDao
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.user.PaymentMethodDao
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import kotlin.test.assertNotNull

import com.wini.owle.repo.UnconstrainedDeleteRepoTest


open class PaymentMethodRepositoryTest : DbLayerIntegrationTests<Long, PaymentMethodDao>() {

    private val realRepo by KoinJavaComponent.inject<PaymentMethodRepository>(PaymentMethodRepository::class.java)

    override fun getRepo() = realRepo

    override val implicatedTables = listOf(PaymentMethods, Users)

    override val testModule: Module
        get() = module {
            single<PaymentMethodRepository> { PaymentMethodRepository() }
        }

    override fun getInstance(): PaymentMethodDao {
        val user = userDao()
        return paymentMethodDao(user = user)
    }

    @Test
    fun `test PaymentMethods can be retrieved by associated User`() {
        transaction {
            val user = userDao()
            val paymentMethod = paymentMethodDao(user = user, lastFour = "0921")
            val userId = user.id.value

            val repo = getRepo()
            repo.create(paymentMethod)

            val fetched = getRepo().getPaymentMethodsOfUser(userId = userId)
            assertEquals(1, fetched.count(), "Expected to retrieve 1 inserted PaymentMethod matching User")
            assertEquals("0921", fetched.first().lastFour, "Expected retrieve PaymentMethod matching User to have values consistent with inserted")
        }
    }
}