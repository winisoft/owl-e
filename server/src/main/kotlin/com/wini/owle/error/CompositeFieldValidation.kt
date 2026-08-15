package com.wini.owle.error

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable
import kotlin.reflect.KProperty1



@Serializable
data class TimestampEnforcedValid<T : BaseDto>(
    val timestampField: KProperty1<T, String>,
    val receiver: T
) : CompositeValidation() {

    override val delegateValidations: List<ConstraintError> by lazy {
        listOf(
            TimestampFormatMustMatchIso8601(timestampField, receiver),
            TimestampMustBeChronologicallyCoherent(timestampField, receiver),
        )
    }
    
}

/**
 * Composite validation to ensure text fields:
 * - Are not empty
 * - Are not just blank/whitespace
 * - Do not exceed a configurable max length
 * - Contain only characters from permitted Unicode categories
 */
@Serializable
class StringEnforcedValid<T : BaseDto>(
    private val field: KProperty1<T, String?>,
    private val maxLength: Int,
    private val provideReceiver: () -> T,
) : CompositeValidation() {
    val receiver: T get() = provideReceiver()
    override val delegateValidations: List<ConstraintError> by lazy {
        listOf(
            StringLengthMustBeGt(field, receiver, 0),
            StringMustNotBeBlank(field, receiver),
            StringLengthMustBeLt(field, receiver, maxLength),
            StringContainsRestrictedChars(field, receiver)
        )
    }

}
