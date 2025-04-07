package com.wini.owle.dll.workflows

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.ToolParamDto
import com.wini.owle.services.workflows.ToolParamService

class ToolParamDll(
    private val service: ToolParamService
): DLL<Long, ToolParamDto> {

    override fun getAll(): AsyncResult<List<ToolParamDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<ToolParamDto> = service.getById(id)
    override fun create(dto: ToolParamDto): AsyncResult<ToolParamDto> = service.create(dto)
    override fun createAll(dtos: List<ToolParamDto>): AsyncResult<List<ToolParamDto>> = service.create(dtos)
    override fun update(dto: ToolParamDto): AsyncResult<ToolParamDto> = service.update(dto)
    override fun updateAll(dtos: List<ToolParamDto>): AsyncResult<List<ToolParamDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}