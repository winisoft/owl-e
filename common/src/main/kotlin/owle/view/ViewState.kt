package com.wini.owle.view

import kotlinx.serialization.Serializable

@Serializable
sealed class ViewState<out T> {

    @Serializable
    object Loading : ViewState<Nothing>()

    @Serializable
    data class Success<T>(val data: T) : ViewState<T>()

    @Serializable
    data class Error(val errorBundle: String, val errorName: String): ViewState<Nothing>()

}