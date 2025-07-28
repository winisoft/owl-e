package com.wini.owle.dto.environs

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.environs.enums.OverflowComponentType
import com.wini.owle.schema.OwleModel
import kotlinx.serialization.Serializable


@OwleModel
@Serializable
class VersionOverflow(
    override val id: Long,
    val pythonVersion: PythonVersion,
    val componentType: OverflowComponentType,
    val stringValue: String
): StandardDto {


}