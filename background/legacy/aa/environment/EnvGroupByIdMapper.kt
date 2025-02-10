package com.wini.owle.air.domain.aa.environment

import com.wini.owle.air.db.group.SelectByCompoundId
import com.wini.owle.air.domain.model.item.ItemGrouping
import com.wini.owle.air.domain.aa.DbSelectByIdMapper

class EnvGroupByIdMapper: DbSelectByIdMapper<SelectByCompoundId, ItemGrouping> {

    override fun SelectByCompoundId?.mapSelectByIdToModel(): ItemGrouping? {
        return this?.run {
            ItemGrouping.build(
                Long,
                groupId,
                isEnabled,
                isExpanded,
                isVisible,
                isDisabledImplicit
            )
        }
    }
}