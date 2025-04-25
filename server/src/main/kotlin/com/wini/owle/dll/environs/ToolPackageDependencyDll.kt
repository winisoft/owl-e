package com.wini.owle.dll.environs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.ToolPyPackDep
import com.wini.owle.services.environs.ToolPackageDependencyService
import org.jetbrains.exposed.dao.id.CompositeID


class ToolPackageDependencyDll(
    private val service: ToolPackageDependencyService
): DLL<CompositeID, ToolPyPackDep> {

    override fun getAll(): AsyncResult<List<ToolPyPackDep>> = service.getAll()
    override fun getById(id: CompositeID): AsyncResult<ToolPyPackDep> = service.getById(id)
    override fun create(dto: ToolPyPackDep): AsyncResult<ToolPyPackDep> = service.create(dto)
    override fun createAll(dtos: List<ToolPyPackDep>): AsyncResult<List<ToolPyPackDep>> = service.create(dtos)
    override fun update(dto: ToolPyPackDep): AsyncResult<ToolPyPackDep> = service.update(dto)
    override fun updateAll(dtos: List<ToolPyPackDep>): AsyncResult<List<ToolPyPackDep>> = service.update(dtos)
    override fun delete(id: CompositeID): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<CompositeID>): AsyncResult<Unit> = service.delete(ids)

}