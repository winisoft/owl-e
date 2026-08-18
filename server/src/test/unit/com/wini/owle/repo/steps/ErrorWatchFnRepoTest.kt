package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.ErrorWatchFnEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


/**
 * Represents a function filter in an error watch, specifying a function
 * name and position that must appear in the call stack for the error watch to trigger.
 */
class ErrorWatchFnRepoTest : ULongIdGenericRepoTest<ErrorWatchFnEntity>() {

    override fun mockEntity(): ErrorWatchFnEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): ErrorWatchFnEntity {
        return any<ErrorWatchFnEntity>()
    }

    

}

