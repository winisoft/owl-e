package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeVarBoundEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


class TypeVarBoundRepoTest(id: EntityID<CompositeID>): CompositeIdRepoTest<TypeVarBoundEntity>() {

    override fun mockEntity(): TypeVarBoundEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<CompositeID> {
        val id = mockk<EntityID<CompositeID>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<CompositeID> {
        val id = mockk<EntityID<CompositeID>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): TypeVarBoundEntity {
        return any<TypeVarBoundEntity>()
    }

    
}