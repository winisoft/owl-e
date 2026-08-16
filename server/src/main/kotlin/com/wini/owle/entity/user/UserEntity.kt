package com.wini.owle.entity.user

import com.wini.owle.entity.runs.TestCases
import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.tags.Tags
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import com.wini.owle.entity.workflows.WorkflowFolders
import com.wini.owle.entity.workflows.Workflows
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


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

    var personalFirst by Users.firstName

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
     * Current account status of the user (e.g., active, on hold).
     */
    var userStatus by Users.userStatus

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

    // --- owned content ------------------------------------------------------------------------

    val paymentMethods: SizedIterable<PaymentMethodEntity> by PaymentMethodEntity referrersOn PaymentMethods.user

    val workflows: SizedIterable<WorkflowEntity> by WorkflowEntity optionalReferrersOn Workflows.user

    val workflowFolders: SizedIterable<WorkflowFolderEntity> by WorkflowFolderEntity referrersOn WorkflowFolders.user

    val testCases: SizedIterable<TestCaseEntity> by TestCaseEntity referrersOn TestCases.user

    /**
     * Tags this user authored — system and PyPI tags have no user.
     */
    val tags: SizedIterable<TagEntity> by TagEntity optionalReferrersOn Tags.user
}
