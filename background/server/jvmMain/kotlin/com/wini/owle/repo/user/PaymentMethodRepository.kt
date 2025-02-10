package com.wini.owle.repo.user

import com.wini.owle.entity.Users
import com.wini.owle.entity.user.PaymentMethodDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction

class PaymentMethodRepository : OwleLongIdRepoImpl<PaymentMethodDao>() {

    override val entityClass: LongEntityClass<PaymentMethodDao>
        get() = PaymentMethodDao

    fun getPaymentMethodsOfUser(userId: Long): List<PaymentMethodDao> {
        return getWhere {
            it.user.id == EntityID(userId, Users)
        }
    }

    override fun getWhere(predicate: (PaymentMethodDao) -> Boolean): List<PaymentMethodDao> {
        return transaction {
            PaymentMethodDao.all().filter(predicate)
        }
    }
}