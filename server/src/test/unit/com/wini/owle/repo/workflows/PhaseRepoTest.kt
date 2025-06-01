package com.wini.owle.repo.workflows

import com.wini.owle.entity.workflows.PhaseEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


/**
 * Represents a phase within a workflow, grouping steps or other phases.
 * Phases support hierarchical organization, enabling subphases.
 */
class PhaseRepoTest(id: EntityID<ULong>) : ULongIdGenericRepoTest<PhaseEntity>() {

    override fun mockEntity(): PhaseEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): PhaseEntity {
        return any<PhaseEntity>()
    }

    

}

