package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.BenchmarkReport
import com.wini.owle.services.runs.BenchmarkingDataService

class BenchmarkingDataDll(
    private val service: BenchmarkingDataService
): DLL<Long, BenchmarkReport> {

    override fun getAll(): AsyncResult<List<BenchmarkReport>> = service.getAll()
    override fun getById(id: Long): AsyncResult<BenchmarkReport> = service.getById(id)
    override fun create(dto: BenchmarkReport): AsyncResult<BenchmarkReport> = service.create(dto)
    override fun createAll(dtos: List<BenchmarkReport>): AsyncResult<List<BenchmarkReport>> = service.create(dtos)
    override fun update(dto: BenchmarkReport): AsyncResult<BenchmarkReport> = service.update(dto)
    override fun updateAll(dtos: List<BenchmarkReport>): AsyncResult<List<BenchmarkReport>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}