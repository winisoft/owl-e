package com.wini.owle.entity.workflows

import com.wini.owle.entity.user.UserEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * A [rootWorkflow] together with the closure of workflows reachable from it through junctions.
 *
 * This is the unit that actually gets built and executed: all [workflows] share one container and
 * one environment, so a junction between members is an in-process call, and only entering a
 * Multiflow from outside stands up a new sandbox.
 */
class MultiflowEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<MultiflowEntity>(Multiflows)

    var user by UserEntity optionalReferencedOn Multiflows.user

    var name by Multiflows.name

    var description by Multiflows.description

    /**
     * The workflow execution enters at.
     */
    var rootWorkflow by WorkflowEntity referencedOn Multiflows.rootWorkflow

    var createdAt by Multiflows.createdAt

    var updatedAt by Multiflows.updatedAt

    /**
     * Every member of the closure, including the root.
     */
    val workflows: SizedIterable<WorkflowEntity>
            by WorkflowEntity.via(MultiflowWorkflows.multiflow, MultiflowWorkflows.workflow)

    /**
     * Membership rows, which additionally carry each member's canvas placement.
     */
    val members: SizedIterable<MultiflowWorkflowEntity>
            by MultiflowWorkflowEntity referrersOn MultiflowWorkflows.multiflow
}
