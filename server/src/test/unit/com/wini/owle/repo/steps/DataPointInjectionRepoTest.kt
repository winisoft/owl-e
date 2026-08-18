package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.DataPointInjectionEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


/**
 * Represents the prerequisite data injection between workflows.
 * Specifies how `sourceDataPoint` in the completed workflow replaces `injectedDataPoint` in the target workflow.
 */
class DataPointInjectionRepoTest : ULongIdGenericRepoTest<DataPointInjectionEntity>() {

    override fun mockEntity(): DataPointInjectionEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): DataPointInjectionEntity {
        return any<DataPointInjectionEntity>()
    }

    

}

