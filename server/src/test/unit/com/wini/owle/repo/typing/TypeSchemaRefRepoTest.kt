package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeSchemaRefEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk

/**
 * Represents a reference within a type schema, supporting nested or hierarchical type structures.
 * Each schema reference is tied to a specific type declaration and may contain child references.
 */
abstract class TypeSchemaRefRepoTest : ULongIdGenericRepoTest<TypeSchemaRefEntity>() {

    override fun mockEntity(): TypeSchemaRefEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TypeSchemaRefEntity {
        return any<TypeSchemaRefEntity>()
    }


}