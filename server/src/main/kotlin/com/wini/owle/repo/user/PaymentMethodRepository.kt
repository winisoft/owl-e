package com.wini.owle.repo.user

import com.wini.owle.entity.Users
import com.wini.owle.entity.user.PaymentMethodEntity
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.id.EntityID

class PaymentMethodRepository: OwleRepoImpl<Long, PaymentMethodEntity>(PaymentMethodEntity) {

    fun getPaymentMethodsOfUser(userId: Long): List<PaymentMethodEntity> {
        return getWhere {
            it.userEntity.id == EntityID(userId, Users)
        }
    }

}