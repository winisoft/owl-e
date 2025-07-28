package com.wini.owle.dll.environs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.ToolPyPackDependency
import com.wini.owle.services.environs.ToolPackageDependencyService
import org.jetbrains.exposed.dao.id.CompositeID


class ToolPackageDependencyDll(
    private val service: ToolPackageDependencyService
): DLL<CompositeID, ToolPyPackDependency> {

    override fun getAll(): AsyncResult<List<ToolPyPackDependency>> = service.getAll()
    override fun getById(id: CompositeID): AsyncResult<ToolPyPackDependency> = service.getById(id)
    override fun create(dto: ToolPyPackDependency): AsyncResult<ToolPyPackDependency> = service.create(dto)
    override fun createAll(dtos: List<ToolPyPackDependency>): AsyncResult<List<ToolPyPackDependency>> = service.create(dtos)
    override fun update(dto: ToolPyPackDependency): AsyncResult<ToolPyPackDependency> = service.update(dto)
    override fun updateAll(dtos: List<ToolPyPackDependency>): AsyncResult<List<ToolPyPackDependency>> = service.update(dtos)
    override fun delete(id: CompositeID): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<CompositeID>): AsyncResult<Unit> = service.delete(ids)

}