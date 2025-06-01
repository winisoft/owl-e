package com.wini.owle.mapper

import com.wini.owle.api.ApiError
import com.wini.owle.error.AuthenticationError
import com.wini.owle.error.AuthorizationError
import com.wini.owle.error.BatchOpOfNoDataObjects
import com.wini.owle.error.ConnectionFailure
import com.wini.owle.error.ConstraintError
import com.wini.owle.error.DbConstraintViolation
import com.wini.owle.error.DbTransactionError
import com.wini.owle.error.DeadlockDetected
import com.wini.owle.error.GenericError
import com.wini.owle.error.MissingParameter
import com.wini.owle.error.MultipleParamFailure
import com.wini.owle.error.MultipleTypeValidations
import com.wini.owle.error.NoSuchIdError
import com.wini.owle.error.OwleError
import com.wini.owle.error.ParamValueMalformedLong
import com.wini.owle.error.RequestBodyMalformed


object ErrorMapper {
    fun asyncToApi(error: OwleError) {
        error.run {
            when (this) {
                is FieldConstraintError -> ApiError(errorCode, message, detail)
                EmptyListNotAllowed -> TODO()
                is MissingParameter -> TODO()
                is MultipleParamFailure -> TODO()
                is MultipleTypeValidations -> TODO()
                is ParamValueMalformedLong -> TODO()
                RequestBodyMalformed -> TODO()
                ConnectionFailure -> TODO()
                DbConstraintViolation -> TODO()
                DeadlockDetected -> TODO()
                is GenericError -> TODO()
                Timeout -> TODO()
                DbTransactionError -> TODO()
                AuthenticationError -> TODO()
                AuthorizationError -> TODO()
                NoSuchIdError -> TODO()
            }
        }
    }
}