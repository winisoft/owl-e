package com.wini.owle.dto.workflows

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.user.UserDto
import kotlinx.serialization.Serializable


@Serializable
sealed class ModificationTermsDto {
    data object Prohibited: ModificationTermsDto()
    data object AllowModification: ModificationTermsDto()
    data object AllowModificationDistribution: ModificationTermsDto()
}

@Serializable
sealed class AuthorshipCreditDto {
    data object Required: AuthorshipCreditDto()
    data object Unnecessary: AuthorshipCreditDto()
}

@Serializable
sealed class CommercialUseDto {
    data object PersonalNonProfit: CommercialUseDto()
    data object PersonalProfit: CommercialUseDto()
    data object Corporation: CommercialUseDto()
}

@Serializable
data class WorkflowMetadataDto(
    val id: Long,

    /**
     * The name of the entity (e.g., workflow or step).
     * Required.
     */
    val name: String,

    /**
     * The version of the entity, following semantic versioning (e.g., "1.0.0").
     * Defaults to "1.0.0" if not provided.
     */
    val version: String = "1.0.0",

    /**
     * List of contributor user IDs for the entity. Must have at least one author.
     * Required.
     */
    val contributors: List<UserDto>,

    /**
     * Optional copyright information for the entity.
     */
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
    val commercialUsage: CommercialUseDto = CommercialUseDto.PersonalNonProfit,

    /**
     * Defines credit display requirements:
     * - "required" (default): Credit must be displayed.
     * - "unnecessary": Credit display is not required.
     */
    val creditNotation: AuthorshipCreditDto = AuthorshipCreditDto.Required,

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
    val modification: ModificationTermsDto = ModificationTermsDto.Prohibited,

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
    val excludedSteps: List<StepDto> = emptyList(),

) : BaseDto
