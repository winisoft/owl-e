package com.wini.owle.air.domain.endpoint

import com.wini.owle.air.db.endpoint.EndpointEntity
import com.wini.owle.air.db.endpoint.SelectAll
import com.wini.owle.air.domain.model.item.EndpointOverride
import com.wini.owle.air.domain.repo.DbSelectAllMapper

class EndpointSelectAllMapper: DbSelectAllMapper<EndpointEntity, Endpoint> {
    override fun EndpointEntity.mapSelectAllToModel(): Endpoint {
        return Endpoint(id, apiId, path, name)
    }
}