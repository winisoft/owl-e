package com.wini.owle.air.domain.aa

import com.wini.owle.air.db.call.SelectById
import com.wini.owle.air.domain.model.item.Call

class DbCallSelectByIdMapper: DbSelectByIdMapper<SelectById, Call> {

    override fun SelectById?.mapSelectByIdToModel(): Call? {
        return this?.run {
            Call(
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
}