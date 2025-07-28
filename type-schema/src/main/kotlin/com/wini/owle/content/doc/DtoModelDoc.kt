package com.wini.owle.ksp.doc.models



/**
 * Represents a body of documentation regarding a single type extending from BaseDto.
 *
 * @property name The simple name of the class (i.e., User::class.simpleName).
 * @property description Documentation extracted from the class-level KDoc, or a fallback if missing.
 * @property properties A list of documented fields belonging to this model type, including theeir own names, types and descriptions.
 */
data class DtoModelDoc(
    val name: String,
    val description: String,
    val properties: List<ModelPropertyDoc>
)