package com.wini.owle.dll.transforms

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.TransformationTargetTypeDto
import com.wini.owle.services.transforms.TransformTargetTypeService
import org.jetbrains.exposed.dao.id.CompositeID

class TransformTargetTypeDll(
    private val service: TransformTargetTypeService
): DLL<CompositeID, TransformationTargetTypeDto> {

    override fun getAll(): AsyncResult<List<TransformationTargetTypeDto>> = service.getAll()
    override fun getById(id: CompositeID): AsyncResult<TransformationTargetTypeDto?> = service.getById(id)
    override fun create(dto: TransformationTargetTypeDto): AsyncResult<TransformationTargetTypeDto> = service.create(dto)
    override fun create(dtos: List<TransformationTargetTypeDto>): AsyncResult<List<TransformationTargetTypeDto>> = service.create(dtos)
    override fun update(dto: TransformationTargetTypeDto): AsyncResult<TransformationTargetTypeDto> = service.update(dto)
    override fun update(dtos: List<TransformationTargetTypeDto>): AsyncResult<List<TransformationTargetTypeDto>> = service.update(dtos)
    override fun delete(id: CompositeID): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<CompositeID>): AsyncResult<Boolean> = service.delete(ids)

}