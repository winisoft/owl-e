package com.wini.owle.repo.tags

import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a tag that can be associated with workflow steps.
 * Tags help categorize or filter steps and have a many-to-many relationship with StepDao.
 */
open class TagRepoTest : ULongIdGenericRepoTest<TagEntity>() {

    override fun mockEntity(): TagEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): TagEntity {
        return any<TagEntity>()
    }

    

}

