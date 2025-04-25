package com.wini.owle.dll.workflows

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.services.workflows.DataPointService

class DataPointDll(
    private val service: DataPointService
): DLL<Long, DataPoint> {

    override fun getAll(): AsyncResult<List<DataPoint>> = service.getAll()
    override fun getById(id: Long): AsyncResult<DataPoint> = service.getById(id)
    override fun create(dto: DataPoint): AsyncResult<DataPoint> = service.create(dto)
    override fun createAll(dtos: List<DataPoint>): AsyncResult<List<DataPoint>> = service.create(dtos)
    override fun update(dto: DataPoint): AsyncResult<DataPoint> = service.update(dto)
    override fun updateAll(dtos: List<DataPoint>): AsyncResult<List<DataPoint>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}