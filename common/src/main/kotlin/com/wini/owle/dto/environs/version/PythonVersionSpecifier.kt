package com.wini.owle.dto.environs.version

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.environs.enums.VersionOperator
import com.wini.owle.dto.environs.enums.WildcardSegment
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

@ModelDefinition
@Serializable
data class PythonVersionSpecifier(

    override val id: ULong,

    val pythonVersion: PythonVersion,

    val operator: VersionOperator,

    val wildcard: WildcardSegment

): ULongDto