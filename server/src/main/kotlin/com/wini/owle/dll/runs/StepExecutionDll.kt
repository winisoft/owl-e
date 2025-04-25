package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.services.runs.StepExecutionService

class StepExecutionDll(
    private val service: StepExecutionService
): DLL<Long, StepExecution> {

    override fun getAll(): AsyncResult<List<StepExecution>> = service.getAll()
    override fun getById(id: Long): AsyncResult<StepExecution> = service.getById(id)
    override fun create(dto: StepExecution): AsyncResult<StepExecution> = service.create(dto)
    override fun createAll(dtos: List<StepExecution>): AsyncResult<List<StepExecution>> = service.create(dtos)
    override fun update(dto: StepExecution): AsyncResult<StepExecution> = service.update(dto)
    override fun updateAll(dtos: List<StepExecution>): AsyncResult<List<StepExecution>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}