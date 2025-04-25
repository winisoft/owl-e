package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ProvisionDataPoint
import com.wini.owle.services.steps.ProvisionDataPointService
import org.jetbrains.exposed.dao.id.CompositeID


class ProvisionDataPointDll(
    private val service: ProvisionDataPointService
): DLL<CompositeID, ProvisionDataPoint> {

    override fun getAll(): AsyncResult<List<ProvisionDataPoint>> = service.getAll()
    override fun getById(id: CompositeID): AsyncResult<ProvisionDataPoint> = service.getById(id)
    override fun create(dto: ProvisionDataPoint): AsyncResult<ProvisionDataPoint> = service.create(dto)
    override fun createAll(dtos: List<ProvisionDataPoint>): AsyncResult<List<ProvisionDataPoint>> = service.create(dtos)
    override fun update(dto: ProvisionDataPoint): AsyncResult<ProvisionDataPoint> = service.update(dto)
    override fun updateAll(dtos: List<ProvisionDataPoint>): AsyncResult<List<ProvisionDataPoint>> = service.update(dtos)
    override fun delete(id: CompositeID): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<CompositeID>): AsyncResult<Unit> = service.delete(ids)

}