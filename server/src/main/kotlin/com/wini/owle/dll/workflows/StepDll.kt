package com.wini.owle.dll.workflows

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.Step
import com.wini.owle.services.workflows.PhaseService
import com.wini.owle.services.workflows.StepService
import kotlin.math.max


class StepDll(
    private val stepService: StepService,
    private val phaseService: PhaseService
): DLL<Long, Step> {

    override fun getAll(): AsyncResult<List<Step>> = stepService.getAll()
    override fun getById(id: Long): AsyncResult<Step> = stepService.getById(id)

    override fun create(dto: Step): AsyncResult<Step> {
        val maxOrder = calculateMaxDisplayOrder(dto.workflow.id, dto.parentPhase?.id)
        val stepEntity = dto.copy(displayOrder = maxOrder + 1)
        return stepService.create(stepEntity)
    }
    override fun createAll(dtos: List<Step>): AsyncResult<List<Step>> {
        if (dtos.isEmpty()) return AsyncResult.Success(emptyList())

        // Group DTOs by (workflowId, parentPhaseId)
        val groupedDtos = dtos.groupBy { dto ->
            dto.workflow.id to dto.parentPhase?.id
        }

        // Collect results for all groups
        val allResults = mutableListOf<Step>()
        for ((scope, groupDtos) in groupedDtos) {
            val (workflowId, parentPhaseId) = scope

            // Fetch the max display order for the current group
            val maxOrder = calculateMaxDisplayOrder(workflowId, parentPhaseId)

            // Assign display orders
            var currentOrder = maxOrder
            val stepsToCreate = groupDtos.map { dto ->
                dto.copy(displayOrder = ++currentOrder)
            }

            // Call the service layer to create steps
            when (val result = stepService.create(stepsToCreate)) {
                is AsyncResult.Success -> allResults.addAll(result.data)
                is AsyncResult.Failure -> return result // Pass along the failure
            }
        }

        // Return all successfully created steps
        return AsyncResult.Success(allResults)
    }


    override fun update(dto: Step): AsyncResult<Step> = stepService.update(dto)
    override fun updateAll(dtos: List<Step>): AsyncResult<List<Step>> = stepService.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = stepService.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = stepService.delete(ids)

    private fun calculateMaxDisplayOrder(workflowId: Long, parentPhaseId: Long?): Int {
        val maxStepOrder: Int = stepService.getMaxDisplayOrder(workflowId, parentPhaseId)
        val maxPhaseOrder: Int = phaseService.getMaxDisplayOrder(workflowId, parentPhaseId)
        return max(maxStepOrder, maxPhaseOrder)
    }
}