package com.wini.owle.mapper.steps

import com.wini.owle.dto.ProvisionDto
import com.wini.owle.entity.ProvisionDataPoints
import com.wini.owle.entity.Provisions
import com.wini.owle.entity.Steps
import com.wini.owle.entity.ToolParams
import com.wini.owle.entity.steps.ProvisionDao
import com.wini.owle.entity.steps.ProvisionDataPointDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.mapper.workflows.ToolParamsMapper
import org.jetbrains.exposed.dao.id.EntityID


class ProvisionsMapper(
    private val stepsMapper: StepsMapper,
    private val toolParamsMapper: ToolParamsMapper,
    private val provisionDataPointMapper: ProvisionDataPointMapper
) : DbMapper<ProvisionDto, ProvisionDao>() {

    override val applyProps: ProvisionDao.(ProvisionDto) -> Unit = { dto ->
        step = stepsMapper.run { dto.step.toEntity() }
        toolParam = toolParamsMapper.run { dto.toolParam.toEntity() }
    }

    override fun ProvisionDto.toEntity(): ProvisionDao {
        return if (id == 0L) {
            ProvisionDao.new { applyProps(this@toEntity) }
        } else {
            ProvisionDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("Provision with id=$id not found")
        }
    }

    override fun ProvisionDao.toDto(): ProvisionDto {
        return ProvisionDto(
            id = id.value,
            step = stepsMapper.run { step.toDto() },
            toolParam = toolParamsMapper.run { toolParam.toDto() },
            dataPoints = ProvisionDataPointDao
                .find { ProvisionDataPoints.provision eq id }
                .map { provisionDataPointMapper.run { it.toDto() } }
        )
    }
}