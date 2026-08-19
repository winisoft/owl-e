package com.wini.owle.repo.tags.targets

import com.wini.owle.entity.tags.targets.StepTagEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk

/**
 * Represents a tag a given user has associated with a step
 */
abstract class StepTagRepoTest : CompositeIdRepoTest<StepTagEntity>() {

    override fun mockEntity(): StepTagEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): StepTagEntity {
        return any<StepTagEntity>()
    }


}