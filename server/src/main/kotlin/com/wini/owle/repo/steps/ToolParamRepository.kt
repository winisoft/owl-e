package com.wini.owle.repo.steps

import com.wini.owle.entity.Tools
import com.wini.owle.entity.steps.ToolParamEntity
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.id.EntityID

class ToolParamRepository: OwleRepoImpl<Long, ToolParamEntity>(ToolParamEntity.Companion) {

    fun getParamsOfTool(toolId: Long): List<ToolParamEntity> {
        return getWhere {
            it.tool.id == EntityID(toolId, Tools)
        }
    }

}