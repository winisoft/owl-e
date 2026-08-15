package com.wini.owle.entity.steps

import com.wini.owle.entity.transforms.TransformOccurrences
import com.wini.owle.entity.typing.CallableParameters
import com.wini.owle.entity.typing.CallableSignatures
import com.wini.owle.entity.typing.TypeNodes
import com.wini.owle.entity.workflows.DataPoints
import com.wini.owle.entity.workflows.Steps
import com.wini.owle.entity.workflows.Workflows
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IdTable
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable


object Tools : ULongIdTable("tools") {
    val signature = reference("signature_id", CallableSignatures).nullable()
    val name = text("name")
    val description = varchar("description", 8192).nullable()
    val body = text("body")
    val returnType = reference("return_type_node_id", TypeNodes, onDelete = ReferenceOption.SET_NULL).nullable()
}


object ErrorWatches : ULongIdTable("error_watches") {
    val step = reference("step_id", Steps)
    val errorType = varchar("error_type", 255)
    val triggerStep = reference("trigger_step_id", Steps)
    val workflow = reference("workflow_id", Workflows)
}

object ErrorWatchFns : ULongIdTable("error_watch_fns") {
    val errorWatch = reference("error_watch_id", ErrorWatches, onDelete = ReferenceOption.CASCADE)
    val functionNode = reference("function_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val position = ulong("position")

    init {
        uniqueIndex("errorWatchId", errorWatch, position)
        uniqueIndex("errorWatchId_2", errorWatch, functionNode, position)
    }
}


/**
 * A routing predicate attached to a step's completion (see docs/REPO-STATE.md §3): its own Python
 * source lives in `body`, evaluated after the step's writes land. `note` is author-facing only.
 */
object Inquisitions : ULongIdTable("inquisitions") {
    val step = reference("step_id", Steps)
    val note = varchar("note", 8192).nullable()
    val executionOrder = ulong("execution_order")
    val signature = reference("signature_id", CallableSignatures).nullable()
    val body = text("body")
}

object InquisitionDataPointReads : CompositeIdTable("inquisition_data_point_reads") {
    val inquisition = reference("inquisition_id", Inquisitions, onDelete = ReferenceOption.CASCADE)
    val callableParameter = reference("callable_parameter_id", CallableParameters, onDelete = ReferenceOption.CASCADE)
    val dataPoint = reference("data_point_id", DataPoints)
    val transformOccurrence = reference("transform_occurrence_id", TransformOccurrences).nullable()
    override val primaryKey = PrimaryKey(inquisition, callableParameter)
}

/**
 * Maps 1:1 onto Inquisitions.id — when an inquisition transitions workflow rather than routing
 * to a step, this row (rather than a `trigger_step_id`) carries the target junction.
 */
object InquisitionJunctionRedirects : IdTable<ULong>("inquisition_junction_redirects") {
    override val id: Column<EntityID<ULong>> = reference("inquisition_id", Inquisitions)
    val junction = reference("junction_id", WorkflowJunctions)
    val step = reference("step_id", Steps)
    override val primaryKey = PrimaryKey(id)
}

/**
 * Maps 1:1 onto Inquisitions.id — the trigger step for an inquisition that routes within the
 * same workflow (as opposed to across a junction; see [InquisitionJunctionRedirects]).
 */
object InquisitionStepRedirects : IdTable<ULong>("inquisition_step_redirects") {
    override val id: Column<EntityID<ULong>> = reference("inquisition_id", Inquisitions)
    val step = reference("step_id", Steps)
    val workflow = reference("workflow_id", Workflows)
    override val primaryKey = PrimaryKey(id)
}


object StepDataPointReads : CompositeIdTable("step_data_point_reads") {
    val step = reference("step_id", Steps, onDelete = ReferenceOption.CASCADE)
    val callableParameter = reference("callable_parameter_id", CallableParameters, onDelete = ReferenceOption.CASCADE)
    val dataPoint = reference("data_point_id", DataPoints)
    val transformOccurrence = reference("transform_occurrence_id", TransformOccurrences).nullable()
    override val primaryKey = PrimaryKey(step, callableParameter)
}


object DataPointWrites : ULongIdTable("data_point_writes") {
    val dataPoint = reference("data_point_id", DataPoints, onDelete = ReferenceOption.CASCADE)
    val step = reference("step_id", Steps, onDelete = ReferenceOption.CASCADE)
    val transformOccurrence = reference("transform_occurrence_id", TransformOccurrences, onDelete = ReferenceOption.CASCADE).nullable()
}

object DataPointWriteArgs : CompositeIdTable("data_point_write_args") {
    val dataPointWrite = reference("data_point_write_id", DataPointWrites, onDelete = ReferenceOption.CASCADE)
    val dataPoint = reference("data_point_id", DataPoints, onDelete = ReferenceOption.CASCADE)
    val position = uinteger("position")
    override val primaryKey = PrimaryKey(dataPointWrite, position)
}


object StepDefaultRoutes : IdTable<ULong>("step_default_routes") {
    override val id: Column<EntityID<ULong>> = reference("step_id", Steps, onDelete = ReferenceOption.CASCADE)
    val nextStep = reference("next_step_id", Steps)
    val workflow = reference("workflow_id", Workflows)
    override val primaryKey = PrimaryKey(id)
}


object WorkflowJunctions : ULongIdTable("workflow_junctions") {
    val sourceStep = reference("src_step_id", Steps)
    val targetWorkflow = reference("target_workflow_id", Workflows)
}

object DataPointInjections : ULongIdTable("data_point_injections") {
    val junction = reference("junction_id", WorkflowJunctions)
    val sourceDataPoint = reference("src_data_point_id", DataPoints)
    val targetDataPoint = reference("target_data_point_id", DataPoints)
    val name = varchar("name", 255).nullable()
    val description = varchar("description", 8192).nullable()
}
