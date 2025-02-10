package com.wini.owle.air.ktor.feature.jitter.processors

import com.wini.owle.air.domain.model.item.Call
import com.wini.owle.air.ktor.feature.jitter.ResponseProcessingStrategy
import io.ktor.server.application.*
import io.ktor.server.request.*


interface PostOrdersProcessor: ResponseProcessingStrategy {

    override fun execute(applicationCall: ApplicationCall, call: Call): String? {
        return applicationCall.request.path().let { path ->
            path.split("/").filterNot { it == "" }[2].run {
                call.responseBody?.toString()?.replace(":vin:", this)
            }
        }
    }
}