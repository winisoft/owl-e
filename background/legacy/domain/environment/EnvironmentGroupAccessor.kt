package com.wini.owle.air.domain.environment

import arrow.core.Either
import com.wini.owle.air.db.environment.EnvironmentGroupRepository
import com.wini.owle.air.db.group.EnvironmentGroupEntity
import com.wini.owle.air.db.group.SelectAll
import com.wini.owle.air.db.group.SelectByCompoundId
import com.wini.owle.air.db.group.SelectGroupIdIsEnabledWhereEnvId
import com.wini.owle.air.domain.model.item.EnvironmentGroup
import com.wini.owle.air.domain.model.item.ItemCategory
import com.wini.owle.air.domain.model.item.ItemGrouping
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.repo.*
import com.wini.owle.air.inlines.EnvironmentGroupId
import com.wini.owle.air.inlines.EnvironmentId
import com.wini.owle.air.inlines.GroupId
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.*

class EnvironmentGroupAccessor(
    private val repository: EnvironmentGroupRepository
): DomainAccessor<ItemGrouping, EnvironmentGroupEntity>(repository),
    DbSelectAllMapper<SelectAll, ItemGrouping> by EnvGroupSelectAllMapper(),
    DbSelectByIdMapper<SelectByCompoundId, ItemGrouping> by EnvGroupByIdMapper() {

    fun all(): Flow<List<LoItem>> = flowOf(repository.queries.selectAll().executeAsList().mapSelectAllToModel())

    fun getAll(): Flow<List<ItemGrouping>> = flowOf(repository.queries.selectAll().executeAsList()
        .mapSelectAllToModel())

    fun getById(id: GroupId, envId: EnvironmentId): Flow<ItemGrouping?> = flowOf(repository.queries.selectByCompoundId(id, envId).executeAsOneOrNull()
        .mapSelectByIdToModel())

    fun getByEnvironmentAndGroup(env: EnvironmentId, category: ItemCategory): Flow<Either<LoError, ItemGrouping>> =
        flowOf(repository.queries.selectWhereEnvIdAndGroupId(env, category.id).executeAsOneOrNull())
            .map {
                it?.run {
                    ItemGrouping.build(
                        environmentId,
                        groupId,
                        isEnabled,
                        isExpanded,
                        isVisible,
                        isDisabledImplicit
                    ).run {
                        Either.Right(this)
                    }
                } ?: Either.Left(LoError.ExpectedDbValueNotFound) as Either<LoError, ItemGrouping>
            }

//    fun getIsEnabledBreakdown(envId: EnvironmentId): Flow<EnvironmentGroupsEnabled> = repository.selectIsEnabled(envId).single().executeAsList().asFlow().map {
//            EnvironmentGroupsEnabled(
//                isApiOverrideGroupEnabled = list.find { it.groupId.value == 0L }?.isEnabled?.value,
//                isCalOverrideGroupEnabled = list.find { it.groupId.value == 1L }?.isEnabled?.value,
//                isCarOverrideGroupEnabled = list.find { it.groupId.value == 2L }?.isEnabled?.value,
//                isSystemPropOverrideGroupEnabled = list.find { it.groupId.value == 3L }?.isEnabled?.value,
//                isSharedPrefOverrideGroupEnabled = list.find { it.groupId.value == 4L }?.isEnabled?.value,
//            )
//        }

    override fun ItemGrouping.mapToEntity(): EnvironmentGroupEntity {
        return EnvironmentGroupEntity(
            category.id,
            environmentId,
            isEnabled,
            isExpanded
        )
    }

}