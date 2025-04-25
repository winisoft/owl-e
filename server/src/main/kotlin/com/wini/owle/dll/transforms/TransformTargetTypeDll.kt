package com.wini.owle.dll.transforms

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.services.transforms.TransformTargetTypeService
import org.jetbrains.exposed.dao.id.CompositeID

class TransformTargetTypeDll(
    private val service: TransformTargetTypeService
): DLL<CompositeID, TransformTargetType> {

    override fun getAll(): AsyncResult<List<TransformTargetType>> = service.getAll()
    override fun getById(id: CompositeID): AsyncResult<TransformTargetType> = service.getById(id)
    override fun create(dto: TransformTargetType): AsyncResult<TransformTargetType> = service.create(dto)
    override fun createAll(dtos: List<TransformTargetType>): AsyncResult<List<TransformTargetType>> = service.create(dtos)
    override fun update(dto: TransformTargetType): AsyncResult<TransformTargetType> = service.update(dto)
    override fun updateAll(dtos: List<TransformTargetType>): AsyncResult<List<TransformTargetType>> = service.update(dtos)
    override fun delete(id: CompositeID): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<CompositeID>): AsyncResult<Unit> = service.delete(ids)

}