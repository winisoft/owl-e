package com.wini.owle.repo.runs.test

import com.wini.owle.entity.runs.test.TestOutcomeExpectationEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Defines an expected outcome for a test case, specifying the expected value of
 * a data point after execution and the comparison operator to use.
 */
class TestExpectationRepoTest(id: EntityID<ULong>) : ULongIdGenericRepoTest<TestOutcomeExpectationEntity>() {

    override fun mockEntity(): TestOutcomeExpectationEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): TestOutcomeExpectationEntity {
        return any<TestOutcomeExpectationEntity>()
    }

    

}

