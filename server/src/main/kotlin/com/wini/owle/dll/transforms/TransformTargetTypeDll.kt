package com.wini.owle.dll.transforms

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.TransformationTargetTypeDto
import com.wini.owle.services.transforms.TransformTargetTypeService
import org.jetbrains.exposed.dao.id.CompositeID

class TransformTargetTypeDll(
    private val service: TransformTargetTypeService
): DLL<CompositeID, TransformationTargetTypeDto> {

    override fun getAll(): AsyncResult<List<TransformationTargetTypeDto>> = service.getAll()
    override fun getById(id: CompositeID): AsyncResult<TransformationTargetTypeDto> = service.getById(id)
    override fun create(dto: TransformationTargetTypeDto): AsyncResult<TransformationTargetTypeDto> = service.create(dto)
    override fun createAll(dtos: List<TransformationTargetTypeDto>): AsyncResult<List<TransformationTargetTypeDto>> = service.create(dtos)
    override fun update(dto: TransformationTargetTypeDto): AsyncResult<TransformationTargetTypeDto> = service.update(dto)
    override fun updateAll(dtos: List<TransformationTargetTypeDto>): AsyncResult<List<TransformationTargetTypeDto>> = service.update(dtos)
    override fun delete(id: CompositeID): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<CompositeID>): AsyncResult<Unit> = service.delete(ids)

}