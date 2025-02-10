package com.wini.owle.air.db.backoffice.call

import arrow.core.Either
//import com.wini.owle.air.db.call.CallEntity
//import com.wini.owle.air.db.call.CallEntityQueries
//import com.wini.owle.air.db.call.SelectAll
//import com.wini.owle.air.db.call.SelectById
import com.wini.owle.air.db.error.UnknownDbExecuteException
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.inlines.*
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class CallRepository(
    override val queries: CallEntityQueries,
) : com.wini.owle.air.db.repository.LoRepository<CallEntity>(queries) {

    fun selectAllQuery(): Flow<app.cash.sqldelight.Query<SelectAll>> = flowOf(queries.selectAll())

    fun selectAll(): Flow<List<SelectAll>> = selectAllQuery().map {
        it.executeAsList()
    }

    fun selectByIdQuery(id: CallId): Flow<app.cash.sqldelight.Query<SelectById>> = flowOf(queries.selectById(id))

    fun selectById(id: CallId): Flow<SelectById?> = selectByIdQuery(id).map {
        it.executeAsOneOrNull()
    }

    //TODO
    fun setIsEnabled(entity: CallEntity): Flow<Either<Throwable?, Unit>> {
        return runCatching {
            entity.run {
                queries.update(
                    endpointOverrideId,
                    overrideName,
                    description,
                    method,
                    postBody,
                    requestHeaders,
                    queryParams,
                    responseHeaders,
                    httpCode,
                    delayMillis,
                    responseBody,
                    isEnabled,
                    id
                )
            }
        }.run {
            exceptionOrNull()?.run {
                flowOf(Either.Left(this))
            } ?: getOrNull()?.run {
                flowOf(Either.Right(this))
            } ?: flowOf(Either.Left(com.wini.owle.air.db.error.UnknownDbExecuteException()))
        }
    }

    fun selectWhereMatchingIntercept(
        potentialParents: List<EndpointOverrideId>,
        method: com.wini.owle.air.db.backoffice.call.HttpMethod,
        postBody: PostBody?
    ): Flow<app.cash.sqldelight.Query<CallEntity>> {
        return flowOf(queries.selectWithInterceptFeatures(potentialParents, method, postBody))
    }

    override val insertEntityExecution: (CallEntity) -> Unit = {
        it.run {
            queries.insert(getNextId(), endpointOverrideId, overrideName, description, method, postBody,
                requestHeaders, queryParams, responseHeaders, httpCode, delayMillis, responseBody, isEnabled)
        }
    }

    override val updateEntityExecution: (CallEntity) -> Unit = {
        it.run {
            queries.update(endpointOverrideId, overrideName, description, method, postBody, requestHeaders,
                queryParams, responseHeaders, httpCode, delayMillis, responseBody, isEnabled, id)
        }
    }

    override val deleteEntityExecution: (CallEntity) -> Unit = {
        it.run { queries.delete(it.id) }
    }

    private fun getNextId(): CallId = queries.getMaxId().executeAsOne().run { CallId(value + 1) }

}