package com.wini.owle.repo.workflows

import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * Represents a step within a workflow, defining its tool dependency, next and previous steps,
 * and whether it serves as the workflow's head step.
 */
abstract class StepRepoTest : ULongIdGenericRepoTest<StepEntity>() {

    override fun mockEntity(): StepEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): StepEntity {
        return any<StepEntity>()
    }


}

