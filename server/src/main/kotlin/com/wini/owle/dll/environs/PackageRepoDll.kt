package com.wini.owle.dll.environs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.services.environs.PackageRepoService

class PackageRepoDll(
    private val service: PackageRepoService
): DLL<Long, PyPackageRepo> {

    override fun getAll(): AsyncResult<List<PyPackageRepo>> = service.getAll()
    override fun getById(id: Long): AsyncResult<PyPackageRepo> = service.getById(id)
    override fun create(dto: PyPackageRepo): AsyncResult<PyPackageRepo> = service.create(dto)
    override fun createAll(dtos: List<PyPackageRepo>): AsyncResult<List<PyPackageRepo>> = service.create(dtos)
    override fun update(dto: PyPackageRepo): AsyncResult<PyPackageRepo> = service.update(dto)
    override fun updateAll(dtos: List<PyPackageRepo>): AsyncResult<List<PyPackageRepo>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)
    
}