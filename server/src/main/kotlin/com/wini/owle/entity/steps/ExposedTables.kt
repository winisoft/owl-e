package com.wini.owle.entity.steps

import com.wini.owle.entity.transforms.TransformOccurrences
import com.wini.owle.entity.typing.CallableSignatures
import com.wini.owle.entity.typing.TypeNodes
import com.wini.owle.entity.workflows.DataPoints
import com.wini.owle.entity.workflows.Steps
import com.wini.owle.entity.workflows.Workflows
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable


object ErrorWatches : ULongIdTable("error_watches") {
    val step = reference("step_id", Steps, onDelete = ReferenceOption.CASCADE)
    val errorType = varchar("error_type", 255)
    val triggerStep = reference("trigger_step_id", Steps, onDelete = ReferenceOption.CASCADE)
}

object ErrorWatchFns : ULongIdTable("error_watch_fns") {
    val errorWatch = reference("error_watch_id", ErrorWatches, onDelete = ReferenceOption.CASCADE)
    val functionName = reference("function_node_id", TypeNodes)
    val position = integer("position")
    init {
        uniqueIndex("unique_error_watch_position", errorWatch, position)
        uniqueIndex("unique_error_watch_function_position", errorWatch, functionName, position)
    }
}


object Inquisitions : ULongIdTable("inquisitions") {
    val step = reference("step_id", Steps, onDelete = ReferenceOption.CASCADE)
    val condition = reference("condition_node_id", TypeNodes, ReferenceOption.CASCADE)
    val dataPoint = reference("data_point_id", DataPoints, onDelete = ReferenceOption.CASCADE)
    val triggerStep = reference("trigger_step_id", Steps, onDelete = ReferenceOption.CASCADE)
    val executionOrder = integer("execution_order")
}

object InquisitionArgs : CompositeIdTable("inquisition_args") {
    val inquisition = reference("inquisition_id", Inquisitions, onDelete = ReferenceOption.CASCADE)
    val dataPoint = reference("data_point_id", DataPoints, onDelete = ReferenceOption.CASCADE)
    val position = uinteger("position")
}


object DataPointReads : ULongIdTable("data_point_reads") {
    val step = reference("step_id", Steps, onDelete = ReferenceOption.CASCADE)
    val toolParam = reference("tool_param_id", ToolParams, onDelete = ReferenceOption.CASCADE)
}


object DataPointWriteLinks : CompositeIdTable("provision_data_points") {
    val provision = reference("provision_id", DataPointReads, onDelete = ReferenceOption.CASCADE)
    val dataPoint = reference("data_point_id", DataPoints, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(provision, dataPoint)
}


object DataPointWrites : ULongIdTable("data_point_writes") {
    val dataPoint = reference("data_point_id", DataPoints, onDelete = ReferenceOption.CASCADE)
    val step = reference("step_id", Steps, onDelete = ReferenceOption.CASCADE)
    val transform = reference("transform_occurrence_id", TransformOccurrences, onDelete = ReferenceOption.CASCADE).nullable()
}

object DataPointWriteArgs : CompositeIdTable("data_point_write_args") {
    val revision = reference("revision_id", DataPointWrites, onDelete = ReferenceOption.CASCADE)
    val dataPoint = reference("data_point_id", DataPoints, onDelete = ReferenceOption.CASCADE)
    val position = uinteger("position")
}

object Tools : ULongIdTable("tools") {
    val name = varchar("name", 512)
    val description = varchar("description", 8192).nullable()
    val signatureNode = reference("signature_node_id", CallableSignatures, ReferenceOption.CASCADE).nullable()
    val body = text("body")
    val returnType = reference("return_type_node_id", TypeNodes, ReferenceOption.CASCADE).nullable()
}

object ToolParams : ULongIdTable("tool_params") {
    val tool = reference("tool_id", Tools, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255)
    val dataType = reference("type_node_id", TypeNodes, onDelete = ReferenceOption.CASCADE)
    val defaultValue = text("defaultValue").nullable()
}

object WorkflowJunctions : ULongIdTable("workflow_junctions") {
    val sourceStep = reference("src_step_id", Steps, onDelete = ReferenceOption.CASCADE)
    val targetWorkflow = reference("target_workflow_id", Workflows, onDelete = ReferenceOption.CASCADE)
    val triggerInquisition = reference("inquisition_id", Inquisitions, onDelete = ReferenceOption.CASCADE).nullable()
}

object DataPointInjections : ULongIdTable("prereq_injections") {
    val junction = reference("junction_id", WorkflowJunctions, onDelete = ReferenceOption.CASCADE)
    val sourceDataPoint = reference("src_data_point_id", DataPoints, onDelete = ReferenceOption.CASCADE)
    val targetDataPoint = reference("target_data_point_id", DataPoints, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255).nullable()
    val description = varchar("description", 8192).nullable()
}

