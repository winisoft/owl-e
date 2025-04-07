package com.wini.owle.dll.environs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.VersionSpecDto
import com.wini.owle.services.environs.VersionSpecService

class VersionSpecDll(
    private val service: VersionSpecService
): DLL<Long, VersionSpecDto> {

    override fun getAll(): AsyncResult<List<VersionSpecDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<VersionSpecDto> = service.getById(id)
    override fun create(dto: VersionSpecDto): AsyncResult<VersionSpecDto> = service.create(dto)
    override fun createAll(dtos: List<VersionSpecDto>): AsyncResult<List<VersionSpecDto>> = service.create(dtos)
    override fun update(dto: VersionSpecDto): AsyncResult<VersionSpecDto> = service.update(dto)
    override fun updateAll(dtos: List<VersionSpecDto>): AsyncResult<List<VersionSpecDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}