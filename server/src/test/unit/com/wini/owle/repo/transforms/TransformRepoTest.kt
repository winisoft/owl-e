package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.entity.typing.TypeNodeEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


/**
 * Represents a transformation process with a specified return type and related arguments.
 * A transformation can have multiple arguments, target types, and applied transformations.
 */
class TransformRepoTest(id: EntityID<ULong>) : ULongIdGenericRepoTest<TransformEntity>() {

    override fun mockEntity(): TransformEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): TransformEntity {
        return any<TransformEntity>()
    }

    

}

