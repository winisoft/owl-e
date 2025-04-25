package com.wini.owle.dll.environs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.VersionSpec
import com.wini.owle.services.environs.VersionSpecService

class VersionSpecDll(
    private val service: VersionSpecService
): DLL<Long, VersionSpec> {

    override fun getAll(): AsyncResult<List<VersionSpec>> = service.getAll()
    override fun getById(id: Long): AsyncResult<VersionSpec> = service.getById(id)
    override fun create(dto: VersionSpec): AsyncResult<VersionSpec> = service.create(dto)
    override fun createAll(dtos: List<VersionSpec>): AsyncResult<List<VersionSpec>> = service.create(dtos)
    override fun update(dto: VersionSpec): AsyncResult<VersionSpec> = service.update(dto)
    override fun updateAll(dtos: List<VersionSpec>): AsyncResult<List<VersionSpec>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}