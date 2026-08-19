package com.wini.owle.repo.workflows

import com.wini.owle.entity.steps.ToolEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk


/**
 * Represents a tool that can be utilized within a step.
 * Contains the tool's body, optional description, return type, and dependencies.
 */
abstract class ToolRepoTest : ULongIdGenericRepoTest<ToolEntity>() {

    override fun mockEntity(): ToolEntity = mockk(relaxed = true)

    override fun MockKMatcherScope.anyT(): ToolEntity {
        return any<ToolEntity>()
    }


}

