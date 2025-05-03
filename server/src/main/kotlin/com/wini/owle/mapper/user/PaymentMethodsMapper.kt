package com.wini.owle.mapper.user

import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.dto.user.enums.CardBrand
import com.wini.owle.entity.user.PaymentMethodEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.user.enums.CardBrandMapper


class PaymentMethodsMapper(
    private val daoFactory: DaoFactory<PaymentMethodEntity, Long>,
    private val userMapper: UsersMapper,
    private val cardBrandMapper: CardBrandMapper,
    private val dateTimeConverter: DateTimeConverter
): DbMapper<PaymentMethod, PaymentMethodEntity>() {


    override fun toEntity(dto: PaymentMethod): PaymentMethodEntity {
        val applyProps: PaymentMethodEntity.() -> Unit = {
            stripePaymentMethodId = dto.stripePaymentMethodId
            userEntity = userMapper.toEntity(dto.user)
            //cardBrand = dto.cardBrand?.let { cardBrandMapper. }
            lastFour = dto.lastFour
            isDefault = dto.isDefault
            createdAt = dateTimeConverter.fromString(dto.createdAt)
            updatedAt = dateTimeConverter.fromString(dto.updatedAt)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            PaymentMethodEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("PaymentMethod with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: PaymentMethodEntity): PaymentMethod {
        return PaymentMethod(
            id = entity.id.value,
            stripePaymentMethodId = entity.stripePaymentMethodId,
            user = userMapper.toDto(entity.userEntity),
            cardBrand = null, //entity.cardBrand?.let { return PaymentMethod. },
            lastFour = entity.lastFour,
            isDefault = entity.isDefault,
            createdAt = dateTimeConverter.toString(entity.createdAt),
            updatedAt = dateTimeConverter.toString(entity.updatedAt)
        )
    }
}