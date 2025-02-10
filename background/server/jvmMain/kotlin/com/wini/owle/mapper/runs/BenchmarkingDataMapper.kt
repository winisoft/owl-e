package com.wini.owle.mapper.runs

import com.wini.owle.dto.BenchmarkingDataDto
import com.wini.owle.dto.PackageRepoDto
import com.wini.owle.entity.BenchmarkingData
import com.wini.owle.entity.WorkflowExecutions
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.runs.BenchmarkingDataDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.toBigDecimal
import com.wini.owle.mapper.toDecimalValue
import org.jetbrains.exposed.dao.id.EntityID


class BenchmarkingDataMapper(
    private val workflowExecutionsMapper: WorkflowExecutionsMapper
) : DbMapper<BenchmarkingDataDto, BenchmarkingDataDao>() {

    override val applyProps: BenchmarkingDataDao.(BenchmarkingDataDto) -> Unit = { dto ->
        this.workflowExecution = workflowExecutionsMapper.run { dto.workflowExecution.toEntity() }
        this.totalExecutionTime = dto.totalExecutionTime.toBigDecimal()
        this.totalSteps = dto.totalSteps
        this.aiDeterminedSteps = dto.aiDeterminedSteps
        this.predeterminedSteps = dto.predeterminedSteps
    }

    override fun BenchmarkingDataDto.toEntity(): BenchmarkingDataDao {
        return if (id == 0L) BenchmarkingDataDao.new {
            applyProps(this@toEntity)
        } else
            BenchmarkingDataDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("VersionSpec with id=$id not found")
    }

    override fun BenchmarkingDataDao.toDto(): BenchmarkingDataDto {
        return BenchmarkingDataDto(
            id = id.value,
            workflowExecution = workflowExecutionsMapper.run { workflowExecution.toDto() },
            totalExecutionTime = totalExecutionTime.toDecimalValue(),
            totalSteps = totalSteps,
            aiDeterminedSteps = aiDeterminedSteps,
            predeterminedSteps = predeterminedSteps
        )
    }
}