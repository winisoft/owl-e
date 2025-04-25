package com.wini.owle.dll.typing

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeParameter
import com.wini.owle.services.typing.TypeParameterService

class TypeParameterDll(
    private val service: TypeParameterService
): DLL<Long, TypeParameter> {

    override fun getAll(): AsyncResult<List<TypeParameter>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TypeParameter> = service.getById(id)
    override fun create(dto: TypeParameter): AsyncResult<TypeParameter> = service.create(dto)
    override fun createAll(dtos: List<TypeParameter>): AsyncResult<List<TypeParameter>> = service.create(dtos)
    override fun update(dto: TypeParameter): AsyncResult<TypeParameter> = service.update(dto)
    override fun updateAll(dtos: List<TypeParameter>): AsyncResult<List<TypeParameter>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}