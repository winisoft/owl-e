package com.wini.owle.air.domain.call

import com.wini.owle.air.db.call.SelectAll
import com.wini.owle.air.domain.model.item.Call
import com.wini.owle.air.domain.repo.DbSelectAllMapper

class DbCallSelectAllMapper: DbSelectAllMapper<SelectAll, Call> {

    override fun SelectAll.mapSelectAllToModel(): Call {
        return Call(
            id,
            environmentId,
            apiOverrideId,
            endpointOverrideId,
            utility,
            overrideName,
            description,
            isEnabled,
            HttpMethod.fromString(method.key),
            postBody,
            requestHeaders,
            queryParams,
            responseHeaders,
            HttpCode.fromLong(httpCode.value),
            delayMillis,
            responseBody,
            isVisible,
            isDisabledImplicit,
            isHorizontalDisabled
        )
    }
}