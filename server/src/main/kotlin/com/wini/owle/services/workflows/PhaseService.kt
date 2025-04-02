package com.wini.owle.services.workflows

import com.wini.owle.dto.workflows.PhaseDto
import com.wini.owle.entity.workflows.PhaseDao
import com.wini.owle.mapper.workflows.PhasesMapper
import com.wini.owle.repo.workflows.PhaseRepository
import com.wini.owle.services.BaseServiceImpl


class PhaseService(
    private val repository: PhaseRepository,
    private val mapper: PhasesMapper
): BaseServiceImpl<Long, PhaseDto, PhaseDao>(repository, mapper) {

    fun getMaxDisplayOrder(workflowId: Long, parentPhaseId: Long?): Int {
        return repository.getWhere {
            it.workflow.id.value == workflowId && it.parentPhase?.id?.value == parentPhaseId
        }.maxOfOrNull {
            it.displayOrder
        } ?: 0
    }
}