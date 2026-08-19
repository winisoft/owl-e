package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.WorkflowJunctionEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * Represents a junction connecting one workflow to another, allowing a transition to a target workflow.
 * A junction may include conditions under which the transition is triggered,
 * and prerequisite injections required for the target workflow.
 */
abstract class WorkflowJunctionRepoTest : ULongIdGenericRepoTest<WorkflowJunctionEntity>() {

    override fun mockEntity(): WorkflowJunctionEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): WorkflowJunctionEntity {
        return any<WorkflowJunctionEntity>()
    }

}

