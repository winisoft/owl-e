package com.wini.owle.repo.environs.version

import com.wini.owle.entity.environs.version.PythonVersionEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class PythonVersionRepoTest(id: EntityID<ULong>) : ULongIdGenericRepoTest<PythonVersionEntity>() {

    override fun mockEntity(): PythonVersionEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): PythonVersionEntity {
        return any<PythonVersionEntity>()
    }

    

}

