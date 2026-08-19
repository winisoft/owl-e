package com.wini.owle.repo.tags.targets

import com.wini.owle.entity.tags.targets.DistributionTagEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk

/**
 * Represents a tag a given user has associated with a Distribution
 */
abstract class DistributionTagRepoTest : CompositeIdRepoTest<DistributionTagEntity>() {

    override fun mockEntity(): DistributionTagEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): DistributionTagEntity {
        return any<DistributionTagEntity>()
    }


}