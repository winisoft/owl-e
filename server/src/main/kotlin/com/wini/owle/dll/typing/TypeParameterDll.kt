package com.wini.owle.dll.typing

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.TypeParameterDto
import com.wini.owle.services.typing.TypeParameterService

class TypeParameterDll(
    private val service: TypeParameterService
): DLL<Long, TypeParameterDto> {

    override fun getAll(): AsyncResult<List<TypeParameterDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TypeParameterDto?> = service.getById(id)
    override fun create(dto: TypeParameterDto): AsyncResult<TypeParameterDto> = service.create(dto)
    override fun create(dtos: List<TypeParameterDto>): AsyncResult<List<TypeParameterDto>> = service.create(dtos)
    override fun update(dto: TypeParameterDto): AsyncResult<TypeParameterDto> = service.update(dto)
    override fun update(dtos: List<TypeParameterDto>): AsyncResult<List<TypeParameterDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}