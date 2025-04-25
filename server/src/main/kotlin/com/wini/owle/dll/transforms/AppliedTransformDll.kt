package com.wini.owle.dll.transforms

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.AppliedTransform
import com.wini.owle.services.transforms.AppliedTransformService

class AppliedTransformDll(
    private val service: AppliedTransformService
): DLL<Long, AppliedTransform> {

    override fun getAll(): AsyncResult<List<AppliedTransform>> = service.getAll()
    override fun getById(id: Long): AsyncResult<AppliedTransform> = service.getById(id)
    override fun create(dto: AppliedTransform): AsyncResult<AppliedTransform> = service.create(dto)
    override fun createAll(dtos: List<AppliedTransform>): AsyncResult<List<AppliedTransform>> = service.create(dtos)
    override fun update(dto: AppliedTransform): AsyncResult<AppliedTransform> = service.update(dto)
    override fun updateAll(dtos: List<AppliedTransform>): AsyncResult<List<AppliedTransform>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}