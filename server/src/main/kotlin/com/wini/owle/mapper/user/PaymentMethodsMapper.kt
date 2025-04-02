package com.wini.owle.mapper.user

import com.wini.owle.dto.PaymentMethodDto
import com.wini.owle.entity.CardBrand
import com.wini.owle.entity.CardBrand.Companion.toDto
import com.wini.owle.entity.PaymentMethods
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.user.PaymentMethodDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import org.jetbrains.exposed.dao.id.EntityID


class PaymentMethodsMapper(
    private val daoFactory: DaoFactory<PaymentMethodDao, Long>,
    private val userMapper: UsersMapper
): DbMapper<PaymentMethodDto, PaymentMethodDao>() {


    override fun toEntity(dto: PaymentMethodDto): PaymentMethodDao {
        val applyProps: PaymentMethodDao.() -> Unit = {
            stripePaymentMethodId = dto.stripePaymentMethodId
            user = userMapper.toEntity(dto.user)
            cardBrand = dto.cardBrand?.let { CardBrand.fromDto(it) }
            lastFour = dto.lastFour
            isDefault = dto.isDefault
            createdAt = LocalDateTime.parse(dto.createdAt)
            updatedAt = LocalDateTime.parse(dto.updatedAt)
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
            cardBrand = entity.cardBrand?.toDto(),
            lastFour = entity.lastFour,
            isDefault = entity.isDefault,
            createdAt = DateTimeConverter.toString(entity.createdAt),
            updatedAt = DateTimeConverter.toString(entity.updatedAt)
        )
    }
}