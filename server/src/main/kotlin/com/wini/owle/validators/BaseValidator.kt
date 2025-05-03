package com.wini.owle.validators

import com.wini.owle.dto.BaseDto

/**
 * Base implementation of DtoValidator that provides common validation logic.
 * Concrete validators should extend this class and implement the validate method.
 */
abstract class BaseValidator<T : BaseDto> : DtoValidator<T> {

} 