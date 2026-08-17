package com.wini.owle.mapper.user

import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.entity.user.PaymentMethodEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper


class PaymentMethodsMapper(
    private val daoDelegate: DaoDelegate<ULong, PaymentMethod, PaymentMethodEntity>,
    private val userMapper: UsersMapper,
    private val dateTimeConverter: DateTimeConverter
): DbMapper<ULong, PaymentMethod, PaymentMethodEntity> {


    override fun toEntity(dto: PaymentMethod): PaymentMethodEntity {
        return daoDelegate.findOrCreate(dto) {
            stripePaymentMethodId = dto.stripePaymentMethodId
            user = userMapper.toEntity(dto.user)
            cardBrand = dto.cardBrand
            lastFour = dto.lastFour
            isDefault = dto.isDefault
            createdAt = dateTimeConverter.fromString(dto.createdAt)
            updatedAt = dateTimeConverter.fromString(dto.updatedAt)
        }
    }

    override fun toDto(entity: PaymentMethodEntity): PaymentMethod {
        return PaymentMethod(
            id = entity.id.value,
            stripePaymentMethodId = entity.stripePaymentMethodId,
            user = userMapper.toDto(entity.user),
            cardBrand = null, //entity.cardBrand?.let { return PaymentMethod. },
            lastFour = entity.lastFour,
            isDefault = entity.isDefault,
            createdAt = dateTimeConverter.toString(entity.createdAt),
            updatedAt = dateTimeConverter.toString(entity.updatedAt)
        )
    }
}