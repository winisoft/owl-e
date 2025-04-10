package com.wini.owle.api

import com.wini.owle.async.AsyncError
import com.wini.owle.async.TypeValidation
import com.wini.owle.validators.DtoValidator
import com.wini.owle.dto.BaseDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.async.TypeValidation.StringEmpty
import com.wini.owle.async.TypeValidation.ListNotEmpty


/**
 * Utility class to create test validators for the test suite.
 */
object TestValidators {
    
    /**
     * Creates a validator that always passes validation.
     * 
     * @return A validator that always returns true
     */
    fun <T : BaseDto> alwaysValid(): DtoValidator<T> = DtoValidator.alwaysValid()

    /**
     * Creates a validator that returns the specified result.
     *
     * @param result The result to return from the validator
     * @return A validator that returns the specified result
     */
    fun <T : BaseDto> stringEmptyWithId1OnName(
        stringEmpty: StringEmpty = StringEmpty(TYPE_NAME, ID_STRING, FIELD_NAME)
    ): DtoValidator<T> = object : DtoValidator<T> {
        override fun validate(dto: T): ValidationResult = ValidationResult(
            listOf(
                stringEmpty
            )
        )
    }

    val TYPE_NAME = "FakeType"
    val ID_STRING = "id={1}"
    val FIELD_NAME = "description"
    val TYPE_VALID_ERR_STR_EMPTY = StringEmpty(TYPE_NAME, ID_STRING, FIELD_NAME)
    val API_ERRORS_1_STR_EMPTY = listOf(ApiError(
        code = TYPE_VALID_ERR_STR_EMPTY.errorCode,
        message = TYPE_VALID_ERR_STR_EMPTY.message,
        detail = "Id: [$ID_STRING] Field: [$FIELD_NAME]"
    ))

} 