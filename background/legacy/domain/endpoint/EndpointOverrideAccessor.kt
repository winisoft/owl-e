package com.wini.owle.air.domain.endpoint

import arrow.core.Either
import com.wini.owle.air.db.endpoint.EndpointOverrideEntity
import com.wini.owle.air.db.backoffice.endpoint.EndpointOverrideRepository
import com.wini.owle.air.db.endpoint.SelectAll
import com.wini.owle.air.db.endpoint.SelectById
import com.wini.owle.air.domain.model.item.EndpointOverride
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.repo.EntityToModelMapper
import com.wini.owle.air.domain.repo.DomainAccessor
import com.wini.owle.air.domain.repo.LoError
import com.wini.owle.air.domain.repo.ModelToEntityMapper
import com.wini.owle.air.inlines.EndpointOverrideId
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class EndpointOverrideAccessor(
    private val repository: EndpointOverrideRepository
): DomainAccessor<EndpointOverride, EndpointOverrideEntity>(repository),
    ModelToEntityMapper<EndpointOverride, EndpointOverrideEntity> by object:
    ModelToEntityMapper<EndpointOverride, EndpointOverrideEntity> {
        override fun EndpointOverride.mapToEntity(): EndpointOverrideEntity {
            return EndpointOverrideEntity(
                id,
                endpointId,
                apiOverrideId,
                overrideName,
                description,
                isEnabled,
                isExpanded,
                isHorizontalDisable,
            )
        }
    } {


    private val selectAllMapper: EntityToModelMapper<SelectAll, EndpointOverride>
        get() = object: EntityToModelMapper<SelectAll, EndpointOverride> {
            override fun SelectAll.mapToModel(): EndpointOverride {
                return EndpointOverride(
                    id,
                    endpointId,
                    environmentId,
                    apiOverrideId,
                    overrideName,
                    description,
                    name,
                    path,
                    isEnabled,
                    isExpanded,
                    isVisible,
                    isDisabledImplicit,
                    isHorizontalDisabled
                )
            }
        }

    private val selectByIdMapper: EntityToModelMapper<SelectById, EndpointOverride>
        get() = object: EntityToModelMapper<SelectById, EndpointOverride> {
            override fun SelectById.mapToModel(): EndpointOverride {
                return EndpointOverride(
                    id,
                    endpointId,
                    environmentId,
                    apiOverrideId,
                    overrideName,
                    description,
                    endpointName,
                    endpointPath,
                    isEnabled,
                    isExpanded,
                    isVisible,
                    isDisabledImplicit,
                    isHorizontalDisabled
                )
            }
        }

    fun all(): Flow<List<LoItem>> = flowOf(repository.queries.selectAll().executeAsList())
        .map { selectAllResults -> selectAllMapper.run { selectAllResults.mapToModel() } }

    fun getById(id: EndpointOverrideId): Flow<EndpointOverride?> = repository.selectById(id).map {
        selectByIdMapper.run { it.mapToModelNullable() }
    }

    fun setIsEnabled(endpointOverride: EndpointOverride): Flow<Either<LoError, Unit>> = repository.updateSetIsEnabledTrue(
        id = endpointOverride.id,
        apiOverrideId = endpointOverride.apiOverrideId
    ).map {
        it.mapLeft { cause ->
            LoError.fromThrowable(cause)
        }
    }

}

