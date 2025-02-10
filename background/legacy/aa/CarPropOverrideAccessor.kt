package com.wini.owle.air.domain.aa

import arrow.core.Either
import com.wini.owle.air.db.car.CarOverrideEntity
import com.wini.owle.air.db.library.car.CarOverrideRepository
import com.wini.owle.air.domain.model.item.CarPropOverride
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.inlines.Long
import com.wini.owle.air.inlines.Long
import com.wini.owle.air.inlines.Long
import com.wini.owle.air.inlines.OverrideValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single


class CarPropOverrideAccessor(
    private val repository: CarOverrideRepository,
): DomainAccessor<CarPropOverride, CarOverrideEntity>(repository) {

    fun all(): Flow<List<LoItem>> = flowOf(repository.queries.selectAll().executeAsList())
        .map { selectAllResults ->
            selectAllResults.map { resultRow ->
                resultRow.run {
                    CarPropOverride(
                        id,
                        Long,
                        Long,
                        name,
                        overrideName,
                        description,
                        overrideValue,
                        isEnabled,
                        isVisible,
                        isDisabledImplicit,
                        isHorizontalDisabled
                    )
                }
            }
        }

    suspend fun getEnabledWith(id: Long): Either<LoError, OverrideValue> {
        return repository.getIsFullyEnabledAndLong(id).map {
            it.mapLeft { cause ->
                LoError.fromThrowable(cause)
            }.map { confirmedNonNull ->
                confirmedNonNull.overrideValue!!
            }
        }.single()
    }

    override fun CarPropOverride.mapToEntity(): CarOverrideEntity {
        return CarOverrideEntity(
            id,
            Long,
            Long,
            overrideName,
            overrideValue,
            description,
            isEnabled,
            isHorizontalDisable
        )
    }

    fun setBoolean(carPropOverride: CarPropOverride): Flow<Either<LoError, Unit>> = repository.updateSetBooleanTrue(
        id = carPropOverride.id,
        Long = carPropOverride.Long,
        envId = carPropOverride.Long
    ).map {
        it.mapLeft { cause ->
            LoError.fromThrowable(cause)
        }
    }

    fun getAllInEnvironment(id: Long): Flow<List<CarPropOverride>> = repository
        .selectWhereLong(id)
        .map { list ->
            list.map { entity ->
                entity.run {
                    CarPropOverride(
                        id = this.id,
                        Long = Long,
                        Long = Long,
                        name,
                        overrideName,
                        description,
                        overrideValue,
                        isEnabled,
                        isVisible,
                        isDisabledImplicit,
                        isHorizontalDisabled
                    )
                }
            }
        }

    fun getNextId(): Long {
        return repository.queries.getMaxId().executeAsOne().value.run { Long(this) }
    }

/*    fun getLoItemBreakdown(envId: Long): Flow<LoItemBreakdown> {
        return repository.getCarOverrideBreakdown(envId).map {
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