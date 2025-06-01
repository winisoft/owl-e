package com.wini.owle.repo

import io.mockk.MockKMatcherScope
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


abstract class CompositeIdRepoTest<T : CompositeEntity> : GenericRepoTest<CompositeID, T>() {

    override fun mockID(): CompositeID = CompositeID { }

    override fun MockKMatcherScope.anyID(): CompositeID = any()

    override fun mockEntityId(): EntityID<CompositeID> {
        val id = mockk<EntityID<CompositeID>>()
        every { id.value } returns mockID()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<CompositeID> {
        val id = mockk<EntityID<CompositeID>>()
        every { id.value } returns CompositeID {}
        return id
    }
}
