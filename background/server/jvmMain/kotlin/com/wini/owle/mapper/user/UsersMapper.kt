package com.wini.owle.mapper.user

import com.wini.owle.dto.UserDto
import com.wini.owle.entity.PaymentMethods
import com.wini.owle.entity.SubscriptionStatus
import com.wini.owle.entity.SubscriptionStatus.Companion.toDto
import com.wini.owle.entity.Users
import com.wini.owle.entity.user.PaymentMethodDao
import com.wini.owle.entity.user.UserDao
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import org.jetbrains.exposed.dao.id.EntityID


class UsersMapper: DbMapper<UserDto, UserDao>() {

    override val applyProps: UserDao.(UserDto) -> Unit = { dto ->
        email = dto.email
        password = dto.password
        resetToken = dto.resetToken
        oauthProvider = dto.oauthProvider
        oauthId = dto.oauthId
        subscriptionStatus = SubscriptionStatus.fromDto(dto.subscriptionStatus)
        apiKey = dto.apiKey
        createdAt = LocalDateTime.parse(dto.createdAt)
        updatedAt = LocalDateTime.parse(dto.updatedAt)
    }

    override fun UserDto.toEntity(): UserDao {
        return if (id == 0L) {
            UserDao.new { applyProps(this@toEntity) }
        } else {
            UserDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("User with id=$id not found")
        }
    }

    override fun UserDao.toDto(): UserDto {
        return UserDto(
            id = id.value,
            email = email,
            password = password,
            resetToken = resetToken,
            oauthProvider = oauthProvider,
            oauthId = oauthId,
            subscriptionStatus = subscriptionStatus.toDto(),
            apiKey = apiKey,
            createdAt = DateTimeConverter.toString(createdAt),
            updatedAt = DateTimeConverter.toString(updatedAt),
            paymentMethods = emptyList()
        )
    }
}