package com.wini.owle.entity.runs.test

import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.TestCases
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Defines a test case to validate workflows or steps.
 * Each test case may contain multiple inputs, expected outcomes, and historical results.
 */
class TestCaseEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TestCaseEntity>(TestCases)

    /**
     * Relationship to the associated user.
     */
    var userEntity by UserEntity referencedOn TestCases.user

    /**
     * Relationship to the workflow associated with this test case, if applicable.
     */
    var workflow by WorkflowEntity optionalReferencedOn TestCases.workflow

    /**
     * Relationship to the step associated with this test case, if applicable.
     */
    var step by StepEntity optionalReferencedOn TestCases.step

    /**
     * Name of the test case, for easy identification.
     */
    var name by TestCases.name

    /**
     * Optional description providing additional context or purpose of the test.
     */
    var description by TestCases.description
}