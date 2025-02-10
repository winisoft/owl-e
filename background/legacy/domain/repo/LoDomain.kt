@file:Suppress("UNCHECKED_CAST")
package com.wini.owle.air.domain.repo

import arrow.core.Either
import com.wini.owle.air.domain.api.ApiAccessor
import com.wini.owle.air.domain.api.ApiOverrideAccessor
import com.wini.owle.air.domain.cal.CalOverrideAccessor
import com.wini.owle.air.domain.cal.CalibrationAccessor
import com.wini.owle.air.domain.call.CallAccessor
import com.wini.owle.air.domain.car.CarPropAccessor
import com.wini.owle.air.domain.car.CarPropOverrideAccessor
import com.wini.owle.air.domain.endpoint.EndpointAccessor
import com.wini.owle.air.domain.endpoint.EndpointOverrideAccessor
import com.wini.owle.air.domain.environment.EnvironmentAccessor
import com.wini.owle.air.domain.environment.EnvironmentBreakdown
import com.wini.owle.air.domain.environment.EnvironmentGroupAccessor
import com.wini.owle.air.domain.model.LoItemBreakdown
import com.wini.owle.air.domain.model.ParentItem
import com.wini.owle.air.domain.model.item.*
import com.wini.owle.air.domain.model.prop.RunningState
import com.wini.owle.air.domain.prefs.SharedPrefAccessor
import com.wini.owle.air.domain.prefs.SharedPrefOverrideAccessor
import com.wini.owle.air.domain.root.RootItem
import com.wini.owle.air.domain.settings.SettingsAccessor
import com.wini.owle.air.domain.sysprop.SystemPropAccessor
import com.wini.owle.air.domain.sysprop.SystemPropOverrideAccessor
import com.wini.owle.air.inlines.EnvironmentId
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


interface LoDomain {

    val rootItems: RootItem
    val apis: ApiAccessor
    val apiOverrides: ApiOverrideAccessor
    val endpoints: EndpointAccessor
    val endpointOverrides: EndpointOverrideAccessor
    val calibrations: CalibrationAccessor
    val calOverrides: CalOverrideAccessor
    val carProperties: CarPropAccessor
    val carPropOverrides: CarPropOverrideAccessor
    val systemProperties: SystemPropAccessor
    val systemPropOverrides: SystemPropOverrideAccessor
    val sharedPreferences: SharedPrefAccessor
    val sharedPrefOverrides: SharedPrefOverrideAccessor
    val environments: EnvironmentAccessor
    val calls: CallAccessor
    val envGroups: EnvironmentGroupAccessor
    val settings: SettingsAccessor

    suspend fun add(loItem: LoItem) {

        when (loItem) {
            is Environment -> environments.add(loItem)
            is ApiOverride -> apiOverrides.add(loItem)
            is CalOverride -> calOverrides.add(loItem)
            is CarPropOverride -> carPropOverrides.add(loItem)
            is EndpointOverride -> endpointOverrides.add(loItem)
            is SharedPrefOverride -> sharedPrefOverrides.add(loItem)
            is SystemPropOverride -> systemPropOverrides.add(loItem)
            else -> {
                error("")
            }
        }
    }

//    @OptIn(FlowPreview::class)
//    suspend fun Flow<Pair<LoRootItem, LoItem>>.save(): Flow<Either<LoError, Unit>> = flatMapConcat { (_, item) ->
//        when (item) {
//            is LoRootItem -> rootItems.save(item)
//            else -> error("")
//        }
//    }

    suspend fun save(loItem: LoItem): Flow<Either<LoError, Unit>> = when (loItem) {
        is LoRootItem -> rootItems.save(loItem)
        is Environment -> environments.save(loItem)
        is ApiOverride -> apiOverrides.save(loItem)
        is CalOverride -> calOverrides.save(loItem)
        is CarPropOverride -> carPropOverrides.save(loItem)
        is EndpointOverride -> endpointOverrides.save(loItem)
        is SharedPrefOverride -> sharedPrefOverrides.save(loItem)
        is SystemPropOverride -> systemPropOverrides.save(loItem)
        is ItemGrouping -> envGroups.save(loItem)
        else -> {
            error("")
        }
    }

    suspend fun toggleExpanded(item: ParentItem): Flow<Either<LoError, Unit>> {
        when (item) {
            is LoRootItem -> item.copyExpandedToggled()
            is Environment -> item.copyExpandedToggled()
            is ApiOverride -> item.copyExpandedToggled()
            is EndpointOverride -> item.copyExpandedToggled()
            is ItemGrouping -> item.copyExpandedToggled()
            else -> error("unanticipated parent type item...")
        }.run {
            return save(this)
        }
    }

    suspend fun collapseRoot(): Flow<Either<LoError, Unit>> {
        return rootItems.collapse()
    }

