package com.wini.owle.error

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable
import kotlin.reflect.KProperty1



@Serializable
data class TimestampEnforcedValid<T : BaseDto>(
    val timestampField: KProperty1<T, String>,
    val receiver: T
) : CompositeValidation() {

    override val delegateValidations: List<FieldConstraintError> by lazy {
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
    override val delegateValidations: List<FieldConstraintError> by lazy {
        listOf(
            StringLengthMustBeGt(field, receiver, 0),
            StringMustNotBeBlank(field, receiver),
            StringLengthMustBeLt(field, receiver, maxLength),
            StringContainsRestrictedChars(field, receiver)
        )
    }

}


/*

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringMustNotContainRestricted(
    val errorCode: String = StringRestrictedChars.ERROR_CODE,
    val message: String = StringRestrictedChars.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ResetTokenCharsOnly(
    val errorCode: String = InvalidResetTokenFormat.ERROR_CODE,
    val message: String = InvalidResetTokenFormat.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class OAuthProviderEnum(
    val errorCode: String = InvalidResetTokenFormat.ERROR_CODE,
    val message: String = InvalidResetTokenFormat.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class OneSpecialCharMinimum(
    val errorCode: String = MustHaveOneSpecialChar.ERROR_CODE,
    val message: String = MustHaveOneSpecialChar.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class OneUppercaseMinimum(
    val errorCode: String = MustHaveOneCapitalLetter.ERROR_CODE,
    val message: String = MustHaveOneCapitalLetter.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class OneLowercaseMinimum(
    val errorCode: String = MustHaveOneLowercaseLetter.ERROR_CODE,
    val message: String = MustHaveOneLowercaseLetter.MESSAGE
)

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class OneDigitMinimum(
    val errorCode: String = MustHaveOneNumeral.ERROR_CODE,
    val message: String = MustHaveOneNumeral.MESSAGE
)
 */