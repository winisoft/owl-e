package com.wini.owle.entity.runs.test

import com.wini.owle.entity.runs.TestInputs
import com.wini.owle.entity.workflows.DataPointEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * Defines an input value for a test case, seeding a data point before the workflow or step
 * under test runs.
 */
class TestInputEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TestInputEntity>(TestInputs)

    /**
     * Relationship to the associated test case.
     */
    var testCase by TestCaseEntity referencedOn TestInputs.testCase

    /**
     * The data point this input seeds.
     */
    var dataPoint by DataPointEntity referencedOn TestInputs.dataPoint

    /**
     * Value assigned to the data point for the test case.
     */
    var value by TestInputs.value
}
