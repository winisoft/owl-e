package com.wini.owle.dto.environs.pkg

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.environs.version.Version
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.Schema
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 * Represents a unique Python library package, along with its specific version details.
 * Used to store information about packages that can be installed in the workflow.
 */
@ModelDefinition
@ApiEndpointDefinition("api.environs.pkg.distributionPackage", ["environs"], "/distribution-package")
@Serializable
data class DistributionPackage(
    val distribution: Distribution,
    val version: Version,

    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.DistributionPackage.VERSION_STR_MAX_LENGTH)
    val versionString: String,

    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.DistributionPackage.SUMMARY_MAX_LENGTH)
    val summary: String?,

    // TEXT column in the DB — no length cap sourced from the CSV.
    @ConstraintSimple([StringContainsRestrictedChars::class])
    val keywords: String?,

    // TEXT column in the DB — no length cap sourced from the CSV.
    @ConstraintSimple([StringContainsRestrictedChars::class])
    val description: String?,

    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.DistributionPackage.DESCRIPTION_CONTENT_TYPE_MAX_LENGTH)
    val descriptionContentType: String?,

    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.DistributionPackage.LICENSE_MAX_LENGTH)
    val license: String?,

    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.DistributionPackage.HOME_PAGE_MAX_LENGTH)
    val homePage: String?,

    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.DistributionPackage.PROJECT_URL_MAX_LENGTH)
    val projectUrl: String?,

    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.DistributionPackage.DOWNLOAD_URL_MAX_LENGTH)
    val downloadUrl: String?,

    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.DistributionPackage.DOCS_URL_MAX_LENGTH)
    val docsUrl: String?,

    val yanked: Boolean?,
    val lastSerial: UInt?,
    //val requirements: List<PackageDistRequirement>,
) : CompositeDto {

    override val owleId: String get() = "distribution=${distribution},4"

}
