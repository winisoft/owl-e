package com.wini.owle.air.domain.sysprop

import arrow.core.Either
import com.wini.owle.air.db.sysprop.SystemPropOverrideEntity
import com.wini.owle.air.db.library.sysprop.SystemOverrideRepository
import com.wini.owle.air.db.sysprop.SelectAll
import com.wini.owle.air.domain.model.LoItemBreakdown
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.model.item.SystemPropOverride
import com.wini.owle.air.domain.repo.DbSelectAllMapper
import com.wini.owle.air.domain.repo.DomainAccessor
import com.wini.owle.air.domain.repo.LoError
import com.wini.owle.air.domain.repo.ModelToEntityMapper
import com.wini.owle.air.inlines.EnvironmentId
import com.wini.owle.air.inlines.SystemPropOverrideId
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class SystemPropOverrideAccessor(
    private val repository: SystemOverrideRepository
): DomainAccessor<SystemPropOverride, SystemPropOverrideEntity>(repository),
    ModelToEntityMapper<SystemPropOverride, SystemPropOverrideEntity> by object:
    ModelToEntityMapper<SystemPropOverride, SystemPropOverrideEntity> {
        override fun SystemPropOverride.mapToEntity(): SystemPropOverrideEntity {
            return SystemPropOverrideEntity(
                id,
                systemPropId,
                environmentId,
                overrideValue,
                description,
                isEnabled,
                isHorizontalDisable
            )
        }
    } {

        private val selectAllMapper = object: DbSelectAllMapper<SelectAll, SystemPropOverride> {
            override fun SelectAll.mapSelectAllToModel(): SystemPropOverride {
                return SystemPropOverride(
                    id,
                    systemPropId,
                    environmentId,
                    key,
                    overrideValue,
                    isEnabled,
                    isVisible,
                    isDisabledImplicit,
                    isHorizontalDisabled
                )
            }
        }

        fun all(): Flow<List<LoItem>> = flowOf(repository.queries.selectAll().executeAsList())
            .map { list -> selectAllMapper.run { list.mapSelectAllToModel() } }

    fun getAllInEnvironment(envId: EnvironmentId): Flow<List<SystemPropOverride>> = repository
        .selectWhereEnvironmentId(envId)
        .map { list ->
            list.map {
                it.run {
                    SystemPropOverride(
                        id,
                        systemPropId,
                        environmentId,
                        key,
                        overrideValue,
                        isEnabled,
                        isVisible,
                        isDisabledImplicit,
                        isHorizontalDisabled
                    )
                }
            }
        }

    fun updateSetIsEnabled(sysPropOverride: SystemPropOverride): Flow<Either<LoError, Unit>> = repository.updateSetIsEnabledTrue(
        id = sysPropOverride.id,
        sysPropOverride.systemPropId,
        envId = sysPropOverride.environmentId
    ).map { result ->
        result.mapLeft { cause ->
            LoError.fromThrowable(cause)
        }
    }

    fun getNextId(): SystemPropOverrideId = repository.getNextId()

/*    fun getLoItemBreakdown(envId: EnvironmentId): Flow<LoItemBreakdown> {
        return repository.getSystemPropOverrideBreakdown(envId).map {
            it.run {
                LoItemBreakdown(
                    countTotal.toInt(),
                    countEnabled.toInt(),
                    countHorizontalDisabled.toInt()
                )
            }
        }
    }*/
}