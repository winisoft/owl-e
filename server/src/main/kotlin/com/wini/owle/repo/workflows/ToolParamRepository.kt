package com.wini.owle.repo.workflows

import com.wini.owle.entity.Tools
import com.wini.owle.entity.workflows.ToolParamEntity
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.id.EntityID

class ToolParamRepository: OwleRepoImpl<Long, ToolParamEntity>(ToolParamEntity) {

    fun getParamsOfTool(toolId: Long): List<ToolParamEntity> {
        return getWhere {
            it.tool.id == EntityID(toolId, Tools)
        }
    }

}