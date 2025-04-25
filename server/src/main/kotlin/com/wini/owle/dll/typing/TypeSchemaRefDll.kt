package com.wini.owle.dll.typing

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.services.typing.TypeSchemaRefService

class TypeSchemaRefDll(
    private val service: TypeSchemaRefService
): DLL<Long, TypeSchemaRef> {

    override fun getAll(): AsyncResult<List<TypeSchemaRef>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TypeSchemaRef> = service.getById(id)
    override fun create(dto: TypeSchemaRef): AsyncResult<TypeSchemaRef> = service.create(dto)
    override fun createAll(dtos: List<TypeSchemaRef>): AsyncResult<List<TypeSchemaRef>> = service.create(dtos)
    override fun update(dto: TypeSchemaRef): AsyncResult<TypeSchemaRef> = service.update(dto)
    override fun updateAll(dtos: List<TypeSchemaRef>): AsyncResult<List<TypeSchemaRef>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}