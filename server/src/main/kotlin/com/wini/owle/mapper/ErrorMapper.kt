package com.wini.owle.mapper

import com.wini.owle.api.ApiError
import com.wini.owle.async.AsyncError
import com.wini.owle.async.TypeValidation

object ErrorMapper {
    fun asyncToApi(error: AsyncError) {
        error.run {
            when (this) {
                is TypeValidation ->
                    ApiError(errorCode, message, detail)
                AsyncError.ApiRequestError.EmptyListNotAllowed -> TODO()
                is AsyncError.ApiRequestError.MissingParameter -> TODO()
                is AsyncError.ApiRequestError.MultipleParamFailure -> TODO()
                is AsyncError.ApiRequestError.MultipleTypeValidations -> TODO()
                is AsyncError.ApiRequestError.ParamValueMalformedLong -> TODO()
                AsyncError.ApiRequestError.RequestBodyMalformed -> TODO()
                AsyncError.AuthenticationError -> TODO()
                AsyncError.AuthorizationError -> TODO()
                AsyncError.DatabaseError.ConnectionFailure -> TODO()
                AsyncError.DatabaseError.ConstraintViolation -> TODO()
                AsyncError.DatabaseError.DeadlockDetected -> TODO()
                is AsyncError.DatabaseError.Generic -> TODO()
                AsyncError.DatabaseError.Timeout -> TODO()
                AsyncError.DbTransactionError -> TODO()
                AsyncError.NoSuchIdError -> TODO()
                is AsyncError.PartialRequestBodyFailure<*> -> TODO()
            }
        }
    }
}