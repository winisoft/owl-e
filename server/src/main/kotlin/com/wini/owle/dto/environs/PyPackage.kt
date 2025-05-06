package com.wini.owle.dto.environs

import com.wini.owle.dto.StandardDto
import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.StringLengthMax
import com.wini.owle.schema.StringLengthMin
import com.wini.owle.schema.StringMustNotBeBlank
import com.wini.owle.schema.StringMustNotContainRestricted
import kotlinx.serialization.Serializable


/**
 * Represents a unique Python library package, along with its specific version details.
 * Used to store information about packages that can be installed in the workflow.
 */
@OwleModel
@Serializable
data class PyPackage(

    @LongValueMin(0)
    override val id: Long,

    /**
     * The name of the Python library package (e.g., 'numpy', 'pandas').
     * Required.
     */
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_NAME_LENGTH)
    val name: String,

    /**
     * Optional field to distinguish between packages with similar names.
     * Useful if the package is a fork or variant.
     */
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_DISAMBIGUATION_LENGTH)
    val disambiguation: String?,

    /**
     * Defines the exact version details (min, max, or exact version) available for this package.
     */
    val versionSpec: VersionSpec

) : StandardDto {

    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_DISAMBIGUATION_LENGTH = 255
        const val JSON_ONE_EXAMPLE = """{
            "id": 1,
            "name": "pandas",
            "disambiguation": "not the bear"
        }"""
        const val JSON_LIST_EXAMPLE = """
            [
                {
                    "id": 1,
                    "name": "spacy",
                    "disambiguation": "not the kevin"
                }
            ]
        """
    }
}
