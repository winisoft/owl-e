package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.ErrorWatchFnDto
import com.wini.owle.services.steps.ErrorWatchFnService

class ErrorWatchFnDll(
    private val service: ErrorWatchFnService
): DLL<Long, ErrorWatchFnDto> {

    override fun getAll(): AsyncResult<List<ErrorWatchFnDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<ErrorWatchFnDto?> = service.getById(id)
    override fun create(dto: ErrorWatchFnDto): AsyncResult<ErrorWatchFnDto> = service.create(dto)
    override fun create(dtos: List<ErrorWatchFnDto>): AsyncResult<List<ErrorWatchFnDto>> = service.create(dtos)
    override fun update(dto: ErrorWatchFnDto): AsyncResult<ErrorWatchFnDto> = service.update(dto)
    override fun update(dtos: List<ErrorWatchFnDto>): AsyncResult<List<ErrorWatchFnDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}