    fun setRootRunningState(runningState: RunningState): Flow<Either<LoError, Unit>> =
        rootItems.setRunningState(runningState)

    suspend fun toggleEnabled(item: LoItem): Flow<Either<LoError, Unit>> {
        return if (item.isEnabled.value) disableItem(item) else enableItem(item)
    }

    private suspend fun disableItem(item: LoItem): Flow<Either<LoError, Unit>> = when (item) {
        is ApisGroup,
        is CalOverrideGroup,
        is CarOverrideGroup,
        is SysPropOverrideGroup,
        is SharedPrefOverrideGroup -> envGroups.save(item.copyEnabledToggled() as ItemGrouping)

        is LoRootItem -> rootItems.save(item.copyEnabledToggled() as LoRootItem)
        is Environment -> environments.save(item)
        is ApiOverride -> apiOverrides.save(item)
        is CalOverride -> calOverrides.save(item)
        is CarPropOverride -> carPropOverrides.save(item)
        is SharedPrefOverride -> sharedPrefOverrides.save(item)
        is SystemPropOverride -> systemPropOverrides.save(item)
        is EndpointOverride -> endpointOverrides.save(item)
        is Call -> calls.save(item)
        else -> error("Impossible condition -- required else due to subclasses already covered by other cases")
    }

    private suspend fun enableItem(item: LoItem): Flow<Either<LoError, Unit>> = when (item) {
        is ApisGroup,
        is CalOverrideGroup,
        is CarOverrideGroup,
        is SysPropOverrideGroup,
        is SharedPrefOverrideGroup -> envGroups.save(item.copyEnabledToggled() as ItemGrouping)

        is LoRootItem -> rootItems.save(item.copyEnabledToggled() as LoRootItem)
        is Environment -> environments.setIsEnabled(item)
        is ApiOverride -> apiOverrides.setIsEnabled(item)
        is CalOverride -> calOverrides.setIsEnabled(item)
        is CarPropOverride -> carPropOverrides.setIsEnabled(item)
        is SharedPrefOverride -> sharedPrefOverrides.setIsEnabled(item)
        is SystemPropOverride -> systemPropOverrides.updateSetIsEnabled(item)
        is EndpointOverride -> endpointOverrides.setIsEnabled(item)
        is Call -> calls.enable(item)
        else -> error("Unexpected type")
    }

    fun all(): Flow<List<LoItem>> =
        combine(
            rootItems.all(),
            environments.all(),
            envGroups.all(),
            calOverrides.all(),
            carPropOverrides.all(),
            systemPropOverrides.all(),
            sharedPrefOverrides.all(),
            apiOverrides.all(),
            endpointOverrides.all(),
            calls.all()
        ) {
            it.fold(emptyList()) { acc: List<LoItem>, each: List<LoItem> ->
                acc.plus(each)
            }
        }.sort()

    suspend fun List<LoItem>.getItemHeritage(item: LoItem): List<LoItem> = when (item) {
        is LoRootItem -> emptyList()
        is Environment -> listOf(rootItems.get())
        is ItemGrouping -> environments.getById(item.environmentId).single().orNull()?.run {
            listOf(rootItems.get(), this)
        } ?: emptyList()

        is ApiOverride,
        is CalOverride,
        is CarPropOverride,
        is SystemPropOverride,
        is SharedPrefOverride -> {
            item as Environment.ChildOf
            val env = environments.getById(item.environmentId).first()
            val envGroup = envGroups.getByEnvironmentAndGroup(item.environmentId, item.category).first()
            val right: List<LoItem> = listOf(rootItems.get()).plus(env.orNull()!!).plus(envGroup.orNull()!!)
            right
        }

        is EndpointOverride -> {
            val apiOverride = filterIsInstance<ApiOverride>().first { it.id == item.apiOverrideId }
            getItemHeritage(apiOverride).plus(apiOverride as LoItem)
        }

        is Call -> {
            val endpointOverride = filterIsInstance<EndpointOverride>().first { it.id == item.endpointOverrideId }
            getItemHeritage(endpointOverride).plus(endpointOverride)
        }
    }

    suspend fun delete(item: LoItem): Flow<Either<LoError, Unit>> = when (item) {
        is CalOverride -> calOverrides.remove(item)
        is CarPropOverride -> carPropOverrides.remove(item)
        is Environment -> environments.remove(item)
        is SharedPrefOverride -> sharedPrefOverrides.remove(item)
        is SystemPropOverride -> systemPropOverrides.remove(item)
        is ApiOverride -> apiOverrides.remove(item)
        is EndpointOverride -> endpointOverrides.remove(item)
        is Call -> calls.remove(item)
        else -> error("Not a type that can be deleted")
    }

