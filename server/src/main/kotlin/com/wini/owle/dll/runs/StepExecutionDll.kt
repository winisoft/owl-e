package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.StepExecutionDto
import com.wini.owle.services.runs.StepExecutionService

class StepExecutionDll(
    private val service: StepExecutionService
): DLL<Long, StepExecutionDto> {

    override fun getAll(): AsyncResult<List<StepExecutionDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<StepExecutionDto> = service.getById(id)
    override fun create(dto: StepExecutionDto): AsyncResult<StepExecutionDto> = service.create(dto)
    override fun createAll(dtos: List<StepExecutionDto>): AsyncResult<List<StepExecutionDto>> = service.create(dtos)
    override fun update(dto: StepExecutionDto): AsyncResult<StepExecutionDto> = service.update(dto)
    override fun updateAll(dtos: List<StepExecutionDto>): AsyncResult<List<StepExecutionDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}