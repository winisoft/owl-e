package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.services.runs.WorkflowExecutionService

class WorkflowExecutionDll(
    private val service: WorkflowExecutionService
): DLL<Long, WorkflowExecution> {

    override fun getAll(): AsyncResult<List<WorkflowExecution>> = service.getAll()
    override fun getById(id: Long): AsyncResult<WorkflowExecution> = service.getById(id)
    override fun create(dto: WorkflowExecution): AsyncResult<WorkflowExecution> = service.create(dto)
    override fun createAll(dtos: List<WorkflowExecution>): AsyncResult<List<WorkflowExecution>> = service.create(dtos)
    override fun update(dto: WorkflowExecution): AsyncResult<WorkflowExecution> = service.update(dto)
    override fun updateAll(dtos: List<WorkflowExecution>): AsyncResult<List<WorkflowExecution>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}