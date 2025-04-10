package com.wini.owle.api

import com.wini.owle.validators.DtoValidator
import com.wini.owle.dto.BaseDto

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
     * Creates a validator that always fails validation.
     * 
     * @return A validator that always returns false
     */
    fun <T : BaseDto> alwaysFailing(): DtoValidator<T> = object : DtoValidator<T> {
        override fun validate(dto: T): Boolean = false
    }
    
    /**
     * Creates a validator that returns the specified result.
     * 
     * @param result The result to return from the validator
     * @return A validator that returns the specified result
     */
    fun <T : BaseDto> fixed(result: Boolean): DtoValidator<T> = object : DtoValidator<T> {
        override fun validate(dto: T): Boolean = result
    }
    
    /**
     * Creates a validator that delegates to the provided function.
     * 
     * @param validator The function to call to validate the DTO
     * @return A validator that calls the provided function
     */
    fun <T : BaseDto> fromFunction(validator: (T) -> Boolean): DtoValidator<T> =
        DtoValidator.fromFunction(validator)
} 