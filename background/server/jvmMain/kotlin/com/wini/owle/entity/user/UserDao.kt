package com.wini.owle.entity.user

import com.wini.owle.entity.Users
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a user in the Owl-E application, with authentication details,
 * subscription status, and related payment methods.
 */
class UserDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserDao>(Users)

    /**
     * User's email, used for login and communication.
     */
    var email by Users.email

    /**
     * User's password, stored as a hashed value.
     */
    var password by Users.password

    /**
     * Token used to reset the user's password, if applicable.
     */
    var resetToken by Users.resetToken

    /**
     * OAuth provider, if the user is authenticated via an external provider.
     */
    var oauthProvider by Users.oauthProvider

    /**
     * OAuth provider ID, if the user is authenticated via an external provider.
     */
    var oauthId by Users.oauthId

    /**
     * Current subscription status of the user (e.g., active, on hold).
     */
    var subscriptionStatus by Users.subscriptionStatus

    /**
     * API key for accessing restricted resources.
     */
    var apiKey by Users.apiKey

    /**
     * Timestamp for when the user was created.
     */
    var createdAt by Users.createdAt

    /**
     * Timestamp for when the user was last updated.
     */
    var updatedAt by Users.updatedAt
}