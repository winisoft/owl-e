package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.ToolParamEntity
import com.wini.owle.entity.typing.TypeNodeEntity
import com.wini.owle.entity.workflows.ToolEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test

/**
 * Represents a parameter for a tool within a workflow.
 * Each parameter has a name, type, and can optionally have a default value.
 */
class ToolParamRepoTest(id: EntityID<ULong>) : ULongIdGenericRepoTest<ToolParamEntity>() {

    override fun mockEntity(): ToolParamEntity = mockk(relaxed = true)

    override fun mockEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun mockNonExistentEntityId(): EntityID<ULong> {
        val id = mockk<EntityID<ULong>>()
        return id
    }

    override fun MockKMatcherScope.anyT(): ToolParamEntity {
        return any<ToolParamEntity>()
    }

    

}

