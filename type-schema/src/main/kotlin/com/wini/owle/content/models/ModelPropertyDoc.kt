package com.wini.owle.ksp.doc.models



/**
 * Represents documentation for an individual property on a DTO class.
 *
 * @property name The name of the property (e.g., "email").
 * @property type Fully qualified Kotlin type name of the property (e.g., "kotlin.String").
 * @property description KDoc for the property, or a fallback string if no documentation is present.
 * @property constraints A list of validation constraints associated with this property.
 */
data class ModelPropertyDoc(
    val name: String,
    val type: String,
    val description: String,
    val constraints: List<OwleErrorDoc>
)
