package com.wini.owle.error

import kotlinx.serialization.Serializable

@Serializable
data class MultipleTypeValidations(
    val violations: List<FieldValidation>
): OwleError {
    override val errorCode: String get() = "TYPE_INVALID"
    override val message: String get() = "One or more of the constraints on valid type definitions was violated."
    override val detail: String? get() = null
}