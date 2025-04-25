package com.wini.owle.dto.user

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.user.SubscriptionStatusDto
import kotlinx.serialization.Serializable

/**
 * Represents a user in the Owl-E application, with authentication details,
 * subscription status, and related payment methods.
 */
@Serializable
data class User(
    override val id: Long,
    /**
     * User's email, used for login and communication.
     */
    val email: String,
    /**
     * User's password, stored as a hashed value.
     */
    val password: String,
    /**
     * Token used to reset the user's password, if applicable.
     */
    val resetToken: String? = null,
    /**
     * OAuth provider, if the user is authenticated via an external provider.
     */
    val oauthProvider: String? = null,
    /**
     * OAuth provider ID, if the user is authenticated via an external provider.
     */
    val oauthId: String? = null,
    /**
     * Current subscription status of the user (e.g., active, on hold).
     */
    val subscriptionStatus: SubscriptionStatusDto,
    /**
     * API key for accessing restricted resources.
     */
    val apiKey: String? = null,
    /**
     * Timestamp for when the user was created.
     */
    val createdAt: String,
    /**
     * Timestamp for when the user was last updated.
     */
    val updatedAt: String,
    val paymentMethods: List<PaymentMethod> = emptyList()
) : StandardDto