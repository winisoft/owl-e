package com.wini.owle.air.db.backoffice

import com.wini.owle.air.inlines.*
import com.squareup.sqldelight.ColumnAdapter
import com.wini.owle.air.db.InlineAdapters
import com.wini.owle.air.db.api.ApiEntity
import com.wini.owle.air.db.api.ApiOverrideEntity
import com.wini.owle.air.db.backoffice.call.HttpCode
import com.wini.owle.air.db.backoffice.call.HttpMethod
import com.wini.owle.air.db.call.CallEntity
import com.wini.owle.air.db.endpoint.EndpointEntity
import com.wini.owle.air.db.endpoint.EndpointOverrideEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

object BoDbAdapters {

    val apiEntityAdapter = ApiEntity.Adapter(
        idAdapter = object: ColumnAdapter<ApiId, Long>, app.cash.sqldelight.ColumnAdapter<ApiId, Long> {
            override fun decode(databaseValue: Long): ApiId {
                TODO("Not yet implemented")
            }

            override fun encode(value: ApiId): Long {
                TODO("Not yet implemented")
            }
        },
        nameAdapter = InlineAdapters.String.name,
        loRouteAdapter = InlineAdapters.String.loRoute
    )

    val apiOverrideEntityAdapter = ApiOverrideEntity.Adapter(
        idAdapter = InlineAdapters.TypeId.apiOverrideId,
        apiIdAdapter = InlineAdapters.TypeId.apiId,
        environmentIdAdapter = InlineAdapters.TypeId.environmentId,
        overrideNameAdapter = InlineAdapters.String.overrideName,
        descriptionAdapter = InlineAdapters.String.description,
        isEnabledAdapter =  InlineAdapters.Bool.isEnabled,
        isExpandedAdapter = InlineAdapters.Bool.isExpanded,
        isHorizontalDisabledAdapter = InlineAdapters.Bool.isHorizontalDisabled
    )

    val endpointEntityAdapter = EndpointEntity.Adapter(
        apiIdAdapter = InlineAdapters.Long.api,
        pathAdapter = InlineAdapters.String.endpointPath,
        nameAdapter = InlineAdapters.String.name,
        idAdapter = InlineAdapters.TypeId.endpointId
    )

    val endpointOverrideEntityAdapter = EndpointOverrideEntity.Adapter(
        idAdapter = InlineAdapters.TypeId.endpointOverrideId,
        endpointIdAdapter = InlineAdapters.TypeId.endpointId,
        apiOverrideIdAdapter = InlineAdapters.TypeId.apiOverrideId,
        overrideNameAdapter = InlineAdapters.String.overrideName,
        isEnabledAdapter = InlineAdapters.Bool.isEnabled,
        isExpandedAdapter = InlineAdapters.Bool.isExpanded,
        descriptionAdapter = InlineAdapters.String.description,
        isHorizontalDisabledAdapter = InlineAdapters.Bool.isHorizontalDisabled
    )

    val callEntityAdapter = CallEntity.Adapter(
        idAdapter = InlineAdapters.TypeId.callId,
        overrideNameAdapter = InlineAdapters.String.overrideName,
        delayMillisAdapter = InlineAdapters.Long.delayMillis,
        isEnabledAdapter = InlineAdapters.Bool.isEnabled,
        endpointOverrideIdAdapter = InlineAdapters.TypeId.endpointOverrideId,
        descriptionAdapter = InlineAdapters.String.description,
        utilityAdapter = InlineAdapters.String.utilityAdapter,
        isHorizontalDisabledAdapter = InlineAdapters.Bool.isHorizontalDisabled,
        requestHeadersAdapter = object : ColumnAdapter<RequestHeaders, String>,
            app.cash.sqldelight.ColumnAdapter<RequestHeaders, String> {
            override fun decode(databaseValue: String): RequestHeaders = RequestHeaders(mapOf())
            override fun encode(value: RequestHeaders): String = Json.encodeToString(value)
        },
        methodAdapter = object : ColumnAdapter<HttpMethod, String>,
            app.cash.sqldelight.ColumnAdapter<HttpMethod, String> {
                override fun decode(databaseValue: String) = HttpMethod.fromString(databaseValue)
                override fun encode(value: HttpMethod) = value.key
        },
        postBodyAdapter = object : ColumnAdapter<PostBody, String>,
            app.cash.sqldelight.ColumnAdapter<PostBody, String> {
                override fun decode(databaseValue: String): PostBody = PostBody(Json.encodeToJsonElement(databaseValue))
                override fun encode(value: PostBody): String = Json.encodeToString(value)
        },
        queryParamsAdapter = object : ColumnAdapter<QueryParams, String>,
            app.cash.sqldelight.ColumnAdapter<QueryParams, String> {
            override fun decode(databaseValue: String): QueryParams = databaseValue.run {
                if (isNullOrEmpty()) QueryParams(emptyMap()) else QueryParams(Json.decodeFromString(this))
            }
            override fun encode(value: QueryParams): String = Json.encodeToString(value.value)
        },
        responseHeadersAdapter = object: ColumnAdapter<ResponseHeaders, String>,
            app.cash.sqldelight.ColumnAdapter<ResponseHeaders, String> {
            override fun decode(databaseValue: String): ResponseHeaders = ResponseHeaders(mapOf<String, List<String>>())
            override fun encode(value: ResponseHeaders): String = Json.encodeToString(value)
        },
        httpCodeAdapter = object: ColumnAdapter<HttpCode, Long>, app.cash.sqldelight.ColumnAdapter<HttpCode, Long> {
            override fun decode(databaseValue: Long): HttpCode = HttpCode.fromLong(databaseValue)
            override fun encode(value: HttpCode): Long = value.value
        },
        responseBodyAdapter = object: ColumnAdapter<ResponseBody, String>,
            app.cash.sqldelight.ColumnAdapter<ResponseBody, String> {
            override fun decode(databaseValue: String): ResponseBody = ResponseBody(Json.encodeToJsonElement(databaseValue))
            override fun encode(value: ResponseBody): String = Json.encodeToString(value)
        }
    )
}