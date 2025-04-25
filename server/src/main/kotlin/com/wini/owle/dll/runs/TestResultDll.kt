package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.services.runs.TestResultService

class TestResultDll(
    private val service: TestResultService
): DLL<Long, WorkflowTestResult> {

    override fun getAll(): AsyncResult<List<WorkflowTestResult>> = service.getAll()
    override fun getById(id: Long): AsyncResult<WorkflowTestResult> = service.getById(id)
    override fun create(dto: WorkflowTestResult): AsyncResult<WorkflowTestResult> = service.create(dto)
    override fun createAll(dtos: List<WorkflowTestResult>): AsyncResult<List<WorkflowTestResult>> = service.create(dtos)
    override fun update(dto: WorkflowTestResult): AsyncResult<WorkflowTestResult> = service.update(dto)
    override fun updateAll(dtos: List<WorkflowTestResult>): AsyncResult<List<WorkflowTestResult>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}