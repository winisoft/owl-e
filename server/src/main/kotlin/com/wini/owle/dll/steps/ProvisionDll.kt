package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.Provision
import com.wini.owle.services.steps.ProvisionService


class ProvisionDll(
    private val service: ProvisionService
): DLL<Long, Provision> {

    override fun getAll(): AsyncResult<List<Provision>> = service.getAll()
    override fun getById(id: Long): AsyncResult<Provision> = service.getById(id)
    override fun create(dto: Provision): AsyncResult<Provision> = service.create(dto)
    override fun createAll(dtos: List<Provision>): AsyncResult<List<Provision>> = service.create(dtos)
    override fun update(dto: Provision): AsyncResult<Provision> = service.update(dto)
    override fun updateAll(dtos: List<Provision>): AsyncResult<List<Provision>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}