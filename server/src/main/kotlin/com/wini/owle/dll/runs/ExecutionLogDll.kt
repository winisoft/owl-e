package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.ExecutionLogDto
import com.wini.owle.services.runs.ExecutionLogService

class ExecutionLogDll(
    private val service: ExecutionLogService
): DLL<Long, ExecutionLogDto> {

    override fun getAll(): AsyncResult<List<ExecutionLogDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<ExecutionLogDto> = service.getById(id)
    override fun create(dto: ExecutionLogDto): AsyncResult<ExecutionLogDto> = service.create(dto)
    override fun createAll(dtos: List<ExecutionLogDto>): AsyncResult<List<ExecutionLogDto>> = service.create(dtos)
    override fun update(dto: ExecutionLogDto): AsyncResult<ExecutionLogDto> = service.update(dto)
    override fun updateAll(dtos: List<ExecutionLogDto>): AsyncResult<List<ExecutionLogDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}