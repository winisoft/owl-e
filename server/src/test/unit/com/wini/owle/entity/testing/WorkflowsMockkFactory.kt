package com.wini.owle.entity.testing

import com.wini.owle.entity.workflows.WorkflowFolderEntity
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.Instant


/**
 * Canned Entity mockk instances for tests.
 *
 * One factory per entity package, composed by [EntityMockkFactory]. Cross-package
 * instances are obtained from the sibling factory that owns them, so each file stays
 * responsible for exactly the types its own package declares.
 */
object WorkflowsMockkFactory {

    fun workflowFolderDao(
        parentFolder: WorkflowFolderEntity? = null,
        name: String = "ExampleFolder",
        description: String? = "Folder description",
        createdAt: Instant = mockk(relaxed = true),
        updatedAt: Instant = mockk(relaxed = true)
    ): WorkflowFolderEntity = mockk(relaxed = true) {
        every { this@mockk.parentFolder } returns parentFolder
        every { this@mockk.name } returns name
        every { this@mockk.description } returns description
        every { this@mockk.createdAt } returns createdAt
        every { this@mockk.updatedAt } returns updatedAt
    }

}
