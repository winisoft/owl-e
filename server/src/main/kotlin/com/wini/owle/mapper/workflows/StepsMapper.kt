package com.wini.owle.mapper.workflows

import com.wini.owle.dto.StepDto
import com.wini.owle.entity.Steps
import com.wini.owle.entity.Tools
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class StepsMapper(
    private val daoFactory: DaoFactory<StepDao, Long>,
    private val workflowMapper: WorkflowsMapper,
    private val toolMapper: ToolsMapper,
    private val phasesMapper: PhasesMapper
) : DbMapper<StepDto, StepDao>() {

    override fun toEntity(dto: StepDto): StepDao {

        val applyProps: StepDao.() -> Unit = {
            workflow = workflowMapper.toEntity(dto.workflow)
            name = dto.name
            description = dto.description
            tool = toolMapper.toEntity(dto.tool)
            defaultNext = dto.nextStep?.let { toEntity(it) }
            isWorkflowHead = dto.isWorkflowHead
            parentPhase = dto.parentPhase?.let { phasesMapper.toEntity(it) }
            displayOrder = dto.displayOrder
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            StepDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Step with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: StepDao): StepDto {
        return StepDto(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            workflow = workflowMapper.toDto(entity.workflow),
            isWorkflowHead = entity.isWorkflowHead,
            tool = toolMapper.toDto(entity.tool),
            nextStep = entity.defaultNext?.let { toDto(it) },
            displayOrder = entity.displayOrder,
            parentPhase = entity.parentPhase?.let { phasesMapper.toDto(it) }
        )
    }
}