package com.wini.owle.mapper.user

import com.wini.owle.dto.PaymentMethodDto
import com.wini.owle.entity.CardBrand
import com.wini.owle.entity.CardBrand.Companion.toDto
import com.wini.owle.entity.PaymentMethods
import com.wini.owle.entity.user.PaymentMethodDao
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import org.jetbrains.exposed.dao.id.EntityID


class PaymentMethodsMapper(
    val userMapper: UsersMapper
) : DbMapper<PaymentMethodDto, PaymentMethodDao>() {

    override val applyProps: PaymentMethodDao.(PaymentMethodDto) -> Unit = { dto ->
        stripePaymentMethodId = dto.stripePaymentMethodId
        user = userMapper.run { dto.user.toEntity() }
        cardBrand = dto.cardBrand?.let { CardBrand.fromDto(it) }
        lastFour = dto.lastFour
        isDefault = dto.isDefault
        createdAt = LocalDateTime.parse(dto.createdAt)
        updatedAt = LocalDateTime.parse(dto.updatedAt)
    }

    override fun PaymentMethodDto.toEntity(): PaymentMethodDao {
        return if (id == 0L) {
            PaymentMethodDao.new { applyProps(this@toEntity) }
        } else {
            PaymentMethodDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("PaymentMethod with id=$id not found")
        }
    }

    override fun PaymentMethodDao.toDto(): PaymentMethodDto {
        return PaymentMethodDto(
            id = id.value,
            stripePaymentMethodId = stripePaymentMethodId,
            user = userMapper.run { user.toDto() },
            cardBrand = cardBrand?.toDto(),
            lastFour = lastFour,
            isDefault = isDefault,
            createdAt = DateTimeConverter.toString(createdAt),
            updatedAt = DateTimeConverter.toString(updatedAt)
        )
    }
}