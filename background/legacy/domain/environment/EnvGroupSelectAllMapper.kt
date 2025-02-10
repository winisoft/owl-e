package com.wini.owle.air.domain.environment

import com.wini.owle.air.db.group.SelectAll
import com.wini.owle.air.domain.model.item.ItemGrouping
import com.wini.owle.air.domain.repo.DbSelectAllMapper

class EnvGroupSelectAllMapper: DbSelectAllMapper<SelectAll, ItemGrouping> {

    override fun SelectAll.mapSelectAllToModel(): ItemGrouping {
        return ItemGrouping.build(
            environmentId,
            groupId,
            isEnabled,
            isExpanded,
            isVisible,
            isDisabledImplicit
        )
    }

}