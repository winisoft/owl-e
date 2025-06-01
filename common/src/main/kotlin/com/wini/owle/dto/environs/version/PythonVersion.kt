package com.wini.owle.dto.environs.version

import com.wini.owle.dto.ULongDto
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintSimple
import kotlinx.serialization.Serializable


@ModelDefinition
@Serializable
data class PythonVersion(

    override val id: ULong,

    val version: Version,


): ULongDto