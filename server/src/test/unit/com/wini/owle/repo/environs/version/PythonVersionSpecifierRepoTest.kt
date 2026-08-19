package com.wini.owle.repo.environs.version

import com.wini.owle.entity.environs.version.PythonVersionSpecifierEntity
import com.wini.owle.repo.CompositeIdRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


abstract class PythonVersionSpecifierRepoTest : CompositeIdRepoTest<PythonVersionSpecifierEntity>() {

    override fun mockEntity(): PythonVersionSpecifierEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): PythonVersionSpecifierEntity {
        return any<PythonVersionSpecifierEntity>()
    }


}

