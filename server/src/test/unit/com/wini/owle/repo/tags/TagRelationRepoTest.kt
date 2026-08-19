package com.wini.owle.repo.tags

import com.wini.owle.entity.tags.TagRelationEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


abstract class TagRelationRepoTest : CompositeIdRepoTest<TagRelationEntity>() {

    override fun mockEntity(): TagRelationEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TagRelationEntity {
        return any<TagRelationEntity>()
    }


}