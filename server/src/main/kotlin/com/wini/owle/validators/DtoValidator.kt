package com.wini.owle.validators

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.BaseDto

/**
 * Container for validation results that can hold multiple validation errors.
 */
class ValidationResult private constructor(
    val violations: List<TypeValidation>
) {
    /**
     * Whether the validation passed with no errors.
     */
    val isValid: Boolean get() = violations.isEmpty()
    
    /**
     * Converts this validation result to an AsyncResult.
     * If validation passed, returns Success with the provided item.
     * If validation failed, returns Failure with MultipleTypeValidations.
     */
    fun <T> asResult(item: T): AsyncResult<T> {
        return if (isValid) {
            AsyncResult.Success(item)
        } else {
            AsyncError.ApiRequestError.MultipleTypeValidations(violations).asFailure()
        }
    }
    
    /**
     * Converts this validation result to an AsyncResult.Failure.
     */
    fun asFailure(): AsyncResult.Failure {
        return AsyncError.ApiRequestError.MultipleTypeValidations(violations).asFailure()
    }
    
    companion object {
        /**
         * Creates a successful validation result with no violations.
         */
        fun valid(): ValidationResult = ValidationResult(emptyList())
        
        /**
         * Creates a validation result with the provided violations.
         */
        fun invalid(vararg violations: TypeValidation): ValidationResult = 
            ValidationResult(violations.toList())
        
        /**
         * Creates a validation result with the provided violations.
         */
        fun invalid(violations: List<TypeValidation>): ValidationResult = 
            ValidationResult(violations)
    }
}

/**
 * Interface for validating DTOs in API endpoints.
 * This replaces the previous (T) -> Boolean function approach with a more structured interface.
 */
interface DtoValidator<T : BaseDto> {
    /**
     * Validates a DTO against business rules and constraints.
     * @param dto The DTO to validate
     * @return ValidationResult containing any validation errors
     */
    fun validate(dto: T): ValidationResult
    
    /**
     * Validates a list of DTOs, ensuring all are valid.
     * @param dtos The list of DTOs to validate
     * @return ValidationResult containing any validation errors from all DTOs
     */
    fun validateAll(dtos: List<T>): ValidationResult {
        val allViolations = dtos.flatMap { validate(it).violations }
        return if (allViolations.isEmpty()) {
            ValidationResult.valid()
        } else {
            ValidationResult.invalid(allViolations)
        }
    }
    
    companion object {
        /**
         * Creates a simple validator that always validates successfully.
         * Useful for cases where no validation is needed.
         */
        fun <T : BaseDto> alwaysValid(): DtoValidator<T> = object : DtoValidator<T> {
            override fun validate(dto: T): ValidationResult = ValidationResult.valid()
        }
        
        /**
         * Creates a validator from a function.
         * Useful for simple validation cases or for backward compatibility.
         */
        fun <T : BaseDto> fromFunction(validatorFn: (T) -> Boolean): DtoValidator<T> = object : DtoValidator<T> {
            override fun validate(dto: T): ValidationResult {
                return if (validatorFn(dto)) {
                    ValidationResult.valid()
                } else {
                    ValidationResult.invalid(
                        TypeValidation.Generic(
                            idString = dto.idToString(),
                            fieldName = "unknown",
                            message = "Validation failed"
                        )
                    )
                }
            }
        }
    }
} 