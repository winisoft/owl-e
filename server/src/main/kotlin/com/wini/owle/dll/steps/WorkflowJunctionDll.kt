package com.wini.owle.dll.steps

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.WorkflowJunction
import com.wini.owle.services.steps.WorkflowJunctionService

class WorkflowJunctionDll(
    private val service: WorkflowJunctionService
): DLL<Long, WorkflowJunction> {

    override fun getAll(): AsyncResult<List<WorkflowJunction>> = service.getAll()
    override fun getById(id: Long): AsyncResult<WorkflowJunction> = service.getById(id)
    override fun create(dto: WorkflowJunction): AsyncResult<WorkflowJunction> = service.create(dto)
    override fun createAll(dtos: List<WorkflowJunction>): AsyncResult<List<WorkflowJunction>> = service.create(dtos)
    override fun update(dto: WorkflowJunction): AsyncResult<WorkflowJunction> = service.update(dto)
    override fun updateAll(dtos: List<WorkflowJunction>): AsyncResult<List<WorkflowJunction>> = service.update(dtos)
    override fun delete(id: Long): AsyncResult<Unit> = service.delete(id)
    override fun deleteAll(ids: List<Long>): AsyncResult<Unit> = service.delete(ids)

}