package com.wini.owle.entity.runs.test

import com.wini.owle.entity.runs.TestExpectedOutcomes
import com.wini.owle.entity.workflows.DataPointEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * An assertion a test case makes about a data point once the run completes: compare its final
 * value to [expectedValue] using [operator].
 */
class TestExpectedOutcomeEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TestExpectedOutcomeEntity>(TestExpectedOutcomes)

    /**
     * The test case making this assertion.
     */
    var testCase by TestCaseEntity referencedOn TestExpectedOutcomes.testCase

    /**
     * The data point whose final value is checked.
     */
    var dataPoint by DataPointEntity referencedOn TestExpectedOutcomes.dataPoint

    /**
     * The value to compare against.
     */
    var expectedValue by TestExpectedOutcomes.expectedValue

    /**
     * Comparison to apply.
     */
    var operator by TestExpectedOutcomes.operator
}
