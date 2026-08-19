package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeNodeEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


abstract class TypeNodeRepoTest : ULongIdGenericRepoTest<TypeNodeEntity>() {

    override fun mockEntity(): TypeNodeEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TypeNodeEntity {
        return any<TypeNodeEntity>()
    }


}