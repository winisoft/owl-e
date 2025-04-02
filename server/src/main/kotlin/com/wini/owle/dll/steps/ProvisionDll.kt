package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ProvisionDto
import com.wini.owle.services.steps.ProvisionService


class ProvisionDll(
    private val service: ProvisionService
): DLL<Long, ProvisionDto> {

    override fun getAll(): AsyncResult<List<ProvisionDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<ProvisionDto?> = service.getById(id)
    override fun create(dto: ProvisionDto): AsyncResult<ProvisionDto> = service.create(dto)
    override fun create(dtos: List<ProvisionDto>): AsyncResult<List<ProvisionDto>> = service.create(dtos)
    override fun update(dto: ProvisionDto): AsyncResult<ProvisionDto> = service.update(dto)
    override fun update(dtos: List<ProvisionDto>): AsyncResult<List<ProvisionDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}