package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformTargetTypeEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test

/**
 * Represents the target types for a transformation.
 * Each target type defines which types are eligible for a transformation and the resulting type after the transformation is applied.
 */
abstract class TransformTargetTypeRepoTest : CompositeIdRepoTest<TransformTargetTypeEntity>() {

    override fun mockEntity(): TransformTargetTypeEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TransformTargetTypeEntity {
        return any<TransformTargetTypeEntity>()
    }

    
}
