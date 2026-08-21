package com.wini.owle.dto.environs.pkg

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.environs.version.enums.VersionOperator
import com.wini.owle.dto.environs.version.enums.WildcardSegment
import com.wini.owle.dto.environs.version.PythonVersion
import com.wini.owle.dto.environs.version.Version
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition


@ModelDefinition
@ApiEndpointDefinition("api.environs.pkg.packageRequiredPython", ["environs"], "/package-required-python")
@Serializable
data class PackagePythonRequirement(

    val distribution: Distribution,

    /**
     * The version half of the package's own key.
     *
     * A package is identified by (distribution, version) together, so this is the release that
     * *declares* the requirement - not the interpreter it requires. That one is [pythonVersion].
     */
    val packageVersion : Version,

    /**
     * The interpreter release this clause names, as in the `3.9` of `>=3.9`.
     *
     * Part of the key rather than a payload: a Requires-Python such as `>=3.9,<3.13` is two rows,
     * and they differ only in this column and [operator]. Keying without it collapsed every
     * bounded range to whichever clause was written first.
     */
    val pythonVersion: PythonVersion,

    /**
     * How many release components [operator] compares, for a clause written with a `.*`.
     *
     * [WildcardSegment.NONE] for an ordinary clause. Names how many components are *pinned*, not
     * where the star sits: `==3.*` is [WildcardSegment.MAJOR], `==3.9.*` is
     * [WildcardSegment.MINOR].
     */
    val wildcard: WildcardSegment,

    val operator: VersionOperator


) : CompositeDto {

    override val owleId: String
        get() = ""
}