package com.wini.owle.repo.runs.workflow

import com.wini.owle.entity.runs.workflow.TestResultEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * Stores the outcome of a test case execution, indicating whether the test passed or failed.
 * Includes the execution timestamp for audit and debugging purposes.
 */
abstract class TestResultRepoTest : ULongIdGenericRepoTest<TestResultEntity>() {

    override fun mockEntity(): TestResultEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TestResultEntity {
        return any<TestResultEntity>()
    }

}

