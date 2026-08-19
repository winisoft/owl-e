package com.wini.owle.repo.workflows

import com.wini.owle.entity.workflows.WorkflowMetadataEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk

/**
 * Represents metadata for workflows or steps within the Owl-E application.
 * Includes information on authorship, usage permissions, and sharing configuration.
 */
abstract class WorkflowMetadataRepoTest : ULongIdGenericRepoTest<WorkflowMetadataEntity>() {

    override fun mockEntity(): WorkflowMetadataEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): WorkflowMetadataEntity {
        return any<WorkflowMetadataEntity>()
    }


}

