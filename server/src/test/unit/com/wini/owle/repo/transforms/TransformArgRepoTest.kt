package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformArgEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


abstract class TransformArgRepoTest : ULongIdGenericRepoTest<TransformArgEntity>() {

    override fun mockEntity(): TransformArgEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TransformArgEntity {
        return any<TransformArgEntity>()
    }

    

}

