package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.InquisitionEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * Represents a conditional check or "inquisition" within a workflow,
 * analyzing a specific data point value and determining if workflow redirection is required.
 */
abstract class InquisitionRepoTest : ULongIdGenericRepoTest<InquisitionEntity>() {

    override fun mockEntity(): InquisitionEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): InquisitionEntity {
        return any<InquisitionEntity>()
    }

}

