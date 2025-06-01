package com.wini.owle.dto.transforms

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.transforms.enums.TransformOccurrence
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.error.PositiveValueULongError
import kotlinx.serialization.Serializable

/**
 * Represents an instance of an applied transformation.
 * Each applied transformation has a type, may recursively reference a previous applied transform,
 * and specifies the result type it produces.
 */
@ModelDefinition
@Serializable
data class TransformOccurrence(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    /**
     * The transformation from which this applied instance originates.
     */
    val transformation: Transform,

    /**
     * Describes the type of occurrence for the transformation (e.g., 'provision', 'revision').
     */
    val occurrence: TransformOccurrence,

    /**
     * The type of the result produced by this transformation.
     */
    val resultType: TypeNode,

) : ULongDto