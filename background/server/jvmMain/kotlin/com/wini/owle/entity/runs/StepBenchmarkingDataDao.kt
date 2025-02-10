package com.wini.owle.entity.runs

import com.wini.owle.entity.StepBenchmarkingData
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Detailed benchmarking data for an individual step within a workflow,
 * capturing metrics like execution time and resource usage.
 */
class StepBenchmarkingDataDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<StepBenchmarkingDataDao>(StepBenchmarkingData)

    /**
     * Relationship to the associated step execution.
     */
    var stepExecution by StepExecutionDao referencedOn StepBenchmarkingData.stepExecution

    /**
     * Execution time for the step, measured in seconds.
     */
    var executionTime by StepBenchmarkingData.executionTime

    /**
     * JSON-encoded resource usage data (e.g., CPU and memory metrics).
     */
    var resourceUsage by StepBenchmarkingData.resourceUsage
}