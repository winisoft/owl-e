package com.wini.owle.dll.workflows

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.services.workflows.WorkflowService


class WorkflowDll(
    private val service: WorkflowService
): DLL<Long, Workflow> {

    override fun getAll(): AsyncResult<List<Workflow>> = service.getAll()
    override fun getById(id: Long): AsyncResult<Workflow> = service.getById(id)
    override fun create(dto: Workflow): AsyncResult<Workflow> = service.create(dto)
    override fun createAll(dtos: List<Workflow>): AsyncResult<List<Workflow>> = service.create(dtos)
    override fun update(dto: Workflow): AsyncResult<Workflow> = service.update(dto)
    override fun updateAll(dtos: List<Workflow>): AsyncResult<List<Workflow>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}