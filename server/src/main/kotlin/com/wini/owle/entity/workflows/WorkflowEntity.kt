package com.wini.owle.entity.workflows

import com.wini.owle.entity.environs.version.PythonVersionEntity
import com.wini.owle.entity.user.UserEntity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a workflow, containing steps and data points, and supports organization
 * into folders. Metadata is stored as JSON to allow for flexible information capture.
 */
open class WorkflowEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<WorkflowEntity>(Workflows)

    var name by Workflows.name

    var user by UserEntity referencedOn Workflows.user

    var description by Workflows.description

    var pythonVersion by PythonVersionEntity referencedOn Workflows.pythonVersion

    var parentFolder by WorkflowFolderEntity optionalReferencedOn Workflows.parentFolder

    var createdAt by Workflows.createdAt

    var updatedAt by Workflows.updatedAt

}
