package com.wini.owle.entity.workflows

import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * Membership of a workflow in a Multiflow, plus where it sits on the Multiflow's own canvas.
 */
class MultiflowWorkflowEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<MultiflowWorkflowEntity>(MultiflowWorkflows)

    var multiflow by MultiflowEntity referencedOn MultiflowWorkflows.multiflow

    var workflow by WorkflowEntity referencedOn MultiflowWorkflows.workflow

    var posX by MultiflowWorkflows.posX

    var posY by MultiflowWorkflows.posY

    var color by MultiflowWorkflows.color

    var isCollapsed by MultiflowWorkflows.isCollapsed
}
