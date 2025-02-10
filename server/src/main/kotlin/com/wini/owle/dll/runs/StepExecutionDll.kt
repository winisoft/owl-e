package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.StepExecutionDto
import com.wini.owle.services.runs.StepExecutionService

class StepExecutionDll(
    private val service: StepExecutionService
): DLL<Long, StepExecutionDto> {

    override fun getAll(): AsyncResult<List<StepExecutionDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<StepExecutionDto?> = service.getById(id)
    override fun create(dto: StepExecutionDto): AsyncResult<StepExecutionDto> = service.create(dto)
    override fun create(dtos: List<StepExecutionDto>): AsyncResult<List<StepExecutionDto>> = service.create(dtos)
    override fun update(dto: StepExecutionDto): AsyncResult<StepExecutionDto> = service.update(dto)
    override fun update(dtos: List<StepExecutionDto>): AsyncResult<List<StepExecutionDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}