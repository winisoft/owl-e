package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformArgEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


abstract class TransformArgRepoTest : ULongIdGenericRepoTest<TransformArgEntity>() {

    override fun mockEntity(): TransformArgEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TransformArgEntity {
        return any<TransformArgEntity>()
    }


}

