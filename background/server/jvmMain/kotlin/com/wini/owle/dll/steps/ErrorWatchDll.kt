package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.ErrorWatchDto
import com.wini.owle.services.steps.ErrorWatchService

class ErrorWatchDll(
    private val service: ErrorWatchService
): DLL<Long, ErrorWatchDto> {

    override fun getAll(): AsyncResult<List<ErrorWatchDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<ErrorWatchDto?> = service.getById(id)
    override fun create(dto: ErrorWatchDto): AsyncResult<ErrorWatchDto> = service.create(dto)
    override fun create(dtos: List<ErrorWatchDto>): AsyncResult<List<ErrorWatchDto>> = service.create(dtos)
    override fun update(dto: ErrorWatchDto): AsyncResult<ErrorWatchDto> = service.update(dto)
    override fun update(dtos: List<ErrorWatchDto>): AsyncResult<List<ErrorWatchDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}