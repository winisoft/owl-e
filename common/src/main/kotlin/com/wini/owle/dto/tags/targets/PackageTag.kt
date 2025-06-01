package com.wini.owle.dto.tags.targets

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.dto.environs.pkg.DistributionPackage
import com.wini.owle.dto.tags.Tag
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable


@ModelDefinition
@Serializable
data class PackageTag(

    val distribution: Distribution,

    val distributionPackage: DistributionPackage,

    val tag: Tag

): CompositeDto {

    override val owleId: String get() = "distribution=${distribution.owleId},package=${distributionPackage.owleId},tag=${tag.owleId}"

}