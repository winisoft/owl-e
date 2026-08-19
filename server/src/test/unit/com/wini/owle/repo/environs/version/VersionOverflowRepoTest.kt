package com.wini.owle.repo.environs.version

import com.wini.owle.entity.environs.version.VersionOverflowEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


abstract class VersionOverflowRepoTest : ULongIdGenericRepoTest<VersionOverflowEntity>() {

    override fun mockEntity(): VersionOverflowEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): VersionOverflowEntity {
        return any<VersionOverflowEntity>()
    }

}

