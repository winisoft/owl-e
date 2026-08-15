package com.wini.owle.dto.environs.pkg

import com.wini.owle.dto.ULongDto
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable


@ModelDefinition
@Serializable
data class PackageExtra(
    override val id: ULong,
    val name: String,
    val distribution: Distribution
): ULongDto {

    override val owleId: String get() = "id=$id,name=${name},distribution=${distribution.owleId}"

}