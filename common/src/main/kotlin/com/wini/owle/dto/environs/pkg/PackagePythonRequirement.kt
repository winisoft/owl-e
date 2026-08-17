package com.wini.owle.dto.environs.pkg

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.environs.version.enums.VersionOperator
import com.wini.owle.dto.environs.version.enums.WildcardSegment
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
     *
     */
    val packageVersion : Version,

    /**
     *
     */
    val wildcard: WildcardSegment,

    val operator: VersionOperator


) : CompositeDto {

    override val owleId: String
        get() = ""
}