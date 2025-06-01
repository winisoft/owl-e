package com.wini.owle.repo.runs.step

import com.wini.owle.entity.runs.step.StepTestRunEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents the execution instance of a step within a workflow execution.
 * Tracks the status, start and completion times, and links to execution logs.
 */
class StepRunEntity(id: EntityID<ULong>) : ULongIdGenericRepoTest<StepTestRunEntity>() {

    override fun mockEntity(): StepTestRunEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): StepTestRunEntity {
        return any<StepTestRunEntity>()
    }

    

}

