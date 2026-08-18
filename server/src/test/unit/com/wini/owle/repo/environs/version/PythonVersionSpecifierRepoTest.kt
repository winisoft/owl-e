package com.wini.owle.repo.environs.version

import com.wini.owle.entity.environs.version.PythonVersionSpecifierEntity
import com.wini.owle.repo.CompositeIdRepoTest
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test


class PythonVersionSpecifierRepoTest(id: EntityID<CompositeID>) : CompositeIdRepoTest<PythonVersionSpecifierEntity>() {

    override fun mockEntity(): PythonVersionSpecifierEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<CompositeID> {
        val id = mockk<EntityID<CompositeID>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<CompositeID> {
        val id = mockk<EntityID<CompositeID>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): PythonVersionSpecifierEntity {
        return any<PythonVersionSpecifierEntity>()
    }

    

}

