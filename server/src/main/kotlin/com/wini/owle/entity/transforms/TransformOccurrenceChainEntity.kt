package com.wini.owle.entity.transforms

import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * Orders two transform applications at the same site, so a value can be passed through several
 * transforms in sequence.
 */
class TransformOccurrenceChainEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<TransformOccurrenceChainEntity>(TransformOccurrenceChains)

    /**
     * The occurrence that runs first.
     */
    var antecedentOccurrence by TransformOccurrenceEntity referencedOn TransformOccurrenceChains.antecedentOccurrence

    /**
     * The occurrence that consumes its output.
     */
    var subsequentOccurrence by TransformOccurrenceEntity referencedOn TransformOccurrenceChains.subsequentOccurrence
}
