package com.wini.owle.repo.user

import com.wini.owle.entity.CardBrand
import com.wini.owle.entity.EntityTestInstanceFactory
import com.wini.owle.entity.EntityTestInstanceFactory.paymentMethodDao
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.PaymentMethods
import com.wini.owle.entity.Users
import com.wini.owle.entity.user.PaymentMethodDao
import com.wini.owle.repo.BaseOwleRepoTest
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

open class PaymentMethodRepositoryTest : UnconstrainedDeleteRepoTest<Long, PaymentMethodDao>() {
    private val realRepo by KoinJavaComponent.inject<PaymentMethodRepository>(PaymentMethodRepository::class.java)
    override fun getRepo() = realRepo
    override fun modifyEntities(entities: List<PaymentMethodDao>): List<PaymentMethodDao> {
        return transaction {
            entities.apply {
                get(0).apply {
                    cardBrand = CardBrand.EFTPOS_AUSTRALIA
                }
                get(1).apply {
                    cardBrand = CardBrand.DINERS_CLUB
                }
            }
        }
    }

    override fun modifyEntity(entity: PaymentMethodDao): PaymentMethodDao {
        return transaction {
            entity.apply {
                cardBrand = CardBrand.EFTPOS_AUSTRALIA
            }
        }
    }

    override val implicatedTables = listOf(PaymentMethods, Users)

    override val testModule: Module
        get() = module {
            single<PaymentMethodRepository> { PaymentMethodRepository() }
        }

    override fun getInstance(): PaymentMethodDao {
        val user = userDao()
        return paymentMethodDao(user = user)
    }

    override fun getList(): List<PaymentMethodDao> {
        val user1 = userDao(email = "jim@jim.com")
        val user2 = userDao(email = "jon@jon.com")
        return listOf(paymentMethodDao(user = user1), paymentMethodDao(user = user2))
    }

    override fun makeEntityID(id: Long): EntityID<Long> {
        return EntityID(id, PaymentMethods)
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