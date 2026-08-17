package com.wini.owle.dto.tags.targets

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.dto.environs.version.Version
import com.wini.owle.dto.tags.Tag
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.api.ApiEndpointDefinition
import kotlinx.serialization.Serializable

/**
 * A tag applied to one released package.
 *
 * A package is keyed by (distribution, version), so both halves are carried here — matching
 * `tagged_packages` and the entity.
 */
@ModelDefinition
@ApiEndpointDefinition("api.tags.packageTag", ["user"], "/package-tag")
@Serializable
data class PackageTag(

    val distribution: Distribution,

    val packageVersion: Version,

    val tag: Tag

) : CompositeDto {

    override val owleId: String
        get() = "distribution=${distribution.owleId},packageVersion=${packageVersion.owleId},tag=${tag.owleId}"
}
