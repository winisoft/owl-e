package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ErrorWatchFnDto
import com.wini.owle.services.steps.ErrorWatchFnService

class ErrorWatchFnDll(
    private val service: ErrorWatchFnService
): DLL<Long, ErrorWatchFnDto> {

    override fun getAll(): AsyncResult<List<ErrorWatchFnDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<ErrorWatchFnDto> = service.getById(id)
    override fun create(dto: ErrorWatchFnDto): AsyncResult<ErrorWatchFnDto> = service.create(dto)
    override fun createAll(dtos: List<ErrorWatchFnDto>): AsyncResult<List<ErrorWatchFnDto>> = service.create(dtos)
    override fun update(dto: ErrorWatchFnDto): AsyncResult<ErrorWatchFnDto> = service.update(dto)
    override fun updateAll(dtos: List<ErrorWatchFnDto>): AsyncResult<List<ErrorWatchFnDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}