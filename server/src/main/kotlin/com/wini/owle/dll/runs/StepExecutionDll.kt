package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.step.StepRun
import com.wini.owle.services.runs.StepExecutionService

class StepExecutionDll(
    private val service: StepExecutionService
): DLL<Long, StepRun> {

    override fun getAll(): AsyncResult<List<StepRun>> = service.getAll()
    override fun getById(id: Long): AsyncResult<StepRun> = service.getById(id)
    override fun create(dto: StepRun): AsyncResult<StepRun> = service.create(dto)
    override fun createAll(dtos: List<StepRun>): AsyncResult<List<StepRun>> = service.create(dtos)
    override fun update(dto: StepRun): AsyncResult<StepRun> = service.update(dto)
    override fun updateAll(dtos: List<StepRun>): AsyncResult<List<StepRun>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}