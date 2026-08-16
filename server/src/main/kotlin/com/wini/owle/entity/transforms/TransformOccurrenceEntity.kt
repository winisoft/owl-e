package com.wini.owle.entity.transforms

import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.StepEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * One application of a [TransformEntity] at a concrete site.
 *
 * The source is exactly one of [dataPoint] or [toolResultStep] — a table CHECK enforces it —
 * so a transform either re-shapes a stored value or the raw return of a step's tool.
 */
class TransformOccurrenceEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TransformOccurrenceEntity>(TransformOccurrences)

    /**
     * The transformation from which this applied instance originates.
     */
    var transform by TransformEntity referencedOn TransformOccurrences.transform

    /**
     * Which kind of site this is: read, write, inquisition, or inject.
     */
    var occurrence by TransformOccurrences.occurrence

    /**
     * Source data point, when the input is a stored value.
     */
    var dataPoint by DataPointEntity optionalReferencedOn TransformOccurrences.dataPoint

    /**
     * Source step, when the input is that step's raw tool result.
     */
    var toolResultStep by StepEntity optionalReferencedOn TransformOccurrences.toolResultStep

    /**
     * Bound argument values for this application.
     */
    val args: SizedIterable<TransformOccurrenceArgEntity>
            by TransformOccurrenceArgEntity referrersOn TransformOccurrenceArgs.transformOccurrence

    /**
     * Occurrences that run after this one in a chain.
     */
    val subsequent: SizedIterable<TransformOccurrenceEntity>
            by TransformOccurrenceEntity.via(
                TransformOccurrenceChains.antecedentOccurrence,
                TransformOccurrenceChains.subsequentOccurrence
            )

    /**
     * Occurrences that run before this one in a chain.
     */
    val antecedent: SizedIterable<TransformOccurrenceEntity>
            by TransformOccurrenceEntity.via(
                TransformOccurrenceChains.subsequentOccurrence,
                TransformOccurrenceChains.antecedentOccurrence
            )
}
