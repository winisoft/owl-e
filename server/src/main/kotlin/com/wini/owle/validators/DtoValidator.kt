package com.wini.owle.validators

import com.wini.owle.async.AsyncResult
import com.wini.owle.error.FieldConstraintError
import com.wini.owle.dto.BaseDto
import com.wini.owle.error.MultipleTypeValidations

/**
 * Container for validation results that can hold multiple validation errors.
 */
class ValidationResult(
    val violations: List<FieldConstraintError>
) {
    /**
     * Whether the validation passed with no errors.
     */
    val isValid: Boolean get() = violations.isEmpty()
    
    /**
     * Converts this validation result to an AsyncResult.Failure.
     */
    fun asFailure(): AsyncResult.Failure {
        return MultipleTypeValidations(violations).asFailure()
    }
    
    companion object {
        /**
         * Creates a successful validation result with no violations.
         */
        fun valid(): ValidationResult = ValidationResult(emptyList())
        
        /**
         * Creates a validation result with the provided violations.
         */
        fun invalid(vararg violations: FieldConstraintError): ValidationResult =
            ValidationResult(violations.toList())
        
        /**
         * Creates a validation result with the provided violations.
         */
        fun invalid(violations: List<FieldConstraintError>): ValidationResult =
            ValidationResult(violations)
    }
}

/**
 * Interface for validating DTOs in API endpoints.
 * This replaces the previous (T) -> Boolean function approach with a more structured interface.
 */
interface DtoValidator<T : BaseDto> {

    /**
     * Returns a list of FieldValidations, representing a constraint of the data model enforced on the generic argument T
     * of this DtoValidator.
     *
     * NOTE: The steps defined in the lists have a meaningful relative position that is logically
     * necessary for the successful validation of the type.
     */
    fun fieldValidations(dto: T): List<FieldConstraintError>

    /**
     * Validates a DTO against business rules and constraints.
     * @param dto The DTO to validate
     * @return ValidationResult containing any validation errors
     */
    fun validate(dto: T): ValidationResult {
        val validations = fieldValidations(dto)
        return if (validations.none { it.isInvalid })
            ValidationResult.valid()
        else
            validations.filter { it.isInvalid }.run {
                ValidationResult.invalid(this)
            }
    }
    
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
        fun <T : BaseDto> alwaysValid(): DtoValidator<T> =
            object : DtoValidator<T> {
                override fun fieldValidations(dto: T): List<FieldConstraintError> = emptyList()
                override fun validate(dto: T): ValidationResult = ValidationResult.valid()
            }
    }
} 