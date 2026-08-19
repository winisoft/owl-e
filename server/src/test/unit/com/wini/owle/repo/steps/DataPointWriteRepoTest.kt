package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.DataPointWriteEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * Represents a revision made to a data point following the successful execution of a step.
 * Tracks the affected data point, the responsible step, and optionally, any transformation.
 */
abstract class DataPointWriteRepoTest : ULongIdGenericRepoTest<DataPointWriteEntity>() {

    override fun mockEntity(): DataPointWriteEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): DataPointWriteEntity {
        return any<DataPointWriteEntity>()
    }

}

