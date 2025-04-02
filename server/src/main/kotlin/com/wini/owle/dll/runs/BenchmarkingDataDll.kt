package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.BenchmarkingDataDto
import com.wini.owle.services.runs.BenchmarkingDataService

class BenchmarkingDataDll(
    private val service: BenchmarkingDataService
): DLL<Long, BenchmarkingDataDto> {

    override fun getAll(): AsyncResult<List<BenchmarkingDataDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<BenchmarkingDataDto?> = service.getById(id)
    override fun create(dto: BenchmarkingDataDto): AsyncResult<BenchmarkingDataDto> = service.create(dto)
    override fun create(dtos: List<BenchmarkingDataDto>): AsyncResult<List<BenchmarkingDataDto>> = service.create(dtos)
    override fun update(dto: BenchmarkingDataDto): AsyncResult<BenchmarkingDataDto> = service.update(dto)
    override fun update(dtos: List<BenchmarkingDataDto>): AsyncResult<List<BenchmarkingDataDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}