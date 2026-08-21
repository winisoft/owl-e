package com.wini.owle.entity.transforms

import com.wini.owle.entity.OwleCompositeIdTable

import com.wini.owle.entity.dbEnum

import com.wini.owle.dto.transforms.enums.TransformArgSource
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

    /** Which of the columns below carries this argument's value. */
    val sourceKind = dbEnum<TransformArgSource>("source_kind", 24)

    /** The literal, when [sourceKind] is [TransformArgSource.LITERAL]. Null otherwise. */
    val value = text("value").nullable()

    val sourceDataPoint = reference("source_data_point_id", DataPoints).nullable()

    /** The step whose tool result is being passed. */
    val sourceStep = reference("source_step_id", Steps).nullable()

    /**
     * Another occurrence, whose result is this argument.
     *
     * One column for three of the cases a user would describe separately: a transformed read, a
     * transformed tool result, and the far end of a chain are all one occurrence's output.
     */
    val sourceOccurrence = reference("source_occurrence_id", TransformOccurrences).nullable()

    init {
        // Exactly one source, and it agrees with the discriminator - the same shape
        // `TransformOccurrences` uses for its own subject, rather than a second idiom.
        check("chk_transform_occurrence_arg_source") {
            ((sourceKind eq TransformArgSource.LITERAL) and value.isNotNull() and
                sourceDataPoint.isNull() and sourceStep.isNull() and sourceOccurrence.isNull()) or
            ((sourceKind eq TransformArgSource.DATA_POINT) and value.isNull() and
                sourceDataPoint.isNotNull() and sourceStep.isNull() and sourceOccurrence.isNull()) or
            ((sourceKind eq TransformArgSource.TOOL_RESULT) and value.isNull() and
                sourceDataPoint.isNull() and sourceStep.isNotNull() and sourceOccurrence.isNull()) or
            ((sourceKind eq TransformArgSource.TRANSFORM_RESULT) and value.isNull() and
                sourceDataPoint.isNull() and sourceStep.isNull() and sourceOccurrence.isNotNull())
        }
    }
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
