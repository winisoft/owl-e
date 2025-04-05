package com.wini.owle.mapper.user

import com.wini.owle.dto.user.PaymentMethodDto
import com.wini.owle.entity.user.enums.CardBrand
import com.wini.owle.entity.user.PaymentMethodDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.user.enums.CardBrandMapper
import kotlinx.datetime.LocalDateTime


class PaymentMethodsMapper(
    private val daoFactory: DaoFactory<PaymentMethodDao, Long>,
    private val userMapper: UsersMapper,
    private val cardBrandMapper: CardBrandMapper,
    private val dateTimeConverter: DateTimeConverter
): DbMapper<PaymentMethodDto, PaymentMethodDao>() {


    override fun toEntity(dto: PaymentMethodDto): PaymentMethodDao {
        val applyProps: PaymentMethodDao.() -> Unit = {
            stripePaymentMethodId = dto.stripePaymentMethodId
            user = userMapper.toEntity(dto.user)
            cardBrand = dto.cardBrand?.let { cardBrandMapper.toEntity(it) }
            lastFour = dto.lastFour
            isDefault = dto.isDefault
            createdAt = dateTimeConverter.fromString(dto.createdAt)
            updatedAt = dateTimeConverter.fromString(dto.updatedAt)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            PaymentMethodDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("PaymentMethod with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: PaymentMethodDao): PaymentMethodDto {
        return PaymentMethodDto(
            id = entity.id.value,
            stripePaymentMethodId = entity.stripePaymentMethodId,
            user = userMapper.toDto(entity.user),
            cardBrand = entity.cardBrand?.let { cardBrandMapper.toDto(it) },
            lastFour = entity.lastFour,
            isDefault = entity.isDefault,
            createdAt = dateTimeConverter.toString(entity.createdAt),
            updatedAt = dateTimeConverter.toString(entity.updatedAt)
        )
    }
}