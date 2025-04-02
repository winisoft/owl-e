package com.wini.owle.dll.workflows

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.PhaseDto
import com.wini.owle.services.workflows.PhaseService
import com.wini.owle.services.workflows.StepService
import kotlin.math.max

class PhaseDll(
    private val phaseService: PhaseService,
    private val stepService: StepService,
): DLL<Long, PhaseDto> {

    override fun getAll(): AsyncResult<List<PhaseDto>> = phaseService.getAll()
    override fun getById(id: Long): AsyncResult<PhaseDto?> = phaseService.getById(id)
    override fun create(dto: PhaseDto): AsyncResult<PhaseDto> {
        val maxOrder = calculateMaxDisplayOrder(dto.workflow.id, dto.parentPhase?.id)
        val phase = dto.copy(displayOrder = maxOrder + 1)
        return phaseService.create(phase)
    }
    override fun create(dtos: List<PhaseDto>): AsyncResult<List<PhaseDto>> {
        if (dtos.isEmpty()) return AsyncResult.Success(emptyList())
        // Group DTOs by (workflowId, parentPhaseId)
        val groupedDtos = dtos.groupBy { dto -> dto.workflow.id to dto.parentPhase?.id }
        // Collect results for all groups
        val allResults = mutableListOf<PhaseDto>()
        for ((scope, groupDtos) in groupedDtos) {
            val (workflowId, parentPhaseId) = scope
            // Fetch the max display order for the current group & assign display orders
            val maxOrder = calculateMaxDisplayOrder(workflowId, parentPhaseId)
            var currentOrder = maxOrder
            val phasesToCreate = groupDtos.map { dto ->
                dto.copy(displayOrder = ++currentOrder)
            }

            // Call the service layer to create steps
            when (val result = phaseService.create(phasesToCreate)) {
                is AsyncResult.Success -> allResults.addAll(result.data)
                is AsyncResult.Failure -> return result // Pass along the failure
            }
        }
        // Return all successfully created steps
        return AsyncResult.Success(allResults)
    }
    override fun update(dto: PhaseDto): AsyncResult<PhaseDto> = phaseService.update(dto)
    override fun update(dtos: List<PhaseDto>): AsyncResult<List<PhaseDto>> = phaseService.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = phaseService.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = phaseService.delete(ids)

    private fun calculateMaxDisplayOrder(workflowId: Long, parentPhaseId: Long?): Int {
        val maxStepOrder: Int = stepService.getMaxDisplayOrder(workflowId, parentPhaseId)
        val maxPhaseOrder: Int = phaseService.getMaxDisplayOrder(workflowId, parentPhaseId)
        return max(maxStepOrder, maxPhaseOrder)
    }
}