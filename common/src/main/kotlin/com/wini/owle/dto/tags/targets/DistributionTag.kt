package com.wini.owle.dto.tags.targets

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.dto.tags.Tag
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintSimple
import kotlinx.serialization.Serializable


@ModelDefinition
@Serializable
data class DistributionTag(

    val distribution: Distribution,

    val tag: Tag

): CompositeDto {

    override val owleId: String get() = "distribution=${distribution.name},tag=${tag.owleId}"
}