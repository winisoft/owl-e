package com.wini.owle.dll.environs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.PackageRepoDto
import com.wini.owle.services.environs.PackageRepoService

class PackageRepoDll(
    private val service: PackageRepoService
): DLL<Long, PackageRepoDto> {

    override fun getAll(): AsyncResult<List<PackageRepoDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<PackageRepoDto?> = service.getById(id)
    override fun create(dto: PackageRepoDto): AsyncResult<PackageRepoDto> = service.create(dto)
    override fun create(dtos: List<PackageRepoDto>): AsyncResult<List<PackageRepoDto>> = service.create(dtos)
    override fun update(dto: PackageRepoDto): AsyncResult<PackageRepoDto> = service.update(dto)
    override fun update(dtos: List<PackageRepoDto>): AsyncResult<List<PackageRepoDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}