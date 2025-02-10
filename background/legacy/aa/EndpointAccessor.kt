package com.wini.owle.air.domain.aa

import com.wini.owle.air.db.endpoint.EndpointEntity
import com.wini.owle.air.db.backoffice.endpoint.EndpointRepository
import com.wini.owle.air.domain.endpoint.Endpoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class EndpointAccessor(
    private val repository: EndpointRepository
): DomainAccessor<Endpoint, EndpointEntity>(repository),
   DbSelectAllMapper<EndpointEntity, Endpoint> by EndpointSelectAllMapper() {

    fun getAll(): Flow<List<Endpoint>> = flowOf(emptyList())
        //flowOf(repository.getAll().map { it.executeAsList() }.mapSelectAllToModel()).single().single()

    suspend fun getById(id: Long): Flow<Endpoint?> = repository.selectById(id).map {
        it?.mapSelectAllToModel()
    }

    override fun Endpoint.mapToEntity(): EndpointEntity {
        return EndpointEntity(
            id, Long, path, name
        )
    }

}