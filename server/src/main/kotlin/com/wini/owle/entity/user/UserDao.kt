package com.wini.owle.entity.user

import com.wini.owle.dto.user.SubscriptionStatusDto
import com.wini.owle.entity.Users
import com.wini.owle.entity.WorkflowMetadataContributors
import com.wini.owle.entity.workflows.WorkflowMetadataDao
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID


enum class SubscriptionStatus {
    REGISTERED, ACTIVE, CANCELLED, ON_HOLD;

    companion object {
        fun fromDto(dto: SubscriptionStatusDto): SubscriptionStatus {
            return when (dto) {
                SubscriptionStatusDto.Registered -> REGISTERED
                SubscriptionStatusDto.Active -> ACTIVE
                SubscriptionStatusDto.Cancelled -> CANCELLED
                SubscriptionStatusDto.OnHold -> ON_HOLD
            }
        }
        fun SubscriptionStatus.toDto(): SubscriptionStatusDto {
            return when(this){
                REGISTERED -> SubscriptionStatusDto.Registered
                ACTIVE -> SubscriptionStatusDto.Active
                CANCELLED -> SubscriptionStatusDto.Cancelled
                ON_HOLD -> SubscriptionStatusDto.OnHold
            }
        }
    }
}

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

    /**
     * Metadata records representing distributed workflow content the user has contributed to.
     * This is a many-to-many relationship through `workflow_metadata_contributors`.
     */
    val sharedWorkflowContributions by WorkflowMetadataDao via WorkflowMetadataContributors

}