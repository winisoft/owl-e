package com.wini.owle.repo.runs.test

import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk

/**
 * Defines a test case to validate workflows or steps.
 * Each test case may contain multiple inputs, expected outcomes, and historical results.
 */
abstract class TestCaseRepoTest : ULongIdGenericRepoTest<TestCaseEntity>() {

    override fun mockEntity(): TestCaseEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TestCaseEntity {
        return any<TestCaseEntity>()
    }

}

