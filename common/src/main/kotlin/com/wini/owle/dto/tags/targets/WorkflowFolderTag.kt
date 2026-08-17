package com.wini.owle.dto.tags.targets

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.api.ApiEndpointDefinition
import kotlinx.serialization.Serializable

/**
 * A tag applied to a workflowFolder.
 */
@ModelDefinition
@ApiEndpointDefinition("api.tags.workflowFolderTag", ["user"], "/workflow-folder-tag")
@Serializable
data class WorkflowFolderTag(

    val workflowFolder: WorkflowFolder,

    val tag: Tag

) : CompositeDto {

    override val owleId: String get() = "workflowFolder=${workflowFolder.owleId},tag=${tag.owleId}"
}
