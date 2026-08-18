package com.wini.owle.repo.environs.marker

import com.wini.owle.entity.environs.marker.EnvMarkerExpressionEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test

class EnvMarkerExpressionRepoTest : ULongIdGenericRepoTest<EnvMarkerExpressionEntity>() {

    override fun mockEntity(): EnvMarkerExpressionEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): EnvMarkerExpressionEntity {
        return any<EnvMarkerExpressionEntity>()
    }

    

}

