package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.RevisionDto
import com.wini.owle.services.steps.RevisionService

class RevisionDll(
    private val service: RevisionService
): DLL<Long, RevisionDto> {

    override fun getAll(): AsyncResult<List<RevisionDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<RevisionDto?> = service.getById(id)
    override fun create(dto: RevisionDto): AsyncResult<RevisionDto> = service.create(dto)
    override fun create(dtos: List<RevisionDto>): AsyncResult<List<RevisionDto>> = service.create(dtos)
    override fun update(dto: RevisionDto): AsyncResult<RevisionDto> = service.update(dto)
    override fun update(dtos: List<RevisionDto>): AsyncResult<List<RevisionDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}