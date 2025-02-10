package com.wini.owle.air.domain.aa

import com.wini.owle.air.db.call.SelectAll
import com.wini.owle.air.domain.model.item.Call

class DbCallSelectAllMapper: DbSelectAllMapper<SelectAll, Call> {

    override fun SelectAll.mapSelectAllToModel(): Call {
        return Call(
            id,
            Long,
            Long,
            Long,
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