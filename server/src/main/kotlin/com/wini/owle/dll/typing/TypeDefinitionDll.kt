package com.wini.owle.dll.typing

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeDefinition
import com.wini.owle.services.typing.TypeDefinitionService

class TypeDefinitionDll(
    private val service: TypeDefinitionService
): DLL<Long, TypeDefinition> {

    override fun getAll(): AsyncResult<List<TypeDefinition>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TypeDefinition> = service.getById(id)
    override fun create(dto: TypeDefinition): AsyncResult<TypeDefinition> = service.create(dto)
    override fun createAll(dtos: List<TypeDefinition>): AsyncResult<List<TypeDefinition>> = service.create(dtos)
    override fun update(dto: TypeDefinition): AsyncResult<TypeDefinition> = service.update(dto)
    override fun updateAll(dtos: List<TypeDefinition>): AsyncResult<List<TypeDefinition>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}