package com.wini.owle.repo.user

import com.wini.owle.entity.user.PaymentMethodEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * Represents a payment method for a user, storing details from Stripe
 * like the card brand, last four digits, and whether it's the default payment method.
 */
abstract class PaymentMethodRepoTest : ULongIdGenericRepoTest<PaymentMethodEntity>() {

    override fun mockEntity(): PaymentMethodEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): PaymentMethodEntity {
        return any<PaymentMethodEntity>()
    }


}

