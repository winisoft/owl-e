package com.wini.owle.dll.transforms

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.services.transforms.TransformationArgService

class TransformationArgDll(
    private val service: TransformationArgService
): DLL<Long, TransformArg> {

    override fun getAll(): AsyncResult<List<TransformArg>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TransformArg> = service.getById(id)
    override fun create(dto: TransformArg): AsyncResult<TransformArg> = service.create(dto)
    override fun createAll(dtos: List<TransformArg>): AsyncResult<List<TransformArg>> = service.create(dtos)
    override fun update(dto: TransformArg): AsyncResult<TransformArg> = service.update(dto)
    override fun updateAll(dtos: List<TransformArg>): AsyncResult<List<TransformArg>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}