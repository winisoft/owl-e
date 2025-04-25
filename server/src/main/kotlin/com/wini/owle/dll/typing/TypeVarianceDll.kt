package com.wini.owle.dll.typing

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeVariance
import com.wini.owle.services.typing.TypeVarianceService

class TypeVarianceDll(
    private val service: TypeVarianceService
): DLL<Long, TypeVariance> {

    override fun getAll(): AsyncResult<List<TypeVariance>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TypeVariance> = service.getById(id)
    override fun create(dto: TypeVariance): AsyncResult<TypeVariance> = service.create(dto)
    override fun createAll(dtos: List<TypeVariance>): AsyncResult<List<TypeVariance>> = service.create(dtos)
    override fun update(dto: TypeVariance): AsyncResult<TypeVariance> = service.update(dto)
    override fun updateAll(dtos: List<TypeVariance>): AsyncResult<List<TypeVariance>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}