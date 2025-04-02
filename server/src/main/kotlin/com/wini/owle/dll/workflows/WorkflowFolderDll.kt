package com.wini.owle.dll.workflows

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.WorkflowFolderDto
import com.wini.owle.services.workflows.WorkflowFolderService

class WorkflowFolderDll(
    private val service: WorkflowFolderService
): DLL<Long, WorkflowFolderDto> {

    override fun getAll(): AsyncResult<List<WorkflowFolderDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<WorkflowFolderDto?> = service.getById(id)
    override fun create(dto: WorkflowFolderDto): AsyncResult<WorkflowFolderDto> = service.create(dto)
    override fun create(dtos: List<WorkflowFolderDto>): AsyncResult<List<WorkflowFolderDto>> = service.create(dtos)
    override fun update(dto: WorkflowFolderDto): AsyncResult<WorkflowFolderDto> = service.update(dto)
    override fun update(dtos: List<WorkflowFolderDto>): AsyncResult<List<WorkflowFolderDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}