package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeUnionMemberEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * Represents individual member types within a union type declaration.
 * Supports union types such as 'Union[int, str]' by linking each member type to the union type declaration.
 */
abstract class TypeUnionMemberRepoTest : CompositeIdRepoTest<TypeUnionMemberEntity>() {

    override fun mockEntity(): TypeUnionMemberEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): TypeUnionMemberEntity {
        return any<TypeUnionMemberEntity>()
    }


}