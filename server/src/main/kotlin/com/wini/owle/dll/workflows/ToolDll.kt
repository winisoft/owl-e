package com.wini.owle.dll.workflows

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.ToolDto
import com.wini.owle.services.workflows.ToolService

class ToolDll(
    private val service: ToolService
): DLL<Long, ToolDto> {

    override fun getAll(): AsyncResult<List<ToolDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<ToolDto> = service.getById(id)
    override fun create(dto: ToolDto): AsyncResult<ToolDto> = service.create(dto)
    override fun createAll(dtos: List<ToolDto>): AsyncResult<List<ToolDto>> = service.create(dtos)
    override fun update(dto: ToolDto): AsyncResult<ToolDto> = service.update(dto)
    override fun updateAll(dtos: List<ToolDto>): AsyncResult<List<ToolDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}