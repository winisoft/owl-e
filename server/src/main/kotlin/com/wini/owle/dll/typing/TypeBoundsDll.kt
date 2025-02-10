package com.wini.owle.dll.typing

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.TypeBoundsDto
import com.wini.owle.services.typing.TypeBoundsService

class TypeBoundsDll(
    private val service: TypeBoundsService
): DLL<Long, TypeBoundsDto> {
    override fun getAll(): AsyncResult<List<TypeBoundsDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TypeBoundsDto?> = service.getById(id)
    override fun create(dto: TypeBoundsDto): AsyncResult<TypeBoundsDto> = service.create(dto)
    override fun create(dtos: List<TypeBoundsDto>): AsyncResult<List<TypeBoundsDto>> = service.create(dtos)
    override fun update(dto: TypeBoundsDto): AsyncResult<TypeBoundsDto> = service.update(dto)
    override fun update(dtos: List<TypeBoundsDto>): AsyncResult<List<TypeBoundsDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}