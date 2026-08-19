package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformOccurrenceArgEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


abstract class TransformOccurrenceArgRepoTest : ULongIdGenericRepoTest<TransformOccurrenceArgEntity>() {

    override fun mockEntity(): TransformOccurrenceArgEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TransformOccurrenceArgEntity {
        return any<TransformOccurrenceArgEntity>()
    }


}

