package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.DataPointWriteEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


/**
 * Represents a revision made to a data point following the successful execution of a step.
 * Tracks the affected data point, the responsible step, and optionally, any transformation.
 */
open class DataPointWriteRepoTest : ULongIdGenericRepoTest<DataPointWriteEntity>() {

    override fun mockEntity(): DataPointWriteEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): DataPointWriteEntity {
        return any<DataPointWriteEntity>()
    }

    

}

