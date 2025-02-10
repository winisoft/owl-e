package com.wini.owle.dll.transforms

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.AppliedTransformDto
import com.wini.owle.services.transforms.AppliedTransformService

class AppliedTransformDll(
    private val service: AppliedTransformService
): DLL<Long, AppliedTransformDto> {

    override fun getAll(): AsyncResult<List<AppliedTransformDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<AppliedTransformDto?> = service.getById(id)
    override fun create(dto: AppliedTransformDto): AsyncResult<AppliedTransformDto> = service.create(dto)
    override fun create(dtos: List<AppliedTransformDto>): AsyncResult<List<AppliedTransformDto>> = service.create(dtos)
    override fun update(dto: AppliedTransformDto): AsyncResult<AppliedTransformDto> = service.update(dto)
    override fun update(dtos: List<AppliedTransformDto>): AsyncResult<List<AppliedTransformDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}