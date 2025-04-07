package com.wini.owle.dll.environs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.PackageDto
import com.wini.owle.services.environs.PackageService

class PackageDll(
    private val service: PackageService
): DLL<Long, PackageDto> {

    override fun getAll(): AsyncResult<List<PackageDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<PackageDto> = service.getById(id)
    override fun create(dto: PackageDto): AsyncResult<PackageDto> = service.create(dto)
    override fun createAll(dtos: List<PackageDto>): AsyncResult<List<PackageDto>> = service.create(dtos)
    override fun update(dto: PackageDto): AsyncResult<PackageDto> = service.update(dto)
    override fun updateAll(dtos: List<PackageDto>): AsyncResult<List<PackageDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}