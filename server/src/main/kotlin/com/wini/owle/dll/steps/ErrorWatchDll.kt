package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.services.steps.ErrorWatchService

class ErrorWatchDll(
    private val service: ErrorWatchService
): DLL<Long, ErrorWatch> {

    override fun getAll(): AsyncResult<List<ErrorWatch>> = service.getAll()
    override fun getById(id: Long): AsyncResult<ErrorWatch> = service.getById(id)
    override fun create(dto: ErrorWatch): AsyncResult<ErrorWatch> = service.create(dto)
    override fun createAll(dtos: List<ErrorWatch>): AsyncResult<List<ErrorWatch>> = service.create(dtos)
    override fun update(dto: ErrorWatch): AsyncResult<ErrorWatch> = service.update(dto)
    override fun updateAll(dtos: List<ErrorWatch>): AsyncResult<List<ErrorWatch>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}