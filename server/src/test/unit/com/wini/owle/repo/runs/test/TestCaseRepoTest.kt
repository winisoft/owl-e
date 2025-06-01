package com.wini.owle.repo.runs.test

import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.entity.workflows.StepEntity
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
 * Defines a test case to validate workflows or steps.
 * Each test case may contain multiple inputs, expected outcomes, and historical results.
 */
class TestCaseRepoTest(id: EntityID<ULong>) : ULongIdGenericRepoTest<TestCaseEntity>() {

    override fun mockEntity(): TestCaseEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): TestCaseEntity {
        return any<TestCaseEntity>()
    }

    

}

