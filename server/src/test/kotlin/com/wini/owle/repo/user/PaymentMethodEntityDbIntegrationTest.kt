package com.wini.owle.repo.user

import com.wini.owle.TestHelper
import com.wini.owle.entity.EntityTestInstanceFactory.paymentMethodDao
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals


open class PaymentMethodEntityDbIntegrationTest {

    @BeforeEach
    fun setUp() {
        TestHelper.connectToTestDb()
        TestHelper.refreshTestDbData(TestHelper.ImplicatedTables.UserModule.PaymentMethod)
    }


    @Test
    fun `test PaymentMethods can be retrieved by associated User`() {
        transaction {
            val user = userDao()
            val paymentMethod = paymentMethodDao(userEntity = user, lastFour = "0921")
            val userId = user.id.value

            val repo = PaymentMethodRepository()
            repo.create(paymentMethod)

            val fetched = PaymentMethodRepository().getPaymentMethodsOfUser(userId = userId)
            assertEquals(1, fetched.count(), "Expected to retrieve 1 inserted PaymentMethod matching User")
            assertEquals("0921", fetched.first().lastFour, "Expected retrieve PaymentMethod matching User to have values consistent with inserted")
        }
    }
}