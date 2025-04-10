package com.wini.owle.validators

import com.wini.owle.dto.BaseDto

/**
 * Interface for validating DTOs in API endpoints.
 * This replaces the previous (T) -> Boolean function approach with a more structured interface.
 */
interface DtoValidator<T : BaseDto> {
    /**
     * Validates a DTO against business rules and constraints.
     * @param dto The DTO to validate
     * @return true if the DTO is valid, false otherwise
     */
    fun validate(dto: T): Boolean
    
    /**
     * Validates a list of DTOs, ensuring all are valid.
     * @param dtos The list of DTOs to validate
     * @return true if all DTOs are valid, false if any is invalid
     */
    fun validateAll(dtos: List<T>): Boolean = dtos.all { validate(it) }
    
    companion object {
        /**
         * Creates a simple validator that always validates successfully.
         * Useful for cases where no validation is needed.
         */
        fun <T : BaseDto> alwaysValid(): DtoValidator<T> = object : DtoValidator<T> {
            override fun validate(dto: T): Boolean = true
        }
        
        /**
         * Creates a validator from a function.
         * Useful for simple validation cases or for backward compatibility.
         */
        fun <T : BaseDto> fromFunction(validatorFn: (T) -> Boolean): DtoValidator<T> = object : DtoValidator<T> {
            override fun validate(dto: T): Boolean = validatorFn(dto)
        }
    }
} 