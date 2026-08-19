package com.wini.owle.repo.tags.targets

import com.wini.owle.entity.tags.targets.PackageTagEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk

/**
 * Represents a tag a given user has associated with a Package
 */
abstract class PackageTagRepoTest : CompositeIdRepoTest<PackageTagEntity>() {

    override fun mockEntity(): PackageTagEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): PackageTagEntity {
        return any<PackageTagEntity>()
    }


}