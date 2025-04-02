package com.wini.owle.dto.user

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class PaymentMethodDto(
    val id: Long,
    val stripePaymentMethodId: String?,
    val user: UserDto,
    val cardBrand: CardBrandDto?,
    val lastFour: String?,
    val isDefault: Boolean,
    val createdAt: String,
    val updatedAt: String
) : BaseDto