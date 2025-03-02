package com.wini.owle.entity.runs

import com.wini.owle.entity.StepBenchmarkingDatas
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Detailed benchmarking data for an individual step within a workflow,
 * capturing metrics like execution time and resource usage.
 */
class StepBenchmarkingDataDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<StepBenchmarkingDataDao>(StepBenchmarkingDatas)

    /**
     * Relationship to the associated step execution.
     */
    var stepExecution by StepExecutionDao referencedOn StepBenchmarkingDatas.stepExecution

    /**
     * Execution time for the step, measured in seconds.
     */
    var executionTime by StepBenchmarkingDatas.executionTime

    /**
     * JSON-encoded resource usage data (e.g., CPU and memory metrics).
     */
    var resourceUsage by StepBenchmarkingDatas.resourceUsage
}