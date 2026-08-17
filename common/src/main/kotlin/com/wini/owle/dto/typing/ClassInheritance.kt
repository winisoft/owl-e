package com.wini.owle.dto.typing

import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition


@Serializable
@ApiEndpointDefinition("api.typing.classInheritanceApi", ["type"], "/class-inheritance")
data class ClassInheritance(

    /**
     * Represents the parent node in an inheritance relationship. Both it and parentNode are CodeFeatureKind.BASE
     */
    val parent : TypeNode,

    /**
     * Represents the child node in an inheritance relationship. Both it and parentNode are CodeFeatureKind.BASE
     */
    val child : TypeNode

) : CompositeDto {


    override val owleId: String get() = "parent=${parent.owleId},child=${child.owleId}"

}
