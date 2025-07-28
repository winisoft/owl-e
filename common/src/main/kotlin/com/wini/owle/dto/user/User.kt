package com.wini.owle.dto.user

import com.wini.owle.schema.error.EmailFormat
import com.wini.owle.schema.error.ResetTokenCharsOnly
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.error.TimestampCoherent
import com.wini.owle.schema.error.TimestampIso8601Format
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.user.enums.SubscriptionStatus
import com.wini.owle.error.IncoherentInstantError
import com.wini.owle.error.InvalidOAuthProvider
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.ResetTokenCharsError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.error.TimestampFormatError
import com.wini.owle.error.ValidEmailFormatError
import com.wini.owle.schema.error.InconsistentOAuthId
import com.wini.owle.schema.error.OAuthProviderEnum
import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.error.PreventsTimeParadoxes
import com.wini.owle.schema.error.StringSafeChars
import kotlinx.serialization.Serializable

/**
 * Represents a user in the Owl-E application, with authentication details,
 * subscription status, and related payment methods.
 */
@OwleModel
@OAuthProviderEnum
@Serializable
data class User(

    @LongValueMin(0)
    override val id: Long,

    /**
     * User's email, used for login and communication.
     */
    @EmailTerms
    val email: String,

    /**
     * User's password, stored as a hashed value.
     */
    @PasswordSecurityPolicy
    val password: String,

    /**
     * Token used to reset the user's password, if applicable.
     */
    @ResetTokenCharsOnly
    val resetToken: String? = null,

    /**
     * OAuth provider, if the user is authenticated via an external provider.
     */
    @OAuthProviderEnum
    val oauthProvider: String? = null,

    /**
     * OAuth provider ID, if the user is authenticated via an external provider.
     */
    val oauthId: String? = null,

    /**
     * Current subscription status of the user (e.g., active, on hold).
     */
    val subscriptionStatus: SubscriptionStatus,

    /**
     * API key for accessing restricted resources.
     */
    @ResetTokenCharsOnly
    val apiKey: String? = null,

    /**
     * Timestamp for when the user was created.
     */
    @TimestampIso8601Format
    @TimestampCoherent
    val createdAt: String,

    /**
     * Timestamp for when the user was last updated.
     */
    @TimestampIso8601Format
    @TimestampCoherent
    val updatedAt: String,

    val paymentMethods: List<PaymentMethod> = emptyList()
) : StandardDto