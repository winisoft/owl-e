package com.wini.owle.entity.workflows

import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class WorkflowFolderRelationEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<WorkflowFolderRelationEntity>(WorkflowFolderRelations)

    var parent by WorkflowFolderEntity referencedOn WorkflowFolderRelations.parent

    var child by WorkflowFolderEntity referencedOn WorkflowFolderRelations.child

}