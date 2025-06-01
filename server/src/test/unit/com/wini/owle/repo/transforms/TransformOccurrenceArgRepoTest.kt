package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformOccurrenceArgEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


class TransformOccurrenceArgRepoTest(id: EntityID<ULong>) : ULongIdGenericRepoTest<TransformOccurrenceArgEntity>() {

    override fun mockEntity(): TransformOccurrenceArgEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): TransformOccurrenceArgEntity {
        return any<TransformOccurrenceArgEntity>()
    }

    

}

