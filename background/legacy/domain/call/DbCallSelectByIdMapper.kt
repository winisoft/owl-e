package com.wini.owle.air.domain.call

import com.wini.owle.air.db.call.SelectById
import com.wini.owle.air.domain.model.item.Call
import com.wini.owle.air.domain.repo.DbSelectByIdMapper

class DbCallSelectByIdMapper: DbSelectByIdMapper<SelectById, Call> {

    override fun SelectById?.mapSelectByIdToModel(): Call? {
        return this?.run {
            Call(
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
}