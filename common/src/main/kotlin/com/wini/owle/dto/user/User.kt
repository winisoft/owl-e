package com.wini.owle.dto.user

import com.wini.owle.ksp.StringMustNotContainRestricted
import com.wini.owle.ksp.EmailTerms
import com.wini.owle.ksp.OAuthProviderEnum
import com.wini.owle.ksp.ResetTokenCharsOnly
import com.wini.owle.ksp.LongValueMin
import com.wini.owle.ksp.OneDigitMinimum
import com.wini.owle.ksp.OneLowercaseMinimum
import com.wini.owle.ksp.OneSpecialCharMinimum
import com.wini.owle.ksp.OneUppercaseMinimum
import com.wini.owle.ksp.OwleModel
import com.wini.owle.ksp.TimestampCoherent
import com.wini.owle.ksp.TimestampIso8601Format
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.user.enums.SubscriptionStatus
import kotlinx.serialization.Serializable

/**
 * Represents a user in the Owl-E application, with authentication details,
 * subscription status, and related payment methods.
 */
@OwleModel
//@OAuthProviderEnum
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
    @StringMustNotContainRestricted
    @OneUppercaseMinimum
    @OneLowercaseMinimum
    @OneSpecialCharMinimum
    @OneDigitMinimum
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