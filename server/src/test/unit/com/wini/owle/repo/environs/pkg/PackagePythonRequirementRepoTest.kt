package com.wini.owle.repo.environs.pkg

import com.wini.owle.entity.environs.pkg.PackagePythonRequirementEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk

/**
 * Specifies version constraints for a package, allowing for precise control
 * over the range or exact version of a dependency.
 */
abstract class PackagePythonRequirementRepoTest : CompositeIdRepoTest<PackagePythonRequirementEntity>() {

    override fun mockEntity(): PackagePythonRequirementEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): PackagePythonRequirementEntity {
        return any<PackagePythonRequirementEntity>()
    }


}