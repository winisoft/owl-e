package com.wini.owle.air.domain.aa

import com.wini.owle.air.db.endpoint.EndpointEntity
import com.wini.owle.air.domain.endpoint.Endpoint

class EndpointSelectAllMapper: DbSelectAllMapper<EndpointEntity, Endpoint> {
    override fun EndpointEntity.mapSelectAllToModel(): Endpoint {
        return Endpoint(id, Long, path, name)
    }
}