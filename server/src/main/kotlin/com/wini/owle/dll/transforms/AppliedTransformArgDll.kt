package com.wini.owle.dll.transforms

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.AppliedTransformArgDto
import com.wini.owle.services.transforms.AppliedTransformArgService

class AppliedTransformArgDll(
    private val service: AppliedTransformArgService
): DLL<Long, AppliedTransformArgDto> {

    override fun getAll(): AsyncResult<List<AppliedTransformArgDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<AppliedTransformArgDto> = service.getById(id)
    override fun create(dto: AppliedTransformArgDto): AsyncResult<AppliedTransformArgDto> = service.create(dto)
    override fun createAll(dtos: List<AppliedTransformArgDto>): AsyncResult<List<AppliedTransformArgDto>> = service.create(dtos)
    override fun update(dto: AppliedTransformArgDto): AsyncResult<AppliedTransformArgDto> = service.update(dto)
    override fun updateAll(dtos: List<AppliedTransformArgDto>): AsyncResult<List<AppliedTransformArgDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}