    fun Flow<List<LoItem>>.sort(): Flow<List<LoItem>> = map { items ->
        items.sortedWith(compareBy(
            { if (it is LoRootItem) -1 else 0 },
            {
                when (it) {
                    is Environment -> it.id.value.toInt()
                    is Environment.ChildOf -> it.environmentId.value.toInt()
                    else -> 0
                }
            }, {
                //TODO
                if (it is Environment.ChildOf) it.category.id.value.toInt() else 0
            }, {
                when (it) {
                    is ApiOverride -> it.id.value.toInt()
                    is ApiOverride.ChildOf -> it.apiOverrideId.value.toInt()
                    else -> 0
                }
            }, {
                when (it) {
                    is EndpointOverride -> it.id.value.toInt()
                    is EndpointOverride.ChildOf -> it.endpointOverrideId.value.toInt()
                    else -> 0
                }
            }, {
                if (it is Call) it.id.value.toInt() else 0
            }
        ))
    }

    fun List<LoItem>.getEnvironmentCounts(items: List<LoItem>, envId: EnvironmentId): EnvironmentBreakdown {
        return EnvironmentBreakdown(
            apiOverrideBreakdown = filterIsInstance<ApiOverride>()
                .filter { it.environmentId == envId }.run {
                    Triple(size, filter { it.isEnabled.value }.size, filter { it.isHorizontalDisable.value }.size)
                }.let { (countTotal, countIsEnabled, countHorizontalDisabled) ->
                    LoItemBreakdown(
                        countTotal = countTotal,
                        countIsEnabled = countIsEnabled,
                        countHorizontalDisabled = countHorizontalDisabled,
                        doesGroupExist = filterIsInstance<ApisGroup>().find { it.environmentId == envId } != null,
                        isGroupDisabled = filterIsInstance<ApisGroup>().find { it.environmentId == envId }?.isEnabled?.value == false
                    )
                },
            calOverrideBreakdown = filterIsInstance<CalOverride>()
                .filter { it.environmentId == envId }.run {
                    Triple(size, filter { it.isEnabled.value }.size, filter { it.isHorizontalDisable.value }.size)
                }.let { (countTotal, countIsEnabled, countHorizontalDisabled) ->
                    LoItemBreakdown(
                        countTotal = countTotal,
                        countIsEnabled = countIsEnabled,
                        countHorizontalDisabled = countHorizontalDisabled,
                        doesGroupExist = filterIsInstance<CalOverrideGroup>().find { it.environmentId == envId } != null,
                        isGroupDisabled = filterIsInstance<CalOverrideGroup>().find { it.environmentId == envId }?.isEnabled?.value == false
                    )
                },
            carOverrideBreakdown = filterIsInstance<CarPropOverride>()
                .filter { it.environmentId == envId }.run {
                    Triple(size, filter { it.isEnabled.value }.size, filter { it.isHorizontalDisable.value }.size)
                }.let { (countTotal, countIsEnabled, countHorizontalDisabled) ->
                    LoItemBreakdown(
                        countTotal = countTotal,
                        countIsEnabled = countIsEnabled,
                        countHorizontalDisabled = countHorizontalDisabled,
                        doesGroupExist = filterIsInstance<CarOverrideGroup>().find { it.environmentId == envId } != null,
                        isGroupDisabled = filterIsInstance<CarOverrideGroup>().find { it.environmentId == envId }?.isEnabled?.value == false
                    )
                },
            systemPropOverrideBreakdown = filterIsInstance<SystemPropOverride>()
                .filter { it.environmentId == envId }.run {
                    Triple(size, filter { it.isEnabled.value }.size, filter { it.isHorizontalDisable.value }.size)
                }.let { (countTotal, countIsEnabled, countHorizontalDisabled) ->
                    LoItemBreakdown(
                        countTotal = countTotal,
                        countIsEnabled = countIsEnabled,
                        countHorizontalDisabled = countHorizontalDisabled,
                        doesGroupExist = filterIsInstance<SysPropOverrideGroup>().find { it.environmentId == envId } != null,
                        isGroupDisabled = filterIsInstance<SysPropOverrideGroup>().find { it.environmentId == envId }?.isEnabled?.value == false
                    )
                },
            sharedPrefOverrideBreakdown = filterIsInstance<SharedPrefOverride>()
                .filter { it.environmentId == envId }.run {
                    Triple(size, filter { it.isEnabled.value }.size, filter { it.isHorizontalDisable.value }.size)
                }.let { (countTotal, countIsEnabled, countHorizontalDisabled) ->
                    LoItemBreakdown(
                        countTotal = countTotal,
                        countIsEnabled = countIsEnabled,
                        countHorizontalDisabled = countHorizontalDisabled,
                        doesGroupExist = filterIsInstance<SharedPrefOverrideGroup>().find { it.environmentId == envId } != null,
                        isGroupDisabled = filterIsInstance<SharedPrefOverrideGroup>().find { it.environmentId == envId }?.isEnabled?.value == false
                    )
                },
        )
    }

}

