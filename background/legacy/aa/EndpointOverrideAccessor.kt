package com.wini.owle.air.domain.aa

import arrow.core.Either
import com.wini.owle.air.db.endpoint.EndpointOverrideEntity
import com.wini.owle.air.db.backoffice.endpoint.EndpointOverrideRepository
import com.wini.owle.air.db.endpoint.SelectAll
import com.wini.owle.air.db.endpoint.SelectById
import com.wini.owle.air.domain.model.item.EndpointOverride
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.inlines.Long
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
                Long,
                Long,
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
                    Long,
                    Long,
                    Long,
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
                    Long,
                    Long,
                    Long,
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

    fun getById(id: Long): Flow<EndpointOverride?> = repository.selectById(id).map {
        selectByIdMapper.run { it.mapToModelNullable() }
    }

    fun setBoolean(endpointOverride: EndpointOverride): Flow<Either<LoError, Unit>> = repository.updateSetBooleanTrue(
        id = endpointOverride.id,
        Long = endpointOverride.Long
    ).map {
        it.mapLeft { cause ->
            LoError.fromThrowable(cause)
        }
    }

}

