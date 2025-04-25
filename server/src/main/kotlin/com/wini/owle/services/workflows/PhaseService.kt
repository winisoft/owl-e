package com.wini.owle.services.workflows

import com.wini.owle.dto.workflows.Phase
import com.wini.owle.entity.workflows.PhaseEntity
import com.wini.owle.mapper.workflows.PhasesMapper
import com.wini.owle.repo.PhaseIdFactory
import com.wini.owle.repo.workflows.PhaseRepository
import com.wini.owle.services.BaseServiceImpl


class PhaseService(
    private val repository: PhaseRepository,
    private val mapper: PhasesMapper,
    entityIdFactory: PhaseIdFactory
): BaseServiceImpl<Long, Phase, PhaseEntity>(
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