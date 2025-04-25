package com.wini.owle.dll.workflows

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.ToolParam
import com.wini.owle.services.workflows.ToolParamService

class ToolParamDll(
    private val service: ToolParamService
): DLL<Long, ToolParam> {

    override fun getAll(): AsyncResult<List<ToolParam>> = service.getAll()
    override fun getById(id: Long): AsyncResult<ToolParam> = service.getById(id)
    override fun create(dto: ToolParam): AsyncResult<ToolParam> = service.create(dto)
    override fun createAll(dtos: List<ToolParam>): AsyncResult<List<ToolParam>> = service.create(dtos)
    override fun update(dto: ToolParam): AsyncResult<ToolParam> = service.update(dto)
    override fun updateAll(dtos: List<ToolParam>): AsyncResult<List<ToolParam>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}