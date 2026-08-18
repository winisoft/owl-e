package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeVarBoundEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


abstract class TypeVarBoundRepoTest : ULongIdGenericRepoTest<TypeVarBoundEntity>() {

    override fun mockEntity(): TypeVarBoundEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TypeVarBoundEntity {
        return any<TypeVarBoundEntity>()
    }

    
}