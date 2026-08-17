package com.wini.owle.dto.workflows

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.enums.AuthorshipCredit
import com.wini.owle.dto.workflows.enums.CommercialUse
import com.wini.owle.dto.workflows.enums.ModificationTerms
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.schema.Schema
import com.wini.owle.schema.error.ConstraintStringLenMax
import kotlinx.serialization.Serializable

/**
 * Represents metadata for workflows or steps within the Owl-E application.
 * Includes information on authorship, usage permissions, and sharing configuration.
 */
@ModelDefinition
@Serializable
data class WorkflowMetadata(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_255)
    val name: String,

    /**
     * The version of the entity, following semantic versioning (e.g., "1.0.0").
     * Defaults to "1.0.0" if not provided.
     */
    val version: String = "1.0.0",

    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    val copyrightInformation: String? = null,

    /**
     * Optional contact information for the author(s) of the entity.
     */
    val contactInformation: String? = null,

    /**
     * Information about third-party licenses that apply to this entity.
     * Optional.
     */
    val thirdPartyLicenses: String? = null,

    /**
     * Advises users to exercise discretion when using this workflow or step.
     * Defaults to `false`.
     */
    val discretionAdvisedContent: Boolean = false,

    /**
     * Permits commercial use under defined terms:
     * - "personalNonProfit" (default): Personal, non-profit use only.
     * - "personalProfit": Personal use, regardless of profit.
     * - "corporation": Corporate use allowed.
     */
    val commercialUsage: CommercialUse = CommercialUse.PERSONAL_NON_PROFIT,

    /**
     * Defines credit display requirements:
     * - "required" (default): Credit must be displayed.
     * - "unnecessary": Credit display is not required.
     */
    val creditNotation: AuthorshipCredit = AuthorshipCredit.REQUIRED,

    /**
     * Allows or disallows redistribution of the entity.
     * Defaults to `false`.
     */
    val allowRedistribution: Boolean = false,

    /**
     * Defines modification permissions:
     * - "prohibited" (default): Modifications are not allowed.
     * - "allowModification": Modifications are allowed but cannot be redistributed.
     * - "allowModificationRedistribution": Modifications and redistribution allowed.
     */
    val modification: ModificationTerms = ModificationTerms.PROHIBITED,

    /**
     * The version number of the distribution agreement.
     */
    val distributionVersion: String = "1.0.0",

    /**
     * Timestamp for when the user accepted the distribution terms.
     */
    val distributionAcceptedAt: String,

    /**
     * Timestamp for the most recent update to the accepted distribution terms.
     */
    val distributionUpdatedAt: String,

    /**
     * List of steps that are explicitly excluded from distribution.
     */
    val excludedSteps: List<Step> = emptyList(),

    ) : ULongDto
