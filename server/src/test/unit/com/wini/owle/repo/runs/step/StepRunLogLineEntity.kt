package com.wini.owle.repo.runs.step

import com.wini.owle.entity.runs.step.ExecutionNotificationEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Stores individual log messages generated during step execution.
 * Each log entry includes a timestamp, log level, and message content.
 */
class StepRunLogLineEntity(id: EntityID<ULong>) : ULongIdGenericRepoTest<ExecutionNotificationEntity>() {

    override fun mockEntity(): ExecutionNotificationEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): ExecutionNotificationEntity {
        return any<ExecutionNotificationEntity>()
    }

    

}

