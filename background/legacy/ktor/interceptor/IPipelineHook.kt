package com.wini.owle.air.ktor.interceptor

import io.ktor.server.application.ApplicationCall
import io.ktor.util.pipeline.PipelineContext

interface IPipelineHook {
    suspend fun mockResponse(pipelineContext: PipelineContext<Unit, ApplicationCall>)
}
