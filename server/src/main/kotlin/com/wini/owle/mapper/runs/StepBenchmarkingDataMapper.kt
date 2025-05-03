package com.wini.owle.mapper.runs

import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


//open class StepBenchmarkingDataMapper(
//    private val daoFactory: DaoFactory<Ste, Long>,
//    private val stepExecutionsMapper: StepExecutionsMapper
//) : DbMapper<StepBenchmarkingDataDto, StepBenchmarkingDataDao>() {
//
//    override fun toEntity(dto: StepBenchmarkingDataDto): StepBenchmarkingDataDao {
//        val applyProps: StepBenchmarkingDataDao.() -> Unit = {
//            stepExecution = stepExecutionsMapper.toEntity(dto.stepExecution)
//            executionTime = dto.executionTime.toBigDecimal()
//        }
//        return if (dto.id == 0L)
//            daoFactory.create().apply(applyProps)
//        else
//            StepBenchmarkingDataDao.findById(dto.id)
//                ?.apply(applyProps)
//                ?: throw IllegalStateException("StepBenchmarkingData with id=${dto.id} not found")
//    }
//
//    override fun toDto(entity: StepBenchmarkingDataDao): StepBenchmarkingDataDto {
//        return StepBenchmarkingDataDto(
//            id = entity.id.value,
//            stepExecution = stepExecutionsMapper.toDto(entity.stepExecution),
//            executionTime = entity.executionTime.toDouble()
//        )
//    }
//}