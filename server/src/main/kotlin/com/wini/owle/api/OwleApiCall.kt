package com.wini.owle.api

import io.ktor.http.*


sealed class OwleApiCall {

    abstract val successStatus: HttpStatusCode

    data object GetMany : OwleApiCall() {
        override val successStatus: HttpStatusCode get() = HttpStatusCode.OK
    }
    data object GetOne : OwleApiCall() {
        override val successStatus: HttpStatusCode get() = HttpStatusCode.OK
    }
    data object CreateOne : OwleApiCall() {
        override val successStatus: HttpStatusCode get() = HttpStatusCode.Created
    }
    data object CreateMany : OwleApiCall() {
        override val successStatus: HttpStatusCode get() = HttpStatusCode.Created
    }
    data object UpdateOne : OwleApiCall() {
        override val successStatus: HttpStatusCode get() = HttpStatusCode.OK
    }
    data object UpdateMany : OwleApiCall() {
        override val successStatus: HttpStatusCode get() = HttpStatusCode.OK
    }
    data object DeleteOne : OwleApiCall() {
        override val successStatus: HttpStatusCode get() = HttpStatusCode.NoContent
    }
    data object DeleteMany : OwleApiCall() {
        override val successStatus: HttpStatusCode get() = HttpStatusCode.NoContent
    }
}