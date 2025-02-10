package com.wini.owle.air.ktor.feature.jitter

import com.wini.owle.air.domain.model.item.Call
import com.wini.owle.air.ktor.feature.jitter.processors.RequestInfoProcessor
import com.wini.owle.air.ktor.feature.jitter.processors.CommandResponseProcessor
import io.ktor.server.application.*


object ResponseProcessor: (ApplicationCall, Call) -> String?, java.io.Serializable {

    override fun invoke(appCall: ApplicationCall, call: Call): String? = when (call.endpointOverrideId.value) {
        37L -> object: RequestInfoProcessor {}.execute(appCall, call)
        127L -> object: CommandResponseProcessor {}.execute(appCall, call)
        else -> call.responseBody?.value?.toString()
    }

}
