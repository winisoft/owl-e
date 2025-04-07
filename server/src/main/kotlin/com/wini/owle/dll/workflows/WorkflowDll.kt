package com.wini.owle.dll.workflows

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.services.workflows.WorkflowService


class WorkflowDll(
    private val service: WorkflowService
): DLL<Long, WorkflowDto> {

    override fun getAll(): AsyncResult<List<WorkflowDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<WorkflowDto> = service.getById(id)
    override fun create(dto: WorkflowDto): AsyncResult<WorkflowDto> = service.create(dto)
    override fun createAll(dtos: List<WorkflowDto>): AsyncResult<List<WorkflowDto>> = service.create(dtos)
    override fun update(dto: WorkflowDto): AsyncResult<WorkflowDto> = service.update(dto)
    override fun updateAll(dtos: List<WorkflowDto>): AsyncResult<List<WorkflowDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}