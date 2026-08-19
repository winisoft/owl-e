package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.DataPointInjectionEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * Represents the prerequisite data injection between workflows.
 * Specifies how `sourceDataPoint` in the completed workflow replaces `injectedDataPoint` in the target workflow.
 */
abstract class DataPointInjectionRepoTest : ULongIdGenericRepoTest<DataPointInjectionEntity>() {

    override fun mockEntity(): DataPointInjectionEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): DataPointInjectionEntity {
        return any<DataPointInjectionEntity>()
    }

}

