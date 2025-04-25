package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.services.steps.ErrorWatchFnService

class ErrorWatchFnDll(
    private val service: ErrorWatchFnService
): DLL<Long, ErrorWatchFn> {

    override fun getAll(): AsyncResult<List<ErrorWatchFn>> = service.getAll()
    override fun getById(id: Long): AsyncResult<ErrorWatchFn> = service.getById(id)
    override fun create(dto: ErrorWatchFn): AsyncResult<ErrorWatchFn> = service.create(dto)
    override fun createAll(dtos: List<ErrorWatchFn>): AsyncResult<List<ErrorWatchFn>> = service.create(dtos)
    override fun update(dto: ErrorWatchFn): AsyncResult<ErrorWatchFn> = service.update(dto)
    override fun updateAll(dtos: List<ErrorWatchFn>): AsyncResult<List<ErrorWatchFn>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}