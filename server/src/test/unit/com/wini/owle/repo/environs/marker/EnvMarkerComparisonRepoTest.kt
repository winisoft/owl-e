package com.wini.owle.repo.environs.marker

import com.wini.owle.entity.environs.marker.EnvMarkerComparisonEntity
import com.wini.owle.dto.environs.marker.EnvMarkerComparison
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


class EnvMarkerComparisonRepoTest : ULongIdGenericRepoTest<EnvMarkerComparisonEntity>() {

    override fun mockEntity(): EnvMarkerComparisonEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): EnvMarkerComparisonEntity {
        return any<EnvMarkerComparisonEntity>()
    }

    

}

