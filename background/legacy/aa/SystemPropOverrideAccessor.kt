package com.wini.owle.air.domain.aa

import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.model.item.SystemPropOverride


class SystemPropOverrideAccessor(
    private val repository: SystemOverrideRepository
): DomainAccessor<SystemPropOverride, SystemPropOverrideEntity>(repository),
    ModelToEntityMapper<SystemPropOverride, SystemPropOverrideEntity> by object:
        ModelToEntityMapper<SystemPropOverride, SystemPropOverrideEntity> {
        override fun SystemPropOverride.mapToEntity(): SystemPropOverrideEntity {
            return SystemPropOverrideEntity(
                id,
                Long,
                Long,
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
                    Long,
                    Long,
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

    fun getAllInEnvironment(envId: Long): Flow<List<SystemPropOverride>> = repository
        .selectWhereLong(envId)
        .map { list ->
            list.map {
                it.run {
                    SystemPropOverride(
                        id,
                        Long,
                        Long,
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

    fun updateSetBoolean(sysPropOverride: SystemPropOverride): Flow<Either<LoError, Unit>> = repository.updateSetBooleanTrue(
        id = sysPropOverride.id,
        sysPropOverride.Long,
        envId = sysPropOverride.Long
    ).map { result ->
        result.mapLeft { cause ->
            LoError.fromThrowable(cause)
        }
    }

    fun getNextId(): Long = repository.getNextId()

/*    fun getLoItemBreakdown(envId: Long): Flow<LoItemBreakdown> {
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