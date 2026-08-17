package com.wini.owle.dto.environs.version

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.environs.version.enums.VersionOperator
import com.wini.owle.dto.environs.version.enums.WildcardSegment
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

@ModelDefinition
@ApiEndpointDefinition("api.environs.version.pythonVersionSpecifier", ["environs"], "/python-version-specifier")
@Serializable
data class PythonVersionSpecifier(

    val pythonVersion: PythonVersion,

    val operator: VersionOperator,

    val wildcard: WildcardSegment

): CompositeDto {

    override val owleId: String
        get() = "pythonVersion=${pythonVersion.owleId},operator=${operator.name},wildcard=${wildcard.name}"
}