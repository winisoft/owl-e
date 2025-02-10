package com.wini.owle.air.domain.endpoint

import com.wini.owle.air.db.endpoint.EndpointEntity
import com.wini.owle.air.db.backoffice.endpoint.EndpointRepository
import com.wini.owle.air.db.endpoint.SelectAll
import com.wini.owle.air.domain.model.item.EndpointOverride
import com.wini.owle.air.domain.repo.DbSelectAllMapper
import com.wini.owle.air.domain.repo.DomainAccessor
import com.wini.owle.air.inlines.EndpointId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single

class EndpointAccessor(
    private val repository: EndpointRepository
): DomainAccessor<Endpoint, EndpointEntity>(repository),
   DbSelectAllMapper<EndpointEntity, Endpoint> by EndpointSelectAllMapper() {

    fun getAll(): Flow<List<Endpoint>> = flowOf(emptyList())
        //flowOf(repository.getAll().map { it.executeAsList() }.mapSelectAllToModel()).single().single()

    suspend fun getById(id: EndpointId): Flow<Endpoint?> = repository.selectById(id).map {
        it?.mapSelectAllToModel()
    }

    override fun Endpoint.mapToEntity(): EndpointEntity {
        return EndpointEntity(
            id, apiId, path, name
        )
    }

}