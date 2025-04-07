package com.wini.owle.dll.environs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.services.environs.ToolPackageDependencyService
import org.jetbrains.exposed.dao.id.CompositeID


class ToolPackageDependencyDll(
    private val service: ToolPackageDependencyService
): DLL<CompositeID, ToolPackageDependencyDto> {

    override fun getAll(): AsyncResult<List<ToolPackageDependencyDto>> = service.getAll()
    override fun getById(id: CompositeID): AsyncResult<ToolPackageDependencyDto> = service.getById(id)
    override fun create(dto: ToolPackageDependencyDto): AsyncResult<ToolPackageDependencyDto> = service.create(dto)
    override fun createAll(dtos: List<ToolPackageDependencyDto>): AsyncResult<List<ToolPackageDependencyDto>> = service.create(dtos)
    override fun update(dto: ToolPackageDependencyDto): AsyncResult<ToolPackageDependencyDto> = service.update(dto)
    override fun updateAll(dtos: List<ToolPackageDependencyDto>): AsyncResult<List<ToolPackageDependencyDto>> = service.update(dtos)
    override fun delete(id: CompositeID): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<CompositeID>): AsyncResult<Unit> = service.delete(ids)

}