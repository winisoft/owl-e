package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.services.runs.TestCaseService

class TestCaseDll(
    private val service: TestCaseService
): DLL<Long, TestCase> {

    override fun getAll(): AsyncResult<List<TestCase>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TestCase> = service.getById(id)
    override fun create(dto: TestCase): AsyncResult<TestCase> = service.create(dto)
    override fun createAll(dtos: List<TestCase>): AsyncResult<List<TestCase>> = service.create(dtos)
    override fun update(dto: TestCase): AsyncResult<TestCase> = service.update(dto)
    override fun updateAll(dtos: List<TestCase>): AsyncResult<List<TestCase>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}