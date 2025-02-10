package com.wini.owle.air.domain.api

import arrow.core.Either
import com.wini.owle.air.db.api.ApiOverrideEntity
import com.wini.owle.air.db.api.SelectAll
import com.wini.owle.air.db.api.SelectById
import com.wini.owle.air.db.backoffice.api.ApiOverrideRepository
import com.wini.owle.air.domain.model.LoItemBreakdown
import com.wini.owle.air.domain.model.item.ApiOverride
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.repo.DbSelectAllMapper
import com.wini.owle.air.domain.repo.DbSelectByIdMapper
import com.wini.owle.air.domain.repo.DomainAccessor
import com.wini.owle.air.domain.repo.LoError
import com.wini.owle.air.inlines.*
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class ApiOverrideAccessor(
    private val repository: ApiOverrideRepository
): DomainAccessor<ApiOverride, ApiOverrideEntity>(repository),
   DbSelectAllMapper<SelectAll, ApiOverride> by object: DbSelectAllMapper<SelectAll, ApiOverride> {
       override fun SelectAll.mapSelectAllToModel(): ApiOverride = ApiOverride(id, apiId, environmentId, loRoute, name, openApi, overrideName, description,
           isEnabled, isExpanded, isVisible, isDisabledImplicit, isHorizontalDisabled)
   },
   DbSelectByIdMapper<SelectById, ApiOverride?> by object: DbSelectByIdMapper<SelectById, ApiOverride?> {
       override fun SelectById?.mapSelectByIdToModel(): ApiOverride? {
           return this?.run {
               ApiOverride(
                   id, apiId, environmentId, loRoute, name, openApi, overrideName, description, isEnabled, isExpanded, isDisabledImplicit, isVisible, isHorizontalDisabled
               )
           }
       }
   }
{

    fun getAll(): Flow<List<ApiOverride>> = repository.selectAll().mapSelectAllToModel()

     fun getById(id: ApiOverrideId): Flow<ApiOverride?> = repository.selectById(id).mapSelectByIdToModel()

    fun all(): Flow<List<LoItem>> = flowOf(repository.queries.selectAll().executeAsList())
        .mapSelectAllToModel()

    fun getAllInEnvironment(environmentId: EnvironmentId): Flow<List<ApiOverride>> = repository
        .selectAll()
        .mapSelectAllToModel()

    fun getPathsOfApiWith(loRoute: LoRoute): List<Pair<EndpointOverrideId, EndpointPath>> {
        return repository.selectEndpointOverridesWithEnabledStackWhereApiHasPrefix(loRoute).map {
            it.id to it.path
        }
    }

    fun getWhereApiId(apiId: ApiId): Either<LoError, List<ApiOverride>> = Either.Right(emptyList<ApiOverride>()) as Either<LoError, List<ApiOverride>>

    fun setIsEnabled(apiOverride: ApiOverride): Flow<Either<LoError, Unit>> = repository.updateSetIsEnabledTrue(
        apiOverride.id,
        apiOverride.apiId,
        apiOverride.environmentId
    ).map {
        it.mapLeft { cause ->
            LoError.fromThrowable(cause)
        }
    }

    override fun ApiOverride.mapToEntity(): ApiOverrideEntity {
        return ApiOverrideEntity(id, apiId, environmentId, overrideName, description, isEnabled, isExpanded, isHorizontalDisable)
    }

    fun getNextId(): ApiOverrideId {
        return repository.queries.getMaxId().executeAsOne()
    }
}