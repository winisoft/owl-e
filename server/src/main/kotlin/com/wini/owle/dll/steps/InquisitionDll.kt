package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.InquisitionDto
import com.wini.owle.services.steps.InquisitionService

class InquisitionDll(
    private val service: InquisitionService
): DLL<Long, InquisitionDto> {

    override fun getAll(): AsyncResult<List<InquisitionDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<InquisitionDto?> = service.getById(id)
    override fun create(dto: InquisitionDto): AsyncResult<InquisitionDto> = service.create(dto)
    override fun create(dtos: List<InquisitionDto>): AsyncResult<List<InquisitionDto>> = service.create(dtos)
    override fun update(dto: InquisitionDto): AsyncResult<InquisitionDto> = service.update(dto)
    override fun update(dtos: List<InquisitionDto>): AsyncResult<List<InquisitionDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}