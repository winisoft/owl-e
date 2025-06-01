package com.wini.owle.dto.environs.pkg

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.environs.enums.VersionOperator
import com.wini.owle.dto.environs.enums.WildcardSegment
import com.wini.owle.dto.environs.version.Version
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable


@ModelDefinition
@Serializable
data class PackagePythonRequirement(

    val distribution: Distribution,

    /**
     *
     */
    val version : Version,

    /**
     *
     */
    val wildcardSegment: WildcardSegment,

    val operator: VersionOperator


) : CompositeDto {

    override val owleId: String
        get() = ""
}