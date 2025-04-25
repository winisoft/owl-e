package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.step.StepRunLogLine
import com.wini.owle.services.runs.ExecutionLogService

class ExecutionLogDll(
    private val service: ExecutionLogService
): DLL<Long, StepRunLogLine> {

    override fun getAll(): AsyncResult<List<StepRunLogLine>> = service.getAll()
    override fun getById(id: Long): AsyncResult<StepRunLogLine> = service.getById(id)
    override fun create(dto: StepRunLogLine): AsyncResult<StepRunLogLine> = service.create(dto)
    override fun createAll(dtos: List<StepRunLogLine>): AsyncResult<List<StepRunLogLine>> = service.create(dtos)
    override fun update(dto: StepRunLogLine): AsyncResult<StepRunLogLine> = service.update(dto)
    override fun updateAll(dtos: List<StepRunLogLine>): AsyncResult<List<StepRunLogLine>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}