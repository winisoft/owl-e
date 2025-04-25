package com.wini.owle.services.workflows

import com.wini.owle.dto.workflows.Step
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.repo.StepIdFactory
import com.wini.owle.repo.workflows.StepRepository
import com.wini.owle.services.BaseServiceImpl


class StepService(
    private val repository: StepRepository,
    private val mapper: StepsMapper,
    private val entityIdFactory: StepIdFactory
): BaseServiceImpl<Long, Step, StepEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
) {

    fun getMaxDisplayOrder(workflowId: Long, parentPhaseId: Long?): Int {
        return repository.getWhere {
            it.workflow.id.value == workflowId && it.parentPhase?.id?.value == parentPhaseId
        }.maxOfOrNull {
            it.displayOrder
        } ?: 0
    }

}