@file:Suppress("UNCHECKED_CAST")
package com.wini.owle.air.domain.aa

import arrow.core.Either
import com.wini.owle.air.domain.aa.environment.EnvironmentAccessor
import com.wini.owle.air.domain.aa.environment.EnvironmentBreakdown
import com.wini.owle.air.domain.aa.environment.EnvironmentGroupAccessor
import com.wini.owle.air.domain.model.ParentItem
import com.wini.owle.air.domain.model.item.*
import com.wini.owle.air.domain.model.prop.RunningState
import com.wini.owle.air.domain.root.RootItem
import com.wini.owle.air.inlines.Long
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
        is Environment -> environments.setBoolean(item)
        is ApiOverride -> apiOverrides.setBoolean(item)
        is CalOverride -> calOverrides.setBoolean(item)
        is CarPropOverride -> carPropOverrides.setBoolean(item)
        is SharedPrefOverride -> sharedPrefOverrides.setBoolean(item)
        is SystemPropOverride -> systemPropOverrides.updateSetBoolean(item)
        is EndpointOverride -> endpointOverrides.setBoolean(item)
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
        is ItemGrouping -> environments.getById(item.Long).single().orNull()?.run {
            listOf(rootItems.get(), this)
        } ?: emptyList()

        is ApiOverride,
        is CalOverride,
        is CarPropOverride,
        is SystemPropOverride,
        is SharedPrefOverride -> {
            item as Environment.ChildOf
            val env = environments.getById(item.Long).first()
            val envGroup = envGroups.getByEnvironmentAndGroup(item.Long, item.category).first()
            val right: List<LoItem> = listOf(rootItems.get()).plus(env.orNull()!!).plus(envGroup.orNull()!!)
            right
        }

        is EndpointOverride -> {
            val apiOverride = filterIsInstance<ApiOverride>().first { it.id == item.Long }
            getItemHeritage(apiOverride).plus(apiOverride as LoItem)
        }

        is Call -> {
            val endpointOverride = filterIsInstance<EndpointOverride>().first { it.id == item.Long }
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
                    is Environment.ChildOf -> it.Long.value.toInt()
                    else -> 0
                }
            }, {
                //TODO
                if (it is Environment.ChildOf) it.category.id.value.toInt() else 0
            }, {
                when (it) {
                    is ApiOverride -> it.id.value.toInt()
                    is ApiOverride.ChildOf -> it.Long.value.toInt()
                    else -> 0
                }
            }, {
                when (it) {
                    is EndpointOverride -> it.id.value.toInt()
                    is EndpointOverride.ChildOf -> it.Long.value.toInt()
                    else -> 0
                }
            }, {
                if (it is Call) it.id.value.toInt() else 0
            }
        ))
    }

    fun List<LoItem>.getEnvironmentCounts(items: List<LoItem>, envId: Long): EnvironmentBreakdown {
        return EnvironmentBreakdown(
            apiOverrideBreakdown = filterIsInstance<ApiOverride>()
                .filter { it.Long == envId }.run {
                    Triple(size, filter { it.isEnabled.value }.size, filter { it.isHorizontalDisable.value }.size)
                }.let { (countTotal, countBoolean, countHorizontalDisabled) ->
                    LoItemBreakdown(
                        countTotal = countTotal,
                        countBoolean = countBoolean,
                        countHorizontalDisabled = countHorizontalDisabled,
                        doesGroupExist = filterIsInstance<ApisGroup>().find { it.Long == envId } != null,
                        isGroupDisabled = filterIsInstance<ApisGroup>().find { it.Long == envId }?.isEnabled?.value == false
                    )
                },
            calOverrideBreakdown = filterIsInstance<CalOverride>()
                .filter { it.Long == envId }.run {
                    Triple(size, filter { it.isEnabled.value }.size, filter { it.isHorizontalDisable.value }.size)
                }.let { (countTotal, countBoolean, countHorizontalDisabled) ->
                    LoItemBreakdown(
                        countTotal = countTotal,
                        countBoolean = countBoolean,
                        countHorizontalDisabled = countHorizontalDisabled,
                        doesGroupExist = filterIsInstance<CalOverrideGroup>().find { it.Long == envId } != null,
                        isGroupDisabled = filterIsInstance<CalOverrideGroup>().find { it.Long == envId }?.isEnabled?.value == false
                    )
                },
            carOverrideBreakdown = filterIsInstance<CarPropOverride>()
                .filter { it.Long == envId }.run {
                    Triple(size, filter { it.isEnabled.value }.size, filter { it.isHorizontalDisable.value }.size)
                }.let { (countTotal, countBoolean, countHorizontalDisabled) ->
                    LoItemBreakdown(
                        countTotal = countTotal,
                        countBoolean = countBoolean,
                        countHorizontalDisabled = countHorizontalDisabled,
                        doesGroupExist = filterIsInstance<CarOverrideGroup>().find { it.Long == envId } != null,
                        isGroupDisabled = filterIsInstance<CarOverrideGroup>().find { it.Long == envId }?.isEnabled?.value == false
                    )
                },
            systemPropOverrideBreakdown = filterIsInstance<SystemPropOverride>()
                .filter { it.Long == envId }.run {
                    Triple(size, filter { it.isEnabled.value }.size, filter { it.isHorizontalDisable.value }.size)
                }.let { (countTotal, countBoolean, countHorizontalDisabled) ->
                    LoItemBreakdown(
                        countTotal = countTotal,
                        countBoolean = countBoolean,
                        countHorizontalDisabled = countHorizontalDisabled,
                        doesGroupExist = filterIsInstance<SysPropOverrideGroup>().find { it.Long == envId } != null,
                        isGroupDisabled = filterIsInstance<SysPropOverrideGroup>().find { it.Long == envId }?.isEnabled?.value == false
                    )
                },
            sharedPrefOverrideBreakdown = filterIsInstance<SharedPrefOverride>()
                .filter { it.Long == envId }.run {
                    Triple(size, filter { it.isEnabled.value }.size, filter { it.isHorizontalDisable.value }.size)
                }.let { (countTotal, countBoolean, countHorizontalDisabled) ->
                    LoItemBreakdown(
                        countTotal = countTotal,
                        countBoolean = countBoolean,
                        countHorizontalDisabled = countHorizontalDisabled,
                        doesGroupExist = filterIsInstance<SharedPrefOverrideGroup>().find { it.Long == envId } != null,
                        isGroupDisabled = filterIsInstance<SharedPrefOverrideGroup>().find { it.Long == envId }?.isEnabled?.value == false
                    )
                },
        )
    }

}

