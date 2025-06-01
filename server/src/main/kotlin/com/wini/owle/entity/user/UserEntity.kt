package com.wini.owle.entity.user

import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a user in the Owl-E application, with authentication details,
 * subscription status, and related payment methods.
 */
class UserEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<UserEntity>(Users)

    /**
     * User's email, used for login and communication.
     */
    var email by Users.email

    var userName by Users.userName

    var personalFirst by Users.firstName

    var personalLast by Users.lastName

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

    var userStatus by Users.userStatus

    /**
     * OAuth provider ID, if the user is authenticated via an external provider.
     */
    var oauthId by Users.oauthId

    /**
     * Current subscription status of the user (e.g., active, on hold).
     */
    var subscriptionStatus by Users.userStatus

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

    /**
     * Metadata records representing distributed workflow content the user has contributed to.
     * This is a many-to-many relationship through `workflow_metadata_contributors`.
     */
    val sharedWorkflowContributions by WorkflowMetadataEntity via WorkflowMetadataContributors

}