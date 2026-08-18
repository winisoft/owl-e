package com.wini.owle.repo.runs.workflow

import com.wini.owle.entity.runs.workflow.WorkflowExecutionEntity
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


/**
 * Represents an execution instance of a workflow. Tracks the execution details such as start and end times,
 * user executing the workflow, and current status of the workflow execution.
 */
class WorkflowRunRepoTest(id: EntityID<ULong>) : ULongIdGenericRepoTest<WorkflowExecutionEntity>() {

    override fun mockEntity(): WorkflowExecutionEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): WorkflowExecutionEntity {
        return any<WorkflowExecutionEntity>()
    }

    

}

