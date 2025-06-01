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

    val getOnePath get() = "/api/$endpointPath/{id}"
    val getManyPath get() = "/api/$endpointPath"
    val postOnePath get() = "/api/$endpointPath"
    val postManyPath get() = "/api/$endpointPath/batch"
    val putOnePath get() = "/api/$endpointPath"
    val putManyPath get() = "/api/$endpointPath/batch"
    val deleteOnePath get() = "/api/$endpointPath/{id}"
    val deleteManyPath get() = "/api/$endpointPath/batch"

}