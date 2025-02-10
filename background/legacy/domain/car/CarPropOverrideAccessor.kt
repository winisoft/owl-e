package com.wini.owle.air.domain.car

import arrow.core.Either
import com.wini.owle.air.db.car.CarOverrideEntity
import com.wini.owle.air.db.library.car.CarOverrideRepository
import com.wini.owle.air.domain.model.LoItemBreakdown
import com.wini.owle.air.domain.model.item.CarPropOverride
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.repo.DomainAccessor
import com.wini.owle.air.domain.repo.LoError
import com.wini.owle.air.inlines.CarOverrideId
import com.wini.owle.air.inlines.CarPropId
import com.wini.owle.air.inlines.EnvironmentId
import com.wini.owle.air.inlines.OverrideValue
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
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
                        carPropId,
                        environmentId,
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

    suspend fun getEnabledWith(id: CarPropId): Either<LoError, OverrideValue> {
        return repository.getIsFullyEnabledAndCarPropId(id).map {
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
            carPropId,
            environmentId,
            overrideName,
            overrideValue,
            description,
            isEnabled,
            isHorizontalDisable
        )
    }

    fun setIsEnabled(carPropOverride: CarPropOverride): Flow<Either<LoError, Unit>> = repository.updateSetIsEnabledTrue(
        id = carPropOverride.id,
        carPropId = carPropOverride.carPropId,
        envId = carPropOverride.environmentId
    ).map {
        it.mapLeft { cause ->
            LoError.fromThrowable(cause)
        }
    }

    fun getAllInEnvironment(id: EnvironmentId): Flow<List<CarPropOverride>> = repository
        .selectWhereEnvironmentId(id)
        .map { list ->
            list.map { entity ->
                entity.run {
                    CarPropOverride(
                        id = this.id,
                        carPropId = carPropId,
                        environmentId = environmentId,
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

    fun getNextId(): CarOverrideId {
        return repository.queries.getMaxId().executeAsOne().value.run { CarOverrideId(this) }
    }

/*    fun getLoItemBreakdown(envId: EnvironmentId): Flow<LoItemBreakdown> {
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