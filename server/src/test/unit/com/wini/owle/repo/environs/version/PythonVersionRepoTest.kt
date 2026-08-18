package com.wini.owle.repo.environs.version

import com.wini.owle.entity.environs.version.PythonVersionEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.core.dao.id.EntityID


abstract class PythonVersionRepoTest : ULongIdGenericRepoTest<PythonVersionEntity>() {

    override fun mockEntity(): PythonVersionEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): PythonVersionEntity {
        return any<PythonVersionEntity>()
    }

    

}

