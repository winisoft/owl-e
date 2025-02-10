package com.wini.owle.mapper.steps

import com.wini.owle.dto.PreReqInjectionDto
import com.wini.owle.entity.DataPoints
import com.wini.owle.entity.PrereqInjections
import com.wini.owle.entity.WorkflowJunctions
import com.wini.owle.entity.steps.PreReqInjectionDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.DataPointsMapper
import org.jetbrains.exposed.dao.id.EntityID


class PreReqInjectionsMapper(
    private val workflowJunctionsMapper: WorkflowJunctionsMapper,
    private val dataPointsMapper: DataPointsMapper
) : DbMapper<PreReqInjectionDto, PreReqInjectionDao>() {

    override val applyProps: PreReqInjectionDao.(PreReqInjectionDto) -> Unit = { dto ->
        junction = workflowJunctionsMapper.run { dto.junction.toEntity() }
        sourceDataPoint = dataPointsMapper.run { dto.sourceDataPoint.toEntity() }
        injectedDataPoint = dataPointsMapper.run { dto.injectedDataPoint.toEntity() }
        name = dto.name
        description = dto.description
    }

    override fun PreReqInjectionDto.toEntity(): PreReqInjectionDao {
        return if (id == 0L) {
            PreReqInjectionDao.new { applyProps(this@toEntity) }
        } else {
            PreReqInjectionDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("PreReqInjection with id=$id not found")
        }
    }

    override fun PreReqInjectionDao.toDto(): PreReqInjectionDto {
        return PreReqInjectionDto(
            id = id.value,
            junction = workflowJunctionsMapper.run { junction.toDto() },
            sourceDataPoint = dataPointsMapper.run { sourceDataPoint.toDto() },
            injectedDataPoint = dataPointsMapper.run { injectedDataPoint.toDto() },
            name = name,
            description = description
        )
    }
}