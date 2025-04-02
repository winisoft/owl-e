package com.wini.owle.dll.transforms

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.TransformationArgDto
import com.wini.owle.services.transforms.TransformationArgService

class TransformationArgDll(
    private val service: TransformationArgService
): DLL<Long, TransformationArgDto> {

    override fun getAll(): AsyncResult<List<TransformationArgDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TransformationArgDto?> = service.getById(id)
    override fun create(dto: TransformationArgDto): AsyncResult<TransformationArgDto> = service.create(dto)
    override fun create(dtos: List<TransformationArgDto>): AsyncResult<List<TransformationArgDto>> = service.create(dtos)
    override fun update(dto: TransformationArgDto): AsyncResult<TransformationArgDto> = service.update(dto)
    override fun update(dtos: List<TransformationArgDto>): AsyncResult<List<TransformationArgDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}