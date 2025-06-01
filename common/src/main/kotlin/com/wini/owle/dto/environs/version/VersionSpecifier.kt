package com.wini.owle.dto.environs.version

import com.wini.owle.dto.ULongDto
import kotlinx.serialization.Serializable


@Serializable
data class VersionSpecifier(
    override val id: ULong
): ULongDto {


}