package com.wini.owle.error


/**
 * A representation of a FieldConstraintError that is a single application comprised of multiple
 * independent validations occurring.
 *
 * @property delegateValidations The list of criteria that together constitute the CompositeValidation
 * @property firstFailure A lazily acquired value exhibiting the current FieldConstraintError when a
 * delegate Validation is being violated; it is null if none apply.
 * @property errorCode A dynamically evaluated reference to the unique errorCode of the OwleError that
 * is currently indicated.
 * @property message A dynamically evaluated reference to the message that represents the unique
 * OwleError currently reflected by an error state
 * @property detail A delegated evaluation of the 'detail text' content and format implemented by the
 * OwleError expressed presently.
 * @throws IllegalStateException Note that the only context in which an OwleError's properties are accessed
 * is during their inclusion in an ApiResponse among the ApiErrors reported; An attempt to access such properties
 * despite their absence appropriately raises this exception.
 */
abstract class CompositeValidation : OwleError, FieldConstraintError() {

    protected abstract val delegateValidations: List<FieldConstraintError>

    protected val firstFailure: FieldConstraintError? by lazy {
        delegateValidations.firstOrNull { it.isInvalid }
    }

    override val isInvalid: Boolean
        get() = firstFailure != null

    override val errorCode: String
        get() = firstFailure?.errorCode ?: throw IllegalStateException("Accessing errorCode but isInvalid == false")

    override val message: String
        get() = firstFailure?.message ?: throw IllegalStateException("Accessing message but isInvalid == false")

    override val detail: String
        get() = firstFailure?.detail ?: throw IllegalStateException("Accessing detail but isInvalid == false")

}