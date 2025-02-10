package com.wini.owle.services

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncError.DatabaseError
import com.wini.owle.async.AsyncResult


fun <T, R> Result<T>.foldToAsyncResult(
    successTransform: (T) -> R,
    errorTransform: (Throwable) -> AsyncError = { DatabaseError(it.message ?: "Operation failed") }
): AsyncResult<R> {
    return fold(
        onSuccess = { AsyncResult.Success(successTransform(it)) },
        onFailure = { AsyncResult.Failure(errorTransform(it)) }
    )
}