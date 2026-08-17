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

    /**
     * The collection route. [endpointPath] is normalized so declarations may write it either
     * way — `"/type-node"` and `"type-node"` both yield `/api/type-node`. Without the trim,
     * the leading slash most declarations carry produced `/api//type-node`.
     */
    val basePath get() = "/api/" + endpointPath.trim('/')

    val getOnePath get() = "$basePath/{id}"
    val getManyPath get() = basePath
    val postOnePath get() = basePath
    val postManyPath get() = "$basePath/batch"
    val putOnePath get() = basePath
    val putManyPath get() = "$basePath/batch"
    val deleteOnePath get() = "$basePath/{id}"
    val deleteManyPath get() = "$basePath/batch"

}