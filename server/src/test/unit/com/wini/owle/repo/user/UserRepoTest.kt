package com.wini.owle.repo.user

import com.wini.owle.entity.user.UserEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


/**
 * Represents a user in the Owl-E application, with authentication details,
 * subscription status, and related payment methods.
 */
class UserRepoTest(id: EntityID<ULong>) : ULongIdGenericRepoTest<UserEntity>() {

    override fun mockEntity(): UserEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): UserEntity {
        return any<UserEntity>()
    }

    

}

