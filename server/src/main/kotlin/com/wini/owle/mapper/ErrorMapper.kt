package com.wini.owle.mapper

import com.wini.owle.api.ApiError
import com.wini.owle.error.AuthenticationError
import com.wini.owle.error.AuthorizationError
import com.wini.owle.error.DatabaseError
import com.wini.owle.error.EmptyListNotAllowed
import com.wini.owle.error.FieldConstraintError
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
                DatabaseError.ConnectionFailure -> TODO()
                DatabaseError.DbConstraintViolation -> TODO()
                DatabaseError.DeadlockDetected -> TODO()
                is DatabaseError.Generic -> TODO()
                DatabaseError.Timeout -> TODO()
                DatabaseError.DbTransactionError -> TODO()
                AuthenticationError -> TODO()
                AuthorizationError -> TODO()
                NoSuchIdError -> TODO()
            }
        }
    }
}