package com.wini.owle.mapper.runs

import com.wini.owle.dto.ResourceUsageDto
import com.wini.owle.dto.StepBenchmarkingDataDto
import com.wini.owle.entity.StepBenchmarkingData
import com.wini.owle.entity.runs.StepBenchmarkingDataDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.toBigDecimal
import com.wini.owle.mapper.toDecimalValue
import org.jetbrains.exposed.dao.id.EntityID


class StepBenchmarkingDataMapper(
    private val stepExecutionsMapper: StepExecutionsMapper
) : DbMapper<StepBenchmarkingDataDto, StepBenchmarkingDataDao>() {

    override val applyProps: StepBenchmarkingDataDao.(StepBenchmarkingDataDto) -> Unit = { dto ->
        stepExecution = stepExecutionsMapper.run { dto.stepExecution.toEntity() }
        executionTime = dto.executionTime.toBigDecimal()
        resourceUsage = null
    }

    override fun StepBenchmarkingDataDto.toEntity(): StepBenchmarkingDataDao {
        return if (id == 0L) {
            StepBenchmarkingDataDao.new { applyProps(this@toEntity) }
        } else {
            StepBenchmarkingDataDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("StepBenchmarkingData with id=$id not found")
        }
    }

    override fun StepBenchmarkingDataDao.toDto(): StepBenchmarkingDataDto {
        return StepBenchmarkingDataDto(
            id = id.value,
            stepExecution = stepExecutionsMapper.run { stepExecution.toDto() },
            executionTime = executionTime.toDecimalValue(),
            resourceUsage = ResourceUsageDto()
        )
    }
}