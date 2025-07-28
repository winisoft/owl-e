package com.wini.owle.dll.workflows

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.Tool
import com.wini.owle.services.workflows.ToolService


class ToolDll(
    private val service: ToolService
): DLL<Long, Tool> {

    override fun getAll(): AsyncResult<List<Tool>> = service.getAll()
    override fun getById(id: Long): AsyncResult<Tool> = service.getById(id)
    override fun create(dto: Tool): AsyncResult<Tool> = service.create(dto)
    override fun createAll(dtos: List<Tool>): AsyncResult<List<Tool>> = service.create(dtos)
    override fun update(dto: Tool): AsyncResult<Tool> = service.update(dto)
    override fun updateAll(dtos: List<Tool>): AsyncResult<List<Tool>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}