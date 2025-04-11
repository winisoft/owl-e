package com.wini.owle.view

//
//import com.wini.owle.async.AsyncResult
//import com.wini.owle.async.AsyncError

//
//fun <T> AsyncResult<T>.toViewState(): ViewState<T> {
//    return when (this) {
//        is AsyncResult.Success -> ViewState.Success(data)
//        is AsyncResult.Failure -> when(errorName) {
//            AsyncError.AuthenticationError::class.simpleName ->
//                Json.encodeToString(AsyncError.AuthenticationError) to AsyncError.AuthenticationError::class.simpleName
//            AsyncError.AuthorizationError::class.simpleName ->
//                Json.encodeToString(AsyncError.AuthorizationError) to AsyncError.AuthorizationError::class.simpleName
//            AsyncError.AuthorizationError::class.simpleName ->
//                Json.encodeToString(AsyncError.AuthorizationError) to AsyncError.AuthorizationError::class.simpleName
//            AsyncError.DatabaseError::class.simpleName ->
//                Json.encodeToString(AsyncError.DatabaseError) to AsyncError.DatabaseError::class.simpleName
//            AsyncError.NetworkError ::class.simpleName ->
//                Json.encodeToString(AsyncError.NetworkError) to AsyncError.NetworkError::class.simpleName
//            AsyncError.ValidationError ::class.simpleName ->
//                Json.encodeToString(AsyncError.ValidationError) to AsyncError.ValidationError::class.simpleName
//            else ->
//                Json.encodeToString(AsyncError.UnknownError) to AsyncError.UnknownError::class.simpleName
//        }.let { (bundle, name) ->
//            ViewState.Error(errorBundle=bundle, errorName=name!!)
//        }
//    }
//}