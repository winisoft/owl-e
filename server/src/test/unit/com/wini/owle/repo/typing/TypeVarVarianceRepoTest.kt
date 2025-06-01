package com.wini.owle.repo.typing

import com.wini.owle.dto.typing.TypeVarVariance
import com.wini.owle.entity.typing.TypeVarBoundEntity
import com.wini.owle.entity.typing.TypeVarVarianceEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


class TypeVarVarianceRepoTest(id: EntityID<CompositeID>): CompositeIdRepoTest<TypeVarVarianceEntity>() {

    override fun mockEntity(): TypeVarVarianceEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<CompositeID> {
        val id = mockk<EntityID<CompositeID>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<CompositeID> {
        val id = mockk<EntityID<CompositeID>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): TypeVarVarianceEntity {
        return any<TypeVarVarianceEntity>()
    }

    
}
