package com.wini.owle.air.api.operations

import com.wini.owle.air.api.models.Property
import java.math.BigDecimal

interface SerializableParameter : Parameter {
    val type: String?
    val items: Property?
    val format: String?
    val collectionFormat: String?
    val enum: List<String?>?
    val enumValue: List<Any?>?
    val maxLength: Int?
    val minLength: Int?

    // io.swagger.models.parameters.Parameter
    // io.swagger.models.parameters.Parameter
    val pattern: String?

    val isUniqueItems: Boolean?
    val multipleOf: Number?
    val isExclusiveMaximum: Boolean?
    val isExclusiveMinimum: Boolean?
    val maximum: BigDecimal?
    val minimum: BigDecimal?
    val maxItems: Int?
    val minItems: Int?
    val allowEmptyValue: Boolean?
}
