package com.wini.owle.entity.runs.test

import com.wini.owle.entity.TestExpectedOutcomes
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Defines an expected outcome for a test case, specifying the expected value of
 * a data point after execution and the comparison operator to use.
 */
class TestExpectationEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TestExpectationEntity>(TestExpectedOutcomes)

    /**
     * Relationship to the associated test case.
     */
    var testCaseEntity by TestCaseEntity referencedOn TestExpectedOutcomes.testCase

    /**
     * Name of the data point this expected outcome corresponds to.
     */
    var dataPointName by TestExpectedOutcomes.dataPointName

    /**
     * Expected value for the data point after test execution.
     */
    var expectedValue by TestExpectedOutcomes.expectedValue

    /**
     * Operator for comparing the actual value to the expected value.
     */
    var comparisonOperator by TestExpectedOutcomes.comparisonOperator
}