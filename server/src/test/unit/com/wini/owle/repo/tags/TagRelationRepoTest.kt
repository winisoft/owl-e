package com.wini.owle.repo.tags

import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import com.wini.owle.entity.tags.TagRelationEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID


open class TagRelationRepoTest : CompositeIdRepoTest<TagRelationEntity>() {

    override fun mockEntity(): TagRelationEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<CompositeID> {
        val id = mockk<EntityID<CompositeID>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<CompositeID> {
        val id = mockk<EntityID<CompositeID>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): TagRelationEntity {
        return any<TagRelationEntity>()
    }

    
}