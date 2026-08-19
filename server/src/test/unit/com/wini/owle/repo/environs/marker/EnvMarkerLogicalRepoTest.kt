package com.wini.owle.repo.environs.marker

import com.wini.owle.entity.environs.marker.EnvMarkerLogicalEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


abstract class EnvMarkerLogicalRepoTest : ULongIdGenericRepoTest<EnvMarkerLogicalEntity>() {

    override fun mockEntity(): EnvMarkerLogicalEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): EnvMarkerLogicalEntity {
        return any<EnvMarkerLogicalEntity>()
    }

}

