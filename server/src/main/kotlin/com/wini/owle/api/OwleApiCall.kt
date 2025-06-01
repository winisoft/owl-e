package com.wini.owle.api

import io.ktor.http.*


/**
 * Represents the combination of HTTP method and the arity of the operation. This is only really helpful
 * insofar as it's something we can pass into the ApplicationCall execution function to guide its logic
 */
sealed class OwleApiCall {

    data object GetMany : OwleApiCall()
    data object GetOne : OwleApiCall()
    data object CreateOne : OwleApiCall()
    data object CreateMany : OwleApiCall()
    data object UpdateOne : OwleApiCall()
    data object UpdateMany : OwleApiCall()
    data object DeleteOne : OwleApiCall()
    data object DeleteMany : OwleApiCall()



    companion object {

        val OwleApiCall.successStatus: HttpStatusCode
            get() = when (this) {
                is CreateMany,
                is CreateOne,
                    -> HttpStatusCode.Created
                is DeleteMany,
                is DeleteOne,
                    -> HttpStatusCode.NoContent
                else
                    -> HttpStatusCode.OK
            }
    }

}