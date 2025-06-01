package com.wini.owle.repo.typing

import com.wini.owle.entity.typing.TypeParameterEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


/**
 * Represents parameters used within complex types, allowing for parameterized type declarations.
 * Supports types such as 'List\[int]' or 'Dict[str, int]', where 'int' or 'str' would be parameters of 'List' or 'Dict'.
 */
class TypeParameterRepoTest(id: EntityID<ULong>) : ULongIdGenericRepoTest<TypeParameterEntity>() {

    override fun mockEntity(): TypeParameterEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): TypeParameterEntity {
        return any<TypeParameterEntity>()
    }

    

}

