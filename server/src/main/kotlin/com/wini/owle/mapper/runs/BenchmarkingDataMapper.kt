package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.test.BenchmarkReport
import com.wini.owle.entity.runs.test.BenchmarkReportEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class BenchmarkingDataMapper(
    private val daoFactory: DaoFactory<BenchmarkReportEntity, Long>,
    private val workflowExecutionsMapper: WorkflowExecutionsMapper
) : DbMapper<BenchmarkReport, BenchmarkReportEntity>() {

    override fun toEntity(dto: BenchmarkReport): BenchmarkReportEntity {
        val applyProps: BenchmarkReportEntity.() -> Unit = {
            workflowExecution = workflowExecutionsMapper.toEntity(dto.workflowExecution)
            totalExecutionTime = dto.totalExecutionTime.toBigDecimal()
            totalSteps = dto.totalSteps
            aiDeterminedSteps = dto.aiDeterminedSteps
            predeterminedSteps = dto.predeterminedSteps
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else
            BenchmarkReportEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("BenchmarkingData with id=${dto.id} not found")
    }

    override fun toDto(entity: BenchmarkReportEntity): BenchmarkReport {
        return BenchmarkReport(
            id = entity.id.value,
            workflowExecution = workflowExecutionsMapper.toDto(entity.workflowExecution),
            totalExecutionTime = entity.totalExecutionTime.toDouble(),
            totalSteps = entity.totalSteps,
            aiDeterminedSteps = entity.aiDeterminedSteps,
            predeterminedSteps = entity.predeterminedSteps
        )
    }
}