package com.wini.owle.dll.transforms

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.AppliedTransformArg
import com.wini.owle.services.transforms.AppliedTransformArgService

class AppliedTransformArgDll(
    private val service: AppliedTransformArgService
): DLL<Long, AppliedTransformArg> {

    override fun getAll(): AsyncResult<List<AppliedTransformArg>> = service.getAll()
    override fun getById(id: Long): AsyncResult<AppliedTransformArg> = service.getById(id)
    override fun create(dto: AppliedTransformArg): AsyncResult<AppliedTransformArg> = service.create(dto)
    override fun createAll(dtos: List<AppliedTransformArg>): AsyncResult<List<AppliedTransformArg>> = service.create(dtos)
    override fun update(dto: AppliedTransformArg): AsyncResult<AppliedTransformArg> = service.update(dto)
    override fun updateAll(dtos: List<AppliedTransformArg>): AsyncResult<List<AppliedTransformArg>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}