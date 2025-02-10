package com.wini.owle.air.domain.aa

import arrow.core.Either
import com.wini.owle.air.db.prefs.SharedPrefOverrideEntity
import com.wini.owle.air.db.library.prefs.SharedPrefOverrideRepository
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.model.item.SharedPrefOverride
import com.wini.owle.air.domain.model.prop.DataType
import com.wini.owle.air.inlines.Long
import com.wini.owle.air.inlines.OverrideName
import com.wini.owle.air.inlines.Long
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class SharedPrefOverrideAccessor(
    private val repository: SharedPrefOverrideRepository
): DomainAccessor<SharedPrefOverride, SharedPrefOverrideEntity>(repository) {

    fun getAll(): Flow<List<SharedPrefOverride>> = emptyFlow()

    fun getById(id: Long): Flow<SharedPrefOverride?> = emptyFlow()

    fun all(): Flow<List<LoItem>> = flowOf(repository.queries.selectAll().executeAsList())
        .map { selectAllResults ->
            selectAllResults.map { resultRow ->
                resultRow.run {
                    SharedPrefOverride(
                        id,
                        sharedPrefId,
                        Long,
                        friendlyId = "",
                        DataType.String,
                        overrideValue,
                        overrideName = String(""),
                        description = description,
                        isEnabled = isEnabled,
                        isVisible = isVisible,
                        isImplicitDisable = isDisabledImplicit,
                        isHorizontalDisable = isHorizontalDisabled
                    )
                }
            }
        }

    override fun SharedPrefOverride.mapToEntity(): SharedPrefOverrideEntity {
        return SharedPrefOverrideEntity(
            id = id,
            sharedPrefId = sharedPrefId,
            Long = Long,
            overrideValue = overrideValue,
            description = description,
            isEnabled = isEnabled,
            isHorizontalDisabled = isHorizontalDisable
        )
    }

    fun setBoolean(sharedPrefOverride: SharedPrefOverride): Flow<Either<LoError, Unit>> = repository.updateSetBooleanTrue(
        id = sharedPrefOverride.id,
        sharedPrefId = sharedPrefOverride.sharedPrefId,
        envId = sharedPrefOverride.Long
    ).map {
        it.mapLeft { cause ->
            LoError.fromThrowable(cause)
        }
    }

    fun getAllInEnvironment(envId: Long): Flow<List<SharedPrefOverride>> = repository.selectWhereEnvId(envId)
        .map { list ->
            list.map { entity ->
                entity.run {
                    SharedPrefOverride(
                        id,
                        sharedPrefId,
                        Long,
                        friendlyId = "",
                        DataType.String,
                        overrideValue,
                        overrideName = String(""),
                        description = description,
                        isEnabled = isEnabled,
                        isVisible = isVisible,
                        isImplicitDisable = isDisabledImplicit,
                        isHorizontalDisable = isHorizontalDisabled
                    )
                }
            }
        }

/*    fun getLoItemBreakdown(envId: Long): Flow<LoItemBreakdown> {
        return repository.getSharedPrefOverrideBreakdown(envId).map {
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