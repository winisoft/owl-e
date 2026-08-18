package com.wini.owle.dto.user

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.user.enums.UserStatus
import com.wini.owle.error.TimestampMustBeChronologicallyCoherent
import com.wini.owle.error.InvalidOAuthProvider
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.InvalidResetToken
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.TimestampFormatMustMatchIso8601
import com.wini.owle.error.InvalidEmailFormat
import com.wini.owle.schema.PasswordSecurityPolicy
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 * Represents a user in the Owl-E application, with authentication details,
 * subscription status, and related payment methods.
 */
@ModelDefinition
@ApiEndpointDefinition("api.user.user", ["user"], "/user")
@Serializable
//@PreventsTimeParadox(
//    happensFirstField = "createdAt",
//    happensSecondField = "updatedAt")
//@InconsistentOAuthId(
//    providerName = "oauthProvider",
//    oauthName = "oauthId")
data class User(

    override val id: ULong,

    /**
     * User's email, used for login and communication.
     */
    @ConstraintSimple([InvalidEmailFormat::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.User.EMAIL_MAX_LENGTH)
    val email: String,

    /**
     * User's password, stored as a hashed value.
     */
    @PasswordSecurityPolicy
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.User.PASSWORD_MAX_LENGTH)
    val password: String,

    /**
     * Token used to reset the user's password, if applicable.
     */
    @ConstraintSimple([InvalidResetToken::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.User.RESET_TOKEN_MAX_LENGTH)
    val resetToken: String? = null,

    /**
     * OAuth provider, if the user is authenticated via an external provider.
     */
    @ConstraintSimple([InvalidOAuthProvider::class])
    val oauthProvider: String? = null,

    /**
     * OAuth provider ID, if the user is authenticated via an external provider.
     */
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.User.OAUTH_ID_MAX_LENGTH)
    val oauthId: String? = null,

    /**
     * Current subscription status of the user (e.g., active, on hold).
     */
    val userStatus: UserStatus,

    /**
     * API key for accessing restricted resources.
     */
    @ConstraintSimple([InvalidResetToken::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.User.API_KEY_MAX_LENGTH)
    val apiKey: String? = null,

    /**
     * Timestamp for when the user was created.
     */
    @ConstraintSimple([TimestampFormatMustMatchIso8601::class, TimestampMustBeChronologicallyCoherent::class])
    val createdAt: String,

    /**
     * Timestamp for when the user was last updated.
     */
    @ConstraintSimple([TimestampFormatMustMatchIso8601::class, TimestampMustBeChronologicallyCoherent::class])
    val updatedAt: String,

    val paymentMethods: List<PaymentMethod> = emptyList()
) : ULongDto