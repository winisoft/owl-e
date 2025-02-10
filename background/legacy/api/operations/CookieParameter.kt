package com.wini.owle.air.api.operations

import com.wini.owle.air.api.models.Property
import java.math.BigDecimal

data class CookieParameter(
    val `in`: String? = null,
    val name: String? = null,
    val description: String? = null,
    val access: String? = null,
    val pattern: String? = null,
    val readOnly: Boolean? = null,
    val vendorExtensions: Map<String, Boolean> = LinkedHashMap(),
    val required: Boolean = false,
    val type: String? = null,
    val format: String? = null,
    val collectionFormat: String? = null,
    val items: Property? = null,
    val exclusiveMaximum: Boolean? = null,
    val maximum: BigDecimal? = null,
    val exclusiveMinimum: Boolean? = null,
    val minimum: BigDecimal? = null,
    val example: String? = null,
    val maxItems: Int? = null,
    val minItems: Int? = null,
    val allowEmptyValue: Boolean? = null,
    val enum: List<String>? = null,
    val maxLength: Int? = null,
    val minLength: Int? = null,
    val uniqueItems: Boolean? = null,
    val multipleOf: Number? = null,
    val defaultValue: String? = null
)