package com.wini.owle.dto.tags.targets

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.workflows.Step
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.api.ApiEndpointDefinition
import kotlinx.serialization.Serializable

/**
 * A tag applied to a step.
 */
@ModelDefinition
@ApiEndpointDefinition("api.tags.stepTag", ["user"], "/step-tag")
@Serializable
data class StepTag(

    val step: Step,

    val tag: Tag

) : CompositeDto {

    override val owleId: String get() = "step=${step.owleId},tag=${tag.owleId}"
}
