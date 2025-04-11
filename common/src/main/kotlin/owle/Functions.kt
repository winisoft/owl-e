package com.wini.owle

import com.wini.owle.async.AsyncResult


fun <T> AsyncResult<T>.parseAsString(): String {

    return when (this) {

        is AsyncResult.Success<*> -> {
            this.data.toString()
        }

        is AsyncResult.Failure -> TODO()
    }
}