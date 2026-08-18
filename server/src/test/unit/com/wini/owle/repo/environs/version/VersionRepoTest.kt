package com.wini.owle.repo.environs.version

import com.wini.owle.entity.environs.version.VersionEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID


open class VersionRepoTest : ULongIdGenericRepoTest<VersionEntity>() {

    override fun mockEntity(): VersionEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): VersionEntity {
        return any<VersionEntity>()
    }

    

}

