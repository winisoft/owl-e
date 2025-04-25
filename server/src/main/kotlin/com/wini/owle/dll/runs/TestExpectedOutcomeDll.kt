package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.TestExpectation
import com.wini.owle.services.runs.TestExpectedOutcomeService

class TestExpectedOutcomeDll(
    private val service: TestExpectedOutcomeService
): DLL<Long, TestExpectation> {

    override fun getAll(): AsyncResult<List<TestExpectation>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TestExpectation> = service.getById(id)
    override fun create(dto: TestExpectation): AsyncResult<TestExpectation> = service.create(dto)
    override fun createAll(dtos: List<TestExpectation>): AsyncResult<List<TestExpectation>> = service.create(dtos)
    override fun update(dto: TestExpectation): AsyncResult<TestExpectation> = service.update(dto)
    override fun updateAll(dtos: List<TestExpectation>): AsyncResult<List<TestExpectation>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}