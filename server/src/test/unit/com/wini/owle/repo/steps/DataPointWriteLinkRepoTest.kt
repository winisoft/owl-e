package com.wini.owle.repo.steps

import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test
//
//class DataPointWriteLinkRepoTest(id: EntityID<CompositeID>) : ULongIdGenericRepoTest<DataPointWriteLinkEntity>() {
//
//    override fun mockEntity(): DataPointWriteLinkEntity = mockk(relaxed = true)
//
//    override fun mockEntityId(): EntityID<ULong> {
//        val id = mockk<EntityID<ULong>>()
//        return id
//    }
//
//    override fun mockNonExistentEntityId(): EntityID<ULong> {
//        val id = mockk<EntityID<ULong>>()
//        return id
//    }
//
//    override fun MockKMatcherScope.anyT(): DataPointWriteLinkEntity {
//        return any<DataPointWriteLinkEntity>()
//    }
//
//    @Test
//    fun `type node specific test`() {
//        // Add type-specific tests here if needed
//    }
//
//}
//
