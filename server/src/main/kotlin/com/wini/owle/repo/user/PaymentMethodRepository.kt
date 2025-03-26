package com.wini.owle.repo.user

import com.wini.owle.entity.Users
import com.wini.owle.entity.user.PaymentMethodDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction

class PaymentMethodRepository: OwleRepoImpl<Long, PaymentMethodDao>(PaymentMethodDao) {

    fun getPaymentMethodsOfUser(userId: Long): List<PaymentMethodDao> {
        return getWhere {
            it.user.id == EntityID(userId, Users)
        }
    }

}