package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestResultDto
import com.wini.owle.services.runs.TestResultService

class TestResultDll(
    private val service: TestResultService
): DLL<Long, TestResultDto> {

    override fun getAll(): AsyncResult<List<TestResultDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TestResultDto> = service.getById(id)
    override fun create(dto: TestResultDto): AsyncResult<TestResultDto> = service.create(dto)
    override fun createAll(dtos: List<TestResultDto>): AsyncResult<List<TestResultDto>> = service.create(dtos)
    override fun update(dto: TestResultDto): AsyncResult<TestResultDto> = service.update(dto)
    override fun updateAll(dtos: List<TestResultDto>): AsyncResult<List<TestResultDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}