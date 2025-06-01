package com.wini.owle.repo.runs.workflow

import com.wini.owle.entity.runs.workflow.TestResultEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Stores the outcome of a test case execution, indicating whether the test passed or failed.
 * Includes the execution timestamp for audit and debugging purposes.
 */
class TestResultRepoTest(id: EntityID<ULong>) : ULongIdGenericRepoTest<TestResultEntity>() {

    override fun mockEntity(): TestResultEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): TestResultEntity {
        return any<TestResultEntity>()
    }

    

}

