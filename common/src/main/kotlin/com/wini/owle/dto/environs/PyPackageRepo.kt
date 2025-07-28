package com.wini.owle.dto.environs

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.environs.enums.PyPackageRepoType
import com.wini.owle.error.InvalidUrlFormatError
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.error.UrlFormat
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.error.StringLengthMax
import com.wini.owle.schema.error.StringSafeChars
import kotlinx.serialization.Serializable


/**
 * Represents potential sources or repositories for packages that a workflow step can utilize.
 * Examples include official repositories (like PyPI or Conda) and custom or local sources.
 */
@OwleModel
@Serializable
data class PyPackageRepo(

    @LongValueMin(0)
    override val id: Long,

    /**
     * Enum value that specifies the type of repository.
     */
    val sourceType: PyPackageRepoType,

    /**
     * URL of the repository where the package can be sourced.
     * Required, even for custom or local sources, to ensure accessibility.
     */
    @StringMustNotContainRestricted
    @InvalidUrl
    @StringLengthMax(MAX_SOURCE_URL_LEN)
    val sourceUrl: String?
) : StandardDto {

    companion object {
        const val MAX_SOURCE_URL_LEN: Int = 1024
    }

}
