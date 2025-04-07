package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ErrorWatchDto
import com.wini.owle.services.steps.ErrorWatchService

class ErrorWatchDll(
    private val service: ErrorWatchService
): DLL<Long, ErrorWatchDto> {

    override fun getAll(): AsyncResult<List<ErrorWatchDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<ErrorWatchDto> = service.getById(id)
    override fun create(dto: ErrorWatchDto): AsyncResult<ErrorWatchDto> = service.create(dto)
    override fun createAll(dtos: List<ErrorWatchDto>): AsyncResult<List<ErrorWatchDto>> = service.create(dtos)
    override fun update(dto: ErrorWatchDto): AsyncResult<ErrorWatchDto> = service.update(dto)
    override fun updateAll(dtos: List<ErrorWatchDto>): AsyncResult<List<ErrorWatchDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}