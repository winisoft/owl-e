package com.wini.owle.repo.environs.marker

import com.wini.owle.entity.environs.marker.EnvMarkerExpressionEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


abstract class EnvMarkerExpressionRepoTest : ULongIdGenericRepoTest<EnvMarkerExpressionEntity>() {

    override fun mockEntity(): EnvMarkerExpressionEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): EnvMarkerExpressionEntity {
        return any<EnvMarkerExpressionEntity>()
    }

}

