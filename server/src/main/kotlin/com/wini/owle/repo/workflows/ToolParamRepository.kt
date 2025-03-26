package com.wini.owle.repo.workflows

import com.wini.owle.entity.Tools
import com.wini.owle.entity.workflows.ToolParamDao
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction

class ToolParamRepository: OwleRepoImpl<Long, ToolParamDao>(ToolParamDao) {

    fun getParamsOfTool(toolId: Long): List<ToolParamDao> {
        return getWhere {
            it.tool.id == EntityID(toolId, Tools)
        }
    }

}