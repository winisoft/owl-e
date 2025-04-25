package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.Inquisition
import com.wini.owle.services.steps.InquisitionService

class InquisitionDll(
    private val service: InquisitionService
): DLL<Long, Inquisition> {

    override fun getAll(): AsyncResult<List<Inquisition>> = service.getAll()
    override fun getById(id: Long): AsyncResult<Inquisition> = service.getById(id)
    override fun create(dto: Inquisition): AsyncResult<Inquisition> = service.create(dto)
    override fun createAll(dtos: List<Inquisition>): AsyncResult<List<Inquisition>> = service.create(dtos)
    override fun update(dto: Inquisition): AsyncResult<Inquisition> = service.update(dto)
    override fun updateAll(dtos: List<Inquisition>): AsyncResult<List<Inquisition>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}