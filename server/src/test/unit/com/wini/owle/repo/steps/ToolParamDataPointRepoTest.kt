package com.wini.owle.repo.steps

import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.steps.ToolParamEntity
import com.wini.owle.repo.ULongIdGenericRepoTest
import io.mockk.MockKMatcherScope
import io.mockk.mockk
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.junit.jupiter.api.Test

/**
 * Represents the parameters and data provisions associated with a tool execution within a step.
 * Links the tool parameter to the step and holds any associated data points.
 */
class ToolParamDataPointRepoTest(id: EntityID<ULong>) : ULongIdGenericRepoTest<ToolParamEntity>() {

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

