package com.wini.owle.dll.transforms

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.Transform
import com.wini.owle.services.transforms.TransformationService

class TransformationDll(
    private val service: TransformationService
): DLL<Long, Transform> {

    override fun getAll(): AsyncResult<List<Transform>> = service.getAll()
    override fun getById(id: Long): AsyncResult<Transform> = service.getById(id)
    override fun create(dto: Transform): AsyncResult<Transform> = service.create(dto)
    override fun createAll(dtos: List<Transform>): AsyncResult<List<Transform>> = service.create(dtos)
    override fun update(dto: Transform): AsyncResult<Transform> = service.update(dto)
    override fun updateAll(dtos: List<Transform>): AsyncResult<List<Transform>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}