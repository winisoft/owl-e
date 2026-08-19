package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeVarVarianceEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


abstract class TypeVarVarianceRepoTest : ULongIdGenericRepoTest<TypeVarVarianceEntity>() {

    override fun mockEntity(): TypeVarVarianceEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TypeVarVarianceEntity {
        return any<TypeVarVarianceEntity>()
    }


}
