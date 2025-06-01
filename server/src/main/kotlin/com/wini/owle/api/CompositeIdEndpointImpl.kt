package com.wini.owle.api

import com.wini.owle.validators.DtoValidator
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.StringDto
import com.wini.owle.error.MissingParameter
import com.wini.owle.error.MultipleParamFailure
import com.wini.owle.error.OwleError
import com.wini.owle.error.ParamValueMalformedLong
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import io.ktor.server.application.*
import io.ktor.util.*
import kotlinx.serialization.KSerializer
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import kotlin.collections.component1
import kotlin.collections.component2


/**
 * Implementation of a Ktor API endpoint suited to types with composite primary key. These must be [CompositeDto].
 */
open class CompositeIdEndpointImpl<T: CompositeDto, E: CompositeEntity>(
    dll: DLL<CompositeID, T>,
    private val validator: DtoValidator<T>,
    private val daoDelegate: CompositeIdDaoDelegate<T, E>,
    override val endpointPath: String,
    serializer: KSerializer<T>,
): BaseApiImpl<CompositeID, T>(
    dll = dll,
    validator = validator,
    serializer = serializer
) {

    override fun ApplicationCall.readId(): AsyncResult<CompositeID> {
        return compositeIdFromRequestParams(
            entityColumnsByReqParams = daoDelegate.columnsByRequestParam,
            params = request.queryParameters.toMap()
        )
    }

    override suspend fun ApplicationCall.readIds(): AsyncResult<List<CompositeID>> {
        return when(val dtosResult = readDtos(dtoListSerializer, validator)) {
            is AsyncResult.Success ->
                dtosResult.data
                    .map { daoDelegate.buildId(it).value }
                    .let { compositeIds ->
                        AsyncResult.Success(compositeIds)
                    }
            is AsyncResult.Failure ->
                return dtosResult
        }
    }

    /**
     * Reads Ktor's representation of a request's query parameters and produces from them a CompositeId suitable for
     * identifying the type represented by the classes generic parameter type, returning null if the required parameters
     * are either not found or malformed.
     */
    fun compositeIdFromRequestParams(
        entityColumnsByReqParams: Map<String, Column<out Any>>,
        params: Map<String, List<String>>
    ): AsyncResult<CompositeID> {
        val failed = mutableListOf<OwleError>()

        // map the names of the relevant db table columns to values found under that key in the request query params
        return entityColumnsByReqParams.keys.associateWith {
            params[it]
            // return null if the query parameters come in an unexpected format
        }.mapNotNull { (key: String, values: List<String>?) ->
            when {
                values == null || values.firstOrNull() == null -> {
                    failed.add(MissingParameter(key))
                    null
                }
                values.first().toLongOrNull() == null -> {
                    failed.add(ParamValueMalformedLong(key))
                    null
                }
                else -> key to values[0].toULong()
            }
        }.also {
            // logically 'short-circuits' further consideration of the already-determined invalid data provided
            if (failed.isNotEmpty())
                return MultipleParamFailure(failed = failed.toList()).asFailure()
        }.associate {
            // map the key under which the parameter was provided (its db column name) to the valid Long value it offered
            it.first to it.second
        }.let { validatedParams: Map<String, Any> ->
            val mm: MutableMap<String, Any?> = daoDelegate.columnsByRequestParam.toMutableMap()
            validatedParams.forEach {
                mm[it.key] = it.value
            }
            val compositeId = daoDelegate.buildId(mm)

            AsyncResult.Success(compositeId)
        }
    }

}
