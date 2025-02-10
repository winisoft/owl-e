package com.wini.owle.air.domain.aa

import com.wini.owle.air.domain.api.Api


class ApiAccessor(
    private val repository: ApiRepository
): DomainAccessor<Api, ApiEntity>(repository),
    EntityToModelMapper<ApiEntity, Api> {

    fun getAll(): Flow<List<Api>> {
        return repository.selectAll().mapToModel()
    }

    fun getById(id: Long): Flow<Api?> {
        return repository.selectById(id).mapToModelNullable()
    }

    override fun ApiEntity.mapToModel(): Api {
        return Api(id, name, Long, openApi)
    }

    override fun Api.mapToEntity(): ApiEntity {
        return ApiEntity(id, name, Long, openApi)
    }
}

