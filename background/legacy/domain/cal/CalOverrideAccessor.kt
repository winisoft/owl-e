package com.wini.owle.air.domain.cal

import arrow.core.Either
import com.wini.owle.air.db.cal.CalOverrideEntity
import com.wini.owle.air.db.cal.SelectAll
import com.wini.owle.air.db.cal.SelectById
import com.wini.owle.air.db.library.cal.CalOverrideRepository
import com.wini.owle.air.domain.model.LoItemBreakdown
import com.wini.owle.air.domain.model.item.CalOverride
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
import kotlinx.coroutines.flow.single

class CalOverrideAccessor(
    private val repository: CalOverrideRepository
): DomainAccessor<CalOverride, CalOverrideEntity>(repository),
    DbSelectAllMapper<SelectAll, CalOverride> by object : DbSelectAllMapper<SelectAll, CalOverride> {
        override fun SelectAll.mapSelectAllToModel(): CalOverride {
            return CalOverride(
                id,
                calId,
                environmentId,
                defaultName,
                calType,
                defaultValue,
                overrideName,
                overrideValue,
                isEnabled,
                isVisible,
                isDisabledImplicit,
                isHorizontalDisabled
            )
        }
    },
    DbSelectByIdMapper<SelectById, CalOverride?> by object : DbSelectByIdMapper<SelectById, CalOverride?> {
        override fun SelectById?.mapSelectByIdToModel(): CalOverride? {
            return this?.run {
                CalOverride(
                    id,
                    calId,
                    environmentId,
                    defaultName,
                    calType,
                    defaultValue,
                    overrideName,
                    overrideValue,
                    isEnabled,
                    isVisible,
                    isDisabledImplicit,
                    isHorizontalDisabled
                )
            }
        }
    }
{

    fun all(): Flow<List<LoItem>> = flowOf(repository.queries.selectAll().executeAsList())
        .mapSelectAllToModel()

    override fun CalOverride.mapToEntity(): CalOverrideEntity {
        return CalOverrideEntity(
            id, calId, environmentId, overrideName, overrideValue, description, isEnabled, isHorizontalDisable
        )
    }

    suspend fun getAllInEnvironment(envId: EnvironmentId): Flow<List<CalOverride>> = repository.getAllWhereEnvId(envId)
        .map { list ->
            list.map { entity ->
                entity.run {
                    CalOverride(
                        id,
                        calId,
                        environmentId,
                        defaultName,
                        calType,
                        defaultValue,
                        overrideName,
                        overrideValue,
                        isEnabled,
                        isVisible,
                        isDisabledImplicit,
                        isHorizontalDisabled
                    )
                }
            }
        }

//    suspend fun getEnabledWith(calibrationId: CalibrationId): Either<LoError, Triple<CalOverrideId, OverrideValue?, CalType>> {
//        return repository. .getWhereIsEnabledAndCalId(calibrationId).map {
//            it.mapLeft { cause ->
//                LoError.fromThrowable(cause)
//            }.map { selectEnabledWhereCalId ->
//                selectEnabledWhereCalId!!
//                Triple(CalOverrideId(0), selectEnabledWhereCalId.overrideValue, selectEnabledWhereCalId.calType)
//            }
//        }.single()
//    }

    fun setIsEnabled(calOverride: CalOverride): Flow<Either<LoError, Unit>> = repository.updateSetIsEnabledTrue(
        id = calOverride.id,
        calId = calOverride.calId,
        envId = calOverride.environmentId
    ).map {
        it.mapLeft { cause ->
            LoError.fromThrowable(cause)
        }
    }

    fun getNextId(): CalOverrideId = repository.queries.getMaxId().executeAsOne()

/*    fun getLoItemBreakdown(envId: EnvironmentId): Flow<LoItemBreakdown> {
        return repository.getCalOverrideBreakdown(envId).map {
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