package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeVarVarianceEntity
import com.wini.owle.dto.typing.TypeVarVariance
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


class TypeVarVarianceRepoTest : ULongIdGenericRepoTest<TypeVarVarianceEntity>() {

    override fun mockEntity(): TypeVarVarianceEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): TypeVarVarianceEntity {
        return any<TypeVarVarianceEntity>()
    }

    
}
