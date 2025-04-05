package com.wini.owle.mapper.user

import com.wini.owle.dto.user.UserDto
import com.wini.owle.entity.user.UserDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.user.enums.SubscriptionStatusMapper


class UsersMapper(
    private val daoFactory: DaoFactory<UserDao, Long>,
    private val subscriptionStatusMapper: SubscriptionStatusMapper,
    private val dateTimeConverter: DateTimeConverter
): DbMapper<UserDto, UserDao>() {

    override fun toEntity(dto: UserDto): UserDao {
        val applyProps: UserDao.() -> Unit = {
            email = dto.email
            password = dto.password
            resetToken = dto.resetToken
            oauthProvider = dto.oauthProvider
            oauthId = dto.oauthId
            subscriptionStatus = subscriptionStatusMapper.toEntity(dto.subscriptionStatus)
            apiKey = dto.apiKey
            createdAt = dateTimeConverter.fromString(dto.createdAt)
            updatedAt = dateTimeConverter.fromString(dto.updatedAt)
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
            subscriptionStatus = subscriptionStatusMapper.toDto(entity.subscriptionStatus),
            apiKey = entity.apiKey,
            createdAt = dateTimeConverter.toString(entity.createdAt),
            updatedAt = dateTimeConverter.toString(entity.updatedAt),
            paymentMethods = emptyList() // Keeping original behavior
        )
    }
}