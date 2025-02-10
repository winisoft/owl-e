package com.wini.owle.air.ktor.feature.mocker

import com.wini.owle.air.domain.api.ApiOverrideAccessor
import com.wini.owle.air.domain.cal.CalOverrideAccessor
import com.wini.owle.air.domain.call.CallAccessor
import com.wini.owle.air.domain.call.HttpMethod
import com.wini.owle.air.domain.car.CarPropOverrideAccessor
import com.wini.owle.air.domain.model.item.Call
import com.wini.owle.air.domain.prefs.SharedPrefOverrideAccessor
import com.wini.owle.air.domain.repo.LoError
import com.wini.owle.air.domain.sysprop.SystemPropOverrideAccessor
import com.wini.owle.air.inlines.*
import io.ktor.server.application.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.cio.*
import io.ktor.util.pipeline.*
import io.ktor.utils.io.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.net.URI
import java.nio.charset.Charset

internal class ResponseMocker(
    val apiOverrideRepo: ApiOverrideAccessor,
    val calOverrides: CalOverrideAccessor,
    val carPropOverrides: CarPropOverrideAccessor,
    val systemPropOverrides: SystemPropOverrideAccessor,
    val sharedPrefOverrides: SharedPrefOverrideAccessor,
    val callRepo: CallAccessor
) : IResponseMocker {

    override suspend fun mockResponse(pipelineContext: PipelineContext<Unit, ApplicationCall>) {
        pipelineContext.context.apply {
            interceptRequestAndMockResponse()
        }
    }

    private val reservedPropApiKeys = listOf("cal", "car", "sys", "spr")

    private suspend fun ApplicationCall.interceptRequestAndMockResponse() {
        val uri = URI("${request.local.scheme}://${request.local.host}:${request.port()}${request.uri}")
        val pathSegments = request.uri.substringAfter("/").split("/")
        val compliantFormat = pathSegments.run {
            isNotEmpty() && get(0).length == 3 && get(0).toCharArray().all { ch -> ch.isLetter() }
        }

        if (compliantFormat && pathSegments[0] in reservedPropApiKeys)
            return mockPropertyValue(pathSegments[0], pathSegments[1])

        val potentialParents = apiOverrideRepo.getPathsOfApiWith(LoRoute(pathSegments[0]))
            .asSequence()
            .filter { uri.matchesWithWildcards(it.second.value) }
            .map { it.first }

        val method = HttpMethod.fromString(request.httpMethod.toString())
        val bytes = if (method !is HttpMethod.Post) null else request.receiveChannel().toByteArray()
        val json = bytes?.run { toString(Charset.defaultCharset()).let { Json.parseToJsonElement(it) } }

        callRepo.getWhereMatchesIntercept(
            potentialParents.toList(),
            method,
            json?.run { PostBody(this) }
        ).let { candidates ->

            val queryParamsProvided = request.call.request.queryParameters.entries()
                .associate { it.key to it.value }
                .run { QueryParams(this) }
            val headersProvided = request.call.request.headers.entries()
                .associate { it.key to it.value }
                .run { RequestHeaders(this) }

            candidates.first { call ->
                call.queryParams?.value?.all { req ->
                    queryParamsProvided.value?.run { containsKey(req.key) && get(req.key) == req.value } ?: false
                } ?: true && call.requestHeaders?.value?.all { req ->
                    headersProvided.value.containsKey(req.key) && (headersProvided.value[req.key] == req.value)
                } ?: true
            }.run {
                delay(delayMillis.value)
                mockResponse(this@interceptRequestAndMockResponse, this)
            }
        }
    }

    fun URI.matchesWithWildcards(dbPath: String): Boolean {
        val dbSegments = dbPath.split("/").filterNot { it == "" }
        val pathSegments =
            path.split("/").filterNot { it == "" }.toMutableList().run { if (size > 0) removeFirst(); this }
        dbSegments.forEach {
            if (pathSegments.size == 0)
                return false
            if (it.startsWith("{") && it.endsWith("}") || it == pathSegments[0])
                pathSegments.removeAt(0)
        }
        return pathSegments.size == 0
    }

    suspend fun mockResponse(
        applicationCall: ApplicationCall,
        call: Call
    ) {
        val bytes = call.responseBody?.value?.toString()
            ?.run {
                replace("\\", "").substring(1).substringBeforeLast('"')
            }?.toByteArray()
            ?: byteArrayOf()

        val outgoing = object : OutgoingContent.ByteArrayContent() {
            override val contentType: ContentType = ContentType.parse("application/json; charset=utf-8")
            override val status = HttpStatusCode.fromValue(call.httpCode.value.toInt())
            override val contentLength: Long = bytes.size.toLong()
            override fun bytes(): ByteArray = bytes
        }
        applicationCall.respond(outgoing)
    }


    private suspend fun handleCalRequest(requestedId: String) {
//        calOverrides.getEnabledWith(CalibrationId(requestedId)).run {
//            mapLeft {
//                if (it is LoError.ExpectedDbValueNotFound)
//                    LoPropResponse("cal", requestedId, 2)
//                else
//                    LoPropResponse("cal", requestedId, 1)
//            }.map {
//                LoPropResponse(
//                    "cal",
//                    requestedId,
//                    0,
//                    it.second?.value,
//                    it.third.value.toInt()
//                )
//            }
//        }
    }

    @Serializable
    data class LoPropResponse(
        val requestType: String,
        val requestedId: String,
        val responseCode: Int,
        val result: String? = null,
        val resultDataType: Int? = null
    )

    enum class PropResponseType(val value: Int) {
        SUCCESS(0),
        UNKNOWN_ERROR(1),
        NONE_SUITABLE(2),
        INVALID_REQUEST(3)
    }

    private suspend fun ApplicationCall.handleCarPropRequest(requestedId: String) {
        requestedId.toLongOrNull()?.run { CarPropId(this) }?.let { id ->
            carPropOverrides.getEnabledWith(id)
        } ?: run {
            firePropResponse(LoPropResponse("car", requestedId, PropResponseType.INVALID_REQUEST.value))
        }
    }

    private suspend fun ApplicationCall.mockPropertyValue(key: String, requestedId: String) {
        if (key == "cal")
            handleCalRequest(requestedId)
        if (key == "car")
            handleCarPropRequest(requestedId)
    }

    private suspend fun ApplicationCall.firePropResponse(response: LoPropResponse) {
        val bytes = Json.encodeToJsonElement(response).toString().toByteArray()
        object : OutgoingContent.ByteArrayContent() {
            override val contentType: ContentType = ContentType.parse("application/json; charset=utf-8")
            override val status = HttpStatusCode.OK
            override val contentLength: Long = bytes.size.toLong()
            override fun bytes(): ByteArray = bytes
        }.apply {
            respond(this)
        }
    }

    private suspend fun sufferToPass(context: ApplicationCall, requestUri: URI): HttpStatement {
        val client = HttpClient()
        val builder = HttpRequestBuilder().apply {
            method = context.request.httpMethod
            url {
                takeFrom(requestUri)
                host = context.request.call.request.host()
                port = context.request.port().takeIf { it > 0 } ?: DEFAULT_PORT
            }

            client.request(this)
        }
        return HttpStatement(builder, client)
    }

}