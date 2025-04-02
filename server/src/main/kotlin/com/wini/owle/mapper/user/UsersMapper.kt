package com.wini.owle.mapper.user

import com.wini.owle.dto.UserDto
import com.wini.owle.entity.PaymentMethods
import com.wini.owle.entity.SubscriptionStatus
import com.wini.owle.entity.SubscriptionStatus.Companion.toDto
import com.wini.owle.entity.Users
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.user.PaymentMethodDao
import com.wini.owle.entity.user.UserDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import kotlinx.datetime.LocalDateTime // Assuming kotlinx-datetime is used
import org.jetbrains.exposed.dao.id.EntityID


class UsersMapper(
    private val daoFactory: DaoFactory<UserDao, Long>
): DbMapper<UserDto, UserDao>() {

    override fun toEntity(dto: UserDto): UserDao {
        val applyProps: UserDao.() -> Unit = {
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
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else {
            UserDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("User with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: UserDao): UserDto {
        return UserDto(
            id = entity.id.value,
            email = entity.email,
            password = entity.password, // Be cautious about exposing password hashes
            resetToken = entity.resetToken,
            oauthProvider = entity.oauthProvider,
            oauthId = entity.oauthId,
            subscriptionStatus = entity.subscriptionStatus.toDto(),
            apiKey = entity.apiKey,
            createdAt = DateTimeConverter.toString(entity.createdAt),
            updatedAt = DateTimeConverter.toString(entity.updatedAt),
            paymentMethods = emptyList() // Keeping original behavior
        )
    }
}