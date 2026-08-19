package com.wini.owle.repo.environs.pkg

import com.wini.owle.entity.environs.pkg.PackageDistRequirementEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


abstract class PackageRequiredDistRepoTest : ULongIdGenericRepoTest<PackageDistRequirementEntity>() {

    override fun mockEntity(): PackageDistRequirementEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): PackageDistRequirementEntity {
        return any<PackageDistRequirementEntity>()
    }

}

