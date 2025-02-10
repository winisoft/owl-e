package com.wini.owle.air.domain.aa

import arrow.core.Either
import com.wini.owle.air.db.call.CallEntity
import com.wini.owle.air.db.backoffice.call.CallRepository
import com.wini.owle.air.db.call.SelectAll
import com.wini.owle.air.db.call.SelectById
import com.wini.owle.air.domain.aa.inlines.PostBody
import com.wini.owle.air.domain.model.item.Call
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.repo.*
import com.wini.owle.air.inlines.*
import com.wini.owle.air.domain.call.HttpMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import com.wini.owle.air.db.backoffice.call.HttpCode as DataLayerHttpCode

class CallAccessor(
    private val repository: CallRepository
): DomainAccessor<Call, CallEntity>(repository),
    ModelToEntityMapper<Call, CallEntity>,
    EntityToModelMapper<CallEntity, Call>,
   DbSelectAllMapper<SelectAll, Call> by DbCallSelectAllMapper(),
   DbSelectByIdMapper<SelectById, Call> by DbCallSelectByIdMapper() {

    fun all(): Flow<List<LoItem>> = flowOf(repository.queries.selectAll().executeAsList())
        .mapSelectAllToModel()

    fun getAll(): Flow<List<Call>> = repository.selectAllQuery().map { it.executeAsList() }.mapSelectAllToModel()

    fun getById(id: CallId): Flow<Call?> = repository.selectById(id).mapSelectByIdToModel()

    suspend fun getWhereMatchesIntercept(
        potentialParents: List<Long>,
        method: HttpMethod,
        postBody: PostBody? = null
    ): List<Call> = repository.selectWhereMatchingIntercept(potentialParents, method.toEntity(), postBody).map { it.executeAsList().mapToModel() }.single()

    fun getWhereEndpointOverride(id: Long): Flow<List<Call>> = repository.selectAllQuery().map { it.executeAsList() }.mapSelectAllToModel()

    fun enable(call: Call): Flow<Either<LoError, Unit>> = repository.setBoolean(call.mapToEntity())
        .map { result ->
            result.mapLeft {
                LoError.fromThrowable(it)
            }
        }

    override fun Call.mapToEntity(): CallEntity {
        return CallEntity(
            id,
            Long,
            overrideName,
            utility,
            description,
            method.toEntity(),
            postBody,
            requestHeaders,
            queryParams,
            responseHeaders,
            DataLayerHttpCode.fromLong(httpCode.value),
            delayMillis,
            responseBody,
            isEnabled,
            isHorizontalDisable
        )
    }

    override fun CallEntity.mapToModel(): Call {
        return Call(
            id,
            Long(0),
            Long(0),
            Long,
            utility,
            overrideName,
            description,
            isEnabled,
            HttpMethod.fromString(method.key),
            postBody,
            requestHeaders,
            queryParams,
            responseHeaders,
            HttpCode.fromLong(httpCode.value),
            delayMillis,
            responseBody,
            false,
            false,
            isHorizontalDisabled
        )
    }

    fun getNextId(): CallId {
        return repository.queries.getMaxId().executeAsOne()
    }

}