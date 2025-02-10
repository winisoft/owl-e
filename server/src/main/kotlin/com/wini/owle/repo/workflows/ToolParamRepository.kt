package com.wini.owle.repo.workflows

import com.wini.owle.entity.Tools
import com.wini.owle.entity.workflows.ToolParamDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction

class ToolParamRepository : OwleLongIdRepoImpl<ToolParamDao>() {

    override val entityClass: LongEntityClass<ToolParamDao>
        get() = ToolParamDao

    fun getParamsOfTool(toolId: Long): List<ToolParamDao> {
        return getWhere {
            it.tool.id == EntityID(toolId, Tools)
        }
    }

    override fun getWhere(predicate: (ToolParamDao) -> Boolean): List<ToolParamDao> {
        return transaction {
            ToolParamDao.all().filter(predicate)
        }
    }
}