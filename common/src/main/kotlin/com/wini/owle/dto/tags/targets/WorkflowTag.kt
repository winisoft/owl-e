package com.wini.owle.dto.tags.targets

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.api.ApiEndpointDefinition
import kotlinx.serialization.Serializable

/**
 * A tag applied to a workflow.
 */
@ModelDefinition
@ApiEndpointDefinition("api.tags.workflowTag", ["user"], "/workflow-tag")
@Serializable
data class WorkflowTag(

    val workflow: Workflow,

    val tag: Tag

) : CompositeDto {

    override val owleId: String get() = "workflow=${workflow.owleId},tag=${tag.owleId}"
}
