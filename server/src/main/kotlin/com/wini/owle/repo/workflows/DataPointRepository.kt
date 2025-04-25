package com.wini.owle.repo.workflows

import com.wini.owle.entity.Workflows
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.id.EntityID

class DataPointRepository: OwleRepoImpl<Long, DataPointEntity>(DataPointEntity) {

    fun getDataPointsOfWorkflow(workflowId: Long): List<DataPointEntity> {
        return getWhere {
            it.workflow.id == EntityID(workflowId, Workflows)
        }
    }

}