package com.wini.owle.dto.environs

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.environs.enums.PyPackageRepoType
import com.wini.owle.error.InvalidUrlFormatError
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import kotlinx.serialization.Serializable


/**
 * Represents potential sources or repositories for packages that a workflow step can utilize.
 * Examples include official repositories (like PyPI or Conda) and custom or local sources.
 */
@ModelDefinition
@Serializable
data class PyPackageRepo(

    /**
     * The unique identifier of this PyPackageRepo
     */
    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,

    /**
     * Enum value that specifies the type of repository.
     */
    val sourceType: PyPackageRepoType,

    /**
     * URL of the repository where the package can be sourced.
     * Required, even for custom or local sources, to ensure accessibility.
     */
    @ConstraintSimple([StringSafeCharsError::class, InvalidUrlFormatError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_SOURCE_URL_LEN)
    val sourceUrl: String?
) : StandardDto {

    companion object {
        const val MAX_SOURCE_URL_LEN: Int = 1024
    }

}
