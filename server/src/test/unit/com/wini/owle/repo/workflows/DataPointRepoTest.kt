package com.wini.owle.repo.workflows

import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


/**
 * Represents a data point within a workflow, used for tracking values,
 * inputs, or outputs that may be accessed by workflow steps.
 */
abstract class DataPointRepoTest : ULongIdGenericRepoTest<DataPointEntity>() {

    override fun mockEntity(): DataPointEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): DataPointEntity {
        return any<DataPointEntity>()
    }

    

}

