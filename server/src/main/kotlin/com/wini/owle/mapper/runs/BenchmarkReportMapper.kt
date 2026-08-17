package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.test.BenchmarkReport
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.runs.test.BenchmarkReportEntity
import com.wini.owle.mapper.DbMapper


class BenchmarkReportMapper(
    private val daoDelegate: DaoDelegate<ULong, BenchmarkReport, BenchmarkReportEntity>,
    private val workflowExecutionsMapper: WorkflowRunMapper
): DbMapper<ULong, BenchmarkReport, BenchmarkReportEntity> {

    override fun toEntity(dto: BenchmarkReport): BenchmarkReportEntity {
        return daoDelegate.findOrCreate(dto) {
            workflowExecution = workflowExecutionsMapper.toEntity(dto.workflowExecution)
        }
    }

    override fun toDto(entity: BenchmarkReportEntity): BenchmarkReport {
        return BenchmarkReport(
            id = entity.id.value,
            workflowRun = workflowExecutionsMapper.toDto(entity.workflowExecution)
        )
    }
}