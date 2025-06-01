package com.wini.owle.dto.user

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.user.enums.UserStatus
import com.wini.owle.error.IncoherentInstantError
import com.wini.owle.error.InvalidOAuthProvider
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.ResetTokenCharsError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.error.TimestampFormatError
import com.wini.owle.error.ValidEmailFormatError
import kotlinx.serialization.Serializable

/**
 * Represents a user in the Owl-E application, with authentication details,
 * subscription status, and related payment methods.
 */
@ModelDefinition
@Serializable
//@PreventsTimeParadox(
//    happensFirstField = "createdAt",
//    happensSecondField = "updatedAt")
//@InconsistentOAuthId(
//    providerName = "oauthProvider",
//    oauthName = "oauthId")
data class User(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    /**
     * User's email, used for login and communication.
     */
    @ConstraintSimple([ValidEmailFormatError::class, StringSafeCharsError::class])
    val email: String,

    /**
     * User's password, stored as a hashed value.
     */
    @PasswordSecurityPolicy
    val password: String,

    /**
     * Token used to reset the user's password, if applicable.
     */
    @ConstraintSimple([ResetTokenCharsError::class])
    val resetToken: String? = null,

    /**
     * OAuth provider, if the user is authenticated via an external provider.
     */
    @ConstraintSimple([InvalidOAuthProvider::class])
    val oauthProvider: String? = null,

    /**
     * OAuth provider ID, if the user is authenticated via an external provider.
     */
    val oauthId: String? = null,

    /**
     * Current subscription status of the user (e.g., active, on hold).
     */
    val userStatus: UserStatus,

    /**
     * API key for accessing restricted resources.
     */
    @ConstraintSimple([ResetTokenCharsError::class])
    val apiKey: String? = null,

    /**
     * Timestamp for when the user was created.
     */
    @ConstraintSimple([TimestampFormatError::class, IncoherentInstantError::class])
    val createdAt: String,

    /**
     * Timestamp for when the user was last updated.
     */
    @ConstraintSimple([TimestampFormatError::class, IncoherentInstantError::class])
    val updatedAt: String,

    val paymentMethods: List<PaymentMethod> = emptyList()
) : ULongDto