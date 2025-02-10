package com.wini.owle.mapper.user

import com.wini.owle.dto.UserDto
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.mapper.BaseDbMapper
import com.wini.owle.mapper.enum.DbEnumMapper
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime

/**
 * Sealed class for User mapping strategies, offering different levels of data enrichment and customization
 * for the UserEntity and UserDto.
 */
sealed class UserMapper : BaseDbMapper<UserDto, UserEntity>() {

    /**
     * Provides an undefined instance of the UserEntity, used as a base to populate fields during transformation.
     */
    override val undefinedEntity: () -> UserEntity = { UserEntity() }

    /**
     * Defines the minimum viable DTO, mapping basic and core properties from the UserEntity.
     */
    override val minViableDto: (UserEntity) -> UserDto = { entity ->
        UserDto(
            id = entity.id,
            email = entity.email,
            password = entity.password,
            resetToken = entity.resetToken,
            oauthProvider = entity.oauthProvider,
            oauthId = entity.oauthId,
            subscriptionStatus = DbEnumMapper.SubscriptionStatusMapper.toDto(entity.subscriptionStatus),
            apiKey = entity.apiKey,
            createdAt = entity.createdAt.toKotlinLocalDateTime(),
            updatedAt = entity.updatedAt.toKotlinLocalDateTime()
        )
    }

    /**
     * Defines the minimum viable Entity, mapping basic and core properties from the UserDto.
     */
    override val minViableEntity: UserEntity.(UserDto) -> UserEntity
        get() = { dto ->
            id = dto.id
            email = dto.email
            password = dto.password
            resetToken = dto.resetToken
            oauthProvider = dto.oauthProvider
            oauthId = dto.oauthId
            subscriptionStatus = DbEnumMapper.SubscriptionStatusMapper.toEntity(dto.subscriptionStatus)
            apiKey = dto.apiKey
            createdAt = dto.createdAt.toJavaLocalDateTime()
            updatedAt = dto.updatedAt.toJavaLocalDateTime()
            this
        }

    /**
     * Represents a basic user mapping strategy that uses only primitive values, covering core fields.
     */
    data object PrimitiveValues : UserMapper() {
        /**
         * Defines the sequence of steps to create a UserEntity from a UserDto, covering all primitive and core fields.
         */
        override val toEntitySteps: List<UserEntity.(UserDto) -> Unit> = listOf { dto ->
            minViableEntity(dto)
        }

        /**
         * Defines the sequence of steps to create a UserDto from a UserEntity, covering all primitive and core fields.
         */
        override val toDtoSteps: List<(UserDto, UserEntity) -> UserDto> = listOf { _, entity ->
            minViableDto(entity)
        }
    }

    /**
     * Represents a user mapping strategy that also includes mapping the user's payment methods, extending beyond core fields.
     */
    data object UserWithPaymentMethods : UserMapper() {
        /**
         * Private function to handle the mapping of payment methods from UserDto to UserEntity.
         */
        private val paymentMethodsToEntity: UserEntity.(UserDto) -> Unit = { dto ->
            paymentMethods = dto.paymentMethods.mapNotNull {
                DataLayerMapper.PaymentMethodMapper.toEntity(it)
            }
        }

        /**
         * Private function to handle the mapping of payment methods from UserEntity to UserDto.
         */
        private val paymentMethodsToDto: (UserDto, UserEntity) -> UserDto = { dto, entity ->
            dto.copy(
                paymentMethods = entity.paymentMethods.map {
                    DataLayerMapper.PaymentMethodMapper.toDto(it)
                        ?: throw Exception("PaymentMethod mapping failed")
                }
            )
        }

        /**
         * Extends the core sequence of steps to include payment methods while converting a UserDto to UserEntity.
         */
        override val toEntitySteps: List<UserEntity.(UserDto) -> Unit>
            get() = listOf({ minViableEntity(it) }, paymentMethodsToEntity)

        /**
         * Extends the core sequence of steps to include payment methods while converting a UserEntity to UserDto.
         */
        override val toDtoSteps: List<(UserDto, UserEntity) -> UserDto>
            get() = listOf({ _, entity -> minViableDto(entity) }, paymentMethodsToDto)
    }
}