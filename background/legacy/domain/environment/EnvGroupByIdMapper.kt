package com.wini.owle.air.domain.environment

import com.wini.owle.air.db.group.SelectByCompoundId
import com.wini.owle.air.domain.model.item.ItemGrouping
import com.wini.owle.air.domain.repo.DbSelectAllMapper
import com.wini.owle.air.domain.repo.DbSelectByIdMapper

class EnvGroupByIdMapper: DbSelectByIdMapper<SelectByCompoundId, ItemGrouping> {

    override fun SelectByCompoundId?.mapSelectByIdToModel(): ItemGrouping? {
        return this?.run {
            ItemGrouping.build(
                environmentId,
                groupId,
                isEnabled,
                isExpanded,
                isVisible,
                isDisabledImplicit
            )
        }
    }
}