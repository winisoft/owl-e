package com.wini.owle.dll.transforms

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.AppliedTransformDto
import com.wini.owle.services.transforms.AppliedTransformService

class AppliedTransformDll(
    private val service: AppliedTransformService
): DLL<Long, AppliedTransformDto> {

    override fun getAll(): AsyncResult<List<AppliedTransformDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<AppliedTransformDto> = service.getById(id)
    override fun create(dto: AppliedTransformDto): AsyncResult<AppliedTransformDto> = service.create(dto)
    override fun createAll(dtos: List<AppliedTransformDto>): AsyncResult<List<AppliedTransformDto>> = service.create(dtos)
    override fun update(dto: AppliedTransformDto): AsyncResult<AppliedTransformDto> = service.update(dto)
    override fun updateAll(dtos: List<AppliedTransformDto>): AsyncResult<List<AppliedTransformDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}