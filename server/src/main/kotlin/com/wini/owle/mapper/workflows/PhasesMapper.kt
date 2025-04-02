package com.wini.owle.mapper.workflows

import com.wini.owle.dto.PhaseDto
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.workflows.PhaseDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class PhasesMapper(
    private val daoFactory: DaoFactory<PhaseDao, Long>,
    private val workflowMapper: WorkflowsMapper
) : DbMapper<PhaseDto, PhaseDao>() {

    override fun toEntity(dto: PhaseDto): PhaseDao {
        val applyProps: PhaseDao.() -> Unit = {
            workflow = workflowMapper.toEntity(dto.workflow)
            parentPhase = dto.parentPhase?.let { toEntity(it) }
            name = dto.name
            description = dto.description
            displayOrder = dto.displayOrder
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else
            PhaseDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Phase with id=${dto.id} not found")
    }

    override fun toDto(entity: PhaseDao): PhaseDto {
        return PhaseDto(
            id = entity.id.value,
            workflow = workflowMapper.toDto(entity.workflow),
            parentPhase = entity.parentPhase?.let { toDto(it) },
            name = entity.name,
            description = entity.description,
            displayOrder = entity.displayOrder
        )
    }
}