package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.TestInput
import com.wini.owle.services.runs.TestInputService

class TestInputDll(
    private val service: TestInputService
): DLL<Long, TestInput> {

    override fun getAll(): AsyncResult<List<TestInput>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TestInput> = service.getById(id)
    override fun create(dto: TestInput): AsyncResult<TestInput> = service.create(dto)
    override fun createAll(dtos: List<TestInput>): AsyncResult<List<TestInput>> = service.create(dtos)
    override fun update(dto: TestInput): AsyncResult<TestInput> = service.update(dto)
    override fun updateAll(dtos: List<TestInput>): AsyncResult<List<TestInput>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}