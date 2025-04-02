package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.PreReqInjectionDto
import com.wini.owle.services.steps.PreReqInjectionService

class PreReqInjectionDll(
    private val service: PreReqInjectionService
): DLL<Long, PreReqInjectionDto> {

    override fun getAll(): AsyncResult<List<PreReqInjectionDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<PreReqInjectionDto?> = service.getById(id)
    override fun create(dto: PreReqInjectionDto): AsyncResult<PreReqInjectionDto> = service.create(dto)
    override fun create(dtos: List<PreReqInjectionDto>): AsyncResult<List<PreReqInjectionDto>> = service.create(dtos)
    override fun update(dto: PreReqInjectionDto): AsyncResult<PreReqInjectionDto> = service.update(dto)
    override fun update(dtos: List<PreReqInjectionDto>): AsyncResult<List<PreReqInjectionDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}