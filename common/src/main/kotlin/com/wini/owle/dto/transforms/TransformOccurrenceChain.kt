package com.wini.owle.dto.transforms

import com.wini.owle.dto.CompositeDto
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * Orders two transform applications so a value passes through both in sequence.
 */
@ModelDefinition
@Serializable
data class TransformOccurrenceChain(

    val antecedentOccurrence: TransformOccurrence,

    val subsequentOccurrence: TransformOccurrence,

) : CompositeDto {

    override val owleId: String get() = "antecedent=${antecedentOccurrence.owleId},subsequent=${subsequentOccurrence.owleId}"
}
