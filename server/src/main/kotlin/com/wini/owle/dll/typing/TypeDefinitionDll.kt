package com.wini.owle.dll.typing

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeDefinitionDto
import com.wini.owle.services.typing.TypeDefinitionService

class TypeDefinitionDll(
    private val service: TypeDefinitionService
): DLL<Long, TypeDefinitionDto> {

    override fun getAll(): AsyncResult<List<TypeDefinitionDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TypeDefinitionDto> = service.getById(id)
    override fun create(dto: TypeDefinitionDto): AsyncResult<TypeDefinitionDto> = service.create(dto)
    override fun createAll(dtos: List<TypeDefinitionDto>): AsyncResult<List<TypeDefinitionDto>> = service.create(dtos)
    override fun update(dto: TypeDefinitionDto): AsyncResult<TypeDefinitionDto> = service.update(dto)
    override fun updateAll(dtos: List<TypeDefinitionDto>): AsyncResult<List<TypeDefinitionDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}