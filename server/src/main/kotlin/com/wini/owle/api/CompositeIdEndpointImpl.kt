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
import org.jetbrains.exposed.v1.core.EntityIDColumnType
import org.jetbrains.exposed.v1.core.StringColumnType
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
     * Builds a [CompositeID] from a request's query parameters.
     *
     * Each key column is parsed as the column itself stores it. That distinction is not cosmetic:
     * six composite keys (`packages`, `package_extras`, `package_python_requirements`,
     * `tagged_distributions`, `tagged_packages`, `workflow_requirements`) include the distribution
     * *name*, a VARCHAR. Parsing every part as a number rejected every valid request to those
     * endpoints with "must be a valid whole number".
     *
     * Numeric parts go through `toULongOrNull`, which also closes a crash: the previous check
     * accepted anything `toLongOrNull` liked and then called `toULong`, so `?id=-5` passed
     * validation and threw `NumberFormatException` out of the handler.
     *
     * Every bad parameter is reported, not just the first, so one round trip tells the caller
     * everything that is wrong with the request.
     */
    fun compositeIdFromRequestParams(
        entityColumnsByReqParams: Map<String, Column<out Any>>,
        params: Map<String, List<String>>
    ): AsyncResult<CompositeID> {
        val failed = mutableListOf<OwleError>()
        val values = mutableMapOf<String, Any?>()

        entityColumnsByReqParams.forEach { (key, column) ->
            val raw = params[key]?.firstOrNull()
            when {
                raw.isNullOrBlank() -> failed += MissingParameter(key)
                column.holdsText() -> values[key] = raw
                else -> raw.toULongOrNull()
                    ?.let { values[key] = it }
                    ?: failed.add(ParamValueMalformedLong(key))
            }
        }

        if (failed.isNotEmpty()) return MultipleParamFailure(failed = failed.toList()).asFailure()

        return AsyncResult.Success(daoDelegate.buildId(values))
    }

    /**
     * Whether the column ultimately stores text.
     *
     * A key column is usually an `EntityIDColumnType` wrapping the referenced table's id column,
     * which may itself be a reference, so the wrappers are unwound to whatever is underneath.
     */
    private fun Column<*>.holdsText(): Boolean =
        generateSequence(columnType) { (it as? EntityIDColumnType<*>)?.idColumn?.columnType }
            .last() is StringColumnType



}
