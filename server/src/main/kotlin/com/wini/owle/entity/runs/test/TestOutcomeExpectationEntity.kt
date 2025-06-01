package com.wini.owle.entity.runs.test

import com.wini.owle.entity.runs.TestOutcomeExpectations
import com.wini.owle.entity.workflows.DataPointEntity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Defines an expected outcome for a test case, specifying the expected value of
 * a data point after execution and the comparison operator to use.
 */
class TestOutcomeExpectationEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TestOutcomeExpectationEntity>(TestOutcomeExpectations)

    /**
     * Name of the data point this expected outcome corresponds to.
     */
    var dataPoint by DataPointEntity referencedOn TestOutcomeExpectations.dataPoint

    /**
     * Operator for comparing the actual value to the expected value.
     */
    var assertion by TestOutcomeExpectations.assertion
}