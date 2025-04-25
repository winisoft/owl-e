package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.DataPointSave
import com.wini.owle.services.steps.RevisionService

class RevisionDll(
    private val service: RevisionService
): DLL<Long, DataPointSave> {

    override fun getAll(): AsyncResult<List<DataPointSave>> = service.getAll()
    override fun getById(id: Long): AsyncResult<DataPointSave> = service.getById(id)
    override fun create(dto: DataPointSave): AsyncResult<DataPointSave> = service.create(dto)
    override fun createAll(dtos: List<DataPointSave>): AsyncResult<List<DataPointSave>> = service.create(dtos)
    override fun update(dto: DataPointSave): AsyncResult<DataPointSave> = service.update(dto)
    override fun updateAll(dtos: List<DataPointSave>): AsyncResult<List<DataPointSave>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}