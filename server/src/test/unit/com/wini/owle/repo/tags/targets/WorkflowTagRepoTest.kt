package com.wini.owle.repo.tags.targets

import com.wini.owle.entity.tags.targets.WorkflowTagEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk

/**
 * Represents a tag a given user has associated with a workflow
 */
abstract class WorkflowTagRepoTest : CompositeIdRepoTest<WorkflowTagEntity>() {

    override fun mockEntity(): WorkflowTagEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): WorkflowTagEntity {
        return any<WorkflowTagEntity>()
    }


}