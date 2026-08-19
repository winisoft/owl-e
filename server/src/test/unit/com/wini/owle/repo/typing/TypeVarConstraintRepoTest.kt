package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeVarConstraintEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk

/**
 *
 */
abstract class TypeVarConstraintRepoTest : ULongIdGenericRepoTest<TypeVarConstraintEntity>() {

    override fun mockEntity(): TypeVarConstraintEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TypeVarConstraintEntity {
        return any<TypeVarConstraintEntity>()
    }


}

