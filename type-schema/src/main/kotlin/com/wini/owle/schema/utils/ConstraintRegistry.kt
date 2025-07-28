package com.wini.owle.ksp.utils

object ConstraintRegistry {

    val annoQualifiedNames: List<String>
        get() = listOf(
            "PositiveValueLong",
            "StringSafeChars",
            "PasswordSecurityPolicy",
            "ResetTokenCharsOnly",
            "StringNotBlank",
            "ListHasMembers",
            "EmailFormat",
            "UrlFormat",
            "TimestampIso8601Format",
            "TimestampCoherent",
            "NumeralsOnly",
            "PositiveValueInt",
            "StringNotEmpty",
            "OAuthProviderEnum",
            "StringLengthMax",
            "StringLengthExact",
            "IntValueMin",
            "LongValueMin",
            "DoubleInRange"
        ).map { "com.wini.owle.schema.$it" }

    private val errorQualifiedNameByConstraintAnnotationQualifiedName = mapOf(
        "com.wini.owle.schema.constraints.StringLengthMin" to "com.wini.owle.error.StringLengthMustBeGt",
        "com.wini.owle.schema.constraints.StringMaxLength" to "com.wini.owle.error.StringMaxLengthError",
        "com.wini.owle.schema.error.StringSafeChars" to "com.wini.owle.error.StringSafeCharsError",
        "com.wini.owle.schema.error.StringNotBlank" to "com.wini.owle.error.StringNotBlankError",
        "com.wini.owle.schema.error.PositiveValueLong" to "com.wini.owle.error.PositiveValueLongError",
        "com.wini.owle.schema.error.PositiveValueInt" to "com.wini.owle.error.PositiveValueIntError",
        "com.wini.owle.schema.error.TimestampIso8601Format" to "com.wini.owle.error.TimestampFormatError",
        "com.wini.owle.schema.error.TimestampCoherent" to "com.wini.owle.error.IncoherentInstantError",
        "com.wini.owle.schema.error.ListHasMembers" to "com.wini.owle.error.ListMustNotBeEmpty",
        "com.wini.owle.schema.error.EmailFormat" to "com.wini.owle.error.ValidUrlFormatError",
        "com.wini.owle.schema.error.UrlFormat" to "com.wini.owle.error.InvalidUrlFormatError",
        "com.wini.owle.schema.error.NumeralsOnly" to "com.wini.owle.error.MustContainOnlyNumerals",
        "com.wini.owle.schema.error.StringLengthExact" to "com.wini.owle.error.StringLengthExactError",
        "com.wini.owle.schema.error.StringNotEmpty" to "com.wini.owle.error.StringNotEmptyError"
    )

    val constraintAnnotationFqns: Set<String> get() = errorQualifiedNameByConstraintAnnotationQualifiedName.keys

    fun getErrorQualifiedName(constraintAnnotationFqn: String): String? {
        return errorQualifiedNameByConstraintAnnotationQualifiedName[constraintAnnotationFqn]
    }

}