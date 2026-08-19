package com.wini.owle.repo.workflows

import com.wini.owle.entity.workflows.WorkflowFolderEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk

/**
 * Represents a folder for organizing workflows in a hierarchical structure.
 * Supports nested folders and categorizes workflows.
 */
abstract class WorkflowFolderRepoTest : ULongIdGenericRepoTest<WorkflowFolderEntity>() {

    override fun mockEntity(): WorkflowFolderEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): WorkflowFolderEntity {
        return any<WorkflowFolderEntity>()
    }


}

