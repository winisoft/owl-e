package com.wini.owle.air.domain.aa.environment

import arrow.core.Either
import com.wini.owle.air.db.environment.EnvironmentGroupRepository
import com.wini.owle.air.db.group.EnvironmentGroupEntity
import com.wini.owle.air.db.group.SelectAll
import com.wini.owle.air.db.group.SelectByCompoundId
import com.wini.owle.air.domain.model.item.ItemCategory
import com.wini.owle.air.domain.model.item.ItemGrouping
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.repo.*
import com.wini.owle.air.domain.aa.inlines.GroupId
import com.wini.owle.air.domain.aa.DbSelectAllMapper
import com.wini.owle.air.domain.aa.DbSelectByIdMapper
import com.wini.owle.air.domain.aa.DomainAccessor
import com.wini.owle.air.domain.aa.LoError
import kotlinx.coroutines.flow.*

class EnvironmentGroupAccessor(
    private val repository: EnvironmentGroupRepository
): DomainAccessor<ItemGrouping, EnvironmentGroupEntity>(repository),
    DbSelectAllMapper<SelectAll, ItemGrouping> by EnvGroupSelectAllMapper(),
    DbSelectByIdMapper<SelectByCompoundId, ItemGrouping> by EnvGroupByIdMapper() {

    fun all(): Flow<List<LoItem>> = flowOf(repository.queries.selectAll().executeAsList().mapSelectAllToModel())

    fun getAll(): Flow<List<ItemGrouping>> = flowOf(repository.queries.selectAll().executeAsList()
        .mapSelectAllToModel())

    fun getById(id: GroupId, envId: Long): Flow<ItemGrouping?> = flowOf(repository.queries.selectByCompoundId(id, envId).executeAsOneOrNull()
        .mapSelectByIdToModel())

    fun getByEnvironmentAndGroup(env: Long, category: ItemCategory): Flow<Either<LoError, ItemGrouping>> =
        flowOf(repository.queries.selectWhereEnvIdAndGroupId(env, category.id).executeAsOneOrNull())
            .map {
                it?.run {
                    ItemGrouping.build(
                        Long,
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

//    fun getBooleanBreakdown(envId: Long): Flow<EnvironmentGroupsEnabled> = repository.selectBoolean(envId).single().executeAsList().asFlow().map {
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
            Long,
            isEnabled,
            isExpanded
        )
    }

}