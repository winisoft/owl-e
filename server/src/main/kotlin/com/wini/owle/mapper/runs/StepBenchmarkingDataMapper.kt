package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.ResourceUsageDto
import com.wini.owle.dto.runs.StepBenchmarkingDataDto
import com.wini.owle.entity.runs.StepBenchmarkingDataDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.toBigDecimal
import com.wini.owle.mapper.toDecimalValue


class StepBenchmarkingDataMapper(
    private val daoFactory: DaoFactory<StepBenchmarkingDataDao, Long>,
    private val stepExecutionsMapper: StepExecutionsMapper
) : DbMapper<StepBenchmarkingDataDto, StepBenchmarkingDataDao>() {

    override fun toEntity(dto: StepBenchmarkingDataDto): StepBenchmarkingDataDao {
        val applyProps: StepBenchmarkingDataDao.() -> Unit = {
            stepExecution = stepExecutionsMapper.toEntity(dto.stepExecution)
            executionTime = dto.executionTime.toBigDecimal()
            resourceUsage = null
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else
            StepBenchmarkingDataDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("StepBenchmarkingData with id=${dto.id} not found")
    }

    override fun toDto(entity: StepBenchmarkingDataDao): StepBenchmarkingDataDto {
        return StepBenchmarkingDataDto(
            id = entity.id.value,
            stepExecution = stepExecutionsMapper.toDto(entity.stepExecution), // Updated call
            executionTime = entity.executionTime.toDecimalValue(),
            resourceUsage = ResourceUsageDto() // TODO: Map from entity.resourceUsage if it exists
        )
    }
}