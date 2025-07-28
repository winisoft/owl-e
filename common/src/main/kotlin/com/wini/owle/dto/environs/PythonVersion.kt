package com.wini.owle.dto.environs

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.environs.enums.PythonPhase
import com.wini.owle.schema.OwleModel
import kotlinx.serialization.Serializable


@OwleModel
@Serializable
class PythonVersion(
    override val id: Long,
    val major: ULong,
    val minor: ULong?,
    val micro: ULong?,
    val phase: PythonPhase,
    val phaseNumber: ULong?,
    val isPostRelease: Boolean,
    val postNumber: ULong?,
    val isDevRelease: Boolean,
    val devNumber: ULong?,
    val versionString: String,
    val hasOverflow: Boolean,
    val overflowComponents: List<VersionOverflow>
): StandardDto {


}