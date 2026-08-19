package com.wini.owle.repo.tags

import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * Represents a tag that can be associated with workflow steps.
 * Tags help categorize or filter steps and have a many-to-many relationship with StepDao.
 */
abstract class TagRepoTest : ULongIdGenericRepoTest<TagEntity>() {

    override fun mockEntity(): TagEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TagEntity {
        return any<TagEntity>()
    }


}

