package com.wini.owle.air.ktor.feature.jitter

import com.wini.owle.air.domain.model.item.Call
import io.ktor.server.application.*

interface ResponseProcessingStrategy {

    fun execute(applicationCall: ApplicationCall, call: Call): String?

}