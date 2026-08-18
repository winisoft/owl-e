package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeVarBoundEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


open class TypeVarBoundRepoTest : ULongIdGenericRepoTest<TypeVarBoundEntity>() {

    override fun mockEntity(): TypeVarBoundEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): TypeVarBoundEntity {
        return any<TypeVarBoundEntity>()
    }

    
}