package com.wini.owle.repo.tags.targets

import com.wini.owle.entity.tags.targets.ToolTagEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk

/**
 * Represents a tag a given user has associated with a tool
 */
abstract class ToolTagRepoTest : CompositeIdRepoTest<ToolTagEntity>() {

    override fun mockEntity(): ToolTagEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): ToolTagEntity {
        return any<ToolTagEntity>()
    }


}