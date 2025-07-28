package com.wini.owle.api


interface IOwleApi {

    val endpointPath: String

    suspend fun getMany(block: suspend () -> Unit) { block() }

    suspend fun getOne(block: suspend () -> Unit) { block() }

    suspend fun postMany(block: suspend () -> Unit) { block() }

    suspend fun postOne(block: suspend () -> Unit) { block() }

    suspend fun putMany(block: suspend () -> Unit) { block() }

    suspend fun putOne(block: suspend () -> Unit) { block() }

    suspend fun deleteMany(block: suspend () -> Unit) { block() }

    suspend fun deleteOne(block: suspend () -> Unit) { block() }

    val baseEndpointPath: String get() = "${BASE_PATH}/$endpointPath"

    companion object {
        const val BASE_PATH = "/api"
    }
}