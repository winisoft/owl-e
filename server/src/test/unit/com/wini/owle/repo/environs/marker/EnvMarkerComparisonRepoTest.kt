package com.wini.owle.repo.environs.marker

import com.wini.owle.entity.environs.marker.EnvMarkerComparisonEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


abstract class EnvMarkerComparisonRepoTest : ULongIdGenericRepoTest<EnvMarkerComparisonEntity>() {

    override fun mockEntity(): EnvMarkerComparisonEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): EnvMarkerComparisonEntity {
        return any<EnvMarkerComparisonEntity>()
    }

}

