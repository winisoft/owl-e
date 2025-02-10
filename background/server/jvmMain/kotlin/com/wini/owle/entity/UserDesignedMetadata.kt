package com.wini.owle.entity
//
//import com.wini.owle.dto.UserDesignedMetadataDto
//import kotlinx.datetime.Clock
//import kotlinx.serialization.Serializable
//import kotlinx.serialization.json.Json
//
///**
// * Represents metadata for workflows or steps within the Owl-E application.
// * Includes information on authorship, usage permissions, and sharing configuration.
// */
//@Serializable
//data class UserDesignedMetadata(
//    /**
//     * The name of the entity (e.g., workflow or step).
//     * Required.
//     */
//    val name: String,
//
//    /**
//     * The version of the entity, following semantic versioning (e.g., "1.0.0").
//     * Defaults to "1.0.0" if not provided.
//     */
//    val version: String = "1.0.0",
//
//    /**
//     * List of authors for the entity. Must have at least one author.
//     * Required.
//     */
//    val authors: List<String>,
//
//    /**
//     * Optional copyright information for the entity.
//     */
//    val copyrightInformation: String? = null,
//
//    /**
//     * Optional contact information for the author(s) of the entity.
//     */
//    val contactInformation: String? = null,
//
//    /**
//     * Information about third-party licenses that apply to this entity.
//     * Optional.
//     */
//    val thirdPartyLicenses: String? = null,
//
//    /**
//     * Advises users to exercise discretion when using this workflow or step.
//     * Defaults to `false`.
//     */
//    val discretionAdvisedContent: Boolean = false,
//
//    /**
//     * Permits commercial use under defined terms:
//     * - "personalNonProfit" (default): Personal, non-profit use only.
//     * - "personalProfit": Personal use, regardless of profit.
//     * - "corporation": Corporate use allowed.
//     */
//    val commercialUsage: CommercialUsage = CommercialUsage.PERSONAL_NON_PROFIT,
//
//    /**
//     * Defines credit display requirements:
//     * - "required" (default): Credit must be displayed.
//     * - "unnecessary": Credit display is not required.
//     */
//    val creditNotation: CreditNotation = CreditNotation.REQUIRED,
//
//    /**
//     * Allows or disallows redistribution of the entity.
//     * Defaults to `false`.
//     */
//    val allowRedistribution: Boolean = false,
//
//    /**
//     * Defines modification permissions:
//     * - "prohibited" (default): Modifications are not allowed.
//     * - "allowModification": Modifications are allowed but cannot be redistributed.
//     * - "allowModificationRedistribution": Modifications and redistribution allowed.
//     */
//    val modification: Modification = Modification.PROHIBITED,
//
//    /**
//     * Tracks the user's accepted distribution terms, including version number
//     * and timestamps for initial acceptance and most recent update.
//     */
//    val distributionAgreement: DistributionAgreement = DistributionAgreement(),
//
//    /**
//     * Configuration for sharing the workflow or step, including whether
//     * all steps are bundled by default and any step exclusions.
//     */
//    val sharingConfig: SharingConfig = SharingConfig()
//) {
//
//    companion object {
//        val jsonConfig = Json {
//            prettyPrint = true
//            isLenient = true
//            ignoreUnknownKeys = true
//        }
//
//        val mock =  UserDesignedMetadata(
//            name = "John Arbuckle",
//            version = "1.0.0",
//            authors = listOf("Garfield Cat", "Nermal"),
//            copyrightInformation = "2025",
//            contactInformation = "lasagnalonely@email.com",
//            thirdPartyLicenses = "Ok",
//            discretionAdvisedContent = true,
//            commercialUsage = CommercialUsage.CORPORATION,
//            creditNotation = CreditNotation.REQUIRED,
//            allowRedistribution = false,
//            modification = Modification.PROHIBITED,
//            distributionAgreement = DistributionAgreement(
//                version = "1.0.0",
//                acceptedAt = "",
//                updatedAt = ""
//            ),
//            sharingConfig = SharingConfig(
//                includeAllSteps = true,
//                excludedSteps = emptyList()
//            )
//        )
//    }
//}
//
//@Serializable
//data class DistributionAgreement(
//    val version: String = "1.0.0",
//    val acceptedAt: String = Clock.System.now().toString(),
//    val updatedAt: String = Clock.System.now().toString()
//)
//@Serializable
//data class SharingConfig(
//    val includeAllSteps: Boolean = true,
//    val excludedSteps: List<Int> = emptyList()
//)
//
//@Serializable
//enum class CommercialUsage {
//    PERSONAL_NON_PROFIT, PERSONAL_PROFIT, CORPORATION
//}
//
//@Serializable
//enum class CreditNotation {
//    REQUIRED, UNNECESSARY
//}
//
//@Serializable
//enum class Modification {
//    PROHIBITED, ALLOW_MODIFICATION, ALLOW_MODIFICATION_REDISTRIBUTION
//}