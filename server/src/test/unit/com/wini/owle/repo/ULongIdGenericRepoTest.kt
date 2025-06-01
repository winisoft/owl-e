package com.wini.owle.repo

import io.mockk.MockKMatcherScope
import io.mockk.every
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import io.mockk.mockk

/**
 * Abstract test class for repositories that use ULong as ID type.
 * This class implements the ID-specific methods from GenericRepoTest
 * so concrete test implementations only need to focus on the entity-specific methods.
 */
abstract class ULongIdGenericRepoTest<T : Entity<ULong>> : GenericRepoTest<ULong, T>() {
    
    override fun mockID(): ULong = 1uL
    
    override fun MockKMatcherScope.anyID(): ULong = any()
    
    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        every { id.value } returns mockID()
        return id
    }
    
    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        every { id.value } returns 999uL
        return id
    }
}