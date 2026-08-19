package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * Represents a transformation process with a specified return type and related arguments.
 * A transformation can have multiple arguments, target types, and applied transformations.
 */
abstract class TransformRepoTest : ULongIdGenericRepoTest<TransformEntity>() {

    override fun mockEntity(): TransformEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TransformEntity {
        return any<TransformEntity>()
    }


}

