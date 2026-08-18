package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.WorkflowJunctionEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


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

