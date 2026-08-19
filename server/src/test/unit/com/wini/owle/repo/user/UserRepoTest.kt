package com.wini.owle.repo.user

import com.wini.owle.entity.user.UserEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * Represents a user in the Owl-E application, with authentication details,
 * subscription status, and related payment methods.
 */
abstract class UserRepoTest : ULongIdGenericRepoTest<UserEntity>() {

    override fun mockEntity(): UserEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): UserEntity {
        return any<UserEntity>()
    }


}

