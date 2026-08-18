package com.wini.owle.repo.transforms

import com.wini.owle.entity.transforms.TransformOccurrenceEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


/**
 * Represents an instance of an applied transformation.
 * Each applied transformation has a type, may recursively reference a previous applied transform,
 * and specifies the result type it produces.
 */
open class TransformOccurrenceRepoTest : ULongIdGenericRepoTest<TransformOccurrenceEntity>() {

    override fun mockEntity(): TransformOccurrenceEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): TransformOccurrenceEntity {
        return any<TransformOccurrenceEntity>()
    }

    

}

