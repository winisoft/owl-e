package com.wini.owle.dto.user

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Long,
    val email: String,
    val password: String,
    val resetToken: String? = null,
    val oauthProvider: String? = null,
    val oauthId: String? = null,
    val subscriptionStatus: SubscriptionStatusDto,
    val apiKey: String? = null,
    val createdAt: String,
    val updatedAt: String,
    val paymentMethods: List<PaymentMethodDto> = emptyList()
) : BaseDto