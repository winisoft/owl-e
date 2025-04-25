package com.wini.owle.dll.workflows

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.services.workflows.WorkflowFolderService

class WorkflowFolderDll(
    private val service: WorkflowFolderService
): DLL<Long, WorkflowFolder> {

    override fun getAll(): AsyncResult<List<WorkflowFolder>> = service.getAll()
    override fun getById(id: Long): AsyncResult<WorkflowFolder> = service.getById(id)
    override fun create(dto: WorkflowFolder): AsyncResult<WorkflowFolder> = service.create(dto)
    override fun createAll(dtos: List<WorkflowFolder>): AsyncResult<List<WorkflowFolder>> = service.create(dtos)
    override fun update(dto: WorkflowFolder): AsyncResult<WorkflowFolder> = service.update(dto)
    override fun updateAll(dtos: List<WorkflowFolder>): AsyncResult<List<WorkflowFolder>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}