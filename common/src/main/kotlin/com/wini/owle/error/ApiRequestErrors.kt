package com.wini.owle.error

import com.wini.owle.schema.error.OwleDocsError
import kotlinx.serialization.Serializable

sealed class ApiRequestError: OwleError

@OwleDocsError("errors.apiRequest.requestBodyMalformed")
@Serializable
data object RequestBodyMalformed : ApiRequestError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail: String? get() = null
}

@OwleDocsError("errors.apiRequest.multipleTypeValidations")
@Serializable
data class MultipleTypeValidations(
    val violations: List<FieldConstraintError>
): ApiRequestError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail: String? get() = null
}

@OwleDocsError("errors.apiRequest.multipleParamFailure")
@Serializable
data class MultipleParamFailure(
    val failed: List<OwleError>
) : ApiRequestError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail: String? get() = null
}

@OwleDocsError("errors.apiRequest.missingParameter")
@Serializable
data class MissingParameter(
    val paramKey: String
) : ApiRequestError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail = "'$paramKey' was not provided"
}

@OwleDocsError("errors.apiRequest.authorizationError")
@Serializable
data object AuthorizationError : ApiRequestError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail = null
}

/**
 * An error whose origin is in the repository layer, which presumably has faithfully informed that the id that had
 * been referenced for selection or deletion did not exist. This indicates a disconnect between clients interacting
 * with the database and the application's efficacy representing the concurrent operations globally
 */
@OwleDocsError("errors.apiRequest.noSuchIdError")
@Serializable
data object NoSuchIdError : ApiRequestError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail = null
}

/**
 *
 */
@OwleDocsError("errors.apiRequest.batchOpOfNoDataObjects")
@Serializable
data object BatchOpOfNoDataObjects : ApiRequestError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail: String? get() = null
}

@OwleDocsError("errors.apiRequest.authenticationError")
@Serializable
data object AuthenticationError : ApiRequestError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail = null
}


@OwleDocsError("errors.apiRequest.paramValueMalformedLong")
@Serializable
data class ParamValueMalformedLong(
    val paramKey: String,
    val formatDescr: String = "number"
) : ApiRequestError() {
    override val errorCode: String get() = errorResCode()
    override val message: String get() = errorResMsg()
    override val detail = "'$paramKey' must be a valid whole number."
}