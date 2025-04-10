package com.wini.owle.validators

import com.wini.owle.dto.BaseDto

/**
 * Base implementation of DtoValidator that provides common validation logic.
 * Concrete validators should extend this class and implement the validate method.
 */
abstract class BaseValidator<T : BaseDto> : DtoValidator<T> {
    
    /**
     * Validates that a string field is not empty if it's present.
     * 
     * @param value The string value to check
     * @param fieldName The name of the field (for error reporting)
     * @return true if the field is valid, false otherwise
     */
    protected fun validateStringNotEmpty(value: String?, fieldName: String): Boolean {
        return value == null || value.isNotEmpty()
    }
    
    /**
     * Validates that a string field is not blank if it's present.
     * 
     * @param value The string value to check
     * @param fieldName The name of the field (for error reporting)
     * @return true if the field is valid, false otherwise
     */
    protected fun validateStringNotBlank(value: String?, fieldName: String): Boolean {
        return value == null || value.isNotBlank()
    }
    
    /**
     * Validates that a string does not exceed a maximum length.
     * 
     * @param value The string value to check
     * @param maxLength The maximum allowed length
     * @param fieldName The name of the field (for error reporting)
     * @return true if the field is valid, false otherwise
     */
    protected fun validateMaxLength(value: String?, maxLength: Int, fieldName: String): Boolean {
        return value == null || value.length <= maxLength
    }
    
    /**
     * Validates that a numeric value is within a specified range.
     * 
     * @param value The numeric value to check
     * @param min The minimum allowed value
     * @param max The maximum allowed value
     * @param fieldName The name of the field (for error reporting)
     * @return true if the field is valid, false otherwise
     */
    protected fun validateRange(value: Number?, min: Number, max: Number, fieldName: String): Boolean {
        return value == null || (value.toDouble() >= min.toDouble() && value.toDouble() <= max.toDouble())
    }
    
    /**
     * Validates that a list field is not empty if it's present.
     * 
     * @param value The list to check
     * @param fieldName The name of the field (for error reporting)
     * @return true if the field is valid, false otherwise
     */
    protected fun validateListNotEmpty(value: List<*>?, fieldName: String): Boolean {
        return value == null || value.isNotEmpty()
    }
} 