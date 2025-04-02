package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.BenchmarkingDataDto
import com.wini.owle.entity.runs.BenchmarkingDataDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.toBigDecimal
import com.wini.owle.mapper.toDecimalValue


class BenchmarkingDataMapper(
    private val daoFactory: DaoFactory<BenchmarkingDataDao, Long>,
    private val workflowExecutionsMapper: WorkflowExecutionsMapper
) : DbMapper<BenchmarkingDataDto, BenchmarkingDataDao>() {

    override fun toEntity(dto: BenchmarkingDataDto): BenchmarkingDataDao {
        val applyProps: BenchmarkingDataDao.() -> Unit = {
            workflowExecution = workflowExecutionsMapper.toEntity(dto.workflowExecution)
            totalExecutionTime = dto.totalExecutionTime.toBigDecimal()
            totalSteps = dto.totalSteps
            aiDeterminedSteps = dto.aiDeterminedSteps
            predeterminedSteps = dto.predeterminedSteps
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else
            BenchmarkingDataDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("BenchmarkingData with id=${dto.id} not found")
    }

    override fun toDto(entity: BenchmarkingDataDao): BenchmarkingDataDto {
        return BenchmarkingDataDto(
            id = entity.id.value,
            workflowExecution = workflowExecutionsMapper.toDto(entity.workflowExecution), // Updated call
            totalExecutionTime = entity.totalExecutionTime.toDecimalValue(),
            totalSteps = entity.totalSteps,
            aiDeterminedSteps = entity.aiDeterminedSteps,
            predeterminedSteps = entity.predeterminedSteps
        )
    }
}