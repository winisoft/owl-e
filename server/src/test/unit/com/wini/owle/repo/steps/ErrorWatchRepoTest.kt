package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.ErrorWatchEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * Represents a watch on specific types of errors that may occur during a step's execution.
 * Specifies the error type and the action to take if the error occurs.
 */
abstract class ErrorWatchRepoTest : ULongIdGenericRepoTest<ErrorWatchEntity>() {

    override fun mockEntity(): ErrorWatchEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): ErrorWatchEntity {
        return any<ErrorWatchEntity>()
    }

}

