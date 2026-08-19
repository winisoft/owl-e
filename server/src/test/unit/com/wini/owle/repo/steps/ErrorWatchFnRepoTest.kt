package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.ErrorWatchFnEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * Represents a function filter in an error watch, specifying a function
 * name and position that must appear in the call stack for the error watch to trigger.
 */
abstract class ErrorWatchFnRepoTest : ULongIdGenericRepoTest<ErrorWatchFnEntity>() {

    override fun mockEntity(): ErrorWatchFnEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): ErrorWatchFnEntity {
        return any<ErrorWatchFnEntity>()
    }

}

