package com.wini.owle.entity.workflows

import com.wini.owle.entity.steps.DataPointWriteEntity
import com.wini.owle.entity.steps.DataPointWrites
import com.wini.owle.entity.steps.ErrorWatchEntity
import com.wini.owle.entity.steps.ErrorWatches
import com.wini.owle.entity.steps.InquisitionEntity
import com.wini.owle.entity.steps.Inquisitions
import com.wini.owle.entity.steps.StepDataPointReadEntity
import com.wini.owle.entity.steps.StepDataPointReads
import com.wini.owle.entity.steps.StepDefaultRouteEntity
import com.wini.owle.entity.steps.StepDefaultRoutes
import com.wini.owle.entity.steps.ToolEntity
import com.wini.owle.entity.steps.WorkflowJunctionEntity
import com.wini.owle.entity.steps.WorkflowJunctions
import com.wini.owle.entity.tags.StepTags
import com.wini.owle.entity.tags.TagEntity
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * One invocation of a tool within a workflow, with everything the six-phase lifecycle needs:
 * [reads] resolve the tool's arguments, [errorWatches] guard the invocation, [writes] persist
 * the result, [inquisitions] evaluate afterwards in order, and routing falls through
 * [defaultRoute] when nothing redirected.
 *
 * Position is canvas layout only ([posX]/[posY]/[parentGrouper]) — sequence lives in the routes.
 */
class StepEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<StepEntity>(Steps)

    /**
     * Workflow that contains this step.
     */
    var workflow by WorkflowEntity referencedOn Steps.workflow

    /**
     * Name of the step, used for identification within the workflow.
     */
    var name by Steps.name

    /**
     * Optional description of the step’s purpose or action.
     */
    var description by Steps.description

    /**
     * Tool associated with this step for execution.
     */
    var tool by ToolEntity referencedOn Steps.tool

    /**
     * Indicates if this step is the first step in the workflow.
     */
    var isWorkflowHead by Steps.isWorkflowHead

    /**
     * The grouper this step is displayed within. If null, it sits at the workflow level.
     */
    var parentGrouper by GrouperEntity optionalReferencedOn Steps.parentGrouper

    var posX by Steps.posX

    var posY by Steps.posY

    var color by Steps.color

    var createdAt by Steps.createdAt

    var updatedAt by Steps.updatedAt

    // --- lifecycle wiring -------------------------------------------------------------------

    /**
     * Argument bindings for the tool's parameters.
     */
    val reads: SizedIterable<StepDataPointReadEntity>
            by StepDataPointReadEntity referrersOn StepDataPointReads.step

    /**
     * Error handlers guarding the invocation.
     */
    val errorWatches: SizedIterable<ErrorWatchEntity> by ErrorWatchEntity referrersOn ErrorWatches.step

    /**
     * Where this step's result is persisted. Runs unconditionally on normal return.
     */
    val writes: SizedIterable<DataPointWriteEntity> by DataPointWriteEntity referrersOn DataPointWrites.step

    /**
     * Routing predicates, in evaluation order. Evaluated after [writes] land.
     */
    val inquisitions: SizedIterable<InquisitionEntity>
        get() = InquisitionEntity
            .find { Inquisitions.step eq id }
            .orderBy(Inquisitions.executionOrder to SortOrder.ASC)

    /**
     * Junctions leaving this step.
     */
    val junctions: SizedIterable<WorkflowJunctionEntity>
            by WorkflowJunctionEntity referrersOn WorkflowJunctions.sourceStep

    /**
     * The fallthrough next step. Null means this step is terminal.
     */
    val defaultRoute: StepDefaultRouteEntity?
        get() = StepDefaultRouteEntity.findById(id)

    /**
     * Steps that route into this one by default.
     */
    val routedFrom: SizedIterable<StepDefaultRouteEntity>
            by StepDefaultRouteEntity referrersOn StepDefaultRoutes.nextStep

    val tags: SizedIterable<TagEntity> by TagEntity.via(StepTags.step, StepTags.tag)
}
