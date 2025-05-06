package com.wini.owle.error

import kotlinx.serialization.Serializable

sealed class ApiRequestError: OwleError


@Serializable
data object RequestBodyMalformed : ApiRequestError() {
    override val errorCode: String get() = "MALFORMED_REQUEST_BODY"
    override val message: String get() = "Request body is missing or not readable as JSON"
    override val detail: String? get() = null
}

@Serializable
data class MultipleTypeValidations(
    val violations: List<FieldConstraintError>
): ApiRequestError() {
    override val errorCode: String get() = "TYPE_INVALID"
    override val message: String get() = "One or more of the constraints on valid type definitions was violated."
    override val detail: String? get() = null
}

@Serializable
data class MultipleParamFailure(
    val failed: List<OwleError>
) : ApiRequestError() {
    override val errorCode: String get() = "MULTI_PARAM_FAILURE"
    override val message: String get() = "The required values were not provided in valid format."
    override val detail: String? get() = null
}

@Serializable
data class MissingParameter(
    val paramKey: String
) : ApiRequestError() {
    override val errorCode = "MISSING_PARAM_VALUE"
    override val message = "Missing required parameter"
    override val detail = "'$paramKey' was not provided"
}

@Serializable
data object AuthorizationError : ApiRequestError() {
    override val errorCode = "AUTHORIZATION_ERROR"
    override val message = "Authorization Error"
    override val detail = null
}

/**
 * An error whose origin is in the repository layer, which presumably has faithfully informed that the id that had
 * been referenced for selection or deletion did not exist. This indicates a disconnect between clients interacting
 * with the database and the application's efficacy representing the concurrent operations globally
 */
@Serializable
data object NoSuchIdError : ApiRequestError() {
    override val errorCode = "NO_SUCH_ID"
    override val message = "No such ID"
    override val detail = null
}

@Serializable
data object EmptyListNotAllowed : ApiRequestError() {
    override val errorCode: String get() = "BODY_EMPTY_LIST"
    override val message: String get() = "Request body contained no items"
    override val detail: String? get() = null
}

@Serializable
data object AuthenticationError : ApiRequestError() {
    override val errorCode = "AUTHENTICATION_ERROR"
    override val message = "Authentication Error"
    override val detail = null
}


@Serializable
data class ParamValueMalformedLong(
    val paramKey: String,
    val formatDescr: String = "number"
) : ApiRequestError() {
    override val errorCode = "MALFORMED_PARAM_VALUE"
    override val message = "Parameter provided in an invalid format."
    override val detail = "'$paramKey' must be a valid whole number."
}