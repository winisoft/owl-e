package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.InitialDataPointInjector
import com.wini.owle.services.steps.PreReqInjectionService

class PreReqInjectionDll(
    private val service: PreReqInjectionService
): DLL<Long, InitialDataPointInjector> {

    override fun getAll(): AsyncResult<List<InitialDataPointInjector>> = service.getAll()
    override fun getById(id: Long): AsyncResult<InitialDataPointInjector> = service.getById(id)
    override fun create(dto: InitialDataPointInjector): AsyncResult<InitialDataPointInjector> = service.create(dto)
    override fun createAll(dtos: List<InitialDataPointInjector>): AsyncResult<List<InitialDataPointInjector>> = service.create(dtos)
    override fun update(dto: InitialDataPointInjector): AsyncResult<InitialDataPointInjector> = service.update(dto)
    override fun updateAll(dtos: List<InitialDataPointInjector>): AsyncResult<List<InitialDataPointInjector>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}