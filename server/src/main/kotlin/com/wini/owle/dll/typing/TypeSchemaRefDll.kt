package com.wini.owle.dll.typing

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeSchemaRefDto
import com.wini.owle.services.typing.TypeSchemaRefService

class TypeSchemaRefDll(
    private val service: TypeSchemaRefService
): DLL<Long, TypeSchemaRefDto> {

    override fun getAll(): AsyncResult<List<TypeSchemaRefDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TypeSchemaRefDto?> = service.getById(id)
    override fun create(dto: TypeSchemaRefDto): AsyncResult<TypeSchemaRefDto> = service.create(dto)
    override fun create(dtos: List<TypeSchemaRefDto>): AsyncResult<List<TypeSchemaRefDto>> = service.create(dtos)
    override fun update(dto: TypeSchemaRefDto): AsyncResult<TypeSchemaRefDto> = service.update(dto)
    override fun update(dtos: List<TypeSchemaRefDto>): AsyncResult<List<TypeSchemaRefDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}