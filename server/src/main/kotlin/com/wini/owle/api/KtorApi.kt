package com.wini.owle.api

import com.wini.owle.async.AsyncResult
import io.ktor.server.application.ApplicationCall
import io.ktor.server.routing.Routing


interface KtorApi<ID: Any>: IOwleApi {

    /**
     * Called at server initialization to register our API implementation classes as the responding implementation
     * that fulfills the HTTP requests to the routes it is designed to handle.
     */
    fun register(routing: Routing)

    /**
     * Handles the interpretation of HTTP request query parameters present on [ApplicationCall] by following whatever
     * rules describe that process for the [ID] data type.
     */
    fun ApplicationCall.readId(): AsyncResult<ID>

    /**
     * Handles the interpretation of HTTP request query parameters present on [ApplicationCall] by following whatever
     * rules describe that process for the [ID] data type.
     */
    suspend fun ApplicationCall.readIds(): AsyncResult<List<ID>>

}