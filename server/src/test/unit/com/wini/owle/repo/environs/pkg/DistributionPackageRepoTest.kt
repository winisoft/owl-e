package com.wini.owle.repo.environs.pkg

import com.wini.owle.entity.environs.pkg.DistributionPackageEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * Represents a unique Python library package, along with its specific version details.
 * Used to store information about packages that can be installed in the workflow.
 */
abstract class DistributionPackageRepoTest : CompositeIdRepoTest<DistributionPackageEntity>() {

    override fun mockEntity(): DistributionPackageEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): DistributionPackageEntity {
        return any<DistributionPackageEntity>()
    }


}
