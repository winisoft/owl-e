package com.wini.owle.dto.transforms

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

/**
 * Represents an argument value for an applied transformation.
 */
@Serializable
data class AppliedTransformArg(
    override val id: Long,
    /**
     * The applied transformation this argument belongs to.
     */
    val appliedTransform: AppliedTransform,
    /**
     * The transformation argument definition.
     */
    val transformationArg: TransformArg,
    /**
     * The actual value provided for this argument.
     */
    val value: String
) : StandardDto