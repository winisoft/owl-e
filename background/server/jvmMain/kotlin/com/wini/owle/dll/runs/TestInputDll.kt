package com.wini.owle.dll.runs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.TestInputDto
import com.wini.owle.services.runs.TestInputService

class TestInputDll(
    private val service: TestInputService
): DLL<Long, TestInputDto> {

    override fun getAll(): AsyncResult<List<TestInputDto>> = service.getAll()
    override fun getById(id: Long): AsyncResult<TestInputDto?> = service.getById(id)
    override fun create(dto: TestInputDto): AsyncResult<TestInputDto> = service.create(dto)
    override fun create(dtos: List<TestInputDto>): AsyncResult<List<TestInputDto>> = service.create(dtos)
    override fun update(dto: TestInputDto): AsyncResult<TestInputDto> = service.update(dto)
    override fun update(dtos: List<TestInputDto>): AsyncResult<List<TestInputDto>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = service.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = service.delete(ids)

}