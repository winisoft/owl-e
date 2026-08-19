package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeVarBoundEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


abstract class TypeVarBoundRepoTest : ULongIdGenericRepoTest<TypeVarBoundEntity>() {

    override fun mockEntity(): TypeVarBoundEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TypeVarBoundEntity {
        return any<TypeVarBoundEntity>()
    }


}