package com.wini.owle.entity.runs.test

import com.wini.owle.entity.runs.TestCases
import com.wini.owle.entity.runs.TestExpectedOutcomes
import com.wini.owle.entity.runs.TestInputs
import com.wini.owle.entity.runs.TestResults
import com.wini.owle.entity.runs.workflow.TestResultEntity
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * Defines a test case to validate workflows or steps.
 * Each test case may contain multiple inputs, expected outcomes, and historical results.
 */
class TestCaseEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TestCaseEntity>(TestCases)

    /**
     * The author of this test case.
     */
    var user by UserEntity referencedOn TestCases.user

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

    /**
     * Seed values bound to data points before the run.
     */
    val inputs: SizedIterable<TestInputEntity> by TestInputEntity referrersOn TestInputs.testCase

    /**
     * Assertions checked against data points after the run.
     */
    val expectedOutcomes: SizedIterable<TestExpectedOutcomeEntity>
            by TestExpectedOutcomeEntity referrersOn TestExpectedOutcomes.testCase

    /**
     * Historical pass/fail records.
     */
    val results: SizedIterable<TestResultEntity> by TestResultEntity referrersOn TestResults.testCase
}
