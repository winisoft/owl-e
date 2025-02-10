package com.wini.owle.air.domain.api

import com.wini.owle.air.db.api.ApiEntity
import com.wini.owle.air.db.backoffice.api.ApiRepository
import com.wini.owle.air.domain.repo.EntityToModelMapper
import com.wini.owle.air.domain.repo.DomainAccessor
import com.wini.owle.air.inlines.ApiId
import kotlinx.coroutines.flow.Flow

class ApiAccessor(
    private val repository: ApiRepository
): DomainAccessor<Api, ApiEntity>(repository),
   EntityToModelMapper<ApiEntity, Api> {

    fun getAll(): Flow<List<Api>> {
        return repository.selectAll().mapToModel()
    }

    fun getById(id: ApiId): Flow<Api?> {
        return repository.selectById(id).mapToModelNullable()
    }

    override fun ApiEntity.mapToModel(): Api {
        return Api(id, name, loRoute, openApi)
    }

    override fun Api.mapToEntity(): ApiEntity {
        return ApiEntity(id, name, loRoute, openApi)
    }
}

