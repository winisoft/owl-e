package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.WorkflowExecutionDto
import com.wini.owle.services.runs.WorkflowExecutionService

class WorkflowExecutionDll(
    private val service: WorkflowExecutionService
): DLL<Long, WorkflowExecutionDto> {

    override fun getAll(): AsyncResult<List<WorkflowExecutionDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<WorkflowExecutionDto> = service.getById(id)
    override fun create(dto: WorkflowExecutionDto): AsyncResult<WorkflowExecutionDto> = service.create(dto)
    override fun createAll(dtos: List<WorkflowExecutionDto>): AsyncResult<List<WorkflowExecutionDto>> = service.create(dtos)
    override fun update(dto: WorkflowExecutionDto): AsyncResult<WorkflowExecutionDto> = service.update(dto)
    override fun updateAll(dtos: List<WorkflowExecutionDto>): AsyncResult<List<WorkflowExecutionDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}