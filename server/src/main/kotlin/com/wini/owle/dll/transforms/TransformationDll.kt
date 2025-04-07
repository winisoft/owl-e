package com.wini.owle.dll.transforms

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.TransformationDto
import com.wini.owle.services.transforms.TransformationService

class TransformationDll(
    private val service: TransformationService
): DLL<Long, TransformationDto> {

    override fun getAll(): AsyncResult<List<TransformationDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TransformationDto> = service.getById(id)
    override fun create(dto: TransformationDto): AsyncResult<TransformationDto> = service.create(dto)
    override fun createAll(dtos: List<TransformationDto>): AsyncResult<List<TransformationDto>> = service.create(dtos)
    override fun update(dto: TransformationDto): AsyncResult<TransformationDto> = service.update(dto)
    override fun updateAll(dtos: List<TransformationDto>): AsyncResult<List<TransformationDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}