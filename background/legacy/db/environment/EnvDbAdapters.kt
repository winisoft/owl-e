package com.wini.owle.air.db.environment

import com.wini.owle.air.db.InlineAdapters
import com.wini.owle.air.db.group.EnvironmentGroupEntity
import com.wini.owle.air.db.group.GroupEntity

object EnvDbAdapters {

    val environmentEntityAdapter = EnvironmentEntity.Adapter(
        isEnabledAdapter = com.wini.owle.air.db.InlineAdapters.Bool.isEnabled,
        isExpandedAdapter = com.wini.owle.air.db.InlineAdapters.Bool.isExpanded,
        idAdapter = com.wini.owle.air.db.InlineAdapters.TypeId.environmentId,
        nameAdapter = com.wini.owle.air.db.InlineAdapters.String.name,
        descriptionAdapter = com.wini.owle.air.db.InlineAdapters.String.description,
        isHorizontalDisabledAdapter = com.wini.owle.air.db.InlineAdapters.Bool.isHorizontalDisabled
    )

    val groupEntityAdapter = GroupEntity.Adapter(
        idAdapter = com.wini.owle.air.db.InlineAdapters.TypeId.groupId,
        nameAdapter = com.wini.owle.air.db.InlineAdapters.String.name
    )

    val environmentGroupEntityAdapter = EnvironmentGroupEntity.Adapter(
        groupIdAdapter = com.wini.owle.air.db.InlineAdapters.TypeId.groupId,
        environmentIdAdapter = com.wini.owle.air.db.InlineAdapters.TypeId.environmentId,
        isEnabledAdapter = com.wini.owle.air.db.InlineAdapters.Bool.isEnabled,
        isExpandedAdapter = com.wini.owle.air.db.InlineAdapters.Bool.isExpanded
    )

}