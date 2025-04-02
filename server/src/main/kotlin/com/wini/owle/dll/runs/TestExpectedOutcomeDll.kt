package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestExpectedOutcomeDto
import com.wini.owle.services.runs.TestExpectedOutcomeService

class TestExpectedOutcomeDll(
    private val service: TestExpectedOutcomeService
): DLL<Long, TestExpectedOutcomeDto> {

    override fun getAll(): AsyncResult<List<TestExpectedOutcomeDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TestExpectedOutcomeDto?> = service.getById(id)
    override fun create(dto: TestExpectedOutcomeDto): AsyncResult<TestExpectedOutcomeDto> = service.create(dto)
    override fun create(dtos: List<TestExpectedOutcomeDto>): AsyncResult<List<TestExpectedOutcomeDto>> = service.create(dtos)
    override fun update(dto: TestExpectedOutcomeDto): AsyncResult<TestExpectedOutcomeDto> = service.update(dto)
    override fun update(dtos: List<TestExpectedOutcomeDto>): AsyncResult<List<TestExpectedOutcomeDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}