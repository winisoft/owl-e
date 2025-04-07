package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestCaseDto
import com.wini.owle.services.runs.TestCaseService

class TestCaseDll(
    private val service: TestCaseService
): DLL<Long, TestCaseDto> {

    override fun getAll(): AsyncResult<List<TestCaseDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TestCaseDto> = service.getById(id)
    override fun create(dto: TestCaseDto): AsyncResult<TestCaseDto> = service.create(dto)
    override fun createAll(dtos: List<TestCaseDto>): AsyncResult<List<TestCaseDto>> = service.create(dtos)
    override fun update(dto: TestCaseDto): AsyncResult<TestCaseDto> = service.update(dto)
    override fun updateAll(dtos: List<TestCaseDto>): AsyncResult<List<TestCaseDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}