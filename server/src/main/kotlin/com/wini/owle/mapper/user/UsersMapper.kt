package com.wini.owle.mapper.user

import com.wini.owle.dto.user.User
import com.wini.owle.dto.user.enums.UserStatus
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper


class UsersMapper(
    private val daoDelegate: DaoDelegate<ULong, User, UserEntity>
): DbMapper<ULong, User, UserEntity> {

    private val dateTimeConverter: DateTimeConverter = DateTimeConverter

    override fun toEntity(dto: User): UserEntity {
        return daoDelegate.findOrCreate(dto) {
            email = dto.email
            password = dto.password
            resetToken = dto.resetToken
            oauthProvider = dto.oauthProvider
            oauthId = dto.oauthId
            subscriptionStatus = dto.userStatus
            apiKey = dto.apiKey
            createdAt = dateTimeConverter.fromString(dto.createdAt)
            updatedAt = dateTimeConverter.fromString(dto.updatedAt)
        }
    }

    override fun toDto(entity: UserEntity): User {
        return User(
            id = entity.id.value,
            email = entity.email,
            password = entity.password, // Be cautious about exposing password hashes
            resetToken = entity.resetToken,
            oauthProvider = entity.oauthProvider,
            oauthId = entity.oauthId,
            userStatus = UserStatus.ACTIVE,
            apiKey = entity.apiKey,
            createdAt = dateTimeConverter.toString(entity.createdAt),
            updatedAt = dateTimeConverter.toString(entity.updatedAt),
            paymentMethods = emptyList() // Keeping original behavior
        )
    }
}