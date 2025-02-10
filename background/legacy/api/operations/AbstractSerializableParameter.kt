package com.wini.owle.air.api.operations

import com.wini.owle.air.api.models.Property
import com.wini.owle.air.api.operations.AbstractParameter
import java.math.BigDecimal

abstract class AbstractSerializableParameter<T>(
    override val type: String?,
    override val format: String?,
    override val collectionFormat: String?,
    override val items: Property?,
    override val exclusiveMaximum: Boolean,
    override val maximum: BigDecimal,
    override val exclusiveMinimum: Boolean,
    override val minimum: BigDecimal,
    override val example: String,
    override val maxItems: Int,
    override val minItems: Int,
    override val allowEmptyValue: Boolean,
    override val enum: List<String>?,
    override val maxLength: Int?,
    override val minLength: Int?,
    override val pattern: String,
    override val uniqueItems: Boolean,
    override val multipleOf: Number?,
    open val defaultValue: String?
): SerializableParameter where T: AbstractParameter