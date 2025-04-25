package com.wini.owle.dll.environs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.PyPackage
import com.wini.owle.services.environs.PackageService

class PackageDll(
    private val service: PackageService
): DLL<Long, PyPackage> {

    override fun getAll(): AsyncResult<List<PyPackage>> = service.getAll()
    override fun getById(id: Long): AsyncResult<PyPackage> = service.getById(id)
    override fun create(dto: PyPackage): AsyncResult<PyPackage> = service.create(dto)
    override fun createAll(dtos: List<PyPackage>): AsyncResult<List<PyPackage>> = service.create(dtos)
    override fun update(dto: PyPackage): AsyncResult<PyPackage> = service.update(dto)
    override fun updateAll(dtos: List<PyPackage>): AsyncResult<List<PyPackage>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}