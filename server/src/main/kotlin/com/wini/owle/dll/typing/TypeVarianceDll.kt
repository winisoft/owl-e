package com.wini.owle.dll.typing

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeVarianceDto
import com.wini.owle.services.typing.TypeVarianceService

class TypeVarianceDll(
    private val service: TypeVarianceService
): DLL<Long, TypeVarianceDto> {

    override fun getAll(): AsyncResult<List<TypeVarianceDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TypeVarianceDto?> = service.getById(id)
    override fun create(dto: TypeVarianceDto): AsyncResult<TypeVarianceDto> = service.create(dto)
    override fun create(dtos: List<TypeVarianceDto>): AsyncResult<List<TypeVarianceDto>> = service.create(dtos)
    override fun update(dto: TypeVarianceDto): AsyncResult<TypeVarianceDto> = service.update(dto)
    override fun update(dtos: List<TypeVarianceDto>): AsyncResult<List<TypeVarianceDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}