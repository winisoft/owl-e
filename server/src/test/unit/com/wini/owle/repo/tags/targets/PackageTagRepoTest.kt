package com.wini.owle.repo.tags.targets

import com.wini.owle.entity.tags.targets.PackageTagEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test

/**
 * Represents a tag a given user has associated with a Package
 */
class PackageTagRepoTest : CompositeIdRepoTest<PackageTagEntity>() {

    override fun mockEntity(): PackageTagEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<CompositeID> {
        val id = mockk<EntityID<CompositeID>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<CompositeID> {
        val id = mockk<EntityID<CompositeID>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): PackageTagEntity {
        return any<PackageTagEntity>()
    }

    
}