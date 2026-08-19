package com.wini.owle.repo.tags.targets

import com.wini.owle.entity.tags.targets.WorkflowFolderTagEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk

/**
 * Represents a tag a given user has associated with a workflowFolder
 */
abstract class WorkflowFolderTagRepoTest : CompositeIdRepoTest<WorkflowFolderTagEntity>() {

    override fun mockEntity(): WorkflowFolderTagEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): WorkflowFolderTagEntity {
        return any<WorkflowFolderTagEntity>()
    }


}