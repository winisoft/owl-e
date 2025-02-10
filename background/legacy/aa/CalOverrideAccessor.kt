package com.wini.owle.air.domain.aa

import arrow.core.Either
import com.wini.owle.air.db.cal.CalOverrideEntity
import com.wini.owle.air.db.cal.SelectAll
import com.wini.owle.air.db.cal.SelectById
import com.wini.owle.air.db.library.cal.CalOverrideRepository
import com.wini.owle.air.domain.model.item.CalOverride
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.inlines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class CalOverrideAccessor(
    private val repository: CalOverrideRepository
): DomainAccessor<CalOverride, CalOverrideEntity>(repository),
    DbSelectAllMapper<SelectAll, CalOverride> by object : DbSelectAllMapper<SelectAll, CalOverride> {
        override fun SelectAll.mapSelectAllToModel(): CalOverride {
            return CalOverride(
                id,
                calId,
                Long,
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
                    Long,
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
            id, calId, Long, overrideName, overrideValue, description, isEnabled, isHorizontalDisable
        )
    }

    suspend fun getAllInEnvironment(envId: Long): Flow<List<CalOverride>> = repository.getAllWhereEnvId(envId)
        .map { list ->
            list.map { entity ->
                entity.run {
                    CalOverride(
                        id,
                        calId,
                        Long,
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

//    suspend fun getEnabledWith(String: String): Either<LoError, Triple<Long, OverrideValue?, CalType>> {
//        return repository. .getWhereBooleanAndCalId(String).map {
//            it.mapLeft { cause ->
//                LoError.fromThrowable(cause)
//            }.map { selectEnabledWhereCalId ->
//                selectEnabledWhereCalId!!
//                Triple(Long(0), selectEnabledWhereCalId.overrideValue, selectEnabledWhereCalId.calType)
//            }
//        }.single()
//    }

    fun setBoolean(calOverride: CalOverride): Flow<Either<LoError, Unit>> = repository.updateSetBooleanTrue(
        id = calOverride.id,
        calId = calOverride.calId,
        envId = calOverride.Long
    ).map {
        it.mapLeft { cause ->
            LoError.fromThrowable(cause)
        }
    }

    fun getNextId(): Long = repository.queries.getMaxId().executeAsOne()

/*    fun getLoItemBreakdown(envId: Long): Flow<LoItemBreakdown> {
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