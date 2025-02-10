package com.wini.owle.air.ktor.feature.jitter.processors


import com.wini.owle.air.domain.model.item.Call
import com.wini.owle.air.ktor.feature.jitter.ResponseProcessingStrategy
import io.ktor.server.application.*
import io.ktor.server.request.*

interface CommandResponseProcessor: ResponseProcessingStrategy {

    override fun execute(applicationCall: ApplicationCall, call: Call): String? =
        applicationCall.request.path()
        .split("/")
        .filterNot { it == "" }
        .run { this[2] to this[4] }
        .run {
            call.responseBody?.value?.toString()?.replace(":vin:", first)?.replace(":requestId:", second)
        }
}