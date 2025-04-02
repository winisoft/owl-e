package com.wini.owle.dll.environs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.PackageDto
import com.wini.owle.services.environs.PackageService

class PackageDll(
    private val service: PackageService
): DLL<Long, PackageDto> {

    override fun getAll(): AsyncResult<List<PackageDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<PackageDto?> = service.getById(id)
    override fun create(dto: PackageDto): AsyncResult<PackageDto> = service.create(dto)
    override fun create(dtos: List<PackageDto>): AsyncResult<List<PackageDto>> = service.create(dtos)
    override fun update(dto: PackageDto): AsyncResult<PackageDto> = service.update(dto)
    override fun update(dtos: List<PackageDto>): AsyncResult<List<PackageDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}