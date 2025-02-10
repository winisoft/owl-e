package com.wini.owle.air.domain.prefs

import arrow.core.Either
import com.wini.owle.air.db.prefs.SharedPrefOverrideEntity
import com.wini.owle.air.db.library.prefs.SharedPrefOverrideRepository
import com.wini.owle.air.domain.model.LoItemBreakdown
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.model.item.SharedPrefOverride
import com.wini.owle.air.domain.model.prop.DataType
import com.wini.owle.air.domain.repo.DomainAccessor
import com.wini.owle.air.domain.repo.LoError
import com.wini.owle.air.inlines.EnvironmentId
import com.wini.owle.air.inlines.OverrideName
import com.wini.owle.air.inlines.SharedPrefOverrideId
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class SharedPrefOverrideAccessor(
    private val repository: SharedPrefOverrideRepository
): DomainAccessor<SharedPrefOverride, SharedPrefOverrideEntity>(repository) {

    fun getAll(): Flow<List<SharedPrefOverride>> = emptyFlow()

    fun getById(id: SharedPrefOverrideId): Flow<SharedPrefOverride?> = emptyFlow()

    fun all(): Flow<List<LoItem>> = flowOf(repository.queries.selectAll().executeAsList())
        .map { selectAllResults ->
            selectAllResults.map { resultRow ->
                resultRow.run {
                    SharedPrefOverride(
                        id,
                        sharedPrefId,
                        environmentId,
                        friendlyId = "",
                        DataType.String,
                        overrideValue,
                        overrideName = OverrideName(""),
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
            environmentId = environmentId,
            overrideValue = overrideValue,
            description = description,
            isEnabled = isEnabled,
            isHorizontalDisabled = isHorizontalDisable
        )
    }

    fun setIsEnabled(sharedPrefOverride: SharedPrefOverride): Flow<Either<LoError, Unit>> = repository.updateSetIsEnabledTrue(
        id = sharedPrefOverride.id,
        sharedPrefId = sharedPrefOverride.sharedPrefId,
        envId = sharedPrefOverride.environmentId
    ).map {
        it.mapLeft { cause ->
            LoError.fromThrowable(cause)
        }
    }

    fun getAllInEnvironment(envId: EnvironmentId): Flow<List<SharedPrefOverride>> = repository.selectWhereEnvId(envId)
        .map { list ->
            list.map { entity ->
                entity.run {
                    SharedPrefOverride(
                        id,
                        sharedPrefId,
                        environmentId,
                        friendlyId = "",
                        DataType.String,
                        overrideValue,
                        overrideName = OverrideName(""),
                        description = description,
                        isEnabled = isEnabled,
                        isVisible = isVisible,
                        isImplicitDisable = isDisabledImplicit,
                        isHorizontalDisable = isHorizontalDisabled
                    )
                }
            }
        }

/*    fun getLoItemBreakdown(envId: EnvironmentId): Flow<LoItemBreakdown> {
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