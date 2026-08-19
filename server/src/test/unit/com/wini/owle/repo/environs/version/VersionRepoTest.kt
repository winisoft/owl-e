package com.wini.owle.repo.environs.version

import com.wini.owle.entity.environs.version.VersionEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


abstract class VersionRepoTest : ULongIdGenericRepoTest<VersionEntity>() {

    override fun mockEntity(): VersionEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): VersionEntity {
        return any<VersionEntity>()
    }

}
