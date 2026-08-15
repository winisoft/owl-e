package com.wini.owle.error

import com.wini.owle.schema.error.ErrorDefinition
import kotlinx.serialization.Serializable

sealed class ApiRequestError: OwleError

@ErrorDefinition("errors.apiRequest.requestBodyMalformed")
@Serializable
data object RequestBodyMalformed : ApiRequestError() {
    override val errorCode: String get() = ""
    override val message: String get() = ""
    override val detail: String? get() = null
}

@ErrorDefinition("errors.apiRequest.multipleTypeValidations")
@Serializable
data class MultipleTypeValidations(
    val violations: List<ConstraintError>
): ApiRequestError() {
    override val errorCode: String get() = ""
    override val message: String get() = ""
    override val detail: String? get() = null
}

@ErrorDefinition("errors.apiRequest.multipleParamFailure")
@Serializable
data class MultipleParamFailure(
    val failed: List<OwleError>
) : ApiRequestError() {
    override val errorCode: String get() = ""
    override val message: String get() = ""
    override val detail: String? get() = null
}

@ErrorDefinition("errors.apiRequest.missingParameter")
@Serializable
data class MissingParameter(
    val paramKey: String
) : ApiRequestError() {
    override val errorCode: String get() = ""
    override val message: String get() = ""
    override val detail = "'$paramKey' was not provided"
}

@ErrorDefinition("errors.apiRequest.authorizationError")
@Serializable
data object AuthorizationError : ApiRequestError() {
    override val errorCode: String get() = ""
    override val message: String get() = ""
    override val detail = null
}

/**
 * An error whose origin is in the repository layer, which presumably has faithfully informed that the id that had
 * been referenced for selection or deletion did not exist. This indicates a disconnect between clients interacting
 * with the database and the application's efficacy representing the concurrent operations globally
 */
@ErrorDefinition("errors.apiRequest.noSuchIdError")
@Serializable
data object NoSuchIdError : ApiRequestError() {
    override val errorCode: String get() = ""
    override val message: String get() = ""
    override val detail = null
}

/**
 *
 */
@ErrorDefinition("errors.apiRequest.batchOpOfNoDataObjects")
@Serializable
data object BatchOpOfNoDataObjects : ApiRequestError() {
    override val errorCode: String get() = ""
    override val message: String get() = ""
    override val detail: String? get() = null
}

@ErrorDefinition("errors.apiRequest.authenticationError")
@Serializable
data object AuthenticationError : ApiRequestError() {
    override val errorCode: String get() = ""
    override val message: String get() = ""
    override val detail = null
}


@ErrorDefinition("errors.apiRequest.paramValueMalformedLong")
@Serializable
data class ParamValueMalformedLong(
    val paramKey: String,
    val formatDescr: String = "number"
) : ApiRequestError() {
    override val errorCode: String get() = ""
    override val message: String get() = ""
    override val detail = "'$paramKey' must be a valid whole number."
}