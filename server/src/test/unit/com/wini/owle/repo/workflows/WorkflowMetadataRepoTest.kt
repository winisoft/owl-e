package com.wini.owle.repo.workflows

import com.wini.owle.entity.workflows.WorkflowMetadataEntity
import com.wini.owle.entity.workflows.metadata.WorkflowMetadata
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test

/**
 * Represents metadata for workflows or steps within the Owl-E application.
 * Includes information on authorship, usage permissions, and sharing configuration.
 */
class WorkflowMetadataRepoTest(id: EntityID<ULong>) : ULongIdGenericRepoTest<WorkflowMetadataEntity>() {

    override fun mockEntity(): WorkflowMetadataEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): WorkflowMetadataEntity {
        return any<WorkflowMetadataEntity>()
    }

    

}

