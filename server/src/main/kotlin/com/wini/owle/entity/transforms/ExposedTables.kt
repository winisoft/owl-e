package com.wini.owle.entity.transforms

import com.wini.owle.entity.OwleCompositeIdTable

import com.wini.owle.entity.dbEnum

import com.wini.owle.dto.transforms.enums.TransformOccurrenceInitiator
import com.wini.owle.entity.typing.CallableSignatures
import com.wini.owle.entity.typing.TypeNodes
import com.wini.owle.entity.workflows.DataPoints
import com.wini.owle.entity.workflows.Steps
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable
import org.jetbrains.exposed.v1.core.or


object Transforms : ULongIdTable("transforms") {
    val name = varchar("name", 255)
    val description = varchar("description", 1024).nullable()
    val examples = varchar("examples", 2046).nullable()
    val signature = reference("signature_id", CallableSignatures).nullable()
}

object TransformArgs : ULongIdTable("transform_args") {
    val transform = reference("transform_id", Transforms)
    val typeNode = reference("type_node_id", TypeNodes)
    val name = varchar("name", 255)
    val description = varchar("description", 8192).nullable()
}

object TransformOccurrences : ULongIdTable("transform_occurrences") {
    val transform = reference("transform_id", Transforms)
    val initiator = dbEnum<TransformOccurrenceInitiator>("initiator", 24)
    val dataPoint = reference("dataPointId", DataPoints).nullable()
    val toolResultStep = reference("toolResultStepId", Steps).nullable()

    init {
        check("chkapplied_transformsSource") {
            (dataPoint.isNotNull() and toolResultStep.isNull()) or
                (dataPoint.isNull() and toolResultStep.isNotNull())
        }
    }
}

/**
 * Table name preserves the DB's own spelling (`occurence`, missing the second `r`).
 */
object TransformOccurrenceArgs : ULongIdTable("transform_occurence_args") {
    val transformOccurrence = reference("transform_occurrence_id", TransformOccurrences)
    val transformArg = reference("transform_arg_id", TransformArgs)
    val value = text("value")
}

object TransformOccurrenceChains : OwleCompositeIdTable("transform_occurrence_chains") {
    val antecedentOccurrence = keyReference("antecedent_occurrence_id", TransformOccurrences, onDelete = ReferenceOption.CASCADE)
    val subsequentOccurrence = keyReference("subsequent_occurrence_id", TransformOccurrences, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(antecedentOccurrence, subsequentOccurrence)
}

object TransformTargetTypes : OwleCompositeIdTable("transform_target_types") {
    val transform = keyReference("transform_id", Transforms, onDelete = ReferenceOption.CASCADE)
    val eligibleTypeNode = keyReference("eligible_type_node_id", TypeNodes)
    val returnTypeNode = reference("return_type_node_id", TypeNodes)
    override val primaryKey = PrimaryKey(transform, eligibleTypeNode)
}
