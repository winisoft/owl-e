package com.wini.owle.dll.workflows

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.DataPointDto
import com.wini.owle.services.workflows.DataPointService

class DataPointDll(
    private val service: DataPointService
): DLL<Long, DataPointDto> {

    override fun getAll(): AsyncResult<List<DataPointDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<DataPointDto> = service.getById(id)
    override fun create(dto: DataPointDto): AsyncResult<DataPointDto> = service.create(dto)
    override fun createAll(dtos: List<DataPointDto>): AsyncResult<List<DataPointDto>> = service.create(dtos)
    override fun update(dto: DataPointDto): AsyncResult<DataPointDto> = service.update(dto)
    override fun updateAll(dtos: List<DataPointDto>): AsyncResult<List<DataPointDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}