package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.ErrorWatchEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


/**
 * Represents a watch on specific types of errors that may occur during a step's execution.
 * Specifies the error type and the action to take if the error occurs.
 */
abstract class ErrorWatchRepoTest : ULongIdGenericRepoTest<ErrorWatchEntity>() {

    override fun mockEntity(): ErrorWatchEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): ErrorWatchEntity {
        return any<ErrorWatchEntity>()
    }

    

}

