package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.services.steps.ProvisionDataPointService
import org.jetbrains.exposed.dao.id.CompositeID


class ProvisionDataPointDll(
    private val service: ProvisionDataPointService
): DLL<CompositeID, ProvisionDataPointDto> {

    override fun getAll(): AsyncResult<List<ProvisionDataPointDto>> = service.getAll()
    override fun getById(id: CompositeID): AsyncResult<ProvisionDataPointDto> = service.getById(id)
    override fun create(dto: ProvisionDataPointDto): AsyncResult<ProvisionDataPointDto> = service.create(dto)
    override fun createAll(dtos: List<ProvisionDataPointDto>): AsyncResult<List<ProvisionDataPointDto>> = service.create(dtos)
    override fun update(dto: ProvisionDataPointDto): AsyncResult<ProvisionDataPointDto> = service.update(dto)
    override fun updateAll(dtos: List<ProvisionDataPointDto>): AsyncResult<List<ProvisionDataPointDto>> = service.update(dtos)
    override fun delete(id: CompositeID): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<CompositeID>): AsyncResult<Unit> = service.delete(ids)

}