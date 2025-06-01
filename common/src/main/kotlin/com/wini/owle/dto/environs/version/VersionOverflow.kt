package com.wini.owle.dto.environs.version

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.environs.enums.OverflowViolation
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable


@ModelDefinition
@Serializable
class VersionOverflow(
    override val id: ULong,
    val version: Version,
    val violation: OverflowViolation,
    val stringValue: String
): ULongDto