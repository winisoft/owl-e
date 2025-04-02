package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.WorkflowJunctionDto
import com.wini.owle.services.steps.WorkflowJunctionService

class WorkflowJunctionDll(
    private val service: WorkflowJunctionService
): DLL<Long, WorkflowJunctionDto> {

    override fun getAll(): AsyncResult<List<WorkflowJunctionDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<WorkflowJunctionDto?> = service.getById(id)
    override fun create(dto: WorkflowJunctionDto): AsyncResult<WorkflowJunctionDto> = service.create(dto)
    override fun create(dtos: List<WorkflowJunctionDto>): AsyncResult<List<WorkflowJunctionDto>> = service.create(dtos)
    override fun update(dto: WorkflowJunctionDto): AsyncResult<WorkflowJunctionDto> = service.update(dto)
    override fun update(dtos: List<WorkflowJunctionDto>): AsyncResult<List<WorkflowJunctionDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}