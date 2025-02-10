package com.wini.owle.air.domain.model.item.builder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wini.owle.air.db.backoffice.call.HttpCode as HttpCodeEntity
import com.wini.owle.air.db.backoffice.call.HttpMethod as HttpMethodEntity
import com.wini.owle.air.db.call.CallEntity
import com.wini.owle.air.domain.action.SaveActions
import com.wini.owle.air.domain.action.SaveCall
import com.wini.owle.air.domain.call.CallAccessor
import com.wini.owle.air.domain.call.HttpCode
import com.wini.owle.air.domain.call.HttpMethod
import com.wini.owle.air.domain.model.item.Call
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.model.item.builder.CallBuilder.mapToEntity
import com.wini.owle.air.inlines.*

object CallBuilder : LoItem.LoItemBuilder<CallEntity> {

    var id: CallId? by mutableStateOf(null)
    var endpointOverrideId: EndpointOverrideId? by mutableStateOf(null)
    var overrideName: OverrideName? by mutableStateOf(null)
    var utility: Utility? by mutableStateOf(null)
    var description: Description? by mutableStateOf(null)
    var method: HttpMethodEntity? by mutableStateOf(null)
    var postBody: PostBody? by mutableStateOf(null)
    var requestHeaders: RequestHeaders? by mutableStateOf(null)
    var queryParams: QueryParams? by mutableStateOf(null)
    var responseHeaders: ResponseHeaders? by mutableStateOf(null)
    var httpCode: HttpCodeEntity? by mutableStateOf(null)
    var delayMillis: DelayMillis? by mutableStateOf(null)
    var responseBody: ResponseBody? by mutableStateOf(null)
    var isEnabled: IsEnabled? by mutableStateOf(null)
    var isHorizontalDisabled: IsHorizontalDisabled? by mutableStateOf(null)

    operator fun invoke(
        call: Call
    ): CallBuilder = apply {
        id = call.id
        endpointOverrideId = call.endpointOverrideId
        overrideName = call.overrideName
        utility = call.utility
        description = call.description
        method = call.method.toEntity()
        postBody = call.postBody
        requestHeaders = call.requestHeaders
        queryParams = call.queryParams
        responseHeaders = call.responseHeaders
        httpCode = HttpCodeEntity.fromLong(call.httpCode.value)
        delayMillis = call.delayMillis
        responseBody = call.responseBody
        isEnabled = call.isEnabled
        isHorizontalDisabled = call.isHorizontalDisable
    }

    operator fun invoke(epOverrideId: EndpointOverrideId): CallBuilder = apply {
        endpointOverrideId = epOverrideId
    }

    override val isValid: MutableState<Boolean>
        get() = mutableStateOf(
            id != null
                    && endpointOverrideId != null
                    && isEnabled != null
                    && method != null
                    && requestHeaders != null
                    && responseHeaders != null
                    && httpCode != null
                    && delayMillis != null
                    && responseBody != null
                    && isHorizontalDisabled != null
        )

    override fun build(): SaveCall = SaveCall(mapToEntity())

    override fun LoItem.LoItemBuilder<CallEntity>.mapToEntity(): CallEntity = CallEntity(
        id!!,
        endpointOverrideId!!,
        overrideName,
        utility,
        description,
        method!!,
        postBody,
        requestHeaders,
        queryParams,
        responseHeaders,
        httpCode!!,
        delayMillis!!,
        responseBody,
        isEnabled!!,
        isHorizontalDisabled!!
    )

}